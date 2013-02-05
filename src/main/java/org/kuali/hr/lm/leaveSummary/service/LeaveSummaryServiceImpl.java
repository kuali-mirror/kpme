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
package org.kuali.hr.lm.leaveSummary.service;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDateTime;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.accrual.AccrualCategoryRule;
import org.kuali.hr.lm.employeeoverride.EmployeeOverride;
import org.kuali.hr.lm.leaveSummary.LeaveSummary;
import org.kuali.hr.lm.leaveSummary.LeaveSummaryRow;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leaveblock.service.LeaveBlockService;
import org.kuali.hr.lm.leaveplan.LeavePlan;
import org.kuali.hr.lm.workflow.LeaveCalendarDocumentHeader;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.krad.util.ObjectUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class LeaveSummaryServiceImpl implements LeaveSummaryService {
	private LeaveBlockService leaveBlockService;

    @Override
    public LeaveSummary getLeaveSummary(String principalId, CalendarEntries calendarEntry) {
        LeaveSummary ls = new LeaveSummary();
        List<LeaveSummaryRow> rows = new ArrayList<LeaveSummaryRow>();

        if(StringUtils.isEmpty(principalId) || calendarEntry == null) {
            return ls;
        }
        PrincipalHRAttributes pha = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, calendarEntry.getBeginPeriodDate());
        if(pha == null) {	// principal hr attributes does not exist at the beginning of this calendar entry
        	pha = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, calendarEntry.getEndPeriodDate());
        }
        // get list of principalHrAttributes that become active during this pay period
        List<PrincipalHRAttributes> phaList = TkServiceLocator.getPrincipalHRAttributeService()
                .getActivePrincipalHrAttributesForRange(principalId, calendarEntry.getBeginPeriodDate(), calendarEntry.getEndPeriodDate());

        Set<String> lpStrings = new HashSet<String>();     //
        if(pha != null) {
            lpStrings.add(pha.getLeavePlan());
        }
        if(CollectionUtils.isNotEmpty(phaList)) {
            for(PrincipalHRAttributes aPha : phaList) {
                lpStrings.add(aPha.getLeavePlan());
            }
        }

        if (CollectionUtils.isNotEmpty(lpStrings)) {
            for(String aLpString : lpStrings) {
                LeavePlan lp = TkServiceLocator.getLeavePlanService().getLeavePlan(aLpString, calendarEntry.getEndPeriodDate());
                if(lp == null) {
                    continue;
                }
                DateFormat formatter = new SimpleDateFormat("MMMM d");
                DateFormat formatter2 = new SimpleDateFormat("MMMM d yyyy");
                DateTime entryEndDate = calendarEntry.getEndLocalDateTime().toDateTime();
                if (entryEndDate.getHourOfDay() == 0) {
                    entryEndDate = entryEndDate.minusDays(1);
                }
                String aString = formatter.format(calendarEntry.getBeginPeriodDate()) + " - " + formatter2.format(entryEndDate.toDate());
                ls.setPendingDatesString(aString);	
                
                LeaveCalendarDocumentHeader approvedLcdh = TkServiceLocator.getLeaveCalendarDocumentHeaderService().getMaxEndDateApprovedLeaveCalendar(principalId);
                if(approvedLcdh != null) {
                    Date endApprovedDate = new java.sql.Date(approvedLcdh.getEndDate().getTime());
                    LocalDateTime aLocalTime = new DateTime(approvedLcdh.getEndDate()).toLocalDateTime();
                    DateTime endApprovedTime = aLocalTime.toDateTime(TkServiceLocator.getTimezoneService().getUserTimezoneWithFallback());
                    if(endApprovedTime.getHourOfDay() == 0) {
                        endApprovedDate = TKUtils.addDates(endApprovedDate, -1);
                    }
                    String datesString = formatter.format(approvedLcdh.getBeginDate()) + " - " + formatter2.format(endApprovedDate);
                    ls.setYtdDatesString(datesString);
                }

                //until we have something that creates carry over, we need to grab everything.
                // Calculating leave bLocks from Calendar Year start instead of Service Date
                java.util.Date yearStartDate = this.getLeavePlanCalendarYearStart(lp, calendarEntry);
                List<LeaveBlock> leaveBlocks = getLeaveBlockService().getLeaveBlocks(principalId, pha.getServiceDate(), calendarEntry.getEndPeriodDateTime());


                List<LeaveBlock> futureLeaveBlocks = getLeaveBlockService().getLeaveBlocks(principalId, calendarEntry.getEndPeriodDateTime(), calendarEntry.getEndLocalDateTime().toDateTime().plusYears(5).toDate());
                Map<String, List<LeaveBlock>> leaveBlockMap = mapLeaveBlocksByAccrualCategory(leaveBlocks);
                Map<String, List<LeaveBlock>> futureLeaveBlockMap = mapLeaveBlocksByAccrualCategory(futureLeaveBlocks);
                List<AccrualCategory> acList = TkServiceLocator.getAccrualCategoryService().getActiveAccrualCategoriesForLeavePlan(lp.getLeavePlan(), calendarEntry.getEndPeriodDate());
                if(CollectionUtils.isNotEmpty(acList)) {
                    for(AccrualCategory ac : acList) {
                        if(ac.getShowOnGrid().equals("Y")) {
                            LeaveSummaryRow lsr = new LeaveSummaryRow();
                            lsr.setAccrualCategory(ac.getAccrualCategory());
                            lsr.setAccrualCategoryId(ac.getLmAccrualCategoryId());
                            //get max balances
                            AccrualCategoryRule acRule = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRuleForDate(ac, TKUtils.getCurrentDate(), pha.getServiceDate());
                            //accrual category rule id set on a leave summary row will be useful in generating a relevant balance transfer
                            //document from the leave calendar display. Could put this id in the request for balance transfer document.
                            lsr.setAccrualCategoryRuleId(acRule == null ? null : acRule.getLmAccrualCategoryRuleId());
                            if(acRule != null &&
                                    (acRule.getMaxBalance()!= null
                                      || acRule.getMaxUsage() != null)) {
                                if (acRule.getMaxUsage() != null) {
                                    lsr.setUsageLimit(new BigDecimal(acRule.getMaxUsage()).setScale(2));
                                } else {
                                    lsr.setUsageLimit(null);
                                }

                            } else {
                                lsr.setUsageLimit(null);
                            }
                            
                            List<EmployeeOverride> employeeOverrides = TkServiceLocator.getEmployeeOverrideService().getEmployeeOverrides(principalId,TKUtils.getCurrentDate()); //current date ok?
                            for(EmployeeOverride eo : employeeOverrides) {
                                if(eo.getLeavePlan().equals(lp.getLeavePlan()) && eo.getAccrualCategory().equals(ac.getAccrualCategory())) {
                                    if(eo.getOverrideType().equals("MU") && eo.isActive()) {
                                        if(eo.getOverrideValue()!=null && !eo.getOverrideValue().equals(""))
                                            lsr.setUsageLimit(new BigDecimal(eo.getOverrideValue()));
                                        else // no limit flag
                                            lsr.setUsageLimit(null);
                                    }
                                }
                            }

                            //handle up to current leave blocks
                            assignApprovedValuesToRow(lsr, ac.getAccrualCategory(), leaveBlockMap.get(ac.getAccrualCategory()), lp, calendarEntry.getBeginPeriodDate());
                                                    
                            BigDecimal carryOver = BigDecimal.ZERO.setScale(2);
                            lsr.setCarryOver(carryOver);
                            
                            
//                            //Fetching leaveblocks for accCat with type CarryOver -- This is logic according to the CO blocks created from scheduler job.
//                            List<LeaveBlock> accCatBlocks = new ArrayList<LeaveBlock>();
//                            List<LeaveBlock> carryOverBlocks = leaveBlockService.getLeaveBlocksWithType(principalId, pha.getServiceDate(), yearStartDate, LMConstants.LEAVE_BLOCK_TYPE.CARRY_OVER);
//                            if(carryOverBlocks != null && !carryOverBlocks.isEmpty()) {
//                            	for(LeaveBlock carryOverLB : carryOverBlocks) {
//                            		if(carryOverLB.getAccrualCategory().equals(ac.getAccrualCategory())) {
//                            			accCatBlocks.add(carryOverLB);
//                            		}
//                            	}
//                            }
//                            if(accCatBlocks.size() > 0) {
//	                            
//                            	Collections.sort(accCatBlocks, new Comparator<LeaveBlock>() {
//									@Override
//									public int compare(LeaveBlock o1, LeaveBlock o2) {
//										return o2.getLeaveDate().compareTo(o1.getLeaveDate());
//									}
//								});
//                            	
//	                            carryOver = accCatBlocks.get(0).getLeaveAmount();
//	                            
//	                            if (acRule != null && acRule.getMaxCarryOver() != null && acRule.getMaxCarryOver() < carryOver.longValue()) {
//	                            	carryOver = new BigDecimal(acRule.getMaxCarryOver());
//	                            }
//                            }
//                            lsr.setCarryOver(carryOver);
                            
                            //how about the leave blocks on the calendar entry being currently handled??

                            //figure out past carry over values!!!
                            //We now have list of past years accrual and use (with ordered keys!!!)
                            
                            // TODO : Here the problem is : In Oliver' case YearlyAccrued map {2012= 178.40} and YearlyUsage map is {2011=-80, 2012=-122}
                            // in this case 2011 is not getting calculated as yearlyAccrued map does not have key 2011.
                            for (Map.Entry<String, BigDecimal> entry : lsr.getPriorYearsTotalAccrued().entrySet()) {
                                  carryOver = carryOver.add(entry.getValue());
                                  BigDecimal use = lsr.getPriorYearsUsage().containsKey(entry.getKey()) ? lsr.getPriorYearsUsage().get(entry.getKey()) : BigDecimal.ZERO;
                                  carryOver = carryOver.add(use);
                                  if (acRule != null  && acRule.getMaxCarryOver() != null && acRule.getMaxCarryOver() < carryOver.longValue()) {
                                        carryOver = new BigDecimal(acRule.getMaxCarryOver());
                                  }
                            }
                            
                            lsr.setCarryOver(carryOver);
                            
                            // Jignasha : I have put this code to get the actual carry over amount.
//                            carryOver = BigDecimal.ZERO.setScale(2);
//                            Set<String> yearKeys = new HashSet<String>();
//                            
//                            yearKeys.addAll(lsr.getPriorYearsTotalAccrued().keySet());
//                            yearKeys.addAll(lsr.getPriorYearsUsage().keySet());
//                            
//                            // This is for including any 
//                            for (String entry : yearKeys) {
//                            	BigDecimal tempCarryOver = BigDecimal.ZERO.setScale(2);
//                            	tempCarryOver = lsr.getPriorYearsTotalAccrued().containsKey(entry) ? lsr.getPriorYearsTotalAccrued().get(entry) : BigDecimal.ZERO;
//                                carryOver = carryOver.add(tempCarryOver);
//                                BigDecimal use = lsr.getPriorYearsUsage().containsKey(entry) ? lsr.getPriorYearsUsage().get(entry) : BigDecimal.ZERO;
//                                carryOver = carryOver.add(use);
//                                if (acRule != null
//                                        && acRule.getMaxCarryOver() != null
//                                        && acRule.getMaxCarryOver() < carryOver.longValue()) {
//                                    carryOver = new BigDecimal(acRule.getMaxCarryOver());
//                                }
//                            }
//                            
//                            lsr.setCarryOver(carryOver);
                            

                            //handle future leave blocks
                            assignPendingValuesToRow(lsr, ac.getAccrualCategory(), futureLeaveBlockMap.get(ac.getAccrualCategory()));

                            //compute Leave Balance
                            BigDecimal leaveBalance = lsr.getAccruedBalance().subtract(lsr.getPendingLeaveRequests());
                            //if leave balance is set 
                            //Employee overrides have already been taken into consideration and the appropriate values
                            //for usage have been set by this point.
//                            if (acRule != null && StringUtils.equals(acRule.getMaxBalFlag(), "Y")) {
                        	//there exists an accrual category rule with max balance limit imposed.
                        	//max bal flag = 'Y' has no precedence here with max-bal / balance transfers implemented.
                        	//unless institutions are not required to define a max balance limit for action_at_max_bal = LOSE.
                        	//Possibly preferable to procure forfeiture blocks through balance transfer
                        	if(lsr.getUsageLimit()!=null) { //should not set leave balance to usage limit simply because it's not null.
                        		BigDecimal availableUsage = lsr.getUsageLimit().subtract(lsr.getYtdApprovedUsage().add(lsr.getPendingLeaveRequests()));
                        		if(leaveBalance.compareTo( availableUsage ) > 0)
                        			lsr.setLeaveBalance(availableUsage);
                        		else
                        			lsr.setLeaveBalance(leaveBalance);
                        	} else { //no usage limit
                        		lsr.setLeaveBalance(leaveBalance);
                            }

                        	//Rows should only be marked transferable/payoutable for calendars that have not already been submitted for approval,
                        	//and if either the current date falls within the calendar, or it is beyond the end date of the calendar.
                        	//Logic should be implemented which would take action on over-the-limit balances with frequency on-demand, if the user
                        	//did not transfer the excess themselves, upon calendar submission/approval.
                        	//Doing so would eliminate the need to suppress the nested method calls in cases dealing with calendar entries
                        	//that have moved beyond status initiated.
                        	DateTime startDateTime = new DateTime(calendarEntry.getBeginPeriodDateTime());
                        	DateTime endDateTime = new DateTime(calendarEntry.getEndPeriodDateTime());
                        	Interval interval = new Interval(startDateTime,endDateTime);
                        	if(interval.containsNow() || !calendarEntry.getEndPeriodDate().after(TKUtils.getCurrentDate())) {
                        		//current or past calendar is being used for leave summary calculation
                        		if(ObjectUtils.isNotNull(approvedLcdh)) {
	                        		if(approvedLcdh.getEndDate().before(TKUtils.getCurrentDate())) {
	                        			//should only allow on-demand transfers if calendar document has status initiated.
	                        			//obviously this code block is executed for all non-approved calendars, displaying the buttons
	                        			//for calendar documents that have already been routed.
	                        			//Depending on requirements for on demand display, may need to update this.
			                            markTransferable(lsr,acRule,principalId);
			                            markPayoutable(lsr,acRule,principalId);
	                        		}
                        		}
                        		// One situation where this would cause problems is when there is no previous approved leave calendar document
                        		// i.e. When someone starts a new position, or if the principal has no leave eligible jobs. ( no leave calendar doc ).
                        		// if someone were to move into a new leave plan and a balance was transferred that exceeded the new leave plans
                        		// balance limit, with a transfer frequency on-demand, the principal would not be allowed to transfer this excess.
                        		// fringe case, but should still be addressed...
                        	}
                            rows.add(lsr);
                        }
                    }
                    // let's check for 'empty' accrual categories
                    if (leaveBlockMap.containsKey(null)
                            || futureLeaveBlockMap.containsKey(null)) {
                        LeaveSummaryRow otherLeaveSummary = new LeaveSummaryRow();
                        //otherLeaveSummary.setAccrualCategory("Other");

                        assignApprovedValuesToRow(otherLeaveSummary, null, leaveBlockMap.get(null), lp, calendarEntry.getBeginPeriodDate());
                        BigDecimal carryOver = BigDecimal.ZERO.setScale(2);
                        for (Map.Entry<String, BigDecimal> entry : otherLeaveSummary.getPriorYearsTotalAccrued().entrySet()) {
                            carryOver = carryOver.add(entry.getValue());
                            BigDecimal use = otherLeaveSummary.getPriorYearsUsage().containsKey(entry.getKey()) ? otherLeaveSummary.getPriorYearsUsage().get(entry.getKey()) : BigDecimal.ZERO;
                            carryOver = carryOver.add(use);
                        }
                        otherLeaveSummary.setCarryOver(carryOver);
                        assignPendingValuesToRow(otherLeaveSummary, null, futureLeaveBlockMap.get(null));
                        otherLeaveSummary.setAccrualCategory("Other");

                        //compute Leave Balance
                        // blank the avail
                        otherLeaveSummary.setUsageLimit(null);
                      	otherLeaveSummary.setLeaveBalance(null);

                        rows.add(otherLeaveSummary);
                    }
                }
            }
        }
        ls.setLeaveSummaryRows(rows);
        return ls;
    }

    @Override
    public LeaveSummary getLeaveSummary(String principalId, CalendarEntries calendarEntry, Calendar previousYearCalendarStart) {
        LeaveSummary ls = new LeaveSummary();
        List<LeaveSummaryRow> rows = new ArrayList<LeaveSummaryRow>();

        if(StringUtils.isEmpty(principalId) || calendarEntry == null) {
            return ls;
        }
        PrincipalHRAttributes pha = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, calendarEntry.getBeginPeriodDate());
        if(pha == null) {	// principal hr attributes does not exist at the beginning of this calendar entry
        	pha = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, calendarEntry.getEndPeriodDate());
        }
        // get list of principalHrAttributes that become active during this pay period
        List<PrincipalHRAttributes> phaList = TkServiceLocator.getPrincipalHRAttributeService()
                .getActivePrincipalHrAttributesForRange(principalId, calendarEntry.getBeginPeriodDate(), calendarEntry.getEndPeriodDate());

        Set<String> lpStrings = new HashSet<String>();     //
        if(pha != null) {
            lpStrings.add(pha.getLeavePlan());
        }
        if(CollectionUtils.isNotEmpty(phaList)) {
            for(PrincipalHRAttributes aPha : phaList) {
                lpStrings.add(aPha.getLeavePlan());
            }
        }

        if (CollectionUtils.isNotEmpty(lpStrings)) {
            for(String aLpString : lpStrings) {
                LeavePlan lp = TkServiceLocator.getLeavePlanService().getLeavePlan(aLpString, calendarEntry.getEndPeriodDate());
                if(lp == null) {
                    continue;
                }
                DateFormat formatter = new SimpleDateFormat("MMMM d");
                DateFormat formatter2 = new SimpleDateFormat("MMMM d yyyy");
                DateTime entryEndDate = calendarEntry.getEndLocalDateTime().toDateTime();
                if (entryEndDate.getHourOfDay() == 0) {
                    entryEndDate = entryEndDate.minusDays(1);
                }
                String aString = formatter.format(calendarEntry.getBeginPeriodDate()) + " - " + formatter2.format(entryEndDate.toDate());
                ls.setPendingDatesString(aString);

                LeaveCalendarDocumentHeader approvedLcdh = TkServiceLocator.getLeaveCalendarDocumentHeaderService().getMaxEndDateApprovedLeaveCalendar(principalId);
                if(approvedLcdh != null) {
                    Date endApprovedDate = new java.sql.Date(approvedLcdh.getEndDate().getTime());
                    LocalDateTime aLocalTime = new DateTime(approvedLcdh.getEndDate()).toLocalDateTime();
                    DateTime endApprovedTime = aLocalTime.toDateTime(TkServiceLocator.getTimezoneService().getUserTimezoneWithFallback());
                    if(endApprovedTime.getHourOfDay() == 0) {
                        endApprovedDate = TKUtils.addDates(endApprovedDate, -1);
                    }
                    String datesString = formatter.format(approvedLcdh.getBeginDate()) + " - " + formatter2.format(endApprovedDate);
                    ls.setYtdDatesString(datesString);
                }

                List<LeaveBlock> leaveBlocks = null;
                //until we have something that creates carry over, we need to grab everything.
//                List<LeaveBlock> leaveBlocks = getLeaveBlockService().getLeaveBlocks(principalId, pha.getServiceDate(), calendarEntry.getEndPeriodDateTime());
                
                if(previousYearCalendarStart == null) {
                	leaveBlocks = getLeaveBlockService().getLeaveBlocks(principalId, this.getLeavePlanCalendarYearStart(lp, calendarEntry), calendarEntry.getEndPeriodDateTime());
                } else {
                	leaveBlocks = getLeaveBlockService().getLeaveBlocks(principalId, previousYearCalendarStart.getTime(), calendarEntry.getEndPeriodDateTime());	
                }
                

                List<LeaveBlock> futureLeaveBlocks = getLeaveBlockService().getLeaveBlocks(principalId, calendarEntry.getEndPeriodDateTime(), calendarEntry.getEndLocalDateTime().toDateTime().plusYears(5).toDate());
                Map<String, List<LeaveBlock>> leaveBlockMap = mapLeaveBlocksByAccrualCategory(leaveBlocks);
                Map<String, List<LeaveBlock>> futureLeaveBlockMap = mapLeaveBlocksByAccrualCategory(futureLeaveBlocks);
                List<AccrualCategory> acList = TkServiceLocator.getAccrualCategoryService().getActiveAccrualCategoriesForLeavePlan(lp.getLeavePlan(), calendarEntry.getEndPeriodDate());
                if(CollectionUtils.isNotEmpty(acList)) {
                    for(AccrualCategory ac : acList) {
                        if(ac.getShowOnGrid().equals("Y")) {
                            LeaveSummaryRow lsr = new LeaveSummaryRow();
                            lsr.setAccrualCategory(ac.getAccrualCategory());
                            lsr.setAccrualCategoryId(ac.getLmAccrualCategoryId());
                            //get max balances
                            AccrualCategoryRule acRule = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRuleForDate(ac, TKUtils.getCurrentDate(), pha.getServiceDate());
                            //accrual category rule id set on a leave summary row will be useful in generating a relevant balance transfer
                            //document from the leave calendar display. Could put this id in the request for balance transfer document.
                            lsr.setAccrualCategoryRuleId(acRule == null ? null : acRule.getLmAccrualCategoryRuleId());
                            if(acRule != null &&
                                    (acRule.getMaxBalance()!= null
                                      || acRule.getMaxUsage() != null)) {
                                if (acRule.getMaxUsage() != null) {
                                    lsr.setUsageLimit(new BigDecimal(acRule.getMaxUsage()).setScale(2));
                                } else {
                                    lsr.setUsageLimit(null);
                                }

                            } else {
                                lsr.setUsageLimit(null);
                            }
                            
                            // set MaxCarryOver
                            if(acRule != null &&
                                    (acRule.getMaxCarryOver()!= null
                                      || acRule.getMaxCarryOver() != null)) {
                                if (acRule.getMaxCarryOver() != null) {
                                    lsr.setMaxCarryOver(new BigDecimal(acRule.getMaxCarryOver()).setScale(2));
                                } else {
                                    lsr.setMaxCarryOver(null);
                                }

                            } else {
                            	lsr.setMaxCarryOver(null);
                            }

                            List<EmployeeOverride> employeeOverrides = TkServiceLocator.getEmployeeOverrideService().getEmployeeOverrides(principalId,TKUtils.getCurrentDate()); //current date ok?
                            for(EmployeeOverride eo : employeeOverrides) {
                                if(eo.getLeavePlan().equals(lp.getLeavePlan()) && eo.getAccrualCategory().equals(ac.getAccrualCategory())) {
                                    if(eo.getOverrideType().equals("MU") && eo.isActive()) {
                                        if(eo.getOverrideValue()!=null && !eo.getOverrideValue().equals(""))
                                            lsr.setUsageLimit(new BigDecimal(eo.getOverrideValue()));
                                        else // no limit flag
                                            lsr.setUsageLimit(null);
                                    }
                                }
                            }

                            //handle up to current leave blocks
                            assignApprovedValuesToRow(lsr, ac.getAccrualCategory(), leaveBlockMap.get(ac.getAccrualCategory()), lp, calendarEntry.getBeginPeriodDate());
                          

                            //figure out past carry over values!!!
                            //We now have list of past years accrual and use (with ordered keys!!!)
                            BigDecimal carryOver = BigDecimal.ZERO.setScale(2);
//                            for (Map.Entry<String, BigDecimal> entry : lsr.getPriorYearsTotalAccrued().entrySet()) {
//                                carryOver = carryOver.add(entry.getValue());
//                                BigDecimal use = lsr.getPriorYearsUsage().containsKey(entry.getKey()) ? lsr.getPriorYearsUsage().get(entry.getKey()) : BigDecimal.ZERO;
//                                carryOver = carryOver.add(use);
//
//                                if (acRule != null
//                                        && acRule.getMaxCarryOver() != null
//                                        && acRule.getMaxCarryOver() < carryOver.longValue()) {
//                                    carryOver = new BigDecimal(acRule.getMaxCarryOver());
//                                }
//                            }
                            lsr.setCarryOver(carryOver);

                            //handle future leave blocks
                            assignPendingValuesToRow(lsr, ac.getAccrualCategory(), futureLeaveBlockMap.get(ac.getAccrualCategory()));

                            //compute Leave Balance
                            BigDecimal leaveBalance = lsr.getAccruedBalance().subtract(lsr.getPendingLeaveRequests());
                            //if leave balance is set 
                            //Employee overrides have already been taken into consideration and the appropriate values
                            //for usage have been set by this point.
//                            if (acRule != null && StringUtils.equals(acRule.getMaxBalFlag(), "Y")) {
                        	//there exists an accrual category rule with max balance limit imposed.
                        	//max bal flag = 'Y' has no precedence here with max-bal / balance transfers implemented.
                        	//unless institutions are not required to define a max balance limit for action_at_max_bal = LOSE.
                        	//Possibly preferable to procure forfeiture blocks through balance transfer
                        	if(lsr.getUsageLimit()!=null) { //should not set leave balance to usage limit simply because it's not null.
                        		BigDecimal availableUsage = lsr.getUsageLimit().subtract(lsr.getYtdApprovedUsage().add(lsr.getPendingLeaveRequests()));
                        		if(leaveBalance.compareTo( availableUsage ) > 0)
                        			lsr.setLeaveBalance(availableUsage);
                        		else
                        			lsr.setLeaveBalance(leaveBalance);
                        	} else { //no usage limit
                        		lsr.setLeaveBalance(leaveBalance);
                            }

                            markTransferable(lsr,acRule,principalId);
                            markPayoutable(lsr,acRule,principalId);

                            rows.add(lsr);
                        }
                    }
                    // let's check for 'empty' accrual categories
                    if (leaveBlockMap.containsKey(null)
                            || futureLeaveBlockMap.containsKey(null)) {
                        LeaveSummaryRow otherLeaveSummary = new LeaveSummaryRow();
                        //otherLeaveSummary.setAccrualCategory("Other");
                        assignApprovedValuesToRow(otherLeaveSummary, null, leaveBlockMap.get(null), lp, calendarEntry.getBeginPeriodDate());
                        BigDecimal carryOver = BigDecimal.ZERO.setScale(2);
                        for (Map.Entry<String, BigDecimal> entry : otherLeaveSummary.getPriorYearsTotalAccrued().entrySet()) {
                            carryOver = carryOver.add(entry.getValue());
                            BigDecimal use = otherLeaveSummary.getPriorYearsUsage().containsKey(entry.getKey()) ? otherLeaveSummary.getPriorYearsUsage().get(entry.getKey()) : BigDecimal.ZERO;
                            carryOver = carryOver.add(use);
                        }
                        otherLeaveSummary.setCarryOver(carryOver);
                        assignPendingValuesToRow(otherLeaveSummary, null, futureLeaveBlockMap.get(null));
                        otherLeaveSummary.setAccrualCategory("Other");

                        //compute Leave Balance
                        // blank the avail
                        otherLeaveSummary.setUsageLimit(null);
                      	otherLeaveSummary.setLeaveBalance(null);

                        rows.add(otherLeaveSummary);
                    }
                }
            }
        }
        ls.setLeaveSummaryRows(rows);
        return ls;
    }

    private Map<String, List<LeaveBlock>> mapLeaveBlocksByAccrualCategory(List<LeaveBlock> leaveBlocks) {
        Map<String, List<LeaveBlock>> map = new HashMap<String, List<LeaveBlock>>();
        for (LeaveBlock lb : leaveBlocks) {
            if (map.containsKey(lb.getAccrualCategory())) {
                map.get(lb.getAccrualCategory()).add(lb);
            } else {
                List<LeaveBlock> splitLeaveBlocks = new ArrayList<LeaveBlock>();
                splitLeaveBlocks.add(lb);
                map.put(lb.getAccrualCategory(), splitLeaveBlocks);
            }
        }
        return map;
    }

    /**
     * Use with button display for balance transfers available On-Demand.
     * @param lsr
     * @param accrualCategoryRule
     * @param principalId 
     */
    private void markTransferable(LeaveSummaryRow lsr, AccrualCategoryRule accrualCategoryRule, String principalId) {
    	//return type must be changed to boolean, or an associated field element must be created for decision
    	//purposes.
    	//an accrual category's balance is transferable if the accrued balance is 
    	//greater than the maximum balance allowed for the accrual category. action_at_max_balance must be TRANSFER
    	boolean transferable = false;
    	if(ObjectUtils.isNotNull(accrualCategoryRule)) {
    		if(ObjectUtils.isNotNull(accrualCategoryRule.getMaxBalance())) {
    			BigDecimal maxBalance = accrualCategoryRule.getMaxBalance();
    			BigDecimal fte = TkServiceLocator.getJobService().getFteSumForAllActiveLeaveEligibleJobs(principalId, TKUtils.getCurrentDate());
    			BigDecimal adjustedMaxBalance = maxBalance.multiply(fte);
    			List<EmployeeOverride> overrides = TkServiceLocator.getEmployeeOverrideService().getEmployeeOverrides(principalId, TKUtils.getCurrentDate());
    			for(EmployeeOverride override : overrides) {
    				if(StringUtils.equals(override.getOverrideType(),TkConstants.EMPLOYEE_OVERRIDE_TYPE.get("MB"))
    						&& override.isActive()) {
    					adjustedMaxBalance = new BigDecimal(override.getOverrideValue());
    					break;
    				}
    			}
    			if(adjustedMaxBalance.compareTo(lsr.getAccruedBalance()) < 0) {
    				if(StringUtils.equals(accrualCategoryRule.getActionAtMaxBalance(), LMConstants.ACTION_AT_MAX_BAL.TRANSFER) &&
    						StringUtils.equals(accrualCategoryRule.getMaxBalanceActionFrequency(),LMConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND))
    					transferable = true;
    			}
    		}
    	}
    	lsr.setTransferable(transferable);
    }
    
    /**
     * Use with button display for balance transfer payouts available On-Demand.
     * @param lsr
     * @param accrualCategoryRule 
     * @param principalId 
     */
    private void markPayoutable(LeaveSummaryRow lsr, AccrualCategoryRule accrualCategoryRule, String principalId) {
    	//return type must be changed to boolean, or an associated field element must be created for decision
    	//purposes.
    	//an accrual category's balance is transferable if max_bal_action_frequency is ON-DEMAND
    	//and action_at_max_balance is PAYOUT
    	boolean payoutable = false;
    	if(ObjectUtils.isNotNull(accrualCategoryRule)) {
    		if(ObjectUtils.isNotNull(accrualCategoryRule.getMaxBalance())) {
    			BigDecimal maxBalance = accrualCategoryRule.getMaxBalance();
    			BigDecimal fte = TkServiceLocator.getJobService().getFteSumForAllActiveLeaveEligibleJobs(principalId, TKUtils.getCurrentDate());
    			BigDecimal adjustedMaxBalance = maxBalance.multiply(fte);
    			List<EmployeeOverride> overrides = TkServiceLocator.getEmployeeOverrideService().getEmployeeOverrides(principalId, TKUtils.getCurrentDate());
    			for(EmployeeOverride override : overrides) {
    				if(StringUtils.equals(override.getOverrideType(),TkConstants.EMPLOYEE_OVERRIDE_TYPE.get("MB"))
    						&& override.isActive()) {
    					adjustedMaxBalance = new BigDecimal(override.getOverrideValue());
    					break;
    				}
    			}
    			if(adjustedMaxBalance.compareTo(lsr.getAccruedBalance()) < 0) {
    				if(StringUtils.equals(accrualCategoryRule.getActionAtMaxBalance(), LMConstants.ACTION_AT_MAX_BAL.PAYOUT) &&
    						StringUtils.equals(accrualCategoryRule.getMaxBalanceActionFrequency(),LMConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND))
    					payoutable = true;
    			}
    		}
    	}
    	lsr.setPayoutable(payoutable);
    }
    
	private void assignApprovedValuesToRow(LeaveSummaryRow lsr, String accrualCategory, List<LeaveBlock> approvedLeaveBlocks, LeavePlan lp, Date effectiveDate) {
        //List<TimeOffAccrual> timeOffAccruals = TkServiceLocator.getTimeOffAccrualService().getTimeOffAccrualsCalc(principalId, lsr.get)
		//BigDecimal carryOver = BigDecimal.ZERO.setScale(2);
        SortedMap<String, BigDecimal> yearlyAccrued = new TreeMap<String, BigDecimal>();
        SortedMap<String, BigDecimal> yearlyUsage = new TreeMap<String, BigDecimal>();
        BigDecimal accrualedBalance = BigDecimal.ZERO.setScale(2);
		BigDecimal approvedUsage = BigDecimal.ZERO.setScale(2);
		BigDecimal fmlaUsage = BigDecimal.ZERO.setScale(2);

        int priorYearCutOffMonth = lp.getCalendarYearStart() == null ? 1 : Integer.parseInt(lp.getCalendarYearStart().substring(0,2));
        int priorYearCutOffDay = lp.getCalendarYearStart() == null ? 1 : Integer.parseInt(lp.getCalendarYearStart().substring(3,5));
        // cutOffDate = current ca
        DateMidnight cutOffDate = new DateMidnight(effectiveDate).withMonthOfYear(priorYearCutOffMonth).withDayOfMonth(priorYearCutOffDay);
        if (cutOffDate.isAfter(effectiveDate.getTime())) {
            cutOffDate = cutOffDate.withYear(cutOffDate.getYear() - 1);
        }
        
        cutOffDate = cutOffDate.minusDays(1);
        Timestamp priorYearCutOff = new Timestamp(cutOffDate.getMillis());

        if (CollectionUtils.isNotEmpty(approvedLeaveBlocks)) {
            // create it here so we don't need to get instance every loop iteration
            for(LeaveBlock aLeaveBlock : approvedLeaveBlocks) {
            	if(!aLeaveBlock.getLeaveBlockType().equals(LMConstants.LEAVE_BLOCK_TYPE.CARRY_OVER)) {
                if((StringUtils.isBlank(accrualCategory) && StringUtils.isBlank(aLeaveBlock.getAccrualCategory()))
                        || (StringUtils.isNotBlank(aLeaveBlock.getAccrualCategory())
                            && StringUtils.equals(aLeaveBlock.getAccrualCategory(), accrualCategory))) {
                    if(aLeaveBlock.getLeaveAmount().compareTo(BigDecimal.ZERO) >= 0
                            && !aLeaveBlock.getLeaveBlockType().equals(LMConstants.LEAVE_BLOCK_TYPE.LEAVE_CALENDAR)) {
                        /** KPME-2057: Removed conditional to consider all statuses **/
                        //if(StringUtils.equals(LMConstants.REQUEST_STATUS.APPROVED, aLeaveBlock.getRequestStatus())) {
                            if (aLeaveBlock.getLeaveDate().getTime() < priorYearCutOff.getTime()) {
                                String yearKey = getYearKey(aLeaveBlock.getLeaveDate(), lp);
                                BigDecimal co = yearlyAccrued.get(yearKey);
                                if (co == null) {
                                    co = BigDecimal.ZERO.setScale(2);
                                }
                                co = co.add(aLeaveBlock.getLeaveAmount());
                                yearlyAccrued.put(yearKey, co);
                            } else if(aLeaveBlock.getLeaveDate().getTime() < effectiveDate.getTime()) {
                                accrualedBalance = accrualedBalance.add(aLeaveBlock.getLeaveAmount());
                            }
                       // }
                    } else {
                    	//LEAVE_BLOCK_TYPE.BALANCE_TRANSFER should not count as usage, but it does need to be taken out of accrued balance.
                        BigDecimal currentLeaveAmount = aLeaveBlock.getLeaveAmount().compareTo(BigDecimal.ZERO) > 0 ? aLeaveBlock.getLeaveAmount().negate() : aLeaveBlock.getLeaveAmount();
                        //we only want this for the current calendar!!!
                        /** KPME-2057: Removed conditional to consider all statuses **/
                        //if(StringUtils.equals(LMConstants.REQUEST_STATUS.APPROVED, aLeaveBlock.getRequestStatus())) {
                            if (aLeaveBlock.getLeaveDate().getTime() > priorYearCutOff.getTime()) {
                                approvedUsage = approvedUsage.add(currentLeaveAmount);
                                EarnCode ec = TkServiceLocator.getEarnCodeService().getEarnCode(aLeaveBlock.getEarnCode(), aLeaveBlock.getLeaveDate());
                                if(ec != null && ec.getFmla().equals("Y")) {
                                    fmlaUsage = fmlaUsage.add(aLeaveBlock.getLeaveAmount());
                                }
                            } else {
                                //these usages are for previous years, to help figure out correct carry over values
                                String yearKey = getYearKey(aLeaveBlock.getLeaveDate(), lp);
                                BigDecimal use = yearlyUsage.get(yearKey);
                                if (use == null) {
                                    use = BigDecimal.ZERO.setScale(2);
                                }
                                use = use.add(currentLeaveAmount);
                                yearlyUsage.put(yearKey, use);
                            }
                        //}
                    }

                    //}
                }
            }
            }
        }

        lsr.setPriorYearsTotalAccrued(yearlyAccrued);
        lsr.setPriorYearsUsage(yearlyUsage);
		lsr.setYtdAccruedBalance(accrualedBalance);
		lsr.setYtdApprovedUsage(approvedUsage.negate());
		lsr.setFmlaUsage(fmlaUsage.negate());
		
		//lsr.setLeaveBalance(lsr.getYtdAccruedBalance().add(approvedUsage));
	}

    private String getYearKey(Date leaveDate, LeavePlan lp){
        Calendar cal = Calendar.getInstance();
        Calendar leavePlanCal = Calendar.getInstance();
        cal.setTime(leaveDate);
        String yearKey = Integer.toString(cal.get(Calendar.YEAR));
        leavePlanCal.set(Calendar.MONTH, Integer.parseInt(lp.getCalendarYearStartMonth()) - 1);
        leavePlanCal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(lp.getCalendarYearStartDayOfMonth()));
        leavePlanCal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
        leavePlanCal.set(Calendar.HOUR_OF_DAY, 0);
        leavePlanCal.set(Calendar.MINUTE, 0);
        leavePlanCal.set(Calendar.SECOND, 0);
        leavePlanCal.set(Calendar.MILLISECOND, 0);

        if (cal.getTime().before(leavePlanCal.getTime())) {
            yearKey = Integer.toString(cal.get(Calendar.YEAR) - 1);
        }
        return yearKey;
    }
	
	private void assignPendingValuesToRow(LeaveSummaryRow lsr, String accrualCategory, List<LeaveBlock> pendingLeaveBlocks ) {
		BigDecimal pendingAccrual= BigDecimal.ZERO.setScale(2);
		BigDecimal pendingRequests = BigDecimal.ZERO.setScale(2);
        if (CollectionUtils.isNotEmpty(pendingLeaveBlocks)) {
            for(LeaveBlock aLeaveBlock : pendingLeaveBlocks) {
            	if(!aLeaveBlock.getLeaveBlockType().equals(LMConstants.LEAVE_BLOCK_TYPE.CARRY_OVER)) {
                if((StringUtils.isBlank(accrualCategory) && StringUtils.isBlank(aLeaveBlock.getAccrualCategory()))
                        || (StringUtils.isNotBlank(aLeaveBlock.getAccrualCategory())
                            && StringUtils.equals(aLeaveBlock.getAccrualCategory(), accrualCategory))) {
                    if(aLeaveBlock.getLeaveAmount().compareTo(BigDecimal.ZERO) >= 0) {
                        pendingAccrual = pendingAccrual.add(aLeaveBlock.getLeaveAmount());
                    } else {
                        pendingRequests = pendingRequests.add(aLeaveBlock.getLeaveAmount());
                    }
                }
            	}
            }
        }
		lsr.setPendingLeaveAccrual(pendingAccrual);
		lsr.setPendingLeaveRequests(pendingRequests.negate());
	}
	
	
	private Date calendarYearStartDate(String dateString, Date asOfDate) throws ParseException {
		// dateString in MM/DD format
		Calendar gc = new GregorianCalendar();
		gc.setTime(asOfDate);
		String yearString = Integer.toString(gc.get(Calendar.YEAR));
		String tempString = dateString;
		if(StringUtils.isEmpty(dateString)) {
			tempString = "01/01";		// use 01/01 as default starting date
		}
		String fullString = tempString + "/" + yearString;
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Date aDate = formatter.parse(fullString);
		if(aDate.after(asOfDate)) {
			yearString = Integer.toString(gc.get(Calendar.YEAR) -1);
			fullString = tempString + "/" + yearString;
			aDate = formatter.parse(fullString);
		}
		return aDate;
	}
	
	@Override
	public List<Date> getLeaveSummaryDates(CalendarEntries calendarEntry) {
		List<Date> leaveSummaryDates = new ArrayList<Date>();

		DateTime start = calendarEntry.getBeginLocalDateTime().toDateTime();
		DateTime end = calendarEntry.getEndLocalDateTime().toDateTime();
        Interval interval = new Interval(start, end);

        for (DateTime day = interval.getStart(); day.isBefore(interval.getEnd()); day = day.plusDays(1)) {
        	leaveSummaryDates.add(day.toLocalDate().toDateTimeAtStartOfDay().toDate());
        }
		 
		 return leaveSummaryDates;
	}

    protected LeaveBlockService getLeaveBlockService() {
        if (leaveBlockService == null) {
            leaveBlockService = TkServiceLocator.getLeaveBlockService();
        }
        return leaveBlockService;
    }

    
    private boolean ifCalendarYearStartForLeavePlan(LeavePlan leavePlan, CalendarEntries leaveCalEntries) {

    	boolean flag = true;
		// check if Calendar entry is first entry of the year start the make accrued balance and approved usage zero
		String calendarYearStartStr = leavePlan.getCalendarYearStart();
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
		sdf.setLenient(false);
		Date calYearStart = null;
		try {
			calYearStart = sdf.parse(calendarYearStartStr);
		} catch (ParseException e) {
		}
		System.out.println("Leave Plan is >> "+leavePlan.getLeavePlan());
		System.out.println("Leave Cal Entry is  >> "+leaveCalEntries);
		Calendar lpYearStart = Calendar.getInstance();
		lpYearStart.setTime(calYearStart);
		lpYearStart.set(Calendar.HOUR_OF_DAY, 0);
		lpYearStart.set(Calendar.MINUTE, 0);
		lpYearStart.set(Calendar.SECOND, 0);
		lpYearStart.set(Calendar.MILLISECOND, 0);
		lpYearStart.set(Calendar.YEAR, leaveCalEntries.getBeginLocalDateTime().getYear());
		if(lpYearStart.getTime() != null) {
			if((lpYearStart.getTime().compareTo(leaveCalEntries.getBeginPeriodDateTime()) >=0) && (lpYearStart.getTime().compareTo(leaveCalEntries.getEndPeriodDateTime()) <=0)){
				flag = true;
			}
		}
		return flag;
    }
    
    private Date getLeavePlanCalendarYearStart(LeavePlan leavePlan, CalendarEntries leaveCalEntries) {
    	boolean flag = true;
		// check if Calendar entry is first entry of the year start the make accrued balance and approved usage zero
		String calendarYearStartStr = leavePlan.getCalendarYearStart();
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
		sdf.setLenient(false);
		Date calYearStart = null;
		try {
			calYearStart = sdf.parse(calendarYearStartStr);
		} catch (ParseException e) {
		}
		Calendar lpYearStart = Calendar.getInstance();
		lpYearStart.setTime(calYearStart);
		lpYearStart.set(Calendar.HOUR_OF_DAY, 0);
		lpYearStart.set(Calendar.MINUTE, 0);
		lpYearStart.set(Calendar.SECOND, 0);
		lpYearStart.set(Calendar.MILLISECOND, 0);
		lpYearStart.set(Calendar.YEAR, leaveCalEntries.getBeginLocalDateTime().getYear());
		if(lpYearStart.getTime() != null) {
			if((lpYearStart.getTime().compareTo(leaveCalEntries.getBeginPeriodDateTime()) >=0) && (lpYearStart.getTime().compareTo(leaveCalEntries.getEndPeriodDateTime()) <=0)){
				flag = true;
			}
		}
		return lpYearStart.getTime();
    }
}
