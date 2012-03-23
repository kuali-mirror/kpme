package org.kuali.hr.time.assignment.dao;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
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

    public Assignment getAssignment(String principalId, Long jobNumber, Long workArea, Long task, Date asOfDate) {
        Criteria root = new Criteria();
        Criteria effdt = new Criteria();
        Criteria timestamp = new Criteria();

        effdt.addEqualToField("jobNumber", Criteria.PARENT_QUERY_PREFIX + "jobNumber");
        effdt.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
        effdt.addEqualToField("task", Criteria.PARENT_QUERY_PREFIX + "task");
        effdt.addLessOrEqualThan("effectiveDate", asOfDate);
        effdt.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
        ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(Assignment.class, effdt);
        effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

        // OJB's awesome sub query setup part 2
        timestamp.addEqualToField("jobNumber", Criteria.PARENT_QUERY_PREFIX + "jobNumber");
        timestamp.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
        timestamp.addEqualToField("task", Criteria.PARENT_QUERY_PREFIX + "task");
        timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
        //timestamp.addEqualTo("active", true);
        timestamp.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
        ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(Assignment.class, timestamp);
        timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});

        root.addEqualTo("principalId", principalId);
        root.addEqualTo("jobNumber", jobNumber);
        root.addEqualTo("workArea", workArea);
        root.addEqualTo("task", task);
        root.addEqualTo("effectiveDate", effdtSubQuery);
        root.addEqualTo("timestamp", timestampSubQuery);
        //root.addEqualTo("active", true);

        Criteria activeFilter = new Criteria(); // Inner Join For Activity
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(Assignment.class, root);
        Object o = this.getPersistenceBrokerTemplate().getObjectByQuery(query);

        return (Assignment) o;
    }


    @Override
    public Assignment getAssignment(Long job, Long workArea, Long task, Date asOfDate) {
        Criteria root = new Criteria();
        Criteria effdt = new Criteria();
        Criteria timestamp = new Criteria();

        effdt.addEqualToField("jobNumber", Criteria.PARENT_QUERY_PREFIX + "jobNumber");
        effdt.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
        effdt.addEqualToField("task", Criteria.PARENT_QUERY_PREFIX + "task");
        effdt.addLessOrEqualThan("effectiveDate", asOfDate);
        effdt.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
        ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(Assignment.class, effdt);
        effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

        // OJB's awesome sub query setup part 2
        timestamp.addEqualToField("jobNumber", Criteria.PARENT_QUERY_PREFIX + "jobNumber");
        timestamp.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
        timestamp.addEqualToField("task", Criteria.PARENT_QUERY_PREFIX + "task");
        timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
        //timestamp.addEqualTo("active", true);
        timestamp.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
        ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(Assignment.class, timestamp);
        timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});

        root.addEqualTo("jobNumber", job);
        root.addEqualTo("workArea", workArea);
        root.addEqualTo("task", task);
        root.addEqualTo("effectiveDate", effdtSubQuery);
        root.addEqualTo("timestamp", timestampSubQuery);
        root.addEqualTo("principalId", TKContext.getTargetPrincipalId());
        //root.addEqualTo("active", true);

        Criteria activeFilter = new Criteria(); // Inner Join For Activity
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(Assignment.class, root);
        Object o = this.getPersistenceBrokerTemplate().getObjectByQuery(query);

        return (Assignment) o;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public List<Assignment> findAssignments(String principalId, Date asOfDate) {
        List<Assignment> assignments = new ArrayList<Assignment>();
        Criteria root = new Criteria();
        Criteria effdt = new Criteria();
        Criteria timestamp = new Criteria();

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

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public List<Assignment> findAssignmentsWithinPeriod(String principalId, Date startDate, Date endDate) {
        List<Assignment> assignments = new ArrayList<Assignment>();
        Criteria root = new Criteria();

        root.addGreaterOrEqualThan("effectiveDate", startDate);
        root.addLessOrEqualThan("effectiveDate", endDate);
        root.addEqualTo("principalId", principalId);
        root.addEqualTo("active", true);

        Query query = QueryFactory.newQuery(Assignment.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null) {
            assignments.addAll(c);
        }

        return assignments;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public List<Assignment> getActiveAssignmentsInWorkArea(Long workArea, Date asOfDate) {
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

    public List<Assignment> getActiveAssignments(Date asOfDate) {
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

    // KPME-1129 Kagata
    @SuppressWarnings({"rawtypes", "unchecked"})
    public List<Assignment> getActiveAssignmentsForJob(String principalId, Long jobNumber, Date asOfDate) {
        List<Assignment> assignments = new ArrayList<Assignment>();
        Criteria root = new Criteria();
        Criteria effdt = new Criteria();
        Criteria timestamp = new Criteria();

        // subquery for effective date
        effdt.addLessOrEqualThan("effectiveDate", asOfDate);
        effdt.addEqualTo("principalId", principalId);
        effdt.addEqualTo("jobNumber", jobNumber);
        effdt.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
        effdt.addEqualToField("task", Criteria.PARENT_QUERY_PREFIX + "task");
        ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(Assignment.class, effdt);
        effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

        // subquery for timestamp
        timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
        timestamp.addEqualTo("principalId", principalId);
        timestamp.addEqualTo("jobNumber", jobNumber);
        timestamp.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
        timestamp.addEqualToField("task", Criteria.PARENT_QUERY_PREFIX + "task");
        ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(Assignment.class, timestamp);
        timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});

        root.addEqualTo("principalId", principalId);
        root.addEqualTo("jobNumber", jobNumber);
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

    @Override
    public List<Assignment> getAssignments(Date fromEffdt, Date toEffdt, String principalId, String jobNumber,
                                           String dept, String workArea, String active, String showHistory) {

        Criteria crit = new Criteria();
        Criteria effdt = new Criteria();

        List<Assignment> results = new ArrayList<Assignment>();

        if (fromEffdt != null) {
            crit.addGreaterOrEqualThan("effectiveDate", fromEffdt);
        }

        if (toEffdt != null) {
            crit.addLessOrEqualThan("effectiveDate", toEffdt);
        } else {
            crit.addLessOrEqualThan("effectiveDate", TKUtils.getCurrentDate());
        }

        if (StringUtils.isNotEmpty(principalId)) {
            crit.addLike("principalId", principalId);
        }

        if (StringUtils.isNotEmpty(jobNumber)) {
            crit.addLike("jobNumber", jobNumber);
        }

        if (StringUtils.isNotEmpty(dept)) {
            crit.addLike("dept", dept);
        }

        if (StringUtils.isNotEmpty(workArea)) {
            crit.addLike("workArea", workArea);
        }


        if (StringUtils.isEmpty(active) && StringUtils.equals(showHistory, "Y")) {
            Query query = QueryFactory.newQuery(Assignment.class, crit);
            Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
            results.addAll(c);
        } else if (StringUtils.isEmpty(active) && StringUtils.equals(showHistory, "N")) {
            Query query = QueryFactory.newQuery(Assignment.class, crit);
            Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
            results.addAll(c);
        } else if (StringUtils.equals(active, "Y") && StringUtils.equals("N", showHistory)) {
            Criteria activeFilter = new Criteria(); // Inner Join For Activity
            activeFilter.addEqualTo("active", true);
            crit.addAndCriteria(activeFilter);
            Query query = QueryFactory.newQuery(Assignment.class, crit);
            Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
            results.addAll(c);
        } //return all active records from the database
        else if (StringUtils.equals(active, "Y") && StringUtils.equals("Y", showHistory)) {
            Criteria activeFilter = new Criteria(); // Inner Join For Activity
            activeFilter.addEqualTo("active", true);
            crit.addAndCriteria(activeFilter);
            Query query = QueryFactory.newQuery(Assignment.class, crit);
            Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
            results.addAll(c);
        }
        //return all inactive records in the database
        else if (StringUtils.equals(active, "N") && StringUtils.equals(showHistory, "Y")) {
            Criteria activeFilter = new Criteria(); // Inner Join For Activity
            activeFilter.addEqualTo("active", false);
            crit.addAndCriteria(activeFilter);
            Query query = QueryFactory.newQuery(Assignment.class, crit);
            Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
            results.addAll(c);
        }

        //return the most effective inactive rows if there are no active rows <= the curr date
        else if (StringUtils.equals(active, "N") && StringUtils.equals(showHistory, "N")) {
            Criteria activeFilter = new Criteria(); // Inner Join For Activity
            activeFilter.addEqualTo("active", false);
            crit.addAndCriteria(activeFilter);
            Query query = QueryFactory.newQuery(Assignment.class, crit);
            Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
            results.addAll(c);

        }
        return results;
    }


}
