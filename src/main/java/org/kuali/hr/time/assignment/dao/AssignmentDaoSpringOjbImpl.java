package org.kuali.hr.time.assignment.dao;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.time.assignment.Assignment;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AssignmentDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements AssignmentDao {

	private static final Logger LOG = Logger.getLogger(AssignmentDaoSpringOjbImpl.class);

	@Override
	public void saveOrUpdate(Assignment assignment) {
		this.getPersistenceBrokerTemplate().store(assignment);
	}

	@Override
	public void saveOrUpdate(List<Assignment> assignments) {
		if (assignments != null) {
			for (Assignment assign : assignments) {
				this.getPersistenceBrokerTemplate().store(assign);
			}
		}
	}

	@Override
	public void delete(Assignment assignment) {
		if (assignment != null) {
			LOG.debug("Deleting assignment:" + assignment.getTkAssignmentId());
			this.getPersistenceBrokerTemplate().delete(assignment);
		} else {
			LOG.warn("Attempt to delete null assignment.");
		}
	}

	@Override
	public void deleteAllAssignments() {
		Criteria crit = new Criteria();
		Query query = QueryFactory.newQuery(Assignment.class, crit);
		LOG.warn("Deleting all Assignments");
		this.getPersistenceBrokerTemplate().deleteByQuery(query);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Assignment> findAssignments(String principalId, Date asOfDate) {
		List<Assignment> assignments = new ArrayList<Assignment>();
		Criteria root = new Criteria();
		Criteria effdt = new Criteria();
		Criteria timestamp = new Criteria();

		// OJB's awesome sub query setup part 1
		effdt.addEqualToField("jobNumber", Criteria.PARENT_QUERY_PREFIX + "jobNumber");
		effdt.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
		effdt.addEqualToField("task", Criteria.PARENT_QUERY_PREFIX + "task");
		effdt.addLessOrEqualThan("effectiveDate", asOfDate);
		//effdt.addEqualTo("active", true);
		effdt.addEqualTo("principalId", principalId);
		ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(Assignment.class, effdt);
		effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

		// OJB's awesome sub query setup part 2
		timestamp.addEqualToField("jobNumber", Criteria.PARENT_QUERY_PREFIX + "jobNumber");
		timestamp.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
		timestamp.addEqualToField("task", Criteria.PARENT_QUERY_PREFIX + "task");
		timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
		//timestamp.addEqualTo("active", true);
		timestamp.addEqualTo("principalId", principalId);
		ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(Assignment.class, timestamp);
		timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});

		root.addEqualTo("principalId", principalId);
		root.addEqualTo("effectiveDate", effdtSubQuery);
		root.addEqualTo("timestamp", timestampSubQuery);
		//root.addEqualTo("active", true);

		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);

		Query query = QueryFactory.newQuery(Assignment.class, root);
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

		if (c != null) {
			assignments.addAll(c);
		}

		return assignments;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Assignment> getActiveAssignmentsInWorkArea(Long workArea, Date asOfDate){
		List<Assignment> assignments = new ArrayList<Assignment>();
		Criteria root = new Criteria();
		Criteria effdt = new Criteria();
		Criteria timestamp = new Criteria();

		// OJB's awesome sub query setup part 1
		effdt.addLessOrEqualThan("effectiveDate", asOfDate);
		effdt.addEqualTo("active", true);
		effdt.addEqualTo("workArea", workArea);
        effdt.addEqualToField("task", Criteria.PARENT_QUERY_PREFIX + "task");
        effdt.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
		ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(Assignment.class, effdt);
		effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

		// OJB's awesome sub query setup part 2
		timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
		timestamp.addEqualTo("active", true);
		timestamp.addEqualTo("workArea", workArea);
        timestamp.addEqualToField("task", Criteria.PARENT_QUERY_PREFIX + "task");
        timestamp.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
		ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(Assignment.class, timestamp);
		timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});

		root.addEqualTo("workArea", workArea);
		root.addEqualTo("effectiveDate", effdtSubQuery);
		root.addEqualTo("timestamp", timestampSubQuery);
		root.addEqualTo("active", true);

		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);

		Query query = QueryFactory.newQuery(Assignment.class, root);
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

		if (c != null) {
			assignments.addAll(c);
		}

		return assignments;
	}

	public List<Assignment> getActiveAssignments(Date asOfDate){
		List<Assignment> assignments = new ArrayList<Assignment>();
		Criteria root = new Criteria();
		Criteria effdt = new Criteria();
		Criteria timestamp = new Criteria();

		// OJB's awesome sub query setup part 1
		effdt.addLessOrEqualThan("effectiveDate", asOfDate);
		effdt.addEqualTo("active", true);
		ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(Assignment.class, effdt);
		effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

		// OJB's awesome sub query setup part 2
		timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
		timestamp.addEqualTo("active", true);
		ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(Assignment.class, timestamp);
		timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});

		root.addEqualTo("effectiveDate", effdtSubQuery);
		root.addEqualTo("timestamp", timestampSubQuery);
		root.addEqualTo("active", true);

		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);

		Query query = QueryFactory.newQuery(Assignment.class, root);
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

		if (c != null) {
			assignments.addAll(c);
		}

		return assignments;
	}

	public Assignment getAssignment(String tkAssignmentId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("tkAssignmentId", tkAssignmentId);
		Query query = QueryFactory.newQuery(Assignment.class, crit);
		return (Assignment) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}


}
