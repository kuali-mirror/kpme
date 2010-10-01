package org.kuali.hr.time.dept.lunch.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.PersistenceBrokerFactory;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.time.clock.location.ClockLocationRule;
import org.kuali.hr.time.clock.location.validation.ClockLocationRuleRule;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.dept.lunch.DeptLunchRule;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.GlobalVariables;

public class DeptLunchRuleRule extends MaintenanceDocumentRuleBase {

	protected boolean validateWorkArea(DeptLunchRule deptLunchRule ) {
		boolean valid = false;
		LOG.debug("Validating workarea: " + deptLunchRule.getWorkArea());
		Criteria crit = new Criteria();
		crit.addEqualTo("workArea", deptLunchRule.getWorkArea());		
		Query query = QueryFactory.newQuery(WorkArea.class, crit);
		int count = PersistenceBrokerFactory.defaultPersistenceBroker().getCount(query);
		
		if (count >0 ) {
			valid = true;
			LOG.debug("found workarea.");
		} else {
			this.putFieldError("workArea", "error.existence", "workarea '"
					+ deptLunchRule.getWorkArea()+ "'");
		}
		return valid;
	}

	protected boolean validateDepartment(DeptLunchRule deptLunchRule) {
		boolean valid = false;
		LOG.debug("Validating dept : " + deptLunchRule.getDept());
		// TODO: We may need a full DAO that handles bo lookups at some point,
		// but we can use the provided one:
		Criteria crit = new Criteria();
		crit.addEqualTo("dept", deptLunchRule.getDept());		
		Query query = QueryFactory.newQuery(Department.class, crit);
		int count = PersistenceBrokerFactory.defaultPersistenceBroker().getCount(query);
		
		if (count >0 ) {
			valid = true;
			LOG.debug("found department.");
		} else {
			this.putFieldError("dept", "error.existence", "department '"
					+ deptLunchRule.getDept() + "'");
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

		LOG.debug("entering custom validation for DeptLunchRule");
		PersistableBusinessObject pbo = this.getNewBo();
		if (pbo instanceof DeptLunchRule) {
			DeptLunchRule deptLunchRule = (DeptLunchRule) pbo;
			deptLunchRule.setUserPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
			if (deptLunchRule != null) {
				valid = true;
				valid &= this.validateDepartment(deptLunchRule);
				valid &= this.validateWorkArea(deptLunchRule);					
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