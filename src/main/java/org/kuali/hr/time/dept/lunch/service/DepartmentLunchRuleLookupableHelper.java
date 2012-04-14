package org.kuali.hr.time.dept.lunch.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.authorization.DepartmentalRule;
import org.kuali.hr.time.authorization.DepartmentalRuleAuthorizer;
import org.kuali.hr.time.authorization.TkAuthorizedLookupableHelperBase;
import org.kuali.hr.time.dept.lunch.DeptLunchRule;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.krad.bo.BusinessObject;

public class DepartmentLunchRuleLookupableHelper extends
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
			@SuppressWarnings("rawtypes") List pkNames) {
		List<HtmlData> customActionUrls = super.getCustomActionUrls(
				businessObject, pkNames);
		DeptLunchRule deptLunchRule = (DeptLunchRule) businessObject;
		final String className = this.getBusinessObjectClass().getName();
		final String dept = deptLunchRule.getDept();
		final Long workArea = deptLunchRule.getWorkArea();
		final String tkDeptLunchRuleId = deptLunchRule.getTkDeptLunchRuleId();
		HtmlData htmlData = new HtmlData() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public String constructCompleteHtmlTag() {
				return "<a target=\"_blank\" href=\"inquiry.do?businessObjectClassName="
						+ className + "&methodToCall=start&dept=" + dept + "&tkDeptLunchRuleId=" + tkDeptLunchRuleId
						+ "&workArea="+workArea+"\">view</a>";
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
}
