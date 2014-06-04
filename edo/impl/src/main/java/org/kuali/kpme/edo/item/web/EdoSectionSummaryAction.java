package org.kuali.kpme.edo.item.web;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.base.web.EdoAction;
import org.kuali.kpme.edo.candidate.EdoSelectedCandidate;
import org.kuali.kpme.edo.checklist.EdoChecklist;
import org.kuali.kpme.edo.item.count.EdoItemCountV;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
        List<EdoChecklist> checklistView;
        List<EdoItemCountV> itemCount;
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
        checklistView = cliForm.getChecklistView();
        List<EdoChecklist> currentCheckListSection = new LinkedList<EdoChecklist>();
        for (EdoChecklist chklist : checklistView ) {
            checklistSectionId = chklist.getChecklistSectionID();
            if ( checklistSectionId.intValue() == currentSectionId ) {
                currentCheckListSection.add(chklist);
                request.setAttribute("nodeID", currentSectionId );
                request.setAttribute("sectionName", chklist.getChecklistSectionName() );
                request.setAttribute("checklistSectionID", checklistSectionId.intValue() );
                request.setAttribute("nidFwd", ssn.getAttribute("nid"));
            }
        }

        itemCount = EdoServiceLocator.getEdoItemCountVService().getItemCount(selectedCandidate.getCandidateDossierID(), BigDecimal.valueOf(currentSectionId));
        cliForm.setItemCount(itemCount);
        cliForm.setChecklistItemID(currentSectionId);

        Map<BigDecimal, EdoItemCountV> itemCountVMap = new HashMap<BigDecimal, EdoItemCountV>();
        for (EdoItemCountV itemCountV : itemCount) {
            itemCountVMap.put(itemCountV.getChecklistItemId(), itemCountV);
        }

        int count = 0;

        for (EdoChecklist checklistItem : currentCheckListSection) {
            if (itemCountVMap.containsKey(checklistItem.getChecklistItemID())) {
                String itemJSON = itemCountVMap.get(checklistItem.getChecklistItemID()).getItemCountJSON() + ",";
                itemJSON = itemJSON.replace("[", "[\"" + count + "\",");
                itemCountJSON = itemCountJSON.concat(itemJSON);
                count++;
            } else {
                EdoItemCountV itemCountV = new EdoItemCountV();
                itemCountV.setChecklistItemId(checklistItem.getChecklistItemID());
                itemCountV.setChecklistItemName(checklistItem.getChecklistItemName());
                itemCountV.setChecklistSectionId(checklistItem.getChecklistSectionID());
                itemCountV.setDocCount(BigDecimal.ZERO);
                itemCountV.setDossierId(selectedCandidate.getCandidateDossierID());
                String itemJSON = itemCountV.getItemCountJSON() + ",";
                itemJSON = itemJSON.replace("[", "[\"" + count + "\",");
                itemCountJSON = itemCountJSON.concat(itemJSON);
                count++;
            }
        }

        request.setAttribute("itemcount", itemCount);
        request.setAttribute("itemcountJSON", itemCountJSON);

        String path = request.getRequestURL().toString().replace(request.getServletPath(), "").concat("/");
        request.setAttribute("requestPath", path);

        return super.execute(mapping, cliForm, request, response);
    }
}
