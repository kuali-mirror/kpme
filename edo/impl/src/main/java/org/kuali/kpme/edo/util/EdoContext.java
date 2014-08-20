package org.kuali.kpme.edo.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.edo.api.dossier.EdoDossier;
import org.kuali.kpme.edo.api.dossier.EdoDossierDocumentInfo;
import org.kuali.kpme.edo.base.web.EdoForm;
import org.kuali.kpme.edo.candidate.EdoSelectedCandidate;
import org.kuali.kpme.edo.dossier.EdoDossierBo;
import org.kuali.kpme.edo.permission.EDOPermissionTemplate;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.submitButton.EdoSubmitButton;
import org.kuali.kpme.edo.workflow.EdoDossierDocumentInfoBo;
import org.kuali.rice.kew.api.action.ActionType;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.util.*;


public class EdoContext {
    private static final String USER_SESSION_FORM_KEY = "_DOCUMENT_FORM";
    private static final String LOOKUP_CONTEXT = "_LOOKUP_CONTEXT";
    private static final String REDIRECT_HEADER_SET = "_REDIRECT_HEADER_SET";
    private static final String USER = "_USER";
    private static final String YEAR = "_YEAR";

    private static final ThreadLocal<Map<String, Object>> STORAGE_MAP = new ThreadLocal<Map<String, Object>>() {
        @Override
        protected Map<String, Object> initialValue() {
            return Collections.synchronizedMap(new HashMap<String, Object>());
        }
    };

    private static Map<String, Object> getStorageMap() {
        return STORAGE_MAP.get();
    }

    public static void clear() {
        GlobalVariables.clear();
        EdoContext.getStorageMap().clear();
    }
    public static void clearFormsFromSession(){
    	 
        if(getHttpServletRequest()!= null){
           // EdoSessionState edoSessionState = new EdoSessionState(EdoContext.getHttpServletRequest().getSession());
            //edoSessionState.removeFormsForUser();
        	UserSession uSession = GlobalVariables.getUserSession(); 
       	 uSession.clearBackdoorUser();
       	GlobalVariables.getUserSession().removeObject(EdoConstants.EDO_TARGET_USER_PERSON);
       
        	
        }
    }

    public static EdoForm getEdoForm() {
        return (EdoForm) getStorageMap().get(USER_SESSION_FORM_KEY);
    }

    public static void setEdoForm(EdoForm edoForm) {
        getStorageMap().put(USER_SESSION_FORM_KEY, edoForm);
    }

    //EDO Backdoor Principal - > EDO Principal
    public static String getPrincipalId() {
        return GlobalVariables.getUserSession().getPrincipalId();
    }
    public static String getPrincipalName() {
        return GlobalVariables.getUserSession().getPrincipalName();
    }

    //EDO Target Principal -> EDO Backdoor Principal -> EDO Principal
    public static String getTargetPrincipalId() {
        String principalId = (String) GlobalVariables.getUserSession().retrieveObject(EdoConstants.EDO_TARGET_USER_PERSON);
        if (principalId == null) {
            principalId = GlobalVariables.getUserSession().getPrincipalId();
        }
        return principalId;
    }
    //get all the pricipalId for whom the logged in user is a delegate.
   
    public static List<String> getPrincipalDelegators(String principalId) {
    	
    	List<String> roles = EdoServiceLocator.getAuthorizationService().getRoleList(principalId);
    	List<String> principalList = new LinkedList<String>();
    	if(roles.contains("Committee Chair Delegate")) {
    		principalList = EdoServiceLocator.getEdoMaintenanceService().getChairListForDelegate(principalId);
    	}
    	principalList.add(principalId); //also add logged in person to the list
    	return principalList;
    }

    public static void setTargetPrincipalId(String principalId) {
        GlobalVariables.getUserSession().addObject(EdoConstants.EDO_TARGET_USER_PERSON, principalId);
    }

    public static String getTargetName() {
        return KimApiServiceLocator.getIdentityService().getDefaultNamesForPrincipalId(getTargetPrincipalId()).getDefaultName().getCompositeName();
    }

    public static boolean isTargetInUse() {
        return GlobalVariables.getUserSession().retrieveObject(EdoConstants.EDO_TARGET_USER_PERSON) != null;
    }

    public static void clearTargetUser() {
        GlobalVariables.getUserSession().removeObject(EdoConstants.EDO_TARGET_USER_PERSON);
    }

    public static HttpServletRequest getHttpServletRequest() {
        return (HttpServletRequest) getStorageMap().get("REQUEST");
    }

    public static void setHttpServletRequest(HttpServletRequest request) {
        getStorageMap().put("REQUEST", request);
    }

    public static void setUser(EdoUser edoUser) {
        if (getHttpServletRequest() != null) {
            getHttpServletRequest().getSession().setAttribute(USER, edoUser);
        }
    }

    public static EdoUser getUser() {
        if (getHttpServletRequest() != null) {
            EdoUser edoUser = (EdoUser) getHttpServletRequest().getSession().getAttribute(USER);
            return edoUser;
        }
        return null;
    }


    public static void setYear(String year) {
        if (getHttpServletRequest() != null) {
            getHttpServletRequest().getSession().setAttribute(YEAR, year);
        }
    }

    public static String getYear() {
        if (getHttpServletRequest() != null) {
            String year = (String) getHttpServletRequest().getSession().getAttribute(YEAR);
            if (year != null) {
                return year;
            }
        }
        return "";
    }

    public static boolean isCandidate() {
        boolean isCandidate = EdoContext.getUser().getEmplId().equals(KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName(EdoContext.getSelectedCandidate().getCandidateUsername()).getPrincipalId());
        return isCandidate;
    }

    public static boolean canUserReviewerScreen() {
        return (isAuthorized(EdoContext.getUser().getEmplId(), EdoConstants.EDO_NAME_SPACE, EdoConstants.EDO_REVIEWER_PERMISSION, new HashMap<String, String>())
                ||isAuthorized(EdoContext.getUser().getEmplId(), EdoConstants.EDO_NAME_SPACE, EdoConstants.EDO_ALL_SCREEN_PERMISSION, new HashMap<String, String>()));
    }
    public static boolean isAuthorized(String principalId, String nameSpace, String permission, HashMap<String, String> quals) {
        boolean isAuthorized = false;

        List<String> allPrincipals = EdoContext.getPrincipalDelegators(principalId);

        for (String principal : allPrincipals) {
            isAuthorized = isAuthorized || KimApiServiceLocator.getPermissionService().isAuthorized(principal, nameSpace, permission, quals);
        }

        return isAuthorized;

    }

    public static boolean canEditAoeForSelectedDossier() {
        EdoSelectedCandidate selectedCandidate = getSelectedCandidate();
        if (selectedCandidate != null) {
            return EdoServiceLocator.getAuthorizationService().isAuthorizedToEditAoe_W(getPrincipalId(), selectedCandidate.getCandidateDossierID().intValue());
        }

        return false;
    }
   

    public static boolean canVoteForSelectedDossier() {
        EdoSelectedCandidate selectedCandidate = getSelectedCandidate();
        if (selectedCandidate != null) {
            return EdoServiceLocator.getAuthorizationService().isAuthorizedToVote_W(getPrincipalId(), selectedCandidate.getCandidateDossierID().intValue());
        }

        return false;
    }

    public static boolean canViewVoteRecordsForSelectedDossier() {
        return getAuthorizedViewVoteRecordLevels().size() > 0;
    }

    public static boolean canSUApproveSelectedDossier() {
        EdoSelectedCandidate selectedCandidate = getSelectedCandidate();
        if (selectedCandidate != null) {
            return EdoServiceLocator.getAuthorizationService().isAuthorizedToTakeDocumentAction_W(getPrincipalId(), selectedCandidate.getCandidateDossierID().intValue(), ActionType.SU_ROUTE_NODE_APPROVE);
        }

        return false;
    }

    public static boolean canSUReturnSelectedDossier() {
        EdoSelectedCandidate selectedCandidate = getSelectedCandidate();
        if (selectedCandidate != null) {
            return EdoServiceLocator.getAuthorizationService().isAuthorizedToTakeDocumentAction_W(getPrincipalId(), selectedCandidate.getCandidateDossierID().intValue(), ActionType.SU_RETURN_TO_PREVIOUS);
        }

        return false;
    }

    public static boolean canCancelSelectedDossier() {
        EdoSelectedCandidate selectedCandidate = getSelectedCandidate();
        if (selectedCandidate != null) {
            return EdoServiceLocator.getAuthorizationService().isAuthorizedToTakeDocumentAction_W(getPrincipalId(), selectedCandidate.getCandidateDossierID().intValue(), ActionType.CANCEL);
        }

        return false;
    }
    
    public static boolean canSeeSubmitDossierButton() {
        EdoSelectedCandidate selectedCandidate = getSelectedCandidate();
        if (selectedCandidate != null) {
        	if(StringUtils.equals(selectedCandidate.getDossierStatus(), EdoConstants.DOSSIER_STATUS.RECONSIDERATION) || StringUtils.equals(selectedCandidate.getDossierStatus(), EdoConstants.DOSSIER_STATUS.CLOSED) || StringUtils.equals(selectedCandidate.getDossierStatus(), EdoConstants.DOSSIER_STATUS.SUBMITTED)) {
        		return false;
        	}
        	else {
        		return true;
        	}
        	
        }

        return false;
    }
    public static boolean isSubmitButtonActive() {
        EdoSelectedCandidate selectedCandidate = getSelectedCandidate();
        if (selectedCandidate != null) {
        	EdoSubmitButton edoSubmitButton = EdoServiceLocator.getEdoDisplaySubmitButtonService().getEdoSubmitButton(selectedCandidate.getCandidateCampusCode());
        	if(ObjectUtils.isNotNull(edoSubmitButton) && edoSubmitButton.isActiveFlag()) {
        		return true;
        	}
        	else {
        		return false;
        	}
        	
        }

        return false;
    }

    public static boolean canSubmitSelectedDossier() {
        EdoSelectedCandidate selectedCandidate = getSelectedCandidate();
        if (selectedCandidate != null) {
            return EdoServiceLocator.getAuthorizationService().isAuthorizedToTakeDocumentAction_W(getPrincipalId(), selectedCandidate.getCandidateDossierID().intValue(), ActionType.ROUTE);
        }

        return false;
    }
    public static boolean canSubmitSupplemental() {
        EdoSelectedCandidate selectedCandidate = getSelectedCandidate();
        if (selectedCandidate != null) {
            return EdoServiceLocator.getAuthorizationService().isAuthorizedToSubmitSupplemental_W(getPrincipalId());
        }

        return false;
    }
    public static boolean canSubmitReconsider() {
        EdoSelectedCandidate selectedCandidate = getSelectedCandidate();
        if (selectedCandidate != null) {
            return EdoServiceLocator.getAuthorizationService().isAuthorizedToSubmitReconsider_W(getPrincipalId());
        }

        return false;
    }
    public static boolean canRouteReconsider() {
    	 EdoSelectedCandidate selectedCandidate = getSelectedCandidate();
    	 if (selectedCandidate != null) {
           if(StringUtils.equals(selectedCandidate.getDossierStatus(), EdoConstants.DOSSIER_STATUS.RECONSIDERATION)) {
        	   List<String> roleIds = new ArrayList<String>();
               roleIds.add(KimApiServiceLocator.getRoleService().getRoleIdByNamespaceCodeAndName(EdoConstants.EDO_NAME_SPACE,EdoConstants.CHAIR_DELEGATE_ROLE));
               if (KimApiServiceLocator.getRoleService().principalHasRole(getPrincipalId(),roleIds,new HashMap<String,String>())) {
                    return false;
               }
               else {
            	   
            	   return true;
               }
        	   
           }
           else {
        	   return true;
           }
          
         }

         return false;
    	
    }
    
    //check to see if logged in user is a edoselectedcandidate
    public static boolean canSeeHisOwnSupplementalButton() {
    	EdoSelectedCandidate selectedCandidate = getSelectedCandidate();
    	  if (selectedCandidate != null) {
    		  if(StringUtils.equals(selectedCandidate.getCandidateUsername(),EdoContext.getPrincipalName())) {
    			  return true;
    		  }
    	  }
    	  return false;
    }
    
    public static boolean canRouteSelectedDossier() {
        EdoSelectedCandidate selectedCandidate = getSelectedCandidate();
        if (selectedCandidate != null) {
            return EdoServiceLocator.getAuthorizationService().isAuthorizedToTakeDocumentAction_W(getPrincipalId(), selectedCandidate.getCandidateDossierID().intValue(), ActionType.APPROVE);
        }

        return false;
    }

    public static boolean canReturnSelectedDossier() {
        EdoSelectedCandidate selectedCandidate = getSelectedCandidate();
        if (selectedCandidate != null) {
            return EdoServiceLocator.getAuthorizationService().isAuthorizedToTakeDocumentAction_W(getPrincipalId(), selectedCandidate.getCandidateDossierID().intValue(), ActionType.RETURN_TO_PREVIOUS);
        }

        return false;
    }
  
    //can update the dossier status to "CLOSED" or "RECONSIDER'
    
  public static boolean canUpdateDossierstatus() {
    	//check if the logged in person is a super user
    	if(EdoContext.getUser().getCurrentRoleList().contains("Super User") || EdoContext.getUser().getCurrentRoleList().contains("Final Administrator")) {
    	EdoSelectedCandidate selectedCandidate = getSelectedCandidate();
        if (selectedCandidate != null) {
        	EdoDossier dossier = EdoServiceLocator.getEdoDossierService().getEdoDossierById(selectedCandidate.getCandidateDossierID().toString());
        	if(ObjectUtils.isNotNull(dossier) && !StringUtils.equals(dossier.getDossierStatus(),"CLOSED")) {
        	//TODO: take care of dossier.getDocumentID(), documentID is not in EdoDossier
        	//DossierProcessDocumentHeader documentHeader = EdoServiceLocator.getDossierProcessDocumentHeaderService().getDossierProcessDocumentHeader(dossier.getDocumentID());
        	EdoDossierDocumentInfoBo documentHeader = null;
        		if(documentHeader != null) {
            	//check if document is finalized or not
            	if(StringUtils.equals(documentHeader.getDocumentStatus(), "F")) {
            		return true;
            		}
            	}
            
        	}
        }
    }
        return false;
    }

    //can see the ack with action and acknowledge with no action buttons
    public static boolean canApproveSuppDoc() {

        // chair delegates are not allowed to approve/acknowledge supplemental documents, so check this first
        List<String> roleIds = new ArrayList<String>();
        roleIds.add(KimApiServiceLocator.getRoleService().getRoleIdByNamespaceCodeAndName(EdoConstants.EDO_NAME_SPACE,EdoConstants.CHAIR_DELEGATE_ROLE));
        if (KimApiServiceLocator.getRoleService().principalHasRole(getPrincipalId(),roleIds,new HashMap<String,String>())) {
             return false;
        }

    	EdoSelectedCandidate selectedCandidate = getSelectedCandidate();
        if (selectedCandidate != null) {
            EdoDossierDocumentInfo documentHeader = EdoServiceLocator.getEdoDossierDocumentInfoService().getEdoDossierDocumentInfoByDossierId(selectedCandidate.getCandidateDossierID().toString());
            if(documentHeader != null) {
                List<EdoDossierDocumentInfo> suppDocHeaders = EdoServiceLocator.getEdoDossierDocumentInfoService().getPendingSupplementalDocuments(documentHeader.getEdoDossierId());
                if (CollectionUtils.isNotEmpty(suppDocHeaders)) {
                    boolean isWaiting = false;
                    for(EdoDossierDocumentInfo docHeader : suppDocHeaders) {
                        isWaiting = isWaiting || EdoServiceLocator.getEdoSupplementalPendingStatusService().isWaiting(docHeader.getEdoDocumentId(),EdoContext.getPrincipalId());
                    }
                    if (isWaiting) {
                        return true;
                    }
                }

            }
        }
    	return false;
    }
    

    public static List<BigDecimal> getAuthorizedEditVoteRecordLevels() {
        return EdoServiceLocator.getAuthorizationService().getAuthorizedLevels_W(getPrincipalId(), EDOPermissionTemplate.EDIT_VOTE_RECORD.getPermissionTemplateName());
    }

    public static List<BigDecimal> getAuthorizedViewVoteRecordLevels() {
        return EdoServiceLocator.getAuthorizationService().getAuthorizedLevels_W(getPrincipalId(), EDOPermissionTemplate.VIEW_VOTE_RECORD.getPermissionTemplateName());
    }

    public static List<BigDecimal> getAuthorizedUploadReviewLetterLevels() {
        return EdoServiceLocator.getAuthorizationService().getAuthorizedLevels_W(getPrincipalId(), EDOPermissionTemplate.UPLOAD_REVIEW_LETTER.getPermissionTemplateName());
    }

    public static List<BigDecimal> getAuthorizedUploadExternalLetterLevels() {
        return EdoServiceLocator.getAuthorizationService().getAuthorizedLevels_W(getPrincipalId(), EDOPermissionTemplate.UPLOAD_EXTERNAL_LETTER.getPermissionTemplateName());
    }

    public static List<BigDecimal> getAuthorizedViewReviewLetterLevels() {
        return EdoServiceLocator.getAuthorizationService().getAuthorizedLevels_W(getPrincipalId(), EDOPermissionTemplate.VIEW_VOTE_RECORD.getPermissionTemplateName());
    }

    public static EdoSelectedCandidate getSelectedCandidate() {
        return (EdoSelectedCandidate) getHttpServletRequest().getSession().getAttribute(EdoConstants.SELECTED_CANDIDATE);
    }

    public static BigDecimal getPrincipalMaxReviewLevel() {
        List<BigDecimal> authorizedEditVoteRecordLevels = getAuthorizedEditVoteRecordLevels();
        BigDecimal max = BigDecimal.ZERO;
        for(int i=0; i<authorizedEditVoteRecordLevels.size(); i++){
            if(authorizedEditVoteRecordLevels.get(i).compareTo(max) == 1){
                max = authorizedEditVoteRecordLevels.get(i);
            }
        }
        return max;
    }
}
