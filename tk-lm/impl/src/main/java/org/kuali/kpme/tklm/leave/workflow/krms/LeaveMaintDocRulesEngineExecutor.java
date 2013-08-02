package org.kuali.kpme.tklm.leave.workflow.krms;


import org.kuali.kpme.core.KPMENamespace;
import org.kuali.kpme.core.api.assignment.Assignable;
import org.kuali.kpme.core.krms.KPMERulesEngineExecuter;
import org.kuali.kpme.core.krms.KpmeKrmsFactBuilderService;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.tklm.common.krms.TklmKrmsConstants;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krms.api.engine.Engine;
import org.kuali.rice.krms.api.engine.EngineResults;
import org.kuali.rice.krms.api.engine.Facts;
import org.kuali.rice.krms.api.engine.SelectionCriteria;

import java.util.HashMap;
import java.util.Map;

public class LeaveMaintDocRulesEngineExecutor extends KPMERulesEngineExecuter {

    //assumes document's dataobject being routed implements AssignedToAssignment
    @Override
    protected EngineResults performExecute(RouteContext routeContext, Engine engine) {
        Map<String, String> contextQualifiers = new HashMap<String, String>();
        contextQualifiers.put("namespaceCode", KPMENamespace.KPME_LM.getNamespaceCode());
        contextQualifiers.put("name", TklmKrmsConstants.Context.LM_CONTEXT_NAME);

        // extract facts from routeContext
        String docContent = routeContext.getDocument().getDocContent();

        SelectionCriteria selectionCriteria = SelectionCriteria.createCriteria(null, contextQualifiers,
                new HashMap<String, String>());

        //Get timesheet document to create facts
        String documentId = routeContext.getDocument().getDocumentId();
        Object dataObject = null;
        MaintenanceDocument document = null;
        try {
            document = (MaintenanceDocument) KRADServiceLocatorWeb.getDocumentService().getByDocumentHeaderId(documentId);
            if (document.getNewMaintainableObject().getDataObject() != null
                    && document.getNewMaintainableObject().getDataObject() instanceof Assignable) {
                dataObject = document.getNewMaintainableObject().getDataObject();
            }
        } catch (WorkflowException e) {
            e.printStackTrace();
        }

        KpmeKrmsFactBuilderService fbService = HrServiceLocator.getService("lmFactBuilderService");
        Facts.Builder factsBuilder = Facts.Builder.create();
        //factsBuilder.addFact()
        fbService.addFacts(factsBuilder, dataObject, TklmKrmsConstants.Context.LM_CONTEXT_NAME, KPMENamespace.KPME_LM.getNamespaceCode());
        EngineResults results = engine.execute(selectionCriteria, factsBuilder.build(), null);
        return results;
    }
}
