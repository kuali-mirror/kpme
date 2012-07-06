package org.kuali.hr.time.workarea.web;

import java.util.HashSet;
import java.util.Set;

import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kns.document.authorization.TransactionalDocumentAuthorizerBase;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.util.KRADConstants;

public class WorkAreaMaintenanceDocumentAuthorizer extends TransactionalDocumentAuthorizerBase {

	@Override
	public Set<String> getDocumentActions(Document document, Person user, Set<String> documentActions) {
		Set<String> finalDocActs = new HashSet<String>();

		finalDocActs.add(KRADConstants.KUALI_ACTION_CAN_EDIT_DOCUMENT_OVERVIEW);
		finalDocActs.add(KRADConstants.KUALI_ACTION_CAN_EDIT);
		finalDocActs.add(KRADConstants.KUALI_ACTION_CAN_ROUTE);
		finalDocActs.add(KRADConstants.KUALI_ACTION_CAN_SAVE);
		finalDocActs.add(KRADConstants.KUALI_ACTION_CAN_BLANKET_APPROVE);
		finalDocActs.add(KRADConstants.KUALI_ACTION_CAN_CANCEL);

		// all docs can close
		finalDocActs.add(KRADConstants.KUALI_ACTION_CAN_CLOSE);
		return finalDocActs;
	}

}
