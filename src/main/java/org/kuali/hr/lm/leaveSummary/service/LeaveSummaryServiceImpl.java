package org.kuali.hr.lm.leaveSummary.service;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.joda.time.LocalDateTime;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.accrual.AccrualCategoryRule;
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

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class LeaveSummaryServiceImpl implements LeaveSummaryService {
	private LeaveBlockService leaveBlockService;

    @Override
    public LeaveSummary getLeaveSummary(String principalId, CalendarEntries calendarEntry) throws Exception {
        LeaveSummary ls = new LeaveSummary();
        List<LeaveSummaryRow> rows = new ArrayList<LeaveSummaryRow>();

        if(StringUtils.isEmpty(principalId) || calendarEntry == null) {
            return ls;
        }

        PrincipalHRAttributes pha = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, calendarEntry.getBeginPeriodDate());
        // get list of principalHrAttributes that become active during this pay period
        List<PrincipalHRAttributes> phaList = TkServiceLocator.getPrincipalHRAttributeService()
                .getActivePrincipalHrAttributesForRange(principalId, calendarEntry.getBeginPeriodDate(), calendarEntry.getEndPeriodDate());

        Set<String> lpStrings = new HashSet<String>();
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
                    Date yearStartDate = this.calendarYearStartDate(lp.getCalendarYearStart(), endApprovedDate);
                    String datesString = formatter.format(yearStartDate) + " - " + formatter2.format(endApprovedDate);
                    ls.setYtdDatesString(datesString);
                }

                List<LeaveBlock> leaveBlocks = getLeaveBlockService().getLeaveBlocks(principalId, pha.getServiceDate(), calendarEntry.getEndPeriodDateTime());
                List<LeaveBlock> futureLeaveBlocks = getLeaveBlockService().getLeaveBlocks(principalId, calendarEntry.getEndPeriodDateTime(), calendarEntry.getEndLocalDateTime().toDateTime().plusYears(5).toDate());
                List<AccrualCategory> acList = TkServiceLocator.getAccrualCategoryService().getActiveAccrualCategoriesForLeavePlan(lp.getLeavePlan(), calendarEntry.getEndPeriodDate());
                if(CollectionUtils.isNotEmpty(acList)) {
                    for(AccrualCategory ac : acList) {
                        if(ac.getShowOnGrid().equals("Y")) {
                            LeaveSummaryRow lsr = new LeaveSummaryRow();
                            lsr.setAccrualCategory(ac.getAccrualCategory());
                            //get max balances
                            AccrualCategoryRule acRule = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRuleForDate(ac, calendarEntry.getEndPeriodDateTime(), pha.getServiceDate());
                            if(acRule != null &&
                                    (acRule.getMaxBalance()!= null
                                      || acRule.getMaxUsage() != null)) {
                                if (acRule.getMaxUsage() != null) {
                                    lsr.setUsageLimit(new BigDecimal(acRule.getMaxUsage()));
                                } else {
                                    lsr.setUsageLimit(acRule.getMaxBalance());
                                }

                            } else {
                                lsr.setUsageLimit(BigDecimal.ZERO);
                            }

                            //handle up to current leave blocks
                            assignApprovedValuesToRow(lsr, ac, leaveBlocks);

                            //Check for going over max carry over
                            if (acRule != null
                                    && acRule.getMaxCarryOver() != null
                                    && acRule.getMaxCarryOver() < lsr.getCarryOver().longValue()) {
                                lsr.setCarryOver(new BigDecimal(acRule.getMaxCarryOver()));
                            }

                            //handle future leave blocks
                            assignPendingValuesToRow(lsr, ac, futureLeaveBlocks);

                            //compute Leave Balance
                            BigDecimal leaveBalance = lsr.getAccruedBalance().subtract(lsr.getPendingLeaveRequests());
                            lsr.setLeaveBalance(leaveBalance.compareTo(lsr.getUsageLimit()) <= 0 ? leaveBalance : lsr.getUsageLimit());

                            rows.add(lsr);
                        }
                    }
                }
            }
        }
        ls.setLeaveSummaryRows(rows);
        return ls;
    }



	
	private void assignApprovedValuesToRow(LeaveSummaryRow lsr, AccrualCategory ac, List<LeaveBlock> approvedLeaveBlocks ) {
        //List<TimeOffAccrual> timeOffAccruals = TkServiceLocator.getTimeOffAccrualService().getTimeOffAccrualsCalc(principalId, lsr.get)
		BigDecimal carryOver = BigDecimal.ZERO;
        BigDecimal accrualedBalance = BigDecimal.ZERO;
		BigDecimal approvedUsage = BigDecimal.ZERO;
		BigDecimal fmlaUsage = BigDecimal.ZERO;

        //TODO: probably should get from Leave Plan
        Timestamp priorYearCutOff = new Timestamp(new DateMidnight().withWeekOfWeekyear(1).withDayOfWeek(1).toDate().getTime());

		for(LeaveBlock aLeaveBlock : approvedLeaveBlocks) {
			if(aLeaveBlock.getAccrualCategoryId().equals(ac.getLmAccrualCategoryId())) {
				if(StringUtils.isNotEmpty(aLeaveBlock.getRequestStatus())
						&& aLeaveBlock.getRequestStatus().equals(LMConstants.REQUEST_STATUS.APPROVED)) {
					if(aLeaveBlock.getLeaveAmount().compareTo(BigDecimal.ZERO) >= 0) {
                        if (aLeaveBlock.getLeaveDate().getTime() <= priorYearCutOff.getTime()) {
                            carryOver = carryOver.add(aLeaveBlock.getLeaveAmount());
                        } else {
						    accrualedBalance = accrualedBalance.add(aLeaveBlock.getLeaveAmount());
                        }
					} else {
						approvedUsage = approvedUsage.add(aLeaveBlock.getLeaveAmount());
						EarnCode ec = TkServiceLocator.getEarnCodeService().getEarnCode(aLeaveBlock.getEarnCode(), aLeaveBlock.getLeaveDate());
				    	if(ec != null && ec.getFmla().equals("Y")) {
				    		fmlaUsage = fmlaUsage.add(aLeaveBlock.getLeaveAmount());
				    	}
					}
					
				}
			}
		}
        lsr.setCarryOver(carryOver);
		lsr.setYtdAccruedBalance(accrualedBalance);
		lsr.setYtdApprovedUsage(approvedUsage.negate());
		lsr.setFmlaUsage(fmlaUsage.negate());
		//lsr.setLeaveBalance(lsr.getYtdAccruedBalance().add(approvedUsage));
	}
	
	private void assignPendingValuesToRow(LeaveSummaryRow lsr, AccrualCategory ac, List<LeaveBlock> pendingLeaveBlocks ) {
		BigDecimal pendingAccrual= BigDecimal.ZERO;
		BigDecimal pendingRequests = BigDecimal.ZERO;
		
		for(LeaveBlock aLeaveBlock : pendingLeaveBlocks) {
			if(aLeaveBlock.getAccrualCategoryId() != null && aLeaveBlock.getAccrualCategoryId().equals(ac.getLmAccrualCategoryId())) {
				if(aLeaveBlock.getLeaveAmount().compareTo(BigDecimal.ZERO) >= 0) {
					pendingAccrual = pendingAccrual.add(aLeaveBlock.getLeaveAmount());
				} else {
					pendingRequests = pendingRequests.add(aLeaveBlock.getLeaveAmount());
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
	public List<String> getHeaderForSummary(CalendarEntries cal) {
		 List<String> header = new ArrayList<String>();	
		 LocalDateTime startDate = cal.getBeginLocalDateTime();
	     LocalDateTime endDate = cal.getEndLocalDateTime();
	     if (endDate.get(DateTimeFieldType.hourOfDay()) != 0 || endDate.get(DateTimeFieldType.minuteOfHour()) != 0 ||
	                endDate.get(DateTimeFieldType.secondOfMinute()) != 0) {
	            endDate = endDate.plusDays(1);
	     }
		 for (LocalDateTime currentDate = startDate; currentDate.compareTo(endDate) < 0; currentDate = currentDate.plusDays(1)) {
			 header.add(currentDate.toString(TkConstants.DT_ABBREV_DATE_FORMAT));
		 }
		 return header;
	}

    protected LeaveBlockService getLeaveBlockService() {
        if (leaveBlockService == null) {
            leaveBlockService = TkServiceLocator.getLeaveBlockService();
        }
        return leaveBlockService;
    }

}
