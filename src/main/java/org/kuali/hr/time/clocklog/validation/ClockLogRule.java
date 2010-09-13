package org.kuali.hr.time.clocklog.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.clock.location.validation.ClockLocationRuleRule;
import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.dept.earncode.DepartmentEarnCode;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.salgroup.SalGroup;
import org.kuali.hr.time.task.Task;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.GlobalVariables;

public class ClockLogRule  extends MaintenanceDocumentRuleBase {

	private static final String WILDCARD_CHARACTER = "%";
	private static final String REGEX_IP_ADDRESS_STRING = "(?:(?:"
			+ WILDCARD_CHARACTER
			+ "|25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:"
			+ WILDCARD_CHARACTER + "|25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
	private static final Pattern REGEX_IP_ADDRESS_PATTERN = Pattern
			.compile(REGEX_IP_ADDRESS_STRING);

	private static Logger LOG = Logger.getLogger(ClockLocationRuleRule.class);

	protected boolean validateIpAddress(String ip) {
		boolean valid = false;

		LOG.debug("Validating IP address: " + ip);
		Matcher matcher = REGEX_IP_ADDRESS_PATTERN.matcher(ip);
		valid = matcher.matches();
		if (!valid) {
			this.putFieldError("ipAddress", "ipaddress.invalid.format", ip);
		}

		return valid;
	}
	
	
	protected boolean validateWorkArea(ClockLog clockLog ) {
		boolean valid = false;
		LOG.debug("Validating workarea: " + clockLog.getWorkAreaId());
		WorkArea workArea = KNSServiceLocator.getBusinessObjectService()
				.findBySinglePrimaryKey(WorkArea.class, clockLog.getWorkAreaId());
		if (workArea != null) {
			valid = true;
			LOG.debug("found workarea.");
		} else {
			this.putFieldError("workAreaId", "error.existence", "Workarea '"
					+ clockLog.getWorkAreaId()+ "'");
		}
		return valid;
	} 
	
	protected boolean validateTask(ClockLog clockLog ) {
		boolean valid = false;
		LOG.debug("Validating task: " + clockLog.getTaskId());
		Task task = KNSServiceLocator.getBusinessObjectService()
				.findBySinglePrimaryKey(Task.class, clockLog.getTaskId());
		if (task != null) {
			valid = true;
			LOG.debug("found task.");
		} else {
			this.putFieldError("taskId", "error.existence", "Task '"
					+ clockLog.getTaskId()+ "'");
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

		LOG.debug("entering custom validation for ClockLog");
		PersistableBusinessObject pbo = this.getNewBo();
		if (pbo instanceof ClockLog) {
			ClockLog clockLog = (ClockLog) pbo;
			clockLog.setUserPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
			if (clockLog != null) {
				valid = true;
				valid &= this.validateIpAddress(clockLog.getIpAddress());
				valid &= this.validateWorkArea(clockLog);
				valid &= this.validateTask(clockLog);									
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