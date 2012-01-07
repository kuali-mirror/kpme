package org.kuali.hr.time.batch;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TkConstants;

public class PayPeriodEndBatchJob extends BatchJob {
    private Logger LOG = Logger.getLogger(PayPeriodEndBatchJob.class);


    public PayPeriodEndBatchJob(String hrPyCalendarEntryId) {
        super();
        this.setBatchJobName(TkConstants.BATCH_JOB_NAMES.PAY_PERIOD_END);
        this.setPayCalendarEntryId(hrPyCalendarEntryId);
    }

    @Override
    public void doWork() {
    	CalendarEntries payCalendarEntry = TkServiceLocator.getCalendarEntriesSerivce().getCalendarEntries(getPayCalendarEntryId());
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
