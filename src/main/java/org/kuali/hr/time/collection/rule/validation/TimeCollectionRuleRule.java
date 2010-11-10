package org.kuali.hr.time.collection.rule.validation;

import org.apache.ojb.broker.PersistenceBrokerFactory;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.time.collection.rule.TimeCollectionRule;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.kns.util.GlobalVariables;

public class TimeCollectionRuleRule extends MaintenanceDocumentRuleBase {

	protected boolean validateWorkArea(TimeCollectionRule timeCollectionRule ) {
		boolean valid = false;	
		LOG.debug("Validating workarea: " + timeCollectionRule.getWorkArea());
		Criteria crit = new Criteria();
		crit.addEqualTo("workArea", timeCollectionRule.getWorkArea());		
		Query query = QueryFactory.newQuery(WorkArea.class, crit);
		int count = PersistenceBrokerFactory.defaultPersistenceBroker().getCount(query);	
		
		if (count >0 ) {
			valid = true;
			LOG.debug("found workarea.");			
		} else {
			this.putFieldError("workarea", "error.existence", "workarea '"
					+ timeCollectionRule.getWorkArea()+ "'");			
		}
		return valid;
	}

	protected boolean validateDepartment(TimeCollectionRule timeCollectionRule) {
		boolean valid = false;
		LOG.debug("Validating department: " + timeCollectionRule.getDept());
		// TODO: We may need a full DAO that handles bo lookups at some point,
		// but we can use the provided one:
		
		Criteria crit = new Criteria();
		crit.addEqualTo("dept", timeCollectionRule.getDept());		
		Query query = QueryFactory.newQuery(Department.class, crit);
		int count = PersistenceBrokerFactory.defaultPersistenceBroker().getCount(query);		
		
		if (count >0 ) {
			valid = true;			
			LOG.debug("found department.");
		} else {						
			this.putFieldError("dept", "error.existence", "department '"
					+ timeCollectionRule.getDept() + "'");
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
		
		
		
		if (pbo instanceof TimeCollectionRule) {
			TimeCollectionRule timeCollectionRule = (TimeCollectionRule) pbo;
			timeCollectionRule.setUserPrincipalId(GlobalVariables.getUserSession().getLoggedInUserPrincipalName());
			if (timeCollectionRule != null) {
				valid = true;
				valid &= this.validateDepartment(timeCollectionRule);
				valid &= this.validateWorkArea(timeCollectionRule);			
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