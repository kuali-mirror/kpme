/**
 * Copyright 2004-2012 The Kuali Foundation
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
package org.kuali.hr.time.holidaycalendar;

import java.util.ArrayList;
import java.util.List;

import org.kuali.hr.core.KPMEConstants;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

public class HolidayCalendar extends PersistableBusinessObjectBase {
    public static final String CACHE_NAME = KPMEConstants.APPLICATION_NAMESPACE_CODE + "/" + "HolidayCalendar";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String hrHolidayCalendarId;
	private String holidayCalendarGroup;
	private String descr;
	private boolean active = true;
	
	private List<HolidayCalendarDateEntry> dateEntries = new ArrayList<HolidayCalendarDateEntry>();


	public String getHrHolidayCalendarId() {
		return hrHolidayCalendarId;
	}


	public void setHrHolidayCalendarId(String hrHolidayCalendarId) {
		this.hrHolidayCalendarId = hrHolidayCalendarId;
	}


	public String getHolidayCalendarGroup() {
		return holidayCalendarGroup;
	}


	public void setHolidayCalendarGroup(String holidayCalendarGroup) {
		this.holidayCalendarGroup = holidayCalendarGroup;
	}


	public String getDescr() {
		return descr;
	}


	public void setDescr(String descr) {
		this.descr = descr;
	}


	public List<HolidayCalendarDateEntry> getDateEntries() {
		return dateEntries;
	}


	public void setDateEntries(List<HolidayCalendarDateEntry> dateEntries) {
		this.dateEntries = dateEntries;
	}


	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}


}
