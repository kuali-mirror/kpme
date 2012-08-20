package org.kuali.hr.time.paytype;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestConstants;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class PayTypeTest extends KPMETestCase {
	
	private static Long payTypeId = 1L;//id entered in the bootstrap SQL
	
	@Test
	public void testPayTypeMaintenancePage() throws Exception{	
		HtmlPage earnCodeLookUp = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.PAYTYPE_MAINT_URL);
		earnCodeLookUp = HtmlUnitUtil.clickInputContainingText(earnCodeLookUp, "search");
		Assert.assertTrue("Page contains BW entry", earnCodeLookUp.asText().contains("BW"));		
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(earnCodeLookUp, "edit",payTypeId.toString());		
		Assert.assertTrue("Maintenance Page contains RGN entry",maintPage.asText().contains("RGN"));
	}
	
}
