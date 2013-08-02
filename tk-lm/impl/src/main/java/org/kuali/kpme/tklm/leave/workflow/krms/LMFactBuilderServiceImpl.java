package org.kuali.kpme.tklm.leave.workflow.krms;


import org.apache.commons.collections.CollectionUtils;
import org.kuali.kpme.core.KPMENamespace;
import org.kuali.kpme.core.api.assignment.Assignable;
import org.kuali.kpme.core.api.assignment.AssignmentContract;
import org.kuali.kpme.core.department.Department;
import org.kuali.kpme.core.krms.KpmeKrmsFactBuilderServiceHelper;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.tklm.common.krms.TklmKrmsConstants;
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

            for (AssignmentContract a : assignable.getAssignments()) {
                workAreas.add(String.valueOf(a.getWorkArea()));
                depts.add(a.getDept());
                Department department = HrServiceLocator.getDepartmentService().getDepartment(a.getDept(), a.getEffectiveLocalDate());
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
