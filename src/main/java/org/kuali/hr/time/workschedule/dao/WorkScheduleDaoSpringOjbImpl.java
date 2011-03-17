package org.kuali.hr.time.workschedule.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.time.workschedule.WorkSchedule;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

import java.sql.Date;
import java.util.List;

public class WorkScheduleDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements WorkScheduleDao {

    @Override
    public WorkSchedule getWorkSchedule(Long workSchedule, Date asOfDate) {
        WorkSchedule ws = null;

        // TODO : add effdt/timestamp

        Criteria root = new Criteria();
        Criteria effdt = new Criteria();
        Criteria timestamp = new Criteria();

        effdt.addEqualToField("hrWorkSchedule", Criteria.PARENT_QUERY_PREFIX + "hrWorkSchedule");
        effdt.addLessOrEqualThan("effectiveDate", asOfDate);
        ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(WorkSchedule.class, effdt);
        effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

        timestamp.addEqualToField("hrWorkSchedule", Criteria.PARENT_QUERY_PREFIX + "hrWorkSchedule");
        timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
        ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(WorkSchedule.class, timestamp);
        timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});

        root.addEqualTo("hrWorkSchedule", workSchedule);
        root.addEqualTo("effectiveDate", effdtSubQuery);
        root.addEqualTo("timestamp", timestampSubQuery);

        Criteria activeFilter = new Criteria(); // Inner Join For Activity
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(WorkSchedule.class, root);

        ws = (WorkSchedule) this.getPersistenceBrokerTemplate().getObjectByQuery(query);

        return ws;
    }

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

}
