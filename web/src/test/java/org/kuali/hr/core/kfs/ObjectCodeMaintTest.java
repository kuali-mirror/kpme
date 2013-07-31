package org.kuali.hr.core.kfs;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.hr.KPMEWebTestCase;
import org.kuali.hr.util.HtmlUnitUtil;
import org.kuali.kpme.core.util.HrTestConstants;

import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class ObjectCodeMaintTest extends KPMEWebTestCase {
	
	private String newUrl;
	private String lookupUrl;
	private Map<String,String> requiredFields;

	@Override
	public void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		before();
	}

	@Override
	public void tearDown() throws Exception {
		// TODO Auto-generated method stub
		after();
		super.tearDown();
	}
	
	private void before() {
		
		newUrl = HrTestConstants.Urls.OBJECT_CODE_MAINT_NEW_URL;
		lookupUrl = HrTestConstants.Urls.OBJECT_CODE_MAINT_URL;
		
		requiredFields = new HashMap<String,String>();
		requiredFields.put("universityFiscalYear", "Fiscal Year (FY) is a required field.");
		requiredFields.put("chartOfAccountsCode", "Chart of Accounts Code (ChartOfAccountsCode) is a required field.");
		requiredFields.put("financialObjectCode", "Object Code (Object) is a required field.");
		requiredFields.put("financialObjectCodeName", "Object Code Name (ObjCodeName) is a required field.");
		requiredFields.put("financialObjectCodeShortName", "Object Code Short Name (ObjCodeShortName) is a required field.");
	}
	
	private void after() {
		requiredFields.clear();
	}
	
	@Test
	public void testRequiredFields() throws Exception {
		HtmlPage maintPage = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), newUrl);
		assertNotNull("maintenance page is null", maintPage);
		
		HtmlInput docDescription = HtmlUnitUtil.getInputContainingText(maintPage, "* Document Description");
		assertNotNull("maintenance page does not contain document description", docDescription);
		
		docDescription.setValueAttribute("testing submission");
		
		HtmlPage resultPage = HtmlUnitUtil.clickInputContainingText(maintPage, "submit");
		assertNotNull("no result page returned after submit", resultPage);
		
		String resultPageAsText = resultPage.asText();
		for(Entry<String,String> requiredField : requiredFields.entrySet()) {
			assertTrue("page does not contain error message for required field: '" + requiredField.getKey() + "'",
					resultPageAsText.contains(requiredField.getValue()));
		}
	}
	
	@Test
	public void testLookup() throws Exception {
		HtmlPage lookupPage = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), lookupUrl);
		assertNotNull("lookup page is null", lookupPage);
		
		lookupPage = HtmlUnitUtil.clickInputContainingText(lookupPage, "search");
		assertNotNull("lookup result page is null", lookupPage);
		
	}
	
	@Test
	public void dummyTest() throws Exception {
		Assert.assertNull(null);
	}
}
