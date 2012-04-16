package org.kuali.hr.time.userrole;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.util.TkConstants;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;

public class UserRoleMaintenanceDocumentTest extends TkTestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

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
				"edit", "principalId=admin1");

		// set Description
		setFieldValue(editPage, "document.documentHeader.documentDescription",
				"Adding role to user admin1");

		// Click on Add and see errors
		HtmlElement elementAddRole = HtmlUnitUtil.getInputContainingText(
				editPage, "methodToCall.addLine.roles");
		editPage = elementAddRole.click();

		Assert.assertTrue(
				"page text:\n" + editPage.asText() + "\n does not contain:\n",
				editPage.asText().contains(
						"Effective Date (Effective Date) is a required field."));

		// Set Effective date and Role Name
		setFieldValue(editPage,
				"document.newMaintainableObject.add.roles.effectiveDate",
				"01/01/2011");
		setFieldValue(editPage,
				"document.newMaintainableObject.add.roles.roleName",
				TkConstants.ROLE_TK_SYS_ADMIN);

		elementAddRole = HtmlUnitUtil.getInputContainingText(editPage,
				"methodToCall.addLine.roles");

		// click on add
		editPage = elementAddRole.click();

		Assert.assertTrue("page text:\n" + editPage.asText()
				+ "\n does not contain:\n",
				!editPage.asText().contains("error(s) found on page."));

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

		// Add Location Admin/Location View Only Role.
		elementAddRole = HtmlUnitUtil.getInputContainingText(editPage,
				"methodToCall.addLine.roles");
		setFieldValue(editPage,
				"document.newMaintainableObject.add.roles.effectiveDate",
				"01/01/2011");
		setFieldValue(editPage,
				"document.newMaintainableObject.add.roles.roleName",
				TkConstants.ROLE_TK_LOCATION_ADMIN);

		editPage = elementAddRole.click();

		// as location is required in case of Location Admin/Location view only
		// roles
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

		// Delete Location Admin Role
		HtmlElement deleteElement = HtmlUnitUtil.getInputContainingText(
				editPage,
				"methodToCall.deleteLine.roles.(!!.line2.(:::;61;:::).anchor9");
		editPage = deleteElement.click();

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
				"principalId=admin1");

		// check if this page contains created roles System Admin and Global View Only
		HtmlSpan oldRoleEle = (HtmlSpan) editPage
				.getElementById("document.oldMaintainableObject.roles[0].roleName.div");
		Assert.assertTrue("page text:\n" + oldRoleEle.asText()
				+ "\n does not contain:\n",
				oldRoleEle.asText().contains("System Admin"));

		oldRoleEle = (HtmlSpan) editPage
				.getElementById("document.oldMaintainableObject.roles[1].roleName.div");
		Assert.assertTrue("page text:\n" + oldRoleEle.asText()
				+ "\n does not contain:\n",
				oldRoleEle.asText().contains("Global View Only"));

		// Now update
		setFieldValue(editPage, "document.documentHeader.documentDescription",
				"Inactivating department admin role");
		setFieldValue(editPage,
				"document.newMaintainableObject.roles[1].active", "off");

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
				"principalId=admin1");

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
