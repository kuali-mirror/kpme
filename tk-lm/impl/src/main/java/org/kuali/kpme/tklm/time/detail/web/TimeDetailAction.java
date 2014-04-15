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
package org.kuali.kpme.tklm.time.detail.web;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.api.accrualcategory.AccrualCategoryContract;
import org.kuali.kpme.core.api.accrualcategory.rule.AccrualCategoryRuleContract;
import org.kuali.kpme.core.api.assignment.Assignment;
import org.kuali.kpme.core.api.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.core.api.calendar.Calendar;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.api.earncode.EarnCode;
import org.kuali.kpme.core.api.earncode.EarnCodeContract;
import org.kuali.kpme.core.principal.PrincipalHRAttributes;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.api.common.TkConstants;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlockContract;
import org.kuali.kpme.tklm.api.leave.summary.LeaveSummaryContract;
import org.kuali.kpme.tklm.api.leave.summary.LeaveSummaryRowContract;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlockContract;
import org.kuali.kpme.tklm.api.time.timehourdetail.TimeHourDetail;
import org.kuali.kpme.tklm.common.LMConstants;
import org.kuali.kpme.tklm.leave.block.LeaveBlockAggregate;
import org.kuali.kpme.tklm.leave.calendar.validation.LeaveCalendarValidationUtil;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.leave.transfer.BalanceTransfer;
import org.kuali.kpme.tklm.leave.transfer.validation.BalanceTransferValidationUtils;
import org.kuali.kpme.tklm.time.calendar.TkCalendar;
import org.kuali.kpme.tklm.time.detail.validation.TimeDetailValidationUtil;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timeblock.TimeBlockBo;
import org.kuali.kpme.tklm.time.timeblock.TimeBlockHistory;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.kpme.tklm.time.timesheet.TimesheetUtils;
import org.kuali.kpme.tklm.time.timesheet.web.TimesheetAction;
import org.kuali.kpme.tklm.time.timesummary.AssignmentColumn;
import org.kuali.kpme.tklm.time.timesummary.AssignmentRow;
import org.kuali.kpme.tklm.time.timesummary.EarnCodeSection;
import org.kuali.kpme.tklm.time.timesummary.EarnGroupSection;
import org.kuali.kpme.tklm.time.timesummary.TimeSummary;
import org.kuali.kpme.tklm.time.util.TkContext;
import org.kuali.kpme.tklm.time.util.TkTimeBlockAggregate;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kim.api.identity.principal.EntityNamePrincipalName;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.exception.AuthorizationException;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.UrlFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

public class TimeDetailAction extends TimesheetAction {

    @Override
    protected void checkTKAuthorization(ActionForm form, String methodToCall) throws AuthorizationException {
        super.checkTKAuthorization(form, methodToCall);
        
        TimeDetailActionForm timeDetailActionForm = (TimeDetailActionForm) form;

        String principalId = GlobalVariables.getUserSession().getPrincipalId();
    	TimesheetDocument timesheetDocument = TkServiceLocator.getTimesheetService().getTimesheetDocument(timeDetailActionForm.getDocumentId());
        if (StringUtils.equals(methodToCall, "addTimeBlock") 
        		|| StringUtils.equals(methodToCall, "deleteTimeBlock") 
        		|| StringUtils.equals(methodToCall, "updateTimeBlock")) {
            if (!HrServiceLocator.getHRPermissionService().canEditCalendarDocument(principalId, timesheetDocument)) {
                throw new AuthorizationException(principalId, "TimeDetailAction", "");
            }
        }
    }

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionForward forward = super.execute(mapping, form, request, response);
        TimeDetailActionForm timeDetailActionForm = (TimeDetailActionForm) form;

        CalendarEntry calendarEntry = timeDetailActionForm.getCalendarEntry();
        TimesheetDocument timesheetDocument = timeDetailActionForm.getTimesheetDocument();

        if (calendarEntry != null && timesheetDocument != null) {
			List<String> assignmentKeys = new ArrayList<String>();
	        for (Assignment assignment : timesheetDocument.getAllAssignments()) {
	        	assignmentKeys.add(assignment.getAssignmentKey());
	        }

	        //timeDetailActionForm.setAssignmentDescriptions(timeDetailActionForm.getTimesheetDocument().getAssignmentDescriptions(false));

	        timeDetailActionForm.setDocEditable("false");
	        if (HrContext.isSystemAdmin()) {
	            timeDetailActionForm.setDocEditable("true");
	        } else {
	        	DocumentStatus documentStatus = KewApiServiceLocator.getWorkflowDocumentService().getDocumentStatus(timeDetailActionForm.getDocumentId());
	            if (!DocumentStatus.FINAL.equals(documentStatus) 
	            		&& !DocumentStatus.CANCELED.equals(documentStatus)
	     	 	 	 	&& !DocumentStatus.DISAPPROVED.equals(documentStatus)) {
	            	if(StringUtils.equals(timesheetDocument.getPrincipalId(), GlobalVariables.getUserSession().getPrincipalId())
                            || HrContext.isSystemAdmin()
                            || TkContext.isLocationAdmin()
                            || HrContext.isReviewer()
                            || HrContext.isAnyApprover()
                            || HrContext.isAnyPayrollProcessor()) {
                        timeDetailActionForm.setDocEditable("true");
                    }
	            	
		            //if the timesheet has been approved by at least one of the approvers, the employee should not be able to edit it
		            if (StringUtils.equals(timesheetDocument.getPrincipalId(), GlobalVariables.getUserSession().getPrincipalId())
		            		&& timesheetDocument.getDocumentHeader().getDocumentStatus().equals(HrConstants.ROUTE_STATUS.ENROUTE)) {
			        	Collection actions = KEWServiceLocator.getActionTakenService().findByDocIdAndAction(timesheetDocument.getDocumentHeader().getDocumentId(), HrConstants.DOCUMENT_ACTIONS.APPROVE);
		        		if (!actions.isEmpty()) {
		        			timeDetailActionForm.setDocEditable("false");  
		        		}
			        }
	            } else if (DocumentStatus.FINAL.equals(documentStatus)) {
	            	if(HrContext.isSystemAdmin()) {
	            	  timeDetailActionForm.setNotesEditable(Boolean.TRUE);
	            	} else { 
	            	  timeDetailActionForm.setNotesEditable(Boolean.FALSE);
	            	}
	            }
	        }

	        List<TimeBlock> timeBlocks = TkServiceLocator.getTimesheetService().getTimesheetDocument(timeDetailActionForm.getDocumentId()).getTimeBlocks();
            List<LeaveBlock> leaveBlocks = LmServiceLocator.getLeaveBlockService().getLeaveBlocksForTimeCalendar(timesheetDocument.getPrincipalId(),
                    calendarEntry.getBeginPeriodFullDateTime().toLocalDate(), calendarEntry.getEndPeriodFullDateTime().toLocalDate(), assignmentKeys);


	        timeDetailActionForm.getTimesheetDocument().setTimeBlocks(timeBlocks);
	        assignStyleClassMapForTimeSummary(timeDetailActionForm, timeBlocks, leaveBlocks);

            Calendar payCalendar = HrServiceLocator.getCalendarService().getCalendar(calendarEntry != null ? calendarEntry.getHrCalendarId() : null);

            List<Interval> intervals = TKUtils.getFullWeekDaySpanForCalendarEntry(calendarEntry);
	        LeaveBlockAggregate lbAggregate = new LeaveBlockAggregate(leaveBlocks, calendarEntry, intervals);
	        TkTimeBlockAggregate tbAggregate = new TkTimeBlockAggregate(timeBlocks, calendarEntry, payCalendar, true,intervals);

	        // use both time aggregate and leave aggregate to populate the calendar
	        TkCalendar cal = TkCalendar.getCalendar(tbAggregate, lbAggregate);
	        cal.assignAssignmentStyle(timeDetailActionForm.getAssignStyleClassMap());
	        timeDetailActionForm.setTkCalendar(cal);

	        timeDetailActionForm.setTimeBlockString(ActionFormUtils.getTimeBlocksJson(tbAggregate.getFlattenedTimeBlockList()));
	        timeDetailActionForm.setLeaveBlockString(ActionFormUtils.getLeaveBlocksJson(lbAggregate.getFlattenedLeaveBlockList()));

	        timeDetailActionForm.setOvertimeEarnCodes(HrServiceLocator.getEarnCodeService().getOvertimeEarnCodesStrs(timesheetDocument.getAsOfDate()));
	
	        if (StringUtils.equals(timesheetDocument.getPrincipalId(), GlobalVariables.getUserSession().getPrincipalId())) {
	        	timeDetailActionForm.setWorkingOnItsOwn("true");
	        }

	        setMessages(timeDetailActionForm);

        }
        return forward;
    }

    // use lists of time blocks and leave blocks to build the style class map and assign css class to associated summary rows
	private void assignStyleClassMapForTimeSummary(TimeDetailActionForm tdaf, List<? extends TimeBlockContract> timeBlocks, List<? extends LeaveBlockContract> leaveBlocks) throws Exception {
        TimesheetDocument td = tdaf.getTimesheetDocument();
        TimeSummary ts = (TimeSummary)TkServiceLocator.getTimeSummaryService()
                .getTimeSummary(td.getPrincipalId(), td.getTimeBlocks(), td.getCalendarEntry(), td.getAssignmentMap());

        tdaf.setAssignStyleClassMap(ActionFormUtils.buildAssignmentStyleClassMap(timeBlocks, leaveBlocks));
        Map<String, String> aMap = tdaf.getAssignStyleClassMap();
        // set css classes for each assignment row
        for (EarnGroupSection earnGroupSection : ts.getSections()) {
            for (EarnCodeSection section : earnGroupSection.getEarnCodeSections()) {
                for (AssignmentRow assignRow : section.getAssignmentsRows()) {
                	String assignmentCssStyle = MapUtils.getString(aMap, assignRow.getAssignmentKey());
                	assignRow.setCssClass(assignmentCssStyle);
                	for (AssignmentColumn assignmentColumn : assignRow.getAssignmentColumns().values()) {
                		assignmentColumn.setCssClass(assignmentCssStyle);
                	}
                }
            }

        }
        tdaf.setTimeSummary(ts);
        //ActionFormUtils.validateHourLimit(tdaf);
        ActionFormUtils.addWarningTextFromEarnGroup(tdaf);
        ActionFormUtils.addUnapprovedIPWarningFromClockLog(tdaf);
	}

	protected void setMessages(TimeDetailActionForm timeDetailActionForm) {
        String principalId = HrContext.getTargetPrincipalId();
		TimesheetDocument timesheetDocument = timeDetailActionForm.getTimesheetDocument();
        CalendarEntry calendarEntry = timeDetailActionForm.getCalendarEntry();
		
        List<LeaveBlock> balanceTransferLeaveBlocks = LmServiceLocator.getLeaveBlockService().getLeaveBlocksWithType(timesheetDocument.getPrincipalId(),
                calendarEntry.getBeginPeriodFullDateTime().toLocalDate(), calendarEntry.getEndPeriodFullDateTime().toLocalDate(), LMConstants.LEAVE_BLOCK_TYPE.BALANCE_TRANSFER);
       
        Map<String, Set<String>> allMessages = LeaveCalendarValidationUtil.getWarningMessagesForLeaveBlocks(balanceTransferLeaveBlocks, calendarEntry.getBeginPeriodFullDateTime(), calendarEntry.getEndPeriodFullDateTime());
       
        // add warning messages based on max carry over balances for each accrual category for non-exempt leave users
        List<BalanceTransfer> losses = new ArrayList<BalanceTransfer>();
        if (LmServiceLocator.getLeaveApprovalService().isActiveAssignmentFoundOnJobFlsaStatus(principalId, HrConstants.FLSA_STATUS_NON_EXEMPT, true)) {
        	PrincipalHRAttributes principalCalendar = (PrincipalHRAttributes) HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, calendarEntry.getEndPeriodFullDateTime().toLocalDate());

        	Interval calendarInterval = new Interval(calendarEntry.getBeginPeriodFullDateTime(), calendarEntry.getEndPeriodFullDateTime());
        	Map<String,Set<LeaveBlockContract>> maxBalInfractions = new HashMap<String,Set<LeaveBlockContract>>();
	        
        	if (principalCalendar != null) {
        		maxBalInfractions = LmServiceLocator.getAccrualCategoryMaxBalanceService().getMaxBalanceViolations(calendarEntry, principalId);
   	        
        		for (Entry<String,Set<LeaveBlockContract>> entry : maxBalInfractions.entrySet()) {
        			for (LeaveBlockContract lb : entry.getValue()) {
        				if (calendarInterval.contains(lb.getLeaveDateTime())) {
	    	        		AccrualCategoryContract accrualCat = lb.getAccrualCategoryObj();
				        	AccrualCategoryRuleContract aRule = lb.getAccrualCategoryRule();
				        	if (StringUtils.equals(aRule.getActionAtMaxBalance(),HrConstants.ACTION_AT_MAX_BALANCE.LOSE)) {
				        		DateTime aDate = null;
				        		if (StringUtils.equals(aRule.getMaxBalanceActionFrequency(), HrConstants.MAX_BAL_ACTION_FREQ.YEAR_END)) {
				        			aDate = HrServiceLocator.getLeavePlanService().getRolloverDayOfLeavePlan(principalCalendar.getLeavePlan(), lb.getLeaveLocalDate());
				        		} else {
					        		Calendar cal = HrServiceLocator.getCalendarService().getCalendarByPrincipalIdAndDate(principalId, lb.getLeaveLocalDate(), true);
					        		CalendarEntry leaveEntry = HrServiceLocator.getCalendarEntryService().getCurrentCalendarEntryByCalendarId(cal.getHrCalendarId(), new DateTime(lb.getLeaveDateTime()));
					        		aDate = leaveEntry.getEndPeriodFullDateTime();
				        		}
				        		aDate = aDate.minusDays(1);
				        		if (calendarInterval.contains(aDate.getMillis()) && aDate.compareTo(calendarEntry.getEndPeriodFullDateTime()) <= 0) {
					        		//may want to calculate summary for all rows, displayable or not, and determine displayability via tags.
					    			AccrualCategory accrualCategory = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(aRule.getLmAccrualCategoryId());
					    			BigDecimal accruedBalance = LmServiceLocator.getAccrualService().getAccruedBalanceForPrincipal(principalId, accrualCategory, lb.getLeaveLocalDate());
						        	
//					    			BigDecimal leaveBalance = LmServiceLocator.getLeaveSummaryService().getLeaveBalanceForAccrCatUpToDate(principalId, startDate, endDate, accrualCategory, usageEndDate)
					    			
					    			
						        	BalanceTransfer loseTransfer = LmServiceLocator.getBalanceTransferService().initializeTransfer(principalId, aRule.getLmAccrualCategoryRuleId(), accruedBalance, lb.getLeaveLocalDate());
						        	boolean valid = BalanceTransferValidationUtils.validateTransfer(loseTransfer);
						        	if (valid) {
						        		//validation occurs again before the "transfer" action occurs that submits the forfeiture.
						        		losses.add(loseTransfer);
						        	}
				        		}
				        	}
	
	    		        	// accrual categories within the leave plan that are hidden from the leave summary WILL appear.
	        				String message = "You have exceeded the maximum balance limit for '" + accrualCat.getAccrualCategory() + "' as of " + lb.getLeaveLocalDate() + ". "
	        						+ "Depending upon the accrual category rules, leave over this limit may be forfeited.";
	        				//  leave blocks are sorted in getMaxBalanceViolations() method, so we just take the one with the earliest leave date for an accrual category.
	        				if (!StringUtils.contains(allMessages.get("warningMessages").toString(), "You have exceeded the maximum balance limit for '" + accrualCat.getAccrualCategory())) {
                        	   allMessages.get("warningMessages").add(message);
	        				}
        				}
        			}
        		}
        	}
        	timeDetailActionForm.setForfeitures(losses);
        	
            Map<String, Set<String>> transactionalMessages = LeaveCalendarValidationUtil.validatePendingTransactions(HrContext.getTargetPrincipalId(), calendarEntry.getBeginPeriodFullDateTime().toLocalDate(), calendarEntry.getEndPeriodFullDateTime().toLocalDate());
            allMessages.get("infoMessages").addAll(transactionalMessages.get("infoMessages"));
            allMessages.get("warningMessages").addAll(transactionalMessages.get("warningMessages"));
            allMessages.get("actionMessages").addAll(transactionalMessages.get("actionMessages"));
           
            
            LeaveSummaryContract leaveSummary = null;
			try {
				leaveSummary = LmServiceLocator.getLeaveSummaryService().getLeaveSummary(principalId, calendarEntry);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        	if (principalCalendar != null) {
        	   Calendar calendar = HrServiceLocator.getCalendarService().getCalendarByPrincipalIdAndDate(principalId, calendarEntry.getEndPeriodFullDateTime().toLocalDate(), true);
					
				if (calendar != null) {
					List<CalendarEntry> leaveCalendarEntries = HrServiceLocator.getCalendarEntryService().getCalendarEntriesEndingBetweenBeginAndEndDate(calendar.getHrCalendarId(), calendarEntry.getBeginPeriodFullDateTime(), calendarEntry.getEndPeriodFullDateTime());
					
					List<AccrualCategory> accrualCategories = HrServiceLocator.getAccrualCategoryService().getActiveLeaveAccrualCategoriesForLeavePlan(principalCalendar.getLeavePlan(), calendarEntry.getEndPeriodFullDateTime().toLocalDate());
					for (AccrualCategory accrualCategory : accrualCategories) {
						if (LmServiceLocator.getAccrualCategoryMaxCarryOverService().exceedsAccrualCategoryMaxCarryOver(accrualCategory.getAccrualCategory(), principalId, leaveCalendarEntries, calendarEntry.getEndPeriodFullDateTime().toLocalDate())) {
							String message = "Your pending leave balance is greater than the annual max carry over for accrual category '" + accrualCategory.getAccrualCategory() + "' and upon approval, the excess balance will be lost.";
							if (!allMessages.get("warningMessages").contains(message)) {
		                        allMessages.get("warningMessages").add(message);
							}
						}
					}
					
					// check for the negative Accrual balance for the category.
					if(leaveSummary != null && leaveSummary.getLeaveSummaryRows().size() > 0) {
						for(LeaveSummaryRowContract summaryRow : leaveSummary.getLeaveSummaryRows()) {
							if(summaryRow.getLeaveBalance() != null && summaryRow.getLeaveBalance().compareTo(BigDecimal.ZERO) < 0) {
								String message = "Negative available balance found for the accrual category '"+summaryRow.getAccrualCategory()+ "'.";
			        			allMessages.get("warningMessages").add(message);
			        		}
						}
					}
				}
			}
        }
        
        List<String> infoMessages = timeDetailActionForm.getInfoMessages();
        infoMessages.addAll(allMessages.get("infoMessages"));

        List<String> warningMessages = timeDetailActionForm.getWarningMessages();
        warningMessages.addAll(allMessages.get("warningMessages"));

        List<String> actionMessages = timeDetailActionForm.getActionMessages();
        actionMessages.addAll(allMessages.get("actionMessages"));

        timeDetailActionForm.setInfoMessages(infoMessages);
        timeDetailActionForm.setWarningMessages(warningMessages);
        timeDetailActionForm.setActionMessages(actionMessages);
	}

    /**
     * This method involves creating an object-copy of every TimeBlock on the
     * time sheet for overtime re-calculation.
     *
     * @throws Exception
     */
    public ActionForward deleteTimeBlock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeDetailActionForm tdaf = (TimeDetailActionForm) form;
        
        String principalId = HrContext.getPrincipalId();
        String targetPrincipalId = HrContext.getTargetPrincipalId();
        String documentId = tdaf.getDocumentId();
	 	 	
        //Grab timeblock to be deleted from form
        List<TimeBlock> timeBlocks = tdaf.getTimesheetDocument().getTimeBlocks();
        TimeBlock deletedTimeBlock = null;
        for (TimeBlock tb : timeBlocks) {
            if (tb.getTkTimeBlockId().compareTo(tdaf.getTkTimeBlockId()) == 0) {
                deletedTimeBlock = tb;
                break;
            }
        }
        if (deletedTimeBlock == null) {
            return mapping.findForward("basic");
        }
        List<TimeBlock> newTimeBlocks = TimesheetUtils.getTimesheetTimeblocksForProcessing(tdaf.getTimesheetDocument(), true);
        List<TimeBlock> referenceTimeBlocks = TimesheetUtils.getReferenceTimeBlocks(newTimeBlocks);

        newTimeBlocks.remove(deletedTimeBlock);

        //Delete timeblock
        TkServiceLocator.getTimeBlockService().deleteTimeBlock(deletedTimeBlock);
        // Add a row to the history table
        TimeBlockHistory tbh = new TimeBlockHistory(TimeBlockBo.from(deletedTimeBlock));
        tbh.setActionHistory(TkConstants.ACTIONS.DELETE_TIME_BLOCK);
        TkServiceLocator.getTimeBlockHistoryService().saveTimeBlockHistory(tbh);


        List<LeaveBlock> leaveBlocks = TimesheetUtils.getLeaveBlocksForTimesheet(tdaf.getTimesheetDocument());
        //reset time block
        TimesheetUtils.processTimeBlocksWithRuleChange(newTimeBlocks, referenceTimeBlocks, leaveBlocks, tdaf.getTimesheetDocument().getCalendarEntry(), tdaf.getTimesheetDocument(), HrContext.getPrincipalId());
        generateTimesheetChangedNotification(principalId, targetPrincipalId, documentId);
        
        return mapping.findForward("basic");
    }

    /**
     * This method involves creating an object-copy of every TimeBlock on the
     * time sheet for overtime re-calculation.
     * Based on the form's timeBlockId or leaveBlockId, existing Time/Leave blocks will be deleted and new ones created
     *
     * @throws Exception
     */
    public ActionForward addTimeBlock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeDetailActionForm tdaf = (TimeDetailActionForm) form;
        //if(this.isTokenValid(request,true)) {
        if(StringUtils.isNotEmpty(tdaf.getLmLeaveBlockId())) {
        	List<String> errors = TimeDetailValidationUtil.validateLeaveEntry(tdaf);
        	if(errors.isEmpty()) {
        		//KPME-2832: validate leave entry prior to save.
        		//This duplicates validation done on submissions that went through TimeDetailWSAction, i.e. typical time calendar transactions.
        		this.updateLeaveBlock(tdaf);
        	}
        	else {
        		tdaf.setErrorMessages(errors);
        	}
        	return mapping.findForward("basic");
        }
        
        if(StringUtils.isNotEmpty(tdaf.getSelectedEarnCode())) {
        	EarnCode ec = HrServiceLocator.getEarnCodeService().getEarnCode(tdaf.getSelectedEarnCode(), TKUtils.formatDateTimeStringNoTimezone(tdaf.getEndDate()).toLocalDate());
        	if(ec != null && (ec.getLeavePlan() != null || (ec.getEligibleForAccrual().equals("N") && ec.getAccrualBalanceAction().equals("U")) )) {
        		//leave blocks changes
            	List<String> errors = TimeDetailValidationUtil.validateLeaveEntry(tdaf);
            	if(errors.isEmpty()) {
            		//KPME-2832: validate leave entry prior to save.
            		//This duplicates validation done on submissions that went through TimeDetailWSAction, i.e. typical time calendar transactions.
            		this.changeLeaveBlocks(tdaf);
            	}
            	else {
            		tdaf.setErrorMessages(errors);
            	}
            } else {
            	// time blocks changes
                List<String> errors = TimeDetailValidationUtil.validateTimeEntryDetails(tdaf);
                if(errors.isEmpty()) {
            		//KPME-2832: validate leave entry prior to save.
            		//This duplicates validation done on submissions that went through TimeDetailWSAction, i.e. typical time calendar transactions.
                	this.changeTimeBlocks(tdaf, ec);
                }
                else {
                	tdaf.setErrorMessages(errors);
                }
        	}
        }
       // ActionFormUtils.validateHourLimit(tdaf);
        // Removing the redirect and returning the basic action mapping forward results in
        // duplicate time detail entry forms being submitted on browser refresh or back actions.
        ActionFormUtils.addWarningTextFromEarnGroup(tdaf);
        ActionRedirect redirect = new ActionRedirect();
        redirect.setPath("/TimeDetail.do");
        redirect.addParameter("documentId", tdaf.getDocumentId());
        return redirect;
    }
    
    private void removeOldTimeBlock(TimeDetailActionForm tdaf) {
	  if (tdaf.getTkTimeBlockId() != null) {
	      TimeBlock tb = TkServiceLocator.getTimeBlockService().getTimeBlock(tdaf.getTkTimeBlockId());
	      if (tb != null) {
	          TimeBlockHistory tbh = new TimeBlockHistory(TimeBlockBo.from(tb));
	          TkServiceLocator.getTimeBlockService().deleteTimeBlock(tb);
	
	          // mark the original timeblock as deleted in the history table
			  tbh.setActionHistory(TkConstants.ACTIONS.DELETE_TIME_BLOCK);
			  TkServiceLocator.getTimeBlockHistoryService().saveTimeBlockHistory(tbh);
	
			  // delete the timeblock from the memory
	          tdaf.getTimesheetDocument().getTimeBlocks().remove(tb);
	      }
	  }
    }
    
    private void removeOldLeaveBlock(String lbId) {
  	  if (lbId != null) {
  	      LeaveBlock lb = LmServiceLocator.getLeaveBlockService().getLeaveBlock(lbId);
  	      if (lb != null) {
  	          LmServiceLocator.getLeaveBlockService().deleteLeaveBlock(lbId, HrContext.getPrincipalId());
  	      }
  	  }
    }
    
	/**
	 * 
	 * Callers must first run Time Entry validations on tdaf.
	 * 
	 * @param tdaf
	 */
    // add/update leave blocks 
	private void changeLeaveBlocks(TimeDetailActionForm tdaf) {
		DateTime beginDate = null;
		DateTime endDate = null;
		
		if(tdaf.getStartTime() != null && tdaf.getEndTime() != null) {
			beginDate = TKUtils.convertDateStringToDateTime(tdaf.getStartDate(), tdaf.getStartTime());
			endDate = TKUtils.convertDateStringToDateTime(tdaf.getEndDate(), tdaf.getEndTime());
		} else {
			// should not apply time zone to dates when user's changing an hour entry
			beginDate = TKUtils.formatDateTimeStringNoTimezone(tdaf.getStartDate());
			endDate = TKUtils.formatDateTimeStringNoTimezone(tdaf.getEndDate());
		}
		
		String selectedEarnCode = tdaf.getSelectedEarnCode();
		BigDecimal leaveAmount = tdaf.getLeaveAmount();
		
		String desc = "";	// there's no description field in time calendar pop window
		String spanningWeeks = tdaf.getSpanningWeeks();
        Assignment currentAssignment = tdaf.getTimesheetDocument().getAssignment(AssignmentDescriptionKey.get(tdaf.getSelectedAssignment()), beginDate.toLocalDate());

        LmServiceLocator.getLeaveBlockService().addLeaveBlocks(beginDate, endDate, tdaf.getCalendarEntry(), selectedEarnCode, leaveAmount, desc, currentAssignment,
                spanningWeeks, LMConstants.LEAVE_BLOCK_TYPE.TIME_CALENDAR, HrContext.getTargetPrincipalId());

        List<LeaveBlock> leaveBlocks = TimesheetUtils.getLeaveBlocksForTimesheet(tdaf.getTimesheetDocument());

        // A bad hack to apply rules to all timeblocks on timesheet
        List<TimeBlock> newTimeBlocks = TimesheetUtils.getTimesheetTimeblocksForProcessing(tdaf.getTimesheetDocument(), true);
        List<TimeBlock> referenceTimeBlocks = TimesheetUtils.getReferenceTimeBlocks(newTimeBlocks);

        // if we are changing an existing time block, we need to remove the time block
        if(StringUtils.isNotBlank(tdaf.getTkTimeBlockId())) {
        	TimeBlock tb = TkServiceLocator.getTimeBlockService().getTimeBlock(tdaf.getTkTimeBlockId());
        	if(tb != null) {
        		TkServiceLocator.getTimeBlockService().deleteTimeBlock(tb);
        		newTimeBlocks.remove(tb);	// removed the timeblock that should be deleted from list 
        	}
        }
        TimesheetUtils.processTimeBlocksWithRuleChange(newTimeBlocks, referenceTimeBlocks, leaveBlocks, tdaf.getTimesheetDocument().getCalendarEntry(), tdaf.getTimesheetDocument(), HrContext.getPrincipalId());
        generateTimesheetChangedNotification(HrContext.getPrincipalId(), HrContext.getTargetPrincipalId(), tdaf.getDocumentId());
	}
	
	/**
	 * 
	 * Callers must first run Time Entry validations on tdaf.
	 * 
	 * @param tdaf
	 */
	// add/update time blocks
	private void changeTimeBlocks(TimeDetailActionForm tdaf, EarnCodeContract ec) {
		boolean isClockLogCreated = false;
        String clockLogBeginId = null;
        String clockLogEndId = null;
        tdaf.getDocumentId();

        
        // This is for updating a timeblock or changing
        // If tkTimeBlockId is not null and the new timeblock is valid, delete the existing timeblock and a new one will be created after submitting the form.
        if (tdaf.getTkTimeBlockId() != null) {
            TimeBlock tb = TkServiceLocator.getTimeBlockService().getTimeBlock(tdaf.getTkTimeBlockId());
            if (tb != null) {
	            isClockLogCreated = tb.isClockLogCreated();
                clockLogBeginId = tb.getClockLogBeginId();
                clockLogEndId = tb.getClockLogEndId();
            }
        }


        // Surgery point - Need to construct a Date/Time with Appropriate Timezone.
        DateTime startTime;
        DateTime endTime;
        if(tdaf.getStartTime() != null && tdaf.getEndTime() != null) {
            startTime = TKUtils.convertDateStringToDateTime(tdaf.getStartDate(), tdaf.getStartTime());
            endTime = TKUtils.convertDateStringToDateTime(tdaf.getEndDate(), tdaf.getEndTime());
        	if(ec != null &&  StringUtils.equals(ec.getRecordMethod(), HrConstants.RECORD_METHOD.TIME)) {
	            //KPME-2737
	            if (HrContext.isAnyAdmin() || HrContext.isAnyApprover() || HrContext.isAnyPayrollProcessor()) {
	                startTime = TkServiceLocator.getGracePeriodService().processGracePeriodRule(startTime, LocalDate.fromDateFields(tdaf.getBeginCalendarEntryDate()));
	                endTime = TkServiceLocator.getGracePeriodService().processGracePeriodRule(endTime, LocalDate.fromDateFields(tdaf.getBeginCalendarEntryDate()));
	            }
        	}
        } else {
            // should not apply time zone to dates when user's changing an hour entry
            startTime = TKUtils.formatDateTimeStringNoTimezone(tdaf.getStartDate());
            endTime = TKUtils.formatDateTimeStringNoTimezone(tdaf.getEndDate());
        }
        Assignment currentAssignment = tdaf.getTimesheetDocument().getAssignment(AssignmentDescriptionKey.get(tdaf.getSelectedAssignment()), startTime.toLocalDate());


        // This is just a reference, for code clarity, the below list is actually
        // separate at the object level.
        List<TimeBlock> initialBlocks = TimesheetUtils.getTimesheetTimeblocksForProcessing(tdaf.getTimesheetDocument(), true);
        List<TimeBlock.Builder> newTimeBlocks = ModelObjectUtils.transform(initialBlocks, toTimeBlockBuilder);

        // We need a  cloned reference set so we know whether or not to
        // persist any potential changes without making hundreds of DB calls.
        List<TimeBlock> referenceTimeBlocks = TimesheetUtils.getReferenceTimeBlocks(initialBlocks);

        List<TimeBlock.Builder> timeBlocksToAdd = null;
        // KPME-1446 add spanningweeks to the calls below 
        if (StringUtils.equals(tdaf.getAcrossDays(), "y")
                && !(endTime.getDayOfYear() - startTime.getDayOfYear() <= 1
                && endTime.getHourOfDay() == 0)) {

            timeBlocksToAdd = ModelObjectUtils.transform(TkServiceLocator.getTimeBlockService().buildTimeBlocksSpanDates(tdaf.getTimesheetDocument().getPrincipalId(),
                    tdaf.getTimesheetDocument().getCalendarEntry(), currentAssignment,
                    tdaf.getSelectedEarnCode(), tdaf.getTimesheetDocument().getDocumentId(), startTime,
                    endTime, tdaf.getHours(), tdaf.getAmount(), isClockLogCreated, Boolean.parseBoolean(tdaf.getLunchDeleted()),
                    tdaf.getSpanningWeeks(), HrContext.getPrincipalId(), clockLogBeginId, clockLogEndId), toTimeBlockBuilder);
        } else {
            TimesheetDocument tempTd = tdaf.getTimesheetDocument();
            timeBlocksToAdd = ModelObjectUtils.transform(TkServiceLocator.getTimeBlockService().buildTimeBlocks(tempTd.getPrincipalId(), tempTd.getCalendarEntry(), currentAssignment,
                    tdaf.getSelectedEarnCode(), tdaf.getDocumentId(), startTime,
                    endTime, tdaf.getHours(), tdaf.getAmount(), isClockLogCreated, Boolean.parseBoolean(tdaf.getLunchDeleted()),
                    HrContext.getPrincipalId(), clockLogBeginId, clockLogEndId), toTimeBlockBuilder);
        }
        
        //TimeBlock.Builder existingTimeBlock = null;
        TimeBlock.Builder timeBlockToUpdate = null;
        
        
        if (tdaf.getTkTimeBlockId() != null
               && CollectionUtils.isNotEmpty(timeBlocksToAdd)) {
        	timeBlockToUpdate = timeBlocksToAdd.get(0);
        	TkServiceLocator.getTimeHourDetailService().removeTimeHourDetails(tdaf.getTkTimeBlockId());
        	timeBlockToUpdate.setTkTimeBlockId(tdaf.getTkTimeBlockId());
        }
        
        List<TimeBlock.Builder> finalNewTimeBlocks = new ArrayList<TimeBlock.Builder>();
        for (TimeBlock.Builder tb : newTimeBlocks) {
        	if(!ObjectUtils.equals(tb.getTkTimeBlockId(), tdaf.getTkTimeBlockId())) {
        		finalNewTimeBlocks.add(TimeBlock.Builder.create(tb));
        	} else {
                //existingTimeBlock = tb;
                //existingTimeBlock.setTkTimeBlockId(timeBlockToUpdate.getTkTimeBlockId());
                TimeBlock.Builder existingBlock = TimeBlock.Builder.create(timeBlockToUpdate);
                existingBlock.setVersionNumber(tb.getVersionNumber());
                existingBlock.setObjectId(tb.getObjectId());
                //existingBlock.setTkTimeBlockId(tb.getTkTimeBlockId());
        		finalNewTimeBlocks.add(existingBlock);
        	}
        }
        
        for (TimeBlock.Builder tb : timeBlocksToAdd) {
        	if(tdaf.getTkTimeBlockId() != null) {
	        	if(!StringUtils.equals(tb.getTkTimeBlockId(), tdaf.getTkTimeBlockId())) {
	        		finalNewTimeBlocks.add(TimeBlock.Builder.create(tb));
	        	}
        	} else {
        		finalNewTimeBlocks.add(TimeBlock.Builder.create(tb));
        	}
        }

        //reset time block
        List<TimeBlock> tbs = TkServiceLocator.getTimesheetService().resetTimeBlock(ModelObjectUtils.<TimeBlock>buildImmutableCopy(finalNewTimeBlocks), tdaf.getTimesheetDocument().getAsOfDate());
        finalNewTimeBlocks = ModelObjectUtils.transform(tbs, toTimeBlockBuilder);
        // apply overtime pref
        // I changed start and end times comparison below. it used to be overtimeBeginTimestamp and overtimeEndTimestamp but
        // for some reason, they're always null because, we have removed the time block before getting here. KPME-2162
        if(StringUtils.isNotEmpty(tdaf.getOvertimePref())) {
	        for (TimeBlock.Builder tb : finalNewTimeBlocks) {
	        	if ((StringUtils.isNotEmpty(tdaf.getTkTimeBlockId()) && tdaf.getTkTimeBlockId().equals(tb.getTkTimeBlockId()))
	        		|| (tb.getBeginDateTime().compareTo(startTime) == 0 && tb.getEndDateTime().compareTo(endTime) == 0)) {
	                tb.setOvertimePref(tdaf.getOvertimePref());
	            }
	        }
        }

        tbs = ModelObjectUtils.<TimeBlock>buildImmutableCopy(finalNewTimeBlocks);
        List<LeaveBlock> leaveBlocks = TimesheetUtils.getLeaveBlocksForTimesheet(tdaf.getTimesheetDocument());

        TimesheetUtils.processTimeBlocksWithRuleChange(tbs, referenceTimeBlocks, leaveBlocks, tdaf.getTimesheetDocument().getCalendarEntry(), tdaf.getTimesheetDocument(), HrContext.getPrincipalId());
        //tbs = TkServiceLocator.getTkRuleControllerService().applyRules(TkConstants.ACTIONS.ADD_TIME_BLOCK, tbs, leaveBlocks, tdaf.getCalendarEntry(), tdaf.getTimesheetDocument(), HrContext.getPrincipalId());
        //tbs = TkServiceLocator.getTimeBlockService().saveOrUpdateTimeBlocks(referenceTimeBlocks, tbs, HrContext.getPrincipalId());
        
        generateTimesheetChangedNotification(HrContext.getPrincipalId(), HrContext.getTargetPrincipalId(), tdaf.getDocumentId());
        tdaf.getTimesheetDocument().setTimeBlocks(tbs);
	}
	
	/**
	 * 
	 * Callers must first run Time Entry validations on tdaf.
	 * 
	 * @param tdaf
	 */
	// KPME-2386
	private void updateLeaveBlock(TimeDetailActionForm tdaf) throws Exception {

		String principalId = HrContext.getPrincipalId();
		String targetPrincipalId = HrContext.getTargetPrincipalId();

		String selectedEarnCode = tdaf.getSelectedEarnCode();
		String leaveBlockId = tdaf.getLmLeaveBlockId();
		
		LeaveBlock updatedLeaveBlock = null;
		updatedLeaveBlock = LmServiceLocator.getLeaveBlockService().getLeaveBlock(leaveBlockId);
		
		//	KPME-3070: Code for creating new time block and deleting existing leave block starts here
		EarnCodeContract ec = HrServiceLocator.getEarnCodeService().getEarnCode(tdaf.getSelectedEarnCode(), TKUtils.formatDateTimeStringNoTimezone(tdaf.getEndDate()).toLocalDate());
		if (ec == null || ec.getLeavePlan() == null) {
			//	delete leave block code will come here
			LmServiceLocator.getLeaveBlockService().deleteLeaveBlock(leaveBlockId, HrContext.getPrincipalId());
			
			// time blocks changes
			List<String> errors = TimeDetailValidationUtil.validateTimeEntryDetails(tdaf);
			if (errors.isEmpty()) {
				// validate leave entry prior to save.
				this.changeTimeBlocks(tdaf, ec);
			} else {
				tdaf.setErrorMessages(errors);
			}
			
			ActionFormUtils.addWarningTextFromEarnGroup(tdaf);
			ActionRedirect redirect = new ActionRedirect();
			redirect.setPath("/TimeDetail.do");
			redirect.addParameter("documentId", tdaf.getDocumentId());
			return;
		}

		//	Code for creating new time block ends here


        if (LmServiceLocator.getLMPermissionService().canEditLeaveBlock(HrContext.getPrincipalId(), updatedLeaveBlock)) {
            LeaveBlock.Builder builder = LeaveBlock.Builder.create(updatedLeaveBlock);
            DateTime beginDate = null;
            DateTime endDate = null;

            beginDate = TKUtils.formatDateTimeStringNoTimezone(tdaf.getStartDate());
            endDate = TKUtils.formatDateTimeStringNoTimezone(tdaf.getEndDate());
            builder.setLeaveDateTime(beginDate);

            EarnCode earnCode =  HrServiceLocator.getEarnCodeService().getEarnCode(selectedEarnCode, updatedLeaveBlock.getLeaveLocalDate()); // selectedEarnCode = hrEarnCodeId
            if(earnCode != null && earnCode.getRecordMethod().equalsIgnoreCase(HrConstants.EARN_CODE_TIME)) {
                if(tdaf.getStartTime() != null && tdaf.getEndTime() != null) {
                    beginDate = TKUtils.convertDateStringToDateTimeWithoutZone(tdaf.getStartDate(), tdaf.getStartTime());
                    endDate   = TKUtils.convertDateStringToDateTimeWithoutZone(tdaf.getEndDate(), tdaf.getEndTime());
                }  else {
                    beginDate = TKUtils.formatDateTimeStringNoTimezone(tdaf.getStartDate());
                    endDate = TKUtils.formatDateTimeStringNoTimezone(tdaf.getEndDate());
                }
                builder.setBeginDateTime(beginDate);
                builder.setEndDateTime(endDate);
                builder.setLeaveAmount(TKUtils.getHoursBetween(beginDate.getMillis(), endDate.getMillis()));
            }
            if (!updatedLeaveBlock.getLeaveAmount().equals(tdaf.getLeaveAmount())) {
                builder.setLeaveAmount(tdaf.getLeaveAmount());
                Assignment assignment = tdaf.getTimesheetDocument().getAssignment(AssignmentDescriptionKey.get(tdaf.getSelectedAssignment()), beginDate.toLocalDate());
                builder.setAssignmentKey(tdaf.getSelectedAssignment());
                builder.setJobNumber(assignment.getJobNumber());
                builder.setWorkArea(assignment.getWorkArea());
                builder.setTask(assignment.getTask());
            }

            if (earnCode != null && !StringUtils.equals(updatedLeaveBlock.getEarnCode(), earnCode.getEarnCode())) {
                builder.setEarnCode(earnCode.getEarnCode());
            }
            
            LmServiceLocator.getLeaveBlockService().updateLeaveBlock(updatedLeaveBlock, principalId);
        }
        
        List<LeaveBlock> leaveBlocks = TimesheetUtils.getLeaveBlocksForTimesheet(tdaf.getTimesheetDocument());

        // A bad hack to apply rules to all timeblocks on timesheet
		List<TimeBlock> newTimeBlocks = TimesheetUtils.getTimesheetTimeblocksForProcessing(tdaf.getTimesheetDocument(), true);
        List<TimeBlock> referenceTimeBlocks = TimesheetUtils.getReferenceTimeBlocks(newTimeBlocks);
        TimesheetUtils.processTimeBlocksWithRuleChange(newTimeBlocks, referenceTimeBlocks, leaveBlocks, tdaf.getTimesheetDocument().getCalendarEntry(), tdaf.getTimesheetDocument(), HrContext.getPrincipalId());
   	 	generateTimesheetChangedNotification(principalId, targetPrincipalId, tdaf.getDocumentId());

    }

	//No time blocks should be saved directly in this action forward without first validating the entry.
    public ActionForward updateTimeBlock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        TimeDetailActionForm tdaf = (TimeDetailActionForm) form;

        //Grab timeblock to be updated from form
        List<TimeBlock> timeBlocks = TimesheetUtils.getTimesheetTimeblocksForProcessing(tdaf.getTimesheetDocument(), true);
        // We need a  cloned reference set so we know whether or not to
        // persist any potential changes without making hundreds of DB calls.
        List<TimeBlock> referenceTimeBlocks = TimesheetUtils.getReferenceTimeBlocks(timeBlocks);

        TimeBlock.Builder updatedTimeBlock = null;
        List<TimeHourDetail.Builder> oldDetailList = new ArrayList<TimeHourDetail.Builder>();
        String oldAssignmenString = "";
        AssignmentDescriptionKey assignmentKey = AssignmentDescriptionKey.get(tdaf.getSelectedAssignment());
        for (TimeBlock tb : timeBlocks) {
            if (tb.getTkTimeBlockId().compareTo(tdaf.getTkTimeBlockId()) == 0) {
                updatedTimeBlock = TimeBlock.Builder.create(tb);
            	oldDetailList = updatedTimeBlock.getTimeHourDetails();
            	oldAssignmenString = updatedTimeBlock.getAssignmentKey();
                updatedTimeBlock.setJobNumber(assignmentKey.getJobNumber());
                updatedTimeBlock.setWorkArea(assignmentKey.getWorkArea());
                updatedTimeBlock.setTask(assignmentKey.getTask());
                break;
            }
        }
        
        AssignmentDescriptionKey assignKey = AssignmentDescriptionKey.get(oldAssignmenString);
        Assignment oldAssignment = HrServiceLocator.getAssignmentService().getAssignment(updatedTimeBlock.getPrincipalId(), assignKey, updatedTimeBlock.getBeginDateTime().toLocalDate());
        String oldRegEarnCode = oldAssignment.getJob().getPayTypeObj().getRegEarnCode();
        
        List<TimeHourDetail.Builder> tempList = new ArrayList<TimeHourDetail.Builder>();
        tempList.addAll(oldDetailList);
        for(TimeHourDetail.Builder thd : tempList) {
        	// remove rule created details from old time block
        	if(!thd.getEarnCode().equals(oldRegEarnCode)) {
        	    oldDetailList.remove(thd);
        	}
        }
        
        Set<String> earnCodes = new HashSet<String>();
        if (updatedTimeBlock != null) {
            Assignment assignment = tdaf.getTimesheetDocument().getAssignment(AssignmentDescriptionKey.get(updatedTimeBlock.getAssignmentDescription()), updatedTimeBlock.getBeginDateTime().toLocalDate());

            List<EarnCode> validEarnCodes = TkServiceLocator.getTimesheetService().getEarnCodesForTime(assignment, updatedTimeBlock.getBeginDateTime().toLocalDate(), true);
            for (EarnCode e : validEarnCodes) {
                earnCodes.add(e.getEarnCode());
            }
        }

        if (updatedTimeBlock != null
        		&& earnCodes.contains(updatedTimeBlock.getEarnCode())) {
            List<LeaveBlock> leaveBlocks = TimesheetUtils.getLeaveBlocksForTimesheet(tdaf.getTimesheetDocument());

            TimesheetUtils.processTimeBlocksWithRuleChange(timeBlocks, referenceTimeBlocks, leaveBlocks, tdaf.getTimesheetDocument().getCalendarEntry(), tdaf.getTimesheetDocument(), HrContext.getPrincipalId());
            generateTimesheetChangedNotification(HrContext.getPrincipalId(), HrContext.getTargetPrincipalId(), tdaf.getDocumentId());
        }
        
        //addTimeBlock handles validation and creation of object. Do not save time blocks directly in this method without validating the entry!
        tdaf.setMethodToCall("addTimeBlock");

        return mapping.findForward("basic");
    }


    public ActionForward actualTimeInquiry(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return mapping.findForward("ati");
    }

    public ActionForward deleteLunchDeduction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        TimeDetailActionForm tdaf = (TimeDetailActionForm) form;
        String timeHourDetailId = tdaf.getTkTimeHourDetailId();

        List<TimeBlock> existingBlocks = TimesheetUtils.getTimesheetTimeblocksForProcessing(tdaf.getTimesheetDocument(), true);
        List<TimeBlock> referenceTimeBlocks = TimesheetUtils.getReferenceTimeBlocks(existingBlocks);
        List<TimeBlock.Builder> newTimeBlocks = ModelObjectUtils.transform(existingBlocks, toTimeBlockBuilder);
        TimeHourDetail thd = TkServiceLocator.getTimeHourDetailService().getTimeHourDetail(timeHourDetailId);
        for(TimeBlock.Builder tb : newTimeBlocks) {
        	if(tb.getTkTimeBlockId().equals(thd.getTkTimeBlockId())) {
	        	// mark the lunch deleted as Y
	            tb.setLunchDeleted(true);
        	}
        }
        // remove the related time hour detail row with the lunch deduction
        //TkServiceLocator.getTimeHourDetailService().removeTimeHourDetail(thd.getTkTimeHourDetailId());

        List<LeaveBlock> leaveBlocks = TimesheetUtils.getLeaveBlocksForTimesheet(tdaf.getTimesheetDocument());

        List<TimeBlock> tbs = ModelObjectUtils.<TimeBlock>buildImmutableCopy(newTimeBlocks);

        TimesheetUtils.processTimeBlocksWithRuleChange(tbs, referenceTimeBlocks, leaveBlocks, tdaf.getTimesheetDocument().getCalendarEntry(), tdaf.getTimesheetDocument(), HrContext.getPrincipalId());
        //tbs = TkServiceLocator.getTimesheetService().resetTimeBlock(tbs, tdaf.getTimesheetDocument().getAsOfDate());
        
        // KPME-1340
        //tbs = TkServiceLocator.getTkRuleControllerService().applyRules(TkConstants.ACTIONS.ADD_TIME_BLOCK, tbs, leaveBlocks, tdaf.getCalendarEntry(), tdaf.getTimesheetDocument(), HrContext.getPrincipalId());
        //tbs = TkServiceLocator.getTimeBlockService().saveTimeBlocks(tbs);
        //tdaf.getTimesheetDocument().setTimeBlocks(tbs);
        
        return mapping.findForward("basic");
    }
  
  public ActionForward deleteLeaveBlock(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	  TimeDetailActionForm tdaf = (TimeDetailActionForm) form;
	  
	  String principalId = HrContext.getPrincipalId();
	  String targetPrincipalId = HrContext.getTargetPrincipalId();
	  String documentId = tdaf.getDocumentId();
	  String leaveBlockId = tdaf.getLmLeaveBlockId();

      LeaveBlock blockToDelete = LmServiceLocator.getLeaveBlockService().getLeaveBlock(leaveBlockId);
      if (blockToDelete != null && LmServiceLocator.getLMPermissionService().canDeleteLeaveBlock(HrContext.getPrincipalId(), blockToDelete)) {
		    LmServiceLocator.getLeaveBlockService().deleteLeaveBlock(leaveBlockId, HrContext.getPrincipalId());

          // A bad hack to apply rules to all timeblocks on timesheet
          List<TimeBlock> newTimeBlocks = TimesheetUtils.getTimesheetTimeblocksForProcessing(tdaf.getTimesheetDocument(), true);
          // We need a  cloned reference set so we know whether or not to
          // persist any potential changes without making hundreds of DB calls.
          List<TimeBlock> referenceTimeBlocks = TimesheetUtils.getReferenceTimeBlocks(newTimeBlocks);
          List<LeaveBlock> leaveBlocks = TimesheetUtils.getLeaveBlocksForTimesheet(tdaf.getTimesheetDocument());

          //reset time block
          TimesheetUtils.processTimeBlocksWithRuleChange(newTimeBlocks, referenceTimeBlocks, leaveBlocks, tdaf.getTimesheetDocument().getCalendarEntry(), tdaf.getTimesheetDocument(), HrContext.getPrincipalId());
          generateTimesheetChangedNotification(principalId, targetPrincipalId, documentId);
      }

      // if the leave block is NOT eligible for accrual, rerun accrual service for the leave calendar the leave block is on
      EarnCodeContract ec = HrServiceLocator.getEarnCodeService().getEarnCode(blockToDelete.getEarnCode(), blockToDelete.getLeaveLocalDate());
      if(ec != null && ec.getEligibleForAccrual().equals("N")) {
    	  CalendarEntry ce = HrServiceLocator.getCalendarEntryService()
					.getCurrentCalendarDatesForLeaveCalendar(blockToDelete.getPrincipalId(), blockToDelete.getLeaveDateTime());
    	  if(ce != null) {
    		  LmServiceLocator.getLeaveAccrualService().runAccrual(blockToDelete.getPrincipalId(), ce.getBeginPeriodFullDateTime().toDateTime(), ce.getEndPeriodFullDateTime().toDateTime(), false);
    	  }
      }
		
      return mapping.findForward("basic");
	}
  
	private void generateTimesheetChangedNotification(String principalId, String targetPrincipalId, String documentId) {
		if (!StringUtils.equals(principalId, targetPrincipalId)) {
			EntityNamePrincipalName person = KimApiServiceLocator.getIdentityService().getDefaultNamesForPrincipalId(principalId);
			if (person != null && person.getDefaultName() != null) {
				String subject = "Timesheet Modification Notice";
				StringBuilder message = new StringBuilder();
				message.append("Your Timesheet was changed by ");
				message.append(person.getDefaultName().getCompositeNameUnmasked());
				message.append(" on your behalf.");
				message.append(SystemUtils.LINE_SEPARATOR);
				message.append(getTimesheetURL(documentId));

				HrServiceLocator.getKPMENotificationService().sendNotification(subject, message.toString(), targetPrincipalId);
			}
		}
	}

	@SuppressWarnings("deprecation")
	private String getTimesheetURL(String documentId) {
		Properties params = new Properties();
		params.put("documentId", documentId);
		return UrlFactory.parameterizeUrl(getApplicationBaseUrl() + "/TimeDetail.do", params);
	}

	@Override
	protected String generateToken(HttpServletRequest request) {
		return super.generateToken(request);
	}

	@Override
	protected boolean isTokenValid(HttpServletRequest request, boolean reset) {
		return super.isTokenValid(request, reset);
	}

	@Override
	protected void resetToken(HttpServletRequest request) {
		super.resetToken(request);
	}

	@Override
	protected void saveToken(HttpServletRequest request) {
		super.saveToken(request);
	}

}
