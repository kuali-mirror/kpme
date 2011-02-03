package org.kuali.hr.time.timeblock.service;

import org.kuali.hr.time.timeblock.TimeBlockHistory;
import org.kuali.hr.time.timeblock.dao.TimeBlockHistoryDao;

import java.util.List;

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

    public TimeBlockHistory getTimeBlockHistoryByTkTimeBlockId(Long tkTimeBlockId) {
        return timeBlockHistoryDao.getTimeBlockHistoryByTkTimeBlockId(tkTimeBlockId);
    }

}
