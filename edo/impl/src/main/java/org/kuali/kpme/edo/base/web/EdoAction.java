package org.kuali.kpme.edo.base.web;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.kpme.edo.candidate.EdoSelectedCandidate;
import org.kuali.kpme.edo.checklist.EdoChecklist;
import org.kuali.kpme.edo.dossier.EdoDossier;
import org.kuali.kpme.edo.item.EdoItemTracker;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.kpme.edo.util.EdoContext;
import org.kuali.kpme.edo.util.EdoRule;
import org.kuali.rice.core.api.config.property.Config;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.kim.api.permission.PermissionService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kns.web.struts.action.KualiAction;
import org.kuali.rice.krad.exception.AuthorizationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: lfox
 * Date: 8/27/12
 * Time: 8:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class EdoAction extends KualiAction {

    protected Config config;
    private EdoSelectedCandidate selectedCandidate;
    public HashMap<String, List<EdoChecklist>> checklistHash;
    private PermissionService permissionService;
    static final Logger LOG = Logger.getLogger(EdoAction.class);

    public HashMap<String, List<EdoChecklist>> getChecklistHash() {
        return checklistHash;
    }

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
	 	EdoForm edoForm = (EdoForm)form;
        config = ConfigContext.getCurrentContextConfig();
        Map<String, String> permissionDetails = new HashMap<String, String>();

        // setup the selected candidate object for the current session
        HttpSession ssn = request.getSession();
        if (ssn.isNew() || (null == ssn.getAttribute("selectedCandidate")) ) {
            ssn.setAttribute("selectedCandidate", new EdoSelectedCandidate() );
            // set a default node ID
            ssn.setAttribute("nid", EdoConstants.EDO_DEFAULT_CHECKLIST_NODE_ID);
        }
        selectedCandidate = (EdoSelectedCandidate)ssn.getAttribute("selectedCandidate");
        request.setAttribute("selectedCandidate", selectedCandidate);

        // create item-tracker object in session if it doesn't exist
        if (ssn.isNew() || (null == ssn.getAttribute("itemTracker")) ) {
            ssn.setAttribute("itemTracker", new EdoItemTracker() );
        }

        // setup the checklist for the currently selected candidate for navigation display
        if (selectedCandidate.isSelected()) {
            SortedMap<String, List<EdoChecklist>> checklistHash = EdoServiceLocator.getChecklistViewService().getCheckListHash(selectedCandidate.getCandidateCampusCode(),selectedCandidate.getCandidateSchoolID(),selectedCandidate.getCandidateDepartmentID() );
            request.setAttribute("checklisthash", checklistHash);
        } else {
            request.setAttribute("checklisthash", null);
        }
        List<String> currRoles =  EdoContext.getUser().getCurrentRoleList();
        request.setAttribute("currRoles",currRoles);
    	request.setAttribute("emplId", EdoContext.getUser().getEmplId());
       	request.setAttribute("fullName", EdoContext.getUser().getName()); 
        request.setAttribute("userName", EdoContext.getUser().getNetworkId());
        request.setAttribute("deptName", EdoContext.getUser().getDeptName());
        request.setAttribute("isCandidateSelected", selectedCandidate.isSelected());
        //this is for displaying the supplemental items
        if(selectedCandidate.isSelected() == true)
        {
        //edo-74
		     EdoDossier currentDossier = EdoServiceLocator.getEdoDossierService().getCurrentDossier(selectedCandidate.getCandidateUsername());
		     if(currentDossier != null)
		     {
		     request.setAttribute("candidateDossierStatus", currentDossier.getDossierStatus());
		     }
        }
        //edo - 134 - Role check is replaced with permission check
        if(hasPermission(EdoContext.getUser().getEmplId(), EdoConstants.EDO_NAME_SPACE, EdoConstants.EDO_LOGIN_PERMISSION))
        {
            //if(isAuthorized(EdoContext.getUser().getEmplId(), EdoConstants.EDO_NAME_SPACE, EdoConstants.EDO_CANDIDATE_PERMISSION, new HashMap<String, String>()))
            if(getPermissionService().isAuthorized(EdoContext.getUser().getEmplId(), EdoConstants.EDO_NAME_SPACE, EdoConstants.EDO_CANDIDATE_PERMISSION, new HashMap<String, String>()))
            {
	        	edoForm.setUseCandidateScreen(true);
	        	edoForm.setUseHelpScreen(true);
	        }
	        if(isAuthorized(EdoContext.getUser().getEmplId(), EdoConstants.EDO_NAME_SPACE, EdoConstants.EDO_REVIEWER_PERMISSION, new HashMap<String, String>()))
	        {
	        	edoForm.setUseReviewerScreen(true);
	        	edoForm.setUseHelpScreen(true);
	        }
	        if(isAuthorized(EdoContext.getUser().getEmplId(), EdoConstants.EDO_NAME_SPACE, EdoConstants.EDO_GEN_ADMIN_PERMISSION, new HashMap<String, String>()))
	        {
	        	edoForm.setUseGenAdminScreen(true);
	        	edoForm.setUseHelpScreen(true);
	        }
	        //check to see if the logged in user has assign delegate permission
	        if(isAuthorized(EdoContext.getUser().getEmplId(), EdoConstants.EDO_NAME_SPACE, EdoConstants.EDO_ASSIGN_DELEGATE_PERMISSION, new HashMap<String, String>()))
	        {
	        	
	        	edoForm.setUseAssignDelegateFunc(true);
	        }
	        if(isAuthorized(EdoContext.getUser().getEmplId(), EdoConstants.EDO_NAME_SPACE, EdoConstants.EDO_ASSIGN_GUEST_PERMISSION, new HashMap<String, String>()))
	        {
	        	
	        	edoForm.setUseAssignGuestFunc(true);
	        }

            List<String> roleIds = new LinkedList<String>();
            roleIds.add(KimApiServiceLocator.getRoleService().getRoleIdByNamespaceCodeAndName(EdoConstants.EDO_NAME_SPACE, EdoConstants.EDO_SUPERUSER_ROLE));
            if (KimApiServiceLocator.getRoleService().principalHasRole(EdoContext.getUser().getEmplId(),roleIds, new HashMap<String, String>())) {
                edoForm.setCanManageGroups(true);
            }

            if (EdoServiceLocator.getEdoMaintenanceService().hasCandidateRole(EdoContext.getPrincipalId())) {
                edoForm.setHasCandidateRole(true);
            }
            if (EdoServiceLocator.getEdoMaintenanceService().hasSuperUserRole(EdoContext.getPrincipalId())) {
                edoForm.setHasSuperUserRole(true);
            }
            if (hasChairRole(EdoContext.getPrincipalId())) {
                edoForm.setHasChairRole(true);
            }

            //--------------------------------------------------------------------------
            // dossier-dependent permissions are set in this block
            //
            if (selectedCandidate.isSelected()) {
                // check for edit dossier permissions
                if (EdoServiceLocator.getAuthorizationService().isAuthorizedToEditDossier_W(EdoContext.getUser().getEmplId(), selectedCandidate.getCandidateDossierID().intValue())) {
                    edoForm.setUseEditDossierFunc(true);
                }
                // check for review letter upload perms
                if (EdoServiceLocator.getAuthorizationService().isAuthorizedToUploadReviewLetter_W(EdoContext.getUser().getEmplId(), selectedCandidate.getCandidateDossierID().intValue()) ) {
                    edoForm.setHasUploadReviewLetter(true);
                }

                // set flag for external letter upload for current dossier department
                if (EdoServiceLocator.getAuthorizationService().isAuthorizedToUploadExternalLetter_W(EdoContext.getUser().getEmplId(), selectedCandidate.getCandidateDossierID().intValue()) ) {
                    edoForm.setHasUploadExternalLetterByDept(true);
                }

                // set flag for viewing the vote record, by level
                String workflowId = EdoServiceLocator.getEdoDossierService().getDossierById(selectedCandidate.getCandidateDossierID()).getWorkflowId();

                List<BigDecimal> authorizedViewVoteRecordLevels = EdoContext.getAuthorizedViewVoteRecordLevels();
                List<BigDecimal> authorizedViewReviewLetterLevels = EdoContext.getAuthorizedViewReviewLetterLevels();
                List<String> routeNodeNames = EdoServiceLocator.getEdoReviewLayerDefinitionService().getValidReviewLevelNodeNames(workflowId);
                List<BigDecimal> authLevelVoteNodeIntersect = new LinkedList<BigDecimal>();
                List<BigDecimal> authLevelReviewNodeIntersect = new LinkedList<BigDecimal>();

                // if there is an intersection between authorizedViewVoteRecordLevels and routeNodeNames (mapped), then allow viewing of the vote record for that level
                for (String nodeName : routeNodeNames) {
                    BigDecimal nodeLevel = EdoServiceLocator.getEdoReviewLayerDefinitionService().buildReviewLevelByRouteMap(EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinitions(workflowId)).get(nodeName);
                    if (authorizedViewVoteRecordLevels.contains(nodeLevel)) {
                        authLevelVoteNodeIntersect.add(nodeLevel);
                    }
                    if (authorizedViewReviewLetterLevels.contains(nodeLevel)) {
                        authLevelReviewNodeIntersect.add(nodeLevel);
                    }
                }
                
                //edo-381
                if (CollectionUtils.isNotEmpty(authLevelVoteNodeIntersect) && !(StringUtils.equals(selectedCandidate.getCandidateUsername(), EdoContext.getPrincipalName()))) {
                    edoForm.setHasViewVoteRecordCurrentDossier(true);
                }
                if (CollectionUtils.isNotEmpty(authLevelReviewNodeIntersect) && !(StringUtils.equals(selectedCandidate.getCandidateUsername(), EdoContext.getPrincipalName()))) {
                    edoForm.setHasViewReviewLetterCurrentDossier(true);
                }
                //when can candidate upload files under reconsider category
                if (EdoRule.canUploadFileUnderReconsiderCategory(selectedCandidate.getCandidateDossierID())) {
                	edoForm.setCanUploadReconsiderItems(true);
                } 
                
            }
        }
        else{
        	edoForm.setUseHelpScreen(true);
        }

        // set a default tabId for navigation display
        if (request.getParameterMap().containsKey("tabId") ) {
            request.setAttribute("tabId", request.getParameter("tabId"));
        } else {
            request.setAttribute("tabId", "home");
        }

        // common page attributes
        request.setAttribute("appTitle", config.getProperty("app.title"));
        request.setAttribute("appSubTitle", config.getProperty("app.subtitle"));
        request.setAttribute("config", config);
        request.setAttribute("CGIscript_name", request.getServletPath() );
        request.setAttribute("CGIquery_string", request.getQueryString() );
        request.setAttribute("CGIcontext_path", request.getContextPath());

        return super.execute(mapping, form, request, response);
    }

    //we are overriding checkauthorization of kuali as we don't need that
	@Override
	protected void checkAuthorization(ActionForm form, String methodToCall)
			throws AuthorizationException {
		
	}

	public PermissionService getPermissionService() {
		if(this.permissionService == null) {
			this.permissionService = KimApiServiceLocator.getPermissionService();
		}
		return permissionService;
	}

    public boolean hasPermission(String principalId, String nameSpace, String permission) {
        boolean hasPermission = false;

        List<String> allPrincipals = EdoContext.getPrincipalDelegators(principalId);

        for (String principal : allPrincipals) {
            hasPermission = hasPermission || getPermissionService().hasPermission(principal, nameSpace, permission);
        }

        return hasPermission;

    }

    public boolean isAuthorized(String principalId, String nameSpace, String permission, HashMap<String, String> quals) {
        boolean isAuthorized = false;

        List<String> allPrincipals = EdoContext.getPrincipalDelegators(principalId);

        for (String principal : allPrincipals) {
            isAuthorized = isAuthorized || getPermissionService().isAuthorized(principal, nameSpace, permission, quals);
        }

        return isAuthorized;

    }
    public boolean hasCandidateRole(String principalId) {
    	 boolean hasCandidateRole = false;
    	 List<String> allPrincipals = EdoContext.getPrincipalDelegators(principalId);

         for (String principal : allPrincipals) {
        	 hasCandidateRole = hasCandidateRole || EdoServiceLocator.getEdoMaintenanceService().hasCandidateRole_W(principal);
         }
         return hasCandidateRole;
    }
    
    public boolean hasChairRole(String principalId) {
   	 boolean hasChairRole = false;
   	 List<String> allPrincipals = EdoContext.getPrincipalDelegators(principalId);

        for (String principal : allPrincipals) {
        	hasChairRole = hasChairRole || EdoServiceLocator.getEdoMaintenanceService().hasChairRole_W(principal);
        }
        return hasChairRole;
   }
            
    
}




	

   
