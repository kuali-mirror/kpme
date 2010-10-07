package org.kuali.hr.time.timeblock.service;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import org.kuali.hr.time.clock.web.ClockActionForm;
import org.kuali.hr.time.detail.web.TimeDetailActionForm;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.TimeBlockHistory;
import org.kuali.hr.time.timeblock.dao.TimeBlockHistoryDao;
import org.kuali.hr.time.timesheet.web.TimesheetActionForm;
import org.kuali.hr.time.util.TKContext;

public class TimeBlockHistoryServiceImpl implements TimeBlockHistoryService {

	private TimeBlockHistoryDao timeBlockHistoryDao;

	public void saveTimeBlockHistory(TimesheetActionForm form) {
		
		TimeBlockHistory tbh;
		TimeBlock tb;
		
		// may have to handle different types of forms in the future
		if(form instanceof ClockActionForm) {
			ClockActionForm caf = (ClockActionForm) form;
			tb = caf.getTimeBlock();
			tbh = new TimeBlockHistory(tb);
			tbh.setActionHistory(caf.getClockLog().getClockAction());
		}
		else {
			TimeDetailActionForm tdaf = (TimeDetailActionForm) form;
			tb = tdaf.getTimeBlock();
			tbh = new TimeBlockHistory(tb);
			tbh.setActionHistory(tdaf.getClockAction());
		}
		
		tbh.setModifiedByPrincipalId(TKContext.getUser().getPrincipalId());
		tbh.setTimestampModified(new Timestamp(System.currentTimeMillis()));
		timeBlockHistoryDao.saveOrUpdate(tbh);
	}
	
	@Override
	public List<TimeBlockHistory> saveTimeBlockHistoryList(TimeDetailActionForm tdaf) {
		List<TimeBlockHistory> tbhs = new LinkedList<TimeBlockHistory>();
		for(TimeBlock tb : tdaf.getTimeBlockList()) {
			TimeBlockHistory tbh = new TimeBlockHistory(tb);
			tbh.setActionHistory(tdaf.getClockAction());
			tbh.setModifiedByPrincipalId(TKContext.getUser().getPrincipalId());
			tbh.setTimestampModified(new Timestamp(System.currentTimeMillis()));
			
			tbhs.add(tbh);
		}
		timeBlockHistoryDao.saveOrUpdate(tbhs);
		
		return tbhs;
	}
	
	public void setTimeBlockHistoryDao(TimeBlockHistoryDao timeBlockHistoryDao) {
		this.timeBlockHistoryDao = timeBlockHistoryDao;
	}

}
