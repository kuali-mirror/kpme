package org.kuali.hr.time.assignment.validation;


import org.kuali.hr.core.document.question.KpmeEffectiveDatePromptBase;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.AssignmentDescriptionKey;
import org.kuali.hr.time.assignment.service.AssignmentService;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.krad.bo.PersistableBusinessObject;

public class AssignmentEffectiveDatePrompt extends KpmeEffectiveDatePromptBase {
    @Override
    protected boolean futureEffectiveDateExists(PersistableBusinessObject pbo) {
        Assignment assignment = (Assignment)pbo;

        AssignmentDescriptionKey key = new AssignmentDescriptionKey(assignment);
        //check for future recort
        AssignmentService assignmentService = TkServiceLocator.getAssignmentService();

        Assignment lastAssignment = assignmentService.getAssignment(assignment.getPrincipalId(), key, TKUtils.END_OF_TIME);
        if (lastAssignment == null) {
            return false;
        }
        return TKUtils.getTimelessDate(lastAssignment.getEffectiveDate()).after(TKUtils.getTimelessDate(assignment.getEffectiveDate()));
    }
}
