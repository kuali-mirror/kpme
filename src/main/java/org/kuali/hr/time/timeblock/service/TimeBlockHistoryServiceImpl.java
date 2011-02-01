package org.kuali.hr.time.timeblock.service;

import java.util.List;

import org.kuali.hr.time.timeblock.TimeBlockHistory;
import org.kuali.hr.time.timeblock.dao.TimeBlockHistoryDao;
import org.kuali.hr.time.util.TKContext;

public class TimeBlockHistoryServiceImpl implements TimeBlockHistoryService {

	private TimeBlockHistoryDao timeBlockHistoryDao;

	public void saveTimeBlockHistory(TimeBlockHistory tbh) {
		timeBlockHistoryDao.saveOrUpdate(tbh);
	}
	
	public List<TimeBlockHistory> saveTimeBlockHistoryList(List<TimeBlockHistory> tbhs) {
		return tbhs;
	}
	
	public void setTimeBlockHistoryDao(TimeBlockHistoryDao timeBlockHistoryDao) {
		this.timeBlockHistoryDao = timeBlockHistoryDao;
	}

}
