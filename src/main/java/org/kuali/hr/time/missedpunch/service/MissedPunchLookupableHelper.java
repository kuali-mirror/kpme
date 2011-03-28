package org.kuali.hr.time.missedpunch.service;

import java.util.List;

import org.kuali.hr.job.Job;
import org.kuali.hr.time.missedpunch.MissedPunch;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;

public class MissedPunchLookupableHelper extends
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
		MissedPunch missedPunch = (MissedPunch) businessObject;
		final String className = this.getBusinessObjectClass().getName();
		final Long tkMissedPunchId = missedPunch.getTkMissedPunchId();
		HtmlData htmlData = new HtmlData() {

			@Override
			public String constructCompleteHtmlTag() {
				return "<a target=\"_blank\" href=\"inquiry.do?businessObjectClassName="
						+ className + "&methodToCall=start&tkMissedPunchId=" + tkMissedPunchId
						+ "\">view</a>";
			}
		};
		customActionUrls.add(htmlData);
		return customActionUrls;
	}
}
