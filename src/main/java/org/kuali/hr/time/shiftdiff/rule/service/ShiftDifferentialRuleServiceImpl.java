package org.kuali.hr.time.shiftdiff.rule.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalTime;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.shiftdiff.rule.ShiftDifferentialRule;
import org.kuali.hr.time.shiftdiff.rule.dao.ShiftDifferentialRuleDao;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.TimeHourDetail;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.util.TkTimeBlockAggregate;


public class ShiftDifferentialRuleServiceImpl implements ShiftDifferentialRuleService {
	
	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(ShiftDifferentialRuleServiceImpl.class);
	/**
	 * The maximum allowable time between timeblocks before we consider them to
	 * be day-boundary single time blocks.
	 */
	private ShiftDifferentialRuleDao shiftDifferentialRuleDao = null;
	

	@Override
	public void processShiftDifferentialRules(TimesheetDocument timesheetDocument, TkTimeBlockAggregate aggregate) {
		Map<Long,List<ShiftDifferentialRule>> jobNumberToShifts = new HashMap<Long,List<ShiftDifferentialRule>>();
		
		List<List<TimeBlock>> blockDays = aggregate.getDayTimeBlockList();
		LocalTime periodStartTime = LocalTime.fromDateFields(timesheetDocument.getPayCalendarEntry().getBeginPeriodDateTime());
		
		// Create JobNumber -> Shift Rules ...
		for (Job job : timesheetDocument.getJobs()) {
			List<ShiftDifferentialRule> shiftDifferentialRules = getShiftDifferentalRules(
					job.getLocation(), 
					job.getTkSalGroup(), 
					job.getPayGrade(),
					job.getPayType().getCalendarGroup(),
					TKUtils.getTimelessDate(timesheetDocument.getPayCalendarEntry().getBeginPeriodDateTime()));
			if (shiftDifferentialRules.size() > 0) 
				jobNumberToShifts.put(job.getJobNumber(), shiftDifferentialRules);
		}
		
		if (jobNumberToShifts.isEmpty()) {
			return;
		}
		
		// Day By Day
		for (int pos = 0; pos < blockDays.size(); pos++) {
			int dayOfWeek = pos % 6; // 0 indexed
			List<TimeBlock> blocks = blockDays.get(pos); // Timeblocks for this day.
			Map<Long, List<TimeBlock>> jobNumberToTimeBlocks = new HashMap<Long,List<TimeBlock>>();
			
			if (blocks.isEmpty()) {
				continue;
			}
			
			// TODO : If 'pos' is 0, we need to pull previous pay period last day
			// so we can check Max Gap
			
			// Builds our JobNumber to TimeBlock for Current Day List.
			for (TimeBlock block : blocks) {
				Long jobNumber = block.getJobNumber();
				if (jobNumberToShifts.containsKey(jobNumber)) {
					// we have a useful timeblock.
					List<TimeBlock> jblist = jobNumberToTimeBlocks.get(jobNumber);
					if (jblist == null) {
						jblist = new ArrayList<TimeBlock>();
						jobNumberToTimeBlocks.put(jobNumber, jblist);
					}
					jblist.add(block);
				}
			}
			
			// Iteration over Keyset of Job->Shift map
			for (Long jobNumber: jobNumberToShifts.keySet()) {
				List<ShiftDifferentialRule> shiftDifferentialRules = jobNumberToShifts.get(jobNumber);				
				for (ShiftDifferentialRule rule : shiftDifferentialRules) {
					Set<String> fromEarnGroup = TkServiceLocator.getEarnGroupService().getEarnCodeListForEarnGroup(rule.getFromEarnGroup(), TKUtils.getTimelessDate(timesheetDocument.getPayCalendarEntry().getBeginPeriodDateTime()));
					// if (dayIsRuleActive(dayOfWeek, rule)) {
					// Mid Refactor with DB model changes, need to change the 
					// way we handle Day / Day of Week / etc.
					if (true) {
						List<TimeBlock> ruleTimeBlocks = jobNumberToTimeBlocks.get(jobNumber);
						
						Collections.sort(ruleTimeBlocks, new Comparator<TimeBlock>() { // Sort the Time Blocks
							public int compare(TimeBlock tb1, TimeBlock tb2) {
								if (tb1 != null && tb2 != null)
									return tb1.getBeginTimestamp().compareTo(tb2.getBeginTimestamp());
								return 0;
							}	
						});
						
						LocalTime shiftStart = LocalTime.fromDateFields(rule.getBeginTime());
						LocalTime shiftEnd = LocalTime.fromDateFields(rule.getEndTime());
						Interval shiftInterval = this.createAdjustedShiftInterval(shiftStart, shiftEnd);
						
						TimeBlock previous = null; // Previous Time Block
						List<TimeBlock> accumulatedBlocks = new ArrayList<TimeBlock>(); // TimeBlocks we MAY or MAY NOT apply Shift Premium to.  
						// Iterate over sorted list, checking time boundaries vs Shift Intervals.
						long accumulatedMillis = 0L;
						
						/*
						 * We will touch each time block and accumulate time blocks that are applicable to
						 * the current rule we are on.  
						 */
						for (TimeBlock current : ruleTimeBlocks) {
							if (!fromEarnGroup.contains(current.getEarnCode())) {
								// The timeblock's earn code is not in the earn group list of earn codes.
								// Move to next timeblock.
								continue;
							}
							
							Interval blockInterval = createAdjustedTimeBlockInterval(current, periodStartTime);
							Interval overlap = shiftInterval.overlap(blockInterval);
							if (overlap != null) {
								// There IS overlap.
								if (previous != null) {
									if (exceedsMaxGap(previous, current, rule.getMaxGap())) {
										BigDecimal accumHours = TKUtils.convertMillisToHours(accumulatedMillis);
										if (accumHours.compareTo(rule.getMinHours()) >= 0) {
											applyShiftPremiumToTimeBlocks(accumulatedBlocks, shiftInterval, rule.getEarnCode(), periodStartTime);
										}
										accumulatedBlocks.clear();
										accumulatedMillis = 0L; // reset accumulated hours..
									} else {
										accumulatedMillis  += overlap.toDuration().getMillis();
									}	
								} else {
									// Overlap shift at first time block.
									accumulatedMillis  += overlap.toDuration().getMillis();
								}
								accumulatedBlocks.add(current);
								previous = current; // current can still apply to next.
							} else {
								// No Overlap / Outside of Rule 
								if (previous != null) {
									BigDecimal accumHours = TKUtils.convertMillisToHours(accumulatedMillis);
									if (accumHours.compareTo(rule.getMinHours()) >= 0) {
										applyShiftPremiumToTimeBlocks(accumulatedBlocks, shiftInterval, rule.getEarnCode(), periodStartTime);
									}
									accumulatedBlocks.clear();
									accumulatedMillis = 0L; // reset accumulated hours..									
								} 
							}
							
						}
						// Check containers for time, and apply if needed.
						BigDecimal accumHours = TKUtils.convertMillisToHours(accumulatedMillis);
						if (accumHours.compareTo(rule.getMinHours()) >= 0) {
							applyShiftPremiumToTimeBlocks(accumulatedBlocks, shiftInterval, rule.getEarnCode(), periodStartTime);
						}
					}
				}
			}
		}		
	}
	
	/**
	 * This method takes blocks that have some sort of overlap of the shift boundary
	 * and applies the shift premium earn code to that time block for the hours that
	 * are represented by that time block.
	 * 
	 * If overlap hours is equal to block hours, then we remove the block detail
	 * and add a new detail entry with the premium earn code containing the 
	 * number of hours.
	 * 
	 * An assumption about the state of the block.getTimeHourDetails() is made,
	 * and commented about below.
	 * 
	 * @param blocks
	 * @param shiftInterval
	 * @param earnCode, 
	 */
	void applyShiftPremiumToTimeBlocks(List<TimeBlock> blocks, Interval shiftInterval, String earnCode, LocalTime periodStartTime) {
		// Iterate over TimeBlocks
		for (TimeBlock block : blocks) {
			Interval blockInterval = createAdjustedTimeBlockInterval(block, periodStartTime);
			Interval overlap = shiftInterval.overlap(blockInterval);
			BigDecimal hours = TKUtils.convertMillisToHours(overlap.toDurationMillis());
			List<TimeHourDetail> details = block.getTimeHourDetails();
			
			// TODO: Verify assumption about the contents of details.size...
			// for now just to catch it while debugging, I'll throw RuntimeException.
			if (details.size() > 1 || details.size() == 0)
				throw new RuntimeException("Should only (and at least) be one entry in the details list this point <TODO: Refactor this Throw!>");
			
			TimeHourDetail detail = details.get(0);
			if (detail.getHours().compareTo(hours) > 0) {
				BigDecimal newHours = detail.getHours().subtract(hours, TkConstants.MATH_CONTEXT);
				detail.setHours(newHours);
			} else {
				// in this case hours == the detail hours, we just add a new THD
				// that == hours.
				details.clear();
			}
			TimeHourDetail premium = new TimeHourDetail();
			premium.setHours(hours);
			premium.setEarnCode(earnCode);
			premium.setTkTimeBlockId(detail.getTkTimeBlockId());
			details.add(premium);
		}		
	}
	
	/**
	 * Does the difference between the previous time blocks clock out time and the 
	 * current time blocks clock in time exceed the max gap?
	 * 
	 * @param previous
	 * @param current
	 * @param maxGap
	 * @return
	 */
	boolean exceedsMaxGap(TimeBlock previous, TimeBlock current, BigDecimal maxGap) {		
		long difference = current.getBeginTimestamp().getTime() - previous.getEndTimestamp().getTime();
		BigDecimal gapHours = TKUtils.convertMillisToHours(difference);
		
		return (gapHours.compareTo(maxGap) < 0);
	}
		
	/**
	 * Method to let us know if the given time block overlaps the shift 
	 * interval.  Handles non-natural 24hour day definitions (noon-noon).
	 * 
	 * @param block The block we are checking against the Shift Interval
	 * @param shiftStart Shift interval start time
	 * @param shiftEnd Shift interval end time - it is possible that this time
	 * is before the shift start time, which indicates a non-natural 24 hour
	 * day.
	 * 
	 * @return True if block overlaps the shift interval in any way.
	 */
	boolean timeblockInShiftInterval(TimeBlock block, Interval shiftInterval, LocalTime periodStartTime) {
		boolean inInterval = false;
		
		Interval blockInterval = createAdjustedTimeBlockInterval(block, periodStartTime);
		
		if (blockInterval.overlaps(shiftInterval))
			inInterval = true;
		
		return inInterval;
	}
				
	/**
	 * Generates an interval for a clockIN and clockOUT time, checking if the 
	 * out time is before in-time and adjusting accordingly.
	 * 	 * 
	 * @param clockIn
	 * @param clockOut
	 * @return An interval based on TODAY - since we were dealing with partials.
	 */
	Interval createAdjustedShiftInterval(LocalTime clockIn, LocalTime clockOut) {
		DateTime dIn = clockIn.toDateTimeToday();
		DateTime dOut = clockOut.toDateTimeToday();
		
		if (dOut.isBefore(dIn) || dOut.isEqual(dIn))
			dOut = dOut.plusDays(1);
		
		return new Interval(dIn, dOut);
	}

	/**
	 * For a given time block, generates a Today-Based interval that is 
	 * compatible with our shift intervals.  
	 * 
	 * @param block
	 * @param periodStartTime The time that the pay periods are starting.
	 * An assumption is made that all day definitions are 24 hour periods, 
	 * which can start at any time, but within a pay period are consistent.	
	 *  
	 * @return
	 */
	Interval createAdjustedTimeBlockInterval(TimeBlock block, LocalTime periodStartTime) {
		LocalTime start = LocalTime.fromDateFields(block.getBeginTimestamp());
		LocalTime end = LocalTime.fromDateFields(block.getEndTimestamp());
		
		DateTime dIn = start.toDateTimeToday();
		DateTime dOut = end.toDateTimeToday();

		if (end.isBefore(start)) {
			dOut = dOut.plusDays(1);
		} else if (start.isBefore(periodStartTime)) {
			dIn = dIn.plusDays(1);
			dOut = dOut.plusDays(1);
		}
		
		return new Interval(dIn, dOut);
	}
	
	public void setShiftDifferentialRuleDao(ShiftDifferentialRuleDao shiftDifferentialRuleDao) {
		this.shiftDifferentialRuleDao = shiftDifferentialRuleDao;
	}

	@Override
	public List<ShiftDifferentialRule> getShiftDifferentalRules(String location, String tkSalGroup, String payGrade, String calendarGroup, Date asOfDate) {
		List<ShiftDifferentialRule> sdrs = null;
	
		// location, sal group, pay grade
		if (sdrs == null)
			sdrs = shiftDifferentialRuleDao.findShiftDifferentialRules(location, tkSalGroup, payGrade, calendarGroup, asOfDate);
		
		// location, sal group, *
		if (sdrs == null)
			sdrs = shiftDifferentialRuleDao.findShiftDifferentialRules(location, tkSalGroup, "*", calendarGroup, asOfDate);
		
		// location, *, pay grade
		if (sdrs == null)
			sdrs = shiftDifferentialRuleDao.findShiftDifferentialRules(location, tkSalGroup, "*", calendarGroup, asOfDate);
		
		// location, *, *
		if (sdrs == null)
			sdrs = shiftDifferentialRuleDao.findShiftDifferentialRules(location, "*", "*", calendarGroup, asOfDate);

		// *, sal group, pay grade
		if (sdrs == null)
			sdrs = shiftDifferentialRuleDao.findShiftDifferentialRules("*", tkSalGroup, payGrade, calendarGroup, asOfDate);
		
		// *, sal group, *
		if (sdrs == null) 
			sdrs = shiftDifferentialRuleDao.findShiftDifferentialRules("*", tkSalGroup, "*", calendarGroup, asOfDate);
		
		// *, *, pay grade
		if (sdrs == null) 
			sdrs = shiftDifferentialRuleDao.findShiftDifferentialRules("*", "*", payGrade, calendarGroup, asOfDate);
				
		// *, *, *
		if (sdrs == null) 
			sdrs = shiftDifferentialRuleDao.findShiftDifferentialRules("*", "*", "*", calendarGroup, asOfDate);
		
		if (sdrs == null)
			sdrs = Collections.emptyList();
		
		return sdrs;
	}	
	
	@Override
	public void saveOrUpdate(List<ShiftDifferentialRule> shiftDifferentialRules) {
		shiftDifferentialRuleDao.saveOrUpdate(shiftDifferentialRules);
	}

	@Override
	public void saveOrUpdate(ShiftDifferentialRule shiftDifferentialRule) {
		shiftDifferentialRuleDao.saveOrUpdate(shiftDifferentialRule);
	}

}
