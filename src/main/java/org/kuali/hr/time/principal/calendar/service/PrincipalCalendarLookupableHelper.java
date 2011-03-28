package org.kuali.hr.time.principal.calendar.service;

import java.util.List;

import org.kuali.hr.time.principal.calendar.PrincipalCalendar;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;

public class PrincipalCalendarLookupableHelper extends
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
		PrincipalCalendar principalCalendar = (PrincipalCalendar) businessObject;
		final String className = this.getBusinessObjectClass().getName();
		final String principalId = principalCalendar.getPrincipalId();
		HtmlData htmlData = new HtmlData() {

			@Override
			public String constructCompleteHtmlTag() {
				return "<a target=\"_blank\" href=\"inquiry.do?businessObjectClassName="
						+ className + "&methodToCall=start&principalId=" + principalId
						+ "\">view</a>";
			}
		};
		customActionUrls.add(htmlData);
		return customActionUrls;
	}
}
