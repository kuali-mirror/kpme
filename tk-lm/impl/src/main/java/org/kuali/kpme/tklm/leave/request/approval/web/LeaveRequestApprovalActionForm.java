/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.tklm.leave.request.approval.web;

import java.util.List;

import org.kuali.kpme.core.api.calendar.entry.CalendarEntryContract;
import org.kuali.kpme.core.calendar.entry.CalendarEntry;
import org.kuali.kpme.tklm.common.ApprovalForm;
import org.kuali.kpme.tklm.leave.calendar.LeaveCalendar;
import org.kuali.kpme.tklm.leave.calendar.LeaveRequestCalendar;

public class LeaveRequestApprovalActionForm extends ApprovalForm {
	
	private static final long serialVersionUID = 1L;
	
	private String principalId;
	private CalendarEntry calendarEntry;
	private LeaveRequestCalendar leaveRequestCalendar;
	private String prevDocumentId;
	private String nextDocumentId;
	private String hrCalendarEntryId;
    private String prevHrCalendarEntryId;
    private String nextHrCalendarEntryId;
    private String actionList;
    private String action;
    private String leaveRequestString;
    private String selectedCalendarType;
    private String beginDateString;
    private String endDateString;
    private String navigationAction;
    private String reason;
    private List<String> principalIds;
    private String selectedPrincipal;
    
	public String getSelectedPrincipal() {
		return selectedPrincipal;
	}
	public void setSelectedPrincipal(String selectedPrincipal) {
		this.selectedPrincipal = selectedPrincipal;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getNavigationAction() {
		return navigationAction;
	}
	public void setNavigationAction(String navigationAction) {
		this.navigationAction = navigationAction;
	}
	public String getBeginDateString() {
		return beginDateString;
	}
	public void setBeginDateString(String beginDateString) {
		this.beginDateString = beginDateString;
	}
	public String getEndDateString() {
		return endDateString;
	}
	public void setEndDateString(String endDateString) {
		this.endDateString = endDateString;
	}
	public String getSelectedCalendarType() {
		return selectedCalendarType;
	}
	public void setSelectedCalendarType(String selectedCalendarType) {
		this.selectedCalendarType = selectedCalendarType;
	}
	public String getPrincipalId() {
		return principalId;
	}
	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}
	public CalendarEntry getCalendarEntry() {
		return calendarEntry;
	}
	public void setCalendarEntry(CalendarEntry calendarEntry) {
		this.calendarEntry = calendarEntry;
	}
	public LeaveRequestCalendar getLeaveRequestCalendar() {
		return leaveRequestCalendar;
	}
	public void setLeaveRequestCalendar(LeaveRequestCalendar leaveRequestCalendar) {
		this.leaveRequestCalendar = leaveRequestCalendar;
	}
	public String getPrevDocumentId() {
		return prevDocumentId;
	}
	public void setPrevDocumentId(String prevDocumentId) {
		this.prevDocumentId = prevDocumentId;
	}
	public String getNextDocumentId() {
		return nextDocumentId;
	}
	public void setNextDocumentId(String nextDocumentId) {
		this.nextDocumentId = nextDocumentId;
	}
	public String getPrevHrCalendarEntryId() {
		return prevHrCalendarEntryId;
	}
	public void setPrevHrCalendarEntryId(String prevHrCalendarEntryId) {
		this.prevHrCalendarEntryId = prevHrCalendarEntryId;
	}
	public String getNextHrCalendarEntryId() {
		return nextHrCalendarEntryId;
	}
	public void setNextHrCalendarEntryId(String nextHrCalendarEntryId) {
		this.nextHrCalendarEntryId = nextHrCalendarEntryId;
	}
	public String getHrCalendarEntryId() {
		return hrCalendarEntryId;
	}
	public void setHrCalendarEntryId(String hrCalendarEntryId) {
		this.hrCalendarEntryId = hrCalendarEntryId;
	}
	public String getActionList() {
		return actionList;
	}
	public void setActionList(String actionList) {
		this.actionList = actionList;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getLeaveRequestString() {
		return leaveRequestString;
	}
	public void setLeaveRequestString(String leaveRequestString) {
		this.leaveRequestString = leaveRequestString;
	}
	public List<String> getPrincipalIds() {
		return principalIds;
	}
	public void setPrincipalIds(List<String> principalIds) {
		this.principalIds = principalIds;
	}
}
