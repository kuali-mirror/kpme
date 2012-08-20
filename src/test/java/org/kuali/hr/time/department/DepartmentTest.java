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
		HtmlPage deptLookup = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.DEPT_MAINT_URL);
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
