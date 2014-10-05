package org.kuali.kpme.edo.document;

import org.kuali.kpme.edo.workflow.EdoDossierDocumentInfoBo;

import java.io.Serializable;

public class PromotionProcessDocument implements Serializable, DossierProcessDocument {

    public static final String PROMOTION_PROCESS_DOCUMENT_TYPE = "PromotionProcessDocument";

    private EdoDossierDocumentInfoBo documentHeader;

    public PromotionProcessDocument(EdoDossierDocumentInfoBo documentHeader) {
        this.documentHeader = documentHeader;
    }

    public EdoDossierDocumentInfoBo getDocumentHeader() {
        return documentHeader;
    }

    public void setDocumentHeader(EdoDossierDocumentInfoBo documentHeader) {
        this.documentHeader = documentHeader;
    }
}
