package org.kuali.kpme.tklm.time.missedpunch;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.assignment.Assignment;
import org.kuali.kpme.core.api.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.core.api.assignment.service.AssignmentService;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.service.timezone.TimezoneService;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.api.common.TkConstants;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.api.time.clocklog.ClockLog;
import org.kuali.kpme.tklm.api.time.clocklog.ClockLogService;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlockService;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.time.clocklog.ClockLogBo;
import org.kuali.kpme.tklm.time.missedpunch.dao.MissedPunchDao;
import org.kuali.kpme.tklm.time.rules.TkRuleControllerService;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.kpme.tklm.time.timesheet.service.TimesheetService;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.DocumentService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class MissedPunchServiceImpl implements MissedPunchService {
    private MissedPunchDao missedPunchDao;

    private AssignmentService assignmentService;
    private BusinessObjectService businessObjectService;
    private ClockLogService clockLogService;
    private DocumentService documentService;
    private IdentityService identityService;
    private TimeBlockService timeBlockService;
    private TimesheetService timesheetService;
    private TimezoneService timezoneService;
    private TkRuleControllerService tkRuleControllerService;
    
    @Override
    public List<MissedPunch> getMissedPunchByTimesheetDocumentId(String timesheetDocumentId) {
        return missedPunchDao.getMissedPunchesByTimesheetDocumentId(timesheetDocumentId);
    }

    @Override
    public MissedPunch getMissedPunchByClockLogId(String clockLogId) {
        return missedPunchDao.getMissedPunchByClockLogId(clockLogId);
    }

    @Override
    public MissedPunch addClockLog(MissedPunch missedPunch, String ipAddress) {
        TimesheetDocument timesheetDocument = timesheetService.getTimesheetDocument(missedPunch.getTimesheetDocumentId());
        AssignmentDescriptionKey assignmentDescriptionKey = new AssignmentDescriptionKey(missedPunch.getJobNumber(), missedPunch.getWorkArea(), missedPunch.getTask());
        Assignment assignment = HrServiceLocator.getAssignmentService().getAssignment(missedPunch.getPrincipalId(), assignmentDescriptionKey, LocalDate.fromDateFields(missedPunch.getActionDate()));
        CalendarEntry calendarEntry = timesheetDocument.getCalendarEntry();

        // use the actual date and time from the document to build the date time with user zone, then apply system time zone to it
        String dateString = TKUtils.formatDateTimeShort(missedPunch.getActionFullDateTime());
        String longDateString = TKUtils.formatDateTimeLong(missedPunch.getActionFullDateTime());
        String timeString = TKUtils.formatTimeShort(longDateString);

        DateTime dateTimeWithUserZone = TKUtils.convertDateStringToDateTime(dateString, timeString);
        DateTime actionDateTime = dateTimeWithUserZone.withZone(TKUtils.getSystemDateTimeZone());

        String clockAction = missedPunch.getClockAction();
        String principalId = timesheetDocument.getPrincipalId();

        ClockLog clockLog = clockLogService.processClockLog(principalId, timesheetDocument.getDocumentId(), actionDateTime, assignment, calendarEntry, ipAddress, LocalDate.now(), clockAction, false);

        clockLog = clockLogService.saveClockLog(clockLog);
        missedPunch.setTkClockLogId(clockLog.getTkClockLogId());

        if (StringUtils.equals(clockLog.getClockAction(), TkConstants.CLOCK_OUT) ||
                StringUtils.equals(clockLog.getClockAction(), TkConstants.LUNCH_OUT)) {
            ClockLog lastClockLog = clockLogService.getLastClockLog(missedPunch.getPrincipalId());
            // KPME-2735 This is where it creates a zero timeblock...  So we will check the clock id of clockLog and lastClockLog and 
            // if they are the same, we will assume it's trying to create a zero timeblock for the same clock action, therefore skip the code 
            if (!clockLog.getTkClockLogId().equals(lastClockLog.getTkClockLogId())) {
                String earnCode = assignment.getJob().getPayTypeObj().getRegEarnCode();
                buildTimeBlockRunRules(lastClockLog, clockLog, timesheetDocument, assignment, earnCode, lastClockLog.getClockDateTime(), clockLog.getClockDateTime());
            }
        }
        return missedPunch;
    }

    @Override
    public MissedPunch updateClockLog(MissedPunch missedPunch, String ipAddress) {
        DateTime actionDateTime = missedPunch.getActionFullDateTime();

        ClockLog clockLog = clockLogService.getClockLog(missedPunch.getTkClockLogId());
        if (clockLog != null && !clockLog.getClockDateTime().equals(actionDateTime)){
            String clockLogEndId = null;
            String clockLogBeginId = null;

            List<TimeBlock> timeBlocks = timeBlockService.getTimeBlocksForClockLogEndId(clockLog.getTkClockLogId());
            if (timeBlocks.isEmpty()) {
                timeBlocks = timeBlockService.getTimeBlocksForClockLogBeginId(clockLog.getTkClockLogId());
                if (!timeBlocks.isEmpty()) {
                    clockLogEndId = timeBlocks.get(0).getClockLogEndId();
                }
            } else {
                clockLogBeginId = timeBlocks.get(0).getClockLogBeginId();
            }

            deleteClockLogAndTimeBlocks(clockLog, timeBlocks);

            addClockLogAndTimeBlocks(missedPunch, ipAddress, clockLogEndId, clockLogBeginId);
        }
        return missedPunch;
    }

    protected void deleteClockLogAndTimeBlocks(ClockLog clockLog, List<TimeBlock> timeBlocks) {
        if (clockLog != null) {
            ClockLogBo bo = ClockLogBo.from(clockLog);
            businessObjectService.delete(bo);
        }

        if (CollectionUtils.isNotEmpty(timeBlocks)) {
            for (TimeBlock timeBlock : timeBlocks) {
                timeBlockService.deleteTimeBlock(timeBlock);
            }
        }
    }

    protected void addClockLogAndTimeBlocks(MissedPunch missedPunch, String ipAddress, String logEndId, String logBeginId) {
        TimesheetDocument timesheetDocument = timesheetService.getTimesheetDocument(missedPunch.getTimesheetDocumentId());
        AssignmentDescriptionKey assignmentDescriptionKey = new AssignmentDescriptionKey(missedPunch.getJobNumber(), missedPunch.getWorkArea(), missedPunch.getTask());
        Assignment assignment = HrServiceLocator.getAssignmentService().getAssignment(missedPunch.getPrincipalId(), assignmentDescriptionKey, LocalDate.fromDateFields(missedPunch.getActionDate()));
        CalendarEntry calendarEntry = timesheetDocument.getCalendarEntry();
        DateTime userActionDateTime = missedPunch.getActionFullDateTime();
        DateTimeZone userTimeZone = HrServiceLocator.getTimezoneService().getUserTimezoneWithFallback();
        DateTime actionDateTime = new DateTime(userActionDateTime, userTimeZone).withZone(TKUtils.getSystemDateTimeZone());
        String clockAction = missedPunch.getClockAction();
        String principalId = timesheetDocument.getPrincipalId();

        ClockLog clockLog = clockLogService.processClockLog(principalId, timesheetDocument.getDocumentId(), actionDateTime, assignment, calendarEntry, ipAddress, LocalDate.now(), clockAction, false);

        clockLogService.saveClockLog(clockLog);
        missedPunch.setActionFullDateTime(clockLog.getClockDateTime());
        missedPunch.setTkClockLogId(clockLog.getTkClockLogId());

        if (logEndId != null || logBeginId != null) {
            ClockLog endLog = null;
            ClockLog beginLog = null;

            if (logEndId != null) {
                endLog = clockLogService.getClockLog(logEndId);
            } else {
                endLog = clockLog;
            }

            if (logBeginId != null) {
                beginLog = clockLogService.getClockLog(logBeginId);
            } else {
                beginLog = clockLog;
            }

            if (beginLog != null && endLog != null && beginLog.getClockDateTime().isBefore(endLog.getClockDateTime())) {
                String earnCode = assignment.getJob().getPayTypeObj().getRegEarnCode();
                buildTimeBlockRunRules(beginLog, endLog, timesheetDocument, assignment, earnCode, beginLog.getClockDateTime(), endLog.getClockDateTime());
            }
        }
    }

    /**
     * Helper method to build time blocks and fire the rules processing. This
     * should be called only if there was a CLOCK_OUT action.
     */
    private void buildTimeBlockRunRules(ClockLog beginClockLog, ClockLog endClockLog, TimesheetDocument tdoc, Assignment currentAssignment, String earnCode, DateTime beginDateTime, DateTime endDateTime) {
        // New Time Blocks, pointer reference
        List<TimeBlock> newTimeBlocks = tdoc.getTimeBlocks();
        List<TimeBlock> referenceTimeBlocks = new ArrayList<TimeBlock>();
        boolean createNewTb = true;
        for (TimeBlock tb : newTimeBlocks) {
            if(beginClockLog != null && StringUtils.isNotBlank(tb.getClockLogBeginId()) && tb.getClockLogBeginId().equals(beginClockLog.getTkClockLogId())
                    && endClockLog != null && StringUtils.isNotBlank(tb.getClockLogEndId()) && tb.getClockLogEndId().equals(endClockLog.getTkClockLogId())) {
                // if there's already time block created with the same clock logs, don't create timeblock for it again
                createNewTb = false;
            }
            referenceTimeBlocks.add(TimeBlock.Builder.create(tb).build());
        }

        if(createNewTb) {
            // Add TimeBlocks after we store our reference object!
            List<TimeBlock> blocks = timeBlockService.buildTimeBlocks(tdoc.getPrincipalId(), tdoc.getCalendarEntry(),
                    currentAssignment, earnCode, tdoc.getDocumentId(), beginDateTime,
                    endDateTime, BigDecimal.ZERO, BigDecimal.ZERO, true, false, HrContext.getPrincipalId(),
                    beginClockLog != null ? beginClockLog.getTkClockLogId() : null,
                    endClockLog != null ? endClockLog.getTkClockLogId() : null);

            newTimeBlocks.addAll(blocks);
        }

        List<Assignment> assignments = tdoc.getAssignments();
        List<String> assignmentKeys = new ArrayList<String>();
        for (Assignment assignment : assignments) {
            assignmentKeys.add(assignment.getAssignmentKey());
        }
        List<LeaveBlock> leaveBlocks = LmServiceLocator.getLeaveBlockService().getLeaveBlocksForTimeCalendar(tdoc.getPrincipalId(), tdoc.getAsOfDate(), tdoc.getDocEndDate(), assignmentKeys);

        //reset time block
        newTimeBlocks = timesheetService.resetTimeBlock(newTimeBlocks, tdoc.getAsOfDate());
        //apply any rules for this action
        newTimeBlocks = tkRuleControllerService.applyRules(
                TkConstants.ACTIONS.CLOCK_OUT, newTimeBlocks, leaveBlocks,
                tdoc.getCalendarEntry(),
                tdoc, tdoc.getPrincipalId()
        );

        timeBlockService.saveOrUpdateTimeBlocks(referenceTimeBlocks, newTimeBlocks, HrContext.getPrincipalId());
    }

    public void setMissedPunchDao(MissedPunchDao missedPunchDao) {
        this.missedPunchDao = missedPunchDao;
    }

    public void setAssignmentService(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    public void setBusinessObjectService(BusinessObjectService businessObjectService) {
        this.businessObjectService = businessObjectService;
    }

    public void setClockLogService(ClockLogService clockLogService) {
        this.clockLogService = clockLogService;
    }

    public void setDocumentService(DocumentService documentService) {
        this.documentService = documentService;
    }

    public void setIdentityService(IdentityService identityService) {
        this.identityService = identityService;
    }

    public void setTimeBlockService(TimeBlockService timeBlockService) {
        this.timeBlockService = timeBlockService;
    }

    public void setTimesheetService(TimesheetService timesheetService) {
        this.timesheetService = timesheetService;
    }

    public void setTimezoneService(TimezoneService timezoneService) {
        this.timezoneService = timezoneService;
    }

    public void setTkRuleControllerService(TkRuleControllerService tkRuleControllerService) {
        this.tkRuleControllerService = tkRuleControllerService;
    }
}
