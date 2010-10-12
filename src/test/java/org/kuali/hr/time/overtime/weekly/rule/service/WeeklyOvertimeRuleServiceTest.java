package org.kuali.hr.time.overtime.weekly.rule.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import org.kuali.hr.time.earngroup.EarnGroup;
import org.kuali.hr.time.overtime.weekly.rule.WeeklyOvertimeRule;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestUtils;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKUtils;

public class WeeklyOvertimeRuleServiceTest extends TkTestCase {

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
		
		//TODO setup earn group for earn codes that are added as timeblocks if not present

		//iterate over timeblocks and build a list of earn groups that could have 
		//corresponding weekly overtime rules associated with them
		
		List<String> earnGroups = new ArrayList<String>();
		for(TimeBlock timeBlock : timeSheetDocument.getTimeBlocks()){
			EarnGroup earnGroup = TkServiceLocator.getEarnGroupService().getEarnGroupForEarnCode(timeBlock.getEarnCode(), new Date(System.currentTimeMillis()));
			if(!earnGroups.contains(earnGroup.getEarnGroup())){
				earnGroups.add(earnGroup.getEarnGroup());
			}
			
		}
		//setup a weekly overtime rule for this
		setupWeeklyOvertimeRules("REG", "OVT", "REG", 1, new BigDecimal(15));
		
		
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
	
	private WeeklyOvertimeRule setupWeeklyOvertimeRules(String fromEarnGroup, String toEarnGroup, String maxHoursEarnGroup, int step, BigDecimal maxHours){
		WeeklyOvertimeRule weeklyOverTimeRule = new WeeklyOvertimeRule();
		weeklyOverTimeRule.setActive(true);
		weeklyOverTimeRule.setConvertFromEarnGroup(fromEarnGroup);
		weeklyOverTimeRule.setConvertToEarnCode(toEarnGroup);
		weeklyOverTimeRule.setMaxHoursEarnGroup(maxHoursEarnGroup);
		weeklyOverTimeRule.setStep(new BigDecimal(step));
		weeklyOverTimeRule.setMaxHours(maxHours);
		Date asOfDate = new Date((new DateTime(2010, 1, 1, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		weeklyOverTimeRule.setEffectiveDate(asOfDate);
		
		TkServiceLocator.getWeeklyOvertimeRuleService().saveOrUpdate(weeklyOverTimeRule);
		return weeklyOverTimeRule;
	}
	
	
}
