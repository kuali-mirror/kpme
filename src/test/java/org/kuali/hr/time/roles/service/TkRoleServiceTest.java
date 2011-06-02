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
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kns.service.KNSServiceLocator;

public class TkRoleServiceTest  extends TkTestCase {

	public static final String TEST_USER = "eric";
	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(TkRoleServiceTest.class);	
	
	
	
	
	@Test
	public void testGetWorkAreaRoles() throws Exception {
		TkRoleService trs = TkServiceLocator.getTkRoleService();
		
		Long workArea = 999L;
		
		// Finds TkConstants.ROLE_TK_APPROVER roles
		Date asOfDate = new Date((new DateTime(2010, 8, 1, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		List<TkRole> roles = trs.getWorkAreaRoles(workArea, TkConstants.ROLE_TK_APPROVER, asOfDate);
		assertNotNull(roles);
		assertEquals("Incorrect number of roles.", 2, roles.size());
		
		asOfDate = new Date((new DateTime(2010, 8, 10, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		roles = trs.getWorkAreaRoles(workArea, TkConstants.ROLE_TK_APPROVER, asOfDate);
		assertNotNull(roles);
		assertEquals("Incorrect number of roles.", 2, roles.size());

		asOfDate = new Date((new DateTime(2010, 8, 20, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		roles = trs.getWorkAreaRoles(workArea, TkConstants.ROLE_TK_APPROVER, asOfDate);
		assertNotNull(roles);
		assertEquals("Incorrect number of roles.", 2, roles.size());
		for (TkRole role : roles) {
			assertTrue("Incorrect values.", role.getTkRolesId().longValue() == 21L || role.getTkRolesId().longValue() == 5L);
		}
		
		// Finds any roles
		roles=trs.getWorkAreaRoles(workArea, asOfDate);
		assertNotNull(roles);
		assertEquals("Incorrect number of roles.", 5, roles.size());
		for (TkRole role : roles) {
			assertTrue("Incorrect values.", 
					role.getTkRolesId().longValue() == 23L || 
					role.getTkRolesId().longValue() == 5L  ||
					role.getTkRolesId().longValue() == 15L ||
					role.getTkRolesId().longValue() == 20L || 
					role.getTkRolesId().longValue() == 21L);
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
					role.getTkRolesId().longValue() == 6L || 
					role.getTkRolesId().longValue() == 16L);			
		}
		
		// All Role Names, One User with Specific asOfDate with multiple timestamps
		asOfDate = new Date((new DateTime(2010, 8, 20, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		roles = trs.getRoles(principalId, asOfDate);
		assertNotNull(roles);
		assertEquals("Incorrect number of roles.", 2, roles.size());
		for (TkRole role: roles) {
			assertTrue("Incorrect values.", 
					role.getTkRolesId().longValue() == 20L || 
					role.getTkRolesId().longValue() == 21L);			
		}
		
		// Specific Role Name, Specific User
		asOfDate = new Date((new DateTime(2010, 8, 21, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		roles = trs.getRoles(principalId, TkConstants.ROLE_TK_APPROVER, asOfDate);
		assertNotNull(roles);
		assertEquals("Incorrect number of roles.", 1, roles.size());
		for (TkRole role: roles) {
			assertTrue("Incorrect values.", 
					role.getTkRolesId().longValue() == 21L);			
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
		KNSServiceLocator.getBusinessObjectService().save(pos);
		Long posNumber = pos.getPositionNumber();
		
		//setup a job with that position
		Job job = new Job();
		job.setPositionNumber(posNumber);
		job.setPrincipalId("earl");
		job.setJobNumber(0L);
		job.setEffectiveDate(new Date((new DateTime(2010, 8, 20, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis()));
		job.setDept("BL-CHEM");
		job.setTkSalGroup("BW1");
		job.setPayGrade("2IT");
		job.setTimestamp(new Timestamp(System.currentTimeMillis()));
		job.setCompRate(BigDecimal.ZERO);
		job.setLocation("BL");
		job.setStandardHours(BigDecimal.ZERO);
		job.setHrPayType("BW1");
		job.setActive(true);
		job.setPrimaryIndicator(true);
		
		
		TkServiceLocator.getJobSerivce().saveOrUpdate(job);
		
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
	
	}

	@Override
	public void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
	}
}
