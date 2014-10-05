package org.kuali.kpme.edo.item.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.kpme.edo.api.item.EdoItem;
import org.kuali.kpme.edo.base.web.EdoAction;
import org.kuali.kpme.edo.item.EdoItemTracker;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.rice.core.api.config.property.Config;

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
        List<EdoItem> itemList;
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

        // TODO just pass itemTracker.getItemsMarked() when ItemTracker is ready (uncomment the line below).  
        // For now, we will convert an array of BigDecimal to an array of String
        // itemList = EdoServiceLocator.getEdoItemService().getListOfEdoItems(itemTracker.getItemsMarked());
        itemList = EdoServiceLocator.getEdoItemService().getListOfEdoItems(convertToListOfString(itemTracker.getItemsMarked()));
       
        cliForm.setItemList(itemList);
        cliForm.setChecklistItemID(currentTreeNodeID);

        if (itemList != null && itemList.size() > 0) {
        	Collections.sort(itemList);
            for (EdoItem item : itemList) {
                itemListJSON = itemListJSON.concat(EdoServiceLocator.getEdoItemService().getItemJSONString(item) + ",");
            }
        }
        request.setAttribute("itemlistJSON", itemListJSON);

        String path = request.getRequestURL().toString().replace(request.getServletPath(), "").concat("/");
        request.setAttribute("requestPath", path);

        request.setAttribute("itemTracker", itemTracker.getItemTrackerJSONString());

        return super.execute(mapping, cliForm, request, response);
    }
    
    private List<String> convertToListOfString(List<BigDecimal> bds) {
    	List<String> trackerIDs = new ArrayList<String>();
        for (BigDecimal bd: bds) {
        	trackerIDs.add(bd.toString());
        }
        return trackerIDs;
    }

}
