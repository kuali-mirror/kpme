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
package org.kuali.kpme.core.workarea.dao;

import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.department.DepartmentBo;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.OjbSubQueryUtil;
import org.kuali.kpme.core.workarea.WorkAreaBo;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;
import org.kuali.rice.krad.service.KRADServiceLocator;

public class WorkAreaDaoOjbImpl extends PlatformAwareDaoBaseOjb implements WorkAreaDao {
   
    @Override
    public WorkAreaBo getWorkArea(Long workArea, LocalDate asOfDate) {
		Criteria root = new Criteria();

		root.addEqualTo("workArea", workArea);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(WorkAreaBo.class, asOfDate, WorkAreaBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(WorkAreaBo.class, WorkAreaBo.BUSINESS_KEYS, false));

		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);

		Query query = QueryFactory.newQuery(WorkAreaBo.class, root);
		return (WorkAreaBo) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }

    @Override
    public List<WorkAreaBo> getWorkAreas(List<Long> workAreas, LocalDate asOfDate) {
        Criteria root = new Criteria();

        root.addIn("workArea", workAreas);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(WorkAreaBo.class, asOfDate, WorkAreaBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(WorkAreaBo.class, WorkAreaBo.BUSINESS_KEYS, false));

        Criteria activeFilter = new Criteria(); // Inner Join For Activity
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(WorkAreaBo.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        List<WorkAreaBo> wal = new ArrayList<WorkAreaBo>(c.size());
        wal.addAll(c);
        return wal;
    }

    @Override
    public List<WorkAreaBo> getWorkAreaForDepartmentBusinessKeyIds(List<String> departmentBusinessKeyIds, LocalDate asOfDate) {
        Criteria root = new Criteria();

        Map<String, List<String>> groupKeyDeptMap = new HashMap<String, List<String>>();
        for (String id : departmentBusinessKeyIds) {
            String tempKey = DepartmentBo.getGroupKeycodeFromBusinessKeyId(id);
            String tempDept = DepartmentBo.getDeptFromBusinessKeyId(id);

            if (groupKeyDeptMap.containsKey(tempKey)) {
                groupKeyDeptMap.get(tempKey).add(tempDept);
            } else {
                List<String> tempList = new ArrayList<String>();
                tempList.add(tempDept);
                groupKeyDeptMap.put(tempKey, tempList);
            }
        }
        Criteria subCriteria = new Criteria();
        for (Map.Entry<String, List<String>> entry : groupKeyDeptMap.entrySet()) {
            Criteria bKey = new Criteria();
            bKey.addEqualTo("groupKeyCode", entry.getKey());
            bKey.addIn("dept", entry.getValue());
            subCriteria.addOrCriteria(bKey);
        }
        root.addAndCriteria(subCriteria);
        //root.addIn("dept", thdepartments);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(WorkAreaBo.class, asOfDate, WorkAreaBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(WorkAreaBo.class, WorkAreaBo.BUSINESS_KEYS, false));

        Criteria activeFilter = new Criteria(); // Inner Join For Activity
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(WorkAreaBo.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        List<WorkAreaBo> wal = new ArrayList<WorkAreaBo>(c.size());
        wal.addAll(c);
        return wal;
    }

    @Override
     public List<WorkAreaBo> getWorkArea(String department, LocalDate asOfDate) {
        Criteria root = new Criteria();
        if(StringUtils.contains(department, HrConstants.WILDCARD_CHARACTER)) {
        	root.addLike("dept", department);
        } else {
        	root.addEqualTo("dept", department);
        }
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(WorkAreaBo.class, asOfDate, WorkAreaBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(WorkAreaBo.class, WorkAreaBo.BUSINESS_KEYS, false));

        Criteria activeFilter = new Criteria(); // Inner Join For Activity
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(WorkAreaBo.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        List<WorkAreaBo> wal = new ArrayList<WorkAreaBo>(c.size());
        wal.addAll(c);
        return wal;
    }

    @Override
    public void saveOrUpdate(WorkAreaBo workArea) {
    	this.getPersistenceBrokerTemplate().store(workArea);
    }

	@Override
	public WorkAreaBo getWorkArea(String tkWorkAreaId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("tkWorkAreaId", tkWorkAreaId);
		
		Query query = QueryFactory.newQuery(WorkAreaBo.class, crit);
		return (WorkAreaBo)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}
	
	public Long getNextWorkAreaKey(){
		 return KRADServiceLocator.getSequenceAccessorService().getNextAvailableSequenceNumber("tk_work_area_key_s");
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<WorkAreaBo> getWorkAreas(String dept, String workArea, String description, LocalDate fromEffdt, LocalDate toEffdt, String active, String showHistory) {
		List<WorkAreaBo> results = new ArrayList<WorkAreaBo>();
		
		Criteria root = new Criteria();

		if (StringUtils.isNotBlank(dept)) {
			root.addLike("dept", dept);
		}
		
		if (StringUtils.isNotBlank(workArea)) {
			OjbSubQueryUtil.addNumericCriteria(root, "workArea", workArea);
		}
		
		if (StringUtils.isNotBlank(description)) {
			root.addLike("UPPER(descr)", description.toUpperCase()); // KPME-2695
		}
		
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
            root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQueryWithFilter(WorkAreaBo.class, effectiveDateFilter, WorkAreaBo.BUSINESS_KEYS, false));
            root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(WorkAreaBo.class, WorkAreaBo.BUSINESS_KEYS, false));
		}
		
        Query query = QueryFactory.newQuery(WorkAreaBo.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));
		
		return results;
	}

	@Override
	public int getWorkAreaCount(String dept, Long workArea) {
		Criteria crit = new Criteria();
		if(dept != null) {
			crit.addEqualTo("dept", dept);
		}
		crit.addEqualTo("workArea", workArea);
		Query query = QueryFactory.newQuery(WorkAreaBo.class, crit);
		return this.getPersistenceBrokerTemplate().getCount(query); 
	}

}
