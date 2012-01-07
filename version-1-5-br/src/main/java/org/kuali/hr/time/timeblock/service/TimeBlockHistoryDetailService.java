package org.kuali.hr.time.timeblock.service;

import java.util.List;

import org.kuali.hr.time.timeblock.TimeBlockHistoryDetail;


public interface TimeBlockHistoryDetailService{
	/**
	 * Fetch TimeBlockHistoryDetail based on id given
	 * @param timeBlockHistoryDetailId
	 * @return
	 */
	public TimeBlockHistoryDetail getTimeBlockHistoryDetail(String timeBlockHistoryDetailId);
	/**
	 * Save time block history detail
	 * @param timeBlockHistoryDetail
	 * @return
	 */
	public TimeBlockHistoryDetail saveTimeBlockHistoryDetail(TimeBlockHistoryDetail timeBlockHistoryDetail);
	/**
	 * Remove TimeBlockHistoryDetail for the given id
	 * @param timeBlockHistoryId
	 */
    public void removeTimeBlockHistoryDetails(Long timeBlockHistoryId);
    
	/**
	 * Fetch List of TimeBlockHistoryDetail based on time block history id
	 * @param timeBlockHistoryId
	 * @return
	 */
    public List<TimeBlockHistoryDetail> getTimeBlockHistoryDetailsForTimeBlockHistory(String timeBlockHistoryId);
	

}
