package org.kuali.kpme.edo.item.web;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.base.web.EdoAction;
import org.kuali.kpme.edo.item.EdoItem;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.kpme.edo.util.QueryParams;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.MessageMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 3/13/13
 * Time: 3:18 PM
 */
public class EdoDeleteConfirmAction extends EdoAction {

    static final Logger LOG = Logger.getLogger(EdoDeleteConfirmAction.class);
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String op   = ((EdoDeleteConfirmForm)form).getOperation();
        String data = ((EdoDeleteConfirmForm)form).getFormData();
        String nidParam = "x_x_1";
        MessageMap msgmap = GlobalVariables.getMessageMap();

        if (request.getParameterMap().containsKey("nid")) {
            nidParam = request.getParameter("nid");
            request.setAttribute("nidFwd", nidParam);
        }

        QueryParams itemData = new QueryParams(data);
        List<String> params = itemData.getParameterValues("itemID");

        if (op.equalsIgnoreCase("confirm")) {
            List<EdoItem> itemList = new LinkedList<EdoItem>();
            for (String id : params) {
                EdoItem item = EdoServiceLocator.getEdoItemDao().getEdoItem(BigDecimal.valueOf(Integer.parseInt(id)));

                // delete file from the file system
                File itemFile = new File(item.getFileLocation());

                if (itemFile.exists()) {
                    if (itemFile.canWrite()) {
                        try {
                            boolean deleteResult = itemFile.delete();
                            if (!deleteResult) {
                                msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS,"error.fileDelete.null", "Error deleting file");
                                LOG.error("Error deleting file [" + item.getFileLocation() + "]");
                            }
                        } catch (Exception e) {
                            msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS,"error.fileDelete.exception", e.getMessage() );
                            LOG.error("Error deleting file [" + item.getFileLocation() + "] " + e.getMessage() );
                        }
                    } else {
                        LOG.error("Can't delete file from [" + item.getFileLocation() + "]");
                        msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS,"error.fileDelete.access", "Permissions incorrect for this file");
                    }
                } else {
                    msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS,"error.fileDelete.invalid", "The file does not exist");
                    LOG.warn("Missing file from file system [" + item.getFileLocation() + "]");
                }
                // all is well with the file system, delete the item from the database
                EdoServiceLocator.getEdoItemDao().deleteItem(item);
            }
        }

        ActionRedirect redirect = new ActionRedirect(mapping.findForward("cancel"));
        redirect.addParameter("nid", ((EdoDeleteConfirmForm) form).getNidFwd());
        redirect.addParameter("globalmsgmap", msgmap );

        return redirect;
    }
}
