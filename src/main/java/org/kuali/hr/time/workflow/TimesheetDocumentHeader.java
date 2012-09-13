package org.kuali.hr.time.workflow;

import java.util.Date;

import org.kuali.hr.core.document.CalendarDocumentHeaderContract;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

public class TimesheetDocumentHeader extends PersistableBusinessObjectBase implements CalendarDocumentHeaderContract {

	private static final long serialVersionUID = 1L;
	private String documentId;
	private String principalId;
	private Date beginDate;
	private Date endDate;
	private String documentStatus;

	public TimesheetDocumentHeader() {
		
	}
	
	public TimesheetDocumentHeader(String documentId, String principalId, Date payBeginDate, Date payEndDate, String documentStatus) {
		this.documentId = documentId;
		this.principalId = principalId;
		this.beginDate = payBeginDate;
		this.endDate = payEndDate;
		this.documentStatus = documentStatus;
	}

    @Override
	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

    @Override
	public String getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}

    @Override
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

    @Override
	public String getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}

    @Override
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

}
