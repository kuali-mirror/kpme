package org.kuali.hr.time.timeblock.dao;

import java.util.List;

import org.kuali.hr.time.timeblock.TimeBlockHistoryDetail;

public interface TimeBlockHistoryDetailDao {
	public TimeBlockHistoryDetail getTimeBlockHistoryDetail(String timeBlockHistoryDetailId);

	public void saveOrUpdate(List<TimeBlockHistoryDetail> timeBlockHistoryDetails);

	public void saveOrUpdate(TimeBlockHistoryDetail timeBlockHistoryDetail);

    public void remove(Long timeBlockHistoryId);
    
    public List<TimeBlockHistoryDetail> getTimeBlockHistoryDetailsForTimeBlockHistory(String timeBlockHistoryId);
}

