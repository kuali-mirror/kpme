/**
 * Copyright 2004-2012 The Kuali Foundation
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
package org.kuali.hr.time.timezone.service;

import java.util.List;

import org.joda.time.DateTimeZone;
import org.kuali.hr.core.KPMEConstants;
import org.kuali.hr.time.timeblock.TimeBlock;
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
    @Cacheable(value= KPMEConstants.KPME_GLOBAL_CACHE_NAME, key="'{UserTimezone}' + 'principalId=' + #p0")
    public String getUserTimezone(String principalId);

	/**
	 * Translate TimeBlocks to a given timezone
	 * @param timeBlocks
	 * @param timezone
	 * @return
	 */
	public List<TimeBlock> translateForTimezone(List<TimeBlock> timeBlocks, String timezone);

    public void translateForTimezone(List<TimeBlock> timeBlocks);

	/**
	 * Determine if Timezone is same as server timezone
	 * @return
	 */
	public boolean isSameTimezone();
	
	public long getTimezoneOffsetFromServerTime(DateTimeZone dtz);
}
