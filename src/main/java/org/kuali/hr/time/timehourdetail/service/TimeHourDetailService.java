package org.kuali.hr.time.timehourdetail.service;

import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.TimeHourDetail;


public interface TimeHourDetailService{

	public TimeHourDetail getTimeHourDetail(String timeHourDetailId);
	public TimeHourDetail saveTimeHourDetail(TimeBlock timeBlock);
    public void removeTimeHourDetails(Long timeBlockId);

}
