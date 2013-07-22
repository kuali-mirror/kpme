package org.kuali.kpme.core.block.service;

import java.util.List;

import org.kuali.kpme.core.block.CalendarBlock;
import org.kuali.kpme.core.block.dao.CalendarBlockDao;

public class CalendarBlockServiceImpl implements CalendarBlockService {

	private CalendarBlockDao calendarBlockDao;

	public CalendarBlockDao getCalendarBlockDao() {
		return calendarBlockDao;
	}

	public void setCalendarBlockDao(CalendarBlockDao calendarBlockDao) {
		this.calendarBlockDao = calendarBlockDao;
	}

	@Override
	public List<CalendarBlock> getAllCalendarBlocks() {
		return calendarBlockDao.getAllCalendarBlocks();
	}

	
	
}
