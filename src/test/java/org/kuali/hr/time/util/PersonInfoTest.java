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
package org.kuali.hr.time.util;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestConstants;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class PersonInfoTest extends KPMETestCase {
	
	@Test
	public void testPersonInfo() throws Exception{	
		// pass the login filter
		HtmlPage clockPage = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.PERSON_INFO_URL);
		HtmlUnitUtil.createTempFile(clockPage);
		Assert.assertTrue("Person Info Page renders with inappropriate data",clockPage.asText().contains("Principal Name"));
		Assert.assertTrue("Person Info Page renders with inappropriate data",clockPage.asText().contains("Name"));
		Assert.assertTrue("Person Info Page renders with inappropriate data",clockPage.asText().contains("admin, admin"));
		Assert.assertTrue("Person Info Page renders with inappropriate data",clockPage.asText().contains("Job Number"));
		Assert.assertTrue("Person Info Page renders with inappropriate data",clockPage.asText().contains("Department Admin"));
		
	}
 
}
