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
package org.kuali.kpme.core.api.document.calendar;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.assignment.Assignment;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntry;

import java.util.List;
import java.util.Map;

/**
 * <p>CalendarDocumentContract interface</p>
 *
 */
public interface CalendarDocumentContract {

	/**
	 * The CalendarDocumentHeader object the CalendarDocument is associated with
	 * 
	 * <p>
	 * documentHeader of a CalendarDocument
	 * </p>
	 * 
	 * @return documentHeader of CalendarDocument
	 */
	CalendarDocumentHeaderContract getDocumentHeader();

    /**
	 * The list of Assignment objects the CalendarDocument is associated with
	 * 
	 * <p>
	 * assignments of a CalendarDocument
	 * </p>
	 * 
	 * @return assignments of CalendarDocument
	 */
    Map<LocalDate, List<Assignment>> getAssignmentMap();

    List<Assignment> getAllAssignments();

    /**
	 * The CalendarEntry object the CalendarDocument is associated with
	 * 
	 * <p>
	 * calendarEntry of a CalendarDocument
	 * </p>
	 * 
	 * @return calendarEntry of CalendarDocument
	 */
    CalendarEntry getCalendarEntry();

    /**
	 * The beginning date of the calendar entry the CalendarDocument is associated with
	 * 
	 * <p>
	 * asOfDate of a CalendarDocument
	 * </p>
	 * 
	 * @return asOfDate of CalendarDocument
	 */
    LocalDate getAsOfDate();
    
}
