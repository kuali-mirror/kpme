package org.kuali.hr.time.position;

import org.junit.Test;

import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestConstants;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlInput;

public class PositionTest extends TkTestCase {

	public static final String TEST_USER = "admin";
	
	
	@Test
	public void testPositionMaintWorkAreaSave() throws Exception {

		HtmlPage positionLookup = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.POSITION_MAINT_URL);
		HtmlPage resultPage = HtmlUnitUtil.clickInputContainingText(positionLookup, "search");
		
		//look up a work area
		HtmlPage positionMaintPage = HtmlUnitUtil.clickAnchorContainingText(resultPage, "edit", "2085");
		HtmlUnitUtil.createTempFile(positionMaintPage);
		assertTrue("Maintenance page contains:\n" + "Work area for Position 2085 is 1003", positionMaintPage.asText().contains("1003"));
		
		//submit a changed work area
	  	setFieldValue(positionMaintPage, "document.documentHeader.documentDescription", "Position workArea - test");
		HtmlInput workAreaText = HtmlUnitUtil.getInputContainingText(positionMaintPage, "document.newMaintainableObject.workArea");
		workAreaText.setValueAttribute("30");
		HtmlPage outputPage = HtmlUnitUtil.clickInputContainingText(positionMaintPage, "submit");
		HtmlUnitUtil.createTempFile(outputPage);
		assertTrue("Maintenance page text contains:\n" + "Document was successfully submitted", outputPage.asText().contains("Document was successfully submitted"));
		assertTrue("Maintenance page contains:\n" + "Work area changed to 30", outputPage.asText().contains("30"));
		
		//look up the changed work area
		HtmlPage fetchedPositionMaintPage = HtmlUnitUtil.clickAnchorContainingText(resultPage, "edit", "2085");
		HtmlUnitUtil.createTempFile(fetchedPositionMaintPage);
		assertTrue("Fetched maintenance page now contains:\n" + "Work area for Position 2085 is 30", fetchedPositionMaintPage.asText().contains("30"));
	}
	
	@Test
	public void testPositionMaintWorkAreaInquiry() throws Exception {
		
		HtmlPage positionLookup = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.POSITION_MAINT_URL);
		HtmlPage resultPage = HtmlUnitUtil.clickInputContainingText(positionLookup, "search");

		//test the work area inquiry from the position page note that this returns the first work area in tk_work_area_t in all cases for now -- see KPME-1219
		HtmlPage workAreaInquiryPage = HtmlUnitUtil.clickAnchorContainingText(resultPage, "workarea", "1009");
		HtmlUnitUtil.createTempFile(workAreaInquiryPage);
		assertTrue("Inquiry page text contains:\n" + "WorkArea Inquiry", workAreaInquiryPage.asText().contains("WorkArea Inquiry"));
		//first work area in tk-test -- see comment above for this test
		assertTrue("Inquiry page text contains:\n" + "First work area in tk-test", workAreaInquiryPage.asText().contains("30"));
		
	}
	
}