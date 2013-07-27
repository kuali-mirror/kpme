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

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.core.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.document.CalendarDocumentHeaderContract;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.TKUtils;

public abstract class CalendarDocument implements Serializable {

	protected CalendarDocumentHeader documentHeader;
	protected List<Assignment> assignments = new LinkedList<Assignment>();
	protected CalendarEntry calendarEntry = null;
	protected LocalDate asOfDate;
	protected String calendarType;
	
	public abstract CalendarDocumentHeader getDocumentHeader();

	public abstract List<Assignment> getAssignments();

	public abstract CalendarEntry getCalendarEntry();

	public abstract LocalDate getAsOfDate();	
	
	public String getDocumentId() {
		if(documentHeader != null)
			return documentHeader.getDocumentId();
		else
			return null;
	}
	
	public String getPrincipalId() {
		if(documentHeader != null)
			return documentHeader.getPrincipalId();
		else
			return null;
	}

    public String getCalendarType() {
    	return calendarType;
    }
    
    public void setCalendarType(String calendarType) {
    	this.calendarType = calendarType;
    }
    
    public Map<String, String> getAssignmentDescriptions() {
   	 Map<String, String> assignmentDescriptions = new LinkedHashMap<String, String>();
     for (Assignment assignment : assignments) {
             assignmentDescriptions.putAll(TKUtils.formatAssignmentDescription(assignment));
     }
     return assignmentDescriptions;
    }
    
    public Assignment getAssignment(AssignmentDescriptionKey assignmentDescriptionKey) {

        for (Assignment assignment : assignments) {
            if (assignment.getJobNumber().compareTo(assignmentDescriptionKey.getJobNumber()) == 0 &&
                    assignment.getWorkArea().compareTo(assignmentDescriptionKey.getWorkArea()) == 0 &&
                    assignment.getTask().compareTo(assignmentDescriptionKey.getTask()) == 0) {
                return assignment;
            }
        }

        //No assignment found so fetch the inactive ones for this payBeginDate
        Assignment foundAssign = HrServiceLocator.getAssignmentService().getAssignment(assignmentDescriptionKey, calendarEntry.getBeginPeriodFullDateTime().toLocalDate());
        if (foundAssign != null) {
            return foundAssign;
        }
        else
        	return null;
    }

}
