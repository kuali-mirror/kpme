package org.kuali.kpme.edo.item.web;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import org.kuali.kpme.edo.checklist.EdoChecklistV;
import org.kuali.kpme.edo.item.EdoItem;
import org.kuali.kpme.edo.item.EdoItemTracker;
import org.kuali.kpme.edo.item.EdoItemV;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.kpme.edo.util.EdoContext;
import org.kuali.kpme.edo.util.EdoItemVDateComparator;
import org.kuali.kpme.edo.util.EdoItemVDateDescComparator;
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
 * Date: 11/9/12
 * Time: 11:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class EdoChecklistItemAction extends EdoAction {

    protected Config config;
    static final Logger LOG = Logger.getLogger(EdoChecklistItemAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        MessageMap msgmap = GlobalVariables.getMessageMap();
        BigDecimal checklistItemId = null;
        int currentTreeNodeID;
        List<EdoChecklistV> checklistView;
        List<EdoItemV> itemList;
        EdoSelectedCandidate selectedCandidate = (EdoSelectedCandidate) request.getSession().getAttribute("selectedCandidate");
        String itemListJSON = "";
        EdoChecklistItemForm cliForm = (EdoChecklistItemForm) form;
        HttpSession ssn = request.getSession();

        if (request.getParameterMap().containsKey("nid")) {
            ssn.setAttribute("nid", request.getParameter("nid"));
        } else if (request.getParameterMap().containsKey("nidFwd")) {
            ssn.setAttribute("nid", request.getParameter("nidFwd"));
        }
        request.setAttribute("nidFwd", ssn.getAttribute("nid"));

        ssn.setAttribute("currentNodeID", Integer.parseInt(ssn.getAttribute("nid").toString().split("_")[2]));
        currentTreeNodeID = Integer.parseInt(ssn.getAttribute("nid").toString().split("_")[2]);

        if (!selectedCandidate.isSelected()) {
            msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.access.candidate" );
            return super.execute(mapping, cliForm,request, response);
        }

        // set page request variables for title and description
        checklistView = cliForm.getChecklistView();
        for (EdoChecklistV chklist : checklistView ) {
            checklistItemId = chklist.getChecklistItemID();
            if ( checklistItemId.intValue() == currentTreeNodeID ) {
                request.setAttribute("nodeID", currentTreeNodeID );
                request.setAttribute("itemName", chklist.getChecklistItemName() );
                request.setAttribute("itemDescription", chklist.getItemDescription());
                request.setAttribute("checklistItemID", checklistItemId.intValue() );
            }
        }

        itemList = EdoServiceLocator.getEdoItemVService().getItemList( selectedCandidate.getCandidateDossierID(), BigDecimal.valueOf(currentTreeNodeID) );
        cliForm.setItemList(itemList);
        cliForm.setChecklistItemID(currentTreeNodeID);

        if (itemList != null && itemList.size() > 0) {
            if (EdoConstants.EDO_SUPPLEMENTAL_ITEM_CATEGORY_NAME.equals(request.getAttribute("itemName"))) {
                Collections.sort(itemList, new EdoItemVDateDescComparator());
            } else {
                Collections.sort(itemList);
            }
            for ( EdoItemV item : itemList ) {
                itemListJSON = itemListJSON.concat(item.getItemJSONString() + ",");
            }
        }
        request.setAttribute("itemlistJSON", itemListJSON);
        boolean isDossierSubmitted = selectedCandidate.getDossierStatus().equals(EdoConstants.DOSSIER_STATUS.SUBMITTED);
        request.setAttribute("isDossierSubmitted", isDossierSubmitted);

        String path = request.getRequestURL().toString().replace(request.getServletPath(), "").concat("/");
        request.setAttribute("requestPath", path);

        EdoItemTracker itemTracker = (EdoItemTracker) ssn.getAttribute("itemTracker");
        request.setAttribute("itemTracker", itemTracker.getItemTrackerJSONString());

        request.setAttribute("isRoutedAsReconsiderDocument", false);
        if (EdoServiceLocator.getEdoDossierService().isRoutedAsReconsiderDocument(selectedCandidate.getCandidateDossierID().intValue())) {
            request.setAttribute("isRoutedAsReconsiderDocument", true);
        }

        return super.execute(mapping, cliForm, request, response);
    }

    public ActionForward updateIndex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EdoChecklistItemForm cliForm = (EdoChecklistItemForm) form;
        List<EdoItemV> itemlist = cliForm.getItemList();

        Map<String, String[]> map = request.getParameterMap();
        Map<BigDecimal, Integer> itemIndexMap = new HashMap<BigDecimal, Integer>();

        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            String key = entry.getKey();
            if (StringUtils.contains(key, "item")) {
                key = key.split("_")[1];
                Integer val = Integer.parseInt(entry.getValue()[0]);
                BigDecimal bdKey = BigDecimal.valueOf(Integer.parseInt(key));
                itemIndexMap.put(bdKey, val);
            }
        }

        List<EdoItem> itemsToUpdate = new ArrayList<EdoItem>();
        List<EdoItemV> itemVs = new ArrayList<EdoItemV>();
        for (EdoItemV edoItemV : itemlist) {
            Integer oldIndex = edoItemV.getRowIndex();
            Integer newIndex = itemIndexMap.get(edoItemV.getItemID());
            EdoItem edoItem = new EdoItem(edoItemV);
            if (oldIndex.compareTo(newIndex) != 0) {
                edoItemV.setRowIndex(newIndex);
                edoItem.setRowIndex(newIndex);
                itemsToUpdate.add(edoItem);
            }

            itemVs.add(edoItemV);
        }

        Collections.sort(itemVs);
        LOG.info("Items sorted");

        // update edoItems with new row indexes
        EdoServiceLocator.getChecklistVService().saveOrUpdate(itemsToUpdate);

        // update json
        // not happy about the code below since it involves too much handcrafted json.
        // will circle back in the future to figure out how to use gson to create the data structure datatables needs.
        String itemListJSON = "";
        for ( EdoItemV item : itemVs ) {
            itemListJSON = itemListJSON.concat(item.getItemJSONString() + ",");
        }
        itemListJSON = "[" + itemListJSON.substring(0, itemListJSON.length()-1) + "]";

        cliForm.setOutputJson(itemListJSON);

        return mapping.findForward("ws");
    }

    public void storeMarkedItemID(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map<String, String[]> map = request.getParameterMap();
        HttpSession ssn = request.getSession();
        EdoItemTracker itemTracker = (EdoItemTracker) ssn.getAttribute("itemTracker");
        BigDecimal val = BigDecimal.ZERO;
        String op = "none";

        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            String key = entry.getKey();
            if (StringUtils.contains(key, "itemID")) {
                val = BigDecimal.valueOf(Integer.parseInt(entry.getValue()[0]));
            }
            if (StringUtils.contains(key, "op")) {
                op = entry.getValue()[0];
            }
        }
        if (op.equals("store")) {
            itemTracker.addItem(val);
        }
        if (op.equals("remove")) {
            itemTracker.removeItem(val);
        }
    }

    public ActionForward deleteConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String itemListJSON = "";
        String data = ((EdoChecklistItemForm)form).getFormData();
        MessageMap msgmap = GlobalVariables.getMessageMap();
        EdoSelectedCandidate selectedCandidate = (EdoSelectedCandidate) request.getSession().getAttribute("selectedCandidate");
        EdoChecklistItemForm cliForm = (EdoChecklistItemForm) form;

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
            Collections.sort(itemList, new EdoItemVDateComparator());
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

    public ActionForward renameFile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        EdoChecklistItemForm edoChecklistItemForm = (EdoChecklistItemForm)form;
        MessageMap msgmap = GlobalVariables.getMessageMap();

        String newFilename = edoChecklistItemForm.getNewFilename();
        String fileDescription = edoChecklistItemForm.getFileDescription();
        BigDecimal itemID = BigDecimal.valueOf(edoChecklistItemForm.getItemID());
        EdoItem item = EdoServiceLocator.getEdoItemService().getEdoItem(itemID);

        item.setFileName(newFilename);
        item.setFileDescription(fileDescription);
        EdoServiceLocator.getEdoItemService().saveOrUpdate(item);

        ActionForward fwd = mapping.findForward("basic");

        return fwd;
    }

    public ActionForward postFile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        EdoChecklistItemForm edoChecklistItemForm = (EdoChecklistItemForm)form;
        MessageMap msgmap = GlobalVariables.getMessageMap();
        String itemListJSON = "";
        boolean replaceFile = false;
        boolean isNewFile = true;
        boolean isSupplemental = false;
        BigDecimal itemID = BigDecimal.ZERO;

        FormFile uploadFile = edoChecklistItemForm.getUploadFile();
        int checklistItemID = edoChecklistItemForm.getChecklistItemID();
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
                msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.fileUpload.length");
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
                // TODO when item is ready, uncomment out the line below
                //item.setItemTypeID(EdoServiceLocator.getEdoItemTypeService().getItemTypeID(EdoConstants.EDO_ITEM_TYPE_NAME_SUPPORTING_DOCUMENT));
                LocalDate currentDate = LocalDate.now();
                item.setItemTypeID(new BigDecimal(EdoServiceLocator.getEdoItemTypeService().getItemTypeID(EdoConstants.EDO_ITEM_TYPE_NAME_SUPPORTING_DOCUMENT, currentDate)));
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
                originalItem = item;
                if (!deleteFileFromFS(item)) {
                    // error deleting the file; report it and return
                    ActionForward fwd = mapping.findForward("basic");
                    return fwd;
                }
            }
            // these attributes will need to be updated for both new file and replacement
            // but we can't update until the old file is removed, as above
            EdoChecklistV edoChecklist = EdoServiceLocator.getChecklistVService().getChecklistItemByID(BigDecimal.valueOf(checklistItemID));
          //  if(StringUtils.equals(EdoConstants.EDO_SUPPLEMENTAL_ITEM_CATEGORY_NAME, edoChecklist.getChecklistItemName())) {
          if(StringUtils.equals(EdoConstants.EDO_SUPPLEMENTAL_ITEM_CATEGORY_NAME, edoChecklist.getChecklistItemName()) || StringUtils.equals(EdoConstants.EDO_RECONSIDERATION_ITEM_CATEGORY_NAME, edoChecklist.getChecklistItemName())) {
	
            	item.setAddendumRouted(BigDecimal.ONE);
            }
            else {
            	item.setAddendumRouted(BigDecimal.ZERO);
            }
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
        edoChecklistItemForm.setItemList(itemList);

        if (itemList != null && itemList.size() > 0) {
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

