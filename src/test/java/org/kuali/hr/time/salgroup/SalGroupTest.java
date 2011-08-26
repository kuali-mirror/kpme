package org.kuali.hr.time.salgroup;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.Test;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;

public class SalGroupTest extends TkTestCase {
	
	@Test
	public void testSalGroupMaintenancePage() throws Exception{	
    	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.SAL_GROUP_MAINT_URL);
    	page = HtmlUnitUtil.clickInputContainingText(page, "search");
    	HtmlUnitUtil.createTempFile(page);
    	page = HtmlUnitUtil.clickAnchorContainingText(page, "edit","hrSalGroupId=10");
    	assertTrue("Test that maintenance screen rendered", page.asText().contains("A10"));
	}
	
}
