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
package org.kuali.hr.lm.leave.approval.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.accrual.AccrualCategoryRule;
import org.kuali.hr.lm.balancetransfer.BalanceTransfer;
import org.kuali.hr.lm.leaveSummary.LeaveSummary;
import org.kuali.hr.lm.leaveSummary.LeaveSummaryRow;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leavecalendar.validation.LeaveCalendarValidationUtil;
import org.kuali.hr.lm.leavepayout.LeavePayout;
import org.kuali.hr.lm.workflow.LeaveCalendarDocumentHeader;
import org.kuali.hr.time.approval.web.ApprovalLeaveSummaryRow;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.person.TKPerson;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.principal.dao.PrincipalHRAttributesDao;
import org.kuali.hr.time.roles.TkUserRoles;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.note.Note;
import org.kuali.rice.krad.util.GlobalVariables;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class LeaveApprovalServiceImpl implements LeaveApprovalService{
	public static final int DAYS_WINDOW_DELTA = 31;
    private PrincipalHRAttributesDao principalHRAttributesDao;

    public void setPrincipalHRAttributesDao(PrincipalHRAttributesDao principalHRAttributesDao) {
        this.principalHRAttributesDao = principalHRAttributesDao;
    }
	
	@Override
	public List<ApprovalLeaveSummaryRow> getLeaveApprovalSummaryRows(List<TKPerson> persons, CalendarEntries payCalendarEntries, List<Date> leaveSummaryDates) {
		Date payBeginDate = payCalendarEntries.getBeginPeriodDate();
		Date payEndDate = payCalendarEntries.getEndPeriodDate();
		List<ApprovalLeaveSummaryRow> rowList = new ArrayList<ApprovalLeaveSummaryRow>();		
		
		for(TKPerson aPerson : persons) {
			String principalId = aPerson.getPrincipalId();
			ApprovalLeaveSummaryRow aRow = new ApprovalLeaveSummaryRow();
            List<Note> notes = new ArrayList<Note>();
//            List<String> warnings = new ArrayList<String>();
            Map<String, Set<String>> allMessages = new HashMap<String, Set<String>>();
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
			
			List<LeaveBlock> leaveBlocks = TkServiceLocator.getLeaveBlockService().getLeaveBlocks(principalId, payBeginDate, payEndDate);
			allMessages = findWarnings(aDoc, payCalendarEntries, leaveBlocks);
			allMessages.putAll(findTransactionsWithinPeriod(aDoc, payCalendarEntries));
            aRow.setLeaveBlockList(leaveBlocks);
			Map<Date, Map<String, BigDecimal>> earnCodeLeaveHours = getEarnCodeLeaveHours(leaveBlocks, leaveSummaryDates);
			aRow.setEarnCodeLeaveHours(earnCodeLeaveHours);
            aRow.setNotes(notes);

            Set<String> msgs = allMessages.get("warningMessages");
            List<String> warningMessages = new ArrayList<String>();
            warningMessages.addAll(msgs);
            warningMessages.addAll(allMessages.get("infoMessages"));
            warningMessages.addAll(allMessages.get("actionMessages"));

            aRow.setWarnings(warningMessages); //these are only warning messages.
			
			rowList.add(aRow);
		}
		
		return rowList;
	}

    private Map<String,Set<String>> findTransactionsWithinPeriod(LeaveCalendarDocumentHeader aDoc,
			CalendarEntries payCalendarEntries) {
		Map<String,Set<String>> allMessages = new HashMap<String,Set<String>>();
		
		allMessages.put("actionMessages", new HashSet<String>());
		allMessages.put("infoMessages", new HashSet<String>());
		allMessages.put("warningMessages", new HashSet<String>());
		if(aDoc != null) {
			List<BalanceTransfer> transfers = TkServiceLocator.getBalanceTransferService().getBalanceTransfers(aDoc.getPrincipalId(), payCalendarEntries.getBeginPeriodDate(), payCalendarEntries.getEndPeriodDate());
	        for(BalanceTransfer transfer : transfers) {
	        	if(StringUtils.equals(transfer.getStatus(), TkConstants.ROUTE_STATUS.ENROUTE)) {
	        		allMessages.get("actionMessages").add("A pending balance transfer exists on this calendar. It must be finalized before this calendar can be approved");
	        	}
	    		if(StringUtils.equals(transfer.getStatus() ,TkConstants.ROUTE_STATUS.FINAL)) {
	    			if(StringUtils.isEmpty(transfer.getSstoId())) {
		            	if(transfer.getTransferAmount().compareTo(BigDecimal.ZERO) == 0 && transfer.getAmountTransferred().compareTo(BigDecimal.ZERO) == 0) {
		            		if(transfer.getForfeitedAmount() != null && transfer.getForfeitedAmount().signum() != 0)
		            			allMessages.get("infoMessages").add("A transfer action that forfeited leave occured on this calendar");
		            	}
		            	else
		           			allMessages.get("infoMessages").add("A transfer action occurred on this calendar");
	    			}
	    			else
	        			allMessages.get("infoMessages").add("System scheduled time off was transferred on this calendar");
	    		}
	    		if(StringUtils.equals(transfer.getStatus() ,TkConstants.ROUTE_STATUS.DISAPPROVED)) {
	    			if(StringUtils.isEmpty(transfer.getSstoId())) {
	    	        	if(transfer.getTransferAmount().compareTo(BigDecimal.ZERO) == 0 && transfer.getAmountTransferred().compareTo(BigDecimal.ZERO) == 0) {
	    	        		if(transfer.getForfeitedAmount() != null && transfer.getForfeitedAmount().signum() != 0)
	    	        			allMessages.get("infoMessages").add("A transfer action that forfeited leave occured on this calendar");
	    	        	}
	    	        	else
	    	       			allMessages.get("infoMessages").add("A transfer action occurred on this calendar");
	    			}
	    		}
	        }
	        List<LeavePayout> payouts = TkServiceLocator.getLeavePayoutService().getLeavePayouts(aDoc.getPrincipalId(), payCalendarEntries.getBeginPeriodDate(), payCalendarEntries.getEndPeriodDate());
	        for(LeavePayout payout : payouts) {
	        	if(StringUtils.equals(payout.getStatus(), TkConstants.ROUTE_STATUS.ENROUTE)) {
	        		allMessages.get("actionMessages").add("A pending payout exists on this calendar. It must be finalized before this calendar can be approved");
	        	}
	    		if(StringUtils.equals(payout.getStatus() ,TkConstants.ROUTE_STATUS.FINAL)) {
	            	if(payout.getPayoutAmount().compareTo(BigDecimal.ZERO) == 0) {
	            		if(payout.getForfeitedAmount() != null && payout.getForfeitedAmount().signum() != 0)
	            			allMessages.get("infoMessages").add("A payout action that forfeited leave occured on this calendar");
	            	}
	            	else
	           			allMessages.get("infoMessages").add("A payout action occurred on this calendar");
	    		}
	    		if(StringUtils.equals(payout.getStatus() ,TkConstants.ROUTE_STATUS.DISAPPROVED)) {
    	        	if(payout.getPayoutAmount().compareTo(BigDecimal.ZERO) == 0) {
    	        		if(payout.getForfeitedAmount() != null && payout.getForfeitedAmount().signum() != 0)
    	        			allMessages.get("infoMessages").add("A disapproved payout that forfeited leave occured on this calendar");
    	        	}
    	        	else
    	       			allMessages.get("infoMessages").add("A disapproved payout occurred on this calendar");
	    		}
	        }
		}
		return allMessages;
	}

	private Map<String, Set<String>> findWarnings(LeaveCalendarDocumentHeader doc, CalendarEntries calendarEntry, List<LeaveBlock> leaveBlocks) {
//        List<String> warnings = LeaveCalendarValidationUtil.getWarningMessagesForLeaveBlocks(leaveBlocks);
        Map<String, Set<String>> allMessages= LeaveCalendarValidationUtil.getWarningMessagesForLeaveBlocks(leaveBlocks);
        //get LeaveSummary and check for warnings
        if (doc != null) {
            Map<String, ArrayList<String>> eligibilities;
            try {
        	 eligibilities = TkServiceLocator.getBalanceTransferService().getEligibleTransfers(calendarEntry, doc.getPrincipalId());
            } catch (Exception e) {
            	eligibilities = null;
            }           
            if (eligibilities != null) {
                for (Entry<String,ArrayList<String>> entry : eligibilities.entrySet()) {
                	for(String accrualRuleId : entry.getValue()) {
	                    AccrualCategoryRule rule = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(accrualRuleId);
	                    if (rule != null) {
	                    	String frequency = "";
	                    	if(StringUtils.equals(LMConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND,"OD"))
	                    		frequency = "on-demand";
                    		else
                    			frequency = "on leave approval";
	                    	
	                    	AccrualCategory accrualCategory = rule.getAccrualCategoryObj();
	                        if (rule.getActionAtMaxBalance().equals(LMConstants.ACTION_AT_MAX_BAL.TRANSFER)) {
	                            //Todo: add link to balance transfer
	                            allMessages.get("warningMessages").add("Accrual Category '" + accrualCategory + "' is over max balance. Transfer is set to occur " + frequency);   //warningMessages
	                        } else if (rule.getActionAtMaxBalance().equals(LMConstants.ACTION_AT_MAX_BAL.LOSE)) {
	                            //Todo: compute and display amount of time lost.
	                            allMessages.get("warningMessages").add("Accrual Category '" + accrualCategory + "' is over max balance. Excess leave will be lost " + frequency);      //warningMessages
	                        } else if (rule.getActionAtMaxBalance().equals(LMConstants.ACTION_AT_MAX_BAL.PAYOUT)){
	                        	allMessages.get("warningMessages").add("Accrual category '" + accrualCategory + "' is over max balance. Payout is set to occur " + frequency);
	                        }
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
                String key = lb.getEarnCode() + "|" + lb.getRequestStatus();
				if (leaveHours.get(lb.getEarnCode()) != null) {
					amount = leaveHours.get(key).add(lb.getLeaveAmount());
				}
				
				leaveHours.put(key, amount);
			}
		}
		
		return earnCodeLeaveHours;
	}

	public Map<String, LeaveCalendarDocumentHeader> getLeaveDocumentHeaderMap(List<TKPerson> persons, Date payBeginDate, Date payEndDate) {
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
		CalendarEntries calendarEntry = TkServiceLocator.getCalendarEntriesService().getCalendarEntriesByBeginAndEndDate(lcdh.getBeginDate(), lcdh.getEndDate());
		if(calendarEntry != null) {
			Date beginDate = calendarEntry.getBeginPeriodDate();
			Date endDate = calendarEntry.getEndPeriodDate();
			LeaveSummary leaveSummary;
			List<Date> leaveSummaryDates = TkServiceLocator.getLeaveSummaryService().getLeaveSummaryDates(calendarEntry);
            try {
                leaveSummary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(principalId, calendarEntry);
            } catch (Exception e) {
                leaveSummary = null;
            }
            List<LeaveBlock> leaveBlocks = TkServiceLocator.getLeaveBlockService().getLeaveBlocks(principalId, beginDate, endDate);
			Map<Date, Map<String, BigDecimal>> accrualCategoryLeaveHours = getAccrualCategoryLeaveHours(leaveBlocks, leaveSummaryDates);

			//get all accrual categories of this employee
			PrincipalHRAttributes pha = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, endDate);
			if(pha != null) {
				List<AccrualCategory> acList = TkServiceLocator.getAccrualCategoryService().getActiveAccrualCategoriesForLeavePlan(pha.getLeavePlan(), new java.sql.Date(endDate.getTime()));
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
			
			AccrualCategory ac = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(lb.getAccrualCategory(), lb.getLeaveDate());
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
	public List<CalendarEntries> getAllLeavePayCalendarEntriesForApprover(String principalId, Date currentDate) {
		TKUser tkUser = TKContext.getUser();
		Set<String> principals = new HashSet<String>();
		DateTime minDt = new DateTime(currentDate,
				TKUtils.getSystemDateTimeZone());
		minDt = minDt.minusDays(DAYS_WINDOW_DELTA);
		Set<Long> approverWorkAreas = TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId()).getApproverWorkAreas();

		// Get all of the principals within our window of time.
		for (Long waNum : approverWorkAreas) {
			List<Assignment> assignments = TkServiceLocator
					.getAssignmentService().getActiveAssignmentsForWorkArea(waNum, TKUtils.getTimelessDate(currentDate));

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
		Set<CalendarEntries> payPeriodSet = new HashSet<CalendarEntries>();
		for(LeaveCalendarDocumentHeader lcdh : documentHeaders) {
    		CalendarEntries pe = TkServiceLocator.getCalendarEntriesService().getCalendarEntriesByBeginAndEndDate(lcdh.getBeginDate(), lcdh.getEndDate());
    		if(pe != null) {
    			payPeriodSet.add(pe);
    		}
        }
		List<CalendarEntries> ppList = new ArrayList<CalendarEntries>(payPeriodSet);
        
		return ppList;
	}
	@Override
	public void removeNonLeaveEmployees(List<String> principalIds) {
		if(CollectionUtils.isNotEmpty(principalIds)) {
			java.sql.Date asOfDate = TKUtils.getTimelessDate(null);
			List<String> idList = new ArrayList<String>();
			idList.addAll(principalIds);
	     	for(String principalId: idList) {
	     		boolean leaveFlag = false;
	     		List<Assignment> activeAssignments = TkServiceLocator.getAssignmentService().getAssignments(principalId, asOfDate);
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
	public List<String> getLeavePrincipalIdsWithSearchCriteria(List<String> workAreaList, String calendarGroup, java.sql.Date effdt, java.sql.Date beginDate, java.sql.Date endDate) {
		if (CollectionUtils.isEmpty(workAreaList)) {
	      return new ArrayList<String>();
	    }
		
		List<String> principalIds = TkServiceLocator.getAssignmentService().getPrincipalIds(workAreaList, effdt, beginDate, endDate);
		TkServiceLocator.getLeaveApprovalService().removeNonLeaveEmployees(principalIds);

		if(CollectionUtils.isEmpty(principalIds)) {
		return new ArrayList<String>();
		}
		// use unique principalIds and selected calendarGroup to get unique ids from principalHRAttributes table
		List<String> idList = CollectionUtils.isEmpty(principalIds) ? 
			new ArrayList<String> () 
			: TkServiceLocator.getPrincipalHRAttributeService()
				.getActiveEmployeesIdForLeaveCalendarAndIdList(calendarGroup, principalIds, endDate); 
		
		return idList;
	}	

	@Override
	public Map<String, LeaveCalendarDocumentHeader> getPrincipalDocumehtHeader(List<TKPerson> persons, Date payBeginDate, Date payEndDate) {
		Map<String, LeaveCalendarDocumentHeader> principalDocumentHeader = new LinkedHashMap<String, LeaveCalendarDocumentHeader>();
		for (TKPerson person : persons) {
			String principalId = person.getPrincipalId();
			LeaveCalendarDocumentHeader lcdh = TkServiceLocator.getLeaveCalendarDocumentHeaderService().getDocumentHeader(principalId, payBeginDate, DateUtils.addMilliseconds(payEndDate, 1));
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
		java.sql.Date asOfDate = TKUtils.getTimelessDate(null);
		List<Assignment> activeAssignments = TkServiceLocator
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
