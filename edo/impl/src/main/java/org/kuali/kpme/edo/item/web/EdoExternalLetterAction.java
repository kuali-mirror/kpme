package org.kuali.kpme.edo.item.web;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.edo.api.item.EdoItem;
import org.kuali.kpme.edo.api.reviewlayerdef.EdoReviewLayerDefinition;
import org.kuali.kpme.edo.base.web.EdoAction;
import org.kuali.kpme.edo.candidate.EdoSelectedCandidate;
import org.kuali.kpme.edo.item.EdoItemTracker;
import org.kuali.kpme.edo.reviewlayerdef.EdoReviewLayerDefinitionBo;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.kpme.edo.util.EdoContext;
import org.kuali.kpme.edo.util.EdoRule;
import org.kuali.kpme.edo.util.EdoUtils;
import org.kuali.kpme.edo.util.QueryParams;
import org.kuali.rice.core.api.config.property.Config;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.MessageMap;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 7/19/13
 * Time: 10:54 AM
 */
public class EdoExternalLetterAction extends EdoAction {

    protected Config config;
    static final Logger LOG = Logger.getLogger(EdoExternalLetterAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        int currentTreeNodeID;
        List<EdoItem> itemList;
        EdoSelectedCandidate selectedCandidate = (EdoSelectedCandidate) request.getSession().getAttribute("selectedCandidate");
        String itemListJSON = "";
        String workflowId = selectedCandidate.getDossierWorkflowId();

        EdoExternalLetterForm externalLetterForm = (EdoExternalLetterForm) form;
        Collection<EdoReviewLayerDefinition> authorizedReviewLevels = EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinitions(workflowId);

        List<BigDecimal> reviewLevels = EdoContext.getAuthorizedUploadExternalLetterLevels();
        Map<String, EdoReviewLayerDefinition> lvlMap = EdoServiceLocator.getEdoReviewLayerDefinitionService().buildReviewLevelMap( EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinitions(workflowId) );

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

        // set page request variables for title and description
        // checklistView = reviewLetterForm.getChecklistView();
        request.setAttribute("nodeID", currentTreeNodeID );
        request.setAttribute("itemName", "External Review Letters" );
        request.setAttribute("itemDescription", "External Review letters");
        request.setAttribute("checklistItemID", currentTreeNodeID );

        itemList = EdoServiceLocator.getEdoItemService().getItemList(selectedCandidate.getCandidateDossierID().toString(), currentTreeNodeID+"");
        externalLetterForm.setItemList(itemList);
        externalLetterForm.setChecklistItemID(currentTreeNodeID);

        if (CollectionUtils.isNotEmpty(itemList)) {
        	Collections.sort(itemList);
            for (EdoItem item : itemList ) {
                itemListJSON = itemListJSON.concat(EdoServiceLocator.getEdoItemService().getItemJSONString(item) + ",");
            }
        }
        request.setAttribute("itemlistJSON", itemListJSON);

        String path = request.getRequestURL().toString().replace(request.getServletPath(), "").concat("/");
        request.setAttribute("requestPath", path);

        EdoItemTracker itemTracker = (EdoItemTracker) ssn.getAttribute("itemTracker");
        request.setAttribute("itemTracker", itemTracker.getItemTrackerJSONString());

        return super.execute(mapping, externalLetterForm, request, response);
    }

    public ActionForward postFile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        EdoExternalLetterForm externalLetterForm = (EdoExternalLetterForm)form;
        MessageMap msgmap = GlobalVariables.getMessageMap();
        String itemListJSON = "";
        boolean replaceFile = false;
        boolean isNewFile = true;
        BigDecimal itemID = BigDecimal.ZERO;
        // TODO When item is ready, uncomment the line below
        // String itemTypeID = EdoServiceLocator.getEdoItemTypeService().getItemTypeID(EdoConstants.EDO_ITEM_TYPE_NAME_REVIEW_LETTER);
        LocalDate currentDate = LocalDate.now();
        int itemTypeID = Integer.parseInt(EdoServiceLocator.getEdoItemTypeService().getItemTypeID(EdoConstants.EDO_ITEM_TYPE_NAME_REVIEW_LETTER, currentDate));

        FormFile uploadFile = externalLetterForm.getUploadFile();
        int checklistItemID = externalLetterForm.getChecklistItemID();
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

            EdoItem.Builder item = EdoItem.Builder.create();

            // set attributes unique to a new file in the DB
            if (isNewFile) {
                // TODO: this will need to be a dynamic value, not hard coded; see declarations above
                item.setEdoItemTypeId(itemTypeID+"");
                item.setRouted(true);
                item.setUserPrincipalId(uploadUsername);
                item.setActionFullDateTime(new DateTime(sqlTimestamp));
                item.setEdoDossierId(dossierID.toString());
                item.setEdoChecklistItemId(checklistItemID+"");
                Integer nextRowIndexNum = EdoServiceLocator.getEdoItemService().getNextRowIndexNum(checklistItemID+"", uploadUsername);
                item.setRowIndex(nextRowIndexNum);

            }
            // if a replacement, get the existing file information from the DB record
            if (replaceFile) {
                EdoItem edoItem = EdoServiceLocator.getEdoItemService().getEdoItem(itemID.toString());
                if (!deleteFileFromFS(edoItem)) {
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
            item.setUserPrincipalId(uploadUsername);
            item.setActionFullDateTime(new DateTime(sqlTimestamp));
            item.setEdoReviewLayerDefId("0");

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
                EdoServiceLocator.getEdoItemService().saveOrUpdate(item.build());
                LOG.info("File entry [" + item.getEdoItemId() + "][" + item.getFileName() + "] updated/saved to the database.");
            }
        }

        List<EdoItem> itemList = EdoServiceLocator.getEdoItemService().getItemList(selectedCandidate.getCandidateDossierID().toString(), currentTreeNodeID+"");
        externalLetterForm.setItemList(itemList);

        if (CollectionUtils.isNotEmpty(itemList)) {
        	Collections.sort(itemList);
            for (EdoItem item : itemList) {
                itemListJSON = itemListJSON.concat(EdoServiceLocator.getEdoItemService().getItemJSONString(item) + ",");
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
        String data = ((EdoExternalLetterForm)form).getFormData();
        MessageMap msgmap = GlobalVariables.getMessageMap();
        EdoSelectedCandidate selectedCandidate = (EdoSelectedCandidate) request.getSession().getAttribute("selectedCandidate");
        EdoExternalLetterForm cliForm = (EdoExternalLetterForm) form;

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
            EdoItem item = EdoServiceLocator.getEdoItemService().getEdoItem(id);
            if (deleteFileFromFS(item)) {
                // all is well with the file system, delete the item from the database
                EdoServiceLocator.getEdoItemService().deleteItem(item);
                LOG.info("File deleted [" + item.getFileName() + "]");
            }
        }

        List<EdoItem> itemList = EdoServiceLocator.getEdoItemService().getItemList(selectedCandidate.getCandidateDossierID().toString(), currentTreeNodeID+"");
        cliForm.setItemList(itemList);

        if (itemList != null && itemList.size() > 0) {
        	Collections.sort(itemList);
            for (EdoItem item : itemList) {
                itemListJSON = itemListJSON.concat(EdoServiceLocator.getEdoItemService().getItemJSONString(item) + ",");
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
