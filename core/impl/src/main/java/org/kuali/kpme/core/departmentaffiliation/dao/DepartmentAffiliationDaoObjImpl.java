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
package org.kuali.kpme.core.departmentaffiliation.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.util.OjbSubQueryUtil;
import org.kuali.kpme.core.departmentaffiliation.DepartmentAffiliation;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class DepartmentAffiliationDaoObjImpl extends PlatformAwareDaoBaseOjb implements DepartmentAffiliationDao {

    @Override
    public DepartmentAffiliation getDepartmentAffiliationById(String hrDeptAfflId) {
        Criteria crit = new Criteria();
        crit.addEqualTo("hrDeptAfflId", hrDeptAfflId);
        Query query = QueryFactory.newQuery(DepartmentAffiliation.class, crit);
        return (DepartmentAffiliation) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }

    @Override
    public DepartmentAffiliation getDepartmentAffiliationByType(String deptAfflType) {
        Criteria crit = new Criteria();
        crit.addEqualTo("deptAfflType", deptAfflType);
        Query query = QueryFactory.newQuery(DepartmentAffiliation.class, crit);
        return (DepartmentAffiliation) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }

    @Override
    public List<DepartmentAffiliation> getDepartmentAffiliationList(String deptAfflType, LocalDate asOfDate) {

        List<DepartmentAffiliation> pdaList = new ArrayList<DepartmentAffiliation>();
        Criteria root = new Criteria();


        if(StringUtils.isNotEmpty(deptAfflType)) {
            root.addEqualTo("deptAfflType", deptAfflType);
        }

        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(DepartmentAffiliation.class, asOfDate, DepartmentAffiliation.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(DepartmentAffiliation.class, DepartmentAffiliation.BUSINESS_KEYS, false));

        Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(DepartmentAffiliation.class, root);

        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        if(!c.isEmpty())
            pdaList.addAll(c);

        return pdaList;
    }

    @Override
    public List<DepartmentAffiliation> getAllActiveAffiliations() {
        List<DepartmentAffiliation> aList = new ArrayList<DepartmentAffiliation>();
        Criteria root = new Criteria();
        root.addEqualTo("active", true);
        Query query = QueryFactory.newQuery(DepartmentAffiliation.class, root);

        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        if(!c.isEmpty())
            aList.addAll(c);

        return aList;
    }

}
