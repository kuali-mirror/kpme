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
package org.kuali.kpme.tklm.time.timeblock;

import java.util.Date;

import org.kuali.kpme.tklm.time.timehourdetail.TimeHourDetail;
import org.kuali.rice.kim.api.identity.Person;

public class TimeBlockHistoryDetail extends TimeHourDetail{

	private static final long serialVersionUID = 1L;
	private String tkTimeBlockHistoryDetailId;
	private String tkTimeBlockHistoryId;
	
	private TimeBlockHistory timeBlockHistory;
	
	private transient Person principal;
	private transient Person userPrincipal;

	public String getTkTimeBlockHistoryDetailId() {
		return tkTimeBlockHistoryDetailId;
	}

	public void setTkTimeBlockHistoryDetailId(String tkTimeBlockHistoryDetailId) {
		this.tkTimeBlockHistoryDetailId = tkTimeBlockHistoryDetailId;
	}

	public String getTkTimeBlockHistoryId() {
		return tkTimeBlockHistoryId;
	}

	public void setTkTimeBlockHistoryId(String tkTimeBlockHistoryId) {
		this.tkTimeBlockHistoryId = tkTimeBlockHistoryId;
	}

		
	public TimeBlockHistoryDetail(TimeHourDetail thd) {
		this.setEarnCode(thd.getEarnCode());
		this.setAmount(thd.getAmount());
		this.setHours(thd.getHours());
	}
	
    protected TimeBlockHistoryDetail(TimeBlockHistoryDetail t) {
        this.tkTimeBlockHistoryDetailId = t.tkTimeBlockHistoryDetailId;
        this.tkTimeBlockHistoryId = t.tkTimeBlockHistoryId;
        this.setEarnCode(t.getEarnCode());
        this.setHours(t.getHours());
        this.setAmount(t.getAmount());
    }

    public TimeBlockHistoryDetail copy() {
        return new TimeBlockHistoryDetail(this);
    }
    public TimeBlockHistoryDetail() {
    }

	public TimeBlockHistory getTimeBlockHistory() {
		return timeBlockHistory;
	}

	public void setTimeBlockHistory(TimeBlockHistory timeBlockHistory) {
		this.timeBlockHistory = timeBlockHistory;
	}

	public Person getPrincipal() {
		return principal;
	}

	public void setPrincipal(Person principal) {
		this.principal = principal;
	}

	public Person getUserPrincipal() {
		return userPrincipal;
	}

	public void setUserPrincipal(Person userPrincipal) {
		this.userPrincipal = userPrincipal;
	}

	public Date getBeginDate() {
		return timeBlockHistory.getBeginDate();
	}



}
