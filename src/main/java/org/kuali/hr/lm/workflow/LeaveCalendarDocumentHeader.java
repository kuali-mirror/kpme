package org.kuali.hr.lm.workflow;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

import java.util.Date;
import java.util.LinkedHashMap;

public class LeaveCalendarDocumentHeader extends PersistableBusinessObjectBase {

    private String documentId;
    private String principalId;
    private Date beginDate;
    private Date endDate;
    private String documentStatus;

    public LeaveCalendarDocumentHeader() {

    }

    public LeaveCalendarDocumentHeader(String documentId, String principalId, Date beginDate, Date endDate, String documentStatus) {
		this.documentId = documentId;
		this.principalId = principalId;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.documentStatus = documentStatus;
	}

    @Override
    protected LinkedHashMap toStringMapper() {
        return null;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getDocumentStatus() {
        return documentStatus;
    }

    public void setDocumentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date payEndDate) {
        this.endDate = payEndDate;
    }

    public String getPrincipalId() {
        return principalId;
    }

    public void setPrincipalId(String principalId) {
        this.principalId = principalId;
    }
}
