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
import org.kuali.kpme.edo.api.checklist.EdoChecklistItem;
import org.kuali.kpme.edo.base.web.EdoAction;
import org.kuali.kpme.edo.candidate.EdoSelectedCandidate;
import org.kuali.kpme.edo.item.EdoItem;
import org.kuali.kpme.edo.item.EdoItemTracker;
import org.kuali.kpme.edo.item.EdoItemV;
import org.kuali.kpme.edo.reviewlayerdef.EdoReviewLayerDefinition;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.util.*;
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
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 7/19/13
 * Time: 10:54 AM
 */
public class EdoSolicitedLetterAction extends EdoAction {

    protected Config config;
    static final Logger LOG = Logger.getLogger(EdoChecklistItemAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        int currentTreeNodeID;
        List<EdoItemV> itemList;
        EdoSelectedCandidate selectedCandidate = (EdoSelectedCandidate) request.getSession().getAttribute("selectedCandidate");
        String itemListJSON = "";
        String workflowId = selectedCandidate.getDossierWorkflowId();
        BigDecimal checklistItemId = null;

        EdoSolicitedLetterForm solicitedLetterForm = (EdoSolicitedLetterForm) form;
        Collection<EdoReviewLayerDefinition> authorizedReviewLevels = EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinitions(workflowId);

        List<BigDecimal> reviewLevels = EdoContext.getAuthorizedUploadExternalLetterLevels();
        Map<BigDecimal, EdoReviewLayerDefinition> lvlMap = EdoServiceLocator.getEdoReviewLayerDefinitionService().buildReviewLevelMap( EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinitions(workflowId) );

        for ( BigDecimal lvl : reviewLevels ) {
            authorizedReviewLevels.add(lvlMap.get(lvl));
        }
        request.setAttribute("authorizedReviewLevels", authorizedReviewLevels);
        request.setAttribute("authorizedEditExternalLevels", reviewLevels.toString());
        HttpSession ssn = request.getSession();

        if (request.getParameterMap().containsKey("nid")) {
            ssn.setAttribute("nid", request.getParameter("nid"));
        } else if (request.getParameterMap().containsKey("nidFwd")) {
            ssn.setAttribute("nid", request.getParameter("nidFwd"));
        }
        request.setAttribute("nidFwd", ssn.getAttribute("nid"));

        ssn.setAttribute("currentNodeID", Integer.parseInt(ssn.getAttribute("nid").toString().split("_")[2]));
        currentTreeNodeID = Integer.parseInt(ssn.getAttribute("nid").toString().split("_")[2]);
        // KPME-3685 now checklist item id is string, but keep integer value above to not break other code
        String currentTreeNodeID_s = ssn.getAttribute("nid").toString().split("_")[2];

        // set page request variables for title and description
        EdoChecklistItem checklistItem = EdoServiceLocator.getChecklistItemService().getChecklistItemByID(currentTreeNodeID_s);
        request.setAttribute("nodeID", currentTreeNodeID );
        request.setAttribute("itemName", checklistItem.getChecklistItemName());
        request.setAttribute("itemDescription", checklistItem.getItemDescription());
        request.setAttribute("checklistItemID", currentTreeNodeID );

        itemList = EdoServiceLocator.getEdoItemVService().getItemList( selectedCandidate.getCandidateDossierID(), BigDecimal.valueOf(currentTreeNodeID) );
        solicitedLetterForm.setItemList(itemList);
        solicitedLetterForm.setChecklistItemID(currentTreeNodeID);

        if (CollectionUtils.isNotEmpty(itemList)) {
            Collections.sort(itemList);
            for ( EdoItemV item : itemList ) {
                itemListJSON = itemListJSON.concat(item.getItemJSONString() + ",");
            }
        }
        request.setAttribute("itemlistJSON", itemListJSON);

        String path = request.getRequestURL().toString().replace(request.getServletPath(), "").concat("/");
        request.setAttribute("requestPath", path);

        EdoItemTracker itemTracker = (EdoItemTracker) ssn.getAttribute("itemTracker");
        request.setAttribute("itemTracker", itemTracker.getItemTrackerJSONString());

        return super.execute(mapping, solicitedLetterForm, request, response);
    }

    public ActionForward postFile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        EdoSolicitedLetterForm solicitedLetterForm = (EdoSolicitedLetterForm)form;
        MessageMap msgmap = GlobalVariables.getMessageMap();
        String itemListJSON = "";
        boolean replaceFile = false;
        boolean isNewFile = true;
        BigDecimal itemID = BigDecimal.ZERO;
        // TODO When item is ready, uncomment the line below
        // String itemTypeID = EdoServiceLocator.getEdoItemTypeService().getItemTypeID(EdoConstants.EDO_ITEM_TYPE_NAME_ADDENDUM);
        LocalDate currentDate = LocalDate.now();
        int itemTypeID = Integer.parseInt(EdoServiceLocator.getEdoItemTypeService().getItemTypeID(EdoConstants.EDO_ITEM_TYPE_NAME_ADDENDUM, currentDate));

        FormFile uploadFile = solicitedLetterForm.getUploadFile();
        int checklistItemID = solicitedLetterForm.getChecklistItemID();
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

            EdoItem item = new EdoItem();

            // set attributes unique to a new file in the DB
            if (isNewFile) {
                item.setCreateDate(sqlTimestamp);
                item.setItemTypeID(BigDecimal.valueOf(itemTypeID));
                item.setAddendumRouted(BigDecimal.ZERO);
                item.setCreatedBy(uploadUsername);
                item.setCreateDate(sqlTimestamp);
                item.setDossierID(dossierID);
                item.setChecklistItemID( BigDecimal.valueOf(checklistItemID) );
                Integer nextRowIndexNum = EdoServiceLocator.getEdoItemService().getNextRowIndexNum(BigDecimal.valueOf(checklistItemID), uploadUsername);
                item.setRowIndex(nextRowIndexNum);

            }
            // if a replacement, get the existing file information from the DB record
            if (replaceFile) {
                item = EdoServiceLocator.getEdoItemService().getEdoItem(itemID);
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
            item.setReviewLayerDefID(BigDecimal.ZERO);

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

        List<EdoItemV> itemList = EdoServiceLocator.getEdoItemVService().getItemList(selectedCandidate.getCandidateDossierID(), BigDecimal.valueOf(currentTreeNodeID));
        solicitedLetterForm.setItemList(itemList);

        if (CollectionUtils.isNotEmpty(itemList)) {
            Collections.sort(itemList);
            for ( EdoItemV itemV : itemList ) {
                itemListJSON = itemListJSON.concat(itemV.getItemJSONString() + ",");
            }
        }

        String path = request.getRequestURL().toString().replace(request.getServletPath(), "").concat("/");
        request.setAttribute("requestPath", path);
        request.setAttribute("itemlistJSON", itemListJSON);
        request.setAttribute("nidFwd", nidParam);

        ActionForward fwd = mapping.findForward("basic");

        return fwd;
    }

    public ActionForward deleteConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String itemListJSON = "";
        String data = ((EdoSolicitedLetterForm)form).getFormData();
        MessageMap msgmap = GlobalVariables.getMessageMap();
        EdoSelectedCandidate selectedCandidate = (EdoSelectedCandidate) request.getSession().getAttribute("selectedCandidate");
        EdoSolicitedLetterForm cliForm = (EdoSolicitedLetterForm) form;

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


}
