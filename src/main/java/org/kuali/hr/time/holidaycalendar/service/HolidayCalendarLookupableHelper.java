package org.kuali.hr.time.holidaycalendar.service;

import java.util.List;

import org.kuali.hr.time.holidaycalendar.HolidayCalendar;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;

public class HolidayCalendarLookupableHelper extends
		KualiLookupableHelperServiceImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject,
			List pkNames) {
		List<HtmlData> customActionUrls = super.getCustomActionUrls(
				businessObject, pkNames);
		HolidayCalendar holidayCalendar = (HolidayCalendar) businessObject;
		final String className = this.getBusinessObjectClass().getName();
		final Long holidayCalendarId = holidayCalendar.getHolidayCalendarId();
		HtmlData htmlData = new HtmlData() {

			@Override
			public String constructCompleteHtmlTag() {
				return "<a target=\"_blank\" href=\"inquiry.do?businessObjectClassName="
						+ className + "&methodToCall=start&holidayCalendarId=" + holidayCalendarId
						+ "\">view</a>";
			}
		};
		customActionUrls.add(htmlData);
		return customActionUrls;
	}
}
