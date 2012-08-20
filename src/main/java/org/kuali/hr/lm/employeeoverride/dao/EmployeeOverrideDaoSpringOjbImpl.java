package org.kuali.hr.lm.employeeoverride.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.lm.employeeoverride.EmployeeOverride;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class EmployeeOverrideDaoSpringOjbImpl extends PlatformAwareDaoBaseOjb implements EmployeeOverrideDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeOverride> getEmployeeOverrides(String principalId, Date asOfDate) {
        List<EmployeeOverride> employeeOverrides = new ArrayList<EmployeeOverride>();
        Criteria root = new Criteria();
        Criteria effdt = new Criteria();
        Criteria timestamp = new Criteria();

        effdt.addLessOrEqualThan("effectiveDate", asOfDate);
        effdt.addEqualTo("principalId", principalId);
        ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(EmployeeOverride.class, effdt);
        effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

        timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
        timestamp.addEqualTo("principalId", principalId);
        ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(EmployeeOverride.class, timestamp);
        timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});

        root.addEqualTo("principalId", principalId);
        root.addEqualTo("effectiveDate", effdtSubQuery);
        root.addEqualTo("timestamp", timestampSubQuery);

        Criteria activeFilter = new Criteria(); // Inner Join For Activity
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(EmployeeOverride.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null) {
        	employeeOverrides.addAll(c);
        }
        return employeeOverrides;
    }

	@Override
	public EmployeeOverride getEmployeeOverride(String lmEmployeeOverrideId) {
		Criteria crit = new Criteria();
        crit.addEqualTo("lmEmployeeOverrideId", lmEmployeeOverrideId);
        Query query = QueryFactory.newQuery(EmployeeOverride.class, crit);
        return (EmployeeOverride) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

}
