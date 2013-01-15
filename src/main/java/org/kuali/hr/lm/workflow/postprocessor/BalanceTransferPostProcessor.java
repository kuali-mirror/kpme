package org.kuali.hr.lm.workflow.postprocessor;

import java.util.List;

import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.balancetransfer.BalanceTransfer;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leaverequest.LeaveRequestActionValue;
import org.kuali.hr.lm.workflow.LeaveRequestDocument;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.kew.framework.postprocessor.DocumentRouteStatusChange;
import org.kuali.rice.kew.framework.postprocessor.ProcessDocReport;
import org.kuali.rice.kew.postprocessor.DefaultPostProcessor;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.util.ObjectUtils;

public class BalanceTransferPostProcessor extends DefaultPostProcessor {

	@Override
	public ProcessDocReport doRouteStatusChange(
			DocumentRouteStatusChange statusChangeEvent) throws Exception {
		ProcessDocReport pdr = new ProcessDocReport(true, "");
		String documentId = statusChangeEvent.getDocumentId();
		MaintenanceDocument document = (MaintenanceDocument) KRADServiceLocatorWeb.getDocumentService().getByDocumentHeaderId(documentId);
		//which version of the maintainable is needed/will be available during subsequent calls to doRouteStatusChange?
		BalanceTransfer balanceTransfer = (BalanceTransfer) document.getNewMaintainableObject().getDataObject();

		if (balanceTransfer != null) {
			pdr = super.doRouteStatusChange(statusChangeEvent);
			
			// Only update the status if it's different.
			if (!statusChangeEvent.getOldRouteStatus().equals(statusChangeEvent.getNewRouteStatus())) {
				DocumentStatus newDocumentStatus = DocumentStatus.fromCode(statusChangeEvent.getNewRouteStatus());
				if (DocumentStatus.ENROUTE.equals(newDocumentStatus)) {
					//when transfer document is routed, initiate the balance transfer - creating the leave blocks
					balanceTransfer = TkServiceLocator.getBalanceTransferService().transfer(balanceTransfer);
					//balanceTransfer now holds the associated leave block ids that were created during transfer.
					//encapsulate this information for later document status changes.
					document.getNewMaintainableObject().setDataObject(balanceTransfer);
					document.setNewMaintainableObject(document.getNewMaintainableObject());
					//KRADServiceLocatorWeb.getDocumentService().updateDocument(document);
					KRADServiceLocatorWeb.getDocumentService().saveDocument(document);
				} else if (DocumentStatus.DISAPPROVED.equals(newDocumentStatus)) {
					//When transfer document is disapproved, set all leave block's request statuses to disapproved.
					for(LeaveBlock lb : balanceTransfer.getLeaveBlocks()) {
						if(ObjectUtils.isNotNull(lb)) {
							lb.setRequestStatus(LMConstants.REQUEST_STATUS.DISAPPROVED);
							TkServiceLocator.getLeaveBlockService().deleteLeaveBlock(lb.getLmLeaveBlockId(), TKContext.getPrincipalId());
						}
					}
					//update status of document and associated leave blocks.
				} else if (DocumentStatus.FINAL.equals(newDocumentStatus)) {
					//When transfer document moves to final, set all leave block's request statuses to approved.
					for(LeaveBlock lb : balanceTransfer.getLeaveBlocks()) {
						if(ObjectUtils.isNotNull(lb)) {
							lb.setRequestStatus(LMConstants.REQUEST_STATUS.APPROVED);
							TkServiceLocator.getLeaveBlockService().updateLeaveBlock(lb, TKContext.getPrincipalId());
						}
					}
				} else if (DocumentStatus.CANCELED.equals(newDocumentStatus)) {
					//When transfer document is canceled, set all leave block's request statuses to deferred
					for(LeaveBlock lb : balanceTransfer.getLeaveBlocks()) {
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
