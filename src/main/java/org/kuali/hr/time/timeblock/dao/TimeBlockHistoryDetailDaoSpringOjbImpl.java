package org.kuali.hr.time.timeblock.dao;

import java.util.List;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.time.timeblock.TimeBlockHistoryDetail;
import org.kuali.hr.time.timeblock.TimeHourDetail;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class TimeBlockHistoryDetailDaoSpringOjbImpl extends PlatformAwareDaoBaseOjb implements TimeBlockHistoryDetailDao {

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
