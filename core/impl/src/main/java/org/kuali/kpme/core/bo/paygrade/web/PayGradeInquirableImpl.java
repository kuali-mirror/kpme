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
package org.kuali.kpme.core.bo.paygrade.web;


import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.paygrade.PayGrade;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.rice.kns.inquiry.KualiInquirableImpl;
import org.kuali.rice.krad.bo.BusinessObject;

import java.util.Map;

public class PayGradeInquirableImpl extends KualiInquirableImpl {

	private static final long serialVersionUID = -4002061046745019065L;

	@Override
	public BusinessObject getBusinessObject(Map fieldValues) {
        PayGrade payGrade = null;
        if (StringUtils.isNotBlank((String) fieldValues.get("hrPayGradeId"))) {
            payGrade = HrServiceLocator.getPayGradeService().getPayGrade((String) fieldValues.get("hrPayGradeId"));
        } else if (fieldValues.containsKey("payGrade")
                && fieldValues.containsKey("salGroup")
                && fieldValues.containsKey("effectiveDate")) {
            String pg = (String)fieldValues.get("payGrade");
            String sg = (String)fieldValues.get("salGroup");
            LocalDate effectiveDate = TKUtils.formatDateString((String) fieldValues.get("effectiveDate"));
            
            payGrade = HrServiceLocator.getPayGradeService().getPayGrade(pg, sg, effectiveDate);
        } else {
            payGrade = (PayGrade) super.getBusinessObject(fieldValues);
        }


		return payGrade;
	}
	
}