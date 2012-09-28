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
package org.kuali.hr.time.clocklog.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class ClockLogDaoSpringOjbImpl extends PlatformAwareDaoBaseOjb implements ClockLogDao {

    private static final Logger LOG = Logger.getLogger(ClockLogDaoSpringOjbImpl.class);
    
    public void saveOrUpdate(ClockLog clockLog) {
	this.getPersistenceBrokerTemplate().store(clockLog);
    }
    
    public void saveOrUpdate(List<ClockLog> clockLogList) {
    	if (clockLogList != null) {
	    	for (ClockLog clockLog : clockLogList) {
	    		this.getPersistenceBrokerTemplate().store(clockLog);
	    	}
		}
    }
    
    public ClockLog getClockLog(String tkClockLogId){
    	Criteria crit = new Criteria();
    	crit.addEqualTo("tkClockLogId", tkClockLogId);
    	Query query = QueryFactory.newQuery(ClockLog.class, crit);
    	return (ClockLog)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }
    
    public ClockLog getLastClockLog(String principalId){
	Criteria currentRecordCriteria = new Criteria();
	currentRecordCriteria.addEqualTo("principalId", principalId);
	
	Criteria clockTimeJoinCriteria = new Criteria();
	clockTimeJoinCriteria.addEqualToField("principalId",Criteria.PARENT_QUERY_PREFIX +"principalId");
	
	ReportQueryByCriteria clockTimeSubQuery = QueryFactory.newReportQuery(ClockLog.class, clockTimeJoinCriteria);
	clockTimeSubQuery.setAttributes(new String[]{"max(clockTimestamp)"});
	
	currentRecordCriteria.addEqualTo("clockTimestamp", clockTimeSubQuery);
	
	Criteria timestampJoinCriteria = new Criteria();
	timestampJoinCriteria.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
	timestampJoinCriteria.addEqualToField("clockTimestamp", Criteria.PARENT_QUERY_PREFIX + "clockTimestamp");
	
		ReportQueryByCriteria timeStampSubQuery = QueryFactory.newReportQuery(ClockLog.class, timestampJoinCriteria);
		timeStampSubQuery.setAttributes(new String[]{"max(timestamp)"});
	
		currentRecordCriteria.addEqualTo("timestamp", timeStampSubQuery);
	
		return (ClockLog)this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(ClockLog.class,currentRecordCriteria));
    }
    
    @Override
	public ClockLog getLastClockLog(String principalId, String clockAction){
    	Criteria currentRecordCriteria = new Criteria();
    	currentRecordCriteria.addEqualTo("principalId", principalId);
    	currentRecordCriteria.addEqualTo("clockAction", clockAction);
    	
    	Criteria clockTimeJoinCriteria = new Criteria();
    	clockTimeJoinCriteria.addEqualToField("principalId",Criteria.PARENT_QUERY_PREFIX +"principalId");
    	clockTimeJoinCriteria.addEqualToField("clockAction",Criteria.PARENT_QUERY_PREFIX +"clockAction");
    	
    	ReportQueryByCriteria clockTimeSubQuery = QueryFactory.newReportQuery(ClockLog.class, clockTimeJoinCriteria);
    	clockTimeSubQuery.setAttributes(new String[]{"max(clockTimestamp)"});
    	
    	currentRecordCriteria.addEqualTo("clockTimestamp", clockTimeSubQuery);
    	
    	Criteria timestampJoinCriteria = new Criteria();
    	timestampJoinCriteria.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
    	timestampJoinCriteria.addEqualToField("clockAction", Criteria.PARENT_QUERY_PREFIX + "clockAction");
    	timestampJoinCriteria.addEqualToField("clockTimestamp", Criteria.PARENT_QUERY_PREFIX + "clockTimestamp");
    	
    	ReportQueryByCriteria timeStampSubQuery = QueryFactory.newReportQuery(ClockLog.class, timestampJoinCriteria);
    	timeStampSubQuery.setAttributes(new String[]{"max(timestamp)"});
    	
    	currentRecordCriteria.addEqualTo("timestamp", timeStampSubQuery);
    	
    	return (ClockLog)this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(ClockLog.class,currentRecordCriteria));
    }
    
    @SuppressWarnings("unchecked")
	public List<ClockLog> getOpenClockLogs(CalendarEntries payCalendarEntry){
    	Criteria criteria = new Criteria();
    	criteria.addIn("clockAction", TkConstants.ON_THE_CLOCK_CODES);

    	Criteria clockTimeJoinCriteria = new Criteria();
        clockTimeJoinCriteria.addBetween("clockTimestamp", payCalendarEntry.getBeginPeriodDate(), payCalendarEntry.getEndPeriodDate());
    	clockTimeJoinCriteria.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
    	ReportQueryByCriteria clockTimeSubQuery = QueryFactory.newReportQuery(ClockLog.class, clockTimeJoinCriteria);
    	clockTimeSubQuery.setAttributes(new String[] {new StringBuffer("max(").append("clockTimestamp").append(")").toString() });
    	
    	criteria.addEqualTo("clockTimestamp", clockTimeSubQuery);
    	
    	Criteria clockTimestampJoinCriteria = new Criteria();
        clockTimestampJoinCriteria.addBetween("clockTimestamp", payCalendarEntry.getBeginPeriodDate(), payCalendarEntry.getEndPeriodDate());
    	clockTimestampJoinCriteria.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
    	ReportQueryByCriteria clockTimestampSubQuery = QueryFactory.newReportQuery(ClockLog.class, clockTimestampJoinCriteria);
    	clockTimestampSubQuery.setAttributes(new String[] { new StringBuffer("max(").append("timestamp").append(")").toString()});
    	
    	criteria.addEqualTo("timestamp", clockTimestampSubQuery);
    	return (List<ClockLog>)this.getPersistenceBrokerTemplate().getCollectionByQuery((QueryFactory.newQuery(ClockLog.class, criteria)));
    }

}
