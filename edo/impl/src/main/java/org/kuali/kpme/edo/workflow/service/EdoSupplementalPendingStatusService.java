package org.kuali.kpme.edo.workflow.service;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 9/20/13
 * Time: 4:05 PM
 */
public interface EdoSupplementalPendingStatusService {

    public boolean isWaiting(String supplementalDocumentId, String principalId);
    public void notifyCurrentEdossierLevelForSuppDoc(String dossierDocumentId, String currentRouteLevel, String contentSingle,  String contentMultiple);
    public boolean hasAddedSupplementalReviewLetter(String supplementalDocumentId);
    public boolean hasAddedSupplementalVoteRecord(String supplementalDocumentId);
    }
