package org.kuali.hr.paygrade.validation;

import junit.framework.Assert;

import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestConstants;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class PayGradeValidationTest extends KPMETestCase{
	@Test
	public void testValidateSalGroup() throws Exception {
		String baseUrl = TkTestConstants.Urls.PAY_GRADE_MAINT_NEW_URL;
	  	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(baseUrl);
	  	Assert.assertNotNull(page);
	 
	  	HtmlForm form = page.getFormByName("KualiForm");
	  	Assert.assertNotNull("Search form was missing from page.", form);
	  	
	  	setFieldValue(page, "document.documentHeader.documentDescription", "Pay Grade - test");
	    setFieldValue(page, "document.newMaintainableObject.effectiveDate", "04/01/2012");
	    setFieldValue(page, "document.newMaintainableObject.payGrade", "test");
	    setFieldValue(page, "document.newMaintainableObject.salGroup", "testSG");	//nonexisting salary group
	    setFieldValue(page, "document.newMaintainableObject.description", "test");	
	  	
	  	HtmlInput  input  = HtmlUnitUtil.getInputContainingText(form, "methodToCall.route");
	  	Assert.assertNotNull("Could not locate submit button", input);
	  	HtmlElement element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	  	HtmlUnitUtil.createTempFile(page);
	  	Assert.assertTrue("page text contains:\n" + "'testSG' does not exist", page.asText().contains("'testSG' does not exist"));
	  	
	  	setFieldValue(page, "document.newMaintainableObject.salGroup", "SD1");	//existing salary group
	  	element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	  	Assert.assertFalse("page text contains: error", page.asText().contains("error"));
	  	Assert.assertTrue("New Pay grade successfully submitted.", page.asText().contains("Document was successfully submitted"));
	}
	
}