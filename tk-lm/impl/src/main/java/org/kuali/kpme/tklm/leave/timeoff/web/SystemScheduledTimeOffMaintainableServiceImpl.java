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
package org.kuali.kpme.tklm.leave.timeoff.web;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.bo.HrBusinessObjectMaintainableImpl;
import org.kuali.kpme.core.earncode.EarnCode;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.rice.kns.document.MaintenanceDocument;

public class SystemScheduledTimeOffMaintainableServiceImpl extends HrBusinessObjectMaintainableImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public HrBusinessObject getObjectById(String id) {
		return LmServiceLocator.getSysSchTimeOffService().getSystemScheduledTimeOff(id);
	}
	
	// KPME-2763/2787
	@Override
	public Map populateBusinessObject(Map<String, String> fieldValues, MaintenanceDocument maintenanceDocument, String methodToCall) {
		if (fieldValues.containsKey("earnCode")
			&& StringUtils.isNotEmpty(fieldValues.get("earnCode"))
			&& fieldValues.containsKey("effectiveDate")
			&& StringUtils.isNotEmpty(fieldValues.get("effectiveDate"))) {
			
			LocalDate effDate = TKUtils.formatDateString(fieldValues.get("effectiveDate"));
			EarnCode ec =  HrServiceLocator.getEarnCodeService().getEarnCode(fieldValues.get("earnCode"), effDate);
			if (ec != null) {
				fieldValues.put("accrualCategory", ec.getAccrualCategory());
				fieldValues.put("leavePlan", ec.getLeavePlan());
			} else {
				fieldValues.put("accrualCategory", "");
				fieldValues.put("leavePlan", "");
			}
		}

		return super.populateBusinessObject(fieldValues, maintenanceDocument,
				methodToCall);
	}
}
