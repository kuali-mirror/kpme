package org.kuali.hr.time.timeblock.service;

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

}
