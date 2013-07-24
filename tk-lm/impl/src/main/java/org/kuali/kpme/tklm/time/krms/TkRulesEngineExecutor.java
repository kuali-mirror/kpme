package org.kuali.kpme.tklm.time.krms;

import org.kuali.kpme.core.KPMENamespace;
import org.kuali.kpme.core.krms.KPMERulesEngineExecuter;
import org.kuali.kpme.core.krms.KpmeKrmsFactBuilderService;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.krms.api.engine.Engine;
import org.kuali.rice.krms.api.engine.EngineResults;
import org.kuali.rice.krms.api.engine.Facts;
import org.kuali.rice.krms.api.engine.SelectionCriteria;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class TkRulesEngineExecutor extends KPMERulesEngineExecuter {
    private final static String TK_TIMESHEET_CONTEXT = "KPME-TK-Context";

    @Override
    protected EngineResults performExecute(RouteContext routeContext, Engine engine) {
        Map<String, String> contextQualifiers = new HashMap<String, String>();
        contextQualifiers.put("namespaceCode", KPMENamespace.KPME_TK.getNamespaceCode());
        contextQualifiers.put("name", TK_TIMESHEET_CONTEXT);

        // extract facts from routeContext
        String docContent = routeContext.getDocument().getDocContent();
        String unitNumber = getElementValue(docContent, "//document/coiDisclosureList[1]/org.kuali.kra.coi.CoiDisclosure[1]/disclosurePersons[1]/org.kuali.kra.coi.disclosure.DisclosurePerson[1]/disclosurePersonUnits[1]/org.kuali.kra.coi.disclosure.DisclosurePersonUnit[1]/unitNumber[1]");

        SelectionCriteria selectionCriteria = SelectionCriteria.createCriteria(null, contextQualifiers,
                Collections.singletonMap("Unit Number", unitNumber));

        //KpmeKrmsFactBuilderService fbService = HrServiceLocator.getService("tkTimesheetFactBuilderService");
        Facts.Builder factsBuilder = Facts.Builder.create();
        //fbService.addFacts(factsBuilder, docContent);
        EngineResults results = engine.execute(selectionCriteria, factsBuilder.build(), null);
        return results;
    }
}
