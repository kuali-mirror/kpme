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
package org.kuali.kpme.tklm.leave.summary.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.kuali.kpme.core.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.accrualcategory.rule.AccrualCategoryRule;
import org.kuali.kpme.core.bo.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.bo.earncode.EarnCode;
import org.kuali.kpme.core.bo.leaveplan.LeavePlan;
import org.kuali.kpme.core.bo.principal.PrincipalHRAttributes;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.tklm.common.LMConstants;
import org.kuali.kpme.tklm.common.TkConstants;
import org.kuali.kpme.tklm.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.leave.block.service.LeaveBlockService;
import org.kuali.kpme.tklm.leave.override.EmployeeOverride;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.leave.summary.LeaveSummary;
import org.kuali.kpme.tklm.leave.summary.LeaveSummaryRow;
import org.kuali.kpme.tklm.leave.workflow.LeaveCalendarDocumentHeader;
import org.kuali.rice.krad.util.ObjectUtils;

public class LeaveSummaryServiceImpl implements LeaveSummaryService {
	private LeaveBlockService leaveBlockService;

    @Override
    public LeaveSummary getLeaveSummaryAsOfDate(String principalId, LocalDate asOfDate) {
        return getLeaveSummary(principalId, asOfDate, asOfDate, null, true);
    }

    public LeaveSummary getLeaveSummaryAsOfDateWithoutFuture(String principalId, LocalDate asOfDate) {
        return getLeaveSummary(principalId, asOfDate, asOfDate, null, false);
    }

    @Override
    public LeaveSummary getLeaveSummary(String principalId, CalendarEntry calendarEntry) {
        return getLeaveSummary(principalId, calendarEntry.getBeginPeriodFullDateTime().toLocalDate(), calendarEntry.getEndPeriodFullDateTime().toLocalDate(), null, true);
    }

    @Override
    public LeaveSummary getLeaveSummaryAsOfDateForAccrualCategory(String principalId, LocalDate asOfDate, String accrualCategory) {
        return getLeaveSummary(principalId, asOfDate, asOfDate, accrualCategory, true);
    }

    @Override
    // startDate is the leave request start date, endDat is leave request end date, usageEndDate is the date before next accrual interval date for leave requst end date
    // will get leave balance up to the next earn interval for a certain date, including usage up to that next earn interval
    public BigDecimal getLeaveBalanceForAccrCatUpToDate(String principalId, LocalDate startDate, LocalDate endDate, String accrualCategory, LocalDate usageEndDate) {
    	BigDecimal leaveBalance = BigDecimal.ZERO;
        if(StringUtils.isEmpty(principalId) || startDate == null || endDate == null || StringUtils.isEmpty(accrualCategory) || usageEndDate == null) {
            return leaveBalance;
        }

        LeaveSummaryRow lsr = new LeaveSummaryRow();
        AccrualCategory ac = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(accrualCategory, endDate);
        
        if(ac != null) {
            LeavePlan lp = HrServiceLocator.getLeavePlanService().getLeavePlan(ac.getLeavePlan(), ac.getEffectiveLocalDate());
            if(lp == null) {
               return leaveBalance;
            }
            PrincipalHRAttributes pha = getPrincipalHrAttributes(principalId, startDate, endDate);
            //until we have something that creates carry over, we need to grab everything.
            // Calculating leave bLocks from Calendar Year start instead of Service Date
            Map<String, LeaveBlock> carryOverBlocks = getLeaveBlockService().getLastCarryOverBlocks(principalId, startDate);
            //remove unwanted carry over blocks from map
            LeaveBlock carryOverBlock = carryOverBlocks.get(accrualCategory);
            carryOverBlocks = new HashMap<String, LeaveBlock>(1);
            if(ObjectUtils.isNotNull(carryOverBlock))
            	carryOverBlocks.put(carryOverBlock.getAccrualCategory(), carryOverBlock);
           
            List<LeaveBlock> leaveBlocks = getLeaveBlockService().getLeaveBlocksSinceCarryOver(principalId, carryOverBlocks, endDate, true);
            List<LeaveBlock> acLeaveBlocks = new ArrayList<LeaveBlock>();
            for(LeaveBlock lb : leaveBlocks) {
            	if(StringUtils.equals(lb.getAccrualCategory(), accrualCategory)) {
            		acLeaveBlocks.add(lb);
            	}
            }
            // get all leave blocks from the requested date to the usageEndDate
            List<LeaveBlock> futureLeaveBlocks = getLeaveBlockService().getLeaveBlocksWithAccrualCategory(principalId, endDate, usageEndDate, accrualCategory);
            EmployeeOverride maxUsageOverride = LmServiceLocator.getEmployeeOverrideService().getEmployeeOverride(principalId, lp.getLeavePlan(), accrualCategory, "MU", usageEndDate);

            //get max balances
            AccrualCategoryRule acRule = HrServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRuleForDate(ac, LocalDate.now(), pha.getServiceLocalDate());
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

            if(maxUsageOverride !=null)
                lsr.setUsageLimit(new BigDecimal(maxUsageOverride.getOverrideValue()));

            //Fetching leaveblocks for accCat with type CarryOver -- This is logic according to the CO blocks creatLed from scheduler job.
            BigDecimal carryOver = BigDecimal.ZERO.setScale(2);
            lsr.setCarryOver(carryOver);

            assignApprovedValuesToRow(lsr, ac.getAccrualCategory(), acLeaveBlocks, lp, startDate, endDate);

            //merge key sets
            if (carryOverBlocks.containsKey(lsr.getAccrualCategory())) {
                carryOver = carryOverBlocks.get(lsr.getAccrualCategory()).getLeaveAmount();
            }
            Set<String> keyset = new HashSet<String>();
            keyset.addAll(lsr.getPriorYearsUsage().keySet());
            keyset.addAll(lsr.getPriorYearsTotalAccrued().keySet());
            for (String key : keyset) {
                BigDecimal value = lsr.getPriorYearsTotalAccrued().get(key);
                if (value == null) {
                    value = BigDecimal.ZERO;
                }
                carryOver = carryOver.add(value);
                BigDecimal use = lsr.getPriorYearsUsage().containsKey(key) ? lsr.getPriorYearsUsage().get(key) : BigDecimal.ZERO;
                carryOver = carryOver.add(use);
                if (acRule != null  && acRule.getMaxCarryOver() != null && acRule.getMaxCarryOver() < carryOver.longValue()) {
                    carryOver = new BigDecimal(acRule.getMaxCarryOver());
                }
            }

            lsr.setCarryOver(carryOver);
            //handle future leave blocks
            assignPendingValuesToRow(lsr, ac.getAccrualCategory(), futureLeaveBlocks);
            //compute Leave Balance
            leaveBalance = lsr.getAccruedBalance().subtract(lsr.getPendingLeaveRequests());
            if(lsr.getUsageLimit()!=null) { //should not set leave balance to usage limit simply because it's not null.
                BigDecimal availableUsage = lsr.getUsageLimit().subtract(lsr.getYtdApprovedUsage().add(lsr.getPendingLeaveRequests()));
                if(leaveBalance.compareTo( availableUsage ) > 0)
                    lsr.setLeaveBalance(availableUsage);
                else
                    lsr.setLeaveBalance(leaveBalance);
            } else { //no usage limit
                lsr.setLeaveBalance(leaveBalance);
            }
        }
        leaveBalance = lsr.getLeaveBalance();
        return leaveBalance;    
    }
    
    protected LeaveSummary getLeaveSummary(String principalId, LocalDate startDate, LocalDate endDate, String accrualCategory, boolean includeFuture) {
        LeaveSummary ls = new LeaveSummary();
        List<LeaveSummaryRow> rows = new ArrayList<LeaveSummaryRow>();

        if(StringUtils.isEmpty(principalId) || startDate == null || endDate == null) {
            return ls;
        }

        Set<String> leavePlans = getLeavePlans(principalId, startDate, endDate);
        PrincipalHRAttributes pha = getPrincipalHrAttributes(principalId, startDate, endDate);
        if (CollectionUtils.isNotEmpty(leavePlans)) {
            for(String aLpString : leavePlans) {
                LeavePlan lp = HrServiceLocator.getLeavePlanService().getLeavePlan(aLpString, startDate);
                if(lp == null) {
                    continue;
                }
                DateTimeFormatter formatter = DateTimeFormat.forPattern("MMMM d");
                DateTimeFormatter formatter2 = DateTimeFormat.forPattern("MMMM d yyyy");
                DateTime entryEndDate = endDate.toDateTimeAtStartOfDay();
                if (entryEndDate.getHourOfDay() == 0) {
                    entryEndDate = entryEndDate.minusDays(1);
                }
                String aString = formatter.print(startDate) + " - " + formatter2.print(entryEndDate);
                ls.setPendingDatesString(aString);

                LeaveCalendarDocumentHeader approvedLcdh = LmServiceLocator.getLeaveCalendarDocumentHeaderService().getMaxEndDateApprovedLeaveCalendar(principalId);
                if(approvedLcdh != null) {
                    DateTime endApprovedDate = approvedLcdh.getEndDateTime();
                    LocalDateTime aLocalTime = approvedLcdh.getEndDateTime().toLocalDateTime();
                    DateTime endApprovedTime = aLocalTime.toDateTime(HrServiceLocator.getTimezoneService().getUserTimezoneWithFallback());
                    if(endApprovedTime.getHourOfDay() == 0) {
                        endApprovedDate = endApprovedDate.minusDays(1);
                    }
                    String datesString = formatter.print(approvedLcdh.getBeginDateTime()) + " - " + formatter2.print(endApprovedDate);
                    ls.setYtdDatesString(datesString);
                }

                //until we have something that creates carry over, we need to grab everything.
                // Calculating leave bLocks from Calendar Year start instead of Service Date
                Map<String, LeaveBlock> carryOverBlocks = getLeaveBlockService().getLastCarryOverBlocks(principalId, startDate);
                
                boolean filterByAccrualCategory = false;
                if (StringUtils.isNotEmpty(accrualCategory)) {
                    filterByAccrualCategory = true;
                    //remove unwanted carry over blocks from map
                    LeaveBlock carryOverBlock = carryOverBlocks.get(accrualCategory);
                    carryOverBlocks = new HashMap<String, LeaveBlock>(1);
                    if(ObjectUtils.isNotNull(carryOverBlock))
                    	carryOverBlocks.put(carryOverBlock.getAccrualCategory(), carryOverBlock);
                }
                List<LeaveBlock> leaveBlocks = getLeaveBlockService().getLeaveBlocksSinceCarryOver(principalId, carryOverBlocks, endDate, filterByAccrualCategory);

                List<LeaveBlock> futureLeaveBlocks = new ArrayList<LeaveBlock>();
                if (includeFuture) {
                    if (!filterByAccrualCategory) {
                        futureLeaveBlocks = getLeaveBlockService().getLeaveBlocks(principalId, endDate, endDate.plusMonths(Integer.parseInt(lp.getPlanningMonths())));
                    } else {
                        futureLeaveBlocks = getLeaveBlockService().getLeaveBlocksWithAccrualCategory(principalId, endDate, endDate.plusMonths(Integer.parseInt(lp.getPlanningMonths())), accrualCategory);
                    }
                }
                Map<String, List<LeaveBlock>> leaveBlockMap = mapLeaveBlocksByAccrualCategory(leaveBlocks);
                Map<String, List<LeaveBlock>> futureLeaveBlockMap = mapLeaveBlocksByAccrualCategory(futureLeaveBlocks);
                List<AccrualCategory> acList = HrServiceLocator.getAccrualCategoryService().getActiveAccrualCategoriesForLeavePlan(lp.getLeavePlan(), endDate);
                if(CollectionUtils.isNotEmpty(acList)) {
                    for(AccrualCategory ac : acList) {
                        if(ac.getShowOnGrid().equals("Y")) {
                        	
                            LeaveSummaryRow lsr = new LeaveSummaryRow();
                            lsr.setAccrualCategory(ac.getAccrualCategory());
                            lsr.setAccrualCategoryId(ac.getLmAccrualCategoryId());
                            //get max balances
                            AccrualCategoryRule acRule = HrServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRuleForDate(ac, endDate, pha.getServiceLocalDate());
                            //accrual category rule id set on a leave summary row will be useful in generating a relevant balance transfer
                            //document from the leave calendar display. Could put this id in the request for balance transfer document.
                            EmployeeOverride maxUsageOverride = LmServiceLocator.getEmployeeOverrideService().getEmployeeOverride(principalId, lp.getLeavePlan(), ac.getAccrualCategory(), "MU", endDate);

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

                            if(maxUsageOverride !=null)
                                lsr.setUsageLimit(new BigDecimal(maxUsageOverride.getOverrideValue()));

                            //Fetching leaveblocks for accCat with type CarryOver -- This is logic according to the CO blocks created from scheduler job.
                            BigDecimal carryOver = BigDecimal.ZERO.setScale(2);
                            lsr.setCarryOver(carryOver);

                            //handle up to current leave blocks
                            //CalendarEntry.getEndPeriodDate passed to fetch leave block amounts on last day of Calendar period
                            assignApprovedValuesToRow(lsr, ac.getAccrualCategory(), leaveBlockMap.get(ac.getAccrualCategory()), lp, startDate, endDate);

                            //how about the leave blocks on the calendar entry being currently handled??
/*                            if(carryOverBlocks.containsKey(lsr.getAccrualCategory())) {
                            	LeaveBlock carryOverBlock = carryOverBlocks.get(lsr.getAccrualCategory());
                            	DateTime carryOverBlockLPStart = HrServiceLocator.getLeavePlanService().getFirstDayOfLeavePlan(lp.getLeavePlan(), carryOverBlock.getLeaveDate());
                            	DateTime currentLPStart = HrServiceLocator.getLeavePlanService().getFirstDayOfLeavePlan(lp.getLeavePlan(), TKUtils.getCurrentDate());
                            	if(carryOverBlockLPStart.equals(currentLPStart))
                            		carryOver = carryOverBlock.getLeaveAmount();
                            }*/
                            //figure out past carry over values!!!
                            //We now have list of past years accrual and use (with ordered keys!!!)

                            //merge key sets
                            if (carryOverBlocks.containsKey(lsr.getAccrualCategory())) {
                                carryOver = carryOverBlocks.get(lsr.getAccrualCategory()).getLeaveAmount();
                                carryOver = carryOver.setScale(2);
                            }
                            Set<String> keyset = new HashSet<String>();
                            keyset.addAll(lsr.getPriorYearsUsage().keySet());
                            keyset.addAll(lsr.getPriorYearsTotalAccrued().keySet());
                            for (String key : keyset) {
                                BigDecimal value = lsr.getPriorYearsTotalAccrued().get(key);
                                if (value == null) {
                                    value = BigDecimal.ZERO;
                                }
                                carryOver = carryOver.add(value);
                                BigDecimal use = lsr.getPriorYearsUsage().containsKey(key) ? lsr.getPriorYearsUsage().get(key) : BigDecimal.ZERO;
                                carryOver = carryOver.add(use);
                                if (acRule != null  && acRule.getMaxCarryOver() != null && acRule.getMaxCarryOver() < carryOver.longValue()) {
                                    carryOver = new BigDecimal(acRule.getMaxCarryOver());
                                }
                            }
                            
                            lsr.setCarryOver(carryOver);
                            if (acRule != null && acRule.getMaxCarryOver() != null) {
                                lsr.setMaxCarryOver(new BigDecimal(acRule.getMaxCarryOver()));
                            }

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

                            rows.add(lsr);
                        }
                    }
                    // let's check for 'empty' accrual categories
                    if (leaveBlockMap.containsKey(null)
                            || futureLeaveBlockMap.containsKey(null)) {
                        LeaveSummaryRow otherLeaveSummary = new LeaveSummaryRow();
                        //otherLeaveSummary.setAccrualCategory("Other");

                        assignApprovedValuesToRow(otherLeaveSummary, null, leaveBlockMap.get(null), lp, startDate, endDate);
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

    private PrincipalHRAttributes getPrincipalHrAttributes(String principalId, LocalDate startDate, LocalDate endDate) {
        PrincipalHRAttributes pha = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, startDate);
        if(pha == null) {	// principal hr attributes does not exist at the beginning of this calendar entry
            pha = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, endDate);
        }
        return pha;
    }

    private Set<String> getLeavePlans(String principalId, LocalDate startDate, LocalDate endDate) {
        Set<String> lpStrings = new HashSet<String>();     //

        PrincipalHRAttributes pha = getPrincipalHrAttributes(principalId, startDate, endDate);
        // get list of principalHrAttributes that become active during this pay period
        List<PrincipalHRAttributes> phaList = HrServiceLocator.getPrincipalHRAttributeService()
                .getActivePrincipalHrAttributesForRange(principalId, startDate, endDate);
        if(pha != null) {
            lpStrings.add(pha.getLeavePlan());
        }
        if(CollectionUtils.isNotEmpty(phaList)) {
            for(PrincipalHRAttributes aPha : phaList) {
                lpStrings.add(aPha.getLeavePlan());
            }
        }

        return lpStrings;
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
    			BigDecimal fte = HrServiceLocator.getJobService().getFteSumForAllActiveLeaveEligibleJobs(principalId, LocalDate.now());
    			BigDecimal adjustedMaxBalance = maxBalance.multiply(fte);
    			List<EmployeeOverride> overrides = LmServiceLocator.getEmployeeOverrideService().getEmployeeOverrides(principalId, LocalDate.now());
    			for(EmployeeOverride override : overrides) {
    				if(StringUtils.equals(override.getOverrideType(),TkConstants.EMPLOYEE_OVERRIDE_TYPE.get("MB"))
    						&& override.isActive()) {
    					adjustedMaxBalance = new BigDecimal(override.getOverrideValue());
    					break;
    				}
    			}
    			if(adjustedMaxBalance.compareTo(lsr.getAccruedBalance()) < 0) {
    				if(StringUtils.equals(accrualCategoryRule.getActionAtMaxBalance(), HrConstants.ACTION_AT_MAX_BALANCE.TRANSFER) &&
    						StringUtils.equals(accrualCategoryRule.getMaxBalanceActionFrequency(),HrConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND))
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
    			BigDecimal fte = HrServiceLocator.getJobService().getFteSumForAllActiveLeaveEligibleJobs(principalId, LocalDate.now());
    			BigDecimal adjustedMaxBalance = maxBalance.multiply(fte);
    			List<EmployeeOverride> overrides = LmServiceLocator.getEmployeeOverrideService().getEmployeeOverrides(principalId, LocalDate.now());
    			for(EmployeeOverride override : overrides) {
    				if(StringUtils.equals(override.getOverrideType(),TkConstants.EMPLOYEE_OVERRIDE_TYPE.get("MB"))
    						&& override.isActive()) {
    					adjustedMaxBalance = new BigDecimal(override.getOverrideValue());
    					break;
    				}
    			}
    			if(adjustedMaxBalance.compareTo(lsr.getAccruedBalance()) < 0) {
    				if(StringUtils.equals(accrualCategoryRule.getActionAtMaxBalance(), HrConstants.ACTION_AT_MAX_BALANCE.PAYOUT) &&
    						StringUtils.equals(accrualCategoryRule.getMaxBalanceActionFrequency(),HrConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND))
    					payoutable = true;
    			}
    		}
    	}
    	lsr.setPayoutable(payoutable);
    }
    
	private void assignApprovedValuesToRow(LeaveSummaryRow lsr, String accrualCategory, List<LeaveBlock> approvedLeaveBlocks, LeavePlan lp, LocalDate ytdEarnedEffectiveDate, LocalDate effectiveDate) {

        SortedMap<String, BigDecimal> yearlyAccrued = new TreeMap<String, BigDecimal>();
        SortedMap<String, BigDecimal> yearlyUsage = new TreeMap<String, BigDecimal>();
        BigDecimal accrualedBalance = BigDecimal.ZERO.setScale(2);
		BigDecimal approvedUsage = BigDecimal.ZERO.setScale(2);
		BigDecimal fmlaUsage = BigDecimal.ZERO.setScale(2);

        LocalDate cutOffDateToCheck = ytdEarnedEffectiveDate != null ? ytdEarnedEffectiveDate : effectiveDate;
        DateTime cutOffDate = HrServiceLocator.getLeavePlanService().getFirstDayOfLeavePlan(lp.getLeavePlan(), cutOffDateToCheck).minus(1);

        if (CollectionUtils.isNotEmpty(approvedLeaveBlocks)) {
            // create it here so we don't need to get instance every loop iteration
            for(LeaveBlock aLeaveBlock : approvedLeaveBlocks) {
             	// check if leave date is before the next calendar start.
            	if(!aLeaveBlock.getLeaveBlockType().equals(LMConstants.LEAVE_BLOCK_TYPE.CARRY_OVER)
                        && aLeaveBlock.getLeaveDate().getTime() < effectiveDate.toDate().getTime()) {
                    if((StringUtils.isBlank(accrualCategory) && StringUtils.isBlank(aLeaveBlock.getAccrualCategory()))
                            || (StringUtils.isNotBlank(aLeaveBlock.getAccrualCategory())
                                && StringUtils.equals(aLeaveBlock.getAccrualCategory(), accrualCategory))) {
                        if(aLeaveBlock.getLeaveAmount().compareTo(BigDecimal.ZERO) >= 0
                                && !aLeaveBlock.getLeaveBlockType().equals(LMConstants.LEAVE_BLOCK_TYPE.LEAVE_CALENDAR)) {
                            if(!(StringUtils.equals(HrConstants.REQUEST_STATUS.DISAPPROVED, aLeaveBlock.getRequestStatus()) ||
                            		StringUtils.equals(HrConstants.REQUEST_STATUS.DEFERRED, aLeaveBlock.getRequestStatus()))) {
                                if (aLeaveBlock.getLeaveLocalDate().toDate().getTime() <= cutOffDate.toDate().getTime()) {
                                    String yearKey = getYearKey(aLeaveBlock.getLeaveLocalDate(), lp);
                                    BigDecimal co = yearlyAccrued.get(yearKey);
                                    if (co == null) {
                                        co = BigDecimal.ZERO.setScale(2);
                                    }
                                    co = co.add(aLeaveBlock.getLeaveAmount());
                                    yearlyAccrued.put(yearKey, co);
                                } else if(aLeaveBlock.getLeaveDate().getTime() < ytdEarnedEffectiveDate.toDate().getTime()) {
                                    accrualedBalance = accrualedBalance.add(aLeaveBlock.getLeaveAmount());
                                }
                           }
                        } else {
                            BigDecimal currentLeaveAmount = aLeaveBlock.getLeaveAmount().compareTo(BigDecimal.ZERO) > 0 ? aLeaveBlock.getLeaveAmount().negate() : aLeaveBlock.getLeaveAmount();
                            //we only want this for the current calendar!!!
                            if(!(StringUtils.equals(HrConstants.REQUEST_STATUS.DISAPPROVED, aLeaveBlock.getRequestStatus()) ||
                            		StringUtils.equals(HrConstants.REQUEST_STATUS.DEFERRED, aLeaveBlock.getRequestStatus()))) {
                                if (aLeaveBlock.getLeaveLocalDate().toDate().getTime() > cutOffDate.toDate().getTime()) {
                                    EarnCode ec = HrServiceLocator.getEarnCodeService().getEarnCode(aLeaveBlock.getEarnCode(), aLeaveBlock.getLeaveLocalDate());
                                    if (ec != null && StringUtils.equals(ec.getAccrualBalanceAction(), HrConstants.ACCRUAL_BALANCE_ACTION.USAGE)){
                                        approvedUsage = approvedUsage.add(currentLeaveAmount);
                                    }
                                    if(ec != null && ec.getFmla().equals("Y")) {
                                        fmlaUsage = fmlaUsage.add(aLeaveBlock.getLeaveAmount());
                                    }
                                } else {
                                    //these usages are for previous years, to help figure out correct carry over values
                                    String yearKey = getYearKey(aLeaveBlock.getLeaveLocalDate(), lp);
                                    BigDecimal use = yearlyUsage.get(yearKey);
                                    if (use == null) {
                                        use = BigDecimal.ZERO.setScale(2);
                                    }
                                    use = use.add(currentLeaveAmount);
                                    yearlyUsage.put(yearKey, use);
                                }
                            }
                        }

                        //}
                    }
                } else {
                    //we can actually use the carry over block!!

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

    private String getYearKey(LocalDate leaveDate, LeavePlan lp){
        String yearKey = Integer.toString(leaveDate.getYear());
        
        LocalDate leavePlanDate = new LocalDate(leaveDate.getYear(), Integer.parseInt(lp.getCalendarYearStartMonth()) - 1, Integer.parseInt(lp.getCalendarYearStartDayOfMonth()));

        if (leaveDate.isBefore(leavePlanDate)) {
            yearKey = Integer.toString(leaveDate.getYear() - 1);
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
	
	@Override
	public List<Date> getLeaveSummaryDates(CalendarEntry calendarEntry) {
		List<Date> leaveSummaryDates = new ArrayList<Date>();

		DateTime start = calendarEntry.getBeginPeriodLocalDateTime().toDateTime();
		DateTime end = calendarEntry.getEndPeriodLocalDateTime().toDateTime();
        Interval interval = new Interval(start, end);

        for (DateTime day = interval.getStart(); day.isBefore(interval.getEnd()); day = day.plusDays(1)) {
        	leaveSummaryDates.add(day.toLocalDate().toDateTimeAtStartOfDay().toDate());
        }
		 
		 return leaveSummaryDates;
	}

    protected LeaveBlockService getLeaveBlockService() {
        if (leaveBlockService == null) {
            leaveBlockService = LmServiceLocator.getLeaveBlockService();
        }
        return leaveBlockService;
    }


}

