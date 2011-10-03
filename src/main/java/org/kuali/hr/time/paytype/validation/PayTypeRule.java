package org.kuali.hr.time.paytype.validation;

import org.kuali.hr.job.Job;
import org.kuali.hr.time.paytype.PayType;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

import java.sql.Date;
import java.util.List;

public class PayTypeRule extends MaintenanceDocumentRuleBase {

	boolean validateEarnCode(String regEarnCode, Date asOfDate) {
		boolean valid = ValidationUtils.validateEarnCode(regEarnCode, asOfDate);

		if (!valid) {
			this.putFieldError("regEarnCode", "earncode.notfound");
		} else {
			valid = !ValidationUtils.validateEarnCode(regEarnCode, true,
					asOfDate);
			if (!valid) {
				this.putFieldError("regEarnCode", "earncode.ovt.not.required",
						regEarnCode);
			}
		}

		return valid;
	}

	boolean validateActive(String hrPayType, Date asOfDate) {
		boolean valid = true;
		List<Job> jobs = TkServiceLocator.getJobSerivce()
				.getActiveJobsForPayType(hrPayType, asOfDate);
		if (jobs == null || jobs.size() == 0) {
			this.putFieldError("active", "paytype.inactivate.locked", hrPayType);
			valid = false;
		}
		return valid;
	}
    
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		boolean valid = false;

		PersistableBusinessObject pbo = this.getNewBo();
		if (pbo instanceof PayType) {
			PayType pt = (PayType) pbo;

			valid = validateEarnCode(pt.getRegEarnCode(), pt.getEffectiveDate());
			if (document.isOldBusinessObjectInDocument() && !pt.isActive()) {
				valid = validateActive(pt.getPayType(), pt.getEffectiveDate());
			}
		}

		return valid;
	}
}
