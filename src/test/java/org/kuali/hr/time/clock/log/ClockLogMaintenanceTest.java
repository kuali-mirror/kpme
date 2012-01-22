package org.kuali.hr.time.clock.log;

import org.junit.Test;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;

import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class ClockLogMaintenanceTest extends TkTestCase{
	private static Long TEST_CODE_INVALID_TASK_ID =9999L;
	private static Long TEST_CODE_INVALID_WORK_AREA_ID =9999L;
	private static Long clockLogId = 1L;	
	
	@Test
	public void testClockLogMaint() throws Exception {
		HtmlPage clockLogLookUp = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.CLOCK_LOG_MAINT_URL);
		clockLogLookUp = HtmlUnitUtil.clickInputContainingText(clockLogLookUp, "search");
		assertTrue("Page contains test ClockLog", clockLogLookUp.asText().contains("TEST"));		
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(clockLogLookUp, "edit",clockLogId.toString());		
		assertTrue("Maintenance Page contains test ClockLog",maintPage.asText().contains("TEST"));		
	}
	
	@Test
	public void testClockLogMaintForErrorMessages() throws Exception {
		HtmlPage clockLogLookUp = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.CLOCK_LOG_MAINT_URL);
		clockLogLookUp = HtmlUnitUtil.clickInputContainingText(clockLogLookUp, "search");
		assertTrue("Page contains test ClockLog", clockLogLookUp.asText().contains("TEST"));		
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(clockLogLookUp, "edit",clockLogId.toString());		
		assertTrue("Maintenance Page contains test ClockLog",maintPage.asText().contains("TEST"));
		
		HtmlInput inputForDescription = HtmlUnitUtil.getInputContainingText(
				maintPage, "* Document Description");
		inputForDescription.setValueAttribute("Test_Description");
		setFieldValue(maintPage, "document.newMaintainableObject.workArea", TEST_CODE_INVALID_WORK_AREA_ID.toString());
		setFieldValue(maintPage, "document.newMaintainableObject.task", TEST_CODE_INVALID_TASK_ID.toString());
		HtmlPage resultantPageAfterEdit = HtmlUnitUtil
				.clickInputContainingText(maintPage, "submit");
		HtmlUnitUtil.createTempFile(resultantPageAfterEdit);
		
		assertTrue("Maintenance Page contains test Workarea ",
				resultantPageAfterEdit.asText().contains(
						"The specified Workarea '"
								+ TEST_CODE_INVALID_WORK_AREA_ID
								+ "' does not exist."));
		
		HtmlUnitUtil.createTempFile(resultantPageAfterEdit);
		
		assertTrue("Maintenance Page contains test Task ",
				resultantPageAfterEdit.asText().contains(
						"The specified Task '"
								+ TEST_CODE_INVALID_TASK_ID
								+ "' does not exist."));
		
		
	}
	
}