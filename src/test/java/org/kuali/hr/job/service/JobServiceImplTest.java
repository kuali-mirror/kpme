package org.kuali.hr.job.service;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.TkTestCase;

public class JobServiceImplTest extends TkTestCase {

	public static final String TEST_USER = "admin";
	private static final Logger LOG = Logger.getLogger(JobServiceImplTest.class);

	JobService jobService = null;
	

	@Before
	public void setUp() throws Exception {
		super.setUp();
		jobService=TkServiceLocator.getJobSerivce();
	}
	
	@Test
	public void testGetJobs() {
		Set<Long> ids = new HashSet<Long>();
		Date payPeriodEndDate = new Date((new DateTime(2010,7,30,1,0,0,0,DateTimeZone.forID("EST"))).getMillis());
		List<Job> jobs = jobService.getJobs(TEST_USER, payPeriodEndDate);
		assertNotNull("Jobs was null", jobs);
		assertEquals("Incorrect number of jobs", 0, jobs.size());
		
		// Verifies correct timestamp by pid is pulled back
		payPeriodEndDate = new Date((new DateTime(2010,8,10,1,0,0,0,DateTimeZone.forID("EST"))).getMillis());
		jobs = jobService.getJobs(TEST_USER, payPeriodEndDate);
		assertNotNull("Jobs was null", jobs);
		assertEquals("Incorrect number of jobs", 2, jobs.size());
		ids = new HashSet<Long>();
		ids.add(new Long(1012));
		ids.add(new Long(1013));
		for (Job job : jobs) {
			assertTrue("Invalid ID.", ids.contains(job.getHrJobId()));
		}

		
		payPeriodEndDate = new Date((new DateTime(2010,8,11,1,0,0,0,DateTimeZone.forID("EST"))).getMillis());
		jobs = jobService.getJobs(TEST_USER, payPeriodEndDate);
		assertNotNull("Jobs was null", jobs);
		assertEquals("Incorrect number of jobs", 2, jobs.size());
		ids = new HashSet<Long>();
		ids.add(new Long(1015));
		ids.add(new Long(1012));
		for (Job job : jobs) {
			assertTrue("Invalid ID.", ids.contains(job.getHrJobId()));
		}
		
		
		payPeriodEndDate = new Date((new DateTime(2010,8,13,1,0,0,0,DateTimeZone.forID("EST"))).getMillis());
		jobs = jobService.getJobs(TEST_USER, payPeriodEndDate);
		assertNotNull("Jobs was null", jobs);
		assertEquals("Incorrect number of jobs", 2, jobs.size());		
		ids = new HashSet<Long>();
		ids.add(new Long(1016));
		ids.add(new Long(1012));
		for (Job job : jobs) {
			assertTrue("Invalid ID.", ids.contains(job.getHrJobId()));
		}
	}
}
