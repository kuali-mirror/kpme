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
package org.kuali.kpme.pm.positionappointment.web;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.lookup.KPMELookupableImpl;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.pm.service.base.PmServiceLocator;
import org.kuali.rice.krad.web.form.LookupForm;

public class PositionAppointmentLookupableImpl extends KPMELookupableImpl {

	private static final long serialVersionUID = 4826886027602440306L;
	
    @Override
    public List<?> getSearchResults(LookupForm form, Map<String, String> searchCriteria, boolean bounded) {
        String pmPositionAppointmentId = searchCriteria.get("pmPositionAppointmentId");
        String description = searchCriteria.get("description");
        String fromEffdt = TKUtils.getFromDateString(searchCriteria.get("effectiveDate"));
        String toEffdt = TKUtils.getToDateString(searchCriteria.get("effectiveDate"));
        String active = searchCriteria.get("active");
        String showHist = searchCriteria.get("history");
        String positionAppointment = searchCriteria.get("positionAppointment");
        String institution = searchCriteria.get("institution");
        String campus = searchCriteria.get("campus");

        if (StringUtils.equals(pmPositionAppointmentId, "%")) {
        	pmPositionAppointmentId = "";
        }
        
        return PmServiceLocator.getPositionAppointmentService().getPositionAppointmentList(positionAppointment, institution, campus, TKUtils.formatDateString(toEffdt));
        			//TKUtils.formatDateString(toEffdt), active, showHist);
    }

}