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
package org.kuali.kpme.core.bo.calendar.entry.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryByCriteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.kuali.kpme.core.bo.calendar.entry.CalendarEntry;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class CalendarEntryDaoOjbImpl extends PlatformAwareDaoBaseOjb implements CalendarEntryDao {


    public void saveOrUpdate(CalendarEntry calendarEntry) {
        this.getPersistenceBrokerTemplate().store(calendarEntry);
    }

    public CalendarEntry getCalendarEntry(String hrCalendarEntryId) {
        Criteria currentRecordCriteria = new Criteria();
        currentRecordCriteria.addEqualTo("hrCalendarEntryId", hrCalendarEntryId);

        return (CalendarEntry) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(CalendarEntry.class, currentRecordCriteria));
    }

    @Override
    public CalendarEntry getCalendarEntryByIdAndPeriodEndDate(String hrCalendarId, DateTime endPeriodDate) {
        Criteria root = new Criteria();
        root.addEqualTo("hrCalendarId", hrCalendarId);
        root.addEqualTo("endPeriodDateTime", endPeriodDate.toDate());

        Query query = QueryFactory.newQuery(CalendarEntry.class, root);
        CalendarEntry pce = (CalendarEntry) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
        return pce;
    }

    @Override
    public CalendarEntry getCurrentCalendarEntryByCalendarId(
            String hrCalendarId, DateTime currentDate) {
        Criteria root = new Criteria();
//		Criteria beginDate = new Criteria();
//		Criteria endDate = new Criteria();

//		beginDate.addEqualToField("hrPyCalendarId", Criteria.PARENT_QUERY_PREFIX + "hrPyCalendarId");
//		beginDate.addLessOrEqualThan("beginPeriodDateTime", currentDate);
//		ReportQueryByCriteria beginDateSubQuery = QueryFactory.newReportQuery(CalendarEntry.class, beginDate);
//		beginDateSubQuery.setAttributes(new String[] { "max(beginPeriodDateTime)" });

//		endDate.addEqualToField("hrPyCalendarId", Criteria.PARENT_QUERY_PREFIX + "hrPyCalendarId");
//		endDate.addGreaterOrEqualThan("endPeriodDateTime", currentDate);
//		ReportQueryByCriteria endDateSubQuery = QueryFactory.newReportQuery(CalendarEntry.class, endDate);
//		endDateSubQuery.setAttributes(new String[] { "min(endPeriodDateTime)" });

        root.addEqualTo("hrCalendarId", hrCalendarId);
        //root.addEqualTo("beginPeriodDateTime", beginDateSubQuery);
        root.addLessOrEqualThan("beginPeriodDateTime", currentDate.toDate());
        root.addGreaterThan("endPeriodDateTime", currentDate.toDate());
//		root.addEqualTo("endPeriodDateTime", endDateSubQuery);

        Query query = QueryFactory.newQuery(CalendarEntry.class, root);

        CalendarEntry pce = (CalendarEntry) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
        return pce;
    }

    @Override
    public CalendarEntry getCalendarEntryByCalendarIdAndDateRange(
            String hrCalendarId, DateTime beginDate, DateTime endDate) {
        Criteria root = new Criteria();
        root.addEqualTo("hrCalendarId", hrCalendarId);
        //root.addEqualTo("beginPeriodDateTime", beginDateSubQuery);
        root.addLessOrEqualThan("beginPeriodDateTime", endDate.toDate());
        root.addGreaterThan("endPeriodDateTime", beginDate.toDate());
//		root.addEqualTo("endPeriodDateTime", endDateSubQuery);

        Query query = QueryFactory.newQuery(CalendarEntry.class, root);

        CalendarEntry pce = (CalendarEntry) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
        return pce;
    }

    @Override
    public CalendarEntry getNextCalendarEntryByCalendarId(String hrCalendarId, CalendarEntry calendarEntry) {
        Criteria root = new Criteria();
        Criteria beginDate = new Criteria();
        Criteria endDate = new Criteria();

        beginDate.addEqualToField("hrCalendarId", Criteria.PARENT_QUERY_PREFIX + "hrCalendarId");
        beginDate.addGreaterThan("beginPeriodDateTime", calendarEntry.getBeginPeriodDateTime());
        ReportQueryByCriteria beginDateSubQuery = QueryFactory.newReportQuery(CalendarEntry.class, beginDate);
        beginDateSubQuery.setAttributes(new String[]{"min(beginPeriodDateTime)"});

        endDate.addEqualToField("hrCalendarId", Criteria.PARENT_QUERY_PREFIX + "hrCalendarId");
        endDate.addGreaterThan("endPeriodDateTime", calendarEntry.getEndPeriodDateTime());
        ReportQueryByCriteria endDateSubQuery = QueryFactory.newReportQuery(CalendarEntry.class, endDate);
        endDateSubQuery.setAttributes(new String[]{"min(endPeriodDateTime)"});

        root.addEqualTo("hrCalendarId", hrCalendarId);
        root.addEqualTo("beginPeriodDateTime", beginDateSubQuery);
        root.addEqualTo("endPeriodDateTime", endDateSubQuery);

        Query query = QueryFactory.newQuery(CalendarEntry.class, root);

        CalendarEntry pce = (CalendarEntry) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
        return pce;
    }

    @Override
    public CalendarEntry getPreviousCalendarEntryByCalendarId(String hrCalendarId, CalendarEntry calendarEntry) {
        Criteria root = new Criteria();
        Criteria beginDate = new Criteria();
        Criteria endDate = new Criteria();

        beginDate.addEqualToField("hrCalendarId", Criteria.PARENT_QUERY_PREFIX + "hrCalendarId");
        beginDate.addLessThan("beginPeriodDateTime", calendarEntry.getBeginPeriodDateTime());
        ReportQueryByCriteria beginDateSubQuery = QueryFactory.newReportQuery(CalendarEntry.class, beginDate);
        beginDateSubQuery.setAttributes(new String[]{"max(beginPeriodDateTime)"});

        endDate.addEqualToField("hrCalendarId", Criteria.PARENT_QUERY_PREFIX + "hrCalendarId");
        endDate.addLessThan("endPeriodDateTime", calendarEntry.getEndPeriodDateTime());
        ReportQueryByCriteria endDateSubQuery = QueryFactory.newReportQuery(CalendarEntry.class, endDate);
        endDateSubQuery.setAttributes(new String[]{"max(endPeriodDateTime)"});

        root.addEqualTo("hrCalendarId", hrCalendarId);
        root.addEqualTo("beginPeriodDateTime", beginDateSubQuery);
        root.addEqualTo("endPeriodDateTime", endDateSubQuery);

        Query query = QueryFactory.newQuery(CalendarEntry.class, root);

        CalendarEntry pce = (CalendarEntry) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
        return pce;
    }

    public List<CalendarEntry> getCurrentCalendarEntryNeedsScheduled(int thresholdDays, DateTime asOfDate) {
        DateTime windowStart = asOfDate.minusDays(thresholdDays);
        DateTime windowEnd = asOfDate.plusDays(thresholdDays);

        Criteria root = new Criteria();

        root.addGreaterOrEqualThan("beginPeriodDateTime", windowStart.toDate());
        root.addLessOrEqualThan("beginPeriodDateTime", windowEnd.toDate());

        Query query = QueryFactory.newQuery(CalendarEntry.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        List<CalendarEntry> pce = new ArrayList<CalendarEntry>(c.size());
        pce.addAll(c);

        return pce;
    }

    public List<CalendarEntry> getFutureCalendarEntries(String hrCalendarId, DateTime currentDate, int numberOfEntries) {
        Criteria root = new Criteria();
        root.addEqualTo("hrCalendarId", hrCalendarId);
        root.addGreaterOrEqualThan("beginPeriodDateTime", currentDate.toDate());
        QueryByCriteria q = QueryFactory.newReportQuery(CalendarEntry.class, root);
        q.addOrderByAscending("beginPeriodDateTime");
        q.setStartAtIndex(1);
        q.setEndAtIndex(numberOfEntries);
        List<CalendarEntry> calendarEntries = new ArrayList<CalendarEntry>(this.getPersistenceBrokerTemplate().getCollectionByQuery(q));
        return calendarEntries;
    }

    public CalendarEntry getCalendarEntryByBeginAndEndDate(DateTime beginPeriodDate, DateTime endPeriodDate) {
        Criteria root = new Criteria();
        root.addEqualTo("beginPeriodDateTime", beginPeriodDate.toDate());
        root.addEqualTo("endPeriodDateTime", endPeriodDate.toDate());
        Query query = QueryFactory.newQuery(CalendarEntry.class, root);

        return (CalendarEntry) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }
    
    @SuppressWarnings("unchecked")
	public List<CalendarEntry> getCalendarEntriesEndingBetweenBeginAndEndDate(String hrCalendarId, DateTime beginDate, DateTime endDate) {
        List<CalendarEntry> results = new ArrayList<CalendarEntry>();
    	
    	Criteria root = new Criteria();
        
        root.addEqualTo("hrCalendarId", hrCalendarId);
        root.addGreaterOrEqualThan("endPeriodDateTime", beginDate.toDate());
        root.addLessOrEqualThan("endPeriodDateTime", endDate.toDate());
        Query query = QueryFactory.newQuery(CalendarEntry.class, root);
        
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return results;
    }
    
    public List<CalendarEntry> getAllCalendarEntriesForCalendarId(String hrCalendarId) {
    	Criteria root = new Criteria();
        root.addEqualTo("hrCalendarId", hrCalendarId);
        Query query = QueryFactory.newQuery(CalendarEntry.class, root);
        List<CalendarEntry> ceList = new ArrayList<CalendarEntry> (this.getPersistenceBrokerTemplate().getCollectionByQuery(query));
        return ceList;
    }
    
    public List<CalendarEntry> getAllCalendarEntriesForCalendarIdUpToCutOffTime(String hrCalendarId, DateTime cutOffTime) {
    	Criteria root = new Criteria();
        root.addEqualTo("hrCalendarId", hrCalendarId);
        root.addLessOrEqualThan("endPeriodDateTime", cutOffTime.toDate());
        Query query = QueryFactory.newQuery(CalendarEntry.class, root);
        List<CalendarEntry> ceList = new ArrayList<CalendarEntry> (this.getPersistenceBrokerTemplate().getCollectionByQuery(query));
        return ceList;
    }
    
    public List<CalendarEntry> getAllCalendarEntriesForCalendarIdAndYear(String hrCalendarId, String year) {
        Criteria crit = new Criteria();
        List<CalendarEntry> ceList = new ArrayList<CalendarEntry>();
        crit.addEqualTo("hrCalendarId", hrCalendarId);
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy");
        LocalDate currentYear = formatter.parseLocalDate(year);
        LocalDate nextYear = currentYear.plusYears(1);
        crit.addGreaterOrEqualThan("beginPeriodDateTime", currentYear.toDate());
        crit.addLessThan("beginPeriodDateTime", nextYear.toDate());
	   	 
        QueryByCriteria query = new QueryByCriteria(CalendarEntry.class, crit);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        if (c != null) {
        	ceList.addAll(c);
        }
        return ceList;
    }

}