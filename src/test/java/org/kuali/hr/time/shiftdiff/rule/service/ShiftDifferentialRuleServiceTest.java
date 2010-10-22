package org.kuali.hr.time.shiftdiff.rule.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.LocalTime;
import org.junit.Test;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.paycalendar.PayCalendar;
import org.kuali.hr.time.paycalendar.PayCalendarDates;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.shiftdiff.rule.ShiftDifferentialRule;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.service.TimeBlockService;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TkTimeBlockAggregate;
import org.kuali.rice.kns.service.KNSServiceLocator;

public class ShiftDifferentialRuleServiceTest extends TkTestCase {

	private static final String TEST_CODE = "_T";
	private static final BigDecimal TEST_NO = new BigDecimal(10);
	private static Long shiftDifferentialRuleId;
	private static final String TEST_TIME = "11:00 PM";
	private static final Date TEST_DATE = new Date(Calendar.getInstance().getTimeInMillis());
	
	@Test
	public void testCreateAdjustedShiftInterval() throws Exception {
		ShiftDifferentialRuleServiceImpl service = getServiceImpl();
				
		// Scenario 1: Clock In before Clock Out (Normal) (3h span)
		DateTime in  = new DateTime(2010, 1, 1, 12, 0, 0, 0, DateTimeZone.forID("EST"));
		DateTime out = new DateTime(2010, 1, 1, 15, 0, 0, 0, DateTimeZone.forID("EST"));
		LocalTime ci = in.toLocalTime();
		LocalTime co = out.toLocalTime();
		Interval interval = service.createAdjustedShiftInterval(ci, co);
		assertNotNull("Missing interval.", interval);
		assertEquals("Incorrect Days", 0, interval.toPeriod().getDays());
		assertEquals("Incorrect Hours", 3, interval.toPeriod().getHours());
		assertEquals("Incorrect Minutes", 0, interval.toPeriod().getMinutes());
		
		// Scenario 2: Clock In after Clock Out (Due to 24 hour offset/Day defn)
		in  = new DateTime(2010, 1, 1, 16, 0, 0, 0, DateTimeZone.forID("EST"));
		out = new DateTime(2010, 1, 2, 3, 0, 0, 0, DateTimeZone.forID("EST"));
		ci = in.toLocalTime();
		co = out.toLocalTime();
		interval = service.createAdjustedShiftInterval(ci, co);
		assertNotNull("Missing interval.", interval);
		assertEquals("Incorrect Days", 0, interval.toPeriod().getDays());
		assertEquals("Incorrect Hours", 11, interval.toPeriod().getHours());
		assertEquals("Incorrect Minutes", 0, interval.toPeriod().getMinutes());
		
		// Scenario 3: Shift start == Shift End of next Day
		in  = new DateTime(2010, 1, 1, 12, 0, 0, 0, DateTimeZone.forID("EST"));
		out = new DateTime(2010, 1, 2, 12, 0, 0, 0, DateTimeZone.forID("EST"));
		ci = in.toLocalTime();
		co = out.toLocalTime();
		interval = service.createAdjustedShiftInterval(ci, co);
		assertNotNull("Missing interval.", interval);
		assertEquals("Incorrect Days", 1, interval.toPeriod().getDays());
		assertEquals("Incorrect Hours", 0, interval.toPeriod().getHours());
		assertEquals("Incorrect Minutes", 0, interval.toPeriod().getMinutes());		
	}
	
	@Test
	public void testCreateAdjustedTimeBlockInterval() throws Exception {
		ShiftDifferentialRuleServiceImpl service = getServiceImpl();
		LocalTime periodStartTime = new LocalTime(12,0,0,0); // Noon to Noon
		
		// Scenario 1: Clock in and out on same natural day
		TimeBlock block = this.getDummyTimeBlock(new DateTime(2010, 1, 1, 12, 0, 0, 0, DateTimeZone.forID("EST")), 
				new DateTime(2010, 1, 1, 15, 0, 0, 0, DateTimeZone.forID("EST")));
		Interval interval = service.createAdjustedTimeBlockInterval(block, periodStartTime);
		assertNotNull("Missing interval.", interval);
		assertEquals("Incorrect Days", 0, interval.toPeriod().getDays());
		assertEquals("Incorrect Hours", 3, interval.toPeriod().getHours());
		assertEquals("Incorrect Minutes", 0, interval.toPeriod().getMinutes());
		
		
		// Scenario 2: Clock in and out spans 2 natural days (24 hour day defn)
		block = this.getDummyTimeBlock(	new DateTime(2010, 1, 1, 16, 0, 0, 0, DateTimeZone.forID("EST")),
				new DateTime(2010, 1, 2, 3, 0, 0, 0, DateTimeZone.forID("EST")));
		interval = service.createAdjustedTimeBlockInterval(block, periodStartTime);
		assertNotNull("Missing interval.", interval);
		assertEquals("Incorrect Days", 0, interval.toPeriod().getDays());
		assertEquals("Incorrect Hours", 11, interval.toPeriod().getHours());
		assertEquals("Incorrect Minutes", 0, interval.toPeriod().getMinutes());

		
		// Scenario 3: Clock in and out on same natural day, but 24 hour day
		// definition spans 2 natural days and our in/out are on the 2nd day.
		// 
		// We create the shift interval first, and then check for overlap.
		DateTime sin  = new DateTime(2010, 1, 1, 16, 0, 0, 0, DateTimeZone.forID("EST"));
		DateTime sout = new DateTime(2010, 1, 2, 3, 0, 0, 0, DateTimeZone.forID("EST"));
		LocalTime sci = sin.toLocalTime();
		LocalTime sco = sout.toLocalTime();
		Interval shiftInterval = service.createAdjustedShiftInterval(sci, sco);
		assertNotNull("Missing Shift Interval", shiftInterval);
		
		block = this.getDummyTimeBlock(new DateTime(2010, 1, 2, 1, 0, 0, 0, DateTimeZone.forID("EST")),
				new DateTime(2010, 1, 2, 2, 0, 0, 0, DateTimeZone.forID("EST")));
		interval = service.createAdjustedTimeBlockInterval(block, periodStartTime);
		assertNotNull("Missing interval.", interval);
		assertEquals("Incorrect Days", 0, interval.toPeriod().getDays());
		assertEquals("Incorrect Hours", 1, interval.toPeriod().getHours());
		assertEquals("Incorrect Minutes", 0, interval.toPeriod().getMinutes());
		assertTrue("interval should overlap shift interval", interval.overlaps(shiftInterval));
	}
	
	
	
	private TimeBlock getDummyTimeBlock(DateTime clockIn, DateTime clockOut) {
		TimeBlock block = new TimeBlock();
		Timestamp ci = new Timestamp(clockIn.getMillis());
		Timestamp co = new Timestamp(clockOut.getMillis());
		block.setBeginTimestamp(ci);
		block.setEndTimestamp(co);
		return block;
	}

	@Test
	public void testProcessShiftDifferentialRules() throws Exception {
		TimeBlockService tbService = TkServiceLocator.getTimeBlockService();
		PayCalendar payCalendar = TkServiceLocator.getPayCalendarSerivce().getPayCalendar(1L);
		assertNotNull("Pay calendar not found", payCalendar);
		List<PayCalendarDates> dates = payCalendar.getPayCalendarDates();
		assertNotNull("Test Pay Calendar not found.", dates);
		assertTrue("No dates in list.", dates.size() > 0);
		PayCalendarDates payCalendarEntry = dates.get(0); // Should only be one.
		assertNotNull("Missing default test pay calendar.", payCalendarEntry);

		TimesheetDocument timesheetDocument = TkServiceLocator.getTimesheetService().openTimesheetDocument(TKContext.getPrincipalId(), payCalendarEntry);
		assertNotNull("Missing timesheet.", timesheetDocument);
		Assignment assignment = timesheetDocument.getAssignments().get(0);
		assertNotNull("Missing assignment.", assignment);
		String earnCode = "REG";
		
		// Delete blocks, refetch.
		this.deleteTimeBlocks(timesheetDocument);
		timesheetDocument = TkServiceLocator.getTimesheetService().openTimesheetDocument(TKContext.getPrincipalId(), payCalendarEntry);
		assertEquals("TimeBlock list not empty.", 0, timesheetDocument.getTimeBlocks().size());

		
		List<TimeBlock> timeBlocks = new ArrayList<TimeBlock>();
		
		Timestamp in = new Timestamp(new DateTime(2010, 1, 1, 12, 0, 0, 0, DateTimeZone.forID("EST")).getMillis());
		Timestamp out = new Timestamp(new DateTime(2010, 1, 1, 13, 0, 0, 0, DateTimeZone.forID("EST")).getMillis());
		TimeBlock block = tbService.createTimeBlock(timesheetDocument, in, out, assignment, earnCode);
		timeBlocks.add(block);
		tbService.saveTimeBlocks(timesheetDocument.getTimeBlocks(), timeBlocks);
		
		timesheetDocument = TkServiceLocator.getTimesheetService().openTimesheetDocument(TKContext.getPrincipalId(), payCalendarEntry);
		assertTrue("Should be a time block", timesheetDocument.getTimeBlocks().size() == 1);

		TkTimeBlockAggregate aggregate = new TkTimeBlockAggregate(timeBlocks, payCalendarEntry);
		assertNotNull(aggregate);
		
		
		// TODO call into rule and evaluate

		// scenario 1 continuous timeblock through the shift on one day and
		// meets min hours

		// scenario 2 continuous timeblock that does not meet min hours on one
		// day

		// scenario 3 2 timeblocks that are less than maxGap apart on one day
		// and meet min hours

		// scenario 4 2 timeblocks that are less than maxGap apart on one day
		// but do not meet min hours

		// scenario 5 2 timeblocks split by virtual day that have no maxGap and
		// meet min hours criteria

		// scenario 6 2 timeblocks split by virtual day that have no maxGap and
		// do not meet min hours criteria

		// scenario 7 2 timeblocks split by virtual day that have a < maxGap
		// difference and do not meet min hours critieria

		// scenario 8 3 timeblocks on day 1 that are < maxGap apart and 1
		// timeblock linking them to day 2 and meets min hours
		// rule is setup for both days

		// scenario 9 3 timeblocks on day 1 that are < maxGap apart and 1
		// timeblock linking them to day 2 and meets min hours
		// rule is setup for only 2 day

	}

	@Override
	public void tearDown() throws Exception {
		ShiftDifferentialRule shiftDifferentialRuleObj = KNSServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(ShiftDifferentialRule.class, shiftDifferentialRuleId);
		KNSServiceLocator.getBusinessObjectService().delete(shiftDifferentialRuleObj);
		super.tearDown();
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		ShiftDifferentialRule shiftDifferentialRule = new ShiftDifferentialRule();
		shiftDifferentialRule.setActive(true);
		// shiftDifferentialRule.setBeginTime(TEST_TIME);
		shiftDifferentialRule.setEarnCode(TEST_CODE);
		shiftDifferentialRule.setEffectiveDate(TEST_DATE);
		// shiftDifferentialRule.setEndTime(TEST_TIME);
		shiftDifferentialRule.setLocation(TEST_CODE);
		shiftDifferentialRule.setMaxGap(TEST_NO);
		shiftDifferentialRule.setMinHours(TEST_NO);
		shiftDifferentialRule.setPayGrade(TEST_CODE);
		shiftDifferentialRule.setCalendarGroup("BW-CAL1");
		shiftDifferentialRule.setDay0(true);
		shiftDifferentialRule.setDay1(true);
		shiftDifferentialRule.setDay2(true);
		shiftDifferentialRule.setDay3(true);
		shiftDifferentialRule.setDay4(true);
		shiftDifferentialRule.setDay5(true);
		shiftDifferentialRule.setDay6(true);

		KNSServiceLocator.getBusinessObjectService().save(shiftDifferentialRule);
		shiftDifferentialRuleId = shiftDifferentialRule.getTkShiftDiffRuleId();
	}
	
	private void deleteTimeBlocks(TimesheetDocument document) {
		TimeBlockService tbService = TkServiceLocator.getTimeBlockService();
		List<TimeBlock> blocks = document.getTimeBlocks();
		for (TimeBlock block : blocks) {
			tbService.deleteTimeBlock(block);
		}
	}
	
	private ShiftDifferentialRuleServiceImpl getServiceImpl() {
		ShiftDifferentialRuleService serviceI = TkServiceLocator.getShiftDifferentialRuleService();
		assertNotNull("Null service.", serviceI);
		assertTrue("Unit tests set up to test package members of Impl class", serviceI instanceof ShiftDifferentialRuleServiceImpl);
		return (ShiftDifferentialRuleServiceImpl)serviceI;
	}
}
