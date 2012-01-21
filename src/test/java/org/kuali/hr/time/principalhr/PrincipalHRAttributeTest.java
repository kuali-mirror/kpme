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
    	page = HtmlUnitUtil.clickAnchorContainingText(page, "edit","principalId=admin");
    	assertTrue("Test that maintenance screen rendered", page.asText().contains("admin"));
	}


}
