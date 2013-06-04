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
package org.kuali.hr.pm.positionreportsubcat;

import junit.framework.Assert;

import org.junit.Test;
import org.kuali.hr.KPMEWebTestCase;
import org.kuali.hr.util.HtmlUnitUtil;
import org.kuali.kpme.pm.positionreportsubcat.PositionReportSubCategory;
import org.kuali.kpme.pm.service.base.PmServiceLocator;
import org.kuali.kpme.pm.utils.PmTestConstants;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class PositionReportSubCatMaintTest extends KPMEWebTestCase {
	@Test
	public void testRequiredFields() throws Exception {
	  	String baseUrl = PmTestConstants.Urls.POSITION_REPORT_SUB_CAT_MAINT_NEW_URL;
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
	  	Assert.assertTrue("page text does not contain:\n" + "Position Report Sub Category (Position Report Sub Category) is a required field.", 
	  			page.asText().contains("Position Report Sub Category (Position Report Sub Category) is a required field."));
	  	Assert.assertTrue("page text does not contain:\n" + "Position Report Category (Position Report Category) is a required field.", 
	  			page.asText().contains("Position Report Category (Position Report Category) is a required field."));
	  	Assert.assertTrue("page text does not contain:\n" + "Position Report Type (Position Report Type) is a required field.", 
	  			page.asText().contains("Position Report Type (Position Report Type) is a required field."));
	  	Assert.assertTrue("page text does not contain:\n" + "Institution (Institution) is a required field.",
	  			page.asText().contains("Institution (Institution) is a required field."));
	  	Assert.assertTrue("page text does not contain:\n" + "Location (Location) is a required field.", 
	  			page.asText().contains("Location (Location) is a required field."));
	}
	
	@Test
	public void testAddNew() throws Exception {
		String prscString = "testPRSC";
		PositionReportSubCategory prsc = PmServiceLocator.getPositionReportSubCatService().getPositionReportSubCatById("1000");
		Assert.assertTrue("There should NOT be Position Report Sub Category with name " + prscString, prsc == null);
		
	  	String baseUrl = PmTestConstants.Urls.POSITION_REPORT_SUB_CAT_MAINT_NEW_URL;
	  	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), baseUrl);
	  	Assert.assertNotNull(page);
	 
	  	HtmlForm form = page.getFormByName("KualiForm");
	  	Assert.assertNotNull("Search form was missing from page.", form);
	  	
	  	HtmlUnitUtil.setFieldValue(page, "document.documentHeader.documentDescription", "Position Report Category - test");
	    HtmlUnitUtil.setFieldValue(page, "document.newMaintainableObject.effectiveDate", "04/01/2012");
	    HtmlUnitUtil.setFieldValue(page, "document.newMaintainableObject.positionReportSubCat", prscString);
//	    HtmlUnitUtil.setFieldValue(page, "document.newMaintainableObject.positionReportCat", "nonCat");	// non-existing PositionReportCategory
//	    HtmlUnitUtil.setFieldValue(page, "document.newMaintainableObject.positionReportType.div", "testPRT"); 
	    HtmlUnitUtil.setFieldValue(page, "document.newMaintainableObject.institution", "nonExistInst");	//nonexisting institution
	    HtmlUnitUtil.setFieldValue(page, "document.newMaintainableObject.location", "nonCam");	//nonexisting location
	  	
	  	HtmlInput  input  = HtmlUnitUtil.getInputContainingText(form, "methodToCall.route");
	  	Assert.assertNotNull("Could not locate submit button", input);
	  	HtmlElement element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	  	HtmlUnitUtil.createTempFile(page);
		Assert.assertTrue("page text contains:\n" + "The specified Instituion 'nonExistInst' does not exist.", 
	  			page.asText().contains("The specified Instituion 'nonExistInst' does not exist."));
	  	Assert.assertTrue("page text contains:\n" + "The specified Location 'nonCam' does not exist.", 
	  			page.asText().contains("The specified Location 'nonCam' does not exist."));

	  	HtmlUnitUtil.setFieldValue(page, "document.newMaintainableObject.institution", "testInst"); // matching institution	  	
	  	HtmlUnitUtil.setFieldValue(page, "document.newMaintainableObject.location", "BL"); // matching Location
	  	element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	  	Assert.assertFalse("page text should NOT contain:\n" + "The specified Instituion 'nonExistInst' does not exist.", 
	  			page.asText().contains("The specified Instituion 'nonExistInst' does not exist."));
	  	Assert.assertFalse("page text should NOT contain:\n" + "The specified Location 'nonCam' does not exist.", 
	  			page.asText().contains("The specified Location 'nonCam' does not exist."));
	
	}
}
