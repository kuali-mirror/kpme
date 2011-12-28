package org.kuali.hr.time.timeblock.dao;

import org.kuali.hr.time.timeblock.TimeBlockHistory;

import java.util.List;

public interface TimeBlockHistoryDao {

	public void saveOrUpdate(TimeBlockHistory timeBlockHistory);
	public void saveOrUpdate(List<TimeBlockHistory> timeBlockHistoryList);

    TimeBlockHistory getTimeBlockHistoryByTkTimeBlockId(String tkTimeBlockId);
}
