package org.kuali.hr.time.batch;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TkConstants;

import java.util.List;

public class PayPeriodEndBatchJob extends BatchJob {
    private Logger LOG = Logger.getLogger(PayPeriodEndBatchJob.class);


    public PayPeriodEndBatchJob(Long hrPyCalendarEntryId) {
        super();
        this.setBatchJobName(TkConstants.BATCH_JOB_NAMES.PAY_PERIOD_END);
        this.setCalendarEntryId(hrPyCalendarEntryId);
    }

    @Override
    public void doWork() {
    	CalendarEntries calendarEntry = TkServiceLocator.getCalendarEntriesSerivce().getCalendarEntries(getCalendarEntryId());
    	List<ClockLog> lstOpenClockLogs = TkServiceLocator.getClockLogService().getOpenClockLogs(calendarEntry);
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
