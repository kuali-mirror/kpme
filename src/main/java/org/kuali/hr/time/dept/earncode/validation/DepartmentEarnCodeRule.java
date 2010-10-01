package org.kuali.hr.time.dept.earncode.validation;

import org.apache.ojb.broker.PersistenceBrokerFactory;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.dept.earncode.DepartmentEarnCode;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.salgroup.SalGroup;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.kns.service.KNSServiceLocator;


public class DepartmentEarnCodeRule extends MaintenanceDocumentRuleBase {
//TODO fix this class
	protected boolean validateSalGroup(DepartmentEarnCode departmentEarnCode ) {
		boolean valid = false;
		LOG.debug("Validating SalGroup: " + departmentEarnCode.getTkSalGroup());
		Criteria crit = new Criteria();
		crit.addEqualTo("tkSalGroup", departmentEarnCode.getTkSalGroup());		
		Query query = QueryFactory.newQuery(SalGroup.class, crit);
		int count = PersistenceBrokerFactory.defaultPersistenceBroker().getCount(query);	
		
		if (count >0 ) {
			valid = true;
			LOG.debug("found SalGroup.");
		} else {
			this.putFieldError("tkSalGroup", "error.existence", "Salgroup '"
					+ departmentEarnCode.getTkSalGroup()+ "'");
		}
		return valid;
	}

	protected boolean validateDept(DepartmentEarnCode departmentEarnCode) {
		boolean valid = false;
		LOG.debug("Validating dept: " + departmentEarnCode.getDept());
		// TODO: We may need a full DAO that handles bo lookups at some point,
//		// but we can use the provided one:
		Criteria crit = new Criteria();
		crit.addEqualTo("dept", departmentEarnCode.getDept());		
		Query query = QueryFactory.newQuery(Department.class, crit);
		int count = PersistenceBrokerFactory.defaultPersistenceBroker().getCount(query);	
		
		if (count >0 ) {
			valid = true;
			LOG.debug("found department.");
		} else {
			this.putFieldError("dept", "error.existence", "Department '"
					+ departmentEarnCode.getDept() + "'");
		}
		return valid;
	}
	
	protected boolean validateEarnCode(DepartmentEarnCode departmentEarnCode ) {
		boolean valid = false;
		LOG.debug("Validating earnCode: " + departmentEarnCode.getEarnCode());
		Criteria crit = new Criteria();
		crit.addEqualTo("earnCode", departmentEarnCode.getEarnCode());		
		Query query = QueryFactory.newQuery(EarnCode.class, crit);
		int count = PersistenceBrokerFactory.defaultPersistenceBroker().getCount(query);	
		
		if (count >0 ) {
			valid = true;
			LOG.debug("found earnCode.");
		} else {
			this.putFieldError("earnCode", "error.existence", "Earncode '"
					+ departmentEarnCode.getEarnCode()+ "'");
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

		LOG.debug("entering custom validation for DepartmentEarnCode");
		PersistableBusinessObject pbo = this.getNewBo();
		if (pbo instanceof DepartmentEarnCode) {
			DepartmentEarnCode departmentEarnCode = (DepartmentEarnCode) pbo;
			
			if (departmentEarnCode != null) {
				valid = true;
				valid &= this.validateSalGroup(departmentEarnCode);
				valid &= this.validateDept(departmentEarnCode);
				valid &= this.validateEarnCode(departmentEarnCode);					
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