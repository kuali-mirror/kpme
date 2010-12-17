package org.kuali.hr.time.overtime.daily.rule.service;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.hr.time.overtime.daily.rule.DailyOvertimeRule;
import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestUtils;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.util.TkTimeBlockAggregate;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DailyOvertimeRuleServiceTest extends TkTestCase {
	
	public static final String USER_PRINCIPAL_ID = "admin";
	private Date JAN_AS_OF_DATE = new Date((new DateTime(2010, 1, 1, 0, 0, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE)).getMillis());

	
	private void createDailyOvertimeRule(String fromEarnGroup, String earnCode, String location, String paytype, String dept, Long workArea, Long task, BigDecimal minHours, BigDecimal maxGap, String overtimePref) {
		DailyOvertimeRuleService service = TkServiceLocator.getDailyOvertimeRuleService();	
		DailyOvertimeRule rule = new DailyOvertimeRule();

		rule.setEffectiveDate(JAN_AS_OF_DATE);
		rule.setFromEarnGroup(fromEarnGroup);
		rule.setEarnCode(earnCode);
		rule.setLocation(location);
		rule.setPaytype(paytype);
		rule.setDept(dept);
		rule.setWorkArea(workArea);
		rule.setTask(task);
		rule.setMaxGap(maxGap);
		rule.setMinHours(minHours);
		rule.setOvertimePref(overtimePref);
		rule.setActive(true);
		
		service.saveOrUpdate(rule);
	}
	
	
	@SuppressWarnings("serial")
	@Test
	public void testDailyOvertimeGapExceeded() throws Exception {
		Long jobNumber = 30L;
		Long workArea = 30L;
		Long task = 30L;
		createDailyOvertimeRule("REG", "OVT", "SD1", "BW", "TEST-DEPT", workArea, 
				task, new BigDecimal(8), new BigDecimal("0.10"), null);
		
		// Create Time Blocks (2 days, 2 blocks on each day, 15 minute gap between blocks, 4 hours first block, 5 the next.
		// Should end up with 2 hours total OVT.
		DateTime start = new DateTime(2010, 3, 29, 14, 0, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE);
		List<TimeBlock> blocks = new ArrayList<TimeBlock>();
		PayCalendarEntries payCalendarEntry = TkServiceLocator.getPayCalendarSerivce().getCurrentPayCalendarDates("admin", new Date(start.getMillis()));
		blocks.addAll(TkTestUtils.createUniformTimeBlocks(start, 2, new BigDecimal("4"), "REG", jobNumber, workArea, task));
		blocks.addAll(TkTestUtils.createUniformTimeBlocks(start.plusHours(4).plusMinutes(15), 2, new BigDecimal("5"), "REG", jobNumber, workArea, task));
		TkTimeBlockAggregate aggregate = new TkTimeBlockAggregate(blocks, payCalendarEntry);
		
		// Verify pre-Rule Run
		TkTestUtils.verifyAggregateHourSums("Pre-Check", new HashMap<String,BigDecimal>() {{put("OVT", BigDecimal.ZERO);put("REG", new BigDecimal(18));}},aggregate,2);
		
		// Run Rule
		TimesheetDocument tdoc = TkTestUtils.populateBlankTimesheetDocument(new Date(start.getMillis()));
		tdoc.setTimeBlocks(blocks);
		TkServiceLocator.getDailyOvertimeRuleService().processDailyOvertimeRules(tdoc, aggregate);
		
		// Verify post-Rule Run
		TkTestUtils.verifyAggregateHourSums("Post Rules Check", new HashMap<String,BigDecimal>() {{put("OVT", new BigDecimal(0));put("REG", new BigDecimal(18));}},aggregate,2);		
	}
	

	@SuppressWarnings("serial")
	@Test
	public void testDailyOvertimeSimpleCase() throws Exception {
		Long jobNumber = 30L;
		Long workArea = 30L;
		Long task = 30L;
		createDailyOvertimeRule("REG", "OVT", "SD1", "BW", "TEST-DEPT", workArea, 
				task, new BigDecimal(8), new BigDecimal("0.25"), null);
		
		// Create Time Blocks (2 days, 2 blocks on each day, 15 minute gap between blocks, 4 hours first block, 5 the next.
		// Should end up with 2 hours total OVT.
		DateTime start = new DateTime(2010, 3, 29, 14, 0, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE);
		List<TimeBlock> blocks = new ArrayList<TimeBlock>();
		PayCalendarEntries payCalendarEntry = TkServiceLocator.getPayCalendarSerivce().getCurrentPayCalendarDates("admin", new Date(start.getMillis()));
		blocks.addAll(TkTestUtils.createUniformTimeBlocks(start, 2, new BigDecimal("4"), "REG", jobNumber, workArea, task));
		blocks.addAll(TkTestUtils.createUniformTimeBlocks(start.plusHours(4).plusMinutes(15), 2, new BigDecimal("5"), "REG", jobNumber, workArea, task));
		TkTimeBlockAggregate aggregate = new TkTimeBlockAggregate(blocks, payCalendarEntry);
		
		// Verify pre-Rule Run
		TkTestUtils.verifyAggregateHourSums("Pre-Check", new HashMap<String,BigDecimal>() {{put("OVT", BigDecimal.ZERO);put("REG", new BigDecimal(18));}},aggregate,2);
		
		// Run Rule
		TimesheetDocument tdoc = TkTestUtils.populateBlankTimesheetDocument(new Date(start.getMillis()));
		tdoc.setTimeBlocks(blocks);
		TkServiceLocator.getDailyOvertimeRuleService().processDailyOvertimeRules(tdoc, aggregate);
		
		// Verify post-Rule Run
		TkTestUtils.verifyAggregateHourSums("Post Rules Check", new HashMap<String,BigDecimal>() {{put("OVT", new BigDecimal(2));put("REG", new BigDecimal(16));}},aggregate,2);
	}
	
	@Test
	public void testRuleCreationAndRetrieval() throws Exception {
		Long workArea = 0L;
		Long task = 30L;
		createDailyOvertimeRule("REG", "OVT", "SD1", "BW", "TEST-DEPT", workArea, 
				task, new BigDecimal(8), new BigDecimal("0.25"), null);
		DailyOvertimeRule rule = TkServiceLocator.getDailyOvertimeRuleService().getDailyOvertimeRule("TEST-DEPT", workArea, task, JAN_AS_OF_DATE);
		assertNotNull("Rule not created.", rule);		
	}
	
	
	@Test
    @Ignore
	public void testGetailyOvertimeRules() throws Exception {
		DailyOvertimeRuleService doors = TkServiceLocator.getDailyOvertimeRuleService();
		String dept = null;
		Long ruleId = null;
		Long workArea = null;
		Long task = null;
		Date asOfDate = new Date((new DateTime(2010, 1, 1, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		DailyOvertimeRule rule = null;

		// 1: dept, workarea task
		dept = "TEST-DEPT";
		ruleId = 1L;
		workArea = 1234L;
		task = 0L;		
		rule = doors.getDailyOvertimeRule(dept, workArea, task, asOfDate);
		assertNotNull("Null list of rules", rule);
		assertEquals("ID of rule is incorrect.", ruleId.longValue(), rule.getTkDailyOvertimeRuleId().longValue());

		// 2: dept, workarea, -1 
		dept = "TEST-DEPT";
		ruleId = 2L;
		workArea = 1234L;
		task = -999L;		
		rule = doors.getDailyOvertimeRule(dept, workArea, task, asOfDate);
		assertNotNull("Null list of rules", rule);
		assertEquals("ID of rule is incorrect.", ruleId.longValue(), rule.getTkDailyOvertimeRuleId().longValue());

		// 3: dept, -1, task
		dept = "TEST-DEPT";
		ruleId = 3L;
		workArea = -999L;
		task = 0L;		
		rule = doors.getDailyOvertimeRule(dept, workArea, task, asOfDate);
		assertNotNull("Null list of rules", rule);
		assertEquals("ID of rule is incorrect.", ruleId.longValue(), rule.getTkDailyOvertimeRuleId().longValue());

		// 4: dept, -1, -1
		dept = "TEST-DEPT";
		ruleId = 4L;
		workArea = -999L;
		task = -999L;		
		rule = doors.getDailyOvertimeRule(dept, workArea, task, asOfDate);
		assertNotNull("Null list of rules", rule);
	}
	
}
