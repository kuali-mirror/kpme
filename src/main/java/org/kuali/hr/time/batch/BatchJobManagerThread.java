package org.kuali.hr.time.batch;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;

public class BatchJobManagerThread extends Thread {
	//This represents a number of days on both sides of today
	public static int numOfDaysToPoll = 30;

	@Override
	public void run() {
		Date asOfDate = TKUtils.getCurrentDate();
		List<PayCalendarEntries> payCalendarEntries = TkServiceLocator.getPayCalendarEntriesSerivce().getCurrentPayCalendarEntryNeedsScheduled(numOfDaysToPoll, asOfDate);

        List<BatchJob> jobsToRun = new ArrayList<BatchJob>();
        for(PayCalendarEntries payCalendarEntry: payCalendarEntries){
			//Compare against each batch job column and make the associated job entry if needed
			//Write a query that checks table for the batch job and the pay calendar entry id
            List<BatchJob> batchJobs = TkServiceLocator.getBatchJobService().getBatchJobs(payCalendarEntry.getPayCalendarEntriesId());

            if ((payCalendarEntry.getBatchInitiateDate() != null) && (!jobPresentInJobsList(batchJobs, TkConstants.BATCH_JOB_NAMES.INITIATE)) ) {
                // TODO: Create Initiate Job
            }

            if ((payCalendarEntry.getBatchEmployeeApprovalDate() != null) && (!jobPresentInJobsList(batchJobs, TkConstants.BATCH_JOB_NAMES.APPROVE)) ) {
                // TODO: Approval Job
            }

            if ((payCalendarEntry.getBatchEndPayPeriodDate() != null) && (!jobPresentInJobsList(batchJobs, TkConstants.BATCH_JOB_NAMES.PAY_PERIOD_END)) ) {
                // TODO: Create Pay Period End Job
            }

            if ((payCalendarEntry.getBatchSupervisorApprovalDate() != null) && (!jobPresentInJobsList(batchJobs, TkConstants.BATCH_JOB_NAMES.SUPERVISOR_APPROVAL)) ) {
                // TODO: Create supervisor approval Job
            }
		}

        for (BatchJob job : jobsToRun) {
            job.runJob();
        }
	}

    private boolean jobPresentInJobsList(List<BatchJob> batchJobs, String batchJobName) {
        for (BatchJob batchJob : batchJobs) {
            if (StringUtils.equals(batchJob.getBatchJobName(), batchJobName)) {
                return true;
            }
        }

        return false;
    }



}
