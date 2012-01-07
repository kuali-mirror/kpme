package org.kuali.hr.time.timeblock.service;

import java.util.List;

import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.TimeBlockHistory;

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
    public TimeBlockHistory getTimeBlockHistoryByTkTimeBlockId(String tkTimeBlockId);
    /**
	 * Add Time Block history Details to Time Block History using timeHourDetails
	 * of given time block
	 * @param timeBlockHistory
	 * @param timeBlock
	 */
    public void addTimeBlockHistoryDetails(TimeBlockHistory timeBlockHistory, TimeBlock timeBlock);
}
