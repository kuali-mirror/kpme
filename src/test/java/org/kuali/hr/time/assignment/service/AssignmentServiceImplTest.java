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
package org.kuali.hr.time.assignment.service;

import java.sql.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.dao.AssignmentDao;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;

public class AssignmentServiceImplTest extends KPMETestCase {

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
		List<Assignment> assignments = assignmentService.getAssignments("admin", new Date((new DateTime(2010,8,5,1,0,0,0, TKUtils.getSystemDateTimeZone())).getMillis()));
		Assert.assertNotNull("Null assignment list", assignments);
		Assert.assertTrue("No assignments found", assignments.size() > 0);
		
		for(Assignment assign : assignments){
			Assert.assertNotNull("Null job found", assign.getJob());
			Assert.assertTrue("Job number is same", assign.getJob().getJobNumber().compareTo(assign.getJobNumber())==0);
		}
		
	}
	@Test
	public void testGetAssignmentsByCalEntryForLeaveCalendar() throws Exception {
		CalendarEntries ce = TkServiceLocator.getCalendarEntriesService().getCalendarEntries("5000");
		List<Assignment> assignments = assignmentService.getAssignmentsByCalEntryForLeaveCalendar("testUser", ce);
		Assert.assertNotNull("Null assignment list", assignments);
		
		Assert.assertTrue("Assignments size for Leave calendar should be 2, not " + assignments.size(), assignments.size() == 2);
		for(Assignment anAssignment : assignments) {
			Assert.assertTrue("Assignment found for Leave calendar should be '5001' or '5002', not " + anAssignment.getTkAssignmentId(), 
					anAssignment.getTkAssignmentId().equals("5001") || anAssignment.getTkAssignmentId().equals("5002") );
		}
	}
	
	@Test
	public void testGetAssignmentsByCalEntryForTimeCalendar() throws Exception {
		CalendarEntries ce = TkServiceLocator.getCalendarEntriesService().getCalendarEntries("5000");
		List<Assignment> assignments = assignmentService.getAssignmentsByCalEntryForTimeCalendar("testUser", ce);
		Assert.assertNotNull("Null assignment list", assignments);
		
		Assert.assertTrue("Assignments size for Time calendar should be 2, not " + assignments.size(), assignments.size() == 2);
		for(Assignment anAssignment : assignments) {
			Assert.assertTrue("Assignment found for Time calendar should be '5000' or '5001', not " + anAssignment.getTkAssignmentId(), 
					anAssignment.getTkAssignmentId().equals("5000") || anAssignment.getTkAssignmentId().equals("5001") );
		}
		
	}
	@Test
	public void testGetAssignmentsByPayEntry() throws Exception {
		CalendarEntries ce = TkServiceLocator.getCalendarEntriesService().getCalendarEntries("5000");
		List<Assignment> assignments = assignmentService.getAssignmentsByPayEntry("testUser", ce);
		Assert.assertNotNull("Null assignment list", assignments);
		Assert.assertTrue("Assignments size for Calendar Entry 5000 should be 3, not " + assignments.size(), assignments.size() == 3);
		
		ce = TkServiceLocator.getCalendarEntriesService().getCalendarEntries("5001");
		assignments = assignmentService.getAssignmentsByPayEntry("testUser", ce);
		Assert.assertNotNull("Null assignment list", assignments);
		Assert.assertTrue("Assignments size for Calendar Entry 5000 should be 4, not " + assignments.size(), assignments.size() == 4);
	}
}
