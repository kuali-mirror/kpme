/**
 * Copyright 2004-2012 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.hr.time.batch;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.batch.service.BatchJobService;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TkConstants;

public class BatchJobTest extends KPMETestCase {

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yy");
	private static final String BATCH_JOB_NAME = "testJob";
	private Long ibjId;
	private BatchJobService bjService;

	@Before
    public void setUp() throws Exception {
    	super.setUp();
    	bjService = TkServiceLocator.getBatchJobService();
    }
	
	private void creatAndSaveBatchJob() throws Exception{
		Date beginPeriodDate = DATE_FORMAT.parse("04/01/2011");
		Date endPeriodDate = DATE_FORMAT.parse("04/15/2011");
		CalendarEntries calendarEntry = TkServiceLocator.getCalendarEntriesService().getCalendarEntriesByBeginAndEndDate(beginPeriodDate, endPeriodDate);
		InitiateBatchJob ibj = new InitiateBatchJob(calendarEntry);
		ibj.setBatchJobName(BATCH_JOB_NAME);
		ibj.setBatchJobStatus(TkConstants.BATCH_JOB_ENTRY_STATUS.SCHEDULED);
		ibj.setTimeElapsed(new Long(123));

		bjService.saveBatchJob(ibj);

		ibjId = ibj.getTkBatchJobId();
	}

	@Test
	public void testSavingAndRetrievingBatchJob() throws Exception {
		creatAndSaveBatchJob();
		BatchJob bj = (BatchJob) bjService.getBatchJob(ibjId);
		Assert.assertTrue("Batch Job Name not right", bj.getBatchJobName().equals(BATCH_JOB_NAME));
	}
}
