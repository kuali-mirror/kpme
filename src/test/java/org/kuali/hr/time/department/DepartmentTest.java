package org.kuali.hr.time.department;

import org.junit.Test;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.rice.kns.service.KNSServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class DepartmentTest extends TkTestCase {
	@Test
	public void testDepartmentMaint() throws Exception {
		HtmlPage deptLookup = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.DEPT_MAINT_URL);
		deptLookup = HtmlUnitUtil.clickInputContainingText(deptLookup, "search");
		assertTrue("Page contains test dept", deptLookup.asText().contains("TEST"));
		HtmlUnitUtil.createTempFile(deptLookup);
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(deptLookup, "edit","100");

		assertTrue("Maintenance Page contains test dept",maintPage.asText().contains("TEST"));
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
		KNSServiceLocator.getBusinessObjectService().save(dept);
	}
}
