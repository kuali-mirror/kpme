/**
 * Copyright 2004-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kpme.tklm.time.missedpunch.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.kuali.kpme.core.bo.assignment.Assignment;
import org.kuali.kpme.core.bo.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.core.bo.assignment.service.AssignmentService;
import org.kuali.kpme.core.service.timezone.TimezoneService;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.tklm.common.TkConstants;
import org.kuali.kpme.tklm.time.clocklog.ClockLog;
import org.kuali.kpme.tklm.time.clocklog.service.ClockLogService;
import org.kuali.kpme.tklm.time.missedpunch.MissedPunch;
import org.kuali.kpme.tklm.time.missedpunch.MissedPunchDocument;
import org.kuali.kpme.tklm.time.missedpunch.dao.MissedPunchDao;
import org.kuali.kpme.tklm.time.rules.TkRuleControllerService;
import org.kuali.kpme.tklm.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.time.timeblock.service.TimeBlockService;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.kpme.tklm.time.timesheet.service.TimesheetService;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kew.api.WorkflowDocumentFactory;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.DocumentService;

public class MissedPunchServiceImpl implements MissedPunchService {
	
	private static final Logger LOG = Logger.getLogger(MissedPunchServiceImpl.class);

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
	public List<MissedPunchDocument> getMissedPunchDocumentsByTimesheetDocumentId(String timesheetDocumentId) {
		List<MissedPunchDocument> missedPunchDocuments = new ArrayList<MissedPunchDocument>();
		
		List<MissedPunch> missedPunches = getMissedPunchDao().getMissedPunchesByTimesheetDocumentId(timesheetDocumentId);
		for (MissedPunch missedPunch : missedPunches) {
			MissedPunchDocument missedPunchDocument = getMissedPunchDao().getMissedPunchDocument(missedPunch.getTkMissedPunchId());
			
			if (missedPunchDocument != null) {
				missedPunchDocuments.add(missedPunchDocument);
			}
		}

		return missedPunchDocuments;
	}

    @Override
    public MissedPunch getMissedPunchByClockLogId(String clockLogId) {
        return getMissedPunchDao().getMissedPunchByClockLogId(clockLogId);
    }

    @Override
    public void addClockLog(MissedPunch missedPunch, String ipAddress) {
        TimesheetDocument timesheetDocument = getTimesheetService().getTimesheetDocument(missedPunch.getTimesheetDocumentId());
        AssignmentDescriptionKey assignmentDescriptionKey = new AssignmentDescriptionKey(missedPunch.getJobNumber(), missedPunch.getWorkArea(), missedPunch.getTask());
        Assignment assignment = timesheetDocument.getAssignment(assignmentDescriptionKey);
        ClockLog lastClockLog = getClockLogService().getLastClockLog(missedPunch.getPrincipalId());
        Long zoneOffset = getTimezoneService().getTimezoneOffsetFromServerTime(DateTimeZone.forID(lastClockLog.getClockTimestampTimezone()));
        DateTime clockLogDateTime = new DateTime(missedPunch.getActionFullDateTime().getMillis() - zoneOffset);

        ClockLog clockLog = getClockLogService().buildClockLog(clockLogDateTime, new Timestamp(clockLogDateTime.getMillis()), assignment, timesheetDocument, 
        		missedPunch.getClockAction(), ipAddress);

        getClockLogService().saveClockLog(clockLog);
        missedPunch.setTkClockLogId(clockLog.getTkClockLogId());

        if (StringUtils.equals(clockLog.getClockAction(), TkConstants.CLOCK_OUT) ||
                StringUtils.equals(clockLog.getClockAction(), TkConstants.LUNCH_OUT)) {
            String earnCode = assignment.getJob().getPayTypeObj().getRegEarnCode();
            buildTimeBlockRunRules(lastClockLog, clockLog, timesheetDocument, assignment, earnCode, lastClockLog.getClockDateTime(), clockLog.getClockDateTime());
        }
    }

    @Override
    public void updateClockLog(MissedPunch missedPunch, String ipAddress) {
        DateTime actionDateTime = missedPunch.getActionFullDateTime();

        ClockLog clockLog = getClockLogService().getClockLog(missedPunch.getTkClockLogId());
        if (clockLog != null && !clockLog.getClockDateTime().equals(actionDateTime)){
        	String clockLogEndId = null;
        	String clockLogBeginId = null;
        	
        	List<TimeBlock> timeBlocks = getTimeBlockService().getTimeBlocksForClockLogEndId(clockLog.getTkClockLogId());
        	if (timeBlocks.isEmpty()) {
        		timeBlocks = getTimeBlockService().getTimeBlocksForClockLogBeginId(clockLog.getTkClockLogId());
        		if (!timeBlocks.isEmpty()) {
        			clockLogEndId = timeBlocks.get(0).getClockLogEndId();
        		}
        	} else {
        		clockLogBeginId = timeBlocks.get(0).getClockLogBeginId();
        	}
        	
        	deleteClockLogAndTimeBlocks(clockLog, timeBlocks);
        	
        	addClockLogAndTimeBlocks(missedPunch, ipAddress, clockLogEndId, clockLogBeginId);
        }
    }
    
    private void deleteClockLogAndTimeBlocks(ClockLog clockLog, List<TimeBlock> timeBlocks) {
    	getBusinessObjectService().delete(clockLog);

    	for (TimeBlock timeBlock : timeBlocks) {
    		getTimeBlockService().deleteTimeBlock(timeBlock);
    	}
    }

    private void addClockLogAndTimeBlocks(MissedPunch missedPunch, String ipAddress, String logEndId, String logBeginId) {
        TimesheetDocument timesheetDocument = getTimesheetService().getTimesheetDocument(missedPunch.getTimesheetDocumentId());
        AssignmentDescriptionKey assignmentDescriptionKey = new AssignmentDescriptionKey(missedPunch.getJobNumber(), missedPunch.getWorkArea(), missedPunch.getTask());
        Assignment assignment = timesheetDocument.getAssignment(assignmentDescriptionKey);
        ClockLog lastLog = getClockLogService().getLastClockLog(missedPunch.getPrincipalId());
        Long zoneOffset = getTimezoneService().getTimezoneOffsetFromServerTime(DateTimeZone.forID(lastLog.getClockTimestampTimezone()));
        DateTime clockLogDateTime = new DateTime(missedPunch.getActionFullDateTime().getMillis() - zoneOffset);

        ClockLog clockLog = getClockLogService().buildClockLog(clockLogDateTime, new Timestamp(clockLogDateTime.getMillis()), assignment, timesheetDocument, 
        		missedPunch.getClockAction(), ipAddress);
        
        getClockLogService().saveClockLog(clockLog);
        missedPunch.setTkClockLogId(clockLog.getTkClockLogId());
        
        if (logEndId != null || logBeginId != null) {
	        ClockLog endLog = null;
	        ClockLog beginLog = null;
	        
	        if (logEndId != null) {
	        	endLog = getClockLogService().getClockLog(logEndId);
	        } else {
	        	endLog = clockLog; 
	        }
	       
	        if (logBeginId != null) {
	        	beginLog = getClockLogService().getClockLog(logBeginId);
	        } else {
	        	beginLog = clockLog;
	        }
	        
	        if (beginLog != null && endLog != null && beginLog.getClockTimestamp().before(endLog.getClockTimestamp())) {
	        	String earnCode = assignment.getJob().getPayTypeObj().getRegEarnCode();
	        	buildTimeBlockRunRules(beginLog, endLog, timesheetDocument, assignment, earnCode, beginLog.getClockDateTime(), endLog.getClockDateTime());
	        }
        }
    }
    
    @Override
    public void approveMissedPunchDocument(MissedPunchDocument missedPunchDocument) {
    	String batchUserPrincipalId = getBatchUserPrincipalId();
        
        if (batchUserPrincipalId != null) {
        	String documentNumber = missedPunchDocument.getDocumentNumber();
	        WorkflowDocument wd = WorkflowDocumentFactory.loadDocument(batchUserPrincipalId, documentNumber);
	        wd.superUserBlanketApprove("Batch job superuser approving missed punch document.");
        } else {
        	String principalName = ConfigContext.getCurrentContextConfig().getProperty(TkConstants.BATCH_USER_PRINCIPAL_NAME);
        	LOG.error("Could not approve missed punch document due to missing batch user " + principalName);
        }
    }

    /**
     * Helper method to build time blocks and fire the rules processing. This
     * should be called only if there was a CLOCK_OUT action.
     */
    private void buildTimeBlockRunRules(ClockLog beginClockLog, ClockLog endClockLog, TimesheetDocument tdoc, Assignment assignment, String earnCode, DateTime beginDateTime, DateTime endDateTime) {
        // New Time Blocks, pointer reference
        List<TimeBlock> newTimeBlocks = tdoc.getTimeBlocks();
        List<TimeBlock> referenceTimeBlocks = new ArrayList<TimeBlock>(newTimeBlocks);
        for (TimeBlock tb : newTimeBlocks) {
            referenceTimeBlocks.add(tb.copy());
        }

        // Add TimeBlocks after we store our reference object!
        List<TimeBlock> blocks = getTimeBlockService().buildTimeBlocks(
                assignment, earnCode, tdoc, beginDateTime,
                endDateTime, BigDecimal.ZERO, BigDecimal.ZERO, true, false, HrContext.getPrincipalId());


        // Add the clock log IDs to the time blocks that were just created.
        for (TimeBlock block : blocks) {
            block.setClockLogBeginId(beginClockLog.getTkClockLogId());
            block.setClockLogEndId(endClockLog.getTkClockLogId());
        }

        newTimeBlocks.addAll(blocks);

        //reset time block
        getTimesheetService().resetTimeBlock(newTimeBlocks);
        //apply any rules for this action
        getTkRuleControllerService().applyRules(
                TkConstants.ACTIONS.CLOCK_OUT, newTimeBlocks,
                tdoc.getCalendarEntry(),
                tdoc, tdoc.getPrincipalId()
        );

        getTimeBlockService().saveTimeBlocks(referenceTimeBlocks, newTimeBlocks, HrContext.getPrincipalId());
    }
    
    private String getBatchUserPrincipalId() {
    	String principalName = ConfigContext.getCurrentContextConfig().getProperty(TkConstants.BATCH_USER_PRINCIPAL_NAME);
        Principal principal = getIdentityService().getPrincipalByPrincipalName(principalName);
        return principal == null ? null : principal.getPrincipalId();
    }
    
    public MissedPunchDao getMissedPunchDao() {
    	return missedPunchDao;
    }
    
    public void setMissedPunchDao(MissedPunchDao missedPunchDao) {
        this.missedPunchDao = missedPunchDao;
    }

	public AssignmentService getAssignmentService() {
		return assignmentService;
	}

	public void setAssignmentService(AssignmentService assignmentService) {
		this.assignmentService = assignmentService;
	}

	public BusinessObjectService getBusinessObjectService() {
		return businessObjectService;
	}

	public void setBusinessObjectService(BusinessObjectService businessObjectService) {
		this.businessObjectService = businessObjectService;
	}

	public ClockLogService getClockLogService() {
		return clockLogService;
	}

	public void setClockLogService(ClockLogService clockLogService) {
		this.clockLogService = clockLogService;
	}

	public DocumentService getDocumentService() {
		return documentService;
	}

	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}

	public IdentityService getIdentityService() {
		return identityService;
	}

	public void setIdentityService(IdentityService identityService) {
		this.identityService = identityService;
	}

	public TimeBlockService getTimeBlockService() {
		return timeBlockService;
	}

	public void setTimeBlockService(TimeBlockService timeBlockService) {
		this.timeBlockService = timeBlockService;
	}

	public TimesheetService getTimesheetService() {
		return timesheetService;
	}

	public void setTimesheetService(TimesheetService timesheetService) {
		this.timesheetService = timesheetService;
	}

	public TimezoneService getTimezoneService() {
		return timezoneService;
	}

	public void setTimezoneService(TimezoneService timezoneService) {
		this.timezoneService = timezoneService;
	}

	public TkRuleControllerService getTkRuleControllerService() {
		return tkRuleControllerService;
	}

	public void setTkRuleControllerService(TkRuleControllerService tkRuleControllerService) {
		this.tkRuleControllerService = tkRuleControllerService;
	}

}