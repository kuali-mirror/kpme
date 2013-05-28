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
package org.kuali.kpme.core.location.web;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.location.Location;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.rice.kns.inquiry.KualiInquirableImpl;
import org.kuali.rice.krad.bo.BusinessObject;

@SuppressWarnings("deprecation")
public class LocationInquirableImpl extends KualiInquirableImpl {

	private static final long serialVersionUID = 7952001181052895833L;

	@Override
	@SuppressWarnings("rawtypes")
	public BusinessObject getBusinessObject(Map fieldValues) {
		Location locationObj = null;
		
		if (StringUtils.isNotBlank((String) fieldValues.get("hrLocationId"))) {
			locationObj = HrServiceLocator.getLocationService().getLocation((String) fieldValues.get("hrLocationId"));
        } else if (fieldValues.containsKey("location") && fieldValues.containsKey("effectiveDate")) {
            String location = (String) fieldValues.get("location");
            LocalDate effectiveDate = TKUtils.formatDateString((String) fieldValues.get("effectiveDate"));
            locationObj = HrServiceLocator.getLocationService().getLocation(location, effectiveDate);
        } else {
        	locationObj = (Location) super.getBusinessObject(fieldValues);
        }

		return locationObj;
	}
	
}