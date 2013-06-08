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
package org.kuali.kpme.tklm.leave.payout.web;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.accrualcategory.rule.AccrualCategoryRule;
import org.kuali.kpme.core.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.web.KPMEAction;
import org.kuali.kpme.tklm.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.leave.calendar.LeaveCalendarDocument;
import org.kuali.kpme.tklm.leave.payout.LeavePayout;
import org.kuali.kpme.tklm.leave.payout.validation.LeavePayoutValidationUtils;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.leave.workflow.LeaveCalendarDocumentHeader;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.kpme.tklm.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.ObjectUtils;

public class LeavePayoutAction extends KPMEAction {
	
	private static final Logger LOG = Logger.getLogger(LeavePayoutAction.class);

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
				LOG.error("No document found");
//				throw new RuntimeException("No document found");
				GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, "error.document.notfound");
				mapping.findForward("basic");
			}
			else if(ObjectUtils.isNotNull(tsdh)) {
				//Throws runtime exception, separate action forwards for timesheet/leave calendar payouts.
				TimesheetDocument tsd = TkServiceLocator.getTimesheetService().getTimesheetDocument(documentId);
				calendarEntry = tsd == null ? null : tsd.getCalendarEntry();
				strutsActionForward = "timesheetPayoutSuccess";
				methodToCall = "approveTimesheet";
			}
			else {
				LeaveCalendarDocument lcd = LmServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(documentId);
				calendarEntry = lcd == null ? null : lcd.getCalendarEntry();
				strutsActionForward = "leaveCalendarPayoutSuccess";
				methodToCall = "approveLeaveCalendar";    
			}
			
			if(ObjectUtils.isNull(calendarEntry)) {
				LOG.error("Could not retreive calendar entry for document " + documentId);
//				throw new RuntimeException("Could not retreive calendar entry for document " + documentId);
				GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, "error.calendarentry.notfound", new String[] {documentId});
				return mapping.findForward("basic");
			}
			
			AccrualCategoryRule accrualRule = HrServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(accrualRuleId);
			AccrualCategory accrualCategory = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(accrualRule.getLmAccrualCategoryId());
			BigDecimal accruedBalance = LmServiceLocator.getAccrualService().getAccruedBalanceForPrincipal(leavePayout.getPrincipalId(), accrualCategory, leavePayout.getEffectiveLocalDate());

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
				if(StringUtils.equals(accrualRule.getMaxBalanceActionFrequency(),HrConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE) ||
						StringUtils.equals(accrualRule.getMaxBalanceActionFrequency(), HrConstants.MAX_BAL_ACTION_FREQ.YEAR_END)) {
					
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
		
		if(StringUtils.equals(actionFrequency,HrConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND))
			return mapping.findForward("closeLeavePayoutDoc");
		else 
			if(StringUtils.equals(actionFrequency, HrConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE) ||
					StringUtils.equals(actionFrequency, HrConstants.MAX_BAL_ACTION_FREQ.YEAR_END)) {
				
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
			else {
				LOG.warn("Action should only be reachable through triggers with frequency ON_DEMAND or LEAVE_APPROVE");
				GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, "action.reachable.through.triggers");
//				throw new RuntimeException("Action should only be reachable through triggers with frequency ON_DEMAND or LEAVE_APPROVE");
				return mapping.findForward("basic");
			}
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
				if(!StringUtils.equals(aRule.getMaxBalanceActionFrequency(),HrConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND)) {
//					throw new RuntimeException("attempted to execute on-demand balance payout for accrual category with action frequency " + aRule.getMaxBalanceActionFrequency());
					LOG.error("attempted to execute on-demand balance payout for accrual category with action frequency " + aRule.getMaxBalanceActionFrequency());
					GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, "attempted.payout.accrualcategory",new String[] { aRule.getMaxBalanceActionFrequency()});
					return mapping.findForward("basic");
				} else {
					TimesheetDocument tsd = null;
					LeaveCalendarDocument lcd = null;
					String principalId = null;
					CalendarEntry calendarEntry = null;

					if(isTimesheet) {
						tsd = TkServiceLocator.getTimesheetService().getTimesheetDocument(documentId);
						principalId = tsd == null ? null : tsd.getPrincipalId();
					}
					else {
						lcd = LmServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(documentId);
						principalId = lcd == null ? null : lcd.getPrincipalId();
					}
					
					AccrualCategoryRule accrualRule = HrServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(leaveBlockId);
					AccrualCategory accrualCategory = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(accrualRule.getLmAccrualCategoryId());
					BigDecimal accruedBalance = LmServiceLocator.getAccrualService().getAccruedBalanceForPrincipal(principalId, accrualCategory, LocalDate.now());

					LeavePayout leavePayout = LmServiceLocator.getLeavePayoutService().initializePayout(principalId, leaveBlockId, accruedBalance, LocalDate.now());
					leavePayout.setLeaveCalendarDocumentId(documentId);
					if(ObjectUtils.isNotNull(leavePayout)) {
						if(StringUtils.equals(aRule.getActionAtMaxBalance(),HrConstants.ACTION_AT_MAX_BALANCE.LOSE)) {	
							// this particular combination of action / action frequency does not particularly make sense
							// unless for some reason users still need to be prompted to submit the loss.
							// For now, we treat as though it is a valid use-case.
							//LmServiceLocator.getLeavePayoutService().submitToWorkflow(leavePayout);
							// May need to update to save the business object to KPME's tables for record keeping.
							leavePayout = LmServiceLocator.getLeavePayoutService().payout(leavePayout);
							// May need to update to save the business object to KPME's tables for record keeping.
							LeaveBlock forfeitedLeaveBlock = LmServiceLocator.getLeaveBlockService().getLeaveBlock(leavePayout.getForfeitedLeaveBlockId());
							forfeitedLeaveBlock.setRequestStatus(HrConstants.REQUEST_STATUS.APPROVED);
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
					else {
//						throw new RuntimeException("could not initialize a balance payout");
						LOG.error("could not initialize a balance payout");
						GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, "couldnot.initialize.payout");
						return mapping.findForward("basic");
					}
				}
			}
			else {
				LOG.error("No rule for this accrual category could be found");
//				throw new RuntimeException("No rule for this accrual category could be found");
				GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, "no.acccatrule.found");
				return mapping.findForward("basic");
			}
		}
		else { 
			LOG.error("No accrual category rule id has been sent in the request.");
			GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, "no.acccat.ruleid.sent");
			return mapping.findForward("basic");
//			throw new RuntimeException("No accrual category rule id has been sent in the request.");
		}
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
			
			String principalId = lcd == null ? null : lcd.getPrincipalId();
			LeaveBlock leaveBlock = eligiblePayouts.get(0);
			LocalDate effectiveDate = leaveBlock.getLeaveLocalDate();
			String accrualCategoryRuleId = leaveBlock.getAccrualCategoryRuleId();
			if(!StringUtils.isBlank(accrualCategoryRuleId)) {
				AccrualCategoryRule accrualRule = HrServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(accrualCategoryRuleId);
				AccrualCategory accrualCategory = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(accrualRule.getLmAccrualCategoryId());
				BigDecimal accruedBalance = LmServiceLocator.getAccrualService().getAccruedBalanceForPrincipal(principalId, accrualCategory, effectiveDate);
			
				LeavePayout leavePayout = LmServiceLocator.getLeavePayoutService().initializePayout(principalId, accrualCategoryRuleId, accruedBalance, effectiveDate);
				leavePayout.setLeaveCalendarDocumentId(leaveCalendarDocumentId);
	
				if(ObjectUtils.isNotNull(leavePayout)) { 
	
				if(StringUtils.equals(accrualRule.getActionAtMaxBalance(),HrConstants.ACTION_AT_MAX_BALANCE.LOSE)) {
					//payouts should never contain losses.
					//losses are treated as a special case of transfer
					//LmServiceLocator.getLeavePayoutService().submitToWorkflow(leavePayout);
					leavePayout = LmServiceLocator.getLeavePayoutService().payout(leavePayout);
					// May need to update to save the business object to KPME's tables for record keeping.
					LeaveBlock forfeitedLeaveBlock = LmServiceLocator.getLeaveBlockService().getLeaveBlock(leavePayout.getForfeitedLeaveBlockId());
					KRADServiceLocator.getBusinessObjectService().save(leavePayout);
					forfeitedLeaveBlock.setRequestStatus(HrConstants.REQUEST_STATUS.APPROVED);
					LmServiceLocator.getLeaveBlockService().updateLeaveBlock(forfeitedLeaveBlock, principalId);
					
					if(ObjectUtils.isNotNull(leaveCalendarDocumentId)) {
						if(StringUtils.equals(accrualRule.getMaxBalanceActionFrequency(),HrConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE) ||
								StringUtils.equals(accrualRule.getMaxBalanceActionFrequency(), HrConstants.MAX_BAL_ACTION_FREQ.YEAR_END)) {
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

			}  else {
				LOG.error("could not initialize balance transfer.");
				GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, "couldnot.initialize.baltransfer");
				return mapping.findForward("basic");
	
//			throw new RuntimeException("could not initialize balance transfer");
			}

		}
			else { 
				LOG.error("unable to fetch the accrual category that triggerred this transfer");
				GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, "unable.fetch.acccat");
				return mapping.findForward("basic");
//				throw new RuntimeException("unable to fetch the accrual category that triggerred this transfer");
			}
		}
		else {
			LOG.error("No infractions given");
			GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, "no.infractions.given");
			return mapping.findForward("basic");
//			throw new RuntimeException("No infractions given");
		}
		return mapping.findForward("basic");
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
			String principalId = tsd == null ? null : tsd.getPrincipalId();
			
			LeaveBlock leaveBlock = eligiblePayouts.get(0);
			LocalDate effectiveDate = leaveBlock.getLeaveLocalDate();
			String accrualCategoryRuleId = leaveBlock.getAccrualCategoryRuleId();
			if(!StringUtils.isBlank(accrualCategoryRuleId)) {
				AccrualCategoryRule accrualRule = HrServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(accrualCategoryRuleId);
				AccrualCategory accrualCategory = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(accrualRule.getLmAccrualCategoryId());
				BigDecimal accruedBalance = LmServiceLocator.getAccrualService().getAccruedBalanceForPrincipal(principalId, accrualCategory, effectiveDate);

				LeavePayout leavePayout = LmServiceLocator.getLeavePayoutService().initializePayout(principalId, accrualCategoryRuleId, accruedBalance, effectiveDate);
				leavePayout.setLeaveCalendarDocumentId(timesheetDocumentId);
	
				if(ObjectUtils.isNotNull(leavePayout)) {
	
					if(StringUtils.equals(accrualRule.getActionAtMaxBalance(),HrConstants.ACTION_AT_MAX_BALANCE.LOSE)) {
						// TODO: Redirect user to prompt stating excess leave will be forfeited and ask for confirmation.
						// Do not submit the object to workflow for this max balance action.
						leavePayout = LmServiceLocator.getLeavePayoutService().payout(leavePayout);
						KRADServiceLocator.getBusinessObjectService().save(leavePayout);
	
						// May need to update to save the business object to KPME's tables for record keeping.
						LeaveBlock forfeitedLeaveBlock = LmServiceLocator.getLeaveBlockService().getLeaveBlock(leavePayout.getForfeitedLeaveBlockId());
						forfeitedLeaveBlock.setRequestStatus(HrConstants.REQUEST_STATUS.APPROVED);
						LmServiceLocator.getLeaveBlockService().updateLeaveBlock(forfeitedLeaveBlock, principalId);

						if(ObjectUtils.isNotNull(timesheetDocumentId)) {
							if(StringUtils.equals(accrualRule.getMaxBalanceActionFrequency(),HrConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE) ||
									StringUtils.equals(accrualRule.getMaxBalanceActionFrequency(), HrConstants.MAX_BAL_ACTION_FREQ.YEAR_END)) {
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
//				throw new RuntimeException("could not initialize balance transfer");
				LOG.error("could not initialize balance transfer");
				GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, "couldnot.initialize.baltransfer");
				return mapping.findForward("basic");

		}
		else {
			LOG.error("unable to fetch the accrual category that triggerred this transfer");
			GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, "unable.fetch.acccat");
			return mapping.findForward("basic");
			
//			throw new RuntimeException("unable to fetch the accrual category that triggerred this transfer");
		}
		}
		else {
			LOG.error("no eligible transfers exist");
			GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, "error.eligible.transfer.notExist");
			return mapping.findForward("basic");
		
//			throw new RuntimeException("no eligible transfers exist");
		}
	}
	
	public ActionForward closeLeavePayoutDoc(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("closeLeavePayoutDoc");
	}
}
