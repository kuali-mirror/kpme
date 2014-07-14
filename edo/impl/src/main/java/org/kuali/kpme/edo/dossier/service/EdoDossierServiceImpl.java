package org.kuali.kpme.edo.dossier.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.kpme.edo.api.dossier.EdoDossier;
import org.kuali.kpme.edo.api.dossier.type.EdoDossierType;
import org.kuali.kpme.edo.api.reviewlayerdef.EdoReviewLayerDefinition;
import org.kuali.kpme.edo.api.vote.EdoVoteRecord;
import org.kuali.kpme.edo.dossier.EdoDossierBo;
import org.kuali.kpme.edo.dossier.dao.EdoDossierDao;
import org.kuali.kpme.edo.reviewlayerdef.EdoReviewLayerDefinitionBo;
import org.kuali.kpme.edo.reviewlayerdef.EdoSuppReviewLayerDefinition;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.supplemental.EdoSupplementalTracking;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.kpme.edo.util.EdoContext;
import org.kuali.kpme.edo.util.EdoUtils;
import org.kuali.kpme.edo.util.TagSupport;
import org.kuali.kpme.edo.workflow.DossierProcessDocumentHeader;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kew.api.WorkflowDocumentFactory;
import org.kuali.rice.kew.api.action.ActionType;
import org.kuali.rice.kew.api.action.MovePoint;
import org.kuali.rice.kew.api.document.DocumentContentUpdate;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.kew.api.document.DocumentUpdate;
import org.kuali.rice.kew.api.exception.InvalidActionTakenException;
import org.kuali.rice.kew.engine.node.RouteNodeInstance;
import org.kuali.rice.kew.routeheader.DocumentRouteHeaderValue;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.util.ObjectUtils;

public class EdoDossierServiceImpl implements EdoDossierService {

    private static final Logger LOG = Logger.getLogger(EdoDossierServiceImpl.class);
    private EdoDossierDao edoDossierDao;

    public EdoDossier getCurrentDossierPrincipalName( String candidatePrincipalName ) {
    	EdoDossierBo edoDossierBo = edoDossierDao.getCurrentDossierPrincipalName(candidatePrincipalName);
    	
    	if ( edoDossierBo == null){
    		return null;
    	}
    	
    	EdoDossier.Builder builder = EdoDossier.Builder.create(edoDossierBo);
    	
    	return builder.build();
    }

    public List<EdoDossier> getDossierList() {
    	
    	return ModelObjectUtils.transform(edoDossierDao.getDossierList(), EdoDossierBo.toImmutable);
    }

    public EdoDossier getEdoDossierById(String edoDossierID) {
    	EdoDossierBo edoDossierBo = edoDossierDao.getEdoDossierById(edoDossierID);
    	
    	if ( edoDossierBo == null){
    		return null;
    	}
    	
    	EdoDossier.Builder builder = EdoDossier.Builder.create(edoDossierBo);
    	
    	return builder.build();
    }


    protected boolean initiateWorkflowDocument(String principalId, Integer dossierId, String dossierType) {
        //this is returning null
    	EdoDossierType dossierTypeObj = EdoServiceLocator.getEdoDossierTypeService().getEdoDossierTypeByName(dossierType);
        Principal principal = KimApiServiceLocator.getIdentityService().getPrincipal(principalId);
        String workflowId = EdoServiceLocator.getEdoDossierService().getEdoDossierById(dossierId.toString()).getWorkflowId();

        boolean routed = false;

        if (ObjectUtils.isNotNull(dossierTypeObj) && ObjectUtils.isNotNull(principal)) {
            WorkflowDocument workflowDocument = createWorkflowDocument(principalId, dossierId.toString(), dossierTypeObj.getDocumentTypeName());
            workflowDocument.setTitle("Dossier - candidate: " + principal.getPrincipalName());

            //Route the document.
            workflowDocument.route(buildRouteDescription(workflowId, workflowDocument.getCurrentNodeNames(), null));

            //Update that we have finished the routing process.
            routed = true;
        }
        return routed;
    }
    //initiate work flow document for Supplemental Document
    protected boolean initiateSuppWorkflowDocument(String principalId, Integer dossierId, String dossierType, Collection<String> authorizedNodes ) {
      
    	EdoDossierType dossierTypeObj = EdoServiceLocator.getEdoDossierTypeService().getEdoDossierTypeByName(dossierType);
        Principal principal = KimApiServiceLocator.getIdentityService().getPrincipal(principalId);
        String workflowId = EdoServiceLocator.getEdoDossierService().getEdoDossierById(dossierId.toString()).getWorkflowId();

        boolean routed = false;

        if (ObjectUtils.isNotNull(dossierTypeObj) && ObjectUtils.isNotNull(principal)) {
            WorkflowDocument workflowDocument = createSuppWorkflowDocument(principalId, dossierId.toString(), dossierTypeObj.getDocumentTypeName(), authorizedNodes);
            workflowDocument.setTitle("Supplemental - candidate: " + principal.getPrincipalName());

            //Route the document.
            workflowDocument.route(buildRouteDescription(workflowId, workflowDocument.getCurrentNodeNames(), null));

            //Update that we have finished the routing process.
            routed = true;
        }
        return routed;
    }
    protected boolean initiateReconsiderWorkflowDocument(String principalId, Integer dossierId, String dossierType, Collection<EdoReviewLayerDefinition> moveNodes) {
    	EdoDossierType dossierTypeObj = EdoServiceLocator.getEdoDossierTypeService().getEdoDossierTypeByName(dossierType);
        Principal principal = KimApiServiceLocator.getIdentityService().getPrincipal(principalId);
        boolean routed = false;

        if (ObjectUtils.isNotNull(dossierTypeObj) && ObjectUtils.isNotNull(principal)) {
            WorkflowDocument workflowDocument = createReconsiderWorkflowDocument(principalId, dossierId.toString(), dossierTypeObj.getDocumentTypeName(), moveNodes);
            workflowDocument.setTitle("Reconsider Dossier - candidate: " + principal.getPrincipalName());

            //Route the document.
            //workflowDocument.move(MovePoint.create(SeqSetup.WORKFLOW_DOCUMENT_NODE, 1), "");
            //authorizedNodes - this collection will always be of size one.
            for(EdoReviewLayerDefinition moveNode : moveNodes) {
                //workflowDocument.move(MovePoint.create(EdoConstants.ROUTING_NODE_NAMES.INITIATED, moveNode.getRouteLevel().intValue()), "");
            	//Initiated document has to be saved before its routed using the move command
            	//workflowDocument.saveDocument("");
            	workflowDocument.move(MovePoint.create(EdoConstants.ROUTING_NODE_NAMES.INITIATED, new Integer(moveNode.getRouteLevel())), "This document is routed/moved to" +  moveNode.getNodeName());
            }
            //Update that we have finished the routing process.
            routed = true;
        }
        return routed;
    }

    private WorkflowDocument createWorkflowDocument(String principalId, String dossierId, String documentTypeName) {
        EdoDossier dossier = EdoServiceLocator.getEdoDossierService().getEdoDossierById(dossierId);
        DocumentUpdate.Builder documentUpdateBuilder = DocumentUpdate.Builder.create();
        documentUpdateBuilder.setApplicationDocumentId(dossierId);
        DocumentContentUpdate.Builder documentContentBuilder = DocumentContentUpdate.Builder.create();
        //TODO: need to uncomment out this following line when working on workflow
        //documentContentBuilder.setApplicationContent(EdoWorkflowUtils.generateApplicationContent(dossier));
        
        WorkflowDocument workflowDocument = WorkflowDocumentFactory.createDocument(principalId, documentTypeName, documentUpdateBuilder.build(), documentContentBuilder.build());

        //Add the document to the header table.
        DossierProcessDocumentHeader documentHeader = new DossierProcessDocumentHeader();
        documentHeader.setDocumentId(workflowDocument.getDocumentId());
        documentHeader.setDocumentStatus(workflowDocument.getStatus().getCode());
        documentHeader.setEdoDossierId(dossierId);
        documentHeader.setPrincipalId(principalId);
        documentHeader.setDocumentTypeName(documentTypeName);
        EdoServiceLocator.getDossierProcessDocumentHeaderService().saveOrUpdate(documentHeader);

        //Update the dossier table
        //dossier.setDocumentID(workflowDocument.getDocumentId());
        //dossier.setDossierStatus(EdoConstants.DOSSIER_STATUS.SUBMITTED);
        // dossier.setDossierStatus(EdoConstants.DOSSIER_STATUS.PENDING);
        //dossier.setLastUpdated(EdoUtils.getNow());
        Principal submitter = KimApiServiceLocator.getIdentityService().getPrincipal(EdoContext.getPrincipalId());
        //dossier.setUpdatedBy(submitter.getPrincipalName());
        EdoServiceLocator.getEdoDossierService().saveOrUpdate(dossier);

        return workflowDocument;
    }
    
    private WorkflowDocument createSuppWorkflowDocument(String principalId, String dossierId, String documentTypeName, Collection<String> authorizedNodes) {
        EdoDossier dossier = EdoServiceLocator.getEdoDossierService().getEdoDossierById(dossierId);
        DocumentUpdate.Builder documentUpdateBuilder = DocumentUpdate.Builder.create();
        documentUpdateBuilder.setApplicationDocumentId(dossierId);
        DocumentContentUpdate.Builder documentContentBuilder = DocumentContentUpdate.Builder.create();
        //TODO: need to uncomment out this following line when working on workflow
        //documentContentBuilder.setApplicationContent(EdoWorkflowUtils.generateSuppDocApplicationContent(dossier, authorizedNodes));
        WorkflowDocument workflowDocument = WorkflowDocumentFactory.createDocument(principalId, documentTypeName, documentUpdateBuilder.build(), documentContentBuilder.build());

        //Add the document to the header table.
        DossierProcessDocumentHeader documentHeader = new DossierProcessDocumentHeader();
        documentHeader.setDocumentId(workflowDocument.getDocumentId());
        documentHeader.setDocumentStatus(workflowDocument.getStatus().getCode());
        documentHeader.setEdoDossierId(dossierId);
        documentHeader.setPrincipalId(principalId);
        documentHeader.setDocumentTypeName(documentTypeName);
        EdoServiceLocator.getDossierProcessDocumentHeaderService().saveOrUpdate(documentHeader);

        return workflowDocument;
    }
    
    private WorkflowDocument createReconsiderWorkflowDocument(String principalId, String dossierId, String documentTypeName, Collection<EdoReviewLayerDefinition> authorizedNodes) {
        EdoDossier dossier = EdoServiceLocator.getEdoDossierService().getEdoDossierById(dossierId);
        DocumentUpdate.Builder documentUpdateBuilder = DocumentUpdate.Builder.create();
        documentUpdateBuilder.setApplicationDocumentId(dossierId);
        DocumentContentUpdate.Builder documentContentBuilder = DocumentContentUpdate.Builder.create();
      //TODO: need to uncomment out this following line when working on workflow
        //documentContentBuilder.setApplicationContent(EdoWorkflowUtils.generateReconsiderApplicationContent(dossier, authorizedNodes));
        WorkflowDocument workflowDocument = WorkflowDocumentFactory.createDocument(principalId, documentTypeName, documentUpdateBuilder.build(), documentContentBuilder.build());
       
        //Add the document to the header table.
        DossierProcessDocumentHeader documentHeader = new DossierProcessDocumentHeader();
        documentHeader.setDocumentId(workflowDocument.getDocumentId());
        documentHeader.setDocumentStatus(workflowDocument.getStatus().getCode());
        documentHeader.setEdoDossierId(dossierId);
        documentHeader.setPrincipalId(principalId);
        documentHeader.setDocumentTypeName(documentTypeName);
        EdoServiceLocator.getDossierProcessDocumentHeaderService().saveOrUpdate(documentHeader);
        
        //Update the dossier table
        //dossier.setDocumentID(workflowDocument.getDocumentId());
        //dossier.setLastUpdated(EdoUtils.getNow());
        Principal submitter = KimApiServiceLocator.getIdentityService().getPrincipal(EdoContext.getPrincipalId());
        //dossier.setUpdatedBy(submitter.getPrincipalName());
        EdoServiceLocator.getEdoDossierService().saveOrUpdate(dossier);

        return workflowDocument;
    }

    public boolean routeDocument(String principalId, Integer dossierId, String dossierType) {
        WorkflowDocument workflowDocument = null;
        boolean routed = false;
        if (ObjectUtils.isNotNull(dossierId)) {
            EdoDossier dossier = EdoServiceLocator.getEdoDossierService().getEdoDossierById(dossierId.toString());
            String workflowId = EdoServiceLocator.getEdoDossierService().getEdoDossierById(dossierId.toString()).getWorkflowId();

            //Get the document header.
            DossierProcessDocumentHeader documentHeader = EdoServiceLocator.getDossierProcessDocumentHeaderService().getDossierProcessDocumentHeader(dossierId);
            boolean isResubmission = false;
            String candidatePrincipalId = EdoServiceLocator.getEdoDossierService().getEdoDossierById(dossierId.toString()).getCandidatePrincipalName();
            Principal candidatePrincipal = KimApiServiceLocator.getIdentityService().getPrincipal(candidatePrincipalId);

            if (ObjectUtils.isNotNull(documentHeader)) {
                //Get the document.
                workflowDocument = WorkflowDocumentFactory.loadDocument(EdoContext.getPrincipalId(), documentHeader.getDocumentId());
                DocumentRouteHeaderValue routeHeaderValue = KEWServiceLocator.getRouteHeaderService().getRouteHeader(documentHeader.getDocumentId());
                List<RouteNodeInstance> initialNodes = routeHeaderValue.getInitialRouteNodeInstances();
                for (RouteNodeInstance node : initialNodes) {
                    if (workflowDocument.getCurrentNodeNames().contains(node.getName())
                                && documentHeader.getDocumentStatus().equals(DocumentStatus.ENROUTE.getCode())) {
                        isResubmission = true;
                        break;
                    }
                }
                //Route the document.
                if (workflowDocument.checkStatus(DocumentStatus.INITIATED) || workflowDocument.checkStatus(DocumentStatus.SAVED)) {
                    workflowDocument.route(buildRouteDescription(workflowId, workflowDocument.getCurrentNodeNames(), ActionType.ROUTE));
                   
                    
                } else {
                    Person candidatePerson = KimApiServiceLocator.getPersonService().getPerson(candidatePrincipalId);

                    // send candidate FYI email here, if appropriate
                    if (workflowDocument.getCurrentNodeNames().contains(EdoConstants.ROUTING_NODE_NAMES.LEVELX)) {
                        LOG.info("EdoDossierService.routeDocument: Notifying candidate [" + candidatePrincipal.getPrincipalName() + "] of chair sign-off");
                        if (!TagSupport.isNonProductionEnvironment()) {
                            EdoServiceLocator.getEdoNotificationService().sendMail(candidatePerson.getEmailAddress(),
                                "edossier@indiana.edu", "Workflow Notification",
                                "This is an FYI email notification: Your Department Chair has provided the Checklist Sign-off and your dossier has been submitted for review.\n\n" +
                                "For additional help or feedback, email <edossier@indiana.edu>.");
                        } else {
                            LOG.info("In non-production environment, not sending notification.");
                        }
                    }
                    //Update the dossier
                    workflowDocument.approve(buildRouteDescription(workflowId, workflowDocument.getCurrentNodeNames(), ActionType.APPROVE));
                    //i think its here i have to check for reconsider routing or parent dossier routing
                    //debug this tomorrow
                    if(StringUtils.equals(dossier.getDossierStatus(), EdoConstants.DOSSIER_STATUS.RECONSIDERATION)) {
                    	//dossier.setDossierStatus(EdoConstants.DOSSIER_STATUS.RECONSIDERATION);
                    }
                    else {
                        //dossier.setDossierStatus(EdoConstants.DOSSIER_STATUS.SUBMITTED);
                    }
                    //dossier.setLastUpdated(EdoUtils.getNow());
                    Principal submitter = KimApiServiceLocator.getIdentityService().getPrincipal(EdoContext.getPrincipalId());
                    //dossier.setUpdatedBy(submitter.getPrincipalName());
                    EdoServiceLocator.getEdoDossierService().saveOrUpdate(dossier);
                }

                /* if (isResubmission) {
                    //Update the dossier
                    EdoDossier dossier = EdoServiceLocator.getEdoDossierService().getDossierById(new BigDecimal(dossierId));
                    dossier.setDossierStatus(EdoConstants.DOSSIER_STATUS.PENDING);
                    dossier.setLastUpdated(EdoUtils.getNow());
                    Principal submitter = KimApiServiceLocator.getIdentityService().getPrincipal(EdoContext.getPrincipalId());
                    dossier.setUpdatedBy(submitter.getPrincipalName());
                    EdoServiceLocator.getEdoDossierService().saveOrUpdate(dossier);
                }  */

                // update any pending review letters
                for (String node : workflowDocument.getCurrentNodeNames() ) {
                    EdoReviewLayerDefinition rld = EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinition(dossier.getWorkflowId(), node);
                    if (EdoServiceLocator.getEdoItemService().isReviewLetterPendingRoute(dossierId.toString(), rld.getId().toString())) {
                        EdoServiceLocator.getEdoItemService().updateLetterAsLevelRouted(dossierId.toString(), rld.getId().toString());
                    }
                }
                //Update that we have finished the routing process.
                routed = true;
            } else {
                routed = this.initiateWorkflowDocument(principalId, dossierId, dossierType);
            }
        }
        return routed;
    }

    public boolean superUserAction(String principalId, Integer dossierId, String dossierType, ActionType superUserAction, String node) {
        WorkflowDocument workflowDocument = null;
        boolean routed = false;
        if (ObjectUtils.isNotNull(dossierId)) {
            //Get the document header.
            DossierProcessDocumentHeader documentHeader = EdoServiceLocator.getDossierProcessDocumentHeaderService().getDossierProcessDocumentHeader(dossierId);

            if (ObjectUtils.isNotNull(documentHeader) && StringUtils.isNotEmpty(node)) {
                //Route the document.
                if (ActionType.SU_APPROVE.equals(superUserAction)) {
                    try {
                        KEWServiceLocator.getWorkflowDocumentService().superUserNodeApproveAction(EdoContext.getPrincipalId(), documentHeader.getDocumentId(), node, "Super User approved.", true);
                        routed = true;
                    } catch (InvalidActionTakenException ex) {
                        //Add error for display.
                    }
                } else if (ActionType.SU_RETURN_TO_PREVIOUS.equals(superUserAction)) {
                    try {
                        KEWServiceLocator.getWorkflowDocumentService().superUserReturnDocumentToPreviousNode(EdoContext.getPrincipalId(), documentHeader.getDocumentId(), node, "Super User approved.", true);
                        routed = true;
                    } catch (InvalidActionTakenException ex) {
                        //Add error for display.
                    }
                }
            } else {
                routed = this.initiateWorkflowDocument(principalId, dossierId, dossierType);
            }
        }
        return routed;
    }

    public boolean returnToCandidate(String principalId, Integer dossierId, String dossierType) {
        WorkflowDocument workflowDocument = null;
        boolean routed = false;
        if (ObjectUtils.isNotNull(dossierId)) {
            //Get the document header.
            DossierProcessDocumentHeader documentHeader = EdoServiceLocator.getDossierProcessDocumentHeaderService().getDossierProcessDocumentHeader(dossierId);

            if (ObjectUtils.isNotNull(documentHeader)) {
                //Get the document.
                DocumentRouteHeaderValue routeHeaderValue = KEWServiceLocator.getRouteHeaderService().getRouteHeader(documentHeader.getDocumentId());
                workflowDocument = WorkflowDocumentFactory.loadDocument(EdoContext.getPrincipalId(), documentHeader.getDocumentId());

                if (workflowDocument != null && routeHeaderValue != null && CollectionUtils.isNotEmpty(routeHeaderValue.getInitialRouteNodeInstances())) {
                    workflowDocument.returnToPreviousNode("Returning to candidate", routeHeaderValue.getInitialRouteNodeInstances().get(0).getName());

                    //Update the dossier
                    EdoDossier dossier = EdoServiceLocator.getEdoDossierService().getEdoDossierById(dossierId.toString());
                    //dossier.setDossierStatus(EdoConstants.DOSSIER_STATUS.OPEN);
                    //dossier.setLastUpdated(EdoUtils.getNow());
                    Principal submitter = KimApiServiceLocator.getIdentityService().getPrincipal(EdoContext.getPrincipalId());
                    //dossier.setUpdatedBy(submitter.getPrincipalName());
                    EdoServiceLocator.getEdoDossierService().saveOrUpdate(dossier);

                    //Update that we have finished the routing process.
                    routed = true;
                }
            }
        }
        return routed;
    }

    public boolean routeSupplementalDocument(String principalId, Integer dossierId, String dossierType) {
        WorkflowDocument workflowDocument = null;
        WorkflowDocument dossierWorkflowDocument = null;
        boolean routed = false;
        EdoDossier dossier = EdoServiceLocator.getEdoDossierService().getEdoDossierById(dossierId.toString());
        // resolve the supplemental doc type from the dossierType
        String suppDocType = getSupplementalDocTypeMap().get(dossierType);
        if (ObjectUtils.isNotNull(dossierId)) {
            //Get the document header of the parent dossier workflow document.
            DossierProcessDocumentHeader dossierDocumentHeader = EdoServiceLocator.getDossierProcessDocumentHeaderService().getDossierProcessDocumentHeader(dossierId);
            if (ObjectUtils.isNotNull(dossierDocumentHeader)) {
                //Get the parent workflow document (dossier).
                dossierWorkflowDocument = WorkflowDocumentFactory.loadDocument(dossierDocumentHeader.getPrincipalId(), dossierDocumentHeader.getDocumentId());
                //Get the parent workflow document route level name.
                DocumentRouteHeaderValue dossierRouteHeader = KEWServiceLocator.getRouteHeaderService().getRouteHeader(dossierWorkflowDocument.getDocumentId());
                //String routeDescription = null;
                String currentRouteLevel;
                //List<String> previousNodes = new ArrayList<String>();
                Collection<String> authorizedNodes = new TreeSet<String>() {};
                if (ObjectUtils.isNotNull(dossierRouteHeader)) {
                	//this will tell you what level is the dossier edoc is? i mean at what level is the edossier edoc is in the route?
                    currentRouteLevel = dossierRouteHeader.getCurrentRouteLevelName();
                    //  previousNodes = dossierWorkflowDocument.getPreviousNodeNames();
                    EdoReviewLayerDefinition reviewLayerDefinition = EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinition(dossier.getWorkflowId(), currentRouteLevel);
                    if (reviewLayerDefinition != null) {
                    	Collection<EdoReviewLayerDefinition> validReviewLayers = EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinitionsToMax(reviewLayerDefinition.getReviewLevel());
                        // generate a list of node names
                       for (EdoReviewLayerDefinition rvwLayer : validReviewLayers) {
                           // authorizedNodes.add(EdoServiceLocator.getEdoReviewLayerDefinitionService().buildNodeMap(validReviewLayers).get(rvwLayer.getNodeName())); //this is throwing null pointer exception
                           authorizedNodes.addAll(EdoServiceLocator.getEdoReviewLayerDefinitionService().getAuthorizedSupplementalNodes(rvwLayer.getEdoReviewLayerDefinitionId()));

                       }
                    }
                }
                // now build the supplemental document for workflow
                //this will create docid for supplemental edoc
                //and also adds the notify nodes to the supplemental edoc header
                //not sure about this if part - have to look into deeper on this one
                /*  DossierProcessDocumentHeader suppDocumentHeader = EdoServiceLocator.getDossierProcessDocumentHeaderService().getDossierProcessDocumentHeaderByDocType(dossierId, suppDocType);
                if (ObjectUtils.isNotNull(suppDocumentHeader)) {
                    //supplemental edoc
                	workflowDocument = WorkflowDocumentFactory.loadDocument(suppDocumentHeader.getPrincipalId(), suppDocumentHeader.getDocumentId());
                   
                    //appending the authorized nodes to the document header and building the xml document
                	workflowDocument.setApplicationContent(workflowDocument.getApplicationContent() + EdoWorkflowUtils.generateSuppDocApplicationContent(dossier, authorizedNodes));

                    // need to do something with the supp doc here: route/approve/???
                    routed = true;
                } else {*/
                    // we have to route the suppl document as many times as the candidates submit their supplemental.les
                
                    routed = this.initiateSuppWorkflowDocument(principalId, dossierId, suppDocType, authorizedNodes);
                //}
                if(routed == true) {
                	String contentSingle = "This is an FYI email notification:"
							+ EdoContext.getSelectedCandidate().getCandidateUsername()
							+ " whose e-dossier is currently under review at your level, has added material(s) to the supplemental folder. No other action is required.\n\n"
							+ "For additional help or feedback, email <edossier@indiana.edu>.";
					String contentMultiple = "This is an FYI email notification:"
							+ EdoContext.getSelectedCandidate()
									.getCandidateUsername()
							+ " whose e-dossier is currently under review at your level, has added material(s) to the supplemental folder. Please notify others at your level (i.e., committee members) of these materials. No other action is required.\n\n"
							+ "For additional help or feedback, email <edossier@indiana.edu>.";
					
               
                	//make a service call to notify
                	EdoServiceLocator.getEdoSupplementalPendingStatusService().notifyCurrentEdossierLevelForSuppDoc(dossierRouteHeader.getDocumentId(), dossierRouteHeader.getCurrentRouteLevelName(), contentSingle, contentMultiple);
                } 
                
            }
        }
        return routed;
    }

    public void setEdoDossierDao(EdoDossierDao edoDossierDao) {
        this.edoDossierDao = edoDossierDao;
    }

    public void saveOrUpdate(EdoDossier edoDossier) {
        edoDossierDao.saveOrUpdate(EdoDossierBo.from(edoDossier));
    }
    //to populate the dossier drop down on the assign delegate page
    public List<EdoDossierBo> getDossierListByUserName(String userName) {
    	return edoDossierDao.getDossierListByUserName(userName);
    }
    //supplemental tracking table
    public void populateSuppTrackingTable( List<String> previousNodes, Integer dossierId) {
    	 List<BigDecimal> previousLevels = new ArrayList<BigDecimal>();
        EdoDossier dossier = EdoServiceLocator.getEdoDossierService().getEdoDossierById(dossierId.toString());
         //for this previous nodes get the review level from the review level def table
         for(String previousNode : previousNodes ) {
            EdoReviewLayerDefinition previousLevelReviewLayerDef = EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinition(dossier.getWorkflowId(), previousNode);
            if(previousLevelReviewLayerDef.getReviewLevel() != null){
                previousLevels.add(new BigDecimal(previousLevelReviewLayerDef.getReviewLevel()));
         	}
         }
         //once we get the previous levels
         //see if they have any rows in the supp review table
         for(BigDecimal previousLevel : previousLevels) {
         	 //see if they have any rows in the supp review table
        	 EdoSuppReviewLayerDefinition suppReviewLayerDef= EdoServiceLocator.getEdoReviewLayerDefinitionService().getSuppReviewLayerDefinition(previousLevel);
         	 //check to see if any rows exist in the supp tracking table
        	 //if not then insert
        	 //if yes then dont insert just skip
        	 if(suppReviewLayerDef != null){
        		 EdoSupplementalTracking edoSuppTracking = EdoServiceLocator.getEdoSupplementalTrackingService().getSupplementalTrackingEntryObj(dossierId, suppReviewLayerDef.getReviewLayerDefinitionId());
        	 if(edoSuppTracking != null){
        		 //compare flag
        		 if(edoSuppTracking.isAcknowledged() == true){
        			 //update acknowledged to False to avoid redundancy
        			 edoSuppTracking.setAcknowledged(false);
        			 EdoServiceLocator.getEdoSupplementalTrackingService().saveOrUpdate(edoSuppTracking);
        			}
         		
         		}
        	 else {
        		//store it in the supp tracking table
          		EdoSupplementalTracking edoSupplementalTracking = new EdoSupplementalTracking();
          		edoSupplementalTracking.setReviewLevel(suppReviewLayerDef.getReviewLayerDefinitionId());
          		edoSupplementalTracking.setDossierId(dossierId);
          		edoSupplementalTracking.setLastUpdated(EdoUtils.getNow());
          		edoSupplementalTracking.setAcknowledged(false);
          		EdoServiceLocator.getEdoSupplementalTrackingService().saveOrUpdate(edoSupplementalTracking);
        		 
        	 	}
        	 }
         }
    	
    }

    // TODO fix this map; hardcoded strings = bad
    public Map<String, String> getSupplementalDocTypeMap() {
        Map<String, String> docTypeMap = new HashMap<String, String>();

        docTypeMap.put("TenureProcessDocument", "Tenure Supplemental");
        docTypeMap.put("PromotionProcessDocument", "Promotion Supplemental");
        docTypeMap.put("Tenure", "Tenure Supplemental");
        docTypeMap.put("Promotion", "Promotion Supplemental");
        docTypeMap.put("Tenure+Promotion", "Tenure Supplemental");
   
        return docTypeMap;
    }
    
    //approve supplemental document
    public boolean approveSupplemental(String principalId, Integer dossierId, String dossierType) {
        WorkflowDocument workflowDocument = null;
        boolean routed = false;
        EdoDossierType dossierTypeObj = EdoServiceLocator.getEdoDossierTypeService().getEdoDossierTypeByName(dossierType);
        if (ObjectUtils.isNotNull(dossierId)) {
            //Get the document header.
            List<DossierProcessDocumentHeader> documentHeaders = EdoServiceLocator.getDossierProcessDocumentHeaderService().getDossierProcessDocumentHeaderByDocType(dossierId, dossierTypeObj.getDocumentTypeName());

            if (!documentHeaders.isEmpty()) {
            	  //Get the document.
            	//loop through all the document headers and change the status to approve
            	for(DossierProcessDocumentHeader documentHeader :  documentHeaders) {
            		workflowDocument = WorkflowDocumentFactory.loadDocument(EdoContext.getPrincipalId(), documentHeader.getDocumentId());
           
            	  if (workflowDocument.checkStatus(DocumentStatus.ENROUTE)) {
                      workflowDocument.approve("Supplemental Document is Approved");
                      routed = true;
            	  	}
            	}
            	/// update the edo 
            }
        }
        return routed;
    }
    public boolean routeReconsiderDocument(String principalId, Integer dossierId, String dossierType) {
        boolean routed = false;
        if (ObjectUtils.isNotNull(dossierId)) {
            String workflowId = EdoServiceLocator.getEdoDossierService().getEdoDossierById(dossierId.toString()).getWorkflowId();

            //Get the document header.
            //DossierProcessDocumentHeader documentHeader = EdoServiceLocator.getDossierProcessDocumentHeaderService().getDossierProcessDocumentHeader(dossierId);
            //String candidatePrincipalId = EdoServiceLocator.getEdoDossierService().getDossierById(BigDecimal.valueOf(dossierId)).getCandidatePrincipalId();
            // Principal candidatePrincipal = KimApiServiceLocator.getIdentityService().getPrincipal(candidatePrincipalId);
            //Get the document.
            //workflowDocument = WorkflowDocumentFactory.loadDocument(EdoContext.getPrincipalId(), documentHeader.getDocumentId());
            //DocumentRouteHeaderValue routeHeaderValue = KEWServiceLocator.getRouteHeaderService().getRouteHeader(documentHeader.getDocumentId());

            Collection<EdoReviewLayerDefinition> authorizedNodes = new TreeSet<EdoReviewLayerDefinition>() {};
            //get all the node names
            Collection<EdoReviewLayerDefinition> edoReviewLayerDefinitions = EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinitions(workflowId);
            for(EdoReviewLayerDefinition edoReviewLayerDefinition : edoReviewLayerDefinitions){
               //get vote record of the most current round for each and every review layer def id
                EdoVoteRecord edoVoteRecord = EdoServiceLocator.getEdoVoteRecordService().getVoteRecordMostCurrentRound(dossierId.toString(), edoReviewLayerDefinition.getEdoReviewLayerDefinitionId());
//              if((edoVoteRecord.getNoCountTenure() != null && edoVoteRecord.getYesCountTenure() != null && (edoVoteRecord.getNoCountTenure() > edoVoteRecord.getYesCountTenure())) ||
//                    (edoVoteRecord.getNoCountPromotion() != null && edoVoteRecord.getYesCountPromotion() != null && (edoVoteRecord.getNoCountPromotion() > edoVoteRecord.getYesCountPromotion()))) {
//                
                if((edoVoteRecord.getNoCount() != null && edoVoteRecord.getYesCount() != null && (edoVoteRecord.getNoCount() > edoVoteRecord.getYesCount())) ) {
                        
                	//if(edoVoteRecord.getNoCountTenure() > edoVoteRecord.getYesCountTenure()) {
                    //add node to the authorized nodes
                    authorizedNodes.add(edoReviewLayerDefinition);
                    //we need the first node - which has the negative vote
                    //from their the workflow will be sequential workflow as the parent eDossier work flow
                    if(authorizedNodes.size() > 0) {
                        break;
                    }
                }
            }
            routed = this.initiateReconsiderWorkflowDocument(principalId, dossierId, dossierType , authorizedNodes);
        }
		return routed;
    }

    public boolean isRoutedAsReconsiderDocument(Integer dossierId) {
        boolean isRouted = false;

        EdoDossier dossier = EdoServiceLocator.getEdoDossierService().getEdoDossierById(dossierId.toString());
        if (dossier == null) {
            LOG.info("Dossier ID is invalid: " + dossierId.toString());
            return isRouted;
        }
        if (dossier.getDossierStatus().equals(EdoConstants.DOSSIER_STATUS.RECONSIDERATION)) {
        	//TODO: take care of dossier.getDocumentID()
            //DossierProcessDocumentHeader documentHeader = EdoServiceLocator.getDossierProcessDocumentHeaderService().getDossierProcessDocumentHeader(dossier.getDocumentID());
        	DossierProcessDocumentHeader documentHeader = null;
        	if (documentHeader != null) {
                if (documentHeader.getDocumentStatus().equals("R")) {
                    isRouted = true;
                }
            }
        }
        return isRouted;
    }

    public EdoDossierBo getDossier(String documentId) {
        return edoDossierDao.getDossier(documentId);
    }

    private String buildRouteDescription(String workflowId, Set<String> currentNodeNames, ActionType actionType) {
        StringBuilder routeDescription = new StringBuilder();
        if (actionType != null) {
            routeDescription.append(actionType.getLabel() + ": ");
        }

        if (CollectionUtils.isNotEmpty(currentNodeNames)) {
            Collection<EdoReviewLayerDefinition> reviewLayerDefinitions = EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinitions(workflowId, currentNodeNames);

            if (CollectionUtils.isNotEmpty(reviewLayerDefinitions)) {
                int i = 0;
                for (EdoReviewLayerDefinition reviewLayerDefinition : reviewLayerDefinitions) {
                    if (i == 0) {
                        routeDescription.append(reviewLayerDefinition.getDescription());
                    } else {
                        routeDescription.append(", ");
                        routeDescription.append(reviewLayerDefinition.getDescription());
                    }
                }
            } else {
                routeDescription.append("Completed");
            }
        }

        return routeDescription.toString();
    }
}
