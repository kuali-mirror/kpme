package org.kuali.kpme.edo.candidate.web;

import org.kuali.kpme.edo.base.web.EdoForm;
import org.kuali.kpme.edo.candidate.EdoCandidateBo;
import org.kuali.kpme.edo.checklist.EdoChecklist;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.api.candidate.EdoCandidate;

import java.util.List;
import java.util.SortedMap;

/**
 * Created with IntelliJ IDEA.
 * User: tcbradley
 * Date: 10/19/12
 * Time: 1:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class EdoCandidateListForm extends EdoForm {

    private List<EdoCandidate> candidateLists = EdoServiceLocator.getCandidateService().getCandidateList();
    private List<EdoChecklist> checklistView = EdoServiceLocator.getChecklistViewService().getCheckListView("BL", "SPEA", "SPEA");
    private SortedMap<String, List<EdoChecklist>> checklistHash = EdoServiceLocator.getChecklistViewService().getCheckListHash("BL","SPEA","SPEA");

    public void setCandidateLists( List<EdoCandidate> list) {
        candidateLists = list;
    }

    public List<EdoCandidate> getCandidateLists() {
        return candidateLists;
    }

    public List<EdoChecklist> getChecklistView() {
        return checklistView;
    }

    public SortedMap<String, List<EdoChecklist>> getChecklistHash() {
        return checklistHash;
    }
}
