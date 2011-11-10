package org.kuali.hr.time.batch;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
            List<CalendarEntries> calendarEntries = TkServiceLocator.getCalendarEntriesSerivce().getCurrentCalendarEntryNeedsScheduled(numOfDaysToPoll, asOfDate);

            LOG.info("Scanning for batch jobs to run: ("+asOfDate.toString()+")");

            List<BatchJob> jobsToRun = new ArrayList<BatchJob>();
            for(CalendarEntries calendarEntry: calendarEntries){

                List<BatchJob> batchJobs = TkServiceLocator.getBatchJobService().getBatchJobs(calendarEntry.getHrCalendarEntriesId());

//                batchJobs.clear();
//                DumbJob dj = new DumbJob();
//                jobsToRun.add(dj);
                if ((calendarEntry.getBatchInitiateDate() != null) && (!jobPresentInJobsList(batchJobs, TkConstants.BATCH_JOB_NAMES.INITIATE)) ) {
                    BatchJob job = new InitiateBatchJob(calendarEntry.getHrCalendarEntriesId());
                    TkServiceLocator.getBatchJobService().saveBatchJob(job);
                    batchJobs.add(job);
                    jobsToRun.add(job);
                }
//
//                if ((calendarEntry.getBatchEmployeeApprovalDate() != null) && (!jobPresentInJobsList(batchJobs, TkConstants.BATCH_JOB_NAMES.APPROVE)) ) {
//                    BatchJob job = new EmployeeApprovalBatchJob(calendarEntry);
//                    TkServiceLocator.getBatchJobService().saveBatchJob(job);
//                    batchJobs.add(job);
//                }
//
//                if ((calendarEntry.getBatchEndPayPeriodDate() != null) && (!jobPresentInJobsList(batchJobs, TkConstants.BATCH_JOB_NAMES.PAY_PERIOD_END)) ) {
//                    BatchJob job = new PayPeriodEndBatchJob(calendarEntry.getHrPyCalendarEntriesId());
//                    TkServiceLocator.getBatchJobService().saveBatchJob(job);
//                    batchJobs.add(job);
//                }
//
//                if ((calendarEntry.getBatchSupervisorApprovalDate() != null) && (!jobPresentInJobsList(batchJobs, TkConstants.BATCH_JOB_NAMES.SUPERVISOR_APPROVAL)) ) {
//                    BatchJob job = new SupervisorApprovalBatchJob(calendarEntry.getHrPyCalendarEntriesId());
//                    TkServiceLocator.getBatchJobService().saveBatchJob(job);
//                    batchJobs.add(job);
//                }
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
