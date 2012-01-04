package org.kuali.hr.time.batch;

import org.junit.Before;
import org.junit.Test;
import org.kuali.hr.time.batch.service.BatchJobEntryService;
import org.kuali.hr.time.paycalendar.PayCalendar;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.util.TkConstants;

import java.util.List;

public class BatchJobEntryTest extends TkTestCase {
	private static final String BATCH_JOB_NAME = "testJob";
	private Long bjeId;
	
	private BatchJobEntryService bjeService;
	
	@Before
    public void setUp() throws Exception {
    	super.setUp();
    	bjeService = TkServiceLocator.getBatchJobEntryService();
    }
	private void creatAndSaveBatchJobEntry(){
		BatchJobEntry bje = new BatchJobEntry();
		bje.setBatchJobName(BATCH_JOB_NAME);
		bje.setBatchJobEntryStatus(TkConstants.BATCH_JOB_ENTRY_STATUS.SCHEDULED);
		bje.setBatchJobException("test error");
		bje.setIpAddress("1.2.3.4");
		bje.setPrincipalId("admin");
		bje.setDocumentId("testDoc");
		
		PayCalendar pc = TkServiceLocator.getPayCalendarSerivce().getPayCalendarByGroup("BW-CAL");		
		bje.setHrPyCalendarEntryId(pc.getHrPyCalendarId());
		List<BatchJob> batchJobs = TkServiceLocator.getBatchJobService().getBatchJobs(pc.getHrPyCalendarId());
		Long batchJobId = new Long(0);
		if(!batchJobs.isEmpty()){
			batchJobId = batchJobs.get(0).getTkBatchJobId();
		}
		bje.setTkBatchJobId(batchJobId);
		
		bjeService.saveBatchJobEntry(bje);
		
		bjeId = bje.getTkBatchJobEntryId();
	}
	
	@Test
	public void testSavingAndRetrievingBatchJobEntry() throws Exception {
		creatAndSaveBatchJobEntry();
		BatchJobEntry bje = (BatchJobEntry) bjeService.getBatchJobEntry(bjeId);
		assertTrue("Batch Job Name not right", bje.getBatchJobName().equals(BATCH_JOB_NAME));
		
		List<BatchJobEntry> entries = bjeService.getBatchJobEntries(bje.getTkBatchJobId());
		assertTrue("Batch Job Entry not empty", (!entries.isEmpty()));
	}
}
