package org.kuali.hr.time.principalhr;

import org.junit.Test;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class PrincipalHRAttributeTest extends TkTestCase {
	@Test
	public void testPrincipalHRAttributeTest() throws Exception{
		//confirm maintenance page renders default data
		//confirm non existent pay calendar throws error
		//confirm non existenet holiday calendar throws error
		HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.PRIN_HR_MAINT_URL);
    	page = HtmlUnitUtil.clickInputContainingText(page, "search");
    	HtmlUnitUtil.createTempFile(page);
    	page = HtmlUnitUtil.clickAnchorContainingText(page, "edit","principalId=fred");
    	HtmlUnitUtil.createTempFile(page);
    	assertTrue("Test that maintenance screen rendered", page.asText().contains("fred"));
    	assertTrue("Test that maintenance screen rendered", page.asText().contains("Record Time"));//KPME1268
    	assertTrue("Test that maintenance screen rendered", page.asText().contains("Record Leave"));//KPME1268
	}
	
	// KPME-1442 Kagata
	//@Test
	//public void testFutureEffectiveDate() throws Exception {
	//	this.futureEffectiveDateValidation(TkTestConstants.Urls.PRIN_HR_MAINT_NEW_URL);
	//}
}
