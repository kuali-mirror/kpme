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
package org.kuali.hr.core.workarea;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.hr.core.HrTestConstants;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.kpme.core.department.Department;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.core.workarea.WorkArea;
import org.kuali.rice.krad.service.KRADServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class WorkAreaMaintenanceDocumentTest extends KPMETestCase {
	
	final String ERROR_MESSAGE = "At least one active role must be defined.";
	final String ERROR_ROLE_MESSAGE = "Cannot define both Principal Id and Position Number for Role.";
	final String SUCCESS_MESSAGE = "Document was successfully submitted.";
	private static final String TEST_CODE_DEPARTMENT_VALID = "_TEST";
	private static final DateTime TEST_DATE = new DateTime(2011,1,1,1,0,0,0,TKUtils.getSystemDateTimeZone());

    @Before
    public void setUp() throws Exception {
    	super.setUp();
    	Department department = new Department();
		department.setDept(TEST_CODE_DEPARTMENT_VALID);
		department.setChart(TEST_CODE_DEPARTMENT_VALID);
		department.setDescription(TEST_CODE_DEPARTMENT_VALID);
		department.setOrg(TEST_CODE_DEPARTMENT_VALID);
		department.setLocation("BL");
		department.setEffectiveLocalDate(TEST_DATE.toLocalDate());
		department.setActive(true);
		KRADServiceLocator.getBusinessObjectService().save(department);
    }
    

    @Test
    public void testCreateNew() throws Exception {
    	String baseUrl = HrTestConstants.Urls.WORK_AREA_MAINT_NEW_URL;
        Long workArea = this.maxWorkArea()+1;
    	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), baseUrl);
    	Assert.assertNotNull(page);
   
    	HtmlForm form = page.getFormByName("KualiForm");
    	Assert.assertNotNull("Search form was missing from page.", form);
    	
    	HtmlInput  input  = HtmlUnitUtil.getInputContainingText(form, "methodToCall.route");
    	Assert.assertNotNull("Could not locate submit button", input);
    	
        setFieldValue(page, "document.documentHeader.documentDescription", "Work Area - test");
        setFieldValue(page, "document.newMaintainableObject.effectiveDate", "4/01/2011");
        setFieldValue(page, "document.newMaintainableObject.active", "on");
        setFieldValue(page, "document.newMaintainableObject.description", "test");
        setFieldValue(page, "document.newMaintainableObject.dept", TEST_CODE_DEPARTMENT_VALID);
        setFieldValue(page, "document.newMaintainableObject.adminDescr", "TEST");
        setFieldValue(page, "document.newMaintainableObject.overtimeEditRole", "Approver");
        
        HtmlElement element = page.getElementByName("methodToCall.route");
        HtmlPage nextPage = element.click();
        Assert.assertTrue("page does not contain:\n" + ERROR_MESSAGE, nextPage.asText().contains(ERROR_MESSAGE));
        
        setFieldValue(page, "document.newMaintainableObject.add.principalRoleMembers.effectiveDate", "04/01/2011");
        setFieldValue(page, "document.newMaintainableObject.add.principalRoleMembers.principalId", "admin");

        element = HtmlUnitUtil.getInputContainingText(page,"methodToCall.addLine.principalRoleMembers");
        nextPage = element.click();
            	
    	form = nextPage.getFormByName("KualiForm");
    	Assert.assertNotNull("Search form was missing from page.", form);
    	
    	input  = HtmlUnitUtil.getInputContainingText(form, "methodToCall.route");
    	Assert.assertNotNull("Could not locate submit button", input);
        //work area should be saved successfully and work area field should be populated with the max work area from db
        element = nextPage.getElementByName("methodToCall.route");
        HtmlPage lastPage = element.click();
        Assert.assertFalse("page contains:\n" + ERROR_MESSAGE, lastPage.asText().contains(ERROR_MESSAGE));
        Assert.assertTrue("page does not contains:\n" + SUCCESS_MESSAGE, lastPage.asText().contains(SUCCESS_MESSAGE));
        Assert.assertTrue("page does not contains:\n" + workArea.toString(), lastPage.asText().contains(workArea.toString()));
        
        // search page should find the new work area
        HtmlPage searchPage = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), HrTestConstants.Urls.WORK_AREA_MAINT_URL);
        searchPage = HtmlUnitUtil.clickInputContainingText(searchPage, "search");
        Assert.assertTrue("Page contains test Earn Code", searchPage.asText().contains(workArea.toString()));
		
		DateTime aDate = new DateTime(2011, 5, 1, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone());
		WorkArea wa = HrServiceLocator.getWorkAreaService().getWorkArea(workArea, aDate.toLocalDate());
		String workAreaId = wa.getTkWorkAreaId().toString();
		
		// when open the new work area, role should be show up
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(searchPage, "edit", workAreaId);
		HtmlElement e = maintPage.getHtmlElementById("document.oldMaintainableObject.principalRoleMembers[0].effectiveDate.div");
		Assert.assertNotNull("Maintenance Page does not contain role ", e);
    }
    
	public Long maxWorkArea() {
		return HrServiceLocator.getWorkAreaService().getNextWorkAreaKey();

	}
	
	@Override
	public void tearDown() throws Exception {
		Department deptObj = HrServiceLocator.getDepartmentService().getDepartment(TEST_CODE_DEPARTMENT_VALID, LocalDate.now());
		KRADServiceLocator.getBusinessObjectService().delete(deptObj);
		super.tearDown();
	}
}