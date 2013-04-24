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
package org.kuali.hr.tklm.leave.workflow.postprocessor;

import org.kuali.hr.tklm.leave.LMConstants;
import org.kuali.hr.tklm.leave.block.LeaveBlock;
import org.kuali.hr.tklm.leave.workflow.LeaveRequestDocument;
import org.kuali.hr.tklm.time.service.base.TkServiceLocator;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.kew.framework.postprocessor.DocumentRouteStatusChange;
import org.kuali.rice.kew.framework.postprocessor.ProcessDocReport;
import org.kuali.rice.kew.postprocessor.DefaultPostProcessor;

public class LeaveRequestPostProcessor extends DefaultPostProcessor {

	@Override
	public ProcessDocReport doRouteStatusChange(DocumentRouteStatusChange statusChangeEvent) throws Exception {		
		ProcessDocReport pdr = new ProcessDocReport(true, "");
		String documentId = statusChangeEvent.getDocumentId();
        LeaveRequestDocument document = TkServiceLocator.getLeaveRequestDocumentService().getLeaveRequestDocument(documentId);

		//LeaveCalendarDocument document = TkServiceLocator.getLeaveCalendarDocumentHeaderService().getDocumentHeader(documentId);
		if (document != null) {
			pdr = super.doRouteStatusChange(statusChangeEvent);
            LeaveBlock lb = document.getLeaveBlock();
	        if(lb != null) {
	            if (!document.getDocumentNumber().equals(lb.getLeaveRequestDocumentId())) {
	                lb.setLeaveRequestDocumentId(document.getDocumentNumber());
	            }
				// Only update the status if it's different.
				if (!statusChangeEvent.getOldRouteStatus().equals(statusChangeEvent.getNewRouteStatus())) {
	
	                DocumentStatus newDocumentStatus = DocumentStatus.fromCode(statusChangeEvent.getNewRouteStatus());
	                if (DocumentStatus.ENROUTE.equals(newDocumentStatus)) {
	                    lb.setRequestStatus(LMConstants.REQUEST_STATUS.REQUESTED);
	                } else if (DocumentStatus.DISAPPROVED.equals(newDocumentStatus)) {
	                    lb.setRequestStatus(LMConstants.REQUEST_STATUS.DISAPPROVED);	                                      
	                } else if (DocumentStatus.FINAL.equals(newDocumentStatus)) {
	                    lb.setRequestStatus(LMConstants.REQUEST_STATUS.APPROVED);
	                } else if (DocumentStatus.CANCELED.equals(newDocumentStatus)) {
	                    lb.setRequestStatus(LMConstants.REQUEST_STATUS.DEFERRED);
	                    lb.setLeaveRequestDocumentId("");
	                }
	                TkServiceLocator.getLeaveBlockService().updateLeaveBlock(lb, document.getDocumentHeader().getWorkflowDocument().getRoutedByPrincipalId());
	                if (DocumentStatus.DISAPPROVED.equals(newDocumentStatus)) {
		                // delete leave block from leave block table when leave request gets disapproved 
	                    // leave request page gets disapproved leave block list from leave block history table
	                    TkServiceLocator.getLeaveBlockService().deleteLeaveBlock(lb.getLmLeaveBlockId(), document.getDocumentHeader().getWorkflowDocument().getRoutedByPrincipalId());
	                }
				}
	       }
		}
		
		return pdr;
	}



}
