package org.kuali.hr.time.timezone.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.time.test.TkTestUtils;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class TimezoneTest extends TkTestCase {

	@Test
	public void testClockInOutWithTimezone() throws Exception {
		HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.CLOCK_URL,true);
		Assert.assertNotNull(page);
    	
    	Map<String, Object> criteria = new LinkedHashMap<String, Object>();
    	criteria.put("selectedAssignment", new String[]{TkTestConstants.FormElementTypes.DROPDOWN, "30_30_30"});
    	// choose the first assignment from the drop down
    	page = TkTestUtils.fillOutForm(page, criteria);
    	Assert.assertNotNull(page);
    	// clock in
    	page = TkTestUtils.clickButton(page, "clockAction");
    	// clock out 
    	page = TkTestUtils.clickButton(page, "clockAction");
    	HtmlUnitUtil.createTempFile(page);
    	Assert.assertTrue("Time zone information is incorrect", page.asText().contains("Central Standard Time"));
	}
}
