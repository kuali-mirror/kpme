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
package org.kuali.kpme.core.salarygroup.web;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.api.salarygroup.SalaryGroup;
import org.kuali.kpme.core.lookup.KPMELookupableHelperServiceImpl;
import org.kuali.kpme.core.salarygroup.SalaryGroupBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.krad.bo.BusinessObject;

import java.util.List;
import java.util.Map;

public class SalaryGroupLookupableHelper extends KPMELookupableHelperServiceImpl {
    private static final ModelObjectUtils.Transformer<SalaryGroup, SalaryGroupBo> toSalaryGroupBo =
            new ModelObjectUtils.Transformer<SalaryGroup, SalaryGroupBo>() {
                public SalaryGroupBo transform(SalaryGroup input) {
                    return SalaryGroupBo.from(input);
                };
            };

	@Override
    public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {
        String hrSalGroup = fieldValues.get("hrSalGroup");
        String fromEffdt = TKUtils.getFromDateString(fieldValues.get("effectiveDate"));
        String toEffdt = TKUtils.getToDateString(fieldValues.get("effectiveDate"));
        String active = fieldValues.get("active");
        String showHist = fieldValues.get("history");
        String institution = fieldValues.get("institution");
        String location = fieldValues.get("location");
        String leavePlan = fieldValues.get("leavePlan");
        String benefitsEligible = fieldValues.get("benefitsEligible");
        String leaveEligible = fieldValues.get("leaveEligible");
        String percentTime = fieldValues.get("percentTime");

        if (StringUtils.equals(hrSalGroup, "%")) {
            hrSalGroup = "";
        }
        
        return ModelObjectUtils.transform(HrServiceLocator.getSalaryGroupService().getSalaryGroups(hrSalGroup, institution, location, leavePlan, TKUtils.formatDateString(fromEffdt),
                TKUtils.formatDateString(toEffdt), active, showHist, benefitsEligible, leaveEligible, percentTime), toSalaryGroupBo);
	}
}
