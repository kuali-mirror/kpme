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
package org.kuali.kpme.core.calendar.entry.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntryContract;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntryPeriodType;
import org.kuali.kpme.core.api.calendar.entry.service.CalendarEntryService;
import org.kuali.kpme.core.api.leaveplan.LeavePlanContract;
import org.kuali.kpme.core.api.principal.PrincipalHRAttributesContract;
import org.kuali.kpme.core.calendar.Calendar;
import org.kuali.kpme.core.calendar.dao.CalendarDao;
import org.kuali.kpme.core.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.calendar.entry.dao.CalendarEntryDao;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.util.ObjectUtils;

public class CalendarEntryServiceImpl implements CalendarEntryService {

    private CalendarEntryDao calendarEntryDao;
    private CalendarDao calendarDao;

	public void setCalendarDao(CalendarDao calendarDao) {
		this.calendarDao = calendarDao;
	}

    public void setCalendarEntryDao(CalendarEntryDao calendarEntryDao) {
        this.calendarEntryDao = calendarEntryDao;
    }

    public CalendarEntry getCalendarEntry(String hrCalendarEntryId) {

        return calendarEntryDao.getCalendarEntry(hrCalendarEntryId);
    }

    @Override
    public CalendarEntry getCalendarEntryByIdAndPeriodEndDate(String hrCalendarId, DateTime endPeriodDate) {
        return calendarEntryDao.getCalendarEntryByIdAndPeriodEndDate(hrCalendarId, endPeriodDate);
    }

    @Override
    public CalendarEntry getCalendarEntryByCalendarIdAndDateRange(
            String hrCalendarId, DateTime beginDate, DateTime endDate) {
        return calendarEntryDao.getCalendarEntryByCalendarIdAndDateRange(hrCalendarId, beginDate, endDate);
    }

    @Override
    public CalendarEntry getCurrentCalendarEntryByCalendarId(
            String hrCalendarId, DateTime currentDate) {
        return calendarEntryDao.getCurrentCalendarEntryByCalendarId(hrCalendarId, currentDate);
    }

    @Override
    public CalendarEntry getPreviousCalendarEntryByCalendarId(String hrCalendarId, CalendarEntryContract pce) {
        return calendarEntryDao.getPreviousCalendarEntryByCalendarId(hrCalendarId, (CalendarEntry)pce);
    }

    @Override
    public CalendarEntry getNextCalendarEntryByCalendarId(String hrCalendarId, CalendarEntryContract pce) {
        return calendarEntryDao.getNextCalendarEntryByCalendarId(hrCalendarId, (CalendarEntry)pce);
    }

    @Override
    public List<CalendarEntry> getCurrentCalendarEntriesNeedsScheduled(int thresholdDays, DateTime asOfDate) {
        return calendarEntryDao.getCurrentCalendarEntryNeedsScheduled(thresholdDays, asOfDate);
    }

    @Override
    public CalendarEntry createNextCalendarEntry(CalendarEntryContract calendarEntry, CalendarEntryPeriodType type) {
        CalendarEntry newEntry = new CalendarEntry();
        calendarEntry = (CalendarEntry) calendarEntry;
        newEntry.setCalendarName(calendarEntry.getCalendarName());
        newEntry.setHrCalendarId(calendarEntry.getHrCalendarId());
        newEntry.setCalendarObj((Calendar)calendarEntry.getCalendarObj());
        
        if (type == null) {
            type = CalendarEntryPeriodType.BI_WEEKLY;
        }
        if (CalendarEntryPeriodType.WEEKLY.equals(type)
                || CalendarEntryPeriodType.BI_WEEKLY.equals(type)) {
            int weekly_multiplier = 2;
            if (CalendarEntryPeriodType.WEEKLY.equals(type)) {
                weekly_multiplier = 1;
            }
            newEntry.setBeginPeriodFullDateTime(calendarEntry.getBeginPeriodFullDateTime().plusWeeks(weekly_multiplier));
            newEntry.setEndPeriodFullDateTime(calendarEntry.getEndPeriodFullDateTime().plusWeeks(weekly_multiplier));
            newEntry.setBatchInitiateFullDateTime(calendarEntry.getBatchInitiateFullDateTime().plusWeeks(weekly_multiplier));
            newEntry.setBatchEndPayPeriodFullDateTime(calendarEntry.getBatchEndPayPeriodFullDateTime().plusWeeks(weekly_multiplier));
            newEntry.setBatchEmployeeApprovalFullDateTime(calendarEntry.getBatchEmployeeApprovalFullDateTime().plusWeeks(weekly_multiplier));
            newEntry.setBatchSupervisorApprovalFullDateTime(calendarEntry.getBatchSupervisorApprovalFullDateTime().plusWeeks(weekly_multiplier));
        } else if (CalendarEntryPeriodType.MONTHLY.equals(type)) {
            newEntry.setBeginPeriodFullDateTime(calendarEntry.getBeginPeriodFullDateTime().plusMonths(1));
            newEntry.setEndPeriodFullDateTime(calendarEntry.getEndPeriodFullDateTime().plusMonths(1));
            newEntry.setBatchInitiateFullDateTime(calendarEntry.getBatchInitiateFullDateTime().plusMonths(1));
            newEntry.setBatchEndPayPeriodFullDateTime(calendarEntry.getBatchEndPayPeriodFullDateTime().plusMonths(1));
            newEntry.setBatchEmployeeApprovalFullDateTime(calendarEntry.getBatchEmployeeApprovalFullDateTime().plusMonths(1));
            newEntry.setBatchSupervisorApprovalFullDateTime(calendarEntry.getBatchSupervisorApprovalFullDateTime().plusMonths(1));
        } else if (CalendarEntryPeriodType.SEMI_MONTHLY.equals(type)) {
            newEntry.setBeginPeriodFullDateTime(plusSemiMonth(calendarEntry.getBeginPeriodFullDateTime()));
            newEntry.setEndPeriodFullDateTime(plusSemiMonth(calendarEntry.getEndPeriodFullDateTime()));
            newEntry.setBatchInitiateFullDateTime(plusSemiMonth(calendarEntry.getBatchInitiateFullDateTime()));
            newEntry.setBatchEndPayPeriodFullDateTime(plusSemiMonth(calendarEntry.getBatchEndPayPeriodFullDateTime()));
            newEntry.setBatchEmployeeApprovalFullDateTime(plusSemiMonth(calendarEntry.getBatchEmployeeApprovalFullDateTime()));
            newEntry.setBatchSupervisorApprovalFullDateTime(plusSemiMonth(calendarEntry.getBatchSupervisorApprovalFullDateTime()));
        }
        KRADServiceLocator.getBusinessObjectService().save(newEntry);
        return getNextCalendarEntryByCalendarId(calendarEntry.getHrCalendarId(), calendarEntry);
    }

    private DateTime plusSemiMonth(DateTime date) {
        //so assuming the common pairs of this are the 1st & 16th, and then 15th and the last day,
        // and 14th with the last day minus 1
        //so we'll peek at the current date and try to figure out the best guesses for addition.
        if (date.getDayOfMonth() == date.dayOfMonth().getMaximumValue()) {
            //date is on the last day of the month.  Set next date to the 15th
        	return date.plusMonths(1).withDayOfMonth(15);
        } else if (date.getDayOfMonth() == 15) {
            //we are on the 15th of the month, so now lets go to the end of the month
        	return date.withDayOfMonth(date.dayOfMonth().getMaximumValue());
        } else if (date.getDayOfMonth() == 1) {
            //first of the month, next would be 16
            return date.withDayOfMonth(16);
        } else if (date.getDayOfMonth() == 16) {
            //16th, so add a month and set day to '1'
        	return date.plusMonths(1).withDayOfMonth(1);
        } else if (date.getDayOfMonth() == 14) {
            //14th day, set next one to last day minus 1
        	return date.withDayOfMonth(date.dayOfMonth().getMaximumValue() - 1);
        } else if (date.getDayOfMonth() == date.dayOfMonth().getMaximumValue() - 1) {
            //date is on the second to last day of the month.  Set next date to the 14th
        	return date.plusMonths(1).withDayOfMonth(14);
        } else {
            // so it isn't one of the common dates... i guess we'll just add 15 days...
        	return date.plusDays(15);
        }
    }

    @Override
    public List<CalendarEntry> getFutureCalendarEntries(String hrCalendarId, DateTime currentDate, int numberOfEntries) {
        return calendarEntryDao.getFutureCalendarEntries(hrCalendarId, currentDate, numberOfEntries);
    }
    
    @Override
    public List<CalendarEntry> getCalendarEntriesEndingBetweenBeginAndEndDate(String hrCalendarId, DateTime beginDate, DateTime endDate) {
        return calendarEntryDao.getCalendarEntriesEndingBetweenBeginAndEndDate(hrCalendarId, beginDate, endDate);
    }
    
    @Override
    public List<CalendarEntry> getAllCalendarEntriesForCalendarId(String hrCalendarId) {
    	return calendarEntryDao.getAllCalendarEntriesForCalendarId(hrCalendarId);
    }
    
    @Override
    public List<CalendarEntry> getAllCalendarEntriesForCalendarIdAndYear(String hrCalendarId, String year) {
    	return calendarEntryDao.getAllCalendarEntriesForCalendarIdAndYear(hrCalendarId, year);
    }
    
    @Override
    public List<CalendarEntry> getAllCalendarEntriesForCalendarIdUpToPlanningMonths(String hrCalendarId, String principalId) {
		int planningMonths = 0;
		PrincipalHRAttributesContract principalHRAttributes = HrServiceLocator
				.getPrincipalHRAttributeService().getPrincipalCalendar(
						principalId, LocalDate.now());
		if (principalHRAttributes != null
				&& principalHRAttributes.getLeavePlan() != null) {
			LeavePlanContract lp = HrServiceLocator.getLeavePlanService()
					.getLeavePlan(principalHRAttributes.getLeavePlan(),
							LocalDate.now());
			if (lp != null && lp.getPlanningMonths() != null) {
				planningMonths = Integer.parseInt(lp.getPlanningMonths());
			}
		}
    	List<CalendarEntry> futureCalEntries = getFutureCalendarEntries(hrCalendarId, LocalDate.now().toDateTimeAtStartOfDay(), planningMonths);
    	CalendarEntry futureCalEntry = null;
    	if (futureCalEntries != null && !futureCalEntries.isEmpty()) {
    		futureCalEntry = futureCalEntries.get(futureCalEntries.size() - 1);
    	}
    	DateTime cutOffTime;
    	if(futureCalEntry != null) {
    		cutOffTime = futureCalEntry.getEndPeriodFullDateTime();
    	} else {
	    	CalendarEntry currentCE = getCurrentCalendarEntryByCalendarId(hrCalendarId, LocalDate.now().toDateTimeAtStartOfDay());
	    	cutOffTime = currentCE.getEndPeriodFullDateTime();    	
    	}
    	return getAllCalendarEntriesForCalendarIdUpToCutOffTime(hrCalendarId, cutOffTime);
    }
    
    @Override
    public List<CalendarEntry> getAllCalendarEntriesForCalendarIdUpToCutOffTime(String hrCalendarId, DateTime cutOffTime) {
    	return calendarEntryDao.getAllCalendarEntriesForCalendarIdUpToCutOffTime(hrCalendarId, cutOffTime);
    }
    
    //******** kpme-2396 ********
    @Override
	public CalendarEntry getCurrentCalendarDates(String principalId, DateTime currentDate) {
		CalendarEntry pcd = null;
        Calendar calendar = (Calendar)HrServiceLocator.getCalendarService().getCalendarByPrincipalIdAndDate(principalId, currentDate.toLocalDate(), false);
        if(calendar != null) {
		    pcd = (CalendarEntry) HrServiceLocator.getCalendarEntryService().getCurrentCalendarEntryByCalendarId(calendar.getHrCalendarId(), currentDate);
		    if(pcd != null) {
		    	pcd.setCalendarObj(calendar);
		    }
        }
		return pcd;
	}
    
    @Override
    public CalendarEntry getCurrentCalendarDates(String principalId, DateTime beginDate, DateTime endDate) {
        CalendarEntry pcd = null;
        Calendar calendar = (Calendar) HrServiceLocator.getCalendarService().getCalendarByPrincipalIdAndDate(principalId, beginDate.toLocalDate(), endDate.toLocalDate(), false);
        if(calendar != null) {
            pcd = (CalendarEntry) HrServiceLocator.getCalendarEntryService().getCalendarEntryByCalendarIdAndDateRange(calendar.getHrCalendarId(), beginDate, endDate);
            if(pcd != null) {
                pcd.setCalendarObj(calendar);
            }
        }
        return pcd;
    }
    
    @Override
    public CalendarEntry getCalendarDatesByPayEndDate(String principalId, DateTime payEndDate, String calendarType) {
        PrincipalHRAttributesContract principalCalendar = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, payEndDate.toLocalDate());
        
        Calendar calendar = null;
        if(ObjectUtils.isNull(principalCalendar)) {
        	return null;
        }
        if (StringUtils.equalsIgnoreCase(calendarType, HrConstants.PAY_CALENDAR_TYPE)) {
        	calendar = (Calendar) HrServiceLocator.getCalendarService().getCalendarByGroup(principalCalendar.getPayCalendar());
        } else if (StringUtils.equalsIgnoreCase(calendarType, HrConstants.LEAVE_CALENDAR_TYPE)) {
        	calendar = (Calendar) HrServiceLocator.getCalendarService().getCalendarByGroup(principalCalendar.getLeaveCalendar());
        }
        
        if (calendar == null) {
        	return null;
        }
        CalendarEntry calendarEntry = (CalendarEntry) HrServiceLocator.getCalendarEntryService().getCalendarEntryByIdAndPeriodEndDate(calendar.getHrCalendarId(), payEndDate);
        
        if(ObjectUtils.isNotNull(calendarEntry)) {
        	calendarEntry.setCalendarObj(calendar);
        }
        
        return calendarEntry;
    }
    
    @Override
    public CalendarEntry getCurrentCalendarDatesForLeaveCalendar(String principalId, DateTime beginDate, DateTime endDate) {
        CalendarEntry pcd = null;
        Calendar calendar = (Calendar)HrServiceLocator.getCalendarService().getCalendarByPrincipalIdAndDate(principalId, beginDate.toLocalDate(), endDate.toLocalDate(), true);
        if(calendar != null) {
            pcd = (CalendarEntry) HrServiceLocator.getCalendarEntryService().getCalendarEntryByCalendarIdAndDateRange(calendar.getHrCalendarId(), beginDate, endDate);
            if(pcd != null) {
                pcd.setCalendarObj(calendar);
            }
        }
        return pcd;
    }
    
    @Override
	public CalendarEntry getCurrentCalendarDatesForLeaveCalendar(
			String principalId, DateTime currentDate) {
		CalendarEntry pcd = null;
        Calendar calendar = (Calendar)HrServiceLocator.getCalendarService().getCalendarByPrincipalIdAndDate(principalId, currentDate.toLocalDate(), true);
        if(calendar != null) {
		    pcd = (CalendarEntry) HrServiceLocator.getCalendarEntryService().getCurrentCalendarEntryByCalendarId(calendar.getHrCalendarId(), currentDate);
		    if(pcd != null) {
		    	pcd.setCalendarObj(calendar);
		    }
        }
		return pcd;
	}

    @Override
    public List<CalendarEntry> getSearchResults(String calendarName, String calendarTypes, LocalDate fromBeginDate, LocalDate toBeginDate, LocalDate fromendDate, LocalDate toEndDate) {
        return calendarEntryDao.getSearchResults(calendarName, calendarTypes, fromBeginDate, toBeginDate, fromendDate, toEndDate);
    }


}
