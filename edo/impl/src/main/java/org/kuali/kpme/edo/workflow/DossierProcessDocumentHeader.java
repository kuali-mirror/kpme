package org.kuali.kpme.edo.workflow;

import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

public class DossierProcessDocumentHeader extends PersistableBusinessObjectBase {

    private static final long serialVersionUID = 5073307260441401533L;
    private String documentId;
    private String principalId;
    private Integer dossierId;
    private String documentStatus;
    private String documentTypeName;

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

    public Integer getDossierId() {
        return dossierId;
    }

    public void setDossierId(Integer dossierId) {
        this.dossierId = dossierId;
    }

    public String getDocumentStatus() {
        return documentStatus;
    }

    public void setDocumentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
    }

    public String getDocumentTypeName() {
        return documentTypeName;
    }

    public void setDocumentTypeName(String documentTypeName) {
        this.documentTypeName = documentTypeName;
    }
}
