package org.kuali.hr.time.systemlunch.rule;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.syslunch.rule.SystemLunchRule;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.time.test.TkTestUtils;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TkConstants;

import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class SystemLunchRuleTest extends TkTestCase {
	
	SystemLunchRule systemLunchRule;
	Date date = new Date((new DateTime(2010, 1, 1, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
	
	@Test
	public void testSystemLunchRuleFetch() throws Exception{
		this.systemLunchRule = TkServiceLocator.getSystemLunchRuleService().getSystemLunchRule(date);
		assertTrue("System lunch rule is pulled back", this.systemLunchRule!=null);
	}
	
	/**
	 * Test if the lunch in/out button shows and if the time block is created with the correct clock action
	 */
	
	@Test
	public void testSystemLunchRule() throws Exception {
		
		systemLunchRule = TkServiceLocator.getSystemLunchRuleService().getSystemLunchRule(date);
		assertTrue("System lunch rule is pulled back", systemLunchRule!=null);

        String baseUrl = TkTestConstants.Urls.CLOCK_URL;
    	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(baseUrl);

//    	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.CLOCK_URL);
    	assertNotNull(page);
    	
    	Map<String, Object> criteria = new LinkedHashMap<String, Object>();
    	criteria.put("selectedAssignment", new String[]{TkTestConstants.FormElementTypes.DROPDOWN, "2_1234_2"});
    	
    	// choose the first assignment from the drop down
    	page = TkTestUtils.fillOutForm(page, criteria);
    	assertNotNull(page);
    	
    	// clock in
    	page = TkTestUtils.clickButton(page, "clockAction");
    	HtmlUnitUtil.createTempFile(page);
    	assertTrue("The take lunch button didn't appear", page.asXml().contains("lunchOut"));
    	
    	// the lunch in button should display after clocking in
    	page = TkTestUtils.clickButton(page, "lunchOut");
    	assertTrue("The return from lunch button didn't appear", page.asXml().contains("lunchIn"));
    	assertEquals(TkConstants.LUNCH_OUT, TkServiceLocator.getClockLogService().getLastClockLog(TKContext.getPrincipalId()).getClockAction());
    	
    	// the lunch out button should display after lunching in
    	page = TkTestUtils.clickButton(page, "lunchIn");
    	assertEquals(TkConstants.LUNCH_IN, TkServiceLocator.getClockLogService().getLastClockLog(TKContext.getPrincipalId()).getClockAction());
    	
	}
}
