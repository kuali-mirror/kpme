package org.kuali.hr.time.approval.web;

import org.kuali.hr.time.base.web.TkForm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimeApprovalActionForm extends TkForm {

    public static final String ORDER_BY_PRINCIPAL = "principalId";
    public static final String ORDER_BY_DOCID = "documentId";

    private static final long serialVersionUID = -173408280988754540L;

    private String name;
    private Date payBeginDate;
    private Date payEndDate;
    private List<String> payCalendarLabels = new ArrayList<String>();
    private List<ApprovalTimeSummaryRow> approvalRows = new ArrayList<ApprovalTimeSummaryRow>();
    private String lastDocumentId;

    /** Used for ajax dynamic row updating */
    private String outputString;

    private String searchField;
    private String term;

    private int rows = 5;
    private String orderBy = TimeApprovalActionForm.ORDER_BY_PRINCIPAL;
    private boolean ascending = true;

    /**
     * Gets the name of the user that this row represents.
     * @return String representing the users name.
     */
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

	public void setPayCalendarLabels(List<String> payCalendarLabels) {
		this.payCalendarLabels = payCalendarLabels;
	}
	public List<String> getPayCalendarLabels() {
		return payCalendarLabels;
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

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public List<ApprovalTimeSummaryRow> getApprovalRows() {
        return approvalRows;
    }

    public void setApprovalRows(List<ApprovalTimeSummaryRow> approvalRows) {
        this.approvalRows = approvalRows;
    }

    public boolean isAscending() {
        return ascending;
    }

    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }
}
