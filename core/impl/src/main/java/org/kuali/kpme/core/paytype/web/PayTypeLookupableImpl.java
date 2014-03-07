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
package org.kuali.kpme.core.paytype.web;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.api.paytype.PayType;
import org.kuali.kpme.core.lookup.KpmeHrBusinessObjectLookupableImpl;
import org.kuali.kpme.core.paytype.PayTypeBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.krad.web.form.LookupForm;

public class PayTypeLookupableImpl extends KpmeHrBusinessObjectLookupableImpl {

	private static final long serialVersionUID = 3694868213243393295L;
    private static final ModelObjectUtils.Transformer<PayType, PayTypeBo> toPayTypeBo =
            new ModelObjectUtils.Transformer<PayType, PayTypeBo>() {
                public PayTypeBo transform(PayType input) {
                    return PayTypeBo.from(input);
                };
            };

	@Override
	protected List<?> getSearchResults(LookupForm form,
			Map<String, String> searchCriteria, boolean unbounded) {
		String payType = searchCriteria.get("payType");
        String regEarnCode = searchCriteria.get("regEarnCode");
        String descr = searchCriteria.get("descr");
        String location = searchCriteria.get("location"); // KPME-2701
        String institution = searchCriteria.get("institution");
        String flsaStatus = searchCriteria.get("flsaStatus");
        String payFrequency = searchCriteria.get("payFrequency");
        String fromEffdt = TKUtils.getFromDateString(searchCriteria.get("effectiveDate"));
        String toEffdt = TKUtils.getToDateString(searchCriteria.get("effectiveDate"));
        String active = searchCriteria.get("active");
        String showHist = searchCriteria.get("history");

        if (StringUtils.equals(payType, "%")) {
            payType = "";
        }
        
        if (StringUtils.equals(location, "*")) {
            location = "";
        }
        
        if (StringUtils.equals(institution, "*")) {
        	institution = "";
        }
        
        return ModelObjectUtils.transform(HrServiceLocator.getPayTypeService().getPayTypes(payType, regEarnCode, descr, location, institution, flsaStatus, payFrequency,
                TKUtils.formatDateString(fromEffdt), TKUtils.formatDateString(toEffdt), active, showHist), toPayTypeBo);
	}
    
}