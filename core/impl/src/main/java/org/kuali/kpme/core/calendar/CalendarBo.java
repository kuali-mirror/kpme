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
package org.kuali.kpme.core.calendar;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalTime;
import org.kuali.kpme.core.api.calendar.Calendar;
import org.kuali.kpme.core.api.calendar.CalendarContract;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

import java.sql.Time;

public class CalendarBo extends PersistableBusinessObjectBase implements CalendarContract {
    public static final String CACHE_NAME = HrConstants.CacheNamespace.NAMESPACE_PREFIX + "Calendar";
    public static final ModelObjectUtils.Transformer<CalendarBo, Calendar> toCalendar =
            new ModelObjectUtils.Transformer<CalendarBo, Calendar>() {
                public Calendar transform(CalendarBo input) {
                    return CalendarBo.to(input);
                };
            };
    public static final ModelObjectUtils.Transformer<Calendar, CalendarBo> toCalendarBo =
            new ModelObjectUtils.Transformer<Calendar, CalendarBo>() {
                public CalendarBo transform(Calendar input) {
                    return CalendarBo.from(input);
                };
            };
    public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
            .add("calendarName")
            .build();
    /**
     *
     */
	private static final long serialVersionUID = 1L;

	private String hrCalendarId;
	private String calendarName;
	private String calendarDescriptions;

	private String flsaBeginDay;
	private Time flsaBeginTime;
	private String calendarTypes;
	private int flsaBeginDayConstant = -1;

	public String getHrCalendarId() {
		return hrCalendarId;
	}

	public void setHrCalendarId(String hrCalendarId) {
		this.hrCalendarId = hrCalendarId;
	}

	public String getCalendarName() {
		return calendarName;
	}

	public void setCalendarName(String calendarName) {
		this.calendarName = calendarName;
	}

	public String getCalendarTypes() {
		return calendarTypes;
	}

	public void setCalendarTypes(String calendarTypes) {
		this.calendarTypes = calendarTypes;
	}

	public void setFlsaBeginDayConstant(int flsaBeginDayConstant) {
		this.flsaBeginDayConstant = flsaBeginDayConstant;
	}

	public String getCalendarDescriptions() {
		return calendarDescriptions;
	}

	public void setCalendarDescriptions(String calendarDescriptions) {
		this.calendarDescriptions = calendarDescriptions;
	}

	public String getFlsaBeginDay() {
		return flsaBeginDay;
	}

	public void setFlsaBeginDay(String flsaBeginDay) {
		this.flsaBeginDay = flsaBeginDay;
		this.setFlsaBeinDayConstant(flsaBeginDay);
	}

	public Time getFlsaBeginTime() {
		return flsaBeginTime;
	}

    public LocalTime getFlsaBeginLocalTime() {
        return getFlsaBeginTime() == null ? null : LocalTime.fromMillisOfDay(getFlsaBeginTime().getTime());
    }

	public void setFlsaBeginTime(Time flsaBeginTime) {
		this.flsaBeginTime = flsaBeginTime;
	}

	/**
	 * This method sets a constant matching those listed in
	 * org.joda.time.DateTimeConstants for day comparisons.
	 *
	 * Currently this is 'hard-coded' to be English specific, it would
	 * be trivial to change and support more than one language/day naming
	 * convention.
	 *
	 * @param day
	 */
	private void setFlsaBeinDayConstant(String day) {
		if (!StringUtils.isEmpty(day)) {
			day = day.toLowerCase().trim();

			if (day.startsWith("m")) {
				this.flsaBeginDayConstant = DateTimeConstants.MONDAY;
			} else if (day.startsWith("tu")) {
				this.flsaBeginDayConstant = DateTimeConstants.TUESDAY;
			} else if (day.startsWith("w")) {
				this.flsaBeginDayConstant = DateTimeConstants.WEDNESDAY;
			} else if (day.startsWith("th")) {
				this.flsaBeginDayConstant = DateTimeConstants.THURSDAY;
			} else if (day.startsWith("f")) {
				this.flsaBeginDayConstant = DateTimeConstants.FRIDAY;
			} else if (day.startsWith("sa")) {
				this.flsaBeginDayConstant = DateTimeConstants.SATURDAY;
			} else if (day.startsWith("su")) {
				this.flsaBeginDayConstant = DateTimeConstants.SUNDAY;
			}
		}
	}

	/**
	 * org.joda.time.DateTimeConstants.MONDAY
	 * ...
	 * org.joda.time.DateTimeConstants.SUNDAY
	 *
	 * @return an int representing the FLSA start day, sourced from
	 * org.joda.time.DateTimeConstants in the interval [1,7].
	 */
	public int getFlsaBeginDayConstant() {
		if (flsaBeginDayConstant < 0) {
			this.setFlsaBeinDayConstant(this.getFlsaBeginDay());
		}
		return flsaBeginDayConstant;
	}

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof CalendarBo) {
            CalendarBo pc = (CalendarBo)o;
            return this.getHrCalendarId().compareTo(pc.getHrCalendarId()) == 0;
        } else {
            return false;
        }
    }

    public static CalendarBo from(Calendar im) {
        if (im == null) {
            return null;
        }
        CalendarBo cal = new CalendarBo();
        cal.setHrCalendarId(im.getHrCalendarId());
        cal.setCalendarName(im.getCalendarName());
        cal.setCalendarDescriptions(im.getCalendarDescriptions());
        cal.setFlsaBeginDay(im.getFlsaBeginDay());
        cal.setFlsaBeginTime(im.getFlsaBeginLocalTime() == null ? null : new Time(im.getFlsaBeginLocalTime().toDateTimeToday().getMillisOfDay()));
        cal.setCalendarTypes(im.getCalendarTypes());
        cal.setFlsaBeginDayConstant(im.getFlsaBeginDayConstant());

        cal.setVersionNumber(im.getVersionNumber());
        cal.setObjectId(im.getObjectId());

        return cal;
    }

    public static Calendar to(CalendarBo bo) {
        if (bo == null) {
            return null;
        }

        return Calendar.Builder.create(bo).build();
    }
}
