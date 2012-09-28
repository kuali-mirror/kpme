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

import java.math.BigDecimal;
import java.sql.Date;

import org.kuali.hr.core.KPMEConstants;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

public class HolidayCalendarDateEntry extends PersistableBusinessObjectBase {
    public static final String CACHE_NAME = KPMEConstants.APPLICATION_NAMESPACE_CODE + "/" + "HolidayCalendarDateEntry";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String hrHolidayCalendarDateId;
	private Date holidayDate;
	private String holidayDescr;
	private String hrHolidayCalendarId;
	private BigDecimal holidayHours;


	public String getHrHolidayCalendarDateId() {
		return hrHolidayCalendarDateId;
	}


	public void setHrHolidayCalendarDateId(String hrHolidayCalendarDateId) {
		this.hrHolidayCalendarDateId = hrHolidayCalendarDateId;
	}


	public Date getHolidayDate() {
		return holidayDate;
	}


	public void setHolidayDate(Date holidayDate) {
		this.holidayDate = holidayDate;
	}


	public String getHolidayDescr() {
		return holidayDescr;
	}


	public void setHolidayDescr(String holidayDescr) {
		this.holidayDescr = holidayDescr;
	}


	public String getHrHolidayCalendarId() {
		return hrHolidayCalendarId;
	}


	public void setHrHolidayCalendarId(String hrHolidayCalendarId) {
		this.hrHolidayCalendarId = hrHolidayCalendarId;
	}


	public BigDecimal getHolidayHours() {
		return holidayHours;
	}


	public void setHolidayHours(BigDecimal holidayHours) {
		this.holidayHours = holidayHours;
	}

}
