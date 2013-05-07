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

package org.kuali.hr.pm.positionappointment.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kuali.kpme.pm.positionappointment.PositionAppointment;
import org.kuali.kpme.pm.service.base.PmServiceLocator;
import org.kuali.hr.test.KPMETestCase;

public class PositionAppointmentServiceTest extends KPMETestCase {

	private final String pmPositionAppointmentId = "123456789";
	private final String positionAppointment = "testAppointment";
	private final String institution = "testInst";
	private final String campus = "TS";

	@Before
	public void setUp() throws Exception {
	super.setUp();
	
	}
	
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	@Test
	public void testGetPositionAppointmentById() throws Exception {

		PositionAppointment positionAppointment = PmServiceLocator.getPositionAppointmentService().getPositionAppointmentById(pmPositionAppointmentId);
		assertEquals("testAppointment", positionAppointment.getPositionAppointment());
	}
	
	@Test
	public void testGetPositionAppointmentList() throws Exception {

		LocalDate todayDate = new LocalDate();
		List<PositionAppointment> positionAppointments = PmServiceLocator.getPositionAppointmentService().getPositionAppointmentList(positionAppointment, institution, campus, todayDate);
		assertTrue(positionAppointments.size() == 1);
	}
	
}
