package org.kuali.hr.time.workarea.dao;

import java.sql.Date;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.time.workarea.WorkArea;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class WorkAreaDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements WorkAreaDao {

    private static final Logger LOG = Logger.getLogger(WorkAreaDaoSpringOjbImpl.class);
    
    @Override
    public WorkArea getWorkArea(Long workArea, Date asOfDate) {
		Criteria root = new Criteria();
		Criteria effdt = new Criteria();
		Criteria timestamp = new Criteria();

		// OJB's awesome sub query setup part 1
		effdt.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
		effdt.addLessOrEqualThan("effectiveDate", asOfDate);
		effdt.addEqualTo("active", true);
		ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(WorkArea.class, effdt);
		effdtSubQuery.setAttributes(new String[]{"max(effdt)"});
		
		// OJB's awesome sub query setup part 2
		timestamp.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
		timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
		timestamp.addEqualTo("active", true);
		ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(WorkArea.class, timestamp);
		timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});

		root.addEqualTo("workArea", workArea);
		root.addEqualTo("effectiveDate", effdtSubQuery);
		root.addEqualTo("timestamp", timestampSubQuery);
		root.addEqualTo("active", true);
		
		Query query = QueryFactory.newQuery(WorkArea.class, root);
		return (WorkArea) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }
    
    @Override
    public void saveOrUpdate(WorkArea workArea) {
    	this.getPersistenceBrokerTemplate().store(workArea);
    }

}
