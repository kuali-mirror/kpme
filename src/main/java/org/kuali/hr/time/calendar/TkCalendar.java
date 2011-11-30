package org.kuali.hr.time.calendar;

import org.joda.time.DateTime;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.util.TkTimeBlockAggregate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TkCalendar extends CalendarParent {

    public TkCalendar(TkTimeBlockAggregate aggregate) {
        super(aggregate.getCalendarEntry());
        List<CalendarWeek> calendarWeeks = new ArrayList<CalendarWeek>();
//
//        if (aggregate != null) {
//            List<TkCalendarWeek> weeks = new ArrayList<TkCalendarWeek>();
//            tc.setPayCalEntry(aggregate.getCalendarEntry());

        int firstDay = 0;
        if (getBeginDateTime().getDayOfWeek() != 7) {
            firstDay = 0 - getBeginDateTime().getDayOfWeek();   // always render calendar weeks from Sundays
        }
        for (int i = 0; i < aggregate.numberOfAggregatedWeeks(); i++) {
            TkCalendarWeek week = new TkCalendarWeek();
            List<List<TimeBlock>> weekBlocks = aggregate.getWeekTimeBlocks(i);
            List<CalendarDay> days = new ArrayList<CalendarDay>(7);

            for (int j = 0; j < weekBlocks.size(); j++) {
                List<TimeBlock> dayBlocks = weekBlocks.get(j);
                // Create the individual days.
                TkCalendarDay day = new TkCalendarDay();
                day.setTimeblocks(dayBlocks);
                day.setDayNumberString(getDayNumberString(i * 7 + j + firstDay));
                day.setDayNumberDelta(Integer.toString(i * 7 + j + firstDay));
                assignDayLunchLabel(day);
                int dayIndex = i * 7 + j + firstDay;
                DateTime beginDateTemp = getBeginDateTime().plusDays(dayIndex);
                day.setGray(false);
                if (beginDateTemp.isBefore(getBeginDateTime().getMillis())
                        || beginDateTemp.isAfter(getEndDateTime().getMillis())) {
                    day.setGray(true);
                }
                if (getEndDateTime().getHourOfDay() == 0 && beginDateTemp.equals(getEndDateTime())) {
                    day.setGray(true);
                }
                days.add(day);
            }
            week.setDays(days);
            calendarWeeks.add(week);
        }
        setWeeks(calendarWeeks);
    }

    public void assignDayLunchLabel(TkCalendarDay day) {
        EarnCode ec = null;
        String label = "";
        for (TimeBlockRenderer tbr : day.getBlockRenderers()) {
            for (TimeHourDetailRenderer thdr : tbr.getDetailRenderers()) {
                if (thdr.getTitle().equals(TkConstants.LUNCH_EARN_CODE)) {
                    ec = TkServiceLocator.getEarnCodeService().getEarnCode(thdr.getTitle(), tbr.getTimeBlock().getBeginDate());
                    if (ec != null) {
                        label = ec.getDescription() + "-" + thdr.getHours();
                    }
                }
            }
            tbr.setLunchLabel(label);
            label = "";
        }
    }

    public void assignAssignmentStyle(Map<String, String> styleMap) {
        for (CalendarWeek aWeek : this.getWeeks()) {
            for (CalendarDay aDay : aWeek.getDays()) {
                for (TimeBlockRenderer tbr : ((TkCalendarDay)aDay).getBlockRenderers()) {
                    String assignmentKey = tbr.getTimeBlock().getAssignmentKey();
                    if (assignmentKey != null && styleMap.containsKey(assignmentKey)) {
                        tbr.setAssignmentClass(styleMap.get(assignmentKey));
                    } else {
                        tbr.setAssignmentClass("");
                    }
                }
            }
        }
    }

    private String getDayNumberString(int dayDelta) {
        StringBuilder b = new StringBuilder();

        if (getBeginDateTime().getMinuteOfDay() == 0) {
            DateTime currStart = getBeginDateTime().plusDays(dayDelta);
            b.append(currStart.toString("d"));
        } else {
            DateTime currStart = getBeginDateTime().plusDays(dayDelta);
            DateTime currEnd = getBeginDateTime().plusDays(dayDelta);

            b.append(currStart.toString("d"));
            b.append(currStart.toString("HH:mm"));
            b.append(" - ");
            b.append(currEnd.toString("d"));
            b.append(currStart.toString("HH:mm"));
        }

        return b.toString();
    }

}
