package org.kuali.hr.time.department.earncode;

import java.sql.Date;

import org.joda.time.DateTime;
import org.junit.Test;
import org.kuali.hr.time.dept.earncode.DepartmentEarnCode;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kns.service.KNSServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class DepartmentEarnCodeMaintenanceTest extends TkTestCase{
	private static final java.sql.Date TEST_DATE = new Date((new DateTime(2009, 1, 1, 0, 0, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE)).getMillis());
	private static final String EARN_CODE = "RGN";
	private static final String DEPT = "TEST-DEPT";
	private static final String SAL_GROUP = "SD1";
	private static String hrDeptEarnCodeId;
	private static String dupTkDeptEarnCodeId;

	@Test
	public void testDepartmentEarnCodeMaint() throws Exception {
		HtmlPage deptEarnCodeLookup = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.DEPARTMENT_EARN_CODE_MAINT_URL);
		deptEarnCodeLookup = HtmlUnitUtil.clickInputContainingText(deptEarnCodeLookup, "search");
		assertTrue("Page contains test DepartmentEarnCode", deptEarnCodeLookup.asText().contains(DEPT));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(deptEarnCodeLookup, "edit", "hrDeptEarnCodeId=19");
		assertTrue("Maintenance Page contains test DepartmentEarnCode",maintPage.asText().contains(DEPT));
	}
	
	@Test
	public void testDepartmentEarnCodeMaintForErrorMessages() throws Exception {

        String testDept = "testDept";
        String testSalGroup = "testSalGroup";
        String testEarnCode = "testEarnCode";


		HtmlPage deptEarnCodeLookup = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.DEPARTMENT_EARN_CODE_MAINT_URL);
		deptEarnCodeLookup = HtmlUnitUtil.clickInputContainingText(deptEarnCodeLookup, "search");
		assertTrue("Page contains test DepartmentEarnCode", deptEarnCodeLookup.asText().contains(DEPT));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(deptEarnCodeLookup, "edit", "hrDeptEarnCodeId");
		
		HtmlInput inputForDescription = HtmlUnitUtil.getInputContainingText(
				maintPage, "* Document Description");
		inputForDescription.setValueAttribute("Test_Description");

        HtmlInput inputForDept = HtmlUnitUtil.getInputContainingText(maintPage, "document.newMaintainableObject.dept");
        inputForDept.setValueAttribute(testDept);

        HtmlInput inputForSalGroup = HtmlUnitUtil.getInputContainingText(maintPage, "document.newMaintainableObject.hrSalGroup");
        inputForSalGroup.setValueAttribute(testSalGroup);

        HtmlInput inputForEarnCode = HtmlUnitUtil.getInputContainingText(maintPage, "document.newMaintainableObject.earnCode");
        inputForEarnCode.setValueAttribute(testEarnCode);

        HtmlPage resultantPageAfterEdit = HtmlUnitUtil
				.clickInputContainingText(maintPage, "submit");


        HtmlUnitUtil.createTempFile(resultantPageAfterEdit);
		assertTrue("Maintenance Page contains test deptErrormessage",
				resultantPageAfterEdit.asText().contains(
						"The specified department '"
								+ testDept
								+ "' does not exist."));
		
		assertTrue("Maintenance Page contains test SalGroupErrormessage",
				resultantPageAfterEdit.asText().contains(
						"The specified Salgroup '"
								+ testSalGroup
								+ "' does not exist."));	
		
		assertTrue("Maintenance Page contains test Earncode",
				resultantPageAfterEdit.asText().contains(
						"The specified Earncode '"
								+ testEarnCode
								+ "' does not exist."));
				
		
	}
	
	@Test
	public void testEditingDepartmentEarnCodeMaint() throws Exception {
		HtmlPage deptEarnCodeLookup = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.DEPARTMENT_EARN_CODE_MAINT_URL);
		deptEarnCodeLookup = HtmlUnitUtil.clickInputContainingText(deptEarnCodeLookup, "search");
		assertTrue("Page contains TEST-DEPT", deptEarnCodeLookup.asText().contains(DEPT));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(deptEarnCodeLookup, "edit", "hrDeptEarnCodeId=23");
		assertFalse("Maintenance Page contains Warnings",maintPage.asText().contains("Warnings for this Section:"));
		assertFalse("Maintenance Page contains Warning message",maintPage.asText().contains("There is a newer version of this Department Earn Code."));
		
		this.createDuplicateDeptEarnCode();
		deptEarnCodeLookup = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.DEPARTMENT_EARN_CODE_MAINT_URL);
		deptEarnCodeLookup = HtmlUnitUtil.clickInputContainingText(deptEarnCodeLookup, "search");
		maintPage = HtmlUnitUtil.clickAnchorContainingText(deptEarnCodeLookup, "edit", "hrDeptEarnCodeId=24");
        HtmlUnitUtil.createTempFile(maintPage);
		assertTrue("Maintenance Page contains Warnings",maintPage.asText().contains("Warnings for this Section:"));
		assertTrue("Maintenance Page contains Warning message",maintPage.asText().contains("There is a newer version of this Department Earn Code."));
		assertTrue("Maintenance Page contains Warning message",maintPage.asText().contains("There is an exact duplicate version of this Department Earn Code."));
	}
	
	public void createNewDeptEarnCode() {
		DepartmentEarnCode deptEarnCode = new DepartmentEarnCode();
		deptEarnCode.setActive(true);
		deptEarnCode.setEarnCode(EARN_CODE);
		deptEarnCode.setEffectiveDate(TEST_DATE);
		deptEarnCode.setDept(DEPT);
		deptEarnCode.setHrSalGroup(SAL_GROUP);
		deptEarnCode.setEmployee(false);
		deptEarnCode.setEffectiveDate(TEST_DATE);
		deptEarnCode.setLocation("test");
		
		KNSServiceLocator.getBusinessObjectService().save(deptEarnCode);	
		hrDeptEarnCodeId = deptEarnCode.getHrDeptEarnCodeId();
	}
	
	public void createDuplicateDeptEarnCode() {
		DepartmentEarnCode deptEarnCode = new DepartmentEarnCode();
		deptEarnCode.setActive(true);
		deptEarnCode.setEarnCode(EARN_CODE);
		deptEarnCode.setEffectiveDate(TEST_DATE);
		deptEarnCode.setDept(DEPT);
		deptEarnCode.setHrSalGroup(SAL_GROUP);
		deptEarnCode.setEmployee(false);
		deptEarnCode.setEffectiveDate(TEST_DATE);
		deptEarnCode.setLocation("test");
		
		KNSServiceLocator.getBusinessObjectService().save(deptEarnCode);	
		dupTkDeptEarnCodeId = deptEarnCode.getHrDeptEarnCodeId();
	}
	
	@Override
	public void tearDown() throws Exception {
		DepartmentEarnCode deptEarnCodeObj = KNSServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(DepartmentEarnCode.class, hrDeptEarnCodeId);
		KNSServiceLocator.getBusinessObjectService().delete(deptEarnCodeObj);
		deptEarnCodeObj = KNSServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(DepartmentEarnCode.class, dupTkDeptEarnCodeId);			
		KNSServiceLocator.getBusinessObjectService().delete(deptEarnCodeObj);
		super.tearDown();
	}
	

	@Override
	public void setUp() throws Exception {
		super.setUp();
		this.createNewDeptEarnCode();		
	}
}
