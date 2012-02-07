package org.kuali.hr.time.timesheet.service;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.holidaycalendar.HolidayCalendar;
import org.kuali.hr.time.holidaycalendar.HolidayCalendarDateEntry;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.kew.exception.WorkflowException;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kew.service.WorkflowDocument;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class TimesheetServiceImpl implements TimesheetService {

    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(TimesheetServiceImpl.class);

    @Override
    public void routeTimesheet(String principalId, TimesheetDocument timesheetDocument) {
        routeTimesheet(TkConstants.TIMESHEET_ACTIONS.ROUTE, principalId, timesheetDocument);
    }

    @Override
    public void routeTimesheet(String action, String principalId, TimesheetDocument timesheetDocument) {
        timesheetAction(action, principalId, timesheetDocument);
    }

    @Override
    public void approveTimesheet(String principalId, TimesheetDocument timesheetDocument) {
        timesheetAction(TkConstants.TIMESHEET_ACTIONS.APPROVE, principalId, timesheetDocument);
    }

    @Override
    public void approveTimesheet(String principalId, TimesheetDocument timesheetDocument, String action) {
        timesheetAction(action, principalId, timesheetDocument);
    }

    @Override
    public void disapproveTimesheet(String principalId, TimesheetDocument timesheetDocument) {
        timesheetAction(TkConstants.TIMESHEET_ACTIONS.DISAPPROVE, principalId, timesheetDocument);
    }

    protected void timesheetAction(String action, String principalId, TimesheetDocument timesheetDocument) {
        WorkflowDocument wd = null;
        if (timesheetDocument != null) {
            try {
                String rhid = timesheetDocument.getDocumentId();
                wd = new WorkflowDocument(principalId, Long.parseLong(rhid));

                if (StringUtils.equals(action, TkConstants.TIMESHEET_ACTIONS.ROUTE)) {
                    wd.routeDocument("Routing for Approval");
                } else if (StringUtils.equals(action, TkConstants.BATCH_JOB_ACTIONS.BATCH_JOB_ROUTE)) {
                    wd.routeDocument("Batch job routing for Approval");
                } else if (StringUtils.equals(action, TkConstants.TIMESHEET_ACTIONS.APPROVE)) {
                    if (TKContext.getUser().getCurrentTargetRoles().isSystemAdmin() &&
                            !TKContext.getUser().getCurrentTargetRoles().isApproverForTimesheet(timesheetDocument)) {
                        wd.superUserApprove("Superuser approving timesheet.");
                    } else {
                        wd.approve("Approving timesheet.");
                    }
                } else if (StringUtils.equals(action, TkConstants.BATCH_JOB_ACTIONS.BATCH_JOB_APPROVE)) {
                    wd.superUserApprove("Batch job superuser approving timesheet.");
                } else if (StringUtils.equals(action, TkConstants.TIMESHEET_ACTIONS.DISAPPROVE)) {
                    if (TKContext.getUser().getCurrentTargetRoles().isSystemAdmin()
                            && !TKContext.getUser().getCurrentTargetRoles().isApproverForTimesheet(timesheetDocument)) {
                        wd.superUserDisapprove("Superuser disapproving timesheet.");
                    } else {
                        wd.disapprove("Disapproving timesheet.");
                    }
                }

                String kewStatus = KEWServiceLocator.getWorkflowUtilityService().getDocumentStatus(Long.parseLong(timesheetDocument.getDocumentHeader().getDocumentId()));
                if (!kewStatus.equals(timesheetDocument.getDocumentHeader().getDocumentStatus())) {
                    timesheetDocument.getDocumentHeader().setDocumentStatus(kewStatus);
                    TkServiceLocator.getTimesheetDocumentHeaderService().saveOrUpdate(timesheetDocument.getDocumentHeader());
                }
            } catch (WorkflowException e) {
                throw new RuntimeException("Exception during route", e);
            }
        }
    }

    @Override
    public TimesheetDocument openTimesheetDocument(String principalId, CalendarEntries calendarDates) throws WorkflowException {
        TimesheetDocument timesheetDocument = null;

        Date begin = calendarDates.getBeginPeriodDateTime();
        Date end = calendarDates.getEndPeriodDateTime();

        TimesheetDocumentHeader header = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(principalId, begin, end);

        if (header == null) {
            List<Assignment> activeAssignments = TkServiceLocator.getAssignmentService().getAssignmentsByPayEntry(principalId, calendarDates);
            //TkServiceLocator.getAssignmentService().getAssignments(principalId, TKUtils.getTimelessDate(payCalendarDates.getEndPeriodDate()));
            if (activeAssignments.size() == 0) {
                throw new RuntimeException("No active assignments for " + principalId + " for " + calendarDates.getEndPeriodDate());
            }
            timesheetDocument = this.initiateWorkflowDocument(principalId, begin, end, TimesheetDocument.TIMESHEET_DOCUMENT_TYPE, TimesheetDocument.TIMESHEET_DOCUMENT_TITLE);
            timesheetDocument.setPayCalendarEntry(calendarDates);
            this.loadTimesheetDocumentData(timesheetDocument, principalId, calendarDates);
            //TODO switch this to scheduled time offs
            //this.loadHolidaysOnTimesheet(timesheetDocument, principalId, begin, end);
        } else {
            timesheetDocument = this.getTimesheetDocument(header.getDocumentId());
            timesheetDocument.setPayCalendarEntry(calendarDates);
        }

        timesheetDocument.setTimeSummary(TkServiceLocator.getTimeSummaryService().getTimeSummary(timesheetDocument));
        return timesheetDocument;
    }

    public void loadHolidaysOnTimesheet(TimesheetDocument timesheetDocument, String principalId, Date beginDate, Date endDate) {
        PrincipalHRAttributes principalCalendar = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, new java.sql.Date(beginDate.getTime()));
        if (principalCalendar != null) {
            HolidayCalendar holidayCalendar = TkServiceLocator.getHolidayCalendarService().getHolidayCalendarByGroup(principalCalendar.getHolidayCalendarGroup());
            if (holidayCalendar != null) {
                List<HolidayCalendarDateEntry> lstHolidays = TkServiceLocator.getHolidayCalendarService().getHolidayCalendarDateEntriesForPayPeriod(holidayCalendar.getHrHolidayCalendarId(),
                        beginDate, endDate);
                for (HolidayCalendarDateEntry holiday : lstHolidays) {
                    Assignment holidayAssign = TkServiceLocator.getHolidayCalendarService().getAssignmentToApplyHolidays(timesheetDocument, TKUtils.getTimelessDate(endDate));
                    if (holidayAssign != null) {
                        BigDecimal holidayCalcHours = TkServiceLocator.getHolidayCalendarService().calculateHolidayHours(holidayAssign.getJob(), holiday.getHolidayHours());
                        TimeBlock timeBlock = TkServiceLocator.getTimeBlockService().createTimeBlock(timesheetDocument, new Timestamp(holiday.getHolidayDate().getTime()),
                                new Timestamp(holiday.getHolidayDate().getTime()), holidayAssign, TkConstants.HOLIDAY_EARN_CODE, holidayCalcHours, BigDecimal.ZERO, false, false);
                        timesheetDocument.getTimeBlocks().add(timeBlock);
                    }
                }

                //If holidays are loaded will need to save them to the database
                if (!lstHolidays.isEmpty()) {
                    TkServiceLocator.getTimeBlockService().saveTimeBlocks(new LinkedList<TimeBlock>(), timesheetDocument.getTimeBlocks());
                }
            }
        }

    }

    protected TimesheetDocument initiateWorkflowDocument(String principalId, Date payBeginDate, Date payEndDate, String documentType, String title) throws WorkflowException {
        TimesheetDocument timesheetDocument = null;
        WorkflowDocument workflowDocument = null;

        workflowDocument = new WorkflowDocument(principalId, documentType);
        workflowDocument.setTitle(title);

        String status = workflowDocument.getRouteHeader().getDocRouteStatus();
        TimesheetDocumentHeader documentHeader = new TimesheetDocumentHeader(workflowDocument.getRouteHeaderId().toString(), principalId, payBeginDate, payEndDate, status);

        documentHeader.setDocumentId(workflowDocument.getRouteHeaderId().toString());
        documentHeader.setDocumentStatus("I");

        TkServiceLocator.getTimesheetDocumentHeaderService().saveOrUpdate(documentHeader);
        timesheetDocument = new TimesheetDocument(documentHeader);

        TkServiceLocator.getTkSearchableAttributeService().updateSearchableAttribute(timesheetDocument, payEndDate);

        return timesheetDocument;
    }

    public List<TimeBlock> getPrevDocumentTimeBlocks(String principalId, Date payBeginDate) {
        TimesheetDocumentHeader prevTdh = TkServiceLocator.getTimesheetDocumentHeaderService().getPreviousDocumentHeader(principalId, payBeginDate);
        if (prevTdh == null) {
            return new ArrayList<TimeBlock>();
        }
        return TkServiceLocator.getTimeBlockService().getTimeBlocks(Long.parseLong(prevTdh.getDocumentId()));
    }

    @Override
    public TimesheetDocument getTimesheetDocument(String documentId) {
        TimesheetDocument timesheetDocument = null;
        TimesheetDocumentHeader tdh = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(documentId);

        if (tdh != null) {
            timesheetDocument = new TimesheetDocument(tdh);
            CalendarEntries pce = TkServiceLocator.getCalendarSerivce().getCalendarDatesByPayEndDate(tdh.getPrincipalId(), tdh.getPayEndDate());
            loadTimesheetDocumentData(timesheetDocument, tdh.getPrincipalId(), pce);

            timesheetDocument.setPayCalendarEntry(pce);
        } else {
            throw new RuntimeException("Could not find TimesheetDocumentHeader for DocumentID: " + documentId);
        }
        return timesheetDocument;
    }

    protected void loadTimesheetDocumentData(TimesheetDocument tdoc, String principalId, CalendarEntries payCalEntry) {
        List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignmentsByPayEntry(principalId, payCalEntry);
        List<Job> jobs = TkServiceLocator.getJobSerivce().getJobs(principalId, TKUtils.getTimelessDate(payCalEntry.getEndPeriodDate()));
        List<TimeBlock> timeBlocks = TkServiceLocator.getTimeBlockService().getTimeBlocks(Long.parseLong(tdoc.getDocumentHeader().getDocumentId()));

        tdoc.setAssignments(assignments);
        tdoc.setJobs(jobs);
        tdoc.setTimeBlocks(timeBlocks);
    }

    public boolean isSynchronousUser() {
        List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignments(TKContext.getUser().getTargetPrincipalId(), TKUtils.getCurrentDate());
        boolean isSynchronousUser = true;
        for (Assignment assignment : assignments) {
            isSynchronousUser &= assignment.isSynchronous();
        }
        return isSynchronousUser;
    }

    //this is an admin function used for testing
    public void deleteTimesheet(String documentId) {
        TkServiceLocator.getTimeBlockService().deleteTimeBlocksAssociatedWithDocumentId(documentId);
        TkServiceLocator.getTimesheetDocumentHeaderService().deleteTimesheetHeader(documentId);
    }

    public TimeBlock resetWorkedHours(TimeBlock timeBlock) {
        if (timeBlock.getBeginTime() != null && timeBlock.getEndTime() != null && StringUtils.equals(timeBlock.getEarnCodeType(), TkConstants.EARN_CODE_TIME)) {
            BigDecimal hours = TKUtils.getHoursBetween(timeBlock.getBeginTime().getTime(), timeBlock.getEndTime().getTime());
            timeBlock.setHours(hours);
        }
        return timeBlock;
    }

    @Override
    public void resetTimeBlock(List<TimeBlock> timeBlocks) {
        for (TimeBlock tb : timeBlocks) {
            resetWorkedHours(tb);
        }
        TkServiceLocator.getTimeBlockService().resetTimeHourDetail(timeBlocks);
    }

}