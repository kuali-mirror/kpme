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
package org.kuali.kpme.core.document.calendar;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.assignment.Assignment;
import org.kuali.kpme.core.api.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.api.document.calendar.CalendarDocumentContract;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.TKUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class CalendarDocument implements Serializable, CalendarDocumentContract{
	private static final long serialVersionUID = 6074564807962995821L;
	protected CalendarDocumentHeader documentHeader;
	protected Map<LocalDate, List<Assignment>> assignments = new HashMap<LocalDate, List<Assignment>>();
	protected CalendarEntry calendarEntry = null;
	protected LocalDate asOfDate;
	protected String calendarType;
	
	public abstract CalendarDocumentHeader getDocumentHeader();

	public abstract Map<LocalDate, List<Assignment>> getAssignmentMap();

    public List<Assignment> getAllAssignments() {
        if (MapUtils.isEmpty(getAssignmentMap())) {
            return Collections.emptyList();
        }
        Set<Assignment> allAssignments = new HashSet<Assignment>();
        for (List<Assignment> assignList : getAssignmentMap().values()) {
            allAssignments.addAll(assignList);
        }
        return new ArrayList<Assignment>(allAssignments);
    }

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
    
    public Map<String, String> getAssignmentDescriptions(LocalDate date) {
        Map<String, String> assignmentDescriptions = new LinkedHashMap<String, String>();
        List<Assignment> dayAssignments = getAssignmentMap().get(date);
        if (CollectionUtils.isNotEmpty(dayAssignments)) {
            for (Assignment assignment : dayAssignments) {
                assignmentDescriptions.putAll(TKUtils.formatAssignmentDescription(assignment));
            }
        }
        return assignmentDescriptions;
    }
    
    public Assignment getAssignment(AssignmentDescriptionKey assignmentDescriptionKey, LocalDate date) {
        List<Assignment> dayAssignments = getAssignmentMap().get(date);
        if (CollectionUtils.isNotEmpty(dayAssignments)) {
            for (Assignment assignment : dayAssignments) {
                if (StringUtils.equals(assignment.getGroupKeyCode(), assignmentDescriptionKey.getGroupKeyCode()) &&
                        assignment.getJobNumber().compareTo(assignmentDescriptionKey.getJobNumber()) == 0 &&
                        assignment.getWorkArea().compareTo(assignmentDescriptionKey.getWorkArea()) == 0 &&
                        assignment.getTask().compareTo(assignmentDescriptionKey.getTask()) == 0) {
                    return assignment;
                }
            }
        }

        //No assignment found so fetch the inactive ones for this payBeginDate
        Assignment foundAssign = HrServiceLocator.getAssignmentService().getAssignmentForTargetPrincipal(assignmentDescriptionKey, date);
        if (foundAssign != null) {
            return foundAssign;
        } else {
            return null;
        }
    }

}
