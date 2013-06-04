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
package org.kuali.hr.time.personinfo;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.KPMEWebTestCase;
import org.kuali.hr.util.HtmlUnitUtil;
import org.kuali.kpme.tklm.utils.TkTestConstants;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class PersonInfoTest extends KPMEWebTestCase {
	
	@Test
	public void testPersonInfo() throws Exception{	
		// pass the login filter
		HtmlPage clockPage = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), TkTestConstants.Urls.PERSON_INFO_URL);
		HtmlUnitUtil.createTempFile(clockPage);
		Assert.assertTrue("Person Info Page renders with inappropriate data",clockPage.asText().contains("Principal Name"));
		Assert.assertTrue("Person Info Page renders with inappropriate data",clockPage.asText().contains("Name"));
		Assert.assertTrue("Person Info Page renders with inappropriate data",clockPage.asText().contains("admin, admin"));
		Assert.assertTrue("Person Info Page renders with inappropriate data",clockPage.asText().contains("Job Number"));
		Assert.assertTrue("Person Info Page renders with inappropriate data",clockPage.asText().contains("Department Admin"));
		
	}
 
}
