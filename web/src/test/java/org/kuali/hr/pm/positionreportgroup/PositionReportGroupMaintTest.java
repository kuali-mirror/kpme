package org.kuali.hr.pm.positionreportgroup;

import java.util.List;

import junit.framework.Assert;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.junit.Test;
import org.kuali.hr.pm.test.PmTestConstants;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.pm.positionreportgroup.PositionReportGroup;
import org.kuali.kpme.pm.service.base.PmServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class PositionReportGroupMaintTest extends KPMETestCase {
	
	@Test
	public void testRequiredFields() throws Exception {
	  	String baseUrl = PmTestConstants.Urls.POSITION_REPORT_GROUP_MAINT_NEW_URL;
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
	  	Assert.assertTrue("page text does not contain:\n" + "Position Report Group (Position Report Group) is a required field.", 
	  			page.asText().contains("Position Report Group (Position Report Group) is a required field."));
	  	Assert.assertTrue("page text does not contain:\n" + "Institution (Institution) is a required field.",
	  			page.asText().contains("Institution (Institution) is a required field."));
	  	Assert.assertTrue("page text does not contain:\n" + "Campus (Campus) is a required field.", 
	  			page.asText().contains("Campus (Campus) is a required field."));
	}
	
	@Test
	public void testAddNew() throws Exception {
		DateTime effectiveDate =  new DateTime(2012, 4, 1, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone());
		String prgString = "testPRG";
		List<PositionReportGroup> prgList = PmServiceLocator.getPositionReportGroupService().getPositionReportGroupList(prgString, "testInst", "TS", effectiveDate.toLocalDate());
		Assert.assertTrue("There should NOT be Position Report Group with name " + prgString, CollectionUtils.isEmpty(prgList));
		
	  	String baseUrl = PmTestConstants.Urls.POSITION_REPORT_GROUP_MAINT_NEW_URL;
	  	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), baseUrl);
	  	Assert.assertNotNull(page);
	 
	  	HtmlForm form = page.getFormByName("KualiForm");
	  	Assert.assertNotNull("Search form was missing from page.", form);
	  	
	  	setFieldValue(page, "document.documentHeader.documentDescription", "Position Report Group - test");
	    setFieldValue(page, "document.newMaintainableObject.effectiveDate", "04/01/2012");
	    setFieldValue(page, "document.newMaintainableObject.positionReportGroup", prgString);
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
	  	  	
	  	setFieldValue(page, "document.newMaintainableObject.institution", "testInst"); // existing institution
	  	element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	  	Assert.assertFalse("page text contains:\n" + "The specified Instituion 'testInst' does not exist.", 
	  			page.asText().contains("The specified Instituion 'testInst' does not exist."));
	  	
	  	setFieldValue(page, "document.newMaintainableObject.campus", "TS"); // existing campus
	  	element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	  	Assert.assertFalse("page text contains error", page.asText().contains("error"));
	  	
	  	prgList = PmServiceLocator.getPositionReportGroupService().getPositionReportGroupList(prgString, "testInst", "TS", effectiveDate.toLocalDate());
	  	Assert.assertTrue("There should be Position Report Group with name " + prgString, CollectionUtils.isNotEmpty(prgList));
	  	
	}
}
