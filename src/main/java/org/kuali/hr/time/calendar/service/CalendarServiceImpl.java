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
package org.kuali.hr.time.calendar.service;

import java.util.Date;
import java.util.List;

import org.kuali.hr.job.Job;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.time.calendar.Calendar;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.calendar.dao.CalendarDao;
import org.kuali.hr.time.paytype.PayType;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;

public class CalendarServiceImpl implements CalendarService {

	private CalendarDao calendarDao;

	public void setCalendarDao(CalendarDao calendarDao) {
		this.calendarDao = calendarDao;
	}

	@Override
	public Calendar getCalendar(String hrCalendarId) {
		return calendarDao.getCalendar(hrCalendarId);
	}

	@Override
	public Calendar getCalendarByGroup(String calendarName) {
		return calendarDao.getCalendarByGroup(calendarName);
	}

    @Override
    public CalendarEntries getCalendarDatesByPayEndDate(String principalId, Date payEndDate, String calendarType) {
        CalendarEntries pcd = null;
        boolean findLeaveCal = false;
        if(calendarType != null && calendarType.equalsIgnoreCase(LMConstants.LEAVE_CALENDAR_TYPE)) {
        	findLeaveCal = true;
        }
        Calendar calendar = getCalendar(principalId, payEndDate, findLeaveCal);
        pcd = TkServiceLocator.getCalendarEntriesService().getCalendarEntriesByIdAndPeriodEndDate(calendar.getHrCalendarId(), payEndDate);
        pcd.setCalendarObj(calendar);

        return pcd;
    }

	@Override
	public CalendarEntries getCurrentCalendarDates(String principalId, Date currentDate) {
		CalendarEntries pcd = null;
        Calendar calendar = getCalendarByPrincipalIdAndDate(principalId, currentDate, false);
        if(calendar != null) {
		    pcd = TkServiceLocator.getCalendarEntriesService().getCurrentCalendarEntriesByCalendarId(calendar.getHrCalendarId(), currentDate);
		    if(pcd != null) {
		    	pcd.setCalendarObj(calendar);
		    }
        }
		return pcd;
	}

    /**
     * Helper method common to the CalendarEntry search methods above.
     * @param principalId Principal ID to lookup
     * @param date A date, Principal Calendars are EffDt/Timestamped, so we can any current date.
     * @return A Calendar
     */
    private Calendar getCalendar(String principalId, Date date, boolean findLeaveCal) {
        Calendar pcal = null;

        List<Job> currentJobs = TkServiceLocator.getJobService().getJobs(principalId, date);
        if(currentJobs.size() < 1){
            throw new RuntimeException("No jobs found for principal id "+principalId);
        }
        Job job = currentJobs.get(0);

        if (principalId == null || job == null) {
            throw new RuntimeException("Null parameters passed to getEndDate");
        } else {
            PayType payType = job.getPayTypeObj();
            if (payType == null)
                throw new RuntimeException("Null pay type on Job in getEndDate");
            PrincipalHRAttributes principalCalendar = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, date);
            if(principalCalendar == null){
                throw new RuntimeException("Null principal calendar for principalid "+principalId);
            }
            if(!findLeaveCal) {
            	pcal = principalCalendar.getCalendar();
            	if (pcal == null){
            		pcal = principalCalendar.getLeaveCalObj();
            		if(pcal == null){
            			throw new RuntimeException("Null principal calendar for principalId " + principalId);
            		}
            	}
            } else {
        		pcal = principalCalendar.getLeaveCalObj();
        		if(pcal == null){
        			throw new RuntimeException("Null principal calendar for principalId " + principalId);
        		}
            }
        }

        return pcal;
    }

    @Override
	public CalendarEntries getPreviousCalendarEntry(String tkCalendarId, Date beginDateCurrentCalendar){
		return calendarDao.getPreviousCalendarEntry(tkCalendarId, beginDateCurrentCalendar);
	}

	@Override
	public Calendar getCalendarByPrincipalIdAndDate(String principalId, Date asOfDate, boolean findLeaveCal) {
		Calendar pcal = null;
        List<Job> currentJobs = TkServiceLocator.getJobService().getJobs(principalId, asOfDate);
        if(currentJobs.size() < 1){
           return pcal;
        }
        Job job = currentJobs.get(0);
        if (principalId == null || job == null) {
            return pcal;
        } else {
            PayType payType = job.getPayTypeObj();
            if (payType == null)  {
                throw new RuntimeException("No paytype setup for "+principalId + " job number: "+job.getJobNumber());
            }

            PrincipalHRAttributes principalCalendar = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, asOfDate);
            if(principalCalendar == null){
                throw new RuntimeException("No principal hr attribute setup for "+principalId);
            }
            if(!findLeaveCal) {
            	pcal = principalCalendar.getCalendar();
            	if (pcal == null){
            		pcal = principalCalendar.getLeaveCalObj();
            		if(pcal == null){
            			return pcal;
            		}
            	}
            } else {
        		pcal = principalCalendar.getLeaveCalObj();
        		if(pcal == null){
        			return pcal;
        		}
            }
        }

        return pcal;
	}

	@Override
	public CalendarEntries getCurrentCalendarDatesForLeaveCalendar(
			String principalId, Date currentDate) {
		CalendarEntries pcd = null;
        Calendar calendar = getCalendarByPrincipalIdAndDate(principalId, currentDate, true);
        if(calendar != null) {
		    pcd = TkServiceLocator.getCalendarEntriesService().getCurrentCalendarEntriesByCalendarId(calendar.getHrCalendarId(), currentDate);
		    if(pcd != null) {
		    	pcd.setCalendarObj(calendar);
		    }
        }
		return pcd;
	}

}
