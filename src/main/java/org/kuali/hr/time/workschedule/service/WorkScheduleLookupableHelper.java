package org.kuali.hr.time.workschedule.service;

import java.util.List;

import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.workschedule.WorkSchedule;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;
import org.kuali.rice.krad.bo.BusinessObject;

public class WorkScheduleLookupableHelper extends
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
		if (TKContext.getUser().getCurrentPersonRoles().isSystemAdmin() || TKContext.getUser().isGlobalViewOnly()) {
			WorkSchedule workSchedule = (WorkSchedule) businessObject;
			final String className = this.getBusinessObjectClass().getName();
			final Long hrWorkScheduleId = workSchedule.getHrWorkSchedule();
			HtmlData htmlData = new HtmlData() {

				@Override
				public String constructCompleteHtmlTag() {
					return "<a target=\"_blank\" href=\"inquiry.do?businessObjectClassName="
							+ className
							+ "&methodToCall=start&hrWorkScheduleId="
							+ hrWorkScheduleId + "\">view</a>";
				}
			};
			customActionUrls.add(htmlData);
		} else if (customActionUrls.size() != 0) {
			customActionUrls.remove(0);
		}
		return customActionUrls;
	}

}
