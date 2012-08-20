package org.kuali.hr.time.principalhr;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestConstants;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

public class PrincipalHRAttributeTest extends KPMETestCase {
	@Test
	public void testPrincipalHRAttributeTest() throws Exception{
		//confirm maintenance page renders default data
		//confirm non existent pay calendar throws error
		//confirm non existenet holiday calendar throws error
		HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.PRIN_HR_MAINT_URL);
    	page = HtmlUnitUtil.clickInputContainingText(page, "search");
    	HtmlUnitUtil.createTempFile(page);
    	page = HtmlUnitUtil.clickAnchorContainingText(page, "edit","hrPrincipalAttributeId=2004");
    	HtmlUnitUtil.createTempFile(page);
    	Assert.assertTrue("Test that maintenance screen rendered", page.asText().contains("fred"));
    	Assert.assertTrue("Test that maintenance screen rendered", page.asText().contains("Pay Calendar"));
    	Assert.assertTrue("Test that maintenance screen rendered", page.asText().contains("Leave Calendar"));
	}
	
	// KPME-1442 Kagata
	//@Test
	//public void testFutureEffectiveDate() throws Exception {
	//	this.futureEffectiveDateValidation(TkTestConstants.Urls.PRIN_HR_MAINT_NEW_URL);
	//}
}
