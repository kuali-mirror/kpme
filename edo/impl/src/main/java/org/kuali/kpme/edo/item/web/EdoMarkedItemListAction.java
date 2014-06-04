package org.kuali.kpme.edo.item.web;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.kpme.edo.base.web.EdoAction;
import org.kuali.kpme.edo.item.EdoItemTracker;
import org.kuali.kpme.edo.item.EdoItemV;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.rice.core.api.config.property.Config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 7/15/13
 * Time: 10:41 AM
 */
public class EdoMarkedItemListAction extends EdoAction {

    protected Config config;
    static final Logger LOG = Logger.getLogger(EdoChecklistItemAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        int currentTreeNodeID;
        List<EdoItemV> itemList;
        String itemListJSON = "";
        EdoMarkedItemListForm cliForm = (EdoMarkedItemListForm) form;
        HttpSession ssn = request.getSession();

        if (request.getParameterMap().containsKey("nid")) {
            ssn.setAttribute("nid", request.getParameter("nid"));
        } else if (request.getParameterMap().containsKey("nidFwd")) {
            ssn.setAttribute("nid", request.getParameter("nidFwd"));
        }
        request.setAttribute("nidFwd", ssn.getAttribute("nid"));

        ssn.setAttribute("currentNodeID", Integer.parseInt(ssn.getAttribute("nid").toString().split("_")[2]));
        currentTreeNodeID = Integer.parseInt(ssn.getAttribute("nid").toString().split("_")[2]);
        EdoItemTracker itemTracker = (EdoItemTracker) ssn.getAttribute("itemTracker");

        itemList = EdoServiceLocator.getEdoItemVService().getListOfEdoItems(itemTracker.getItemsMarked());
        cliForm.setItemList(itemList);
        cliForm.setChecklistItemID(currentTreeNodeID);

        if (itemList != null && itemList.size() > 0) {
            Collections.sort(itemList);
            for ( EdoItemV item : itemList ) {
                itemListJSON = itemListJSON.concat(item.getItemJSONString() + ",");
            }
        }
        request.setAttribute("itemlistJSON", itemListJSON);

        String path = request.getRequestURL().toString().replace(request.getServletPath(), "").concat("/");
        request.setAttribute("requestPath", path);

        request.setAttribute("itemTracker", itemTracker.getItemTrackerJSONString());

        return super.execute(mapping, cliForm, request, response);
    }

}
