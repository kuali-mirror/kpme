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
package org.kuali.hr.time.earngroup.validation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.kpme.core.bo.earncode.EarnCode;
import org.kuali.kpme.core.bo.earncode.group.EarnCodeGroup;
import org.kuali.kpme.core.bo.earncode.group.EarnCodeGroupDefinition;
import org.kuali.rice.krad.service.KRADServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class EarnCodeGroupMaintenanceTest extends KPMETestCase {
    private static final Logger LOG = Logger.getLogger(EarnCodeGroupMaintenanceTest.class);
    private static final LocalDate TEST_DATE = LocalDate.now();
    private static final String EARN_CODE = "RGN";
    private static String hrEarnGroupId;
    private static String hrEarnCodeId;
    private static String hrEarnGroupIdRGG;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        List<EarnCodeGroupDefinition> earnGroups = new ArrayList<EarnCodeGroupDefinition>();
        EarnCodeGroupDefinition definition = new EarnCodeGroupDefinition();
        definition.setEarnCode(EARN_CODE);
        earnGroups.add(definition);

        EarnCodeGroup earnGroup = new EarnCodeGroup();
        earnGroup.setEarnCodeGroup("testGroup");
        earnGroup.setDescr("test");
        earnGroup.setEffectiveLocalDate(TEST_DATE);
        earnGroup.setShowSummary(true);
        earnGroup.setActive(true);
        earnGroup.setEarnCodeGroups(earnGroups);
        KRADServiceLocator.getBusinessObjectService().save(earnGroup);
        hrEarnGroupId = earnGroup.getHrEarnCodeGroupId();

        // Set up earn code RGG in tk-earn_code_t
        EarnCode earnCode = new EarnCode();
        earnCode.setActive(true);
        earnCode.setEarnCode("RGG");
        earnCode.setEffectiveLocalDate(TEST_DATE);
        earnCode.setRecordMethod("T");
        earnCode.setFractionalTimeAllowed("99");
        earnCode.setRoundingOption("T");
        earnCode.setEligibleForAccrual("Y");
        earnCode.setAffectPay("Y");
        earnCode.setAllowScheduledLeave("Y");
        earnCode.setFmla("Y");
        earnCode.setWorkmansComp("Y");
        earnCode.setAllowNegativeAccrualBalance("Y");
        earnCode.setDescription("RGG");
        earnCode.setOvtEarnCode(false);
        earnCode.setInflateMinHours(BigDecimal.ZERO);
        earnCode.setInflateFactor(BigDecimal.ZERO);
        earnCode = KRADServiceLocator.getBusinessObjectService().save(earnCode);
        hrEarnCodeId = earnCode.getHrEarnCodeId();

        // Set up earn group RGG in tk-earn_group_t
        EarnCodeGroup earnGroupRGG = new EarnCodeGroup();
        earnGroupRGG.setEarnCodeGroup("RGG");
        earnGroupRGG.setDescr("test RGG");
        earnGroupRGG.setEffectiveLocalDate(TEST_DATE);
        earnGroupRGG.setShowSummary(true);
        earnGroupRGG.setActive(true);
        earnGroupRGG = KRADServiceLocator.getBusinessObjectService().save(earnGroupRGG);
        hrEarnGroupIdRGG = earnGroupRGG.getHrEarnCodeGroupId();
    }

    @Override
    public void tearDown() throws Exception {
        EarnCodeGroup earnGroupObj = KRADServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(EarnCodeGroup.class, hrEarnGroupId);
        KRADServiceLocator.getBusinessObjectService().delete(earnGroupObj);

        EarnCodeGroup earnGroupObjRGG = KRADServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(EarnCodeGroup.class, hrEarnGroupIdRGG);
        KRADServiceLocator.getBusinessObjectService().delete(earnGroupObjRGG);

        if (StringUtils.isNotBlank(hrEarnCodeId)) {
            EarnCode earnCodeObj = KRADServiceLocator.getBusinessObjectService().findByPrimaryKey(EarnCode.class, Collections.singletonMap("hrEarnCodeId", hrEarnCodeId));
            KRADServiceLocator.getBusinessObjectService().delete(earnCodeObj);
        }
        super.tearDown();
    }

    @Test
    public void testEditExistingEarnGroup() throws Exception {
        HtmlPage earnGroupLookUp = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), TkTestConstants.Urls.EARN_CODE_GROUP_MAINT_URL);
        earnGroupLookUp = HtmlUnitUtil.clickInputContainingText(earnGroupLookUp, "search");
        Assert.assertTrue("Page contains test Earn Group", earnGroupLookUp.asText().contains("test"));
        HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(earnGroupLookUp, "edit", hrEarnGroupId.toString());
        Assert.assertTrue("Maintenance Page contains test ClockLog", maintPage.asText().contains("test"));
        HtmlTextInput text = (HtmlTextInput) maintPage.getHtmlElementById("document.documentHeader.documentDescription");
        text.setValueAttribute("test1");

        // pull out earn group RGG to edit.
        earnGroupLookUp = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), TkTestConstants.Urls.EARN_CODE_GROUP_MAINT_URL);
        earnGroupLookUp = HtmlUnitUtil.clickInputContainingText(earnGroupLookUp, "search");
        Assert.assertTrue("Page contains test Earn Group", earnGroupLookUp.asText().contains("test RGG"));
        HtmlPage testEditRGGPage = HtmlUnitUtil.clickAnchorContainingText(earnGroupLookUp, "edit", hrEarnGroupIdRGG.toString());
        Assert.assertTrue("Maintenance Page contains test ClockLog", testEditRGGPage.asText().contains("test RGG"));
        text = (HtmlTextInput) testEditRGGPage.getHtmlElementById("document.documentHeader.documentDescription");
        text.setValueAttribute("testEditRGG");

        // Add a new Earn Code Group Definition
        text = (HtmlTextInput) testEditRGGPage.getHtmlElementById("document.newMaintainableObject.add.earnCodeGroups.earnCode");
        text.setValueAttribute("RGG");
        HtmlElement element = HtmlUnitUtil.getInputContainingText(testEditRGGPage, "methodToCall.addLine.earnCodeGroups");
        HtmlPage newCodeAddedPage = element.click();
//        HtmlUnitUtil.createTempFile(newCodeAddedPage);
        Assert.assertFalse("Page contains Error", newCodeAddedPage.asText().contains("error"));

        // Delete this Earn Code Group Definition
//        element = HtmlUnitUtil.getInputContainingText(newCodeAddedPage,"methodToCall.deleteLine.earnGroups");
//        HtmlPage deleteCodePage = element.click();
//		assertFalse("Page contains Error", deleteCodePage.asText().contains("error"));

        // submit the changes
        element = newCodeAddedPage.getElementByName("methodToCall.route");
        HtmlPage finalPage = element.click();

        LOG.debug("Final Space is >>> " + finalPage.asText());
//        HtmlUnitUtil.createTempFile(finalPage);
        Assert.assertTrue("Maintenance page is submitted successfully", finalPage.asText().contains("Document was successfully submitted."));

    }


    @Test
    //tests EarnCodeGroupValidation
    public void testSubmitEarnGroupMaint() throws Exception {
        String baseUrl = HtmlUnitUtil.getBaseURL() + "/kr/maintenance.do?businessObjectClassName=org.kuali.kpme.core.bo.earncode.group.EarnCodeGroup&methodToCall=start";
        HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), baseUrl);
        Assert.assertNotNull(page);

        HtmlTextInput text = (HtmlTextInput) page.getHtmlElementById("document.documentHeader.documentDescription");
        text.setValueAttribute("test");
        text = (HtmlTextInput) page.getHtmlElementById(TkTestConstants.DOC_NEW_ELEMENT_ID_PREFIX + "descr");
        text.setValueAttribute("Test Earn Group");
        text = (HtmlTextInput) page.getHtmlElementById(TkTestConstants.DOC_NEW_ELEMENT_ID_PREFIX + "earnCodeGroup");
        text.setValueAttribute("Test");
        // set an old effective date so that the earn code that's added later is not effective by that date
        text = (HtmlTextInput) page.getHtmlElementById(TkTestConstants.DOC_NEW_ELEMENT_ID_PREFIX + "effectiveDate");
        text.setValueAttribute("12/01/2008");

        HtmlCheckBoxInput checkbox = (HtmlCheckBoxInput) page.getHtmlElementById(TkTestConstants.DOC_NEW_ELEMENT_ID_PREFIX + "showSummary");
        checkbox.setChecked(true);
        checkbox = (HtmlCheckBoxInput) page.getHtmlElementById(TkTestConstants.DOC_NEW_ELEMENT_ID_PREFIX + "active");
        checkbox.setChecked(true);

        // add an Earn code that is being used by another active earn group, submit, should generate error message
        text = (HtmlTextInput) page.getHtmlElementById(TkTestConstants.DOC_NEW_ELEMENT_ID_PREFIX + "add.earnCodeGroups.earnCode");
        text.setValueAttribute(EARN_CODE);
        HtmlElement element = HtmlUnitUtil.getInputContainingText(page, "methodToCall.addLine.earnCodeGroups");
        HtmlPage page1 = element.click();
        //error for earn code not being effective by the effectiveDate of the earn group
        LOG.debug("Page1 click >>>>"+page1.asText());
        
        Assert.assertTrue("Maintenance Page contains error messages", page1.asText().contains("The specified Earncode '" + EARN_CODE + "' does not exist."));

        // set the effective date to one that works for the earn code
        text = (HtmlTextInput) page1.getHtmlElementById(TkTestConstants.DOC_NEW_ELEMENT_ID_PREFIX + "effectiveDate");
        text.setValueAttribute("5/01/2011");
        element = HtmlUnitUtil.getInputContainingText(page1, "methodToCall.addLine.earnCodeGroups");
        HtmlPage page2 = element.click();
        Assert.assertFalse("Page contains Error", page2.asText().contains("error"));

        // add the same earn code again to get the duplicate error
        text = (HtmlTextInput) page2.getHtmlElementById(TkTestConstants.DOC_NEW_ELEMENT_ID_PREFIX + "add.earnCodeGroups.earnCode");
        text.setValueAttribute(EARN_CODE);
        element = HtmlUnitUtil.getInputContainingText(page2, "methodToCall.addLine.earnCodeGroups");
        page1 = element.click();
        Assert.assertTrue("Maintenance Page contains error messages", page1.asText().contains(EARN_CODE + " is already a part of this earngroup."));

        element = page1.getElementByName("methodToCall.route");
        HtmlPage finalPage = element.click();
        // error for earn code that is being used by another earn group
        Assert.assertTrue("Maintenance Page contains error messages", finalPage.asText().contains(EARN_CODE + " is used by another earn group - 'test'."));

        //delete this earn code
        element = HtmlUnitUtil.getInputContainingText(finalPage, "methodToCall.deleteLine.earnCodeGroups");
        HtmlPage page3 = element.click();
        Assert.assertFalse("Page contains Error", page3.asText().contains("error"));

        //add an earn code that is not being used, submit, should get success message
        text = (HtmlTextInput) page3.getHtmlElementById(TkTestConstants.DOC_NEW_ELEMENT_ID_PREFIX + "add.earnCodeGroups.earnCode");
        text.setValueAttribute("XZZ");
        element = HtmlUnitUtil.getInputContainingText(page3, "methodToCall.addLine.earnCodeGroups");
        page1 = element.click();
        LOG.error("page1 click >>> adding SDR to earn code group >>>" + page1.asXml());
        Assert.assertFalse("Page contains Error", page1.asText().contains("error"));
        element = page1.getElementByName("methodToCall.route");
        finalPage = element.click();
//        HtmlUnitUtil.createTempFile(finalPage);
        Assert.assertTrue("Maintenance page is submitted successfully", finalPage.asText().contains("Document was successfully submitted."));
        Assert.assertTrue("Maintenance page is submitted successfully", finalPage.asText().contains("Status: 	 FINAL"));
    }

    @Test
    public void testSubmitEarnGroupWithNewerVersionMaint() throws Exception {
        String baseUrl = HtmlUnitUtil.getBaseURL() + "/kr/maintenance.do?businessObjectClassName=org.kuali.kpme.core.bo.earncode.group.EarnCodeGroup&methodToCall=start";
        HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), baseUrl);
        Assert.assertNotNull(page);

        //save a Earn code
        populateEarnGroup(page, "01/01/2011");
        HtmlInput text = (HtmlTextInput) page.getHtmlElementById("document.newMaintainableObject.add.earnCodeGroups.earnCode");
        text.setValueAttribute("RGH");
        HtmlElement element = HtmlUnitUtil.getInputContainingText(page, "methodToCall.addLine.earnCodeGroups");
        HtmlPage newCodeAddedPage = element.click();
//        HtmlUnitUtil.createTempFile(newCodeAddedPage);
        Assert.assertFalse("Page contains Error", newCodeAddedPage.asText().contains("error"));
        element = newCodeAddedPage.getElementByName("methodToCall.route");
        HtmlPage finalPage = element.click();

        Assert.assertTrue("Maintenance page is submitted successfully", finalPage.asText().contains("Document was successfully submitted."));

        //try to save the same Earn code with older effective date
        page = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), baseUrl);
        populateEarnGroup(page, "01/01/2010");
        text = (HtmlTextInput) page.getHtmlElementById("document.newMaintainableObject.add.earnCodeGroups.earnCode");
        text.setValueAttribute("RGH");
        element = HtmlUnitUtil.getInputContainingText(page, "methodToCall.addLine.earnCodeGroups");
        newCodeAddedPage = element.click();
        Assert.assertFalse("Page contains Error", newCodeAddedPage.asText().contains("error"));
        element = newCodeAddedPage.getElementByName("methodToCall.route");
        finalPage = element.click();
        Assert.assertTrue("Maintenance Page doesn't return warning about later effective date", finalPage.asText().contains("A record for this object exists with a later effective date"));
        HtmlElement yesButton = finalPage.getElementByName("methodToCall.processAnswer.button0");
        finalPage = yesButton.click();
        Assert.assertTrue("Maintenance Page contains error messages", finalPage.asText().contains("There is a newer version of this Earn Group."));

    }

    private void populateEarnGroup(HtmlPage page, String effDateString) {
        setFieldValue(page, "document.documentHeader.documentDescription", "test");
        setFieldValue(page, TkTestConstants.DOC_NEW_ELEMENT_ID_PREFIX + "descr", "Test Earn Group");
        setFieldValue(page, TkTestConstants.DOC_NEW_ELEMENT_ID_PREFIX + "earnCodeGroup", "MM");
        setFieldValue(page, TkTestConstants.DOC_NEW_ELEMENT_ID_PREFIX + "effectiveDate", effDateString);
        setFieldValue(page, TkTestConstants.DOC_NEW_ELEMENT_ID_PREFIX + "showSummary", "on");
        setFieldValue(page, TkTestConstants.DOC_NEW_ELEMENT_ID_PREFIX + "active", "on");
    }


}
