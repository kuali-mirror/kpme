package org.kuali.hr.time.earncode.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.kuali.hr.job.service.JobServiceImplTest;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.util.TKUtils;

public class EarnCodeServiceImplTest extends TkTestCase {


	public static final String TEST_USER = "admin";
	public static final Long TEST_ASSIGNMENT_JOB_NUMBER = 1L;
	public static final Long TEST_ASSIGNMENT_JOB_NUMBER_2 = 2L;
	public static final Long TEST_ASSIGNMENT_JOB_NUMBER_3 = 3L;
	public static final Long TEST_ASSIGNMENT_JOB_NUMBER_4 = 4L;
	
	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(JobServiceImplTest.class);

	EarnCodeService earnCodeService = null;
	

	@Before
	public void setUp() throws Exception {
		super.setUp();
		earnCodeService=TkServiceLocator.getEarnCodeService();
	}
	
	@Test
	public void getEarnCodes() throws Exception {
		List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignments(TEST_USER, TKUtils.getTimelessDate(null));
		assertNotNull(assignments);
		assertTrue("Emtpy assignment list", !assignments.isEmpty());
		
		Assignment assignment1 = null;
		Assignment assignment2 = null;
		Assignment assignment3 = null;
		Assignment assignment4 = null;
		for (Assignment a : assignments) {
			if (a.getJobNumber().equals(TEST_ASSIGNMENT_JOB_NUMBER)) {
				assignment1 = a;
			} else if (a.getJobNumber().equals(TEST_ASSIGNMENT_JOB_NUMBER_2)) {
				assignment2 = a;
			} else if (a.getJobNumber().equals(TEST_ASSIGNMENT_JOB_NUMBER_3)) {
				assignment3 = a;
			} else if (a.getJobNumber().equals(TEST_ASSIGNMENT_JOB_NUMBER_4)) {
				assignment4 = a;
			}
		}
		
		// one for each test scenario involving wildcards at least...
		assertNotNull("Test assignment not found.", assignment1);
		assertNotNull("Test assignment not found.", assignment2);
		assertNotNull("Test assignment not found.", assignment3);
		assertNotNull("Test assignment not found.", assignment4);
		
		// Testing standard lookup.
		List<EarnCode> earnCodes = earnCodeService.getEarnCodes(assignment1);
		assertEquals("Wrong number of earn codes returned.", 5, earnCodes.size());
		
		// Wildcard on SalGroup
		earnCodes = earnCodeService.getEarnCodes(assignment2);
		assertEquals("Wrong number of earn codes returned.", 2, earnCodes.size());
		for (EarnCode ec : earnCodes) {
			assertTrue("Wrong earn codes.", (ec.getEarnCode().equals("RGN") || ec.getEarnCode().equals("XYY")));
		}
		
		// Dual Wildcards
		earnCodes = earnCodeService.getEarnCodes(assignment3);
		assertEquals("Wrong number of earn codes returned.", 2, earnCodes.size());
		for (EarnCode ec : earnCodes) {
			assertTrue("Wrong earn codes.", (ec.getEarnCode().equals("RGN") || ec.getEarnCode().equals("XZZ")));
		}
		
		// Wildcard on Department
		earnCodes = earnCodeService.getEarnCodes(assignment4);
		assertEquals("Wrong number of earn codes returned.", 2, earnCodes.size());
		for (EarnCode ec : earnCodes) {
			assertTrue("Wrong earn codes.", (ec.getEarnCode().equals("RGN") || ec.getEarnCode().equals("XYZ")));
		}
		
	}
	
	@Test
	public void testEarnCodeMaintenancePage() throws Exception{
		//TODO - Sai confirm the maintenance page renders
		//TODO - Sai confirm that the error is throw by not selecting a record type
		//TODO - Sai confirm that the error is thrown if more than one record type is selected
	}
	
	
}
