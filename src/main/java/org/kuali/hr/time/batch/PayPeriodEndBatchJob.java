package org.kuali.hr.time.batch;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TkConstants;

public class PayPeriodEndBatchJob extends BatchJob {
    private Logger LOG = Logger.getLogger(PayPeriodEndBatchJob.class);


    public PayPeriodEndBatchJob(Long payCalendarEntryId) {
        super();
        this.setBatchJobName(TkConstants.BATCH_JOB_NAMES.PAY_PERIOD_END);
        this.setPayCalendarEntryId(payCalendarEntryId);
    }

    @Override
    public void doWork() {
    }


    @Override
    protected void populateBatchJobEntry(Object o) {
        String ip = this.getNextIpAddressInCluster();
        if(StringUtils.isNotBlank(ip)){
            //insert a batch job entry here
            BatchJobEntry entry = this.createBatchJobEntry(this.getBatchJobName(), ip, null, null);
            TkServiceLocator.getBatchJobEntryService().saveBatchJobEntry(entry);
        } else {
            LOG.info("No ip found in cluster to assign batch jobs");
        }
    }

}
