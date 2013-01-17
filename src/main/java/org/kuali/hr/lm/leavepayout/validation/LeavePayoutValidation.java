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
package org.kuali.hr.lm.leavepayout.validation;

import java.math.BigDecimal;
import java.sql.Date;
import org.kuali.hr.lm.leavepayout.LeavePayout;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.bo.PersistableBusinessObject;

public class LeavePayoutValidation extends MaintenanceDocumentRuleBase {

    @Override
    protected boolean processCustomSaveDocumentBusinessRules(
            MaintenanceDocument document) {
        boolean isValid = true;
        LOG.debug("entering custom validation for Leave Payout");
        PersistableBusinessObject pbo = (PersistableBusinessObject) this.getNewBo();
        if(pbo instanceof LeavePayout) {

            LeavePayout leavePayout = (LeavePayout) pbo;

            if(isValid) {

                /**
                 * Validation is basically governed by accrual category rules.
                 *
                 * Leave payouts initiated from the leave calendar display should already have all values
                 * populated, thus validated.
                 *
                 * Leave payouts initiated via the Maintenance tab will have no values populated.
                 */

                isValid &= validateEffectiveDate(leavePayout); // Institutionally dependent policy
                isValid &= validatePayoutFromAccrualCategory(leavePayout); // Global rule.
                isValid &= validatePayoutAmount(leavePayout);
                isValid &= validateForfeitedAmount(leavePayout);
                isValid &= validatePrincipal(leavePayout);

            }
        }
        return isValid;
    }

    /**
     * Effective date
     * @param leavePayout
     * @return
     */
    private boolean validateEffectiveDate(LeavePayout leavePayout) {
        //Limit on future dates?
        return true;
    }

    /**
     * Payout amount
     * @param leavePayout
     * @return
     */
    private boolean validatePayoutAmount(LeavePayout leavePayout) {
        return true;
    }

    /**
     * Forfeited amount
     * @param leavePayout
     * @return
     */
    private boolean validateForfeitedAmount(LeavePayout leavePayout) {
        return true;
    }

    /**
     * From accrual category
     * @param leavePayout
     * @return
     */
    private boolean validatePayoutFromAccrualCategory(LeavePayout leavePayout) {
        return true;
    }

    private boolean validatePrincipal(LeavePayout leavePayout) {
        // TODO Auto-generated method stub
        return true;
    }

    /**
     * Validate payout amount
     *
     * @param btd
     * @return
     */
    private boolean isMaxCarryOverConditionMet(LeavePayout btd) {
        String principalId = btd.getPrincipalId();
        Date date = btd.getEffectiveDate();
        BigDecimal payoutAmount = btd.getPayoutAmount();
        PrincipalHRAttributes pha = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, date);
        Date serviceDate = pha.getServiceDate();

        return true;
    }

    private boolean isPayoutAmountUnderMaxLimit(LeavePayout leavePayout) {
        BigDecimal payoutAmount = leavePayout.getPayoutAmount();

        return true;
    }

}
