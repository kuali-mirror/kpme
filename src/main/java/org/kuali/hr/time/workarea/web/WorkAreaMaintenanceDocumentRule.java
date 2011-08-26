package org.kuali.hr.time.workarea.web;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.hr.time.roles.TkRole;
import org.kuali.hr.time.task.Task;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

import java.sql.Date;
import java.util.List;

public class WorkAreaMaintenanceDocumentRule extends
		MaintenanceDocumentRuleBase {

	private static Logger LOG = Logger
			.getLogger(WorkAreaMaintenanceDocumentRule.class);

	boolean validateDepartment(String dept, Date asOfDate) {
		boolean valid = ValidationUtils.validateDepartment(dept, asOfDate);
		if (!valid) {
			this.putFieldError("dept", "dept.notfound");
		}
		return valid;
	}

	boolean validateRoles(List<TkRole> roles) {
		boolean valid = false;

		if (roles != null && roles.size() > 0) {
			for (TkRole role : roles) {
				valid |= role.isActive()
						&& StringUtils.equals(role.getRoleName(),
								TkConstants.ROLE_TK_APPROVER);
			}
		}

		if (!valid) {
			this.putGlobalError("role.required");
		}

		return valid;
	}

	boolean validateTask(List<TkRole> roles) {
		boolean valid = false;

		if (roles != null && roles.size() > 0) {
			for (TkRole role : roles) {
				valid |= role.isActive()
						&& StringUtils.equals(role.getRoleName(),
								TkConstants.ROLE_TK_APPROVER);
			}
		}

		if (!valid) {
			this.putGlobalError("role.required");
		}

		return valid;
	}
	
	boolean validateTask(Task task, List<Task> tasks) {
		boolean valid = true;
		
		for (Task t : tasks) {
			if (t.getTask().equals(task.getTask()) ) {
				this.putGlobalError("error.duplicate.entry", "task '" + task.getTask() + "'");
				valid = false;
			}
		}
		return valid;
	}
	
	boolean validateTaskNumber(Task task, List<Task> tasks) {
		boolean valid = true;
		
		if (task.getTask().compareTo(100L) < 0){
			this.putFieldError("add.tasks.task", "error.workArea.addTask.taskNumber" );
			valid = false;
		}
		
		return valid;
	}
	
	boolean validateDefaultOTEarnCode(String earnCode, Date asOfDate) {
		// defaultOvertimeEarnCode is a nullable field. 
		if (earnCode != null
				&& !ValidationUtils.validateEarnCode(earnCode, asOfDate)) {
			this.putFieldError("earnCode", "error.existence", "earnCode '"
					+ earnCode + "'");
			return false;
		} else {
			if (earnCode != null
					&& !ValidationUtils.validateEarnCode(earnCode, true, asOfDate)) {
				this.putFieldError("earnCode", "earncode.ovt.required",
						earnCode);
				return false;
			}
			return true;
		}
	}

	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		boolean valid = false;

		PersistableBusinessObject pbo = this.getNewBo();
		if (pbo instanceof WorkArea) {
			WorkArea wa = (WorkArea) pbo;
			valid = validateDepartment(wa.getDept(), wa.getEffectiveDate());
			valid &= validateRoles(wa.getRoles());
			// defaultOvertimeEarnCode is a nullable field. 
			if ( wa.getDefaultOvertimeEarnCode() != null ){
				valid &= validateDefaultOTEarnCode(wa.getDefaultOvertimeEarnCode(),
						wa.getEffectiveDate());
			}
		}

		return valid;
	}

	@Override
	public boolean processCustomAddCollectionLineBusinessRules(
			MaintenanceDocument document, String collectionName,
			PersistableBusinessObject line) {
		boolean valid = false;
		LOG.debug("entering custom validation for Task");
		PersistableBusinessObject pboWorkArea = document
				.getDocumentBusinessObject();
		PersistableBusinessObject pbo = line;
		if (pbo instanceof Task && pboWorkArea instanceof WorkArea) {
			WorkArea wa = (WorkArea) pboWorkArea;
			
			Task task = (Task) pbo;
			
			if (task != null && wa.getTasks() != null) {
				valid = true;
				valid &= this.validateTask(task, wa.getTasks());
				valid &= this.validateTaskNumber(task, wa.getTasks()); // Jira870
			}
		} else if ((pbo instanceof TkRole && pboWorkArea instanceof WorkArea)) {
			TkRole tkRole = (TkRole)pbo;
			valid = true;
			if(StringUtils.isEmpty(tkRole.getPrincipalId()) && (tkRole.getPositionNumber() == null || StringUtils.isEmpty(tkRole.getPositionNumber().toString()))){
				this.putFieldError("add.roles.principalId", "principal.position.required");
				valid = false;
			}
		}
		return valid;
	}

}
