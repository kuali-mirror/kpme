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
package org.kuali.kpme.core.calendar.entry.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryByCriteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.kuali.kpme.core.api.calendar.Calendar;
import org.kuali.kpme.core.api.calendar.CalendarContract;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntryContract;
import org.kuali.kpme.core.calendar.entry.CalendarEntryBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class CalendarEntryDaoOjbImpl extends PlatformAwareDaoBaseOjb implements CalendarEntryDao {


    public void saveOrUpdate(CalendarEntryBo calendarEntry) {
        this.getPersistenceBrokerTemplate().store(calendarEntry);
    }

    public CalendarEntryBo getCalendarEntry(String hrCalendarEntryId) {
        Criteria currentRecordCriteria = new Criteria();
        currentRecordCriteria.addEqualTo("hrCalendarEntryId", hrCalendarEntryId);

        return (CalendarEntryBo) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(CalendarEntryBo.class, currentRecordCriteria));
    }

    @Override
    public CalendarEntryBo getCalendarEntryByIdAndPeriodEndDate(String hrCalendarId, DateTime endPeriodDate) {
        Criteria root = new Criteria();
        root.addEqualTo("hrCalendarId", hrCalendarId);
        root.addEqualTo("endPeriodDateTime", endPeriodDate.toDate());

        Query query = QueryFactory.newQuery(CalendarEntryBo.class, root);
        CalendarEntryBo pce = (CalendarEntryBo) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
        return pce;
    }

    @Override
    public CalendarEntryBo getCurrentCalendarEntryByCalendarId(
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

        Query query = QueryFactory.newQuery(CalendarEntryBo.class, root);

        CalendarEntryBo pce = (CalendarEntryBo) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
        return pce;
    }

    @Override
    public CalendarEntryBo getCalendarEntryByCalendarIdAndDateRange(
            String hrCalendarId, DateTime beginDate, DateTime endDate) {
        Criteria root = new Criteria();
        root.addEqualTo("hrCalendarId", hrCalendarId);
        //root.addEqualTo("beginPeriodDateTime", beginDateSubQuery);
        root.addLessOrEqualThan("beginPeriodDateTime", endDate.toDate());
        root.addGreaterThan("endPeriodDateTime", beginDate.toDate());
//		root.addEqualTo("endPeriodDateTime", endDateSubQuery);

        Query query = QueryFactory.newQuery(CalendarEntryBo.class, root);

        CalendarEntryBo pce = (CalendarEntryBo) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
        return pce;
    }

    @Override
    public CalendarEntryBo getNextCalendarEntryByCalendarId(String hrCalendarId, CalendarEntryContract calendarEntry) {
        Criteria root = new Criteria();
        Criteria beginDate = new Criteria();
        Criteria endDate = new Criteria();

        beginDate.addEqualToField("hrCalendarId", Criteria.PARENT_QUERY_PREFIX + "hrCalendarId");
        beginDate.addGreaterThan("beginPeriodDateTime", calendarEntry.getBeginPeriodFullDateTime().toDate());
        ReportQueryByCriteria beginDateSubQuery = QueryFactory.newReportQuery(CalendarEntryBo.class, beginDate);
        beginDateSubQuery.setAttributes(new String[]{"min(beginPeriodDateTime)"});

        endDate.addEqualToField("hrCalendarId", Criteria.PARENT_QUERY_PREFIX + "hrCalendarId");
        endDate.addGreaterThan("endPeriodDateTime", calendarEntry.getEndPeriodFullDateTime().toDate());
        ReportQueryByCriteria endDateSubQuery = QueryFactory.newReportQuery(CalendarEntryBo.class, endDate);
        endDateSubQuery.setAttributes(new String[]{"min(endPeriodDateTime)"});

        root.addEqualTo("hrCalendarId", hrCalendarId);
        root.addEqualTo("beginPeriodDateTime", beginDateSubQuery);
        root.addEqualTo("endPeriodDateTime", endDateSubQuery);

        Query query = QueryFactory.newQuery(CalendarEntryBo.class, root);

        CalendarEntryBo pce = (CalendarEntryBo) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
        return pce;
    }

    @Override
    public CalendarEntryBo getPreviousCalendarEntryByCalendarId(String hrCalendarId, CalendarEntryContract calendarEntry) {
        Criteria root = new Criteria();
        Criteria beginDate = new Criteria();
        Criteria endDate = new Criteria();

        beginDate.addEqualToField("hrCalendarId", Criteria.PARENT_QUERY_PREFIX + "hrCalendarId");
        beginDate.addLessThan("beginPeriodDateTime", calendarEntry.getBeginPeriodFullDateTime().toDate());
        ReportQueryByCriteria beginDateSubQuery = QueryFactory.newReportQuery(CalendarEntryBo.class, beginDate);
        beginDateSubQuery.setAttributes(new String[]{"max(beginPeriodDateTime)"});

        endDate.addEqualToField("hrCalendarId", Criteria.PARENT_QUERY_PREFIX + "hrCalendarId");
        endDate.addLessThan("endPeriodDateTime", calendarEntry.getEndPeriodFullDateTime().toDate());
        ReportQueryByCriteria endDateSubQuery = QueryFactory.newReportQuery(CalendarEntryBo.class, endDate);
        endDateSubQuery.setAttributes(new String[]{"max(endPeriodDateTime)"});

        root.addEqualTo("hrCalendarId", hrCalendarId);
        root.addEqualTo("beginPeriodDateTime", beginDateSubQuery);
        root.addEqualTo("endPeriodDateTime", endDateSubQuery);

        Query query = QueryFactory.newQuery(CalendarEntryBo.class, root);

        CalendarEntryBo pce = (CalendarEntryBo) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
        return pce;
    }

    public List<CalendarEntryBo> getCurrentCalendarEntryNeedsScheduled(int thresholdDays, DateTime asOfDate) {
        DateTime windowStart = asOfDate.minusDays(thresholdDays);
        DateTime windowEnd = asOfDate.plusDays(thresholdDays);

        Criteria root = new Criteria();

        root.addGreaterOrEqualThan("beginPeriodDateTime", windowStart.toDate());
        root.addLessOrEqualThan("beginPeriodDateTime", windowEnd.toDate());

        Query query = QueryFactory.newQuery(CalendarEntryBo.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        List<CalendarEntryBo> pce = new ArrayList<CalendarEntryBo>(c.size());
        pce.addAll(c);

        return pce;
    }

    public List<CalendarEntryBo> getFutureCalendarEntries(String hrCalendarId, DateTime currentDate, int numberOfEntries) {
        Criteria root = new Criteria();
        root.addEqualTo("hrCalendarId", hrCalendarId);
        root.addGreaterOrEqualThan("beginPeriodDateTime", currentDate.toDate());
        QueryByCriteria q = QueryFactory.newReportQuery(CalendarEntryBo.class, root);
        q.addOrderByAscending("beginPeriodDateTime");
        q.setStartAtIndex(1);
        q.setEndAtIndex(numberOfEntries);
        List<CalendarEntryBo> calendarEntries = new ArrayList<CalendarEntryBo>(this.getPersistenceBrokerTemplate().getCollectionByQuery(q));
        return calendarEntries;
    }
    
    @SuppressWarnings("unchecked")
	public List<CalendarEntryBo> getCalendarEntriesEndingBetweenBeginAndEndDate(String hrCalendarId, DateTime beginDate, DateTime endDate) {
        List<CalendarEntryBo> results = new ArrayList<CalendarEntryBo>();
    	
    	Criteria root = new Criteria();
        
        root.addEqualTo("hrCalendarId", hrCalendarId);
        root.addGreaterOrEqualThan("endPeriodDateTime", beginDate.toDate());
        root.addLessOrEqualThan("endPeriodDateTime", endDate.toDate());
        Query query = QueryFactory.newQuery(CalendarEntryBo.class, root);

        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return results;
    }
    
    public List<CalendarEntryBo> getAllCalendarEntriesForCalendarId(String hrCalendarId) {
    	Criteria root = new Criteria();
        root.addEqualTo("hrCalendarId", hrCalendarId);
        Query query = QueryFactory.newQuery(CalendarEntryBo.class, root);
        List<CalendarEntryBo> ceList = new ArrayList<CalendarEntryBo> (this.getPersistenceBrokerTemplate().getCollectionByQuery(query));
        return ceList;
    }
    
    public List<CalendarEntryBo> getAllCalendarEntriesForCalendarIdUpToCutOffTime(String hrCalendarId, DateTime cutOffTime) {
    	Criteria root = new Criteria();
        root.addEqualTo("hrCalendarId", hrCalendarId);
        root.addLessOrEqualThan("endPeriodDateTime", cutOffTime.toDate());
        Query query = QueryFactory.newQuery(CalendarEntryBo.class, root);
        List<CalendarEntryBo> ceList = new ArrayList<CalendarEntryBo> (this.getPersistenceBrokerTemplate().getCollectionByQuery(query));
        return ceList;
    }
    
    public List<CalendarEntryBo> getAllCalendarEntriesForCalendarIdAndYear(String hrCalendarId, String year) {
        Criteria crit = new Criteria();
        List<CalendarEntryBo> ceList = new ArrayList<CalendarEntryBo>();
        crit.addEqualTo("hrCalendarId", hrCalendarId);
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy");
        LocalDate currentYear = formatter.parseLocalDate(year);
        LocalDate nextYear = currentYear.plusYears(1);
        crit.addGreaterOrEqualThan("beginPeriodDateTime", currentYear.toDate());
        crit.addLessThan("beginPeriodDateTime", nextYear.toDate());
	   	 
        QueryByCriteria query = new QueryByCriteria(CalendarEntryBo.class, crit);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        if (c != null) {
        	ceList.addAll(c);
        }
        return ceList;
    }
    
    public List<CalendarEntryBo> getAllCalendarEntriesForCalendarIdWithinLeavePlanYear(String hrCalendarId, String leavePlan, LocalDate dateWithinYear) {
    	Criteria crit = new Criteria();
    	List<CalendarEntryBo> ceList = new ArrayList<CalendarEntryBo>();
    	crit.addEqualTo("hrCalendarId", hrCalendarId);
    	DateTime leavePlanStart = HrServiceLocator.getLeavePlanService().getRolloverDayOfLeavePlan(leavePlan, dateWithinYear);
    	DateTime leavePlanEnd = HrServiceLocator.getLeavePlanService().getFirstDayOfLeavePlan(leavePlan, dateWithinYear);
    	crit.addGreaterOrEqualThan("endPeriodDateTime", leavePlanStart);
    	crit.addLessThan("beginPeriodDateTime", leavePlanEnd);
    	
    	QueryByCriteria query = new QueryByCriteria(CalendarEntryBo.class, crit);
    	Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
    	if(c != null) {
    		ceList.addAll(c);
    	}
    	return ceList;
    }

    public List<CalendarEntryBo> getSearchResults(String calendarName, String calendarTypes, LocalDate fromBeginDate, LocalDate toBeginDate, LocalDate fromEndDate, LocalDate toEndDate) {
        Criteria crit = new Criteria();
        List<CalendarEntryBo> results = new ArrayList<CalendarEntryBo>();
        // for either pay or leave (not both!) get all the calendars that match
        if (!StringUtils.equals(calendarTypes,"")) {
            List<Calendar> calendars = HrServiceLocator.getCalendarService().getCalendars(calendarName, calendarTypes, null, null);
            List<String> hrCalendarIdList = new ArrayList<String>();
            for (Calendar cal : calendars) {
                hrCalendarIdList.add(cal.getHrCalendarId());
            }
            if (hrCalendarIdList.isEmpty()) {      //no calendar with that combination of name and type
                return results;
            } else {
                crit.addIn("hrCalendarId", hrCalendarIdList);
            }
        }
        if (!StringUtils.equals(calendarName,"")){
            crit.addLike("calendarName", calendarName);
        }
        if (fromBeginDate != null) {
            crit.addGreaterOrEqualThan("beginPeriodDateTime", fromBeginDate.toDate());
        }
        if (toBeginDate != null) {
            crit.addLessOrEqualThan("beginPeriodDateTime", toBeginDate.toDate());
        }
        if (fromEndDate != null) {
            crit.addGreaterOrEqualThan("endPeriodDateTime", fromEndDate.toDate());
        }
        if (toEndDate != null){
            crit.addLessOrEqualThan("endPeriodDateTime", toEndDate.toDate());
        }

        Query query = QueryFactory.newQuery(CalendarEntryBo.class, crit);

        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return results;

    }

}