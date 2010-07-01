package org.kuali.hr.time.assignment.dao;

import java.sql.Date;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.time.assignment.Assignment;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class AssignmentDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements AssignmentDao {

    private static final Logger LOG = Logger.getLogger(AssignmentDaoSpringOjbImpl.class);

    @SuppressWarnings("unchecked")
    @Override
    public List<Assignment> findCurrentlyValidActiveAssignments(String principalId) {
	List<Assignment> list = new LinkedList<Assignment>();
	
	Criteria crit = new Criteria();
	crit.addEqualTo("active", true);
	crit.addEqualTo("principalId", principalId);
	
	Query query = QueryFactory.newQuery(Assignment.class, crit);
	Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
	if (c != null) {
	    list.addAll(c);
	}
	
	return list;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Assignment> findAssignmentsOnOrAfter(Date date) {
	List<Assignment> list = new LinkedList<Assignment>();
	Criteria crit = new Criteria();
	crit.addGreaterOrEqualThan("effectiveDate", date);

	Query query = QueryFactory.newQuery(Assignment.class, crit);
	Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

	if (c != null) {
	    list.addAll(c);
	}

	return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Assignment> findAllAssignments() {
	List<Assignment> list = new LinkedList<Assignment>();
	Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(QueryFactory.newQuery(Assignment.class, (Criteria) null));
	
	if (c != null) {
	    list.addAll(c);
	}
	
	return list;
    }

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

}
