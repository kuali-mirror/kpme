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
package org.kuali.hr.core.earncode.validation;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.kpme.core.HrTestConstants;
import org.kuali.kpme.core.earncode.EarnCode;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.time.timeblock.TimeBlock;
import org.kuali.rice.krad.service.KRADServiceLocator;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlRadioButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class EarnCodeMaintenaceTest extends KPMETestCase {
	private static final DateTime TEST_DATE = new DateTime(2009, 1, 1, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone());
	private static final String EARN_CODE = "RGN";
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yy");
	
	private static String hrEarnCodeId;
	private static String timeBlockId;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		EarnCode earnCode = new EarnCode();
		earnCode.setActive(true);
		earnCode.setEarnCode(EARN_CODE);
		earnCode.setEffectiveLocalDate(TEST_DATE.toLocalDate());
		earnCode.setRecordMethod("T");
		earnCode.setFractionalTimeAllowed("99");
		earnCode.setRoundingOption("T");
		earnCode.setAffectPay("Y");
		earnCode.setWorkmansComp("Y");
		earnCode.setEligibleForAccrual("Y");
		earnCode.setAllowScheduledLeave("Y");
		earnCode.setFmla("Y");
		earnCode.setAllowNegativeAccrualBalance("Y");
		earnCode.setDescription("RGN Test");
		earnCode.setOvtEarnCode(false);
		earnCode.setInflateMinHours(BigDecimal.ZERO);
		earnCode.setInflateFactor(BigDecimal.ZERO);		

		KRADServiceLocator.getBusinessObjectService().save(earnCode);	
		hrEarnCodeId = earnCode.getHrEarnCodeId();
		
		TimeBlock timeBlock = new TimeBlock();
		timeBlock.setAmount(BigDecimal.ONE);
		timeBlock.setAssignmentKey("somedesc");
		timeBlock.setJobNumber(new Long(30));
		timeBlock.setBeginDate(DATE_FORMAT.parse("01/01/2010"));
		timeBlock.setEndDate(DATE_FORMAT.parse("01/03/2010"));
		timeBlock.setBeginDateTime(new DateTime(2010, 1, 1, 0, 0, 0));
		timeBlock.setEndDateTime(new DateTime(2010, 1, 3, 0, 0, 0));
		timeBlock.setEarnCode(EARN_CODE);
		timeBlock.setWorkArea(new Long(20));
		timeBlock.setTask(new Long(15));
		timeBlock.setLunchDeleted(false);
		timeBlock.setTimestamp(TKUtils.getCurrentTimestamp());
		timeBlock.setUserPrincipalId("princ");
		timeBlock.setHours(BigDecimal.TEN);
		timeBlock.setClockLogCreated(true);
		timeBlock.setDocumentId("10039");

		KRADServiceLocator.getBusinessObjectService().save(timeBlock);
		timeBlockId = timeBlock.getTkTimeBlockId();
	}

	@Override
	public void tearDown() throws Exception {
		EarnCode earnCodeObj = KRADServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(EarnCode.class, hrEarnCodeId);
		KRADServiceLocator.getBusinessObjectService().delete(earnCodeObj);
		TimeBlock timeBlockObj = KRADServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(TimeBlock.class, timeBlockId);
		KRADServiceLocator.getBusinessObjectService().delete(timeBlockObj);
		super.tearDown();
	}
	
	 
	@Test
	public void testEditExistingEarnCode() throws Exception {
		HtmlPage earnCodeLookUp = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), HrTestConstants.Urls.EARN_CODE_MAINT_URL);
		List<DomElement> lstElements = earnCodeLookUp.getElementsByIdAndOrName("history");
		for(DomElement e : lstElements) {
			HtmlRadioButtonInput radioButton = (HtmlRadioButtonInput) e;
			if(e.getAttribute("title").equals("Show History - Yes")) {
				radioButton.setChecked(true);	// set show history to yes
				break;
			}
		}
		earnCodeLookUp = HtmlUnitUtil.clickInputContainingText(earnCodeLookUp, "search");
		Assert.assertTrue("Page contains test Earn Code", earnCodeLookUp.asText().contains("RGN Test"));		
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(earnCodeLookUp, "edit", hrEarnCodeId.toString());
		Assert.assertTrue("Maintenance Page contains Test ",maintPage.asText().contains("RGN Test"));
		HtmlTextInput text  = (HtmlTextInput) maintPage.getHtmlElementById("document.documentHeader.documentDescription");
		text.setValueAttribute("test");
		HtmlElement element = maintPage.getElementByName("methodToCall.route");
        HtmlPage finalPage = element.click();

        Assert.assertTrue("Maintenance Page doesn't return warning about later effective date", finalPage.asText().contains("A record for this object exists with a later effective date"));
        HtmlElement yesButton = finalPage.getElementByName("methodToCall.processAnswer.button0");
        finalPage = yesButton.click();
        Assert.assertTrue("Maintenance Page contains error messages", finalPage.asText().contains("There is a newer version of this Earn Code."));
	}
	
	@Test
	@Ignore
	public void testDeactivateEarnCodeWithActiveTimeBlock() throws Exception {
		
		HtmlPage earnCodeLookUp = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), HrTestConstants.Urls.EARN_CODE_MAINT_URL);
		List<DomElement> lstElements = earnCodeLookUp.getElementsByIdAndOrName("history");
		for(DomElement e : lstElements) {
			HtmlRadioButtonInput radioButton = (HtmlRadioButtonInput) e;
			if(e.getAttribute("title").equals("Show History - Yes")) {
				radioButton.setChecked(true);	// set show history to yes
				break;
			}
		}
		earnCodeLookUp = HtmlUnitUtil.clickInputContainingText(earnCodeLookUp, "search");
		Assert.assertTrue("Page contains test Earn Code", earnCodeLookUp.asText().contains("RGN Test"));		
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(earnCodeLookUp, "edit", hrEarnCodeId.toString());
		Assert.assertTrue("Maintenance Page contains Test ",maintPage.asText().contains("RGN Test"));
		HtmlTextInput text  = (HtmlTextInput) maintPage.getHtmlElementById("document.documentHeader.documentDescription");
		text.setValueAttribute("test");
		HtmlCheckBoxInput active = (HtmlCheckBoxInput) maintPage.getHtmlElementById("document.newMaintainableObject.active");
		active.setChecked(false);
		HtmlTextInput effectiveDate = (HtmlTextInput) maintPage.getHtmlElementById("document.newMaintainableObject.effectiveDate");
		effectiveDate.setValueAttribute("01/02/2010");
		HtmlElement element = maintPage.getElementByName("methodToCall.route");
        HtmlPage finalPage = element.click();
        Assert.assertTrue("Maintenance Page does not return warning about active timeblock existence.", finalPage.asText().contains("Can not inactivate earn code 'RGN'. It is used in active time blocks"));
	}
	
	@Test
	public void testDeactivateEarnCodeWithInActiveTimeBlock() throws Exception {
		
		HtmlPage earnCodeLookUp = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), HrTestConstants.Urls.EARN_CODE_MAINT_URL);
		List<DomElement> lstElements = earnCodeLookUp.getElementsByIdAndOrName("history");
		for(DomElement e : lstElements) {
			HtmlRadioButtonInput radioButton = (HtmlRadioButtonInput) e;
			if(e.getAttribute("title").equals("Show History - Yes")) {
				radioButton.setChecked(true);	// set show history to yes
				break;
			}
		}
		earnCodeLookUp = HtmlUnitUtil.clickInputContainingText(earnCodeLookUp, "search");
		Assert.assertTrue("Page contains test Earn Code", earnCodeLookUp.asText().contains("RGN Test"));		
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(earnCodeLookUp, "edit", hrEarnCodeId.toString());
		Assert.assertTrue("Maintenance Page contains Test ",maintPage.asText().contains("RGN Test"));
		HtmlTextInput text  = (HtmlTextInput) maintPage.getHtmlElementById("document.documentHeader.documentDescription");
		text.setValueAttribute("test");
		HtmlCheckBoxInput active = (HtmlCheckBoxInput) maintPage.getHtmlElementById("document.newMaintainableObject.active");
		active.setChecked(false);
		HtmlTextInput effectiveDate = (HtmlTextInput) maintPage.getHtmlElementById("document.newMaintainableObject.effectiveDate");
		effectiveDate.setValueAttribute("01/04/2010");
		HtmlElement element = maintPage.getElementByName("methodToCall.route");
        HtmlPage finalPage = element.click();
        Assert.assertTrue("Maintenance Page should not return warning about active timeblock existence.", !finalPage.asText().contains("Can not inactivate earn code 'RGN'. It is used in active time blocks"));
	}
	
}
