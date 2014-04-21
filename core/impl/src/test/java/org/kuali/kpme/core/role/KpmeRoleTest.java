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
package org.kuali.kpme.core.role;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.kpme.core.CoreUnitTestCase;
import org.kuali.kpme.core.IntegrationTest;
import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.kim.api.role.RoleService;

@IntegrationTest
public class KpmeRoleTest extends CoreUnitTestCase {
	
	private RoleService roleService;
	
	@Before
    public void setUp() throws Exception {
       super.setUp();
       roleService = HrServiceLocator.getService("kimRoleService");
    }
	
	@Test
	public void testGetPositionRoles() {
		
		// Test roles in KPME-PM name space
		String roleId = roleService.getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_PM.getNamespaceCode(), KPMERole.POSITION_SYSTEM_ADMINISTRATOR.getRoleName());
		Assert.assertNotNull(roleId);
		roleId = roleService.getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_PM.getNamespaceCode(), KPMERole.POSITION_SYSTEM_VIEW_ONLY.getRoleName());
		Assert.assertNotNull(roleId);
		
		// Test roles in KPME_HR name space
		roleId = roleService.getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.KOHR_INSTITUTION_ADMINISTRATOR.getRoleName());
		Assert.assertNotNull(roleId);
		roleId = roleService.getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.KOHR_ACADEMIC_HR_ADMINISTRATOR.getRoleName());
		Assert.assertNotNull(roleId);
		roleId = roleService.getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.KOHR_INSTITUTION_VIEW_ONLY.getRoleName());
		Assert.assertNotNull(roleId);
		roleId = roleService.getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.KOHR_LOCATION_ADMINISTRATOR.getRoleName());
		Assert.assertNotNull(roleId);
		roleId = roleService.getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.KOHR_LOCATION_VIEW_ONLY.getRoleName());
		Assert.assertNotNull(roleId);
		roleId = roleService.getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.KOHR_ORG_ADMINISTRATOR.getRoleName());
		Assert.assertNotNull(roleId);
		roleId = roleService.getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.KOHR_ORG_VIEW_ONLY.getRoleName());
		Assert.assertNotNull(roleId);
		roleId = roleService.getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.HR_DEPARTMENT_ADMINISTRATOR.getRoleName());
		Assert.assertNotNull(roleId);
		roleId = roleService.getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.HR_DEPARTMENT_VIEW_ONLY.getRoleName());
		Assert.assertNotNull(roleId);
		roleId = roleService.getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.HR_INSTITUTION_APPROVER.getRoleName());
		Assert.assertNotNull(roleId);
		roleId = roleService.getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.ACADEMIC_HR_INSTITUTION_APPROVER.getRoleName());
		Assert.assertNotNull(roleId);
		roleId = roleService.getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.BUDGET_APPROVER.getRoleName());
		Assert.assertNotNull(roleId);
		roleId = roleService.getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.PAYROLL_APPROVER.getRoleName());
		Assert.assertNotNull(roleId);
		roleId = roleService.getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.HR_LOCATION_APPROVER.getRoleName());
		Assert.assertNotNull(roleId);
		roleId = roleService.getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.ACADEMIC_HR_LOCATION_APPROVER.getRoleName());
		Assert.assertNotNull(roleId);
		roleId = roleService.getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.FISCAL_LOCATION_APPROVER.getRoleName());
		Assert.assertNotNull(roleId);
		roleId = roleService.getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.HR_ORG_APPROVER.getRoleName());
		Assert.assertNotNull(roleId);
		roleId = roleService.getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.FISCAL_ORG_APPROVER.getRoleName());
		Assert.assertNotNull(roleId);
		roleId = roleService.getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.DEPARTMENT_APPROVER.getRoleName());
		Assert.assertNotNull(roleId);
		roleId = roleService.getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.FISCAL_DEPARTMENT_APPROVER.getRoleName());
		Assert.assertNotNull(roleId);
	}

}
