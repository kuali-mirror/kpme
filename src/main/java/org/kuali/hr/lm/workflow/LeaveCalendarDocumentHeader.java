package org.kuali.hr.lm.workflow;

import java.util.Date;

import org.kuali.hr.core.document.CalendarDocumentHeaderContract;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

public class LeaveCalendarDocumentHeader extends PersistableBusinessObjectBase implements CalendarDocumentHeaderContract {

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
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
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

    @Override
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date payEndDate) {
        this.endDate = payEndDate;
    }

    @Override
    public String getPrincipalId() {
        return principalId;
    }

    public void setPrincipalId(String principalId) {
        this.principalId = principalId;
    }
}
