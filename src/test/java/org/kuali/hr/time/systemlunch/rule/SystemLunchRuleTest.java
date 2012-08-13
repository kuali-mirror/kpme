package org.kuali.hr.time.systemlunch.rule;

import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.syslunch.rule.SystemLunchRule;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.time.test.TkTestUtils;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TkConstants;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class SystemLunchRuleTest extends KPMETestCase {
	
	SystemLunchRule systemLunchRule;
	Date date = new Date((new DateTime(2010, 1, 1, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
	
	@Test
	public void testSystemLunchRuleFetch() throws Exception{
		this.systemLunchRule = TkServiceLocator.getSystemLunchRuleService().getSystemLunchRule(date);
		Assert.assertTrue("System lunch rule is pulled back", this.systemLunchRule!=null);
	}
	
	/**
	 * Test if the lunch in/out button shows and if the time block is created with the correct clock action
	 */
	
	@Test
	public void testSystemLunchRule() throws Exception {
		
		systemLunchRule = TkServiceLocator.getSystemLunchRuleService().getSystemLunchRule(date);
		Assert.assertTrue("System lunch rule is pulled back", systemLunchRule!=null);

        String baseUrl = TkTestConstants.Urls.CLOCK_URL;
    	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(baseUrl);

//    	HtmlPage page = HtmlUnitUtil.gotoPageAnBatchJobEntryTestdLogin(TkTestConstants.Urls.CLOCK_URL);
    	Assert.assertNotNull(page);
    	
    	Map<String, Object> criteria = new LinkedHashMap<String, Object>();
    	criteria.put("selectedAssignment", new String[]{TkTestConstants.FormElementTypes.DROPDOWN, "2_1234_2"});
    	HtmlUnitUtil.createTempFile(page);
    	// choose the first assignment from the drop down
    	page = TkTestUtils.fillOutForm(page, criteria);
    	Assert.assertNotNull(page);
    	
    	// clock in
    	page = TkTestUtils.clickClockInOrOutButton(page);
    	HtmlUnitUtil.createTempFile(page);
    	Assert.assertTrue("The take lunch button didn't appear", page.asXml().contains("lunchOut"));
    	
    	// the lunch in button should display after clocking in
    	page = TkTestUtils.clickLunchInOrOutButton(page, "LO");
    	Assert.assertTrue("The return from lunch button didn't appear", page.asXml().contains("lunchIn"));
    	Thread.sleep(3000);
    	Assert.assertEquals(TkConstants.LUNCH_OUT, TkServiceLocator.getClockLogService().getLastClockLog(TKContext.getPrincipalId()).getClockAction());
    	
    	// the lunch out button should display after lunching in
    	page = TkTestUtils.clickLunchInOrOutButton(page, "LI");
    	Thread.sleep(3000);
    	Assert.assertEquals(TkConstants.LUNCH_IN, TkServiceLocator.getClockLogService().getLastClockLog(TKContext.getPrincipalId()).getClockAction());
    	
	}
}
