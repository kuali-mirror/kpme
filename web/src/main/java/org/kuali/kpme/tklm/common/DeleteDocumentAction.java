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
package org.kuali.kpme.tklm.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.kpme.core.web.KPMEAction;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.leave.workflow.LeaveCalendarDocumentHeader;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.workflow.TimesheetDocumentHeader;

public class DeleteDocumentAction extends KPMEAction {

    public ActionForward deleteDocument(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	DeleteDocumentForm deleteDocumentForm = (DeleteDocumentForm) form;
    	String documentId = deleteDocumentForm.getDeleteDocumentId();

    	if (StringUtils.isNotBlank(documentId)) {
            TimesheetDocumentHeader tdh = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(documentId);
            LeaveCalendarDocumentHeader ldh = LmServiceLocator.getLeaveCalendarDocumentHeaderService().getDocumentHeader(documentId);
            
            if(tdh != null) {
                TkServiceLocator.getTimeBlockService().deleteTimeBlocksAssociatedWithDocumentId(documentId);
    		    TkServiceLocator.getTimesheetService().deleteTimesheet(documentId);
            } else if (ldh != null) {
            	LmServiceLocator.getLeaveBlockService().deleteLeaveBlocksForDocumentId(documentId);
                LmServiceLocator.getLeaveCalendarDocumentHeaderService().deleteLeaveCalendarHeader(documentId);
            }
    	}
    	
    	return mapping.findForward("basic");
    }

}