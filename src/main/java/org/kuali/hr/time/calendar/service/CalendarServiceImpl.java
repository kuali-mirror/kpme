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
package org.kuali.hr.time.calendar.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.hr.job.Job;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.time.calendar.Calendar;
import org.kuali.hr.time.calendar.CalendarEntry;
import org.kuali.hr.time.calendar.dao.CalendarDao;
import org.kuali.hr.time.paytype.PayType;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TkConstants;

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
    public CalendarEntry getCalendarDatesByPayEndDate(String principalId, DateTime payEndDate, String calendarType) {
        PrincipalHRAttributes principalCalendar = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, payEndDate.toLocalDate());
        
        Calendar calendar = null;
        if (StringUtils.equalsIgnoreCase(calendarType, TkConstants.PAY_CALENDAR_TYPE)) {
        	calendar = getCalendarByGroup(principalCalendar.getPayCalendar());
        } else if (StringUtils.equalsIgnoreCase(calendarType, LMConstants.LEAVE_CALENDAR_TYPE)) {
        	calendar = getCalendarByGroup(principalCalendar.getLeaveCalendar());
        }
        
        CalendarEntry calendarEntry = TkServiceLocator.getCalendarEntryService().getCalendarEntryByIdAndPeriodEndDate(calendar.getHrCalendarId(), payEndDate);
        calendarEntry.setCalendarObj(calendar);
        
        return calendarEntry;
    }

	@Override
	public CalendarEntry getCurrentCalendarDates(String principalId, DateTime currentDate) {
		CalendarEntry pcd = null;
        Calendar calendar = getCalendarByPrincipalIdAndDate(principalId, currentDate.toLocalDate(), false);
        if(calendar != null) {
		    pcd = TkServiceLocator.getCalendarEntryService().getCurrentCalendarEntryByCalendarId(calendar.getHrCalendarId(), currentDate);
		    if(pcd != null) {
		    	pcd.setCalendarObj(calendar);
		    }
        }
		return pcd;
	}

    @Override
    public CalendarEntry getCurrentCalendarDates(String principalId, DateTime beginDate, DateTime endDate) {
        CalendarEntry pcd = null;
        Calendar calendar = getCalendarByPrincipalIdAndDate(principalId, beginDate.toLocalDate(), endDate.toLocalDate(), false);
        if(calendar != null) {
            pcd = TkServiceLocator.getCalendarEntryService().getCalendarEntryByCalendarIdAndDateRange(calendar.getHrCalendarId(), beginDate, endDate);
            if(pcd != null) {
                pcd.setCalendarObj(calendar);
            }
        }
        return pcd;
    }

    @Override
	public CalendarEntry getPreviousCalendarEntry(String tkCalendarId, DateTime beginDateCurrentCalendar){
		return calendarDao.getPreviousCalendarEntry(tkCalendarId, beginDateCurrentCalendar);
	}

    @Override
    public Calendar getCalendarByPrincipalIdAndDate(String principalId, LocalDate beginDate, LocalDate endDate, boolean findLeaveCal) {
        Calendar pcal = null;
        List<Job> currentJobs = TkServiceLocator.getJobService().getJobs(principalId, endDate);
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

            PrincipalHRAttributes principalCalendar = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, beginDate);
            if(principalCalendar == null){
            	return null;
                //throw new RuntimeException("No principal hr attribute setup for "+principalId);
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
	public Calendar getCalendarByPrincipalIdAndDate(String principalId, LocalDate asOfDate, boolean findLeaveCal) {
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
	public CalendarEntry getCurrentCalendarDatesForLeaveCalendar(
			String principalId, DateTime currentDate) {
		CalendarEntry pcd = null;
        Calendar calendar = getCalendarByPrincipalIdAndDate(principalId, currentDate.toLocalDate(), true);
        if(calendar != null) {
		    pcd = TkServiceLocator.getCalendarEntryService().getCurrentCalendarEntryByCalendarId(calendar.getHrCalendarId(), currentDate);
		    if(pcd != null) {
		    	pcd.setCalendarObj(calendar);
		    }
        }
		return pcd;
	}

    @Override
    public CalendarEntry getCurrentCalendarDatesForLeaveCalendar(String principalId, DateTime beginDate, DateTime endDate) {
        CalendarEntry pcd = null;
        Calendar calendar = getCalendarByPrincipalIdAndDate(principalId, beginDate.toLocalDate(), endDate.toLocalDate(), true);
        if(calendar != null) {
            pcd = TkServiceLocator.getCalendarEntryService().getCalendarEntryByCalendarIdAndDateRange(calendar.getHrCalendarId(), beginDate, endDate);
            if(pcd != null) {
                pcd.setCalendarObj(calendar);
            }
        }
        return pcd;
    }

    @Override
    public List<Calendar> getCalendars(String calendarName, String calendarTypes, String flsaBeginDay, String flsaBeginTime) {
        return  calendarDao.getCalendars(calendarName, calendarTypes, flsaBeginDay, flsaBeginTime);
    }

}
