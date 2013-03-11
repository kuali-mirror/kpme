/**
 * Copyright 2004-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.hr.time.roles.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.core.util.OjbSubQueryUtil;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.roles.TkRole;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;
import org.kuali.rice.krad.service.KRADServiceLocator;

public class TkRoleDaoSpringOjbImpl extends PlatformAwareDaoBaseOjb implements TkRoleDao {
    private static final ImmutableList<String> EQUAL_TO_FIELDS = new ImmutableList.Builder<String>()
            .add("roleName")
            .add("positionNumber")
            .add("principalId")
            .add("workArea")
            .add("department")
            .add("chart")
            .build();


    public List<TkRole> findAllRoles(String principalId, Date asOfDate) {
        return findRoles(principalId, asOfDate, null, null, null, null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TkRole> findPositionRoles(String positionNumber, Date asOfDate, String roleName, Long workArea, String department, String chart) {
        List<TkRole> roles = new ArrayList<TkRole>();

        Criteria root = new Criteria();
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(TkRole.class, asOfDate, EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(TkRole.class, EQUAL_TO_FIELDS, false));

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

        if (StringUtils.isNotEmpty(principalId)) {
            root.addEqualTo("principalId", principalId);
        }
        
        if (asOfDate != null) {
            root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(TkRole.class, asOfDate, EQUAL_TO_FIELDS, false));
            root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(TkRole.class, EQUAL_TO_FIELDS, false));
        }
        
        if (StringUtils.isNotEmpty(roleName)) {
            root.addEqualTo("roleName", roleName);
        }
        
        if (workArea != null) {
            root.addEqualTo("workArea", workArea);
        }
        if (StringUtils.isNotEmpty(department)) {
            Criteria departmentCriteria = new Criteria();
            departmentCriteria.addEqualTo("department", department);
            Collection<WorkArea> collectionWorkAreas = TkServiceLocator.getWorkAreaService().getWorkAreas(department, asOfDate);
            if (CollectionUtils.isNotEmpty(collectionWorkAreas)) {
                List<Long> longWorkAreas = new ArrayList<Long>();
                for(WorkArea cwa : collectionWorkAreas){
                    longWorkAreas.add(cwa.getWorkArea());
                }
                Criteria workAreaCriteria = new Criteria();
                workAreaCriteria.addIn("workArea", longWorkAreas);
                departmentCriteria.addOrCriteria(workAreaCriteria);
            }
            root.addAndCriteria(departmentCriteria);
        }
        
        if (StringUtils.isNotEmpty(chart)) {
            root.addEqualTo("chart", chart);
        }

        root.addEqualTo("active", true);

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
                if (!roles.contains(tkRole)) {
                    roles.add(tkRole);
                }
            }
        }
        return roles;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TkRole> findInActiveRoles(String principalId, Date asOfDate, String roleName, Long workArea, String department, String chart) {
        List<TkRole> roles = new ArrayList<TkRole>();

        Criteria root = new Criteria();
        
        if (StringUtils.isNotEmpty(principalId)) {
            root.addEqualTo("principalId", principalId);
        }

        if (asOfDate != null) {
            root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(TkRole.class, asOfDate, EQUAL_TO_FIELDS, false));
            root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(TkRole.class, EQUAL_TO_FIELDS, false));
        }
        
        
        if (StringUtils.isNotEmpty(roleName)) {
            root.addEqualTo("roleName", roleName);
        }
        
        if (workArea != null) {
            root.addEqualTo("workArea", workArea);
        }
        
        if (StringUtils.isNotEmpty(department)) {
            root.addEqualTo("department", department);
        }
        
        if (StringUtils.isNotEmpty(chart)) {
            root.addEqualTo("chart", chart);
        }

        root.addEqualTo("active", false);

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
        currentRecordCriteria.addEqualTo("positionNumber", positionNumber);

        currentRecordCriteria.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(TkRole.class, TKUtils.getCurrentDate(), EQUAL_TO_FIELDS, false));

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
        currentRecordCriteria.addEqualTo("positionNumber", positionNumber);

        currentRecordCriteria.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(TkRole.class, TKUtils.getCurrentDate(), EQUAL_TO_FIELDS, false));

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

        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(TkRole.class, asOfDate, EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(TkRole.class, EQUAL_TO_FIELDS, false));

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
