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
package org.kuali.hr.time.roles.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.job.Job;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.position.Position;
import org.kuali.hr.time.roles.TkRole;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.krad.service.KRADServiceLocator;

public class TkRoleServiceTest  extends KPMETestCase {

	public static final String ADMIN = "admin";
	public static final String TEST_USER = "eric";
	public static final Long WORK_AREA = 999L;

	private String posRoleId = null;
	
	@Test
	public void testGetAnyWorkAreaRoles() throws Exception {
		Date asOfDate = new Date((new DateTime(2010, 8, 25, 12, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
		List<TkRole> roles = TkServiceLocator.getTkRoleService().getWorkAreaRoles(WORK_AREA, asOfDate);
		Assert.assertNotNull(roles);
		Assert.assertEquals("Incorrect number of roles.", 4, roles.size());
		for (TkRole role : roles) {
			Assert.assertTrue("Incorrect values.", 
					(role.getPrincipalId().equals(ADMIN) && role.getRoleName().equals(TkConstants.ROLE_TK_LOCATION_ADMIN))
				 || (role.getPrincipalId().equals(ADMIN) && role.getRoleName().equals(TkConstants.ROLE_TK_APPROVER))
				 || (role.getPrincipalId().equals(TEST_USER) && role.getRoleName().equals(TkConstants.ROLE_TK_LOCATION_ADMIN))
				 || (role.getPrincipalId().equals(TEST_USER) && role.getRoleName().equals(TkConstants.ROLE_TK_APPROVER)));
		}
	}
	
	@Test
	public void testGetApproverWorkAreaRoles() throws Exception {
		Date asOfDate = new Date((new DateTime(2010, 8, 25, 12, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
		List<TkRole> roles = TkServiceLocator.getTkRoleService().getWorkAreaRoles(WORK_AREA, TkConstants.ROLE_TK_APPROVER, asOfDate);
		Assert.assertNotNull(roles);
		Assert.assertEquals("Incorrect number of roles.", 2, roles.size());
		for (TkRole role : roles) {
			Assert.assertTrue("Incorrect values.", role.getPrincipalId().equals(ADMIN) || role.getPrincipalId().equals(TEST_USER));
		}
	}
	
	@Test
	public void testGetRolesForPrincipal() throws Exception {
		Date asOfDate = new Date((new DateTime(2010, 8, 21, 12, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
		List<TkRole> roles = TkServiceLocator.getTkRoleService().getRoles(TEST_USER, TkConstants.ROLE_TK_APPROVER, asOfDate);
		Assert.assertNotNull(roles);
		Assert.assertEquals("Incorrect number of roles.", 1, roles.size());
		for (TkRole role: roles) {
			Assert.assertTrue("Incorrect values.", 
				   role.getPrincipalId().equals(TEST_USER) 
				&& role.getRoleName().equals(TkConstants.ROLE_TK_APPROVER)
				&& role.getEffectiveDate().before(asOfDate));
		}
	}
	
	@Test
	public void testGetEffectiveDateRolesForPrincipal() throws Exception {
		Date asOfDate = new Date((new DateTime(2010, 8, 1, 12, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
		List<TkRole> roles = TkServiceLocator.getTkRoleService().getRoles(TEST_USER, asOfDate);
		Assert.assertNotNull(roles);
		Assert.assertEquals("Incorrect number of roles.", 2, roles.size());
		for (TkRole role: roles) {
			Assert.assertTrue("Incorrect values.", 
					role.getPrincipalId().equals(TEST_USER) 
				&& (role.getRoleName().equals(TkConstants.ROLE_TK_LOCATION_ADMIN) || role.getRoleName().equals(TkConstants.ROLE_TK_APPROVER)
				&& role.getEffectiveDate().before(asOfDate)));
		}
	}
	
	@Test
	public void testPositionRole() throws Exception {
		List<TkRole> lstRoles = TkServiceLocator.getTkRoleService().getRoles("earl", TKUtils.getCurrentDate());
		Assert.assertTrue(lstRoles!=null && !lstRoles.isEmpty());
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		Position pos = new Position();
		pos.setActive(true);
		pos.setTimestamp(new Timestamp(System.currentTimeMillis()));
		pos.setDescription("Advising");
		pos.setEffectiveDate(new Date((new DateTime(2010, 8, 20, 12, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis()));
		pos.setPositionNumber("123456");
		KRADServiceLocator.getBusinessObjectService().save(pos);
		String posNumber = pos.getPositionNumber();
		
		//setup a job with that position
		Job job = new Job();
		job.setPositionNumber(posNumber);
		job.setPrincipalId("earl");
		job.setJobNumber(0L);
		job.setEffectiveDate(new Date((new DateTime(2010, 8, 20, 12, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis()));
		job.setDept("BL-CHEM");
		job.setHrSalGroup("BW1");
		job.setPayGrade("2IT");
		job.setTimestamp(new Timestamp(System.currentTimeMillis()));
		job.setCompRate(BigDecimal.ZERO);
		job.setLocation("BL");
		job.setStandardHours(BigDecimal.ZERO);
		job.setHrPayType("BW1");
		job.setActive(true);
		job.setPrimaryIndicator(true);
		job.setEligibleForLeave(false);
		job.setHrJobId("9999");

		// This is causing all the unit tests to have an error - needs to be looked into later.
		TkServiceLocator.getJobService().saveOrUpdate(job);
		
		TkRole tkRole = new TkRole();
		tkRole.setPrincipalId(null);
		tkRole.setRoleName(TkConstants.ROLE_TK_APPROVER);
		tkRole.setUserPrincipalId("admin");
		tkRole.setWorkArea(1234L);
		tkRole.setEffectiveDate(new Date((new DateTime(2010, 8, 20, 12, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis()));
		tkRole.setTimestamp(new Timestamp(System.currentTimeMillis()));
		tkRole.setActive(true);
		tkRole.setPositionNumber(posNumber);
		 
		TkServiceLocator.getTkRoleService().saveOrUpdate(tkRole);
		posRoleId = tkRole.getHrRolesId();
	}

	@Override
	public void tearDown() throws Exception {
		TkRole tkRole = TkServiceLocator.getTkRoleService().getRole(posRoleId);
		KRADServiceLocator.getBusinessObjectService().delete(tkRole);
		super.tearDown();
	}
}