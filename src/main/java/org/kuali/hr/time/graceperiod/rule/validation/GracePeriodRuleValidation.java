package org.kuali.hr.time.graceperiod.rule.validation;

import java.math.BigDecimal;

import org.kuali.hr.time.graceperiod.rule.GracePeriodRule;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

public class GracePeriodRuleValidation extends MaintenanceDocumentRuleBase{
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document){
			GracePeriodRule gracePeriodRule = (GracePeriodRule)this.getNewBo();
			//Confirm that hour factor is greater than 0 and less than 1
			if(gracePeriodRule.getHourFactor().compareTo(BigDecimal.ZERO) <= 0 ||
			   gracePeriodRule.getHourFactor().compareTo(new BigDecimal(60)) > 0){
				this.putFieldError("hourFactor", "graceperiod.hour.factor.invalid", gracePeriodRule.getHourFactor()+"");
				return false;
			}
			return true;
	}
}
