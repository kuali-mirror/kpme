package org.kuali.hr.time.overtime.daily.rule.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.overtime.daily.rule.DailyOvertimeRule;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.TkTestUtils;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.util.TkTimeBlockAggregate;

public class DailyOvertimeRuleServiceTest extends KPMETestCase {

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
		rule.setMaxGap(maxGap);
		rule.setMinHours(minHours);
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
		CalendarEntries payCalendarEntry = TkServiceLocator.getCalendarService().getCurrentCalendarDates("admin", new Date(start.getMillis()));
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
				task, new BigDecimal(8), new BigDecimal("15.0"), null);

		// Create Time Blocks (2 days, 2 blocks on each day, 15 minute gap between blocks, 4 hours first block, 5 the next.
		// Should end up with 2 hours total OVT.
		DateTime start = new DateTime(2010, 3, 29, 14, 0, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE);
		List<TimeBlock> blocks = new ArrayList<TimeBlock>();
		CalendarEntries payCalendarEntry = TkServiceLocator.getCalendarService().getCurrentCalendarDates("admin", new Date(start.getMillis()));
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
		DailyOvertimeRule rule = TkServiceLocator.getDailyOvertimeRuleService().getDailyOvertimeRule("SD1", "BW", "TEST-DEPT", workArea, JAN_AS_OF_DATE);
		Assert.assertNotNull("Rule not created.", rule);
	}

}
