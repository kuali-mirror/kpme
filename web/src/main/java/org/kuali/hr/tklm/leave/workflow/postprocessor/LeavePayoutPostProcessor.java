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
import org.kuali.hr.tklm.leave.payout.LeavePayout;
import org.kuali.hr.tklm.time.service.base.TkServiceLocator;
import org.kuali.hr.tklm.time.util.TKContext;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.kew.framework.postprocessor.DocumentRouteStatusChange;
import org.kuali.rice.kew.framework.postprocessor.ProcessDocReport;
import org.kuali.rice.kew.postprocessor.DefaultPostProcessor;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.util.ObjectUtils;

public class LeavePayoutPostProcessor extends DefaultPostProcessor {

	@Override
	public ProcessDocReport doRouteStatusChange(
			DocumentRouteStatusChange statusChangeEvent) throws Exception {
		ProcessDocReport pdr = new ProcessDocReport(true, "");
		String documentId = statusChangeEvent.getDocumentId();
		MaintenanceDocument document = (MaintenanceDocument) KRADServiceLocatorWeb.getDocumentService().getByDocumentHeaderId(documentId);
		//which version of the maintainable is needed/will be available during subsequent calls to doRouteStatusChange?
		LeavePayout leavePayout = (LeavePayout) document.getNewMaintainableObject().getDataObject();

		if (leavePayout != null) {
			pdr = super.doRouteStatusChange(statusChangeEvent);
			
			// Only update the status if it's different.
			if (!statusChangeEvent.getOldRouteStatus().equals(statusChangeEvent.getNewRouteStatus())) {
				DocumentStatus newDocumentStatus = DocumentStatus.fromCode(statusChangeEvent.getNewRouteStatus());
				if (DocumentStatus.ENROUTE.equals(newDocumentStatus)) {
					//when payout document is routed, initiate the leave payout - creating the leave blocks
					leavePayout = TkServiceLocator.getLeavePayoutService().payout(leavePayout);
					//leavePayout now holds the associated leave block ids that were created during payout.
					//encapsulate this information for later document status changes.
					document.getNewMaintainableObject().setDataObject(leavePayout);
					document.setNewMaintainableObject(document.getNewMaintainableObject());
					//KRADServiceLocatorWeb.getDocumentService().updateDocument(document);
					KRADServiceLocatorWeb.getDocumentService().saveDocument(document);
				} else if (DocumentStatus.DISAPPROVED.equals(newDocumentStatus)) {
					//When payout document is disapproved, set all leave block's request statuses to disapproved.
					for(LeaveBlock lb : leavePayout.getLeaveBlocks()) {
						if(ObjectUtils.isNotNull(lb)) {
							lb.setRequestStatus(LMConstants.REQUEST_STATUS.DISAPPROVED);
							TkServiceLocator.getLeaveBlockService().deleteLeaveBlock(lb.getLmLeaveBlockId(), TKContext.getPrincipalId());
						}
					}
					//update status of document and associated leave blocks.
				} else if (DocumentStatus.FINAL.equals(newDocumentStatus)) {
					//When payout document moves to final, set all leave block's request statuses to approved.
					for(LeaveBlock lb : leavePayout.getLeaveBlocks()) {
						if(ObjectUtils.isNotNull(lb)) {
							lb.setRequestStatus(LMConstants.REQUEST_STATUS.APPROVED);
							TkServiceLocator.getLeaveBlockService().updateLeaveBlock(lb, TKContext.getPrincipalId());
						}
					}
				} else if (DocumentStatus.CANCELED.equals(newDocumentStatus)) {
					//When payout document is canceled, set all leave block's request statuses to deferred
					for(LeaveBlock lb : leavePayout.getLeaveBlocks()) {
						if(ObjectUtils.isNotNull(lb)) {
							lb.setRequestStatus(LMConstants.REQUEST_STATUS.DEFERRED);
							TkServiceLocator.getLeaveBlockService().updateLeaveBlock(lb, TKContext.getPrincipalId());
						}
					}
				}
			}
		}
		document.doRouteStatusChange(statusChangeEvent);
		return pdr;
	}

}
