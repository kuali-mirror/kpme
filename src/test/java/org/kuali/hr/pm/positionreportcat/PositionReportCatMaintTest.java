package org.kuali.hr.pm.positionreportcat;

import java.sql.Date;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.junit.Test;
import org.kuali.hr.pm.service.base.PmServiceLocator;
import org.kuali.hr.pm.test.PmTestConstants;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.util.TKUtils;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class PositionReportCatMaintTest extends KPMETestCase {
	
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
	  	Assert.assertTrue("page text does not contain:\n" + "Campus (Campus) is a required field.", 
	  			page.asText().contains("Campus (Campus) is a required field."));
	}
	
	@Test
	public void testAddNew() throws Exception {
		Date effectiveDate =  new Date((new DateTime(2012, 4, 1, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
		String prcString = "testPRC";
		String prtString = "testPRT";
		List<PositionReportCategory> prcList = PmServiceLocator.getPositionReportCatService().getPositionReportCatList(prcString, prtString, "testInst", "TS", effectiveDate);
		Assert.assertTrue("There should NOT be Position Report Category with name " + prcString, CollectionUtils.isEmpty(prcList));
		
	  	String baseUrl = PmTestConstants.Urls.POSITION_REPORT_CAT_MAINT_NEW_URL;
	  	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), baseUrl);
	  	Assert.assertNotNull(page);
	 
	  	HtmlForm form = page.getFormByName("KualiForm");
	  	Assert.assertNotNull("Search form was missing from page.", form);
	  	
	  	setFieldValue(page, "document.documentHeader.documentDescription", "Position Report Category - test");
	    setFieldValue(page, "document.newMaintainableObject.effectiveDate", "04/01/2012");
	    setFieldValue(page, "document.newMaintainableObject.positionReportCat", prcString);
	    setFieldValue(page, "document.newMaintainableObject.positionReportType", "noType"); // non-existing positionReportType
	    setFieldValue(page, "document.newMaintainableObject.institution", "nonExistInst");	//nonexisting institution
	    setFieldValue(page, "document.newMaintainableObject.campus", "nonCam");	//nonexisting campus
	  	
	  	HtmlInput  input  = HtmlUnitUtil.getInputContainingText(form, "methodToCall.route");
	  	Assert.assertNotNull("Could not locate submit button", input);
	  	HtmlElement element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	  	HtmlUnitUtil.createTempFile(page);
	  	Assert.assertTrue("page text contains:\n" + "There's no Property Report Type 'noType' found with Institution 'nonExistInst' and Campus 'nonCam'.", 
	  			page.asText().contains("There's no Property Report Type 'noType' found with Institution 'nonExistInst' and Campus 'nonCam'."));
		Assert.assertTrue("page text contains:\n" + "The specified Instituion 'nonExistInst' does not exist.", 
	  			page.asText().contains("The specified Instituion 'nonExistInst' does not exist."));
	  	Assert.assertTrue("page text contains:\n" + "The specified Campus 'nonCam' does not exist.", 
	  			page.asText().contains("The specified Campus 'nonCam' does not exist."));
	  	
	  	setFieldValue(page, "document.newMaintainableObject.positionReportType", prtString); // postionReportType and campus do not match
	  	setFieldValue(page, "document.newMaintainableObject.campus", "NN"); // existing, non-matching campus
	  	element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	  	Assert.assertTrue("page text contains:\n" + "There's no Property Report Type 'testPRT' found with Institution 'nonExistInst' and Campus 'NN'.", 
	  			page.asText().contains("There's no Property Report Type 'testPRT' found with Institution 'nonExistInst' and Campus 'NN'."));
	  	Assert.assertFalse("page text contains:\n" + "The specified Campus 'NN' does not exist.", 
	  			page.asText().contains("The specified Campus 'NN' does not exist."));
	  	
	  	setFieldValue(page, "document.newMaintainableObject.institution", "II"); // existing, non-matching institution
	  	element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	  	Assert.assertTrue("page text contains:\n" + "There's no Property Report Type 'testPRT' found with Institution 'II' and Campus 'NN'.", 
	  			page.asText().contains("There's no Property Report Type 'testPRT' found with Institution 'II' and Campus 'NN'."));
	  	Assert.assertFalse("page text contains:\n" + "The specified Instituion 'II' does not exist.", 
	  			page.asText().contains("The specified Instituion 'II' does not exist."));
	  	
	  	setFieldValue(page, "document.newMaintainableObject.institution", "testInst"); // matching institution	  	
	  	setFieldValue(page, "document.newMaintainableObject.campus", "TS"); // matching campus
	  	element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	  	Assert.assertFalse("page text contains error", page.asText().contains("error"));
	  	
	  	prcList = PmServiceLocator.getPositionReportCatService().getPositionReportCatList(prcString, prtString, "testInst", "TS", effectiveDate);
	  	Assert.assertTrue("There should be Position Report Category with name " + prcString, CollectionUtils.isNotEmpty(prcList));
	  	
	}
}
