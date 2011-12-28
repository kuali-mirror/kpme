package org.kuali.hr.time.paycalendar.service;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;

import java.util.ArrayList;
import java.util.List;

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
		List<HtmlData> overrideUrls = new ArrayList<HtmlData>();
		for(HtmlData actionUrl : customActionUrls){
			if(!StringUtils.equals(actionUrl.getMethodToCall(), "copy")){
				overrideUrls.add(actionUrl);
			}
		}
		PayCalendarEntries payCalendarEntries = (PayCalendarEntries) businessObject;
		final String className = this.getBusinessObjectClass().getName();
		final String hrPyCalendarEntriesId = payCalendarEntries.getHrPyCalendarEntriesId();
		HtmlData htmlData = new HtmlData() {

			@Override
			public String constructCompleteHtmlTag() {
				return "<a target=\"_blank\" href=\"inquiry.do?businessObjectClassName="
						+ className + "&methodToCall=start&hrPyCalendarEntriesId=" + hrPyCalendarEntriesId
						+ "&pyCalendarGroup=\">view</a>";
			}
		};
		overrideUrls.add(htmlData);
		return overrideUrls;
	}
}
