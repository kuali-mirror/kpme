package org.kuali.hr.time.workschedule.service;

import java.sql.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workschedule.WorkSchedule;
import org.kuali.hr.time.workschedule.WorkScheduleEntry;

public class WorkScheduleServiceTest extends TkTestCase {

	@Test
	@Ignore
	public void testGetWorkSchedules() throws Exception {
		WorkScheduleService wss = TkServiceLocator.getWorkScheduleService();
		String dept = null;
		String principalId = null;
		Long workScheduleId = null;
		Long workArea = null;
		Date asOfDate = new Date((new DateTime(2010, 1, 1, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		List<WorkSchedule> schedules = null;

		// principal, department, workarea
		workScheduleId = 1L;
		dept = "TEST-DEPT";
		principalId = "admin";
		workArea = 1234L;
		//schedules = wss.getWorkSchedules(principalId, dept, workArea, asOfDate);
		Assert.assertNotNull("Null return list", schedules);
		Assert.assertEquals("Wrong number of elements returned.", 1, schedules.size());
		Assert.assertEquals("Wrong ID returned", workScheduleId, (schedules.get(0)).getHrWorkScheduleId());
		// principal, department, -1
		workScheduleId = 2L;
		dept = "TEST-DEPT";
		principalId = "admin";
		workArea = -999L;
		//schedules = wss.getWorkSchedules(principalId, dept, workArea, asOfDate);
		Assert.assertNotNull("Null return list", schedules);
		Assert.assertEquals("Wrong number of elements returned.", 1, schedules.size());
		Assert.assertEquals("Wrong ID returned", workScheduleId, (schedules.get(0)).getHrWorkScheduleId());

		// principal, *, workarea
		workScheduleId = 3L;
		dept = "NOTFOUND";
		principalId = "admin";
		workArea = 1234L;
		//schedules = wss.getWorkSchedules(principalId, dept, workArea, asOfDate);
		Assert.assertNotNull("Null return list", schedules);
		Assert.assertEquals("Wrong number of elements returned.", 1, schedules.size());
		Assert.assertEquals("Wrong ID returned", workScheduleId, (schedules.get(0)).getHrWorkScheduleId());
	}

	@Test
	public void testWorkScheduleFlattening() throws Exception{
		WorkSchedule workSchedule = new WorkSchedule();
		Date asOfDate = new Date((new DateTime(2010, 1, 1, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		workSchedule.setEffectiveDate(asOfDate);
		WorkScheduleEntry wse = new WorkScheduleEntry();
		for(int i = 0 ;i<TkConstants.LENGTH_OF_WORK_SCHEDULE;i++){
			workSchedule.getWorkScheduleEntries().add(wse);
		}
		Date beginPeriodDate = new Date((new DateTime(2010, 1, 3, 1, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		Date endPeriodDate = new Date((new DateTime(2010, 1, 15, 1, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());

		List<WorkScheduleEntry> lstWorkSchedEntries = TkServiceLocator.getWorkScheduleService().getWorkSchedEntries(
															workSchedule, beginPeriodDate, endPeriodDate);

		Assert.assertTrue("verify work sched entries is correct", lstWorkSchedEntries!= null);
	}
}
