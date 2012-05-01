package org.kuali.hr.time.roles.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.position.Position;
import org.kuali.hr.time.roles.TkRole;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kns.service.KNSServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class TkRoleServiceTest  extends TkTestCase {

	public static final String TEST_USER = "eric";
	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(TkRoleServiceTest.class);	
	
	String posNumber = null;
	String posRoleId = null;
	
	@Test
	public void testGetWorkAreaRoles() throws Exception {
		TkRoleService trs = TkServiceLocator.getTkRoleService();
		
		Long workArea = 999L;
		
		// Finds TkConstants.ROLE_TK_APPROVER roles
		Date asOfDate = new Date((new DateTime(2010, 8, 25, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		List<TkRole> roles = trs.getWorkAreaRoles(workArea, TkConstants.ROLE_TK_APPROVER, asOfDate);
		assertNotNull(roles);
		assertEquals("Incorrect number of roles.", 2, roles.size());
		
		asOfDate = new Date((new DateTime(2010, 8, 25, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		roles = trs.getWorkAreaRoles(workArea, TkConstants.ROLE_TK_APPROVER, asOfDate);
		assertNotNull(roles);
		assertEquals("Incorrect number of roles.", 2, roles.size());

		asOfDate = new Date((new DateTime(2010, 8, 25, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		roles = trs.getWorkAreaRoles(workArea, TkConstants.ROLE_TK_APPROVER, asOfDate);
		assertNotNull(roles);
		assertEquals("Incorrect number of roles.", 2, roles.size());
		for (TkRole role : roles) {
			assertTrue("Incorrect values.", role.getHrRolesId().equals("21") || role.getHrRolesId().equals("5"));
		}
		
		// Finds any roles
		roles=trs.getWorkAreaRoles(workArea, asOfDate);
		assertNotNull(roles);
		assertEquals("Incorrect number of roles.", 5, roles.size());
		for (TkRole role : roles) {
			assertTrue("Incorrect values.", 
					role.getHrRolesId().equals("23") ||
					role.getHrRolesId().equals("5")  ||
					role.getHrRolesId().equals("15") ||
					role.getHrRolesId().equals("20") ||
					role.getHrRolesId().equals("21"));
		}		
	}
	
	@Test
	public void testGetRolesForPrincipal() throws Exception {
		TkRoleService trs = TkServiceLocator.getTkRoleService();
		String principalId = TEST_USER;
		
		// All Role Names, One User with Specific asOfDate
		Date asOfDate = new Date((new DateTime(2010, 8, 1, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		List<TkRole> roles = trs.getRoles(principalId, asOfDate);
		assertNotNull(roles);
		assertEquals("Incorrect number of roles.", 2, roles.size());
		for (TkRole role: roles) {
			assertTrue("Incorrect values.", 
					role.getHrRolesId().equals("6") ||
					role.getHrRolesId().equals("16"));
		}
		
		// All Role Names, One User with Specific asOfDate with multiple timestamps
		asOfDate = new Date((new DateTime(2010, 8, 20, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		roles = trs.getRoles(principalId, asOfDate);
		assertNotNull(roles);
		assertEquals("Incorrect number of roles.", 2, roles.size());
		for (TkRole role: roles) {
			assertTrue("Incorrect values.", 
					role.getHrRolesId().equals("20") ||
					role.getHrRolesId().equals("21"));
		}
		
		// Specific Role Name, Specific User
		asOfDate = new Date((new DateTime(2010, 8, 21, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		roles = trs.getRoles(principalId, TkConstants.ROLE_TK_APPROVER, asOfDate);
		assertNotNull(roles);
		assertEquals("Incorrect number of roles.", 1, roles.size());
		for (TkRole role: roles) {
			assertTrue("Incorrect values.", 
					role.getHrRolesId().equals("21"));
		}
		
	}
	
	@Test
	public void testPositionRole() throws Exception {
		List<TkRole> lstRoles = TkServiceLocator.getTkRoleService().getRoles("earl", TKUtils.getCurrentDate());
		assertTrue(lstRoles!=null && !lstRoles.isEmpty());
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		Position pos = new Position();
		pos.setActive(true);
		pos.setTimestamp(new Timestamp(System.currentTimeMillis()));
		pos.setDescription("Advising");
		pos.setEffectiveDate(new Date((new DateTime(2010, 8, 20, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis()));
		pos.setPositionNumber("123456");
		KNSServiceLocator.getBusinessObjectService().save(pos);
		String posNumber = pos.getPositionNumber();
		
		//setup a job with that position
		Job job = new Job();
		job.setPositionNumber(posNumber);
		job.setPrincipalId("earl");
		job.setJobNumber(0L);
		job.setEffectiveDate(new Date((new DateTime(2010, 8, 20, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis()));
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
		//TkServiceLocator.getJobSerivce().saveOrUpdate(job);
		
		 TkRole tkRole = new TkRole();
		 tkRole.setPrincipalId(null);
		 tkRole.setRoleName(TkConstants.ROLE_TK_APPROVER);
		 tkRole.setUserPrincipalId("admin");
		 tkRole.setWorkArea(1234L);
		 tkRole.setEffectiveDate(new Date((new DateTime(2010, 8, 20, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis()));
		 tkRole.setTimestamp(new Timestamp(System.currentTimeMillis()));
		 tkRole.setActive(true);
		 tkRole.setPositionNumber(posNumber);
		 
		 TkServiceLocator.getTkRoleService().saveOrUpdate(tkRole);
		 posRoleId = tkRole.getHrRolesId();
	}

	@Override
	public void tearDown() throws Exception {
		TkRole tkRole = TkServiceLocator.getTkRoleService().getRole(posRoleId);
		KNSServiceLocator.getBusinessObjectService().delete(tkRole);
		super.tearDown();
	}

	// KPME-1410 Kagata
	@Test
	public void testSaveRoleName() throws Exception {
		TkRoleService trs = TkServiceLocator.getTkRoleService();
	
		TkRole tkrole = trs.getRole("9999");
		assertTrue(tkrole.getRoleName().equals(TkConstants.ROLE_TK_APPROVER));
		
		tkrole.setRoleName(TkConstants.ROLE_LV_REVIEWER);
		trs.saveOrUpdate(tkrole);
		
		TkRole newtkRole = trs.getRole("9999");
		assertTrue(newtkRole.getRoleName().equals(TkConstants.ROLE_LV_REVIEWER));	
	}	
	
}

