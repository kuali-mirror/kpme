package org.kuali.hr.time.timeblock.service;

import java.sql.Timestamp;

import org.kuali.hr.time.clock.web.ClockActionForm;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.TimeBlockHistory;
import org.kuali.hr.time.timeblock.dao.TimeBlockHistoryDao;
import org.kuali.hr.time.util.TKContext;

public class TimeBlockHistoryServiceImpl implements TimeBlockHistoryService {

	private TimeBlockHistoryDao timeBlockHistoryDao;

	public void saveTimeBlockHistory(ClockActionForm form) {
		
		TimeBlock tb = form.getTimeBlock();
		TimeBlockHistory tbs = new TimeBlockHistory(tb);
		
		// TODO: need to handle the delete action
		tbs.setActionHistory(form.getClockLog().getClockAction());
		tbs.setModifiedByPrincipalId(TKContext.getUser().getPrincipalId());
		tbs.setTimestampModified(new Timestamp(System.currentTimeMillis()));
		
		timeBlockHistoryDao.saveOrUpdate(tbs);
	}

	public void setTimeBlockHistoryDao(TimeBlockHistoryDao timeBlockHistoryDao) {
		this.timeBlockHistoryDao = timeBlockHistoryDao;
	}

}
