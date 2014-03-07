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
package org.kuali.kpme.pm.workflow.krms;

import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.krms.KpmeKrmsFactBuilderServiceHelper;
import org.kuali.kpme.pm.api.common.krms.PmKrmsConstants;
import org.kuali.kpme.pm.position.Position;
import org.kuali.kpme.pm.positiontype.PositionType;
import org.kuali.kpme.pm.service.base.PmServiceLocator;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krms.api.engine.Facts;
import org.kuali.rice.krms.api.engine.Term;

import java.util.HashSet;
import java.util.Set;

public class PMFactBuilderServiceImpl extends KpmeKrmsFactBuilderServiceHelper {
    @Override
    public void addFacts(Facts.Builder factsBuilder, String docContent) {
        //remove me?
    }

    @Override
    public void addFacts(Facts.Builder factsBuilder, Object factObject) {
        addFacts(factsBuilder, factObject, KPMENamespace.KPME_PM.getNamespaceCode(), PmKrmsConstants.Context.PM_CONTEXT_NAME);
    }

    @Override
    public void addFacts(Facts.Builder factsBuilder, Object factObject, String contextId, String namespace) {
        addObjectMembersAsFacts(factsBuilder, factObject, contextId, namespace);

        if (factObject != null && factObject instanceof MaintenanceDocument) {
            MaintenanceDocument document = (MaintenanceDocument) factObject;

            if (document.getNewMaintainableObject().getDataObject() != null && document.getNewMaintainableObject().getDataObject() instanceof Position) {
                Position newPosition = (Position) document.getNewMaintainableObject().getDataObject();

                PositionType positionType = (PositionType) PmServiceLocator.getPositionTypeService().getPositionType(newPosition.getPositionType(),newPosition.getEffectiveLocalDate());

                factsBuilder.addFact(new Term("academicFlag"), positionType.isAcademicFlag());
                factsBuilder.addFact(new Term("positionProcess"), newPosition.getProcess());

                if (document.isOldDataObjectInDocument()) {
                    Position oldPosition = (Position) document.getOldMaintainableObject().getDataObject();
                    if (newPosition.getPrimaryDepartment().equals(oldPosition.getPrimaryDepartment())) {
                        factsBuilder.addFact(new Term("primaryDepartmentChanged"), Boolean.FALSE);
                    } else {
                        factsBuilder.addFact(new Term("primaryDepartmentChanged"), Boolean.TRUE);
                    }
                } else {
                    factsBuilder.addFact(new Term("primaryDepartmentChanged"), Boolean.FALSE);
                }
        }
        }
    }
}
