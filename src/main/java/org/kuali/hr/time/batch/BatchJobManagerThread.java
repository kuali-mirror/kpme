package org.kuali.hr.time.batch;

import java.util.Date;
import java.util.List;

import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;

public class BatchJobManagerThread extends Thread {
	//This represents a number of days on both sides of today
	public static int numOfDaysToPoll = 30;
	
	@Override
	public void run() {
		Date asOfDate = TKUtils.getCurrentDate();
		List<PayCalendarEntries> lstPayCalendarEntries = TkServiceLocator.getPayCalendarEntriesSerivce().getCurrentPayCalendarEntryNeedsScheduled(asOfDate);
		for(PayCalendarEntries payCalendarEntry: lstPayCalendarEntries){
			//Compare against each batch job column and make the associated job entry if needed
			//Write a query that checks table for the batch job and the pay calendar entry id
		}
		
		//Iterate over batch jobs and run job
	
	}
	


}
