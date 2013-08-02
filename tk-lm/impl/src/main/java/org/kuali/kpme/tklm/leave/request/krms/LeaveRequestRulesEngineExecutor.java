package org.kuali.kpme.tklm.leave.request.krms;

import org.kuali.kpme.core.KPMENamespace;
import org.kuali.kpme.core.api.assignment.Assignable;
import org.kuali.kpme.core.krms.KPMERulesEngineExecuter;
import org.kuali.kpme.core.krms.KpmeKrmsFactBuilderService;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.tklm.common.krms.TklmKrmsConstants;
import org.kuali.kpme.tklm.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.leave.request.service.LeaveRequestDocumentService;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.leave.workflow.LeaveRequestDocument;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krms.api.engine.Engine;
import org.kuali.rice.krms.api.engine.EngineResults;
import org.kuali.rice.krms.api.engine.Facts;
import org.kuali.rice.krms.api.engine.SelectionCriteria;

import java.util.HashMap;
import java.util.Map;


public class LeaveRequestRulesEngineExecutor extends KPMERulesEngineExecuter {
    @Override
    protected EngineResults performExecute(RouteContext routeContext, Engine engine) {
        Map<String, String> contextQualifiers = new HashMap<String, String>();
        contextQualifiers.put("namespaceCode", KPMENamespace.KPME_LM.getNamespaceCode());
        contextQualifiers.put("name", TklmKrmsConstants.Context.LEAVE_REQUEST_CONTEXT_NAME);

        // extract facts from routeContext
        String docContent = routeContext.getDocument().getDocContent();

        SelectionCriteria selectionCriteria = SelectionCriteria.createCriteria(null, contextQualifiers,
                new HashMap<String, String>());

        //Get leave request document to create facts
        String documentId = routeContext.getDocument().getDocumentId();
        LeaveBlock leaveBlock = null;
        MaintenanceDocument document = null;
        LeaveRequestDocumentService lrds = LmServiceLocator.getLeaveRequestDocumentService();
        LeaveRequestDocument request = lrds.getLeaveRequestDocument(documentId);
        if (request != null) {
            leaveBlock = request.getLeaveBlock();
        }

        KpmeKrmsFactBuilderService fbService = HrServiceLocator.getService("lmFactBuilderService");
        Facts.Builder factsBuilder = Facts.Builder.create();
        //factsBuilder.addFact()
        fbService.addFacts(factsBuilder, leaveBlock, TklmKrmsConstants.Context.LM_CONTEXT_NAME, KPMENamespace.KPME_LM.getNamespaceCode());
        EngineResults results = engine.execute(selectionCriteria, factsBuilder.build(), null);
        return results;
    }
}
