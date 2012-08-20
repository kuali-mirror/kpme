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
