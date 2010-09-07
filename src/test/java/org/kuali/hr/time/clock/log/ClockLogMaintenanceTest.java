package org.kuali.hr.time.clock.log;

import java.util.Calendar;
import org.junit.Test;
import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.rice.kns.service.KNSServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class ClockLogMaintenanceTest extends TkTestCase{
	private static final String TEST_CODE_ONE="TST";
	private static final String TEST_CODE_TWO="_";
	private static final Long TEST_ID=20L;		
	private static final java.sql.Timestamp TEST_TIMESTAMP=new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
	private static Long clockLogId;	
	@Test
	public void testClockLogMaint() throws Exception {
		HtmlPage clockLogLookUp = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.CLOCK_LOG_MAINT_URL);
		clockLogLookUp = HtmlUnitUtil.clickInputContainingText(clockLogLookUp, "search");
		assertTrue("Page contains test ClockLog", clockLogLookUp.asText().contains(TEST_CODE_ONE));		
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(clockLogLookUp, "edit",clockLogId.toString());		
		assertTrue("Maintenance Page contains test ClockLog",maintPage.asText().contains(TEST_CODE_ONE));		
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		ClockLog clocklog = new ClockLog();
		clocklog.setClockAction(TEST_CODE_TWO);
		clocklog.setClockTimestamp(TEST_TIMESTAMP);
		clocklog.setClockTimestampTimezone(TEST_CODE_ONE);
		clocklog.setIpAddress(TEST_CODE_ONE);
		clocklog.setJobNumber(TEST_ID);
		clocklog.setPrincipalId(TEST_CODE_ONE);
		clocklog.setTaskId(TEST_ID);
		clocklog.setTimestamp(TEST_TIMESTAMP);
		clocklog.setUserPrincipalId(TEST_CODE_ONE);
		clocklog.setWorkAreaId(TEST_ID);		
		KNSServiceLocator.getBusinessObjectService().save(clocklog);		
		clockLogId=clocklog.getClockLogId();	
	}

	@Override
	public void tearDown() throws Exception {		
		ClockLog clockLogObj= KNSServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(ClockLog.class, clockLogId);			
		KNSServiceLocator.getBusinessObjectService().delete(clockLogObj);				
		super.tearDown();
	}
}