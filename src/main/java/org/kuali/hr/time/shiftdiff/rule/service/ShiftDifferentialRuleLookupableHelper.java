package org.kuali.hr.time.shiftdiff.rule.service;

import java.util.List;

import org.kuali.hr.time.shiftdiff.rule.ShiftDifferentialRule;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;

public class ShiftDifferentialRuleLookupableHelper extends
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
		ShiftDifferentialRule shiftDifferentialRule = (ShiftDifferentialRule) businessObject;
		final String className = this.getBusinessObjectClass().getName();
		final Long tkShiftDiffRuleId = shiftDifferentialRule.getTkShiftDiffRuleId();
		HtmlData htmlData = new HtmlData() {

			@Override
			public String constructCompleteHtmlTag() {
				return "<a target=\"_blank\" href=\"inquiry.do?businessObjectClassName="
						+ className + "&methodToCall=start&tkShiftDiffRuleId=" + tkShiftDiffRuleId
						+ "\">view</a>";
			}
		};
		customActionUrls.add(htmlData);
		return customActionUrls;
	}
}
