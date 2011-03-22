package org.kuali.hr.time.timezone.service;

import java.util.List;

import org.kuali.hr.time.timeblock.TimeBlock;

public interface TimezoneService {
	/**
	 * Fetch user time zone
	 * @return
	 */
	public String getUserTimeZone();
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
