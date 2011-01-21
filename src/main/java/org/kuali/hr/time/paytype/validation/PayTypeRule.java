package org.kuali.hr.time.paytype.validation;

import org.kuali.hr.time.paytype.PayType;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

import java.sql.Date;

public class PayTypeRule extends MaintenanceDocumentRuleBase {

    boolean validateEarnCode(String regEarnCode, Date asOfDate) {
        boolean valid = ValidationUtils.validateEarnCode(regEarnCode, asOfDate);

        if (!valid) {
            this.putFieldError("regEarnCode", "earncode.notfound");
        }

        return valid;
    }

    @Override
	protected boolean processCustomSaveDocumentBusinessRules(MaintenanceDocument document) {
        boolean valid = false;

        PersistableBusinessObject pbo = this.getNewBo();
        if (pbo instanceof PayType) {
            PayType pt = (PayType)pbo;

            valid = validateEarnCode(pt.getRegEarnCode(), pt.getEffectiveDate());
        }

        return valid;
    }
}
