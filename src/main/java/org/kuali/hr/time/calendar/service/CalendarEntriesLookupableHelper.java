package org.kuali.hr.time.calendar.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;
import org.kuali.rice.krad.bo.BusinessObject;

public class CalendarEntriesLookupableHelper extends
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
		List<HtmlData> overrideUrls = new ArrayList<HtmlData>();
		for(HtmlData actionUrl : customActionUrls){
			if(!StringUtils.equals(actionUrl.getMethodToCall(), "copy")){
				overrideUrls.add(actionUrl);
			}
		}
		CalendarEntries calendarEntries = (CalendarEntries) businessObject;
		final String className = this.getBusinessObjectClass().getName();
		final String hrCalendarEntriesId = calendarEntries.getHrCalendarEntriesId();
		HtmlData htmlData = new HtmlData() {

			@Override
			public String constructCompleteHtmlTag() {
				return "<a target=\"_blank\" href=\"inquiry.do?businessObjectClassName="
						+ className + "&methodToCall=start&hrCalendarEntriesId=" + hrCalendarEntriesId
						+ "&calendarName=\">view</a>";
			}
		};
		overrideUrls.add(htmlData);
		return overrideUrls;
	}
}
