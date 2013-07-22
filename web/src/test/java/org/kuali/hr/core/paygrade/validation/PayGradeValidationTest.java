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
package org.kuali.hr.core.paygrade.validation;

import java.util.List;

import junit.framework.Assert;

import org.apache.commons.lang.StringUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.hr.KPMEWebTestCase;
import org.kuali.hr.util.HtmlUnitUtil;
import org.kuali.kpme.core.FunctionalTest;
import org.kuali.kpme.core.util.HrTestConstants;

import com.gargoylesoftware.htmlunit.html.FrameWindow;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

@FunctionalTest
public class PayGradeValidationTest extends KPMEWebTestCase {
	@Ignore
	@Test
	public void testValidateSalGroup() throws Exception {
        String baseUrl = HrTestConstants.Urls.PAY_GRADE_MAINT_NEW_URL;
        HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), HrTestConstants.BASE_URL, true);
        page = HtmlUnitUtil.clickAnchorContainingText(page,"Maintenance");
        HtmlUnitUtil.createTempFile(page);
        page = HtmlUnitUtil.clickAnchorContainingText(page,"Pay Grade");
        HtmlUnitUtil.createTempFile(page);
        List<HtmlAnchor> anchors = page.getAnchors();
        List<FrameWindow> frames = page.getFrames();
        for (FrameWindow frame : frames) {
            if (StringUtils.equals(frame.getName(), "iframeportlet")) {
               page = frame.getEnclosingPage();
            }
        }
        page.initialize();
        page = HtmlUnitUtil.clickAnchorContainingText(page,"Create");
        
        HtmlUnitUtil.createTempFile(page);
        page.initialize();
        Assert.assertNotNull(page);
        frames = page.getFrames();
        for (FrameWindow frame : frames) {
            if (StringUtils.equals(frame.getName(), "iframeportlet")) {
               page = frame.getEnclosingPage();
            }
        }
        HtmlForm form = (HtmlForm) page.getElementById("kualiForm");
//        List<HtmlForm> forms = page.getDocumentElement().getElementsByAttribute("form", "id", "kualiForm");
//            if (forms.size() == 0) {
//                throw new ElementNotFoundException("form", "id", "kualiForm");
//                } else {
//                form = forms.get(0);
//            }
        Assert.assertNotNull("Search form was missing from page.", form);
	  	
	  	HtmlUnitUtil.setFieldValue(page, "document.documentHeader.documentDescription", "Pay Grade - test");
	    HtmlUnitUtil.setFieldValue(page, "document.newMaintainableObject.effectiveDate", "04/01/2012");
	    HtmlUnitUtil.setFieldValue(page, "document.newMaintainableObject.payGrade", "test");
	    HtmlUnitUtil.setFieldValue(page, "document.newMaintainableObject.salGroup", "testSG");	//nonexisting salary group
	    HtmlUnitUtil.setFieldValue(page, "document.newMaintainableObject.description", "test");	
	  	
	  	HtmlInput  input  = HtmlUnitUtil.getInputContainingText(form, "methodToCall.route");
	  	Assert.assertNotNull("Could not locate submit button", input);
	  	HtmlElement element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	  	HtmlUnitUtil.createTempFile(page);
	  	Assert.assertTrue("page text contains:\n" + "'testSG' does not exist", page.asText().contains("'testSG' does not exist"));
	  	
	  	HtmlUnitUtil.setFieldValue(page, "document.newMaintainableObject.salGroup", "SD1");	//existing salary group
	  	element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	  	Assert.assertFalse("page text contains: error", page.asText().contains("error"));
	  	Assert.assertTrue("New Pay grade successfully submitted.", page.asText().contains("Document was successfully submitted"));
	}
	
}
