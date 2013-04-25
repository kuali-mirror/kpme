/**
 * Copyright 2004-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.hr.tklm.time.clocklog.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.kuali.hr.core.service.HrServiceLocator;
import org.kuali.hr.tklm.time.clocklog.ClockLog;
import org.kuali.hr.tklm.time.rules.clocklocation.validation.ClockLocationRuleRule;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.util.GlobalVariables;

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
		int count = HrServiceLocator.getWorkAreaService().getWorkAreaCount(null, clockLog.getWorkArea());
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
		int count = HrServiceLocator.getTaskService().getTaskCount(clockLog.getTask());
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