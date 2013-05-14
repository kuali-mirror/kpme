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
package org.kuali.kpme.core.bo.calendar.entry;

import java.sql.Time;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.kuali.kpme.core.KPMEConstants;
import org.kuali.kpme.core.bo.calendar.Calendar;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

public class CalendarEntry extends PersistableBusinessObjectBase implements Comparable<CalendarEntry>{

	private static final long serialVersionUID = -1977756526579659122L;

	public static final String CACHE_NAME = KPMEConstants.APPLICATION_NAMESPACE_CODE + "/" + "CalendarEntry";

    private String hrCalendarEntryId;
    private String hrCalendarId;
    private String calendarName;

    private Date beginPeriodDateTime;
    private Date endPeriodDateTime;
    private Date batchInitiateDateTime;
    private Date batchEndPayPeriodDateTime;
    private Date batchEmployeeApprovalDateTime;
    private Date batchSupervisorApprovalDateTime;

    private Calendar calendarObj;

    public String getHrCalendarId() {
        calendarObj = HrServiceLocator.getCalendarService().getCalendarByGroup(this.getCalendarName());
        if (calendarObj != null) {
            this.setHrCalendarId(calendarObj.getHrCalendarId());
        }
        return hrCalendarId;
    }

    public void setHrCalendarId(String hrCalendarId) {
        this.hrCalendarId = hrCalendarId;
    }

    public String getHrCalendarEntryId() {
		return hrCalendarEntryId;
	}

	public void setHrCalendarEntryId(String hrCalendarEntryId) {
		this.hrCalendarEntryId = hrCalendarEntryId;
	}

	public String getCalendarName() {
		return calendarName;
	}

	public void setCalendarName(String calendarName) {
		this.calendarName = calendarName;
	}
	
	public Date getBeginPeriodDateTime() {
        return beginPeriodDateTime;
    }

    public void setBeginPeriodDateTime(Date beginPeriodDateTime) {
        this.beginPeriodDateTime = beginPeriodDateTime;
    }

    public Date getBeginPeriodDate() {
    	return beginPeriodDateTime != null ? LocalDate.fromDateFields(beginPeriodDateTime).toDate() : null;
    }
    
    public void setBeginPeriodDate(Date beginPeriodDate) {
    	LocalDate localDate = beginPeriodDate != null ? LocalDate.fromDateFields(beginPeriodDate) : null;
    	LocalTime localTime = beginPeriodDateTime != null ? LocalTime.fromDateFields(beginPeriodDateTime) : LocalTime.MIDNIGHT;
    	beginPeriodDateTime = localDate != null ? localDate.toDateTime(localTime).toDate() : null;
    }
    
    public Time getBeginPeriodTime() {
    	return beginPeriodDateTime != null ? new Time(beginPeriodDateTime.getTime()) : null;
    }
    
    public void setBeginPeriodTime(Time beginPeriodTime) {
    	LocalDate localDate = beginPeriodDateTime != null ? LocalDate.fromDateFields(beginPeriodDateTime) : LocalDate.now();
    	LocalTime localTime = beginPeriodTime != null ? LocalTime.fromDateFields(beginPeriodTime) : null;
    	beginPeriodDateTime = localTime != null ? localTime.toDateTime(localDate.toDateTimeAtStartOfDay()).toDate() : null;
    }
    
    public DateTime getBeginPeriodFullDateTime() {
    	return beginPeriodDateTime != null ? new DateTime(beginPeriodDateTime) : null;
    }
    
    public void setBeginPeriodFullDateTime(DateTime beginPeriodFullDateTime) {
    	beginPeriodDateTime = beginPeriodFullDateTime != null ? beginPeriodFullDateTime.toDate() : null;
    }
    
    public LocalDateTime getBeginPeriodLocalDateTime() {
        return getBeginPeriodFullDateTime() != null ? getBeginPeriodFullDateTime().toLocalDateTime() : null;
    }
    
    public Date getEndPeriodDateTime() {
        return endPeriodDateTime;
    }

    public void setEndPeriodDateTime(Date endPeriodDateTime) {
        this.endPeriodDateTime = endPeriodDateTime;
    }

    public Date getEndPeriodDate() {
    	return endPeriodDateTime != null ? LocalDate.fromDateFields(endPeriodDateTime).toDate() : null;
    }
    
    public void setEndPeriodDate(Date endPeriodDate) {
    	LocalDate localDate = endPeriodDate != null ? LocalDate.fromDateFields(endPeriodDate) : null;
    	LocalTime localTime = endPeriodDateTime != null ? LocalTime.fromDateFields(endPeriodDateTime) : LocalTime.MIDNIGHT;
    	endPeriodDateTime = localDate != null ? localDate.toDateTime(localTime).toDate() : null;
    }

    public Time getEndPeriodTime() {
    	return endPeriodDateTime != null ? new Time(endPeriodDateTime.getTime()) : null;
    }
    
    public void setEndPeriodTime(Time endPeriodTime) {
    	LocalDate localDate = endPeriodDateTime != null ? LocalDate.fromDateFields(endPeriodDateTime) : LocalDate.now();
    	LocalTime localTime = endPeriodTime != null ? LocalTime.fromDateFields(endPeriodTime) : null;
    	endPeriodDateTime = localTime != null ? localTime.toDateTime(localDate.toDateTimeAtStartOfDay()).toDate() : null;
    }
    
    public DateTime getEndPeriodFullDateTime() {
    	return endPeriodDateTime != null ? new DateTime(endPeriodDateTime) : null;
    }
    
    public void setEndPeriodFullDateTime(DateTime endPeriodFullDateTime) {
    	endPeriodDateTime = endPeriodFullDateTime != null ? endPeriodFullDateTime.toDate() : null;
    }
    
    public LocalDateTime getEndPeriodLocalDateTime() {
        return getEndPeriodFullDateTime() != null ? getEndPeriodFullDateTime().toLocalDateTime() : null;
    }

    public Date getBatchInitiateDateTime() {
        return batchInitiateDateTime;
    }
    
    public void setBatchInitiateDateTime(Date batchInitiateDateTime) {
        this.batchInitiateDateTime = batchInitiateDateTime;
    }
    
    public Date getBatchInitiateDate() {
    	return batchInitiateDateTime != null ? LocalDate.fromDateFields(batchInitiateDateTime).toDate() : null;
    }
    
    public void setBatchInitiateDate(Date batchInitiateDate) {
    	LocalDate localDate = batchInitiateDate != null ? LocalDate.fromDateFields(batchInitiateDate) : null;
    	LocalTime localTime = batchInitiateDateTime != null ? LocalTime.fromDateFields(batchInitiateDateTime) : LocalTime.MIDNIGHT;
    	batchInitiateDateTime = localDate != null ? localDate.toDateTime(localTime).toDate() : null;
    }

    public Time getBatchInitiateTime() {
    	return batchInitiateDateTime != null ? new Time(batchInitiateDateTime.getTime()) : null;
    }
    
    public void setBatchInitiateTime(Time batchInitiateTime) {
    	LocalDate localDate = batchInitiateDateTime != null ? LocalDate.fromDateFields(batchInitiateDateTime) : LocalDate.now();
    	LocalTime localTime = batchInitiateTime != null ? LocalTime.fromDateFields(batchInitiateTime) : null;
    	batchInitiateDateTime = localTime != null ? localTime.toDateTime(localDate.toDateTimeAtStartOfDay()).toDate() : null;
    }
    
    public DateTime getBatchInitiateFullDateTime() {
    	return batchInitiateDateTime != null ? new DateTime(batchInitiateDateTime) : null;
    }
    
    public void setBatchInitiateFullDateTime(DateTime batchInitiateFullDateTime) {
    	batchInitiateDateTime = batchInitiateFullDateTime != null ? batchInitiateFullDateTime.toDate() : null;
    }

    public Date getBatchEndPayPeriodDateTime() {
        return batchEndPayPeriodDateTime;
    }

    public void setBatchEndPayPeriodDateTime(Date batchEndPayPeriodDateTime) {
        this.batchEndPayPeriodDateTime = batchEndPayPeriodDateTime;
    }
    
    public Date getBatchEndPayPeriodDate() {
    	return batchEndPayPeriodDateTime != null ? LocalDate.fromDateFields(batchEndPayPeriodDateTime).toDate() : null;
    }
    
    public void setBatchEndPayPeriodDate(Date batchEndPayPeriodDate) {
    	LocalDate localDate = batchEndPayPeriodDate != null ? LocalDate.fromDateFields(batchEndPayPeriodDate) : null;
    	LocalTime localTime = batchEndPayPeriodDateTime != null ? LocalTime.fromDateFields(batchEndPayPeriodDateTime) : LocalTime.MIDNIGHT;
    	batchEndPayPeriodDateTime = localDate != null ? localDate.toDateTime(localTime).toDate() : null;
    }

    public Time getBatchEndPayPeriodTime() {
    	return batchEndPayPeriodDateTime != null ? new Time(batchEndPayPeriodDateTime.getTime()) : null;
    }
    
    public void setBatchEndPayPeriodTime(Time batchEndPayPeriodTime) {
    	LocalDate localDate = batchEndPayPeriodDateTime != null ? LocalDate.fromDateFields(batchEndPayPeriodDateTime) : LocalDate.now();
    	LocalTime localTime = batchEndPayPeriodTime != null ? LocalTime.fromDateFields(batchEndPayPeriodTime) : null;
    	batchEndPayPeriodDateTime = localTime != null ? localTime.toDateTime(localDate.toDateTimeAtStartOfDay()).toDate() : null;
    }
    
    public DateTime getBatchEndPayPeriodFullDateTime() {
    	return batchEndPayPeriodDateTime != null ? new DateTime(batchEndPayPeriodDateTime) : null;
    }
    
    public void setBatchEndPayPeriodFullDateTime(DateTime batchEndPayPeriodFullDateTime) {
    	batchEndPayPeriodDateTime = batchEndPayPeriodFullDateTime != null ? batchEndPayPeriodFullDateTime.toDate() : null;
    }

    public Date getBatchEmployeeApprovalDateTime() {
        return batchEmployeeApprovalDateTime;
    }

    public void setBatchEmployeeApprovalDateTime(Date batchEmployeeApprovalDateTime) {
        this.batchEmployeeApprovalDateTime = batchEmployeeApprovalDateTime;
    }
    
    public Date getBatchEmployeeApprovalDate() {
    	return batchEmployeeApprovalDateTime != null ? LocalDate.fromDateFields(batchEmployeeApprovalDateTime).toDate() : null;
    }
    
    public void setBatchEmployeeApprovalDate(Date batchEmployeeApprovalDate) {
    	LocalDate localDate = batchEmployeeApprovalDate != null ? LocalDate.fromDateFields(batchEmployeeApprovalDate) : null;
    	LocalTime localTime = batchEmployeeApprovalDateTime != null ? LocalTime.fromDateFields(batchEmployeeApprovalDateTime) : LocalTime.MIDNIGHT;
    	batchEmployeeApprovalDateTime = localDate != null ? localDate.toDateTime(localTime).toDate() : null;
    }

    public Time getBatchEmployeeApprovalTime() {
    	return batchEmployeeApprovalDateTime != null ? new Time(batchEmployeeApprovalDateTime.getTime()) : null;
    }
    
    public void setBatchEmployeeApprovalTime(Time batchEmployeeApprovalTime) {
    	LocalDate localDate = batchEmployeeApprovalDateTime != null ? LocalDate.fromDateFields(batchEmployeeApprovalDateTime) : LocalDate.now();
    	LocalTime localTime = batchEmployeeApprovalTime != null ? LocalTime.fromDateFields(batchEmployeeApprovalTime) : null;
    	batchEmployeeApprovalDateTime = localTime != null ? localTime.toDateTime(localDate.toDateTimeAtStartOfDay()).toDate() : null;
    }
    
    public DateTime getBatchEmployeeApprovalFullDateTime() {
    	return batchEmployeeApprovalDateTime != null ? new DateTime(batchEmployeeApprovalDateTime) : null;
    }
    
    public void setBatchEmployeeApprovalFullDateTime(DateTime batchEmployeeApprovalFullDateTime) {
    	batchEmployeeApprovalDateTime = batchEmployeeApprovalFullDateTime != null ? batchEmployeeApprovalFullDateTime.toDate() : null;
    }

    public Date getBatchSupervisorApprovalDateTime() {
        return batchSupervisorApprovalDateTime;
    }

    public void setBatchSupervisorApprovalDateTime(Date batchSupervisorApprovalDateTime) {
        this.batchSupervisorApprovalDateTime = batchSupervisorApprovalDateTime;
    }
    
    public Date getBatchSupervisorApprovalDate() {
    	return batchSupervisorApprovalDateTime != null ? LocalDate.fromDateFields(batchSupervisorApprovalDateTime).toDate() : null;
    }
    
    public void setBatchSupervisorApprovalDate(Date batchSupervisorApprovalDate) {
    	LocalDate localDate = batchSupervisorApprovalDate != null ? LocalDate.fromDateFields(batchSupervisorApprovalDate) : null;
    	LocalTime localTime = batchSupervisorApprovalDateTime != null ? LocalTime.fromDateFields(batchSupervisorApprovalDateTime) : LocalTime.MIDNIGHT;
    	batchSupervisorApprovalDateTime = localDate != null ? localDate.toDateTime(localTime).toDate() : null;
    }

    public Time getBatchSupervisorApprovalTime() {
    	return batchSupervisorApprovalDateTime != null ? new Time(batchSupervisorApprovalDateTime.getTime()) : null;
    }
    
    public void setBatchSupervisorApprovalTime(Time batchSupervisorApprovalTime) {
    	LocalDate localDate = batchSupervisorApprovalDateTime != null ? LocalDate.fromDateFields(batchSupervisorApprovalDateTime) : LocalDate.now();
    	LocalTime localTime = batchSupervisorApprovalTime != null ? LocalTime.fromDateFields(batchSupervisorApprovalTime) : null;
    	batchSupervisorApprovalDateTime = localTime != null ? localTime.toDateTime(localDate.toDateTimeAtStartOfDay()).toDate() : null;
    }
    
    public DateTime getBatchSupervisorApprovalFullDateTime() {
    	return batchSupervisorApprovalDateTime != null ? new DateTime(batchSupervisorApprovalDateTime) : null;
    }
    
    public void setBatchSupervisorApprovalFullDateTime(DateTime batchSupervisorApprovalFullDateTime) {
    	batchSupervisorApprovalDateTime = batchSupervisorApprovalFullDateTime != null ? batchSupervisorApprovalFullDateTime.toDate() : null;
    }

	public Calendar getCalendarObj() {
		return calendarObj;
	}

	public void setCalendarObj(Calendar calendarObj) {
		this.calendarObj = calendarObj;
	}

    public int compareTo(CalendarEntry pce) {
        return this.getBeginPeriodDate().compareTo(pce.getBeginPeriodDate());
    }

}