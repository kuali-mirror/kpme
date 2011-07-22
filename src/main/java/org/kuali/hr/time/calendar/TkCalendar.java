package org.kuali.hr.time.calendar;

import org.joda.time.DateTime;
import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.util.TkTimeBlockAggregate;

import java.util.ArrayList;
import java.util.List;

public class TkCalendar {
	private List<TkCalendarWeek> weeks = new ArrayList<TkCalendarWeek>();
	private PayCalendarEntries payCalEntry;
    private DateTime beginDateTime;
    private DateTime endDateTime;

    public static TkCalendar getCalendar(TkTimeBlockAggregate aggregate) {
        TkCalendar tc = new TkCalendar();

        if (aggregate != null) {
            List<TkCalendarWeek> weeks = new ArrayList<TkCalendarWeek>();
            tc.setPayCalEntry(aggregate.getPayCalendarEntry());
            for (int i=0; i<aggregate.numberOfAggregatedWeeks(); i++) {
                TkCalendarWeek week = new TkCalendarWeek();
                List<List<TimeBlock>> weekBlocks = aggregate.getWeekTimeBlocks(i);
                List<TkCalendarDay> days = new ArrayList<TkCalendarDay>(7);

                for (int j=0; j<weekBlocks.size(); j++) {
                    List<TimeBlock> dayBlocks = weekBlocks.get(j);
                    TkCalendarDay day = new TkCalendarDay();
                    day.setTimeblocks(dayBlocks);
                    day.setDayNumberString(tc.getDayNumberString(i * 7 + j));
                    days.add(day);
                }
                week.setDays(days);
                weeks.add(week);
            }
            tc.setWeeks(weeks);
        } else {
            return null;
        }

        return tc;
    }

	public List<TkCalendarWeek> getWeeks() {
		return weeks;
	}

	public void setWeeks(List<TkCalendarWeek> weeks) {
		this.weeks = weeks;
	}

	public PayCalendarEntries getPayCalEntry() {
		return payCalEntry;
	}

	public void setPayCalEntry(PayCalendarEntries payCalEntry) {
		this.payCalEntry = payCalEntry;
        this.beginDateTime = new DateTime(payCalEntry.getBeginPeriodDateTime(), TkConstants.SYSTEM_DATE_TIME_ZONE);
        this.endDateTime = new DateTime(payCalEntry.getEndPeriodDateTime(), TkConstants.SYSTEM_DATE_TIME_ZONE);
	}

    public DateTime getBeginDateTime() {
        return beginDateTime;
    }

    public DateTime getEndDateTime() {
        return endDateTime;
    }

    /**
     * Provides the calendar title / heading. If the Pay Calendar entry spans
     * multiple months, you get Abbreviated Month name + year of both the
     * beginning month and the ending month.
     *
     * @return String for calendar title use.
     */
    public String getCalendarTitle() {
        StringBuilder sb = new StringBuilder();

        if (getBeginDateTime().getMonthOfYear() == getEndDateTime().getMonthOfYear()) {
            sb.append(getBeginDateTime().toString("MMMM y"));
        } else {
            sb.append(getBeginDateTime().toString("MMM y"));
            sb.append(" - ");
            sb.append(getEndDateTime().toString("MMM y"));
        }

        return sb.toString();
    }

    /**
     * Assumption of 7 "days" per week, or 7 "blocks" per row.
     * @return A list of string titles for each row block (day)
     */
    public List<String> getCalendarDayHeadings() {
        List<String> dayStrings = new ArrayList<String>(7);

        if (getBeginDateTime().getMinuteOfDay() == 0) {
            // "Standard" days.
            for (int i=0; i<7; i++) {
                DateTime currDay = getBeginDateTime().plusDays(i);
                dayStrings.add(currDay.toString("E"));
            }
        } else {
            // Day Split Strings
            StringBuilder builder = new StringBuilder();
            for (int i=0; i<7; i++) {
                DateTime currStart = getBeginDateTime().plusDays(i);
                DateTime currEnd = getBeginDateTime().plusDays(i);

                builder.append(currStart.toString("E HH:mm"));
                builder.append(" - ");
                builder.append(currEnd.toString("E HH:mm"));

                dayStrings.add(builder.toString());
            }
        }

        return dayStrings;
    }

    private String getDayNumberString(int dayDelta) {
        StringBuilder b = new StringBuilder();

        if (getBeginDateTime().getMinuteOfDay() == 0) {
            DateTime currStart = getBeginDateTime().plusDays(dayDelta);
            b.append(currStart.toString("d")).append(getSuffix(currStart.getDayOfMonth()));
        } else {
            DateTime currStart = getBeginDateTime().plusDays(dayDelta);
            DateTime currEnd = getBeginDateTime().plusDays(dayDelta);

            b.append(currStart.toString("d")).append(getSuffix(currStart.getDayOfMonth()));
            b.append(currStart.toString("HH:mm"));
            b.append(" - ");
            b.append(currEnd.toString("d")).append(getSuffix(currEnd.getDayOfMonth()));
            b.append(currStart.toString("HH:mm"));
        }

        return b.toString();
    }

    private String getSuffix(int dayOfMonth) {
        String ret;
        String dString = dayOfMonth + "";

        if (dayOfMonth > 9 && dayOfMonth < 20)
            ret = "th";
        else if (dString.endsWith("1"))
            ret = "st";
        else if (dString.endsWith("2"))
            ret = "nd";
        else if (dString.endsWith("3"))
            ret = "rd";
        else
            ret = "th";

        return ret;
    }

}
