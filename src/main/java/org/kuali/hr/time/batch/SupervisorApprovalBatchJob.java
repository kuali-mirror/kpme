package org.kuali.hr.time.batch;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;

import java.util.Date;
import java.util.List;

public class SupervisorApprovalBatchJob extends BatchJob {
    private Logger LOG = Logger.getLogger(PayPeriodEndBatchJob.class);
    private CalendarEntries calendarEntry;

    public SupervisorApprovalBatchJob(Long hrPyCalendarEntryId) {
        super();
        this.setBatchJobName(TkConstants.BATCH_JOB_NAMES.SUPERVISOR_APPROVAL);
        this.setCalendarEntryId(hrPyCalendarEntryId);
        this.calendarEntry = TkServiceLocator.getCalendarEntriesSerivce().getCalendarEntries(hrPyCalendarEntryId);
    }

    @Override
    public void doWork() {
		Date payBeginDate = calendarEntry.getBatchEmployeeApprovalDate();
		List<TimesheetDocumentHeader> documentHeaders = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeaders(payBeginDate);
		for(TimesheetDocumentHeader timesheetDocumentHeader : documentHeaders){
			populateBatchJobEntry(timesheetDocumentHeader);
		}
    }


    @Override
    protected void populateBatchJobEntry(Object o) {
    	TimesheetDocumentHeader tdh = (TimesheetDocumentHeader)o;
        String ip = this.getNextIpAddressInCluster();
        if(StringUtils.isNotBlank(ip)){
            //insert a batch job entry here
            BatchJobEntry entry = this.createBatchJobEntry(this.getBatchJobName(), ip, tdh.getPrincipalId(), tdh.getDocumentId(),null);
            TkServiceLocator.getBatchJobEntryService().saveBatchJobEntry(entry);
        } else {
            LOG.info("No ip found in cluster to assign batch jobs");
        }
    }

}
