package org.kuali.kpme.edo.document;

import org.kuali.kpme.edo.workflow.DossierProcessDocumentHeader;
import java.io.Serializable;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 7/28/13
 * Time: 4:04 PM
 */
public class TenureSupplementalProcessDocument implements Serializable, SupplementalProcessDocument {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8898741021354995824L;

	public static final String TENURE_PROCESS_DOCUMENT_TYPE = "TenureSupplementalProcessDocument";

    private DossierProcessDocumentHeader documentHeader;

    public TenureSupplementalProcessDocument(DossierProcessDocumentHeader documentHeader) {
        this.documentHeader = documentHeader;
    }

    public DossierProcessDocumentHeader getDocumentHeader() {
        return documentHeader;
    }

    public void setDocumentHeader(DossierProcessDocumentHeader documentHeader) {
        this.documentHeader = documentHeader;
    }

  
}
