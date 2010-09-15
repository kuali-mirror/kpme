package org.kuali.hr.time.workflow;

import java.util.Date;

import org.kuali.rice.kns.bo.DocumentHeader;

public class TimesheetDocumentHeader extends DocumentHeader {

	private static final long serialVersionUID = 1L;
	private Long documentId;
	private String principalId;
	private Date payBeginDate;
	private Date payEndDate;
	private String documentStatus;
	
	public TimesheetDocumentHeader() {
		
	}
	
	public TimesheetDocumentHeader(Long documentId, String principalId, Date payEndDate, String documentStatus) {
		this.documentId = documentId;
		this.principalId = principalId;
		this.payEndDate = payEndDate;
		this.documentStatus = documentStatus;
	}

	public Long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}

	public String getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}

	public Date getPayEndDate() {
		return payEndDate;
	}

	public void setPayEndDate(Date payEndDate) {
		this.payEndDate = payEndDate;
	}

	public String getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}

	public Date getPayBeginDate() {
		return payBeginDate;
	}

	public void setPayBeginDate(Date payBeginDate) {
		this.payBeginDate = payBeginDate;
	}

}
