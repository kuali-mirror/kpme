package org.kuali.kpme.edo.workflow.service;


import java.math.BigDecimal;
import java.util.List;

import org.kuali.kpme.edo.api.dossier.EdoDossierDocumentInfo;

public interface EdoDossierDocumentInfoService {
	
	public void saveOrUpdate(EdoDossierDocumentInfo documentHeader);
    public EdoDossierDocumentInfo getEdoDossierDocumentInfoByDocId(String documentId);
    public EdoDossierDocumentInfo getEdoDossierDocumentInfoByDossierId(String dossierId);
    public List<EdoDossierDocumentInfo> getEdoDossierDocumentInfoByDocType(String dossierId, String docTypeName);
    public BigDecimal getCurrentRouteLevel(String dossierId );
    public String getCurrentRouteLevelName(String dossierId );
    public boolean hasPendingSupplementalDocument(String dossierId);
    public List<EdoDossierDocumentInfo> getPendingSupplementalDocuments(String dossierId);
    
    
}
