package org.kuali.hr.job.dao;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.util.TKUtils;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

import java.util.*;

/**
 * Represents an implementation of {@link JobDao}.
 */
public class JobDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements JobDao {

    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(JobDaoSpringOjbImpl.class);

    public void saveOrUpdate(Job job) {
        this.getPersistenceBrokerTemplate().store(job);
    }

    public void saveOrUpdate(List<Job> jobList) {
        if (jobList != null) {
            for (Job job : jobList) {
                this.saveOrUpdate(job);
            }
        }
    }

    public Job getPrimaryJob(String principalId, Date payPeriodEndDate) {
        Criteria root = new Criteria();
        Criteria effdt = new Criteria();
        Criteria timestamp = new Criteria();

        effdt.addEqualToField("jobNumber", Criteria.PARENT_QUERY_PREFIX + "jobNumber");
        effdt.addLessOrEqualThan("effectiveDate", payPeriodEndDate);

        effdt.addEqualTo("principalId", principalId);
        ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(Job.class, effdt);
        effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

        timestamp.addEqualToField("jobNumber", Criteria.PARENT_QUERY_PREFIX + "jobNumber");
        timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
        timestamp.addEqualTo("principalId", principalId);
        ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(Job.class, timestamp);
        timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});

        root.addEqualTo("principalId", principalId);
        root.addEqualTo("effectiveDate", effdtSubQuery);
        root.addEqualTo("timestamp", timestampSubQuery);
        root.addEqualTo("primaryIndicator", true);

        Criteria activeFilter = new Criteria(); // Inner Join For Activity
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(Job.class, root);

        return (Job) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public List<Job> getJobs(String principalId, Date payPeriodEndDate) {
        List<Job> jobs = new LinkedList<Job>();
        Criteria root = new Criteria();
        Criteria effdt = new Criteria();
        Criteria timestamp = new Criteria();

        effdt.addEqualToField("jobNumber", Criteria.PARENT_QUERY_PREFIX + "jobNumber");
        effdt.addLessOrEqualThan("effectiveDate", payPeriodEndDate);
        effdt.addEqualTo("principalId", principalId);
        ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(Job.class, effdt);
        effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

        timestamp.addEqualToField("jobNumber", Criteria.PARENT_QUERY_PREFIX + "jobNumber");
        timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
        timestamp.addEqualTo("principalId", principalId);
        ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(Job.class, timestamp);
        timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});

        root.addEqualTo("principalId", principalId);
        root.addEqualTo("effectiveDate", effdtSubQuery);
        root.addEqualTo("timestamp", timestampSubQuery);

        Criteria activeFilter = new Criteria(); // Inner Join For Activity
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);


        Query query = QueryFactory.newQuery(Job.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null) {
            jobs.addAll(c);
        }


        return jobs;
    }

    public Job getJob(String principalId, Long jobNumber, Date asOfDate) {
        Criteria root = new Criteria();
        Criteria effdt = new Criteria();
        Criteria timestamp = new Criteria();

        effdt.addEqualToField("jobNumber", Criteria.PARENT_QUERY_PREFIX + "jobNumber");
        effdt.addLessOrEqualThan("effectiveDate", asOfDate);

        effdt.addEqualTo("principalId", principalId);
        ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(Job.class, effdt);
        effdtSubQuery.setAttributes(new String[]{"max(effdt)"});


        timestamp.addEqualToField("jobNumber", Criteria.PARENT_QUERY_PREFIX + "jobNumber");
        timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");

        timestamp.addEqualTo("principalId", principalId);
        ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(Job.class, timestamp);
        timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});

        root.addEqualTo("principalId", principalId);
        root.addEqualTo("jobNumber", jobNumber);
        root.addEqualTo("effectiveDate", effdtSubQuery);
        root.addEqualTo("timestamp", timestampSubQuery);

        Criteria activeFilter = new Criteria(); // Inner Join For Activity
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(Job.class, root);
        Job job = (Job) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
        return job;
    }

    @SuppressWarnings("unchecked")
    public List<Job> getActiveJobsForPosition(String positionNbr, Date asOfDate) {
        Criteria root = new Criteria();
        Criteria effdt = new Criteria();
        Criteria timestamp = new Criteria();

        // OJB's awesome sub query setup part 1
        effdt.addEqualToField("jobNumber", Criteria.PARENT_QUERY_PREFIX + "jobNumber");
        effdt.addLessOrEqualThan("effectiveDate", asOfDate);
        effdt.addEqualTo("positionNumber", positionNbr);
        ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(Job.class, effdt);
        effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

        // OJB's awesome sub query setup part 2
        timestamp.addEqualToField("jobNumber", Criteria.PARENT_QUERY_PREFIX + "jobNumber");
        timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
        timestamp.addEqualTo("positionNumber", positionNbr);
        ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(Job.class, timestamp);
        timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});

        root.addEqualTo("positionNumber", positionNbr);
        root.addEqualTo("effectiveDate", effdtSubQuery);
        root.addEqualTo("timestamp", timestampSubQuery);

        Criteria activeFilter = new Criteria(); // Inner Join For Activity
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(Job.class, root);
        return (List<Job>) this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
    }

    @SuppressWarnings("unchecked")
    public List<Job> getActiveJobsForPayType(String hrPayType, Date asOfDate) {
        Criteria root = new Criteria();
        Criteria effdt = new Criteria();
        Criteria timestamp = new Criteria();

        // OJB's awesome sub query setup part 1
        effdt.addEqualToField("jobNumber", Criteria.PARENT_QUERY_PREFIX + "jobNumber");
        effdt.addLessOrEqualThan("effectiveDate", asOfDate);
        effdt.addEqualTo("hrPayType", hrPayType);
        ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(Job.class, effdt);
        effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

        // OJB's awesome sub query setup part 2
        timestamp.addEqualToField("jobNumber", Criteria.PARENT_QUERY_PREFIX + "jobNumber");
        timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
        timestamp.addEqualTo("hrPayType", hrPayType);
        ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(Job.class, timestamp);
        timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});

        root.addEqualTo("hrPayType", hrPayType);
        root.addEqualTo("effectiveDate", effdtSubQuery);
        root.addEqualTo("timestamp", timestampSubQuery);

        Criteria activeFilter = new Criteria(); // Inner Join For Activity
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(Job.class, root);
        return (List<Job>) this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
    }

    @Override
    public Job getJob(String hrJobId) {
        Criteria crit = new Criteria();
        crit.addEqualTo("hrJobId", hrJobId);

        Query query = QueryFactory.newQuery(Job.class, crit);
        return (Job) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }

    @Override
    public Job getMaxJob(String principalId) {
        Criteria root = new Criteria();
        Criteria crit = new Criteria();
        crit.addEqualTo("principalId", principalId);
        ReportQueryByCriteria jobNumberSubQuery = QueryFactory.newReportQuery(Job.class, crit);
        jobNumberSubQuery.setAttributes(new String[]{"max(jobNumber)"});

        crit.addEqualTo("principalId", principalId);
        root.addEqualTo("jobNumber", jobNumberSubQuery);

        Query query = QueryFactory.newQuery(Job.class, root);
        return (Job) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }

    @Override
    public List<Job> getJobs(String principalId, String jobNumber,
                             String dept, String positionNbr, String payType,
                             java.sql.Date fromEffdt, java.sql.Date toEffdt, String active, String showHistory) {

        List<Job> results = new ArrayList<Job>();

        Criteria crit = new Criteria();
        Criteria effdtCrit = new Criteria();
        Criteria timestampCrit = new Criteria();

        if (fromEffdt != null) {
            crit.addGreaterOrEqualThan("effectiveDate", fromEffdt);
        }

        if (toEffdt != null) {
            crit.addLessOrEqualThan("effectiveDate", toEffdt);
        } else {
            crit.addLessOrEqualThan("effectiveDate", TKUtils.getCurrentDate());
        }

        if (StringUtils.isNotEmpty(jobNumber)) {
            crit.addLike("jobNumber", jobNumber);
        }

        if (StringUtils.isNotEmpty(principalId)) {
            crit.addLike("principalId", principalId);
        }

        if (StringUtils.isNotEmpty(dept)) {
            crit.addLike("dept", dept);
        }

        if (StringUtils.isNotEmpty(positionNbr)) {
            crit.addLike("positionNbr", positionNbr);
        }

        if (StringUtils.isNotEmpty(payType)) {
            crit.addLike("payType", payType);
        }

        if (StringUtils.isEmpty(active) && StringUtils.equals(showHistory, "Y")) {
            effdtCrit.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
            effdtCrit.addEqualToField("jobNumber", Criteria.PARENT_QUERY_PREFIX + "jobNumber");
            ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(Job.class, effdtCrit);
            effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

            timestampCrit.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
            timestampCrit.addEqualToField("jobNumber", Criteria.PARENT_QUERY_PREFIX + "jobNumber");
            ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(Job.class, timestampCrit);
            timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});

            crit.addEqualTo("effectiveDate", effdtSubQuery);
            crit.addEqualTo("timestamp", timestampSubQuery);

            Query query = QueryFactory.newQuery(Job.class, crit);
            Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
            results.addAll(c);
        } else if (StringUtils.isEmpty(active) && StringUtils.equals(showHistory, "N")) {
            effdtCrit.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
            effdtCrit.addEqualToField("jobNumber", Criteria.PARENT_QUERY_PREFIX + "jobNumber");
            ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(Job.class, effdtCrit);
            effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

            timestampCrit.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
            timestampCrit.addEqualToField("jobNumber", Criteria.PARENT_QUERY_PREFIX + "jobNumber");
            ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(Job.class, timestampCrit);
            timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});

            crit.addEqualTo("effectiveDate", effdtSubQuery);
            crit.addEqualTo("timestamp", timestampSubQuery);

            Query query = QueryFactory.newQuery(Job.class, crit);
            Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
            results.addAll(c);
        } else if (StringUtils.equals(active, "Y") && StringUtils.equals("N", showHistory)) {
            effdtCrit.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
            effdtCrit.addEqualToField("jobNumber", Criteria.PARENT_QUERY_PREFIX + "jobNumber");
            ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(Job.class, effdtCrit);
            effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

            timestampCrit.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
            timestampCrit.addEqualToField("jobNumber", Criteria.PARENT_QUERY_PREFIX + "jobNumber");
            ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(Job.class, timestampCrit);
            timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});

            crit.addEqualTo("effectiveDate", effdtSubQuery);
            crit.addEqualTo("timestamp", timestampSubQuery);

            Criteria activeFilter = new Criteria(); // Inner Join For Activity
            activeFilter.addEqualTo("active", true);
            crit.addAndCriteria(activeFilter);

            Query query = QueryFactory.newQuery(Job.class, crit);
            Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
            results.addAll(c);
        } //return all active records from the database
        else if (StringUtils.equals(active, "Y") && StringUtils.equals("Y", showHistory)) {
            Criteria activeFilter = new Criteria(); // Inner Join For Activity
            activeFilter.addEqualTo("active", true);
            crit.addAndCriteria(activeFilter);
            Query query = QueryFactory.newQuery(Job.class, crit);
            Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
            results.addAll(c);
        }
        //return all inactive records in the database
        else if (StringUtils.equals(active, "N") && StringUtils.equals(showHistory, "Y")) {
            effdtCrit.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
            effdtCrit.addEqualToField("jobNumber", Criteria.PARENT_QUERY_PREFIX + "jobNumber");
            ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(Job.class, effdtCrit);
            effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

            timestampCrit.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
            timestampCrit.addEqualToField("jobNumber", Criteria.PARENT_QUERY_PREFIX + "jobNumber");
            ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(Job.class, timestampCrit);
            timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});

            crit.addEqualTo("effectiveDate", effdtSubQuery);
            crit.addEqualTo("timestamp", timestampSubQuery);

            Criteria activeFilter = new Criteria(); // Inner Join For Activity
            activeFilter.addEqualTo("active", false);
            crit.addAndCriteria(activeFilter);
            Query query = QueryFactory.newQuery(Job.class, crit);
            Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
            results.addAll(c);
        }

        //return the most effective inactive rows if there are no active rows <= the curr date
        else if (StringUtils.equals(active, "N") && StringUtils.equals(showHistory, "N")) {
            effdtCrit.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
            effdtCrit.addEqualToField("jobNumber", Criteria.PARENT_QUERY_PREFIX + "jobNumber");
            ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(Job.class, effdtCrit);
            effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

            timestampCrit.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
            timestampCrit.addEqualToField("jobNumber", Criteria.PARENT_QUERY_PREFIX + "jobNumber");
            ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(Job.class, timestampCrit);
            timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});

            Criteria activeFilter = new Criteria(); // Inner Join For Activity
            activeFilter.addEqualTo("active", false);
            crit.addAndCriteria(activeFilter);
            Query query = QueryFactory.newQuery(Job.class, crit);
            Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
            results.addAll(c);
        }

        return results;
    }

}
