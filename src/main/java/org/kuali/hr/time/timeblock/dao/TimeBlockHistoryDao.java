package org.kuali.hr.time.timeblock.dao;

import java.util.List;

import org.kuali.hr.time.timeblock.TimeBlockHistory;

public interface TimeBlockHistoryDao {

	public void saveOrUpdate(TimeBlockHistory timeBlockHistory);
	public void saveOrUpdate(List<TimeBlockHistory> timeBlockHistoryList);

    TimeBlockHistory getTimeBlockHistoryByTkTimeBlockId(String tkTimeBlockId);
}
