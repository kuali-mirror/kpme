package org.kuali.hr.time.timehourdetail.dao;

import org.kuali.hr.time.timeblock.TimeHourDetail;

import java.util.List;


public interface TimeHourDetailDao{

	public TimeHourDetail getTimeHourDetail(String timeHourDetailId);

	public void saveOrUpdate(List<TimeHourDetail> timeHourDetails);

	public void saveOrUpdate(TimeHourDetail timeHourDetail);

    public void remove(Long timeBlockId);
}
