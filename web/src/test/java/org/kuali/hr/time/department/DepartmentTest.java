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
package org.kuali.hr.time.department;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.rice.krad.service.KRADServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class DepartmentTest extends KPMETestCase {
	@Test
	public void testDepartmentMaint() throws Exception {
		HtmlPage deptLookup = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), TkTestConstants.Urls.DEPT_MAINT_URL);
		deptLookup = HtmlUnitUtil.clickInputContainingText(deptLookup, "search");
		Assert.assertTrue("Page contains test dept", deptLookup.asText().contains("TEST"));
		HtmlUnitUtil.createTempFile(deptLookup);
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(deptLookup, "edit","100");

		Assert.assertTrue("Maintenance Page contains test dept",maintPage.asText().contains("TEST"));
		Assert.assertTrue("Maintenance Page contains test dept",maintPage.asText().contains("Time Department Admin"));
	}
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		Department dept = new Department();
		dept.setHrDeptId("1001");
		dept.setDept("__TEST");
		dept.setDescription("TESTING_DEPT");
		dept.setActive(true);
        dept.setLocation("BL");
		KRADServiceLocator.getBusinessObjectService().save(dept);
	}
}
