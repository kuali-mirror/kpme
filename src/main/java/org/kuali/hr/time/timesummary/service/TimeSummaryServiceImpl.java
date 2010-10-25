package org.kuali.hr.time.timesummary.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.kuali.hr.time.earngroup.EarnGroup;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.TimeHourDetail;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.timesummary.TimeSummary;
import org.kuali.hr.time.timesummary.TimeSummaryRow;
import org.kuali.hr.time.timesummary.TimeSummarySection;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;

public class TimeSummaryServiceImpl implements TimeSummaryService{

	@Override
	public TimeSummary getTimeSummary(TimesheetDocument timesheetDocument) {
		TimeSummary timeSummary = new TimeSummary();
		
		if(timesheetDocument.getTimeBlocks() == null) {
			return timeSummary;
		}
		
		timeSummary.setPayBeginDate(timesheetDocument.getPayCalendarEntry()
				.getBeginPeriodDateTime());
		timeSummary.setPayEndDate(timesheetDocument.getPayCalendarEntry()
				.getEndPeriodDateTime());
		timeSummary.setNumberOfDays(TKUtils.getDaysBetween(timeSummary
				.getPayBeginDate(), timeSummary.getPayEndDate()));

		Map<String, String> dateToDateDescrMap = new HashMap<String, String>();

		Map<String, TimeSummarySection> earnGroupToSection = new HashMap<String, TimeSummarySection>();

		// Iterate over date range and setup a map of days to day strings
		Calendar payBeginCal = GregorianCalendar.getInstance();
		Calendar payEndCal = GregorianCalendar.getInstance();

		payBeginCal.setTime(timeSummary.getPayBeginDate());
		payEndCal.setTime(timeSummary.getPayEndDate());

		int dayCount = 1;
		//Build up map for date to day description
		while (payBeginCal.before(payEndCal) || payBeginCal.equals(payEndCal)) {
			String displayName = (payBeginCal.get(Calendar.MONTH) + 1) + "/"
					+ payBeginCal.get(Calendar.DAY_OF_MONTH);
			Date dt = new Date(payBeginCal.getTime().getTime());
			dateToDateDescrMap.put(dt.toString(), displayName);
			timeSummary.getDateDescr().add(displayName);
			payBeginCal.add(Calendar.DAY_OF_MONTH, 1);
			//Add weekly total column
			if ((dayCount % 7) == 0) {
				timeSummary.getDateDescr().add("Week " + (dayCount / 7));
			}
			dayCount++;

		}
		// add period total column
		timeSummary.getDateDescr().add("Period Total");

		TimeSummaryRow workedHours = timeSummary.getWorkedHours();

		
		
		//Iterate over timeblocks /timehourdetails
		for (TimeBlock timeBlock : timesheetDocument.getTimeBlocks()) {
			for (TimeHourDetail timeHourDetail : timeBlock.getTimeHourDetails()) {
				// break timehourdetail into days
				Map<Timestamp, BigDecimal> dateToHoursMap = TKUtils
						.getDateToHoursMap(timeBlock, timeHourDetail);
				// iterate over each timestamp days
				for (Timestamp timestamp : dateToHoursMap.keySet()) {
					Date day = new Date(timestamp.getTime());
					String dayDescr = dateToDateDescrMap.get(day.toString());
					// Setup worked hours
					if (workedHours.getDayToHours().get(dayDescr) != null) {
						BigDecimal currHours = workedHours.getDayToHours().get(
								dayDescr);
						currHours.add(dateToHoursMap.get(timestamp),
								TkConstants.MATH_CONTEXT);
						workedHours.getDayToHours().put(dayDescr, currHours);
					} else {
						workedHours.getDayToHours().put(dayDescr,
								dateToHoursMap.get(timestamp));
					}

					EarnGroup earnGroup = TkServiceLocator
							.getEarnGroupService()
							.getEarnGroupSummaryForEarnCode(
									timeHourDetail.getEarnCode(),
									new Date(timeBlock.getBeginTimestamp()
											.getTime()));

					// setup other earn group to catch any earncodes not setup
					// in summary
					if (earnGroup == null) {
						earnGroup = new EarnGroup();
						earnGroup.setEarnGroup("Other");
					}

					String assignDescr = timeBlock.getAssignString();
					// aggregate for each section
					if (earnGroupToSection.get(earnGroup.getEarnGroup()) != null) {
						TimeSummarySection timeSection = earnGroupToSection
								.get(earnGroup.getEarnGroup());
						TimeSummaryRow summaryRow = timeSection.getSummaryRow();
						BigDecimal hrs = summaryRow.getDayToHours().get(
								dayDescr);
						if (hrs != null) {
							hrs = hrs.add(dateToHoursMap.get(timestamp));
						} else {
							hrs = dateToHoursMap.get(timestamp);
						}
						summaryRow.getDayToHours().put(dayDescr, hrs);
						summaryRow.setDescr(earnGroup.getEarnGroup());

						TimeSummaryRow assignRow = timeSection
								.getRowForAssignment(assignDescr);
						if (assignRow != null) {
							BigDecimal assignHrs = assignRow.getDayToHours()
									.get(dayDescr);
							if (assignHrs != null) {
								assignHrs = assignHrs.add(dateToHoursMap.get(timestamp), TkConstants.MATH_CONTEXT);
								assignRow.getDayToHours().put(dayDescr,
										assignHrs);
							} else {
								assignRow.getDayToHours().put(dayDescr,
										dateToHoursMap.get(timestamp));
							}
						} else {
							TimeSummaryRow tr = new TimeSummaryRow();
							tr.setDescr(assignDescr);
							tr.getDayToHours().put(dayDescr,
									dateToHoursMap.get(timestamp));
							timeSection.getAssignRows().add(tr);
						}
					} else {
						TimeSummarySection timeSection = new TimeSummarySection();
						timeSection.setEarnGroup(earnGroup.getEarnGroup());
						TimeSummaryRow summaryRow = timeSection.getSummaryRow();
						BigDecimal currHrs = dateToHoursMap.get(timestamp);
						summaryRow.setDescr(earnGroup.getEarnGroup());
						summaryRow.getDayToHours().put(dayDescr, currHrs);
						TimeSummaryRow assignRow = new TimeSummaryRow();
						assignRow.setDescr(assignDescr);
						assignRow.getDayToHours().put(dayDescr,
								dateToHoursMap.get(timestamp));
						timeSection.getAssignRows().add(assignRow);
						timeSummary.getSections().add(timeSection);
						earnGroupToSection.put(earnGroup.getEarnGroup(),
								timeSection);
					}
				}
			}
		}
		
		dayCount = 1;
		BigDecimal weeklyTotal = new BigDecimal(0.00);
		BigDecimal sectionWeeklyTotal = new BigDecimal(0.00);
		BigDecimal periodTotal = new BigDecimal(0.00);
		Map<String,BigDecimal> assignRowToSummaryHours = new HashMap<String,BigDecimal>();
		Map<String,BigDecimal> assignRowToPeriodTotalHours = new HashMap<String, BigDecimal>();
		Map<String,BigDecimal> sectionToPeriodTotalHours = new HashMap<String, BigDecimal>();
		
		//Build up weekly totals for each summary row
		for(String dateDescr : timeSummary.getDateDescr()){

			BigDecimal hrs = timeSummary.getWorkedHours().getDayToHours().get(dateDescr);
			if(hrs!=null){
				weeklyTotal = weeklyTotal.add(hrs, TkConstants.MATH_CONTEXT);
				periodTotal = periodTotal.add(hrs, TkConstants.MATH_CONTEXT);
			}
			if((dayCount % 7)==0){
				timeSummary.getWorkedHours().getWeeklyTotals().add(weeklyTotal);
				weeklyTotal = new BigDecimal(0);
			}
			
			for(TimeSummarySection timeSection : timeSummary.getSections()){
				BigDecimal sectionHrs = new BigDecimal(0.00);
				if(sectionToPeriodTotalHours.get(timeSection.getEarnGroup())!=null){
					sectionHrs = sectionToPeriodTotalHours.get(timeSection.getEarnGroup());
				} 
				

				hrs = timeSection.getSummaryRow().getDayToHours().get(dateDescr);
				if(hrs!=null){
					sectionWeeklyTotal = sectionWeeklyTotal.add(hrs, TkConstants.MATH_CONTEXT);
					sectionHrs = sectionHrs.add(hrs, TkConstants.MATH_CONTEXT);
					sectionToPeriodTotalHours.put(timeSection.getEarnGroup(), sectionHrs);
					timeSection.getSummaryRow().setPeriodTotal(sectionHrs);
				}
				
				if((dayCount % 7)==0){
					timeSection.getSummaryRow().getWeeklyTotals().add(sectionWeeklyTotal);
					sectionWeeklyTotal = new BigDecimal(0);
				}
				
				for(TimeSummaryRow assignRow : timeSection.getAssignRows()){
					BigDecimal assignPeriodTotal = new BigDecimal(0.00);
					BigDecimal assignHrs = new BigDecimal(0.00);
					
					if(assignRowToPeriodTotalHours.get(assignRow.getDescr())!=null){
						assignPeriodTotal = assignRowToPeriodTotalHours.get(assignRow.getDescr());
					}
					if(assignRowToSummaryHours.get(assignRow.getDescr())!=null){
						assignHrs = assignRowToSummaryHours.get(assignRow.getDescr());
					}

					hrs = assignRow.getDayToHours().get(dateDescr);
					if(hrs!=null){
						assignHrs = assignHrs.add(hrs, TkConstants.MATH_CONTEXT);
						assignRowToSummaryHours.put(assignRow.getDescr(), assignHrs);
						assignPeriodTotal = assignPeriodTotal.add(assignHrs, TkConstants.MATH_CONTEXT);
						assignRow.setPeriodTotal(assignPeriodTotal);
					}
					if((dayCount % 7)==0){
						assignRow.getWeeklyTotals().add(assignHrs);
						assignRowToSummaryHours.put(assignRow.getDescr(), BigDecimal.ZERO);
					}
				}
			}
			dayCount++;
		}
		
		//Add period totals for each row
		timeSummary.getWorkedHours().setPeriodTotal(periodTotal);
		
		return timeSummary;
	}


}
