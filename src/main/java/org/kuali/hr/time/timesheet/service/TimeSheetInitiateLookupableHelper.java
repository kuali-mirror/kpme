package org.kuali.hr.time.timesheet.service;

import java.util.List;

import org.kuali.hr.time.timesheet.TimeSheetInitiate;
import org.kuali.hr.time.util.TKContext;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;
import org.kuali.rice.krad.bo.BusinessObject;

public class TimeSheetInitiateLookupableHelper extends KualiLookupableHelperServiceImpl {
	/**
	 * KualiLookupableHelperServiceImpl
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("rawtypes")
	@Override
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject,
			List pkNames) {
		List<HtmlData> customActionUrls = super.getCustomActionUrls(
				businessObject, pkNames);
		if (TKContext.getUser().getCurrentPersonRoles().isSystemAdmin() || TKContext.getUser().isGlobalViewOnly()) {
			TimeSheetInitiate timeSheetInitiateObj = (TimeSheetInitiate) businessObject;
			final String className = this.getBusinessObjectClass().getName();
			final String tkTimeSheetInitId = timeSheetInitiateObj.getTkTimeSheetInitId();
			HtmlData htmlData = new HtmlData() {

				private static final long serialVersionUID = 1L;

				@Override
				public String constructCompleteHtmlTag() {
					return "<a target=\"_blank\" href=\"inquiry.do?businessObjectClassName="
							+ className
							+ "&methodToCall=start&tkTimeSheetInitId="
							+ tkTimeSheetInitId + "\">view</a>";
				}
			};
			customActionUrls.add(htmlData);
		} else if (customActionUrls.size() != 0) {
			customActionUrls.remove(0);
		}
		return customActionUrls;
	}
}
