package org.kuali.kpme.edo.candidate.web;

import java.util.List;

import org.kuali.kpme.edo.api.candidate.EdoCandidate;
import org.kuali.kpme.edo.api.checklist.EdoChecklistItem;
import org.kuali.kpme.edo.base.web.EdoForm;
import org.kuali.kpme.edo.service.EdoServiceLocator;

/**
 * Created with IntelliJ IDEA.
 * User: tcbradley
 * Date: 10/19/12
 * Time: 1:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class EdoCandidateListForm extends EdoForm {

    private List<EdoCandidate> candidateLists = EdoServiceLocator.getCandidateService().getCandidateList();
    private List<EdoChecklistItem> checklistItems = EdoServiceLocator.getChecklistItemService().getChecklistItems("BL-IN", "SPEA", "SPEA");
    /* This is not used anywhere in EdoCandidateListAction
    private SortedMap<String, List<EdoChecklistV>> checklistHash = EdoServiceLocator.getChecklistVService().getCheckListHash("BL","SPEA","SPEA"); */

    public void setCandidateLists( List<EdoCandidate> list) {
        candidateLists = list;
    }

    public List<EdoCandidate> getCandidateLists() {
        return candidateLists;
    }

    public List<EdoChecklistItem> getChecklistItems() {
		return checklistItems;
	}
}
