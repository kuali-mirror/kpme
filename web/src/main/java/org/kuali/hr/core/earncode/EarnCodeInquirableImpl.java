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
package org.kuali.hr.core.earncode;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.hr.core.service.HrServiceLocator;
import org.kuali.hr.tklm.common.TKUtils;
import org.kuali.rice.kns.inquiry.KualiInquirableImpl;
import org.kuali.rice.krad.bo.BusinessObject;

public class EarnCodeInquirableImpl extends KualiInquirableImpl {
	
	@Override
	public BusinessObject getBusinessObject(Map fieldValues) {
		EarnCode ec = null;
		if(StringUtils.isNotBlank((String)fieldValues.get("hrEarnCodeId"))) {
			ec = HrServiceLocator.getEarnCodeService().getEarnCodeById((String)fieldValues.get("hrEarnCodeId"));
			
		} else if(StringUtils.isNotBlank((String)fieldValues.get("earnCode"))
					&& StringUtils.isNotBlank((String)fieldValues.get("effectiveDate"))) {
			String earnCode = (String)fieldValues.get("earnCode");
			LocalDate effectiveDate = TKUtils.formatDateString(fieldValues.get("effectiveDate").toString());
			ec = HrServiceLocator.getEarnCodeService().getEarnCode(earnCode, effectiveDate);
		} else {
			ec = (EarnCode) super.getBusinessObject(fieldValues);
		}

		return ec;
	}

}
