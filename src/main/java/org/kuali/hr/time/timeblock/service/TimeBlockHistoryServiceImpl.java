package org.kuali.hr.time.timeblock.service;

import org.kuali.hr.time.timeblock.TimeBlockHistory;
import org.kuali.hr.time.timeblock.dao.TimeBlockHistoryDao;

public class TimeBlockHistoryServiceImpl implements TimeBlockHistoryService {

	private TimeBlockHistoryDao timeBlockHistoryDao;

	public void saveTimeBlockHistory(TimeBlockHistory timeBlockHistory) {
		timeBlockHistoryDao.saveOrUpdate(timeBlockHistory);
	}

	public void setTimeBlockHistoryDao(TimeBlockHistoryDao timeBlockHistoryDao) {
		this.timeBlockHistoryDao = timeBlockHistoryDao;
	}

}
