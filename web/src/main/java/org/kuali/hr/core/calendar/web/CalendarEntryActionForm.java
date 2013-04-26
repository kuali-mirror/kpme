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
package org.kuali.hr.core.calendar.web;

import org.kuali.hr.core.TkForm;

public class CalendarEntryActionForm extends TkForm {

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