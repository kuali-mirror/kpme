/**
 * Copyright 2004-2013 The Kuali Foundation
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
package org.kuali.kpme.core.document.calendar;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.document.CalendarDocumentHeaderContract;

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
    CalendarEntry getCalendarEntry();

    /**
     * The the beginning date of the calendar entry for the document.
     *
     * @return asOfDate
     */
    LocalDate getAsOfDate();
    
}
