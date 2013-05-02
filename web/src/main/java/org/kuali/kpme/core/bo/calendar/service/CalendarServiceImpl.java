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
package org.kuali.kpme.core.bo.calendar.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.calendar.Calendar;
import org.kuali.kpme.core.bo.calendar.dao.CalendarDao;
import org.kuali.kpme.core.bo.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.bo.job.Job;
import org.kuali.kpme.core.bo.paytype.PayType;
import org.kuali.kpme.core.bo.principal.PrincipalHRAttributes;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.rice.krad.util.ObjectUtils;

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
        PrincipalHRAttributes principalCalendar = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, payEndDate.toLocalDate());
        
        Calendar calendar = null;
        if(ObjectUtils.isNull(principalCalendar)) {
        	return null;
        }
        if (StringUtils.equalsIgnoreCase(calendarType, HrConstants.PAY_CALENDAR_TYPE)) {
        	calendar = getCalendarByGroup(principalCalendar.getPayCalendar());
        } else if (StringUtils.equalsIgnoreCase(calendarType, HrConstants.LEAVE_CALENDAR_TYPE)) {
        	calendar = getCalendarByGroup(principalCalendar.getLeaveCalendar());
        }
        
        CalendarEntry calendarEntry = HrServiceLocator.getCalendarEntryService().getCalendarEntryByIdAndPeriodEndDate(calendar.getHrCalendarId(), payEndDate);
        
        if(ObjectUtils.isNotNull(calendarEntry))
        	calendarEntry.setCalendarObj(calendar);
        
        return calendarEntry;
    }

	@Override
	public CalendarEntry getCurrentCalendarDates(String principalId, DateTime currentDate) {
		CalendarEntry pcd = null;
        Calendar calendar = getCalendarByPrincipalIdAndDate(principalId, currentDate.toLocalDate(), false);
        if(calendar != null) {
		    pcd = HrServiceLocator.getCalendarEntryService().getCurrentCalendarEntryByCalendarId(calendar.getHrCalendarId(), currentDate);
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
            pcd = HrServiceLocator.getCalendarEntryService().getCalendarEntryByCalendarIdAndDateRange(calendar.getHrCalendarId(), beginDate, endDate);
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
        List<Job> currentJobs = HrServiceLocator.getJobService().getJobs(principalId, endDate);
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

            PrincipalHRAttributes principalCalendar = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, beginDate);
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
        List<Job> currentJobs = HrServiceLocator.getJobService().getJobs(principalId, asOfDate);
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

            PrincipalHRAttributes principalCalendar = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, asOfDate);
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
		    pcd = HrServiceLocator.getCalendarEntryService().getCurrentCalendarEntryByCalendarId(calendar.getHrCalendarId(), currentDate);
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
            pcd = HrServiceLocator.getCalendarEntryService().getCalendarEntryByCalendarIdAndDateRange(calendar.getHrCalendarId(), beginDate, endDate);
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
