package org.kuali.hr.time.timeblock.dao;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.time.timeblock.TimeBlockHistory;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

import java.util.List;

public class TimeBlockHistoryDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements TimeBlockHistoryDao {

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
    public TimeBlockHistory getTimeBlockHistoryByTkTimeBlockId(Long tkTimeBlockId) {
        Criteria currentRecordCriteria = new Criteria();
		currentRecordCriteria.addEqualTo("tkTimeBlockId", tkTimeBlockId);
		Query query = QueryFactory.newQuery(TimeBlockHistory.class, currentRecordCriteria);

		return (TimeBlockHistory)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }
}
