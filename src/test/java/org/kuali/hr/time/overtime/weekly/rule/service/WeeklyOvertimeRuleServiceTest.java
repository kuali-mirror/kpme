package org.kuali.hr.time.overtime.weekly.rule.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.junit.Test;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.earngroup.EarnGroup;
import org.kuali.hr.time.earngroup.EarnGroupDefinition;
import org.kuali.hr.time.overtime.weekly.rule.WeeklyOvertimeRule;
import org.kuali.hr.time.paycalendar.PayCalendar;
import org.kuali.hr.time.paycalendar.PayCalendarDates;
import org.kuali.hr.time.paytype.PayType;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestUtils;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.TimeHourDetail;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.job.Job;
import org.kuali.rice.kns.service.KNSServiceLocator;

public class WeeklyOvertimeRuleServiceTest extends TkTestCase {
	
	private static Date DEFAULT_EFFDT = new Date((new DateTime(2010, 1, 1, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());

	@Override
	public void setUp() throws Exception {
		super.setUp();	
		LoadDate();			
	}

	private void LoadDate() {
		Job job = new Job();
		job.setHrJobId(1012L);
		job.setPrincipalId("admin");
		job.setJobNumber(1L);
		job.setEffectiveDate(new Date(14823L*24L*60L*60L*1000L));
		job.setDept("TEST-DEPT");
		job.setActive(true);
		job.setTkSalGroup("A10");
		job.setTimestamp(new Timestamp(14823L*24L*60L*60L*1000L + 16L*60L*60L*1000L + 13L*1000L));
		//joda time
		job.setObjectId("A9225D4A-4871-4277-5638-4C7880A57621");
		job.setVersionNumber(1L);
		job.setHrPayType("BW");
		KNSServiceLocator.getBusinessObjectService().save(job);
		
		PayType payType = new PayType();
		payType.setHrPayTypeId(1L);
		payType.setPayType("BW");
		payType.setRegEarnCode("RGN");
		payType.setEffectiveDate(new Date(14823L*24L*60L*60L*1000L));
		payType.setTimestamp(new Timestamp(14823L*24L*60L*60L*1000L + 16L*60L*60L*1000L + 13L*1000L));
		payType.setActive(true);
		KNSServiceLocator.getBusinessObjectService().save(payType);
		
		PayCalendar payCalendar = new PayCalendar();
		payCalendar.setPayCalendarId(20L);
		payCalendar.setCalendarGroup("BW-CAL1");
		payCalendar.setChart("CHART1");
		payCalendar.setBeginDate(new Date(14611L*24L*60L*60L*1000L));
		payCalendar.setBeginTime(new Time(0L));
		payCalendar.setEndDate(new Date(14975L*24L*60L*60L*1000L));
		payCalendar.setEndTime(new Time(24L*60L*59L*1000L));
		KNSServiceLocator.getBusinessObjectService().save(payCalendar);
		
		PayCalendarDates payCalendarDates = new PayCalendarDates();
		payCalendarDates.setPayCalendarDatesId(1L);
		payCalendarDates.setPayCalendarId(20L);
		payCalendarDates.setBeginPeriodDateTime(new Date(14823L*24L*60L*60L*1000L));
		payCalendarDates.setEndPeriodDateTime(new Date(14837L*24L*60L*60L*1000L + 24L*60L*59L*1000L));
		KNSServiceLocator.getBusinessObjectService().save(payCalendarDates);	
		
		payCalendarDates = new PayCalendarDates();
		payCalendarDates.setPayCalendarDatesId(2L);
		payCalendarDates.setPayCalendarId(20L);
		payCalendarDates.setBeginPeriodDateTime(new Date(14883L*24L*60L*60L*1000L));
		payCalendarDates.setEndPeriodDateTime(new Date(14913L*24L*60L*60L*1000L + 24L*60L*59L*1000L));
		KNSServiceLocator.getBusinessObjectService().save(payCalendarDates);	
		
		payCalendarDates = new PayCalendarDates();
		payCalendarDates.setPayCalendarDatesId(3L);
		payCalendarDates.setPayCalendarId(20L);
		payCalendarDates.setBeginPeriodDateTime(new Date(14914L*24L*60L*60L*1000L));
		payCalendarDates.setEndPeriodDateTime(new Date(14984L*24L*60L*60L*1000L + 24L*60L*59L*1000L));
		KNSServiceLocator.getBusinessObjectService().save(payCalendarDates);	
		
		EarnCode earnCode = new EarnCode();
		earnCode.setTkEarnCodeId(9L);
		earnCode.setEarnCode("RGN");
		earnCode.setActive(true);
		earnCode.setEffectiveDate(new Date(14883L*24L*60L*60L*1000L));
		earnCode.setTimestamp(new Timestamp(14883L*24L*60L*60L*1000L));
		KNSServiceLocator.getBusinessObjectService().save(earnCode);
		
		EarnGroupDefinition earnGroupDefinition = new EarnGroupDefinition();
		earnGroupDefinition.setTkEarnGroupDefId(100L);
		earnGroupDefinition.setTkEarnGroupId(100L);
		earnGroupDefinition.setEarnCode("RGN");
		earnGroupDefinition.setVersionNumber(1L);
		earnGroupDefinition.setObjectId("7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97");
		KNSServiceLocator.getBusinessObjectService().save(earnGroupDefinition);	
		
		EarnGroup earnGroup = new EarnGroup();
		earnGroup.setTkEarnGroupId(100L);
		earnGroup.setEarnGroup("REG");
		earnGroup.setEffectiveDate(new Date(14883L*24L*60L*60L*1000L));
		earnGroup.setActive(true);
		earnGroup.setVersionNumber(20L);
		earnGroup.setTimestamp(new Timestamp(System.currentTimeMillis()));
		KNSServiceLocator.getBusinessObjectService().save(earnGroup);
	}

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
		
		//wors.getWeeklyOvertimeRules(fromEarnGroup, asOfDate);
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
			//WeeklyOvertimeRules = (TkServiceLocator.getWeeklyOvertimeRuleService().getWeeklyOvertimeRules(fromEarnGroup, new Date(System.currentTimeMillis())));
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
