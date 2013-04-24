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
package org.kuali.hr.tklm.leave.payout.service;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.hr.core.HrBusinessObject;
import org.kuali.hr.tklm.leave.LMConstants;
import org.kuali.hr.tklm.leave.block.LeaveBlock;
import org.kuali.hr.tklm.leave.payout.LeavePayout;
import org.kuali.hr.tklm.time.service.base.TkServiceLocator;
import org.kuali.hr.tklm.time.util.HrBusinessObjectMaintainableImpl;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.krad.bo.DocumentHeader;
import org.kuali.rice.krad.service.DocumentService;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.util.ObjectUtils;

public class LeavePayoutMaintainableImpl extends
        HrBusinessObjectMaintainableImpl {

    private static final long serialVersionUID = 1L;

    @Override
    public void saveBusinessObject() {
		LeavePayout bt = (LeavePayout) this.getBusinessObject();
		
		LeavePayout existingBt = TkServiceLocator.getLeavePayoutService().getLeavePayoutById(bt.getId());
		
		if(ObjectUtils.isNotNull(existingBt)) {
			if(existingBt.getPayoutAmount().compareTo(bt.getPayoutAmount()) != 0) {
				//TODO: Create leave block reference within bt, and update leave amount.
			}
			if(existingBt.getForfeitedAmount().compareTo(bt.getForfeitedAmount()) != 0) {
				//TODO: Create reference within bt for forfeited leave block, update leave amount.
			}
			//Will approvers / department admins be changing accrual category? effective date?
		}
    }
    
    @Override
    public HrBusinessObject getObjectById(String id) {
        // TODO Auto-generated method stub
        return TkServiceLocator.getLeavePayoutService().getLeavePayoutById(id);
    }

    @Override
    public void doRouteStatusChange(DocumentHeader documentHeader) {
        //ProcessDocReport pdr = new ProcessDocReport(true, "");
        String documentId = documentHeader.getDocumentNumber();
        LeavePayout payout = (LeavePayout)this.getDataObject();
        DocumentService documentService = KRADServiceLocatorWeb.getDocumentService();

        DocumentStatus newDocumentStatus = documentHeader.getWorkflowDocument().getStatus();
        String routedByPrincipalId = documentHeader.getWorkflowDocument().getRoutedByPrincipalId();
        if (DocumentStatus.ENROUTE.equals(newDocumentStatus)
                && CollectionUtils.isEmpty(payout.getLeaveBlocks())) {
            //when payout document is routed, initiate the leave payout - creating the leave blocks
            try {
                MaintenanceDocument md = (MaintenanceDocument)KRADServiceLocatorWeb.getDocumentService().getByDocumentHeaderId(documentId);

                payout = TkServiceLocator.getLeavePayoutService().payout(payout);
                md.getNewMaintainableObject().setDataObject(payout);
                documentService.saveDocument(md);
            }
            catch (WorkflowException e) {
                LOG.error("caught exception while handling doRouteStatusChange -> documentService.getByDocumentHeaderId(" + documentHeader.getDocumentNumber() + "). ", e);
                throw new RuntimeException("caught exception while handling doRouteStatusChange -> documentService.getByDocumentHeaderId(" + documentHeader.getDocumentNumber() + "). ", e);
            }
        } else if (DocumentStatus.DISAPPROVED.equals(newDocumentStatus)) {
            //When payout document is disapproved, set all leave block's request statuses to disapproved.
            for(LeaveBlock lb : payout.getLeaveBlocks()) {
                if(ObjectUtils.isNotNull(lb)) {
                    lb.setRequestStatus(LMConstants.REQUEST_STATUS.DISAPPROVED);
                    TkServiceLocator.getLeaveBlockService().deleteLeaveBlock(lb.getLmLeaveBlockId(), routedByPrincipalId);
                }
            }
            //update status of document and associated leave blocks.
        } else if (DocumentStatus.FINAL.equals(newDocumentStatus)) {
            //When payout document moves to final, set all leave block's request statuses to approved.
            for(LeaveBlock lb : payout.getLeaveBlocks()) {
                if(ObjectUtils.isNotNull(lb)) {
                    lb.setRequestStatus(LMConstants.REQUEST_STATUS.APPROVED);
                    TkServiceLocator.getLeaveBlockService().updateLeaveBlock(lb, routedByPrincipalId);
                }
            }
            
            List<LeaveBlock> leaveBlocks = TkServiceLocator.getLeaveBlockService().getLeaveBlocksForDocumentId(documentId);
            LeaveBlock carryOverBlock = null;
            for(LeaveBlock lb : leaveBlocks) {
            	if(StringUtils.equals(lb.getAccrualCategory(),payout.getFromAccrualCategory())
            			&& StringUtils.equals(lb.getDescription(),"Max carry over adjustment")
            			&& lb.getAccrualGenerated()) {
            		carryOverBlock = lb;
            	}
            }
            if(carryOverBlock != null) {
            	BigDecimal adjustment = new BigDecimal(0);
            	if(payout.getPayoutAmount() != null)
            		adjustment = adjustment.add(payout.getPayoutAmount().abs());
            	if(payout.getForfeitedAmount() != null)
            		adjustment = adjustment.add(payout.getForfeitedAmount().abs());
            	BigDecimal adjustedLeaveAmount = carryOverBlock.getLeaveAmount().abs().subtract(adjustment);
            	carryOverBlock.setLeaveAmount(adjustedLeaveAmount.negate());
        		TkServiceLocator.getLeaveBlockService().updateLeaveBlock(carryOverBlock, routedByPrincipalId);
            }
            
        } else if (DocumentStatus.CANCELED.equals(newDocumentStatus)) {
            //When payout document is canceled, set all leave block's request statuses to deferred
            for(LeaveBlock lb : payout.getLeaveBlocks()) {
                if(ObjectUtils.isNotNull(lb)) {
                    lb.setRequestStatus(LMConstants.REQUEST_STATUS.DEFERRED);
                    TkServiceLocator.getLeaveBlockService().updateLeaveBlock(lb, routedByPrincipalId);
                }
            }
        }
    }

}
