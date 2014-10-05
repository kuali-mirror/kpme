package org.kuali.kpme.edo.document;

import org.kuali.kpme.edo.workflow.EdoDossierDocumentInfoBo;

import java.io.Serializable;

public class TenureProcessDocument implements Serializable, DossierProcessDocument {

    public static final String TENURE_PROCESS_DOCUMENT_TYPE = "TenureProcessDocument";

    private EdoDossierDocumentInfoBo documentHeader;

    public TenureProcessDocument(EdoDossierDocumentInfoBo documentHeader) {
        this.documentHeader = documentHeader;
    }

    public EdoDossierDocumentInfoBo getDocumentHeader() {
        return documentHeader;
    }

    public void setDocumentHeader(EdoDossierDocumentInfoBo documentHeader) {
        this.documentHeader = documentHeader;
    }
}
