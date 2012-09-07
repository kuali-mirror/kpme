package org.kuali.hr.time.department.earncode;

import java.sql.Date;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.lm.earncodesec.EarnCodeSecurity;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.krad.service.KRADServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class EarnCodeSecurityMaintenanceTest extends KPMETestCase{
	private static final java.sql.Date TEST_DATE_OLD = new Date((new DateTime(2009, 1, 1, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
	private static final java.sql.Date TEST_DATE_NEW = new Date((new DateTime(2010, 1, 1, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
	private static final String EARN_CODE = "RGN";
	private static final String DEPT = "TEST-DEPT";
	private static final String SAL_GROUP = "SD1";
	private static String hrDeptEarnCodeId;
	private static String dupTkDeptEarnCodeId;

	@Test
	public void testEarnCodeSecurityMaint() throws Exception {
		HtmlPage deptEarnCodeLookup = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.EARN_CODE_SECURITY_MAINT_URL);
		deptEarnCodeLookup = HtmlUnitUtil.clickInputContainingText(deptEarnCodeLookup, "search");
		Assert.assertTrue("Page contains test EarnCodeSecurity", deptEarnCodeLookup.asText().contains(DEPT));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(deptEarnCodeLookup, "edit", "hrEarnCodeSecurityId=" + hrDeptEarnCodeId);
		Assert.assertTrue("Maintenance Page contains test EarnCodeSecurity",maintPage.asText().contains(DEPT));
	}
	
	@Test
	public void testEarnCodeSecurityMaintForErrorMessages() throws Exception {

        String testDept = "testDept";
        String testSalGroup = "testSalGroup";
        String testEarnCode = "testEarnCode";


		HtmlPage deptEarnCodeLookup = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.EARN_CODE_SECURITY_MAINT_URL);
		deptEarnCodeLookup = HtmlUnitUtil.clickInputContainingText(deptEarnCodeLookup, "search");
		Assert.assertTrue("Page contains test EarnCodeSecurity", deptEarnCodeLookup.asText().contains(DEPT));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(deptEarnCodeLookup, "edit", "hrEarnCodeSecurityId");
		
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
        Assert.assertTrue("Maintenance Page contains test deptErrormessage",
				resultantPageAfterEdit.asText().contains(
						"The specified department '"
								+ testDept
								+ "' does not exist."));
		
		Assert.assertTrue("Maintenance Page contains test SalGroupErrormessage",
				resultantPageAfterEdit.asText().contains(
						"The specified Salgroup '"
								+ testSalGroup
								+ "' does not exist."));	
		
		Assert.assertTrue("Maintenance Page contains test Earncode",
				resultantPageAfterEdit.asText().contains(
						"The specified Earncode '"
								+ testEarnCode
								+ "' does not exist."));
				
		
	}
	
	@Test
	public void testEditingEarnCodeSecurityMaint() throws Exception {
		HtmlPage deptEarnCodeLookup = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.EARN_CODE_SECURITY_MAINT_URL);
		deptEarnCodeLookup = HtmlUnitUtil.clickInputContainingText(deptEarnCodeLookup, "search");
		Assert.assertTrue("Page contains TEST-DEPT", deptEarnCodeLookup.asText().contains(DEPT));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(deptEarnCodeLookup, "edit", "hrEarnCodeSecurityId=" + hrDeptEarnCodeId);
		Assert.assertFalse("Maintenance Page contains Warnings",maintPage.asText().contains("Warnings for this Section:"));
		Assert.assertFalse("Maintenance Page contains Warning message",maintPage.asText().contains("There is a newer version of this Department Earn Code."));
		
		this.createDuplicateEarnCodeSecurity();
		deptEarnCodeLookup = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.EARN_CODE_SECURITY_MAINT_URL);
		this.setFieldValue(deptEarnCodeLookup, "earnCode", EARN_CODE);
		this.setFieldValue(deptEarnCodeLookup, "dept", DEPT);
		this.setFieldValue(deptEarnCodeLookup, "effectiveDate", "08/01/2010");
		this.setFieldValue(deptEarnCodeLookup, "activeYes", "on");
		this.setFieldValue(deptEarnCodeLookup, "historyYes", "on");
		
		deptEarnCodeLookup = HtmlUnitUtil.clickInputContainingText(deptEarnCodeLookup, "search");
		maintPage = HtmlUnitUtil.clickAnchorContainingText(deptEarnCodeLookup, "edit", "hrEarnCodeSecurityId="+hrDeptEarnCodeId);
		Assert.assertTrue("Maintenance Page contains Warnings",maintPage.asText().contains("Warnings for this Section:"));
		Assert.assertTrue("Maintenance Page contains Warning message",maintPage.asText().contains("There is a newer version of this Department Earn Code."));
	}
	
	public void createNewEarnCodeSecurity() {
        //clear old earncode securitys:
        Collection<EarnCodeSecurity> earnCodeSecurities = KRADServiceLocator.getBusinessObjectService().findMatching(EarnCodeSecurity.class, Collections.singletonMap("earnCode", EARN_CODE));
        KRADServiceLocator.getBusinessObjectService().deleteMatching(EarnCodeSecurity.class, Collections.singletonMap("earnCode", EARN_CODE));
		EarnCodeSecurity deptEarnCode = new EarnCodeSecurity();
		deptEarnCode.setActive(true);
		deptEarnCode.setEarnCode(EARN_CODE);
		deptEarnCode.setDept(DEPT);
		deptEarnCode.setHrSalGroup(SAL_GROUP);
		deptEarnCode.setEmployee(false);
		deptEarnCode.setEffectiveDate(TEST_DATE_OLD);
		deptEarnCode.setLocation("test");
		deptEarnCode.setEarnCodeType("T");
		deptEarnCode = KRADServiceLocator.getBusinessObjectService().save(deptEarnCode);
		hrDeptEarnCodeId = deptEarnCode.getHrEarnCodeSecurityId();
	}
	
	public void createDuplicateEarnCodeSecurity() {
		EarnCodeSecurity deptEarnCode = new EarnCodeSecurity();
		deptEarnCode.setActive(true);
		deptEarnCode.setEarnCode(EARN_CODE);
		deptEarnCode.setDept(DEPT);
		deptEarnCode.setHrSalGroup(SAL_GROUP);
		deptEarnCode.setEmployee(false);
		deptEarnCode.setEffectiveDate(TEST_DATE_NEW);
		deptEarnCode.setLocation("test");
		deptEarnCode.setEarnCodeType("T");
        deptEarnCode = KRADServiceLocator.getBusinessObjectService().save(deptEarnCode);
		dupTkDeptEarnCodeId = deptEarnCode.getHrEarnCodeSecurityId();
	}
	
	@Override
	public void tearDown() throws Exception {
		EarnCodeSecurity deptEarnCodeObj = KRADServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(EarnCodeSecurity.class, hrDeptEarnCodeId);
		KRADServiceLocator.getBusinessObjectService().delete(deptEarnCodeObj);
        if (StringUtils.isNotBlank(dupTkDeptEarnCodeId)) {
		    deptEarnCodeObj = KRADServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(EarnCodeSecurity.class, dupTkDeptEarnCodeId);
		    KRADServiceLocator.getBusinessObjectService().delete(deptEarnCodeObj);
        }
		super.tearDown();
	}
	

	@Override
	public void setUp() throws Exception {
		super.setUp();
		this.createNewEarnCodeSecurity();		
	}
}
