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
package org.kuali.hr.time.principalhr;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestConstants;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class PrincipalHRAttributeTest extends KPMETestCase {
	@Test
	public void testPrincipalHRAttributeTest() throws Exception{
		//confirm maintenance page renders default data
		//confirm non existent pay calendar throws error
		//confirm non existenet holiday calendar throws error
		HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), TkTestConstants.Urls.PRIN_HR_MAINT_URL);
    	page = HtmlUnitUtil.clickInputContainingText(page, "search");
    	HtmlUnitUtil.createTempFile(page);
    	page = HtmlUnitUtil.clickAnchorContainingText(page, "edit","hrPrincipalAttributeId=2004");
    	HtmlUnitUtil.createTempFile(page);
    	Assert.assertTrue("Test that maintenance screen rendered", page.asText().contains("fred"));
    	Assert.assertTrue("Test that maintenance screen rendered", page.asText().contains("Pay Calendar"));
    	Assert.assertTrue("Test that maintenance screen rendered", page.asText().contains("Leave Calendar"));
	}
	
	// KPME-1442 Kagata
	//@Test
	//public void testFutureEffectiveDate() throws Exception {
	//	this.futureEffectiveDateValidation(TkTestConstants.Urls.PRIN_HR_MAINT_NEW_URL);
	//}
}
