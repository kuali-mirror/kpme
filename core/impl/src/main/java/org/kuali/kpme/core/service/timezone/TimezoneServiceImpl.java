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
package org.kuali.kpme.core.service.timezone;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.job.Job;
import org.kuali.kpme.core.api.location.LocationContract;
import org.kuali.kpme.core.api.principal.PrincipalHRAttributesContract;
import org.kuali.kpme.core.job.JobBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.rice.krad.util.GlobalVariables;

public class TimezoneServiceImpl implements TimezoneService {

    @Override
    public String getUserTimezone(String principalId) {
        PrincipalHRAttributesContract principalCalendar = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, LocalDate.now());
        if(principalCalendar != null && principalCalendar.getTimezone() != null){
            return principalCalendar.getTimezone();
        }
        List<Job> jobs = HrServiceLocator.getJobService().getJobs(principalId, LocalDate.now());
        if (jobs.size() > 0) {
            // Grab the location off the first job in the list
            LocationContract location = HrServiceLocator.getLocationService().getLocation(jobs.get(0).getLocation(), LocalDate.now());
            if (location!=null){
                if(StringUtils.isNotBlank(location.getTimezone())){
                    return location.getTimezone();
                }
            }
        }
        return TKUtils.getSystemTimeZone();
    }

    /**
	 * Used to determine if an override condition exists for a user timezone
	 */
	@Override
	public String getUserTimezone() {
		String timezone = "";
		if (GlobalVariables.getUserSession() != null) {
			timezone = getUserTimezone(GlobalVariables.getUserSession().getPrincipalId());
		}
        return timezone;
	}

    @Override
    public DateTimeZone getUserTimezoneWithFallback() {
        String tzid = getUserTimezone();
        if (StringUtils.isEmpty(tzid)) {
            return TKUtils.getSystemDateTimeZone();
        } else {
            return DateTimeZone.forID(tzid);
        }
    }
    
    @Override
    public DateTimeZone getTargetUserTimezoneWithFallback() {
        String tzid = getTargetUserTimezone();
        if (StringUtils.isEmpty(tzid)) {
            return TKUtils.getSystemDateTimeZone();
        } else {
            return DateTimeZone.forID(tzid);
        }
    }
    
    @Override
	public String getTargetUserTimezone() {
		String timezone = "";
		if (GlobalVariables.getUserSession() != null) {
			// Logic in getTargetPrincipalId returns target principal id if the user is targeting an employee, otherwise returns the current user's id
			timezone = getUserTimezone(HrContext.getTargetPrincipalId());
		}
        return timezone;
	}
    
	@Override
	public boolean isSameTimezone() {
		String userTimezone = getUserTimezone();
		if(StringUtils.isNotBlank(userTimezone)) {
			return StringUtils.equals(TKUtils.getSystemTimeZone(), userTimezone);
		}
		return true;
	}

	@Override
	public String getApproverTimezone(String principalId) {
		PrincipalHRAttributesContract principalCalendar = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, LocalDate.now());
	    if(principalCalendar != null && principalCalendar.getTimezone() != null){
	        return principalCalendar.getTimezone();
	    }
		return TKUtils.getSystemTimeZone();
	}

}