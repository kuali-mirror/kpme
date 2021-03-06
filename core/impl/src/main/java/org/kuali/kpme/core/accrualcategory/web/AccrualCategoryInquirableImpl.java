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
package org.kuali.kpme.core.accrualcategory.web;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.accrualcategory.AccrualCategoryBo;
import org.kuali.kpme.core.api.accrualcategory.AccrualCategoryContract;
import org.kuali.kpme.core.inquirable.KPMEInquirableImpl;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.rice.krad.bo.BusinessObject;

public class AccrualCategoryInquirableImpl extends KPMEInquirableImpl {

    private static final long serialVersionUID = -5835722240665963769L;

    @Override
	public BusinessObject getBusinessObject(Map fieldValues) {
		AccrualCategoryBo ac = null;
		if(StringUtils.isNotBlank((String)fieldValues.get("lmAccrualCategoryId"))) {
			ac = AccrualCategoryBo.from(HrServiceLocator.getAccrualCategoryService().getAccrualCategory((String)fieldValues.get("lmAccrualCategoryId")));
			
		} else if(StringUtils.isNotBlank((String)fieldValues.get("accrualCategory"))) {
			String accrualCategory = (String)fieldValues.get("accrualCategory");
            String effDate = (String) fieldValues.get("effectiveDate");
            LocalDate effectiveDate = StringUtils.isBlank(effDate) ? LocalDate.now() : TKUtils.formatDateString(effDate);
		    ac = AccrualCategoryBo.from(HrServiceLocator.getAccrualCategoryService().getAccrualCategory(accrualCategory, effectiveDate));
		} else {
			ac = (AccrualCategoryBo) super.getBusinessObject(fieldValues);
		}

        if (ac == null){
            return null;
        }
        org.kuali.kpme.core.api.accrualcategory.AccrualCategory accrualCategory =
                org.kuali.kpme.core.api.accrualcategory.AccrualCategory.Builder.create(ac).build();
		return AccrualCategoryBo.from(accrualCategory);
	}
}
