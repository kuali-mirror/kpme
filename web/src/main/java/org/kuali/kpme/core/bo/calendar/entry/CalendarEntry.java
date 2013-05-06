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
    	Date beginPeriodDate = null;
    	
    	if (beginPeriodDateTime != null) {
    		beginPeriodDate = new Date(beginPeriodDateTime.getTime());
    	}
    	
    	return beginPeriodDate;
    }
    
    public void setBeginPeriodDate(Date beginPeriodDate) {
    	DateTime dateTime = new DateTime(beginPeriodDateTime);
    	LocalDate localDate = new LocalDate(beginPeriodDate);
    	LocalTime localTime = new LocalTime(beginPeriodDateTime);
    	beginPeriodDateTime = localDate.toDateTime(localTime, dateTime.getZone()).toDate();
    }
    
    public Time getBeginPeriodTime() {
    	Time beginPeriodTime = null;
    	
    	if (beginPeriodDateTime != null) {
    		beginPeriodTime = new Time(beginPeriodDateTime.getTime());
    	}
    	
    	return beginPeriodTime;
    }
    
    public void setBeginPeriodTime(Time beginPeriodTime) {
    	DateTime dateTime = new DateTime(beginPeriodDateTime);
    	LocalDate localDate = new LocalDate(beginPeriodDateTime);
    	LocalTime localTime = new LocalTime(beginPeriodTime);
    	beginPeriodDateTime = localDate.toDateTime(localTime, dateTime.getZone()).toDate();
    }
    
    public DateTime getBeginPeriodFullDateTime() {
    	return new DateTime(beginPeriodDateTime);
    }
    
    public LocalDateTime getBeginPeriodLocalDateTime() {
        return getBeginPeriodFullDateTime().toLocalDateTime();
    }
    
    public Date getEndPeriodDateTime() {
        return endPeriodDateTime;
    }

    public void setEndPeriodDateTime(Date endPeriodDateTime) {
        this.endPeriodDateTime = endPeriodDateTime;
    }

    public Date getEndPeriodDate() {
    	Date endPeriodDate = null;
    	
    	if (endPeriodDateTime != null) {
    		endPeriodDate = new Date(endPeriodDateTime.getTime());
    	}
    	
    	return endPeriodDate;
    }
    
    public void setEndPeriodDate(Date endPeriodDate) {
    	DateTime dateTime = new DateTime(endPeriodDateTime);
    	LocalDate localDate = new LocalDate(endPeriodDate);
    	LocalTime localTime = new LocalTime(endPeriodDateTime);
    	endPeriodDateTime = localDate.toDateTime(localTime, dateTime.getZone()).toDate();
    }

    public Time getEndPeriodTime() {
    	Time endPeriodTime = null;
    	
    	if (endPeriodDateTime != null) {
    		endPeriodTime = new Time(endPeriodDateTime.getTime());
    	}
    	
    	return endPeriodTime;
    }
    
    public void setEndPeriodTime(Time endPeriodDate) {
    	DateTime dateTime = new DateTime(endPeriodDateTime);
    	LocalDate localDate = new LocalDate(endPeriodDateTime);
    	LocalTime localTime = new LocalTime(endPeriodDate);
    	endPeriodDateTime = localDate.toDateTime(localTime, dateTime.getZone()).toDate();
    }
    
    public DateTime getEndPeriodFullDateTime() {
    	return new DateTime(endPeriodDateTime);
    }
    
    public LocalDateTime getEndPeriodLocalDateTime() {
        return getEndPeriodFullDateTime().toLocalDateTime();
    }

    public Date getBatchInitiateDateTime() {
        return batchInitiateDateTime;
    }
    
    public void setBatchInitiateDateTime(Date batchInitiateDateTime) {
        this.batchInitiateDateTime = batchInitiateDateTime;
    }
    
    public Date getBatchInitiateDate() {
    	Date batchInitiateDate = null;
    	
    	if (batchInitiateDateTime != null) {
    		batchInitiateDate = new Date(batchInitiateDateTime.getTime());
    	}
    	
    	return batchInitiateDate;
    }
    
    public void setBatchInitiateDate(Date batchInitiateDate) {
    	DateTime dateTime = new DateTime(batchInitiateDateTime);
    	LocalDate localDate = new LocalDate(batchInitiateDate);
    	LocalTime localTime = new LocalTime(batchInitiateDateTime);
    	batchInitiateDateTime = localDate.toDateTime(localTime, dateTime.getZone()).toDate();
    }

    public Time getBatchInitiateTime() {
    	Time batchInitiateTime = null;
    	
    	if (batchInitiateDateTime != null) {
    		batchInitiateTime = new Time(batchInitiateDateTime.getTime());
    	}
    	
    	return batchInitiateTime;
    }
    
    public void setBatchInitiateTime(Time batchInitiateDate) {
    	DateTime dateTime = new DateTime(batchInitiateDateTime);
    	LocalDate localDate = new LocalDate(batchInitiateDateTime);
    	LocalTime localTime = new LocalTime(batchInitiateDate);
    	batchInitiateDateTime = localDate.toDateTime(localTime, dateTime.getZone()).toDate();
    }
    
    public DateTime getBatchInitiateFullDateTime() {
    	return new DateTime(batchInitiateDateTime);
    }

    public Date getBatchEndPayPeriodDateTime() {
        return batchEndPayPeriodDateTime;
    }

    public void setBatchEndPayPeriodDateTime(Date batchEndPayPeriodDateTime) {
        this.batchEndPayPeriodDateTime = batchEndPayPeriodDateTime;
    }
    
    public Date getBatchEndPayPeriodDate() {
    	Date batchEndPayPeriodDate = null;
    	
    	if (batchEndPayPeriodDateTime != null) {
    		batchEndPayPeriodDate = new Date(batchEndPayPeriodDateTime.getTime());
    	}
    	
    	return batchEndPayPeriodDate;
    }
    
    public void setBatchEndPayPeriodDate(Date batchEndPayPeriodDate) {
    	DateTime dateTime = new DateTime(batchEndPayPeriodDateTime);
    	LocalDate localDate = new LocalDate(batchEndPayPeriodDate);
    	LocalTime localTime = new LocalTime(batchEndPayPeriodDateTime);
    	batchEndPayPeriodDateTime = localDate.toDateTime(localTime, dateTime.getZone()).toDate();
    }

    public Time getBatchEndPayPeriodTime() {
    	Time batchEndPayPeriodTime = null;
    	
    	if (batchEndPayPeriodDateTime != null) {
    		batchEndPayPeriodTime = new Time(batchEndPayPeriodDateTime.getTime());
    	}
    	
    	return batchEndPayPeriodTime;
    }
    
    public void setBatchEndPayPeriodTime(Time batchEndPayPeriodDate) {
    	DateTime dateTime = new DateTime(batchEndPayPeriodDateTime);
    	LocalDate localDate = new LocalDate(batchEndPayPeriodDateTime);
    	LocalTime localTime = new LocalTime(batchEndPayPeriodDate);
    	batchEndPayPeriodDateTime = localDate.toDateTime(localTime, dateTime.getZone()).toDate();
    }
    
    public DateTime getBatchEndPayPeriodFullDateTime() {
    	return new DateTime(batchEndPayPeriodDateTime);
    }

    public Date getBatchEmployeeApprovalDateTime() {
        return batchEmployeeApprovalDateTime;
    }

    public void setBatchEmployeeApprovalDateTime(Date batchEmployeeApprovalDateTime) {
        this.batchEmployeeApprovalDateTime = batchEmployeeApprovalDateTime;
    }
    
    public Date getBatchEmployeeApprovalDate() {
    	Date batchEmployeeApprovalDate = null;
    	
    	if (batchEmployeeApprovalDateTime != null) {
    		batchEmployeeApprovalDate = new Date(batchEmployeeApprovalDateTime.getTime());
    	}
    	
    	return batchEmployeeApprovalDate;
    }
    
    public void setBatchEmployeeApprovalDate(Date batchEmployeeApprovalDate) {
    	DateTime dateTime = new DateTime(batchEmployeeApprovalDateTime);
    	LocalDate localDate = new LocalDate(batchEmployeeApprovalDate);
    	LocalTime localTime = new LocalTime(batchEmployeeApprovalDateTime);
    	batchEmployeeApprovalDateTime = localDate.toDateTime(localTime, dateTime.getZone()).toDate();
    }

    public Time getBatchEmployeeApprovalTime() {
    	Time batchEmployeeApprovalTime = null;
    	
    	if (batchEmployeeApprovalDateTime != null) {
    		batchEmployeeApprovalTime = new Time(batchEmployeeApprovalDateTime.getTime());
    	}
    	
    	return batchEmployeeApprovalTime;
    }
    
    public void setBatchEmployeeApprovalTime(Time batchEmployeeApprovalDate) {
    	DateTime dateTime = new DateTime(batchEmployeeApprovalDateTime);
    	LocalDate localDate = new LocalDate(batchEmployeeApprovalDateTime);
    	LocalTime localTime = new LocalTime(batchEmployeeApprovalDate);
    	batchEmployeeApprovalDateTime = localDate.toDateTime(localTime, dateTime.getZone()).toDate();
    }
    
    public DateTime getBatchEmployeeApprovalFullDateTime() {
    	return new DateTime(batchEmployeeApprovalDateTime);
    }

    public Date getBatchSupervisorApprovalDateTime() {
        return batchSupervisorApprovalDateTime;
    }

    public void setBatchSupervisorApprovalDateTime(Date batchSupervisorApprovalDateTime) {
        this.batchSupervisorApprovalDateTime = batchSupervisorApprovalDateTime;
    }
    
    public Date getBatchSupervisorApprovalDate() {
    	Date batchSupervisorApprovalDate = null;
    	
    	if (batchSupervisorApprovalDateTime != null) {
    		batchSupervisorApprovalDate = new Date(batchSupervisorApprovalDateTime.getTime());
    	}
    	
    	return batchSupervisorApprovalDate;
    }
    
    public void setBatchSupervisorApprovalDate(Date batchSupervisorApprovalDate) {
    	DateTime dateTime = new DateTime(batchSupervisorApprovalDateTime);
    	LocalDate localDate = new LocalDate(batchSupervisorApprovalDate);
    	LocalTime localTime = new LocalTime(batchSupervisorApprovalDateTime);
    	batchSupervisorApprovalDateTime = localDate.toDateTime(localTime, dateTime.getZone()).toDate();
    }

    public Time getBatchSupervisorApprovalTime() {
    	Time batchSupervisorApprovalTime = null;
    	
    	if (batchSupervisorApprovalDateTime != null) {
    		batchSupervisorApprovalTime = new Time(batchSupervisorApprovalDateTime.getTime());
    	}
    	
    	return batchSupervisorApprovalTime;
    }
    
    public void setBatchSupervisorApprovalTime(Time batchSupervisorApprovalDate) {
    	DateTime dateTime = new DateTime(batchSupervisorApprovalDateTime);
    	LocalDate localDate = new LocalDate(batchSupervisorApprovalDateTime);
    	LocalTime localTime = new LocalTime(batchSupervisorApprovalDate);
    	batchSupervisorApprovalDateTime = localDate.toDateTime(localTime, dateTime.getZone()).toDate();
    }
    
    public DateTime getBatchSupervisorApprovalFullDateTime() {
    	return new DateTime(batchSupervisorApprovalDateTime);
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