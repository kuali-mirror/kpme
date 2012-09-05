package org.kuali.hr.time.timecollection.rule.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.time.collection.rule.TimeCollectionRule;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class TimeCollectionRuleDaoServiceImpl extends PlatformAwareDaoBaseOjb implements TimeCollectionRuleDaoService {

    /*
      * Returns valid TimeCollectionRule based on dept,workArea, and asOfDate
      * dept and work area are wildcardable values
      * @see org.kuali.hr.time.timecollection.rule.dao.TimeCollectionRuleDaoService#getTimeCollectionRule(java.lang.String dept,
      * java.lang.Long workArea, java.sql.Date asOfDate)
      */

    @Override
    public TimeCollectionRule getTimeCollectionRule(String dept, Long workArea, Date asOfDate) {


        TimeCollectionRule timeCollectionRule = new TimeCollectionRule();

        //First call confirm no exact match
        timeCollectionRule = getTimeCollectionRuleWildCarded(dept, workArea, asOfDate);
        if (timeCollectionRule != null) {
            return timeCollectionRule;
        }
        //Try with dept wildcarded *
        timeCollectionRule = getTimeCollectionRuleWildCarded("%", workArea, asOfDate);
        if (timeCollectionRule != null) {
            return timeCollectionRule;
        }

        //Try with work area wildcarded
        timeCollectionRule = getTimeCollectionRuleWildCarded(dept, -1L, asOfDate);
        if (timeCollectionRule != null) {
            return timeCollectionRule;
        }

        //Try with everything wildcarded
        timeCollectionRule = getTimeCollectionRuleWildCarded("%", -1L, asOfDate);
        if (timeCollectionRule != null) {
            return timeCollectionRule;
        }

        //Return default time collection rule
        timeCollectionRule = new TimeCollectionRule();
        timeCollectionRule.setActive(true);
        timeCollectionRule.setClockUserFl(true);
        timeCollectionRule.setDept(dept);
        timeCollectionRule.setWorkArea(workArea);

        return timeCollectionRule;
    }

    private TimeCollectionRule getTimeCollectionRuleWildCarded(String dept, Long workArea, Date asOfDate) {
        Criteria root = new Criteria();
        Criteria effdt = new Criteria();
        Criteria timestamp = new Criteria();

        effdt.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
        effdt.addLessOrEqualThan("effectiveDate", asOfDate);
//		effdt.addEqualTo("active", true);
        effdt.addEqualTo("dept", dept);
        ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(TimeCollectionRule.class, effdt);
        effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

        timestamp.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
        timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
//		timestamp.addEqualTo("active", true);
        timestamp.addEqualTo("dept", dept);
        ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(TimeCollectionRule.class, timestamp);
        timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});

        root.addEqualTo("dept", dept);
        root.addEqualTo("workArea", workArea);
        root.addEqualTo("effectiveDate", effdtSubQuery);
        root.addEqualTo("timestamp", timestampSubQuery);
//		root.addEqualTo("active", true);

        Criteria activeFilter = new Criteria(); // Inner Join For Activity
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);


        Query query = QueryFactory.newQuery(TimeCollectionRule.class, root);
        return (TimeCollectionRule) this.getPersistenceBrokerTemplate().getObjectByQuery(query);

    }

    @Override
    public TimeCollectionRule getTimeCollectionRule(String tkTimeCollectionRuleId) {
        Criteria crit = new Criteria();
        crit.addEqualTo("tkTimeCollectionRuleId", tkTimeCollectionRuleId);

        Query query = QueryFactory.newQuery(TimeCollectionRule.class, crit);
        return (TimeCollectionRule) this.getPersistenceBrokerTemplate().getObjectByQuery(query);

    }

    /* Jira 1152
      * Returns valid TimeCollectionRule based on dept, workArea, payType, and asOfDate
      * dept, work area, and payType can be wildcardable values
      */
    @Override
    public TimeCollectionRule getTimeCollectionRule(String dept, Long workArea, String payType, Date asOfDate) {


        TimeCollectionRule timeCollectionRule = new TimeCollectionRule();

        //First call confirm no exact match
        timeCollectionRule = getTimeCollectionRuleWildCarded(dept, workArea, payType, asOfDate);
        if (timeCollectionRule != null) {
            return timeCollectionRule;
        }
        //Try with dept wildcarded *
        timeCollectionRule = getTimeCollectionRuleWildCarded("%", workArea, payType, asOfDate);
        if (timeCollectionRule != null) {
            return timeCollectionRule;
        }

        //Try with work area wildcarded
        timeCollectionRule = getTimeCollectionRuleWildCarded(dept, -1L, payType, asOfDate);
        if (timeCollectionRule != null) {
            return timeCollectionRule;
        }

        //Try with payType wildcarded
        timeCollectionRule = getTimeCollectionRuleWildCarded(dept, workArea, "%", asOfDate);
        if (timeCollectionRule != null) {
            return timeCollectionRule;
        }

        //Try with dept and workArea wildcarded
        timeCollectionRule = getTimeCollectionRuleWildCarded("%", -1L, payType, asOfDate);
        if (timeCollectionRule != null) {
            return timeCollectionRule;
        }

        //Try with dept and payType wildcarded
        timeCollectionRule = getTimeCollectionRuleWildCarded("%", workArea, "%", asOfDate);
        if (timeCollectionRule != null) {
            return timeCollectionRule;
        }

        //Try with workArea and payType wildcarded
        timeCollectionRule = getTimeCollectionRuleWildCarded(dept, -1L, "%", asOfDate);
        if (timeCollectionRule != null) {
            return timeCollectionRule;
        }

        //Try with everything wildcarded
        timeCollectionRule = getTimeCollectionRuleWildCarded("%", -1L, "%", asOfDate);
        if (timeCollectionRule != null) {
            return timeCollectionRule;
        }

        //Return default time collection rule
        timeCollectionRule = new TimeCollectionRule();
        timeCollectionRule.setActive(true);
        timeCollectionRule.setClockUserFl(true);
        timeCollectionRule.setDept(dept);
        timeCollectionRule.setWorkArea(workArea);

        return timeCollectionRule;
    }

    private TimeCollectionRule getTimeCollectionRuleWildCarded(String dept, Long workArea, String payType, Date asOfDate) {
        Criteria root = new Criteria();
        Criteria effdt = new Criteria();
        Criteria timestamp = new Criteria();

        effdt.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
        effdt.addLessOrEqualThan("effectiveDate", asOfDate);
//		effdt.addEqualTo("active", true);
        effdt.addEqualTo("dept", dept);
        effdt.addEqualTo("payType", payType);
        ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(TimeCollectionRule.class, effdt);
        effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

        timestamp.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
        timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
//		timestamp.addEqualTo("active", true);
        timestamp.addEqualTo("dept", dept);
        timestamp.addEqualTo("payType", payType);
        ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(TimeCollectionRule.class, timestamp);
        timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});

        root.addEqualTo("dept", dept);
        root.addEqualTo("workArea", workArea);
        root.addEqualTo("payType", payType);
        root.addEqualTo("effectiveDate", effdtSubQuery);
        root.addEqualTo("timestamp", timestampSubQuery);
//		root.addEqualTo("active", true);

        Criteria activeFilter = new Criteria(); // Inner Join For Activity
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);


        Query query = QueryFactory.newQuery(TimeCollectionRule.class, root);
        return (TimeCollectionRule) this.getPersistenceBrokerTemplate().getObjectByQuery(query);

    }

    @Override
    public List<TimeCollectionRule> getTimeCollectionRules(String dept, Long workArea, String payType, String active, String showHistory) {

        List<TimeCollectionRule> results = new ArrayList<TimeCollectionRule>();

        Criteria root = new Criteria();
        Criteria effdt = new Criteria();
        Criteria timestamp = new Criteria();
        if (workArea != null) {
            effdt.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
        } else {
            effdt.addIsNull(Criteria.PARENT_QUERY_PREFIX + "workArea");
        }
        effdt.addLessOrEqualThan("effectiveDate", TKUtils.getCurrentDate());
        if (StringUtils.isNotBlank(dept)) {
            effdt.addEqualTo("dept", dept);
        }
        if (StringUtils.isNotBlank(payType)) {
            effdt.addEqualTo("payType", payType);
        }
        ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(TimeCollectionRule.class, effdt);
        effdtSubQuery.setAttributes(new String[]{"max(effdt)"});
        if (workArea != null) {
            timestamp.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
        } else {
            timestamp.addIsNull(Criteria.PARENT_QUERY_PREFIX + "workArea");
        }
        timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
        if (StringUtils.isNotBlank(dept)) {
            timestamp.addEqualTo("dept", dept);
        }
        if (StringUtils.isNotBlank(payType)) {
            timestamp.addEqualTo("payType", payType);
        }
        ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(TimeCollectionRule.class, timestamp);
        timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});

        if (StringUtils.isNotBlank(dept)) {
            root.addLike("dept", dept);
        }

        if (workArea != null) {
            root.addLike("workArea", workArea);
        }
        if (StringUtils.isNotBlank(payType)) {
            root.addLike("payType", payType);
        }
        //root.addEqualTo("effectiveDate", effdtSubQuery);
        //root.addEqualTo("timestamp", timestampSubQuery);

        if (StringUtils.equals(active, "Y")) {
            Criteria activeFilter = new Criteria(); // Inner Join For Activity
            activeFilter.addEqualTo("active", true);
            root.addAndCriteria(activeFilter);
        } else if (StringUtils.equals(active, "N")) {
            Criteria activeFilter = new Criteria(); // Inner Join For Activity
            activeFilter.addEqualTo("active", false);
            root.addAndCriteria(activeFilter);
        }

        Query query = QueryFactory.newQuery(TimeCollectionRule.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        results.addAll(c);

        return results;
    }
}
