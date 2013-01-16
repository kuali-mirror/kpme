/**
 * Copyright 2004-2012 The Kuali Foundation
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
import org.kuali.rice.kns.document.MaintenanceDocument;

public class LeavePayoutMaintainableServiceImpl extends
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

}
