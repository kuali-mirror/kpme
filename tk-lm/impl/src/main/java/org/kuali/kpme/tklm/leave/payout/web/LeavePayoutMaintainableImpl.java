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
package org.kuali.kpme.tklm.leave.payout.web;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.bo.HrBusinessObjectMaintainableImpl;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.common.LMConstants;
import org.kuali.kpme.tklm.leave.payout.LeavePayout;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.krad.bo.DocumentHeader;
import org.kuali.rice.krad.service.DocumentService;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.List;

public class LeavePayoutMaintainableImpl extends
        HrBusinessObjectMaintainableImpl {

    private static final long serialVersionUID = 1L;

    @Override
    public HrBusinessObject getObjectById(String id) {
        return LmServiceLocator.getLeavePayoutService().getLeavePayoutById(id);
    }

    @Override
    public void doRouteStatusChange(DocumentHeader documentHeader) {
        //ProcessDocReport pdr = new ProcessDocReport(true, "");
        String documentId = documentHeader.getDocumentNumber();
        LeavePayout payout = (LeavePayout)this.getDataObject();
        DocumentService documentService = KRADServiceLocatorWeb.getDocumentService();
        payout.setDocumentHeaderId(documentId);
        DocumentStatus newDocumentStatus = documentHeader.getWorkflowDocument().getStatus();
        String routedByPrincipalId = documentHeader.getWorkflowDocument().getRoutedByPrincipalId();
        if (DocumentStatus.ENROUTE.equals(newDocumentStatus)
                && CollectionUtils.isEmpty(payout.getLeaveBlocks())) {
            //when payout document is routed, initiate the leave payout - creating the leave blocks
            try {
                MaintenanceDocument md = (MaintenanceDocument)KRADServiceLocatorWeb.getDocumentService().getByDocumentHeaderId(documentId);
                payout = LmServiceLocator.getLeavePayoutService().payout(payout);
                md.getDocumentHeader().setDocumentDescription(TKUtils.getDocumentDescription(payout.getPrincipalId(), payout.getEffectiveLocalDate()));
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
                    LeaveBlock.Builder builder = LeaveBlock.Builder.create(lb);
                    builder.setRequestStatus(HrConstants.REQUEST_STATUS.DISAPPROVED);
                    LmServiceLocator.getLeaveBlockService().deleteLeaveBlock(builder.getLmLeaveBlockId(), routedByPrincipalId);
                }
            }
            //update status of document and associated leave blocks.
        } else if (DocumentStatus.FINAL.equals(newDocumentStatus)) {
            //When payout document moves to final, set all leave block's request statuses to approved.
            for(LeaveBlock lb : payout.getLeaveBlocks()) {
                if(ObjectUtils.isNotNull(lb)) {
                    LeaveBlock.Builder builder = LeaveBlock.Builder.create(lb);
                    builder.setRequestStatus(HrConstants.REQUEST_STATUS.APPROVED);
                    LmServiceLocator.getLeaveBlockService().updateLeaveBlock(builder.build(), routedByPrincipalId);
                }
            }
            
            List<LeaveBlock> leaveBlocks = LmServiceLocator.getLeaveBlockService().getLeaveBlocksForDocumentId(payout.getLeaveCalendarDocumentId());
            for(LeaveBlock lb : leaveBlocks) {
            	if(StringUtils.equals(lb.getAccrualCategory(),payout.getFromAccrualCategory())
            			&& StringUtils.equals(lb.getLeaveBlockType(), LMConstants.LEAVE_BLOCK_TYPE.CARRY_OVER_ADJUSTMENT)) {
            		BigDecimal adjustment = new BigDecimal(0);
            		if(payout.getPayoutAmount() != null)
            			adjustment = adjustment.add(payout.getPayoutAmount().abs());
            		if(payout.getForfeitedAmount() != null)
            			adjustment = adjustment.add(payout.getForfeitedAmount().abs());
            		BigDecimal adjustedLeaveAmount = lb.getLeaveAmount().abs().subtract(adjustment);
                    LeaveBlock.Builder builder = LeaveBlock.Builder.create(lb);
            		builder.setLeaveAmount(adjustedLeaveAmount.negate());
            		LmServiceLocator.getLeaveBlockService().updateLeaveBlock(builder.build(), routedByPrincipalId);
            	}
            }
        } else if (DocumentStatus.CANCELED.equals(newDocumentStatus)) {
            //When payout document is canceled, set all leave block's request statuses to deferred
            for(LeaveBlock lb : payout.getLeaveBlocks()) {
                if(ObjectUtils.isNotNull(lb)) {
                    LeaveBlock.Builder builder = LeaveBlock.Builder.create(lb);
                    builder.setRequestStatus(HrConstants.REQUEST_STATUS.DEFERRED);
                    LmServiceLocator.getLeaveBlockService().updateLeaveBlock(builder.build(), routedByPrincipalId);
                }
            }
        }
    }

}
