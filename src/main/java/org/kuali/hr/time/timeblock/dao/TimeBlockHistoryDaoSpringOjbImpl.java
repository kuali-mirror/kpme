package org.kuali.hr.time.timeblock.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.hr.time.timeblock.TimeBlockHistory;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

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
}
