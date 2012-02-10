package org.kuali.hr.lm.leavecode;

import org.junit.Test;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class LeaveCodeMaintTest extends TkTestCase{
	
	@Test
	public void testLookupPage() throws Exception {	 
		HtmlPage lcLookup = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.LEAVE_CODE_MAINT_URL);
		lcLookup = HtmlUnitUtil.clickInputContainingText(lcLookup, "search");
		assertTrue("Page contains test Leave Code", lcLookup.asText().contains("testLC"));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(lcLookup, "edit");
		assertTrue("Maintenance Page contains test Leave Code",maintPage.asText().contains("testLC"));	 
	}
	
	@Test
	public void testFutureEffectiveDate() throws Exception {
		this.futureEffectiveDateValidation(TkTestConstants.Urls.LEAVE_CODE_MAINT_NEW_URL);
	}

}
