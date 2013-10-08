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
import java.util.TreeSet;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeFieldType;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.kuali.kpme.core.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.accrualcategory.rule.AccrualCategoryRule;
import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.calendar.Calendar;
import org.kuali.kpme.core.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.principal.PrincipalHRAttributes;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.tklm.common.LMConstants;
import org.kuali.kpme.tklm.common.TkConstants;
import org.kuali.kpme.tklm.leave.approval.web.ApprovalLeaveSummaryRow;
import org.kuali.kpme.tklm.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.leave.calendar.LeaveCalendarDocument;
import org.kuali.kpme.tklm.leave.calendar.validation.LeaveCalendarValidationUtil;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.leave.summary.LeaveSummary;
import org.kuali.kpme.tklm.leave.summary.LeaveSummaryRow;
import org.kuali.kpme.tklm.leave.workflow.LeaveCalendarDocumentHeader;
import org.kuali.kpme.tklm.time.timesummary.TimeSummary;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.action.ActionRequest;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.kew.api.note.Note;
import org.kuali.rice.kim.api.identity.principal.EntityNamePrincipalName;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

public class LeaveApprovalServiceImpl implements LeaveApprovalService {
	
	public static final int DAYS_WINDOW_DELTA = 31;

	@Override
	public List<ApprovalLeaveSummaryRow> getLeaveApprovalSummaryRows(List<String> principalIds, CalendarEntry payCalendarEntry, List<Date> leaveSummaryDates, String docIdSearchTerm) {
		DateTime payBeginDate = payCalendarEntry.getBeginPeriodFullDateTime();
		DateTime payEndDate = payCalendarEntry.getEndPeriodFullDateTime();
		List<ApprovalLeaveSummaryRow> rowList = new ArrayList<ApprovalLeaveSummaryRow>();		
		for(String principalId : principalIds) {
			
			ApprovalLeaveSummaryRow aRow = new ApprovalLeaveSummaryRow();
            List<Note> notes = new ArrayList<Note>();
//            List<String> warnings = new ArrayList<String>();
            EntityNamePrincipalName name = KimApiServiceLocator.getIdentityService().getDefaultNamesForPrincipalId(principalId);
            aRow.setName(name != null
                           && name.getDefaultName() != null
                           && name.getDefaultName().getCompositeName() != null ? name.getDefaultName().getCompositeName() : principalId);
			aRow.setPrincipalId(principalId);
			
			String lastApprovedString = "No previous approved leave calendar information";
			LeaveCalendarDocumentHeader lastApprovedDoc = LmServiceLocator.getLeaveCalendarDocumentHeaderService().getMaxEndDateApprovedLeaveCalendar(principalId);
			if(lastApprovedDoc != null) {
				lastApprovedString = "Last Approved: " + (new SimpleDateFormat("MMM yyyy")).format(lastApprovedDoc.getBeginDate());
            }
			aRow.setLastApproveMessage(lastApprovedString);
			
			LeaveCalendarDocumentHeader aDoc = LmServiceLocator.getLeaveCalendarDocumentHeaderService().getDocumentHeader(principalId, payBeginDate, payEndDate);
			if(aDoc != null) {
				if(StringUtils.isNotBlank(docIdSearchTerm) && !aDoc.getDocumentId().contains(docIdSearchTerm)) {
					continue;	// if the leave document of this pricipalId does not match the docIdSearchTerm, move on to the next principalId
				}
				aRow.setDocumentId(aDoc.getDocumentId());
				aRow.setApprovalStatus(HrConstants.DOC_ROUTE_STATUS.get(aDoc.getDocumentStatus()));
                notes = getNotesForDocument(aDoc.getDocumentId());
                if (StringUtils.isNotBlank(aRow.getDocumentId())) {
                    List<ActionRequest> actionRequests = KewApiServiceLocator.getWorkflowDocumentService().getPendingActionRequests(aRow.getDocumentId());
                    Map<String, String> roleNames = new HashMap<String, String>();
                    for (ActionRequest ar : actionRequests) {
                        roleNames.put(ar.getPrincipalId(), ar.getQualifiedRoleNameLabel());
                    }
                    aRow.setRoleNames(roleNames);
                }
			}
			List<LeaveCalendarDocumentHeader> docList = LmServiceLocator.getLeaveCalendarDocumentHeaderService().getApprovalDelinquentDocumentHeaders(principalId);
			if(docList.size() > LMConstants.DELINQUENT_LEAVE_CALENDARS_LIMIT ) {
				aRow.setMoreThanOneCalendar(true);
			}
			
	        // if the time of the last day of the pay period is 00:00 or 12:00 am, we should subtract a day from the calendar's end date
	        // since the leave blocks from that day should go into next pay period
			LocalDate endDate = payEndDate.toLocalDate();
	        if(payEndDate.getHourOfDay() == 0) {
	        	endDate = endDate.plusDays(-1);
	        }
			List<LeaveBlock> leaveBlocks = LmServiceLocator.getLeaveBlockService().getLeaveBlocks(principalId, payBeginDate.toLocalDate(), endDate);
			if(aDoc != null ){
				Map<Integer, String> weeklyDistribution = getWeekHeadersForSummary(principalId,aDoc,payCalendarEntry,aRow.getWeekDates(), aRow.getWeekDateList(), aRow.getDetailMap(), aRow.getEnableWeekDetails());
				aRow.setWeeklyDistribution(weeklyDistribution);
			}else {
											
				if (payCalendarEntry != null) {
					try{
						LeaveCalendarDocument leaveCalendarDocument = null;	
					if (LmServiceLocator.getLeaveCalendarService().shouldCreateLeaveDocument(principalId, payCalendarEntry)) {
						leaveCalendarDocument = LmServiceLocator.getLeaveCalendarService().openLeaveCalendarDocument(principalId, payCalendarEntry);
					} else {
						 aDoc = LmServiceLocator.getLeaveCalendarDocumentHeaderService().getDocumentHeader(principalId, payCalendarEntry.getBeginPeriodFullDateTime(), payCalendarEntry.getEndPeriodFullDateTime());
						if (aDoc != null) {
							leaveCalendarDocument = LmServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(aDoc.getDocumentId());
						}
					}
					
					} catch (WorkflowException e) {
			    		e.printStackTrace();
			    	}
		    	}	
				
				 Map<Integer, String> weeklyDistribution = getWeekHeadersForSummary(principalId,aDoc,payCalendarEntry,aRow.getWeekDates(), aRow.getWeekDateList(), aRow.getDetailMap(), aRow.getEnableWeekDetails());
				 aRow.setWeeklyDistribution(weeklyDistribution);
				
			}
			
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
	private Calendar getPayCalendarForEntry(CalendarEntry calEntry) {
        Calendar cal = null;

        if (calEntry != null) {
            cal = HrServiceLocator.getCalendarService().getCalendar(calEntry.getHrCalendarId());
        }

        return cal;
    }

	private Map<Integer, String> getWeekHeadersForSummary(String principalId,LeaveCalendarDocumentHeader lcdh,CalendarEntry cal, Map<String, String> weekDates, Map<String, Set<Date>> weekDateList, Map<String,List<Map<String, Object>>> weekDetailMap, Map<String,Boolean> enableWeekDetails) {
        
		Map<Integer, String> header = new LinkedHashMap<Integer,String>();                
        header.put(DateTimeConstants.SUNDAY, "Sun");
        header.put(DateTimeConstants.MONDAY, "Mon");
        header.put(DateTimeConstants.TUESDAY, "Tue");
        header.put(DateTimeConstants.WEDNESDAY, "Wed");
        header.put(DateTimeConstants.THURSDAY, "Thu");
        header.put(DateTimeConstants.FRIDAY, "Fri");
        header.put(DateTimeConstants.SATURDAY, "Sat");
        
        int flsaBeginDay = this.getPayCalendarForEntry(cal).getFlsaBeginDayConstant();
        if(flsaBeginDay <= 0) {
        	flsaBeginDay = DateTimeConstants.SUNDAY;
        }
        LocalDateTime startDate = cal.getBeginPeriodLocalDateTime();
        LocalDateTime endDate = cal.getEndPeriodLocalDateTime();

        LocalDateTime actualStartDate = cal.getBeginPeriodLocalDateTime();
        LocalDateTime actualEndDate = cal.getEndPeriodLocalDateTime();
        // if the time of the last day of the pay period is 00:00 or 12:00 am, we should subtract a day from the calendar's end date
        // since the leave blocks from that day should go into next pay periodd
        if(cal.getEndPeriodFullDateTime().getHourOfDay() == 0) {
        	endDate = endDate.plusDays(-1);
        }
        
        int daysToMinus = 0;
        if(DateTimeConstants.SUNDAY != startDate.getDayOfWeek()) {
        	daysToMinus = startDate.getDayOfWeek();
        }
        
        actualStartDate = startDate.minusDays(daysToMinus);
        int daysToAdd = 0;
        if(endDate.getDayOfWeek() != DateTimeConstants.SUNDAY) {
        	daysToAdd = DateTimeConstants.SATURDAY - endDate.getDayOfWeek();
        } else {
        	daysToAdd = DateTimeConstants.SATURDAY;
        }
        
        actualEndDate = endDate.plusDays(daysToAdd);
        
        // Increment end date if we are on a virtual day calendar, so that the
        // for loop can account for having the proper amount of days on the
        // summary calendar.
        if (endDate.get(DateTimeFieldType.hourOfDay()) != 0 || endDate.get(DateTimeFieldType.minuteOfHour()) != 0 ||
                endDate.get(DateTimeFieldType.secondOfMinute()) != 0)
        {
        	actualEndDate = endDate.plusDays(1);
        }

        boolean afterFirstDay = false;
        int week = 1;
        
        LocalDateTime weekStart = actualStartDate;
        LocalDateTime weekEnd = actualStartDate;
        Set<Date> dates = new TreeSet<Date>();
        for (LocalDateTime currentDate = actualStartDate; currentDate.compareTo(actualEndDate) < 0; currentDate = currentDate.plusDays(1)) {
        	
            if (currentDate.getDayOfWeek() == flsaBeginDay && afterFirstDay) {
            	String weekString = "Week " + week;
                StringBuilder display = new StringBuilder();
                // show the week's range within the calendar period
                String startDateString = weekStart.isBefore(startDate) ? 
                		startDate.toString(TkConstants.DT_ABBREV_DATE_FORMAT) : weekStart.toString(TkConstants.DT_ABBREV_DATE_FORMAT);
                display.append(startDateString);
                display.append(" - ");
                String endDateString = weekEnd.minusDays(1).isAfter(endDate) ? 
                		endDate.toString(TkConstants.DT_ABBREV_DATE_FORMAT) : weekEnd.minusDays(1).toString(TkConstants.DT_ABBREV_DATE_FORMAT);
                display.append(endDateString);
                weekDates.put(weekString, display.toString());
                weekDateList.put(weekString, dates);
                dates = new TreeSet<Date>();
                dates.add(currentDate.toDate());
                weekStart = currentDate;
                week++;

            } else {
            	dates.add(currentDate.toDate());
            }

            weekEnd = weekEnd.plusDays(1);
            afterFirstDay = true;
        }

        // We may have a very small final "week" on this pay period. For now
        // we will mark it as a week, and if someone doesn't like it, it can
        // be removed.
        if (!header.isEmpty() && !header.get(header.size() - 1).startsWith("Week")) {
        	if(weekStart.compareTo(endDate) < 0) {
	        	StringBuilder display = new StringBuilder();
	        	// show the week's range within the calendar period
	        	String startDateString = weekStart.isBefore(startDate) ? 
                		startDate.toString(TkConstants.DT_ABBREV_DATE_FORMAT) : weekStart.toString(TkConstants.DT_ABBREV_DATE_FORMAT);
                display.append(startDateString);
	            display.append(" - ");
	            String endDateString = actualEndDate.isAfter(endDate) ? 
                		endDate.toString(TkConstants.DT_ABBREV_DATE_FORMAT) : actualEndDate.toString(TkConstants.DT_ABBREV_DATE_FORMAT);
                display.append(endDateString);
	            weekDates.put("Week "+week, display.toString());
	            dates.add(actualEndDate.toDate());
	            weekDateList.put("Week "+week, dates);
        	}
            
        }       
        
        // get Accrual category map for week
        int cnt = 1;
        for(String key : weekDateList.keySet()) {
        	
        	Set<Date> dateList = weekDateList.get(key);
        	Date[] datesArray = new Date[dateList.size()];
        	datesArray = dateList.toArray(datesArray);
        	LocalDateTime sd = new LocalDateTime(datesArray[0].getTime());
        	LocalDateTime ed = new LocalDateTime(datesArray[datesArray.length -1].getTime());
        	if(cnt == 1) {
        		sd = startDate;
        	}
        	if (cnt == weekDateList.size()) {
        		ed = endDate;
        	}
	        List<Map<String, Object>> detailMap = this.getLeaveApprovalDetailSections(principalId, lcdh,sd.toDateTime(), ed.toDateTime(), new ArrayList<Date>(dateList), key, enableWeekDetails);
	        weekDetailMap.put(key, detailMap);
	        cnt++;
        }
               
        return header;
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
		
		if (lcdh == null) {
			return acRows;
		}
		
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

	public List<Map<String, Object>> getLeaveApprovalDetailSections(String principalId, LeaveCalendarDocumentHeader lcdh,DateTime beginDate, DateTime endDate, List<Date> leaveSummaryDates, String week, Map<String,Boolean> enableWeekDetails)  {
		List<Map<String, Object>> acRows = new ArrayList<Map<String, Object>>();
		
		 
        //CalendarEntry calendarEntry = LmServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(lcdh.getDocumentId()).getCalendarEntry();
		//CalendarEntries calendarEntry = TkServiceLocator.getCalendarEntriesService().getCalendarEntriesByBeginAndEndDate(lcdh.getBeginDate(), lcdh.getEndDate());
			LeaveSummary leaveSummary;
			PrincipalHRAttributes pha; 
//			List<Date> leaveSummaryDates = LmServiceLocator.getLeaveSummaryService().getLeaveSummaryDates(calendarEntry);
            try {
                leaveSummary = LmServiceLocator.getLeaveSummaryService().getLeaveSummaryAsOfDate(principalId, endDate.toLocalDate());
                pha = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, endDate.toLocalDate());
            } catch (Exception e) {
                leaveSummary = null;
                pha=null;
            }
            List<LeaveBlock> leaveBlocks = LmServiceLocator.getLeaveBlockService().getLeaveBlocks(principalId, beginDate.toLocalDate(), endDate.toLocalDate());
			Map<Date, Map<String, BigDecimal>> accrualCategoryLeaveHours = getAccrualCategoryLeaveHours(leaveBlocks, leaveSummaryDates);
			//get all accrual categories of this employee
			
			if(pha != null) {
				List<AccrualCategory> acList = HrServiceLocator.getAccrualCategoryService().getActiveAccrualCategoriesForLeavePlan(pha.getLeavePlan(), endDate.toLocalDate());
				for(AccrualCategory ac : acList) {
					List<BigDecimal> acDayDetails = new ArrayList<BigDecimal>();
					Map<String, Object> displayMap = new HashMap<String, Object>();
					BigDecimal totalAmount = BigDecimal.ZERO;
					displayMap.put("accrualCategory", ac.getAccrualCategory());
					int index = 0;
					for (Date leaveSummaryDate : leaveSummaryDates) {
						if(leaveSummaryDate.compareTo(beginDate.toDate()) >= 0 && leaveSummaryDate.compareTo(endDate.toDate())<=0) {
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
					}
                    LeaveSummaryRow lsr = leaveSummary == null ? null : leaveSummary.getLeaveSummaryRowForAccrualCtgy(ac.getAccrualCategory());
                    if(!totalAmount.equals(BigDecimal.ZERO)) {                    	
                    	enableWeekDetails.put(week, Boolean.TRUE);
                    	
                    }
					displayMap.put("periodUsage", totalAmount);
					displayMap.put("availableBalance", BigDecimal.ZERO);
                    displayMap.put("availableBalance", lsr == null ? BigDecimal.ZERO : lsr.getLeaveBalance());
					displayMap.put("daysDetail", acDayDetails);
					displayMap.put("daysSize", acDayDetails.size());
					acRows.add(displayMap);
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
