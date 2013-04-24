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
package org.kuali.hr.tklm.time.timeblock;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.kuali.hr.tklm.time.service.base.TkServiceLocator;
import org.kuali.rice.kim.api.identity.Person;

public class TimeBlockHistory extends TimeBlock {

	/**
	 *
	 */
	private static final long serialVersionUID = 3943771766084238699L;

	private String tkTimeBlockHistoryId = null;
	private String actionHistory;
	private String modifiedByPrincipalId;
	private Timestamp timestampModified;
	private transient Person principal;
	private transient Person userPrincipal;
	private List<TimeBlockHistoryDetail> timeBlockHistoryDetails = new ArrayList<TimeBlockHistoryDetail>();

	public TimeBlockHistory() {
	}

	public TimeBlockHistory(TimeBlock tb) {
		this.setTkTimeBlockId(tb.getTkTimeBlockId());
		this.setDocumentId(tb.getDocumentId());
		this.setJobNumber(tb.getJobNumber());
		this.setWorkArea(tb.getWorkArea());
		this.setTask(tb.getTask());
		this.setEarnCode(tb.getEarnCode());
		this.setBeginTimestamp(tb.getBeginTimestamp());
		this.setEndTimestamp(tb.getEndTimestamp());
		this.setClockLogCreated(tb.getClockLogCreated());
		this.setHours(tb.getHours());
		this.setUserPrincipalId(tb.getUserPrincipalId());
		this.setPrincipalId(tb.getPrincipalId());
		this.setTimestamp(tb.getTimestamp());
		// add time block history details for this time block history
		TkServiceLocator.getTimeBlockHistoryService().addTimeBlockHistoryDetails(this, tb);
	}
	

	public String getTkTimeBlockHistoryId() {
		return tkTimeBlockHistoryId;
	}
	public void setTkTimeBlockHistoryId(String tkTimeBlockHistoryId) {
		this.tkTimeBlockHistoryId = tkTimeBlockHistoryId;
	}
	public String getActionHistory() {
		return actionHistory;
	}
	public void setActionHistory(String actionHistory) {
		this.actionHistory = actionHistory;
	}
	public String getModifiedByPrincipalId() {
		return modifiedByPrincipalId;
	}
	public void setModifiedByPrincipalId(String modifiedByPrincipalId) {
		this.modifiedByPrincipalId = modifiedByPrincipalId;
	}

	public Timestamp getTimestampModified() {
		return timestampModified;
	}

	public void setTimestampModified(Timestamp timestampModified) {
		this.timestampModified = timestampModified;
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

	public List<TimeBlockHistoryDetail> getTimeBlockHistoryDetails() {
		return timeBlockHistoryDetails;
	}

	public void setTimeBlockHistoryDetails(List<TimeBlockHistoryDetail> timeBlockHistoryDetails) {
		this.timeBlockHistoryDetails = timeBlockHistoryDetails;
	}
}
