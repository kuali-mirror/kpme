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
package org.kuali.hr.time.holidaycalendar.service;

import org.kuali.hr.job.Job;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.holidaycalendar.HolidayCalendar;
import org.kuali.hr.time.holidaycalendar.HolidayCalendarDateEntry;
import org.kuali.hr.time.holidaycalendar.dao.HolidayCalendarDao;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TkConstants;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class HolidayCalendarServiceImpl implements HolidayCalendarService {
	private HolidayCalendarDao holidayCalendarDao;
	
	
	@Override
	public HolidayCalendar getHolidayCalendarByGroup(String holidayCalendarGroup) {
		return holidayCalendarDao.getHolidayCalendarByGroup(holidayCalendarGroup);
	}


	public HolidayCalendarDao getHolidayCalendarDao() {
		return holidayCalendarDao;
	}


	public void setHolidayCalendarDao(HolidayCalendarDao holidayCalendarDao) {
		this.holidayCalendarDao = holidayCalendarDao;
	}


	@Override
	public List<HolidayCalendarDateEntry> getHolidayCalendarDateEntriesForPayPeriod(
			String hrHolidayCalendarId, Date startDate, Date endDate) {
		return holidayCalendarDao.getHolidayCalendarDateEntriesForPayPeriod(hrHolidayCalendarId, startDate, endDate);
	}

	@Override
	public HolidayCalendarDateEntry getHolidayCalendarDateEntryByDate(String hrHolidayCalendarId, Date startDate) {
		return holidayCalendarDao.getHolidayCalendarDateEntryByDate(hrHolidayCalendarId, startDate);
	}

	@Override
	public Assignment getAssignmentToApplyHolidays(TimesheetDocument timesheetDocument, java.sql.Date payEndDate) {
		Job primaryJob = TkServiceLocator.getJobService().getPrimaryJob(timesheetDocument.getPrincipalId(), payEndDate);
		for(Assignment assign : timesheetDocument.getAssignments()){
			if(assign.getJobNumber().equals(primaryJob.getJobNumber())){
				return assign;
			}
		}
		return null;
	}


	@Override
	public BigDecimal calculateHolidayHours(Job job, BigDecimal holidayHours) {
		BigDecimal fte = job.getStandardHours().divide(new BigDecimal(40.0),TkConstants.BIG_DECIMAL_SCALE);
		return fte.multiply(holidayHours).setScale(TkConstants.BIG_DECIMAL_SCALE);
	}

}
