package org.kuali.hr.time.workarea.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

		effdt.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
		effdt.addLessOrEqualThan("effectiveDate", asOfDate);
		ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(WorkArea.class, effdt);
		effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

		timestamp.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
		timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
		ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(WorkArea.class, timestamp);
		timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});

		root.addEqualTo("workArea", workArea);
		root.addEqualTo("effectiveDate", effdtSubQuery);
		root.addEqualTo("timestamp", timestampSubQuery);

		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);

		Query query = QueryFactory.newQuery(WorkArea.class, root);
		return (WorkArea) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }

    @Override
    public List<WorkArea> getWorkArea(String department, Date asOfDate) {
        Criteria root = new Criteria();
        Criteria effdt = new Criteria();
        Criteria timestamp = new Criteria();

        effdt.addEqualToField("dept", Criteria.PARENT_QUERY_PREFIX + "dept");
        effdt.addLessOrEqualThan("effectiveDate", asOfDate);
        ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(WorkArea.class, effdt);
        effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

        timestamp.addEqualToField("dept", Criteria.PARENT_QUERY_PREFIX + "dept");
        timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
        ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(WorkArea.class, timestamp);
        timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});

        root.addEqualTo("dept", department);
        root.addEqualTo("effectiveDate", effdtSubQuery);
        root.addEqualTo("timestamp", timestampSubQuery);

        Criteria activeFilter = new Criteria(); // Inner Join For Activity
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(WorkArea.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        List<WorkArea> wal = new ArrayList<WorkArea>(c.size());
        wal.addAll(c);
        return wal;
    }

    @Override
    public void saveOrUpdate(WorkArea workArea) {
    	this.getPersistenceBrokerTemplate().store(workArea);
    }

	@Override
	public WorkArea getWorkArea(Long tkWorkAreaId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("tkWorkAreaId", tkWorkAreaId);
		
		Query query = QueryFactory.newQuery(WorkArea.class, crit);
		return (WorkArea)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

}
