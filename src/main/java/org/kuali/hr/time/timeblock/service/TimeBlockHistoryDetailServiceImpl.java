package org.kuali.hr.time.timeblock.service;

import java.util.List;

import org.kuali.hr.time.timeblock.TimeBlockHistoryDetail;
import org.kuali.hr.time.timeblock.dao.TimeBlockHistoryDetailDao;

public class TimeBlockHistoryDetailServiceImpl implements TimeBlockHistoryDetailService {

	TimeBlockHistoryDetailDao timeBlockHistoryDetailDao;

	@Override
	public TimeBlockHistoryDetail getTimeBlockHistoryDetail(String timeBlockHistoryDetailId) {
		return timeBlockHistoryDetailDao.getTimeBlockHistoryDetail(timeBlockHistoryDetailId);
	}

	public TimeBlockHistoryDetail saveTimeBlockHistoryDetail(TimeBlockHistoryDetail tbhd) {

		timeBlockHistoryDetailDao.saveOrUpdate(tbhd);

		return tbhd;
	}

	public void setTimeBlockHistoryDetailDao(TimeBlockHistoryDetailDao timeBlockHistoryDetailDao) {
		this.timeBlockHistoryDetailDao = timeBlockHistoryDetailDao;
	}
	@Override
	public List<TimeBlockHistoryDetail> getTimeBlockHistoryDetailsForTimeBlockHistory(String timeBlockHistoryId) {
		return this.timeBlockHistoryDetailDao.getTimeBlockHistoryDetailsForTimeBlockHistory(timeBlockHistoryId);
	}

    public void removeTimeBlockHistoryDetails(Long timeBlockHistoryId) {
        this.timeBlockHistoryDetailDao.remove(timeBlockHistoryId);
    }
}

