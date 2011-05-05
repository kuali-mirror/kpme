package org.kuali.hr.time.batch;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;

public class BatchJobManagerThread extends Thread {
    private static final Logger LOG = Logger.getLogger(BatchJobManagerThread.class);

    int startupSleep = 120;
	//This represents a number of days on both sides of today
	int numOfDaysToPoll = 30;
    int secondsToSleep = 120;

    public BatchJobManagerThread(int secondsToSleep, int numberOfDaysToPoll, int startupSleep) {
        this.numOfDaysToPoll = numberOfDaysToPoll;
        this.secondsToSleep = secondsToSleep;
        this.startupSleep = startupSleep;
    }

	@Override
	public void run() {

        try {
            Thread.sleep(1000 * startupSleep);
        } catch (Exception e) {
            LOG.error(e);
        }

        while (true) {
            Date asOfDate = TKUtils.getCurrentDate();
            List<PayCalendarEntries> payCalendarEntries = TkServiceLocator.getPayCalendarEntriesSerivce().getCurrentPayCalendarEntryNeedsScheduled(numOfDaysToPoll, asOfDate);

            LOG.info("Scanning for batch jobs to run: ("+asOfDate.toString()+")");

            List<BatchJob> jobsToRun = new ArrayList<BatchJob>();
            for(PayCalendarEntries payCalendarEntry: payCalendarEntries){

                List<BatchJob> batchJobs = TkServiceLocator.getBatchJobService().getBatchJobs(payCalendarEntry.getPayCalendarEntriesId());

                if ((payCalendarEntry.getBatchInitiateDate() != null) && (!jobPresentInJobsList(batchJobs, TkConstants.BATCH_JOB_NAMES.INITIATE)) ) {
                    BatchJob job = new InitiateBatchJob(payCalendarEntry.getPayCalendarEntriesId());
                    TkServiceLocator.getBatchJobService().saveBatchJob(job);
                    batchJobs.add(job);
                }

                if ((payCalendarEntry.getBatchEmployeeApprovalDate() != null) && (!jobPresentInJobsList(batchJobs, TkConstants.BATCH_JOB_NAMES.APPROVE)) ) {
                    BatchJob job = new EmployeeApprovalBatchJob(payCalendarEntry);
                    TkServiceLocator.getBatchJobService().saveBatchJob(job);
                    batchJobs.add(job);
                }

                if ((payCalendarEntry.getBatchEndPayPeriodDate() != null) && (!jobPresentInJobsList(batchJobs, TkConstants.BATCH_JOB_NAMES.PAY_PERIOD_END)) ) {
                    BatchJob job = new PayPeriodEndBatchJob(payCalendarEntry.getPayCalendarEntriesId());
                    TkServiceLocator.getBatchJobService().saveBatchJob(job);
                    batchJobs.add(job);
                }

                if ((payCalendarEntry.getBatchSupervisorApprovalDate() != null) && (!jobPresentInJobsList(batchJobs, TkConstants.BATCH_JOB_NAMES.SUPERVISOR_APPROVAL)) ) {
                    BatchJob job = new SupervisorApprovalBatchJob(payCalendarEntry.getPayCalendarEntriesId());
                    TkServiceLocator.getBatchJobService().saveBatchJob(job);
                    batchJobs.add(job);
                }
            }

            for (BatchJob job : jobsToRun) {
                job.runJob();
            }

            try {
                Thread.sleep(1000 * secondsToSleep);
            } catch (Exception e) {
                LOG.error(e);
            }
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
