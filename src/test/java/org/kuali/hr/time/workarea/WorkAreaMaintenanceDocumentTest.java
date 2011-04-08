package org.kuali.hr.time.workarea;

import java.util.Collection;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.rice.kns.service.KNSServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlFileInput;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlHiddenInput;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class WorkAreaMaintenanceDocumentTest extends TkTestCase {
	
	final String ERROR_MESSAGE = "At least one active role must be defined.";
	final String SUCCESS_MESSAGE = "Document was successfully submitted.";
    @Before
    public void setUp() throws Exception {
    	super.setUp();
    }
    

    @Test
    public void testCreateNew() throws Exception {
    	String baseUrl = HtmlUnitUtil.getBaseURL() + "/kr/maintenance.do?businessObjectClassName=org.kuali.hr.time.workarea.WorkArea&methodToCall=start#topOfForm";
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
        
        setFieldValue(page, "document.newMaintainableObject.add.roles.principalId", "admin");
        setFieldValue(page, "document.newMaintainableObject.add.roles.active", "on");

        element = page.getElementByName("methodToCall.addLine.roles.(!!org.kuali.hr.time.roles.TkRole!!).(:::;3;:::).anchor3");
        nextPage = element.click();
        assertFalse("page text:\n" + nextPage.asText() + "\n contains:\n" + ERROR_MESSAGE, nextPage.asText().contains(ERROR_MESSAGE));
            	
    	form = nextPage.getFormByName("KualiForm");
    	assertNotNull("Search form was missing from page.", form);
    	
    	input  = HtmlUnitUtil.getInputContainingText(form, "methodToCall.route");
    	assertNotNull("Could not locate submit button", input);
        
        element = nextPage.getElementByName("methodToCall.route");
        HtmlPage lastPage = element.click();
        assertFalse("page text:\n" + lastPage.asText() + "\n contains:\n" + ERROR_MESSAGE, lastPage.asText().contains(ERROR_MESSAGE));
        assertTrue("page text:\n" + lastPage.asText() + "\n does not contains:\n" + SUCCESS_MESSAGE, lastPage.asText().contains(SUCCESS_MESSAGE));
        String message = "WorkArea: 	 " + this.maxWorkArea().toString();
        assertTrue("page text:\n" + lastPage.asText() + "\n does not contains:\n" + message, lastPage.asText().contains(message));
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
    
    
    protected final void setFieldValue(HtmlPage page, String fieldId, String fieldValue) {
        HtmlElement element = page.getHtmlElementById(fieldId);
        assertTrue("element " + fieldId + " is null, page: " + page.asText(), element != null);

        if (element instanceof HtmlTextInput) {
            HtmlTextInput textField = (HtmlTextInput) element;
            textField.setValueAttribute(fieldValue);
        }
        else if (element instanceof HtmlTextArea) {
            HtmlTextArea textAreaField = (HtmlTextArea) element;
            textAreaField.setText(fieldValue);
        }
        else if (element instanceof HtmlHiddenInput) {
            HtmlHiddenInput hiddenField = (HtmlHiddenInput) element;
            hiddenField.setValueAttribute(fieldValue);
        }
        else if (element instanceof HtmlSelect) {
            HtmlSelect selectField = (HtmlSelect) element;
            try {
                selectField.setSelectedAttribute(fieldValue, true);
            } catch (IllegalArgumentException e) {
                Assert.fail("select element [" + element.asText() + "] " + e.getMessage());
            }
        }
        else if (element instanceof HtmlCheckBoxInput) {
            HtmlCheckBoxInput checkboxField = (HtmlCheckBoxInput) element;
            if (fieldValue.equals("on")) {
                checkboxField.setChecked(true);
            }
            else if (fieldValue.equals("off")) {
                checkboxField.setChecked(false);
            }
            else {
                assertTrue("Invalid checkbox value", false);
            }
        }
        else if (element instanceof HtmlFileInput) {
            HtmlFileInput fileInputField = (HtmlFileInput) element;
            fileInputField.setValueAttribute(fieldValue);
        }
        else {
            fail("Unknown control field: " + fieldId);
        }
    }
	
}