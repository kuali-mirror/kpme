package org.kuali.hr.pm.positionreportsubcat;

import junit.framework.Assert;

import org.junit.Test;
import org.kuali.hr.pm.service.base.PmServiceLocator;
import org.kuali.hr.pm.test.PmTestConstants;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.test.HtmlUnitUtil;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class PositionReportSubCatMaintTest extends KPMETestCase {
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
	  	Assert.assertTrue("page text does not contain:\n" + "Campus (Campus) is a required field.", 
	  			page.asText().contains("Campus (Campus) is a required field."));
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
	  	
	  	setFieldValue(page, "document.documentHeader.documentDescription", "Position Report Category - test");
	    setFieldValue(page, "document.newMaintainableObject.effectiveDate", "04/01/2012");
	    setFieldValue(page, "document.newMaintainableObject.positionReportSubCat", prscString);
//	    setFieldValue(page, "document.newMaintainableObject.positionReportCat", "nonCat");	// non-existing PositionReportCategory
//	    setFieldValue(page, "document.newMaintainableObject.positionReportType.div", "testPRT"); 
	    setFieldValue(page, "document.newMaintainableObject.institution", "nonExistInst");	//nonexisting institution
	    setFieldValue(page, "document.newMaintainableObject.campus", "nonCam");	//nonexisting campus
	  	
	  	HtmlInput  input  = HtmlUnitUtil.getInputContainingText(form, "methodToCall.route");
	  	Assert.assertNotNull("Could not locate submit button", input);
	  	HtmlElement element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	  	HtmlUnitUtil.createTempFile(page);
		Assert.assertTrue("page text contains:\n" + "The specified Instituion 'nonExistInst' does not exist.", 
	  			page.asText().contains("The specified Instituion 'nonExistInst' does not exist."));
	  	Assert.assertTrue("page text contains:\n" + "The specified Campus 'nonCam' does not exist.", 
	  			page.asText().contains("The specified Campus 'nonCam' does not exist."));

	  	setFieldValue(page, "document.newMaintainableObject.institution", "testInst"); // matching institution	  	
	  	setFieldValue(page, "document.newMaintainableObject.campus", "TS"); // matching campus
	  	element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	  	Assert.assertFalse("page text should NOT contain:\n" + "The specified Instituion 'nonExistInst' does not exist.", 
	  			page.asText().contains("The specified Instituion 'nonExistInst' does not exist."));
	  	Assert.assertFalse("page text should NOT contain:\n" + "The specified Campus 'nonCam' does not exist.", 
	  			page.asText().contains("The specified Campus 'nonCam' does not exist."));
	
	}
}
