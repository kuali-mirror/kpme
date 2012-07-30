package org.kuali.hr.lm.leaveplan.dao;


import java.sql.Date;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.PersistenceBrokerFactory;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.lm.leaveplan.LeavePlan;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class LeavePlanDaoSpringOjbImpl extends PlatformAwareDaoBaseOjb implements LeavePlanDao {

	private static final Logger LOG = Logger.getLogger(LeavePlanDaoSpringOjbImpl.class);

	@Override
	public LeavePlan getLeavePlan(String lmLeavePlanId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("lmLeavePlanId", lmLeavePlanId);
		Query query = QueryFactory.newQuery(LeavePlan.class, crit);
		return (LeavePlan) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}
	
	@Override
	public LeavePlan getLeavePlan(String leavePlan, Date asOfDate) {
		LeavePlan lp = null;

		Criteria root = new Criteria();
		Criteria effdt = new Criteria();
		Criteria timestamp = new Criteria();

		// OJB's awesome sub query setup part 1
		effdt.addEqualToField("leavePlan", Criteria.PARENT_QUERY_PREFIX + "leavePlan");
		effdt.addLessOrEqualThan("effectiveDate", asOfDate);
		ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(LeavePlan.class, effdt);
		effdtSubQuery.setAttributes(new String[] { "max(effdt)" });

		// OJB's awesome sub query setup part 2
		timestamp.addEqualToField("leavePlan", Criteria.PARENT_QUERY_PREFIX + "leavePlan");
		timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
		ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(LeavePlan.class, timestamp);
		timestampSubQuery.setAttributes(new String[] { "max(timestamp)" });

		root.addEqualTo("leavePlan", leavePlan);
		root.addEqualTo("effectiveDate", effdtSubQuery);
		root.addEqualTo("timestamp", timestampSubQuery);
		
		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);
		
		
		Query query = QueryFactory.newQuery(LeavePlan.class, root);
		Object obj = this.getPersistenceBrokerTemplate().getObjectByQuery(query);

		if (obj != null) {
			lp = (LeavePlan) obj;
		}

		return lp;
	}

	@Override
	public int getNumberLeavePlan(String leavePlan) {
		Criteria crit = new Criteria();
		crit.addEqualTo("leavePlan", leavePlan);
		Query query = QueryFactory.newQuery(LeavePlan.class, crit);
		return PersistenceBrokerFactory.defaultPersistenceBroker().getCount(query);
	}
	
	@Override
	public List<LeavePlan> getAllActiveLeavePlan(String leavePlan, Date asOfDate) {
		Criteria root = new Criteria();
        root.addEqualTo("leavePlan", leavePlan);
        root.addEqualTo("active", true);
        root.addLessOrEqualThan("effectiveDate", asOfDate);

        Query query = QueryFactory.newQuery(LeavePlan.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        
        List<LeavePlan> lps = new LinkedList<LeavePlan>();
        if (c != null) {
        	lps.addAll(c);
        }
        return lps;
		
	}
	
	@Override
	public List<LeavePlan> getAllInActiveLeavePlan(String leavePlan, Date asOfDate) {
		Criteria root = new Criteria();
        root.addEqualTo("leavePlan", leavePlan);
        root.addEqualTo("active", false);
        root.addLessOrEqualThan("effectiveDate", asOfDate);
        
        Query query = QueryFactory.newQuery(LeavePlan.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        
        List<LeavePlan> lps = new LinkedList<LeavePlan>();
        if (c != null) {
        	lps.addAll(c);
        }
        return lps;
	}
}
