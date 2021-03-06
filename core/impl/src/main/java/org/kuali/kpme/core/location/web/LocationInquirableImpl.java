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
package org.kuali.kpme.core.location.web;


import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.location.LocationContract;
import org.kuali.kpme.core.location.LocationBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.service.HrServiceLocatorInternal;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.rice.kns.inquiry.KualiInquirableImpl;
import org.kuali.rice.krad.inquiry.InquirableImpl;

public class LocationInquirableImpl extends KualiInquirableImpl  {//InquirableImpl
	
	private static final long serialVersionUID = -828163766114475072L;

	@Override
    @SuppressWarnings("rawtypes")
    public LocationContract retrieveDataObject(Map fieldValues) {
    	LocationBo locationObj = null;

    	if (StringUtils.isNotBlank((String) fieldValues.get("hrLocationId"))) {
			locationObj = HrServiceLocatorInternal.getLocationInternalService().getLocationWithRoleData((String) fieldValues.get("hrLocationId"));
        } else if (fieldValues.containsKey("location")) {
            String location = (String) fieldValues.get("location");
            String effDate = (String) fieldValues.get("effectiveDate");
            LocalDate effectiveDate = StringUtils.isBlank(effDate) ? LocalDate.now() : TKUtils.formatDateString(effDate);
            
            locationObj = HrServiceLocatorInternal.getLocationInternalService().getLocationWithRoleData(location, effectiveDate);
        } else {
       	 	if(fieldValues.get("location") != null && !ValidationUtils.isWildCard(fieldValues.get("location").toString())){
       	 		locationObj = (LocationBo) super.retrieveDataObject(fieldValues);
       	 	}
       	}

        return locationObj;
    	
    }

}
