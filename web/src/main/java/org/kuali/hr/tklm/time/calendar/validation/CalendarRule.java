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
package org.kuali.hr.tklm.time.calendar.validation;

import java.sql.Time;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.tklm.time.calendar.Calendar;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

public class CalendarRule extends MaintenanceDocumentRuleBase {
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		boolean valid = true;

		Calendar calendar = (Calendar) this.getNewBo();
		if (StringUtils.equals(calendar.getCalendarTypes(), "Pay")){
			valid = validateFLSABeginDay(calendar.getFlsaBeginDay());
			valid = validateFLSABeginTime(calendar.getFlsaBeginTime());
		}
		return valid;
	}

	boolean validateFLSABeginDay(String flsaBeginDay) {
		boolean valid = true;
		if (StringUtils.isEmpty(flsaBeginDay)) {
			this.putFieldError("flsaBeginDay", "error.required",
					"FLSA Begin Day");
			valid = false;
		}
		return valid;
	}
	boolean validateFLSABeginTime(Time flsaBeginTime) {
		boolean valid = true;
		if (flsaBeginTime == null) {
			this.putFieldError("flsaBeginTime", "error.required",
					"FLSA Begin Time");
			valid = false;
		}
		return valid;
	}

}
