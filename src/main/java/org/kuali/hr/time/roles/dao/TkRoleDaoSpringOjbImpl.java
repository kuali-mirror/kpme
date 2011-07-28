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
import org.kuali.hr.job.Job;
import org.kuali.hr.time.roles.TkRole;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class TkRoleDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements TkRoleDao {

    public List<TkRole> findAllRoles(String principalId, Date asOfDate) {
        return findRoles(principalId, asOfDate, null, null, null, null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TkRole> findPositionRoles(Long positionNumber, Date asOfDate, String roleName, Long workArea, String department, String chart) {
        List<TkRole> roles = new ArrayList<TkRole>();

        Criteria root = new Criteria();
        Criteria effdt = new Criteria();
        Criteria timestamp = new Criteria();
        ReportQueryByCriteria effdtSubQuery;
        ReportQueryByCriteria timestampSubQuery;

        effdt.addEqualToField("roleName", Criteria.PARENT_QUERY_PREFIX + "roleName");
        effdt.addEqualToField("positionNumber", Criteria.PARENT_QUERY_PREFIX + "positionNumber");
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
        timestamp.addEqualToField("positionNumber", Criteria.PARENT_QUERY_PREFIX + "positionNumber");
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
        if (positionNumber != null)
            root.addEqualTo("positionNumber", positionNumber);

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
    public List<TkRole> findRoles(String principalId, Date asOfDate, String roleName, Long workArea, String department, String chart) {
        List<TkRole> roles = new ArrayList<TkRole>();

        Criteria root = new Criteria();
        Criteria effdt = new Criteria();
        Criteria timestamp = new Criteria();
        ReportQueryByCriteria effdtSubQuery;
        ReportQueryByCriteria timestampSubQuery;

        effdt.addEqualToField("roleName", Criteria.PARENT_QUERY_PREFIX + "roleName");
        if(StringUtils.isNotEmpty(principalId)){
        	effdt.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
        }
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
        if(StringUtils.isNotEmpty(principalId)){
        	timestamp.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
        }
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
        
        List<Job> lstActiveJobs = TkServiceLocator.getJobSerivce().getJobs(principalId, TKUtils.getCurrentDate());
        for(Job job : lstActiveJobs){
        	if(job.getPositionNumber()!=null){
        		List<TkRole> lstRoles = findPositionRoles(job.getPositionNumber(), 
        								asOfDate, roleName, workArea, department, chart);
        		roles.addAll(lstRoles);
        	}
        	
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

	@Override
	public TkRole getRole(Long tkRoleId) {
		Criteria currentRecordCriteria = new Criteria();
		currentRecordCriteria.addEqualTo("tkRolesId", tkRoleId);

		return (TkRole) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(TkRole.class, currentRecordCriteria));
	}
	
	@Override
	public List<TkRole> getRolesByPosition(Long positionNumber) {
		Criteria currentRecordCriteria = new Criteria();
		currentRecordCriteria.addEqualTo("positionNumber", positionNumber);
		List<TkRole> tkRoles = new ArrayList<TkRole>();
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(QueryFactory.newQuery(TkRole.class, currentRecordCriteria));
		if(c != null)
			tkRoles.addAll(c);
		return tkRoles;
	}

}
