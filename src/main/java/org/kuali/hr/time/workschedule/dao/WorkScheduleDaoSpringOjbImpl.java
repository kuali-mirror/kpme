package org.kuali.hr.time.workschedule.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.time.workschedule.WorkSchedule;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class WorkScheduleDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements WorkScheduleDao {
	
	/**
	 * Simple save via Spring OJB 
	 * 
	 * @param workSchedule
	 */
	public void saveOrUpdate(WorkSchedule workSchedule) {
		this.getPersistenceBrokerTemplate().store(workSchedule);
	}
	
	/**
	 * This method is broken apart to allow future batch store calls if we think we need it.
	 * @param workSchedules
	 */
	public void saveOrUpdate(List<WorkSchedule> workSchedules) {
		for (WorkSchedule ws : workSchedules) {
			saveOrUpdate(ws);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkSchedule> findWorkSchedules(String principalId, String department, Long workArea, Date asOfDate) {
		List<WorkSchedule> list = new ArrayList<WorkSchedule>();
		
		Criteria root = new Criteria();
		Criteria effdt = new Criteria();
		Criteria timestamp = new Criteria();

		effdt.addEqualToField("dept", Criteria.PARENT_QUERY_PREFIX + "dept");
		effdt.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
		effdt.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
		effdt.addLessOrEqualThan("effectiveDate", asOfDate);
		effdt.addEqualTo("active", true);
		ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(WorkSchedule.class, effdt);
		effdtSubQuery.setAttributes(new String[] { "max(effdt)" });

		// OJB's awesome sub query setup part 2
		timestamp.addEqualToField("dept", Criteria.PARENT_QUERY_PREFIX + "dept");
		timestamp.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
		timestamp.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
		timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
		timestamp.addEqualTo("active", true);
		ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(WorkSchedule.class, timestamp);
		timestampSubQuery.setAttributes(new String[] { "max(timestamp)" });

		root.addEqualTo("dept", department);
		root.addEqualTo("workArea", workArea);
		root.addEqualTo("principalId", principalId);
		root.addEqualTo("effectiveDate", effdtSubQuery);
		root.addEqualTo("timestamp", timestampSubQuery);
		root.addEqualTo("active", true);

		Query query = QueryFactory.newQuery(WorkSchedule.class, root);
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		
		if (c != null) {
			list.addAll(c);
		}
		
		return list;
	}

}
