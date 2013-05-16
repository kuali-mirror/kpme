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
package org.kuali.kpme.tklm.time.missedpunch.authorization;

import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.document.TransactionalDocumentAuthorizerBase;

public class MissedPunchDocumentAuthorizer extends TransactionalDocumentAuthorizerBase {

	private static final long serialVersionUID = -7136475330057339088L;

    @Override
    public boolean canEdit(Document document, Person user) {
        return canApprove(document, user);
    }

    @Override
    public boolean canDisapprove(Document document, Person user) {
        return false;
    }

    @Override
    public boolean canSendAdHocRequests(Document document, String actionRequestCd, Person user) {
        return false;
    }
	

}
