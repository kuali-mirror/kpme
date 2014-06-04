package org.kuali.kpme.edo.document;

import org.kuali.kpme.edo.workflow.DossierProcessDocumentHeader;

import java.io.Serializable;

public class TenureProcessDocument implements Serializable, DossierProcessDocument {

    public static final String TENURE_PROCESS_DOCUMENT_TYPE = "TenureProcessDocument";

    private DossierProcessDocumentHeader documentHeader;

    public TenureProcessDocument(DossierProcessDocumentHeader documentHeader) {
        this.documentHeader = documentHeader;
    }

    public DossierProcessDocumentHeader getDocumentHeader() {
        return documentHeader;
    }

    public void setDocumentHeader(DossierProcessDocumentHeader documentHeader) {
        this.documentHeader = documentHeader;
    }
}
