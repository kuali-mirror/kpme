package org.kuali.kpme.edo.authorization.service;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.edo.api.dossier.EdoDossier;
import org.kuali.kpme.edo.dossier.EdoDossierBo;
import org.kuali.kpme.edo.permission.EDOKimAttributes;
import org.kuali.kpme.edo.permission.EDOPermissionTemplate;
import org.kuali.kpme.edo.reviewlayerdef.EdoReviewLayerDefinition;
import org.kuali.kpme.edo.role.EDORole;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.kpme.edo.util.EdoContext;
import org.kuali.kpme.edo.util.EdoUser;
import org.kuali.kpme.edo.workflow.DossierProcessDocumentHeader;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kew.api.WorkflowDocumentFactory;
import org.kuali.rice.kew.api.action.ActionRequestType;
import org.kuali.rice.kew.api.action.ActionType;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.kew.engine.node.RouteNodeInstance;
import org.kuali.rice.kew.routeheader.DocumentRouteHeaderValue;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.PersonService;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.permission.Permission;
import org.kuali.rice.kim.api.role.RoleService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

import java.math.BigDecimal;
import java.util.*;

public class AuthorizationServiceImpl implements AuthorizationService {

    public EdoUser getEdoUser(String userName) {
        Principal principal = getIdentityService().getPrincipalByPrincipalName(userName);
        //networkId : principal name
        EdoUser edoUser = new EdoUser();
        edoUser.setNetworkId(userName);
        edoUser.setEmplId(principal.getPrincipalId());

        Person person = getPersonService().getPerson(principal.getPrincipalId());//KIMServiceLocator.getPersonService().getPerson(networkId);
        edoUser.setDeptName(person.getPrimaryDepartmentCode());
        edoUser.setEmailAddress(person == null ? null : person.getEmailAddress());
        edoUser.setName(person.getName()); //this is full name
        edoUser.setCurrentRoleList(getRoleList(principal.getPrincipalId()));
        return edoUser;
    }

    public List<String> getRoleList(String emplid) {
        List<String> roleIds = new ArrayList<String>();
        List<String> roleIds1 = new ArrayList<String>();
        List<String> roleIds2 = new ArrayList<String>();
        List<String> roleIds3 = new ArrayList<String>();
        List<String> roleIds4 = new ArrayList<String>();
        List<String> roleIds5 = new ArrayList<String>();
        List<String> roleIds6 = new ArrayList<String>();
        List<String> roleIds7 = new ArrayList<String>();
        List<String> roleIds8 = new ArrayList<String>();
        List<String> roleIds9 = new ArrayList<String>();
        List<String> roleList = new ArrayList<String>();
        roleIds.add(getRoleService().getRoleIdByNamespaceCodeAndName("EDO", "EDO_ADMINISTRATOR"));
        roleIds1.add(getRoleService().getRoleIdByNamespaceCodeAndName("EDO", "EDO_CANDIDATE")); // i have a question regarding this
        roleIds3.add(getRoleService().getRoleIdByNamespaceCodeAndName("EDO", "EDO_GUEST"));
        roleIds4.add(getRoleService().getRoleIdByNamespaceCodeAndName("EDO", "EDO_CANDIDATE_DELEGATE"));
        roleIds5.add(getRoleService().getRoleIdByNamespaceCodeAndName("EDO", "EDO_COMMITTEE_CHAIR"));
        roleIds6.add(getRoleService().getRoleIdByNamespaceCodeAndName("EDO", "EDO_SUPERUSER"));
        roleIds7.add(getRoleService().getRoleIdByNamespaceCodeAndName("EDO", "EDO_CHAIR_DELEGATE"));
        roleIds8.add(getRoleService().getRoleIdByNamespaceCodeAndName("EDO", "EDO_FINAL_ADMINSTRATORS"));
        roleIds9.add(getRoleService().getRoleIdByNamespaceCodeAndName("EDO", "EDO_SECOND_UNIT_REVIEWER"));


        if (KimApiServiceLocator.getRoleService().principalHasRole(emplid, roleIds, new HashMap<String, String>())) {
            List<Map<String, String>> lstAttrSet = getRoleService().getRoleQualifersForPrincipalByRoleIds(emplid, roleIds, new HashMap<String, String>());
            for (Map<String, String> attrSet : lstAttrSet) {
                //get the key from the map and compare with the role attributes defined in the edo constants
                Set st = attrSet.entrySet();
                Iterator it = st.iterator();
                while (it.hasNext()) {
                    Map.Entry m = (Map.Entry) it.next();
                    String admin = (String) m.getKey();

                    if (StringUtils.equals(admin, EdoConstants.ROLE_ADMINISTRATOR_SCHOOL_ID)) {
                        roleList.add("Department Admin");
                    } else if (StringUtils.equals(admin, EdoConstants.ROLE_ADMINISTRATOR_SCHOOL_ID)) {
                        roleList.add("School Admin");
                    } else {
                        roleList.add("Campus Admin");
                    }
                }
            }
        }

        if (KimApiServiceLocator.getRoleService().principalHasRole(emplid, roleIds3, new HashMap<String, String>())) {
            roleList.add("Guest Dossier");
        }

        if (KimApiServiceLocator.getRoleService().principalHasRole(emplid, roleIds4, new HashMap<String, String>())) {
            roleList.add(EdoConstants.ROLE_CANDIDATE_DELEGATE);
        }

        if (KimApiServiceLocator.getRoleService().principalHasRole(emplid, roleIds1, new HashMap<String, String>())) {
            roleList.add("Candidate");
        }

        if (KimApiServiceLocator.getRoleService().principalHasRole(emplid, roleIds6, new HashMap<String, String>())) {
            roleList.add("Super User");
        }

       /* if (KimApiServiceLocator.getRoleService().principalHasRole(emplid, roleIds7, new HashMap<String, String>())) {
            List<Map<String, String>> lstAttrSet = getRoleService().getRoleQualifersForPrincipalByRoleIds(emplid, roleIds7, new HashMap<String, String>());
            for (Map<String, String> attrSet : lstAttrSet) {
                Set st = attrSet.entrySet();
                Iterator it = st.iterator();
                while (it.hasNext()) {
                    Map.Entry m = (Map.Entry) it.next();
                    String chairDelegate = (String) m.getKey();

                    if (StringUtils.equals(chairDelegate, EdoConstants.ROLE_CHAIR_DELEGATE_DEPT_ID)) {
                        roleList.add("Department Committee Chair Delegate");
                    } else if (StringUtils.equals(chairDelegate, EdoConstants.ROLE_CHAIR_DELEGATE_SCHOOL_ID)) {
                        roleList.add("School Committee Chair Delegate");
                    } else if (StringUtils.equals(chairDelegate, EdoConstants.ROLE_CHAIR_DELEGATE_CAMPUS_ID)) {
                        roleList.add("Campus Committee Chair Delegate");
                    } else {
                        roleList.add("University Committee Chair Delegate");
                    }
                }
            }
        }*/
        if (KimApiServiceLocator.getRoleService().principalHasRole(emplid, roleIds5, new HashMap<String, String>())) {
            roleList.add("Committee Chair");
        }
        if (KimApiServiceLocator.getRoleService().principalHasRole(emplid, roleIds7, new HashMap<String, String>())) {
            roleList.add("Committee Chair Delegate");
        }
        if (KimApiServiceLocator.getRoleService().principalHasRole(emplid, roleIds8, new HashMap<String, String>())) {
            roleList.add("Final Administrator");
        }
        
        if (KimApiServiceLocator.getRoleService().principalHasRole(emplid, roleIds9, new HashMap<String, String>())) {
            roleList.add(EdoConstants.ROLE_SECONDARY_UNIT_REVIEWER);
        }
        return roleList;
    }

    private IdentityService getIdentityService() {
        return KimApiServiceLocator.getIdentityService();
    }

    private PersonService getPersonService() {
        return KimApiServiceLocator.getPersonService();
    }

    private RoleService getRoleService() {
        return KimApiServiceLocator.getRoleService();
    }

    public List<BigDecimal> getAuthorizedLevels(String principalId, String permissionTemplate) {
        List<Permission> permissions = new LinkedList<Permission>();
        List<BigDecimal> authorizedLevels = new ArrayList<BigDecimal>();

        if(StringUtils.isNotEmpty(principalId) && StringUtils.isNotEmpty(permissionTemplate)) {
            //Get the permissions.
            permissions.addAll(KimApiServiceLocator.getPermissionService().getAuthorizedPermissionsByTemplate(principalId, EdoConstants.EDO_NAME_SPACE, permissionTemplate, new HashMap<String, String>(), new HashMap<String, String>()));

            //Loop through the permissions to get the attributes.
            for (Permission permission : permissions) {
                if (MapUtils.isNotEmpty(permission.getAttributes())) {
                    Map<String, String> attributes = permission.getAttributes();

                    //If the attributes contains the review level, add it to the list.
                    for (String key : attributes.keySet()) {
                        if (StringUtils.equals(key, EDOKimAttributes.EDO_REVIEW_LEVEL)) {
                            authorizedLevels.add(new BigDecimal(attributes.get(key)));
                        }
                    }
                }
            }

            //Sort the list.
            Collections.sort(authorizedLevels);
        }

        return authorizedLevels;
    }

    public boolean isAuthorizedToVote(String principalId, Integer dossierId) {
        boolean isAuthorized = false;
        if (StringUtils.isNotEmpty(principalId) && dossierId != null) {
            //Get the current document header
            DossierProcessDocumentHeader documentHeader = EdoServiceLocator.getDossierProcessDocumentHeaderService().getDossierProcessDocumentHeader(dossierId);
            List<BigDecimal> authorizedEditLevels = getAuthorizedLevels(principalId, EDOPermissionTemplate.EDIT_VOTE_RECORD.getPermissionTemplateName());
            String workflowId = EdoServiceLocator.getEdoDossierService().getEdoDossierById(dossierId.toString()).getWorkflowId();

            if (documentHeader != null) {
                //Get the current nodes
                List<String> currentNodes = KEWServiceLocator.getRouteNodeService().getCurrentRouteNodeNames(documentHeader.getDocumentId());
                Set<String> currentNodesSet = new HashSet<String>();
                currentNodesSet.addAll(currentNodes);
                Collection<EdoReviewLayerDefinition> reviewLayerDefinitions = EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinitions(workflowId, currentNodesSet);

                for (EdoReviewLayerDefinition reviewLayerDefinition : reviewLayerDefinitions) {
                    if (reviewLayerDefinition.getReviewLevel() != null
                                && authorizedEditLevels.contains(reviewLayerDefinition.getReviewLevel())
                                && reviewLayerDefinition.getVoteType() != EdoConstants.VOTE_TYPE_NONE) {
                        return true;
                    }
                }
            }
        }
        return isAuthorized;
    }

    public boolean isAuthorizedToVote(String principalId) {
        boolean isAuthorized = false;

        List<BigDecimal> authorizedEditLevels = getAuthorizedLevels(principalId, EDOPermissionTemplate.EDIT_VOTE_RECORD.getPermissionTemplateName());
        if (CollectionUtils.isNotEmpty(authorizedEditLevels)) {
            isAuthorized = true;
        }
        return isAuthorized;
    }

    public boolean isAuthorizedToUploadReviewLetter(String principalId, Integer dossierId) {
        boolean isAuthorized = false;
        if (StringUtils.isNotEmpty(principalId) && dossierId != null) {
            //Get the current document header
            DossierProcessDocumentHeader documentHeader = EdoServiceLocator.getDossierProcessDocumentHeaderService().getDossierProcessDocumentHeader(dossierId);
            List<BigDecimal> authorizedEditLevels = getAuthorizedLevels(principalId, EDOPermissionTemplate.UPLOAD_REVIEW_LETTER.getPermissionTemplateName());
            String workflowId = EdoServiceLocator.getEdoDossierService().getEdoDossierById(dossierId.toString()).getWorkflowId();

            if (documentHeader != null) {
                //Get the current nodes
                List<String> currentNodes = KEWServiceLocator.getRouteNodeService().getCurrentRouteNodeNames(documentHeader.getDocumentId());
                Set<String> currentNodesSet = new HashSet<String>();
                currentNodesSet.addAll(currentNodes);
                Collection<EdoReviewLayerDefinition> reviewLayerDefinitions = EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinitions(workflowId, currentNodesSet);

                for (EdoReviewLayerDefinition reviewLayerDefinition : reviewLayerDefinitions) {
                    if (reviewLayerDefinition.getReviewLevel() != null
                            && authorizedEditLevels.contains(reviewLayerDefinition.getReviewLevel()) ) {
                        return true;
                    }
                }
            }
        }
        return isAuthorized;
    }

    public boolean isAuthorizedToUploadReviewLetter(String principalId) {
        boolean isAuthorized = false;

        List<BigDecimal> authorizedEditLevels = getAuthorizedLevels(principalId, EDOPermissionTemplate.UPLOAD_REVIEW_LETTER.getPermissionTemplateName());
        if (CollectionUtils.isNotEmpty(authorizedEditLevels)) {
            isAuthorized = true;
        }
        return isAuthorized;
    }

    public boolean isAuthorizedToUploadExternalLetter(String principalId, Integer dossierId) {
        boolean isAuthorized = false;
        String dossierDepartmentId = null;
        String dossierSchoolId = null;
        String dossierCampusId = null;

        if (StringUtils.isNotEmpty(principalId) && dossierId != null) {
            dossierDepartmentId = EdoServiceLocator.getEdoDossierService().getEdoDossierById(dossierId.toString()).getDepartmentID();
            dossierSchoolId    = EdoServiceLocator.getEdoDossierService().getEdoDossierById(dossierId.toString()).getOrganizationCode();
            //dossierCampusId    = EdoServiceLocator.getEdoDossierService().getDossierById(BigDecimal.valueOf(dossierId)).getCampusCode();
            Map<String, String> qualification = new HashMap<String, String>();

            // check department, school and campus attributes on the permission check, in turn
            qualification.put(EDOKimAttributes.ROLE_ADMINISTRATOR_DEPT_ID, dossierDepartmentId);
            boolean isDeptQualified = KimApiServiceLocator.getPermissionService().isAuthorized(principalId, EdoConstants.EDO_NAME_SPACE, EdoConstants.EDO_MANAGE_EXTERNAL_LETTERS, qualification );

            qualification.clear();
            qualification.put(EDOKimAttributes.ROLE_ADMINISTRATOR_SCHOOL_ID, dossierSchoolId);
            boolean isSchoolQualified = KimApiServiceLocator.getPermissionService().isAuthorized(principalId, EdoConstants.EDO_NAME_SPACE, EdoConstants.EDO_MANAGE_EXTERNAL_LETTERS, qualification );

            qualification.clear();
            qualification.put(EDOKimAttributes.ROLE_ADMINISTRATOR_CAMPUS_ID, dossierCampusId);
            boolean isCampusQualified = KimApiServiceLocator.getPermissionService().isAuthorized(principalId, EdoConstants.EDO_NAME_SPACE, EdoConstants.EDO_MANAGE_EXTERNAL_LETTERS, qualification );

            if (isDeptQualified || isSchoolQualified || isCampusQualified) {
                isAuthorized = true;
            }
        }
        return isAuthorized;

    }

    public boolean isAuthorizedToDelegateChair(String principalId) {
        boolean isAuthorized = false;

        List<BigDecimal> authorizedUploadLetterLevels = getAuthorizedLevels(principalId, EDOPermissionTemplate.UPLOAD_REVIEW_LETTER.getPermissionTemplateName());
        List<BigDecimal> authorizedEditVoteRecLevels = getAuthorizedLevels(principalId, EDOPermissionTemplate.EDIT_VOTE_RECORD.getPermissionTemplateName());

        if (CollectionUtils.isNotEmpty(authorizedEditVoteRecLevels) || CollectionUtils.isNotEmpty(authorizedUploadLetterLevels)) {
            isAuthorized = true;
        }

        return isAuthorized;
    }

    public boolean isAuthorizedToEditDossier(String principalId, Integer dossierId) {
        if (dossierId != null && StringUtils.isNotEmpty(principalId)) {
            List<String> authorizedIds = new LinkedList<String>();

            //Get the dossier.
            EdoDossier dossier = EdoServiceLocator.getEdoDossierService().getEdoDossierById(dossierId.toString());
            Principal principal = KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName(dossier.getCandidatePrincipalName());

            if (principal != null) {
                authorizedIds.add(principal.getPrincipalId());
                authorizedIds.addAll(EdoServiceLocator.getEdoMaintenanceService().getCandidateDelegatesEmplIds(principal.getPrincipalId(), EdoConstants.CANDIDATE_DELEGATE_ROLE));
                authorizedIds.addAll(KimApiServiceLocator.getRoleService().getRoleMemberPrincipalIds(EdoConstants.EDO_NAME_SPACE, EDORole.EDO_SUPERUSER.getEdoRole(), new HashMap<String, String>()));
            }

            return (KimApiServiceLocator.getPermissionService().isAuthorized(principalId, EdoConstants.EDO_NAME_SPACE, EdoConstants.EDO_EDIT_DOSSIER_PERMISSION, new HashMap<String, String>())
                    && authorizedIds.contains(principalId));
        }

        return false;
    }

    public boolean isAuthorizedToEditDossier(String principalId, EdoDossierBo dossier) {
        if (dossier != null && StringUtils.isNotEmpty(principalId)) {
            List<String> authorizedIds = new LinkedList<String>();

            //Get the dossier.
            Principal principal = KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName(dossier.getCandidatePrincipalName());

            if (principal != null) {
                authorizedIds.add(principal.getPrincipalId());
                authorizedIds.addAll(EdoServiceLocator.getEdoMaintenanceService().getCandidateDelegatesEmplIds(principal.getPrincipalId(), EdoConstants.CANDIDATE_DELEGATE_ROLE));
                authorizedIds.addAll(KimApiServiceLocator.getRoleService().getRoleMemberPrincipalIds(EdoConstants.EDO_NAME_SPACE, EDORole.EDO_SUPERUSER.getEdoRole(), new HashMap<String, String>()));
            }

            return (KimApiServiceLocator.getPermissionService().isAuthorized(principalId, EdoConstants.EDO_NAME_SPACE, EdoConstants.EDO_EDIT_DOSSIER_PERMISSION, new HashMap<String, String>())
                            && authorizedIds.contains(principalId));
        }

        return false;
    }
    public boolean isAuthorizedToSubmitSupplemental(String principalId) {
    	 return permissionToSubmit(principalId);
    	
    }
    
    public boolean isAuthorizedToSubmitReconsider(String principalId) {
   	 return permissionToSubmit(principalId);
   	
   }

    public boolean isAuthorizedToEditAoe(String principalId, Integer dossierId) {
        if (dossierId != null && StringUtils.isNotEmpty(principalId)) {
            EdoDossier dossier = EdoServiceLocator.getEdoDossierService().getEdoDossierById(dossierId.toString());

            if (dossier != null
                        && StringUtils.equals(dossier.getDossierStatus(), EdoConstants.DOSSIER_STATUS.OPEN)
                        && isAuthorizedToEditDossier(principalId, EdoDossierBo.from(dossier))) {
                return true;
            }
        }

        return false;
    }

    public boolean isAuthorizedToTakeDocumentAction(String principalId, Integer dossierId, ActionType actionType) {
        DossierProcessDocumentHeader documentHeader = EdoServiceLocator.getDossierProcessDocumentHeaderService().getDossierProcessDocumentHeader(dossierId);
        WorkflowDocument wfd = null;
        Set<ActionRequestType> actionRequests = new HashSet<ActionRequestType>();
        Set<ActionType> validActions = new HashSet<ActionType>();
        boolean isCandidateLevel = false;

        if (documentHeader != null) {
            wfd = WorkflowDocumentFactory.loadDocument(principalId, documentHeader.getDocumentId());
            if (wfd != null) {
                actionRequests = wfd.getRequestedActions().getRequestedActions();
                validActions = wfd.getValidActions().getValidActions();

                DocumentRouteHeaderValue routeHeaderValue = KEWServiceLocator.getRouteHeaderService().getRouteHeader(documentHeader.getDocumentId());
                List<RouteNodeInstance> initialNodes = routeHeaderValue.getInitialRouteNodeInstances();

                for (RouteNodeInstance node : initialNodes) {
                    if (wfd.getCurrentNodeNames().contains(node.getName())) {
                        isCandidateLevel = true;
                        break;
                    }
                }
            }
        }

        //Perform some additional checking.
        if (actionType.equals(ActionType.ROUTE)) {
            if (documentHeader == null) {
                return permissionToSubmit(principalId);
            } else {
                if (wfd != null
                        && validActions.contains(ActionType.ROUTE)
                        && permissionToSubmit(principalId)
                        && (wfd.checkStatus(DocumentStatus.INITIATED)
                                    || wfd.checkStatus(DocumentStatus.SAVED))
                                    || (isCandidateLevel
                                                && actionRequests.contains(ActionRequestType.APPROVE))) {
                    return true;
                }
            }
        } else if (actionType.equals(ActionType.CANCEL)) {
            if (wfd != null
                    && validActions.contains(ActionType.CANCEL)
                    && permissionToCancel(principalId, documentHeader)) {
                return true;
            }
        } else if (actionType.equals(ActionType.APPROVE)) {
            if (wfd != null
                    && actionRequests.contains(ActionRequestType.APPROVE)
                    && !(wfd.checkStatus(DocumentStatus.INITIATED)
                                || wfd.checkStatus(DocumentStatus.SAVED))
                                && !isCandidateLevel){
                return true;
            }
        } else if (actionType.equals(ActionType.RETURN_TO_PREVIOUS)) {
            if (wfd != null
                    && validActions.contains(ActionType.RETURN_TO_PREVIOUS)
                    && wfd.checkStatus(DocumentStatus.ENROUTE)
                    && CollectionUtils.isNotEmpty(wfd.getCurrentNodeNames())
                    && wfd.getCurrentNodeNames().contains("LevelX")
                    && permissionToRecall(principalId)) {
                return true;

                //TODO: LevelX should be changed to a permission attribute check or something similar.
            }
        } else if (actionType.equals(ActionType.SU_ROUTE_NODE_APPROVE)) {
            if (wfd != null
                    && validActions.contains(ActionType.SU_ROUTE_NODE_APPROVE)){
                return true;
            }
        } else if (actionType.equals(ActionType.SU_RETURN_TO_PREVIOUS)) {
            if (wfd != null
                    && validActions.contains(ActionType.SU_RETURN_TO_PREVIOUS)) {
                return true;
            }
        }

        return false;
    }

    public boolean isAuthorizedToVote(String principalId, BigDecimal thisLevel) {
        boolean isAuthorized = false;
        if (StringUtils.isNotEmpty(principalId)) {
            List<BigDecimal> authorizedEditLevels = getAuthorizedLevels(principalId, EDOPermissionTemplate.EDIT_VOTE_RECORD.getPermissionTemplateName());
            if (authorizedEditLevels.contains(thisLevel)) {
                return true;
            }
        }
        return isAuthorized;
    }

    //
    // wrapper methods
    //
    public boolean isAuthorizedToEditDossier_W(String principalId, Integer dossierId) {
    	boolean isAuthorized = false;

        List<String> allPrincipals = EdoContext.getPrincipalDelegators(principalId);

        for (String principal : allPrincipals) {
            isAuthorized = isAuthorized || isAuthorizedToEditDossier(principal, dossierId);
        }
        return isAuthorized;
    	
    }
    public boolean isAuthorizedToEditDossier_W(String principalId, EdoDossierBo dossier) {
    	boolean isAuthorized = false;

        List<String> allPrincipals = EdoContext.getPrincipalDelegators(principalId);

        for (String principal : allPrincipals) {
            isAuthorized = isAuthorized || isAuthorizedToEditDossier(principal, dossier);
        }
        return isAuthorized;
    	
    }
    public boolean isAuthorizedToEditAoe_W(String principalId, Integer dossierId) {
    	boolean isAuthorized = false;

        List<String> allPrincipals = EdoContext.getPrincipalDelegators(principalId);

        for (String principal : allPrincipals) {
            isAuthorized = isAuthorized || isAuthorizedToEditAoe(principal, dossierId);
        }
        return isAuthorized;
    	
    }
    public List<BigDecimal> getAuthorizedLevels_W(String principalId, String permissionTemplate) {
    	 List<BigDecimal> authorizedLevels = new ArrayList<BigDecimal>();

        List<String> allPrincipals = EdoContext.getPrincipalDelegators(principalId);

        for (String principal : allPrincipals) {
        	authorizedLevels.addAll(getAuthorizedLevels(principal, permissionTemplate));
        }
        return authorizedLevels;
    	
    }

    public boolean isAuthorizedToVote_W(String principalId, Integer dossierId) {
        boolean isAuthorized = false;

        List<String> allPrincipals = EdoContext.getPrincipalDelegators(principalId);

        for (String principal : allPrincipals) {
            isAuthorized = isAuthorized || isAuthorizedToVote(principal, dossierId);
        }
        return isAuthorized;
    }
    public boolean isAuthorizedToVote_W(String principalId, BigDecimal thisLevel) {
    	 boolean isAuthorized = false;

         List<String> allPrincipals = EdoContext.getPrincipalDelegators(principalId);

         for (String principal : allPrincipals) {
             isAuthorized = isAuthorized || isAuthorizedToVote(principal, thisLevel);
         }
         return isAuthorized;
    	
    }
    public boolean isAuthorizedToVote_W(String principalId) {
    	 boolean isAuthorized = false;

         List<String> allPrincipals = EdoContext.getPrincipalDelegators(principalId);

         for (String principal : allPrincipals) {
             isAuthorized = isAuthorized || isAuthorizedToVote(principal);
         }
         return isAuthorized;
    	
    }
    public boolean isAuthorizedToTakeDocumentAction_W(String principalId, Integer dossierId, ActionType actionType) {
    	 boolean isAuthorized = false;

         List<String> allPrincipals = EdoContext.getPrincipalDelegators(principalId);

         for (String principal : allPrincipals) {
             isAuthorized = isAuthorized || isAuthorizedToTakeDocumentAction(principal, dossierId, actionType);
         }
         return isAuthorized;
    	
    }
    public boolean isAuthorizedToUploadReviewLetter_W(String principalId, Integer dossierId) {
    	 boolean isAuthorized = false;

         List<String> allPrincipals = EdoContext.getPrincipalDelegators(principalId);

         for (String principal : allPrincipals) {
             isAuthorized = isAuthorized || isAuthorizedToUploadReviewLetter(principal, dossierId);
         }
         return isAuthorized;
    }
    public boolean isAuthorizedToUploadReviewLetter_W(String principalId) {
    	 boolean isAuthorized = false;

         List<String> allPrincipals = EdoContext.getPrincipalDelegators(principalId);

         for (String principal : allPrincipals) {
             isAuthorized = isAuthorized || isAuthorizedToUploadReviewLetter(principal);
         }
         return isAuthorized;
    	
    }
    public boolean isAuthorizedToUploadExternalLetter_W(String principalId, Integer dossierId) {
    	 boolean isAuthorized = false;

         List<String> allPrincipals = EdoContext.getPrincipalDelegators(principalId);

         for (String principal : allPrincipals) {
             isAuthorized = isAuthorized || isAuthorizedToUploadExternalLetter(principal, dossierId);
         }
         return isAuthorized;
    	
    }
    public boolean isAuthorizedToSubmitSupplemental_W(String principalId) {
    	 boolean isAuthorized = false;
    	 List<String> allPrincipals = EdoContext.getPrincipalDelegators(principalId);

         for (String principal : allPrincipals) {
             isAuthorized = isAuthorized || isAuthorizedToSubmitSupplemental(principal);
         }
         return isAuthorized;
    	
    }
    
    public boolean isAuthorizedToSubmitReconsider_W(String principalId) {
   	 boolean isAuthorized = false;
   	 List<String> allPrincipals = EdoContext.getPrincipalDelegators(principalId);

        for (String principal : allPrincipals) {
            isAuthorized = isAuthorized || isAuthorizedToSubmitSupplemental(principal);
        }
        return isAuthorized;
   	
   }
    

    //
    // private methods
    //
    private boolean permissionToSubmit(String principalId) {
        return KimApiServiceLocator.getPermissionService().isAuthorized(principalId, EdoConstants.EDO_NAME_SPACE, EdoConstants.EDO_SUBMIT_DOSSIER_PERMISSION, new HashMap<String, String>());
    }
    private boolean permissionToCancel(String principalId, DossierProcessDocumentHeader documentHeader) {
        if (documentHeader != null && StringUtils.isNotEmpty(principalId)) {
            Map<String, String> permissionDetails = new HashMap<String, String>();
            permissionDetails.put(KewApiConstants.DOCUMENT_TYPE_NAME_DETAIL, documentHeader.getDocumentTypeName());
            permissionDetails.put(KewApiConstants.DOCUMENT_STATUS_DETAIL, DocumentStatus.ENROUTE.getCode());

            return KimApiServiceLocator.getPermissionService().isAuthorizedByTemplate(principalId, EdoConstants.EDO_NAME_SPACE, EDOPermissionTemplate.CANCEL_DOCUMENT.getPermissionTemplateName(), permissionDetails, new HashMap<String, String>());
        }

        return false;
    }

    private boolean permissionToRecall(String principalId) {
        return KimApiServiceLocator.getPermissionService().isAuthorized(principalId, EdoConstants.EDO_NAME_SPACE, EdoConstants.EDO_RECALL_DOSSIER_PERMISSION, new HashMap<String, String>());
    }
}
