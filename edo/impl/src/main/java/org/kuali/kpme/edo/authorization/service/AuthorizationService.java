package org.kuali.kpme.edo.authorization.service;

import org.kuali.kpme.edo.dossier.EdoDossier;
import org.kuali.kpme.edo.util.EdoUser;
import org.kuali.rice.kew.api.action.ActionType;

import java.math.BigDecimal;
import java.util.List;


public interface AuthorizationService {
	
	public EdoUser getEdoUser(String networkId);
	public List<String> getRoleList(String emplid);

    public boolean isAuthorizedToEditDossier(String principalId, Integer dossierId);
    public boolean isAuthorizedToEditDossier(String principalId, EdoDossier dossier);
    public boolean isAuthorizedToEditAoe(String principalId, Integer dossierId);
    public List<BigDecimal> getAuthorizedLevels(String principalId, String permissionTemplate);
    public boolean isAuthorizedToVote(String principalId, Integer dossierId);
    public boolean isAuthorizedToVote(String principalId, BigDecimal thisLevel);
    public boolean isAuthorizedToVote(String principalId);
    public boolean isAuthorizedToTakeDocumentAction(String principalId, Integer dossierId, ActionType actionType);
    public boolean isAuthorizedToUploadReviewLetter(String principalId, Integer dossierId);
    public boolean isAuthorizedToUploadReviewLetter(String principalId);
    public boolean isAuthorizedToUploadExternalLetter(String principalId, Integer dossierId);
    public boolean isAuthorizedToDelegateChair(String principalId);
    public boolean isAuthorizedToSubmitSupplemental(String principalId);
    public boolean isAuthorizedToSubmitReconsider(String principalId);

    // wrappers for chair delegate checks
    public boolean isAuthorizedToEditDossier_W(String principalId, Integer dossierId);
    public boolean isAuthorizedToEditDossier_W(String principalId, EdoDossier dossier);
    public boolean isAuthorizedToEditAoe_W(String principalId, Integer dossierId);
    public List<BigDecimal> getAuthorizedLevels_W(String principalId, String permissionTemplate);
    public boolean isAuthorizedToVote_W(String principalId, Integer dossierId);
    public boolean isAuthorizedToVote_W(String principalId, BigDecimal thisLevel);
    public boolean isAuthorizedToVote_W(String principalId);
    public boolean isAuthorizedToTakeDocumentAction_W(String principalId, Integer dossierId, ActionType actionType);
    public boolean isAuthorizedToUploadReviewLetter_W(String principalId, Integer dossierId);
    public boolean isAuthorizedToUploadReviewLetter_W(String principalId);
    public boolean isAuthorizedToUploadExternalLetter_W(String principalId, Integer dossierId);
    public boolean isAuthorizedToSubmitSupplemental_W(String principalId);
    public boolean isAuthorizedToSubmitReconsider_W(String principalId);
}
