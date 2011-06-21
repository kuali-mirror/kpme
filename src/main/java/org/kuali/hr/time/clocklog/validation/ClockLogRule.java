package org.kuali.hr.time.clocklog.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.PersistenceBrokerFactory;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
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

	//TODO fix this class
	protected boolean validateWorkArea(ClockLog clockLog ) {
		boolean valid = false;
		LOG.debug("Validating workarea: " + clockLog.getWorkArea());
		Criteria crit = new Criteria();
		crit.addEqualTo("workArea", clockLog.getWorkArea());
		Query query = QueryFactory.newQuery(WorkArea.class, crit);
		int count = PersistenceBrokerFactory.defaultPersistenceBroker().getCount(query);

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
		Criteria crit = new Criteria();
		crit.addEqualTo("task", clockLog.getTask());
		Query query = QueryFactory.newQuery(Task.class, crit);
		int count = PersistenceBrokerFactory.defaultPersistenceBroker().getCount(query);

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

}