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
package org.kuali.hr.time.holidaycalendar.dao;

import java.util.Date;
import java.util.List;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.time.holidaycalendar.HolidayCalendar;
import org.kuali.hr.time.holidaycalendar.HolidayCalendarDateEntry;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class HolidayCalendarDaoImpl extends PlatformAwareDaoBaseOjb implements HolidayCalendarDao {

	@Override
	public HolidayCalendar getHolidayCalendarByGroup(String holidayCalendarGroup) {
		Criteria root = new Criteria();
		root.addEqualTo("holidayCalendarGroup", holidayCalendarGroup);
		return (HolidayCalendar) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(HolidayCalendar.class, root));
	}
	
	@SuppressWarnings("unchecked")
	public List<HolidayCalendarDateEntry> getHolidayCalendarDateEntriesForPayPeriod(String hrHolidayCalendarId, Date startDate, Date endDate){
		Criteria root = new Criteria();
		root.addEqualTo("hrHolidayCalendarId", hrHolidayCalendarId);
		root.addBetween("holidayDate", new java.sql.Date(startDate.getTime()), new java.sql.Date(endDate.getTime()));
		return (List<HolidayCalendarDateEntry>)this.getPersistenceBrokerTemplate().getCollectionByQuery(QueryFactory.newQuery(HolidayCalendarDateEntry.class, root));
	}

	@SuppressWarnings("unchecked")
	public HolidayCalendarDateEntry getHolidayCalendarDateEntryByDate(String hrHolidayCalendarId, Date startDate){
		Criteria root = new Criteria();
		root.addEqualTo("hrHolidayCalendarId", hrHolidayCalendarId);
		root.addEqualTo("holidayDate", startDate);
		return (HolidayCalendarDateEntry)this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(HolidayCalendarDateEntry.class, root));
	}
}
