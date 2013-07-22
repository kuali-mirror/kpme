package org.kuali.kpme.core.block.dao;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.block.CalendarBlock;

public interface CalendarBlockDao {

	public List<CalendarBlock> getAllCalendarBlocks();
	
	public List<CalendarBlock> getActiveCalendarBlocksForDate(LocalDate asOfDate);
	
}
