package org.kuali.hr.time.shiftdiff.rule.validation;

import org.apache.ojb.broker.PersistenceBrokerFactory;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.time.salgroup.SalGroup;
import org.kuali.hr.time.shiftdiff.rule.ShiftDifferentialRule;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.GlobalVariables;


public class ShiftDifferentialRuleRule extends MaintenanceDocumentRuleBase {

	protected boolean validateSalGroup(ShiftDifferentialRule shiftDifferentialRule ) {
		boolean valid = false;
		LOG.debug("Validating Salgroup: " + shiftDifferentialRule.getTkSalGroup());
		Criteria crit = new Criteria();
		crit.addEqualTo("tkSalGroup", shiftDifferentialRule.getTkSalGroup());
		crit.addLessOrEqualThan("effectiveDate", shiftDifferentialRule.getEffectiveDate());
		
		Query query = QueryFactory.newQuery(SalGroup.class, crit);
		int count = PersistenceBrokerFactory.defaultPersistenceBroker().getCount(query);
		 
		if (count != 0) {
			valid = true;
			LOG.debug("found salgroup.");			
		} else {
			this.putFieldError("salGroup", "error.existence", "salGroup '"
					+ shiftDifferentialRule.getTkSalGroup()+ "'");			
		}
		return valid;
	}

	 
	
	/**
	 * It looks like the method that calls this class doesn't actually care
	 * about the return type.
	 */
	@Override
	protected boolean processCustomSaveDocumentBusinessRules(
			MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for TimeCollectionRule");
		PersistableBusinessObject pbo = this.getNewBo();
		if (pbo instanceof ShiftDifferentialRule) {
			ShiftDifferentialRule shiftDifferentialRule = (ShiftDifferentialRule) pbo;
			shiftDifferentialRule.setUserPrincipalId(GlobalVariables.getUserSession().getLoggedInUserPrincipalName());
			if (shiftDifferentialRule != null) {
				valid = true;
				valid &= this.validateSalGroup(shiftDifferentialRule);
				 
			}
		}
		
		return valid;
	}

	@Override
	protected boolean processCustomApproveDocumentBusinessRules(
			MaintenanceDocument document) {
		return super.processCustomApproveDocumentBusinessRules(document);
	}

	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		return super.processCustomRouteDocumentBusinessRules(document);
	}
}