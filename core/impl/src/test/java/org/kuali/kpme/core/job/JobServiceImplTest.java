/**
 * Copyright 2004-2013 The Kuali Foundation
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
package org.kuali.kpme.core.job;

import java.util.List;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.kpme.core.KPMEUnitTestCase;
import org.kuali.kpme.core.job.service.JobService;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.TKUtils;

public class JobServiceImplTest extends KPMEUnitTestCase {

	public static final String TEST_USER = "admin";

	JobService jobService = null;
	

	@Before
	public void setUp() throws Exception {
		super.setUp();
		jobService=HrServiceLocator.getJobService();
	}
	
	@Test
	public void testGetJobs() {
		DateTime payPeriodEndDate = new DateTime(2010,7,30,1,0,0,0, TKUtils.getSystemDateTimeZone());
		List<Job> jobs = jobService.getJobs(TEST_USER, payPeriodEndDate.toLocalDate());
		Assert.assertNotNull("Jobs was null", jobs);
		Assert.assertEquals("Incorrect number of jobs", 2, jobs.size());
	}
	
	@Test
	public void testGetMaxJob() {
		Job aJob = jobService.getMaxJob("admin");
		Assert.assertNotNull("Max Job should not be null", aJob);
		Assert.assertEquals("Max job number of admin should be 30", new Long("30"), aJob.getJobNumber());
	}
	
	@Test
	public void testSearchJobs() throws Exception {
		List<Job> allResults = HrServiceLocator.getJobService().getJobs("admin", null, null, null, null, null, null, null, null, null, "Y", "N");
		Assert.assertEquals("Search returned the wrong number of results.", 9, allResults.size());
		
		List<Job> restrictedResults = HrServiceLocator.getJobService().getJobs("testuser6", null, null, null, null, null, null, null, null, null, "Y", "N");
		Assert.assertEquals("Search returned the wrong number of results.", 5, restrictedResults.size());
	}
	
}