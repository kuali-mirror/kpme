package org.kuali.hr.time.workflow;

import java.util.Date;
import java.util.LinkedHashMap;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class TimesheetDocumentHeader extends PersistableBusinessObjectBase {

	private static final long serialVersionUID = 1L;
	private String documentId;
	private String principalId;
	private Date payBeginDate;
	private Date payEndDate;
	private String documentStatus;
	private String objectId;
	private Long versionNumber;
	
	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public Long getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(long versionNumber) {
		this.versionNumber = versionNumber;
	}

	public TimesheetDocumentHeader() {
		
	}
	
	public TimesheetDocumentHeader(String documentId, String principalId, Date payBeginDate, Date payEndDate, String documentStatus) {
		this.documentId = documentId;
		this.principalId = principalId;
		this.payBeginDate = payBeginDate;
		this.payEndDate = payEndDate;
		this.documentStatus = documentStatus;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
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

	@Override
	protected LinkedHashMap toStringMapper() {
		// TODO Auto-generated method stub
		return null;
	}

}
