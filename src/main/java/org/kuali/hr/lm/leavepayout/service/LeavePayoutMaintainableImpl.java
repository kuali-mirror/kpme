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
package org.kuali.hr.lm.leavepayout.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.leavepayout.LeavePayout;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.HrBusinessObjectMaintainableImpl;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
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

        LeavePayout btd = (LeavePayout) this.getBusinessObject();

        AccrualCategory debitedAcc = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(btd.getFromAccrualCategory(),btd.getEffectiveDate());
        LeaveBlock aLeaveBlock = new LeaveBlock();
        aLeaveBlock.setPrincipalId(btd.getPrincipalId());
        aLeaveBlock.setLeaveDate(btd.getEffectiveDate());
        aLeaveBlock.setEarnCode(debitedAcc.getEarnCode());
        aLeaveBlock.setAccrualCategory(btd.getFromAccrualCategory());
        aLeaveBlock.setDescription("Leave Payout");
        aLeaveBlock.setLeaveAmount(btd.getPayoutAmount().negate());	// can be negative or positive.  leave as is.
        aLeaveBlock.setAccrualGenerated(true);
        aLeaveBlock.setLeaveBlockType(LMConstants.LEAVE_BLOCK_TYPE.LEAVE_PAYOUT);
        aLeaveBlock.setRequestStatus(LMConstants.REQUEST_STATUS.REQUESTED);
        aLeaveBlock.setBlockId(0L);

        TkServiceLocator.getLeaveBlockService().saveLeaveBlock(aLeaveBlock, TKContext.getPrincipalId());

        if(btd.getForfeitedAmount()!=null) {
            if(btd.getForfeitedAmount().compareTo(BigDecimal.ZERO) > 0) {
                aLeaveBlock = new LeaveBlock();
                aLeaveBlock.setPrincipalId(btd.getPrincipalId());
                aLeaveBlock.setLeaveDate(btd.getEffectiveDate());
                aLeaveBlock.setEarnCode("FEC");
                aLeaveBlock.setAccrualCategory(btd.getFromAccrualCategory());
                aLeaveBlock.setDescription("Leave Payout Forfeiture");
                aLeaveBlock.setLeaveAmount(btd.getForfeitedAmount());
                aLeaveBlock.setAccrualGenerated(true);
                aLeaveBlock.setLeaveBlockType(LMConstants.LEAVE_BLOCK_TYPE.LEAVE_PAYOUT);
                aLeaveBlock.setRequestStatus(LMConstants.REQUEST_STATUS.REQUESTED);
                aLeaveBlock.setBlockId(0L);

                TkServiceLocator.getLeaveBlockService().saveLeaveBlock(aLeaveBlock, TKContext.getPrincipalId());
            }
        }

        super.saveBusinessObject();
    }

    @Override
    public HrBusinessObject getObjectById(String id) {
        // TODO Auto-generated method stub
        return TkServiceLocator.getLeavePayoutService().getLeavePayoutById(id);
    }

    @Override
    public Map populateBusinessObject(Map<String, String> fieldValues,
                                      MaintenanceDocument maintenanceDocument, String methodToCall) {
        if(StringUtils.equals(getMaintenanceAction(), "New")) {
            String principalId = null;
            String fromAccrualCategory = null;
            Date effectiveDate = null;
            if(fieldValues.containsKey("principalId")
                    && StringUtils.isNotBlank(fieldValues.get("principalId"))
                    && fieldValues.containsKey("fromAccrualCategory")
                    && StringUtils.isNotBlank(fieldValues.get("fromAccrualCategory"))
                    && fieldValues.containsKey("effectiveDate")
                    && StringUtils.isNotBlank(fieldValues.get("effectiveDate"))) {
                LeavePayout bt = (LeavePayout) this.getBusinessObject();
                effectiveDate = TKUtils.formatDateString(fieldValues.get("effectiveDate"));
                principalId = fieldValues.get("principalId");
                fromAccrualCategory = fieldValues.get("fromAccrualCategory");
                PrincipalHRAttributes pha = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, effectiveDate);
                AccrualCategory ac = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(fromAccrualCategory, effectiveDate);
                bt.setFromAccrualCategory(ac.toString());
            }
        }
        return super.populateBusinessObject(fieldValues, maintenanceDocument,
                methodToCall);
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
