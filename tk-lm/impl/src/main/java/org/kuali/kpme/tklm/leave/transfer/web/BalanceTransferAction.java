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
package org.kuali.kpme.tklm.leave.transfer.web;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.api.accrualcategory.rule.AccrualCategoryRule;
import org.kuali.kpme.core.api.accrualcategory.rule.AccrualCategoryRuleContract;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntryContract;
import org.kuali.kpme.core.api.earncode.EarnCode;
import org.kuali.kpme.core.earncode.EarnCodeBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.web.KPMEAction;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.leave.block.LeaveBlockBo;
import org.kuali.kpme.tklm.leave.calendar.LeaveCalendarDocument;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.leave.timeoff.SystemScheduledTimeOff;
import org.kuali.kpme.tklm.leave.transfer.BalanceTransfer;
import org.kuali.kpme.tklm.leave.transfer.validation.BalanceTransferValidationUtils;
import org.kuali.kpme.tklm.leave.workflow.LeaveCalendarDocumentHeader;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.kpme.tklm.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.ObjectUtils;

public class BalanceTransferAction extends KPMEAction {

	private static final Logger LOG = Logger.getLogger(BalanceTransferAction.class);
	
	public ActionForward balanceTransferOnLeaveApproval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		//if action was submit, execute the transfer
		BalanceTransferForm btf = (BalanceTransferForm) form;
		BalanceTransfer balanceTransfer = btf.getBalanceTransfer();
	
		boolean valid = BalanceTransferValidationUtils.validateTransfer(balanceTransfer);
		
		//if transfer amount has changed, and the resulting change produces forfeiture
		//or changes the forfeiture amount, prompt for confirmation with the amount of
		//forfeiture that the entered amount would produce.

		if(valid) {
			
			String accrualRuleId = balanceTransfer.getAccrualCategoryRule();
			
			String documentId = balanceTransfer.getLeaveCalendarDocumentId();
			TimesheetDocumentHeader tsdh = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(documentId);
			LeaveCalendarDocumentHeader lcdh = LmServiceLocator.getLeaveCalendarDocumentHeaderService().getDocumentHeader(documentId);
			CalendarEntryContract calendarEntry = null;
			String strutsActionForward = "";
			String methodToCall = "approveLeaveCalendar";
			if(ObjectUtils.isNull(tsdh) && ObjectUtils.isNull(lcdh)) {
				LOG.error("No document found");
				GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, "error.document.notfound");
				return mapping.findForward("basic");
//				throw new RuntimeException("No document found");
			}
			else if(ObjectUtils.isNotNull(tsdh)) {
				//Throws runtime exception, separate action forwards for timesheet/leave calendar transfers.
				TimesheetDocument tsd = TkServiceLocator.getTimesheetService().getTimesheetDocument(documentId);
				calendarEntry = tsd != null ? tsd.getCalendarEntry() : null;
				strutsActionForward = "timesheetTransferSuccess";
				methodToCall = "approveTimesheet";
			}
			else {
				LeaveCalendarDocument lcd = LmServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(documentId);
				calendarEntry = lcd != null ? lcd.getCalendarEntry() : null;
				strutsActionForward = "leaveCalendarTransferSuccess";
				methodToCall = "approveLeaveCalendar";
			}
			
			if(ObjectUtils.isNull(calendarEntry)) {
				LOG.error("Could not retreive calendar entry for document " + documentId);
				GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, "error.calendarentry.notfound", new String[] {documentId});
				return mapping.findForward("basic");
//				throw new RuntimeException("Could not retreive calendar entry for document " + documentId);
			}
			
			AccrualCategoryRule accrualRule = HrServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(accrualRuleId);
			
			AccrualCategory accrualCategory = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(accrualRule.getLmAccrualCategoryId());
			BigDecimal accruedBalance = LmServiceLocator.getAccrualService().getAccruedBalanceForPrincipal(balanceTransfer.getPrincipalId(), accrualCategory, balanceTransfer.getEffectiveLocalDate());

			BalanceTransfer defaultBT = LmServiceLocator.getBalanceTransferService().initializeTransfer(balanceTransfer.getPrincipalId(), accrualRuleId, accruedBalance, balanceTransfer.getEffectiveLocalDate());
			if(balanceTransfer.getTransferAmount().compareTo(defaultBT.getTransferAmount()) != 0) {
				//employee changed the transfer amount, recalculate forfeiture.
				//Note: transfer form has been validated.
				balanceTransfer = defaultBT.adjust(balanceTransfer.getTransferAmount());
				// showing the adjusted balance transfer via the execution of another forward
				// would cause a loop that would break only if the original transfer amount was re-established in the form.
				// javascript must be written if the forfeited amount is to be updated on the form object.
				// an alternative to javascript would be to render a "re-calculate" button attached to a dedicated action forward method.
				// must re-set leaveCalendarDocumentId, as balanceTransfer is now just an adjustment of the default initialized BT with no leave calendar doc id.
				balanceTransfer.setLeaveCalendarDocumentId(documentId);
			}

			LmServiceLocator.getBalanceTransferService().submitToWorkflow(balanceTransfer);
			
			if(ObjectUtils.isNotNull(documentId)) {
				if(StringUtils.equals(accrualRule.getMaxBalanceActionFrequency(),HrConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE) ||
						StringUtils.equals(accrualRule.getMaxBalanceActionFrequency(), HrConstants.MAX_BAL_ACTION_FREQ.YEAR_END)) {
					ActionForward forward = new ActionForward(mapping.findForward(strutsActionForward));
					forward.setPath(forward.getPath()+"?documentId="+documentId+"&action=R&methodToCall="+methodToCall);
					return forward;
				}
				else
					return mapping.findForward("closeBalanceTransferDoc");
			}
			else
				return mapping.findForward("closeBalanceTransferDoc");
		}
		else //show user errors.
			return mapping.findForward("basic");
	}

	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		BalanceTransferForm btf = (BalanceTransferForm) form;
		BalanceTransfer bt = btf.getBalanceTransfer();

		if(btf.isSstoTransfer()) {
			return mapping.findForward("closeBalanceTransferDoc");
		}
		
		String accrualCategoryRuleId = bt.getAccrualCategoryRule();
		AccrualCategoryRule accrualRule = HrServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(accrualCategoryRuleId);
		String actionFrequency = accrualRule.getMaxBalanceActionFrequency();
		
		if(StringUtils.equals(actionFrequency,HrConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND)) 
			return mapping.findForward("closeBalanceTransferDoc");
		else 
			if(StringUtils.equals(actionFrequency, HrConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE) ||
					StringUtils.equals(actionFrequency, HrConstants.MAX_BAL_ACTION_FREQ.YEAR_END)) {
				String documentId = bt.getLeaveCalendarDocumentId();
				//HrServiceLocator.getCalendarDocumentHeaderService().getCalendarDocumentHeader(documentId)
				TimesheetDocumentHeader tsdh = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(documentId);
				LeaveCalendarDocumentHeader lcdh = LmServiceLocator.getLeaveCalendarDocumentHeaderService().getDocumentHeader(documentId);
				String strutsActionForward = "";
				if(ObjectUtils.isNull(tsdh) && ObjectUtils.isNull(lcdh)) {
					strutsActionForward = "/";
				}
				else if(ObjectUtils.isNotNull(tsdh)) {
					//Throws runtime exception, separate action forwards for timesheet/leave calendar transfers.
					strutsActionForward = mapping.findForward("timesheetCancel").getPath() + "?documentId=" + bt.getLeaveCalendarDocumentId();
				}
				else {
					strutsActionForward = mapping.findForward("leaveCalendarCancel").getPath() + "?documentId=" + bt.getLeaveCalendarDocumentId();
				}

				ActionRedirect redirect = new ActionRedirect();
				redirect.setPath(strutsActionForward);
				return redirect;
			}
			else {
				LOG.error("Action should only be reachable through triggers with frequency ON_DEMAND or LEAVE_APPROVE");
				GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, "action.reachable.through.triggers");
				return mapping.findForward("basic");
//				throw new RuntimeException("Action should only be reachable through triggers with frequency ON_DEMAND or LEAVE_APPROVE");
			}
	}
	
	//Entry point for BalanceTransfer.do for accrual category rule triggered transfers with action frequency On Demand.
	//May be better suited in the LeaveCalendarAction class.
	public ActionForward balanceTransferOnDemand(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GlobalVariables.getMessageMap().putWarning("document.transferAmount","balanceTransfer.transferAmount.adjust");

		BalanceTransferForm btf = (BalanceTransferForm) form;
		//the leave calendar document that triggered this balance transfer.
		String documentId = request.getParameter("documentId");
		String accrualRuleId = request.getParameter("accrualRuleId");
		String timesheet = request.getParameter("timesheet");
		boolean isTimesheet = false;
		if(StringUtils.equals(timesheet, "true")) {
			btf.isTimesheet(true);
			isTimesheet = true;
		}
		if(ObjectUtils.isNotNull(accrualRuleId)) {
			//LeaveBlock lb = LmServiceLocator.getLeaveBlockService().getLeaveBlock(leaveBlockId);
			AccrualCategoryRule aRule = HrServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(accrualRuleId);
			if(ObjectUtils.isNotNull(aRule)) {
				//should somewhat safegaurd against url fabrication.
				if(!StringUtils.equals(aRule.getMaxBalanceActionFrequency(),HrConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND)) {
//					throw new RuntimeException("attempted to execute on-demand balance transfer for accrual category with action frequency " + aRule.getMaxBalanceActionFrequency());
					LOG.error("attempted to execute on-demand balance transfer for accrual category with action frequency " + aRule.getMaxBalanceActionFrequency());
					GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, "attempted.baltransfer.accrualcategory",new String[] { aRule.getMaxBalanceActionFrequency()});
					return mapping.findForward("basic");
				} else {
					String principalId = null;

					if(isTimesheet) {
						TimesheetDocument tsd = TkServiceLocator.getTimesheetService().getTimesheetDocument(documentId);
			 	 	 	principalId = tsd == null ? null : tsd.getPrincipalId();
					}
					else {
						LeaveCalendarDocument lcd = LmServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(documentId);
			 	 	 	principalId = lcd == null ? null : lcd.getPrincipalId();
					}

					AccrualCategoryRule accrualRule = HrServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(accrualRuleId);
					AccrualCategory accrualCategory = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(accrualRule.getLmAccrualCategoryId());
					BigDecimal accruedBalance = LmServiceLocator.getAccrualService().getAccruedBalanceForPrincipal(principalId, accrualCategory, LocalDate.now());

					BalanceTransfer balanceTransfer = LmServiceLocator.getBalanceTransferService().initializeTransfer(principalId, aRule.getLmAccrualCategoryRuleId(), accruedBalance, LocalDate.now());
					balanceTransfer.setLeaveCalendarDocumentId(documentId);
					if(ObjectUtils.isNotNull(balanceTransfer)) {
						if(StringUtils.equals(aRule.getActionAtMaxBalance(),HrConstants.ACTION_AT_MAX_BALANCE.LOSE)) {	
							// this particular combination of action / action frequency does not particularly make sense
							// unless for some reason users still need to be prompted to submit the loss.
							// For now, we treat as though it is a valid use-case.
							//LmServiceLocator.getBalanceTransferService().submitToWorkflow(balanceTransfer);
							// May need to update to save the business object to KPME's tables for record keeping.
							balanceTransfer = LmServiceLocator.getBalanceTransferService().transfer(balanceTransfer);
							// May need to update to save the business object to KPME's tables for record keeping.
							LeaveBlock forfeitedLeaveBlock = LmServiceLocator.getLeaveBlockService().getLeaveBlock(balanceTransfer.getForfeitedLeaveBlockId());
							LeaveBlock.Builder builder = LeaveBlock.Builder.create(forfeitedLeaveBlock);
                            builder.setRequestStatus(HrConstants.REQUEST_STATUS.APPROVED);
							LmServiceLocator.getLeaveBlockService().updateLeaveBlock(builder.build(), principalId);
							return mapping.findForward("closeBalanceTransferDoc");
						}
						else {
							ActionForward forward = mapping.findForward("basic");
							btf.setLeaveCalendarDocumentId(documentId);
							btf.setBalanceTransfer(balanceTransfer);
							btf.setTransferAmount(balanceTransfer.getTransferAmount());
							return forward;
						}
					}
					else {
						LOG.error("could not initialize a balance transfer");
						GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, "couldnot.initialize.baltransfer");
//						throw new RuntimeException("could not initialize a balance transfer");
						return mapping.findForward("basic");
					}

				}
			}
			else {
				LOG.error("No rule for this accrual category could be found");
				GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, "no.acccatrule.found");
				return mapping.findForward("basic");
//				throw new RuntimeException("No rule for this accrual category could be found");
			}
		}
		else {
			LOG.error("No accrual category rule id has been sent in the request.");
			GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, "no.acccat.ruleid.sent");
			return mapping.findForward("basic");
//			throw new RuntimeException("No accrual category rule id has been sent in the request.");
		}
	}

	//Entry point for BalanceTransfer.do for accrual category rule triggered transfers with action frequency Leave Approve.
	//TODO: Rename method to differentiate from ActionForward with same name in LeaveCalendarSubmit.
	public ActionForward approveLeaveCalendar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		
		GlobalVariables.getMessageMap().putWarning("document.newMaintainableObj.transferAmount","balanceTransfer.transferAmount.adjust");
		BalanceTransferForm btf = (BalanceTransferForm) form;

		List<LeaveBlockBo> eligibleTransfers = (List<LeaveBlockBo>) request.getSession().getAttribute("eligibilities");
		if(eligibleTransfers != null && !eligibleTransfers.isEmpty()) {
			
			Collections.sort(eligibleTransfers, new Comparator() {

                @Override
                public int compare(Object o1, Object o2) {
                    LeaveBlockBo l1 = (LeaveBlockBo) o1;
                    LeaveBlockBo l2 = (LeaveBlockBo) o2;
                    return l1.getLeaveDate().compareTo(l2.getLeaveDate());
                }

            });
			
			//This is the leave calendar document that triggered this balance transfer.

			String leaveCalendarDocumentId = request.getParameter("documentId");
			ActionForward forward = new ActionForward(mapping.findForward("basic"));
			LeaveCalendarDocument lcd = LmServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(leaveCalendarDocumentId);
			
			String principalId = lcd == null ? null : lcd.getPrincipalId();
			LeaveBlockBo leaveBlock = eligibleTransfers.get(0);
			LocalDate effectiveDate = leaveBlock.getLeaveLocalDate();
            AccrualCategoryRuleContract aRule = leaveBlock.getAccrualCategoryRule();
			if(aRule != null) {
				
				AccrualCategory accrualCategory = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(aRule.getLmAccrualCategoryId());
				BigDecimal accruedBalance = LmServiceLocator.getAccrualService().getAccruedBalanceForPrincipal(principalId, accrualCategory, leaveBlock.getLeaveLocalDate());
				
				BalanceTransfer balanceTransfer = LmServiceLocator.getBalanceTransferService().initializeTransfer(principalId, aRule.getLmAccrualCategoryRuleId(), accruedBalance, effectiveDate);
				
				balanceTransfer.setLeaveCalendarDocumentId(leaveCalendarDocumentId);
				
				if(ObjectUtils.isNotNull(balanceTransfer)) {
					if(StringUtils.equals(aRule.getActionAtMaxBalance(),HrConstants.ACTION_AT_MAX_BALANCE.LOSE)) {
	
                        //LmServiceLocator.getBalanceTransferService().submitToWorkflow(balanceTransfer);
                        balanceTransfer = LmServiceLocator.getBalanceTransferService().transfer(balanceTransfer);
                        // May need to update to save the business object to KPME's tables for record keeping.
                        LeaveBlock forfeitedLeaveBlock = LmServiceLocator.getLeaveBlockService().getLeaveBlock(balanceTransfer.getForfeitedLeaveBlockId());
                        KRADServiceLocator.getBusinessObjectService().save(balanceTransfer);
                        LeaveBlock.Builder builder = LeaveBlock.Builder.create(forfeitedLeaveBlock);
                        builder.setRequestStatus(HrConstants.REQUEST_STATUS.APPROVED);
                        LmServiceLocator.getLeaveBlockService().updateLeaveBlock(builder.build(), principalId);

                        if(ObjectUtils.isNotNull(leaveCalendarDocumentId)) {
                            if(StringUtils.equals(aRule.getMaxBalanceActionFrequency(),HrConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE) ||
                                    StringUtils.equals(aRule.getMaxBalanceActionFrequency(), HrConstants.MAX_BAL_ACTION_FREQ.YEAR_END)) {
                                ActionForward loseForward = new ActionForward(mapping.findForward("leaveCalendarTransferSuccess"));
                                loseForward.setPath(loseForward.getPath()+"?documentId="+leaveCalendarDocumentId+"&action=R&methodToCall=approveLeaveCalendar");
                                return loseForward;
                            }
                                //on demand handled in separate action forward.
                        }

					} else {
						btf.setLeaveCalendarDocumentId(leaveCalendarDocumentId);
						btf.setBalanceTransfer(balanceTransfer);
						btf.setTransferAmount(balanceTransfer.getTransferAmount());
						return forward;
					}
			    }
				LOG.error("could not initialize balance transfer");
				GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, "couldnot.initialize.baltransfer");
				return mapping.findForward("basic");
//			    throw new RuntimeException("could not initialize balance transfer");
		    } else {
				LOG.error("unable to fetch the accrual category that triggerred this transfer");
				GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, "unable.fetch.acccat");
				return mapping.findForward("basic");
//			    throw new RuntimeException("unable to fetch the accrual category that triggerred this transfer");
            }
		} else {
			LOG.error("No infractions given");
			GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, "no.infractions.given");
			return mapping.findForward("basic");
//			throw new RuntimeException("No infractions given");
        }
	}
	
	public ActionForward approveTimesheet(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		
		GlobalVariables.getMessageMap().putWarning("document.newMaintainableObj.transferAmount","balanceTransfer.transferAmount.adjust");
		BalanceTransferForm btf = (BalanceTransferForm) form;

		List<LeaveBlockBo> eligibleTransfers = (List<LeaveBlockBo>) request.getSession().getAttribute("eligibilities");
		if(eligibleTransfers != null && !eligibleTransfers.isEmpty()) {
			
			Collections.sort(eligibleTransfers, new Comparator() {
				
				@Override
				public int compare(Object o1, Object o2) {
					LeaveBlockBo l1 = (LeaveBlockBo) o1;
					LeaveBlockBo l2 = (LeaveBlockBo) o2;
					return l1.getLeaveDate().compareTo(l2.getLeaveDate());
				}
				
			});
			
			//This is the leave calendar document that triggered this balance transfer.

			String timesheetDocumentId = request.getParameter("documentId");
			ActionForward forward = new ActionForward(mapping.findForward("basic"));
			TimesheetDocument tsd = TkServiceLocator.getTimesheetService().getTimesheetDocument(timesheetDocumentId);
			String principalId = tsd == null ? null : tsd.getPrincipalId();
			
			LeaveBlockBo leaveBlock = eligibleTransfers.get(0);
			LocalDate effectiveDate = leaveBlock.getLeaveLocalDate();
            AccrualCategoryRuleContract accrualRule = leaveBlock.getAccrualCategoryRule();
			if(accrualRule != null) {
				AccrualCategory accrualCategory = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(accrualRule.getLmAccrualCategoryId());
				BigDecimal accruedBalance = LmServiceLocator.getAccrualService().getAccruedBalanceForPrincipal(principalId, accrualCategory, leaveBlock.getLeaveLocalDate());

				BalanceTransfer balanceTransfer = LmServiceLocator.getBalanceTransferService().initializeTransfer(principalId, accrualRule.getLmAccrualCategoryRuleId(), accruedBalance, effectiveDate);
				balanceTransfer.setLeaveCalendarDocumentId(timesheetDocumentId);
	
				if(ObjectUtils.isNotNull(balanceTransfer)) {
	
					if(StringUtils.equals(accrualRule.getActionAtMaxBalance(),HrConstants.ACTION_AT_MAX_BALANCE.LOSE)) {
						// TODO: Redirect user to prompt stating excess leave will be forfeited and ask for confirmation.
						// Do not submit the object to workflow for this max balance action.
						balanceTransfer = LmServiceLocator.getBalanceTransferService().transfer(balanceTransfer);
						KRADServiceLocator.getBusinessObjectService().save(balanceTransfer);
	
						// May need to update to save the business object to KPME's tables for record keeping.
						LeaveBlock forfeitedLeaveBlock = LmServiceLocator.getLeaveBlockService().getLeaveBlock(balanceTransfer.getForfeitedLeaveBlockId());
                        LeaveBlock.Builder builder = LeaveBlock.Builder.create(forfeitedLeaveBlock);
                        builder.setRequestStatus(HrConstants.REQUEST_STATUS.APPROVED);
						LmServiceLocator.getLeaveBlockService().updateLeaveBlock(builder.build(), principalId);

						if(ObjectUtils.isNotNull(timesheetDocumentId)) {
							if(StringUtils.equals(accrualRule.getMaxBalanceActionFrequency(),HrConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE) ||
									StringUtils.equals(accrualRule.getMaxBalanceActionFrequency(), HrConstants.MAX_BAL_ACTION_FREQ.YEAR_END)) {
								ActionForward loseForward = new ActionForward(mapping.findForward("timesheetTransferSuccess"));
								loseForward.setPath(loseForward.getPath()+"?documentId="+timesheetDocumentId+"&action=R&methodToCall=approveTimesheet");
								return loseForward;
							}
							//on demand handled in separate action forward.
						}
	
					} else {
						btf.setLeaveCalendarDocumentId(timesheetDocumentId);
						btf.setBalanceTransfer(balanceTransfer);
						btf.setTransferAmount(balanceTransfer.getTransferAmount());
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
	
	public ActionForward closeBalanceTransferDoc(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("closeBalanceTransferDoc");
	}
	
	/* Delete system scheduled time off usage leave block from Leave or Time Calendar 
	 */
	public ActionForward deleteSSTOLeaveBlock(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BalanceTransferForm btf = (BalanceTransferForm) form;
		buildBalanceTransferForLeaveBlock(btf, request.getParameter("leaveBlockId"));
	
		return new ActionForward(mapping.findForward("basic"));
	}
	
	/* Build balance transfer based on the to-be-deleted leave block 
	 */
	private void buildBalanceTransferForLeaveBlock(BalanceTransferForm btf, String lbId) {
		LeaveBlock lb = LmServiceLocator.getLeaveBlockService().getLeaveBlock(lbId);
		// this leave block is a ssto usage block, need to use it fo find the accrualed leave block which has a positive amount
		if(lb == null || StringUtils.isEmpty(lb.getScheduleTimeOffId())) {
			LOG.error("could not find the System Scheduled Time Off leave block that needs to be transferred!");
			GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, "error.SSTOlb.nonExist");
			return;
//			throw new RuntimeException("could not find the System Scheduled Time Off leave block that needs to be transferred!");	
		}
		SystemScheduledTimeOff ssto = LmServiceLocator.getSysSchTimeOffService().getSystemScheduledTimeOff(lb.getScheduleTimeOffId());
		BigDecimal amountTransferred = ssto.getTransferConversionFactor() == null ? lb.getLeaveAmount() : lb.getLeaveAmount().multiply(ssto.getTransferConversionFactor());
		EarnCode ec = HrServiceLocator.getEarnCodeService().getEarnCode(ssto.getTransfertoEarnCode(), lb.getLeaveLocalDate());
		
		BalanceTransfer bt = new BalanceTransfer();
		bt.setTransferAmount(lb.getLeaveAmount().abs());	// the usage leave block's leave amount is negative
		bt.setFromAccrualCategory(lb.getAccrualCategory());
		bt.setAmountTransferred(amountTransferred.abs());
		bt.setToAccrualCategory(ec.getAccrualCategory());
		bt.setSstoId(lb.getScheduleTimeOffId());
		bt.setEffectiveLocalDate(lb.getLeaveLocalDate());
		bt.setPrincipalId(lb.getPrincipalId());
		
		btf.setBalanceTransfer(bt);
		btf.setTransferAmount(bt.getTransferAmount());
		GlobalVariables.getMessageMap().putWarning("document.newMaintainableObj.transferAmount","balanceTransfer.transferSSTO", 
				bt.getTransferAmount().toString(), bt.getAmountTransferred().toString());
	}
	/*
	 * Submit a balance transfer document when deleting a ssto usage leave block from current Leave/time calendar
	 * delete both accrued and usage ssto leave blocks, a pending transferred leave block is created by the BT doc
	 */
	public ActionForward balanceTransferOnSSTO(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		BalanceTransferForm btf = (BalanceTransferForm) form;
		BalanceTransfer bt = btf.getBalanceTransfer();
		
		if(StringUtils.isEmpty(bt.getSstoId())) {
			LOG.error("System Scheduled Time Off not found for this balance transfer!");
			GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, "error.SSTO.nonExist");
			return mapping.findForward("basic");

//			throw new RuntimeException("System Scheduled Time Off not found for this balance transfer!");
		}
		List<LeaveBlock> lbList = LmServiceLocator.getLeaveBlockService().getSSTOLeaveBlocks(bt.getPrincipalId(), bt.getSstoId(), bt.getEffectiveLocalDate());
		if(CollectionUtils.isEmpty(lbList) || (CollectionUtils.isNotEmpty(lbList) && lbList.size() != 2)) {
			LOG.error("There should be 2 system scheduled time off leave blocks!");
			GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, "error.twoSSTOlb.notfound");
			return mapping.findForward("basic");
//			throw new RuntimeException("There should be 2 system scheduled time off leave blocks!");
		}
		LmServiceLocator.getBalanceTransferService().submitToWorkflow(bt);
		// delete both SSTO accrualed and usage leave blocks
		for(LeaveBlock lb : lbList) {
			LmServiceLocator.getLeaveBlockService().deleteLeaveBlock(lb.getLmLeaveBlockId(), lb.getPrincipalId());
		}
		return mapping.findForward("closeBalanceTransferDoc");
	}

}
