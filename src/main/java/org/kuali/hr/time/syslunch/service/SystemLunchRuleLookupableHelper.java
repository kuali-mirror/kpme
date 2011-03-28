package org.kuali.hr.time.syslunch.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.syslunch.rule.SystemLunchRule;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;

public class SystemLunchRuleLookupableHelper extends
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
		SystemLunchRule systemLunchRule = (SystemLunchRule) businessObject;
		final String className = this.getBusinessObjectClass().getName();
		final Long tkSystemLunchRuleId = systemLunchRule.getTkSystemLunchRuleId();
		HtmlData htmlData = new HtmlData() {

			@Override
			public String constructCompleteHtmlTag() {
				return "<a target=\"_blank\" href=\"inquiry.do?businessObjectClassName="
						+ className + "&methodToCall=start&tkSystemLunchRuleId=" + tkSystemLunchRuleId
						+"\">view</a>";
			}
		};
		customActionUrls.add(htmlData);
		return customActionUrls;
	}
	
	@Override
	public List<? extends BusinessObject> getSearchResults(
			Map<String, String> fieldValues) {
		String showHistory = null;
		if (fieldValues.containsKey("history")) {
			showHistory = fieldValues.get("history");
			fieldValues.remove("history");
		}
		List<? extends BusinessObject> objectList = super
				.getSearchResults(fieldValues);
		if (!objectList.isEmpty() && showHistory != null
				&& StringUtils.equals(showHistory, "N")) {
			Collections.sort(objectList, new Comparator<BusinessObject>() {

				@Override
				public int compare(BusinessObject bo1, BusinessObject bo2) {
					int result = 0;
					if (bo1 instanceof SystemLunchRule) {
						SystemLunchRule slr1 = (SystemLunchRule) bo1;
						SystemLunchRule slr2 = (SystemLunchRule) bo2;
						if (slr1.getTimeStamp().before(slr2.getTimeStamp())) {
							result = 1;
						}
					} 
					return result;
				}
			});
			List<BusinessObject> objectListWithoutHistory = new ArrayList<BusinessObject>();
			objectListWithoutHistory.add(objectList.get(0));
			return objectListWithoutHistory;
		}
		return objectList;
	}

}
