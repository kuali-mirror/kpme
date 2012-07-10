package org.kuali.hr.lm.accrual.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class AccrualCategoryDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements AccrualCategoryDao {

	private static final Logger LOG = Logger.getLogger(AccrualCategoryDaoSpringOjbImpl.class);

	@Override
    public AccrualCategory getAccrualCategory(String accrualCategory, Date asOfDate) {
    	AccrualCategory accrlCategory = null;
		Criteria root = new Criteria();
		
		Criteria effdt = new Criteria();
		effdt.addEqualToField("accrualCategory", Criteria.PARENT_QUERY_PREFIX + "accrualCategory");
		effdt.addLessOrEqualThan("effectiveDate", asOfDate);
		ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(AccrualCategory.class, effdt);
		effdtSubQuery.setAttributes(new String[] { "max(effdt)" });
		
		Criteria timestampSubCrit = new Criteria();
		timestampSubCrit.addEqualToField("accrualCategory", Criteria.PARENT_QUERY_PREFIX + "accrualCategory");
		timestampSubCrit.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
		ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(AccrualCategory.class, timestampSubCrit);
		timestampSubQuery.setAttributes(new String[]{ "max(timestamp)" });
		
		root.addEqualTo("accrualCategory", accrualCategory);
		root.addEqualTo("effectiveDate", effdtSubQuery);
		root.addEqualTo("timestamp", timestampSubQuery);
//		root.addEqualTo("active",true);
		
		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);
		
		Query query = QueryFactory.newQuery(AccrualCategory.class, root);
		Object obj = this.getPersistenceBrokerTemplate().getObjectByQuery(query);
		if (obj != null) {
			accrlCategory = (AccrualCategory) obj;
		}

		return accrlCategory;
    }
 /*   
    @Override
    public void saveOrUpdate(AccrualCategory accrualCategory) {
    	this.getPersistenceBrokerTemplate().store(accrualCategory);
    }
*/
	@Override
	public AccrualCategory getAccrualCategory(String lmAccrualCategoryId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("lmAccrualCategoryId", lmAccrualCategoryId);
		
		Query query = QueryFactory.newQuery(AccrualCategory.class, crit);
		return (AccrualCategory)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
		
	}

	@Override
	//TODO remove me
    public List<AccrualCategory> getActiveAccrualCategories(Date asOfDate) {
		List<AccrualCategory> accrualCategories = new ArrayList<AccrualCategory>();
		Criteria root = new Criteria();
		Criteria timestampSubCrit = new Criteria();
		
		timestampSubCrit.addEqualToField("accrualCategory", Criteria.PARENT_QUERY_PREFIX + "accrualCategory");
		ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(AccrualCategory.class, timestampSubCrit);
		timestampSubQuery.setAttributes(new String[]{ "max(timestamp)" });
		
		root.addLessOrEqualThan("effectiveDate", asOfDate);
		root.addEqualTo("timestamp", timestampSubQuery);
		root.addEqualTo("active",true);
		
		Query query = QueryFactory.newQuery(AccrualCategory.class, root);
		
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		
		if (c != null) {
			accrualCategories.addAll(c);
		}

		return accrualCategories;
    }
	
	public List<AccrualCategory> getActiveAccrualCategories(String leavePlan, Date asOfDate){
		List<AccrualCategory> accrualCategories = new ArrayList<AccrualCategory>();
		
		Criteria root = new Criteria();
		Criteria effdt = new Criteria();
		Criteria timestamp = new Criteria();

		// OJB's awesome sub query setup part 1
		effdt.addEqualToField("leavePlan", Criteria.PARENT_QUERY_PREFIX + "leavePlan");
		effdt.addLessOrEqualThan("effectiveDate", asOfDate);
		ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(AccrualCategory.class, effdt);
		effdtSubQuery.setAttributes(new String[] { "max(effdt)" });

		// OJB's awesome sub query setup part 2
		timestamp.addEqualToField("leavePlan", Criteria.PARENT_QUERY_PREFIX + "leavePlan");
		timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
		ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(AccrualCategory.class, timestamp);
		timestampSubQuery.setAttributes(new String[] { "max(timestamp)" });

		root.addEqualTo("leavePlan", leavePlan);
		root.addEqualTo("effectiveDate", effdtSubQuery);
		root.addEqualTo("timestamp", timestampSubQuery);
		
		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);
		
		
		Query query = QueryFactory.newQuery(AccrualCategory.class, root);
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

		if (c != null) {
			accrualCategories.addAll(c);
		}
		return accrualCategories;
	}
}
