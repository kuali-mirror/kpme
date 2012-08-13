package org.kuali.hr.time.web.lookup;

import java.sql.Date;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.clock.location.ClockLocationRule;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.KRADServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class ClockLocationRuleLookupTest extends KPMETestCase {

    private static BusinessObjectService boService = null;
    
    /**
     * Load some data to find for all of the tests in this test collection.
     */
    
    @Before
    public void setUp() throws Exception {
    	super.setUp();
    	boService = KRADServiceLocator.getBusinessObjectService();
    	clearBusinessObjects(ClockLocationRule.class);
    	
    	ClockLocationRule clr = new ClockLocationRule();
    	clr.setActive(true);
    	clr.setDept("12345");
    	Date asOfDate = new Date((new DateTime(2010, 8, 1, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
    	clr.setEffectiveDate(asOfDate);
    	boService.save(clr);
    }
    
    
    
    @Test
    public void testLookup() throws Exception{
    	String baseUrl = HtmlUnitUtil.getBaseURL() + "/kr/lookup.do?__login_user=admin&methodToCall=start&businessObjectClassName=org.kuali.hr.time.clock.location.ClockLocationRule&returnLocation=" + HtmlUnitUtil.getBaseURL() + "/portal.do&hideReturnLink=true&docFormKey=88888888";
    	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(baseUrl);
    	Assert.assertNotNull(page);
    	Assert.assertTrue("Could not find text 'Clock Location Rule Lookup' in page.", StringUtils.contains(page.asText(), "Clock Location Rule Lookup"));
    	HtmlForm form = page.getFormByName("KualiForm");
    	Assert.assertNotNull("Search form was missing from page.", form);
    	
    	HtmlInput  input  = HtmlUnitUtil.getInputContainingText(form, "methodToCall.search");
    	Assert.assertNotNull("Could not locate search submit button", input);
    	page = (HtmlPage) input.click();
    	Assert.assertNotNull("Page not returned from click.", page);
    	HtmlUnitUtil.createTempFile(page);
    	Assert.assertTrue("Expected one result.", StringUtils.contains(page.asText(), "One item retrieved"));
    	
    	page = HtmlUnitUtil.gotoPageAndLogin(baseUrl);
    	form = page.getFormByName("KualiForm");
    	Assert.assertNotNull("Search form was missing from page.", form);
    	HtmlUnitUtil.createTempFile(page);
    	form.getInputByName("dept").setValueAttribute("20");
    	input  = HtmlUnitUtil.getInputContainingText(form, "methodToCall.search");
    	Assert.assertNotNull("Could not locate search submit button", input);
    	page = (HtmlPage) input.click();
    	Assert.assertNotNull("Page not returned from click.", page);
    	Assert.assertTrue("Expected zero results.", StringUtils.contains(page.asText(), "No values match this search."));
    }

    @SuppressWarnings("unchecked")
    public static void clearBusinessObjects(Class clazz) {
	boService.deleteMatching(clazz, new HashMap());
    }



	@Override
	public void tearDown() throws Exception {
		clearBusinessObjects(ClockLocationRule.class);
		super.tearDown();
	}
}
