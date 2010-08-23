package org.kuali.hr.time.workschedule;

 

 

import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.kuali.hr.time.TimeCollectionRule;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.rice.kns.service.KNSServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class WorkScheduleMaintTest extends TkTestCase{
	private final static String TEST_DESC="_desc";	
	private final static Long TEST_ID_LONG=1L;
	private final static java.sql.Date TEST_DATE=new java.sql.Date(Calendar.getInstance().getTimeInMillis());
	private static Long workScheduleId;
	@Test
	public void testWorkScheduleMaint() throws Exception {		
		HtmlPage workScheduleLookup = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.WORK_SCHEDULE_MAINT_URL);
		workScheduleLookup = HtmlUnitUtil.clickInputContainingText(workScheduleLookup, "search");
		assertTrue("Page contains test workSchedule", workScheduleLookup.asText().contains(TEST_DESC));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(workScheduleLookup, "edit",workScheduleId.toString());
		assertTrue("Maintenance Page contains test workSchedule",maintPage.asText().contains(TEST_DESC));	 		
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		WorkSchedule workSchedule = new WorkSchedule();
		workSchedule.setActive(true);
		workSchedule.setEffectiveDate(TEST_DATE);		
		workSchedule.setPrincipalId(TEST_ID_LONG);
		workSchedule.setWorkScheduleDesc(TEST_DESC);		
		workSchedule.setActive(true);
		//workSchedule.setWorkArea(1L);
		//workSchedule.setDepartmentId(1L);
		KNSServiceLocator.getBusinessObjectService().save(workSchedule);
		workScheduleId=workSchedule.getWorkScheduleId();
				
	}

	@Override
	public void tearDown() throws Exception {
		WorkSchedule workScheduleObj = KNSServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(WorkSchedule.class, workScheduleId);
		KNSServiceLocator.getBusinessObjectService().delete(workScheduleObj);
		super.tearDown();
	}
}
