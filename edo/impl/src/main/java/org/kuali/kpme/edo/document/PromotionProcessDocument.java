package org.kuali.kpme.edo.document;

import org.kuali.kpme.edo.workflow.DossierProcessDocumentHeader;

import java.io.Serializable;

public class PromotionProcessDocument implements Serializable, DossierProcessDocument {

    public static final String PROMOTION_PROCESS_DOCUMENT_TYPE = "PromotionProcessDocument";

    private DossierProcessDocumentHeader documentHeader;

    public PromotionProcessDocument(DossierProcessDocumentHeader documentHeader) {
        this.documentHeader = documentHeader;
    }

    public DossierProcessDocumentHeader getDocumentHeader() {
        return documentHeader;
    }

    public void setDocumentHeader(DossierProcessDocumentHeader documentHeader) {
        this.documentHeader = documentHeader;
    }
}
