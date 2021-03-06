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
package org.kuali.kpme.core.calendar.dao;

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
import org.kuali.kpme.core.calendar.CalendarBo;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class CalendarDaoOjbImpl extends PlatformAwareDaoBaseOjb  implements CalendarDao {

	private static final Logger LOG = Logger.getLogger(CalendarDaoOjbImpl.class);

	public void saveOrUpdate(CalendarBo calendar) {
		this.getPersistenceBrokerTemplate().store(calendar);
	}

	public void saveOrUpdate(List<CalendarBo> calendarList) {
		if (calendarList != null) {
			for (CalendarBo calendar : calendarList) {
				this.getPersistenceBrokerTemplate().store(calendar);
			}
		}
	}
	
	// KPME-2992
	@Override
	public CalendarBo getCalendarByName(String calendarName) {
		Criteria currentRecordCriteria = new Criteria();
		if(StringUtils.isNotBlank(calendarName) && StringUtils.isNotEmpty(calendarName)){
            currentRecordCriteria.addLike("UPPER(calendarName)", calendarName.toUpperCase());
        }
		return (CalendarBo) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(CalendarBo.class, currentRecordCriteria));
	}
	
	public CalendarBo getCalendar(String hrPyCalendarId) {
		Criteria currentRecordCriteria = new Criteria();
		currentRecordCriteria.addEqualTo("hrCalendarId", hrPyCalendarId);

		return (CalendarBo) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(CalendarBo.class, currentRecordCriteria));
	}

	// Is pay clendar groups alwasy unique?
	public CalendarBo getCalendarByGroup(String pyCalendarGroup) {
		Criteria currentRecordCriteria = new Criteria();
		currentRecordCriteria.addEqualTo("calendarName", pyCalendarGroup);

		return (CalendarBo) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(CalendarBo.class, currentRecordCriteria));
	}

    @Override
    public List<CalendarBo> getCalendars(String calendarName, String calendarTypes, String flsaBeginDay, String flsaBeginTime) {
        Criteria crit = new Criteria();

        List<CalendarBo> results = new ArrayList<CalendarBo>();

        if(StringUtils.isNotBlank(calendarName) && StringUtils.isNotEmpty(calendarName)){
            crit.addLike("UPPER(calendarName)", calendarName.toUpperCase()); // KPME-2695
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
        Query query = QueryFactory.newQuery(CalendarBo.class, crit);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        results.addAll(c);

        return results;
    }



}
