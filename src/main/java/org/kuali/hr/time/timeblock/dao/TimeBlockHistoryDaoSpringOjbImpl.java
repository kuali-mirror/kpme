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
package org.kuali.hr.time.timeblock.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.time.timeblock.TimeBlockHistory;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class TimeBlockHistoryDaoSpringOjbImpl extends PlatformAwareDaoBaseOjb implements TimeBlockHistoryDao {

	private static final Logger LOG = Logger.getLogger(TimeBlockHistoryDaoSpringOjbImpl.class);

	public void saveOrUpdate(TimeBlockHistory timeBlockHistory) {
		this.getPersistenceBrokerTemplate().store(timeBlockHistory);
	}

	public void saveOrUpdate(List<TimeBlockHistory> timeBlockHistoryList) {
		if (timeBlockHistoryList != null) {
			for (TimeBlockHistory timeBlockHistory : timeBlockHistoryList) {
				this.getPersistenceBrokerTemplate().store(timeBlockHistory);
			}
		}
	}

    @Override
    public TimeBlockHistory getTimeBlockHistoryByTkTimeBlockId(String tkTimeBlockId) {
        Criteria currentRecordCriteria = new Criteria();
		currentRecordCriteria.addEqualTo("tkTimeBlockId", tkTimeBlockId);
		Query query = QueryFactory.newQuery(TimeBlockHistory.class, currentRecordCriteria);

		return (TimeBlockHistory)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }
}
