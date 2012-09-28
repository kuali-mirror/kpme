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
package org.kuali.hr.job.service;

import java.sql.Date;
import java.util.List;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;
import org.kuali.hr.job.Job;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;

public class JobServiceImplTest extends KPMETestCase {

	public static final String TEST_USER = "admin";

	JobService jobService = null;
	

	@Before
	public void setUp() throws Exception {
		super.setUp();
		jobService=TkServiceLocator.getJobService();
	}
	
	@Test
	public void testGetJobs() {
		Date payPeriodEndDate = new Date((new DateTime(2010,7,30,1,0,0,0, TKUtils.getSystemDateTimeZone())).getMillis());
		List<Job> jobs = jobService.getJobs(TEST_USER, payPeriodEndDate);
		Assert.assertNotNull("Jobs was null", jobs);
		Assert.assertEquals("Incorrect number of jobs", 2, jobs.size());
	}
	
	@Test
	public void testGetMaxJob() {
		Job aJob = jobService.getMaxJob("admin");
		Assert.assertNotNull("Max Job should not be null", aJob);
		Assert.assertEquals("Max job number of admin should be 30", new Long("30"), aJob.getJobNumber());
	}
}
