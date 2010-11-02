package org.kuali.hr.time.timesummary.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.earngroup.EarnGroup;
import org.kuali.hr.time.paycalendar.PayCalendarDates;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.TimeHourDetail;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.timesummary.AssignmentRow;
import org.kuali.hr.time.timesummary.EarnGroupSection;
import org.kuali.hr.time.timesummary.TimeSummary;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.util.TkTimeBlockAggregate;

public class TimeSummaryServiceImpl implements TimeSummaryService{

	@Override
	public TimeSummary getTimeSummary(TimesheetDocument timesheetDocument) {
		TimeSummary timeSummary = new TimeSummary();
		
		if(timesheetDocument.getTimeBlocks() == null) {
			return timeSummary;
		}
		timeSummary.setSummaryHeader(getHeaderForSummary(timesheetDocument.getPayCalendarEntry()));
		TkTimeBlockAggregate tkTimeBlockAggregate = new TkTimeBlockAggregate(timesheetDocument.getTimeBlocks(), timesheetDocument.getPayCalendarEntry());
		timeSummary.setWorkedHours(getWorkedHours(tkTimeBlockAggregate));
		timeSummary.setSections(buildSummarySections(tkTimeBlockAggregate,timesheetDocument.getPayCalendarEntry().getBeginPeriodDateTime(),timesheetDocument));
		
		return timeSummary;
	}
	
	private List<BigDecimal> getWorkedHours(TkTimeBlockAggregate timeBlockAggregate){
		List<BigDecimal> workedHours = new ArrayList<BigDecimal>();
		int dayCount = 1;
		BigDecimal weeklyTotal = TkConstants.BIG_DECIMAL_SCALED_ZERO;
		BigDecimal periodTotal = TkConstants.BIG_DECIMAL_SCALED_ZERO;
		for(List<TimeBlock> lstDayTimeblocks : timeBlockAggregate.getDayTimeBlockList()){
			BigDecimal totalForDay = TkConstants.BIG_DECIMAL_SCALED_ZERO;
			for(TimeBlock tb : lstDayTimeblocks){
				totalForDay = totalForDay.add(tb.getHours(), TkConstants.MATH_CONTEXT);
				weeklyTotal = weeklyTotal.add(tb.getHours(), TkConstants.MATH_CONTEXT);
				periodTotal = periodTotal.add(tb.getHours(), TkConstants.MATH_CONTEXT);
			}
			if((dayCount % 7) == 0){
				dayCount = 0;
				workedHours.add(weeklyTotal);
				weeklyTotal = TkConstants.BIG_DECIMAL_SCALED_ZERO;
			}
			dayCount++;
			workedHours.add(totalForDay);
		}
		workedHours.add(periodTotal);
		return workedHours;
	}
	
	private List<String> getHeaderForSummary(PayCalendarDates payEntry){
		// Iterate over date range and setup a list for the header
		Calendar payBeginCal = GregorianCalendar.getInstance();
		Calendar payEndCal = GregorianCalendar.getInstance();
		payBeginCal.setTime(payEntry.getBeginPeriodDateTime());
		payEndCal.setTime(payEntry.getEndPeriodDateTime());
		
		List<String> summaryHeader = new ArrayList<String>();
		
		int dayCount = 1;
		//Build up map for date to day description
		while (payBeginCal.before(payEndCal) || payBeginCal.equals(payEndCal)) {
			String displayName = (payBeginCal.get(Calendar.MONTH) + 1) + "/"
							+ payBeginCal.get(Calendar.DAY_OF_MONTH);
			payBeginCal.add(Calendar.DAY_OF_MONTH, 1);
			if(TKUtils.isVirtualWorkDay(payBeginCal)){	
				displayName += " - " + (payBeginCal.get(Calendar.MONTH) + 1) + "/"
								+ payBeginCal.get(Calendar.DAY_OF_MONTH);
			}
			summaryHeader.add(displayName);
			//Add weekly total column
			if ((dayCount % 7) == 0) {
				summaryHeader.add("Week " + (dayCount / 7));
			}
			dayCount++;
		
		}
		// add period total column
		summaryHeader.add("Period Total");
		return summaryHeader;
	}
	
	private List<EarnGroupSection> buildSummarySections(TkTimeBlockAggregate timeBlockAggregate, Date asOfDate, TimesheetDocument timesheetDocument){
		Map<Integer,Map<String,BigDecimal>> dayToEarnGroupAssignToHoursMap = new HashMap<Integer,Map<String,BigDecimal>>();
		Map<String,Set<String>> earnGroupToAssignmentSets = new HashMap<String,Set<String>>();
		List<EarnGroupSection> lstEarnGroupSections = new ArrayList<EarnGroupSection>();
		
		int dayCount = 1;

		for(List<TimeBlock> timeBlocksForDay : timeBlockAggregate.getDayTimeBlockList()){
			Map<String,BigDecimal> earnGroupAssignToHoursMap = null;
			for(TimeBlock tb : timeBlocksForDay){
				for(TimeHourDetail thd : tb.getTimeHourDetails()){
					EarnGroup earnGroup = TkServiceLocator.getEarnGroupService().getEarnGroupForEarnCode(thd.getEarnCode(), TKUtils.getTimelessDate(asOfDate));
					if(earnGroup == null){
						earnGroup = new EarnGroup();
						earnGroup.setEarnGroup("Other");
					}
					buildAssignmentSetForEarnGroup(earnGroupToAssignmentSets, tb.getAssignString(), earnGroup.getEarnGroup());
					
					String earnGroupAssignDescr = earnGroup.getEarnGroup()+"_"+tb.getAssignString();
					earnGroupAssignToHoursMap = dayToEarnGroupAssignToHoursMap.get(dayCount); 
					earnGroupAssignToHoursMap = buildTimeHourDetail(earnGroupAssignToHoursMap, thd.getHours(), earnGroupAssignDescr);
					dayToEarnGroupAssignToHoursMap.put(dayCount, earnGroupAssignToHoursMap);
				}
			}
			dayToEarnGroupAssignToHoursMap.put(dayCount, earnGroupAssignToHoursMap);
			earnGroupAssignToHoursMap = null;
			dayCount++;
		}
		
		
		for(String earnGroup : earnGroupToAssignmentSets.keySet()){
			//for each assignment
			EarnGroupSection earnGroupSection = new EarnGroupSection();
			earnGroupSection.setEarnGroup(earnGroup);
			for(String assignmentDescr : earnGroupToAssignmentSets.get(earnGroup)){
				AssignmentRow assignRow = new AssignmentRow();
				Assignment assign = TkServiceLocator.getAssignmentService().getAssignment(timesheetDocument,assignmentDescr);
				assignRow.setDescr(assign.getAssignmentDescription());
				BigDecimal weeklyTotal = TkConstants.BIG_DECIMAL_SCALED_ZERO;
				BigDecimal periodTotal = TkConstants.BIG_DECIMAL_SCALED_ZERO;
				for(int i = 1; i< dayToEarnGroupAssignToHoursMap.size()+1;i++){
					Map<String,BigDecimal> earnGroupAssignToHoursMap = dayToEarnGroupAssignToHoursMap.get(i);
					BigDecimal hrs = TkConstants.BIG_DECIMAL_SCALED_ZERO;
					if(earnGroupAssignToHoursMap != null && earnGroupAssignToHoursMap.get(earnGroup+"_"+assignmentDescr)!=null){
						hrs = earnGroupAssignToHoursMap.get(earnGroup+"_"+assignmentDescr);
					}
					
					assignRow.getTotal().add(hrs);
					weeklyTotal = weeklyTotal.add(hrs,TkConstants.MATH_CONTEXT);
					periodTotal = periodTotal.add(hrs, TkConstants.MATH_CONTEXT);
					if((i % 7)== 0){
						assignRow.getTotal().add(weeklyTotal);
						weeklyTotal = TkConstants.BIG_DECIMAL_SCALED_ZERO;
					}
				}
				assignRow.getTotal().add(periodTotal);
				earnGroupSection.addAssignmentRow(assignRow);
			}
			lstEarnGroupSections.add(earnGroupSection);
		}
		
		return lstEarnGroupSections;
		
	}
	
	private void buildAssignmentSetForEarnGroup(Map<String,Set<String>> earnGroupToAssignmentSets, String assignDescr,String earnGroup){
		Set<String> assignmentSet = earnGroupToAssignmentSets.get(earnGroup);
		if(assignmentSet == null){
			assignmentSet = new HashSet<String>();
		}
		assignmentSet.add(assignDescr);
		earnGroupToAssignmentSets.put(earnGroup, assignmentSet);
	}
	
	
	private Map<String,BigDecimal> buildTimeHourDetail(Map<String,BigDecimal> earnGroupAssignToHoursMap, BigDecimal hours, 
															String earnGroupAssignDescr){
		BigDecimal currentDayHrs = TkConstants.BIG_DECIMAL_SCALED_ZERO;
		if(earnGroupAssignToHoursMap == null){
			earnGroupAssignToHoursMap = new HashMap<String,BigDecimal>();
		} else {
			if(earnGroupAssignToHoursMap.get(earnGroupAssignDescr)!=null){
				currentDayHrs = earnGroupAssignToHoursMap.get(earnGroupAssignDescr);
			}
		}
		currentDayHrs = currentDayHrs.add(hours, TkConstants.MATH_CONTEXT);
		earnGroupAssignToHoursMap.put(earnGroupAssignDescr, currentDayHrs);
		
		return earnGroupAssignToHoursMap;
	}
	


}
