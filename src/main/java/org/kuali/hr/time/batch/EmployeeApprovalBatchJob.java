package org.kuali.hr.time.batch;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;


public class EmployeeApprovalBatchJob extends BatchJob {
	private Logger LOG = Logger.getLogger(EmployeeApprovalBatchJob.class);
    private CalendarEntries calendarEntry;

    public EmployeeApprovalBatchJob(CalendarEntries calendarEntry) {
        this.calendarEntry = calendarEntry;
        setBatchJobName(TkConstants.BATCH_JOB_NAMES.APPROVE);
    }

	@Override
	public void doWork() {
		Date payBeginDate = calendarEntry.getBatchEmployeeApprovalDate();
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
            BatchJobEntry entry = this.createBatchJobEntry(this.getBatchJobName(), ip, timesheetDocumentHeader.getPrincipalId(), timesheetDocumentHeader.getDocumentId(),null);
            TkServiceLocator.getBatchJobEntryService().saveBatchJobEntry(entry);
		} else {
			LOG.info("No ip found in cluster to assign batch jobs");
		}
	}

}
