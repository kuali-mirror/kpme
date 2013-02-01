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
import java.util.List;

import com.google.common.collect.ImmutableList;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.core.util.OjbSubQueryUtil;
import org.kuali.hr.time.workschedule.WorkSchedule;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class WorkScheduleDaoSpringOjbImpl extends PlatformAwareDaoBaseOjb implements WorkScheduleDao {

    @Override
    public WorkSchedule getWorkSchedule(Long workSchedule, Date asOfDate) {
        WorkSchedule ws = null;

        Criteria root = new Criteria();

        ImmutableList<String> fields = new ImmutableList.Builder<String>()
                .add("hrWorkSchedule")
                .build();
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(WorkSchedule.class, asOfDate, fields, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(WorkSchedule.class, fields, false));

        root.addEqualTo("hrWorkSchedule", workSchedule);

        Criteria activeFilter = new Criteria(); // Inner Join For Activity
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(WorkSchedule.class, root);

        ws = (WorkSchedule) this.getPersistenceBrokerTemplate().getObjectByQuery(query);

        return ws;
    }

    /**
	 * Simple save via Spring OJB
	 *
	 * @param workSchedule
	 */
	public void saveOrUpdate(WorkSchedule workSchedule) {
		this.getPersistenceBrokerTemplate().store(workSchedule);
	}

	/**
	 * This method is broken apart to allow future batch store calls if we think we need it.
	 * @param workSchedules
	 */
	public void saveOrUpdate(List<WorkSchedule> workSchedules) {
		for (WorkSchedule ws : workSchedules) {
			saveOrUpdate(ws);
		}
	}

}
