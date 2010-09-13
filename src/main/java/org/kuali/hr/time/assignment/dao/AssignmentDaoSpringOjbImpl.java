package org.kuali.hr.time.assignment.dao;

import java.sql.Date;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.time.assignment.Assignment;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

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
			LOG.debug("Deleting assignment:" + assignment.getAssignmentId());
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Assignment> findAssignments(String principalId, Date payPeriodEndDate) {
		List<Assignment> list = new LinkedList<Assignment>();
		Criteria crit = new Criteria();
		Criteria effdtJoinCriteria = new Criteria();
		effdtJoinCriteria.addEqualToField("assignmentId", Criteria.PARENT_QUERY_PREFIX + "assignmentId");
		effdtJoinCriteria.addLessOrEqualThan("effectiveDate", payPeriodEndDate);

		ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(Assignment.class, effdtJoinCriteria);
		effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

		crit.addEqualTo("principalId", principalId);
		crit.addEqualTo("active", true);
		crit.addEqualTo("effdt", effdtSubQuery);


		Query query = QueryFactory.newQuery(Assignment.class, crit);
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

		if (c != null) {
			list.addAll(c);
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Assignment> findAssignmentsByJobNumber(Long jobNumber, String principalId, Date payPeriodEndDate) {
		List<Assignment> list = new LinkedList<Assignment>();
		Criteria crit = new Criteria();
		Criteria effdtJoinCriteria = new Criteria();
		effdtJoinCriteria.addEqualToField("assignmentId", Criteria.PARENT_QUERY_PREFIX + "assignmentId");
		effdtJoinCriteria.addLessOrEqualThan("effectiveDate", payPeriodEndDate);

		ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(Assignment.class, effdtJoinCriteria);
		effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

		crit.addEqualTo("principalId", principalId);
		crit.addEqualTo("jobNumber", jobNumber);
		crit.addEqualTo("active", true);
		crit.addEqualTo("effdt", effdtSubQuery);


		Query query = QueryFactory.newQuery(Assignment.class, crit);
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

		if (c != null) {
			list.addAll(c);
		}

		return list;
	}



}
