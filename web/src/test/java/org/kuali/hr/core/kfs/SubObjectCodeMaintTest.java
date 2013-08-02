/**
 * Copyright 2004-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.hr.core.kfs;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.hr.KPMEWebTestCase;
import org.kuali.hr.util.HtmlUnitUtil;
import org.kuali.kpme.core.kfs.coa.businessobject.SubObjectCode;
import org.kuali.kpme.core.util.HrTestConstants;
import org.kuali.rice.krad.service.KRADServiceLocator;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class SubObjectCodeMaintTest extends KPMEWebTestCase {

	private static final String NEW_MAINT_DOC_PREFIX = "document.newMaintainableObject.";
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
		requiredFields.clear();
		// TODO Auto-generated method stub
		super.tearDown();
	}
	
	private void before() {
		
		newUrl = HrTestConstants.Urls.SUB_OBJECT_CODE_MAINT_NEW_URL;
		lookupUrl = HrTestConstants.Urls.SUB_OBJECT_CODE_MAINT_URL;
		
		requiredFields = new HashMap<String,String>();
		requiredFields.put("universityFiscalYear", "University Fiscal Year (Year) is a required field.");
		requiredFields.put("chartOfAccountsCode", "Chart Code (Chart) is a required field.");
		requiredFields.put("accountNumber", "Account Number (Account Number) is a required field.");
		requiredFields.put("financialObjectCode", "Object Code (Object) is a required field.");
		requiredFields.put("financialSubObjectCode", "Sub-Object Code (Sub-Object) is a required field.");
		requiredFields.put("financialSubObjectCodeName", "Sub-Object Code Name (SubObjCodeName) is a required field.");
		requiredFields.put("financialSubObjectCdshortNm", "Sub-Object Code Short Name (SubObjCodeShortName) is a required field.");
	}
	
	private void setDefaultTestInputValues() {
		requiredFields = new HashMap<String,String>();
		requiredFields.put("active", "on");
		requiredFields.put("universityFiscalYear", "2013");
		requiredFields.put("chartOfAccountsCode", "UA");
		requiredFields.put("accountNumber", "1111");
		requiredFields.put("financialObjectCode", "1000");
		requiredFields.put("financialSubObjectCode", "10");
		requiredFields.put("financialSubObjectCodeName", "Test Sub Obj");
		requiredFields.put("financialSubObjectCdshortNm", "Sub Obj TST");
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
		assertTrue("lookup page should contain 'UA Sub Obj'", lookupPage.asText().contains("UA Sub Obj"));
	}
	
	@Test
	public void testInvalidChartConsistencyCaseOne() throws Exception {
		/**
		 * TODO: submit sub-object code whose account COA code does not
		 * match the COA specified on this sub-object
		 */
		HtmlPage maintPage = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), newUrl);
		assertNotNull("maintenance page is null", maintPage);
		
		HtmlInput docDescription = HtmlUnitUtil.getInputContainingText(maintPage, "* Document Description");
		assertNotNull("maintenance page does not contain document description", docDescription);

		setDefaultTestInputValues();
		for(Entry<String,String> entry : requiredFields.entrySet()) {
			HtmlUnitUtil.setFieldValue(maintPage, NEW_MAINT_DOC_PREFIX + entry.getKey(), entry.getValue());
		}

		docDescription.setValueAttribute("testing submission");
		
		// this account's COA Code is "UA"
		HtmlUnitUtil.setFieldValue(maintPage, NEW_MAINT_DOC_PREFIX + "accountNumber", "9999");
		// reset requiredFields map to default error messages.
		before();
		HtmlPage resultPage = HtmlUnitUtil.clickInputContainingText(maintPage, "submit");
		assertNotNull("no result page returned after submit", resultPage);
		
		String resultPageAsText = resultPage.asText();
		for(Entry<String,String> requiredField : requiredFields.entrySet()) {
			if(requiredField.getKey().equals("accountNumber")) {
				assertTrue("page does not contain error message for the invalid field: '" + requiredField.getKey() + "'",
						resultPageAsText.contains("No such active account exists whose chart matches 'UA'"));
			}
		}
	}
	
	@Test
	public void testInvalidChartConsistencyCaseTwo() throws Exception {
		/**
		 * TODO: submit sub-object code whose object code chart does not
		 * match the chart specified on the sub-object
		 */
		HtmlPage maintPage = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), newUrl);
		assertNotNull("maintenance page is null", maintPage);
		
		HtmlInput docDescription = HtmlUnitUtil.getInputContainingText(maintPage, "* Document Description");
		assertNotNull("maintenance page does not contain document description", docDescription);

		setDefaultTestInputValues();
		for(Entry<String,String> entry : requiredFields.entrySet()) {
			HtmlUnitUtil.setFieldValue(maintPage, NEW_MAINT_DOC_PREFIX + entry.getKey(), entry.getValue());
		}

		docDescription.setValueAttribute("testing submission");
		//HtmlUnitUtil.setFieldValue(maintPage, NEW_MAINT_DOC_PREFIX + "accountNumber", "1111");

		HtmlUnitUtil.setFieldValue(maintPage, NEW_MAINT_DOC_PREFIX + "financialObjectCode", "9000");
		// reset requiredFields map to default error messages.
		before();
		HtmlPage resultPage = HtmlUnitUtil.clickInputContainingText(maintPage, "submit");
		assertNotNull("no result page returned after submit", resultPage);
		
		String resultPageAsText = resultPage.asText();
		for(Entry<String,String> requiredField : requiredFields.entrySet()) {
			if(requiredField.getKey().equals("financialObjectCode")) {
				assertTrue("page does not contain error message for the invalid field: '" + requiredField.getKey() + "'",
						resultPageAsText.contains("No such active object code exists whose chart matches 'UA'"));
			}
		}
	}
	
	@Test
	public void testInvalidChartConsistencyCaseThree() throws Exception {
		/**
		 * TODO: submit sub-object code whose account chart and object code chart
		 * do not match the chart specified on the sub-object
		 */
		HtmlPage maintPage = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), newUrl);
		assertNotNull("maintenance page is null", maintPage);
		
		HtmlInput docDescription = HtmlUnitUtil.getInputContainingText(maintPage, "* Document Description");
		assertNotNull("maintenance page does not contain document description", docDescription);

		setDefaultTestInputValues();
		for(Entry<String,String> entry : requiredFields.entrySet()) {
			HtmlUnitUtil.setFieldValue(maintPage, NEW_MAINT_DOC_PREFIX + entry.getKey(), entry.getValue());
		}

		docDescription.setValueAttribute("testing submission");
		
		// this account's COA Code is "UA"
		HtmlUnitUtil.setFieldValue(maintPage, NEW_MAINT_DOC_PREFIX + "accountNumber", "9999");
		HtmlUnitUtil.setFieldValue(maintPage, NEW_MAINT_DOC_PREFIX + "financialObjectCode", "9000");
		// reset requiredFields map to default error messages.
		before();
		HtmlPage resultPage = HtmlUnitUtil.clickInputContainingText(maintPage, "submit");
		assertNotNull("no result page returned after submit", resultPage);
		
		String resultPageAsText = resultPage.asText();
		for(Entry<String,String> requiredField : requiredFields.entrySet()) {
			if(requiredField.getKey().equals("financialObjectCode")) {
				assertTrue("page does not contain error message for the invalid field: '" + requiredField.getKey() + "'",
						resultPageAsText.contains("No such active object code exists whose chart matches 'UA'"));
			}
			if(requiredField.getKey().equals("accountNumber")) {
				assertTrue("page does not contain error message for the invalid field: '" + requiredField.getKey() + "'",
						resultPageAsText.contains("No such active account exists whose chart matches 'UA'"));
			}
		}
	}
	
	@Test
	public void testValidChartConsistencyWithClosedAccount() throws Exception {
		/**
		 * TODO: submit sub-object code whose object COA and account COA codes
		 * match the COA specified on this sub-object, but the account is open.
		 * 
		 * This test was changed from asserting a successful submission to asserting a non-successful
		 * insertion. Test data was added that marked the account used in this test as closed. Validation
		 * fails for closed accounts.
		 * 
		 */
		HtmlPage maintPage = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), newUrl);
		assertNotNull("maintenance page is null", maintPage);
		
		HtmlInput docDescription = HtmlUnitUtil.getInputContainingText(maintPage, "* Document Description");
		assertNotNull("maintenance page does not contain document description", docDescription);
		
		setDefaultTestInputValues();
		for(Entry<String,String> entry : requiredFields.entrySet()) {
			HtmlUnitUtil.setFieldValue(maintPage, NEW_MAINT_DOC_PREFIX + entry.getKey(), entry.getValue());
		}
		docDescription.setValueAttribute("testing submission");
		// account 2222 has same chart as default object code, and the chart input value on this form.
		HtmlUnitUtil.setFieldValue(maintPage, NEW_MAINT_DOC_PREFIX + "accountNumber","2222");
		// primary key includes fin_sub_obj_cd
		HtmlUnitUtil.setFieldValue(maintPage, NEW_MAINT_DOC_PREFIX + "financialSubObjectCode", "20");
		HtmlUnitUtil.setFieldValue(maintPage, NEW_MAINT_DOC_PREFIX + "financialSubObjectCodeName", "TST Sub object code");
		HtmlUnitUtil.setFieldValue(maintPage, NEW_MAINT_DOC_PREFIX + "financialSubObjectCdshortNm", "TST SOC");

		HtmlPage resultPage = HtmlUnitUtil.clickInputContainingText(maintPage, "submit");
		assertTrue("page should not contain errors", resultPage.asText().contains("No such active account exists whose chart matches 'UA'"));
		
		Map<String,String> keys = new HashMap<String,String>();
		keys.put("universityFiscalYear", "2013");
		keys.put("chartOfAccountsCode", "UA");
		keys.put("accountNumber", "2222");
		keys.put("financialObjectCode", "1000");
		keys.put("financialSubObjectCode", "20");
		
/*		SubObjectCode subObjectCode = KRADServiceLocator.getBusinessObjectService().findByPrimaryKey(SubObjectCode.class, keys);
		assertNotNull("newly created sub-object code should exist", subObjectCode);
		//clean up after assertion.
		KRADServiceLocator.getBusinessObjectService().delete(subObjectCode);*/
	}

}
