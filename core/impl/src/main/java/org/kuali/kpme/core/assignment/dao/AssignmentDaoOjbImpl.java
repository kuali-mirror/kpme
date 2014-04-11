/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.core.assignment.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.assignment.AssignmentBo;
import org.kuali.kpme.core.assignment.AssignmentBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.util.OjbSubQueryUtil;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class AssignmentDaoOjbImpl extends PlatformAwareDaoBaseOjb implements AssignmentDao {

    private static final Logger LOG = Logger.getLogger(AssignmentDaoOjbImpl.class);
     @Override
    public void saveOrUpdate(AssignmentBo assignment) {
        this.getPersistenceBrokerTemplate().store(assignment);
    }

    @Override
    public void saveOrUpdate(List<AssignmentBo> assignments) {
        if (assignments != null) {
            for (AssignmentBo assign : assignments) {
                this.getPersistenceBrokerTemplate().store(assign);
            }
        }
    }

    @Override
    public void delete(AssignmentBo assignment) {
        if (assignment != null) {
            LOG.debug("Deleting assignment:" + assignment.getTkAssignmentId());
            this.getPersistenceBrokerTemplate().delete(assignment);
        } else {
            LOG.warn("Attempt to delete null assignment.");
        }
    }

    public AssignmentBo getAssignment(String principalId, String groupKeyCode, Long jobNumber, Long workArea, Long task, LocalDate asOfDate) {
        Criteria root = new Criteria();

        root.addEqualTo("principalId", principalId);
        root.addEqualTo("groupKeyCode", groupKeyCode);
        root.addEqualTo("jobNumber", jobNumber);
        root.addEqualTo("workArea", workArea);
        root.addEqualTo("task", task);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(AssignmentBo.class, asOfDate, AssignmentBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(AssignmentBo.class, AssignmentBo.BUSINESS_KEYS, false));
        //root.addEqualTo("active", true);

        Criteria activeFilter = new Criteria(); // Inner Join For Activity
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(AssignmentBo.class, root);
        Object o = this.getPersistenceBrokerTemplate().getObjectByQuery(query);

        return (AssignmentBo) o;
    }


    @Override
    public AssignmentBo getAssignmentForTargetPrincipal(String groupKeyCode, Long job, Long workArea, Long task, LocalDate asOfDate) {
        Criteria root = new Criteria();

        root.addEqualTo("groupKeyCode", groupKeyCode);
        root.addEqualTo("jobNumber", job);
        root.addEqualTo("workArea", workArea);
        root.addEqualTo("task", task);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(AssignmentBo.class, asOfDate, AssignmentBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(AssignmentBo.class, AssignmentBo.BUSINESS_KEYS, false));
        root.addEqualTo("principalId", HrContext.getTargetPrincipalId());
        //root.addEqualTo("active", true);

        Criteria activeFilter = new Criteria(); // Inner Join For Activity
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(AssignmentBo.class, root);
        Object o = this.getPersistenceBrokerTemplate().getObjectByQuery(query);

        return (AssignmentBo) o;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public List<AssignmentBo> findAssignments(String principalId, LocalDate asOfDate) {
        List<AssignmentBo> assignments = new ArrayList<AssignmentBo>();
        Criteria root = new Criteria();

        root.addEqualTo("principalId", principalId);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(AssignmentBo.class, asOfDate, AssignmentBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(AssignmentBo.class, AssignmentBo.BUSINESS_KEYS, false));
        //root.addEqualTo("active", true);

        Criteria activeFilter = new Criteria(); // Inner Join For Activity
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(AssignmentBo.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null) {
            assignments.addAll(c);
        }

        return assignments;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public List<AssignmentBo> findAssignmentsWithinPeriod(String principalId, LocalDate startDate, LocalDate endDate) {
        List<AssignmentBo> assignments = new ArrayList<AssignmentBo>();
        Criteria root = new Criteria();

        root.addGreaterOrEqualThan("effectiveDate", startDate.toDate());
        root.addLessOrEqualThan("effectiveDate", endDate.toDate());
        root.addEqualTo("principalId", principalId);
        root.addEqualTo("active", true);

        Query query = QueryFactory.newQuery(AssignmentBo.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null) {
            assignments.addAll(c);
        }

        return assignments;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public List<AssignmentBo> getActiveAssignmentsInWorkArea(Long workArea, LocalDate asOfDate) {
        List<AssignmentBo> assignments = new ArrayList<AssignmentBo>();
        Criteria root = new Criteria();

        root.addEqualTo("workArea", workArea);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(AssignmentBo.class, asOfDate, AssignmentBo.BUSINESS_KEYS, true));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(AssignmentBo.class, AssignmentBo.BUSINESS_KEYS, true));
        root.addEqualTo("active", true);

        Criteria activeFilter = new Criteria(); // Inner Join For Activity
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(AssignmentBo.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null) {
            assignments.addAll(c);
        }

        return assignments;
    }

    @Override
    public List<AssignmentBo> getActiveAssignmentsInWorkAreas(List<Long> workAreas, LocalDate asOfDate) {
        List<AssignmentBo> assignments = new ArrayList<AssignmentBo>();
        Criteria root = new Criteria();

        root.addIn("workArea", workAreas);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(AssignmentBo.class, asOfDate, AssignmentBo.BUSINESS_KEYS, true));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(AssignmentBo.class, AssignmentBo.BUSINESS_KEYS, true));
        root.addEqualTo("active", true);

        Criteria activeFilter = new Criteria(); // Inner Join For Activity
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(AssignmentBo.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null) {
            assignments.addAll(c);
        }

        return assignments;
    }

    public List<AssignmentBo> getActiveAssignments(LocalDate asOfDate) {
        List<AssignmentBo> assignments = new ArrayList<AssignmentBo>();
        
        Criteria root = new Criteria();
        root.addLessOrEqualThan("effectiveDate", asOfDate.toDate());

        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(AssignmentBo.class, asOfDate, AssignmentBo.BUSINESS_KEYS, true));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(AssignmentBo.class, AssignmentBo.BUSINESS_KEYS, true));

		Criteria activeFilter = new Criteria();
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(AssignmentBo.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null) {
            assignments.addAll(c);
        }

        return assignments;
    }

    public AssignmentBo getAssignment(String tkAssignmentId) {
        Criteria crit = new Criteria();
        crit.addEqualTo("tkAssignmentId", tkAssignmentId);
        Query query = QueryFactory.newQuery(AssignmentBo.class, crit);
        return (AssignmentBo) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }

    // KPME-1129 Kagata
    @SuppressWarnings({"rawtypes", "unchecked"})
    public List<AssignmentBo> getActiveAssignmentsForJob(String principalId, Long jobNumber, LocalDate asOfDate) {
        List<AssignmentBo> assignments = new ArrayList<AssignmentBo>();
        Criteria root = new Criteria();

        root.addEqualTo("principalId", principalId);
        root.addEqualTo("jobNumber", jobNumber);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(AssignmentBo.class, asOfDate, AssignmentBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(AssignmentBo.class, AssignmentBo.BUSINESS_KEYS, false));
        root.addEqualTo("active", true);

        Criteria activeFilter = new Criteria(); // Inner Join For Activity
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(AssignmentBo.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null) {
            assignments.addAll(c);
        }

        return assignments;
    }

	@Override
    @SuppressWarnings("unchecked")
    public List<AssignmentBo> searchAssignments(LocalDate fromEffdt, LocalDate toEffdt, String principalId, String jobNumber, String dept, String workArea,
    										  String active, String showHistory) {

        List<AssignmentBo> results = new ArrayList<AssignmentBo>();
        
        Criteria root = new Criteria();

        Criteria effectiveDateFilter = new Criteria();
        if (fromEffdt != null) {
            effectiveDateFilter.addGreaterOrEqualThan("effectiveDate", fromEffdt.toDate());
        }
        if (toEffdt != null) {
            effectiveDateFilter.addLessOrEqualThan("effectiveDate", toEffdt.toDate());
        }
        if (fromEffdt == null && toEffdt == null) {
            effectiveDateFilter.addLessOrEqualThan("effectiveDate", LocalDate.now().toDate());
        }
        root.addAndCriteria(effectiveDateFilter);
        
        if (StringUtils.isNotBlank(principalId)) {
            root.addLike("UPPER(principalId)", principalId.toUpperCase()); // KPME-2695 in case principal id is not a number
        }

//        if (StringUtils.isNotBlank(jobNumber)) {
//            root.addLike("jobNumber", jobNumber);
//        }
        
        if (StringUtils.isNotBlank(jobNumber)) {
            OjbSubQueryUtil.addNumericCriteria(root, "jobNumber", jobNumber);
        }

        if (StringUtils.isNotBlank(dept)) {
            Criteria workAreaCriteria = new Criteria();
            LocalDate asOfDate = toEffdt != null ? toEffdt : LocalDate.now();
            List<Long> workAreasForDept = HrServiceLocator.getWorkAreaService().getWorkAreasForDepartment(dept, asOfDate);
            if (CollectionUtils.isNotEmpty(workAreasForDept)) {
                workAreaCriteria.addIn("workArea", workAreasForDept);
            }
            root.addAndCriteria(workAreaCriteria);
        }

        if (StringUtils.isNotBlank(workArea)) {
            OjbSubQueryUtil.addNumericCriteria(root, "workArea", workArea);
        }
        
        if (StringUtils.isNotBlank(active)) {
        	Criteria activeFilter = new Criteria();
            if (StringUtils.equals(active, "Y")) {
                activeFilter.addEqualTo("active", true);
            } else if (StringUtils.equals(active, "N")) {
                activeFilter.addEqualTo("active", false);
            }
            root.addAndCriteria(activeFilter);
        }

        if (StringUtils.equals(showHistory, "N")) {
            root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQueryWithFilter(AssignmentBo.class, effectiveDateFilter, AssignmentBo.BUSINESS_KEYS, false));
            root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(AssignmentBo.class, AssignmentBo.BUSINESS_KEYS, false));
        }
        
        Query query = QueryFactory.newQuery(AssignmentBo.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));
        
        return results;
    }
    
    @Override
    public AssignmentBo getMaxTimestampAssignment(String principalId) {
    	Criteria root = new Criteria();
        Criteria crit = new Criteria();
        
        crit.addEqualTo("principalId", principalId);
        ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(AssignmentBo.class, crit);
        timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});

        root.addEqualTo("principalId", principalId);
        root.addEqualTo("timestamp", timestampSubQuery);

        Query query = QueryFactory.newQuery(AssignmentBo.class, root);
        return (AssignmentBo) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }

    public List<String> getPrincipalIds(List<String> workAreaList, LocalDate effdt, LocalDate startDate, LocalDate endDate) {
    	List<AssignmentBo> results = this.getAssignments(workAreaList, effdt, startDate, endDate);
        Set<String> pids = new HashSet<String>();
        for(AssignmentBo anAssignment : results) {
        	if(anAssignment != null) {
        		pids.add(anAssignment.getPrincipalId());
        	}
        }
        List<String> ids = new ArrayList<String>();
        ids.addAll(pids);
     	return ids;
    }
    
    public List<AssignmentBo> getAssignments(List<String> workAreaList, LocalDate effdt, LocalDate startDate, LocalDate endDate) {
    	List<AssignmentBo> results = new ArrayList<AssignmentBo>();
		 
		Criteria activeRoot = new Criteria();
     	Criteria inactiveRoot = new Criteria();

        ReportQueryByCriteria effdtSubQuery = OjbSubQueryUtil.getEffectiveDateSubQueryWithoutFilter(AssignmentBo.class, AssignmentBo.BUSINESS_KEYS, false);
        ReportQueryByCriteria activeEffdtSubQuery = OjbSubQueryUtil.getEffectiveDateSubQuery(AssignmentBo.class, effdt, AssignmentBo.BUSINESS_KEYS, false);
        ReportQueryByCriteria timestampSubQuery = OjbSubQueryUtil.getTimestampSubQuery(AssignmentBo.class, AssignmentBo.BUSINESS_KEYS, false);

        inactiveRoot.addEqualTo("active", "N");
        inactiveRoot.addIn("workArea", workAreaList);
        inactiveRoot.addGreaterOrEqualThan("effectiveDate", startDate.toDate());
        inactiveRoot.addLessOrEqualThan("effectiveDate", endDate.toDate());
        inactiveRoot.addEqualTo("effectiveDate", effdtSubQuery);
        inactiveRoot.addEqualTo("timestamp", timestampSubQuery);
         
        activeRoot.addIn("workArea", workAreaList);
        activeRoot.addEqualTo("active", "Y");
        activeRoot.addEqualTo("effectiveDate", activeEffdtSubQuery);
        activeRoot.addEqualTo("timestamp", timestampSubQuery);
        activeRoot.addOrCriteria(inactiveRoot);
         
        Query query = QueryFactory.newQuery(AssignmentBo.class, activeRoot);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        if (c != null) {
        	results.addAll(c);
        }
        
        return results;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public List<AssignmentBo> findAssignmentsHistoryForPeriod(String principalId, LocalDate startDate, LocalDate endDate) {
        List<AssignmentBo> assignments = new ArrayList<AssignmentBo>();
        Criteria root = new Criteria();

        Date start = new java.sql.Date(startDate.toDate().getTime());
        Date end = new java.sql.Date(endDate.toDate().getTime());
        root.addGreaterOrEqualThan("effectiveDate", start);
        root.addLessThan("effectiveDate", end);
        root.addEqualTo("principalId", principalId);

        ReportQueryByCriteria query = QueryFactory.newReportQuery(AssignmentBo.class, root);
        query.addOrderByAscending("effectiveDate");
        query.addOrderByAscending("timestamp");
        //query.setAttributes(new String[] {"/*+ no_query_transformation */ A0.tk_assignment_id", "principalId", "jobNumber", "effectiveDate",
        //        "workArea", "task", "active", "timestamp"});
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null) {
            assignments.addAll(c);
        }

        return assignments;
    }

    @Override
    public List<AssignmentBo> findAssignmentsWithinPeriod(String principalId, LocalDate startDate, LocalDate endDate, boolean requireActive) {
        if (requireActive) {
            return findAssignmentsWithinPeriod(principalId, startDate, endDate);
        } else {
            List<AssignmentBo> assignments = new ArrayList<AssignmentBo>();
            Criteria root = new Criteria();

            root.addEqualTo("principalId", principalId);

            //Active criteria
            Criteria activeSubCriteria = new Criteria();
            activeSubCriteria.addEqualTo("active", true);
            Criteria activeEffdtSubCriteria = new Criteria();
            activeEffdtSubCriteria.addEqualTo("active", false);
            activeEffdtSubCriteria.addGreaterThan("effectiveDate", startDate);
            activeSubCriteria.addOrCriteria(activeEffdtSubCriteria);
            root.addAndCriteria(activeSubCriteria);

            //Effective date
            Criteria effDateSubCriteria = new Criteria();
            effDateSubCriteria.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
            effDateSubCriteria.addEqualToField("jobNumber", Criteria.PARENT_QUERY_PREFIX + "jobNumber");
            effDateSubCriteria.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
            effDateSubCriteria.addEqualToField("task", Criteria.PARENT_QUERY_PREFIX + "task");
            effDateSubCriteria.addLessOrEqualThan("effectiveDate", endDate);
            ReportQueryByCriteria effectiveDateSubQuery = QueryFactory.newReportQuery(AssignmentBo.class, effDateSubCriteria);
            effectiveDateSubQuery.setAttributes(new String[] { new StringBuffer("max(effectiveDate)").toString() });
            root.addEqualTo("effectiveDate", effectiveDateSubQuery);

            //Effective timestamp
            Criteria effTimestampSubCriteria = new Criteria();
            effTimestampSubCriteria.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
            effTimestampSubCriteria.addEqualToField("jobNumber", Criteria.PARENT_QUERY_PREFIX + "jobNumber");
            effTimestampSubCriteria.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
            effTimestampSubCriteria.addEqualToField("task", Criteria.PARENT_QUERY_PREFIX + "task");
            effTimestampSubCriteria.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
            ReportQueryByCriteria effectiveTimestampSubQuery = QueryFactory.newReportQuery(AssignmentBo.class, effTimestampSubCriteria);
            effectiveTimestampSubQuery.setAttributes(new String[] { new StringBuffer("max(timestamp)").toString() });
            root.addEqualTo("timestamp", effectiveTimestampSubQuery);

            ReportQueryByCriteria query = QueryFactory.newReportQuery(AssignmentBo.class, root);
            query.setAttributes(new String[] {"/*+ no_query_transformation */ A0.tk_assignment_id", "principalId", "jobNumber", "effectiveDate",
                    "workArea", "task", "active", "timestamp"});
            Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

            if (c != null) {
                assignments.addAll(c);
            }

            return assignments;
        }
    }
}
