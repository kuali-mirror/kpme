package org.kuali.hr.time.batch;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.paycalendar.PayCalendarEntries;
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
    	PayCalendarEntries payCalendarEntry = TkServiceLocator.getPayCalendarEntriesSerivce().getPayCalendarEntries(getPayCalendarEntryId());
    	List<ClockLog> lstOpenClockLogs = TkServiceLocator.getClockLogService().getOpenClockLogs(payCalendarEntry);
    	for(ClockLog cl : lstOpenClockLogs){
    		populateBatchJobEntry(cl);
    	}
    	
    }


    @Override
    protected void populateBatchJobEntry(Object o) {
    	ClockLog cl = (ClockLog)o;
        String ip = this.getNextIpAddressInCluster();
        if(StringUtils.isNotBlank(ip)){
            //insert a batch job entry here
        	//TODO finish this
//            BatchJobEntry entry = this.createBatchJobEntry(this.getBatchJobName(), ip, cl.getPrincipalId(), null);
//            TkServiceLocator.getBatchJobEntryService().saveBatchJobEntry(entry);
        } else {
            LOG.info("No ip found in cluster to assign batch jobs");
        }
    }

}
