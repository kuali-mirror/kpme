package org.kuali.hr.time.clock.location.service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.authorization.DepartmentalRule;
import org.kuali.hr.time.authorization.DepartmentalRuleAuthorizer;
import org.kuali.hr.time.authorization.TkAuthorizedLookupableHelperBase;
import org.kuali.hr.time.clock.location.ClockLocationRule;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.HtmlData;

public class ClockLocationRuleLookupableHelper extends
        TkAuthorizedLookupableHelperBase {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

    @Override
    /**
     * Implemented method to reduce the set of Business Objects that are shown
     * to the user based on their current roles.
     */
    public boolean shouldShowBusinessObject(BusinessObject bo) {
        return (bo instanceof DepartmentalRule) && DepartmentalRuleAuthorizer.hasAccessToRead((DepartmentalRule)bo);
    }

    @Override
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject,
			List pkNames) {
		List<HtmlData> customActionUrls = super.getCustomActionUrls(
				businessObject, pkNames);
		ClockLocationRule clockLocationRule = (ClockLocationRule) businessObject;
		final String className = this.getBusinessObjectClass().getName();
		final String tkClockLocationRuleId = clockLocationRule.getTkClockLocationRuleId();
		HtmlData htmlData = new HtmlData() {

			@Override
			public String constructCompleteHtmlTag() {
				return "<a target=\"_blank\" href=\"inquiry.do?businessObjectClassName="
						+ className + "&methodToCall=start&tkClockLocationRuleId=" + tkClockLocationRuleId
						+ "\">view</a>";
			}
		};
		customActionUrls.add(htmlData);
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
	
	@SuppressWarnings("rawtypes")
	public class EffectiveDateTimestampCompare implements Comparator{

		@Override
		public int compare(Object arg0, Object arg1) {
			ClockLocationRule clockLocationRule = (ClockLocationRule)arg0;
			ClockLocationRule clockLocationRule2 = (ClockLocationRule)arg1;
			int result = clockLocationRule.getEffectiveDate().compareTo(clockLocationRule2.getEffectiveDate());
			if(result==0){
				return clockLocationRule.getTimestamp().compareTo(clockLocationRule2.getTimestamp());
			}
			return result;
		}
		
	}
}
