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
package org.kuali.kpme.pm.positionappointment.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.kpme.core.lookup.KPMELookupableImpl;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.pm.api.positionappointment.PositionAppointment;
import org.kuali.kpme.pm.api.positionappointment.PositionAppointmentContract;
import org.kuali.kpme.pm.positionappointment.PositionAppointmentBo;
import org.kuali.kpme.pm.positionappointment.authorization.PositionAppointmentAuthorizer;
import org.kuali.kpme.pm.service.base.PmServiceLocator;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.util.GlobalVariables;

public class PositionAppointmentLookupableImpl extends KPMELookupableImpl {

	private static final long serialVersionUID = 4826886027602440306L;

	private static final ModelObjectUtils.Transformer<PositionAppointment, PositionAppointmentBo> toPositionAppointmentBo =
			new ModelObjectUtils.Transformer<PositionAppointment, PositionAppointmentBo>() {
		public PositionAppointmentBo transform(PositionAppointment input) {
			return PositionAppointmentBo.from(input);
		};
	};

    protected List<PositionAppointmentBo> filterLookupPositionAppointments(List<PositionAppointmentBo> rawResults, Person user)
    {
        List<PositionAppointmentBo> returnList = new ArrayList<PositionAppointmentBo>();
        for (PositionAppointmentBo positionAppointmentObj : rawResults)
        {
            if (((PositionAppointmentAuthorizer)(getDocumentDictionaryService().getDocumentAuthorizer(this.getMaintenanceDocumentTypeName()))).canView((Object) positionAppointmentObj, user))
            {
                returnList.add(positionAppointmentObj);
            }
        }
        return returnList;
    }

    @Override
    protected Collection<?> executeSearch(Map<String, String> searchCriteria, List<String> wildcardAsLiteralSearchCriteria, boolean bounded, Integer searchResultsLimit) {

		//return super.getSearchResults(form, searchCriteria, bounded);
/*
		String description = searchCriteria.get("description");
		String fromEffdt = TKUtils.getFromDateString(searchCriteria.get("effectiveDate"));
		String toEffdt = TKUtils.getToDateString(searchCriteria.get("effectiveDate"));
		String active = searchCriteria.get("active");
		String showHist = searchCriteria.get("history");
		String positionAppointment = searchCriteria.get("positionAppointment");
		String institution = searchCriteria.get("institution");
		String location = searchCriteria.get("location");
		String groupKeyCode = searchCriteria.get("groupKeyCode");

		List<PositionAppointmentBo> posApptContrasts = ModelObjectUtils.transform(PmServiceLocator.getPositionAppointmentService().getPositionAppointmentList
				(positionAppointment, description, groupKeyCode, TKUtils.formatDateString(fromEffdt), TKUtils.formatDateString(toEffdt), active, showHist), toPositionAppointmentBo);
*/

        List<PositionAppointmentBo> posApptContrasts = (List<PositionAppointmentBo>)super.executeSearch(searchCriteria, wildcardAsLiteralSearchCriteria, bounded, searchResultsLimit);

        List<PositionAppointmentBo> filteredResults = filterLookupPositionAppointments(posApptContrasts, GlobalVariables.getUserSession().getPerson());
		// TODO: Filter the result by institution and location here

		return filteredResults;
	}

}