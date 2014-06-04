package org.kuali.kpme.edo.workflow.service;

import org.kuali.kpme.edo.workflow.DossierProcessDocumentHeader;
import org.kuali.kpme.edo.workflow.dao.DossierProcessDocumentHeaderDao;
import org.kuali.rice.kew.routeheader.DocumentRouteHeaderValue;
import org.kuali.rice.kew.service.KEWServiceLocator;

import java.math.BigDecimal;
import java.util.List;

public class DossierProcessDocumentHeaderServiceImpl implements DossierProcessDocumentHeaderService {

    private DossierProcessDocumentHeaderDao dossierProcessDocumentHeaderDao;

    public DossierProcessDocumentHeader getDossierProcessDocumentHeader(String documentId) {
        return this.dossierProcessDocumentHeaderDao.getDossierProcessDocumentHeader(documentId);
    }

    public void saveOrUpdate(DossierProcessDocumentHeader dossierProcessDocumentHeader) {
        this.dossierProcessDocumentHeaderDao.saveOrUpdate(dossierProcessDocumentHeader);
    }

    public DossierProcessDocumentHeader getDossierProcessDocumentHeader(Integer dossierId) {
        return this.dossierProcessDocumentHeaderDao.getDossierProcessDocumentHeader(dossierId);
    }

    public List<DossierProcessDocumentHeader> getDossierProcessDocumentHeaderByDocType(Integer dossierId, String docTypeName) {
        return this.dossierProcessDocumentHeaderDao.getDossierProcessDocumentHeaderByDocType(dossierId, docTypeName);
    }

    public void setDossierProcessDocumentHeaderDao(DossierProcessDocumentHeaderDao dossierProcessDocumentHeaderDao) {
        this.dossierProcessDocumentHeaderDao = dossierProcessDocumentHeaderDao;
    }

    public BigDecimal getCurrentRouteLevel(Integer dossierId) {
        //Get the document header for dossierId to documentId translation.
        DossierProcessDocumentHeader documentHeader = getDossierProcessDocumentHeader(dossierId);
        if (documentHeader != null) {
            //Get the route header for the current route level.
            DocumentRouteHeaderValue routeHeaderValue = KEWServiceLocator.getRouteHeaderService().getRouteHeader(documentHeader.getDocumentId());
            if (routeHeaderValue != null && routeHeaderValue.getDocRouteLevel() != null) {
                return new BigDecimal(routeHeaderValue.getDocRouteLevel());
            }
        }

        return null;
    }

    public String getCurrentRouteLevelName(Integer dossierId) {
        //Get the document header for dossierId to documentId translation.
        DossierProcessDocumentHeader documentHeader = getDossierProcessDocumentHeader(dossierId);
        if (documentHeader != null) {
            //Get the route header for the current route level.
            DocumentRouteHeaderValue routeHeaderValue = KEWServiceLocator.getRouteHeaderService().getRouteHeader(documentHeader.getDocumentId());
            if (routeHeaderValue != null && routeHeaderValue.getDocRouteLevel() != null) {
                //System.out.println("DocHeader: " + routeHeaderValue.getCurrentRouteLevelName());
                return routeHeaderValue.getCurrentRouteLevelName();
            }
        }

        return null;
    }

    public boolean hasPendingSupplementalDocument(Integer dossierId) {
        return this.dossierProcessDocumentHeaderDao.hasPendingSupplementalDocument(dossierId);
    }

    public List<DossierProcessDocumentHeader> getPendingSupplementalDocuments(Integer dossierId) {
        return this.dossierProcessDocumentHeaderDao.getPendingSupplementalDocuments(dossierId);
    }
}
