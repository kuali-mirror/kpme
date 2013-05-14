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
package org.kuali.kpme.core.bo.salarygroup.web;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.salarygroup.SalaryGroup;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.rice.kns.inquiry.KualiInquirableImpl;
import org.kuali.rice.krad.bo.BusinessObject;

import java.util.Map;

public class SalaryGroupInquirableImpl extends KualiInquirableImpl {

	private static final long serialVersionUID = 6341884912342980800L;
	
	@Override
	@SuppressWarnings("rawtypes")
	public BusinessObject getBusinessObject(Map fieldValues) {
		SalaryGroup salaryGroup = null;
		
		if (StringUtils.isNotBlank((String)fieldValues.get("hrSalGroupId"))) {
			salaryGroup = HrServiceLocator.getSalaryGroupService().getSalaryGroup((String) fieldValues.get("hrSalGroupId"));
		} else if (StringUtils.isNotBlank((String)fieldValues.get("hrSalGroup"))
					&& StringUtils.isNotBlank((String)fieldValues.get("effectiveDate"))) {
			String hrSalGroup = (String) fieldValues.get("hrSalGroup");
			LocalDate effectiveDate = TKUtils.formatDateString((String) fieldValues.get("effectiveDate"));

			salaryGroup = HrServiceLocator.getSalaryGroupService().getSalaryGroup(hrSalGroup, effectiveDate);
		} else {
			salaryGroup = (SalaryGroup) super.getBusinessObject(fieldValues);
		}

		return salaryGroup;
	}
	
}