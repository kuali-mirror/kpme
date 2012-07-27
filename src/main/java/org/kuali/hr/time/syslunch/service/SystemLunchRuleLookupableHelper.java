package org.kuali.hr.time.syslunch.service;

import java.util.List;

import org.kuali.hr.time.HrEffectiveDateActiveLookupableHelper;
import org.kuali.hr.time.syslunch.rule.SystemLunchRule;
import org.kuali.hr.time.util.TKContext;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.krad.bo.BusinessObject;

public class SystemLunchRuleLookupableHelper extends
		HrEffectiveDateActiveLookupableHelper {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("serial")
	@Override
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject,
			@SuppressWarnings("rawtypes") List pkNames) {
		List<HtmlData> customActionUrls = super.getCustomActionUrls(
				businessObject, pkNames);
		if (TKContext.getUser().isSystemAdmin() || TKContext.getUser().isGlobalViewOnly()) {
			SystemLunchRule systemLunchRule = (SystemLunchRule) businessObject;
			final String className = this.getBusinessObjectClass().getName();
			final String tkSystemLunchRuleId = systemLunchRule
					.getTkSystemLunchRuleId();
			HtmlData htmlData = new HtmlData() {

				@Override
				public String constructCompleteHtmlTag() {
					return "<a target=\"_blank\" href=\"inquiry.do?businessObjectClassName="
							+ className
							+ "&methodToCall=start&tkSystemLunchRuleId="
							+ tkSystemLunchRuleId + "\">view</a>";
				}
			};
			customActionUrls.add(htmlData);
		} else if (customActionUrls.size() != 0) {
			customActionUrls.remove(0);
		}
		return customActionUrls;
	}


}
