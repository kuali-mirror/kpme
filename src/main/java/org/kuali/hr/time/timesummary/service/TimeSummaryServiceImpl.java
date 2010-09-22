package org.kuali.hr.time.timesummary.service;

import java.math.BigDecimal;
import java.sql.Date;
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
		timeSummary.setPayBeginDate(timesheetDocument.getPayCalendarEntry().getBeginPeriodDate());
		timeSummary.setPayEndDate(timesheetDocument.getPayCalendarEntry().getEndPeriodDate());
		timeSummary.setNumberOfDays(TKUtils.getDaysBetween(timeSummary.getPayBeginDate(), timeSummary.getPayEndDate()));
		
		Map<Date,String> dateToDateDescrMap = new HashMap<Date,String>();
		
		
		Map<String,TimeSummarySection> earnGroupToSection = new HashMap<String,TimeSummarySection>();
		
		//Iterate over date range and setup a map of days to day strings
		Calendar payBeginCal = GregorianCalendar.getInstance();
		Calendar payEndCal = GregorianCalendar.getInstance();
	
		payBeginCal.setTime(timeSummary.getPayBeginDate());
		payEndCal.setTime(timeSummary.getPayEndDate());
		
		while(payBeginCal.before(payEndCal) || payBeginCal.equals(payEndCal)){
			String displayName = (payBeginCal.get(Calendar.MONTH) + 1) + "/" 
									+ payBeginCal.get(Calendar.DAY_OF_MONTH) ;
			Date dt = new Date(payBeginCal.getTime().getTime());
			dateToDateDescrMap.put(dt, displayName);
			timeSummary.getDateDescr().add(displayName);
			payBeginCal.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		TimeSummaryRow workedHours = timeSummary.getWorkedHours();
		
		for(TimeBlock timeBlock : timesheetDocument.getTimeBlocks()){
			for(TimeHourDetail timeHourDetail : timeBlock.getTimeHourDetails()){
				Date day = new Date(timeBlock.getBeginTimestamp().getTime());
				String dayDescr = dateToDateDescrMap.get(day);
				//Setup worked hours
				if(workedHours.getDayToHours().get(dayDescr)!=null){
					BigDecimal currHours = workedHours.getDayToHours().get(dayDescr);
					currHours.add(timeHourDetail.getHours(), TkConstants.MATH_CONTEXT);
					workedHours.getDayToHours().put(dayDescr, currHours);
				} else {
					workedHours.getDayToHours().put(dayDescr, timeHourDetail.getHours());
				}
				EarnGroup earnGroup = TkServiceLocator.getEarnGroupService().getEarnGroupSummaryForEarnCode(timeHourDetail.getEarnCode(), 
						new Date(timeBlock.getBeginTimestamp().getTime()));
				String assignDescr = timeBlock.getAssignString();
				//aggregate for each section
				if(earnGroupToSection.get(earnGroup.getEarnGroup())!= null){
					TimeSummarySection timeSection = earnGroupToSection.get(earnGroup.getEarnGroup());
					TimeSummaryRow summaryRow = timeSection.getSummaryRow();
					BigDecimal hrs = summaryRow.getDayToHours().get(dayDescr);
					hrs = hrs.add(timeHourDetail.getHours());
					summaryRow.getDayToHours().put(dayDescr, hrs);
					
					TimeSummaryRow assignRow = timeSection.getRowForAssignment(assignDescr);
					if(assignRow!=null){
						BigDecimal assignHrs = assignRow.getDayToHours().get(dayDescr);
						if(assignHrs !=null){
							assignHrs = assignHrs.add(timeHourDetail.getHours(), TkConstants.MATH_CONTEXT);
							assignRow.getDayToHours().put(dayDescr, assignHrs);
						} else {
							assignRow.getDayToHours().put(dayDescr, timeHourDetail.getHours());
						}
					} else{
						TimeSummaryRow tr = new TimeSummaryRow();
						tr.setDescr(assignDescr);
						tr.getDayToHours().put(dayDescr, timeHourDetail.getHours());
						timeSection.getAssignRows().add(tr);
					}
				} else {
					TimeSummarySection timeSection = new TimeSummarySection();
					timeSection.setEarnGroup(earnGroup.getEarnGroup());
					TimeSummaryRow summaryRow = timeSection.getSummaryRow();
					BigDecimal currHrs = timeHourDetail.getHours();
					summaryRow.getDayToHours().put(dayDescr, currHrs);
					TimeSummaryRow assignRow = new TimeSummaryRow();
					assignRow.setDescr(assignDescr);
					assignRow.getDayToHours().put(dayDescr, timeHourDetail.getHours());
					timeSummary.getSections().add(timeSection);
					earnGroupToSection.put(earnGroup.getEarnGroup(), timeSection);
				}
			}
		}
		return timeSummary;
	}
}
