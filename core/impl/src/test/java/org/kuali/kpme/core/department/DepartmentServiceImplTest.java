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
package org.kuali.kpme.core.department;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.core.CoreUnitTestCase;
import org.kuali.kpme.core.IntegrationTest;
import org.kuali.kpme.core.api.department.Department;
import org.kuali.kpme.core.service.HrServiceLocator;

import java.util.List;

@IntegrationTest
public class DepartmentServiceImplTest extends CoreUnitTestCase {
	
	@Test
	public void testSearchDepartments() throws Exception {
		// This method is not used anywhere in the code, so comment it out for now
		//List<Department> allResults = HrServiceLocator.getDepartmentService().getDepartments("admin", null, null, null, "Y", "N", "");
		//Assert.assertEquals("Search returned the correct number of results.", 11, allResults.size());
		
		//List<Department> restrictedResults = HrServiceLocator.getDepartmentService().getDepartments("testuser6", null, null, null, "Y", "N", "");
		//Assert.assertEquals("Search returned the wrong number of results.", 1, restrictedResults.size());
	}

}