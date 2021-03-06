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
package org.kuali.kpme.tklm.leave.workflow.krms;


import org.apache.commons.collections.CollectionUtils;
import org.kuali.kpme.core.api.assignment.Assignable;
import org.kuali.kpme.core.api.assignment.Assignment;
import org.kuali.kpme.core.api.department.Department;
import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.krms.KpmeKrmsFactBuilderServiceHelper;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.tklm.api.common.krms.TklmKrmsConstants;
import org.kuali.rice.krms.api.engine.Facts;
import org.kuali.rice.krms.api.engine.Term;

import java.util.HashSet;
import java.util.Set;

public class LMFactBuilderServiceImpl extends KpmeKrmsFactBuilderServiceHelper {
    @Override
    public void addFacts(Facts.Builder factsBuilder, String docContent) {
        //remove me?
    }

    @Override
    public void addFacts(Facts.Builder factsBuilder, Object factObject) {
        addFacts(factsBuilder, factObject, KPMENamespace.KPME_LM.getNamespaceCode(), TklmKrmsConstants.Context.LM_CONTEXT_NAME);
    }

    @Override
    public void addFacts(Facts.Builder factsBuilder, Object factObject, String contextId, String namespace) {
        addObjectMembersAsFacts(factsBuilder, factObject, contextId, namespace);
        factsBuilder.addFact(new Term("payrollProcessorApproval"), Boolean.FALSE);
        if (factObject != null
                && factObject instanceof Assignable) {
            Assignable assignable = (Assignable)factObject;
            factsBuilder.addFact(Assignable.ASSIGNABLE_TERM_NAME, assignable);
            Set<String> workAreas = new HashSet<String>();
            Set<String> depts = new HashSet<String>();

            for (Assignment a : assignable.getAssignments()) {
                workAreas.add(String.valueOf(a.getWorkArea()));
                depts.add(a.getDept());
                Department department = HrServiceLocator.getDepartmentService().getDepartment(a.getDept(), a.getGroupKeyCode(), a.getEffectiveLocalDate());
                if (department != null
                        && department.isPayrollApproval()) {
                    factsBuilder.addFact(new Term("payrollProcessorApproval"), Boolean.TRUE);
                }
            }
            if (CollectionUtils.isNotEmpty(depts)) {
                factsBuilder.addFact("department", depts);
            }
            if (CollectionUtils.isNotEmpty(workAreas)) {
                factsBuilder.addFact("workarea", workAreas);
            }
        }
    }
}
