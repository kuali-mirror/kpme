package org.kuali.hr.time.accrual.dao;

import java.sql.Date;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.time.accrual.AccrualCategory;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class AccrualCategoryDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements AccrualCategoryDao {

    private static final Logger LOG = Logger.getLogger(AccrualCategoryDaoSpringOjbImpl.class);
    
    @Override
    public AccrualCategory getAccrualCategory(String accrualCategory, Date asOfDate) {
    	AccrualCategory accrlCategory = null;
		Criteria root = new Criteria();
		root.addEqualTo("accrualCategory", accrualCategory);
		root.addLessOrEqualThan("effectiveDate", asOfDate);
		root.addEqualTo("active",true);
		
		Query query = QueryFactory.newQuery(AccrualCategory.class, root);
		Object obj = this.getPersistenceBrokerTemplate().getObjectByQuery(query);
		if (obj != null) {
			accrlCategory = (AccrualCategory) obj;
		}

		return accrlCategory;
    }
    
    @Override
    public void saveOrUpdate(AccrualCategory accrualCategory) {
    	this.getPersistenceBrokerTemplate().store(accrualCategory);
    }

}
