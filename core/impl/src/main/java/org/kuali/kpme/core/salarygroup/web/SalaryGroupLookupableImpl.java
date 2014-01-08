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

import org.kuali.kpme.core.lookup.KPMELookupableImpl;

public class SalaryGroupLookupableImpl extends KPMELookupableImpl {

	private static final long serialVersionUID = 4826886027602440306L;
	
//    @Override
//    public List<?> getSearchResults(LookupForm form, Map<String, String> searchCriteria, boolean unbounded) {
//        String hrSalGroup = searchCriteria.get("hrSalGroup");
//        String fromEffdt = TKUtils.getFromDateString(searchCriteria.get("effectiveDate"));
//        String toEffdt = TKUtils.getToDateString(searchCriteria.get("effectiveDate"));
//        String active = searchCriteria.get("active");
//        String showHist = searchCriteria.get("history");
//        String institution = searchCriteria.get("institution");
//        String location = searchCriteria.get("location");
//        String leavePlan = searchCriteria.get("leavePlan");
//        String benefitsEligible = searchCriteria.get("benefitsEligible");
//        String leaveEligible = searchCriteria.get("leaveEligible");
//        String percentTime = searchCriteria.get("percentTime");
//
//        if (StringUtils.equals(hrSalGroup, "%")) {
//            hrSalGroup = "";
//        }
//        
//        return HrServiceLocator.getSalaryGroupService().getSalaryGroups(hrSalGroup, institution, location, leavePlan, TKUtils.formatDateString(fromEffdt),
//                TKUtils.formatDateString(toEffdt), active, showHist, benefitsEligible, leaveEligible, percentTime);
//    }

}