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
package org.kuali.hr.time.earncode.validation;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import com.gargoylesoftware.htmlunit.html.*;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.krad.service.KRADServiceLocator;

public class EarnCodeMaintenaceTest extends KPMETestCase {
	private static final java.sql.Date TEST_DATE = new Date((new DateTime(2009, 1, 1, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
	private static final String EARN_CODE = "RGN";
	private static String hrEarnCodeId;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		EarnCode earnCode = new EarnCode();
		earnCode.setActive(true);
		earnCode.setEarnCode(EARN_CODE);
		earnCode.setEffectiveDate(TEST_DATE);
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
	}

	@Override
	public void tearDown() throws Exception {
		EarnCode earnCodeObj = KRADServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(EarnCode.class, hrEarnCodeId);
		KRADServiceLocator.getBusinessObjectService().delete(earnCodeObj);				
		super.tearDown();
	}
	
	 
	@Test
	public void testEditExistingEarnCode() throws Exception {
		HtmlPage earnCodeLookUp = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), TkTestConstants.Urls.EARN_CODE_MAINT_URL);
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
	
}
