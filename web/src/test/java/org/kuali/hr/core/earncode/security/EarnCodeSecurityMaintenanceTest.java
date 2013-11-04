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
package org.kuali.hr.core.earncode.security;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Collections;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.KPMEWebTestCase;
import org.kuali.hr.util.HtmlUnitUtil;
import org.kuali.kpme.core.FunctionalTest;
import org.kuali.kpme.core.earncode.security.EarnCodeSecurity;
import org.kuali.kpme.core.util.HrTestConstants;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.rice.krad.service.KRADServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

@FunctionalTest
public class EarnCodeSecurityMaintenanceTest extends KPMEWebTestCase{
	private static final DateTime TEST_DATE_OLD = new DateTime(2009, 1, 1, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone());
	private static final DateTime TEST_DATE_NEW = new DateTime(2010, 1, 1, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone());
	private static final String EARN_CODE = "RGN";
	private static final String DEPT = "TEST-DEPT";
	private static final String SAL_GROUP = "SD1";
	private static String hrDeptEarnCodeId;
	private static String dupTkDeptEarnCodeId;

	@Test
	public void testEarnCodeSecurityMaint() throws Exception {
		HtmlPage deptEarnCodeLookup = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), HrTestConstants.Urls.EARN_CODE_SECURITY_MAINT_URL);
		deptEarnCodeLookup = HtmlUnitUtil.clickInputContainingText(deptEarnCodeLookup, "search");
		Assert.assertTrue("Page contains test EarnCodeSecurity", deptEarnCodeLookup.asText().contains(DEPT));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(deptEarnCodeLookup, "edit", "hrEarnCodeSecurityId=" + hrDeptEarnCodeId);
		Assert.assertTrue("Maintenance Page contains test EarnCodeSecurity",maintPage.asText().contains(DEPT));
	}
	
	@Test
	public void testLookup() throws Exception {
		HtmlPage lookupPage = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), HrTestConstants.Urls.EARN_CODE_SECURITY_MAINT_URL);
		assertNotNull("lookup page is null", lookupPage);
		assertTrue("lookup page should contain 'Earn Code Type' field group", lookupPage.asText().contains("Earn Code Type"));
		assertTrue("'Earn Code Type' field group should have 'Time and Leave' option",
				lookupPage.asText().contains("Time and Leave"));
		
		lookupPage = HtmlUnitUtil.clickInputContainingText(lookupPage, "search");
		assertNotNull("lookup result page is null", lookupPage);
		assertTrue("lookup page should contain 'TEST-DEPT'", lookupPage.asText().contains("TEST-DEPT"));
	}
	
	@Test
	public void testEarnCodeSecurityMaintForErrorMessages() throws Exception {

        String testDept = "testDept";
        String testSalGroup = "testSalGroup";
        String testEarnCode = "testEarnCode";
        String testLocation = "testLocation";


		HtmlPage deptEarnCodeLookup = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), HrTestConstants.Urls.EARN_CODE_SECURITY_MAINT_URL);
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
        
        HtmlInput inputForLocation = HtmlUnitUtil.getInputContainingText(maintPage, "document.newMaintainableObject.location");
        inputForLocation.setValueAttribute(testLocation);

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
		HtmlPage deptEarnCodeLookup = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), HrTestConstants.Urls.EARN_CODE_SECURITY_MAINT_URL);
		deptEarnCodeLookup = HtmlUnitUtil.clickInputContainingText(deptEarnCodeLookup, "search");
		Assert.assertTrue("Page contains TEST-DEPT", deptEarnCodeLookup.asText().contains(DEPT));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(deptEarnCodeLookup, "edit", "hrEarnCodeSecurityId=" + hrDeptEarnCodeId);
		Assert.assertFalse("Maintenance Page contains Warnings",maintPage.asText().contains("Warnings for this Section:"));
		Assert.assertFalse("Maintenance Page contains Warning message",maintPage.asText().contains("There is a newer version of this Department Earn Code."));
		
		this.createDuplicateEarnCodeSecurity();
		deptEarnCodeLookup = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), HrTestConstants.Urls.EARN_CODE_SECURITY_MAINT_URL);
		HtmlUnitUtil.setFieldValue(deptEarnCodeLookup, "earnCode", EARN_CODE);
		HtmlUnitUtil.setFieldValue(deptEarnCodeLookup, "dept", DEPT);
		HtmlUnitUtil.setFieldValue(deptEarnCodeLookup, "effectiveDate", "08/01/2010");
		HtmlUnitUtil.setFieldValue(deptEarnCodeLookup, "activeYes", "on");
		HtmlUnitUtil.setFieldValue(deptEarnCodeLookup, "historyYes", "on");
		
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
		deptEarnCode.setEffectiveLocalDate(TEST_DATE_OLD.toLocalDate());
		deptEarnCode.setLocation("BL");
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
		deptEarnCode.setEffectiveLocalDate(TEST_DATE_NEW.toLocalDate());
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
