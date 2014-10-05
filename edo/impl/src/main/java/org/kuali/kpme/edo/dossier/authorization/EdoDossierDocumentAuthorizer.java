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
package org.kuali.kpme.edo.dossier.authorization;

import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.uif.view.ViewAuthorizerBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.document.DocumentViewAuthorizerBase;
import org.kuali.rice.krad.document.TransactionalDocumentAuthorizerBase;

public class EdoDossierDocumentAuthorizer extends DocumentViewAuthorizerBase {//TransactionalDocumentAuthorizerBase

	private static final long serialVersionUID = -7136475330057339088L;

//    @Override
//    public boolean canEdit(Document document, Person user) {
//        return canApprove(document, user);
//    }
//
//    @Override
//    public boolean canDisapprove(Document document, Person user) {
//        return false;
//    }

//    @Override
//    public boolean canSendAdHocRequests(Document document, String actionRequestCd, Person user) {
//        return false;
//    }
    
    @Override
    public boolean canOpenView(View view, ViewModel model, Person user) {
    	return true;
//        DocumentFormBase documentForm = (DocumentFormBase) model;
//
//        return super.canOpenView(view, model, user) && canOpen(documentForm.getDocument(), user);
    }
    
//    @Override
//    public boolean canApprove(Document document, Person user) {
//    	boolean canApprove =  super.canApprove(document, user);
////    	if(canApprove) {
////    		MissedPunchDocument missedPunchDocument = (MissedPunchDocument) document;
////    		if(missedPunchDocument.getPrincipalId().equalsIgnoreCase(user.getPrincipalId())) {
////    			canApprove = false;
////    		}
////    	}
//    	return canApprove;
//    }

}
