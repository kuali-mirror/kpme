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
package org.kuali.kpme.core.institution.web;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.institution.InstitutionContract;
import org.kuali.kpme.core.institution.Institution;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.rice.kns.inquiry.KualiInquirableImpl;
import org.kuali.rice.krad.bo.BusinessObject;

@SuppressWarnings("deprecation")
public class InstitutionInquirableImpl extends KualiInquirableImpl {

	private static final long serialVersionUID = 7952001181052895833L;

	@Override
	@SuppressWarnings("rawtypes")
	public BusinessObject getBusinessObject(Map fieldValues) {
		InstitutionContract institutionObj = null;	
		if (StringUtils.isNotBlank((String) fieldValues.get("pmInstitutionId"))) {
			institutionObj = HrServiceLocator.getInstitutionService().getInstitutionById((String) fieldValues.get("pmInstitutionId"));
        } else if (fieldValues.containsKey("institutionCode") && fieldValues.containsKey("effectiveDate")) {
            String effDate = (String) fieldValues.get("effectiveDate");
            LocalDate effectiveDate = StringUtils.isBlank(effDate) ? LocalDate.now() : TKUtils.formatDateString(effDate);
            
            institutionObj = HrServiceLocator.getInstitutionService().getInstitution((String)fieldValues.get("institutionCode"), effectiveDate);
        } else {
       	 	if(fieldValues.get("institutionCode") != null && !ValidationUtils.isWildCard(fieldValues.get("institutionCode").toString())){
       	 		institutionObj = (Institution) super.getBusinessObject(fieldValues);
       	}
}
		return institutionObj;
	}
	
}