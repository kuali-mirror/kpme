package org.kuali.hr.time.timezone.service;

import java.util.List;

import org.kuali.hr.time.timeblock.TimeBlock;

public interface TimezoneService {
	public String getUserTimeZone();
	public List<TimeBlock> translateForTimezone(List<TimeBlock> timeBlocks, String timezone);
	public boolean isSameTimezone();
}
