package org.kuali.kpme.tklm.time.rules.shiftdifferential.shift;


import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.time.rules.shiftdifferential.ShiftDifferentialRule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ShiftCalendarInterval {
    private Set<Long> jobNumbers;
    private ShiftDifferentialRule rule;
    private List<Shift> shifts;

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
            shifts.add(new Shift(rule, shiftInterval, zone));
            shiftInterval = incrementShift(shiftInterval);
        }
        return shifts;
    }

    protected Interval incrementShift(Interval current) {
        return new Interval(current.getStart().plusDays(1), current.getEnd().plusDays(1));
    }

    public void placeTimeBlocks(List<TimeBlock> timeBlocks, DateTimeZone zone) {
        for (TimeBlock tb : timeBlocks) {
            if (getJobNumbers().contains(tb.getJobNumber())) {
                int counter = 0;  // only possible for timeblock to overlap two shifts
                Interval tbInterval = new Interval(tb.getBeginDateTime(), tb.getEndDateTime());
                for (Shift shift : getShifts()) {
                    if (shift.getShiftInterval().overlaps(tbInterval)) {
                        shift.addShiftBlock(tb);
                        counter++;
                    }
                    if (counter >= 2) {
                        break;
                    }
                }
            }
        }
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

    public boolean exceedsMinHours(long duration) {
        return rule.getMinHours().compareTo(TKUtils.convertMillisToHours(duration)) < 0;
    }
}
