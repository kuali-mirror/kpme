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
import java.util.LinkedHashMap;

import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;



public class WorkScheduleAssignment extends PersistableBusinessObjectBase {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Long hrWorkScheduleAssignmentId;
	private Long hrWorkSchedule;
	private String dept;
	private Long workArea;
	private String principalId;
	private Date effectiveDate;
	private Boolean active;
	private Timestamp timestamp;
	private String userPrincipalId;

	public Long getHrWorkScheduleAssignmentId() {
		return hrWorkScheduleAssignmentId;
	}


	public void setHrWorkScheduleAssignmentId(Long hrWorkScheduleAssignmentId) {
		this.hrWorkScheduleAssignmentId = hrWorkScheduleAssignmentId;
	}


	public Long getHrWorkSchedule() {
		return hrWorkSchedule;
	}


	public void setHrWorkSchedule(Long hrWorkSchedule) {
		this.hrWorkSchedule = hrWorkSchedule;
	}


	public String getDept() {
		return dept;
	}


	public void setDept(String dept) {
		this.dept = dept;
	}


	public Long getWorkArea() {
		return workArea;
	}


	public void setWorkArea(Long workArea) {
		this.workArea = workArea;
	}


	public String getPrincipalId() {
		return principalId;
	}


	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}

	public Boolean getActive() {
		return active;
	}


	public void setActive(Boolean active) {
		this.active = active;
	}


	public Timestamp getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}


	public String getUserPrincipalId() {
		return userPrincipalId;
	}


	public void setUserPrincipalId(String userPrincipalId) {
		this.userPrincipalId = userPrincipalId;
	}


	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}


	public Date getEffectiveDate() {
		return effectiveDate;
	}

}
