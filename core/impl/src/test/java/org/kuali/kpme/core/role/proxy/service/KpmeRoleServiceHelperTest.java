/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.core.role.proxy.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.kpme.core.CoreUnitTestCase;
import org.kuali.kpme.core.IntegrationTest;
import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.service.role.KPMERoleService;
import org.kuali.rice.core.api.membership.MemberType;
import org.kuali.rice.kim.api.role.RoleMember;
import org.kuali.rice.kim.api.role.RoleMembership;
import org.kuali.rice.kim.api.role.RoleService;

@IntegrationTest
public class KpmeRoleServiceHelperTest extends CoreUnitTestCase {
	
	private KPMERoleService kpmeRoleService;
	private RoleService roleService;
	
	@Before
    public void setUp() throws Exception {
           super.setUp();
           kpmeRoleService = HrServiceLocator.getKPMERoleService();
           roleService = HrServiceLocator.getService("kimRoleService");
    }

	// helper method for writing assertions
	private boolean roleMembersListContains(RoleMember member, List<RoleMember> roleMembers) {
		boolean retVal = false;
		String memberId = member.getMemberId();
		MemberType memberType = member.getType();
		for(RoleMember roleMember: roleMembers) {
			if( StringUtils.equals(memberId, roleMember.getMemberId()) && memberType.equals(roleMember.getType()) ) {
				if(member.getAttributes() != null) {
					if(member.getAttributes().equals(roleMember.getAttributes())) {
						retVal = true;
					}
				}
				else {
					retVal = true;
				}
			}
			// now check if active from and active to dates match
			if(retVal == true) {
				if( checkDate(member.getActiveFromDate(), roleMember.getActiveFromDate()) && checkDate(member.getActiveToDate(), roleMember.getActiveToDate()) ) {
					break;
				}
				else {
					retVal = false;
				}
			}
		}
		return retVal;
	}
	
	
	private boolean checkDate(DateTime date1, DateTime date2) {
		if( (date1 != null) ) {
			if(date2 == null) {
				return false;
			}
			else if( date1.isEqual(date2) ) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return true;
		}
	}

	// helper method for writing assertions
	private boolean roleMembershipsListContains(RoleMembership membership, List<RoleMembership> roleMemberships) {
		boolean retVal = false;
		String memberId = membership.getMemberId();
		MemberType memberType = membership.getType();
		for(RoleMembership roleMembership: roleMemberships) {
			if( StringUtils.equals(memberId, roleMembership.getMemberId()) && memberType.equals(roleMembership.getType()) ) {
				if(membership.getQualifier() != null) {
					if(membership.getQualifier().equals(roleMembership.getQualifier())) {
						retVal = true;
						break;
					}
				}
				else {
					retVal = true;
					break;
				}
			}
		}
		return retVal;
	}
	
	private List<RoleMembership> getCurrentActiveInWorkArea(Long workArea, String roleName) {
		List<RoleMembership> roleMembers = new ArrayList<RoleMembership>();
		Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName(), String.valueOf(workArea));
		String roleId = roleService.getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_HR.getNamespaceCode(), roleName);
		roleMembers = roleService.getRoleMembers(Collections.singletonList(roleId), qualification);
		return roleMembers;
	}
	
	private List<RoleMembership> getCurrentActiveApproversInWorkArea(Long workArea) {
		return getCurrentActiveInWorkArea(workArea, KPMERole.APPROVER_PROXY_ROLE.getRoleName());
	}
	
	@SuppressWarnings("unused")
	private List<RoleMembership> getCurrentActiveApproverDelegatesInWorkArea(Long workArea) {
		return getCurrentActiveInWorkArea(workArea, KPMERole.APPROVER_DELEGATE_PROXY_ROLE.getRoleName());
	}
	
	private boolean hasCurrentApproverRoleInWorkArea(String principalId, long workArea) {
		Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName(), String.valueOf(workArea));
		
		String roleId = roleService.getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER_PROXY_ROLE.getRoleName());
		return roleService.principalHasRole(principalId, Collections.singletonList(roleId), qualification);
	}
	
	
	
	
	
	
	@Test
	public void testHasCurrentApproverRoleInWorkArea() {
		boolean hasRoleInWorkArea;
		
		// test the KIM usage of the proxy roles
		// testing approver proxy
		hasRoleInWorkArea = this.hasCurrentApproverRoleInWorkArea("test_principal_1", (long) 4444);
		Assert.assertTrue(hasRoleInWorkArea);
		// check for caching
		hasRoleInWorkArea = this.hasCurrentApproverRoleInWorkArea("test_principal_1", (long) 4444);
		Assert.assertTrue(hasRoleInWorkArea);
		
		hasRoleInWorkArea = this.hasCurrentApproverRoleInWorkArea("SYS-ADMIN-PRN-1", (long) 4444);
		Assert.assertFalse(hasRoleInWorkArea);
	}
	
	@Test
	public void testGetRoleMemberships() {
		List<RoleMembership> roleMemberships;		
		
		RoleMembership membership = null;
		Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName(), "4444");
		membership = RoleMembership.Builder.create("KPME0020", null, "KPME0001", MemberType.GROUP, qualification).build();
		
		roleMemberships = this.getCurrentActiveApproversInWorkArea((long) 4444);
		Assert.assertTrue(roleMembershipsListContains(membership, roleMemberships));
		// check caching
		List<RoleMembership> roleMembershipsCached = this.getCurrentActiveApproversInWorkArea((long) 4444);
		Assert.assertTrue("Caching of role memberships is not working", roleMembershipsCached == roleMemberships);
	}
	
	
	@Test
	public void testGetRoleMembers() {
		DateTime today = new DateTime();
		DateTime yesterday = today.minusDays(1);
		
		
		List<RoleMember> roleMembers;
		Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName(), "4444");
		RoleMember roleMember = RoleMember.Builder.create("KPM0013", null, "KPME0001", MemberType.GROUP, DateTime.parse("2011-01-01"), null, qualification, null, null).build();
		// call with asOfDate = null
		roleMembers = kpmeRoleService.getRoleMembersInWorkArea(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), (long) 4444, null, true);
		Assert.assertTrue(roleMembersListContains(roleMember, roleMembers));
		
		// just to check caching
		List<RoleMember> roleMembersCached = kpmeRoleService.getRoleMembersInWorkArea(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), (long) 4444, null, true);
		Assert.assertTrue("Caching of role members is not working", roleMembersCached == roleMembers);
		
		qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName(), "4444");
		qualification.put(KPMERoleMemberAttribute.POSITION.getRoleMemberAttributeName(), "POS-2");		
		roleMember = RoleMember.Builder.create("KPME0016", null, "POS-2-PRN-2", MemberType.PRINCIPAL, null, null, qualification, null, null).build();
		// call with asOfDate = today, should use caching from above
		roleMembersCached = kpmeRoleService.getRoleMembersInWorkArea(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), (long) 4444, today, true);
		Assert.assertTrue("Caching of role members is not working", roleMembersCached == roleMembers);
		Assert.assertTrue(roleMembersListContains(roleMember, roleMembersCached));
		// call with asOfDate = yesterday 
		List<RoleMember> roleMembersYesterday = kpmeRoleService.getRoleMembersInWorkArea(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), (long) 4444, yesterday, true);
		Assert.assertTrue(roleMembersYesterday != roleMembersCached); // should not get from cache for yesterday		
		// checking caching with now
		roleMembers = kpmeRoleService.getRoleMembersInWorkArea(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), (long) 4444, new DateTime(), true);
		Assert.assertTrue("Caching of role members is not working", roleMembersCached == roleMembers);
		
		roleMembers = kpmeRoleService.getRoleMembersInWorkArea(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), (long) 4444, DateTime.parse("2013-01-01"), true);
		// for checking caching
		roleMembersCached = kpmeRoleService.getRoleMembersInWorkArea(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), (long) 4444, DateTime.parse("2013-01-01"), true);
		Assert.assertTrue("Caching of role members is not working", roleMembersCached == roleMembers);
		
		roleMembers = kpmeRoleService.getRoleMembersInWorkArea(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), (long) 8888, DateTime.parse("2014-01-01"), true);
		qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.POSITION.getRoleMemberAttributeName(), "POS-3");
		qualification.put(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName(), "8888");
	    roleMember = RoleMember.Builder.create("KPM0016", null, "POS-3-PRN-1", MemberType.PRINCIPAL, null, null, qualification, null, null).build();		
	    Assert.assertTrue(roleMembersListContains(roleMember, roleMembers));
	    
		roleMembers = kpmeRoleService.getRoleMembersInWorkArea(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), (long) 8888, DateTime.parse("2013-01-01"), true);
		qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName(), "8888");
	    roleMember = RoleMember.Builder.create("KPME0020", null, "test_principal_2", MemberType.PRINCIPAL, DateTime.parse("2013-01-01"), DateTime.parse("2013-12-31"), qualification, null, null).build();
	    Assert.assertTrue(roleMembersListContains(roleMember, roleMembers));
		
		roleMembers = kpmeRoleService.getRoleMembersInWorkArea(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), (long) 8888, DateTime.parse("2012-12-31"), true);
		roleMember = RoleMember.Builder.create("KPME0020", null, "test_principal_2", MemberType.PRINCIPAL, null, null, qualification, null, null).build();
		// should not contain the same member i.e. test_principal_2 with an asOfDate before 2013
	    Assert.assertFalse(roleMembersListContains(roleMember, roleMembers));
	    
	    roleMembers = kpmeRoleService.getRoleMembersInWorkArea(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), (long) 8888, DateTime.parse("2014-01-01"), true);
	    // should not contain the same member i.e. test_principal_2 with an asOfDate after 2013
	    Assert.assertFalse(roleMembersListContains(roleMember, roleMembers));
	    
		qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.POSITION.getRoleMemberAttributeName(), "POS-3");
		qualification.put(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName(), "8888");
	    roleMember = RoleMember.Builder.create("KPM0016", null, "POS-3-PRN-1", MemberType.PRINCIPAL, null, null, qualification, null, null).build();		
	    Assert.assertTrue(roleMembersListContains(roleMember, roleMembers));
	}
		
	@Test
	public void testPrincipalHasRoleInWorkArea() {
		
	    boolean hasRoleInWorkArea;
	    DateTime today = new DateTime();
		
		hasRoleInWorkArea = kpmeRoleService.principalHasRoleInWorkArea("test_principal_1", KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), (long) 4444, today);
		Assert.assertTrue(hasRoleInWorkArea);
		
		// check for caching (checked via manual observation, not assertions like above)
		hasRoleInWorkArea = kpmeRoleService.principalHasRoleInWorkArea("test_principal_1", KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), (long) 4444, today);
		Assert.assertTrue(hasRoleInWorkArea);
		
		hasRoleInWorkArea = kpmeRoleService.principalHasRoleInWorkArea("POS-2-PRN-2", KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), (long) 4444, today);
		Assert.assertTrue(hasRoleInWorkArea);
		
		hasRoleInWorkArea = kpmeRoleService.principalHasRoleInWorkArea("POS-3-PRN-1", KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), (long) 8888, DateTime.parse("2013-01-01"));
		Assert.assertTrue(hasRoleInWorkArea);
		hasRoleInWorkArea = kpmeRoleService.principalHasRoleInWorkArea("POS-3-PRN-1", KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), (long) 8888, DateTime.parse("2016-01-01"));
		Assert.assertFalse(hasRoleInWorkArea);
		
		// nested group test, 1 level deep
		hasRoleInWorkArea = kpmeRoleService.principalHasRoleInWorkArea("SYS-ADMIN-PRN-1", KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), (long) 4444, DateTime.parse("2013-01-01"));
		Assert.assertTrue(hasRoleInWorkArea);
		hasRoleInWorkArea = kpmeRoleService.principalHasRoleInWorkArea("SYS-ADMIN-PRN-1", KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), (long) 4444, today);
		Assert.assertFalse(hasRoleInWorkArea);
		
		
		// nested group test, 2 level deep
		hasRoleInWorkArea = kpmeRoleService.principalHasRoleInWorkArea("WKFLW-ADMIN-PRN-1", KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), (long) 4444, DateTime.parse("2013-12-31"));
		Assert.assertFalse(hasRoleInWorkArea);
		hasRoleInWorkArea = kpmeRoleService.principalHasRoleInWorkArea("WKFLW-ADMIN-PRN-1", KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), (long) 4444, DateTime.parse("2014-01-01"));
		Assert.assertTrue(hasRoleInWorkArea);
		hasRoleInWorkArea = kpmeRoleService.principalHasRoleInWorkArea("WKFLW-ADMIN-PRN-1", KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), (long) 4444, DateTime.parse("2015-01-01"));
		Assert.assertFalse(hasRoleInWorkArea);
		
		hasRoleInWorkArea = kpmeRoleService.principalHasRoleInWorkArea("admin", KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), (long) 4444, today);
		Assert.assertTrue(hasRoleInWorkArea);
		
		hasRoleInWorkArea = kpmeRoleService.principalHasRoleInWorkArea("guest", KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), (long) 4444, today);
		Assert.assertTrue(hasRoleInWorkArea);
		hasRoleInWorkArea = kpmeRoleService.principalHasRoleInWorkArea("guest", KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), (long) 4444, DateTime.parse("2012-01-01"));
		Assert.assertFalse(hasRoleInWorkArea);
		
	}

}
