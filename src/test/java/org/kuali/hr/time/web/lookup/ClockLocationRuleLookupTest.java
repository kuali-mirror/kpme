package org.kuali.hr.time.web.lookup;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.kuali.hr.time.clock.location.ClockLocationRule;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.kuali.rice.kns.service.KNSServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class ClockLocationRuleLookupTest extends TkTestCase {

    private static BusinessObjectService boService = null;
    
    /**
     * Load some data to find for all of the tests in this test collection.
     */
    
    @Before
    public void setUp() throws Exception {
    	super.setUp();
    	boService = KNSServiceLocator.getBusinessObjectService();
    	clearBusinessObjects(ClockLocationRule.class);
    	
    	ClockLocationRule clr = new ClockLocationRule();
    	clr.setActive(true);
    	clr.setDeptId("12345");
    	clr.setIpAddress("ipaddress");
    	boService.save(clr);
    }
    
    
    
    @Test
    public void testLookup() throws Exception{
    	String baseUrl = HtmlUnitUtil.getBaseURL() + "/kr/lookup.do?__login_user=admin&methodToCall=start&businessObjectClassName=org.kuali.hr.time.clock.location.ClockLocationRule&returnLocation=http://localhost:8080/tk-dev/portal.do&hideReturnLink=true&docFormKey=88888888";	
    	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(baseUrl);
    	assertNotNull(page);
    	assertTrue("Could not find text 'Clock Location Rule Lookup' in page.", StringUtils.contains(page.asText(), "Clock Location Rule Lookup"));
    	HtmlForm form = page.getFormByName("KualiForm");
    	assertNotNull("Search form was missing from page.", form);
    	
    	HtmlInput  input  = HtmlUnitUtil.getInputContainingText(form, "methodToCall.search");
    	assertNotNull("Could not locate search submit button", input);
    	page = (HtmlPage) input.click();
    	assertNotNull("Page not returned from click.", page);
    	assertTrue("Expected one result.", StringUtils.contains(page.asText(), "One item retrieved"));
    	
    	page = HtmlUnitUtil.gotoPageAndLogin(baseUrl);
    	form = page.getFormByName("KualiForm");
    	assertNotNull("Search form was missing from page.", form);
    	HtmlUnitUtil.createTempFile(page);
    	form.getInputByName("deptId").setValueAttribute("20");
    	input  = HtmlUnitUtil.getInputContainingText(form, "methodToCall.search");
    	assertNotNull("Could not locate search submit button", input);
    	page = (HtmlPage) input.click();
    	assertNotNull("Page not returned from click.", page);
    	assertTrue("Expected zero results.", StringUtils.contains(page.asText(), "No values match this search."));
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
