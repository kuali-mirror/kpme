/**
 * Copyright 2004-2013 The Kuali Foundation
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
package org.kuali.hr.time.missedpunch;

import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.hr.time.util.TKContext;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kns.document.authorization.TransactionalDocumentAuthorizerBase;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.util.KRADConstants;

public class MissedPunchAuthorizer extends TransactionalDocumentAuthorizerBase {

	private static final Logger LOG = Logger.getLogger(MissedPunchAuthorizer.class);
	
	@Override
	public boolean canEditDocumentOverview(Document document, Person user) {
		return true;
	}

	@Override
	public Set<String> getDocumentActions(Document document, Person user,
			Set<String> documentActions) {
		Set<String> author =  super.getDocumentActions(document, user, documentActions);
		MissedPunchDocument mpDoc = (MissedPunchDocument)document;
        DocumentStatus documentStatus = document.getDocumentHeader().getWorkflowDocument().getStatus();

		if(DocumentStatus.ENROUTE.equals(documentStatus) ){
			if(KewApiServiceLocator.getWorkflowDocumentActionsService().isFinalApprover(mpDoc.getDocumentNumber(), TKContext.getPrincipalId())){
				author.add(KRADConstants.KUALI_ACTION_CAN_EDIT);
				author.add(KRADConstants.KUALI_ACTION_CAN_APPROVE);
			}
		} else if(DocumentStatus.INITIATED.equals(documentStatus)
                    || DocumentStatus.SAVED.equals(documentStatus)){
			author.add(KRADConstants.KUALI_ACTION_CAN_EDIT);
			author.add(KRADConstants.KUALI_ACTION_CAN_ROUTE);
		} else {
            author.remove(KRADConstants.KUALI_ACTION_CAN_APPROVE);
        }
		author.remove(KRADConstants.KUALI_ACTION_CAN_ADD_ADHOC_REQUESTS);
		author.remove(KRADConstants.KUALI_ACTION_CAN_SEND_ADHOC_REQUESTS);

        //can never disapprove
        author.remove(KRADConstants.KUALI_ACTION_CAN_DISAPPROVE);
		return author;
	}
	
	
	
}
