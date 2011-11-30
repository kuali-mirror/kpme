package org.kuali.hr.time.timehourdetail.dao;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.time.timeblock.TimeHourDetail;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

import java.util.List;

public class TimeHourDetailDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements TimeHourDetailDao {

	private static final Logger LOG = Logger.getLogger(TimeHourDetailDaoSpringOjbImpl.class);

	@Override
	public void saveOrUpdate(TimeHourDetail timeHourDetail) {
		this.getPersistenceBrokerTemplate().store(timeHourDetail);
	}

	@Override
	public void saveOrUpdate(List<TimeHourDetail> timeHourDetails) {
		if (timeHourDetails != null) {
			for (TimeHourDetail timeHourDetail : timeHourDetails) {
				this.getPersistenceBrokerTemplate().store(timeHourDetail);
			}
		}
	}

	@Override
	public TimeHourDetail getTimeHourDetail(String timeHourDetailId) {
		Criteria currentRecordCriteria = new Criteria();
		currentRecordCriteria.addEqualTo("timeHourDetailId", timeHourDetailId);

		return (TimeHourDetail)this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(TimeHourDetail.class, currentRecordCriteria));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TimeHourDetail> getTimeHourDetailsForTimeBlock(Long timeBlockId) {
		Criteria currentRecordCriteria = new Criteria();
		currentRecordCriteria.addEqualTo("tkTimeBlockId", timeBlockId);
		Query query = QueryFactory.newQuery(TimeHourDetail.class, currentRecordCriteria);
		return (List<TimeHourDetail>)this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
	}

    public void remove(Long timeBlockId) {
        Criteria removalCriteria = new Criteria();
        removalCriteria.addEqualTo("tkTimeBlockId", timeBlockId);

        this.getPersistenceBrokerTemplate().deleteByQuery(QueryFactory.newQuery(TimeHourDetail.class, removalCriteria));
    }


}
