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
package org.kuali.kpme.core.job.web;

import org.kuali.kpme.core.lookup.KPMELookupableImpl;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.form.LookupForm;

import java.util.List;
import java.util.Map;

public class JobLookupableImpl extends KPMELookupableImpl{

    @Override
    public List<? extends BusinessObject> getSearchResults(LookupForm form, Map<String, String> searchCriteria, boolean unbounded) {
        String principalId = searchCriteria.get("principalId");
        String firstName = searchCriteria.get("firstName");
        String lastName = searchCriteria.get("lastName");
        String jobNumber = searchCriteria.get("jobNumber");
        String dept = searchCriteria.get("dept");
        String positionNumber = searchCriteria.get("positionNumber");
        String hrPayType = searchCriteria.get("hrPayType");
        String fromEffdt = TKUtils.getFromDateString(searchCriteria.get("effectiveDate"));
        String toEffdt = TKUtils.getToDateString(searchCriteria.get("effectiveDate"));
        String active = searchCriteria.get("active");
        String showHist = searchCriteria.get("history");

        return HrServiceLocator.getJobService().getJobs(GlobalVariables.getUserSession().getPrincipalId(), principalId, firstName, lastName, jobNumber, dept, positionNumber, hrPayType,
                TKUtils.formatDateString(fromEffdt), TKUtils.formatDateString(toEffdt), active, showHist);
    }


}
