/**
 * Copyright 2004-2012 The Kuali Foundation
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
package org.kuali.hr.time.shiftdiff.rule.service;

import org.apache.log4j.Logger;
import org.joda.time.*;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.shiftdiff.rule.ShiftDifferentialRule;
import org.kuali.hr.time.shiftdiff.rule.dao.ShiftDifferentialRuleDao;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.TimeHourDetail;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.util.TkTimeBlockAggregate;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.*;


public class ShiftDifferentialRuleServiceImpl implements ShiftDifferentialRuleService {

	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(ShiftDifferentialRuleServiceImpl.class);
	/**
	 * The maximum allowable time between timeblocks before we consider them to
	 * be day-boundary single time blocks.
	 */
	private ShiftDifferentialRuleDao shiftDifferentialRuleDao = null;

	private Map<Long,List<ShiftDifferentialRule>> getJobNumberToShiftRuleMap(TimesheetDocument timesheetDocument) {
		Map<Long,List<ShiftDifferentialRule>> jobNumberToShifts = new HashMap<Long,List<ShiftDifferentialRule>>();
		PrincipalHRAttributes principalCal = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(timesheetDocument.getPrincipalId(),timesheetDocument.getCalendarEntry().getEndPeriodDate());

		for (Job job : timesheetDocument.getJobs()) {
			List<ShiftDifferentialRule> shiftDifferentialRules = getShiftDifferentalRules(job.getLocation(),job.getHrSalGroup(),job.getPayGrade(),principalCal.getPayCalendar(),
					TKUtils.getTimelessDate(timesheetDocument.getCalendarEntry().getBeginPeriodDateTime()));
			if (shiftDifferentialRules.size() > 0)
				jobNumberToShifts.put(job.getJobNumber(), shiftDifferentialRules);
		}

		return jobNumberToShifts;
	}

	private Map<Long,List<TimeBlock>> getPreviousPayPeriodLastDayJobToTimeBlockMap(TimesheetDocument timesheetDocument, Map<Long,List<ShiftDifferentialRule>> jobNumberToShifts) {
		Map<Long, List<TimeBlock>> jobNumberToTimeBlocksPreviousDay = null;

		// Get the last day of the last week of the previous pay period.
		// This is the only day that can have impact on the current day.
		List<TimeBlock> prevBlocks = TkServiceLocator.getTimesheetService().getPrevDocumentTimeBlocks(timesheetDocument.getPrincipalId(), timesheetDocument.getDocumentHeader().getBeginDate());
		if (prevBlocks.size() > 0) {
			TimesheetDocumentHeader prevTdh = TkServiceLocator.getTimesheetDocumentHeaderService().getPreviousDocumentHeader(timesheetDocument.getPrincipalId(), timesheetDocument.getDocumentHeader().getBeginDate());
			if (prevTdh != null) {
				CalendarEntries prevPayCalendarEntry = TkServiceLocator.getCalendarService().getCalendarDatesByPayEndDate(timesheetDocument.getPrincipalId(), prevTdh.getEndDate(), null);
				TkTimeBlockAggregate prevTimeAggregate = new TkTimeBlockAggregate(prevBlocks, prevPayCalendarEntry, prevPayCalendarEntry.getCalendarObj(), true);
				List<List<TimeBlock>> dayBlocks = prevTimeAggregate.getDayTimeBlockList();
				List<TimeBlock> previousPeriodLastDayBlocks = dayBlocks.get(dayBlocks.size() - 1);
				// Set back to null if there is nothing in the list.
				if (previousPeriodLastDayBlocks.size() > 0) {
					jobNumberToTimeBlocksPreviousDay = new HashMap<Long, List<TimeBlock>>();

					for (TimeBlock block : previousPeriodLastDayBlocks) {
						// Job Number to TimeBlock for Last Day of Previous Time
						// Period
						Long jobNumber = block.getJobNumber();
						if (jobNumberToShifts.containsKey(jobNumber)) {
							// we have a useful timeblock.
							List<TimeBlock> jblist = jobNumberToTimeBlocksPreviousDay.get(jobNumber);
							if (jblist == null) {
								jblist = new ArrayList<TimeBlock>();
								jobNumberToTimeBlocksPreviousDay.put(jobNumber, jblist);
							}
							jblist.add(block);
						}
					}
				}
			}
		}

		return jobNumberToTimeBlocksPreviousDay;
	}

	private boolean timeBlockHasEarnCode(Set<String> earnCodes, TimeBlock block) {
		boolean present = false;

		if (block != null && earnCodes != null)
			present = earnCodes.contains(block.getEarnCode());

		return present;
	}

    /**
     * Returns a BigDecimal representing the sum of all of the negative time
     * hour detail types. In this case, only LUN is considered. This can be
     * modified to add other considerations.
     *
     * @param block The Timeblock to inspect.
     *
     * @return A big decimal.
     */
    private BigDecimal negativeTimeHourDetailSum(TimeBlock block) {
        BigDecimal sum = BigDecimal.ZERO;

        if (block != null) {
            List<TimeHourDetail> details = block.getTimeHourDetails();
            for (TimeHourDetail detail : details) {
                if (detail.getEarnCode().equals(TkConstants.LUNCH_EARN_CODE)) {
                    sum = sum.add(detail.getHours());
                }
            }
        }

        return sum;
    }

	@Override
	public void processShiftDifferentialRules(TimesheetDocument timesheetDocument, TkTimeBlockAggregate aggregate) {
        DateTimeZone zone = TkServiceLocator.getTimezoneService().getUserTimezoneWithFallback();
		List<List<TimeBlock>> blockDays = aggregate.getDayTimeBlockList();
		DateTime periodStartDateTime = timesheetDocument.getCalendarEntry().getBeginLocalDateTime().toDateTime(zone);
		Map<Long,List<ShiftDifferentialRule>> jobNumberToShifts = getJobNumberToShiftRuleMap(timesheetDocument);


        // If there are no shift differential rules, we have an early exit.
		if (jobNumberToShifts.isEmpty()) {
			return;
		}

		// Get the last day of the previous pay period. We need this to determine
		// if there are hours from the previous pay period that will effect the
		// shift rule on the first day of the currently-being-processed pay period.
		//
        // Will be set to null if not applicable.
        boolean previousPayPeriodPrevDay = true;
		Map<Long, List<TimeBlock>> jobNumberToTimeBlocksPreviousDay =
                getPreviousPayPeriodLastDayJobToTimeBlockMap(timesheetDocument, jobNumberToShifts);

		// We are going to look at the time blocks grouped by Days.
        //
        // This is a very large outer loop.
		for (int pos = 0; pos < blockDays.size(); pos++) {
			List<TimeBlock> blocks = blockDays.get(pos); // Timeblocks for this day.
			if (blocks.isEmpty())
				continue; // No Time blocks, no worries.

			DateTime currentDay = periodStartDateTime.plusDays(pos);
			Interval virtualDay = new Interval(currentDay, currentDay.plusHours(24));

			// Builds our JobNumber to TimeBlock for Current Day List.
            //
            // Shift Differential Rules are also grouped by Job number, this
            // provides a quick way to do the lookup / reference.
            // We don't need every time block, only the ones that will be
            // applicable to the shift rules.
			Map<Long, List<TimeBlock>> jobNumberToTimeBlocks = new HashMap<Long,List<TimeBlock>>();
			for (TimeBlock block : blocks) {
				Long jobNumber = block.getJobNumber();
				if (jobNumberToShifts.containsKey(jobNumber)) {
					List<TimeBlock> jblist = jobNumberToTimeBlocks.get(jobNumber);
					if (jblist == null) {
						jblist = new ArrayList<TimeBlock>();
						jobNumberToTimeBlocks.put(jobNumber, jblist);
					}
					jblist.add(block);
				}
			}


            // Large Outer Loop to look at applying the Shift Rules based on
            // the current JobNumber.
            //
			// This loop will handle previous day boundary time as well as the
            // current day.
            //
            // There is room for refactoring here!
			for (Long jobNumber: jobNumberToShifts.keySet()) {
				List<ShiftDifferentialRule> shiftDifferentialRules = jobNumberToShifts.get(jobNumber);
				// Obtain and sort our previous and current time blocks.
				List<TimeBlock> ruleTimeBlocksPrev = null;
				List<TimeBlock> ruleTimeBlocksCurr = jobNumberToTimeBlocks.get(jobNumber);
				if (ruleTimeBlocksCurr != null && ruleTimeBlocksCurr.size() > 0) {
					if (jobNumberToTimeBlocksPreviousDay != null)
						ruleTimeBlocksPrev = jobNumberToTimeBlocksPreviousDay.get(jobNumber);
					if (ruleTimeBlocksPrev != null && ruleTimeBlocksPrev.size() > 0)
						this.sortTimeBlocksInverse(ruleTimeBlocksPrev);
					this.sortTimeBlocksNatural(ruleTimeBlocksCurr);
				} else {
					// Skip to next job, there is nothing for this job
					// on this day, and because of this we don't care
					// about the previous day either.
					continue;
				}

				for (ShiftDifferentialRule rule : shiftDifferentialRules) {
					Set<String> fromEarnGroup = TkServiceLocator.getEarnCodeGroupService().getEarnCodeListForEarnCodeGroup(rule.getFromEarnGroup(), TKUtils.getTimelessDate(timesheetDocument.getCalendarEntry().getBeginPeriodDateTime()));

                    // Because of the way java.sql.Time are stored, we need to first
                    // construct a LocalTime in the System Time Zone, then convert that
                    // time to the users time zone.
                    LocalTime ruleStart = new LocalTime(rule.getBeginTime(), TKUtils.getSystemDateTimeZone());
                    LocalTime ruleEnd = new LocalTime(rule.getEndTime(), TKUtils.getSystemDateTimeZone());
                    ruleStart = new LocalTime(ruleStart, zone);
                    ruleEnd = new LocalTime(ruleEnd, zone);


					DateTime shiftEnd = ruleEnd.toDateTime(currentDay);
					DateTime shiftStart = ruleStart.toDateTime(currentDay);

					if (shiftEnd.isBefore(shiftStart) || shiftEnd.isEqual(shiftStart))
						shiftEnd = shiftEnd.plusDays(1);
					Interval shiftInterval = new Interval(shiftStart, shiftEnd);

					// Set up buckets to handle previous days time accumulations
					BigDecimal hoursBeforeVirtualDay = BigDecimal.ZERO;

					// Check current day first block to see if start time gap from virtual day start is greater than max gap
					// if so, we can skip the previous day checks.
					TimeBlock firstBlockOfCurrentDay = null;
					for (TimeBlock b : ruleTimeBlocksCurr) {
						if (timeBlockHasEarnCode(fromEarnGroup, b)) {
							firstBlockOfCurrentDay = b;
							break;
						}
					}

					// Previous Day :: We have prior block container of nonzero size, and the previous day is active.
					Interval previousDayShiftInterval = new Interval(shiftStart.minusDays(1), shiftEnd.minusDays(1));

                    // Blank initialization pointer for picking which interval to pass to applyPremium()
                    Interval evalInterval = null;
					if (ruleTimeBlocksPrev != null && ruleTimeBlocksPrev.size() > 0 && dayIsRuleActive(currentDay.minusDays(1), rule)) {
						// Simple heuristic to see if we even need to worry about
						// the Shift rule for this set of data.
						if (shiftEnd.isAfter(virtualDay.getEnd())) {
							// Compare first block of previous day with first block of current day for max gaptitude.
							TimeBlock firstBlockOfPreviousDay = null;
							for (TimeBlock b : ruleTimeBlocksPrev) {
								if (timeBlockHasEarnCode(fromEarnGroup, b)) {
									firstBlockOfPreviousDay = b;
									break;
								}
							}
							// Only if we actually have at least one block.
                            // Adding Assumption: We must have both a valid current and previous block. Max Gap can not be more than a virtual day.
                            // If this assumption does not hold, additional logic will be needed to iteratively go back in time to figure out which
                            // blocks are valid.
							if ( (firstBlockOfPreviousDay != null) && (firstBlockOfCurrentDay != null)) {
								Interval previousBlockInterval = new Interval(new DateTime(firstBlockOfPreviousDay.getEndTimestamp(), zone), new DateTime(firstBlockOfCurrentDay.getBeginTimestamp(), zone));
								Duration blockGapDuration = previousBlockInterval.toDuration();
								BigDecimal bgdHours = TKUtils.convertMillisToHours(blockGapDuration.getMillis());
								if (bgdHours.compareTo(rule.getMaxGap()) <= 0) {
									// If we are here, we know we have at least one valid time block to pull some hours forward from.


									// These are inversely sorted.
									for (int i=0; i<ruleTimeBlocksPrev.size(); i++) {
										TimeBlock b = ruleTimeBlocksPrev.get(i);
										if (timeBlockHasEarnCode(fromEarnGroup, b)) {
											Interval blockInterval = new Interval(new DateTime(b.getBeginTimestamp(), zone), new DateTime(b.getEndTimestamp(), zone));

											// Calculate Block Gap, the duration between clock outs and clock ins of adjacent time blocks.
											if (previousBlockInterval != null) {
												blockGapDuration = new Duration(new DateTime(b.getEndTimestamp(), zone), previousBlockInterval.getStart());
												bgdHours = TKUtils.convertMillisToHours(blockGapDuration.getMillis());
											}

											// Check Gap, if good, sum hours
											if (bgdHours.compareTo(rule.getMaxGap()) <= 0) {
												// Calculate Overlap and add it to hours before virtual day bucket.
												if (blockInterval.overlaps(previousDayShiftInterval)) {
													BigDecimal hrs = TKUtils.convertMillisToHours(blockInterval.overlap(previousDayShiftInterval).toDurationMillis());
													hoursBeforeVirtualDay = hoursBeforeVirtualDay.add(hrs);
												}

											} else {
												// Time blocks are reverse sorted, we can jump out as soon as the max gap is exceeded.
												break;
											}

											previousBlockInterval = blockInterval;

										}
									}
								} else {
									// DO NOTHING!
								}
							}
						}
					}

					BigDecimal hoursToApply = BigDecimal.ZERO;
					BigDecimal hoursToApplyPrevious = BigDecimal.ZERO;
                    // If the hours before virtual day are less than or equal to
                    // min hours, we have already applied the time, so we don't
                    // set hoursToApplyPrevious
					if (hoursBeforeVirtualDay.compareTo(rule.getMinHours()) <= 0) {
						// we need to apply these hours.
						hoursToApplyPrevious = hoursBeforeVirtualDay;
					}


					//  Current Day

					TimeBlock previous = null; // Previous Time Block
					List<TimeBlock> accumulatedBlocks = new ArrayList<TimeBlock>(); // TimeBlocks we MAY or MAY NOT apply Shift Premium to.
                    List<Interval> accumulatedBlockIntervals = new ArrayList<Interval>(); // To save recompute time when checking timeblocks for application we store them as we create them.
					// Iterate over sorted list, checking time boundaries vs Shift Intervals.
					long accumulatedMillis = TKUtils.convertHoursToMillis(hoursBeforeVirtualDay);

                    boolean previousDayOnly = false; // IF the rule is not active today, but was on the previous day, we need to still look at time blocks.
                    if (!dayIsRuleActive(currentDay, rule)) {
                        if (dayIsRuleActive(currentDay.minusDays(1), rule)) {
                            previousDayOnly = true;
                        } else {
                            // Nothing to see here, move to next rule.
                            continue;
                        }

                    }

					/*
					 * We will touch each time block and accumulate time blocks that are applicable to
					 * the current rule we are on.
					 */

                    // These blocks are only used for detail application
                    // We don't want to pass along the previous pay period,
                    // because we don't want to modify the time blocks on that
                    // period. If null is passed, time will be placed on the
                    // first block of the first period if the previous period
                    // block had influence.
                    List<TimeBlock> previousBlocksFiltered = (previousPayPeriodPrevDay) ? null : filterBlocksByApplicableEarnGroup(fromEarnGroup, ruleTimeBlocksPrev);

					for (TimeBlock current : ruleTimeBlocksCurr) {
						if (!timeBlockHasEarnCode(fromEarnGroup, current)) {
                            // TODO: WorkSchedule considerations somewhere in here?
                            continue;
                        }

						Interval blockInterval = new Interval(new DateTime(current.getBeginTimestamp(), zone), new DateTime(current.getEndTimestamp(), zone));

						// Check both Intervals, since the time blocks could still
						// be applicable to the previous day.  These two intervals should
						// not have any overlap.
						if (previousDayShiftInterval.overlaps(shiftInterval))
							throw new RuntimeException("Interval of greater than 24 hours created in the rules processing.");

                        // This block of code handles cases where you have time
                        // that spills to multiple days and a shift rule that
                        // has a valid window on multiple consecutive days. Time
                        // must be applied with the correct shift interval.
						Interval overlap = previousDayShiftInterval.overlap(blockInterval);
                        evalInterval = previousDayShiftInterval;
						if (overlap == null) {
                            if (hoursToApplyPrevious.compareTo(BigDecimal.ZERO) > 0) {
                                // we have hours from previous day, and the shift
                                // window is going to move to current day.
                                // Need to apply this now, and move window forward
                                // for current time block.
                                BigDecimal accumHours = TKUtils.convertMillisToHours(accumulatedMillis);
                                this.applyAccumulatedWrapper(accumHours, evalInterval, accumulatedBlockIntervals, accumulatedBlocks, previousBlocksFiltered, hoursToApplyPrevious, hoursToApply, rule);
                                accumulatedMillis = 0L; // reset accumulated hours..
                                hoursToApply = BigDecimal.ZERO;
                                hoursToApplyPrevious = BigDecimal.ZERO;
                            }

                            // Because of our position in the loop, when we are at this point,
                            // we know we've passed any previous day shift intervals, so we can
                            // determine if we should skip the current day based on the boolean
                            // we set earlier.
                            if (previousDayOnly) {
                                continue;
                            }

							overlap = shiftInterval.overlap(blockInterval);
                            evalInterval = shiftInterval;
                        }

                        // Time bucketing and application as normal:
                        //
						if (overlap != null) {
							// There IS overlap.
							if (previous != null) {
								if (exceedsMaxGap(previous, current, rule.getMaxGap())) {
									BigDecimal accumHours = TKUtils.convertMillisToHours(accumulatedMillis);
                                    this.applyAccumulatedWrapper(accumHours, evalInterval, accumulatedBlockIntervals, accumulatedBlocks, previousBlocksFiltered, hoursToApplyPrevious, hoursToApply, rule);
                                    accumulatedMillis = 0L; // reset accumulated hours..
									hoursToApply = BigDecimal.ZERO;
									hoursToApplyPrevious = BigDecimal.ZERO;
								} else {
									long millis = overlap.toDurationMillis();
									accumulatedMillis  += millis;
									hoursToApply = hoursToApply.add(TKUtils.convertMillisToHours(millis));
								}
							} else {
								// Overlap shift at first time block.
								long millis = overlap.toDurationMillis();
								accumulatedMillis  += millis;
								hoursToApply = hoursToApply.add(TKUtils.convertMillisToHours(millis));
							}
							accumulatedBlocks.add(current);
                            accumulatedBlockIntervals.add(blockInterval);
							previous = current; // current can still apply to next.
						} else {
							// No Overlap / Outside of Rule
							if (previous != null) {
								BigDecimal accumHours = TKUtils.convertMillisToHours(accumulatedMillis);
                                this.applyAccumulatedWrapper(accumHours, evalInterval, accumulatedBlockIntervals, accumulatedBlocks, previousBlocksFiltered, hoursToApplyPrevious, hoursToApply, rule);
								accumulatedMillis = 0L; // reset accumulated hours..
								hoursToApply = BigDecimal.ZERO;
								hoursToApplyPrevious = BigDecimal.ZERO;
							}
						}

					}

					// All time blocks are iterated over, check for remainders.
					// Check containers for time, and apply if needed.
					BigDecimal accumHours = TKUtils.convertMillisToHours(accumulatedMillis);
                    this.applyAccumulatedWrapper(accumHours, evalInterval, accumulatedBlockIntervals, accumulatedBlocks, previousBlocksFiltered, hoursToApplyPrevious, hoursToApply, rule);
                }
			}
			// 	Keep track of previous as we move day by day.
			jobNumberToTimeBlocksPreviousDay = jobNumberToTimeBlocks;
            previousPayPeriodPrevDay = false;
		}

	}

    private List<TimeBlock> filterBlocksByApplicableEarnGroup(Set<String> fromEarnGroup, List<TimeBlock> blocks) {
        List<TimeBlock> filtered;

        if (blocks == null || blocks.size() == 0)
            filtered = null;
        else {
            filtered = new ArrayList<TimeBlock>();
            for (TimeBlock b : blocks) {
                if (timeBlockHasEarnCode(fromEarnGroup, b))
                    filtered.add(b);
            }
        }

        return filtered;
    }


    private void applyAccumulatedWrapper(BigDecimal accumHours, Interval evalInterval, List<Interval>accumulatedBlockIntervals, List<TimeBlock>accumulatedBlocks, List<TimeBlock> previousBlocks, BigDecimal hoursToApplyPrevious, BigDecimal hoursToApply, ShiftDifferentialRule rule) {
        if (accumHours.compareTo(rule.getMinHours()) >= 0) {
            this.applyPremium(evalInterval, accumulatedBlockIntervals, accumulatedBlocks, previousBlocks, hoursToApplyPrevious, hoursToApply, rule.getEarnCode());
        }
        accumulatedBlocks.clear();
        accumulatedBlockIntervals.clear();
    }

	private void sortTimeBlocksInverse(List<TimeBlock> blocks) {
		Collections.sort(blocks, new Comparator<TimeBlock>() { // Sort the Time Blocks
			public int compare(TimeBlock tb1, TimeBlock tb2) {
				if (tb1 != null && tb2 != null)
					return -1 * tb1.getBeginTimestamp().compareTo(tb2.getBeginTimestamp());
				return 0;
			}
		});
	}

	private void sortTimeBlocksNatural(List<TimeBlock> blocks) {
		Collections.sort(blocks, new Comparator<TimeBlock>() { // Sort the Time Blocks
			public int compare(TimeBlock tb1, TimeBlock tb2) {
				if (tb1 != null && tb2 != null)
					return tb1.getBeginTimestamp().compareTo(tb2.getBeginTimestamp());
				return 0;
			}
		});
	}

    /**
     *
     * @param shift The shift interval - need to examine the time block to determine how many hours are eligible per block.
     * @param blockIntervals Intervals for each block present in the blocks list. Passed here to avoid re computation.
     * @param blocks The blocks we are applying hours to.
     * @param previousBlocks If present, this is the list of time blocks from a previous "day", on which the initial hours (from previous day) should be placed.
     * @param initialHours hours accumulated from a previous boundary that need to be applied here (NOT SUBJECT TO INTERVAL)
     * @param hours hours to apply
     * @param earnCode what earn code to create time hour detail entry for.
     */
	void applyPremium(Interval shift, List<Interval> blockIntervals, List<TimeBlock> blocks, List<TimeBlock> previousBlocks, BigDecimal initialHours, BigDecimal hours, String earnCode) {
		for (int i=0; i<blocks.size(); i++) {
			TimeBlock b = blocks.get(i);

            // Only apply initial hours to the first timeblock.
			if (i == 0 && (initialHours.compareTo(BigDecimal.ZERO) > 0)) {
                // ONLY if they're on the same document ID, do we apply to previous,
                // otherwise we dump all on the current document.
                if (previousBlocks != null && previousBlocks.size() > 0 && previousBlocks.get(0).getDocumentId().equals(b.getDocumentId())) {
                    for (TimeBlock pb : previousBlocks) {
                        BigDecimal lunchSub = this.negativeTimeHourDetailSum(pb); // A negative number
                        initialHours = BigDecimal.ZERO.max(initialHours.add(lunchSub)); // We don't want negative premium hours!
                        if (initialHours.compareTo(BigDecimal.ZERO) <= 0) // check here now as well, we may not have anything at all to apply.
                            break;

                        // Adjust hours on the block by the lunch sub hours, so we're not over applying.
                        BigDecimal hoursToApply = initialHours.min(pb.getHours().add(lunchSub));
                        addPremiumTimeHourDetail(pb, hoursToApply, earnCode);
                        initialHours = initialHours.subtract(hoursToApply, TkConstants.MATH_CONTEXT);
                        if (initialHours.compareTo(BigDecimal.ZERO) <= 0)
                            break;
                    }
                } else {
				    addPremiumTimeHourDetail(b, initialHours, earnCode);
                }
            }

            BigDecimal lunchSub = this.negativeTimeHourDetailSum(b); // A negative number
            hours = BigDecimal.ZERO.max(hours.add(lunchSub)); // We don't want negative premium hours!

			if (hours.compareTo(BigDecimal.ZERO) > 0) {
                Interval blockInterval = blockIntervals.get(i);
                Interval overlapInterval = shift.overlap(blockInterval);
                if (overlapInterval == null)
                    continue;

                long overlap = overlapInterval.toDurationMillis();
                BigDecimal hoursMax = TKUtils.convertMillisToHours(overlap); // Maximum number of possible hours applicable for this time block and shift rule
                // Adjust this time block's hoursMax (below) by lunchSub to
                // make sure the time applied is the correct amount per block.
                BigDecimal hoursToApply = hours.min(hoursMax.add(lunchSub));

                addPremiumTimeHourDetail(b, hoursToApply, earnCode);
				hours = hours.subtract(hoursToApply, TkConstants.MATH_CONTEXT);
			}
		}
	}

	void addPremiumTimeHourDetail(TimeBlock block, BigDecimal hours, String earnCode) {
		List<TimeHourDetail> details = block.getTimeHourDetails();
		TimeHourDetail premium = new TimeHourDetail();
		premium.setHours(hours);
		premium.setEarnCode(earnCode);
		premium.setTkTimeBlockId(block.getTkTimeBlockId());
		details.add(premium);
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

		return (gapHours.compareTo(maxGap) > 0);
	}

	public void setShiftDifferentialRuleDao(ShiftDifferentialRuleDao shiftDifferentialRuleDao) {
		this.shiftDifferentialRuleDao = shiftDifferentialRuleDao;
	}

	@Override
	public ShiftDifferentialRule getShiftDifferentialRule(String tkShiftDifferentialRuleId) {
		return this.shiftDifferentialRuleDao.findShiftDifferentialRule(tkShiftDifferentialRuleId);
	}

	@Override
	public List<ShiftDifferentialRule> getShiftDifferentalRules(String location, String hrSalGroup, String payGrade, String pyCalendarGroup, Date asOfDate) {
		List<ShiftDifferentialRule> sdrs = new ArrayList<ShiftDifferentialRule>();

		// location, sal group, pay grade

	    sdrs.addAll(shiftDifferentialRuleDao.findShiftDifferentialRules(location, hrSalGroup, payGrade, pyCalendarGroup, asOfDate));

		// location, sal group, *
		sdrs.addAll(shiftDifferentialRuleDao.findShiftDifferentialRules(location, hrSalGroup, "%", pyCalendarGroup, asOfDate));

		// location, *, pay grade
		sdrs.addAll(shiftDifferentialRuleDao.findShiftDifferentialRules(location, "%", payGrade, pyCalendarGroup, asOfDate));

		// location, *, *
		sdrs.addAll(shiftDifferentialRuleDao.findShiftDifferentialRules(location, "%", "%", pyCalendarGroup, asOfDate));

		// *, sal group, pay grade
		sdrs.addAll(shiftDifferentialRuleDao.findShiftDifferentialRules("%", hrSalGroup, payGrade, pyCalendarGroup, asOfDate));

		// *, sal group, *
		sdrs.addAll(shiftDifferentialRuleDao.findShiftDifferentialRules("%", hrSalGroup, "%", pyCalendarGroup, asOfDate));

		// *, *, pay grade
		sdrs.addAll(shiftDifferentialRuleDao.findShiftDifferentialRules("%", "%", payGrade, pyCalendarGroup, asOfDate));

		// *, *, *
		sdrs.addAll(shiftDifferentialRuleDao.findShiftDifferentialRules("%", "%", "%", pyCalendarGroup, asOfDate));

		if (sdrs == null)
			sdrs = Collections.emptyList();

		return sdrs;
	}

	private boolean dayIsRuleActive(DateTime currentDate, ShiftDifferentialRule sdr) {
		boolean active = false;

		switch (currentDate.getDayOfWeek()) {
		case DateTimeConstants.MONDAY:
			active = sdr.isMonday();
			break;
		case DateTimeConstants.TUESDAY:
			active = sdr.isTuesday();
			break;
		case DateTimeConstants.WEDNESDAY:
			active = sdr.isWednesday();
			break;
		case DateTimeConstants.THURSDAY:
			active = sdr.isThursday();
			break;
		case DateTimeConstants.FRIDAY:
			active = sdr.isFriday();
			break;
		case DateTimeConstants.SATURDAY:
			active = sdr.isSaturday();
			break;
		case DateTimeConstants.SUNDAY:
			active = sdr.isSunday();
			break;
		}

		return active;
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
