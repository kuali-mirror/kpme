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
package org.kuali.hr.core.institution;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.KPMEWebTestCase;
import org.kuali.hr.util.HtmlUnitUtil;
import org.kuali.kpme.core.FunctionalTest;
import org.kuali.kpme.core.util.HrTestConstants;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

@FunctionalTest
public class InstitutionMaintTest extends KPMEWebTestCase {

	private static final String INST_CODE = "SOME-CODE";

	@Override
	public void setUp() throws Exception {
		super.setUp();
	}
	
	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	@Test
	public void testRequiredFields() throws Exception {
	  	String baseUrl = HrTestConstants.Urls.INSTITUTION_MAINT_NEW_URL;
	  	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), baseUrl);
	  	Assert.assertNotNull(page);
	 
	  	HtmlForm form = page.getFormByName("KualiForm");
	  	Assert.assertNotNull("Search form was missing from page.", form);
	  	
	  	HtmlInput  input  = HtmlUnitUtil.getInputContainingText(form, "methodToCall.route");
	  	Assert.assertNotNull("Could not locate submit button", input);
	  	
	  	HtmlElement element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	  	Assert.assertTrue("page text does not contain:\n" + "Effective Date (Effective Date) is a required field.", 
	  			page.asText().contains("Effective Date (Effective Date) is a required field."));
	  	Assert.assertTrue("page text does not contain:\n" + "Institution Code (Institution Code) is a required field.", 
	  			page.asText().contains("Institution Code (Institution Code) is a required field."));

	}
	
	@Test
	public void testLookup() throws Exception {
	  	String baseUrl = HrTestConstants.Urls.INSTITUTION_MAINT_URL;
	  	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), baseUrl);
	  	Assert.assertNotNull(page);
	 
	  	HtmlForm form = page.getFormByName("KualiForm");
	  	Assert.assertNotNull("Search form was missing from page.", form);
	  	
	  	Assert.assertNotNull("form should have show history", form.getInputByName("history"));

	  	Assert.assertNotNull("form should have active field", form.getInputByName("active"));

	  	Assert.assertNotNull("form should have institution code", form.getInputByName("institutionCode"));
	  	Assert.assertNotNull("form should have effectiveDateTo", form.getInputByName("effectiveDate"));
	  	HtmlInput  input  = HtmlUnitUtil.getInputContainingText(form, "search");
	  	Assert.assertNotNull("search input not found", input);
	  	
	  	HtmlPage resultPage = input.click();
	  	
	  	Assert.assertTrue("page text does not contain institution", 
	  			resultPage.asText().contains(INST_CODE));
	  	
	  	HtmlAnchor viewAnchor = resultPage.getAnchorByText("view");
	  	Assert.assertNotNull("no 'view' anchor found", viewAnchor);
	  	
	  	HtmlAnchor editAnchor = resultPage.getAnchorByText("edit");
	  	Assert.assertNotNull("no 'edit' anchor found", editAnchor);
	}
	
}
