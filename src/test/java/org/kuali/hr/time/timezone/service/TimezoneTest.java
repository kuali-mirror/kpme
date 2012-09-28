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
package org.kuali.hr.time.timezone.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.time.test.TkTestUtils;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class TimezoneTest extends KPMETestCase {

	@Test
	public void testClockInOutWithTimezone() throws Exception {
		HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.CLOCK_URL,true);
		Assert.assertNotNull(page);
    	
    	Map<String, Object> criteria = new LinkedHashMap<String, Object>();
    	criteria.put("selectedAssignment", new String[]{TkTestConstants.FormElementTypes.DROPDOWN, "30_30_30"});
    	// choose the first assignment from the drop down
    	page = TkTestUtils.fillOutForm(page, criteria);
    	Assert.assertNotNull(page);
    	// clock in
    	page = TkTestUtils.clickButton(page, "clockAction");
    	// clock out 
    	page = TkTestUtils.clickButton(page, "clockAction");
    	HtmlUnitUtil.createTempFile(page);
    	Assert.assertTrue("Time zone information is incorrect", page.asText().contains("Eastern"));
	}
}
