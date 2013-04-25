/**
 * Copyright 2004-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.hr.tklm.time.timehourdetail.service;

import java.util.List;

import org.kuali.hr.tklm.time.timeblock.TimeBlock;
import org.kuali.hr.tklm.time.timeblock.TimeHourDetail;
import org.kuali.hr.tklm.time.timehourdetail.dao.TimeHourDetailDao;

public class TimeHourDetailServiceImpl implements TimeHourDetailService {

	TimeHourDetailDao timeHourDetailDao;

	@Override
	public TimeHourDetail getTimeHourDetail(String timeHourDetailId) {
		return timeHourDetailDao.getTimeHourDetail(timeHourDetailId);
	}

	@Override
	public TimeHourDetail saveTimeHourDetail(TimeBlock tb) {

		TimeHourDetail td = new TimeHourDetail();

		td.setTkTimeBlockId(tb.getTkTimeBlockId());
		td.setEarnCode(tb.getEarnCode());
		td.setHours(tb.getHours());
		tb.setAmount(tb.getAmount());

		timeHourDetailDao.saveOrUpdate(td);

		return td;
	}

	public void setTimeHourDetailDao(TimeHourDetailDao timeHourDetailDao) {
		this.timeHourDetailDao = timeHourDetailDao;
	}
	@Override
	public List<TimeHourDetail> getTimeHourDetailsForTimeBlock(String timeBlockId) {
		return this.timeHourDetailDao.getTimeHourDetailsForTimeBlock(timeBlockId);
	}

    public void removeTimeHourDetails(String timeBlockId) {
        this.timeHourDetailDao.remove(timeBlockId);
    }
    
    @Override
    public void removeTimeHourDetail(String timeHourDetailId) {
        timeHourDetailDao.removeById(timeHourDetailId);
    }
}
