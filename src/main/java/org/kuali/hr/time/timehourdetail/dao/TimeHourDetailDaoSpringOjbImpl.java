package org.kuali.hr.time.timehourdetail.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.time.timeblock.TimeHourDetail;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

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

	
}
