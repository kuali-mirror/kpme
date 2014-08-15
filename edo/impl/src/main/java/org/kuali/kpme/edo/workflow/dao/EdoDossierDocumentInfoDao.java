package org.kuali.kpme.edo.workflow.dao;

import org.kuali.kpme.edo.workflow.EdoDossierDocumentInfoBo;

import java.util.List;

public interface EdoDossierDocumentInfoDao {

    public void saveOrUpdate(EdoDossierDocumentInfoBo documentHeader);
    public EdoDossierDocumentInfoBo getEdoDossierDocumentInfoByDocId(String documentId);
    public EdoDossierDocumentInfoBo getEdoDossierDocumentInfoByDossierId(String dossierId);
    public List<EdoDossierDocumentInfoBo> getEdoDossierDocumentInfoByDocType(String dossierId, String docTypeName);
    public boolean hasPendingSupplementalDocument(String dossierId);
    public List<EdoDossierDocumentInfoBo> getPendingSupplementalDocuments(String dossierId);
}
