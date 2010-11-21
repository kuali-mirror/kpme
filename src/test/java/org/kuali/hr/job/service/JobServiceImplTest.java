package org.kuali.hr.job.service;

import java.sql.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.TkTestCase;

public class JobServiceImplTest extends TkTestCase {

	public static final String TEST_USER = "admin";

	JobService jobService = null;
	

	@Before
	public void setUp() throws Exception {
		super.setUp();
		jobService=TkServiceLocator.getJobSerivce();
	}
	
	@Test
	public void testGetJobs() {
		Date payPeriodEndDate = new Date((new DateTime(2010,7,30,1,0,0,0,DateTimeZone.forID("EST"))).getMillis());
		List<Job> jobs = jobService.getJobs(TEST_USER, payPeriodEndDate);
		assertNotNull("Jobs was null", jobs);
		assertEquals("Incorrect number of jobs", 2, jobs.size());
	}
}
