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
package org.kuali.kpme.core.paystep.web;

import java.util.List;
import java.util.Map;

import org.kuali.kpme.core.lookup.KPMELookupableImpl;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.krad.web.form.LookupForm;

public class PayStepLookupableImpl extends KPMELookupableImpl {

	private static final long serialVersionUID = 7597508514001732034L;

    @Override
    public List<?> getSearchResults(LookupForm form, Map<String, String> searchCriteria, boolean unbounded) {
        String payStep = searchCriteria.get("payStep");
        String institution = searchCriteria.get("institution");
        String location = searchCriteria.get("location");
        String salaryGroup = searchCriteria.get("salaryGroup");
        String payGrade = searchCriteria.get("payGrade");
        String active = searchCriteria.get("active");

        return HrServiceLocator.getPayStepService().getPaySteps(payStep, institution, location, salaryGroup, payGrade, active);
    }
    
}