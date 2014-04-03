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
package org.kuali.rice.krad.kpme.controller;

import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kew.api.action.ActionType;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.maintenance.MaintenanceViewPresentationControllerBase;

public class KPMEMaintenanceViewPresentationControllerBase extends MaintenanceViewPresentationControllerBase {

	private static final long serialVersionUID = 5427336063416427262L;

	@Override
    public boolean canCancel(Document document) {
        WorkflowDocument workflowDocument = document.getDocumentHeader().getWorkflowDocument();
        // check if workflow allows it and invoke hook to allow subclasses to run authorization checks
        return (workflowDocument.isValidAction(ActionType.CANCEL) && isAllowedToCancel(document));
    }

    @Override
    public boolean canClose(Document document) {
        WorkflowDocument workflowDocument = document.getDocumentHeader().getWorkflowDocument();
        return (workflowDocument.isEnroute() || workflowDocument.isFinal());
    }

	// default implementation returns true; subclasses can override this to check if user has permissions to cancel
	protected boolean isAllowedToCancel(Document docuemnt) {
		return true;
	}

}
