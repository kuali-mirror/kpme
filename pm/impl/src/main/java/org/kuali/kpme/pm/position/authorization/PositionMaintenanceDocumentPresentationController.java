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
package org.kuali.kpme.pm.position.authorization;

import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.maintenance.Maintainable;
import org.kuali.rice.krad.maintenance.MaintenanceDocumentBase;
import org.kuali.rice.krad.maintenance.MaintenanceDocumentPresentationControllerBase;
import org.kuali.rice.krad.service.DocumentDictionaryService;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.util.GlobalVariables;

public class PositionMaintenanceDocumentPresentationController extends MaintenanceDocumentPresentationControllerBase {

    @Override
    public boolean canCopy(Document document) {
        Maintainable obj = ((MaintenanceDocumentBase) document).getNewMaintainableObject();


        //((MaintenanceDocumentBase) document).getDocumentHeader().ge
        DocumentDictionaryService documentDictionaryService = KRADServiceLocatorWeb.getDocumentDictionaryService();

        boolean canCopy = false;
        if (document.getAllowsCopy()) {
            canCopy = true;
        }

        if (!(((PositionDocumentAuthorizer)(documentDictionaryService.getDocumentAuthorizer((String)"PositionMaintenanceDocumentType"))).canCopy(obj.getDataObject(), GlobalVariables.getUserSession().getPerson())))
        {
            canCopy = false;
        }
        return canCopy;
    }
}
