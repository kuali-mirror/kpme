package org.kuali.hr.time.timezone.service;

import java.util.List;

import org.apache.abdera.model.DateTime;
import org.kuali.hr.time.timeblock.TimeBlock;

public interface TimezoneService {
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
    public String getUserTimezone(String principalId);

	/**
	 * Translate TimeBlocks to a given timezone
	 * @param timeBlocks
	 * @param timezone
	 * @return
	 */
	public List<TimeBlock> translateForTimezone(List<TimeBlock> timeBlocks, String timezone);

	/**
	 * Determine if Timezone is same as server timezone
	 * @return
	 */
	public boolean isSameTimezone();
}
