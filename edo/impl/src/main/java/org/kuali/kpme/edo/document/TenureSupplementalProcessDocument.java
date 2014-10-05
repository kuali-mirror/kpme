package org.kuali.kpme.edo.document;

import org.kuali.kpme.edo.workflow.EdoDossierDocumentInfoBo;
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

    private EdoDossierDocumentInfoBo documentHeader;

    public TenureSupplementalProcessDocument(EdoDossierDocumentInfoBo documentHeader) {
        this.documentHeader = documentHeader;
    }

    public EdoDossierDocumentInfoBo getDocumentHeader() {
        return documentHeader;
    }

    public void setDocumentHeader(EdoDossierDocumentInfoBo documentHeader) {
        this.documentHeader = documentHeader;
    }

  
}
