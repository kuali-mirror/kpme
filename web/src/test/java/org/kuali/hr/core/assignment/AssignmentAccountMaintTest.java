/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.hr.core.assignment;

/**
//@FunctionalTest
public class AssignmentAccountMaintTest extends KPMEWebTestCase{
	private static final String TEST_CODE="CD";
	private static final String TEST_ID="1";
	private static final String TEST_ASSIGN_ID="23";
	private static String assignmentAccountId;
	private BigDecimal TEST_PERCENT =  new BigDecimal(1);
	
	@Test
	public void testAssignmentAccountMaint() throws Exception {	 
		HtmlPage assignmentAccountLookup = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), HrTestConstants.Urls.ASSIGNMENT_ACCOUNT_MAINT_URL);
		assignmentAccountLookup = HtmlUnitUtil.clickInputContainingText(assignmentAccountLookup, "search");
		Assert.assertTrue("Page contains test assignmentAccount", assignmentAccountLookup.asText().contains(TEST_ASSIGN_ID.toString()));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(assignmentAccountLookup, "edit",assignmentAccountId.toString());
		Assert.assertTrue("Maintenance Page contains test assignmentAccount",maintPage.asText().contains(TEST_ASSIGN_ID.toString()));	 
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		AssignmentAccount assignmentAccount = new AssignmentAccount();
		assignmentAccount.setAccountNbr(TEST_ID);
		assignmentAccount.setActive(true);
		assignmentAccount.setTkAssignmentId(TEST_ASSIGN_ID);
		assignmentAccount.setFinCoaCd(TEST_CODE);
		assignmentAccount.setFinObjectCd(TEST_CODE);
		assignmentAccount.setFinSubObjCd(TEST_CODE);
		assignmentAccount.setPercent(TEST_PERCENT);
        assignmentAccount.setUserPrincipalId("admin");
		KRADServiceLocator.getBusinessObjectService().save(assignmentAccount);
		assignmentAccountId = assignmentAccount.getTkAssignAcctId();
	}

	@Override
	public void tearDown() throws Exception {				
		AssignmentAccount assignmentAccountObj = KRADServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(AssignmentAccount.class, assignmentAccountId);		 
		KRADServiceLocator.getBusinessObjectService().delete(assignmentAccountObj);
		super.tearDown();
	}
	
}

*/
