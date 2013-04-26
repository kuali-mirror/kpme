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
package org.kuali.kpme.tklm.time.timeblock.service;

import java.util.ArrayList;
import java.util.List;

import org.kuali.kpme.tklm.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.time.timeblock.TimeBlockHistory;
import org.kuali.kpme.tklm.time.timeblock.TimeBlockHistoryDetail;
import org.kuali.kpme.tklm.time.timeblock.TimeHourDetail;
import org.kuali.kpme.tklm.time.timeblock.dao.TimeBlockHistoryDao;

public class TimeBlockHistoryServiceImpl implements TimeBlockHistoryService {

    private TimeBlockHistoryDao timeBlockHistoryDao;

	public void saveTimeBlockHistory(TimeBlockHistory tbh) {
		timeBlockHistoryDao.saveOrUpdate(tbh);
	}

	public List<TimeBlockHistory> saveTimeBlockHistoryList(List<TimeBlockHistory> tbhs) {
		return tbhs;
	}

	public void setTimeBlockHistoryDao(TimeBlockHistoryDao timeBlockHistoryDao) {
		this.timeBlockHistoryDao = timeBlockHistoryDao;
	}

    public TimeBlockHistory getTimeBlockHistoryByTkTimeBlockId(String tkTimeBlockId) {
        return timeBlockHistoryDao.getTimeBlockHistoryByTkTimeBlockId(tkTimeBlockId);
    }
    
    public void addTimeBlockHistoryDetails(TimeBlockHistory timeBlockHistory, TimeBlock timeBlock) {
      List<TimeHourDetail> details = timeBlock.getTimeHourDetails();
      if(!details.isEmpty()) {
      	List<TimeBlockHistoryDetail> tbhds = new ArrayList<TimeBlockHistoryDetail>();
      	for(TimeHourDetail thd : details) {
      		TimeBlockHistoryDetail tbhd = new TimeBlockHistoryDetail(thd);
      		tbhds.add(tbhd);
      	}
      	timeBlockHistory.setTimeBlockHistoryDetails(tbhds);
      }
    }

}
