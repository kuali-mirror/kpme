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
package org.kuali.kpme.core.task.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.task.TaskBo;
import org.kuali.kpme.core.util.OjbSubQueryUtil;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class TaskDaoOjbImpl extends PlatformAwareDaoBaseOjb implements TaskDao {
    
	@Override
	public TaskBo getTask(String tkTaskId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("tkTaskId", tkTaskId);
		
		Query query = QueryFactory.newQuery(TaskBo.class, crit);
		return (TaskBo)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

    @Override
    public TaskBo getMaxTask() {
        Criteria root = new Criteria();
        Criteria crit = new Criteria();

        ReportQueryByCriteria taskNumberSubQuery = QueryFactory.newReportQuery(TaskBo.class, crit);
        taskNumberSubQuery.setAttributes(new String[]{"max(task)"});

        root.addEqualTo("task", taskNumberSubQuery);

        Query query = QueryFactory.newQuery(TaskBo.class, root);
        return (TaskBo) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }

    @Override
    public TaskBo getTask(Long task, LocalDate asOfDate) {
        Criteria root = new Criteria();

        root.addEqualTo("task", task);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(TaskBo.class, asOfDate, TaskBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(TaskBo.class, TaskBo.BUSINESS_KEYS, false));

        Criteria activeFilter = new Criteria(); // Inner Join For Activity
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        TaskBo t = null;
        Query query = QueryFactory.newQuery(TaskBo.class, root);
        Object obj = this.getPersistenceBrokerTemplate().getObjectByQuery(query);
        if (obj instanceof TaskBo)
            t = (TaskBo) obj;

        return t;
    }

    @Override
    public void saveOrUpdate(TaskBo task) {
        this.getPersistenceBrokerTemplate().store(task);
    }

    @Override
    public void saveOrUpdate(List<TaskBo> tasks) {
        for (TaskBo task : tasks)
            this.getPersistenceBrokerTemplate().store(task);
    }
    

    
    @SuppressWarnings("unchecked")
	@Override
    public List<TaskBo> getTasks(Long task, String description, Long workArea, LocalDate fromEffdt, LocalDate toEffdt) {
        Criteria root = new Criteria();

        List<TaskBo> results = new ArrayList<TaskBo>();

        if (task != null) {
        	root.addLike("task", task);
        }
        
        if (StringUtils.isNotBlank(description)) {
        	root.addLike("description", description);
        }
        
        if (workArea != null) {
        	root.addEqualTo("workArea", workArea);
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

        Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQueryWithFilter(TaskBo.class, effectiveDateFilter, TaskBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(TaskBo.class, TaskBo.BUSINESS_KEYS, false));

        Query query = QueryFactory.newQuery(TaskBo.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return results;
    }
   
    @Override
    public int getTaskCount(Long task) {
    	Criteria crit = new Criteria();
		crit.addEqualTo("task",task);
		Query query = QueryFactory.newQuery(TaskBo.class, crit);
		return this.getPersistenceBrokerTemplate().getCount(query);
    }

}
