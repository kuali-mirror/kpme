package org.kuali.hr.time.overtime.daily.rule.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.HrEffectiveDateActiveLookupableHelper;
import org.kuali.hr.time.overtime.daily.rule.DailyOvertimeRule;
import org.kuali.hr.time.util.TKContext;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.krad.bo.BusinessObject;

public class DailyOvertimeRuleLookupableHelper extends
		HrEffectiveDateActiveLookupableHelper {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("rawtypes")
	@Override
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject,
			List pkNames) {
		List<HtmlData> customActionUrls = super.getCustomActionUrls(
				businessObject, pkNames);
		if (TKContext.getUser().getCurrentRoles().isSystemAdmin() || TKContext.getUser().isGlobalViewOnly()) {
			DailyOvertimeRule dailyOvertimeRule = (DailyOvertimeRule) businessObject;
			final String className = this.getBusinessObjectClass().getName();
			final String tkDailyOvertimeRuleId = dailyOvertimeRule
					.getTkDailyOvertimeRuleId();
			@SuppressWarnings("serial")
			HtmlData htmlData = new HtmlData() {

				@Override
				public String constructCompleteHtmlTag() {
					return "<a target=\"_blank\" href=\"inquiry.do?businessObjectClassName="
							+ className
							+ "&methodToCall=start&tkDailyOvertimeRuleId="
							+ tkDailyOvertimeRuleId + "\">view</a>";
				}
			};
			customActionUrls.add(htmlData);
		} else if (customActionUrls.size() != 0) {
			customActionUrls.remove(0);
		}
		return customActionUrls;
	}


	@Override
	protected void validateSearchParameterWildcardAndOperators(
			String attributeName, String attributeValue) {
		if (!StringUtils.equals(attributeValue, "%")) {
			super.validateSearchParameterWildcardAndOperators(attributeName,
					attributeValue);
		}
	}
}
