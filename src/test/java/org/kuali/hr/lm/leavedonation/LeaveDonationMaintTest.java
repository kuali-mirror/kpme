package org.kuali.hr.lm.leavedonation;

import org.junit.Test;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class LeaveDonationMaintTest extends TkTestCase{
	
	@Test
	public void testLookupPage() throws Exception {	 
		HtmlPage lcLookup = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.LEAVE_DONATION_MAINT_URL);
		lcLookup = HtmlUnitUtil.clickInputContainingText(lcLookup, "search");
		assertTrue("Page contains test Donated Account Category", lcLookup.asText().contains("dAC"));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(lcLookup, "edit");
		assertTrue("Maintenance Page contains test Donated Account Category",maintPage.asText().contains("dAC"));	 
	}
	
	// commented out this test, KPME-1207, effectiveDate can be past, current or future
	//@Test
	/*public void testFutureEffectiveDate() throws Exception {
		this.futureEffectiveDateValidation(TkTestConstants.Urls.LEAVE_DONATION_MAINT_NEW_URL);
	}*/
}
