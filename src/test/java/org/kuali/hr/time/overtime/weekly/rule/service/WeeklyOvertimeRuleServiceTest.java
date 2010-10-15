package org.kuali.hr.time.overtime.weekly.rule.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.junit.Test;
import org.kuali.hr.time.earngroup.EarnGroup;
import org.kuali.hr.time.overtime.weekly.rule.WeeklyOvertimeRule;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestUtils;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.TimeHourDetail;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKUtils;

public class WeeklyOvertimeRuleServiceTest extends TkTestCase {
	
	private static Date DEFAULT_EFFDT = new Date((new DateTime(2010, 1, 1, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());

	@Test
	public void testGetWeekHourSum() throws Exception {
		WeeklyOvertimeRuleService wors_b = TkServiceLocator.getWeeklyOvertimeRuleService();
		assertTrue(wors_b instanceof WeeklyOvertimeRuleServiceImpl);
		WeeklyOvertimeRuleServiceImpl wors = (WeeklyOvertimeRuleServiceImpl)wors_b;
		// We don't care about the workArea / jobNumber
		Long workArea = 1L;
		Long jobNumber = 1L;
		
		Set<String> maxHoursEarnCodes = new HashSet<String>();
		maxHoursEarnCodes.add("T1");
		maxHoursEarnCodes.add("T2");
		List<TimeBlock> weekBlock = new ArrayList<TimeBlock>();
		
		BigDecimal sum = wors.getWeekHourSum(weekBlock, maxHoursEarnCodes);
		assertTrue(sum.equals(BigDecimal.ZERO));
		
		weekBlock.add(createDummyTimeBlock(workArea, jobNumber, BigDecimal.TEN, BigDecimal.ZERO, "EC", null, null));
		weekBlock.add(createDummyTimeBlock(workArea, jobNumber, BigDecimal.TEN, BigDecimal.ZERO, "EC", null, null));
		weekBlock.add(createDummyTimeBlock(workArea, jobNumber, BigDecimal.TEN, BigDecimal.ZERO, "T1", null, null));
		weekBlock.add(createDummyTimeBlock(workArea, jobNumber, BigDecimal.TEN, BigDecimal.ZERO, "T1", null, null));
		weekBlock.add(createDummyTimeBlock(workArea, jobNumber, BigDecimal.TEN, BigDecimal.ZERO, "T2", null, null));
		weekBlock.add(createDummyTimeBlock(workArea, jobNumber, BigDecimal.TEN, BigDecimal.ZERO, "T2", null, null));

		sum = wors.getWeekHourSum(weekBlock, maxHoursEarnCodes);
		assertTrue(sum.equals(new BigDecimal("40")));
	}
	
	@Test
	public void testGetTimeBlockWeekArrayList() throws Exception {
		WeeklyOvertimeRuleService wors_b = TkServiceLocator.getWeeklyOvertimeRuleService();
		assertTrue(wors_b instanceof WeeklyOvertimeRuleServiceImpl);
		WeeklyOvertimeRuleServiceImpl wors = (WeeklyOvertimeRuleServiceImpl)wors_b;		
		// We don't care about the workArea / jobNumber
		Long workArea = 1L;
		Long jobNumber = 1L;
		
		Timestamp in = null;
		Timestamp out = null;
		List<TimeBlock> timeBlocks = new ArrayList<TimeBlock>();
		// Week 1 Blocks
		in  = new Timestamp((new DateTime(2010, 1, 1, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		out = new Timestamp((new DateTime(2010, 1, 1, 15, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());		
		timeBlocks.add(createDummyTimeBlock(workArea, jobNumber, BigDecimal.TEN, BigDecimal.ZERO, "EC", in, out));		
		in  = new Timestamp((new DateTime(2010, 1, 2, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		out = new Timestamp((new DateTime(2010, 1, 2, 15, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		timeBlocks.add(createDummyTimeBlock(workArea, jobNumber, BigDecimal.TEN, BigDecimal.ZERO, "EC", in,out));
		in  = new Timestamp((new DateTime(2010, 1, 8, 10, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		out = new Timestamp((new DateTime(2010, 1, 8, 11, 59, 59, 999, DateTimeZone.forID("EST"))).getMillis());
		
		// Week 2 Blocks
		timeBlocks.add(createDummyTimeBlock(workArea, jobNumber, BigDecimal.TEN, BigDecimal.ZERO, "T1", in,out));
		in  = new Timestamp((new DateTime(2010, 1, 8, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		out = new Timestamp((new DateTime(2010, 1, 8, 15, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		timeBlocks.add(createDummyTimeBlock(workArea, jobNumber, BigDecimal.TEN, BigDecimal.ZERO, "T1", in,out));
		in  = new Timestamp((new DateTime(2010, 1, 8, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		out = new Timestamp((new DateTime(2010, 1, 8, 15, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());		
		timeBlocks.add(createDummyTimeBlock(workArea, jobNumber, BigDecimal.TEN, BigDecimal.ZERO, "T2", in,out));
		in  = new Timestamp((new DateTime(2010, 1, 15, 10, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		out = new Timestamp((new DateTime(2010, 1, 15, 11, 59, 59, 0, DateTimeZone.forID("EST"))).getMillis());		
		timeBlocks.add(createDummyTimeBlock(workArea, jobNumber, BigDecimal.TEN, BigDecimal.ZERO, "T2", in,out));

		
		
		java.util.Date beginDate = (new DateTime(2010, 1, 1, 12, 0, 0, 0, DateTimeZone.forID("EST"))).toDate();
		java.util.Date endDate = (new DateTime(2010, 1, 15, 12, 0, 0, 0, DateTimeZone.forID("EST"))).toDate();
		List<Interval> weekIntervals = TKUtils.getWeekIntervals(beginDate, endDate);
		assertTrue("Wrong number of Intervals.",weekIntervals.size() == 2);
		
		List<List<TimeBlock>> weekBlocks = wors.getTimeBlockWeekArrayList(timeBlocks, weekIntervals);
		assertEquals("Wrong number of weeks", 2, weekBlocks.size());
		
		for (List<TimeBlock> tlist : weekBlocks) {
			assertEquals("Wrong TimeBlock Split", 3, tlist.size());
		}
		
		// Out of bounds Time Block
		in  = new Timestamp((new DateTime(2010, 1, 15, 10, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		out = new Timestamp((new DateTime(2010, 1, 15, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());		
		timeBlocks.add(createDummyTimeBlock(workArea, jobNumber, BigDecimal.TEN, BigDecimal.ZERO, "T2", in,out));
		
		boolean rte = false;
		try {
			weekBlocks = wors.getTimeBlockWeekArrayList(timeBlocks, weekIntervals);
		} catch (Exception e) {
			rte = true;
		}
		
		assertTrue("Expected out of boundary exception",rte);
	}
	
	@Test
	public void testGetWeeklyOvertimeRules() throws Exception {
		WeeklyOvertimeRuleService wors = TkServiceLocator.getWeeklyOvertimeRuleService();
		
		String fromEarnGroup = "";
		Date asOfDate = new Date((new DateTime(2010, 1, 1, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		
		wors.getWeeklyOvertimeRules(fromEarnGroup, asOfDate);
	}
	
	@Test
	public void testWeeklyOvertimeRules() throws Exception {
		//Create document with timeblocks
		TimesheetDocument timeSheetDocument = TkTestUtils.populateTimesheetDocument(new Date(System.currentTimeMillis()));
		
		List<String> earnGroups = new ArrayList<String>();
		for(TimeBlock timeBlock : timeSheetDocument.getTimeBlocks()){
			EarnGroup earnGroup = TkServiceLocator.getEarnGroupService().getEarnGroupForEarnCode(timeBlock.getEarnCode(), new Date(System.currentTimeMillis()));
			if(!earnGroups.contains(earnGroup.getEarnGroup())){
				earnGroups.add(earnGroup.getEarnGroup());
			}
			
		}
		//setup a weekly overtime rule for this
		setupWeeklyOvertimeRules("REG", "OVT", "REG", 1, new BigDecimal(15), DEFAULT_EFFDT);
		
		
		//fetch all of the rules that apply for above collection
		List<WeeklyOvertimeRule> lstWeeklyOvtRules = new ArrayList<WeeklyOvertimeRule>();
		for(String fromEarnGroup : earnGroups){
			lstWeeklyOvtRules = (TkServiceLocator.getWeeklyOvertimeRuleService().getWeeklyOvertimeRules(fromEarnGroup, new Date(System.currentTimeMillis())));
			//call rule logic
			TkServiceLocator.getWeeklyOvertimeRuleService().processWeeklyOvertimeRule(lstWeeklyOvtRules, timeSheetDocument);
		}	
		
		//validate output of rule logic on state of timeblocks
		
		System.err.println("testing");
	}
	
	
	/**
	 * Creates a TimeBlock / TimeHourDetail that is not persisted.
	 * 
	 * @param workArea
	 * @param jobNumber
	 * @param hours
	 * @param amount
	 * @param earnCode
	 * @return
	 */
	private TimeBlock createDummyTimeBlock(Long workArea, Long jobNumber, BigDecimal hours, BigDecimal amount, String earnCode, Timestamp begin, Timestamp end) {
		TimeBlock t = new TimeBlock();
		
		t.setJobNumber(jobNumber);
		t.setWorkArea(workArea);
		t.setAmount(amount);
		t.setHours(hours);
		t.setEarnCode(earnCode);
		t.setBeginTimestamp(begin);
		t.setEndTimestamp(end);
		
		TimeHourDetail thd = new TimeHourDetail();
		thd.setAmount(amount);
		thd.setHours(hours);
		thd.setEarnCode(earnCode);
		List<TimeHourDetail> timeHourDetails = new ArrayList<TimeHourDetail>();
		timeHourDetails.add(thd);
		t.setTimeHourDetails(timeHourDetails);
		
		return t;
	}
	
	private WeeklyOvertimeRule setupWeeklyOvertimeRules(String fromEarnGroup, String toEarnGroup, String maxHoursEarnGroup, int step, BigDecimal maxHours, Date effectiveDate){
		WeeklyOvertimeRule weeklyOverTimeRule = new WeeklyOvertimeRule();
		weeklyOverTimeRule.setActive(true);
		weeklyOverTimeRule.setConvertFromEarnGroup(fromEarnGroup);
		weeklyOverTimeRule.setConvertToEarnCode(toEarnGroup);
		weeklyOverTimeRule.setMaxHoursEarnGroup(maxHoursEarnGroup);
		weeklyOverTimeRule.setStep(new BigDecimal(step));
		weeklyOverTimeRule.setMaxHours(maxHours);
		weeklyOverTimeRule.setEffectiveDate(effectiveDate);
		
		TkServiceLocator.getWeeklyOvertimeRuleService().saveOrUpdate(weeklyOverTimeRule);
		return weeklyOverTimeRule;
	}
	
	
}
