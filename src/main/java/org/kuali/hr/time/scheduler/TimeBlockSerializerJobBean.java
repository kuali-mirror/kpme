package org.kuali.hr.time.scheduler;


import java.util.Calendar;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class TimeBlockSerializerJobBean extends QuartzJobBean{
	
	private static TimeBlockSerializerService timeBlockSerializerService;
	
	

	public TimeBlockSerializerService getTimeBlockSerializerService() {
		return timeBlockSerializerService;
	}



	public void setTimeBlockSerializerService(
			TimeBlockSerializerService timeBlockSerializerService) {
		TimeBlockSerializerJobBean.timeBlockSerializerService = timeBlockSerializerService;
	}

		
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		try {
			//UnComment following 2 statements to apply the effect of Scheduled Job
			
			System.out.println("Executed at :"+Calendar.getInstance().getTime());
			timeBlockSerializerService.serializeToCSV();
			timeBlockSerializerService.serializeToXML();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
	}
	

}
