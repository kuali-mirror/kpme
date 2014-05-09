/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.core.assignment.web;

import org.kuali.kpme.core.api.assignment.Assignment;
import org.kuali.kpme.core.assignment.AssignmentBo;
import org.kuali.kpme.core.lookup.KpmeHrBusinessObjectLookupableImpl;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.form.LookupForm;

import java.util.List;
import java.util.Map;

@SuppressWarnings("deprecation")
public class AssignmentLookupableHelper extends KpmeHrBusinessObjectLookupableImpl {

	private static final long serialVersionUID = 774015772672806415L;
	private static final ModelObjectUtils.Transformer<Assignment, AssignmentBo> toAssignmentBo =
            new ModelObjectUtils.Transformer<Assignment, AssignmentBo>() {
                public AssignmentBo transform(Assignment input) {
                    return AssignmentBo.from(input);
                };
            };


	@Override
	protected List<?> getSearchResults(LookupForm form,
			Map<String, String> searchCriteria, boolean unbounded) {

        String userPrincipalId = GlobalVariables.getUserSession().getPrincipalId();

        return ModelObjectUtils.transform(HrServiceLocator.getAssignmentService().searchAssignments(userPrincipalId, searchCriteria), toAssignmentBo);
	}
}