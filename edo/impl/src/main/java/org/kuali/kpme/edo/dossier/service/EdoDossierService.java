package org.kuali.kpme.edo.dossier.service;

import org.kuali.kpme.edo.dossier.EdoDossier;
import org.kuali.rice.kew.api.action.ActionType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 1/17/13
 * Time: 2:25 PM
 * To change this template use File | Settings | File Templates.
 */
public interface EdoDossierService {

    public EdoDossier getCurrentDossier( String userName );
    public List<EdoDossier> getDossierList();
    public EdoDossier getDossierById( BigDecimal dossierId );
    public void saveOrUpdate(EdoDossier edoDossier);
    //to populate the dossier drop down on the assign delegate page
    public List<EdoDossier> getDossierListByUserName(String userName);
    public EdoDossier getDossierByDossierId( String dossierId );
    public boolean routeDocument(String principalId, Integer dossierId, String dossierType);
    public boolean returnToCandidate(String principalId, Integer dossierId, String dossierType);
    public boolean superUserAction(String principalId, Integer dossierId, String dossierType, ActionType superUserAction, String node);
    public boolean routeSupplementalDocument(String principalId, Integer dossierId, String dossierType);
    public Map<String, String> getSupplementalDocTypeMap();
    public EdoDossier getDossier(String documentId);
    public boolean approveSupplemental(String principalId, Integer dossierId, String dossierType);
    public boolean routeReconsiderDocument(String principalId, Integer dossierId, String dossierType);
    public boolean isRoutedAsReconsiderDocument(Integer dossierId);
}
