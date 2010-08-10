package org.kuali.hr.time.workarea.web;

import java.util.HashSet;
import java.util.Set;

import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kns.document.Document;
import org.kuali.rice.kns.document.authorization.TransactionalDocumentAuthorizerBase;
import org.kuali.rice.kns.util.KNSConstants;

public class WorkAreaMaintenanceDocumentAuthorizer extends TransactionalDocumentAuthorizerBase {

    @Override
    public Set<String> getDocumentActions(Document document, Person user, Set<String> documentActions) {
	Set<String> finalDocActs = new HashSet<String>();

	finalDocActs.add(KNSConstants.KUALI_ACTION_CAN_EDIT__DOCUMENT_OVERVIEW);
	finalDocActs.add(KNSConstants.KUALI_ACTION_CAN_EDIT);
	finalDocActs.add(KNSConstants.KUALI_ACTION_CAN_ROUTE);
	finalDocActs.add(KNSConstants.KUALI_ACTION_CAN_SAVE);
	finalDocActs.add(KNSConstants.KUALI_ACTION_CAN_BLANKET_APPROVE);
	finalDocActs.add(KNSConstants.KUALI_ACTION_CAN_CANCEL);

	//all docs can close
	finalDocActs.add(KNSConstants.KUALI_ACTION_CAN_CLOSE);
	return finalDocActs;
    }

}
