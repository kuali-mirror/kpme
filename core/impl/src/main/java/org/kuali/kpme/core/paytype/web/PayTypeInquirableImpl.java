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
package org.kuali.kpme.core.paytype.web;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.inquirable.KPMEInquirableImpl;
import org.kuali.kpme.core.paytype.PayType;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.rice.kns.inquiry.KualiInquirableImpl;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.krad.bo.BusinessObject;

public class PayTypeInquirableImpl extends KualiInquirableImpl {

	@Override
	public HtmlData getInquiryUrl(BusinessObject businessObject,
			String attributeName, boolean forceInquiry) {
		return super.getInquiryUrl(businessObject, attributeName, forceInquiry);
	}

	@Override
	@Deprecated
	public BusinessObject getBusinessObject(Map fieldValues) {
        PayType payTypeObj = null;

        if (StringUtils.isNotBlank((String) fieldValues.get("hrPayTypeId"))) {
            payTypeObj = HrServiceLocator.getPayTypeService().getPayType((String) fieldValues.get("hrPayTypeId"));
        } else if (fieldValues.containsKey("payType")) {
            String payType = (String) fieldValues.get("payType");
            String effDate = (String) fieldValues.get("effectiveDate");
            LocalDate effectiveDate = StringUtils.isBlank(effDate) ? LocalDate.now() : TKUtils.formatDateString(effDate);
            payTypeObj = HrServiceLocator.getPayTypeService().getPayType(payType, effectiveDate);
        } else {
            payTypeObj = (PayType) super.getBusinessObject(fieldValues);
        }

        return payTypeObj;
	}

}
