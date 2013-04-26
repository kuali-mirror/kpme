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
package org.kuali.kpme.core.bo.calendar.dao;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.DateTime;
import org.kuali.kpme.core.bo.calendar.Calendar;
import org.kuali.kpme.core.bo.calendar.entry.CalendarEntry;
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
	
	public CalendarEntry getPreviousCalendarEntry(String tkCalendarId, DateTime beginDateCurrentCalendar){
        Criteria payEndDateCriteria = new Criteria();
        payEndDateCriteria.addEqualTo("hr_py_calendar_id", tkCalendarId);
        payEndDateCriteria.addLessOrEqualThan("end_period_date", beginDateCurrentCalendar.toDate());
        
        return (CalendarEntry) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(CalendarEntry.class,payEndDateCriteria));
        
	}

    @Override
    public List<Calendar> getCalendars(String calendarName, String calendarTypes, String flsaBeginDay, String flsaBeginTime) {
        Criteria crit = new Criteria();

        List<Calendar> results = new ArrayList<Calendar>();

        if(StringUtils.isNotBlank(calendarName) && StringUtils.isNotEmpty(calendarName)){
            crit.addLike("calendarName", calendarName);
        }
        if(StringUtils.isNotBlank(calendarTypes) && StringUtils.isNotEmpty(calendarTypes)){
            crit.addLike("calendarTypes", calendarTypes);
        }
        if(StringUtils.isNotBlank(flsaBeginDay) && StringUtils.isNotEmpty(flsaBeginDay)){
            crit.addLike("flsaBeginDay", flsaBeginDay);
        }
        if(flsaBeginTime != null){
            SimpleDateFormat sdFormat = new SimpleDateFormat("hh:mm aa");
            try {
                Time flsaTime = new Time(sdFormat.parse(flsaBeginTime).getTime());
                crit.addLike("flsaBeginTime", flsaTime);
            } catch (ParseException e)  {

            }
        }
        Query query = QueryFactory.newQuery(Calendar.class, crit);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        results.addAll(c);

        return results;
    }



}
