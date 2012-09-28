/**
 * Copyright 2004-2012 The Kuali Foundation
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
