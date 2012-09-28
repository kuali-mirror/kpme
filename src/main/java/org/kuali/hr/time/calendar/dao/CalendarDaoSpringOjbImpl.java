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
package org.kuali.hr.time.calendar.dao;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.time.calendar.Calendar;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class CalendarDaoSpringOjbImpl extends PlatformAwareDaoBaseOjb  implements CalendarDao {

	private static final Logger LOG = Logger.getLogger(CalendarDaoSpringOjbImpl.class);

	public void saveOrUpdate(Calendar calendar) {
		this.getPersistenceBrokerTemplate().store(calendar);
	}

	public void saveOrUpdate(List<Calendar> calendarList) {
		if (calendarList != null) {
			for (Calendar calendar : calendarList) {
				this.getPersistenceBrokerTemplate().store(calendar);
			}
		}
	}

	public Calendar getCalendar(String hrPyCalendarId) {
		Criteria currentRecordCriteria = new Criteria();
		currentRecordCriteria.addEqualTo("hrCalendarId", hrPyCalendarId);

		return (Calendar) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(Calendar.class, currentRecordCriteria));
	}

	// Is pay clendar groups alwasy unique?
	public Calendar getCalendarByGroup(String pyCalendarGroup) {
		Criteria currentRecordCriteria = new Criteria();
		currentRecordCriteria.addEqualTo("calendarName", pyCalendarGroup);

		return (Calendar) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(Calendar.class, currentRecordCriteria));
	}
	
	public CalendarEntries getPreviousCalendarEntry(String tkCalendarId, Date beginDateCurrentCalendar){
        Criteria payEndDateCriteria = new Criteria();
        payEndDateCriteria.addEqualTo("hr_py_calendar_id", tkCalendarId);
        payEndDateCriteria.addLessOrEqualThan("end_period_date", beginDateCurrentCalendar);
        
        return (CalendarEntries) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(CalendarEntries.class,payEndDateCriteria));
        
	}
}
