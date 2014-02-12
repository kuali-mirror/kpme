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
package org.kuali.kpme.tklm.time.clock.web;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.kuali.kpme.core.KPMENamespace;
import org.kuali.kpme.core.api.assignment.AssignmentContract;
import org.kuali.kpme.core.api.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntryContract;
import org.kuali.kpme.core.api.earncode.EarnCodeContract;
import org.kuali.kpme.core.api.workarea.WorkAreaContract;
import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.document.calendar.CalendarDocument;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.common.LMConstants;
import org.kuali.kpme.tklm.common.TkConstants;
import org.kuali.kpme.tklm.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.time.clocklog.ClockLog;
import org.kuali.kpme.tklm.time.rules.lunch.department.DeptLunchRule;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.kpme.tklm.time.timesheet.web.TimesheetAction;
import org.kuali.kpme.tklm.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.krad.exception.AuthorizationException;
import org.kuali.rice.krad.util.GlobalVariables;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class ClockAction extends TimesheetAction {

    public static final SimpleDateFormat SDF = new SimpleDateFormat("EEE, MMMM d yyyy HH:mm:ss, zzzz");
    public static final String SEPERATOR = "[****]+";
    public static final String DOCUMENT_NOT_INITIATE_ERROR = "New Timesheet document could not be found. Please initiate the document first.";
    public static final String TIME_BLOCK_OVERLAP_ERROR = "User has already logged time for this clock period.";

    @Override
    protected void checkTKAuthorization(ActionForm form, String methodToCall) throws AuthorizationException {
        super.checkTKAuthorization(form, methodToCall); // Checks for read access first.

        ClockActionForm clockActionForm = (ClockActionForm) form;

        String principalId = GlobalVariables.getUserSession().getPrincipalId();
    	CalendarDocument timesheetDocument = TkServiceLocator.getTimesheetService().getTimesheetDocument(clockActionForm.getDocumentId());
        // Check for write access to Timeblock.
        if (StringUtils.equals(methodToCall, "clockAction") ||
                StringUtils.equals(methodToCall, "addTimeBlock") ||
                StringUtils.equals(methodToCall, "editTimeBlock") ||
                StringUtils.equals(methodToCall, "distributeTimeBlocks") ||
                StringUtils.equals(methodToCall, "saveNewTimeBlocks") ||
                StringUtils.equals(methodToCall, "deleteTimeBlock")) {
            if (!HrServiceLocator.getHRPermissionService().canEditCalendarDocument(principalId, timesheetDocument)) {
                throw new AuthorizationException(GlobalVariables.getUserSession().getPrincipalId(), "ClockAction", "");
            }
        }
    }


    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionForward actionForward = super.execute(mapping, form, request, response);
        
        ClockActionForm clockActionForm = (ClockActionForm) form;
        
        TimesheetDocument timesheetDocument = clockActionForm.getTimesheetDocument();
        clockActionForm.setShowClockButton(true);
        if (timesheetDocument != null) {
	        if (!timesheetDocument.getDocumentHeader().getDocumentStatus().equals(HrConstants.ROUTE_STATUS.ENROUTE)
	                && !timesheetDocument.getDocumentHeader().getDocumentStatus().equals(HrConstants.ROUTE_STATUS.FINAL)) {
        	
		        String targetPrincipalId = HrContext.getTargetPrincipalId();
		        if (targetPrincipalId != null) {
		            clockActionForm.setPrincipalId(targetPrincipalId);
		        }
		        clockActionForm.setAssignmentDescriptions(timesheetDocument.getAssignmentDescriptions(true));
		        
		        if (clockActionForm.getEditTimeBlockId() != null) {
		            clockActionForm.setCurrentTimeBlock(TkServiceLocator.getTimeBlockService().getTimeBlock(clockActionForm.getEditTimeBlockId()));
		        }
		        
		        ClockLog lastClockLog = TkServiceLocator.getClockLogService().getLastClockLog(targetPrincipalId);
		        if (lastClockLog != null) {
		            DateTime lastClockDateTime = lastClockLog.getClockDateTime();
		            String lastClockZone = lastClockLog.getClockTimestampTimezone();
		            if (StringUtils.isEmpty(lastClockZone)) {
		                lastClockZone = TKUtils.getSystemTimeZone();
		            }
		            // zone will not be null. At this point is Valid or Exception.
		            // Exception would indicate bad data stored in the system. We can wrap this, but
		            // for now, the thrown exception is probably more valuable.
		            DateTimeZone zone = DateTimeZone.forID(lastClockZone);
		            DateTime clockWithZone = lastClockDateTime.withZone(zone);
		            clockActionForm.setLastClockTimeWithZone(clockWithZone.toDate());
		            clockActionForm.setLastClockTimestamp(lastClockDateTime.toDate());
		            clockActionForm.setLastClockAction(lastClockLog.getClockAction());
		        }
		        
		        if (lastClockLog == null || StringUtils.equals(lastClockLog.getClockAction(), TkConstants.CLOCK_OUT)) {
		            clockActionForm.setCurrentClockAction(TkConstants.CLOCK_IN);
		        } else {
		            if (StringUtils.equals(lastClockLog.getClockAction(), TkConstants.LUNCH_OUT) && TkServiceLocator.getSystemLunchRuleService().isShowLunchButton()) {
		                clockActionForm.setCurrentClockAction(TkConstants.LUNCH_IN);
		                clockActionForm.setShowClockButton(false);
		            } else {
		                clockActionForm.setCurrentClockAction(TkConstants.CLOCK_OUT);
		            }
		            // if the current clock action is clock out, displays only the clocked-in assignment
		            String selectedAssignment = new AssignmentDescriptionKey(lastClockLog.getJobNumber(), lastClockLog.getWorkArea(), lastClockLog.getTask()).toAssignmentKeyString();
		            clockActionForm.setSelectedAssignment(selectedAssignment);
		            Assignment assignment = timesheetDocument.getAssignment(AssignmentDescriptionKey.get(selectedAssignment));
		            Map<String, String> assignmentDesc = HrServiceLocator.getAssignmentService().getAssignmentDescriptions(assignment);
		            clockActionForm.setAssignmentDescriptions(assignmentDesc);
		        }
		        
		        // KPME-2772 This issue happens when target employee is clocked out and there are multiple assignments 
		        // because when there are more than one assignment, it uses "default" assignment on the clock action form,
		        // which never gets set above.  When target employee is clocked in, there is always one assignment, so it works.
		        // The solution is to add else if statement and set clockButtonEnabled flag to true when target employee is clocked out.  
		        // Since all the assignments for target employee are already filtered by the time it gets here (i.e, only showing the ones
		        // that approver has permission to view for), we will just enable buttons.  When target employee is clocked in, it gets
		        // handled in else statement
		        if (StringUtils.equals(GlobalVariables.getUserSession().getPrincipalId(), HrContext.getTargetPrincipalId())) {
		        	clockActionForm.setClockButtonEnabled(true);
		        } else {
		        	boolean isApproverOrReviewerForCurrentAssignment = false;
		        	String selectedAssignment = StringUtils.EMPTY;
		        	if (clockActionForm.getAssignmentDescriptions() != null) {
		        		if (clockActionForm.getAssignmentDescriptions().size() == 1) {
		        			for (String assignment : clockActionForm.getAssignmentDescriptions().keySet()) {
		        				selectedAssignment = assignment;
		        			}
		        		} else {
		        			selectedAssignment = clockActionForm.getSelectedAssignment();
		        		}
		        	}
		        	
		        	if(StringUtils.isNotBlank(selectedAssignment)) {
		        		Assignment assignment = (Assignment) HrServiceLocator.getAssignmentService().getAssignmentForTargetPrincipal(AssignmentDescriptionKey.get(selectedAssignment), LocalDate.now());
		        		if (assignment != null) {
		        			Long workArea = assignment.getWorkArea();
                            String dept = assignment.getJob().getDept();
		        			String principalId = HrContext.getPrincipalId();
		        			DateTime startOfToday = LocalDate.now().toDateTimeAtStartOfDay();
                            isApproverOrReviewerForCurrentAssignment =
                                    HrServiceLocator.getKPMERoleService().principalHasRoleInWorkArea(principalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), workArea, startOfToday)
		        					|| HrServiceLocator.getKPMERoleService().principalHasRoleInWorkArea(principalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER_DELEGATE.getRoleName(), workArea, startOfToday)
		        					|| HrServiceLocator.getKPMERoleService().principalHasRoleInWorkArea(principalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.REVIEWER.getRoleName(), workArea, startOfToday)
                                    || HrServiceLocator.getKPMERoleService().principalHasRoleInDepartment(principalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.PAYROLL_PROCESSOR.getRoleName(), dept, startOfToday)
                                    || HrServiceLocator.getKPMERoleService().principalHasRoleInDepartment(principalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.PAYROLL_PROCESSOR_DELEGATE.getRoleName(), dept, startOfToday);
		        		}
		        	} else {
                        if (CollectionUtils.isNotEmpty(clockActionForm.getAssignmentDescriptions().entrySet())) {
                            //only assignments that target user and logged in user have access to should be in this list
                            isApproverOrReviewerForCurrentAssignment = true;
                        }

                    }
		        	clockActionForm.setClockButtonEnabled(isApproverOrReviewerForCurrentAssignment);
		        }
		        
		        clockActionForm.setShowLunchButton(TkServiceLocator.getSystemLunchRuleService().isShowLunchButton());
		        assignShowDistributeButton(clockActionForm);
		        
		        if (clockActionForm.isShowLunchButton()) {
		            // We don't need to worry about the assignments and lunch rules
		            // if the global lunch rule is turned off.
		
		            // Check for presence of department lunch rule.
		            Map<String, Boolean> assignmentDeptLunchRuleMap = new HashMap<String, Boolean>();
		            for (Assignment a : timesheetDocument.getAssignments()) {
	                    String key = AssignmentDescriptionKey.getAssignmentKeyString(a);
	                    DeptLunchRule deptLunchRule = TkServiceLocator.getDepartmentLunchRuleService().getDepartmentLunchRule(a.getDept(), a.getWorkArea(), clockActionForm.getPrincipalId(), a.getJobNumber(), LocalDate.now());
	                    assignmentDeptLunchRuleMap.put(key, deptLunchRule != null);
	                }
		            clockActionForm.setAssignmentLunchMap(assignmentDeptLunchRuleMap);
		        }
	        } else {
	        	clockActionForm.setErrorMessage("Your current timesheet is already submitted for Approval. Clock action is not allowed on this timesheet.");
	        }
        }
        
        return actionForward;
    }
    
    public void assignShowDistributeButton(ClockActionForm caf) {
    	caf.setShowDistrubuteButton(false);
    	
    	TimesheetDocument timesheetDocument = caf.getTimesheetDocument();
        if (timesheetDocument != null) {
            int eligibleAssignmentCount = 0;
            for (Assignment a : timesheetDocument.getAssignments()) {
                WorkAreaContract aWorkArea = HrServiceLocator.getWorkAreaService().getWorkArea(a.getWorkArea(), timesheetDocument.getDocEndDate());
                if(aWorkArea != null && aWorkArea.isHrsDistributionF()) {
                    eligibleAssignmentCount++;
                }

                // Only show the distribute button if there is more than one eligible assignment
                if (eligibleAssignmentCount > 1) {
                    caf.setShowDistrubuteButton(true);
                    break;
                }
            }
        }
    }
    
    public ActionForward clockAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ClockActionForm caf = (ClockActionForm) form;
        DateTime currentDateTime = new DateTime();
        // TODO: Validate that clock action is valid for this user
        // TODO: this needs to be integrated with the error tag
        if (StringUtils.isBlank(caf.getSelectedAssignment())) {
            caf.setErrorMessage("No assignment selected.");
            return mapping.findForward("basic");
        }
        String pId = HrContext.getTargetPrincipalId();
        ClockLog previousClockLog = TkServiceLocator.getClockLogService().getLastClockLog(pId);
        if(previousClockLog != null && StringUtils.equals(caf.getCurrentClockAction(), previousClockLog.getClockAction())){
        	caf.setErrorMessage("The operation is already performed.");
            return mapping.findForward("basic");
        }
        String ip = TKUtils.getIPAddressFromRequest(request);
        
        Assignment assignment = caf.getTimesheetDocument().getAssignment(AssignmentDescriptionKey.get(caf.getSelectedAssignment()));
        
        // check if User takes action from Valid location.
        String allowActionFromInvalidLocaiton = ConfigContext.getCurrentContextConfig().getProperty(LMConstants.ALLOW_CLOCKINGEMPLOYYE_FROM_INVALIDLOCATION);
        if(StringUtils.equals(allowActionFromInvalidLocaiton, "false")) {
	        boolean isInValid = TkServiceLocator.getClockLocationRuleService().isInValidIPClockLocation(assignment.getDept(), assignment.getWorkArea(), assignment.getPrincipalId(), assignment.getJobNumber(), ip, currentDateTime.toLocalDate());
	        if(isInValid){
	        	caf.setErrorMessage("Could not take the action as Action taken from  "+ ip + ",  is not a valid IP address.");
	            return mapping.findForward("basic");
	        }
        }
        
        List<? extends AssignmentContract> lstAssingmentAsOfToday = HrServiceLocator.getAssignmentService().getAssignments(pId, LocalDate.now());
        boolean foundValidAssignment = false;
        for(AssignmentContract assign : lstAssingmentAsOfToday){
        	if((assign.getJobNumber().compareTo(assignment.getJobNumber()) ==0) &&
        		(assign.getWorkArea().compareTo(assignment.getWorkArea()) == 0) &&
        		(assign.getTask().compareTo(assignment.getTask()) == 0)){
        		foundValidAssignment = true;
        		break;
        	}
        }
        
        if(!foundValidAssignment){
        	caf.setErrorMessage("Assignment is not effective as of today");
        	return mapping.findForward("basic");
        }
        
        LocalDate beginDate = LocalDate.now();
   	 	DateTime clockBeginDateTime = new DateTime(beginDate.toDateTimeAtCurrentTime());
        // validate if there's any overlapping with existing time blocks
        if (StringUtils.equals(caf.getCurrentClockAction(), TkConstants.CLOCK_IN) || StringUtils.equals(caf.getCurrentClockAction(), TkConstants.LUNCH_IN)) {
        	 List<TimeBlock> tbList = caf.getTimesheetDocument().getTimeBlocks();
	         for(TimeBlock tb : tbList) {
	        	 String earnCode = tb.getEarnCode();
	        	 boolean isRegularEarnCode = StringUtils.equals(assignment.getJob().getPayTypeObj().getRegEarnCode(), earnCode);
	        	 EarnCodeContract earnCodeObj = HrServiceLocator.getEarnCodeService().getEarnCode(earnCode, caf.getTimesheetDocument().getAsOfDate());
	        	 if(earnCodeObj != null && HrConstants.EARN_CODE_TIME.equals(earnCodeObj.getEarnCodeType())) {
	        		 Interval clockInterval = new Interval(new DateTime(tb.getBeginTimestamp().getTime()), new DateTime(tb.getEndTimestamp().getTime()));
	        		 if(isRegularEarnCode && clockInterval.contains(clockBeginDateTime.getMillis())) {
	        			 caf.setErrorMessage(TIME_BLOCK_OVERLAP_ERROR);
	        			 return mapping.findForward("basic");
	        		 }
	        	 }
	         }
        }
        
        
        // for clock out and lunch out actions, check if the current time and last clock log time is on two different calendar entries,
        // if they are, we need to clock out the employee at the endDatTime (in employee's time zone) of the last calendar entry,
        // and clock employee back in at the beginDateTime (in employee's time zone) of the new calendar entry
        // then clock him out again at current time. 
        if (StringUtils.equals(caf.getCurrentClockAction(), TkConstants.CLOCK_OUT) || StringUtils.equals(caf.getCurrentClockAction(), TkConstants.LUNCH_OUT)) {
            ClockLog lastLog = null;
            String inAction = "";
            String outAction = "";
          	if (StringUtils.equals(caf.getCurrentClockAction(), TkConstants.LUNCH_OUT)) {
               lastLog = TkServiceLocator.getClockLogService().getLastClockLog(pId, TkConstants.CLOCK_IN);
               inAction = TkConstants.LUNCH_IN;
               outAction = TkConstants.LUNCH_OUT;
            } else if (StringUtils.equals(caf.getCurrentClockAction(), TkConstants.CLOCK_OUT)) {
               lastLog = TkServiceLocator.getClockLogService().getLastClockLog(pId);
               inAction = TkConstants.CLOCK_IN;
               outAction = TkConstants.CLOCK_OUT;
            }     
        	
        	TimesheetDocument previousTimeDoc = TkServiceLocator.getTimesheetService().getTimesheetDocument(previousClockLog.getDocumentId());
        	if(previousTimeDoc != null) {
	        	CalendarEntry previousCalEntry = previousTimeDoc.getCalendarEntry();
	        	DateTime previousEndPeriodDateTime = previousCalEntry.getEndPeriodFullDateTime();
	        	// if current time is after the end time of previous calendar entry, it means the clock action covers two calendar entries
	        	if(currentDateTime.isAfter(previousEndPeriodDateTime.getMillis())) {
	        		
	        		// create co, ci and co clock logs and assign the last co clock log to the form
	        		// use the user's time zone and the system time zone to figure out the system time of endPeriodDatTime in the user's timezone
	                DateTimeZone userTimezone = DateTimeZone.forID(HrServiceLocator.getTimezoneService().getUserTimezone(pId));
	        		DateTimeZone systemTimeZone = TKUtils.getSystemDateTimeZone();
	        		// time to use to create the out clock log
	                DateTime outLogDateTime = TKUtils.convertTimeForDifferentTimeZone(previousEndPeriodDateTime, systemTimeZone, userTimezone);
	        	        
	                CalendarEntry nextCalendarEntry = (CalendarEntry)HrServiceLocator.getCalendarEntryService().getNextCalendarEntryByCalendarId(previousCalEntry.getHrCalendarId(), previousCalEntry);
	                DateTime beginNextPeriodDateTime = nextCalendarEntry.getBeginPeriodFullDateTime();
	                // time to use to create the CI clock log
	                DateTime inLogDateTime = TKUtils.convertTimeForDifferentTimeZone(beginNextPeriodDateTime, systemTimeZone, userTimezone);
	                
	                TimesheetDocumentHeader nextTdh = TkServiceLocator.getTimesheetDocumentHeaderService()
	                		.getDocumentHeader(pId, nextCalendarEntry.getBeginPeriodFullDateTime(), nextCalendarEntry.getEndPeriodFullDateTime());
	                if(nextTdh == null) {
	                	 caf.setErrorMessage(DOCUMENT_NOT_INITIATE_ERROR);
		            	 return mapping.findForward("basic");
	                }
	                TimesheetDocument nextTimeDoc = TkServiceLocator.getTimesheetService().getTimesheetDocument(nextTdh.getDocumentId());
	                if(nextTimeDoc == null) {
	                	 caf.setErrorMessage(DOCUMENT_NOT_INITIATE_ERROR);
		            	 return mapping.findForward("basic");
	                }
	        	    // validate if there's any overlapping with existing time blocks
	        		if (lastLog != null) {
	        			// validation with previous calendar entry
	        			// the datetime for the new clock log that's about to be created with grace period rule applied
	        			DateTime endDateTime = TkServiceLocator.getGracePeriodService().processGracePeriodRule(outLogDateTime, previousCalEntry.getBeginPeriodFullDateTime().toLocalDate());
	        			boolean validation = this.validateOverlapping(previousTimeDoc.getAsOfDate(), previousTimeDoc.getTimeBlocks(), lastLog.getClockDateTime(), endDateTime,assignment);
	        			if(!validation) {
	        				 caf.setErrorMessage(TIME_BLOCK_OVERLAP_ERROR);
   		            		 return mapping.findForward("basic");
	        			}
	        			
	        			// validation with the next calendar entry
		   	             // the datetime for the new clock log that's about to be created with grace period rule applied
	        			endDateTime = TkServiceLocator.getGracePeriodService().processGracePeriodRule(currentDateTime, nextCalendarEntry.getBeginPeriodFullDateTime().toLocalDate());
	        			validation = this.validateOverlapping(nextTimeDoc.getAsOfDate(), nextTimeDoc.getTimeBlocks(), inLogDateTime, endDateTime,assignment);
	        			if(!validation) {
	        				 caf.setErrorMessage(TIME_BLOCK_OVERLAP_ERROR);
  		            		 return mapping.findForward("basic");
	        			}
		            } 
	                
	                // clock out employee at the end of the previous pay period
	                ClockLog outLog = TkServiceLocator.getClockLogService().processClockLog(outLogDateTime, assignment, previousCalEntry, ip,
	                		previousEndPeriodDateTime.toLocalDate(), previousTimeDoc, outAction, true, pId);
	                
	                // clock in employee at the begin of the next pay period
	                ClockLog inLog = TkServiceLocator.getClockLogService().processClockLog(inLogDateTime, assignment, nextCalendarEntry, ip,
	                		beginNextPeriodDateTime.toLocalDate(), nextTimeDoc, inAction, true, pId);
	                
	                // finally clock out employee at current time
	                ClockLog finalOutLog = TkServiceLocator.getClockLogService().processClockLog(currentDateTime, assignment, nextCalendarEntry, ip,
	                		currentDateTime.toLocalDate(), nextTimeDoc, caf.getCurrentClockAction(), true, pId);
	                
	                // add 5 seconds to clock out log's timestamp so it will be found as the latest clock action
	                Timestamp ts= finalOutLog.getTimestamp();
	                java.util.Calendar cal = java.util.Calendar.getInstance();
	                cal.setTimeInMillis(ts.getTime());
	                cal.add(java.util.Calendar.SECOND, 5);
	                Timestamp later = new Timestamp(cal.getTime().getTime());
	                finalOutLog.setTimestamp(later);
	                TkServiceLocator.getClockLogService().saveClockLog(finalOutLog);
	                
	                caf.setClockLog(finalOutLog);  
	                return mapping.findForward("basic");
	                
	            } else {	// covers the scenario that user clocks out on the same calendar entry
	                if (lastLog != null) {
		   	            // the datetime for the new clock log that's about to be created with grace period rule applied
		   	         	DateTime endDateTime = TkServiceLocator.getGracePeriodService().processGracePeriodRule(new DateTime(), caf.getCalendarEntry().getBeginPeriodFullDateTime().toLocalDate());
		   	         	
		   	         	boolean validation = this.validateOverlapping(caf.getTimesheetDocument().getAsOfDate(), caf.getTimesheetDocument().getTimeBlocks(), lastLog.getClockDateTime(), endDateTime,assignment);
	        			if(!validation) {
	        				 caf.setErrorMessage(TIME_BLOCK_OVERLAP_ERROR);
			            		 return mapping.findForward("basic");
	        			}
	                } 
	        	}
    		}
    	} 
        // create clock log 
        ClockLog clockLog = TkServiceLocator.getClockLogService().processClockLog(new DateTime(), assignment, caf.getCalendarEntry(), ip,
        		beginDate, caf.getTimesheetDocument(), caf.getCurrentClockAction(), true, pId);

        caf.setClockLog(clockLog);  
        return mapping.findForward("basic"); 
        
    }

    public boolean validateOverlapping(LocalDate asOfDate, List<TimeBlock> tbList, DateTime beginDateTime, DateTime endDateTime, Assignment assignment) {
    	Interval clockInterval = new Interval(beginDateTime, endDateTime);
    	if(clockInterval != null) {
	    	for(TimeBlock tb : tbList) {
	        	 String earnCode = tb.getEarnCode();
	        	 boolean isRegularEarnCode = StringUtils.equals(assignment.getJob().getPayTypeObj().getRegEarnCode(),earnCode);
	        	 EarnCodeContract earnCodeObj = HrServiceLocator.getEarnCodeService().getEarnCode(earnCode, asOfDate);
	        	 if(isRegularEarnCode && earnCodeObj != null && HrConstants.EARN_CODE_TIME.equals(earnCodeObj.getEarnCodeType())) {
	            	 if(clockInterval.contains(tb.getBeginDateTime().getMillis()) || clockInterval.contains(tb.getEndDateTime().getMillis())) {
	            		 return false;
	            	 }
	        	 }
	    	}
	    	return true;
    	}
		return false;
    }
    
    
    public ActionForward distributeTimeBlocks(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ClockActionForm caf = (ClockActionForm) form;
        caf.findTimeBlocksToDistribute();
        return mapping.findForward("tb");
    }


    public ActionForward editTimeBlock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        ClockActionForm caf = (ClockActionForm) form;
        TimeBlock tb = caf.getCurrentTimeBlock();
        caf.setCurrentAssignmentKey(tb.getAssignmentKey());
        caf.populateAssignmentsForSelectedTimeBlock(tb);
        ActionForward forward = mapping.findForward("et");

        return new ActionForward(forward.getPath() + "?editTimeBlockId=" + tb.getTkTimeBlockId().toString());

    }
    public ActionForward addTimeBlock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        ClockActionForm caf = (ClockActionForm) form;
        TimeBlock currentTb = caf.getCurrentTimeBlock();
        List<TimeBlock> newTimeBlocks = caf.getTimesheetDocument().getTimeBlocks();
        List<TimeBlock> referenceTimeBlocks = new ArrayList<TimeBlock>(caf.getTimesheetDocument().getTimeBlocks().size());
        for (TimeBlock tb : caf.getTimesheetDocument().getTimeBlocks()) {
            referenceTimeBlocks.add(tb.copy());
        }
        //call persist method that only saves added/deleted/changed timeblocks
        TkServiceLocator.getTimeBlockService().saveTimeBlocks(referenceTimeBlocks, newTimeBlocks, HrContext.getPrincipalId());

        ActionForward forward = mapping.findForward("et");

        return new ActionForward(forward.getPath() + "?editTimeBlockId=" + currentTb.getTkTimeBlockId().toString());
    }
    
    public ActionForward saveNewTimeBlocks(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		ClockActionForm caf = (ClockActionForm)form;
		String tbId = caf.getTbId();
		String timesheetDocId = caf.getTsDocId();

		String[] assignments = caf.getNewAssignDesCol().split(SEPERATOR);
		String[] beginDates = caf.getNewBDCol().split(SEPERATOR);
		String[] beginTimes = caf.getNewBTCol().split(SEPERATOR);
		String[] endDates = caf.getNewEDCol().split(SEPERATOR);
		String[] endTimes = caf.getNewETCol().split(SEPERATOR);
		String[] hrs = caf.getNewHrsCol().split(SEPERATOR);
		String earnCode = TkServiceLocator.getTimeBlockService().getTimeBlock(tbId).getEarnCode();
		TimesheetDocument tsDoc = TkServiceLocator.getTimesheetService().getTimesheetDocument(timesheetDocId);
		List<TimeBlock> newTbList = new ArrayList<TimeBlock>();
		if(tsDoc != null) {
			for(TimeBlock oldTB : tsDoc.getTimeBlocks()) {
				if(!(oldTB.getTkTimeBlockId().compareTo(tbId) == 0)) {
					newTbList.add(oldTB);
				}
			}
		}
		for(int i = 0; i < hrs.length; i++) {
			BigDecimal hours = new BigDecimal(hrs[i]);
			DateTime beginDateTime = TKUtils.convertDateStringToDateTime(beginDates[i], beginTimes[i]);
			DateTime endDateTime = TKUtils.convertDateStringToDateTime(endDates[i], endTimes[i]);
			String assignString = assignments[i];
			Assignment assignment = (Assignment) HrServiceLocator.getAssignmentService().getAssignment(assignString);
			
			TimeBlock tb = TkServiceLocator.getTimeBlockService().createTimeBlock(tsDoc, beginDateTime, endDateTime, assignment, earnCode, hours,BigDecimal.ZERO, false, false, HrContext.getPrincipalId());
			newTbList.add(tb);
		}
		TkServiceLocator.getTimeBlockService().resetTimeHourDetail(newTbList);
		TkServiceLocator.getTkRuleControllerService().applyRules(TkConstants.ACTIONS.ADD_TIME_BLOCK, newTbList, new ArrayList<LeaveBlock>(), tsDoc.getCalendarEntry(), tsDoc, tsDoc.getPrincipalId());
		TkServiceLocator.getTimeBlockService().saveTimeBlocks(newTbList);
		TimeBlock oldTB = TkServiceLocator.getTimeBlockService().getTimeBlock(tbId);
		TkServiceLocator.getTimeBlockService().deleteTimeBlock(oldTB);
		return mapping.findForward("basic");
	}
	
	public ActionForward validateNewTimeBlock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		ClockActionForm caf = (ClockActionForm)form;
		String tbId = caf.getTbId();
		String[] assignments = caf.getNewAssignDesCol().split(SEPERATOR);
		String[] beginDates = caf.getNewBDCol().split(SEPERATOR);
		String[] beginTimes = caf.getNewBTCol().split(SEPERATOR);
		String[] endDates = caf.getNewEDCol().split(SEPERATOR);
		String[] endTimes = caf.getNewETCol().split(SEPERATOR);
		String[] hrs = caf.getNewHrsCol().split(SEPERATOR);

		List<Interval> newIntervals = new ArrayList<Interval>();
		JSONArray errorMsgList = new JSONArray();

		// validates that all fields are available
		if(assignments.length != beginDates.length ||
				assignments.length!= beginTimes.length ||
				assignments.length != endDates.length ||
				assignments.length != endTimes.length ||
				assignments.length != hrs.length) {
			errorMsgList.add("All fields are required");
		    caf.setOutputString(JSONValue.toJSONString(errorMsgList));
		    return mapping.findForward("ws");
		}

		for(int i = 0; i < hrs.length; i++) {
			String index = String.valueOf(i+1);

			// validate the hours field
			BigDecimal dc = new BigDecimal(hrs[i]);
		    if (dc.compareTo(new BigDecimal("0")) == 0) {
		        errorMsgList.add("The entered hours for entry " + index + " is not valid.");
		        caf.setOutputString(JSONValue.toJSONString(errorMsgList));
		        return mapping.findForward("ws");
		    }

		    // check if the begin / end time are valid
		    // should not include time zone in consideration when conparing time intervals
		    DateTime beginDateTime = TKUtils.convertDateStringToDateTimeWithoutZone(beginDates[i], beginTimes[i]);
			DateTime endDateTime = TKUtils.convertDateStringToDateTimeWithoutZone(endDates[i], endTimes[i]);
		    if ((beginDateTime.compareTo(endDateTime) > 0 || endDateTime.compareTo(beginDateTime) < 0)) {
		        errorMsgList.add("The time or date for entry " + index + " is not valid.");
		        caf.setOutputString(JSONValue.toJSONString(errorMsgList));
		        return mapping.findForward("ws");
		    }

		    // check if new time blocks overlap with existing time blocks
		    Interval addedTimeblockInterval = new Interval(beginDateTime, endDateTime);
		    newIntervals.add(addedTimeblockInterval);
		    for (TimeBlock timeBlock : caf.getTimesheetDocument().getTimeBlocks()) {
		    	if(timeBlock.getTkTimeBlockId().equals(tbId)) {	// ignore the original time block
		    		continue;
		    	}
		    	if(timeBlock.getHours().compareTo(BigDecimal.ZERO) == 0) { // ignore time blocks with zero hours
		    		continue;
		    	}
		    	DateTimeZone dateTimeZone = HrServiceLocator.getTimezoneService().getUserTimezoneWithFallback();
		    	DateTime timeBlockBeginTimestamp = new DateTime(timeBlock.getBeginTimestamp().getTime(), dateTimeZone).withZone(TKUtils.getSystemDateTimeZone());
		    	DateTime timeBlockEndTimestamp = new DateTime(timeBlock.getEndTimestamp().getTime(), dateTimeZone).withZone(TKUtils.getSystemDateTimeZone());
		    	Interval timeBlockInterval = new Interval(timeBlockBeginTimestamp, timeBlockEndTimestamp);
			    if (timeBlockInterval.overlaps(addedTimeblockInterval)) {
			        errorMsgList.add("The time block you are trying to add for entry " + index + " overlaps with an existing time block.");
			        caf.setOutputString(JSONValue.toJSONString(errorMsgList));
			        return mapping.findForward("ws");
			    }
		    }
		}
		// check if new time blocks overlap with each other
		if(newIntervals.size() > 1 ) {
			for(Interval intv1 : newIntervals) {
				for(Interval intv2 : newIntervals) {
					if(intv1.equals(intv2)) {
						continue;
					}
					if (intv1.overlaps(intv2)) {
						errorMsgList.add("There is time overlap between the entries.");
				        caf.setOutputString(JSONValue.toJSONString(errorMsgList));
				        return mapping.findForward("ws");
					}
				}
			}
		}

	    caf.setOutputString(JSONValue.toJSONString(errorMsgList));
		return mapping.findForward("ws");
 	}
	
	 private Boolean isPrincipalAnyProcessorInWorkArea(String principalId, Long tbWorkArea, LocalDate asOfDate) {
	    	Boolean flag = false;
	        Set<Long> workAreas = new HashSet<Long>();
	    	workAreas.addAll(HrServiceLocator.getKPMERoleService().getWorkAreasForPrincipalInRole(principalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.PAYROLL_PROCESSOR.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay(), true));
	        workAreas.addAll(HrServiceLocator.getKPMERoleService().getWorkAreasForPrincipalInRole(principalId, KPMENamespace.KPME_HR.getNamespaceCode(),  KPMERole.PAYROLL_PROCESSOR_DELEGATE.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay(), true));
	        for (Long wa : workAreas) {
	            WorkAreaContract workArea = HrServiceLocator.getWorkAreaService().getWorkArea(wa, asOfDate);
	            if (workArea!= null && tbWorkArea.compareTo(wa)==0) {
	                flag = true;
	                break;
	            }
	        }
	        return flag;
	    }
    
}