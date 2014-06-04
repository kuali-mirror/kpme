package org.kuali.kpme.edo.item.web;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.apache.struts.upload.FormFile;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.base.web.EdoAction;
import org.kuali.kpme.edo.candidate.EdoSelectedCandidate;
import org.kuali.kpme.edo.item.EdoItem;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.kpme.edo.util.EdoContext;
import org.kuali.kpme.edo.util.EdoUtils;
import org.kuali.rice.core.api.config.property.Config;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.MessageMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 1/28/13
 * Time: 2:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class EdoPostFileAction extends EdoAction {

    private static final int THRESHHOLD_SIZE = 1024 * 1024 * 3;
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 40;
    private static final int REQUEST_SIZE = 1024 * 1024 * 50;
    private static final String DEFAULT_MIME_TYPE = "application/octet-stream";
    protected Config config;
    private static final Logger LOG = Logger.getLogger(EdoPostFileAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        EdoChecklistItemForm edoPostFileForm = (EdoChecklistItemForm)form;
        FormFile uploadFile = edoPostFileForm.getUploadFile();
        int checklistItemID = edoPostFileForm.getChecklistItemID();
        EdoItem item = new EdoItem();
        String uploadUsername = EdoContext.getUser().getNetworkId();
        config = ConfigContext.getCurrentContextConfig();
        MessageMap msgmap = GlobalVariables.getMessageMap();

        // pull candidate details needed for storing the item in the db
        EdoSelectedCandidate candidate = (EdoSelectedCandidate)request.getSession().getAttribute("selectedCandidate");
        String candidateUsername = candidate.getCandidateUsername();
        BigDecimal dossierID     = candidate.getCandidateDossierID();

        if (ServletFileUpload.isMultipartContent(request)) {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(THRESHHOLD_SIZE);
            factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setFileSizeMax(MAX_FILE_SIZE);
            upload.setSizeMax(REQUEST_SIZE);
            
           

            String storeFileName = candidateUsername + "_" + uploadFile.getFileName();
            String fileName      = uploadFile.getFileName();
            String contentType   = uploadFile.getContentType();

            if (contentType.contains("Content-Dispostion") || contentType.isEmpty()) {
                contentType = DEFAULT_MIME_TYPE;
            }
            String uploadPath    = EdoUtils.BuildUploadPath(candidateUsername, Integer.toString(checklistItemID));

            if (uploadPath == null) {
                LOG.error("Upload path does not exist: [" + uploadPath + "]");
                msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS,"error.fileUpload.path");
                LOG.error("\n\n the upload path is null. It's either the candidateUserName is null or checklistItemID is null \n\n");
                return super.execute(mapping, form, request, response);
            }

      
            if (StringUtils.isNotBlank(storeFileName)) {
                File newFile = new File(uploadPath, storeFileName);
                if (!newFile.exists()) {
                    try {
                        FileOutputStream fos = new FileOutputStream(newFile);
                        fos.write(uploadFile.getFileData());
                        fos.flush();
                        fos.close();

                        request.setAttribute("message", "Upload has been completed successfully");
                        java.util.Date date = new java.util.Date();
                        long t = date.getTime();
                       
                        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);

                        // populate the item object to save
                        item.setFileName(fileName);
                        item.setFileLocation(uploadPath + File.separator + storeFileName);
                        item.setContentType(contentType);
                        item.setCreateDate(sqlTimestamp);
                        // TODO: this will need to be a dynamic value, not hard coded
                        item.setItemTypeID(BigDecimal.valueOf(1));
                        item.setAddendumRouted(BigDecimal.ZERO);
                        item.setUploaderUsername(uploadUsername);
                        item.setUploadDate(sqlTimestamp);
                        item.setCreatedBy(uploadUsername);
                        item.setUpdatedBy(uploadUsername);
                        item.setCreateDate(sqlTimestamp);
                        item.setLastUpdateDate(sqlTimestamp);
                        item.setDossierID(dossierID);
                        item.setChecklistItemID( BigDecimal.valueOf(checklistItemID) );
                        Integer nextRowIndexNum = EdoServiceLocator.getEdoItemService().getNextRowIndexNum(BigDecimal.valueOf(checklistItemID), uploadUsername);
                        item.setRowIndex(nextRowIndexNum);

                        EdoServiceLocator.getEdoItemService().saveOrUpdate(item);

                    } catch (Exception e) {
                        LOG.error("An exception occurred writing the file: [" + e.getMessage() + "]");
                        msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.fileUpload.exception", e.getMessage() );
                    }
                } else {
                    LOG.error("The file already exists in this checklist section");
                    msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS,"error.fileUpload.exists");
                }
            }
        //   }
          
        }

        ActionRedirect redirect = new ActionRedirect(mapping.findForward("list"));
        redirect.addParameter("nid", ((EdoChecklistItemForm) form).getNidFwd());
        redirect.addParameter("globalmsgmap", msgmap );

        return redirect;
    }
}
