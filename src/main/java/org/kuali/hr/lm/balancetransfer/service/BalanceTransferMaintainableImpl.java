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
package org.kuali.hr.lm.balancetransfer.service;

import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.balancetransfer.BalanceTransfer;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.HrBusinessObjectMaintainableImpl;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.kew.framework.postprocessor.ProcessDocReport;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.krad.bo.DocumentHeader;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.service.DocumentService;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.util.ObjectUtils;

public class BalanceTransferMaintainableImpl extends
		HrBusinessObjectMaintainableImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = -789218061798169466L;

	
	@Override
	public HrBusinessObject getObjectById(String id) {
		return TkServiceLocator.getBalanceTransferService().getBalanceTransferById(id);
	}

    @Override
    public void doRouteStatusChange(DocumentHeader documentHeader) {
        //ProcessDocReport pdr = new ProcessDocReport(true, "");
        String documentId = documentHeader.getDocumentNumber();
        BalanceTransfer balanceTransfer = (BalanceTransfer)this.getDataObject();
        DocumentService documentService = KRADServiceLocatorWeb.getDocumentService();

        DocumentStatus newDocumentStatus = documentHeader.getWorkflowDocument().getStatus();
        String routedByPrincipalId = documentHeader.getWorkflowDocument().getRoutedByPrincipalId();
        if (DocumentStatus.ENROUTE.equals(newDocumentStatus)
                && CollectionUtils.isEmpty(balanceTransfer.getLeaveBlocks())) {
                //when transfer document is routed, initiate the balance transfer - creating the leave blocks
            try {
                MaintenanceDocument md = (MaintenanceDocument)KRADServiceLocatorWeb.getDocumentService().getByDocumentHeaderId(documentId);

                TkServiceLocator.getBalanceTransferService().transfer(balanceTransfer);
                md.getNewMaintainableObject().setDataObject(balanceTransfer);
                documentService.saveDocument(md);
            }
            catch (WorkflowException e) {
                LOG.error("caught exception while handling doRouteStatusChange -> documentService.getByDocumentHeaderId(" + documentHeader.getDocumentNumber() + "). ", e);
                throw new RuntimeException("caught exception while handling doRouteStatusChange -> documentService.getByDocumentHeaderId(" + documentHeader.getDocumentNumber() + "). ", e);
            }
        } else if (DocumentStatus.DISAPPROVED.equals(newDocumentStatus)) {
            //When transfer document is disapproved, set all leave block's request statuses to disapproved.
            for(LeaveBlock lb : balanceTransfer.getLeaveBlocks()) {
                if(ObjectUtils.isNotNull(lb)) {
                    lb.setRequestStatus(LMConstants.REQUEST_STATUS.DISAPPROVED);
                    TkServiceLocator.getLeaveBlockService().deleteLeaveBlock(lb.getLmLeaveBlockId(), routedByPrincipalId);
                }
            }
            //update status of document and associated leave blocks.
        } else if (DocumentStatus.FINAL.equals(newDocumentStatus)) {
            //When transfer document moves to final, set all leave block's request statuses to approved.
            for(LeaveBlock lb : balanceTransfer.getLeaveBlocks()) {
                if(ObjectUtils.isNotNull(lb)) {
                    lb.setRequestStatus(LMConstants.REQUEST_STATUS.APPROVED);
                    TkServiceLocator.getLeaveBlockService().updateLeaveBlock(lb, routedByPrincipalId);
                }
            }
        } else if (DocumentStatus.CANCELED.equals(newDocumentStatus)) {
            //When transfer document is canceled, set all leave block's request statuses to deferred
            for(LeaveBlock lb : balanceTransfer.getLeaveBlocks()) {
                if(ObjectUtils.isNotNull(lb)) {
                    lb.setRequestStatus(LMConstants.REQUEST_STATUS.DEFERRED);
                    TkServiceLocator.getLeaveBlockService().updateLeaveBlock(lb, routedByPrincipalId);
                }
            }
        }
    }

}
