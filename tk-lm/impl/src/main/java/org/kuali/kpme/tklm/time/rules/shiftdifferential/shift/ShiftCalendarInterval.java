/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.tklm.time.rules.shiftdifferential.shift;


import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.time.rules.shiftdifferential.ShiftDifferentialRule;
import org.kuali.kpme.tklm.time.rules.shiftdifferential.service.ShiftTypeService;
import org.kuali.kpme.tklm.time.rules.shiftdifferential.service.ShiftTypeServiceBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ShiftCalendarInterval {
    private Set<Long> jobNumbers;
    private ShiftDifferentialRule rule;
    private List<Shift> shifts;
    private ShiftTypeService shiftTypeService;

    public ShiftCalendarInterval(Set<Long> jobNumbers, ShiftDifferentialRule rule, LocalDateTime spanBegin, LocalDateTime spanEnd, DateTimeZone zone) {
        this.jobNumbers = jobNumbers;
        this.shifts = createShifts(rule, spanBegin, spanEnd, zone);
        this.rule = rule;
    }

    public Set<Long> getJobNumbers() {
        return jobNumbers;
    }

    public void setJobNumbers(Set<Long> jobNumbers) {
        this.jobNumbers = jobNumbers;
    }

    public List<Shift> getShifts() {
        return shifts;
    }

    public void setShifts(List<Shift> shifts) {
        this.shifts = shifts;
    }

    public ShiftDifferentialRule getRule() {
        return rule;
    }

    public void setRule(ShiftDifferentialRule rule) {
        this.rule = rule;
    }

    protected List<Shift> createShifts(ShiftDifferentialRule rule, LocalDateTime spanBegin, LocalDateTime spanEnd, DateTimeZone zone) {
        DateTime spanBeginDT = spanBegin.toDateTime(zone);
        DateTime spanEndDT = spanEnd.toDateTime(zone);
        Interval calendarEntryInterval = new Interval(spanBeginDT, spanEndDT);
        DateTime shiftEnd = LocalTime.fromDateFields(rule.getEndTime()).toDateTime(spanBeginDT).minusDays(1);
        DateTime shiftStart = LocalTime.fromDateFields(rule.getBeginTime()).toDateTime(spanBeginDT).minusDays(1);

        if (shiftEnd.isBefore(shiftStart) || shiftEnd.isEqual(shiftStart)) {
            shiftEnd = shiftEnd.plusDays(1);
        }

        List<Shift> shifts = new ArrayList<Shift>();
        Interval shiftInterval = new Interval(shiftStart, shiftEnd);
        //possible that there is no overlap between 1st interval and cal entry interval... if so, add a day.
        if (!calendarEntryInterval.overlaps(shiftInterval)) {
            shiftInterval = incrementShift(shiftInterval);
        }
        while (calendarEntryInterval.overlaps(shiftInterval)) {
            if (ruleIsActiveForDay(shiftInterval.getStart(), rule)) {
                shifts.add(new Shift(rule, shiftInterval, zone));
            }
            shiftInterval = incrementShift(shiftInterval);
        }
        return shifts;
    }

    protected Interval incrementShift(Interval current) {
        return new Interval(current.getStart().plusDays(1), current.getEnd().plusDays(1));
    }

    public void placeTimeBlocks(List<TimeBlock> timeBlocks) {
        getShiftTypeService().placeTimeBlocks(this, timeBlocks);
    }

    protected boolean ruleIsActiveForDay(DateTime currentDate, ShiftDifferentialRule sdr) {
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

    public Map<TimeBlock, List<ShiftBlock>> getTimeBlockMap() {
        Map<TimeBlock, List<ShiftBlock>> shiftBlocksForTimeBlock = new HashMap<>();

        for (Shift shift: getShifts()) {
            for (ShiftBlock sb : shift.getShiftBlocks())  {
                if (sb.isApplyPremium()) {
                    if (shiftBlocksForTimeBlock.containsKey(sb.getTimeBlock())) {
                        shiftBlocksForTimeBlock.get(sb.getTimeBlock()).add(sb);
                    } else {
                        List<ShiftBlock> tempList = new ArrayList<ShiftBlock>();
                        tempList.add(sb);
                        shiftBlocksForTimeBlock.put(sb.getTimeBlock(), tempList);
                    }
                }
            }
        }
        return shiftBlocksForTimeBlock;
    }

    protected ShiftTypeService getShiftTypeService() {
        if (shiftTypeService == null) {
            if (rule.getRuleTypeObj() == null) {
                //fall back to base logic
                shiftTypeService = new ShiftTypeServiceBase();
            } else {
                shiftTypeService = rule.getRuleTypeObj().getShiftTypeService();
            }
        }
        return shiftTypeService;
    }
}
