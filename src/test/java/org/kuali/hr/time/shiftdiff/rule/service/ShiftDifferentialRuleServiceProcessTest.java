package org.kuali.hr.time.shiftdiff.rule.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.shiftdiff.rule.ShiftDifferentialRule;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestUtils;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TkTimeBlockAggregate;

/**
 * 
 * @author djunk
 *
 */
public class ShiftDifferentialRuleServiceProcessTest extends TkTestCase {

	
	public static final String USER_PRINCIPAL_ID = "admin";
	private Date JAN_AS_OF_DATE = new Date((new DateTime(2010, 1, 1, 0, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());

	@SuppressWarnings("serial")
	@Test
	/**
	 * Runs on every day on the interval: [16:00, 24:00)
	 * Max Gap: 15 minutes
	 * Min Hours: 4
	 * 
	 * @throws Exception
	 */
	public void testProcessShiftSimpleCase() throws Exception {
		// Create the Rule
		boolean[] dayArray = {true, true, true, true, true, true, true};
		// Matches HR Job ID #1 (job # 30)
		Long jobNumber = 30L;
		Long workArea = 0L;
		this.createShiftDifferentialRule(
				"BWS-CAL", 
				"REG", 
				"PRM", 
				"SD1", 
				"SD1", 
				"SD1",
				(new DateTime(2010, 3, 29, 16, 0, 0, 0, DateTimeZone.forID("EST"))),
				(new DateTime(2010, 3, 30, 0, 0, 0, 0, DateTimeZone.forID("EST"))),
				new BigDecimal(4), // minHours
				new BigDecimal("0.25"), // maxGap
				dayArray);
		
		// Create Time Blocks (2 days, 2 blocks on each day, 15 minute gap between blocks, 4 hours total each.
		DateTime start = new DateTime(2010, 3, 29, 14, 0, 0, 0, DateTimeZone.forID("EST"));
		List<TimeBlock> blocks = new ArrayList<TimeBlock>();
		PayCalendarEntries payCalendarEntry = TkServiceLocator.getPayCalendarSerivce().getCurrentPayCalendarDates("admin", new Date(start.getMillis()));
		blocks.addAll(TkTestUtils.createUniformTimeBlocks(start, 2, new BigDecimal("4"), "REG", jobNumber, workArea));
		blocks.addAll(TkTestUtils.createUniformTimeBlocks(start.plusHours(4).plusMinutes(15), 2, new BigDecimal("2"), "REG", jobNumber, workArea));
		TkTimeBlockAggregate aggregate = new TkTimeBlockAggregate(blocks, payCalendarEntry);
		
		// Verify pre-Rule Run
		TkTestUtils.verifyAggregateHourSums("Pre-Check", new HashMap<String,BigDecimal>() {{put("PRM", BigDecimal.ZERO);put("REG", new BigDecimal(12));}},aggregate,2);
		
		// Run Rule
		TimesheetDocument tdoc = TkTestUtils.populateBlankTimesheetDocument(new Date(start.getMillis()));
		tdoc.setTimeBlocks(blocks);
		TkServiceLocator.getShiftDifferentialRuleService().processShiftDifferentialRules(tdoc, aggregate);
		
		// Verify post-Rule Run
		TkTestUtils.verifyAggregateHourSums("Post Rules Check", new HashMap<String,BigDecimal>() {{put("PRM", new BigDecimal(8));put("REG", new BigDecimal(12));}},aggregate,1);
	}
	
	/**
	 * Stores the Shift Differential Rule in the database for testing.
	 * 
	 * dayBooleans[] is a 7 element array of booleans, [0, 6] is [sun, sat]
	 */
	private void createShiftDifferentialRule(String calendarGroup, String fromEarnGroup, String premiumEarnCode, String location, String payGrade, String tkSalGroup, DateTime startTime, DateTime endTime, BigDecimal minHours, BigDecimal maxGap, boolean dayBooleans[]) {
		assertTrue("Wrong number of day booleans", dayBooleans.length == 7);
		
		ShiftDifferentialRuleService service = TkServiceLocator.getShiftDifferentialRuleService();	
		ShiftDifferentialRule sdr = new ShiftDifferentialRule();
		sdr.setBeginTime(startTime.toDate());
		sdr.setEndTime(endTime.toDate());
		sdr.setMinHours(minHours);
		sdr.setMaxGap(maxGap);
		sdr.setActive(true);
		sdr.setUserPrincipalId(USER_PRINCIPAL_ID);
		sdr.setEffectiveDate(JAN_AS_OF_DATE);
		sdr.setLocation(location);
		sdr.setPayGrade(payGrade);
		sdr.setTkSalGroup(tkSalGroup);
		sdr.setFromEarnGroup(fromEarnGroup);
		sdr.setCalendarGroup(calendarGroup);
		sdr.setEarnCode(premiumEarnCode);
		
		for (int i=0; i<dayBooleans.length; i++) {
			switch(i) {
			case 0:
				sdr.setSunday(dayBooleans[i]);
				break;
			case 1:
				sdr.setMonday(dayBooleans[i]);
				break;
			case 2:
				sdr.setTuesday(dayBooleans[i]);
				break;
			case 3:
				sdr.setWednesday(dayBooleans[i]);
				break;
			case 4:
				sdr.setThursday(dayBooleans[i]);
				break;
			case 5:
				sdr.setFriday(dayBooleans[i]);
				break;
			case 6:
				sdr.setSaturday(dayBooleans[i]);
				break;
			}
		}
		
		service.saveOrUpdate(sdr);
	}
}
