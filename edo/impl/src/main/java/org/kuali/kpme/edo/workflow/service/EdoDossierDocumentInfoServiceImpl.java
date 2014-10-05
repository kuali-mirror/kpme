package org.kuali.kpme.edo.workflow.service;

import java.math.BigDecimal;
import java.util.List;

import org.kuali.kpme.edo.api.dossier.EdoDossierDocumentInfo;
import org.kuali.kpme.edo.workflow.EdoDossierDocumentInfoBo;
import org.kuali.kpme.edo.workflow.dao.EdoDossierDocumentInfoDao;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.kew.routeheader.DocumentRouteHeaderValue;
import org.kuali.rice.kew.service.KEWServiceLocator;

public class EdoDossierDocumentInfoServiceImpl implements EdoDossierDocumentInfoService {

    private EdoDossierDocumentInfoDao edoDossierDocumentInfoDao;

    public void setEdoDossierDocumentInfoDao(EdoDossierDocumentInfoDao edoDossierDocumentInfoDao) {
        this.edoDossierDocumentInfoDao = edoDossierDocumentInfoDao;
    }
    
    public void saveOrUpdate(EdoDossierDocumentInfo dossierProcessDocumentHeader) {
    	EdoDossierDocumentInfoBo bo = EdoDossierDocumentInfoBo.from(dossierProcessDocumentHeader);
    	
        this.edoDossierDocumentInfoDao.saveOrUpdate(bo);
        //this.dossierProcessDocumentHeaderDao.saveOrUpdate(dossierProcessDocumentHeader);
    }

    public EdoDossierDocumentInfo getEdoDossierDocumentInfoByDocId(String documentId) {
    	EdoDossierDocumentInfoBo edoDossierDocumentInfoBo = edoDossierDocumentInfoDao.getEdoDossierDocumentInfoByDocId(documentId);
    	
    	if ( edoDossierDocumentInfoBo == null){
    		return null;
    	}
    	
    	EdoDossierDocumentInfo.Builder builder = EdoDossierDocumentInfo.Builder.create(edoDossierDocumentInfoBo);
    	
    	return builder.build();
    }

    public EdoDossierDocumentInfo getEdoDossierDocumentInfoByDossierId(String dossierId) {
    	EdoDossierDocumentInfoBo edoDossierDocumentInfoBo = edoDossierDocumentInfoDao.getEdoDossierDocumentInfoByDossierId(dossierId);
    	
    	if ( edoDossierDocumentInfoBo == null){
    		return null;
    	}
    	
    	EdoDossierDocumentInfo.Builder builder = EdoDossierDocumentInfo.Builder.create(edoDossierDocumentInfoBo);
    	
    	return builder.build();
    }

    public List<EdoDossierDocumentInfo> getEdoDossierDocumentInfoByDocType(String dossierId, String docTypeName) {
    	
    	return ModelObjectUtils.transform(edoDossierDocumentInfoDao.getEdoDossierDocumentInfoByDocType(dossierId, docTypeName), EdoDossierDocumentInfoBo.toImmutable);
    }

    public BigDecimal getCurrentRouteLevel(String dossierId) {
        //Get the document header for dossierId to documentId translation.
        EdoDossierDocumentInfo documentHeader = getEdoDossierDocumentInfoByDossierId(dossierId);
        if (documentHeader != null) {
            //Get the route header for the current route level.
            DocumentRouteHeaderValue routeHeaderValue = KEWServiceLocator.getRouteHeaderService().getRouteHeader(documentHeader.getEdoDocumentId());
            if (routeHeaderValue != null && routeHeaderValue.getDocRouteLevel() != null) {
                return new BigDecimal(routeHeaderValue.getDocRouteLevel());
            }
        }

        return null;
    }

    public String getCurrentRouteLevelName(String dossierId) {
        //Get the document header for dossierId to documentId translation.
        EdoDossierDocumentInfo documentHeader = getEdoDossierDocumentInfoByDossierId(dossierId);
        if (documentHeader != null) {
            //Get the route header for the current route level.
            DocumentRouteHeaderValue routeHeaderValue = KEWServiceLocator.getRouteHeaderService().getRouteHeader(documentHeader.getEdoDocumentId());
            if (routeHeaderValue != null && routeHeaderValue.getDocRouteLevel() != null) {
                //System.out.println("DocHeader: " + routeHeaderValue.getCurrentRouteLevelName());
                return routeHeaderValue.getCurrentRouteLevelName();
            }
        }

        return null;
    }

    public boolean hasPendingSupplementalDocument(String dossierId) {
        return this.edoDossierDocumentInfoDao.hasPendingSupplementalDocument(dossierId);
    }

    public List<EdoDossierDocumentInfo> getPendingSupplementalDocuments(String dossierId) {
    	
    	return ModelObjectUtils.transform(edoDossierDocumentInfoDao.getPendingSupplementalDocuments(dossierId), EdoDossierDocumentInfoBo.toImmutable);
    }
}
