package org.kuali.hr.time.graceperiod.service;

import java.util.List;

import org.kuali.hr.time.graceperiod.rule.GracePeriodRule;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;

public class GracePeriodRuleLookupableHelper extends
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
		GracePeriodRule gracePeriodRule = (GracePeriodRule) businessObject;
		final String className = this.getBusinessObjectClass().getName();
		final Long tkGracePeriodRuleId = gracePeriodRule.getTkGracePeriodRuleId();
		HtmlData htmlData = new HtmlData() {

			@Override
			public String constructCompleteHtmlTag() {
				return "<a target=\"_blank\" href=\"inquiry.do?businessObjectClassName="
						+ className + "&methodToCall=start&tkGracePeriodRuleId=" + tkGracePeriodRuleId
						+ "\">view</a>";
			}
		};
		customActionUrls.add(htmlData);
		return customActionUrls;
	}
}
