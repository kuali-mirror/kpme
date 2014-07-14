package org.kuali.kpme.edo.item.web;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.kpme.edo.api.checklist.EdoChecklistItem;
import org.kuali.kpme.edo.api.checklist.EdoChecklistSection;
import org.kuali.kpme.edo.base.web.EdoAction;
import org.kuali.kpme.edo.candidate.EdoSelectedCandidate;
import org.kuali.kpme.edo.service.EdoServiceLocator;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 6/24/13
 * Time: 10:03 AM
 */
public class EdoSectionSummaryAction  extends EdoAction {
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        BigDecimal checklistItemId = null;
        BigDecimal checklistSectionId = null;
        int currentTreeNodeID;
        int currentSectionId;
        List<EdoChecklistItem> checklistItems;
        int itemCount;
        EdoSelectedCandidate selectedCandidate = (EdoSelectedCandidate) request.getSession().getAttribute("selectedCandidate");
        String itemCountJSON = "";
        EdoSectionSummaryForm cliForm = (EdoSectionSummaryForm) form;
        HttpSession ssn = request.getSession();

        if (request.getParameterMap().containsKey("nid")) {
            ssn.setAttribute("nid", request.getParameter("nid"));
        }
        ssn.setAttribute("currentNodeID", Integer.parseInt(ssn.getAttribute("nid").toString().split("_")[2]));
        //currentTreeNodeID = Integer.parseInt(ssn.getAttribute("nid").toString().split("_")[2]);
        currentSectionId = Integer.parseInt(ssn.getAttribute("nid").toString().split("_")[2]);

        // set page request variables for title and description
        checklistItems = cliForm.getChecklistItems();
        // currentCheckListSection is a list of EdoChecklistItems as a result of removing EdochecklistV, but the variable is used in
        // several places, so we are not changing the name although it's misleading (not a list of Checklist Section)
        List<EdoChecklistItem> currentCheckListSection = new LinkedList<EdoChecklistItem>();
        for (EdoChecklistItem checklistItem : checklistItems) {
            checklistSectionId = new BigDecimal(checklistItem.getEdoChecklistSectionId());
            if ( checklistSectionId.intValue() == currentSectionId ) {
                currentCheckListSection.add(checklistItem);
                request.setAttribute("nodeID", currentSectionId );
                EdoChecklistSection section = EdoServiceLocator.getChecklistSectionService().getChecklistSectionByID(checklistItem.getEdoChecklistSectionId());
                request.setAttribute("sectionName", section.getChecklistSectionName());
                request.setAttribute("checklistSectionID", checklistSectionId.intValue() );
                request.setAttribute("nidFwd", ssn.getAttribute("nid"));
            }
        }

        itemCount = EdoServiceLocator.getEdoItemService().getItemCount(selectedCandidate.getCandidateDossierID().toString(), currentSectionId+"");
        cliForm.setItemCount(itemCount);
        cliForm.setChecklistItemID(currentSectionId);

        /* KPME-3705 comment out this section since we are redesigning the GUI
        Map<BigDecimal, EdoItemCountV> itemCountVMap = new HashMap<BigDecimal, EdoItemCountV>();
        for (EdoItemCountV itemCountV : itemCount) {
            itemCountVMap.put(itemCountV.getChecklistItemId(), itemCountV);
        } 

        int count = 0;

        for (EdoChecklistItem checklistItem : currentCheckListSection) {
            if (itemCountVMap.containsKey(checklistItem.getEdoChecklistItemId())) {
                String itemJSON = itemCountVMap.get(checklistItem.getEdoChecklistItemId()).getItemCountJSON() + ",";
                itemJSON = itemJSON.replace("[", "[\"" + count + "\",");
                itemCountJSON = itemCountJSON.concat(itemJSON);
                count++;
            } else {
                EdoItemCountV itemCountV = new EdoItemCountV();
                itemCountV.setChecklistItemId(new BigDecimal(checklistItem.getEdoChecklistItemId()));
                itemCountV.setChecklistItemName(checklistItem.getChecklistItemName());
                itemCountV.setChecklistSectionId(new BigDecimal(checklistItem.getEdoChecklistSectionId()));
                itemCountV.setDocCount(BigDecimal.ZERO);
                itemCountV.setDossierId(selectedCandidate.getCandidateDossierID());
                String itemJSON = itemCountV.getItemCountJSON() + ",";
                itemJSON = itemJSON.replace("[", "[\"" + count + "\",");
                itemCountJSON = itemCountJSON.concat(itemJSON);
                count++;
            }
        } */

        request.setAttribute("itemcount", itemCount);
        request.setAttribute("itemcountJSON", itemCountJSON);

        String path = request.getRequestURL().toString().replace(request.getServletPath(), "").concat("/");
        request.setAttribute("requestPath", path);

        return super.execute(mapping, cliForm, request, response);
    }
}
