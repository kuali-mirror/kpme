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
package org.kuali.kpme.core.calendar.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.calendar.service.CalendarService;
import org.kuali.kpme.core.calendar.Calendar;
import org.kuali.kpme.core.calendar.dao.CalendarDao;
import org.kuali.kpme.core.job.Job;
import org.kuali.kpme.core.paytype.PayType;
import org.kuali.kpme.core.principal.PrincipalHRAttributes;
import org.kuali.kpme.core.service.HrServiceLocator;

public class CalendarServiceImpl implements CalendarService {

	private static final Logger LOG = Logger.getLogger(CalendarServiceImpl.class);
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
    public Calendar getCalendarByPrincipalIdAndDate(String principalId, LocalDate beginDate, LocalDate endDate, boolean findLeaveCal) {
        Calendar pcal = null;
        List<Job> currentJobs = (List<Job>) HrServiceLocator.getJobService().getJobs(principalId, endDate);
        if(currentJobs.size() < 1){
            return pcal;
        }
        Job job = currentJobs.get(0);
        if (principalId == null || job == null) {
            return pcal;
        } else {
            PayType payType = job.getPayTypeObj();
            if (payType == null)  {
//                throw new RuntimeException("No paytype setup for "+principalId + " job number: "+job.getJobNumber());
            	LOG.warn("No paytype setup for "+principalId + " job number: "+job.getJobNumber());
            }

            PrincipalHRAttributes principalCalendar = (PrincipalHRAttributes) HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, beginDate);
            if(principalCalendar == null){
            	return null;
                //throw new RuntimeException("No principal hr attribute setup for "+principalId);
            }
            if(!findLeaveCal) {
                pcal = principalCalendar.getCalendar();
                if (pcal == null){
                	//shouldn't we just be returning null?
                	//which code expects a non-null value being returned?
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
        List<Job> currentJobs = (List<Job>) HrServiceLocator.getJobService().getJobs(principalId, asOfDate);
        if(currentJobs.size() < 1){
           return pcal;
        }
        Job job = currentJobs.get(0);
        if (principalId == null || job == null) {
            return pcal;
        } else {
            PayType payType = job.getPayTypeObj();
            if (payType == null)  {
//                throw new RuntimeException("No paytype setup for "+principalId + " job number: "+job.getJobNumber());
            	LOG.warn("No paytype setup for "+principalId + " job number: "+job.getJobNumber());
            }

            PrincipalHRAttributes principalCalendar = (PrincipalHRAttributes) HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, asOfDate);
            if(principalCalendar == null){
//                throw new RuntimeException("No principal hr attribute setup for "+principalId);
            	LOG.warn("No principal hr attribute setup for "+principalId);
            	return null;
            }
            if(!findLeaveCal) {
            	pcal = principalCalendar.getCalendar();
            	if (pcal == null){
                	//shouldn't we just be returning null?
                	//which code expects a non-null value being returned?
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
    public List<Calendar> getCalendars(String calendarName, String calendarTypes, String flsaBeginDay, String flsaBeginTime) {
        return  calendarDao.getCalendars(calendarName, calendarTypes, flsaBeginDay, flsaBeginTime);
    }

}
