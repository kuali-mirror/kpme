/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.tklm.time.missedpunch.validation;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.assignment.Assignment;
import org.kuali.kpme.core.api.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.api.earncode.EarnCodeContract;
import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.api.common.TkConstants;
import org.kuali.kpme.tklm.api.time.clocklog.ClockLog;
import org.kuali.kpme.tklm.api.time.missedpunch.MissedPunch;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.time.missedpunch.MissedPunchBo;
import org.kuali.kpme.tklm.time.missedpunch.MissedPunchDocument;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.kpme.tklm.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.rules.TransactionalDocumentRuleBase;
import org.kuali.rice.krad.util.GlobalVariables;

import java.util.List;

public class MissedPunchDocumentRule extends TransactionalDocumentRuleBase {
	
	@Override
	protected boolean processCustomSaveDocumentBusinessRules(Document document) {
        boolean valid = true;
        
        MissedPunchDocument missedPunchDocument = (MissedPunchDocument) document;
        MissedPunchBo missedPunch = missedPunchDocument.getMissedPunch();
        DocumentStatus documentStatus = KewApiServiceLocator.getWorkflowDocumentService().getDocumentStatus(document.getDocumentNumber());
        
        if (DocumentStatus.INITIATED.equals(documentStatus) || DocumentStatus.SAVED.equals(documentStatus)) {
        	valid = validateTimesheet(missedPunch);
        	valid &= validateClockAction(missedPunch);
        	valid &= validateClockTime(missedPunch);
        	valid &= validateOverLappingTimeBlocks(missedPunch, missedPunch.getTimesheetDocumentId());
            valid &= validateTimeSheetInitiated(missedPunch);
        }
	    
        return valid;
	}
	
	/**
	 * Validates whether the Timesheet associated with the Missed Punch is not ENROUTE or FINAL.
	 * 
	 * @param missedPunch The Missed Punch to check
	 * 
	 * @return true if the Timesheet associated with the Missed Punch is not ENROUTE or FINAL, false otherwise
	 */
    protected boolean validateTimesheet(MissedPunchBo missedPunch) {
    	boolean valid = true;
    	
    	if (missedPunch.getTimesheetDocumentId() != null) {
	    	DocumentStatus documentStatus = KewApiServiceLocator.getWorkflowDocumentService().getDocumentStatus(missedPunch.getTimesheetDocumentId());
	    	
	    	if (DocumentStatus.ENROUTE.equals(documentStatus) || DocumentStatus.FINAL.equals(documentStatus)) {
	    		GlobalVariables.getMessageMap().putError("document.timesheetDocumentId", "clock.mp.invalid.timesheet");
	    		valid = false;
	    	}
    	}
    	
    	return valid;
    }

    /**
     * Validates whether the Clock Action associated with the Missed Punch correctly transitions from the applicable last Clock Logs.
     *
     * @param missedPunch The Missed Punch to check
     *
     * @return true if the Clock Action associated with the Missed Punch correctly transitions from the applicable last Clock Logs, false otherwise
     */
	boolean validateClockAction(MissedPunchBo missedPunch) {
        boolean valid = true;
        
        if (TkConstants.CLOCK_OUT.equals(missedPunch.getClockAction()) || TkConstants.LUNCH_OUT.equals(missedPunch.getClockAction())) {
            ClockLog lastClockIn = TkServiceLocator.getClockLogService().getLastClockLog(missedPunch.getPrincipalId(),TkConstants.CLOCK_IN);
            ClockLog lastLunchIn = TkServiceLocator.getClockLogService().getLastClockLog(missedPunch.getPrincipalId(),TkConstants.LUNCH_IN);
            
            if (lastClockIn != null) {
                MissedPunch lastMissedPunch = TkServiceLocator.getMissedPunchService().getMissedPunchByClockLogId(lastClockIn.getTkClockLogId());
                if (lastMissedPunch != null) {
                    GlobalVariables.getMessageMap().putError("document.clockAction", "clock.mp.onlyOne.action");
                    valid = false;
                }
            } else if (lastLunchIn != null) {
            	MissedPunch lastMissedPunch = TkServiceLocator.getMissedPunchService().getMissedPunchByClockLogId(lastLunchIn.getTkClockLogId());
                if (lastMissedPunch != null) {
                    GlobalVariables.getMessageMap().putError("document.clockAction", "clock.mp.onlyOne.action");
                    valid = false;
                }
            }
        }
        
        return valid;
    }

    /**
     * Validates whether the MissedPunch has a valid time relative to the last Clock Log.
     *
     * @param missedPunch The Missed Punch to check
     *
     * @return true if the MissedPunch has a valid time relative to the last Clock Log, false otherwise
     */
    protected boolean validateClockTime(MissedPunchBo missedPunch) {
        boolean valid = true;

        if (missedPunch.getActionFullDateTime() != null) {
        	// convert the action time to system time zone since we will be comparing it with system time
	        String dateString = TKUtils.formatDateTimeShort(missedPunch.getActionFullDateTime());
	        String longDateString = TKUtils.formatDateTimeLong(missedPunch.getActionFullDateTime());
	        String timeString = TKUtils.formatTimeShort(longDateString);
	        		
	        DateTime dateTimeWithUserZone = TKUtils.convertDateStringToDateTime(dateString, timeString);
	        DateTime actionDateTime = dateTimeWithUserZone.withZone(TKUtils.getSystemDateTimeZone());

            //ClockLog lastClockIn = TkServiceLocator.getClockLogService().getLastClockLog(missedPunch.getPrincipalId(),TkConstants.CLOCK_IN);

            //Make sure user should be able to route!!
            String userPrincipalId = HrContext.getPrincipalId();
            if (!StringUtils.equals(userPrincipalId, missedPunch.getPrincipalId())) {
                Assignment assignment = HrServiceLocator.getAssignmentService().getAssignmentForTargetPrincipal(AssignmentDescriptionKey.get(missedPunch.getAssignmentKey()), actionDateTime.toLocalDate());

                if (assignment != null) {
                    Long workArea = assignment.getWorkArea();
                    String dept = assignment.getJob().getDept();

                    boolean isApproverOrReviewerForCurrentAssignment =
                            HrServiceLocator.getKPMERoleService().principalHasRoleInWorkArea(userPrincipalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), workArea, actionDateTime)
                                    || HrServiceLocator.getKPMERoleService().principalHasRoleInWorkArea(userPrincipalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER_DELEGATE.getRoleName(), workArea, actionDateTime)
                                    || HrServiceLocator.getKPMERoleService().principalHasRoleInWorkArea(userPrincipalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.REVIEWER.getRoleName(), workArea, actionDateTime)
                                    || HrServiceLocator.getKPMERoleService().principalHasRoleInDepartment(userPrincipalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.PAYROLL_PROCESSOR.getRoleName(), dept, actionDateTime)
                                    || HrServiceLocator.getKPMERoleService().principalHasRoleInDepartment(userPrincipalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.PAYROLL_PROCESSOR_DELEGATE.getRoleName(), dept, actionDateTime);
                    if (!isApproverOrReviewerForCurrentAssignment) {
                        GlobalVariables.getMessageMap().putError("document", "clock.mp.unauthorized", GlobalVariables.getUserSession().getPrincipalName(), missedPunch.getPrincipalName());
                        //if this fails, don't bother checking the other rules
                        return false;
                    }
                }
            }
	

            if (actionDateTime.toLocalDate().isAfter(LocalDate.now())) {
	        	GlobalVariables.getMessageMap().putError("document.actionDate", "clock.mp.future.date");
	        	valid = false;
	        }

	        if (actionDateTime.toLocalDate().isEqual(LocalDate.now()) && actionDateTime.isAfterNow()) {
	        	GlobalVariables.getMessageMap().putError("document.actionTime", "clock.mp.future.time");
	        	valid = false;
	        }
	        
	    	ClockLog lastClockLog = TkServiceLocator.getClockLogService().getLastClockLog(missedPunch.getPrincipalId());
	        if (lastClockLog != null) {
                if( (StringUtils.isNotEmpty(missedPunch.getTkClockLogId()) && !missedPunch.getTkClockLogId().equals(lastClockLog.getTkClockLogId()))
	        			|| StringUtils.isEmpty(missedPunch.getTkClockLogId()) ) {
	        			
		        	DateTime clockLogDateTime = lastClockLog.getClockDateTime();
			        DateTime boundaryMax = clockLogDateTime.plusDays(1);
                    DateTime boundaryMin = DateTime.now().minusDays(1);
			        if ((!StringUtils.equals(lastClockLog.getClockAction(), TkConstants.CLOCK_OUT) && actionDateTime.isAfter(boundaryMax)) 
			        		|| actionDateTime.isBefore(clockLogDateTime)) {
			        	GlobalVariables.getMessageMap().putError("document.actionTime", "clock.mp.invalid.datetime");
			            valid = false;
			        }
                    if (StringUtils.equals(lastClockLog.getClockAction(), TkConstants.CLOCK_OUT)
                            && actionDateTime.isBefore(boundaryMin)) {
                        GlobalVariables.getMessageMap().putError("document.actionTime", "clock.mp.past24hour.date");
                        valid = false;
                    }
	        	}
	        }
        }

        return valid;
    }
    
    /**
     * 
     * @param missedPunch
     * @return
     */
    boolean validateOverLappingTimeBlocks(MissedPunchBo missedPunch, String documentId) {
        boolean valid = true;
        if (missedPunch.getActionFullDateTime() != null) {
	       // convert the action time to system time zone since we will be comparing it with system time
	        String dateString = TKUtils.formatDateTimeShort(missedPunch.getActionFullDateTime());
	        String longDateString = TKUtils.formatDateTimeLong(missedPunch.getActionFullDateTime());
	        String timeString = TKUtils.formatTimeShort(longDateString);
	        		
	        DateTime dateTimeWithUserZone = TKUtils.convertDateStringToDateTime(dateString, timeString);
	        DateTime actionDateTime = dateTimeWithUserZone.withZone(TKUtils.getSystemDateTimeZone());
	        
	        TimesheetDocument timesheetDocument = null;
	        if (StringUtils.isNotBlank(documentId)) {
	            timesheetDocument = TkServiceLocator.getTimesheetService().getTimesheetDocument(documentId);
		        List<TimeBlock> tbList = timesheetDocument.getTimeBlocks();
		        for(TimeBlock tb : tbList) {
		        	String earnCode = tb.getEarnCode();
		        	EarnCodeContract earnCodeObj = HrServiceLocator.getEarnCodeService().getEarnCode(earnCode, timesheetDocument.getAsOfDate());
		        	if(earnCodeObj != null && HrConstants.EARN_CODE_TIME.equals(earnCodeObj.getEarnCodeType())) {
		        		Interval clockInterval = new Interval(tb.getBeginDateTime(), tb.getEndDateTime());
		           	 	if(clockInterval.contains(actionDateTime.getMillis())) {
		           	 		GlobalVariables.getMessageMap().putError("document", "clock.mp.already.logged.time");
		           	 		return false;
		           	 	}
		        	}
		        }
	        }
        }
        return valid;
    }

    boolean validateTimeSheetInitiated(MissedPunchBo missedPunch) {
        // use the actual date and time from the document to build the date time with user zone, then apply system time zone to it
        String dateString = TKUtils.formatDateTimeShort(missedPunch.getActionFullDateTime());
        String longDateString = TKUtils.formatDateTimeLong(missedPunch.getActionFullDateTime());
        String timeString = TKUtils.formatTimeShort(longDateString);

        DateTime dateTimeWithUserZone = TKUtils.convertDateStringToDateTime(dateString, timeString);
        DateTime actionDateTime = dateTimeWithUserZone.withZone(TKUtils.getSystemDateTimeZone());

        String clockAction = missedPunch.getClockAction();
        String principalId = missedPunch.getPrincipalId();
        ClockLog previousClockLog = TkServiceLocator.getClockLogService().getLastClockLog(principalId);
        if (StringUtils.equals(clockAction, TkConstants.CLOCK_OUT) || StringUtils.equals(clockAction, TkConstants.LUNCH_OUT)) {
            TimesheetDocument previousTimeDoc = TkServiceLocator.getTimesheetService().getTimesheetDocument(previousClockLog.getDocumentId());
            if(previousTimeDoc != null) {
                CalendarEntry previousCalEntry = previousTimeDoc.getCalendarEntry();
                DateTime previousEndPeriodDateTimeWithUserZone = previousCalEntry.getEndPeriodFullDateTime().withZoneRetainFields(DateTimeZone.forID(HrServiceLocator.getTimezoneService().getUserTimezone(principalId)));
                // if current time is after the end time of previous calendar entry, it means the clock action covers two calendar entries
                if(actionDateTime.isAfter(previousEndPeriodDateTimeWithUserZone.getMillis())) {
                    GlobalVariables.getMessageMap().putError("document.timesheetDocumentId", "clock.mp.cross.calendar");
                }
            }

    }
        return true;
    }
}
