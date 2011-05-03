package org.kuali.hr.time.batch;

import org.apache.log4j.Logger;

public class SupervisorApprovalBatchJobRunnable extends BatchJobEntryRunnable  {

    private static final Logger LOG = Logger.getLogger(SupervisorApprovalBatchJobRunnable.class);

    public SupervisorApprovalBatchJobRunnable(BatchJobEntry entry) {
        super(entry);
    }

    @Override
    public void doWork() {

    }
}
