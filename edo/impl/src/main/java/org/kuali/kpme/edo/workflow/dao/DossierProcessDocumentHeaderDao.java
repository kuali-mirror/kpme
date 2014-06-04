package org.kuali.kpme.edo.workflow.dao;

import org.kuali.kpme.edo.workflow.DossierProcessDocumentHeader;

import java.util.List;

public interface DossierProcessDocumentHeaderDao {

    public void saveOrUpdate(DossierProcessDocumentHeader documentHeader);
    public DossierProcessDocumentHeader getDossierProcessDocumentHeader(String documentId);
    public DossierProcessDocumentHeader getDossierProcessDocumentHeader(Integer dossierId);
    public List<DossierProcessDocumentHeader> getDossierProcessDocumentHeaderByDocType(Integer dossierId, String docTypeName);
    public boolean hasPendingSupplementalDocument(Integer dossierId);
    public List<DossierProcessDocumentHeader> getPendingSupplementalDocuments(Integer dossierId);
}
