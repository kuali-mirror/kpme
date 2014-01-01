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
package org.kuali.kpme.tklm.leave.calendar.rules;

import org.kuali.kpme.core.KPMENamespace;
import org.kuali.kpme.core.api.assignment.Assignable;
import org.kuali.kpme.core.krms.KPMERulesEngineExecuter;
import org.kuali.kpme.core.krms.KpmeKrmsFactBuilderService;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.tklm.common.krms.TklmKrmsConstants;
import org.kuali.kpme.tklm.leave.calendar.LeaveCalendarDocument;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.krms.api.engine.Engine;
import org.kuali.rice.krms.api.engine.EngineResults;
import org.kuali.rice.krms.api.engine.Facts;
import org.kuali.rice.krms.api.engine.SelectionCriteria;

import java.util.HashMap;
import java.util.Map;


public class LeaveCalendarRulesEngineExecutor extends KPMERulesEngineExecuter {
    @Override
    protected EngineResults performExecute(RouteContext routeContext, Engine engine) {
        Map<String, String> contextQualifiers = new HashMap<String, String>();
        contextQualifiers.put("namespaceCode", KPMENamespace.KPME_LM.getNamespaceCode());
        contextQualifiers.put("name", TklmKrmsConstants.Calendar.KPME_LEAVE_CALENDAR_CONTEXT_NAME);

        // extract facts from routeContext
        String docContent = routeContext.getDocument().getDocContent();

        SelectionCriteria selectionCriteria = SelectionCriteria.createCriteria(null, contextQualifiers,
                new HashMap<String, String>());

        //Get timesheet document to create facts
        LeaveCalendarDocument doc = LmServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(routeContext.getDocument().getDocumentId());
        KpmeKrmsFactBuilderService fbService = HrServiceLocator.getService("calendarDocumentFactBuilderService");
        Facts.Builder factsBuilder = Facts.Builder.create();
        fbService.addFacts(factsBuilder, doc, TklmKrmsConstants.Calendar.KPME_LEAVE_CALENDAR_CONTEXT_NAME, KPMENamespace.KPME_LM.getNamespaceCode());
        factsBuilder.addFact(Assignable.ASSIGNABLE_TERM_NAME, doc);
        return engine.execute(selectionCriteria, factsBuilder.build(), null);
    }
}
