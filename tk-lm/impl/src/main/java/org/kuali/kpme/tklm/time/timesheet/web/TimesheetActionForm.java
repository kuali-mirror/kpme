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
package org.kuali.kpme.tklm.time.timesheet.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.kuali.kpme.tklm.common.CalendarForm;
import org.kuali.kpme.tklm.leave.transfer.BalanceTransfer;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;

public class TimesheetActionForm extends CalendarForm {

	private static final long serialVersionUID = -1323339639503231411L;

	private TimesheetDocument timesheetDocument;

	/**Job Number -> Formatted department earn codes  */
	private Map<Long,String> earnCodeDescriptions;
	/** String (concat(job number, work_area, task)) -> Formatted Assignment Descriptions */
	private Map<String,String>  assignmentDescriptions;
	private String selectedAssignment;
	private String selectedEarnCode;
	private String transferAccrualCategory;

	private String calNav;

    private List<BalanceTransfer> forfeitures;

	public TimesheetDocument getTimesheetDocument() {
		return timesheetDocument;
	}

	public void setTimesheetDocument(TimesheetDocument timesheetDocument) {
		this.timesheetDocument = timesheetDocument;
	}

	public Map<Long,String> getEarnCodeDescriptions() {
		return earnCodeDescriptions;
	}

	public void setEarnCodeDescriptions(Map<Long,String> earnCodeDescriptions) {
		this.earnCodeDescriptions = earnCodeDescriptions;
	}

	public Map<String,String>  getAssignmentDescriptions() {
		return assignmentDescriptions;
	}

	public void setAssignmentDescriptions(Map<String,String>  assignmentDescriptions) {
		this.assignmentDescriptions = assignmentDescriptions;
	}

	public String getSelectedAssignment() {
		return selectedAssignment;
	}

	public void setSelectedAssignment(String selectedAssignment) {
		this.selectedAssignment = selectedAssignment;
	}

	public String getSelectedEarnCode() {
		return selectedEarnCode;
	}

	public void setSelectedEarnCode(String selectedEarnCode) {
		this.selectedEarnCode = selectedEarnCode;
	}

	public String getCalNav() {
		return calNav;
	}

	public void setCalNav(String calNav) {
		this.calNav = calNav;
	}

	public Date getBeginPeriodDateTime() {
		return getCalendarEntry().getBeginPeriodDateTime();
	}

	public Date getEndPeriodDateTime() {
		return getCalendarEntry().getEndPeriodDateTime();
	}

	public String getTransferAccrualCategory() {
		return transferAccrualCategory;
	}

	public void setTransferAccrualCategory(String transferAccrualCategory) {
		this.transferAccrualCategory = transferAccrualCategory;
	}

	public List<BalanceTransfer> getForfeitures() {
		return forfeitures;
	}

	public void setForfeitures(List<BalanceTransfer> forfeitures) {
		this.forfeitures = forfeitures;
	}
}
