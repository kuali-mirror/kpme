package org.kuali.hr.time.timeblock.service;

import java.util.List;

import org.kuali.hr.time.timeblock.TimeBlockHistory;

public interface TimeBlockHistoryService {

	public void saveTimeBlockHistory(TimeBlockHistory timeBlockHistory);

	public List<TimeBlockHistory> saveTimeBlockHistoryList(List<TimeBlockHistory> timeBlockHistories);
}
