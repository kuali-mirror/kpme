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
package org.kuali.kpme.tklm.common.krms;

import org.kuali.kpme.core.KPMENamespace;
import org.kuali.kpme.core.krms.KPMERulesEngineExecuter;
import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.krms.api.engine.Engine;
import org.kuali.rice.krms.api.engine.EngineResults;
import org.kuali.rice.krms.api.engine.Facts;
import org.kuali.rice.krms.api.engine.SelectionCriteria;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jjhanso
 * Date: 6/25/13
 * Time: 11:12 AM
 * To change this template use File | Settings | File Templates.
 */
/*public class CalendarRulesEngineExecutorImpl extends KPMERulesEngineExecuter {
    public EngineResults performExecute(RouteContext routeContext, Engine engine) {
        Map<String, String> contextQualifiers = new HashMap<String, String>();
        contextQualifiers.put("namespaceCode", KPMENamespace.KPME_LM.getNamespaceCode());
        contextQualifiers.put("name", TklmKrmsConstants.IrbProtocol.IRB_PROTOCOL_CONTEXT);

        // extract facts from routeContext
        String docContent = routeContext.getDocument().getDocContent();
        String unitNumber = getElementValue(docContent, "//leadUnitNumber");

        SelectionCriteria selectionCriteria = SelectionCriteria.createCriteria(null, contextQualifiers,
                Collections.singletonMap("Unit Number", unitNumber));

        KcKrmsFactBuilderService fbService = KraServiceLocator.getService("irbProtocolFactBuilderService");
        Facts.Builder factsBuilder = Facts.Builder.create();
        fbService.addFacts(factsBuilder, docContent);
        EngineResults results = engine.execute(selectionCriteria, factsBuilder.build(), null);
        return results;
    }
}*/
