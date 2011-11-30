package org.kuali.hr.time.timeblock.service;

import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.TimeBlockHistory;
import org.kuali.hr.time.timeblock.TimeBlockHistoryDetail;
import org.kuali.hr.time.timeblock.TimeHourDetail;
import org.kuali.hr.time.timeblock.dao.TimeBlockHistoryDao;

import java.util.ArrayList;
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
    
    public void addTimeBlockHistoryDetails(TimeBlockHistory timeBlockHistory, TimeBlock timeBlock) {
      List<TimeHourDetail> details = timeBlock.getTimeHourDetails();
      if(!details.isEmpty()) {
      	List<TimeBlockHistoryDetail> tbhds = new ArrayList<TimeBlockHistoryDetail>();
      	for(TimeHourDetail thd : details) {
      		TimeBlockHistoryDetail tbhd = new TimeBlockHistoryDetail(thd);
      		tbhds.add(tbhd);
      	}
      	timeBlockHistory.setTimeBlockHistoryDetails(tbhds);
      }
    }

}
