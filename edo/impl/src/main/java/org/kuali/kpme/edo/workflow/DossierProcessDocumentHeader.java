package org.kuali.kpme.edo.workflow;

import org.kuali.kpme.edo.api.dossier.EdoDossier;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

public class DossierProcessDocumentHeader extends PersistableBusinessObjectBase {

    private static final long serialVersionUID = 5073307260441401533L;
    private String documentId;
    private String principalId;
    private String edoDossierId;
    private String documentStatus;
    private String documentTypeName;
    private EdoDossier edoDossierObj;

    public EdoDossier getEdoDossierObj() {
		return edoDossierObj;
	}

	public void setEdoDossierObj(EdoDossier edoDossierObj) {
		this.edoDossierObj = edoDossierObj;
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

    public String getEdoDossierId() {
		return edoDossierId;
	}

	public void setEdoDossierId(String edoDossierId) {
		this.edoDossierId = edoDossierId;
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
