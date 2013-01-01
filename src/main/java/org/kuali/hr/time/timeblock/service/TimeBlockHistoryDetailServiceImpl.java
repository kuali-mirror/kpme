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
package org.kuali.hr.time.timeblock.service;

import java.util.List;

import org.kuali.hr.time.timeblock.TimeBlockHistoryDetail;
import org.kuali.hr.time.timeblock.dao.TimeBlockHistoryDetailDao;

public class TimeBlockHistoryDetailServiceImpl implements TimeBlockHistoryDetailService {

	TimeBlockHistoryDetailDao timeBlockHistoryDetailDao;

	@Override
	public TimeBlockHistoryDetail getTimeBlockHistoryDetail(String timeBlockHistoryDetailId) {
		return timeBlockHistoryDetailDao.getTimeBlockHistoryDetail(timeBlockHistoryDetailId);
	}

	public TimeBlockHistoryDetail saveTimeBlockHistoryDetail(TimeBlockHistoryDetail tbhd) {

		timeBlockHistoryDetailDao.saveOrUpdate(tbhd);

		return tbhd;
	}

	public void setTimeBlockHistoryDetailDao(TimeBlockHistoryDetailDao timeBlockHistoryDetailDao) {
		this.timeBlockHistoryDetailDao = timeBlockHistoryDetailDao;
	}
	@Override
	public List<TimeBlockHistoryDetail> getTimeBlockHistoryDetailsForTimeBlockHistory(String timeBlockHistoryId) {
		return this.timeBlockHistoryDetailDao.getTimeBlockHistoryDetailsForTimeBlockHistory(timeBlockHistoryId);
	}

    public void removeTimeBlockHistoryDetails(Long timeBlockHistoryId) {
        this.timeBlockHistoryDetailDao.remove(timeBlockHistoryId);
    }
}

