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
package org.kuali.hr.core.paytype;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.core.KPMETestCase;
import org.kuali.kpme.core.util.HrTestConstants;
import org.kuali.kpme.core.util.HtmlUnitUtil;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class PayTypeTest extends KPMETestCase {
	
	private static Long payTypeId = 1L;//id entered in the bootstrap SQL
	
	@Test
	public void testPayTypeMaintenancePage() throws Exception{	
		HtmlPage earnCodeLookUp = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), HrTestConstants.Urls.PAYTYPE_MAINT_URL);
		earnCodeLookUp = HtmlUnitUtil.clickInputContainingText(earnCodeLookUp, "search");
		Assert.assertTrue("Page contains BW entry", earnCodeLookUp.asText().contains("BW"));		
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(earnCodeLookUp, "edit",payTypeId.toString());		
		Assert.assertTrue("Maintenance Page contains RGN entry",maintPage.asText().contains("RGN"));
	}
	
}
