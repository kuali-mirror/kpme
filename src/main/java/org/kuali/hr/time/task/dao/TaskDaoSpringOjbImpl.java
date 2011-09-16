package org.kuali.hr.time.task.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.task.Task;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

import java.sql.Date;
import java.util.List;

public class TaskDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements TaskDao {
	
	@Override
	public Task getMaxTaskByWorkArea(Long workAreaId) {
		Criteria root = new Criteria();
		Criteria crit = new Criteria();
		
		crit.addEqualTo("tkWorkAreaId", workAreaId);
		ReportQueryByCriteria taskNumberSubQuery = QueryFactory.newReportQuery(Task.class, crit); 
		taskNumberSubQuery.setAttributes(new String[]{"max(task)"});
		
		root.addEqualTo("tkWorkAreaId", workAreaId);
		root.addEqualTo("task", taskNumberSubQuery);
		
		Query query = QueryFactory.newQuery(Task.class, root);
		return (Task)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

    @Override
    public Task getTask(Long task, Date asOfDate) {
        Criteria root = new Criteria();
        Criteria effdt = new Criteria();
        Criteria timestamp = new Criteria();

        effdt.addEqualToField("task", Criteria.PARENT_QUERY_PREFIX + "task");
        effdt.addLessOrEqualThan("effectiveDate", asOfDate);
        ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(Task.class, effdt);
        effdtSubQuery.setAttributes(new String[] { "max(effdt)" });

        timestamp.addEqualToField("task", Criteria.PARENT_QUERY_PREFIX + "task");
        timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
        ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(Task.class, timestamp);
        timestampSubQuery.setAttributes(new String[] { "max(timestamp)" });

        root.addEqualTo("task", task);
        root.addEqualTo("effectiveDate", effdtSubQuery);
        root.addEqualTo("timestamp", timestampSubQuery);

        Criteria activeFilter = new Criteria(); // Inner Join For Activity
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Task t = null;
        Query query = QueryFactory.newQuery(Task.class, root);
        Object obj = this.getPersistenceBrokerTemplate().getObjectByQuery(query);
        if (obj instanceof Task)
            t = (Task)obj;

        return t;
    }

    @Override
    public void saveOrUpdate(Task task) {
        this.getPersistenceBrokerTemplate().store(task);
    }

    @Override
    public void saveOrUpdate(List<Task> tasks) {
        for (Task task : tasks)
            this.getPersistenceBrokerTemplate().store(task);
    }
}
