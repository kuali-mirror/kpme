package org.kuali.hr.time.roles.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.roles.TkRole;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;
import org.kuali.rice.krad.service.KRADServiceLocator;

public class TkRoleDaoSpringOjbImpl extends PlatformAwareDaoBaseOjb implements TkRoleDao {

    public List<TkRole> findAllRoles(String principalId, Date asOfDate) {
        return findRoles(principalId, asOfDate, null, null, null, null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TkRole> findPositionRoles(String positionNumber, Date asOfDate, String roleName, Long workArea, String department, String chart) {
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
        if (StringUtils.isNotEmpty(department))
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
        Criteria departmentCriteria = new Criteria();
        Criteria workAreaCriteria = new Criteria();
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
        if (workArea != null) {
            root.addEqualTo("workArea", workArea);
        }

        if (StringUtils.isNotEmpty(department)) {
            departmentCriteria.addEqualTo("department", department);
            Collection<WorkArea> collectionWorkAreas = TkServiceLocator.getWorkAreaService().getWorkAreas(department, asOfDate);
            if (CollectionUtils.isNotEmpty(collectionWorkAreas)) {
                List<Long> longWorkAreas = new ArrayList<Long>();
                for(WorkArea cwa : collectionWorkAreas){
                    longWorkAreas.add(cwa.getWorkArea());
                }
                workAreaCriteria.addIn("workArea", longWorkAreas);
                departmentCriteria.addOrCriteria(workAreaCriteria);
            }
            root.addAndCriteria(departmentCriteria);
        }
        if (StringUtils.isNotEmpty(chart)) {
            root.addEqualTo("chart", chart);
        }
        if (StringUtils.isNotEmpty(roleName)) {
            root.addEqualTo("roleName", roleName);
        }
        if (StringUtils.isNotEmpty(principalId)) {
            root.addEqualTo("principalId", principalId);
        }

        // Filter for ACTIVE = 'Y'
        Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(TkRole.class, root);
        // limit the number of the resultset
        // TODO: hard coding the limits?  probably not the most user friendly of ways to do this
        query.setStartAtIndex(0);
        query.setEndAtIndex(299);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null) {
            roles.addAll(c);
        }

        if (StringUtils.isNotBlank(principalId)) {
            //Fetch all the jobs and grab any position roles for this persons jobs
            List<Job> lstActiveJobs = TkServiceLocator.getJobService().getJobs(principalId, asOfDate);
            for (Job job : lstActiveJobs) {
                if (job.getPositionNumber() != null) {
                    List<TkRole> lstRoles = findPositionRoles(job.getPositionNumber(),
                            asOfDate, roleName, workArea, department, chart);
                    roles.addAll(lstRoles);
                }
            }
        } else if (workArea != null) {
            List<TkRole> lstPosRoles = getPositionRolesForWorkArea(workArea, asOfDate);
            for (TkRole tkRole : lstPosRoles) {
                roles.add(tkRole);
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
        KRADServiceLocator.getBusinessObjectService().save(role);
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
    public TkRole getRole(String tkRoleId) {
        Criteria currentRecordCriteria = new Criteria();
        currentRecordCriteria.addEqualTo("hrRolesId", tkRoleId);

        return (TkRole) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(TkRole.class, currentRecordCriteria));
    }

    @Override
    public TkRole getRolesByPosition(String positionNumber) {
        Criteria currentRecordCriteria = new Criteria();
        Criteria effdt = new Criteria();
        currentRecordCriteria.addEqualTo("positionNumber", positionNumber);

        effdt.addEqualToField("positionNumber", Criteria.PARENT_QUERY_PREFIX + "positionNumber");
        effdt.addLessOrEqualThan("effectiveDate", TKUtils.getCurrentDate());
        ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(TkRole.class, effdt);
        effdtSubQuery.setAttributes(new String[]{"max(effdt)"});
        currentRecordCriteria.addEqualTo("effectiveDate", effdtSubQuery);

        // Filter for ACTIVE = 'Y'
        Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        currentRecordCriteria.addAndCriteria(activeFilter);


        TkRole tkRole = (TkRole) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(TkRole.class, currentRecordCriteria));
        return tkRole;
    }

    @Override
    public TkRole getInactiveRolesByPosition(String positionNumber) {
        Criteria currentRecordCriteria = new Criteria();
        Criteria effdt = new Criteria();
        currentRecordCriteria.addEqualTo("positionNumber", positionNumber);

        effdt.addEqualToField("positionNumber", Criteria.PARENT_QUERY_PREFIX + "positionNumber");
        effdt.addLessOrEqualThan("effectiveDate", TKUtils.getCurrentDate());
        ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(TkRole.class, effdt);
        effdtSubQuery.setAttributes(new String[]{"max(effdt)"});
        currentRecordCriteria.addEqualTo("effectiveDate", effdtSubQuery);

        // Filter for ACTIVE = 'N'
        Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", false);
        currentRecordCriteria.addAndCriteria(activeFilter);


        TkRole tkRole = (TkRole) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(TkRole.class, currentRecordCriteria));
        return tkRole;
    }


    @Override
    public List<TkRole> getPositionRolesForWorkArea(Long workArea, Date asOfDate) {
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
        //timestamp.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
        timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");

        // Adding criteria to nest an AND that has multiple ORs to select
        // the correct ID / date combination.
        orWrapperEd = new Criteria();
        nstWaEd = new Criteria();
        nstDptEd = new Criteria();
        nstChrEd = new Criteria();

        // Inner AND to allow for all null chart/dept/work area
        nullAndWrapper = new Criteria();
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
        root.addEqualTo("roleName", TkConstants.ROLE_TK_APPROVER);

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
}
