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

public class WorkScheduleEntryMaintTest extends TkTestCase{
 
	private static final Long TEST_ID_LONG=15L;
	private static Long workScheduleEntryId;
	
	@Test
	public void testWorkScheduleEntryMaint() throws Exception {	 
		HtmlPage workScheduleEntryLookup = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.WORK_SCHEDULE_ENTRY_MAINT_URL);
		workScheduleEntryLookup = HtmlUnitUtil.clickInputContainingText(workScheduleEntryLookup, "search");
		assertTrue("Page contains test workScheduleEntry", workScheduleEntryLookup.asText().contains(TEST_ID_LONG.toString()));		
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(workScheduleEntryLookup, "edit",workScheduleEntryId.toString());		
		assertTrue("Maintenance Page contains test workScheduleEntry",maintPage.asText().contains(TEST_ID_LONG.toString()));		
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		WorkScheduleEntry workScheduleEntry = new WorkScheduleEntry();
		workScheduleEntry.setCalDayId(TEST_ID_LONG);
		workScheduleEntry.setDayOfPeriodId(TEST_ID_LONG);
		workScheduleEntry.setRegHours(TEST_ID_LONG);
		workScheduleEntry.setWorkScheduleId(TEST_ID_LONG);
		KNSServiceLocator.getBusinessObjectService().save(workScheduleEntry);
		workScheduleEntryId=workScheduleEntry.getWorkScheduleEntryId();
	}

	@Override
	public void tearDown() throws Exception {		
		WorkScheduleEntry workScheduleEntryObj = KNSServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(WorkScheduleEntry.class, workScheduleEntryId);
		KNSServiceLocator.getBusinessObjectService().delete(workScheduleEntryObj);
		super.tearDown();
	}
}
