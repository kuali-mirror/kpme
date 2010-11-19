package org.kuali.hr.time.shiftdiff.rule.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.LocalTime;
import org.junit.Test;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.paycalendar.PayCalendar;
import org.kuali.hr.time.paycalendar.PayCalendarDates;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.shiftdiff.rule.ShiftDifferentialRule;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestUtils;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.TimeHourDetail;
import org.kuali.hr.time.timeblock.service.TimeBlockService;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.util.TkTimeBlockAggregate;

public class ShiftDifferentialRuleServiceTest extends TkTestCase {

	public static final String USER_PRINCIPAL_ID = "admin";
	private Date JAN_AS_OF_DATE = new Date((new DateTime(2010, 1, 1, 0, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
	
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
		TimeBlock block = TkTestUtils.createDummyTimeBlock(new DateTime(2010, 1, 1, 12, 0, 0, 0, DateTimeZone.forID("EST")), 
				new DateTime(2010, 1, 1, 15, 0, 0, 0, DateTimeZone.forID("EST")), null, null);
		Interval interval = service.createAdjustedTimeBlockInterval(block, periodStartTime);
		assertNotNull("Missing interval.", interval);
		assertEquals("Incorrect Days", 0, interval.toPeriod().getDays());
		assertEquals("Incorrect Hours", 3, interval.toPeriod().getHours());
		assertEquals("Incorrect Minutes", 0, interval.toPeriod().getMinutes());
		
		
		// Scenario 2: Clock in and out spans 2 natural days (24 hour day defn)
		block = TkTestUtils.createDummyTimeBlock(new DateTime(2010, 1, 1, 16, 0, 0, 0, DateTimeZone.forID("EST")),
				new DateTime(2010, 1, 2, 3, 0, 0, 0, DateTimeZone.forID("EST")), null, null);
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
		
		block =  TkTestUtils.createDummyTimeBlock(new DateTime(2010, 1, 2, 1, 0, 0, 0, DateTimeZone.forID("EST")),
				new DateTime(2010, 1, 2, 2, 0, 0, 0, DateTimeZone.forID("EST")), null, null);
		interval = service.createAdjustedTimeBlockInterval(block, periodStartTime);
		assertNotNull("Missing interval.", interval);
		assertEquals("Incorrect Days", 0, interval.toPeriod().getDays());
		assertEquals("Incorrect Hours", 1, interval.toPeriod().getHours());
		assertEquals("Incorrect Minutes", 0, interval.toPeriod().getMinutes());
		assertTrue("interval should overlap shift interval", interval.overlaps(shiftInterval));
	}
	
	@Test
	public void testProcessShiftDifferentialRules() throws Exception {
		String calendarGroup = "BWS-CAL"; // Biweekly Standard Cal [midnight, midnight)
		
		PayCalendar payCalendar = TkServiceLocator.getPayCalendarSerivce().getPayCalendarByGroup(calendarGroup);
		assertNotNull("Pay calendar not found", payCalendar);
		List<PayCalendarDates> dates = payCalendar.getPayCalendarDates();
		assertNotNull("Test Pay Calendar not found.", dates);
		assertTrue("No dates in list.", dates.size() > 0);
		PayCalendarDates payCalendarEntry = null;
		for (PayCalendarDates d : dates) {
			if (d.getPayCalendarDatesId().longValue() == 11L) 
				payCalendarEntry = d;
		}
		assertNotNull("Missing test pay calendar.", payCalendarEntry);

		TimesheetDocument timesheetDocument = TkServiceLocator.getTimesheetService().openTimesheetDocument(TKContext.getPrincipalId(), payCalendarEntry);
		assertNotNull("Missing timesheet.", timesheetDocument);
		
		
		// Delete blocks, refetch.
		this.deleteTimeBlocks(timesheetDocument);
		timesheetDocument = TkServiceLocator.getTimesheetService().openTimesheetDocument(TKContext.getPrincipalId(), payCalendarEntry);
		assertEquals("TimeBlock list not empty.", 0, timesheetDocument.getTimeBlocks().size());
		
		// Case 1
		runCase1(timesheetDocument, payCalendarEntry, payCalendar);
	}
	
	
	/**
	 * 	SDR for Case 1:
	 *  [begin, end) :: [6pm, Midnight/DayBoundary)
	 *  Created intervals follow inclusive beginning and exclusive ending as indicated above.
	 *  Minimum Hours: 4 hours
	 *  Maximum Gap: .25 hours (15 minutes)
	 *  Active Every Day
	 * 
	 * @param timesheetDocument
	 * @param payCalendarEntry
	 * @throws Exception
	 */
	private void runCase1(TimesheetDocument timesheetDocument, PayCalendarDates payCalendarEntry, PayCalendar payCalendar) throws Exception {
		TimeBlockService tbService = TkServiceLocator.getTimeBlockService();
		ShiftDifferentialRuleService sdrService = TkServiceLocator.getShiftDifferentialRuleService();
		assertNotNull("No Shift Differential Rule Service", sdrService);
		String earnCode = "REG";

		Long jobNumber = 30L;
		Job job = timesheetDocument.getJob(jobNumber);
		assertNotNull("SDR1 Job is missing from test data.", job);
		Assignment assignment = null;
		for (Assignment a : timesheetDocument.getAssignments()) {
			if (a.getJobNumber().longValue() == jobNumber.longValue()) {
				assignment = a;
			}
		}
		assertNotNull("Missing SDR1 assignment.", assignment);
		
		tbService.saveTimeBlocks(timesheetDocument.getTimeBlocks(), createTimeBlocksCase1(timesheetDocument, assignment, earnCode));
		
		timesheetDocument = TkServiceLocator.getTimesheetService().openTimesheetDocument(TKContext.getPrincipalId(), payCalendarEntry);
		assertEquals("Wrong number of time blocks.", 4, timesheetDocument.getTimeBlocks().size());
		TkTimeBlockAggregate aggregate = new TkTimeBlockAggregate(timesheetDocument.getTimeBlocks(), payCalendarEntry, payCalendar);
		assertNotNull(aggregate);
		sdrService.processShiftDifferentialRules(timesheetDocument, aggregate);
		
		List<TimeBlock> modifiedBlocks = timesheetDocument.getTimeBlocks();
		BigDecimal shiftHours = BigDecimal.ZERO;
		for (TimeBlock tb : modifiedBlocks) {
			List<TimeHourDetail> details = tb.getTimeHourDetails();
			for (TimeHourDetail thd : details) {
				if (thd.getEarnCode().equals("SDR")) {
					shiftHours = shiftHours.add(thd.getHours(), TkConstants.MATH_CONTEXT);
				}
			}
		}
		
		assertEquals("Premium Hours should be 5.", 0, (new BigDecimal("5")).compareTo(shiftHours));
	}
	
	/**
	 * Creates the following Clock-IN and Clock-OUT time blocks:
	 * 
	 * 2:00p - 3:00p (not in shift)
	 * 5:30p - 5:46p (not in shift)
	 * 6:00p - 9:00p (in shift)
	 * 9:15p - 11:15p (in shift, meets min gap)
	 * 
	 * @param timesheetDocument
	 * @param assignment
	 * @param earnCode
	 * @return
	 */
	private List<TimeBlock> createTimeBlocksCase1(TimesheetDocument timesheetDocument, Assignment assignment, String earnCode) {
		List<TimeBlock> timeBlocks = new ArrayList<TimeBlock>();
		
		Timestamp in = new Timestamp(new DateTime(2010, 1, 1, 14, 0, 0, 0, DateTimeZone.forID("EST")).getMillis());
		Timestamp out = new Timestamp(new DateTime(2010, 1, 1, 15, 0, 0, 0, DateTimeZone.forID("EST")).getMillis());
		TimeBlock block = TkServiceLocator.getTimeBlockService().createTimeBlock(timesheetDocument, in, out, assignment, earnCode, BigDecimal.ZERO);
		timeBlocks.add(block);
		
		in = new Timestamp(new DateTime(2010, 1, 1, 17, 30, 0, 0, DateTimeZone.forID("EST")).getMillis());
		out = new Timestamp(new DateTime(2010, 1, 1, 17, 46, 0, 0, DateTimeZone.forID("EST")).getMillis());
		block = TkServiceLocator.getTimeBlockService().createTimeBlock(timesheetDocument, in, out, assignment, earnCode, BigDecimal.ZERO);
		timeBlocks.add(block);
		
		in = new Timestamp(new DateTime(2010 , 1, 1, 18, 00, 0, 0, DateTimeZone.forID("EST")).getMillis());
		out = new Timestamp(new DateTime(2010, 1, 1, 21, 00, 0, 0, DateTimeZone.forID("EST")).getMillis());
		block = TkServiceLocator.getTimeBlockService().createTimeBlock(timesheetDocument, in, out, assignment, earnCode, BigDecimal.ZERO);
		timeBlocks.add(block);

		in = new Timestamp(new DateTime(2010 , 1, 1, 21, 15, 0, 0, DateTimeZone.forID("EST")).getMillis());
		out = new Timestamp(new DateTime(2010, 1, 1, 23, 15, 0, 0, DateTimeZone.forID("EST")).getMillis());
		block = TkServiceLocator.getTimeBlockService().createTimeBlock(timesheetDocument, in, out, assignment, earnCode, BigDecimal.ZERO);
		timeBlocks.add(block);
		
		return timeBlocks;
	}
	
	/**
	 * Creates a variety of Shift Differential Rules that we will be using in 
	 * this test.
	 * 
	 * Earn Groups Used:
	 * 
	 * SD1 - Contains only REG and RGN earn codes.
	 * 
	 * Calendar Groups Used:
	 * 
	 * BWS-CAL : Standard Calendar [Midnight-Midnight)
	 * BWN-CAL : NonStd Calendar [Noon-Noon)
	 */
	private void createShiftDifferentialRules(Date asOfDate) {
		ShiftDifferentialRuleServiceImpl service = getServiceImpl();
		
		ShiftDifferentialRule sdr = null;		
		java.util.Date beginTime = null; 
		java.util.Date endTime = null;
		BigDecimal minHours = BigDecimal.ZERO;
		BigDecimal maxGap = BigDecimal.ZERO;
		String calendarGroup = "BWS-CAL";
		String fromEarnGroup = "SD1";		
		String location = "SD1";
		String payGrade = "SD1";
		String tkSalGroup = "SD1";
		
		// SDR for Case 1:
		// [begin, end) :: [6pm, Midnight/DayBoundary)
		// Created intervals follow inclusive beginning and exclusive ending as indicated above.
		//
		// Minimum Hours: 4 hours
		// Maximum Gap: .25 hours (15 minutes)
		// Active Every Day
		// Target Earn Code: SDR
		sdr = new ShiftDifferentialRule();
		beginTime = new java.util.Date((new DateTime(2010, 1, 1, 18, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		endTime = new java.util.Date((new DateTime(2010, 1, 2, 0, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		minHours = new BigDecimal(4);
		maxGap = new BigDecimal("0.25");
		sdr.setEffectiveDate(asOfDate);
		sdr.setBeginTime(beginTime);
		sdr.setEndTime(endTime);
		sdr.setSunday(true);
		sdr.setMonday(true);
		sdr.setTuesday(true);
		sdr.setWednesday(true);
		sdr.setThursday(true);
		sdr.setFriday(true);
		sdr.setSaturday(true);
		sdr.setLocation(location);
		sdr.setPayGrade(payGrade);
		sdr.setTkSalGroup(tkSalGroup);
		sdr.setFromEarnGroup(fromEarnGroup);
		sdr.setCalendarGroup(calendarGroup);
		sdr.setMaxGap(maxGap);
		sdr.setMinHours(minHours);
		sdr.setActive(true);
		sdr.setUserPrincipalId(USER_PRINCIPAL_ID);
		sdr.setEarnCode("SDR");
		
		// Store Case 1
		service.saveOrUpdate(sdr);
	}

	@Override
	public void tearDown() throws Exception {
//		ShiftDifferentialRule shiftDifferentialRuleObj = KNSServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(ShiftDifferentialRule.class, shiftDifferentialRuleId);
//		KNSServiceLocator.getBusinessObjectService().delete(shiftDifferentialRuleObj);
		super.tearDown();
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		this.createShiftDifferentialRules(JAN_AS_OF_DATE);
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
