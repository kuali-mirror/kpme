package org.kuali.hr.time.paycalendar.service;

import java.util.List;

import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;

public class PayCalendarEntriesLookupableHelper extends
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
		PayCalendarEntries payCalendarEntries = (PayCalendarEntries) businessObject;
		final String className = this.getBusinessObjectClass().getName();
		final Long payCalendarEntriesId = payCalendarEntries.getPayCalendarEntriesId();
		HtmlData htmlData = new HtmlData() {

			@Override
			public String constructCompleteHtmlTag() {
				return "<a target=\"_blank\" href=\"inquiry.do?businessObjectClassName="
						+ className + "&methodToCall=start&payCalendarEntriesId=" + payCalendarEntriesId
						+ "&calendarGroup=\">view</a>";
			}
		};
		customActionUrls.add(htmlData);
		return customActionUrls;
	}
}
