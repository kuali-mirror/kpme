package org.kuali.hr.time.holidaycalendar.service;

import java.util.List;

import javax.jws.WebService;

import org.kuali.hr.sys.context.SpringContext;
import org.kuali.hr.time.exceptions.TkException;
import org.kuali.hr.time.holidaycalendar.HolidayCalendar;
import org.kuali.hr.time.holidaycalendar.HolidayCalendarDateEntry;
import org.kuali.hr.time.holidaycalendar.dao.HolidayCalendarDao;
import org.kuali.hr.time.paytype.PayType;
import org.kuali.hr.time.paytype.dao.PayTypeDao;

/**
 * 
 * @author crivera
 * 
 */
@WebService(endpointInterface = "org.kuali.hr.time.holidaycalendar.service.HolidayCalendarService")
public class HolidayCalendarServiceImpl implements HolidayCalendarService {

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.kuali.hr.time.holidaycalendar.service.HolidayCalendarService#
	 * getHolidaysByPrincipalId(java.lang.String)
	 */
	@Override
	public List<HolidayCalendarDateEntry> getHolidaysByPrincipalId(String principalId) throws TkException {
		// get the paytype
		PayTypeDao payTypeDao = SpringContext.getBean(PayTypeDao.class);
		PayType payType = payTypeDao.getPayTypeByPrincipalId(principalId);
		// get the actual calendar
		HolidayCalendar calendar = this.getHolidayCalendarByGroup(payType
				.getHolidayCalendarGroup());
		// return the list of dates
		return calendar.getDateEntries();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.kuali.hr.time.holidaycalendar.service.HolidayCalendarService#
	 * getHolidayCalendarByGroup(java.lang.String)
	 */
	@Override
	public HolidayCalendar getHolidayCalendarByGroup(String holidayCalendarGroup)
			throws TkException {
		HolidayCalendarDao holidayCalendarDao = SpringContext
				.getBean(HolidayCalendarDao.class);
		return holidayCalendarDao
				.getHolidayCalendarByGroup(holidayCalendarGroup);
	}

}
