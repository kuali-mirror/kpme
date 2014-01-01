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
package org.kuali.kpme.tklm.api.time.timeblock;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.kuali.kpme.core.api.block.CalendarBlockContract;
import org.kuali.kpme.tklm.api.time.timehourdetail.TimeHourDetailContract;
import org.kuali.kpme.tklm.api.time.workflow.TimesheetDocumentHeaderContract;
import org.kuali.rice.kim.api.identity.Person;


/**
 * <p>TimeBlockContract interface</p>
 *
 */
public interface TimeBlockContract extends CalendarBlockContract {
	
	/**
	 * The beginTimestamp (Date) associated with the TimeBlock
	 * 
	 * <p>
	 * beginTimestamp wrapped in Date object of a TimeBlock
	 * <p>
	 * 
	 * @return new Date(beginTimestamp.getTime()) 
	 */
    public Date getBeginDate();
    
    /**
	 * The beginTimestamp (Time) associated with the TimeBlock
	 * 
	 * <p>
	 * beginTimestamp wrapped in Time object of a TimeBlock
	 * <p>
	 * 
	 * @return new Time(beginTimestamp.getTime()) 
	 */
    public Time getBeginTime();
   
    /**
	 * The beginTimestamp (Datetime) associated with the TimeBlock
	 * 
	 * <p>
	 * beginTimestamp wrapped in DateTime object of a TimeBlock
	 * <p>
	 * 
	 * @return new DateTime(beginTimestamp)
	 */
    public DateTime getBeginDateTime();

    /**
	 * The endTimestamp associated with the TimeBlock
	 * 
	 * <p>
	 * endTimestamp of a TimeBlock
	 * <p>
	 * 
	 * @return endTimestamp for TimeBlock
	 */
    public Date getEndTimestamp();
    
    /**
	 * The endTimestamp (Date) associated with the TimeBlock
	 * 
	 * <p>
	 * endTimestamp wrapped in Date object of a TimeBlock
	 * <p>
	 * 
	 * @return new Date(endTimestamp.getTime())
	 */
    public Date getEndDate();

    /**
	 * The endTimestamp (Time) associated with the TimeBlock
	 * 
	 * <p>
	 * endTimestamp wrapped in Time object of a TimeBlock
	 * <p>
	 * 
	 * @return new Time(endTimestamp.getTime()) 
	 */
    public Time getEndTime();
    
    /**
	 * The endTimestamp (DateTime) associated with the TimeBlock
	 * 
	 * <p>
	 * endTimestamp wrapped in DateTime object of a TimeBlock
	 * <p>
	 * 
	 * @return new DateTime(endTimestamp)
	 */
    public DateTime getEndDateTime();
   
    /**
     * TODO: Make sure this comment is right
	 * The flag that indicates if a clock log has been created or not.
	 * 
	 * <p>
	 * clockLogCreated flag of a ShiftDifferentialRule
	 * </p>
	 * 
	 * @return Y if created, N if not
	 */
    public Boolean getClockLogCreated();
   
    /**
   	 * The hours associated with the TimeBlock
   	 * 
   	 * <p>
   	 * hours of a TimeBlock
   	 * <p>
   	 * 
   	 * @return hours for TimeBlock
   	 */
    public BigDecimal getHours();
   
    /**
   	 * The amount associated with the TimeBlock
   	 * 
   	 * <p>
   	 * amount of a TimeBlock
   	 * <p>
   	 * 
   	 * @return amount for TimeBlock
   	 */
    public BigDecimal getAmount();

    /**
   	 * The userPrincipalId associated with the TimeBlock
   	 * 
   	 * <p>
   	 * userPrincipalId of a TimeBlock
   	 * <p>
   	 * 
   	 * @return userPrincipalId for TimeBlock
   	 */
    public String getUserPrincipalId();

    /**
   	 * The timestamp associated with the TimeBlock
   	 * 
   	 * <p>
   	 * timestamp of a TimeBlock
   	 * <p>
   	 * 
   	 * @return timestamp for TimeBlock
   	 */
    public Timestamp getTimestamp();
    
    /**
	 * The primary key of a TimeBlock entry saved in a database
	 * 
	 * <p>
	 * tkTimeBlockId of a TimeBlock
	 * <p>
	 * 
	 * @return tkTimeBlockId for TimeBlock
	 */
    public String getTkTimeBlockId();
   
    /**
   	 * The list of TimeHourDetail objects associated with the TimeBlock
   	 * 
   	 * <p>
   	 * timeHourDetails of a TimeBlock
   	 * <p>
   	 * 
   	 * @return timeHourDetails for TimeBlock
   	 */
    public List<? extends TimeHourDetailContract> getTimeHourDetails();
    
    /**
	 * The flag that indicates if the TimeBlock will be pushed back or not
	 * 
	 * <p>
	 * A timeblock will be pushed back if the timeblock is still within the previous interval.
	 * </p>
	 * 
	 * @return Y if pushed back, N if not
	 */
    public Boolean getPushBackward();

    /**
   	 * The Timeblock begin time to display with the user's Timezone taken into account and applied to this DateTime object.
   	 * 
   	 * <p>
   	 * Use this call for all GUI/Display related rendering of the END timestamp of the given time block. 
   	 * Timeblocks require pre-processing before there will be a non-null return value here.
   	 * <p>
   	 * 
   	 * @return beginTimeDisplay for TimeBlock
   	 */
    public DateTime getBeginTimeDisplay();

    /**
   	 * The Timeblock begin time (Date) associated with the TimeBlock
   	 * 
   	 * <p>
   	 * <p>
   	 * 
   	 * @return Date object representing getBeginTimeDisplay()
   	 */
    public Date getBeginTimeDisplayDate();

    /**
   	 * The date portion of the Timeblock begin time associated with the TimeBlock
   	 * 
   	 * <p>
   	 * <p>
   	 * 
   	 * @return a string representing the getBeginTimeDisplay() in MM/dd/yyyy format
   	 */
    public String getBeginTimeDisplayDateOnlyString();

    /**
   	 * The time portion of the Timeblock begin time associated with the TimeBlock
   	 * 
   	 * <p>
   	 * <p>
   	 * 
   	 * @return a string representing getBeginTimeDisplay() in hh:mm aa format
   	 */
    public String getBeginTimeDisplayTimeOnlyString();

    /**
   	 * The date portion of the Timeblock end time associated with the TimeBlock
   	 * 
   	 * <p>
   	 * <p>
   	 * 
   	 * @return a string representing getEndTimeDisplay() in MM/dd/yyyy format
   	 */
    public String getEndTimeDisplayDateOnlyString();

    /**
   	 * The time portion of the Timeblock end time associated with the TimeBlock
   	 * 
   	 * <p>
   	 * <p>
   	 * 
   	 * @return a string representing the getEndTimeDisplay() in hh:mm aa format
   	 */
    public String getEndTimeDisplayTimeOnlyString();

    /**
   	 * The Timeblock end time to display with the user's Timezone taken into account and applied to this DateTime object.
   	 * 
   	 * <p>
   	 * Use this call for all GUI/Display related rendering of the END timestamp of the given time block. 
   	 * Timeblocks require pre-processing before there will be a non-null return value here.
   	 * <p>
   	 * 
   	 * @return endTimeDisplay for TimeBlock
   	 */
    public DateTime getEndTimeDisplay();

    /**
   	 * The Timeblock end time (Date) associated with the TimeBlock
   	 * 
   	 * <p>
   	 * <p>
   	 * 
   	 * @return Date object representing getEndTimeDisplay() 
   	 */
    public Date getEndTimeDisplayDate();
    
    /**
   	 * The TimesheetDocumentHeader object associated with the TimeBlock
   	 * 
   	 * <p>
   	 * timesheetDocumentHeader of a TimeBlock
   	 * <p>
   	 * 
   	 * @return timesheetDocumentHeader for TimeBlock
   	 */
    public TimesheetDocumentHeaderContract getTimesheetDocumentHeader();
  
    /**
   	 * The list of TimeBlockHistory objects associated with the TimeBlock
   	 * 
   	 * <p>
   	 * timeBlockHistories of a TimeBlock
   	 * <p>
   	 * 
   	 * @return timeBlockHistories for TimeBlock
   	 */
    public List<? extends TimeBlockHistoryContract> getTimeBlockHistories();
   
    /**
   	 * The clock log begin id associated with the TimeBlock
   	 * 
   	 * <p>
   	 * clockLogBeginId of a TimeBlock
   	 * <p>
   	 * 
   	 * @return clockLogBeginId for TimeBlock
   	 */
    public String getClockLogBeginId();

    /**
   	 * The clock log end id associated with the TimeBlock
   	 * 
   	 * <p>
   	 * clockLogEndId of a TimeBlock
   	 * <p>
   	 * 
   	 * @return clockLogEndId for TimeBlock
   	 */
    public String getClockLogEndId();
   
    /**
   	 * The assignment key associated with the TimeBlock
   	 * 
   	 * <p>
   	 * assignmentKey of a TimeBlock
   	 * <p>
   	 * 
   	 * @return assignmentKey for TimeBlock
   	 */
    public String getAssignmentKey();
  
    /**
   	 * The assignment description associated with the TimeBlock
   	 * 
   	 * <p>
   	 * assignment description of a TimeBlock
   	 * <p>
   	 * 
   	 * @return assignment description for TimeBlock
   	 */
    public String getAssignmentDescription();
   
    /**
   	 * The earn code type associated with the TimeBlock
   	 * 
   	 * <p>
   	 * earnCodeType of a TimeBlock
   	 * <p>
   	 * 
   	 * @return earnCodeType for TimeBlock
   	 */
    public String getEarnCodeType();
    
    /**
	 * Indicates if the user has permission to edit/delete the TimeBlock
	 * 
	 * <p>
	 * </p>
	 * 
	 * @return Y if user has a permission to edit/delete this time block, N if not
	 */
    public Boolean getEditable();

    /**
   	 * The principalId associated with the TimeBlock
   	 * 
   	 * <p>
   	 * principalId of a TimeBlock
   	 * <p>
   	 * 
   	 * @return principalId for TimeBlock
   	 */
    public String getPrincipalId();
   
    /**
     * TODO: Put a better comment
   	 * The overtimePref associated with the TimeBlock
   	 * 
   	 * <p>
   	 * overtimePref of a TimeBlock
   	 * <p>
   	 * 
   	 * @return overtimePref for TimeBlock
   	 */
    public String getOvertimePref();

    /**
   	 * The actual begin time string associated with the TimeBlock
   	 * 
   	 * <p>
   	 * It applies grace period rule to times of time block.  This string is for GUI of Actual time inquiry
   	 * <p>
   	 * 
   	 * @return actual begin time string for TimeBlock
   	 */
    public String getActualBeginTimeString();

    /**
   	 * The actual end time string associated with the TimeBlock
   	 * 
   	 * <p>
   	 * It applies grace period rule to times of time block.  This string is for GUI of Actual time inquiry
   	 * <p>
   	 * 
   	 * @return actual end time string for TimeBlock
   	 */
    public String getActualEndTimeString();

	/**
	 * Indicates if this user has a permission to delete this time block
	 * 
	 * <p>
	 * </p>
	 * 
	 * @return Y if user has a permission to delete this time block, N if not
	 */
	public Boolean getDeleteable();

	/**
	 * Indicates if overtime earn code can be edited
	 * 
	 * <p>
	 * </p>
	 * 
	 * @return Y if overtime earn code can be edited, N if not
	 */
	public Boolean getOvertimeEditable();

	/**
	 * Indicates if this user has a permission to edit this time block
	 * 
	 * <p>
	 * </p>
	 * 
	 * @return Y if user has a permission to edit this time block, N if not
	 */
    public Boolean getTimeBlockEditable();

    /**
     * TODO: Make sure this comment is right
	 * The flag that indicates if lunch hour is deleted
	 * 
	 * <p>
	 * lunchDeleted flag of a TimeBlock
	 * </p>
	 * 
	 * @return Y if deleted, N if not
	 */
    public boolean isLunchDeleted();

    /**
   	 * The Person object associated with the TimeBlock
   	 * 
   	 * <p>
   	 * user of a TimeBlock
   	 * <p>
   	 * 
   	 * @return user for TimeBlock
   	 */
	public Person getUser();

	/**
   	 * The id of the CalendarBlock object associated with the TimeBlock
   	 * 
   	 * <p>
   	 * super.hrCalendarBlockId a TimeBlock
   	 * <p>
   	 * 
   	 * @return super.hrCalendarBlockId for TimeBlock
   	 */
	public String getHrCalendarBlockId();

}
