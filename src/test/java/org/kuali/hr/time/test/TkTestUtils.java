package org.kuali.hr.time.test;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.kuali.hr.job.Job;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.rice.kew.exception.WorkflowException;

public class TkTestUtils {
	public static TimesheetDocument populateTimesheetDocument(Date calDate) {
		List<Job> jobs = getJobs(calDate);
		try {
			TimesheetDocument timesheet = TkServiceLocator.getTimesheetService().openTimesheetDocument(TKContext.getUser().getPrincipalId(), 
							TkServiceLocator.getPayCalendarSerivce().getCurrentPayCalendarDates(TKContext.getPrincipalId(), 
							  jobs.get(0), calDate));
			for(TimeBlock timeBlock : timesheet.getTimeBlocks()){
				TkServiceLocator.getTimeBlockService().deleteTimeBlock(timeBlock);
			}
			
			//refetch clean document
			timesheet = TkServiceLocator.getTimesheetService().openTimesheetDocument(TKContext.getUser().getPrincipalId(), 
					TkServiceLocator.getPayCalendarSerivce().getCurrentPayCalendarDates(TKContext.getPrincipalId(), 
					  jobs.get(0), calDate));
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
	
	public static TimeBlock createTimeBlock(TimesheetDocument timesheetDocument, int dayInPeriod, int numHours){
		TimeBlock timeBlock = new TimeBlock();
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTimeInMillis(timesheetDocument.getPayCalendarEntry().getBeginPeriodDate().getTime());
		for(int i = 1; i< dayInPeriod;i++){
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}
		cal.set(Calendar.HOUR, 8);
		cal.set(Calendar.MINUTE, 0);
		timeBlock.setBeginTimestamp(new Timestamp(cal.getTimeInMillis()));
		timeBlock.setBeginTimestampTimezone("EST");
		timeBlock.setEarnCode("REG");
		timeBlock.setHours(new BigDecimal(numHours));
		cal.add(Calendar.HOUR, numHours);
		timeBlock.setEndTimestamp(new Timestamp(cal.getTimeInMillis()));
		
		return timeBlock;
	}
	
	public static List<Job> getJobs(Date calDate){
		return TkServiceLocator.getJobSerivce().getJobs(TKContext.getPrincipalId(), calDate);
	}
	
	
	
}
