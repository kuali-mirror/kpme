package org.kuali.hr.time.timeblock.service;

import java.util.List;

import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.dao.TimeBlockDao;

public class TimeBlockServiceImpl implements TimeBlockService {

	private TimeBlockDao timeBlockDao;

	public void setTimeBlockDao(TimeBlockDao timeBlockDao) {
		this.timeBlockDao = timeBlockDao;
	}

	public void saveTimeBlock(TimeBlock timeBlock) {
		timeBlockDao.saveOrUpdate(timeBlock);
	}

	public List<TimeBlock> getTimeBlocksByPeriod(String principalId, String beginDate, String endDate) {
		return timeBlockDao.getTimeBlocksByPeriod(principalId, beginDate, endDate);
	}

	public void deleteTimeBlock(TimeBlock timeBlock) {
		timeBlockDao.deleteTimeBlock(timeBlock);

	}

	public TimeBlock getTimeBlock(String timeBlockId) {
		return timeBlockDao.getTimeBlock(timeBlockId);
	}

	public void saveTimeBlockList(List<TimeBlock> timeBlockList) {
		timeBlockDao.saveOrUpdate(timeBlockList);
	}

}
