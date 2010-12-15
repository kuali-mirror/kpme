package org.kuali.hr.time.clock.log;

import java.util.Calendar;
import java.util.Random;

import org.apache.ojb.broker.PersistenceBrokerFactory;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.junit.Test;
import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.task.Task;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kns.service.KNSServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class ClockLogMaintenanceTest extends TkTestCase{
	//TODO - sai remove the random and use the hard coded data in the bootstrap(add if no clock logs are present)
	
	//private static final String TEST_CODE_ONE="TST";
	//private static final String TEST_CODE_TWO="_";
	//private static final Long TEST_ID=20L;		
	//private static final java.sql.Timestamp TEST_TIMESTAMP=new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
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
		HtmlPage resultantPageAfterEdit = HtmlUnitUtil
				.clickInputContainingText(maintPage, "submit");
		
		
		assertTrue("Maintenance Page contains test Workarea ",
				resultantPageAfterEdit.asText().contains(
						"The specified Workarea '"
								+ TEST_CODE_INVALID_WORK_AREA_ID
								+ "' does not exist."));
		
		assertTrue("Maintenance Page contains test Task ",
				resultantPageAfterEdit.asText().contains(
						"The specified Task '"
								+ TEST_CODE_INVALID_TASK_ID
								+ "' does not exist."));
		
		
	}
	
}