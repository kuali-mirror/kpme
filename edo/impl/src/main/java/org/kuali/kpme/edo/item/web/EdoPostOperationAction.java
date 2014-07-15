package org.kuali.kpme.edo.item.web;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.kpme.edo.api.item.EdoItem;
import org.kuali.kpme.edo.base.web.EdoAction;
import org.kuali.kpme.edo.candidate.EdoSelectedCandidate;
import org.kuali.kpme.edo.item.EdoItemBo;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.kpme.edo.util.QueryParams;
import org.kuali.kpme.edo.web.EdoPostOperationForm;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.MessageMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 3/12/13
 * Time: 9:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class EdoPostOperationAction extends EdoAction {

    static final Logger LOG = Logger.getLogger(EdoPostOperationAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String data = ((EdoPostOperationForm)form).getFormData();
        String nidParam = ((EdoPostOperationForm)form).getNidFwd();

        String itemListJSON = "";
        EdoSelectedCandidate selectedCandidate = (EdoSelectedCandidate) request.getSession().getAttribute("selectedCandidate");

        if (request.getParameterMap().containsKey("nid")) {
            nidParam = request.getParameter("nid");
        }
        if (request.getParameterMap().containsKey("nidFwd")) {
            nidParam = request.getParameter("nidFwd");
        }

        if (data == null) {
            // error!  handle it here;
            return super.execute(mapping, form, request, response);
        }

        QueryParams itemData = new QueryParams(data);
        List<String> params = itemData.getParameterValues("itemID");

        request.setAttribute("formData", data);
        request.setAttribute("nidFwd", nidParam);
        request.setAttribute("itemlistJSON", itemListJSON);

        return super.execute(mapping, form, request, response);
    }

    public ActionForward deleteConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String itemListJSON = "";
        String data = ((EdoPostOperationForm)form).getFormData();
        String nidParam = ((EdoPostOperationForm)form).getNidFwd();
        MessageMap msgmap = GlobalVariables.getMessageMap();
        EdoSelectedCandidate selectedCandidate = (EdoSelectedCandidate) request.getSession().getAttribute("selectedCandidate");

        ArrayList<ArrayList<String>> fileList = new ArrayList<ArrayList<String>>();
        Type tmpType = new TypeToken<List<List<String>>>() {}.getType();
        Gson gson = new Gson();

        if (request.getParameterMap().containsKey("nid")) {
            nidParam = request.getParameter("nid");
        }
        if (request.getParameterMap().containsKey("nidFwd")) {
            nidParam = request.getParameter("nidFwd");
        }
        int currentTreeNodeID = Integer.parseInt(nidParam.split("_")[2]);

        if (data == null) {
            // error!  handle it here;
            return super.execute(mapping, form, request, response);
        }

        QueryParams itemData = new QueryParams(data);
        List<String> params = itemData.getParameterValues("itemID");

        for (String id : params) {
            EdoItem item = EdoItemBo.to(EdoServiceLocator.getEdoItemDao().getEdoItem(id));

            // delete file from the file system
            File itemFile = new File(item.getFileLocation());
            Boolean fsOK = true;

            if (itemFile.exists()) {
                if (itemFile.canWrite()) {
                    try {
                        boolean deleteResult = itemFile.delete();
                        if (!deleteResult) {
                            msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS,"error.fileDelete.null", "Error deleting file");
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
                    msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS,"error.fileDelete.access", "Permissions incorrect for this file");
                }
                if (fsOK) {
                    // all is well with the file system, delete the item from the database
                    EdoServiceLocator.getEdoItemDao().deleteItem(EdoItemBo.from(item));
                }
            } else {
                msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS,"error.fileDelete.invalid", "The file does not exist");
                LOG.warn("Missing file from file system [" + item.getFileLocation() + "]");
            }
        }

        List<EdoItem> itemList = EdoServiceLocator.getEdoItemService().getItemList(selectedCandidate.getCandidateDossierID().toString(), currentTreeNodeID+"");

        if (itemList != null && itemList.size() > 0) {
        	Collections.sort(itemList);
            for (EdoItem item : itemList) {
                itemListJSON = itemListJSON.concat(EdoServiceLocator.getEdoItemService().getItemJSONString(item) + ",");
            }
        }

        String path = request.getRequestURL().toString().replace(request.getServletPath(), "").concat("/");
        itemListJSON = gson.toJson(fileList, tmpType);

        request.setAttribute("requestPath", path);
        request.setAttribute("itemlistJSON", itemListJSON);
        request.setAttribute("nidFwd", nidParam);

        return super.execute(mapping, form, request, response);
    }
}
