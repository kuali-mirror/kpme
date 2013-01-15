package org.kuali.hr.lm.accrual.service;

import java.util.Date;

import org.kuali.hr.time.calendar.CalendarEntries;

public interface AccrualCategoryMaxCarryOverService {

	boolean exceedsAccrualCategoryMaxCarryOver(String accrualCategory, String principalId, CalendarEntries calendarEntry, Date asOfDate);

	void calculateMaxCarryOver(String documentId, String principalId, CalendarEntries calendarEntry, Date asOfDate);

}