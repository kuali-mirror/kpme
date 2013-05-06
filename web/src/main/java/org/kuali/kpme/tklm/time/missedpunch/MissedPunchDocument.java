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
package org.kuali.kpme.tklm.time.missedpunch;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.kuali.rice.krad.document.TransactionalDocumentBase;

public class MissedPunchDocument extends TransactionalDocumentBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String principalId;
	private String clockAction;
	private Date actionDate;
	private Time actionTime;
    private String timesheetDocumentId;
	private String documentStatus;
	private String tkClockLogId;
	private Timestamp timestamp;
	private String ipAddress;
	
    /** Selection key matching what is used on the clock action GUI */
    private String assignment;
	
	public String getPrincipalId() {
		return principalId;
	}
	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}
	public String getClockAction() {
		return clockAction;
	}
	public void setClockAction(String clockAction) {
		this.clockAction = clockAction;
	}
	public Date getActionDate() {
		return actionDate;
	}
	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}
	public Time getActionTime() {
		return actionTime;
	}
	public void setActionTime(Time actionTime) {
		this.actionTime = actionTime;
	}
    public DateTime getActionDateTime() {
    	DateTime actionDateTime = null;
    	
    	if (actionDate != null && actionTime != null) {
	    	LocalDate localDate = new LocalDate(actionDate);
	    	LocalTime localTime = new LocalTime(actionTime);
	    	actionDateTime = localDate.toDateTime(localTime);
    	}
    	
    	return actionDateTime;
    }
	public String getTimesheetDocumentId() {
		return timesheetDocumentId;
	}
	public void setTimesheetDocumentId(String timesheetDocumentId) {
		this.timesheetDocumentId = timesheetDocumentId;
	}
	public String getDocumentStatus() {
		return documentStatus;
	}
	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}
	public String getTkClockLogId() {
		return tkClockLogId;
	}
	public void setTkClockLogId(String tkClockLogId) {
		this.tkClockLogId = tkClockLogId;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getAssignment() {
		return assignment;
	}
	public void setAssignment(String assignment) {
		this.assignment = assignment;
	}

//	@Override
//    public void prepareForSave(KualiDocumentEvent event) {
//        // Add clocklogs only if an MP doc is enroute or approved KPME-2185
//        // checking it for Approved status might be unneccessary since we create the clocklog after routing.
//		if(this.getTkClockLogId() == null && (this.getDocumentStatus() =="A" || this.getDocumentStatus() =="R")) {
//			TkServiceLocator.getMissedPunchService().addClockLogForMissedPunch(this);
//		}
//    }

}