package org.kuali.hr.time.timehourdetail.dao;

import java.util.List;

import org.kuali.hr.time.timeblock.TimeHourDetail;


public interface TimeHourDetailDao{

	public TimeHourDetail getTimeHourDetail(String timeHourDetailId);

	public void saveOrUpdate(List<TimeHourDetail> timeHourDetails);

	public void saveOrUpdate(TimeHourDetail timeHourDetail);

    public void remove(String timeBlockId);
    
    public List<TimeHourDetail> getTimeHourDetailsForTimeBlock(String timeBlockId);
}
