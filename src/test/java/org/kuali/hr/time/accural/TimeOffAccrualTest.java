package org.kuali.hr.time.accural;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;

import com.gargoylesoftware.htmlunit.html.HtmlPage;


public class TimeOffAccrualTest extends TkTestCase
{
	
	private static Long timeOffAccrualId = 1L;//id entered in the bootstrap SQL
	
	@Test
	public void testTimeOffAccrualMaintenancePage() throws Exception{	
		HtmlPage docHeaderLookUp = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.TIME_OFF_ACCRUAL_MAINT_URL);
		docHeaderLookUp = HtmlUnitUtil.clickInputContainingText(docHeaderLookUp, "search");
		Assert.assertTrue("Page contains admin entry", docHeaderLookUp.asText().contains("admin"));		
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(docHeaderLookUp, "edit",timeOffAccrualId.toString());		
		Assert.assertTrue("Maintenance Page contains TEX entry",maintPage.asText().contains("TEX"));
	}
}
	
	

	
	

