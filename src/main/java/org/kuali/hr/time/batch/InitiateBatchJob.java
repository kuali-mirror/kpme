package org.kuali.hr.time.batch;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;

import java.sql.Date;
import java.util.List;


public class InitiateBatchJob extends BatchJob {
	private Logger LOG = Logger.getLogger(InitiateBatchJob.class);


    public InitiateBatchJob(Long hrPyCalendarEntryId) {
        super();
        this.setBatchJobName(TkConstants.BATCH_JOB_NAMES.INITIATE);
        this.setPayCalendarEntryId(hrPyCalendarEntryId);
    }

	@Override
	public void doWork() {
		Date asOfDate = TKUtils.getCurrentDate();
		List<Assignment> lstAssignments = TkServiceLocator.getAssignmentService().getActiveAssignments(asOfDate);
		for(Assignment assign : lstAssignments){
			populateBatchJobEntry(assign);
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
