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
package org.kuali.kpme.core.earncode.web;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.earncode.EarnCodeContract;
import org.kuali.kpme.core.earncode.EarnCodeBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.rice.kns.inquiry.KualiInquirableImpl;
import org.kuali.rice.krad.bo.BusinessObject;

public class EarnCodeInquirableImpl extends KualiInquirableImpl {

    private static final long serialVersionUID = -1039815105625650050L;

    @Override
	public BusinessObject getBusinessObject(Map fieldValues) {
		EarnCodeBo ec = null;
		if(StringUtils.isNotBlank((String)fieldValues.get("hrEarnCodeId"))) {
			ec = EarnCodeBo.from(HrServiceLocator.getEarnCodeService().getEarnCodeById((String)fieldValues.get("hrEarnCodeId")));
			
		} else if(StringUtils.isNotBlank((String)fieldValues.get("earnCode"))) {
			String earnCode = (String)fieldValues.get("earnCode");
            String effDate = (String) fieldValues.get("effectiveDate");
            LocalDate effectiveDate = StringUtils.isBlank(effDate) ? LocalDate.now() : TKUtils.formatDateString(effDate);
			ec = EarnCodeBo.from(HrServiceLocator.getEarnCodeService().getEarnCode(earnCode, effectiveDate));
		} else {
			ec = (EarnCodeBo) super.getBusinessObject(fieldValues);
		}

		return ec;
	}

}
