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
package org.kuali.kpme.core.bo.leaveplan.web;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.leaveplan.LeavePlan;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.rice.kns.inquiry.KualiInquirableImpl;
import org.kuali.rice.krad.bo.BusinessObject;

public class LeavePlanInquirableImpl extends KualiInquirableImpl {
	
	@Override
	public BusinessObject getBusinessObject(Map fieldValues) {
		
		LeavePlan lp = null;
		if(StringUtils.isNotBlank((String)fieldValues.get("lmLeavePlanId"))) {
			lp = HrServiceLocator.getLeavePlanService().getLeavePlan((String)fieldValues.get("lmLeavePlanId"));
			
		} else if(StringUtils.isNotBlank((String)fieldValues.get("leavePlan"))
					&& StringUtils.isNotBlank((String)fieldValues.get("effectiveDate"))) {
			String leavePlan = (String)fieldValues.get("leavePlan");
			LocalDate effectiveDate = TKUtils.formatDateString((String)fieldValues.get("effectiveDate"));
			lp = HrServiceLocator.getLeavePlanService().getLeavePlan(leavePlan, effectiveDate);
		} else {
			lp = (LeavePlan) super.getBusinessObject(fieldValues);
		}

		return lp;
	}

}
