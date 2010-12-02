package org.kuali.hr.time.clocklog.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.time.clocklog.ClockLog;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class ClockLogDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements ClockLogDao {

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

}
