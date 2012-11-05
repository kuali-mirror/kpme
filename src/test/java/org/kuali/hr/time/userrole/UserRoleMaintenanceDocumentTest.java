/**
 * Copyright 2004-2012 The Kuali Foundation
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
package org.kuali.hr.time.userrole;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.util.TkConstants;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;

public class UserRoleMaintenanceDocumentTest extends KPMETestCase {
	
	private static final String PRINCIPAL_ID = "fred";

	@Test
	public void testUserRoleMaintenanceDocumentTest() throws Exception {
		String baseUrl = HtmlUnitUtil.getBaseURL()
				+ "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.roles.TkRoleGroup&returnLocation="
				+ HtmlUnitUtil.getBaseURL()
				+ "/portal.do&hideReturnLink=true&docFormKey=88888888";

		HtmlPage lookUpPage = HtmlUnitUtil.gotoPageAndLogin(baseUrl);
		Assert.assertNotNull(lookUpPage);

		lookUpPage = HtmlUnitUtil
				.clickInputContainingText(lookUpPage, "search");

		HtmlPage editPage = HtmlUnitUtil.clickAnchorContainingText(lookUpPage,
				"edit", "principalId=" + PRINCIPAL_ID);

		// set Description
		setFieldValue(editPage, "document.documentHeader.documentDescription",
				"Adding role to user" + PRINCIPAL_ID);

		// Click on Add and see errors
		HtmlElement elementAddRole = HtmlUnitUtil.getInputContainingText(
				editPage, "methodToCall.addLine.roles");
		editPage = elementAddRole.click();

		Assert.assertTrue(
				"page text:\n" + editPage.asText() + "\n does not contain:\n",
				editPage.asText().contains(
						"Effective Date (Effective Date) is a required field."));

		// Add Global View Only Role.
		elementAddRole = HtmlUnitUtil.getInputContainingText(editPage,
				"methodToCall.addLine.roles");
		setFieldValue(editPage,
				"document.newMaintainableObject.add.roles.effectiveDate",
				"01/01/2011");
		setFieldValue(editPage,
				"document.newMaintainableObject.add.roles.roleName",
				TkConstants.ROLE_TK_GLOBAL_VO);

		editPage = elementAddRole.click();

		Assert.assertTrue("page text:\n" + editPage.asText()
				+ "\n does not contain:\n",
				!editPage.asText().contains("error(s) found on page."));

		// Add Location Admin View Only Role.
		elementAddRole = HtmlUnitUtil.getInputContainingText(editPage,
				"methodToCall.addLine.roles");
		setFieldValue(editPage,
				"document.newMaintainableObject.add.roles.effectiveDate",
				"01/01/2011");
		setFieldValue(editPage,
				"document.newMaintainableObject.add.roles.roleName",
				TkConstants.ROLE_TK_LOCATION_VO);

		editPage = elementAddRole.click();

		// Location is required in case of Location View Only roles
		Assert.assertTrue("page text:\n" + editPage.asText()
				+ "\n does not contain:\n",
				editPage.asText().contains("error(s) found on page."));

		setFieldValue(editPage,
				"document.newMaintainableObject.add.roles.chart", "BL");

		elementAddRole = HtmlUnitUtil.getInputContainingText(editPage,
				"methodToCall.addLine.roles");
		editPage = elementAddRole.click();

		// page should not contain any errors
		Assert.assertTrue("page text:\n" + editPage.asText()
				+ "\n does not contain:\n",
				!editPage.asText().contains("error(s) found on page."));

		// submit
		HtmlElement submitElement = HtmlUnitUtil.getInputContainingText(
				editPage, "methodToCall.route");

		editPage = submitElement.click();
		Assert.assertTrue(
				"page text:\n" + editPage.asText() + "\n does not contain:\n",
				editPage.asText().contains(
						"Document was successfully submitted."));

		// again go to the url and do login
		lookUpPage = HtmlUnitUtil.gotoPageAndLogin(baseUrl);
		Assert.assertNotNull(lookUpPage);

		lookUpPage = HtmlUnitUtil
				.clickInputContainingText(lookUpPage, "search");
		editPage = HtmlUnitUtil.clickAnchorContainingText(lookUpPage, "edit",
				"principalId=" + PRINCIPAL_ID);

		// check if this page contains created role Global View Only
		HtmlSpan oldRoleEle = (HtmlSpan) editPage
				.getElementById("document.oldMaintainableObject.roles[0].roleName.div");
		Assert.assertTrue("page text:\n" + oldRoleEle.asText()
				+ "\n does not contain:\n",
				oldRoleEle.asText().contains("Global View Only"));

		// Now update
		setFieldValue(editPage, "document.documentHeader.documentDescription",
				"Inactivating global view only role");
		setFieldValue(editPage,
				"document.newMaintainableObject.roles[0].active", "off");

		submitElement = HtmlUnitUtil.getInputContainingText(editPage,
				"methodToCall.route");

		editPage = submitElement.click();

		Assert.assertTrue(
				"page text:\n" + editPage.asText() + "\n does not contain:\n",
				editPage.asText().contains(
						"Document was successfully submitted"));

		// again go through maintenance page and check if inactive roles tab contains the role Department Admin
		lookUpPage = HtmlUnitUtil.gotoPageAndLogin(baseUrl);
		Assert.assertNotNull(lookUpPage);

		lookUpPage = HtmlUnitUtil
				.clickInputContainingText(lookUpPage, "search");
		editPage = HtmlUnitUtil.clickAnchorContainingText(lookUpPage, "edit",
				"principalId=" + PRINCIPAL_ID);

		editPage = HtmlUnitUtil.clickInputContainingText(editPage,
				"methodToCall.toggleTab.tabInactivePersonRoles");

		// check if Global view only role is in inactive role list.
		HtmlSpan oldIARoleEle = (HtmlSpan) editPage
				.getElementById("document.oldMaintainableObject.inactiveRoles[0].roleName.div");
		Assert.assertTrue("Inactive Roles "+oldIARoleEle.asText() +" does not contains", oldIARoleEle.asText().contains("Global View Only"));

		oldIARoleEle = (HtmlSpan) editPage
				.getElementById("document.oldMaintainableObject.inactiveRoles[0].active.div");
		Assert.assertTrue("Inactive Roles "+oldIARoleEle.asText() +" does not contains", oldIARoleEle.asText().contains("No"));
	}

}
