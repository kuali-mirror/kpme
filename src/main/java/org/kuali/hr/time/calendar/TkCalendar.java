package org.kuali.hr.time.calendar;

import org.joda.time.DateTime;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.util.TkTimeBlockAggregate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
                    // Create the individual days.
                    TkCalendarDay day = new TkCalendarDay();
                    day.setTimeblocks(dayBlocks);
                    day.setDayNumberString(tc.getDayNumberString(i * 7 + j));
                    day.setDayNumberDelta(i * 7 + j);
                    assignDayLunchLabel(day);
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

    public static void assignDayLunchLabel(TkCalendarDay day) {
    	EarnCode ec = null;
		String label = "";
		for(TimeBlockRenderer tbr : day.getBlockRenderers()) {
			for(TimeHourDetailRenderer thdr : tbr.getDetailRenderers()) {
				if(thdr.getTitle().equals(TkConstants.LUNCH_EARN_CODE)) {
					ec = TkServiceLocator.getEarnCodeService().getEarnCode(thdr.getTitle(), tbr.getTimeBlock().getBeginDate());
					if(ec != null) {
						label = ec.getDescription() + "-" + thdr.getHours();
					}
				}
			}
			tbr.setLunchLabel(label);
			label = "";
		}
    }

    public void assignAssignmentStyle(Map<String, String> styleMap) {
    	for(TkCalendarWeek aWeek : this.getWeeks()) {
    		for(TkCalendarDay aDay: aWeek.getDays()) {
				for(TimeBlockRenderer tbr: aDay.getBlockRenderers()) {
					String assignmentKey = tbr.getTimeBlock().getAssignmentKey();
					if(assignmentKey != null && styleMap.containsKey(assignmentKey)) {
		            	tbr.setAssignmentClass(styleMap.get(assignmentKey));
		            } else {
		            	tbr.setAssignmentClass("");
		            }
				}
    		}
    	}
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
        // Relative time, with time zone added.
        this.beginDateTime = payCalEntry.getBeginLocalDateTime().toDateTime(TkServiceLocator.getTimezoneService().getUserTimezoneWithFallback());
        this.endDateTime = payCalEntry.getEndLocalDateTime().toDateTime(TkServiceLocator.getTimezoneService().getUserTimezoneWithFallback());
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

        if (getBeginDateTime().getMonthOfYear() == getEndDateTime().getMonthOfYear() ||
                (getBeginDateTime().getMonthOfYear() != getEndDateTime().getMonthOfYear()
                    && getEndDateTime().getDayOfMonth() == 1 && getEndDateTime().getSecondOfDay() == 0) )
        {
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
