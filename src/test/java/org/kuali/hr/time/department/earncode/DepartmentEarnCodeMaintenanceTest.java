package org.kuali.hr.time.department.earncode;

import org.junit.Test;
import org.kuali.hr.time.dept.earncode.DepartmentEarnCode;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.rice.kns.service.KNSServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class DepartmentEarnCodeMaintenanceTest extends TkTestCase{
	private static final String TEST_CODE="TST";
	private static Long departmentEarnCodeId;


	@Test
	public void testDepartmentEarnCodeMaint() throws Exception {
		HtmlPage deptEarnCodeLookup = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.DEPARTMENT_EARN_CODE_MAINT_URL);
		deptEarnCodeLookup = HtmlUnitUtil.clickInputContainingText(deptEarnCodeLookup, "search");
		assertTrue("Page contains test DepartmentEarnCode", deptEarnCodeLookup.asText().contains(TEST_CODE.toString()));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(deptEarnCodeLookup, "edit",departmentEarnCodeId.toString());
		assertTrue("Maintenance Page contains test DepartmentEarnCode",maintPage.asText().contains(TEST_CODE.toString()));
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		DepartmentEarnCode departmentEarnCode = new DepartmentEarnCode();
		departmentEarnCode.setApprover(true);
		departmentEarnCode.setDeptId(TEST_CODE);
		departmentEarnCode.setEarnCodeId(10L);
		departmentEarnCode.setEmployee(false);
		departmentEarnCode.setOrg_admin(false);
		departmentEarnCode.setTkSalGroupId(10L);

		KNSServiceLocator.getBusinessObjectService().save(departmentEarnCode);
		departmentEarnCodeId=departmentEarnCode.getDeptEarnCodeId();
	}

	@Override
	public void tearDown() throws Exception {
		//clean up
		DepartmentEarnCode departmentEarnCodeObj= KNSServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(DepartmentEarnCode.class, departmentEarnCodeId);
		KNSServiceLocator.getBusinessObjectService().delete(departmentEarnCodeObj);
		super.tearDown();
	}
}
