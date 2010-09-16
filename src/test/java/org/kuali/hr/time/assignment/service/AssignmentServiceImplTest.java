package org.kuali.hr.time.assignment.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.dao.AssignmentDao;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.TkTestCase;

public class AssignmentServiceImplTest extends TkTestCase {

	private static final Logger LOG = Logger.getLogger(AssignmentServiceImplTest.class);
	AssignmentDao assignmentDao = null;
	AssignmentService assignmentService = null;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		assignmentDao = TkServiceLocator.getAssignmentDao();
		assignmentService=TkServiceLocator.getAssignmentService();
	}
	
	@Test
	public void testGetAssignments() throws Exception {
		List<Assignment> assignments = assignmentService.getAssignments("admin", new Date((new DateTime(2010,8,5,1,0,0,0,DateTimeZone.forID("EST"))).getMillis()));
		assertNotNull("Null assignment list", assignments);
		assertTrue("No assignments found", assignments.size() > 0);
		
		for(Assignment assign : assignments){
			assertNotNull("Null job found", assign.getJob());
			assertTrue("Job number is same", assign.getJob().getJobNumber().compareTo(assign.getJobNumber())==0);
		}
	}

}
