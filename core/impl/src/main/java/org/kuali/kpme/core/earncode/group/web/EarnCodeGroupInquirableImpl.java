/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.core.earncode.group.web;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.earncode.group.EarnCodeGroupBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.rice.kns.inquiry.KualiInquirableImpl;
import org.kuali.rice.krad.bo.BusinessObject;

public class EarnCodeGroupInquirableImpl extends KualiInquirableImpl {

	@Override
	public BusinessObject getBusinessObject(Map fieldValues) {
		EarnCodeGroupBo ec = null;
		if(StringUtils.isNotBlank((String)fieldValues.get("hrEarnCodeGroupId"))) {
			ec = EarnCodeGroupBo.from(HrServiceLocator.getEarnCodeGroupService().getEarnCodeGroup((String)fieldValues.get("hrEarnCodeGroupId")));

		} else if(StringUtils.isNotBlank((String)fieldValues.get("earnCodeGroup"))
					&& StringUtils.isNotBlank((String)fieldValues.get("effectiveDate"))) {
			String earnCodeGroup = (String)fieldValues.get("earnCodeGroup");
            String effDate = (String) fieldValues.get("effectiveDate");
            LocalDate effectiveDate = StringUtils.isBlank(effDate) ? LocalDate.now() : TKUtils.formatDateString(effDate);
			ec = EarnCodeGroupBo.from(HrServiceLocator.getEarnCodeGroupService().getEarnCodeGroup(earnCodeGroup, effectiveDate));
		} else {
			ec = (EarnCodeGroupBo) super.getBusinessObject(fieldValues);
		}

		return ec;
	}

}
