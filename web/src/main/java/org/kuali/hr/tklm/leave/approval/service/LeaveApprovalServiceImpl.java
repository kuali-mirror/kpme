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
package org.kuali.hr.tklm.leave.approval.service;

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
import org.kuali.hr.core.accrualcategory.AccrualCategory;
import org.kuali.hr.core.accrualcategory.rule.AccrualCategoryRule;
import org.kuali.hr.core.assignment.Assignment;
import org.kuali.hr.core.calendar.CalendarEntry;
import org.kuali.hr.core.principal.PrincipalHRAttributes;
import org.kuali.hr.core.principal.dao.PrincipalHRAttributesDao;
import org.kuali.hr.core.role.KPMERole;
import org.kuali.hr.core.service.HrServiceLocator;
import org.kuali.hr.tklm.leave.LMConstants;
import org.kuali.hr.tklm.leave.approval.web.ApprovalLeaveSummaryRow;
import org.kuali.hr.tklm.leave.block.LeaveBlock;
import org.kuali.hr.tklm.leave.calendar.validation.LeaveCalendarValidationUtil;
import org.kuali.hr.tklm.leave.summary.LeaveSummary;
import org.kuali.hr.tklm.leave.summary.LeaveSummaryRow;
import org.kuali.hr.tklm.leave.workflow.LeaveCalendarDocumentHeader;
import org.kuali.hr.tklm.time.person.TKPerson;
import org.kuali.hr.tklm.time.service.base.TkServiceLocator;
import org.kuali.hr.tklm.time.util.TkConstants;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.note.Note;

public class LeaveApprovalServiceImpl implements LeaveApprovalService{
	public static final int DAYS_WINDOW_DELTA = 31;
    private PrincipalHRAttributesDao principalHRAttributesDao;

    public void setPrincipalHRAttributesDao(PrincipalHRAttributesDao principalHRAttributesDao) {
        this.principalHRAttributesDao = principalHRAttributesDao;
    }
	
	@Override
	public List<ApprovalLeaveSummaryRow> getLeaveApprovalSummaryRows(List<TKPerson> persons, CalendarEntry payCalendarEntry, List<Date> leaveSummaryDates) {
		DateTime payBeginDate = payCalendarEntry.getBeginPeriodFullDateTime();
		DateTime payEndDate = payCalendarEntry.getEndPeriodFullDateTime();
		List<ApprovalLeaveSummaryRow> rowList = new ArrayList<ApprovalLeaveSummaryRow>();		
		
		for(TKPerson aPerson : persons) {
			String principalId = aPerson.getPrincipalId();
			ApprovalLeaveSummaryRow aRow = new ApprovalLeaveSummaryRow();
            List<Note> notes = new ArrayList<Note>();
//            List<String> warnings = new ArrayList<String>();
			aRow.setName(aPerson.getPrincipalName());
			aRow.setPrincipalId(aPerson.getPrincipalId());
			
			String lastApprovedString = "No previous approved leave calendar information";
			LeaveCalendarDocumentHeader lastApprovedDoc = TkServiceLocator.getLeaveCalendarDocumentHeaderService().getMaxEndDateApprovedLeaveCalendar(principalId);
			if(lastApprovedDoc != null) {
				lastApprovedString = "Last Approved: " + (new SimpleDateFormat("MMM yyyy")).format(lastApprovedDoc.getBeginDate());
            }
			aRow.setLastApproveMessage(lastApprovedString);
			
			LeaveCalendarDocumentHeader aDoc = TkServiceLocator.getLeaveCalendarDocumentHeaderService().getDocumentHeader(principalId, payBeginDate, payEndDate);
			if(aDoc != null) {
				aRow.setDocumentId(aDoc.getDocumentId());
				aRow.setApprovalStatus(TkConstants.DOC_ROUTE_STATUS.get(aDoc.getDocumentStatus()));
                notes = getNotesForDocument(aDoc.getDocumentId());
			}
			List<LeaveCalendarDocumentHeader> docList = TkServiceLocator.getLeaveCalendarDocumentHeaderService().getApprovalDelinquentDocumentHeaders(principalId);
			if(docList.size() > LMConstants.DELINQUENT_LEAVE_CALENDARS_LIMIT ) {
				aRow.setMoreThanOneCalendar(true);
			}
			
			List<LeaveBlock> leaveBlocks = TkServiceLocator.getLeaveBlockService().getLeaveBlocks(principalId, payBeginDate.toLocalDate(), payEndDate.toLocalDate());

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
    		eligibilities = TkServiceLocator.getAccrualCategoryMaxBalanceService().getMaxBalanceViolations(calendarEntry, principalId);
    	} catch (Exception e) {
    		eligibilities = null;
    	}
    	if (eligibilities != null) {
    		for (Entry<String,Set<LeaveBlock>> entry : eligibilities.entrySet()) {
    			for(LeaveBlock block : entry.getValue()) {

    				AccrualCategoryRule rule = HrServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(block.getAccrualCategoryRuleId());
    				if (rule != null) {
    					AccrualCategory accrualCategory = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(rule.getLmAccrualCategoryId());
    					if (rule.getActionAtMaxBalance().equals(LMConstants.ACTION_AT_MAX_BAL.TRANSFER)) {
    						//Todo: add link to balance transfer
    						allMessages.get("warningMessages").add("Accrual Category '" + accrualCategory.getAccrualCategory() + "' is over max balance.");   //warningMessages
    					} else if (rule.getActionAtMaxBalance().equals(LMConstants.ACTION_AT_MAX_BAL.LOSE)) {
    						//Todo: compute and display amount of time lost.
    						allMessages.get("warningMessages").add("Accrual Category '" + accrualCategory.getAccrualCategory() + "' is over max balance.");      //warningMessages
    					} else if (rule.getActionAtMaxBalance().equals(LMConstants.ACTION_AT_MAX_BAL.PAYOUT)){
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
			DateTime leaveDate = new DateTime(lb.getLeaveDate()).toLocalDate().toDateTimeAtStartOfDay();
			
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

	public Map<String, LeaveCalendarDocumentHeader> getLeaveDocumentHeaderMap(List<TKPerson> persons, DateTime payBeginDate, DateTime payEndDate) {
		Map<String, LeaveCalendarDocumentHeader> leaveDocumentHeaderMap = new LinkedHashMap<String, LeaveCalendarDocumentHeader>();
		if (CollectionUtils.isNotEmpty(persons)) {
			for (TKPerson person : persons) {
				String principalId = person.getPrincipalId();
				LeaveCalendarDocumentHeader aHeader = TkServiceLocator.getLeaveCalendarDocumentHeaderService().getDocumentHeader(principalId, payBeginDate, payEndDate);
				if(aHeader != null) {
					leaveDocumentHeaderMap.put(principalId, aHeader);
				}
				
			}
		}
		return leaveDocumentHeaderMap;
	}
	
	@Override
	public List<Map<String, Object>> getLeaveApprovalDetailSections(LeaveCalendarDocumentHeader lcdh)  {
		
		List<Map<String, Object>> acRows = new ArrayList<Map<String, Object>>();
		
		String principalId = lcdh.getPrincipalId();
        CalendarEntry calendarEntry = TkServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(lcdh.getDocumentId()).getCalendarEntry();
		//CalendarEntries calendarEntry = TkServiceLocator.getCalendarEntriesService().getCalendarEntriesByBeginAndEndDate(lcdh.getBeginDate(), lcdh.getEndDate());
		if(calendarEntry != null) {
			DateTime beginDate = calendarEntry.getBeginPeriodFullDateTime();
			DateTime endDate = calendarEntry.getEndPeriodFullDateTime();
			LeaveSummary leaveSummary;
			List<Date> leaveSummaryDates = TkServiceLocator.getLeaveSummaryService().getLeaveSummaryDates(calendarEntry);
            try {
                leaveSummary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(principalId, calendarEntry);
            } catch (Exception e) {
                leaveSummary = null;
            }
            List<LeaveBlock> leaveBlocks = TkServiceLocator.getLeaveBlockService().getLeaveBlocks(principalId, beginDate.toLocalDate(), endDate.toLocalDate());
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
			DateTime leaveDate = new DateTime(lb.getLeaveDate()).toLocalDate().toDateTimeAtStartOfDay();
			
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
        return principalHRAttributesDao.getUniqueLeavePayGroupsForPrincipalIds(principalIds);
    }
	
	@Override
	public List<CalendarEntry> getAllLeavePayCalendarEntriesForApprover(String principalId, LocalDate currentDate) {
		Set<String> principals = new HashSet<String>();

    	Set<Long> workAreas = new HashSet<Long>();
    	workAreas.addAll(TkServiceLocator.getHRRoleService().getWorkAreasForPrincipalInRole(principalId, KPMERole.APPROVER.getRoleName(), new DateTime(), true));
        workAreas.addAll(TkServiceLocator.getHRRoleService().getWorkAreasForPrincipalInRole(principalId, KPMERole.APPROVER_DELEGATE.getRoleName(), new DateTime(), true));

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
			documentHeaders.addAll(TkServiceLocator.getLeaveCalendarDocumentHeaderService().getAllDocumentHeadersForPricipalId(pid));
		}
		Set<CalendarEntry> payPeriodSet = new HashSet<CalendarEntry>();
		for(LeaveCalendarDocumentHeader lcdh : documentHeaders) {
            CalendarEntry pe = TkServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(lcdh.getDocumentId()).getCalendarEntry();
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
		TkServiceLocator.getLeaveApprovalService().removeNonLeaveEmployees(principalIds);

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
	public Map<String, LeaveCalendarDocumentHeader> getPrincipalDocumehtHeader(List<TKPerson> persons, DateTime payBeginDate, DateTime payEndDate) {
		Map<String, LeaveCalendarDocumentHeader> principalDocumentHeader = new LinkedHashMap<String, LeaveCalendarDocumentHeader>();
		for (TKPerson person : persons) {
			String principalId = person.getPrincipalId();
			LeaveCalendarDocumentHeader lcdh = TkServiceLocator.getLeaveCalendarDocumentHeaderService().getDocumentHeader(principalId, payBeginDate, payEndDate);
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
