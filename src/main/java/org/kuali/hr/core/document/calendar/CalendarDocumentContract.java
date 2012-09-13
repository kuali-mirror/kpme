package org.kuali.hr.core.document.calendar;


import org.kuali.hr.core.document.CalendarDocumentHeaderContract;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.calendar.CalendarEntries;

import java.sql.Date;
import java.util.List;

public interface CalendarDocumentContract {
    /**
     * The document header for the Document.
     *
     * @return documentHeader
     */
    CalendarDocumentHeaderContract getDocumentHeader();

    /**
     * The list of assignments for the Document.
     *
     * @return assignments
     */
    List<Assignment> getAssignments();

    /**
     * The calendar entry for the Document.
     *
     * @return calendarEntry
     */
    CalendarEntries getCalendarEntry();

    /**
     * The the beginning date of the calendar entry for the document.
     *
     * @return asOfDate
     */
    Date getAsOfDate();
}
