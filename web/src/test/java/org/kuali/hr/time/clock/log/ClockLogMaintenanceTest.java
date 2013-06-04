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
package org.kuali.hr.time.clock.log;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.KPMEWebTestCase;
import org.kuali.hr.util.HtmlUnitUtil;
import org.kuali.kpme.tklm.utils.TkTestConstants;

import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class ClockLogMaintenanceTest extends KPMEWebTestCase{
	private static Long TEST_CODE_INVALID_TASK_ID =9999L;
	private static Long TEST_CODE_INVALID_WORK_AREA_ID =9999L;
	private static Long clockLogId = 1L;	
	
	@Test
	public void testClockLogMaint() throws Exception {
		HtmlPage clockLogLookUp = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), TkTestConstants.Urls.CLOCK_LOG_MAINT_URL);
		clockLogLookUp = HtmlUnitUtil.clickInputContainingText(clockLogLookUp, "search");
		Assert.assertTrue("Page contains test ClockLog", clockLogLookUp.asText().contains("TEST"));		
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(clockLogLookUp, "edit",clockLogId.toString());		
		Assert.assertTrue("Maintenance Page contains test ClockLog",maintPage.asText().contains("TEST"));		
	}
	
	@Test
	public void testClockLogMaintForErrorMessages() throws Exception {
		HtmlPage clockLogLookUp = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), TkTestConstants.Urls.CLOCK_LOG_MAINT_URL);
		clockLogLookUp = HtmlUnitUtil.clickInputContainingText(clockLogLookUp, "search");
		Assert.assertTrue("Page contains test ClockLog", clockLogLookUp.asText().contains("TEST"));		
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(clockLogLookUp, "edit",clockLogId.toString());		
		Assert.assertTrue("Maintenance Page contains test ClockLog",maintPage.asText().contains("TEST"));
		
		HtmlInput inputForDescription = HtmlUnitUtil.getInputContainingText(
				maintPage, "* Document Description");
		inputForDescription.setValueAttribute("Test_Description");
		HtmlUnitUtil.setFieldValue(maintPage, "document.newMaintainableObject.workArea", TEST_CODE_INVALID_WORK_AREA_ID.toString());
		HtmlUnitUtil.setFieldValue(maintPage, "document.newMaintainableObject.task", TEST_CODE_INVALID_TASK_ID.toString());
		HtmlPage resultantPageAfterEdit = HtmlUnitUtil
				.clickInputContainingText(maintPage, "submit");
		HtmlUnitUtil.createTempFile(resultantPageAfterEdit);
		
		Assert.assertTrue("Maintenance Page contains test Workarea ",
				resultantPageAfterEdit.asText().contains(
						"The specified Workarea '"
								+ TEST_CODE_INVALID_WORK_AREA_ID
								+ "' does not exist."));
		
		HtmlUnitUtil.createTempFile(resultantPageAfterEdit);
		
		Assert.assertTrue("Maintenance Page contains test Task ",
				resultantPageAfterEdit.asText().contains(
						"The specified Task '"
								+ TEST_CODE_INVALID_TASK_ID
								+ "' does not exist."));
		
		
	}
	
}