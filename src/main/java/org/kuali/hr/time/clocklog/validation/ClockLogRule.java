package org.kuali.hr.time.clocklog.validation;

import org.apache.log4j.Logger;
import org.kuali.hr.time.clock.location.validation.ClockLocationRuleRule;
import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.util.GlobalVariables;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	//TODO fix this class
	protected boolean validateWorkArea(ClockLog clockLog ) {
		boolean valid = false;
		LOG.debug("Validating workarea: " + clockLog.getWorkArea());
		int count = TkServiceLocator.getWorkAreaService().getWorkAreaCount(null, clockLog.getWorkArea());
		if (count >0 ) {
			valid = true;
			LOG.debug("found workarea.");
		} else {
			this.putFieldError("workArea", "error.existence", "Workarea '"
					+ clockLog.getWorkArea()+ "'");
		}
		return valid;
	}

	protected boolean validateTask(ClockLog clockLog ) {
		boolean valid = false;
		LOG.debug("Validating task: " + clockLog.getTask());
		int count = TkServiceLocator.getTaskService().getTaskCount(clockLog.getTask());
		if (count >0 ) {
			valid = true;
			LOG.debug("found task.");
		} else {
			this.putFieldError("task", "error.existence", "Task '"
					+ clockLog.getTask()+ "'");
		}
		return valid;
	}


	/**
	 * It looks like the method that calls this class doesn't actually care
	 * about the return type.
	 */
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		boolean valid = false;

		LOG.debug("entering custom validation for ClockLog");
		PersistableBusinessObject pbo = (PersistableBusinessObject) this.getNewBo();
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

}