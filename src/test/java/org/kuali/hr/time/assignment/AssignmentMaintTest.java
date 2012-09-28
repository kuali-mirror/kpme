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
package org.kuali.hr.time.assignment;


import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestConstants;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class AssignmentMaintTest extends KPMETestCase {
	
	//data defined in boot strap script
	private static final String TEST_CODE="admin";
	final String ERROR_EFF_DATE = "Effective Date (Effective Date) is a required field.";
	final String ERROR_PRINCIPAL_ID = "Principal Id (Principal Id) is a required field.";
	final String ERROR_JOB_NUMBER = "Job Number (Job Number) is a required field.";
	final String ERROR_WORK_AREA = "Work Area (Work Area) is a required field.";
	final String ERROR_TASK = "Task (Task) is a required field.";
	final String ERROR_TASK_NULL = "The specified task 'null' does not exist.";
	final String ERROR_JOB_NUMBER_NULL = "The specified jobNumber 'null' does not exist.";
	final String ERROR_JOB_NUMBER_INVALID = "The specified jobNumber '1' does not exist.";
	
	
	@Test
	public void testAssignmentMaint() throws Exception {
		HtmlPage assignmentLookUp = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.ASSIGNMENT_MAINT_URL);
		setFieldValue(assignmentLookUp, "workArea", "30");
		assignmentLookUp = HtmlUnitUtil.clickInputContainingText(assignmentLookUp, "search");
		Assert.assertTrue("Page contains test assignment", assignmentLookUp.asText().contains(TEST_CODE.toString()));
		HtmlUnitUtil.createTempFile(assignmentLookUp);
		
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(assignmentLookUp, "edit");
		HtmlUnitUtil.createTempFile(maintPage);
		Assert.assertTrue("Maintenance Page contains test assignment",maintPage.asText().contains(TEST_CODE.toString()));	
	}
	
	@Test
	public void testAssignmentCreateNew() throws Exception {
		
    	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.ASSIGNMENT_MAINT_NEW_URL);
    	Assert.assertNotNull(page);
    	HtmlForm form = page.getFormByName("KualiForm");
    	Assert.assertNotNull("Search form was missing from page.", form);
    	HtmlInput  input  = HtmlUnitUtil.getInputContainingText(form, "methodToCall.route");
    	Assert.assertNotNull("Could not locate submit button", input);
    	
    	setFieldValue(page, "document.documentHeader.documentDescription", "Assignment - test");
    	HtmlElement element = page.getElementByName("methodToCall.route");
        HtmlPage nextPage = element.click();
        Assert.assertTrue("pagedoes not contain: " + ERROR_EFF_DATE, nextPage.asText().contains(ERROR_EFF_DATE));
        Assert.assertTrue("page does not contain: " + ERROR_PRINCIPAL_ID, nextPage.asText().contains(ERROR_PRINCIPAL_ID));
        Assert.assertTrue("page does not contain: " + ERROR_JOB_NUMBER, nextPage.asText().contains(ERROR_JOB_NUMBER));
        Assert.assertTrue("page does not contain: " + ERROR_JOB_NUMBER_NULL, nextPage.asText().contains(ERROR_JOB_NUMBER_NULL));
        Assert.assertTrue("page does not contain: " + ERROR_WORK_AREA, nextPage.asText().contains(ERROR_WORK_AREA));
        // Task field is not required
        Assert.assertFalse("page contains: " + ERROR_TASK, nextPage.asText().contains(ERROR_TASK));
        // validating of task has been removed from AssignmentRule
        Assert.assertFalse("page contains: " + ERROR_TASK_NULL, nextPage.asText().contains(ERROR_TASK_NULL));
	}
	
	@Test
	public void testAssignmentCreateNewJobValidation() throws Exception {
		
		HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.ASSIGNMENT_MAINT_NEW_URL);
		Assert.assertNotNull(page);
		HtmlForm form = page.getFormByName("KualiForm");
		Assert.assertNotNull("Search form was missing from page.", form);
		HtmlInput  descriptionText  = HtmlUnitUtil.getInputContainingText(form, "document.documentHeader.documentDescription");
		Assert.assertNotNull("Could not locate submit button", descriptionText);
		descriptionText.setValueAttribute("Creating new assignment");
		HtmlInput  effDateText  = HtmlUnitUtil.getInputContainingText(form, "document.newMaintainableObject.effectiveDate");
		Assert.assertNotNull("Could not locate submit button", effDateText);
		effDateText.setValueAttribute("06/27/2011");		
		HtmlInput  principalText  = HtmlUnitUtil.getInputContainingText(form, "document.newMaintainableObject.principalId");
		Assert.assertNotNull("Could not locate submit button", principalText);
		principalText.setValueAttribute("10008");		
		HtmlInput  jobNumberText  = HtmlUnitUtil.getInputContainingText(form, "document.newMaintainableObject.jobNumber");
		Assert.assertNotNull("Could not locate submit button", jobNumberText);
		jobNumberText.setValueAttribute("1");		
		HtmlInput  workAreaText  = HtmlUnitUtil.getInputContainingText(form, "document.newMaintainableObject.workArea");
		Assert.assertNotNull("Could not locate submit button", workAreaText);
		workAreaText.setValueAttribute("1016");		
		HtmlInput  input  = HtmlUnitUtil.getInputContainingText(form, "methodToCall.route");
		Assert.assertNotNull("Could not locate submit button", input);
		HtmlUnitUtil.createTempFile(page);
		setFieldValue(page, "document.documentHeader.documentDescription", "Assignment - test");
		HtmlElement element = page.getElementByName("methodToCall.route");
		HtmlPage nextPage = element.click();
		HtmlUnitUtil.createTempFile(nextPage);
		Assert.assertTrue("pagedoes not contain: " + ERROR_JOB_NUMBER_INVALID, nextPage.asText().contains(ERROR_JOB_NUMBER_INVALID));
		
	}

}
