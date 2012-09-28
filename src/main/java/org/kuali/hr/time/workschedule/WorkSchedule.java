/**
 * Copyright 2004-2012 The Kuali Foundation
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
package org.kuali.hr.time.workschedule;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

public class WorkSchedule extends PersistableBusinessObjectBase {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Long hrWorkScheduleId;
    private Long hrWorkSchedule;

	private String workScheduleDesc;
	private Date effectiveDate;
	private Timestamp timestamp;
	private boolean active;
	private String userPrincipalId;
    private String earnGroup;

    public Long getHrWorkSchedule() {
        return hrWorkSchedule;
    }

    public void setHrWorkSchedule(Long hrWorkSchedule) {
        this.hrWorkSchedule = hrWorkSchedule;
    }

    public String getEarnGroup() {
        return earnGroup;
    }

    public void setEarnGroup(String earnGroup) {
        this.earnGroup = earnGroup;
    }

    public String getUserPrincipalId() {
		return userPrincipalId;
	}


	public void setUserPrincipalId(String userPrincipalId) {
		this.userPrincipalId = userPrincipalId;
	}


	private List<WorkScheduleEntry> workScheduleEntries = new  LinkedList<WorkScheduleEntry>();



	public List<WorkScheduleEntry> getWorkScheduleEntries() {
		return workScheduleEntries;
	}


	public void setWorkScheduleEntries(List<WorkScheduleEntry> workScheduleEntries) {
		this.workScheduleEntries = workScheduleEntries;
	}

	public String getWorkScheduleDesc() {
		return workScheduleDesc;
	}

	public void setWorkScheduleDesc(String workScheduleDesc) {
		this.workScheduleDesc = workScheduleDesc;
	}


	public Date getEffectiveDate() {
		return effectiveDate;
	}


	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}


	public Timestamp getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}


	public Long getHrWorkScheduleId() {
		return hrWorkScheduleId;
	}


	public void setHrWorkScheduleId(Long hrWorkScheduleId) {
		this.hrWorkScheduleId = hrWorkScheduleId;
	}
}
