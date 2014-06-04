package org.kuali.kpme.edo.workflow.service;


import org.kuali.kpme.edo.workflow.DossierProcessDocumentHeader;

import java.math.BigDecimal;
import java.util.List;

public interface DossierProcessDocumentHeaderService {
	
	public void saveOrUpdate(DossierProcessDocumentHeader documentHeader);
    public DossierProcessDocumentHeader getDossierProcessDocumentHeader(String documentId);
    public DossierProcessDocumentHeader getDossierProcessDocumentHeader(Integer dossierId);
    public List<DossierProcessDocumentHeader> getDossierProcessDocumentHeaderByDocType(Integer dossierId, String docTypeName);
    public BigDecimal getCurrentRouteLevel(Integer dossierId );
    public String getCurrentRouteLevelName(Integer dossierId );
    public boolean hasPendingSupplementalDocument(Integer dossierId);
    public List<DossierProcessDocumentHeader> getPendingSupplementalDocuments(Integer dossierId);
    
    
}
