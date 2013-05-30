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
package org.kuali.hr.time.overtime.daily.rule.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.time.test.TkTestUtils;
import org.kuali.kpme.core.KPMETestCase;
import org.kuali.kpme.core.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.time.rules.overtime.daily.DailyOvertimeRule;
import org.kuali.kpme.tklm.time.rules.overtime.daily.service.DailyOvertimeRuleService;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.kpme.tklm.time.util.TkTimeBlockAggregate;

public class DailyOvertimeRuleServiceTest extends KPMETestCase {

	public static final String USER_PRINCIPAL_ID = "admin";
	private DateTime JAN_AS_OF_DATE = new DateTime(2010, 1, 1, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone());


	private void createDailyOvertimeRule(String fromEarnGroup, String earnCode, String location, String paytype, String dept, Long workArea, Long task, BigDecimal minHours, BigDecimal maxGap, String overtimePref) {
		DailyOvertimeRuleService service = TkServiceLocator.getDailyOvertimeRuleService();
		DailyOvertimeRule rule = new DailyOvertimeRule();

		rule.setEffectiveLocalDate(JAN_AS_OF_DATE.toLocalDate());
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
		DateTime start = new DateTime(2010, 3, 29, 14, 0, 0, 0, TKUtils.getSystemDateTimeZone());
		List<TimeBlock> blocks = new ArrayList<TimeBlock>();
		CalendarEntry payCalendarEntry = HrServiceLocator.getCalendarService().getCurrentCalendarDates("admin", start);
		blocks.addAll(TkTestUtils.createUniformTimeBlocks(start, 2, new BigDecimal("4"), "REG", jobNumber, workArea, task));
		blocks.addAll(TkTestUtils.createUniformTimeBlocks(start.plusHours(4).plusMinutes(15), 2, new BigDecimal("5"), "REG", jobNumber, workArea, task));
		TkTimeBlockAggregate aggregate = new TkTimeBlockAggregate(blocks, payCalendarEntry);

		// Verify pre-Rule Run
		TkTestUtils.verifyAggregateHourSums("Pre-Check", new HashMap<String,BigDecimal>() {{put("OVT", BigDecimal.ZERO);put("REG", new BigDecimal(18));}},aggregate,2);

		// Run Rule
		TimesheetDocument tdoc = TkTestUtils.populateBlankTimesheetDocument(start);
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
		DateTime start = new DateTime(2010, 3, 29, 14, 0, 0, 0, TKUtils.getSystemDateTimeZone());
		List<TimeBlock> blocks = new ArrayList<TimeBlock>();
		CalendarEntry payCalendarEntry = HrServiceLocator.getCalendarService().getCurrentCalendarDates("admin", start);
		blocks.addAll(TkTestUtils.createUniformTimeBlocks(start, 2, new BigDecimal("4"), "REG", jobNumber, workArea, task));
		blocks.addAll(TkTestUtils.createUniformTimeBlocks(start.plusHours(4).plusMinutes(15), 2, new BigDecimal("5"), "REG", jobNumber, workArea, task));
		TkTimeBlockAggregate aggregate = new TkTimeBlockAggregate(blocks, payCalendarEntry);

		// Verify pre-Rule Run
		TkTestUtils.verifyAggregateHourSums("Pre-Check", new HashMap<String,BigDecimal>() {{put("OVT", BigDecimal.ZERO);put("REG", new BigDecimal(18));}},aggregate,2);

		// Run Rule
		TimesheetDocument tdoc = TkTestUtils.populateBlankTimesheetDocument(start);
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
		DailyOvertimeRule rule = TkServiceLocator.getDailyOvertimeRuleService().getDailyOvertimeRule("SD1", "BW", "TEST-DEPT", workArea, JAN_AS_OF_DATE.toLocalDate());
		Assert.assertNotNull("Rule not created.", rule);
	}
	
	@Test
	public void testSearchDailyOvertimeRules() throws Exception {
		createDailyOvertimeRule("REG", "OVT", "SD1", "BL", "TEST-DEPT", 30L, 30L, new BigDecimal(8), new BigDecimal("0.10"), null);
		createDailyOvertimeRule("REG", "OVT", "SD1", "BL", "TEST-DEPT5", 5555L, 30L, new BigDecimal(8), new BigDecimal("0.10"), null);
		
		List<DailyOvertimeRule> allResults = TkServiceLocator.getDailyOvertimeRuleService().getDailyOvertimeRules("admin", null, null, null, null, null, "Y", "N");
		Assert.assertEquals("Search returned the wrong number of results.", 2, allResults.size());
		
		List<DailyOvertimeRule> restrictedResults = TkServiceLocator.getDailyOvertimeRuleService().getDailyOvertimeRules("fran", null, null, null, null, null, "Y", "N");
		Assert.assertEquals("Search returned the wrong number of results.", 0, restrictedResults.size());
	}

}