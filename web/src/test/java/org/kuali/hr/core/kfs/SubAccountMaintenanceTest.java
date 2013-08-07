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

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;
import org.kuali.hr.KPMEWebTestCase;
import org.kuali.hr.util.HtmlUnitUtil;
import org.kuali.kpme.core.kfs.coa.businessobject.SubAccount;
import org.kuali.kpme.core.util.HrTestConstants;
import org.kuali.rice.krad.service.KRADServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class SubAccountMaintenanceTest extends KPMEWebTestCase {
	
	private static final String NEW_MAINT_DOC_PREFIX = "document.newMaintainableObject.";
	private String newUrl;
	private String lookupUrl;
	private Map<String,String> requiredFields;

	private void setDefaultTestInputValues() {
		requiredFields = new HashMap<String,String>();
		requiredFields.put("active", "on");
		requiredFields.put("chartOfAccountsCode", "UA");
		requiredFields.put("subAccountNumber", "4444");
		requiredFields.put("subAccountName", "4444");
		requiredFields.put("accountNumber", "3333");
	}
	
	private void before() {
		
		newUrl = HrTestConstants.Urls.SUB_ACCOUNT_MAINT_NEW_URL;
		lookupUrl = HrTestConstants.Urls.SUB_ACCOUNT_MAINT_URL;
		
		requiredFields = new HashMap<String,String>();
		requiredFields.put("chartOfAccountsCode", "Chart Code (Chart) is a required field.");
		requiredFields.put("subAccountNumber", "Sub-Account Number (Sub-Account) is a required field.");
		requiredFields.put("subAccountName", "Sub-Account Name (Sub-Acct Name) is a required field.");
		requiredFields.put("accountNumber", "Account Number (Account Number) is a required field.");
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

		//TODO: add valid test data, verify returned data in lookup result page.
	}
	
	@Test
	public void testInValidChart() throws Exception {
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
		// use a non-existent chart
		HtmlUnitUtil.setFieldValue(maintPage, NEW_MAINT_DOC_PREFIX + "chartOfAccountsCode","BP");

		HtmlPage resultPage = HtmlUnitUtil.clickInputContainingText(maintPage, "submit");
		assertTrue("page should contain active account existence error", resultPage.asText().contains("No active chart exists for this code"));
	}
	
	@Test
	public void testValidChart() throws Exception {
		/**
		 * 
		 * Also tests chart consistency with valid account
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

		HtmlPage resultPage = HtmlUnitUtil.clickInputContainingText(maintPage, "submit");

		assertTrue("page should not contain errors", !resultPage.asText().contains("error(s)"));
		
		Map<String,String> keys = new HashMap<String,String>();
		keys.put("chartOfAccountsCode", "UA");
		keys.put("accountNumber", "3333");
		keys.put("subAccountNumber", "4444");
		
		SubAccount subAccount = (SubAccount) KRADServiceLocator.getBusinessObjectService().findByPrimaryKey(SubAccount.class, keys);
		assertNotNull("newly created sub-object code should exist", subAccount);
		//clean up after assertion.
		KRADServiceLocator.getBusinessObjectService().delete(subAccount);
	}
	
	@Test
	public void testInvalidAccount() throws Exception {

		HtmlPage maintPage = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), newUrl);
		assertNotNull("maintenance page is null", maintPage);
		
		HtmlInput docDescription = HtmlUnitUtil.getInputContainingText(maintPage, "* Document Description");
		assertNotNull("maintenance page does not contain document description", docDescription);
		
		setDefaultTestInputValues();
		for(Entry<String,String> entry : requiredFields.entrySet()) {
			HtmlUnitUtil.setFieldValue(maintPage, NEW_MAINT_DOC_PREFIX + entry.getKey(), entry.getValue());
		}
		docDescription.setValueAttribute("testing submission");
		
		HtmlUnitUtil.setFieldValue(maintPage, NEW_MAINT_DOC_PREFIX + "accountNumber", "9999");
		
		HtmlPage resultPage = HtmlUnitUtil.clickInputContainingText(maintPage, "submit");
		assertTrue("page should contain active account existence error", resultPage.asText().contains("No such active account exists whose chart matches 'UA'"));
	}
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		before();
	}
	
	@Override
	public void tearDown() throws Exception {
		after();
		super.tearDown();
	}
}
