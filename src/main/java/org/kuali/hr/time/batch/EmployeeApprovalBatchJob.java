package org.kuali.hr.time.batch;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;


public class EmployeeApprovalBatchJob extends BatchJob {
	private Logger LOG = Logger.getLogger(EmployeeApprovalBatchJob.class);
	
	
	@Override
	public void runJob() {
		LOG.info("Running employee approval batch job");
		Date payBeginDate = null;
		//TODO populate pay begin date here
		List<TimesheetDocumentHeader> lstDocumentHeaders = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeaders(payBeginDate);
		for(TimesheetDocumentHeader timesheetDocumentHeader : lstDocumentHeaders){
			populateBatchJobEntry(timesheetDocumentHeader);
		}
		
		LOG.info("Finished employee approval batch job");
	}

	@Override
	protected void populateBatchJobEntry(Object o) {
		TimesheetDocumentHeader timesheetDocumentHeader = (TimesheetDocumentHeader)o;
		String ip = this.getNextIpAddressInCluster();
		if(StringUtils.isNotBlank(ip)){
			//TODO insert a batch job entry here
		} else {
			LOG.info("No ip found in cluster to assign batch jobs");
		}
	}

}
