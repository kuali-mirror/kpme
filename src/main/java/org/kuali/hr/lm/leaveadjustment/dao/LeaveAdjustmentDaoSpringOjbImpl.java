package org.kuali.hr.lm.leaveadjustment.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.lm.leaveadjustment.LeaveAdjustment;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class LeaveAdjustmentDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements LeaveAdjustmentDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<LeaveAdjustment> getLeaveAdjustments(String principalId, Date asOfDate) {
        List<LeaveAdjustment> leaveAdjustments = new ArrayList<LeaveAdjustment>();
        Criteria root = new Criteria();
        Criteria effdt = new Criteria();
        Criteria timestamp = new Criteria();

        effdt.addLessOrEqualThan("effectiveDate", asOfDate);
        effdt.addEqualTo("principalId", principalId);
        ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(LeaveAdjustment.class, effdt);
        effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

        timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
        timestamp.addEqualTo("principalId", principalId);
        ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(LeaveAdjustment.class, timestamp);
        timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});

        root.addEqualTo("principalId", principalId);
        root.addEqualTo("effectiveDate", effdtSubQuery);
        root.addEqualTo("timestamp", timestampSubQuery);

        Criteria activeFilter = new Criteria(); // Inner Join For Activity
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(LeaveAdjustment.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null) {
        	leaveAdjustments.addAll(c);
        }
        return leaveAdjustments;
    }

	@Override
	public LeaveAdjustment getLeaveAdjustment(String lmLeaveAdjustmentId) {
		Criteria crit = new Criteria();
        crit.addEqualTo("lmLeaveAdjustmentId", lmLeaveAdjustmentId);
        Query query = QueryFactory.newQuery(LeaveAdjustment.class, crit);
        return (LeaveAdjustment) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

}
