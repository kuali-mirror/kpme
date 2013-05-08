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
package org.kuali.hr.paygrade.web;

import org.apache.commons.lang3.StringUtils;
import org.kuali.hr.paygrade.PayGrade;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.kns.inquiry.KualiInquirableImpl;
import org.kuali.rice.krad.bo.BusinessObject;

import java.util.Map;

public class PayGradeInquirableImpl extends KualiInquirableImpl {

	private static final long serialVersionUID = -4002061046745019065L;

	@Override
	public BusinessObject getBusinessObject(Map fieldValues) {
        PayGrade payGrade = null;
        if (StringUtils.isNotBlank((String)fieldValues.get("hrPayGradeId"))) {
            payGrade = TkServiceLocator.getPayGradeService().getPayGrade((String) fieldValues.get("hrPayGradeId"));
        } else if (fieldValues.containsKey("payGrade")
                && fieldValues.containsKey("salGroup")
                && fieldValues.containsKey("effectiveDate")) {
            String pg = (String)fieldValues.get("payGrade");
            String sg = (String)fieldValues.get("salGroup");

            payGrade = TkServiceLocator.getPayGradeService().getPayGrade(pg, sg,
                    new java.sql.Date(TKUtils.convertDateStringToTimestampNoTimezone((String) fieldValues.get("effectiveDate")).getTime()));
        } else {
            payGrade = (PayGrade) super.getBusinessObject(fieldValues);
        }


		return payGrade;
	}
	
}