package org.kuali.hr.time.batch;

import java.sql.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;


public class InitiateBatchJob extends BatchJob {
	private Logger LOG = Logger.getLogger(InitiateBatchJob.class);


    public InitiateBatchJob(String hrPyCalendarEntryId) {
        super();
        this.setBatchJobName(TkConstants.BATCH_JOB_NAMES.INITIATE);
        this.setHrPyCalendarEntryId(hrPyCalendarEntryId);
    }

	@Override
	public void doWork() {
		Date asOfDate = TKUtils.getCurrentDate();
		List<Assignment> lstAssignments = TkServiceLocator.getAssignmentService().getActiveAssignments(asOfDate);
		CalendarEntries payCalendarEntry = TkServiceLocator.getCalendarEntriesService().getCalendarEntries(this.getHrPyCalendarEntryId());
		for(Assignment assign : lstAssignments){
			TimesheetDocumentHeader tkDocHeader = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(assign.getPrincipalId(), payCalendarEntry.getBeginPeriodDateTime(), payCalendarEntry.getEndPeriodDateTime());
			if(tkDocHeader == null || StringUtils.equals(tkDocHeader.getDocumentStatus(),TkConstants.ROUTE_STATUS.CANCEL)){
				populateBatchJobEntry(assign);
			}
		}
	}


	@Override
	protected void populateBatchJobEntry(Object o) {
		Assignment assign = (Assignment)o;
		String ip = this.getNextIpAddressInCluster();
		if(StringUtils.isNotBlank(ip)){
			//insert a batch job entry here
            BatchJobEntry entry = this.createBatchJobEntry(this.getBatchJobName(), ip, assign.getPrincipalId(), null,null);
            TkServiceLocator.getBatchJobEntryService().saveBatchJobEntry(entry);
		} else {
			LOG.info("No ip found in cluster to assign batch jobs");
		}
	}

}
