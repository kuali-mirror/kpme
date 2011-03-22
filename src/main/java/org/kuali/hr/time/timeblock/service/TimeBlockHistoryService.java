package org.kuali.hr.time.timeblock.service;

import org.kuali.hr.time.timeblock.TimeBlockHistory;

import java.util.List;

public interface TimeBlockHistoryService {
	/**
	 * Save TimeBlock history
	 * @param timeBlockHistory
	 */
	public void saveTimeBlockHistory(TimeBlockHistory timeBlockHistory);
	/**
	 * Save a List of TimeBlockHistory objects
	 * @param timeBlockHistories
	 * @return
	 */
	public List<TimeBlockHistory> saveTimeBlockHistoryList(List<TimeBlockHistory> timeBlockHistories);
	/**
	 * Fetch a TimeBlockHistory by a particular id
	 * @param tkTimeBlockId
	 * @return
	 */
    public TimeBlockHistory getTimeBlockHistoryByTkTimeBlockId(Long tkTimeBlockId);
}
