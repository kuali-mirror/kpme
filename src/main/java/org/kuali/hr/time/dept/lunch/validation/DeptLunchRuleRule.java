package org.kuali.hr.time.dept.lunch.validation;

import java.math.BigDecimal;

import org.apache.ojb.broker.PersistenceBrokerFactory;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.dept.lunch.DeptLunchRule;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.kns.util.GlobalVariables;

public class DeptLunchRuleRule extends MaintenanceDocumentRuleBase {

	boolean validateWorkArea(DeptLunchRule ruleObj) {
		boolean valid = true;
		if (ruleObj.getWorkArea() != null
				&& !ValidationUtils.validateWorkArea(ruleObj.getWorkArea(), ruleObj
						.getEffectiveDate())) {
			this.putFieldError("workArea", "error.existence", "workArea '"
					+ ruleObj.getWorkArea() + "'");
			valid = false;
		} else if (ruleObj.getWorkArea() != null
				&& !ruleObj.getWorkArea().equals(TkConstants.WILDCARD_LONG)) {
			Criteria crit = new Criteria();
			crit.addEqualTo("dept", ruleObj.getDept());
			crit.addEqualTo("workArea", ruleObj.getWorkArea());
			Query query = QueryFactory.newQuery(WorkArea.class, crit);
			int count = PersistenceBrokerFactory.defaultPersistenceBroker()
					.getCount(query);
			valid = (count > 0);
			if (!valid) {
				this.putFieldError("workArea", "dept.workarea.invalid.sync",
						ruleObj.getWorkArea() + "");
			}
		}
		return valid;
	}

	boolean validateDepartment(DeptLunchRule ruleObj) {
		if (!ValidationUtils.validateDepartment(ruleObj.getDept(), ruleObj.getEffectiveDate())) {
			this.putFieldError("dept", "error.existence", "department '" + ruleObj.getDept() + "'");
			return false;
		} else {
			return true;
		}
	}

	boolean validateJobNumber(DeptLunchRule ruleObj) {
		boolean valid = false;
		if (ruleObj.getJobNumber() == null) {
			valid = false;
		} else if (!ruleObj.getJobNumber().equals(TkConstants.WILDCARD_LONG)) {
			Criteria crit = new Criteria();
			crit.addEqualTo("principalId", ruleObj.getPrincipalId());
			crit.addEqualTo("jobNumber", ruleObj.getJobNumber());
			Query query = QueryFactory.newQuery(Job.class, crit);
			int count = PersistenceBrokerFactory.defaultPersistenceBroker()
					.getCount(query);
			valid = (count > 0);
			if (!valid) {
				this.putFieldError("jobNumber", "principalid.job.invalid.sync",
						ruleObj.getJobNumber() + "");
			}
		}
		return valid;
	}

	boolean validatePrincipalId(DeptLunchRule ruleObj) {
		if (!ruleObj.getPrincipalId().equals(TkConstants.WILDCARD_CHARACTER)
				&&!ValidationUtils.validatePrincipalId(ruleObj.getPrincipalId())) {
			this.putFieldError("principalId", "error.existence", "Principal Id '" + ruleObj.getPrincipalId() + "'");
			return false;
		} else {
			return true;
		}
	}

	boolean validateShiftHour(DeptLunchRule ruleObj) {
		BigDecimal shiftHour = ruleObj.getShiftHours();
		BigDecimal maxHour = new BigDecimal(24);
		if(shiftHour.compareTo(maxHour) == 1) {
			this.putFieldError("shiftHours", "dept.shifthour.exceedsMax");
			return false;
		}
		return true;
	}



	/**
	 * It looks like the method that calls this class doesn't actually care
	 * about the return type.
	 */
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
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
				valid &= this.validatePrincipalId(deptLunchRule);
				valid &= this.validateJobNumber(deptLunchRule);
				valid &= this.validateShiftHour(deptLunchRule);
			}
		}

		return valid;
	}

	@Override
	protected boolean processCustomApproveDocumentBusinessRules(
			MaintenanceDocument document) {
		return super.processCustomApproveDocumentBusinessRules(document);
	}
}