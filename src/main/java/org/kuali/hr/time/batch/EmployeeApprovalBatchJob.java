package org.kuali.hr.time.batch;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;


public class EmployeeApprovalBatchJob extends BatchJob {
	private Logger LOG = Logger.getLogger(EmployeeApprovalBatchJob.class);
    private PayCalendarEntries payCalendarEntry;

    public EmployeeApprovalBatchJob(PayCalendarEntries payCalendarEntry) {
        this.payCalendarEntry = payCalendarEntry;
    }

	@Override
	public void doWork() {
		Date payBeginDate = payCalendarEntry.getBatchEmployeeApprovalDate();
		//TODO populate pay begin date here
		List<TimesheetDocumentHeader> documentHeaders = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeaders(payBeginDate);
		for(TimesheetDocumentHeader timesheetDocumentHeader : documentHeaders){
			populateBatchJobEntry(timesheetDocumentHeader);
		}
	}

	@Override
	protected void populateBatchJobEntry(Object o) {
		TimesheetDocumentHeader timesheetDocumentHeader = (TimesheetDocumentHeader)o;
		String ip = this.getNextIpAddressInCluster();
		if(StringUtils.isNotBlank(ip)){
            //insert a batch job entry here
            BatchJobEntry entry = this.createBatchJobEntry(this.getBatchJobName(), ip, timesheetDocumentHeader.getPrincipalId(), timesheetDocumentHeader.getDocumentId());
            TkServiceLocator.getBatchJobEntryService().saveBatchJobEntry(entry);
		} else {
			LOG.info("No ip found in cluster to assign batch jobs");
		}
	}

}
