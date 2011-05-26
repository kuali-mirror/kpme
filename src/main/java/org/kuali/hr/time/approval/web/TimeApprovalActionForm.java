package org.kuali.hr.time.approval.web;

import org.kuali.hr.time.base.web.TkForm;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimeApprovalActionForm extends TkForm {

    /**
     *
     */
    private static final long serialVersionUID = -173408280988754540L;
    
    private String name;
    private List<ApprovalTimeSummaryRow> approvalTimeSummaryRows = new ArrayList<ApprovalTimeSummaryRow>();
    private Date payBeginDate;
    private Date payEndDate;
    private List<String> payCalendarLabels = new ArrayList<String>();
    private List<TimesheetDocumentHeader> docHeaders = new ArrayList<TimesheetDocumentHeader>();
    private String lastDocumentId;
    private String outputString;
    private String searchField;
    private String term;
    
	public String getName() { 
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPayBeginDate(Date payBeginDate) {
		this.payBeginDate = payBeginDate;
	}
	public Date getPayBeginDate() {
		return payBeginDate;
	}
	public void setPayEndDate(Date payEndDate) {
		this.payEndDate = payEndDate;
	}
	public Date getPayEndDate() {
		return payEndDate;
	}
	public void setApprovalTimeSummaryRows(List<ApprovalTimeSummaryRow> approvalTimeSummaryRows) {
		this.approvalTimeSummaryRows = approvalTimeSummaryRows;
	}
	public List<ApprovalTimeSummaryRow> getApprovalTimeSummaryRows() {
		return approvalTimeSummaryRows;
	}
	public void setPayCalendarLabels(List<String> payCalendarLabels) {
		this.payCalendarLabels = payCalendarLabels;
	}
	public List<String> getPayCalendarLabels() {
		return payCalendarLabels;
	}

    public List<TimesheetDocumentHeader> getDocHeaders() {
        return docHeaders;
    }

    public void setDocHeaders(List<TimesheetDocumentHeader> docHeaders) {
        this.docHeaders = docHeaders;
    }

    public String getLastDocumentId() {
        return lastDocumentId;
    }

    public void setLastDocumentId(String lastDocumentId) {
        this.lastDocumentId = lastDocumentId;
    }

    public String getOutputString() {
        return outputString;
    }

    public void setOutputString(String outputString) {
        this.outputString = outputString;
    }

    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }
}
