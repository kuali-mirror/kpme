package org.kuali.hr.time.timecollection.rule.service;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.authorization.DepartmentalRule;
import org.kuali.hr.time.authorization.DepartmentalRuleAuthorizer;
import org.kuali.hr.time.authorization.TkAuthorizedLookupableHelperBase;
import org.kuali.hr.time.collection.rule.TimeCollectionRule;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.HtmlData;

import java.util.List;
import java.util.Map;

public class TimeCollectionRuleLookupableHelper extends
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
		TimeCollectionRule timeCollectionRule = (TimeCollectionRule) businessObject;
		final String className = this.getBusinessObjectClass().getName();
		final String tkTimeCollectionRuleId = timeCollectionRule.getTkTimeCollectionRuleId();
		HtmlData htmlData = new HtmlData() {

			@Override
			public String constructCompleteHtmlTag() {
				return "<a target=\"_blank\" href=\"inquiry.do?businessObjectClassName="
						+ className + "&methodToCall=start&tkTimeCollectionRuleId=" + tkTimeCollectionRuleId
						+ "&dept=&workArea=&payType=\">view</a>";
			}
		};
		customActionUrls.add(htmlData);
		return customActionUrls;
	}

	@Override
	public List<? extends BusinessObject> getSearchResults( Map<String, String> fieldValues) {
        String workArea = fieldValues.get("workArea");
        String dept = fieldValues.get("dept");
        String payType = fieldValues.get("payType");
        String active = fieldValues.get("active");

        List<TimeCollectionRule> timeCollectionRules = TkServiceLocator.getTimeCollectionRuleService().getTimeCollectionRules(dept, workArea, payType, active);
		return timeCollectionRules;
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
