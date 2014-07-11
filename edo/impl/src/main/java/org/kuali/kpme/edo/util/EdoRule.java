package org.kuali.kpme.edo.util;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.upload.FormFile;
import org.kuali.kpme.edo.api.checklist.EdoChecklistItem;
import org.kuali.kpme.edo.api.item.EdoItem;
import org.kuali.kpme.edo.dossier.EdoDossierBo;
import org.kuali.kpme.edo.item.count.EdoItemCountV;
import org.kuali.kpme.edo.reviewlayerdef.EdoReviewLayerDefinition;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.workflow.DossierProcessDocumentHeader;
import org.kuali.rice.kew.routeheader.DocumentRouteHeaderValue;
import org.kuali.rice.krad.util.GlobalVariables;

public class EdoRule {
	//Far:933
		
	public static boolean validateUserId(String userId) {
		try {
			EdoServiceLocator.getAuthorizationService().getEdoUser(userId);
		}
		catch (Throwable e) {
			GlobalVariables.getMessageMap().putError(EdoConstants.ErrorKeys.ERROR_KEYS,"error.userInfObject.null");
        }

        return GlobalVariables.getMessageMap().hasNoErrors();
    }

    public static boolean validateDateRange(Date startDate, Date endDate) {

        if(startDate != null && endDate != null) {
            if(startDate.after(endDate) || endDate.before(startDate)) {
                GlobalVariables.getMessageMap().putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.edoDateRange.invalid");
            /*return false;*/
            }
        }

        return GlobalVariables.getMessageMap().hasNoErrors();
    }

    public static boolean validateDepartmentId(String departmentId) {
        boolean isValid = true;

        if (StringUtils.isEmpty(departmentId)) {
            GlobalVariables.getMessageMap().putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.group.validate.code", "Department ID", departmentId);
            return false; // return immediately, just in case the argument was NULL
        }
        if (departmentId.contains("*") || departmentId.contains("%")) {
            GlobalVariables.getMessageMap().putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.group.add.nowildcard");
            isValid = false;
        }
        return isValid;
    }

    public static boolean validateSchoolId(String schoolId) {
        boolean isValid = true;

        if (StringUtils.isEmpty(schoolId)) {
            GlobalVariables.getMessageMap().putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.group.validate.code", "School ID", schoolId);
            return false;
        }
        if (schoolId.contains("*") || schoolId.contains("%")) {
            GlobalVariables.getMessageMap().putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.group.add.nowildcard");
            isValid = false;
        }
        return isValid;
    }

    public static boolean validateCampusId(String campusId) {
        boolean isValid = true;

        if (StringUtils.isEmpty(campusId)) {
            GlobalVariables.getMessageMap().putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.group.validate.code", "Campus ID", campusId);
            return false;
        }
        if (campusId.contains("*") || campusId.contains("%")) {
            GlobalVariables.getMessageMap().putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.group.add.nowildcard");
            isValid = false;
        }
        if (!EdoConstants.IU_CAMPUS_CODES.contains(campusId)) {
            GlobalVariables.getMessageMap().putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.group.validate.code", "Campus ID", campusId);
            isValid = false;
        }
        return isValid;
    }

    public static boolean validateWorkflowId(String workflowId) {
        boolean isValid = true;

        if (StringUtils.isEmpty(workflowId)) {
            GlobalVariables.getMessageMap().putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.group.validate.code", "Workflow ID", workflowId);
            return false;
        }
        List<String> validWorkflows = EdoServiceLocator.getEdoWorkflowDefinitionService().getWorkflowIds();
        if (!validWorkflows.contains(workflowId)) {
            GlobalVariables.getMessageMap().putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.group.validate.code", "Workflow ID", workflowId);
            isValid = false;
        }
        return isValid;
    }

    public static boolean validateFileNameLength(FormFile file) {

        boolean isValid = false;
        int filenameLength = file.getFileName().length();

        if (filenameLength > EdoConstants.FILE_UPLOAD_PARAMETERS.MAX_FILENAME_LENGTH) {
            GlobalVariables.getMessageMap().putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.fileUpload.length");
        } else {
            isValid = true;
        }
        return isValid;
    }

    public static boolean validateDossierForSubmission(List<EdoChecklistItem> checklistItems, BigDecimal dossierId) {
        boolean isReady = false;
        int requiredSectionCount = 0;
        BigDecimal generalSectionId = BigDecimal.ZERO;

        // get the ID of the General section (required) from the DB
        for (EdoChecklistItem checklistItem : checklistItems) {
        	String sectionName = EdoServiceLocator.getChecklistSectionService().getChecklistSectionByID(checklistItem.getEdoChecklistSectionID()).getChecklistSectionName();
            if (sectionName.equals(EdoConstants.EDO_GENERAL_SECTION_NAME)) {
                generalSectionId = new BigDecimal(checklistItem.getEdoChecklistSectionID());
            }
        }

        // get the item counts for this dossier and save the number of line items (non-zero counts)
        List<EdoItemCountV> itemCount = EdoServiceLocator.getEdoItemCountVService().getItemCount(dossierId, generalSectionId);
        if (CollectionUtils.isNotEmpty(itemCount)) {
            requiredSectionCount = itemCount.size();
        }
        // if the required item count from above is equal to the number of categories in General, then dossier is valid
        if (requiredSectionCount == EdoConstants.EDO_SUPPLEMENTAL_ITEM_CATEGORY_COUNT) {
            isReady = true;
        }

        return isReady;
    }

    public static boolean dossierHasSupplementalsPending(String edoDossierId) {
        String edoChecklistItemID = EdoServiceLocator.getChecklistItemService().getChecklistItemByDossierID(edoDossierId, EdoConstants.EDO_SUPPLEMENTAL_ITEM_CATEGORY_NAME).getEdoChecklistItemID();
        List<EdoItem> itemList = EdoServiceLocator.getEdoItemService().getPendingItemsByDossierId(edoDossierId, edoChecklistItemID);
        boolean hasPending = CollectionUtils.isNotEmpty(itemList);

        return hasPending;
    }
    public static boolean dossierHasReconsiderPending(String edoDossierId) {
    	String edoChecklistItemID = EdoServiceLocator.getChecklistItemService().getChecklistItemByDossierID(edoDossierId, EdoConstants.EDO_RECONSIDERATION_ITEM_CATEGORY_NAME).getEdoChecklistItemID();
        List<EdoItem> itemList = EdoServiceLocator.getEdoItemService().getPendingItemsByDossierId(edoDossierId, edoChecklistItemID);
        boolean hasPending = CollectionUtils.isNotEmpty(itemList);

        return hasPending;
    }
    public static boolean canUploadFileUnderReconsiderCategory(String edoDossierId) {
    	  String edoChecklistItemID = EdoServiceLocator.getChecklistItemService().getChecklistItemByDossierID(edoDossierId, EdoConstants.EDO_RECONSIDERATION_ITEM_CATEGORY_NAME).getEdoChecklistItemID();
          List<EdoItem> itemList = EdoServiceLocator.getEdoItemService().getItemsByDossierIdForAddendumFalgZero(edoDossierId, edoChecklistItemID);
          boolean canUploadReconsiderItems = CollectionUtils.isEmpty(itemList);

          return canUploadReconsiderItems;
    }

    public static boolean isDossierReadyForRoute(BigDecimal dossierId) {
        //Get the current route nodes.
        if (dossierId != null) {
            DossierProcessDocumentHeader documentHeader = EdoServiceLocator.getDossierProcessDocumentHeaderService().getDossierProcessDocumentHeader(dossierId.intValue());
            if (documentHeader != null) {
                EdoDossierBo eDossier = EdoServiceLocator.getEdoDossierService().getDossier(documentHeader.getDocumentId());
                String workflowId = eDossier.getWorkflowId();
                //TODO: take care of dossier.getDocumentID()
                //DocumentRouteHeaderValue dossierRouteHeader = KEWServiceLocator.getRouteHeaderService().getRouteHeader(eDossier.getDocumentID());
                DocumentRouteHeaderValue dossierRouteHeader = null;
                String currentLevel = dossierRouteHeader.getCurrentRouteLevelName();
                /* List<String> currentNodeNames = KewApiServiceLocator.getWorkflowDocumentService().getCurrentRouteNodeNames(documentHeader.getDocumentId());

                //Get the list of review layer definitions to determine requirements.
                Set<String> nodes = new HashSet<String>();
                nodes.addAll(currentNodeNames);
                Collection<EdoReviewLayerDefinition> reviewLayerDefinitions = EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinitions(nodes);
                */
                List<EdoReviewLayerDefinition> reviewLayerDefinitions = EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinitionsByWorkflowId(workflowId);
                EdoReviewLayerDefinition reviewLayerDefinition = EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinition(workflowId, currentLevel);

                //for (EdoReviewLayerDefinition reviewLayerDefinition : reviewLayerDefinitions) {
                    //Determine if the vote record is required and recorded.
                    boolean canRoute = true;
                    //check for dossier status
                    //if dossier status is reconsider the list size should be greate than one
                    if (!reviewLayerDefinition.getVoteType().equals(EdoConstants.VOTE_TYPE_NONE)) {
                        //canRoute = EdoServiceLocator.getEdoVoteRecordService().getVoteRecords(dossierId.intValue(), reviewLayerDefinition.getReviewLayerDefinitionId()).size() > 0;
                        if(StringUtils.equals(eDossier.getDossierStatus(),EdoConstants.DOSSIER_STATUS.RECONSIDERATION)) {
                            canRoute = EdoServiceLocator.getEdoVoteRecordService().getVoteRecords(dossierId.toString(), reviewLayerDefinition.getReviewLayerDefinitionId().toString()).size() > 1;

                    	}
                    	if(StringUtils.equals(eDossier.getDossierStatus(),EdoConstants.DOSSIER_STATUS.SUBMITTED)) {
                            canRoute = EdoServiceLocator.getEdoVoteRecordService().getVoteRecords(dossierId.toString(), reviewLayerDefinition.getReviewLayerDefinitionId().toString()).size() > 0;

                    	}
                    }

                    //Determine if the review letter is required and recorded.
                    /*if (reviewLayerDefinition.isReviewLetter()) {
                        //TODO: We should change this to use the id of the review letter and correlate it in the layer definition.
                        canRoute = canRoute && EdoServiceLocator.getEdoItemService().getReviewLetterEdoItems(dossierId.toString(), reviewLayerDefinition.getReviewLayerDefinitionId().toString()).size() > 0;
                    }*/
                    //check for dossier status
                    //if dossier status is reconsider the list size should be greate than one
                    if (reviewLayerDefinition.isReviewLetter()) {
                    	if(StringUtils.equals(eDossier.getDossierStatus(),EdoConstants.DOSSIER_STATUS.RECONSIDERATION)) {
                            canRoute = canRoute && EdoServiceLocator.getEdoItemService().getReviewLetterEdoItems(dossierId.toString(), reviewLayerDefinition.getReviewLayerDefinitionId().toString()).size() > 1;

                    	}
                    	if(StringUtils.equals(eDossier.getDossierStatus(),EdoConstants.DOSSIER_STATUS.SUBMITTED)) {
                            canRoute = canRoute && EdoServiceLocator.getEdoItemService().getReviewLetterEdoItems(dossierId.toString(), reviewLayerDefinition.getReviewLayerDefinitionId().toString()).size() > 0;

                    	}
                    	
                    }

                    if (!canRoute) {
                        return false;
                    }
                //}
            }
        }

        return true;
    }
}
