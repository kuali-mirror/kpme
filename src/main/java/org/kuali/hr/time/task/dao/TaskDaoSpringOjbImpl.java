/**
 * Copyright 2004-2012 The Kuali Foundation
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
package org.kuali.hr.time.task.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.time.task.Task;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class TaskDaoSpringOjbImpl extends PlatformAwareDaoBaseOjb implements TaskDao {

    @Override
    public Task getMaxTask() {
        Criteria root = new Criteria();
        Criteria crit = new Criteria();

        ReportQueryByCriteria taskNumberSubQuery = QueryFactory.newReportQuery(Task.class, crit);
        taskNumberSubQuery.setAttributes(new String[]{"max(task)"});

        root.addEqualTo("task", taskNumberSubQuery);

        Query query = QueryFactory.newQuery(Task.class, root);
        return (Task) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }

    @Override
    public Task getTask(Long task, Date asOfDate) {
        Criteria root = new Criteria();
        Criteria effdt = new Criteria();
        Criteria timestamp = new Criteria();

        effdt.addEqualToField("task", Criteria.PARENT_QUERY_PREFIX + "task");
        effdt.addLessOrEqualThan("effectiveDate", asOfDate);
        ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(Task.class, effdt);
        effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

        timestamp.addEqualToField("task", Criteria.PARENT_QUERY_PREFIX + "task");
        timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
        ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(Task.class, timestamp);
        timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});

        root.addEqualTo("task", task);
        root.addEqualTo("effectiveDate", effdtSubQuery);
        root.addEqualTo("timestamp", timestampSubQuery);

        Criteria activeFilter = new Criteria(); // Inner Join For Activity
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Task t = null;
        Query query = QueryFactory.newQuery(Task.class, root);
        Object obj = this.getPersistenceBrokerTemplate().getObjectByQuery(query);
        if (obj instanceof Task)
            t = (Task) obj;

        return t;
    }

    @Override
    public void saveOrUpdate(Task task) {
        this.getPersistenceBrokerTemplate().store(task);
    }

    @Override
    public void saveOrUpdate(List<Task> tasks) {
        for (Task task : tasks)
            this.getPersistenceBrokerTemplate().store(task);
    }

    @Override
    public List<Task> getTasks(Long task, String description, Long workArea, String workAreaDesc, Date fromEffdt, Date toEffdt) {
        Criteria crit = new Criteria();
        Criteria effdt = new Criteria();
        Criteria timestamp = new Criteria();

        List<Task> results = new ArrayList<Task>();

        if (task != null) {
            crit.addLike("task", task);
        }
        if (StringUtils.isNotEmpty(description)) {
            crit.addLike("description", description);
        }
        if (workArea != null) {
            crit.addEqualTo("workArea", workArea);
        }
        if (fromEffdt != null) {
            crit.addGreaterOrEqualThan("effectiveDate", fromEffdt);
        }
        if (toEffdt != null) {
            crit.addLessOrEqualThan("effectiveDate", toEffdt);
        } else {
            crit.addLessOrEqualThan("effectiveDate", TKUtils.getCurrentDate());
        }

        effdt.addEqualToField("task", Criteria.PARENT_QUERY_PREFIX + "task");
        if (toEffdt != null) {
            effdt.addLessOrEqualThan("effectiveDate", toEffdt);
        }
        ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(Task.class, effdt);
        effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

        timestamp.addEqualToField("task", Criteria.PARENT_QUERY_PREFIX + "task");
        timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
        ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(Task.class, timestamp);
        timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});

        crit.addEqualTo("effectiveDate", effdtSubQuery);
        crit.addEqualTo("timestamp", timestampSubQuery);

        Criteria activeFilter = new Criteria(); // Inner Join For Activity
        activeFilter.addEqualTo("active", true);
        crit.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(Task.class, crit);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        results.addAll(c);

        return results;
    }
   
    @Override
    public int getTaskCount(Long task) {
    	Criteria crit = new Criteria();
		crit.addEqualTo("task",task);
		Query query = QueryFactory.newQuery(Task.class, crit);
		return this.getPersistenceBrokerTemplate().getCount(query);
    }
}
