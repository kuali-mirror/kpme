package org.kuali.hr.time.timeblock.service;

import org.kuali.hr.time.timeblock.TimeBlockHistory;

import java.util.List;

public interface TimeBlockHistoryService {

	public void saveTimeBlockHistory(TimeBlockHistory timeBlockHistory);

	public List<TimeBlockHistory> saveTimeBlockHistoryList(List<TimeBlockHistory> timeBlockHistories);

    TimeBlockHistory getTimeBlockHistoryByTkTimeBlockId(Long tkTimeBlockId);
}
