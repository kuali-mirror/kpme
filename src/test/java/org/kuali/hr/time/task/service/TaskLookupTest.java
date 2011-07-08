package org.kuali.hr.time.task.service;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;

import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class TaskLookupTest extends TkTestCase {
	
	@Test
    public void testLookup() throws Exception{
    	String baseUrl = TkTestConstants.Urls.TASK_MAINT_URL;	
    	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(baseUrl);
    	assertNotNull(page);
    	assertTrue("Could not find text 'Task Lookup' in page.", StringUtils.contains(page.asText(), "Task Lookup"));
    	assertTrue("Could not find text 'Task:' in page.", StringUtils.contains(page.asText(), "Task:"));
    	assertTrue("Could not find text 'Description:' in page.", StringUtils.contains(page.asText(), "Description:"));
    	assertTrue("Could not find text 'Work Area:' in page.", StringUtils.contains(page.asText(), "Work Area:"));
    	assertTrue("Could not find text 'Work Area Description:' in page.", StringUtils.contains(page.asText(), "Work Area Description:"));
    	
    	HtmlForm form = page.getFormByName("KualiForm");
    	assertNotNull("Search form was missing from page.", form);
    	HtmlInput  input  = HtmlUnitUtil.getInputContainingText(form, "methodToCall.search");
    	assertNotNull("Could not locate search submit button", input);
    	page = (HtmlPage) input.click();
    	assertNotNull("Page not returned from click.", page);
    	HtmlUnitUtil.createTempFile(page);
    	assertTrue("Expected 6 result.", StringUtils.contains(page.asText(), "6 items retrieved"));
    	
    	setFieldValue(page, "workArea", "30");
    	HtmlPage searchPage = HtmlUnitUtil.clickInputContainingText(page, "search");
    	assertTrue("Expected 1 result.", StringUtils.contains(searchPage.asText(), "One item retrieved"));
    	assertTrue("Could not find text 'SDR1 Work Area' in page.", StringUtils.contains(searchPage.asText(), "SDR1 Work Area"));
    	
   }
}
