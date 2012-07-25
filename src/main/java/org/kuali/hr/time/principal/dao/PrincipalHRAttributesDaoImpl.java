package org.kuali.hr.time.principal.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class PrincipalHRAttributesDaoImpl extends PlatformAwareDaoBaseOjb implements PrincipalHRAttributesDao {

	@Override
	public PrincipalHRAttributes getPrincipalCalendar(String principalId,
			Date asOfDate) {
		PrincipalHRAttributes pc = null;

		Criteria root = new Criteria();
		Criteria effdt = new Criteria();
		Criteria timestamp = new Criteria();

		effdt.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
		effdt.addLessOrEqualThan("effectiveDate", asOfDate);
		ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(PrincipalHRAttributes.class, effdt);
		effdtSubQuery.setAttributes(new String[] { "max(effdt)" });

		timestamp.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
		timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
		ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(PrincipalHRAttributes.class, timestamp);
		timestampSubQuery.setAttributes(new String[] { "max(timestamp)" });

		root.addEqualTo("principalId", principalId);
		root.addEqualTo("effectiveDate", effdtSubQuery);
		root.addEqualTo("timestamp", timestampSubQuery);

		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);
		
		Query query = QueryFactory.newQuery(PrincipalHRAttributes.class, root);
		Object obj = this.getPersistenceBrokerTemplate().getObjectByQuery(query);

		if (obj != null) {
			pc = (PrincipalHRAttributes) obj;
		}

		return pc;
	}

	@Override
	public void saveOrUpdate(PrincipalHRAttributes principalCalendar) {
		this.getPersistenceBrokerTemplate().store(principalCalendar);
		
	}

	@Override
	public void saveOrUpdate(List<PrincipalHRAttributes> lstPrincipalCalendar) {
		if(lstPrincipalCalendar != null){
			for(PrincipalHRAttributes principalCal : lstPrincipalCalendar){
				this.getPersistenceBrokerTemplate().store(principalCal);
			}
		}
		
	}
	
    // KPME-1250 Kagata
    @SuppressWarnings({"rawtypes", "unchecked"})
    public List<PrincipalHRAttributes> getActiveEmployeesForLeavePlan(String leavePlan, Date asOfDate) {

        List<PrincipalHRAttributes> principals = new ArrayList<PrincipalHRAttributes>();
        Criteria root = new Criteria();
        Criteria effdt = new Criteria();
        Criteria timestamp = new Criteria();

        // subquery for effective date
        effdt.addLessOrEqualThan("effectiveDate", asOfDate);
        effdt.addEqualTo("leavePlan", leavePlan);
        ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(PrincipalHRAttributes.class, effdt);
        effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

        // subquery for timestamp
        timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
        timestamp.addEqualTo("leavePlan", leavePlan);
        ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(PrincipalHRAttributes.class, timestamp);
        timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});

        root.addEqualTo("leavePlan", leavePlan);
        root.addEqualTo("effectiveDate", effdtSubQuery);
        root.addEqualTo("timestamp", timestampSubQuery);
        root.addEqualTo("active", true);

        Criteria activeFilter = new Criteria(); // Inner Join For Activity
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(PrincipalHRAttributes.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null) {
        	principals.addAll(c);
        }

        return principals;
    }

//    @Override
//	public PrincipalHRAttributes getPrincipalHRAttributes(String principalId) {
//		Criteria crit = new Criteria();
//		crit.addEqualTo("principalId", principalId);
//		Query query = QueryFactory.newQuery(PrincipalHRAttributes.class, crit);
//		return (PrincipalHRAttributes)this.getPersistenceBrokerTemplate().getObjectByQuery(query);		
//	}
    
    @Override
    public PrincipalHRAttributes getInactivePrincipalHRAttributes(String principalId, Date asOfDate) {
    	PrincipalHRAttributes pc = null;

		Criteria root = new Criteria();
		Criteria effdt = new Criteria();
		Criteria timestamp = new Criteria();

		effdt.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
		effdt.addGreaterOrEqualThan("effectiveDate", asOfDate);
		ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(PrincipalHRAttributes.class, effdt);
		effdtSubQuery.setAttributes(new String[] { "max(effdt)" });

		timestamp.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
		timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
		ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(PrincipalHRAttributes.class, timestamp);
		timestampSubQuery.setAttributes(new String[] { "max(timestamp)" });

		root.addEqualTo("principalId", principalId);
		root.addEqualTo("effectiveDate", effdtSubQuery);
		root.addEqualTo("timestamp", timestampSubQuery);

		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", false);
		root.addAndCriteria(activeFilter);
		
		Query query = QueryFactory.newQuery(PrincipalHRAttributes.class, root);
		Object obj = this.getPersistenceBrokerTemplate().getObjectByQuery(query);

		if (obj != null) {
			pc = (PrincipalHRAttributes) obj;
		}

		return pc;
    }
    
    @Override
    public PrincipalHRAttributes getPrincipalHRAttributes(String hrPrincipalAttributeId) {
    	Criteria crit = new Criteria();
		crit.addEqualTo("hrPrincipalAttributeId", hrPrincipalAttributeId);
		
		Query query = QueryFactory.newQuery(PrincipalHRAttributes.class, crit);
		return (PrincipalHRAttributes)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }
    
    public List<PrincipalHRAttributes> getAllPrincipalHrAttributesForPrincipalId(String principalId, Date asOfDate) {
    	// get future principalHRAttributes
    	List<PrincipalHRAttributes> phaList = new ArrayList<PrincipalHRAttributes>();
    	Criteria crit = new Criteria();
		crit.addEqualTo("principalId", principalId);
		crit.addGreaterOrEqualThan("effectiveDate", asOfDate);
		
    	Query query = QueryFactory.newQuery(PrincipalHRAttributes.class, crit);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null) {
        	phaList.addAll(c);
        }
        PrincipalHRAttributes pha = this.getPrincipalCalendar(principalId, asOfDate);  // get the current PrincipalHRAttributes
        if(pha != null) {
        	phaList.add(pha);
        }
        return phaList;
    }
}
