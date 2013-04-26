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
package org.kuali.hr.tklm.time.timesheet.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.kuali.hr.core.calendar.CalendarEntry;
import org.kuali.hr.tklm.common.TkCommonCalendarForm;
import org.kuali.hr.tklm.leave.transfer.BalanceTransfer;
import org.kuali.hr.tklm.time.timesheet.TimesheetDocument;

public class TimesheetActionForm extends TkCommonCalendarForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 6938733178369007689L;
	private TimesheetDocument timesheetDocument;


	/**Job Number -> Formatted department earn codes  */
	private Map<Long,String> earnCodeDescriptions;
	/** String (concat(job number, work_area, task)) -> Formatted Assignment Descriptions */
	private Map<String,String>  assignmentDescriptions;
	private CalendarEntry payCalendarDates;
	private String selectedAssignment;
	private String selectedEarnCode;
	private String transferAccrualCategory;

	private String calNav;
	private String documentId;

	private Date beginPeriodDateTime;
	private Date endPeriodDateTime;

    private String prevDocumentId;
    private String nextDocumentId;
    private List<BalanceTransfer> forfeitures;

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		//setDocumentId("");
	}

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

	public CalendarEntry getPayCalendarDates() {
		return payCalendarDates;
	}

	public void setPayCalendarDates(CalendarEntry payCalendarDates) {
		this.payCalendarDates = payCalendarDates;
	}

	public String getCalNav() {
		return calNav;
	}

	public void setCalNav(String calNav) {
		this.calNav = calNav;
	}

	public Date getBeginPeriodDateTime() {
		return getPayCalendarDates().getBeginPeriodDateTime();
	}

	public Date getEndPeriodDateTime() {
		return getPayCalendarDates().getEndPeriodDateTime();
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
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
