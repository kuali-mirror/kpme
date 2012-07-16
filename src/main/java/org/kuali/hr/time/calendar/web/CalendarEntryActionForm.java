package org.kuali.hr.time.calendar.web;

import org.kuali.hr.time.base.web.TkForm;

public class CalendarEntryActionForm extends TkForm {
	/**
     *
     */
	private static final long serialVersionUID = 385904747462568474L;
	private Long hrPyCalendarEntryId;
	private Integer noOfPeriods;
    private String calendarEntryPeriodType;
	private String message;

	public Long getHrPyCalendarEntryId() {
		return hrPyCalendarEntryId;
	}

	public void setHrPyCalendarEntryId(Long hrPyCalendarEntryId) {
		this.hrPyCalendarEntryId = hrPyCalendarEntryId;
	}

	public Integer getNoOfPeriods() {
		return noOfPeriods;
	}

	public void setNoOfPeriods(Integer noOfPeriods) {
		this.noOfPeriods = noOfPeriods;
	}

    public String getCalendarEntryPeriodType() {
        return calendarEntryPeriodType;
    }

    public void setCalendarEntryPeriodType(String calendarEntryPeriodType) {
        this.calendarEntryPeriodType = calendarEntryPeriodType;
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}