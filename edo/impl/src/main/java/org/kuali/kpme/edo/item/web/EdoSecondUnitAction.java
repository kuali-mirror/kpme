package org.kuali.kpme.edo.item.web;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.joda.time.LocalDate;
import org.kuali.kpme.edo.base.web.EdoAction;
import org.kuali.kpme.edo.candidate.EdoSelectedCandidate;
import org.kuali.kpme.edo.item.EdoItem;
import org.kuali.kpme.edo.item.EdoItemTracker;
import org.kuali.kpme.edo.item.EdoItemV;
import org.kuali.kpme.edo.reviewlayerdef.EdoReviewLayerDefinition;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.util.*;
import org.kuali.kpme.edo.workflow.DossierProcessDocumentHeader;
import org.kuali.rice.core.api.config.property.Config;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.MessageMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 5/21/14
 * Time: 2:15 PM
 */
public class EdoSecondUnitAction extends EdoAction {
    protected Config config;
    static final Logger LOG = Logger.getLogger(EdoSecondUnitAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        int currentTreeNodeID;
        List<EdoItemV> itemList;
        String itemListJSON = "";
        EdoSecondUnitForm reviewLetterForm = (EdoSecondUnitForm) form;

        EdoSelectedCandidate selectedCandidate = (EdoSelectedCandidate) request.getSession().getAttribute("selectedCandidate");
        String workflowId = selectedCandidate.getDossierWorkflowId();
        Map<BigDecimal, EdoReviewLayerDefinition> lvlMap = EdoServiceLocator.getEdoReviewLayerDefinitionService().buildReviewLevelMap( EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinitions(workflowId) );

        //figure out the dossier's current level
        //BigDecimal dossierCurrentRouteLevel = EdoServiceLocator.getDossierProcessDocumentHeaderService().getCurrentRouteLevel(selectedCandidate.getCandidateDossierID().intValue());
        String dossierCurrentRouteLevelName = EdoServiceLocator.getDossierProcessDocumentHeaderService().getCurrentRouteLevelName(selectedCandidate.getCandidateDossierID().intValue());

        //BigDecimal dossierCurrentReviewLevel = EdoServiceLocator.getEdoReviewLayerDefinitionService().buildRouteLevelMap(EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinitions()).get(dossierCurrentRouteLevel).getReviewLevel();
        BigDecimal dossierCurrentReviewLevel = EdoServiceLocator.getEdoReviewLayerDefinitionService().buildReviewLevelByRouteMap(EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinitions(workflowId)).get(dossierCurrentRouteLevelName);


        // authorized View review letter levels set up
        BigDecimal highestAuthorizedViewReviewLevel = BigDecimal.ZERO;
        List<BigDecimal> authorizedViewReviewLetterLevels = EdoContext.getAuthorizedViewReviewLetterLevels();

        if (CollectionUtils.isNotEmpty(authorizedViewReviewLetterLevels)) {
            highestAuthorizedViewReviewLevel = Collections.max(authorizedViewReviewLetterLevels);
        }

        // authorized Upload review levels set up
        BigDecimal highestAuthorizedUploadReviewLevel = BigDecimal.ZERO;
        Collection<EdoReviewLayerDefinition> authorizedUploadLevelDefs = reviewLetterForm.getLayerList();
        List<BigDecimal> authorizedUploadReviewLetterLevels = EdoContext.getAuthorizedUploadReviewLetterLevels();

        for ( BigDecimal lvl : authorizedUploadReviewLetterLevels ) {
            authorizedUploadLevelDefs.add(lvlMap.get(lvl));
        }
        if (CollectionUtils.isNotEmpty(authorizedUploadReviewLetterLevels)) {
            highestAuthorizedUploadReviewLevel = Collections.max(authorizedUploadReviewLetterLevels);
        }

        request.setAttribute("authorizedUploadReviewLevels", authorizedUploadLevelDefs);
        request.setAttribute("authorizedEditReviewLevels", authorizedUploadReviewLetterLevels.toString());
        request.setAttribute("highestAuthorizedUploadLevel", highestAuthorizedUploadReviewLevel);
        request.setAttribute("currentDossierLevel", 0);
        if (dossierCurrentReviewLevel != null) {
            request.setAttribute("currentDossierLevel", dossierCurrentReviewLevel);
        }

        // parse out the node id and current node id
        HttpSession ssn = request.getSession();

        if (request.getParameterMap().containsKey("nid")) {
            ssn.setAttribute("nid", request.getParameter("nid"));
        } else if (request.getParameterMap().containsKey("nidFwd")) {
            ssn.setAttribute("nid", request.getParameter("nidFwd"));
        }
        request.setAttribute("nidFwd", ssn.getAttribute("nid"));

        ssn.setAttribute("currentNodeID", Integer.parseInt(ssn.getAttribute("nid").toString().split("_")[2]));
        currentTreeNodeID = Integer.parseInt(ssn.getAttribute("nid").toString().split("_")[2]);
        reviewLetterForm.setChecklistItemID(currentTreeNodeID);

        itemList = EdoServiceLocator.getEdoItemVService().getItemList( selectedCandidate.getCandidateDossierID(), BigDecimal.valueOf(currentTreeNodeID) );

        // filter list of letters for display, based upon authorizedViewReviewLetterLevel
        if (CollectionUtils.isNotEmpty(itemList)) {
            Collections.sort(itemList);
            // copy the itemList because we can't "remove" an item while iterating over it
            List<EdoItemV> copy = new ArrayList<EdoItemV>(itemList);
            for ( EdoItemV item : copy ) {
                // only display review levels allowed by policy
                if (item.getReviewLevel().compareTo(highestAuthorizedViewReviewLevel) < 1 ) {
                    itemListJSON = itemListJSON.concat(item.getItemJSONString() + ",");
                } else {
                    itemList.remove(item);
                }
            }
        }
        reviewLetterForm.setItemList(itemList);
        request.setAttribute("itemlistJSON", itemListJSON);

        request.setAttribute("hasSupplementalWaiting", false);
        request.setAttribute("hasUploadReviewLetterSupplemental", false);

        // check to see if there are pending supplemental documents that need a new letter
        DossierProcessDocumentHeader documentHeader = EdoServiceLocator.getDossierProcessDocumentHeaderService().getDossierProcessDocumentHeader(selectedCandidate.getCandidateDossierID().intValue());

        if (documentHeader != null) {
            List<DossierProcessDocumentHeader> suppDocHeaders = EdoServiceLocator.getDossierProcessDocumentHeaderService().getPendingSupplementalDocuments(documentHeader.getDossierId());
            if (CollectionUtils.isNotEmpty(suppDocHeaders)) {
                boolean isWaiting = false;
                for (DossierProcessDocumentHeader docHeader : suppDocHeaders) {
                    isWaiting = isWaiting || EdoServiceLocator.getEdoSupplementalPendingStatusService().isWaiting(docHeader.getDocumentId(),EdoContext.getPrincipalId());
                }
                if (isWaiting) {
                    request.setAttribute("hasSupplementalWaiting", true);
                    request.setAttribute("hasUploadReviewLetterSupplemental", EdoServiceLocator.getAuthorizationService().isAuthorizedToUploadReviewLetter_W(EdoContext.getPrincipalId()));
                }
            }
        }

        request.setAttribute("isRoutedAsReconsiderDocument", false);
        if (EdoServiceLocator.getEdoDossierService().isRoutedAsReconsiderDocument(selectedCandidate.getCandidateDossierID().intValue())) {
            request.setAttribute("isRoutedAsReconsiderDocument", true);
        }

        // set page request variables for title and description
        request.setAttribute("nodeID", currentTreeNodeID );
        request.setAttribute("itemName", "Review Letters" );
        request.setAttribute("itemDescription", "Review letters");
        request.setAttribute("checklistItemID", currentTreeNodeID );
        request.setAttribute("selectedCandidate", selectedCandidate);

        String path = request.getRequestURL().toString().replace(request.getServletPath(), "").concat("/");
        request.setAttribute("requestPath", path);

        return super.execute(mapping, reviewLetterForm, request, response);
    }

    public ActionForward postFile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        EdoSecondUnitForm edoReviewLetterForm = (EdoSecondUnitForm)form;
        MessageMap msgmap = GlobalVariables.getMessageMap();
        String itemListJSON = "";
        boolean replaceFile = false;
        boolean isNewFile = true;
        BigDecimal itemID = BigDecimal.ZERO;
        BigDecimal itemReviewLayerID = null;
        // TODO When item is ready, uncomment the line below
        // String itemTypeID = EdoServiceLocator.getEdoItemTypeService().getItemTypeID(EdoConstants.EDO_ITEM_TYPE_NAME_REVIEW_LETTER);
        LocalDate currentDate = LocalDate.now();
        int itemTypeID = Integer.parseInt(EdoServiceLocator.getEdoItemTypeService().getItemTypeID(EdoConstants.EDO_ITEM_TYPE_NAME_REVIEW_LETTER, currentDate));

        FormFile uploadFile = edoReviewLetterForm.getUploadFile();
        int checklistItemID = edoReviewLetterForm.getChecklistItemID();
        int selectedRvwLayer = edoReviewLetterForm.getSelectedRvwLayer();
        String uploadUsername = EdoContext.getUser().getNetworkId();
        config = ConfigContext.getCurrentContextConfig();
        EdoSelectedCandidate selectedCandidate = (EdoSelectedCandidate) request.getSession().getAttribute("selectedCandidate");

        HttpSession ssn = request.getSession();
        String nidParam = ssn.getAttribute("nid").toString();
        int currentTreeNodeID = Integer.parseInt(nidParam.split("_")[2]);

        // if this is a file replacement, set the replace flag and the item ID from the request scope
        if (request.getParameterMap().containsKey("repl")) {
            replaceFile = Boolean.parseBoolean(request.getParameter("repl"));
            itemID = BigDecimal.valueOf(Integer.parseInt(request.getParameter("itemID")));
        }
        // pull candidate details needed for storing the item in the db
        EdoSelectedCandidate candidate = (EdoSelectedCandidate)request.getSession().getAttribute("selectedCandidate");
        String candidateUsername = candidate.getCandidateUsername();
        BigDecimal dossierID     = candidate.getCandidateDossierID();
        String workflowId        = EdoServiceLocator.getEdoDossierService().getEdoDossierById(dossierID.toString()).getWorkflowId();

        EdoItem item = new EdoItem();
        EdoItem originalItem = new EdoItem();

        if (ServletFileUpload.isMultipartContent(request)) {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(EdoConstants.FILE_UPLOAD_PARAMETERS.THRESHHOLD_SIZE);
            factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setFileSizeMax(EdoConstants.FILE_UPLOAD_PARAMETERS.MAX_FILE_SIZE);
            upload.setSizeMax(EdoConstants.FILE_UPLOAD_PARAMETERS.REQUEST_SIZE);

            //edo-79
            if(!EdoRule.validateFileNameLength(uploadFile)) {
                LOG.error("File name exceeds allowed length.");
                // msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.fileUpload.length");
                ActionForward fwd = mapping.findForward("basic");
                return fwd;
            }

            String storeFileName = candidateUsername + "_" + uploadFile.getFileName();
            String fileName      = uploadFile.getFileName();
            String contentType   = uploadFile.getContentType();
            int uploadSize       = uploadFile.getFileSize();

            // uploaded file is empty or non-existent; flag the error
            if (uploadSize < 1) {
                LOG.error("File size is zero.");
                msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.fileUpload.empty");
                ActionForward fwd = mapping.findForward("basic");
                return fwd;
            }

            if (contentType.contains("Content-Dispostion") || "".equals(contentType)) {
                contentType = EdoConstants.FILE_UPLOAD_PARAMETERS.DEFAULT_MIME_TYPE;
            }
            String uploadPath    = EdoUtils.BuildUploadPath(candidateUsername, Integer.toString(checklistItemID));

            if (uploadPath == null) {
                LOG.error("Upload path does not exist: [" + uploadPath + "]");
                msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS,"error.fileUpload.path");
                LOG.error("\n\n the upload path is null. It's either the candidateUserName is null or checklistItemID is null \n\n");
                ActionForward fwd = mapping.findForward("basic");
                return fwd;
            }

            // populate the item object to save
            request.setAttribute("message", "Upload has been completed successfully");
            java.util.Date date = new java.util.Date();
            long t = date.getTime();
            java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);

            // set attributes unique to a new file in the DB
            if (isNewFile) {
                item.setCreateDate(sqlTimestamp);
                // TODO: this will need to be a dynamic value, not hard coded; see declarations above
                item.setItemTypeID(BigDecimal.valueOf(itemTypeID));
                item.setAddendumRouted(BigDecimal.ZERO);
                item.setCreatedBy(uploadUsername);
                item.setCreateDate(sqlTimestamp);
                item.setDossierID(dossierID);
                item.setChecklistItemID( BigDecimal.valueOf(checklistItemID) );
                Integer nextRowIndexNum = EdoServiceLocator.getEdoItemService().getNextRowIndexNum(BigDecimal.valueOf(checklistItemID), uploadUsername);
                item.setRowIndex(nextRowIndexNum);

                if (selectedRvwLayer != 0) {
                    // translate review layer to DB ID for insert
                    Map<BigDecimal, EdoReviewLayerDefinition> lvlMap = EdoServiceLocator.getEdoReviewLayerDefinitionService().buildReviewLevelMap(EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinitions(workflowId));
                    itemReviewLayerID = lvlMap.get(BigDecimal.valueOf(selectedRvwLayer)).getReviewLayerDefinitionId();
                    item.setReviewLayerDefID(itemReviewLayerID);
                }
            }
            // if a replacement, get the existing file information from the DB record
            if (replaceFile) {
                item = EdoServiceLocator.getEdoItemService().getEdoItem(itemID);
                originalItem = item;
                if (!deleteFileFromFS(item)) {
                    // error deleting the file; report it and return
                    ActionForward fwd = mapping.findForward("basic");
                    return fwd;
                }
            }
            // these attributes will need to be updated for both new file and replacement
            // but we can't update until the old file is removed, as above
            item.setFileName(fileName);
            item.setFileLocation(uploadPath + File.separator + storeFileName);
            item.setContentType(contentType);
            item.setUploaderUsername(uploadUsername);
            item.setUploadDate(sqlTimestamp);
            item.setUpdatedBy(uploadUsername);
            item.setLastUpdateDate(sqlTimestamp);

            if (StringUtils.isNotBlank(storeFileName)) {
                File newFile = new File(uploadPath, storeFileName);
                isNewFile = !newFile.exists();
                if ( isNewFile || replaceFile ) {
                    try {
                        FileOutputStream fos = new FileOutputStream(newFile);
                        fos.write(uploadFile.getFileData());
                        fos.flush();
                        fos.close();
                        LOG.info("File [" + item.getFileName() + "] written to the file system.");
                    } catch (Exception e) {
                        LOG.error("An exception occurred writing the file: [" + e.getMessage() + "]");
                        msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.fileUpload.exception", e.getMessage() );
                        ActionForward fwd = mapping.findForward("basic");
                        return fwd;
                    }
                } else {
                    LOG.error("The file [" + fileName + "] already exists in this checklist section");
                    msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS,"error.fileUpload.exists", fileName);
                    ActionForward fwd = mapping.findForward("basic");
                    return fwd;
                }
            }
            //   }

            // if this is a new file or we are replacing the file, update the db; otherwise, don't do anything
            if (isNewFile || replaceFile) {
                EdoServiceLocator.getEdoItemService().saveOrUpdate(item);
                LOG.info("File entry [" + item.getItemID() + "][" + item.getFileName() + "] updated/saved to the database.");
            }
        }

        // authorized View review letter levels set up
        BigDecimal highestAuthorizedViewReviewLevel = BigDecimal.ZERO;
        List<BigDecimal> authorizedViewReviewLetterLevels = EdoContext.getAuthorizedViewReviewLetterLevels();

        if (CollectionUtils.isNotEmpty(authorizedViewReviewLetterLevels)) {
            highestAuthorizedViewReviewLevel = Collections.max(authorizedViewReviewLetterLevels);
        }

        List<EdoItemV> itemList = EdoServiceLocator.getEdoItemVService().getItemList(selectedCandidate.getCandidateDossierID(), BigDecimal.valueOf(currentTreeNodeID));
        // filter list of letters for display, based upon authorizedViewReviewLetterLevel
        // from EDO-196
        // The code above can't fetch the latest change from db for some reason.
        // It's probably related the transactions but not 100% positive.
        // As a workaround, we update the java EdoItemV list with the new EdoItemV object for json output.
        if (replaceFile) {
            for (EdoItemV itemV : itemList) {
                if (itemV.getItemID().compareTo(originalItem.getItemID()) == 0) {
                    EdoItemV newItemV = new EdoItemV(item);
                    newItemV = convertItemV(itemV, newItemV);
                    itemList.remove(itemV);
                    itemList.add(newItemV);
                    // since we can only update one item at a time, break the loop once the update is done.
                    break;
                }
            }
        }

        if (CollectionUtils.isNotEmpty(itemList)) {
            Collections.sort(itemList);
            // copy the itemList because we can't "remove" an item while iterating over it
            List<EdoItemV> copy = new ArrayList<EdoItemV>(itemList);
            for ( EdoItemV tmpItem : copy ) {
                // only display review levels allowed by policy
                if (tmpItem.getReviewLevel().compareTo(highestAuthorizedViewReviewLevel) < 1 ) {
                    itemListJSON = itemListJSON.concat(tmpItem.getItemJSONString() + ",");
                } else {
                    itemList.remove(item);
                }
            }
        }

        edoReviewLetterForm.setItemList(itemList);

        String path = request.getRequestURL().toString().replace(request.getServletPath(), "").concat("/");
        request.setAttribute("requestPath", path);
        request.setAttribute("itemlistJSON", itemListJSON);
        request.setAttribute("nidFwd", nidParam);

        ActionForward fwd = mapping.findForward("basic");

        return fwd;
    }

    public ActionForward deleteConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String itemListJSON = "";
        String data = ((EdoSecondUnitForm)form).getFormData();
        MessageMap msgmap = GlobalVariables.getMessageMap();
        EdoSelectedCandidate selectedCandidate = (EdoSelectedCandidate) request.getSession().getAttribute("selectedCandidate");
        EdoSecondUnitForm cliForm = (EdoSecondUnitForm) form;

        HttpSession ssn = request.getSession();

        String nidParam = ssn.getAttribute("nid").toString();

        int currentTreeNodeID = Integer.parseInt(nidParam.split("_")[2]);

        if (data == null) {
            msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS,"error.formData.null");
            LOG.error("Form data missing");
            return mapping.findForward("basic");
        }

        QueryParams itemData = new QueryParams(data);
        List<String> params = itemData.getParameterValues("itemID");

        for (String id : params) {
            EdoItem item = EdoServiceLocator.getEdoItemDao().getEdoItem(BigDecimal.valueOf(Integer.parseInt(id)));
            if (deleteFileFromFS(item)) {
                // all is well with the file system, delete the item from the database
                EdoServiceLocator.getEdoItemDao().deleteItem(item);
                LOG.info("File deleted [" + item.getFileName() + "]");
            }
        }

        List<EdoItemV> itemList = EdoServiceLocator.getEdoItemVService().getItemList( selectedCandidate.getCandidateDossierID(), BigDecimal.valueOf(currentTreeNodeID) );
        cliForm.setItemList(itemList);

        if (itemList != null && itemList.size() > 0) {
            Collections.sort(itemList);
            for ( EdoItemV item : itemList ) {
                itemListJSON = itemListJSON.concat(item.getItemJSONString() + ",");
            }
        }

        EdoItemTracker itemTracker = (EdoItemTracker) ssn.getAttribute("itemTracker");
        itemTracker.clearItemsMarked();

        String path = request.getRequestURL().toString().replace(request.getServletPath(), "").concat("/");
        request.setAttribute("requestPath", path);
        request.setAttribute("itemlistJSON", itemListJSON);
        request.setAttribute("nidFwd", nidParam);

        ActionForward fwd = mapping.findForward("basic");

        return fwd;
    }

    // private methods
    private boolean deleteFileFromFS(EdoItem item) {

        MessageMap msgmap = GlobalVariables.getMessageMap();
        File fn = new File(item.getFileLocation());
        Boolean fsOK = true;

        if (fn.isFile() && fn.exists()) {
            if (fn.canWrite()) {
                try {
                    boolean deleteResult = fn.delete();
                    if (!deleteResult) {
                        fsOK = false;
                        msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.fileDelete.null", fn.getName());
                        LOG.error("Error deleting file [" + item.getFileLocation() + "]");
                    }
                } catch (Exception e) {
                    fsOK = false;
                    msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS,"error.fileDelete.exception", e.getMessage() );
                    LOG.error("Error deleting file [" + item.getFileLocation() + "] " + e.getMessage() );
                }
            } else {
                fsOK = false;
                LOG.error("Can't delete file from [" + item.getFileLocation() + "]");
                msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.fileDelete.access", fn.getName());
            }
        } else {
            fsOK = false;
            msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.fileDelete.invalid", fn.getName());
            LOG.warn("Missing file from file system [" + item.getFileLocation() + "]");
        }
        return fsOK;
    }

    private EdoItemV convertItemV(EdoItemV oldItemV, EdoItemV newItemV) {
        newItemV.setItemID(oldItemV.getItemID());
        newItemV.setUploaderUsername(oldItemV.getUploaderUsername());
        newItemV.setItemTypeID(oldItemV.getItemTypeID());
        newItemV.setDossierID(oldItemV.getDossierID());
        newItemV.setNotes(oldItemV.getNotes());
        newItemV.setLayerLevel(oldItemV.getLayerLevel());
        newItemV.setChecklistSectionID(oldItemV.getChecklistSectionID());
        newItemV.setChecklistItemID(oldItemV.getChecklistItemID());
        newItemV.setItemTypeName(oldItemV.getItemTypeName());
        newItemV.setTypeDescription(oldItemV.getTypeDescription());
        newItemV.setInstructions(oldItemV.getInstructions());
        newItemV.setExtAvailability(oldItemV.getExtAvailability());
        newItemV.setReviewLayerDescription(oldItemV.getReviewLayerDescription());
        newItemV.setReviewLevel(oldItemV.getReviewLevel());

        return newItemV;
    }

}
