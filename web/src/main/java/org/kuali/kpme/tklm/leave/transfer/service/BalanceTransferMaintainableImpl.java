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
package org.kuali.kpme.tklm.leave.transfer.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.bo.HrBusinessObjectMaintainableImpl;
import org.kuali.kpme.core.bo.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.common.LMConstants;
import org.kuali.kpme.tklm.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.leave.transfer.BalanceTransfer;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.krad.bo.DocumentHeader;
import org.kuali.rice.krad.service.DocumentService;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.util.ObjectUtils;

@SuppressWarnings("deprecation")
public class BalanceTransferMaintainableImpl extends
		HrBusinessObjectMaintainableImpl {

	private static final long serialVersionUID = -789218061798169466L;

	@Override
	public void saveBusinessObject() {
		// TODO Auto-generated method stub
		super.saveBusinessObject();
	}
	
	@Override
	public HrBusinessObject getObjectById(String id) {
		return LmServiceLocator.getBalanceTransferService().getBalanceTransferById(id);
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
        	// this is a balance transfer on a system scheduled time off leave block
        	if(StringUtils.isNotEmpty(balanceTransfer.getSstoId())) {
        		try {
	                MaintenanceDocument md = (MaintenanceDocument)KRADServiceLocatorWeb.getDocumentService().getByDocumentHeaderId(documentId);
	                balanceTransfer = LmServiceLocator.getBalanceTransferService().transferSsto(balanceTransfer);
	                md.getNewMaintainableObject().setDataObject(balanceTransfer);
	                documentService.saveDocument(md);
	            }
	            catch (WorkflowException e) {
	                LOG.error("caught exception while handling doRouteStatusChange -> documentService.getByDocumentHeaderId(" + documentHeader.getDocumentNumber() + "). ", e);
	                throw new RuntimeException("caught exception while handling doRouteStatusChange -> documentService.getByDocumentHeaderId(" + documentHeader.getDocumentNumber() + "). ", e);
	            }	
        	} else {
                //when transfer document is routed, initiate the balance transfer - creating the leave blocks
	            try {
	                MaintenanceDocument md = (MaintenanceDocument)KRADServiceLocatorWeb.getDocumentService().getByDocumentHeaderId(documentId);
	
	                balanceTransfer = LmServiceLocator.getBalanceTransferService().transfer(balanceTransfer);
	                md.getNewMaintainableObject().setDataObject(balanceTransfer);
	                documentService.saveDocument(md);
	            }
	            catch (WorkflowException e) {
	                LOG.error("caught exception while handling doRouteStatusChange -> documentService.getByDocumentHeaderId(" + documentHeader.getDocumentNumber() + "). ", e);
	                throw new RuntimeException("caught exception while handling doRouteStatusChange -> documentService.getByDocumentHeaderId(" + documentHeader.getDocumentNumber() + "). ", e);
	            }
        	}
        } else if (DocumentStatus.DISAPPROVED.equals(newDocumentStatus)) {
        	/**
        	 * TODO: Remove disapproval action
        	 */
        	// this is a balance transfer on a system scheduled time off leave block

            if(StringUtils.isNotEmpty(balanceTransfer.getSstoId())) {
        		// put two accrual service generated leave blocks back, one accrued, one usage
        		List<LeaveBlock> lbList = buildSstoLeaveBlockList(balanceTransfer);    			
    			LmServiceLocator.getLeaveBlockService().saveLeaveBlocks(lbList);
        	}
            //When transfer document is disapproved, set all leave block's request statuses to disapproved.
            for(LeaveBlock lb : balanceTransfer.getLeaveBlocks()) {
                if(ObjectUtils.isNotNull(lb)) {
                    lb.setRequestStatus(HrConstants.REQUEST_STATUS.DISAPPROVED);
                    LmServiceLocator.getLeaveBlockService().deleteLeaveBlock(lb.getLmLeaveBlockId(), routedByPrincipalId);
                }
            }
            //update status of document and associated leave blocks.
        } else if (DocumentStatus.FINAL.equals(newDocumentStatus)) {
            //When transfer document moves to final, set all leave block's request statuses to approved.
            for(LeaveBlock lb : balanceTransfer.getLeaveBlocks()) {
                if(ObjectUtils.isNotNull(lb)) {
                	//TODO: What happens when an approver edits the fields in the transfer doc before approving?
                    lb.setRequestStatus(HrConstants.REQUEST_STATUS.APPROVED);
                    LmServiceLocator.getLeaveBlockService().updateLeaveBlock(lb, routedByPrincipalId);
                }
            }
            List<LeaveBlock> leaveBlocks = LmServiceLocator.getLeaveBlockService().getLeaveBlocksForDate(balanceTransfer.getPrincipalId(), balanceTransfer.getEffectiveLocalDate());
            LeaveBlock carryOverBlock = null;
            for(LeaveBlock lb : leaveBlocks) {
            	if(StringUtils.equals(lb.getAccrualCategory(),balanceTransfer.getFromAccrualCategory())
            			&& StringUtils.equals(lb.getDescription(),"Max carry over adjustment")) {
            		carryOverBlock = lb;
            	}
            }
            if(carryOverBlock != null) {
            	BigDecimal adjustment = new BigDecimal(0);
            	if(balanceTransfer.getTransferAmount() != null)
            		adjustment = adjustment.add(balanceTransfer.getTransferAmount().abs());
            	if(balanceTransfer.getForfeitedAmount() != null)
            		adjustment = adjustment.add(balanceTransfer.getForfeitedAmount().abs());
            	BigDecimal adjustedLeaveAmount = carryOverBlock.getLeaveAmount().abs().subtract(adjustment);
            	carryOverBlock.setLeaveAmount(adjustedLeaveAmount.negate());
        		LmServiceLocator.getLeaveBlockService().updateLeaveBlock(carryOverBlock, routedByPrincipalId);
            }
        } else if (DocumentStatus.CANCELED.equals(newDocumentStatus)) {
            //When transfer document is canceled, set all leave block's request statuses to deferred

            for(LeaveBlock lb : balanceTransfer.getLeaveBlocks()) {
                if(ObjectUtils.isNotNull(lb)) {
                    lb.setRequestStatus(HrConstants.REQUEST_STATUS.DEFERRED);
                    LmServiceLocator.getLeaveBlockService().updateLeaveBlock(lb, routedByPrincipalId);
                }
            }
        }
    }

	private List<LeaveBlock> buildSstoLeaveBlockList(BalanceTransfer bt) {
		String leaveDocId = CollectionUtils.isNotEmpty(bt.getLeaveBlocks())? bt.getLeaveBlocks().get(0).getDocumentId() : "";
		List<LeaveBlock> lbList = new ArrayList<LeaveBlock>();
		AccrualCategory fromAC = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(bt.getFromAccrualCategory(), bt.getEffectiveLocalDate());
		
		LeaveBlock accruedLeaveBlock = new LeaveBlock();
		accruedLeaveBlock.setAccrualCategory(bt.getFromAccrualCategory());
		accruedLeaveBlock.setLeaveDate(bt.getEffectiveDate());
		accruedLeaveBlock.setPrincipalId(bt.getPrincipalId());
		accruedLeaveBlock.setEarnCode(fromAC.getEarnCode());
		accruedLeaveBlock.setAccrualGenerated(true);
		accruedLeaveBlock.setBlockId(0L);
		accruedLeaveBlock.setScheduleTimeOffId(bt.getSstoId());
		accruedLeaveBlock.setLeaveAmount(bt.getTransferAmount());
		accruedLeaveBlock.setLeaveBlockType(LMConstants.LEAVE_BLOCK_TYPE.ACCRUAL_SERVICE);
		accruedLeaveBlock.setRequestStatus(HrConstants.REQUEST_STATUS.APPROVED);
		accruedLeaveBlock.setDocumentId(leaveDocId);
		accruedLeaveBlock.setPrincipalIdModified(HrContext.getPrincipalId());
		lbList.add(accruedLeaveBlock);
		
		LeaveBlock usageLeaveBlock = new LeaveBlock();
		usageLeaveBlock.setAccrualCategory(bt.getFromAccrualCategory());
		usageLeaveBlock.setLeaveDate(bt.getEffectiveDate());
		usageLeaveBlock.setPrincipalId(bt.getPrincipalId());
		usageLeaveBlock.setEarnCode(fromAC.getEarnCode());
		usageLeaveBlock.setAccrualGenerated(true);
		usageLeaveBlock.setBlockId(0L);
		usageLeaveBlock.setScheduleTimeOffId(bt.getSstoId());
		usageLeaveBlock.setLeaveAmount(bt.getTransferAmount().negate());
		usageLeaveBlock.setLeaveBlockType(LMConstants.LEAVE_BLOCK_TYPE.ACCRUAL_SERVICE);
		usageLeaveBlock.setRequestStatus(HrConstants.REQUEST_STATUS.APPROVED);
		usageLeaveBlock.setDocumentId(leaveDocId);
		usageLeaveBlock.setPrincipalIdModified(HrContext.getPrincipalId());
		lbList.add(usageLeaveBlock);
		
		return lbList;
	}
}
