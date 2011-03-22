package org.kuali.hr.time.timehourdetail.service;

import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.TimeHourDetail;


public interface TimeHourDetailService{
	/**
	 * Fetch TimeHourDetail based on id given
	 * @param timeHourDetailId
	 * @return
	 */
	public TimeHourDetail getTimeHourDetail(String timeHourDetailId);
	/**
	 * Save time hour detail for a given TimeBlock
	 * @param timeBlock
	 * @return
	 */
	public TimeHourDetail saveTimeHourDetail(TimeBlock timeBlock);
	/**
	 * Remove TimeHourDetail for the given id
	 * @param timeBlockId
	 */
    public void removeTimeHourDetails(Long timeBlockId);

}
