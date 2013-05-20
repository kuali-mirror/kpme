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
package org.kuali.kpme.tklm.time.timeblock.dao;

import java.util.List;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.tklm.time.timeblock.TimeBlockHistoryDetail;
import org.kuali.kpme.tklm.time.timehourdetail.TimeHourDetail;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class TimeBlockHistoryDetailDaoOjbImpl extends PlatformAwareDaoBaseOjb implements TimeBlockHistoryDetailDao {

	@Override
	public void saveOrUpdate(TimeBlockHistoryDetail timeBlockHistoryDetail) {
		this.getPersistenceBrokerTemplate().store(timeBlockHistoryDetail);
	}

	@Override
	public void saveOrUpdate(List<TimeBlockHistoryDetail> timeBlockHistoryDetails) {
		if (timeBlockHistoryDetails != null) {
			for (TimeBlockHistoryDetail timeHourDetail : timeBlockHistoryDetails) {
				this.getPersistenceBrokerTemplate().store(timeHourDetail);
			}
		}
	}

	@Override
	public TimeBlockHistoryDetail getTimeBlockHistoryDetail(String timeBlockHistoryDetailId) {
		Criteria currentRecordCriteria = new Criteria();
		currentRecordCriteria.addEqualTo("timeBlockHistoryDetailId",
				timeBlockHistoryDetailId);

		return (TimeBlockHistoryDetail) this.getPersistenceBrokerTemplate()
				.getObjectByQuery(
						QueryFactory.newQuery(TimeBlockHistoryDetail.class,
								currentRecordCriteria));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TimeBlockHistoryDetail> getTimeBlockHistoryDetailsForTimeBlockHistory(String timeBlockHistoryId) {
		Criteria currentRecordCriteria = new Criteria();
		currentRecordCriteria.addEqualTo("tkTimeBlockHistoryId",
				timeBlockHistoryId);
		Query query = QueryFactory.newQuery(TimeHourDetail.class,
				currentRecordCriteria);
		return (List<TimeBlockHistoryDetail>) this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
	}

	public void remove(Long timeBlockHistoryId) {
		Criteria removalCriteria = new Criteria();
		removalCriteria.addEqualTo("tkTimeBlockHistoryId", timeBlockHistoryId);

		this.getPersistenceBrokerTemplate().deleteByQuery(
				QueryFactory.newQuery(TimeHourDetail.class, removalCriteria));
	}

}
