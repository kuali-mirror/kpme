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
package org.kuali.hr.tklm.leave.payout.web;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.joda.time.LocalDate;
import org.kuali.hr.core.bo.accrualcategory.AccrualCategory;
import org.kuali.hr.core.bo.accrualcategory.rule.AccrualCategoryRule;
import org.kuali.hr.core.bo.calendar.entry.CalendarEntry;
import org.kuali.hr.core.service.HrServiceLocator;
import org.kuali.hr.core.web.HrAction;
import org.kuali.hr.tklm.leave.block.LeaveBlock;
import org.kuali.hr.tklm.leave.calendar.LeaveCalendarDocument;
import org.kuali.hr.tklm.leave.payout.LeavePayout;
import org.kuali.hr.tklm.leave.payout.validation.LeavePayoutValidationUtils;
import org.kuali.hr.tklm.leave.service.LmServiceLocator;
import org.kuali.hr.tklm.leave.util.LMConstants;
import org.kuali.hr.tklm.leave.workflow.LeaveCalendarDocumentHeader;
import org.kuali.hr.tklm.time.service.TkServiceLocator;
import org.kuali.hr.tklm.time.timesheet.TimesheetDocument;
import org.kuali.hr.tklm.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.ObjectUtils;

public class LeavePayoutAction extends HrAction {

	public ActionForward leavePayoutOnLeaveApproval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		//if action was submit, execute the payout
		LeavePayoutForm lpf = (LeavePayoutForm) form;
		LeavePayout leavePayout = lpf.getLeavePayout();
	
		boolean valid = LeavePayoutValidationUtils.validatePayout(leavePayout);
		
		//if payout amount has changed, and the resulting change produces forfeiture
		//or changes the forfeiture amount, prompt for confirmation with the amount of
		//forfeiture that the entered amount would produce.

		if(valid) {
			
			String accrualRuleId = leavePayout.getAccrualCategoryRule();
			
			String documentId = leavePayout.getLeaveCalendarDocumentId();
			TimesheetDocumentHeader tsdh = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(documentId);
			LeaveCalendarDocumentHeader lcdh = LmServiceLocator.getLeaveCalendarDocumentHeaderService().getDocumentHeader(documentId);
			CalendarEntry calendarEntry = null;
			String strutsActionForward = "";
			String methodToCall = "approveLeaveCalendar";
			if(ObjectUtils.isNull(tsdh) && ObjectUtils.isNull(lcdh)) {
				throw new RuntimeException("No document found");
			}
			else if(ObjectUtils.isNotNull(tsdh)) {
				//Throws runtime exception, separate action forwards for timesheet/leave calendar payouts.
				TimesheetDocument tsd = TkServiceLocator.getTimesheetService().getTimesheetDocument(documentId);
				calendarEntry = tsd.getCalendarEntry();
				strutsActionForward = "timesheetPayoutSuccess";
				methodToCall = "approveTimesheet";
			}
			else {
				LeaveCalendarDocument lcd = LmServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(documentId);
				calendarEntry = lcd.getCalendarEntry();
				strutsActionForward = "leaveCalendarPayoutSuccess";
				methodToCall = "approveLeaveCalendar";
			}
			
			if(ObjectUtils.isNull(calendarEntry)) {
				throw new RuntimeException("Could not retreive calendar entry for document " + documentId);
			}
			
			AccrualCategoryRule accrualRule = HrServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(accrualRuleId);
			AccrualCategory accrualCategory = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(accrualRule.getLmAccrualCategoryId());
			BigDecimal accruedBalance = HrServiceLocator.getAccrualCategoryService().getAccruedBalanceForPrincipal(leavePayout.getPrincipalId(), accrualCategory, leavePayout.getEffectiveLocalDate());

			LeavePayout defaultBT = LmServiceLocator.getLeavePayoutService().initializePayout(leavePayout.getPrincipalId(), accrualRuleId, accruedBalance, leavePayout.getEffectiveLocalDate());
			if(leavePayout.getPayoutAmount().compareTo(defaultBT.getPayoutAmount()) != 0) {
				//employee changed the payout amount, recalculate forfeiture.
				//Note: payout form has been validated.
				leavePayout = defaultBT.adjust(leavePayout.getPayoutAmount());
				// showing the adjusted balance payout via the execution of another forward
				// would cause a loop that would break only if the original payout amount was re-established in the form.
				// javascript must be written if the forfeited amount is to be updated on the form object.
				// an alternative to javascript would be to render a "re-calculate" button attached to a dedicated action forward method.
				// must re-set leaveCalendarDocumentId, as leavePayout is now just an adjustment of the default initialized BT with no leave calendar doc id.
				leavePayout.setLeaveCalendarDocumentId(documentId);
			}

			LmServiceLocator.getLeavePayoutService().submitToWorkflow(leavePayout);
			
			if(ObjectUtils.isNotNull(documentId)) {
				if(StringUtils.equals(accrualRule.getMaxBalanceActionFrequency(),LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE) ||
						StringUtils.equals(accrualRule.getMaxBalanceActionFrequency(), LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END)) {
					
					ActionForward forward = new ActionForward(mapping.findForward(strutsActionForward));
					forward.setPath(forward.getPath()+"?documentId="+documentId+"&action=R&methodToCall="+methodToCall);
					return forward;
				}
				else
					return mapping.findForward("closeLeavePayoutDoc");
			}
			else
				return mapping.findForward("closeLeavePayoutDoc");
		}
		else //show user errors.
			return mapping.findForward("basic");
	}
	
	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		LeavePayoutForm lpf = (LeavePayoutForm) form;
		LeavePayout leavePayout = lpf.getLeavePayout();
		String accrualCategoryRuleId = leavePayout.getAccrualCategoryRule();
		AccrualCategoryRule accrualRule = HrServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(accrualCategoryRuleId);
		String actionFrequency = accrualRule.getMaxBalanceActionFrequency();
		
		if(StringUtils.equals(actionFrequency,LMConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND))
			return mapping.findForward("closeLeavePayoutDoc");
		else 
			if(StringUtils.equals(actionFrequency, LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE) ||
					StringUtils.equals(actionFrequency, LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END)) {
				
				String documentId = leavePayout.getLeaveCalendarDocumentId();
				TimesheetDocumentHeader tsdh = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(documentId);
				LeaveCalendarDocumentHeader lcdh = LmServiceLocator.getLeaveCalendarDocumentHeaderService().getDocumentHeader(documentId);
				String strutsActionForward = "";
				if(ObjectUtils.isNull(tsdh) && ObjectUtils.isNull(lcdh)) {
					strutsActionForward = "/";
				}
				else if(ObjectUtils.isNotNull(tsdh)) {
					//Throws runtime exception, separate action forwards for timesheet/leave calendar transfers.
					strutsActionForward = mapping.findForward("timesheetCancel").getPath() + "?documentId=" + leavePayout.getLeaveCalendarDocumentId();
				}
				else {
					strutsActionForward = mapping.findForward("leaveCalendarCancel").getPath() + "?documentId=" + leavePayout.getLeaveCalendarDocumentId();
				}

				ActionRedirect redirect = new ActionRedirect();
				redirect.setPath(strutsActionForward);
				return redirect;

			}
			else
				throw new RuntimeException("Action should only be reachable through triggers with frequency ON_DEMAND or LEAVE_APPROVE");
	}
	
	//Entry point for LeavePayout.do for accrual category rule triggered payouts with action frequency On Demand.
	//May be better suited in the LeaveCalendarAction class.
	public ActionForward leavePayoutOnDemand(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GlobalVariables.getMessageMap().putWarning("document.payoutAmount","leavePayout.payoutAmount.adjust");

		LeavePayoutForm lpf = (LeavePayoutForm) form;
		//the leave calendar document that triggered this balance payout.
		String documentId = request.getParameter("documentId");
		String leaveBlockId = request.getParameter("accrualRuleId");
		String timesheet = request.getParameter("timesheet");

		boolean isTimesheet = false;
		if(StringUtils.equals(timesheet, "true")) {
			lpf.isTimesheet(true);
			isTimesheet = true;
		}
		if(ObjectUtils.isNotNull(leaveBlockId)) {
			AccrualCategoryRule aRule = HrServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(leaveBlockId);
			if(ObjectUtils.isNotNull(aRule)) {
				//should somewhat safegaurd against url fabrication.
				if(!StringUtils.equals(aRule.getMaxBalanceActionFrequency(),LMConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND))
					throw new RuntimeException("attempted to execute on-demand balance payout for accrual category with action frequency " + aRule.getMaxBalanceActionFrequency());
				else {
					TimesheetDocument tsd = null;
					LeaveCalendarDocument lcd = null;
					String principalId = null;
					CalendarEntry calendarEntry = null;

					if(isTimesheet) {
						tsd = TkServiceLocator.getTimesheetService().getTimesheetDocument(documentId);
						principalId = tsd.getPrincipalId();
						calendarEntry = tsd.getCalendarEntry();
					}
					else {
						lcd = LmServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(documentId);
						principalId = lcd.getPrincipalId();
						calendarEntry = lcd.getCalendarEntry();
					}
					
					AccrualCategoryRule accrualRule = HrServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(leaveBlockId);
					AccrualCategory accrualCategory = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(accrualRule.getLmAccrualCategoryId());
					BigDecimal accruedBalance = HrServiceLocator.getAccrualCategoryService().getAccruedBalanceForPrincipal(principalId, accrualCategory, LocalDate.now());

					LeavePayout leavePayout = LmServiceLocator.getLeavePayoutService().initializePayout(principalId, leaveBlockId, accruedBalance, LocalDate.now());
					leavePayout.setLeaveCalendarDocumentId(documentId);
					if(ObjectUtils.isNotNull(leavePayout)) {
						if(StringUtils.equals(aRule.getActionAtMaxBalance(),LMConstants.ACTION_AT_MAX_BAL.LOSE)) {	
							// this particular combination of action / action frequency does not particularly make sense
							// unless for some reason users still need to be prompted to submit the loss.
							// For now, we treat as though it is a valid use-case.
							//LmServiceLocator.getLeavePayoutService().submitToWorkflow(leavePayout);
							// May need to update to save the business object to KPME's tables for record keeping.
							leavePayout = LmServiceLocator.getLeavePayoutService().payout(leavePayout);
							// May need to update to save the business object to KPME's tables for record keeping.
							LeaveBlock forfeitedLeaveBlock = LmServiceLocator.getLeaveBlockService().getLeaveBlock(leavePayout.getForfeitedLeaveBlockId());
							forfeitedLeaveBlock.setRequestStatus(LMConstants.REQUEST_STATUS.APPROVED);
							LmServiceLocator.getLeaveBlockService().updateLeaveBlock(forfeitedLeaveBlock, principalId);
							return mapping.findForward("closeLeavePayoutDoc");
						}
						else {
							ActionForward forward = mapping.findForward("basic");
							lpf.setLeaveCalendarDocumentId(documentId);
							lpf.setLeavePayout(leavePayout);
							lpf.setPayoutAmount(leavePayout.getPayoutAmount());
							return forward;
						}
					}
					else
						throw new RuntimeException("could not initialize a balance payout");

				}
			}
			else
				throw new RuntimeException("No rule for this accrual category could be found");
		}
		else
			throw new RuntimeException("No accrual category rule id has been sent in the request.");
	}

	//Entry point for LeavePayout.do for accrual category rule triggered transfers with action frequency Leave Approve.
	//TODO: Rename method to differentiate from ActionForward with same name in LeaveCalendarSubmit.
	public ActionForward approveLeaveCalendar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		
		GlobalVariables.getMessageMap().putWarning("document.newMaintainableObj.transferAmount","leavePayout.transferAmount.adjust");
		LeavePayoutForm btf = (LeavePayoutForm) form;

		List<LeaveBlock> eligiblePayouts = (List<LeaveBlock>) request.getSession().getAttribute("eligibilities");
		if(!eligiblePayouts.isEmpty()) {
			
			Collections.sort(eligiblePayouts, new Comparator() {

                @Override
                public int compare(Object o1, Object o2) {
                    LeaveBlock l1 = (LeaveBlock) o1;
                    LeaveBlock l2 = (LeaveBlock) o2;
                    return l1.getLeaveDate().compareTo(l2.getLeaveDate());
                }

            });
			
			//This is the leave calendar document that triggered this balance transfer.

			String leaveCalendarDocumentId = request.getParameter("documentId");
			ActionForward forward = new ActionForward(mapping.findForward("basic"));
			LeaveCalendarDocument lcd = LmServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(leaveCalendarDocumentId);
			
			LeaveBlock leaveBlock = eligiblePayouts.get(0);
			LocalDate effectiveDate = leaveBlock.getLeaveLocalDate();
			String accrualCategoryRuleId = leaveBlock.getAccrualCategoryRuleId();
			if(!StringUtils.isBlank(accrualCategoryRuleId)) {
				AccrualCategoryRule accrualRule = HrServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(accrualCategoryRuleId);
				AccrualCategory accrualCategory = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(accrualRule.getLmAccrualCategoryId());
				BigDecimal accruedBalance = HrServiceLocator.getAccrualCategoryService().getAccruedBalanceForPrincipal(lcd.getPrincipalId(), accrualCategory, effectiveDate);
			
				LeavePayout leavePayout = LmServiceLocator.getLeavePayoutService().initializePayout(lcd.getPrincipalId(), accrualCategoryRuleId, accruedBalance, effectiveDate);
				leavePayout.setLeaveCalendarDocumentId(leaveCalendarDocumentId);
	
				if(ObjectUtils.isNotNull(leavePayout)) {
	
				if(StringUtils.equals(accrualRule.getActionAtMaxBalance(),LMConstants.ACTION_AT_MAX_BAL.LOSE)) {
					//payouts should never contain losses.
					//losses are treated as a special case of transfer
					//LmServiceLocator.getLeavePayoutService().submitToWorkflow(leavePayout);
					leavePayout = LmServiceLocator.getLeavePayoutService().payout(leavePayout);
					// May need to update to save the business object to KPME's tables for record keeping.
					LeaveBlock forfeitedLeaveBlock = LmServiceLocator.getLeaveBlockService().getLeaveBlock(leavePayout.getForfeitedLeaveBlockId());
					KRADServiceLocator.getBusinessObjectService().save(leavePayout);
					forfeitedLeaveBlock.setRequestStatus(LMConstants.REQUEST_STATUS.APPROVED);
					LmServiceLocator.getLeaveBlockService().updateLeaveBlock(forfeitedLeaveBlock, lcd.getPrincipalId());
					
					if(ObjectUtils.isNotNull(leaveCalendarDocumentId)) {
						if(StringUtils.equals(accrualRule.getMaxBalanceActionFrequency(),LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE) ||
								StringUtils.equals(accrualRule.getMaxBalanceActionFrequency(), LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END)) {
							ActionForward loseForward = new ActionForward(mapping.findForward("leaveCalendarPayoutSuccess"));
							loseForward.setPath(loseForward.getPath()+"?documentId="+leaveCalendarDocumentId+"&action=R&methodToCall=approveLeaveCalendar");
							return loseForward;
						}
						//on demand handled in separate action forward.
					}

				} else {
					btf.setLeaveCalendarDocumentId(leaveCalendarDocumentId);
					btf.setLeavePayout(leavePayout);
					btf.setPayoutAmount(leavePayout.getPayoutAmount());
					return forward;
				}

			}
			throw new RuntimeException("could not initialize balance transfer");

		}
		else
			throw new RuntimeException("unable to fetch the accrual category that triggerred this transfer");
		}
		else
			throw new RuntimeException("No infractions given");
	}
	
	public ActionForward approveTimesheet(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		
		GlobalVariables.getMessageMap().putWarning("document.newMaintainableObj.transferAmount","leavePayout.transferAmount.adjust");
		LeavePayoutForm btf = (LeavePayoutForm) form;

		List<LeaveBlock> eligiblePayouts = (List<LeaveBlock>) request.getSession().getAttribute("eligibilities");
		if(!eligiblePayouts.isEmpty()) {
			
			Collections.sort(eligiblePayouts, new Comparator() {
				
				@Override
				public int compare(Object o1, Object o2) {
					LeaveBlock l1 = (LeaveBlock) o1;
					LeaveBlock l2 = (LeaveBlock) o2;
					return l1.getLeaveDate().compareTo(l2.getLeaveDate());
				}
				
			});
			
			//This is the leave calendar document that triggered this balance transfer.

			String timesheetDocumentId = request.getParameter("documentId");
			ActionForward forward = new ActionForward(mapping.findForward("basic"));
			TimesheetDocument tsd = TkServiceLocator.getTimesheetService().getTimesheetDocument(timesheetDocumentId);
			
			LeaveBlock leaveBlock = eligiblePayouts.get(0);
			LocalDate effectiveDate = leaveBlock.getLeaveLocalDate();
			String accrualCategoryRuleId = leaveBlock.getAccrualCategoryRuleId();
			if(!StringUtils.isBlank(accrualCategoryRuleId)) {
				AccrualCategoryRule accrualRule = HrServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(accrualCategoryRuleId);
				AccrualCategory accrualCategory = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(accrualRule.getLmAccrualCategoryId());
				BigDecimal accruedBalance = HrServiceLocator.getAccrualCategoryService().getAccruedBalanceForPrincipal(tsd.getPrincipalId(), accrualCategory, effectiveDate);

				LeavePayout leavePayout = LmServiceLocator.getLeavePayoutService().initializePayout(tsd.getPrincipalId(), accrualCategoryRuleId, accruedBalance, effectiveDate);
				leavePayout.setLeaveCalendarDocumentId(timesheetDocumentId);
	
				if(ObjectUtils.isNotNull(leavePayout)) {
	
					if(StringUtils.equals(accrualRule.getActionAtMaxBalance(),LMConstants.ACTION_AT_MAX_BAL.LOSE)) {
						// TODO: Redirect user to prompt stating excess leave will be forfeited and ask for confirmation.
						// Do not submit the object to workflow for this max balance action.
						leavePayout = LmServiceLocator.getLeavePayoutService().payout(leavePayout);
						KRADServiceLocator.getBusinessObjectService().save(leavePayout);
	
						// May need to update to save the business object to KPME's tables for record keeping.
						LeaveBlock forfeitedLeaveBlock = LmServiceLocator.getLeaveBlockService().getLeaveBlock(leavePayout.getForfeitedLeaveBlockId());
						forfeitedLeaveBlock.setRequestStatus(LMConstants.REQUEST_STATUS.APPROVED);
						LmServiceLocator.getLeaveBlockService().updateLeaveBlock(forfeitedLeaveBlock, tsd.getPrincipalId());

						if(ObjectUtils.isNotNull(timesheetDocumentId)) {
							if(StringUtils.equals(accrualRule.getMaxBalanceActionFrequency(),LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE) ||
									StringUtils.equals(accrualRule.getMaxBalanceActionFrequency(), LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END)) {
								ActionForward loseForward = new ActionForward(mapping.findForward("timesheetPayoutSuccess"));
								loseForward.setPath(loseForward.getPath()+"?documentId="+timesheetDocumentId+"&action=R&methodToCall=approveTimesheet");
								return loseForward;
							}
							//on demand handled in separate action forward.
						}
	
					} else {
						btf.setLeaveCalendarDocumentId(timesheetDocumentId);
						btf.setLeavePayout(leavePayout);
						btf.setPayoutAmount(leavePayout.getPayoutAmount());
						return forward;
					}
	
				}
				throw new RuntimeException("could not initialize balance transfer");

		}
		else
			throw new RuntimeException("unable to fetch the accrual category that triggerred this transfer");
		}
		else
			throw new RuntimeException("no eligible transfers exist");
	}
	
	public ActionForward closeLeavePayoutDoc(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("closeLeavePayoutDoc");
	}
}
