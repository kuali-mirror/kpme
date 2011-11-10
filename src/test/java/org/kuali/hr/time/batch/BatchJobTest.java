package org.kuali.hr.time.batch;

import org.junit.Before;
import org.junit.Test;
import org.kuali.hr.time.batch.service.BatchJobService;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.util.TkConstants;

public class BatchJobTest extends TkTestCase {

	private static final String BATCH_JOB_NAME = "testJob";
	private Long ibjId;
	private BatchJobService bjService;

	@Before
    public void setUp() throws Exception {
    	super.setUp();
    	bjService = TkServiceLocator.getBatchJobService();
    }
	private void creatAndSaveBatchJob(){
		InitiateBatchJob ibj = new InitiateBatchJob(5L);
		ibj.setBatchJobName(BATCH_JOB_NAME);
		ibj.setBatchJobStatus(TkConstants.BATCH_JOB_ENTRY_STATUS.SCHEDULED);
		ibj.setTimeElapsed(new Long(123));
		ibj.setCalendarEntryId(new Long(1234));

		bjService.saveBatchJob(ibj);

		ibjId = ibj.getTkBatchJobId();
	}

	@Test
	public void testSavingAndRetrievingBatchJob() throws Exception {
		creatAndSaveBatchJob();
		BatchJob bj = (BatchJob) bjService.getBatchJob(ibjId);
		assertTrue("Batch Job Name not right", bj.getBatchJobName().equals(BATCH_JOB_NAME));
	}
}
