package org.kuali.hr.time.roles.dao;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.time.roles.TkRole;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TkRoleDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements TkRoleDao {

    public List<TkRole> findAllRoles(String principalId, Date asOfDate) {
        return findRoles(principalId, asOfDate, null, null, null, null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TkRole> findRoles(String principalId, Date asOfDate, String roleName, Long workArea, String department, String chart) {
        List<TkRole> roles = new ArrayList<TkRole>();

        Criteria root = new Criteria();
        Criteria effdt = new Criteria();
        Criteria timestamp = new Criteria();
        ReportQueryByCriteria effdtSubQuery;
        ReportQueryByCriteria timestampSubQuery;

        effdt.addEqualToField("roleName", Criteria.PARENT_QUERY_PREFIX + "roleName");
        effdt.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
        effdt.addLessOrEqualThan("effectiveDate", asOfDate);

        // EFFECTIVE DATE --

        // Adding criteria to nest an AND that has multiple ORs to select
        // the correct ID / date combination.
        Criteria orWrapperEd = new Criteria();
        Criteria nstWaEd = new Criteria();
        Criteria nstDptEd = new Criteria();
        Criteria nstChrEd = new Criteria();

        // Inner AND to allow for all null chart/dept/work area
        Criteria nullAndWrapper = new Criteria();
        nullAndWrapper.addIsNull("workArea");
        nullAndWrapper.addIsNull("department");
        nullAndWrapper.addIsNull("chart");

        nstWaEd.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea"); // OR
        nstDptEd.addEqualToField("department", Criteria.PARENT_QUERY_PREFIX + "department"); // OR
        nstChrEd.addEqualToField("chart", Criteria.PARENT_QUERY_PREFIX + "chart"); // OR
        orWrapperEd.addOrCriteria(nstWaEd);
        orWrapperEd.addOrCriteria(nstDptEd);
        orWrapperEd.addOrCriteria(nstChrEd);

        // Inner AND to allow for all null chart/dept/work area
        orWrapperEd.addOrCriteria(nullAndWrapper);

        // Add the inner OR criteria to effective date
        effdt.addAndCriteria(orWrapperEd);

        effdtSubQuery = QueryFactory.newReportQuery(TkRole.class, effdt);
        effdtSubQuery.setAttributes(new String[]{"max(effdt)"});


        // TIMESTAMP --

        //Configure the actual "criteria" in the where clause
        timestamp.addEqualToField("roleName", Criteria.PARENT_QUERY_PREFIX + "roleName");
        timestamp.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
        timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");

        // Adding criteria to nest an AND that has multiple ORs to select
        // the correct ID / date combination.
        orWrapperEd = new Criteria();
        nstWaEd = new Criteria();
        nstDptEd = new Criteria();
        nstChrEd = new Criteria();

        // Inner AND to allow for all null chart/dept/work area
        nullAndWrapper = new Criteria();
        nullAndWrapper.addIsNull("workArea");
        nullAndWrapper.addIsNull("department");
        nullAndWrapper.addIsNull("chart");

        nstWaEd.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea"); // OR
        nstDptEd.addEqualToField("department", Criteria.PARENT_QUERY_PREFIX + "department"); // OR
        nstChrEd.addEqualToField("chart", Criteria.PARENT_QUERY_PREFIX + "chart"); // OR
        orWrapperEd.addOrCriteria(nstWaEd);
        orWrapperEd.addOrCriteria(nstDptEd);
        orWrapperEd.addOrCriteria(nstChrEd);

        // Inner AND to allow for all null chart/dept/work area
        orWrapperEd.addOrCriteria(nullAndWrapper);

        // Add the inner OR criteria to effective date
        timestamp.addAndCriteria(orWrapperEd);

        timestampSubQuery = QueryFactory.newReportQuery(TkRole.class, timestamp);
        timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});


        // Filter by Max(EffDt) / Max(Timestamp)
        root.addEqualTo("effectiveDate", effdtSubQuery);
        root.addEqualTo("timestamp", timestampSubQuery);

        // Optional ROOT criteria added :
        if (workArea != null)
            root.addEqualTo("workArea", workArea);
        if (department != null)
            root.addEqualTo("department", department);
        if (chart != null)
            root.addEqualTo("chart", chart);
        if (roleName != null)
            root.addEqualTo("roleName", roleName);
        if (principalId != null)
            root.addEqualTo("principalId", principalId);

        // Filter for ACTIVE = 'Y'
        Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(TkRole.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null) {
            roles.addAll(c);
        }

        return roles;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<TkRole> findInActiveRoles(String principalId, Date asOfDate, String roleName, Long workArea, String department, String chart) {
    	List<TkRole> roles = new ArrayList<TkRole>();
    	
    	Criteria root = new Criteria();
    	Criteria effdt = new Criteria();
    	Criteria timestamp = new Criteria();
    	ReportQueryByCriteria effdtSubQuery;
    	ReportQueryByCriteria timestampSubQuery;
    	
    	effdt.addEqualToField("roleName", Criteria.PARENT_QUERY_PREFIX + "roleName");
    	effdt.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
    	effdt.addLessOrEqualThan("effectiveDate", asOfDate);
    	
    	// EFFECTIVE DATE --
    	
    	// Adding criteria to nest an AND that has multiple ORs to select
    	// the correct ID / date combination.
    	Criteria orWrapperEd = new Criteria();
    	Criteria nstWaEd = new Criteria();
    	Criteria nstDptEd = new Criteria();
    	Criteria nstChrEd = new Criteria();
    	
    	// Inner AND to allow for all null chart/dept/work area
    	Criteria nullAndWrapper = new Criteria();
    	nullAndWrapper.addIsNull("workArea");
    	nullAndWrapper.addIsNull("department");
    	nullAndWrapper.addIsNull("chart");
    	
    	nstWaEd.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea"); // OR
    	nstDptEd.addEqualToField("department", Criteria.PARENT_QUERY_PREFIX + "department"); // OR
    	nstChrEd.addEqualToField("chart", Criteria.PARENT_QUERY_PREFIX + "chart"); // OR
    	orWrapperEd.addOrCriteria(nstWaEd);
    	orWrapperEd.addOrCriteria(nstDptEd);
    	orWrapperEd.addOrCriteria(nstChrEd);
    	
    	// Inner AND to allow for all null chart/dept/work area
    	orWrapperEd.addOrCriteria(nullAndWrapper);
    	
    	// Add the inner OR criteria to effective date
    	effdt.addAndCriteria(orWrapperEd);
    	
    	effdtSubQuery = QueryFactory.newReportQuery(TkRole.class, effdt);
    	effdtSubQuery.setAttributes(new String[]{"max(effdt)"});
    	
    	
    	// TIMESTAMP --
    	
    	//Configure the actual "criteria" in the where clause
    	timestamp.addEqualToField("roleName", Criteria.PARENT_QUERY_PREFIX + "roleName");
    	timestamp.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
    	timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
    	
    	// Adding criteria to nest an AND that has multiple ORs to select
    	// the correct ID / date combination.
    	orWrapperEd = new Criteria();
    	nstWaEd = new Criteria();
    	nstDptEd = new Criteria();
    	nstChrEd = new Criteria();
    	
    	// Inner AND to allow for all null chart/dept/work area
    	nullAndWrapper = new Criteria();
    	nullAndWrapper.addIsNull("workArea");
    	nullAndWrapper.addIsNull("department");
    	nullAndWrapper.addIsNull("chart");
    	
    	nstWaEd.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea"); // OR
    	nstDptEd.addEqualToField("department", Criteria.PARENT_QUERY_PREFIX + "department"); // OR
    	nstChrEd.addEqualToField("chart", Criteria.PARENT_QUERY_PREFIX + "chart"); // OR
    	orWrapperEd.addOrCriteria(nstWaEd);
    	orWrapperEd.addOrCriteria(nstDptEd);
    	orWrapperEd.addOrCriteria(nstChrEd);
    	
    	// Inner AND to allow for all null chart/dept/work area
    	orWrapperEd.addOrCriteria(nullAndWrapper);
    	
    	// Add the inner OR criteria to effective date
    	timestamp.addAndCriteria(orWrapperEd);
    	
    	timestampSubQuery = QueryFactory.newReportQuery(TkRole.class, timestamp);
    	timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});
    	
    	
    	// Filter by Max(EffDt) / Max(Timestamp)
    	root.addEqualTo("effectiveDate", effdtSubQuery);
    	root.addEqualTo("timestamp", timestampSubQuery);
    	
    	// Optional ROOT criteria added :
    	if (workArea != null)
    		root.addEqualTo("workArea", workArea);
    	if (department != null)
    		root.addEqualTo("department", department);
    	if (chart != null)
    		root.addEqualTo("chart", chart);
    	if (roleName != null)
    		root.addEqualTo("roleName", roleName);
    	if (principalId != null)
    		root.addEqualTo("principalId", principalId);
    	
    	 // Filter for ACTIVE = 'N'
        Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", false);
        root.addAndCriteria(activeFilter);
    	
    	Query query = QueryFactory.newQuery(TkRole.class, root);
    	Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
    	
    	if (c != null) {
    		roles.addAll(c);
    	}
    	
    	return roles;
    }

	public List<TkRole> findRoles2(Long workArea, String department, String roleName, Date asOfDate, String principalId) {
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
