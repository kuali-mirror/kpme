package org.kuali.kpme.edo.workflow.postprocessor;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.edo.dossier.EdoDossierBo;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.kpme.edo.util.EdoUtils;
import org.kuali.kpme.edo.util.TagSupport;
import org.kuali.kpme.edo.workflow.DossierProcessDocumentHeader;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kew.api.action.DocumentActionParameters;
import org.kuali.rice.kew.api.action.WorkflowDocumentActionsService;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.kew.framework.postprocessor.DocumentRouteStatusChange;
import org.kuali.rice.kew.framework.postprocessor.ProcessDocReport;
import org.kuali.rice.kew.postprocessor.DefaultPostProcessor;
import org.kuali.rice.kim.api.group.Group;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.ksb.api.KsbApiServiceLocator;

import javax.xml.namespace.QName;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;



public class DossierPostProcessor extends DefaultPostProcessor {

    @Override
    public ProcessDocReport doRouteStatusChange(DocumentRouteStatusChange statusChangeEvent) throws Exception {
        ProcessDocReport pdr = null;
        DossierProcessDocumentHeader document = EdoServiceLocator.getDossierProcessDocumentHeaderService().getDossierProcessDocumentHeader(statusChangeEvent.getDocumentId());
        if (document != null) {
        	pdr = super.doRouteStatusChange(statusChangeEvent);
            DocumentStatus newDocumentStatus = DocumentStatus.fromCode(statusChangeEvent.getNewRouteStatus());
            DocumentStatus oldDocumentStatus = DocumentStatus.fromCode(statusChangeEvent.getOldRouteStatus());
            updateDossierProcessDocumentHeaderStatus(document, newDocumentStatus);
            //if the edoc is not TenureSupplemental or PromotionSupplemental execute the below part
        	if(!(StringUtils.equals(document.getDocumentTypeName(), "TenureSupplementalProcessDocument") || StringUtils.equals(document.getDocumentTypeName(), "PromotionSupplementalProcessDocument"))) {
        	
            //Update the dossier table
            EdoDossierBo dossier = EdoServiceLocator.getEdoDossierService().getDossier(document.getDocumentId());
            if (DocumentStatus.FINAL.equals(newDocumentStatus)) {
            	dossier.setDossierStatus(EdoConstants.DOSSIER_STATUS.SUBMITTED);
                Principal approver = KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName(dossier.getCandidatePrincipalname());
            	//make a permission check
            	if(KimApiServiceLocator.getPermissionService().isAuthorized(approver.getPrincipalId(), EdoConstants.EDO_NAME_SPACE, EdoConstants.EDO_SUPER_USER_APPROVE_TENURE_SUPP_PERMISSION, new HashMap<String, String>()) || 
            	   KimApiServiceLocator.getPermissionService().isAuthorized(approver.getPrincipalId(), EdoConstants.EDO_NAME_SPACE, EdoConstants.EDO_SUPER_USER_APPROVE_PROMOTION_SUPP_PERMISSION, new HashMap<String, String>())) {
                       List<DossierProcessDocumentHeader> pendingSuppDocHeaders = EdoServiceLocator.getDossierProcessDocumentHeaderService().getPendingSupplementalDocuments(Integer.valueOf(dossier.getEdoDossierID()));
                        if (CollectionUtils.isNotEmpty(pendingSuppDocHeaders)) {
                        for(DossierProcessDocumentHeader pendingSuppDocHeader : pendingSuppDocHeaders) {
	                        	WorkflowDocumentActionsService documentActionsService = (WorkflowDocumentActionsService)KsbApiServiceLocator.getMessageHelper().getServiceAsynchronously(new QName(KewApiConstants.Namespaces.KEW_NAMESPACE_2_0, KewApiConstants.ServiceNames.WORKFLOW_DOCUMENT_ACTIONS_SERVICE_SOAP), "EDO");
	                        	DocumentActionParameters.Builder builder = DocumentActionParameters.Builder.create(pendingSuppDocHeader.getDocumentId(), approver.getPrincipalId());
	                            builder.setAnnotation("Blanket Approving pending Supplemental Documents");
	                        	documentActionsService.superUserBlanketApprove( builder.build(), true);
                        	                      	
							}

						}
					}
                 
                        //send an adhoc email to the final_administrators group
                        //fetch all the people in the final_administrators group 
                        Group group =KimApiServiceLocator.getGroupService().getGroupByNamespaceCodeAndName(EdoConstants.EDO_NAME_SPACE, EdoConstants.EDO_FINAL_ADMINSTRATORS_GRP);
                        if (!KimApiServiceLocator.getGroupService()
        						.getMemberPrincipalIds(group.getId()).isEmpty()) {
                        List<String> principalIds = KimApiServiceLocator.getGroupService().getMemberPrincipalIds(group.getId());
                        String subject = "Workflow Notification";
                        String content = "This is an FYI email notification : "
                        		+ "Final reviewer voting for Candidate  " + dossier.getCandidatePrincipalId()
    							+ " has been completed, you can review their dossier at http://edossier.iu.edu.\n\n"
    							+ "For additional help or feedback, email <edossier@indiana.edu>.";        				
                        String testEmailAddresses = new String();
						if (!TagSupport.isNonProductionEnvironment()) {
							for (String principalId : principalIds) {
								// notify each person in the final admin group
								Person person = KimApiServiceLocator
										.getPersonService().getPerson(
												principalId);

								EdoServiceLocator.getEdoNotificationService()
										.sendMail(person.getEmailAddress(),
												"edossier@indiana.edu",
												subject, content);
							}
						} else {
							testEmailAddresses = ConfigContext
									.getCurrentContextConfig()
									.getProperty(
											EdoConstants.TEST_EMAIL_NOTIFICATION);
							EdoServiceLocator.getEdoNotificationService()
									.sendMail(testEmailAddresses,
											"edossier@indiana.edu", subject,
											content);
						}
                        	
                     }
                        
             
                      
            } else if (DocumentStatus.INITIATED.equals(newDocumentStatus) || DocumentStatus.SAVED.equals(newDocumentStatus)) {
                if(!(StringUtils.equals(EdoConstants.DOSSIER_STATUS.RECONSIDERATION, dossier.getDossierStatus()))) {
            	dossier.setDossierStatus(EdoConstants.DOSSIER_STATUS.OPEN);
                }
            }  /* else if ( DocumentStatus.INITIATED.equals(oldDocumentStatus) || DocumentStatus.SAVED.equals(oldDocumentStatus)) {
                if(!(StringUtils.equals(EdoConstants.DOSSIER_STATUS.RECONSIDERATION, dossier.getDossierStatus()))) {
            	dossier.setDossierStatus(EdoConstants.DOSSIER_STATUS.PENDING);
                }
            } */
            else if (DocumentStatus.ENROUTE.equals(newDocumentStatus) && dossier.getDossierStatus().equals(EdoConstants.DOSSIER_STATUS.PENDING)) {
                if(!(StringUtils.equals(EdoConstants.DOSSIER_STATUS.RECONSIDERATION, dossier.getDossierStatus()))) {
            	dossier.setDossierStatus(EdoConstants.DOSSIER_STATUS.SUBMITTED);
                }
            }
            //dossier.setLastUpdated(EdoUtils.getNow());
            EdoServiceLocator.getEdoDossierService().saveOrUpdate(EdoDossierBo.to(dossier));
            
        	}
        }

        return pdr;
       
    }

    private void updateDossierProcessDocumentHeaderStatus(DossierProcessDocumentHeader dossierProcessDocumentHeader, DocumentStatus newDocumentStatus) {
        dossierProcessDocumentHeader.setDocumentStatus(newDocumentStatus.getCode());
        EdoServiceLocator.getDossierProcessDocumentHeaderService().saveOrUpdate(dossierProcessDocumentHeader);
    }
}
