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
package org.kuali.hr.core.accrualcategory;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.core.KPMETestCase;
import org.kuali.kpme.core.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.util.HrTestConstants;
import org.kuali.kpme.core.util.HtmlUnitUtil;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.rice.krad.service.KRADServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class AccrualCategoryMaintTest extends KPMETestCase {

	private static final String TEST_CODE = "_T";
	private static String accrualCategoryId;
	private static final LocalDate TEST_DATE = LocalDate.now();

	@Test
	public void testAccrualCategoryMaint() throws Exception {
		HtmlPage accuralCategoryLookup = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), HrTestConstants.Urls.ACCRUAL_CATEGORY_MAINT_URL);
		Assert.assertEquals("Active is not default to Yes", accuralCategoryLookup.getElementById("activeYes").asText(), "checked");
		accuralCategoryLookup = HtmlUnitUtil.clickInputContainingText(accuralCategoryLookup, "search");
		Assert.assertTrue("Page contains test AccuralCategory", accuralCategoryLookup.asText().contains(TEST_CODE.toString()));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(accuralCategoryLookup, "edit", accrualCategoryId.toString());
		Assert.assertTrue("Maintenance Page contains test AccuralCategory", maintPage.asText().contains(TEST_CODE));
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		AccrualCategory accrualCategory = new AccrualCategory();
		accrualCategory.setAccrualCategory(TEST_CODE);
		accrualCategory.setActive(true);
		accrualCategory.setDescr(TEST_CODE);
		accrualCategory.setEffectiveLocalDate(TEST_DATE);
		accrualCategory.setTimestamp(TKUtils.getCurrentTimestamp());
        accrualCategory.setLeavePlan("");
        accrualCategory.setAccrualEarnInterval("");
        accrualCategory.setUnitOfTime("");
        accrualCategory.setEarnCode("OC1");
		KRADServiceLocator.getBusinessObjectService().save(accrualCategory);
		accrualCategoryId = accrualCategory.getLmAccrualCategoryId();
	}

	@Override
	public void tearDown() throws Exception {
		AccrualCategory accrualCategory = KRADServiceLocator
				.getBusinessObjectService().findBySinglePrimaryKey(
						AccrualCategory.class, accrualCategoryId);
		KRADServiceLocator.getBusinessObjectService().delete(accrualCategory);
		super.tearDown();
	}

}
