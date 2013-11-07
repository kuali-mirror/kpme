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
package org.kuali.kpme.core.api.calendar.entry;

import java.sql.Time;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.kuali.kpme.core.api.calendar.CalendarContract;
import org.kuali.kpme.core.api.util.HrApiConstants;
import org.kuali.rice.krad.bo.PersistableBusinessObject;

/**
 * <p>CalendarEntryContract interface</p>
 *
 */
public interface CalendarEntryContract extends PersistableBusinessObject {
	
	public static final String CACHE_NAME = HrApiConstants.CacheNamespace.NAMESPACE_PREFIX + "CalendarEntry";
	
	/**
	 * The hrCalendarId the CalendarEntry is associated with
	 * 
	 * <p>
	 * hrCalendarId of a CalendarEntry
	 * </p>
	 * 
	 * @return hrCalendarId for CalendarEntry
	 */
    public String getHrCalendarId();
   
    /**
	 * The primary Key of a CalendarEntry entry saved in a database
	 * 
	 * <p>
	 * hrCalendarEntryId of a CalendarEntry
	 * <p>
	 * 
	 * @return hrCalendarEntryId for a CalendarEntry
	 */
    public String getHrCalendarEntryId();

    /**
	 * The calendar to be associated with the CalendarEntry
	 * 
	 * <p>
	 * calendarName of a CalendarEntry
	 * </p>
	 * 
	 * @return calendarName for CalendarEntry
	 */
	public String getCalendarName();
	
	/**
	 * The beginPeriodDateTime the CalendarEntry is associated with
	 * 
	 * <p>
	 * This drives what calendar days show on the timesheet and/or leaving calendar
	 * </p>
	 * 
	 * @return beginPeriodDateTime for CalendarEntry
	 */
	public Date getBeginPeriodDateTime();

	/**
	 * The beginPeriodDateTime (Date with no time zone) the CalendarEntry is associated with
	 * 
	 * <p>
	 * beginPeriodDateTime of a CalendarEntry
	 * </p>
	 * 
	 * @return beginPeriodDateTime with no time zone wrapped in a Date object
	 */
    public Date getBeginPeriodDate();
  
    /**
	 * The beginPeriodDateTime (Time) the CalendarEntry is associated with
	 * 
	 * <p>
	 * beginPeriodDateTime of a CalendarEntry 
	 * </p>
	 * 
	 * @return beginPeriodDateTime wrapped in a Time object
	 */
    public Time getBeginPeriodTime() ;
   
    /**
	 * The beginPeriodDateTime (DateTime) the CalendarEntry is associated with
	 * 
	 * <p>
	 * beginPeriodDateTime of a CalendarEntry
	 * </p>
	 * 
	 * @return beginPeriodDateTime wrapped in a DateTime object
	 */
    public DateTime getBeginPeriodFullDateTime();
    
    /**
	 * The beginPeriodDateTime (LocalDateTime) the CalendarEntry is associated with
	 * 
	 * <p>
	 * beginPeriodDateTime of a CalendarEntry
	 * </p>
	 * 
	 * @return beginPeriodDateTime wrapped in a LocalDateTime object
	 */
    public LocalDateTime getBeginPeriodLocalDateTime();
    
    /**
	 * The endPeriodDateTime the CalendarEntry is associated with
	 * 
	 * <p>
	 * This drives what calendars days show on the timesheetand/or leaving calendar
	 * </p>
	 * 
	 * @return endPeriodDateTime for CalendarEntry
	 */
    public Date getEndPeriodDateTime();

    /**
	 * The endPeriodDateTime (Date with no time zone) the CalendarEntry is associated with
	 * 
	 * <p>
	 * endPeriodDateTime of a CalendarEntry
	 * </p>
	 * 
	 * @return  endPeriodDateTime with no time zone wrapped in a Date object
	 */
    public Date getEndPeriodDate();
   
    /**
	 * The endPeriodDateTime (Time) the CalendarEntry is associated with
	 * 
	 * <p>
	 * endPeriodDateTime of a CalendarEntry
	 * </p>
	 * 
	 * @return endPeriodDateTime wrapped in a Time object
	 */
    public Time getEndPeriodTime();
    
    /**
   	 * The endPeriodDateTime (DateTime) the CalendarEntry is associated with
   	 * 
   	 * <p>
   	 * endPeriodDateTime of a CalendarEntry
   	 * </p>
   	 * 
   	 * @return endPeriodDateTime wrapped in a DateTime object
   	 */
    public DateTime getEndPeriodFullDateTime();
    
    /**
   	 * The endPeriodDateTime (LocalDateTime) the CalendarEntry is associated with
   	 * 
   	 * <p>
   	 * endPeriodDateTime of a CalendarEntry
   	 * </p>
   	 * 
   	 * @return endPeriodDateTime wrapped in a LocalDateTime object
   	 */
    public LocalDateTime getEndPeriodLocalDateTime();

    /**
	 * The date batch should run to create timesheets and/or leaving calendars for the reporting period
	 * 
	 * <p>
	 * batchInitiateDateTime of a CalendarEntry
	 * </p>
	 * 
	 * @return batchInitiateDateTime for CalendarEntry
	 */
    public Date getBatchInitiateDateTime();
    
    /**
	 * The batchInitiateDateTime (Date with no time zone) the CalendarEntry is associated with
	 * 
	 * <p>
	 * batchInitiateDateTime of a CalendarEntry
	 * </p>
	 * 
	 * @return batchInitiateDateTime with no time zone wrapped in a Date object
	 */
    public Date getBatchInitiateDate();
   
    /**
   	 * The batchInitiateDateTime (Time) the CalendarEntry is associated with
   	 * 
   	 * <p>
   	 * batchInitiateDateTime of a CalendarEntry
   	 * </p>
   	 * 
   	 * @return batchInitiateDateTime wrapped in a Time object
   	 */
    public Time getBatchInitiateTime();
    
    /**
   	 * The batchInitiateDateTime (DateTime) the CalendarEntry is associated with
   	 * 
   	 * <p>
   	 * batchInitiateDateTime of a CalendarEntry
   	 * </p>
   	 * 
   	 * @return batchInitiateDateTime wrapped in a DateTime object
   	 */
    public DateTime getBatchInitiateFullDateTime();
    
    /**
	 * The date batch job should run to end all timeblocks for this pay period
	 * 
	 * <p>
	 * This inserts clock outs at the end of the pay period, and clock ins at the beginning of the subsequent pay period.
	 * </p>
	 * 
	 * @return batchEndPayPeriodDateTime for CalendarEntry
	 */
    public Date getBatchEndPayPeriodDateTime();
   
    /**
   	 * The batchEndPayPeriodDateTime (Date with no time zone) the CalendarEntry is associated with
   	 * 
   	 * <p>
   	 * batchEndPayPeriodDateTime of a CalendarEntry 
   	 * </p>
   	 * 
   	 * @return batchEndPayPeriodDateTime with no time zone wrapped in a Date object
   	 */
    public Date getBatchEndPayPeriodDate();
    
    /**
   	 * The batchEndPayPeriodDateTime (Time) the CalendarEntry is associated with
   	 * 
   	 * <p>
   	 * batchEndPayPeriodDateTime of a CalendarEntry 
   	 * </p>
   	 * 
   	 * @return batchEndPayPeriodDateTime wrapped in a Time object
   	 */
    public Time getBatchEndPayPeriodTime();
    
    /**
   	 * The batchEndPayPeriodDateTime (DateTime) the CalendarEntry is associated with
   	 * 
   	 * <p>
   	 * batchEndPayPeriodDateTime wrapped in a DateTime object
   	 * </p>
   	 * 
   	 * @return batchEndPayPeriodDateTime for CalendarEntry
   	 */
    public DateTime getBatchEndPayPeriodFullDateTime();
   
    /**
	 * The date batch job should run to route timesheets and/or leave calendars from the employee to the approver
	 * 
	 * <p>
	 * batchEmployeeApprovalDateTime of a CalendarEntry
	 * </p>
	 * 
	 * @return batchEmployeeApprovalDateTime for CalendarEntry
	 */
    public Date getBatchEmployeeApprovalDateTime();
   
    /**
   	 * The batchEmployeeApprovalDateTime (Date with no time zone) the CalendarEntry is associated with
   	 * 
   	 * <p>
   	 * batchEmployeeApprovalDateTime of a CalendarEntry
   	 * </p>
   	 * 
   	 * @return batchEmployeeApprovalDateTime with no time zone wrapped in a Date object
   	 */
    public Date getBatchEmployeeApprovalDate();
   
    /**
   	 * The batchEmployeeApprovalDateTime (Time) the CalendarEntry is associated with
   	 * 
   	 * <p>
   	 * batchEmployeeApprovalDateTime of a CalendarEntry
   	 * </p>
   	 * 
   	 * @return batchEmployeeApprovalDateTime wrapped in a Time object
   	 */
    public Time getBatchEmployeeApprovalTime();
    
    /**
   	 * The batchEmployeeApprovalDateTime (DateTime) the CalendarEntry is associated with
   	 * 
   	 * <p>
   	 * batchEmployeeApprovalDateTime of a CalendarEntry
   	 * </p>
   	 * 
   	 * @return batchEmployeeApprovalDateTime wrapped in a DateTime object
   	 */
    public DateTime getBatchEmployeeApprovalFullDateTime();
   
    /**
	 * The date batch job should run to route timesheets and/or leave calendars from the approver to final status
	 * 
	 * <p>
	 * batchSupervisorApprovalDateTime of a CalendarEntry
	 * </p>
	 * 
	 * @return batchSupervisorApprovalDateTime for CalendarEntry
	 */
    public Date getBatchSupervisorApprovalDateTime();
  
    /**
   	 * The batchSupervisorApprovalDateTime (Date with no time zone) the CalendarEntry is associated with
   	 * 
   	 * <p>
   	 * batchSupervisorApprovalDateTime of a CalendarEntry
   	 * </p>
   	 * 
   	 * @return batchSupervisorApprovalDateTime with no time zone wrapped in a Date object
   	 */
    public Date getBatchSupervisorApprovalDate();
    
    /**
   	 * The batchSupervisorApprovalDateTime (Time) the CalendarEntry is associated with
   	 * 
   	 * <p>
   	 * batchSupervisorApprovalDateTime of a CalendarEntry
   	 * </p>
   	 * 
   	 * @return batchSupervisorApprovalDateTime wrapped in a Time object
   	 */
    public Time getBatchSupervisorApprovalTime();
  
    /**
   	 * The batchSupervisorApprovalDateTime (DateTime) the CalendarEntry is associated with
   	 * 
   	 * <p>
   	 * batchSupervisorApprovalDateTime of a CalendarEntry
   	 * </p>
   	 * 
   	 * @return batchSupervisorApprovalDateTime wrapped in a DateTime object
   	 */
    public DateTime getBatchSupervisorApprovalFullDateTime();
    
    /**
	 * The Calendar object the CalendarEntry is associated with
	 * 
	 * <p>
	 * calendarObj of a CalendarEntry
	 * </p>
	 * 
	 * @return calendarObj for CalendarEntry
	 */
	public CalendarContract getCalendarObj();

	/**
	 * TODO:  Is this field needed???  
	 * The batchPayrollApprovalDateTime the CalendarEntry is associated with
	 * 
	 * <p>
	 * batchPayrollApprovalDateTime of a CalendarEntry
	 * </p>
	 * 
	 * @return batchPayrollApprovalDateTime for CalendarEntry
	 */
	public Date getBatchPayrollApprovalDateTime();
	
	/**
   	 * The batchPayrollApprovalDateTime (Date with no time zone) the CalendarEntry is associated with
   	 * 
   	 * <p>
   	 * batchPayrollApprovalDateTime of a CalendarEntry 
   	 * </p>
   	 * 
   	 * @return batchPayrollApprovalDateTime with no time zone wrapped in a Date object
   	 */
    public Date getBatchPayrollApprovalDate();
  
    /**
   	 * The batchPayrollApprovalDateTime (Time) the CalendarEntry is associated with
   	 * 
   	 * <p>
   	 * batchPayrollApprovalDateTime of a CalendarEntry
   	 * </p>
   	 * 
   	 * @return batchPayrollApprovalDateTime wrapped in a Time object
   	 */
    public Time getBatchPayrollApprovalTime();
    
    /**
   	 * The batchPayrollApprovalDateTime (DateTime) the CalendarEntry is associated with
   	 * 
   	 * <p>
   	 * batchPayrollApprovalDateTime of a CalendarEntry
   	 * </p>
   	 * 
   	 * @return batchPayrollApprovalDateTime wrapped in a DateTime object
   	 */
    public DateTime getBatchPayrollApprovalFullDateTime();

}