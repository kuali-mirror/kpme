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
package org.kuali.hr.pm.positionreportcat;

import java.util.List;

import junit.framework.Assert;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.junit.Test;
import org.kuali.hr.KPMEWebTestCase;
import org.kuali.hr.util.HtmlUnitUtil;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.pm.positionreportcat.PositionReportCategory;
import org.kuali.kpme.pm.service.base.PmServiceLocator;
import org.kuali.kpme.pm.utils.PmTestConstants;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class PositionReportCatMaintTest extends KPMEWebTestCase {
	
	@Test
	public void testRequiredFields() throws Exception {
	  	String baseUrl = PmTestConstants.Urls.POSITION_REPORT_CAT_MAINT_NEW_URL;
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
		DateTime effectiveDate =  new DateTime(2012, 4, 1, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone());
		String prcString = "testPRC";
		String prtString = "testPRT";
		List<PositionReportCategory> prcList = PmServiceLocator.getPositionReportCatService().getPositionReportCatList(prcString, prtString, "testInst", "TS", effectiveDate.toLocalDate());
		Assert.assertTrue("There should NOT be Position Report Category with name " + prcString, CollectionUtils.isEmpty(prcList));
		
	  	String baseUrl = PmTestConstants.Urls.POSITION_REPORT_CAT_MAINT_NEW_URL;
	  	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), baseUrl);
	  	Assert.assertNotNull(page);
	 
	  	HtmlForm form = page.getFormByName("KualiForm");
	  	Assert.assertNotNull("Search form was missing from page.", form);
	  	
	  	HtmlUnitUtil.setFieldValue(page, "document.documentHeader.documentDescription", "Position Report Category - test");
	    HtmlUnitUtil.setFieldValue(page, "document.newMaintainableObject.effectiveDate", "04/01/2012");
	    HtmlUnitUtil.setFieldValue(page, "document.newMaintainableObject.positionReportCat", prcString);
	    HtmlUnitUtil.setFieldValue(page, "document.newMaintainableObject.positionReportType", "noType"); // non-existing positionReportType
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
	  	Assert.assertTrue("page text contains:\n" + "The specified PositionReportType 'noType' does not exist.", 
	  			page.asText().contains("The specified PositionReportType 'noType' does not exist."));
	  	
	  	HtmlUnitUtil.setFieldValue(page, "document.newMaintainableObject.positionReportType", prtString); // postionReportType and location do not match
	  	HtmlUnitUtil.setFieldValue(page, "document.newMaintainableObject.location", "NN"); // existing, non-matching location
	  	element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	  	Assert.assertFalse("page text contains:\n" + "The specified Location 'NN' does not exist.", 
	  			page.asText().contains("The specified Locaiton 'NN' does not exist."));
		Assert.assertTrue("page text contains:\n" + "Institution 'nonExistInst' is inconsistent with institution 'testInst' of PositionReportType 'testPRT'", 
	  			page.asText().contains("Institution 'nonExistInst' is inconsistent with institution 'testInst' of PositionReportType 'testPRT'"));
	  	
	  	HtmlUnitUtil.setFieldValue(page, "document.newMaintainableObject.institution", "II"); // existing, non-matching institution
	  	element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	  	Assert.assertTrue("page text contains:\n" + "Institution 'II' is inconsistent with institution 'testInst' of PositionReportType 'testPRT'", 
	  			page.asText().contains("Institution 'II' is inconsistent with institution 'testInst' of PositionReportType 'testPRT'"));
	  	Assert.assertFalse("page text contains:\n" + "The specified Instituion 'II' does not exist.", 
	  			page.asText().contains("The specified Instituion 'II' does not exist."));
	  	
	  	HtmlUnitUtil.setFieldValue(page, "document.newMaintainableObject.institution", "testInst"); // matching institution	  	
	  	HtmlUnitUtil.setFieldValue(page, "document.newMaintainableObject.location", "BL"); // matching location
	  	element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	  	Assert.assertFalse("page text contains error", page.asText().contains("error"));
	  	
	  	prcList = PmServiceLocator.getPositionReportCatService().getPositionReportCatList(prcString, prtString, "testInst", "BL", effectiveDate.toLocalDate());
	  	Assert.assertTrue("There should be Position Report Category with name " + prcString, CollectionUtils.isNotEmpty(prcList));
	  	
	}
}
