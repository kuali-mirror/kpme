package org.kuali.hr.time.batch;

import org.apache.log4j.Logger;
import org.kuali.hr.time.util.TkConstants;

public class BatchApproveMissedPunchJob extends BatchJob {
	private Logger LOG = Logger.getLogger(BatchApproveMissedPunchJob.class);
	
   public BatchApproveMissedPunchJob(String hrPyCalendarEntryId) {
        super();
        this.setBatchJobName(TkConstants.BATCH_JOB_NAMES.BATCH_APPROVE_MISSED_PUNCH);
        this.setPayCalendarEntryId(hrPyCalendarEntryId);
    }

}
