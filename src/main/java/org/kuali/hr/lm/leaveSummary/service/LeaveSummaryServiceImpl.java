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
    public LeaveSummary getLeaveSummaryAsOfDate(String principalId, java.sql.Date asOfDate) {
        return getLeaveSummary(principalId, asOfDate, asOfDate, null, true);
    }

    public LeaveSummary getLeaveSummaryAsOfDateWithoutFuture(String principalId, java.sql.Date asOfDate) {
        return getLeaveSummary(principalId, asOfDate, asOfDate, null, false);
    }

    @Override
    public LeaveSummary getLeaveSummary(String principalId, CalendarEntries calendarEntry) {
        return getLeaveSummary(principalId, calendarEntry.getBeginPeriodDate(), calendarEntry.getEndPeriodDate(), null, true);
    }

    @Override
    public LeaveSummary getLeaveSummaryAsOfDateForAccrualCategory(String principalId, java.sql.Date asOfDate, String accrualCategory) {
        return getLeaveSummary(principalId, asOfDate, asOfDate, accrualCategory, true);
    }

    @Override
    // startDate is the leave request start date, endDat is leave request end date, usageEndDate is the date before next accrual interval date for leave requst end date
    // will get leave balance up to the next earn interval for a certain date, including usage up to that next earn interval
    public BigDecimal getLeaveBalanceForAccrCatUpToDate(String principalId,
            java.sql.Date startDate,	
            java.sql.Date endDate,
            String accrualCategory,
            Date usageEndDate) {
    	BigDecimal leaveBalance = BigDecimal.ZERO;
        if(StringUtils.isEmpty(principalId) || startDate == null || endDate == null || StringUtils.isEmpty(accrualCategory) || usageEndDate == null) {
            return leaveBalance;
        }

        LeaveSummaryRow lsr = new LeaveSummaryRow();
        AccrualCategory ac = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(accrualCategory, endDate);
        
        if(ac != null) {
            LeavePlan lp = TkServiceLocator.getLeavePlanService().getLeavePlan(ac.getLeavePlan(), ac.getEffectiveDate());
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
           
            List<LeaveBlock> leaveBlocks = getLeaveBlockService().getLeaveBlocksSinceCarryOver(principalId, carryOverBlocks, (new LocalDateTime(endDate)).toDateTime(), true);
            List<LeaveBlock> acLeaveBlocks = new ArrayList<LeaveBlock>();
            for(LeaveBlock lb : leaveBlocks) {
            	if(lb.getAccrualCategory().equals(accrualCategory)) {
            		acLeaveBlocks.add(lb);
            	}
            }
            // get all leave blocks from the requested date to the usageEndDate
            List<LeaveBlock> futureLeaveBlocks = getLeaveBlockService().getLeaveBlocksWithAccrualCategory(principalId, endDate, usageEndDate, accrualCategory);
            List<EmployeeOverride> employeeOverrides = TkServiceLocator.getEmployeeOverrideService().getEmployeeOverrides(principalId, new java.sql.Date(usageEndDate.getTime()));
         
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

            //Fetching leaveblocks for accCat with type CarryOver -- This is logic according to the CO blocks created from scheduler job.
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
    
    protected LeaveSummary getLeaveSummary(String principalId,
                                           java.sql.Date startDate,
                                           java.sql.Date endDate,
                                           String accrualCategory,
                                           boolean includeFuture) {
        LeaveSummary ls = new LeaveSummary();
        List<LeaveSummaryRow> rows = new ArrayList<LeaveSummaryRow>();

        if(StringUtils.isEmpty(principalId) || startDate == null || endDate == null) {
            return ls;
        }

        Set<String> leavePlans = getLeavePlans(principalId, startDate, endDate);
        PrincipalHRAttributes pha = getPrincipalHrAttributes(principalId, startDate, endDate);
        if (CollectionUtils.isNotEmpty(leavePlans)) {
            for(String aLpString : leavePlans) {
                LeavePlan lp = TkServiceLocator.getLeavePlanService().getLeavePlan(aLpString, startDate);
                if(lp == null) {
                    continue;
                }
                DateFormat formatter = new SimpleDateFormat("MMMM d");
                DateFormat formatter2 = new SimpleDateFormat("MMMM d yyyy");
                DateTime entryEndDate = new LocalDateTime(endDate).toDateTime();
                if (entryEndDate.getHourOfDay() == 0) {
                    entryEndDate = entryEndDate.minusDays(1);
                }
                String aString = formatter.format(startDate) + " - " + formatter2.format(entryEndDate.toDate());
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
                List<LeaveBlock> leaveBlocks = getLeaveBlockService().getLeaveBlocksSinceCarryOver(principalId, carryOverBlocks, (new LocalDateTime(endDate)).toDateTime(), filterByAccrualCategory);

                List<LeaveBlock> futureLeaveBlocks = new ArrayList<LeaveBlock>();
                if (includeFuture) {
                    if (!filterByAccrualCategory) {
                        futureLeaveBlocks = getLeaveBlockService().getLeaveBlocks(principalId, endDate, (new LocalDateTime(endDate)).toDateTime().plusMonths(Integer.parseInt(lp.getPlanningMonths())).toDate());
                    } else {
                        futureLeaveBlocks = getLeaveBlockService().getLeaveBlocksWithAccrualCategory(principalId, endDate, (new LocalDateTime(endDate)).toDateTime().plusMonths(Integer.parseInt(lp.getPlanningMonths())).toDate(), accrualCategory);
                    }
                }
                Map<String, List<LeaveBlock>> leaveBlockMap = mapLeaveBlocksByAccrualCategory(leaveBlocks);
                Map<String, List<LeaveBlock>> futureLeaveBlockMap = mapLeaveBlocksByAccrualCategory(futureLeaveBlocks);
                List<AccrualCategory> acList = TkServiceLocator.getAccrualCategoryService().getActiveAccrualCategoriesForLeavePlan(lp.getLeavePlan(), endDate);
                if(CollectionUtils.isNotEmpty(acList)) {
                    List<EmployeeOverride> employeeOverrides = TkServiceLocator.getEmployeeOverrideService().getEmployeeOverrides(principalId,TKUtils.getCurrentDate()); //current date ok?
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

                            //Fetching leaveblocks for accCat with type CarryOver -- This is logic according to the CO blocks created from scheduler job.
                            BigDecimal carryOver = BigDecimal.ZERO.setScale(2);
                            lsr.setCarryOver(carryOver);

                            //handle up to current leave blocks
                            //CalendarEntry.getEndPeriodDate passed to fetch leave block amounts on last day of Calendar period
                            assignApprovedValuesToRow(lsr, ac.getAccrualCategory(), leaveBlockMap.get(ac.getAccrualCategory()), lp, startDate, endDate);

                            //how about the leave blocks on the calendar entry being currently handled??

                            //figure out past carry over values!!!
                            //We now have list of past years accrual and use (with ordered keys!!!)

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
                            DateTime startDateTime = new DateTime(startDate);
                            DateTime endDateTime = new DateTime(endDate);
                            Interval interval = new Interval(startDateTime,endDateTime);
                            if(interval.containsNow() || !endDate.after(TKUtils.getCurrentDate())) {
                                //current or past calendar is being used for leave summary calculation
                                //should only allow on-demand transfers if calendar document has status initiated.
                                //obviously this code block is executed for all non-approved calendars, displaying the buttons
                                //for calendar documents that have already been routed.
                                //Depending on requirements for on demand display, may need to update this.
                                markTransferable(lsr,acRule,principalId);
                                markPayoutable(lsr,acRule,principalId);
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

    private PrincipalHRAttributes getPrincipalHrAttributes(String principalId, Date startDate, Date endDate) {
        PrincipalHRAttributes pha = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, startDate);
        if(pha == null) {	// principal hr attributes does not exist at the beginning of this calendar entry
            pha = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, endDate);
        }
        return pha;
    }

    private Set<String> getLeavePlans(String principalId, Date startDate, Date endDate) {
        Set<String> lpStrings = new HashSet<String>();     //

        PrincipalHRAttributes pha = getPrincipalHrAttributes(principalId, startDate, endDate);
        // get list of principalHrAttributes that become active during this pay period
        List<PrincipalHRAttributes> phaList = TkServiceLocator.getPrincipalHRAttributeService()
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

    /*@Override
    public LeaveSummary getLeaveSummary(String principalId, CalendarEntries calendarEntry, Calendar previousYearCalendarStart) {
        LeaveSummary ls = new LeaveSummary();
        List<LeaveSummaryRow> rows = new ArrayList<LeaveSummaryRow>();

        java.sql.Date startDate = calendarEntry.getBeginPeriodDate();
        java.sql.Date endDate = calendarEntry.getEndPeriodDate();
        if(StringUtils.isEmpty(principalId) || startDate == null || endDate == null) {
            return ls;
        }

        Set<String> leavePlans = getLeavePlans(principalId, startDate, endDate);
        PrincipalHRAttributes pha = getPrincipalHrAttributes(principalId, startDate, endDate);
        if (CollectionUtils.isNotEmpty(leavePlans)) {

            for(String aLpString : leavePlans) {
                LeavePlan lp = TkServiceLocator.getLeavePlanService().getLeavePlan(aLpString, calendarEntry.getEndPeriodDate());
                if(lp == null) {
                    continue;
                }
                DateFormat formatter = new SimpleDateFormat("MMMM d");
                DateFormat formatter2 = new SimpleDateFormat("MMMM d yyyy");
                DateTime entryEndDate = new LocalDateTime(endDate).toDateTime();
                if (entryEndDate.getHourOfDay() == 0) {
                    entryEndDate = entryEndDate.minusDays(1);
                }
                String aString = formatter.format(startDate) + " - " + formatter2.format(entryEndDate.toDate());
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
                Map<String, LeaveBlock> carryOverBlocks = getLeaveBlockService().getLastCarryOverBlocks(principalId, startDate);
                List<LeaveBlock> leaveBlocks = getLeaveBlockService().getLeaveBlocksSinceCarryOver(principalId, carryOverBlocks, (new LocalDateTime(endDate)).toDateTime());
                
                //todo compute end date based off planning months
                List<LeaveBlock> futureLeaveBlocks = getLeaveBlockService().getLeaveBlocks(principalId, endDate, (new LocalDateTime(endDate)).toDateTime().plusMonths(Integer.parseInt(lp.getPlanningMonths())).toDate());
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

                            //Fetching leaveblocks for accCat with type CarryOver -- This is logic according to the CO blocks created from scheduler job.
                            BigDecimal carryOver = BigDecimal.ZERO.setScale(2);
                            lsr.setCarryOver(carryOver);

                            //handle up to current leave blocks
                            //CalendarEntry.getEndPeriodDate passed to fetch leave block amounts on last day of Calendar period
                            assignApprovedValuesToRow(lsr, ac.getAccrualCategory(), leaveBlockMap.get(ac.getAccrualCategory()), lp, startDate, endDate);
                          
                            //how about the leave blocks on the calendar entry being currently handled??

                            //figure out past carry over values!!!
                            //We now have list of past years accrual and use (with ordered keys!!!)

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
                            DateTime startDateTime = new DateTime(startDate);
                            DateTime endDateTime = new DateTime(endDate);
                            Interval interval = new Interval(startDateTime,endDateTime);
                            if(interval.containsNow() || !endDate.after(TKUtils.getCurrentDate())) {
                                //current or past calendar is being used for leave summary calculation
                                //should only allow on-demand transfers if calendar document has status initiated.
                                //obviously this code block is executed for all non-approved calendars, displaying the buttons
                                //for calendar documents that have already been routed.
                                //Depending on requirements for on demand display, may need to update this.
                            markTransferable(lsr,acRule,principalId);
                            markPayoutable(lsr,acRule,principalId);
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
    }*/

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
    
	private void assignApprovedValuesToRow(LeaveSummaryRow lsr, String accrualCategory, List<LeaveBlock> approvedLeaveBlocks, LeavePlan lp, Date ytdEarnedEffectiveDate, Date effectiveDate) {

        SortedMap<String, BigDecimal> yearlyAccrued = new TreeMap<String, BigDecimal>();
        SortedMap<String, BigDecimal> yearlyUsage = new TreeMap<String, BigDecimal>();
        BigDecimal accrualedBalance = BigDecimal.ZERO.setScale(2);
		BigDecimal approvedUsage = BigDecimal.ZERO.setScale(2);
		BigDecimal fmlaUsage = BigDecimal.ZERO.setScale(2);

		// To calculate the proper priorYearCutOffDate
		Calendar effectiveDateCal = Calendar.getInstance();
		effectiveDateCal.setTime(effectiveDate);
		effectiveDateCal.add(Calendar.DATE, -1);
		effectiveDateCal.set(Calendar.HOUR_OF_DAY, 0);
		effectiveDateCal.set(Calendar.MINUTE, 0);
		effectiveDateCal.set(Calendar.SECOND, 0);
		effectiveDateCal.set(Calendar.MILLISECOND, 0);

        int priorYearCutOffMonth = lp.getCalendarYearStart() == null ? 1 : Integer.parseInt(lp.getCalendarYearStart().substring(0,2));
        int priorYearCutOffDay = lp.getCalendarYearStart() == null ? 1 : Integer.parseInt(lp.getCalendarYearStart().substring(3,5));
        DateMidnight cutOffDate = new DateMidnight(effectiveDateCal.getTime()).withMonthOfYear(priorYearCutOffMonth).withDayOfMonth(priorYearCutOffDay);

        if (cutOffDate.isAfter(effectiveDateCal.getTime().getTime())) {
            cutOffDate = cutOffDate.withYear(cutOffDate.getYear() - 1);
        }
        
        cutOffDate = cutOffDate.minusDays(1);

        Timestamp priorYearCutOff = new Timestamp(cutOffDate.getMillis());

        if (CollectionUtils.isNotEmpty(approvedLeaveBlocks)) {
            // create it here so we don't need to get instance every loop iteration
            for(LeaveBlock aLeaveBlock : approvedLeaveBlocks) {
             	// check if leave date is before the next calendar start.
            	if(!aLeaveBlock.getLeaveBlockType().equals(LMConstants.LEAVE_BLOCK_TYPE.CARRY_OVER)
                        && aLeaveBlock.getLeaveDate().getTime() < effectiveDate.getTime()) {
                    if((StringUtils.isBlank(accrualCategory) && StringUtils.isBlank(aLeaveBlock.getAccrualCategory()))
                            || (StringUtils.isNotBlank(aLeaveBlock.getAccrualCategory())
                                && StringUtils.equals(aLeaveBlock.getAccrualCategory(), accrualCategory))) {
                        if(aLeaveBlock.getLeaveAmount().compareTo(BigDecimal.ZERO) >= 0
                                && !aLeaveBlock.getLeaveBlockType().equals(LMConstants.LEAVE_BLOCK_TYPE.LEAVE_CALENDAR)) {
                            if(!(StringUtils.equals(LMConstants.REQUEST_STATUS.DISAPPROVED, aLeaveBlock.getRequestStatus()) ||
                            		StringUtils.equals(LMConstants.REQUEST_STATUS.DEFERRED, aLeaveBlock.getRequestStatus()))) {
                                if (aLeaveBlock.getLeaveDate().getTime() <= priorYearCutOff.getTime()) {
                                    String yearKey = getYearKey(aLeaveBlock.getLeaveDate(), lp);
                                    BigDecimal co = yearlyAccrued.get(yearKey);
                                    if (co == null) {
                                        co = BigDecimal.ZERO.setScale(2);
                                    }
                                    co = co.add(aLeaveBlock.getLeaveAmount());
                                    yearlyAccrued.put(yearKey, co);
                                } else if(aLeaveBlock.getLeaveDate().getTime() < ytdEarnedEffectiveDate.getTime()) {
                                    accrualedBalance = accrualedBalance.add(aLeaveBlock.getLeaveAmount());
                                }
                           }
                        } else {
                            BigDecimal currentLeaveAmount = aLeaveBlock.getLeaveAmount().compareTo(BigDecimal.ZERO) > 0 ? aLeaveBlock.getLeaveAmount().negate() : aLeaveBlock.getLeaveAmount();
                            //we only want this for the current calendar!!!
                            if(!(StringUtils.equals(LMConstants.REQUEST_STATUS.DISAPPROVED, aLeaveBlock.getRequestStatus()) ||
                            		StringUtils.equals(LMConstants.REQUEST_STATUS.DEFERRED, aLeaveBlock.getRequestStatus()))) {
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


}

