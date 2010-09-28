package org.kuali.hr.time.roles.service;

import java.sql.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import org.kuali.hr.time.roles.TkRole;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.util.TkConstants;

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
			assertTrue("Incorrect values.", role.getTkRolesId().longValue() == 10L || role.getTkRolesId().longValue() == 5L);
		}
		
		// Finds any roles
		roles=trs.getWorkAreaRoles(workArea, asOfDate);
		assertNotNull(roles);
		assertEquals("Incorrect number of roles.", 4, roles.size());
		for (TkRole role : roles) {
			assertTrue("Incorrect values.", 
					role.getTkRolesId().longValue() == 10L || 
					role.getTkRolesId().longValue() == 5L  ||
					role.getTkRolesId().longValue() == 15L ||
					role.getTkRolesId().longValue() == 20L );
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
					role.getTkRolesId().longValue() == 10L || 
					role.getTkRolesId().longValue() == 20L);			
		}
		
		// Specific Role Name, Specific User
		asOfDate = new Date((new DateTime(2010, 8, 20, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		roles = trs.getRoles(principalId, TkConstants.ROLE_TK_APPROVER, asOfDate);
		assertNotNull(roles);
		assertEquals("Incorrect number of roles.", 1, roles.size());
		for (TkRole role: roles) {
			assertTrue("Incorrect values.", 
					role.getTkRolesId().longValue() == 10L);			
		}
		
	}
}
