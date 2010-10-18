package org.kuali.hr.time.overtime.weekly.rule.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
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
		List<List<TimeBlock>> weekBlock = new ArrayList<List<TimeBlock>>();
		
		BigDecimal sum = wors.getWeekHourSum(weekBlock, maxHoursEarnCodes);
		assertTrue(sum.equals(BigDecimal.ZERO));
		
		List<TimeBlock> dayBlock = new LinkedList<TimeBlock>();
		dayBlock.add(createDummyTimeBlock(workArea, jobNumber, BigDecimal.TEN, BigDecimal.ZERO, "EC", null, null));
		dayBlock.add(createDummyTimeBlock(workArea, jobNumber, BigDecimal.TEN, BigDecimal.ZERO, "EC", null, null));
		dayBlock.add(createDummyTimeBlock(workArea, jobNumber, BigDecimal.TEN, BigDecimal.ZERO, "T1", null, null));
		weekBlock.add(dayBlock);
		dayBlock = new LinkedList<TimeBlock>();
		
		dayBlock.add(createDummyTimeBlock(workArea, jobNumber, BigDecimal.TEN, BigDecimal.ZERO, "T1", null, null));
		dayBlock.add(createDummyTimeBlock(workArea, jobNumber, BigDecimal.TEN, BigDecimal.ZERO, "T2", null, null));
		dayBlock.add(createDummyTimeBlock(workArea, jobNumber, BigDecimal.TEN, BigDecimal.ZERO, "T2", null, null));
		weekBlock.add(dayBlock);

		sum = wors.getWeekHourSum(weekBlock, maxHoursEarnCodes);
		assertTrue(sum.equals(new BigDecimal("40")));
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
		List<WeeklyOvertimeRule> WeeklyOvertimeRules = new ArrayList<WeeklyOvertimeRule>();
		for(String fromEarnGroup : earnGroups){
			WeeklyOvertimeRules = (TkServiceLocator.getWeeklyOvertimeRuleService().getWeeklyOvertimeRules(fromEarnGroup, new Date(System.currentTimeMillis())));
			//call rule logic
			//TkServiceLocator.getWeeklyOvertimeRuleService().processWeeklyOvertimeRule(lstWeeklyOvtRules, timeSheetDocument);
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
