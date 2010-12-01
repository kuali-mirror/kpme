package org.kuali.hr.time.test;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.flsa.FlsaDay;
import org.kuali.hr.time.flsa.FlsaWeek;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.TimeHourDetail;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.util.TkTimeBlockAggregate;
import org.kuali.rice.kew.exception.WorkflowException;

public class TkTestUtils {
	
	public static TimesheetDocument populateBlankTimesheetDocument(Date calDate) {
		try {
			TimesheetDocument timesheet = TkServiceLocator.getTimesheetService().openTimesheetDocument(TKContext.getUser().getPrincipalId(), 
							TkServiceLocator.getPayCalendarSerivce().getCurrentPayCalendarDates(TKContext.getPrincipalId(), 
							  calDate));
			for(TimeBlock timeBlock : timesheet.getTimeBlocks()){
				TkServiceLocator.getTimeBlockService().deleteTimeBlock(timeBlock);
			}
			
			return timesheet;			
		} catch (WorkflowException e) {
			throw new RuntimeException("Problem fetching document");
		}		
	}
	
	public static TimesheetDocument populateTimesheetDocument(Date calDate) {
		try {
			TimesheetDocument timesheet = TkServiceLocator.getTimesheetService().openTimesheetDocument(TKContext.getUser().getPrincipalId(), 
							TkServiceLocator.getPayCalendarSerivce().getCurrentPayCalendarDates(TKContext.getPrincipalId(), 
							  calDate));
			for(TimeBlock timeBlock : timesheet.getTimeBlocks()){
				TkServiceLocator.getTimeBlockService().deleteTimeBlock(timeBlock);
			}
			
			//refetch clean document
			timesheet = TkServiceLocator.getTimesheetService().openTimesheetDocument(TKContext.getUser().getPrincipalId(), 
					TkServiceLocator.getPayCalendarSerivce().getCurrentPayCalendarDates(TKContext.getPrincipalId(), 
					   calDate));
			for(int i = 0;i<5;i++){
				TimeBlock timeBlock = createTimeBlock(timesheet, i+1, 10);
				timesheet.getTimeBlocks().add(timeBlock);
			}
			return timesheet;
			
		} catch (WorkflowException e) {
			throw new RuntimeException("Problem fetching document");
		}
	}
	
	//TODO populate this
	public static void createEarnGroup(String earnGroup, List<String> earnCodes, Date asOfDate){
		
	}
	
	/**
	 * Helper method to create regular time blocks for use in testing.
	 * 
	 * @param start
	 * @param days
	 * @param hours
	 * @param earnCode
	 * @param jobNumber
	 * @param workArea
	 * @return
	 */
	public static List<TimeBlock> createUniformTimeBlocks(DateTime start, int days, BigDecimal hours, String earnCode, Long jobNumber, Long workArea) {
		List<TimeBlock> blocks = new ArrayList<TimeBlock>();
		
		for (int i=0; i<days; i++) {
			DateTime ci = start.plusDays(i);
			DateTime co = ci.plusHours(hours.intValue());
			TimeBlock block = TkTestUtils.createDummyTimeBlock(ci, co, hours, earnCode, jobNumber, workArea);
			blocks.add(block);
		}
		
		return blocks;
	}


	public static TimeBlock createDummyTimeBlock(DateTime clockIn, DateTime clockOut, BigDecimal hours, String earnCode) {
		return TkTestUtils.createDummyTimeBlock(clockIn, clockOut, hours, earnCode, -1L, -1L);
	}
	
	public static TimeBlock createDummyTimeBlock(DateTime clockIn, DateTime clockOut, BigDecimal hours, String earnCode, Long jobNumber, Long workArea) {
		TimeBlock block = new TimeBlock();
		Timestamp ci = new Timestamp(clockIn.getMillis());
		Timestamp co = new Timestamp(clockOut.getMillis());
		block.setBeginTimestamp(ci);
		block.setEndTimestamp(co);
		block.setHours(hours);
		block.setEarnCode(earnCode);
		block.setJobNumber(jobNumber);
		block.setWorkArea(workArea);
		
		TimeHourDetail thd = new TimeHourDetail();
		thd.setHours(hours);
		thd.setEarnCode(earnCode);
		List<TimeHourDetail> timeHourDetails = new ArrayList<TimeHourDetail>();
		timeHourDetails.add(thd);
		block.setTimeHourDetails(timeHourDetails);
		
		return block;
	}

	
	public static TimeBlock createTimeBlock(TimesheetDocument timesheetDocument, int dayInPeriod, int numHours){
		TimeBlock timeBlock = new TimeBlock();
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTimeInMillis(timesheetDocument.getPayCalendarEntry().getBeginPeriodDateTime().getTime());
		for(int i = 1; i< dayInPeriod;i++){
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}
		cal.set(Calendar.HOUR, 8);
		cal.set(Calendar.MINUTE, 0);
		timeBlock.setBeginTimestamp(new Timestamp(cal.getTimeInMillis()));
		timeBlock.setBeginTimestampTimezone("EST");
		timeBlock.setEarnCode("RGN");
		timeBlock.setHours((new BigDecimal(numHours)).setScale(TkConstants.BIG_DECIMAL_SCALE, TkConstants.BIG_DECIMAL_SCALE_ROUNDING));
		cal.add(Calendar.HOUR, numHours);
		timeBlock.setEndTimestamp(new Timestamp(cal.getTimeInMillis()));
		
		return timeBlock;
	}
	
	public static List<Job> getJobs(Date calDate){
		return TkServiceLocator.getJobSerivce().getJobs(TKContext.getPrincipalId(), calDate);
	}
	
	/**
	 * Helper method to verify that the aggregate contains the correct sums as 
	 * indicated in the ecToHoursMapping, on a SINGLE given flsaWeek.
	 * 
	 * Warning! Contains Assertions, use only with Test Cases.
	 * 
	 * @param ecToHoursMap ex: { 'REG' => 40, 'OVT' => 10 }
	 * @param aggregate An aggregate object containing the time blocks
	 * @param flsaWeek 0 indexed start week (pulling from aggregate)
	 */
	@SuppressWarnings("serial")
	public static void verifyAggregateHourSums(final Map<String,BigDecimal> ecToHoursMap, TkTimeBlockAggregate aggregate, int flsaWeek) {
		// Initializes sum map to zeros, since we only care about the entires 
		// that were passed in.
		Map<String,BigDecimal> ecToSumMap = new HashMap<String,BigDecimal>() {{ for (String ec : ecToHoursMap.keySet()) { put(ec, BigDecimal.ZERO); }}};
	
		List<FlsaWeek> flsaWeeks = aggregate.getFlsaWeeks();	
		Assert.assertTrue("Not enough FLSA weeks to verify aggregate hours", flsaWeeks.size() > flsaWeek);
		
		// Build our Sum Map.
		FlsaWeek week = flsaWeeks.get(1);
		List<FlsaDay> flsaDays = week.getFlsaDays();
		for (FlsaDay day : flsaDays) 
			for (TimeBlock bl : day.getAppliedTimeBlocks()) 
				for (TimeHourDetail thd : bl.getTimeHourDetails())
					if (ecToSumMap.containsKey(thd.getEarnCode())) 
						ecToSumMap.put(thd.getEarnCode(), ecToSumMap.get(thd.getEarnCode()).add(thd.getHours()));
		
		// Assert that our values are correct.
		for (String key : ecToHoursMap.keySet())
			Assert.assertEquals("Wrong number of hours.", 0, ecToHoursMap.get(key).compareTo(ecToSumMap.get(key)));
	}
	
	
}
