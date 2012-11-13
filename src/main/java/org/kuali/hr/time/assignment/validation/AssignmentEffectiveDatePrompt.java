/**
 * Copyright 2004-2012 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
