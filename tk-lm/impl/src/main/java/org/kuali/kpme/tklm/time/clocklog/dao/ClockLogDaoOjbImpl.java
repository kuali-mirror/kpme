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
package org.kuali.kpme.tklm.time.clocklog.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.kpme.core.calendar.entry.CalendarEntryBo;
import org.kuali.kpme.tklm.time.clocklog.ClockLogBo;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class ClockLogDaoOjbImpl extends PlatformAwareDaoBaseOjb implements ClockLogDao {

    @SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(ClockLogDaoOjbImpl.class);
    
    public void saveOrUpdate(ClockLogBo clockLog) {
	this.getPersistenceBrokerTemplate().store(clockLog);
    }
    
    public void saveOrUpdate(List<ClockLogBo> clockLogList) {
    	if (clockLogList != null) {
	    	for (ClockLogBo clockLog : clockLogList) {
	    		this.getPersistenceBrokerTemplate().store(clockLog);
	    	}
		}
    }
    
    public ClockLogBo getClockLog(String tkClockLogId){
    	Criteria crit = new Criteria();
    	crit.addEqualTo("tkClockLogId", tkClockLogId);
    	Query query = QueryFactory.newQuery(ClockLogBo.class, crit);
    	return (ClockLogBo)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }
    
    public ClockLogBo getLastClockLog(String principalId){
        Criteria currentRecordCriteria = new Criteria();
        currentRecordCriteria.addEqualTo("principalId", principalId);

        Criteria clockTimeJoinCriteria = new Criteria();
        clockTimeJoinCriteria.addEqualToField("principalId",Criteria.PARENT_QUERY_PREFIX +"principalId");

        ReportQueryByCriteria clockTimeSubQuery = QueryFactory.newReportQuery(ClockLogBo.class, clockTimeJoinCriteria);
        clockTimeSubQuery.setAttributes(new String[]{"max(clockTimestamp)"});

        currentRecordCriteria.addEqualTo("clockTimestamp", clockTimeSubQuery);

        Criteria timestampJoinCriteria = new Criteria();
        timestampJoinCriteria.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
        timestampJoinCriteria.addEqualToField("clockTimestamp", Criteria.PARENT_QUERY_PREFIX + "clockTimestamp");
	
		ReportQueryByCriteria timeStampSubQuery = QueryFactory.newReportQuery(ClockLogBo.class, timestampJoinCriteria);
		timeStampSubQuery.setAttributes(new String[]{"max(timestamp)"});
	
		currentRecordCriteria.addEqualTo("timestamp", timeStampSubQuery);
	
		return (ClockLogBo)this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(ClockLogBo.class,currentRecordCriteria));
    }
    
    @Override
	public ClockLogBo getLastClockLog(String principalId, String clockAction){
    	Criteria currentRecordCriteria = new Criteria();
    	currentRecordCriteria.addEqualTo("principalId", principalId);
    	currentRecordCriteria.addEqualTo("clockAction", clockAction);
    	
    	Criteria clockTimeJoinCriteria = new Criteria();
    	clockTimeJoinCriteria.addEqualToField("principalId",Criteria.PARENT_QUERY_PREFIX +"principalId");
    	clockTimeJoinCriteria.addEqualToField("clockAction",Criteria.PARENT_QUERY_PREFIX +"clockAction");
    	
    	ReportQueryByCriteria clockTimeSubQuery = QueryFactory.newReportQuery(ClockLogBo.class, clockTimeJoinCriteria);
    	clockTimeSubQuery.setAttributes(new String[]{"max(clockTimestamp)"});
    	
    	currentRecordCriteria.addEqualTo("clockTimestamp", clockTimeSubQuery);
    	
    	Criteria timestampJoinCriteria = new Criteria();
    	timestampJoinCriteria.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
    	timestampJoinCriteria.addEqualToField("clockAction", Criteria.PARENT_QUERY_PREFIX + "clockAction");
    	timestampJoinCriteria.addEqualToField("clockTimestamp", Criteria.PARENT_QUERY_PREFIX + "clockTimestamp");
    	
    	ReportQueryByCriteria timeStampSubQuery = QueryFactory.newReportQuery(ClockLogBo.class, timestampJoinCriteria);
    	timeStampSubQuery.setAttributes(new String[]{"max(timestamp)"});
    	
    	currentRecordCriteria.addEqualTo("timestamp", timeStampSubQuery);
    	
    	return (ClockLogBo)this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(ClockLogBo.class,currentRecordCriteria));
    }

    @Override
    public ClockLogBo getLastClockLog(String groupKeyCode, String principalId, String jobNumber, String workArea, String task, String timesheetId) {
        Criteria criteria = new Criteria();
        criteria.addEqualTo("groupKeyCode", groupKeyCode);
        criteria.addEqualTo("principalId", principalId);
        criteria.addEqualTo("jobNumber", jobNumber);
        criteria.addEqualTo("workArea", workArea);
        criteria.addEqualTo("task", task);
        criteria.addEqualTo("documentId", timesheetId);
        
        Criteria clockTimeJoinCriteria = new Criteria();
        clockTimeJoinCriteria.addEqualToField("groupKeyCode", Criteria.PARENT_QUERY_PREFIX + "groupKeyCode");
        clockTimeJoinCriteria.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
        clockTimeJoinCriteria.addEqualToField("jobNumber", Criteria.PARENT_QUERY_PREFIX + "jobNumber");
        clockTimeJoinCriteria.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
        clockTimeJoinCriteria.addEqualToField("task", Criteria.PARENT_QUERY_PREFIX + "task");
        clockTimeJoinCriteria.addEqualToField("documentId", Criteria.PARENT_QUERY_PREFIX + "documentId");
        ReportQueryByCriteria clockTimeSubQuery = QueryFactory.newReportQuery(ClockLogBo.class, clockTimeJoinCriteria);
        clockTimeSubQuery.setAttributes(new String[]{"max(clockTimestamp)"});
        criteria.addEqualTo("clockTimestamp", clockTimeSubQuery);

        Criteria timestampJoinCriteria = new Criteria();
        timestampJoinCriteria.addEqualToField("groupKeyCode", Criteria.PARENT_QUERY_PREFIX + "groupKeyCode");
        timestampJoinCriteria.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
        timestampJoinCriteria.addEqualToField("jobNumber", Criteria.PARENT_QUERY_PREFIX + "jobNumber");
        timestampJoinCriteria.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
        timestampJoinCriteria.addEqualToField("task", Criteria.PARENT_QUERY_PREFIX + "task");
        timestampJoinCriteria.addEqualToField("documentId", Criteria.PARENT_QUERY_PREFIX + "documentId");
        ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(ClockLogBo.class, timestampJoinCriteria);
        timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});
        criteria.addEqualTo("timestamp", timestampSubQuery);

        return (ClockLogBo) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(ClockLogBo.class, criteria));
    }

    @Override
	public ClockLogBo getLastClockLog(String principalId, String jobNumber, String workArea, String task, CalendarEntryBo calendarEntry) {
    	Criteria criteria = new Criteria();
    	criteria.addEqualTo("principalId", principalId);
    	criteria.addEqualTo("jobNumber", jobNumber);
    	criteria.addEqualTo("workArea", workArea);
    	criteria.addEqualTo("task", task);

    	Criteria clockTimeJoinCriteria = new Criteria();
    	clockTimeJoinCriteria.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
    	clockTimeJoinCriteria.addEqualToField("jobNumber", Criteria.PARENT_QUERY_PREFIX + "jobNumber");
    	clockTimeJoinCriteria.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
    	clockTimeJoinCriteria.addEqualToField("task", Criteria.PARENT_QUERY_PREFIX + "task");
    	clockTimeJoinCriteria.addBetween("clockTimestamp", calendarEntry.getBeginPeriodDate(), calendarEntry.getEndPeriodDate());
    	ReportQueryByCriteria clockTimeSubQuery = QueryFactory.newReportQuery(ClockLogBo.class, clockTimeJoinCriteria);
    	clockTimeSubQuery.setAttributes(new String[]{"max(clockTimestamp)"});
    	criteria.addEqualTo("clockTimestamp", clockTimeSubQuery);
    	
    	Criteria timestampJoinCriteria = new Criteria();
    	timestampJoinCriteria.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
    	timestampJoinCriteria.addEqualToField("jobNumber", Criteria.PARENT_QUERY_PREFIX + "jobNumber");
    	timestampJoinCriteria.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
    	timestampJoinCriteria.addEqualToField("task", Criteria.PARENT_QUERY_PREFIX + "task");
    	timestampJoinCriteria.addBetween("clockTimestamp", calendarEntry.getBeginPeriodDate(), calendarEntry.getEndPeriodDate());
    	ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(ClockLogBo.class, timestampJoinCriteria);
    	timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});
    	criteria.addEqualTo("timestamp", timestampSubQuery);
    	
    	return (ClockLogBo) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(ClockLogBo.class, criteria));
    }
    
    @Override
	public void deleteClockLogsForDocumentId(String documentId) {
    	Criteria crit = new Criteria();
    	crit.addEqualTo("documentId", documentId);
    	this.getPersistenceBrokerTemplate().deleteByQuery(QueryFactory.newQuery(ClockLogBo.class, crit));
    }

}