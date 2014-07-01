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
