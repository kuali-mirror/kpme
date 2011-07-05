package org.kuali.hr.time.workarea;

import java.sql.Date;
import java.util.Collection;
import java.util.Iterator;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kns.service.KNSServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class WorkAreaMaintenanceDocumentTest extends TkTestCase {
	
	final String ERROR_MESSAGE = "At least one active role must be defined.";
	final String SUCCESS_MESSAGE = "Document was successfully submitted.";

    @Before
    public void setUp() throws Exception {
    	super.setUp();
    }
    

    @Test
    public void testCreateNew() throws Exception {
    	String baseUrl = TkTestConstants.Urls.WORK_AREA_MAINT_NEW_URL;
    	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(baseUrl);
    	assertNotNull(page);
   
    	HtmlForm form = page.getFormByName("KualiForm");
    	assertNotNull("Search form was missing from page.", form);
    	
    	HtmlInput  input  = HtmlUnitUtil.getInputContainingText(form, "methodToCall.route");
    	assertNotNull("Could not locate submit button", input);
    	
        setFieldValue(page, "document.documentHeader.documentDescription", "Work Area - test");
        setFieldValue(page, "document.newMaintainableObject.effectiveDate", "4/01/2011");
        setFieldValue(page, "document.newMaintainableObject.active", "on");
        setFieldValue(page, "document.newMaintainableObject.description", "test");
        setFieldValue(page, "document.newMaintainableObject.dept", "NODEP");
        setFieldValue(page, "document.newMaintainableObject.adminDescr", "TEST");
        setFieldValue(page, "document.newMaintainableObject.overtimeEditRole", "TK_APPROVER");
        setFieldValue(page, "document.newMaintainableObject.defaultOvertimeEarnCode", "OVT");
        
        HtmlElement element = page.getElementByName("methodToCall.route");
        HtmlPage nextPage = element.click();
        assertTrue("page text:\n" + nextPage.asText() + "\n does not contain:\n" + ERROR_MESSAGE, nextPage.asText().contains(ERROR_MESSAGE));
        
        setFieldValue(page, "document.newMaintainableObject.add.roles.effectiveDate", "04/01/2011");
        setFieldValue(page, "document.newMaintainableObject.add.roles.principalId", "admin");
        setFieldValue(page, "document.newMaintainableObject.add.roles.active", "on");

        element = HtmlUnitUtil.getInputContainingText(page,"methodToCall.addLine.roles");
        nextPage = element.click();
        assertFalse("page text:\n" + nextPage.asText() + "\n contains:\n" + ERROR_MESSAGE, nextPage.asText().contains(ERROR_MESSAGE));
            	
    	form = nextPage.getFormByName("KualiForm");
    	assertNotNull("Search form was missing from page.", form);
    	
    	input  = HtmlUnitUtil.getInputContainingText(form, "methodToCall.route");
    	assertNotNull("Could not locate submit button", input);
        //work area should be saved successfully and work area field should be populated with the max work area from db
        element = nextPage.getElementByName("methodToCall.route");
        HtmlPage lastPage = element.click();
        assertFalse("page text:\n" + lastPage.asText() + "\n contains:\n" + ERROR_MESSAGE, lastPage.asText().contains(ERROR_MESSAGE));
        assertTrue("page text:\n" + lastPage.asText() + "\n does not contains:\n" + SUCCESS_MESSAGE, lastPage.asText().contains(SUCCESS_MESSAGE));
        Long workArea = this.maxWorkArea();
        assertTrue("page text:\n" + lastPage.asText() + "\n does not contains:\n" + workArea.toString(), lastPage.asText().contains("WorkArea: 	 " + workArea.toString()));
        
        // search page should find the new work area
        HtmlPage searchPage = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.WORK_AREA_MAINT_URL);
        searchPage = HtmlUnitUtil.clickInputContainingText(searchPage, "search");
		assertTrue("Page contains test Earn Code", searchPage.asText().contains(workArea.toString()));
		
		java.sql.Date aDate = new Date((new DateTime(2011, 5, 1, 0, 0, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE)).getMillis());
		WorkArea wa = TkServiceLocator.getWorkAreaService().getWorkArea(workArea, aDate);
		String workAreaId = wa.getTkWorkAreaId().toString();
		
		// when open the new work area, role should be show up
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(searchPage, "edit", workAreaId);
		HtmlElement e = maintPage.getHtmlElementById("document.oldMaintainableObject.roles[0].effectiveDate.div");
		assertNotNull("Maintenance Page does not contain role ", e);
    }
    
	public Long maxWorkArea() {
		Collection aCol = KNSServiceLocator.getBusinessObjectService().findAll(WorkArea.class);
		Long maxId = new Long(-1);
		Iterator<WorkArea> itr = aCol.iterator();
		while (itr.hasNext()) {
			WorkArea aWorkArea = itr.next();
			if(aWorkArea.getWorkArea() > maxId) {
				maxId = aWorkArea.getWorkArea();
			}
		}
		return maxId;
	}
}