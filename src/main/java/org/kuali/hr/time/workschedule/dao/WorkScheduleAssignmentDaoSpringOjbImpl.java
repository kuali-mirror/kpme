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
package org.kuali.hr.time.workschedule.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.core.util.OjbSubQueryUtil;
import org.kuali.hr.time.workschedule.WorkScheduleAssignment;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class WorkScheduleAssignmentDaoSpringOjbImpl extends PlatformAwareDaoBaseOjb implements WorkScheduleAssignmentDao {

    @Override
    public void saveOrUpdate(WorkScheduleAssignment wsa) {
        this.getPersistenceBrokerTemplate().store(wsa);
    }

    @Override
    public List<WorkScheduleAssignment> getWorkScheduleAssignments(String principalId, String dept, Long workArea, Date asOfDate) {
        List<WorkScheduleAssignment> list = new ArrayList<WorkScheduleAssignment>();

        Criteria root = new Criteria();
        Criteria effdt = new Criteria();
        Criteria timestamp = new Criteria();

        root.addEqualTo("dept", dept);
        root.addEqualTo("workArea", workArea);
        root.addEqualTo("principalId", principalId);
        ImmutableList<String> fields = new ImmutableList.Builder<String>()
                .add("convertFromEarnGroup")
                .add("convertToEarnCode")
                .add("maxHoursEarnGroup")
                .build();
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(WorkScheduleAssignment.class, asOfDate, fields, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(WorkScheduleAssignment.class, fields, false));

        Criteria activeFilter = new Criteria(); // Inner Join For Activity
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(WorkScheduleAssignment.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null) {
            list.addAll(c);
        }

        return list;

    }
}
