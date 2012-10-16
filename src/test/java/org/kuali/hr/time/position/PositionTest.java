/**
 * Copyright 2004-2012 The Kuali Foundation
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
package org.kuali.hr.time.position;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestConstants;

import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class PositionTest extends KPMETestCase {

	public static final String TEST_USER = "admin";
	
	
	@Test
	public void testPositionMaintWorkAreaSave() throws Exception {

		HtmlPage positionLookup = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.POSITION_MAINT_URL);
		HtmlPage resultPage = HtmlUnitUtil.clickInputContainingText(positionLookup, "search");
		
		//look up a work area
		HtmlPage positionMaintPage = HtmlUnitUtil.clickAnchorContainingText(resultPage, "edit", "2085");
		HtmlUnitUtil.createTempFile(positionMaintPage);
		Assert.assertTrue("Maintenance page contains:\n" + "Work area for Position 2085 is 1003", positionMaintPage.asText().contains("1003"));
		
		//submit a changed work area
	  	setFieldValue(positionMaintPage, "document.documentHeader.documentDescription", "Position workArea - test");
        HtmlInput workAreaText = HtmlUnitUtil.getInputContainingText(positionMaintPage, "id=\"document.newMaintainableObject.workArea\"");
		workAreaText.setValueAttribute("30");
		HtmlPage outputPage = HtmlUnitUtil.clickInputContainingText(positionMaintPage, "submit");
		HtmlUnitUtil.createTempFile(outputPage);
		Assert.assertTrue("Maintenance page text contains:\n" + "Document was successfully submitted", outputPage.asText().contains("Document was successfully submitted"));
		Assert.assertTrue("Maintenance page contains:\n" + "Work area changed to 30", outputPage.asText().contains("30"));
		
		//look up the changed work area
		HtmlPage fetchedPositionMaintPage = HtmlUnitUtil.clickAnchorContainingText(resultPage, "edit", "2085");
		HtmlUnitUtil.createTempFile(fetchedPositionMaintPage);
		Assert.assertTrue("Fetched maintenance page now contains:\n" + "Work area for Position 2085 is 30", fetchedPositionMaintPage.asText().contains("30"));
	}
	
	@Test
	public void testPositionMaintWorkAreaInquiry() throws Exception {
		
		HtmlPage positionLookup = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.POSITION_MAINT_URL);
		HtmlPage resultPage = HtmlUnitUtil.clickInputContainingText(positionLookup, "search");

		//test the work area inquiry from the position page note that this returns the first work area in tk_work_area_t in all cases for now -- see KPME-1219
		HtmlPage workAreaInquiryPage = HtmlUnitUtil.clickAnchorContainingText(resultPage, "workarea", "30");
		HtmlUnitUtil.createTempFile(workAreaInquiryPage);
		Assert.assertTrue("Inquiry page text contains:\n" + "WorkArea Inquiry", workAreaInquiryPage.asText().contains("WorkArea Inquiry"));
		//first work area in tk-test -- see comment above for this test
		Assert.assertTrue("Inquiry page text contains:\n" + "First work area in tk-test", workAreaInquiryPage.asText().contains("30"));
		
	}
	
}