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

import org.joda.time.DateTimeZone;
import org.kuali.kpme.core.util.HrConstants;
import org.springframework.cache.annotation.Cacheable;

public interface TimezoneService {

    /**
     * Returns the DateTimeZone object for the current user OR the system
     * default timezone if there is no current user / a time zone is missing.
     *
     * @return
     */
    public DateTimeZone getUserTimezoneWithFallback();

	/**
	 * Fetch user time zone of the current on-context user.
	 * @return
	 */
	public String getUserTimezone();

    /**
     * (this call may be cached)
     * Fetch the users timezone, Data on:
     *
     * Principal Calendar > Job/Location > System Default
     *
     * @param principalId The principal you are looking for.
     *
     * @return String timezone, see: http://joda-time.sourceforge.net/timezones.html
     */
    @Cacheable(value= HrConstants.CacheNamespace.KPME_GLOBAL_CACHE_NAME, key="'{UserTimezone}' + 'principalId=' + #p0")
    public String getUserTimezone(String principalId);

	/**
	 * Determine if Timezone is same as server timezone
	 * @return
	 */
	public boolean isSameTimezone();
	
	/**
	 * Fetch the time zone of the approver. This method is used in the approval page
	 * @param principalId The principal id for the approver
	 * @return String timezone, see: http://joda-time.sourceforge.net/timezones.html
	 */
	public String getApproverTimezone(String principalId);
	
	/**
	 * Fetch the time zone of the current target user.
	 * If there's no target user, time zone of the current user or system time zone will be returned
	 * @return 
	 */
	public DateTimeZone getTargetUserTimezoneWithFallback();
	
	/**
	 * Fetch the id of the time zone of the current target user.
	 * If there's no target user, time zone of the current user or system time zone will be returned
	 * @return String timezone, see: http://joda-time.sourceforge.net/timezones.html
	 */
	public String getTargetUserTimezone();

}