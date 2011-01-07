package org.kuali.hr.time.roles.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.time.roles.TkRole;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class TkRoleDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements TkRoleDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<TkRole> findRoles(Long workArea, String department, String roleName, Date asOfDate, String principalId) {
		List<TkRole> roles = new ArrayList<TkRole>();
		
		if ( (workArea == null || workArea.longValue() < 0) 
				&& (StringUtils.isEmpty(department)) 
				&& (StringUtils.isEmpty(principalId)) )
				throw new RuntimeException("You must provide at least one of [workArea, department, principalId].");
		
		// Set up some booleans as a convenience and readability aid
		//
		boolean principalCheck = !StringUtils.isEmpty(principalId);
		boolean workAreaCheck = (workArea != null && workArea.longValue() > 0);
		boolean departmentCheck = !StringUtils.isEmpty(department);
		boolean roleNameCheck = !StringUtils.isEmpty(roleName);
		boolean dateCheck = (asOfDate != null);
		
		// Create criteria objects - we will only use root if dateCheck is false. 
		Criteria root = new Criteria();
		Criteria effdt = null;
		Criteria timestamp = null;	
		ReportQueryByCriteria effdtSubQuery = null;
		ReportQueryByCriteria timestampSubQuery = null;
		
		// Only need effective date/time stamp criteria if we're doing date checking.
		// date checking is to account for roles are currently effective
		if (dateCheck) {
			effdt = new Criteria();
			timestamp = new Criteria();
			
			if (workAreaCheck) {
				effdt.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
				timestamp.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
			}
			
			if (departmentCheck) {
				effdt.addEqualToField("department", Criteria.PARENT_QUERY_PREFIX + "department");
				timestamp.addEqualToField("department", Criteria.PARENT_QUERY_PREFIX + "department");
			}
			
			effdt.addEqualToField("roleName", Criteria.PARENT_QUERY_PREFIX + "roleName");
			effdt.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
			effdt.addLessOrEqualThan("effectiveDate", asOfDate);
//			effdt.addEqualTo("active", true);

			effdtSubQuery = QueryFactory.newReportQuery(TkRole.class, effdt);
			effdtSubQuery.setAttributes(new String[]{"max(effdt)"});
		
			//Configure the actual "criteria" in the where clause
			timestamp.addEqualToField("roleName", Criteria.PARENT_QUERY_PREFIX + "roleName");
			timestamp.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
			timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
//			timestamp.addEqualTo("active", true);
			
			//Create a subquery based on the just configured where clause
			timestampSubQuery = QueryFactory.newReportQuery(TkRole.class, timestamp);
			
			//Specify the result set of the subquery, this is retrieving the max value of the timestamp field based
			//on all of the items that meet the various criteria specified earlier
			timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});
		}	
		
		//
		// Here we are building up the various criteria for the TkRole we want to return 
		// based in the input parameters.
		//
		if (workAreaCheck)
			root.addEqualTo("workArea", workArea);
		if (departmentCheck)
			root.addEqualTo("department", department);
		if (principalCheck)
			root.addEqualTo("principalId", principalId);
		if (roleNameCheck)
			root.addEqualTo("roleName", roleName);		
		//
		// Optionally add our sub-queries
		//
		if (dateCheck) {
			root.addEqualTo("effectiveDate", effdtSubQuery);
			root.addEqualTo("timestamp", timestampSubQuery);
//			root.addEqualTo("active", true);
			
			Criteria activeFilter = new Criteria(); // Inner Join For Activity
			activeFilter.addEqualTo("active", true);
			root.addAndCriteria(activeFilter);
		}
		
		Query query = QueryFactory.newQuery(TkRole.class, root);
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		
		if (c != null) {
			roles.addAll(c);
		}

		return roles;
	}

	@Override
	public void saveOrUpdateRole(TkRole role) {
		this.getPersistenceBrokerTemplate().store(role);
	}

	@Override
	public void saveOrUpdateRoles(List<TkRole> roles) {
		if (roles != null) {
			for (TkRole role : roles) {
				saveOrUpdateRole(role);
			}
		}
	}

}
