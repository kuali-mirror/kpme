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
package org.kuali.hr.tklm.leave.calendar.validation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeZone;
import org.joda.time.Hours;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.kuali.hr.core.HrConstants;
import org.kuali.hr.core.bo.accrualcategory.AccrualCategory;
import org.kuali.hr.core.bo.assignment.Assignment;
import org.kuali.hr.core.bo.assignment.AssignmentDescriptionKey;
import org.kuali.hr.core.bo.calendar.entry.CalendarEntry;
import org.kuali.hr.core.bo.earncode.EarnCode;
import org.kuali.hr.core.bo.earncode.group.EarnCodeGroup;
import org.kuali.hr.core.service.HrServiceLocator;
import org.kuali.hr.tklm.common.TKContext;
import org.kuali.hr.tklm.common.TKUtils;
import org.kuali.hr.tklm.common.TkConstants;
import org.kuali.hr.tklm.leave.block.LeaveBlock;
import org.kuali.hr.tklm.leave.calendar.LeaveCalendarDocument;
import org.kuali.hr.tklm.leave.override.EmployeeOverride;
import org.kuali.hr.tklm.leave.service.LmServiceLocator;
import org.kuali.hr.tklm.leave.summary.LeaveSummary;
import org.kuali.hr.tklm.leave.summary.LeaveSummaryRow;
import org.kuali.hr.tklm.leave.util.LMConstants;
import org.kuali.hr.tklm.leave.web.LeaveCalendarWSForm;
import org.kuali.hr.tklm.time.service.TkServiceLocator;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.document.DocumentStatus;

public class LeaveCalendarValidationUtil {
    
    //begin KPME-1263
    public static List<String> validateLeaveAccrualRuleMaxUsage(LeaveCalendarWSForm lcf) {
    	LeaveBlock updatedLeaveBlock = null;
    	if(lcf.getLeaveBlockId() != null) {
    		updatedLeaveBlock = LmServiceLocator.getLeaveBlockService().getLeaveBlock(lcf.getLeaveBlockId());
    	}
    	return validateLeaveAccrualRuleMaxUsage(lcf.getLeaveSummary(), lcf.getSelectedEarnCode(), lcf.getStartDate(),
    			lcf.getEndDate(), lcf.getLeaveAmount(), updatedLeaveBlock);
    }

	public static List<String> validateLeaveAccrualRuleMaxUsage(LeaveSummary ls, String selectedEarnCode, String leaveStartDateString,
			String leaveEndDateString, BigDecimal leaveAmount, LeaveBlock updatedLeaveBlock) {
    	List<String> errors = new ArrayList<String>();
        String principalId = TKContext.getTargetPrincipalId();
    	long daysSpan = TKUtils.getDaysBetween(TKUtils.formatDateString(leaveStartDateString), TKUtils.formatDateString(leaveEndDateString));
    	if(leaveAmount == null) {
    		leaveAmount  = TKUtils.getHoursBetween(TKUtils.formatDateString(leaveStartDateString).toDate().getTime(), TKUtils.formatDateString(leaveEndDateString).toDate().getTime());
    	}
    	if(ls != null && CollectionUtils.isNotEmpty(ls.getLeaveSummaryRows())) {
	    	BigDecimal oldLeaveAmount = null;
	    	boolean earnCodeChanged = false;
    		if(updatedLeaveBlock != null) {
    			if(!updatedLeaveBlock.getEarnCode().equals(selectedEarnCode)) {
    				earnCodeChanged = true;
    			}
    			if(!updatedLeaveBlock.getLeaveAmount().equals(leaveAmount)) {
    				oldLeaveAmount = updatedLeaveBlock.getLeaveAmount();
    			}
    		}
    		LocalDate aDate = TKUtils.formatDateString(leaveEndDateString);
	    	EarnCode earnCodeObj = HrServiceLocator.getEarnCodeService().getEarnCode(selectedEarnCode, aDate);
	    	if(earnCodeObj != null) {
	    		AccrualCategory accrualCategory = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(earnCodeObj.getAccrualCategory(), aDate);
	    		if(accrualCategory != null) {
	    			List<LeaveSummaryRow> rows = ls.getLeaveSummaryRows();
	    			for(LeaveSummaryRow aRow : rows) {
	    				if(aRow.getAccrualCategory().equals(accrualCategory.getAccrualCategory())) {
	    					//Does employee have overrides in place?
	    					List<EmployeeOverride> employeeOverrides = LmServiceLocator.getEmployeeOverrideService().getEmployeeOverrides(principalId,TKUtils.formatDateString(leaveEndDateString));
	    					String leavePlan = accrualCategory.getLeavePlan();
	    					BigDecimal maxUsage = aRow.getUsageLimit();
	    					for(EmployeeOverride eo : employeeOverrides) {
	    						if(eo.getLeavePlan().equals(leavePlan) && eo.getAccrualCategory().equals(aRow.getAccrualCategory())) {
	    							if(eo.getOverrideType().equals("MU") && eo.isActive()) {
	    								if(eo.getOverrideValue()!=null) {
	    									maxUsage = new BigDecimal(eo.getOverrideValue());
                                        } else { // no limit flag
	    									maxUsage = null;
                                        }
	    							}
	    						}
	    					}
	    					BigDecimal ytdUsage = aRow.getYtdApprovedUsage();
	    					BigDecimal pendingLeaveBalance = aRow.getPendingLeaveRequests();
	    					BigDecimal desiredUsage = new BigDecimal(0);
	    					if(pendingLeaveBalance!=null) {
	    						if(oldLeaveAmount!=null) {
	    							
	    							if(!earnCodeChanged || 
	    									updatedLeaveBlock.getAccrualCategory().equals(accrualCategory.getAccrualCategory())) {
   			    						pendingLeaveBalance = pendingLeaveBalance.subtract(oldLeaveAmount.abs());
	    							}
	    						}
 
    							desiredUsage = desiredUsage.add(pendingLeaveBalance);
	    					}
   							
	    					desiredUsage = desiredUsage.add(leaveAmount.multiply(new BigDecimal(daysSpan+1)));

   							if(ytdUsage!=null) {
    							desiredUsage = desiredUsage.add(ytdUsage);
    						}
	    					if(maxUsage!=null) {
		    					if(desiredUsage.compareTo(maxUsage) > 0 ) {
		    						errors.add("This leave request would exceed the usage limit for " + aRow.getAccrualCategory());                        //errorMessages
		    					}
	    					}
	    				}
	    			}
	    		}
	    	}
    	}
    	return errors;
    }
	//End KPME-1263

	//TODO: Move to WarningService
	public static Map<String, Set<String>> validatePendingTransactions(String principalId, LocalDate fromDate, LocalDate toDate) {
		Map<String, Set<String>> allMessages = new HashMap<String, Set<String>>();
		
        Set<String> actionMessages = new HashSet<String>();
        Set<String> infoMessages = new HashSet<String>();
        Set<String> warningMessages = new HashSet<String>();
        
        List<LeaveBlock> leaveBlocks = LmServiceLocator.getLeaveBlockService().getLeaveBlocksWithType(principalId, fromDate, toDate, LMConstants.LEAVE_BLOCK_TYPE.BALANCE_TRANSFER);
        Set<String> workflowDocIds = new HashSet<String>();
        for(LeaveBlock lb : leaveBlocks) {
        	if(lb.getTransactionalDocId() != null) {
        		workflowDocIds.add(lb.getTransactionalDocId());
            } else {
        		if(StringUtils.contains(lb.getDescription(), "Forfeited balance transfer amount")) {
        			infoMessages.add("A max balance action that forfeited accrued leave occurred on this calendar");
                }
            }
        }
        for(String workflowDocId : workflowDocIds) {
            DocumentStatus status = KewApiServiceLocator.getWorkflowDocumentService().getDocumentStatus(workflowDocId);
            
            if(StringUtils.equals(status.getCode(), HrConstants.ROUTE_STATUS.FINAL)) {
            	infoMessages.add("A transfer action occurred on this calendar");
            }
            else if(StringUtils.equals(status.getCode(), HrConstants.ROUTE_STATUS.ENROUTE)) {
            	actionMessages.add("A pending balance transfer exists on this calendar. It must be finalized before this calendar can be approved");
            }
            else {
            	warningMessages.add("A balance transfer document exists for this calendar with status neither final nor enroute");
            }
        }
        
        leaveBlocks = LmServiceLocator.getLeaveBlockService().getLeaveBlocksWithType(principalId, fromDate, toDate, LMConstants.LEAVE_BLOCK_TYPE.LEAVE_PAYOUT);
        workflowDocIds = new HashSet<String>();
        for(LeaveBlock lb : leaveBlocks) {
        	if(lb.getTransactionalDocId() != null) {
        		workflowDocIds.add(lb.getTransactionalDocId());
            }
        }
        for(String workflowDocId : workflowDocIds) {
            DocumentStatus status = KewApiServiceLocator.getWorkflowDocumentService().getDocumentStatus(workflowDocId);

            if(StringUtils.equals(status.getCode(), HrConstants.ROUTE_STATUS.FINAL)) {
            	infoMessages.add("A payout action occurred on this calendar");
            }
            else if(StringUtils.equals(status.getCode(), HrConstants.ROUTE_STATUS.ENROUTE)) {
            	actionMessages.add("A pending payout exists on this calendar. It must be finalized before this calendar can be approved");
            }
            else {
            	warningMessages.add("A payout document exists for this calendar with status neither final or enroute");
            }
        }
        allMessages.put("actionMessages", actionMessages);
        allMessages.put("infoMessages", infoMessages);
        allMessages.put("warningMessages", warningMessages);
        
        return allMessages;
	}
	
    // get warning messages associated with earn codes of leave blocks
    public static Map<String, Set<String>> getWarningMessagesForLeaveBlocks(List<LeaveBlock> leaveBlocks) {
//        List<String> warningMessages = new ArrayList<String>();
        Map<String, Set<String>> allMessages = new HashMap<String, Set<String>>();
        
        Set<String> actionMessages = new HashSet<String>();
        Set<String> infoMessages = new HashSet<String>();
        Set<String> warningMessages = new HashSet<String>();

        if (CollectionUtils.isNotEmpty(leaveBlocks)) {
            for(LeaveBlock lb : leaveBlocks) {
                EarnCode ec = HrServiceLocator.getEarnCodeService().getEarnCode(lb.getEarnCode(), lb.getLeaveLocalDate());
                if(ec != null) {
                    EarnCodeGroup eg = HrServiceLocator.getEarnCodeGroupService().getEarnCodeGroupForEarnCode(lb.getEarnCode(), lb.getLeaveLocalDate());
                    if(eg != null && !StringUtils.isEmpty(eg.getWarningText())) {
                        warningMessages.add(eg.getWarningText());
                    }
                }
            }
        }
        allMessages.put("actionMessages", actionMessages);
        allMessages.put("infoMessages", infoMessages);
        allMessages.put("warningMessages", warningMessages);

//        warningMessages.addAll(aSet);
        return allMessages;
    }

    public static List<String> validateAvailableLeaveBalance(LeaveCalendarWSForm lcf) {
    	LeaveBlock updatedLeaveBlock = null;
    	if(lcf.getLeaveBlockId() != null) {
			updatedLeaveBlock = LmServiceLocator.getLeaveBlockService().getLeaveBlock(lcf.getLeaveBlockId());
    	}
    	return validateAvailableLeaveBalanceForUsage(lcf.getSelectedEarnCode(), lcf.getStartDate(), lcf.getEndDate(), lcf.getLeaveAmount(), updatedLeaveBlock);
    }

    public static List<String> validateAvailableLeaveBalanceForUsage(String earnCode, String leaveStartDateString, String leaveEndDateString,
    		BigDecimal leaveAmount, LeaveBlock updatedLeaveBlock) {
    	List<String> errors = new ArrayList<String>();
    	boolean earnCodeChanged = false;
    	BigDecimal oldAmount = null;
    	
    	if(leaveAmount == null) {
    		leaveAmount  = TKUtils.getHoursBetween(TKUtils.formatDateString(leaveStartDateString).toDate().getTime(), TKUtils.formatDateString(leaveEndDateString).toDate().getTime());
    	}
		if(updatedLeaveBlock != null) {
			if(!updatedLeaveBlock.getEarnCode().equals(earnCode)) {
				earnCodeChanged = true;
			}
			if(!updatedLeaveBlock.getLeaveAmount().equals(leaveAmount)) {
				oldAmount = updatedLeaveBlock.getLeaveAmount();
			}
		}
		LocalDate startDate = TKUtils.formatDateString(leaveStartDateString);
		LocalDate endDate = TKUtils.formatDateString(leaveEndDateString);
		long daysSpan = TKUtils.getDaysBetween(startDate,endDate);
    	EarnCode earnCodeObj = HrServiceLocator.getEarnCodeService().getEarnCode(earnCode, endDate);
    	if(earnCodeObj != null && earnCodeObj.getAllowNegativeAccrualBalance().equals("N")) {
    		AccrualCategory accrualCategory = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(earnCodeObj.getAccrualCategory(), endDate);
    		if(accrualCategory != null) {
    			LocalDate nextIntervalDate = LmServiceLocator.getAccrualService().getNextAccrualIntervalDate(accrualCategory.getAccrualEarnInterval(), endDate);
				// get the usage checking cut off Date, normally it's the day before the next interval date
    			LocalDate usageEndDate = nextIntervalDate;
				if (nextIntervalDate.compareTo(endDate) > 0) {
					usageEndDate = nextIntervalDate.minusDays(1);
				}
				// use the end of the year as the interval date for usage checking of no-accrual hours,
				// normally no-accrual hours are from banked/transferred system scheduled time offs
				if(accrualCategory.getAccrualEarnInterval().equals(LMConstants.ACCRUAL_EARN_INTERVAL_CODE.NO_ACCRUAL)) {
					usageEndDate = endDate.withMonthOfYear(DateTimeConstants.DECEMBER).withDayOfMonth(31);
				}
				BigDecimal availableBalance = LmServiceLocator.getLeaveSummaryService()
							.getLeaveBalanceForAccrCatUpToDate(TKContext.getTargetPrincipalId(), startDate, endDate, accrualCategory.getAccrualCategory(), usageEndDate);

				if(oldAmount!=null) {
					if(!earnCodeChanged ||
							updatedLeaveBlock.getAccrualCategory().equals(accrualCategory.getAccrualCategory())) {
						availableBalance = availableBalance.add(oldAmount.abs());
					}
				}
				//multiply by days in span in case the user has also edited the start/end dates.
				BigDecimal desiredUsage =null;
				if(!TkConstants.EARN_CODE_TIME.equals(earnCodeObj.getRecordMethod())) {
					desiredUsage = leaveAmount.multiply(new BigDecimal(daysSpan+1));
				} else {
					desiredUsage = leaveAmount.multiply(new BigDecimal(daysSpan));
				}
				
				if(desiredUsage.compareTo(availableBalance) >  0 ) {
					errors.add("Requested leave amount " + desiredUsage.toString() + " is greater than available leave balance " + availableBalance.toString());      //errorMessages
				}
    		}
    	}
    
    	return errors;
    }
    
    public static List<String> validateDates(String startDateS, String endDateS) {
        List<String> errors = new ArrayList<String>();
        if (errors.size() == 0 && StringUtils.isEmpty(startDateS)) errors.add("The start date is blank.");
        if (errors.size() == 0 && StringUtils.isEmpty(endDateS)) errors.add("The end date is blank.");
        return errors;
    }

    public static List<String> validateTimes(String startTimeS, String endTimeS) {
        List<String> errors = new ArrayList<String>();
        if (errors.size() == 0 && startTimeS == null) errors.add("The start time is blank.");
        if (errors.size() == 0 && endTimeS == null) errors.add("The end time is blank.");
        return errors;
    }

    
//    public static List<String> validateAvailableLeaveBalance(LeaveSummary ls, String earnCode, String leaveStartDateString, String leaveEndDateString,
//    		BigDecimal leaveAmount, LeaveBlock updatedLeaveBlock) {
//    	List<String> errors = new ArrayList<String>();
//    	CalendarEntry calendarEntries = new CalendarEntry();
//    	boolean earnCodeChanged = false;
//    	BigDecimal oldAmount = null;
//    	if(ls != null && CollectionUtils.isNotEmpty(ls.getLeaveSummaryRows())) {
//    		if(updatedLeaveBlock != null) {
//    			if(!updatedLeaveBlock.getEarnCode().equals(earnCode)) {
//    				earnCodeChanged = true;
//    			}
//    			if(!updatedLeaveBlock.getLeaveAmount().equals(leaveAmount)) {
//    				oldAmount = updatedLeaveBlock.getLeaveAmount();
//    			}
//    		}
//			Date startDate = TKUtils.formatDateString(leaveStartDateString);
//			Date endDate = TKUtils.formatDateString(leaveEndDateString);
//			long daysSpan = TKUtils.getDaysBetween(startDate,endDate);
//	    	EarnCode earnCodeObj = HrServiceLocator.getEarnCodeService().getEarnCode(earnCode, endDate);
//	    	if(earnCodeObj != null && earnCodeObj.getAllowNegativeAccrualBalance().equals("N")) {
//	    		AccrualCategory accrualCategory = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(earnCodeObj.getAccrualCategory(), endDate);
//	    		if(accrualCategory != null) {
//	    			LeaveSummaryRow validationRow = ls.getLeaveSummaryRowForAccrualCategory(accrualCategory.getLmAccrualCategoryId());
//    				if(ObjectUtils.isNotNull(validationRow)) {
//    					BigDecimal availableBalance = validationRow.getLeaveBalance();
//    					LeaveSummary ytdSummary = LmServiceLocator.getLeaveSummaryService().getLeaveSummaryAsOfDateForAccrualCategory(TKContext.getTargetPrincipalId(), startDate, accrualCategory.getAccrualCategory());
//    					if(ytdSummary != null) {
//    						LeaveSummaryRow ytdSummaryRow = ytdSummary.getLeaveSummaryRowForAccrualCategory(accrualCategory.getLmAccrualCategoryId());
//    						if(ytdSummaryRow != null)
//    							availableBalance = ytdSummaryRow.getLeaveBalance();
//    					}
//
//    					if(oldAmount!=null) {
//
//	    					if(!earnCodeChanged ||
//	    							updatedLeaveBlock.getAccrualCategory().equals(accrualCategory.getAccrualCategory())) {
//								availableBalance = availableBalance.add(oldAmount.abs());
//	    					}
//
//						}
//						//multiply by days in span in case the user has also edited the start/end dates.
//    					BigDecimal desiredUsage = leaveAmount.multiply(new BigDecimal(daysSpan+1));
//
//    					if(desiredUsage.compareTo(availableBalance) >  0 ) {
//    						errors.add("Requested leave amount is greater than available leave balance.");      //errorMessages
//    					}
//    				}
//	    		}
//	    	}
//    	}
//    	
//    	return errors;
//    }
    

    // KPME-2010
    public static List<String> validateSpanningWeeks(LeaveCalendarWSForm lcf) {
    	boolean spanningWeeks = lcf.getSpanningWeeks().equalsIgnoreCase("y");
        LocalDate startDate = TKUtils.formatDateString(lcf.getStartDate());
        EarnCode ec = HrServiceLocator.getEarnCodeService().getEarnCode(lcf.getSelectedEarnCode(), startDate);
        DateTime startTemp, endTemp;

        if (ec != null && !ec.getRecordMethod().equals(LMConstants.RECORD_METHOD.TIME)) {
            startTemp = new DateTime(startDate);
            endTemp = new DateTime(TKUtils.formatDateString(lcf.getEndDate()));
        } else {
    	    startTemp = TKUtils.formatDateTimeString(lcf.getStartDate());
            endTemp = TKUtils.formatDateTimeString(lcf.getEndDate());
        }
    	
        List<String> errors = new ArrayList<String>();
    	boolean valid = true;
    	while ((startTemp.isBefore(endTemp) || startTemp.isEqual(endTemp)) && valid) {
           	if (!spanningWeeks && 
        		(startTemp.getDayOfWeek() == DateTimeConstants.SATURDAY || startTemp.getDayOfWeek() == DateTimeConstants.SUNDAY)) {
        		valid = false;
        	}
        	startTemp = startTemp.plusDays(1);
        }
        if (!valid) {
        	errors.add("Weekend day is selected, but include weekends checkbox is not checked");            //errorMessages
        }
    	return errors;
    }
    
    public static List<String> validateParametersAccordingToSelectedEarnCodeRecordMethod(LeaveCalendarWSForm lcf) {
    	return validateParametersForLeaveEntry(lcf.getSelectedEarnCode(), lcf.getCalendarEntry(), lcf.getStartDate(), lcf.getEndDate(), lcf.getStartTime(), lcf.getEndTime(), lcf.getSelectedAssignment(), lcf.getLeaveCalendarDocument(), lcf.getLeaveBlockId());
    }
    
    public static List<String> validateParametersForLeaveEntry(String selectedEarnCode, CalendarEntry leaveCalEntry, String startDateS, String endDateS, String startTimeS, String endTimeS, String selectedAssignment, LeaveCalendarDocument leaveCalendarDocument, String leaveBlockId) {
    	List<String> errors = new ArrayList<String>();
    	if (StringUtils.isNotBlank(selectedEarnCode)) {
    		EarnCode  earnCode = HrServiceLocator.getEarnCodeService().getEarnCode(selectedEarnCode, leaveCalEntry.getEndPeriodFullDateTime().toLocalDate());
	    	
    		if(earnCode != null && earnCode.getRecordMethod().equalsIgnoreCase(TkConstants.EARN_CODE_TIME)) {
    			
		    	errors.addAll(LeaveCalendarValidationUtil.validateDates(startDateS, endDateS));
		        errors.addAll(LeaveCalendarValidationUtil.validateTimes(startTimeS, endTimeS));
		        if (errors.size() > 0) return errors;
		
		        Long startTime;
		        Long endTime;
		       
		        startTime = TKUtils.convertDateStringToTimestampWithoutZone(startDateS, startTimeS).getTime();
		        endTime = TKUtils.convertDateStringToTimestampWithoutZone(endDateS, endTimeS).getTime();
		
		        errors.addAll(validateInterval(leaveCalEntry, startTime, endTime));
		        if (errors.size() > 0) return errors;
		        
		        if (startTimeS == null) errors.add("The start time is blank.");
		        if (endTimeS == null) errors.add("The end time is blank.");
		        if (startTime - endTime == 0) errors.add("Start time and end time cannot be equivalent");
		        
		        if (errors.size() > 0) return errors;
		
		        DateTime startTemp = new DateTime(startTime);
		        DateTime endTemp = new DateTime(endTime);
		
		        if (errors.size() == 0) {
		            Hours hrs = Hours.hoursBetween(startTemp, endTemp);
		            if (hrs.getHours() >= 24) errors.add("One leaveblock cannot exceed 24 hours");
		        }
		        if (errors.size() > 0) return errors;
		        
		        //Check that assignment is valid for both days
		        AssignmentDescriptionKey assignKey = HrServiceLocator.getAssignmentService().getAssignmentDescriptionKey(selectedAssignment);
		        Assignment assign = HrServiceLocator.getAssignmentService().getAssignment(assignKey, startTemp.toLocalDate());
		        
		        if ((startTime.compareTo(endTime) > 0 || endTime.compareTo(startTime) < 0)) {
		            errors.add("The time or date is not valid.");
		        }
		        if (errors.size() > 0) return errors;
		        
//		        boolean isRegularEarnCode = StringUtils.equals(assign.getJob().getPayTypeObj().getRegEarnCode(),selectedEarnCode);
		        boolean isRegularEarnCode = true;
	        	errors.addAll(validateOverlap(startTime, endTime, startDateS, endTimeS,startTemp, endTemp, leaveCalEntry, leaveBlockId, isRegularEarnCode, earnCode.getRecordMethod()));
		        if (errors.size() > 0) return errors;
    		}
	    }
        return errors;
    }
    
    public static List<String> validateInterval(CalendarEntry payCalEntry, Long startTime, Long endTime) {
        List<String> errors = new ArrayList<String>();
        LocalDateTime pcb_ldt = payCalEntry.getBeginPeriodLocalDateTime();
        LocalDateTime pce_ldt = payCalEntry.getEndPeriodLocalDateTime();
        DateTimeZone utz = TkServiceLocator.getTimezoneService().getUserTimezoneWithFallback();
        DateTime p_cal_b_dt = pcb_ldt.toDateTime(utz);
        DateTime p_cal_e_dt = pce_ldt.toDateTime(utz);

        Interval payInterval = new Interval(p_cal_b_dt, p_cal_e_dt);
        if (errors.size() == 0 && !payInterval.contains(startTime)) {
            errors.add("The start date/time is outside the pay period");
        }
        if (errors.size() == 0 && !payInterval.contains(endTime) && p_cal_e_dt.getMillis() != endTime) {
            errors.add("The end date/time is outside the pay period");
        }
        return errors;
    }
    
    public static List<String> validateOverlap(Long startTime, Long endTime, String startDateS, String endTimeS, DateTime startTemp, DateTime endTemp, CalendarEntry calendarEntry, String lmLeaveBlockId, boolean isRegularEarnCode, String earnCodeType) {
        List<String> errors = new ArrayList<String>();
        Interval addedTimeblockInterval = new Interval(startTime, endTime);
        List<Interval> dayInt = new ArrayList<Interval>();
        String viewPrincipal = TKContext.getTargetPrincipalId();
        
        dayInt.add(addedTimeblockInterval);
        List<Assignment> assignments = HrServiceLocator.getAssignmentService().getAssignmentsByCalEntryForLeaveCalendar(viewPrincipal, calendarEntry);
		List<String> assignmentKeys = new ArrayList<String>();
        for(Assignment assign : assignments) {
        	assignmentKeys.add(assign.getAssignmentKey());
        }
        
        List<LeaveBlock> leaveBlocks = LmServiceLocator.getLeaveBlockService().getLeaveBlocksForLeaveCalendar(viewPrincipal, calendarEntry.getBeginPeriodFullDateTime().toLocalDate(), calendarEntry.getEndPeriodFullDateTime().toLocalDate(), assignmentKeys);
        for (LeaveBlock leaveBlock : leaveBlocks) {
        	 if (errors.size() == 0 && StringUtils.equals(earnCodeType, TkConstants.EARN_CODE_TIME) && leaveBlock.getBeginTimestamp() != null && leaveBlock.getEndTimestamp()!= null) {
                Interval leaveBlockInterval = new Interval(leaveBlock.getBeginTimestamp().getTime(), leaveBlock.getEndTimestamp().getTime());
                for (Interval intv : dayInt) {
                    if (isRegularEarnCode && leaveBlockInterval.overlaps(intv) && (lmLeaveBlockId == null || lmLeaveBlockId.compareTo(leaveBlock.getLmLeaveBlockId()) != 0)) {
                        errors.add("The leave block you are trying to add overlaps with an existing time block.");
                    }
                }
        	 }
        }

        return errors;
    }
}
