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
package org.kuali.kpme.core.department.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.department.DepartmentBo;
import org.kuali.kpme.core.util.OjbSubQueryUtil;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class DepartmentDaoOjbImpl extends PlatformAwareDaoBaseOjb implements DepartmentDao {
    
	@Override
	public void saveOrUpdate(DepartmentBo dept) {
		this.getPersistenceBrokerTemplate().store(dept);
	}

	@Override
	public DepartmentBo getDepartment(String department, String groupKeyCode, LocalDate asOfDate) {
		Criteria root = new Criteria();

		root.addEqualTo("groupKeyCode", groupKeyCode);
		root.addEqualTo("dept", department);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(DepartmentBo.class, asOfDate, DepartmentBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(DepartmentBo.class, DepartmentBo.BUSINESS_KEYS, false));

		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);

		Query query = QueryFactory.newQuery(DepartmentBo.class, root);

		DepartmentBo d = (DepartmentBo)this.getPersistenceBrokerTemplate().getObjectByQuery(query);

		return d;
	}

    @Override
    public List<DepartmentBo> getDepartments(LocalDate asOfDate) {
		Criteria root = new Criteria();

        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(DepartmentBo.class, asOfDate, DepartmentBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(DepartmentBo.class, DepartmentBo.BUSINESS_KEYS, false));

		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);


		Query query = QueryFactory.newQuery(DepartmentBo.class, root);

        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		List<DepartmentBo> d = new ArrayList<DepartmentBo>(c.size());
        d.addAll(c);

		return d;
    }

    /*
	@Override
	@SuppressWarnings("unchecked")
    public List<DepartmentBo> getDepartments(String dept, String location, String departmentDescr, String active, String showHistory, String payrollApproval) {
        List<DepartmentBo> results = new ArrayList<DepartmentBo>();

        Criteria root = new Criteria();

        if (StringUtils.isNotBlank(dept)) {
        	root.addLike("dept", dept);
        }

        if (StringUtils.isNotBlank(location)) {
            root.addLike("UPPER(location)", location.toUpperCase()); // KPME-2695
        }

        if (StringUtils.isNotBlank(departmentDescr)) {
            root.addLike("UPPER(description)", departmentDescr.toUpperCase()); // KPME-2695
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
            root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQueryWithoutFilter(DepartmentBo.class, DepartmentBo.BUSINESS_KEYS, false));
            root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(DepartmentBo.class, DepartmentBo.BUSINESS_KEYS, false));
        }

        if (StringUtils.isNotEmpty(payrollApproval)) {
            root.addEqualTo("payrollApproval",payrollApproval);
        }

        Query query = QueryFactory.newQuery(DepartmentBo.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return results;
    }*/

	@Override
	public DepartmentBo getDepartment(String hrDeptId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("hrDeptId", hrDeptId);
		
		Query query = QueryFactory.newQuery(DepartmentBo.class, crit);
		return (DepartmentBo)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}
	
	@Override
	public int getDepartmentCount(String department, String groupKeyCode) {
		Criteria crit = new Criteria();
		crit.addEqualTo("groupKeyCode", groupKeyCode);
		crit.addEqualTo("dept", department);
		Query query = QueryFactory.newQuery(DepartmentBo.class, crit);
		return this.getPersistenceBrokerTemplate().getCount(query);
	}
	
	/*@Override
	public List<DepartmentBo> getDepartments(String department, LocalDate asOfDate) {
		Criteria root = new Criteria();
		root.addEqualTo("dept", department);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(DepartmentBo.class, asOfDate, DepartmentBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(DepartmentBo.class, DepartmentBo.BUSINESS_KEYS, false));

		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);

		Query query = QueryFactory.newQuery(DepartmentBo.class, root);

		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		List<DepartmentBo> d = new ArrayList<DepartmentBo>(c.size());
        d.addAll(c);

		return d;
	}*/

    @Override
    public List<DepartmentBo> getDepartments(String groupKeyCode, String department, LocalDate asOfDate) {
        Criteria root = new Criteria();
        root.addEqualTo("groupKeyCode", groupKeyCode);
        root.addEqualTo("dept", department);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(DepartmentBo.class, asOfDate, DepartmentBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(DepartmentBo.class, DepartmentBo.BUSINESS_KEYS, false));

        Criteria activeFilter = new Criteria(); // Inner Join For Activity
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(DepartmentBo.class, root);

        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        List<DepartmentBo> d = new ArrayList<DepartmentBo>(c.size());
        d.addAll(c);

        return d;
    }


    @Override
    public List<DepartmentBo> getDepartmentsWithGroupKeys(List<String> groupKeyCodes, LocalDate asOfDate) {
        Criteria root = new Criteria();

        root.addIn("groupKeyCode", groupKeyCodes);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(DepartmentBo.class, asOfDate, DepartmentBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(DepartmentBo.class, DepartmentBo.BUSINESS_KEYS, false));

        Criteria activeFilter = new Criteria(); // Inner Join For Activity
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(DepartmentBo.class, root);

        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        List<DepartmentBo> d = new ArrayList<DepartmentBo>(c.size());
        d.addAll(c);

        return d;
    }

    @Override
    public List<DepartmentBo> getDepartmentsWithDepartmentAndGroupKeys(String department, List<String> groupKeyCodes, LocalDate asOfDate) {
        Criteria root = new Criteria();
        root.addEqualTo("dept", department);
        root.addIn("groupKeyCode", groupKeyCodes);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(DepartmentBo.class, asOfDate, DepartmentBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(DepartmentBo.class, DepartmentBo.BUSINESS_KEYS, false));

        Criteria activeFilter = new Criteria(); // Inner Join For Activity
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(DepartmentBo.class, root);

        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        List<DepartmentBo> d = new ArrayList<DepartmentBo>(c.size());
        d.addAll(c);

        return d;
    }
}
