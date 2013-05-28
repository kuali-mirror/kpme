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
package org.kuali.kpme.tklm.leave.approval.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.accrualcategory.rule.AccrualCategoryRule;
import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.principal.PrincipalHRAttributes;
import org.kuali.kpme.core.principal.service.PrincipalHRAttributesService;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.tklm.common.LMConstants;
import org.kuali.kpme.tklm.leave.approval.web.ApprovalLeaveSummaryRow;
import org.kuali.kpme.tklm.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.leave.calendar.validation.LeaveCalendarValidationUtil;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.leave.summary.LeaveSummary;
import org.kuali.kpme.tklm.leave.summary.LeaveSummaryRow;
import org.kuali.kpme.tklm.leave.workflow.LeaveCalendarDocumentHeader;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.note.Note;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

public class LeaveApprovalServiceImpl implements LeaveApprovalService{
	public static final int DAYS_WINDOW_DELTA = 31;
    private PrincipalHRAttributesService principalHRAttributesService;

    public void setPrincipalHRAttributesService(PrincipalHRAttributesService principalHRAttributesService) {
        this.principalHRAttributesService = principalHRAttributesService;
    }

	@Override
	public List<ApprovalLeaveSummaryRow> getLeaveApprovalSummaryRows(List<String> principalIds, CalendarEntry payCalendarEntry, List<Date> leaveSummaryDates) {
		DateTime payBeginDate = payCalendarEntry.getBeginPeriodFullDateTime();
		DateTime payEndDate = payCalendarEntry.getEndPeriodFullDateTime();
		List<ApprovalLeaveSummaryRow> rowList = new ArrayList<ApprovalLeaveSummaryRow>();		

		for(String principalId : principalIds) {
			
			ApprovalLeaveSummaryRow aRow = new ApprovalLeaveSummaryRow();
            List<Note> notes = new ArrayList<Note>();
//            List<String> warnings = new ArrayList<String>();
            Principal principal = KimApiServiceLocator.getIdentityService().getPrincipal(principalId);
			aRow.setName(principal.getPrincipalName());
			aRow.setPrincipalId(principalId);
			
			String lastApprovedString = "No previous approved leave calendar information";
			LeaveCalendarDocumentHeader lastApprovedDoc = LmServiceLocator.getLeaveCalendarDocumentHeaderService().getMaxEndDateApprovedLeaveCalendar(principalId);
			if(lastApprovedDoc != null) {
				lastApprovedString = "Last Approved: " + (new SimpleDateFormat("MMM yyyy")).format(lastApprovedDoc.getBeginDate());
            }
			aRow.setLastApproveMessage(lastApprovedString);
			
			LeaveCalendarDocumentHeader aDoc = LmServiceLocator.getLeaveCalendarDocumentHeaderService().getDocumentHeader(principalId, payBeginDate, payEndDate);
			if(aDoc != null) {
				aRow.setDocumentId(aDoc.getDocumentId());
				aRow.setApprovalStatus(HrConstants.DOC_ROUTE_STATUS.get(aDoc.getDocumentStatus()));
                notes = getNotesForDocument(aDoc.getDocumentId());
			}
			List<LeaveCalendarDocumentHeader> docList = LmServiceLocator.getLeaveCalendarDocumentHeaderService().getApprovalDelinquentDocumentHeaders(principalId);
			if(docList.size() > LMConstants.DELINQUENT_LEAVE_CALENDARS_LIMIT ) {
				aRow.setMoreThanOneCalendar(true);
			}
			
			List<LeaveBlock> leaveBlocks = LmServiceLocator.getLeaveBlockService().getLeaveBlocks(principalId, payBeginDate.toLocalDate(), payEndDate.toLocalDate());

            aRow.setLeaveBlockList(leaveBlocks);
			Map<Date, Map<String, BigDecimal>> earnCodeLeaveHours = getEarnCodeLeaveHours(leaveBlocks, leaveSummaryDates);
			aRow.setEarnCodeLeaveHours(earnCodeLeaveHours);
            aRow.setNotes(notes);

            Map<String, Set<String>> allMessages = findWarnings(principalId, payCalendarEntry, leaveBlocks);
			
			Map<String,Set<String>> transactionalMessages = findTransactionsWithinPeriod(aDoc, payCalendarEntry);
			
			allMessages.get("infoMessages").addAll(transactionalMessages.get("infoMessages"));
			allMessages.get("warningMessages").addAll(transactionalMessages.get("warningMessages"));
			allMessages.get("actionMessages").addAll(transactionalMessages.get("actionMessages"));

            List<String> warningMessages = new ArrayList<String>();
            warningMessages.addAll(allMessages.get("warningMessages"));
            warningMessages.addAll(allMessages.get("infoMessages"));
            warningMessages.addAll(allMessages.get("actionMessages"));

            aRow.setWarnings(warningMessages); //these are only warning messages.

			rowList.add(aRow);
		
		}
		
		return rowList;
	}

    private Map<String,Set<String>> findTransactionsWithinPeriod(LeaveCalendarDocumentHeader aDoc,
			CalendarEntry payCalendarEntry) {
		Map<String,Set<String>> allMessages = new HashMap<String,Set<String>>();
		
		allMessages.put("actionMessages", new HashSet<String>());
		allMessages.put("infoMessages", new HashSet<String>());
		allMessages.put("warningMessages", new HashSet<String>());
		if(aDoc != null) {
			allMessages = LeaveCalendarValidationUtil.validatePendingTransactions(aDoc.getPrincipalId(), payCalendarEntry.getBeginPeriodFullDateTime().toLocalDate(), payCalendarEntry.getEndPeriodFullDateTime().toLocalDate());
		}
		return allMessages;
	}

	private Map<String, Set<String>> findWarnings(String principalId, CalendarEntry calendarEntry, List<LeaveBlock> leaveBlocks) {
//        List<String> warnings = LeaveCalendarValidationUtil.getWarningMessagesForLeaveBlocks(leaveBlocks);
        Map<String, Set<String>> allMessages= LeaveCalendarValidationUtil.getWarningMessagesForLeaveBlocks(leaveBlocks);
        //get LeaveSummary and check for warnings
    	Map<String, Set<LeaveBlock>> eligibilities;
    	try {
    		eligibilities = LmServiceLocator.getAccrualCategoryMaxBalanceService().getMaxBalanceViolations(calendarEntry, principalId);
    	} catch (Exception e) {
    		eligibilities = null;
    	}
    	if (eligibilities != null) {
    		for (Entry<String,Set<LeaveBlock>> entry : eligibilities.entrySet()) {
    			for(LeaveBlock block : entry.getValue()) {

    				AccrualCategoryRule rule = HrServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(block.getAccrualCategoryRuleId());
    				if (rule != null) {
    					AccrualCategory accrualCategory = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(rule.getLmAccrualCategoryId());
    					if (rule.getActionAtMaxBalance().equals(HrConstants.ACTION_AT_MAX_BALANCE.TRANSFER)) {
    						//Todo: add link to balance transfer
    						allMessages.get("warningMessages").add("Accrual Category '" + accrualCategory.getAccrualCategory() + "' is over max balance.");   //warningMessages
    					} else if (rule.getActionAtMaxBalance().equals(HrConstants.ACTION_AT_MAX_BALANCE.LOSE)) {
    						//Todo: compute and display amount of time lost.
    						allMessages.get("warningMessages").add("Accrual Category '" + accrualCategory.getAccrualCategory() + "' is over max balance.");      //warningMessages
    					} else if (rule.getActionAtMaxBalance().equals(HrConstants.ACTION_AT_MAX_BALANCE.PAYOUT)){
    						//Todo: display information about the payout
    						allMessages.get("warningMessages").add("Accrual Category '" + accrualCategory.getAccrualCategory() + "' is over max balance.");      //warningMessages

    					}

    				}
    			}
    		}
    	}    
        return allMessages;
    }
	
	@Override
	public Map<Date, Map<String, BigDecimal>> getEarnCodeLeaveHours(List<LeaveBlock> leaveBlocks, List<Date> leaveSummaryDates) {
		Map<Date, Map<String, BigDecimal>> earnCodeLeaveHours = new LinkedHashMap<Date, Map<String, BigDecimal>>();
		
		for (Date leaveSummaryDate : leaveSummaryDates) {
			earnCodeLeaveHours.put(leaveSummaryDate, new LinkedHashMap<String, BigDecimal>());
		}
		
		for (LeaveBlock lb : leaveBlocks) {
			DateTime leaveDate = lb.getLeaveLocalDate().toDateTimeAtStartOfDay();
			
			if (earnCodeLeaveHours.get(leaveDate.toDate()) != null) {
				Map<String, BigDecimal> leaveHours = earnCodeLeaveHours.get(leaveDate.toDate());

				BigDecimal amount = lb.getLeaveAmount();
                String key = lb.getEarnCode() + "|" + lb.getRequestStatus() + "|" + lb.getLeaveBlockType();
				if (leaveHours.get(key) != null) {
					amount = leaveHours.get(key).add(lb.getLeaveAmount());
				}
				
				leaveHours.put(key, amount);
			}
		}
		
		return earnCodeLeaveHours;
	}
	
	@Override
	public List<Map<String, Object>> getLeaveApprovalDetailSections(LeaveCalendarDocumentHeader lcdh)  {
		
		List<Map<String, Object>> acRows = new ArrayList<Map<String, Object>>();
		
		String principalId = lcdh.getPrincipalId();
        CalendarEntry calendarEntry = LmServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(lcdh.getDocumentId()).getCalendarEntry();
		//CalendarEntries calendarEntry = TkServiceLocator.getCalendarEntriesService().getCalendarEntriesByBeginAndEndDate(lcdh.getBeginDate(), lcdh.getEndDate());
		if(calendarEntry != null) {
			DateTime beginDate = calendarEntry.getBeginPeriodFullDateTime();
			DateTime endDate = calendarEntry.getEndPeriodFullDateTime();
			LeaveSummary leaveSummary;
			List<Date> leaveSummaryDates = LmServiceLocator.getLeaveSummaryService().getLeaveSummaryDates(calendarEntry);
            try {
                leaveSummary = LmServiceLocator.getLeaveSummaryService().getLeaveSummary(principalId, calendarEntry);
            } catch (Exception e) {
                leaveSummary = null;
            }
            List<LeaveBlock> leaveBlocks = LmServiceLocator.getLeaveBlockService().getLeaveBlocks(principalId, beginDate.toLocalDate(), endDate.toLocalDate());
			Map<Date, Map<String, BigDecimal>> accrualCategoryLeaveHours = getAccrualCategoryLeaveHours(leaveBlocks, leaveSummaryDates);

			//get all accrual categories of this employee
			PrincipalHRAttributes pha = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, endDate.toLocalDate());
			if(pha != null) {
				List<AccrualCategory> acList = HrServiceLocator.getAccrualCategoryService().getActiveAccrualCategoriesForLeavePlan(pha.getLeavePlan(), endDate.toLocalDate());
				for(AccrualCategory ac : acList) {
					List<BigDecimal> acDayDetails = new ArrayList<BigDecimal>();
					Map<String, Object> displayMap = new HashMap<String, Object>();
					BigDecimal totalAmount = BigDecimal.ZERO;
					displayMap.put("accrualCategory", ac.getAccrualCategory());
					int index = 0;
					for (Date leaveSummaryDate : leaveSummaryDates) {
						acDayDetails.add(index, null);
						if (accrualCategoryLeaveHours.get(leaveSummaryDate) != null) {
							Map<String, BigDecimal> leaveHours = accrualCategoryLeaveHours.get(leaveSummaryDate);
							if (leaveHours.containsKey(ac.getAccrualCategory())) {
								BigDecimal amount =  leaveHours.get(ac.getAccrualCategory());
								totalAmount = totalAmount.add(amount);
								acDayDetails.set(index, amount);
							}
						}
						index++;
					}
                    LeaveSummaryRow lsr = leaveSummary == null ? null : leaveSummary.getLeaveSummaryRowForAccrualCtgy(ac.getAccrualCategory());
					displayMap.put("periodUsage", totalAmount);
					displayMap.put("availableBalance", BigDecimal.ZERO);
                    displayMap.put("availableBalance", lsr == null ? BigDecimal.ZERO : lsr.getLeaveBalance());
					displayMap.put("daysDetail", acDayDetails);
					displayMap.put("daysSize", acDayDetails.size());
					acRows.add(displayMap);
				}
			}
			
		}
		return acRows;
	}

    @Override
    public List<Note> getNotesForDocument(String documentNumber) {
        return KewApiServiceLocator.getNoteService().getNotes(documentNumber);
    }

	@Override
	public Map<Date, Map<String, BigDecimal>> getAccrualCategoryLeaveHours(List<LeaveBlock> leaveBlocks, List<Date> leaveSummaryDates) {
		Map<Date, Map<String, BigDecimal>> accrualCategoryLeaveHours = new LinkedHashMap<Date, Map<String, BigDecimal>>();
		
		for (Date leaveSummaryDate : leaveSummaryDates) {
			accrualCategoryLeaveHours.put(leaveSummaryDate, new LinkedHashMap<String, BigDecimal>());
		}
		
		for (LeaveBlock lb : leaveBlocks) {
			DateTime leaveDate = lb.getLeaveLocalDate().toDateTimeAtStartOfDay();
			
			AccrualCategory ac = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(lb.getAccrualCategory(), lb.getLeaveLocalDate());
			if (ac != null && ac.getShowOnGrid().equals("Y")) {
				if (accrualCategoryLeaveHours.get(leaveDate.toDate()) != null) {
					Map<String, BigDecimal> leaveHours = accrualCategoryLeaveHours.get(leaveDate.toDate());
					
					BigDecimal amount = lb.getLeaveAmount();
					if (leaveHours.get(ac.getAccrualCategory()) != null) {
						amount = leaveHours.get(ac.getAccrualCategory()).add(lb.getLeaveAmount());
					}
					
					leaveHours.put(ac.getAccrualCategory(), amount);
				}
			}
		}
		
		return accrualCategoryLeaveHours;
	}

    @Override
    public List<String> getUniqueLeavePayGroupsForPrincipalIds(List<String> principalIds) {
        return principalHRAttributesService.getUniqueLeavePayGroupsForPrincipalIds(principalIds);
    }
	
	@Override
	public List<CalendarEntry> getAllLeavePayCalendarEntriesForApprover(String principalId, LocalDate currentDate) {
		Set<String> principals = new HashSet<String>();

    	Set<Long> workAreas = new HashSet<Long>();
    	workAreas.addAll(HrServiceLocator.getHRRoleService().getWorkAreasForPrincipalInRole(principalId, KPMERole.APPROVER.getRoleName(), new DateTime(), true));
        workAreas.addAll(HrServiceLocator.getHRRoleService().getWorkAreasForPrincipalInRole(principalId, KPMERole.APPROVER_DELEGATE.getRoleName(), new DateTime(), true));

		// Get all of the principals within our window of time.
		for (Long waNum : workAreas) {
			List<Assignment> assignments = HrServiceLocator
					.getAssignmentService().getActiveAssignmentsForWorkArea(waNum, currentDate);

			if (assignments != null) {
				for (Assignment assignment : assignments) {
					principals.add(assignment.getPrincipalId());
				}
			}
		}
		List<LeaveCalendarDocumentHeader> documentHeaders = new ArrayList<LeaveCalendarDocumentHeader>();
		for(String pid : principals) {
			documentHeaders.addAll(LmServiceLocator.getLeaveCalendarDocumentHeaderService().getAllDocumentHeadersForPricipalId(pid));
		}
		Set<CalendarEntry> payPeriodSet = new HashSet<CalendarEntry>();
		for(LeaveCalendarDocumentHeader lcdh : documentHeaders) {
            CalendarEntry pe = LmServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(lcdh.getDocumentId()).getCalendarEntry();
    		if(pe != null) {
    			payPeriodSet.add(pe);
    		}
        }
		List<CalendarEntry> ppList = new ArrayList<CalendarEntry>(payPeriodSet);
        
		return ppList;
	}
	@Override
	public void removeNonLeaveEmployees(List<String> principalIds) {
		if(CollectionUtils.isNotEmpty(principalIds)) {
			LocalDate asOfDate = LocalDate.now();
			List<String> idList = new ArrayList<String>();
			idList.addAll(principalIds);
	     	for(String principalId: idList) {
	     		boolean leaveFlag = false;
	     		List<Assignment> activeAssignments = HrServiceLocator.getAssignmentService().getAssignments(principalId, asOfDate);
	     		if(CollectionUtils.isNotEmpty(activeAssignments)) {
	         		for(Assignment assignment : activeAssignments) {
	         			if(assignment != null && assignment.getJob() != null && assignment.getJob().isEligibleForLeave()) {
	         				leaveFlag = true;
	         				break;
	         			}
	         		}
	         		if(!leaveFlag) {  // employee is not eligible for leave, remove the id from principalIds
	         			principalIds.remove(principalId);
	         		}
	         	}
	     	}
		}
	}
	
	@Override
	public List<String> getLeavePrincipalIdsWithSearchCriteria(List<String> workAreaList, String calendarGroup, LocalDate effdt, LocalDate beginDate, LocalDate endDate) {
		if (CollectionUtils.isEmpty(workAreaList)) {
	      return new ArrayList<String>();
	    }
		
		List<String> principalIds = HrServiceLocator.getAssignmentService().getPrincipalIds(workAreaList, effdt, beginDate, endDate);
		LmServiceLocator.getLeaveApprovalService().removeNonLeaveEmployees(principalIds);

		if(CollectionUtils.isEmpty(principalIds)) {
		return new ArrayList<String>();
		}
		// use unique principalIds and selected calendarGroup to get unique ids from principalHRAttributes table
		List<String> idList = CollectionUtils.isEmpty(principalIds) ? 
			new ArrayList<String> () 
			: HrServiceLocator.getPrincipalHRAttributeService()
				.getActiveEmployeesIdForLeaveCalendarAndIdList(calendarGroup, principalIds, endDate); 
		
		return idList;
	}	

	@Override
	public Map<String, LeaveCalendarDocumentHeader> getPrincipalDocumentHeader(List<String> principalIds, DateTime payBeginDate, DateTime payEndDate) {
		Map<String, LeaveCalendarDocumentHeader> principalDocumentHeader = new LinkedHashMap<String, LeaveCalendarDocumentHeader>();
		for (String principalId : principalIds) {
			LeaveCalendarDocumentHeader lcdh = LmServiceLocator.getLeaveCalendarDocumentHeaderService().getDocumentHeader(principalId, payBeginDate, payEndDate);
			if(lcdh != null) {
				principalDocumentHeader.put(principalId, lcdh);	
			}
		}

		return principalDocumentHeader;
	}

	@Override
	public boolean isActiveAssignmentFoundOnJobFlsaStatus(String principalId,
			String flsaStatus, boolean chkForLeaveEligible) {
		boolean isActiveAssFound = false;
		LocalDate asOfDate = LocalDate.now();
		List<Assignment> activeAssignments = HrServiceLocator
				.getAssignmentService().getAssignments(principalId, asOfDate);
		if (activeAssignments != null && !activeAssignments.isEmpty()) {
			for (Assignment assignment : activeAssignments) {
				if (assignment != null
						&& assignment.getJob() != null
						&& assignment.getJob().getFlsaStatus() != null
						&& assignment.getJob().getFlsaStatus()
								.equalsIgnoreCase(flsaStatus)) {
					if (chkForLeaveEligible) {
						isActiveAssFound = assignment.getJob()
								.isEligibleForLeave();
						if (!isActiveAssFound) {
							continue;
						}
					}
					isActiveAssFound = true;
					break;
				}
			}
		}
		return isActiveAssFound;
    }
}
