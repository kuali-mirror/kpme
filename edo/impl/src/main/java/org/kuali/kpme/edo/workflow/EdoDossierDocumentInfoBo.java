package org.kuali.kpme.edo.workflow;

import org.kuali.kpme.edo.api.dossier.EdoDossier;
import org.kuali.kpme.edo.api.dossier.EdoDossierDocumentInfo;
import org.kuali.kpme.edo.api.dossier.EdoDossierDocumentInfoContract;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

public class EdoDossierDocumentInfoBo extends PersistableBusinessObjectBase implements EdoDossierDocumentInfoContract {

    private static final long serialVersionUID = 5073307260441401533L;
    private String edoDocumentId;
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

	public String getEdoDocumentId() {
        return edoDocumentId;
    }

    public void setEdoDocumentId(String edoDocumentId) {
        this.edoDocumentId = edoDocumentId;
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
    
    public static EdoDossierDocumentInfoBo from(EdoDossierDocumentInfo edoDossierDocumentInfo) {
        if (edoDossierDocumentInfo == null) {
            return null;
        }
        EdoDossierDocumentInfoBo edoDossierDocumentInfoBo = new EdoDossierDocumentInfoBo();
        
        edoDossierDocumentInfoBo.setEdoDossierId(edoDossierDocumentInfo.getEdoDossierId());
        edoDossierDocumentInfoBo.setEdoDocumentId(edoDossierDocumentInfo.getEdoDocumentId());
        edoDossierDocumentInfoBo.setPrincipalId(edoDossierDocumentInfo.getPrincipalId());
        edoDossierDocumentInfoBo.setDocumentStatus(edoDossierDocumentInfo.getDocumentStatus());
        edoDossierDocumentInfoBo.setDocumentTypeName(edoDossierDocumentInfo.getDocumentTypeName());
        edoDossierDocumentInfoBo.setVersionNumber(edoDossierDocumentInfo.getVersionNumber());
        edoDossierDocumentInfoBo.setObjectId(edoDossierDocumentInfo.getObjectId());
        
        return edoDossierDocumentInfoBo;
    }

    public static EdoDossierDocumentInfo to(EdoDossierDocumentInfoBo bo) {
        if (bo == null) {
            return null;
        }

        return EdoDossierDocumentInfo.Builder.create(bo).build();
    }
    
    
    public static final ModelObjectUtils.Transformer<EdoDossierDocumentInfoBo, EdoDossierDocumentInfo> toImmutable = new ModelObjectUtils.Transformer<EdoDossierDocumentInfoBo, EdoDossierDocumentInfo>() {
		public EdoDossierDocumentInfo transform(EdoDossierDocumentInfoBo input) {
			return EdoDossierDocumentInfoBo.to(input);
		};
	};

	public static final ModelObjectUtils.Transformer<EdoDossierDocumentInfo, EdoDossierDocumentInfoBo> toBo = new ModelObjectUtils.Transformer<EdoDossierDocumentInfo, EdoDossierDocumentInfoBo>() {
		public EdoDossierDocumentInfoBo transform(EdoDossierDocumentInfo input) {
			return EdoDossierDocumentInfoBo.from(input);
		};
	};
}
