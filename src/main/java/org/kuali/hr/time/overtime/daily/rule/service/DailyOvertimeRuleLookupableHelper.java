package org.kuali.hr.time.overtime.daily.rule.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.overtime.daily.rule.DailyOvertimeRule;
import org.kuali.hr.time.util.TKContext;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;

public class DailyOvertimeRuleLookupableHelper extends
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
		if (TKContext.getUser().getCurrentRoles().isSystemAdmin()) {
			DailyOvertimeRule dailyOvertimeRule = (DailyOvertimeRule) businessObject;
			final String className = this.getBusinessObjectClass().getName();
			final Long tkDailyOvertimeRuleId = dailyOvertimeRule
					.getTkDailyOvertimeRuleId();
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
	public List<? extends BusinessObject> getSearchResults(
			Map<String, String> fieldValues) {
		if (fieldValues.containsKey("workArea")
				&& StringUtils.equals(fieldValues.get("workArea"), "%")) {
			fieldValues.put("workArea", "");
		}
		if (fieldValues.containsKey("dept")
				&& StringUtils.equals(fieldValues.get("dept"), "%")) {
			fieldValues.put("dept", "");
		}
		if (fieldValues.containsKey("jobNumber")
				&& StringUtils.equals(fieldValues.get("jobNumber"), "%")) {
			fieldValues.put("jobNumber", "");
		}
		String showHistory = null;
		if (fieldValues.containsKey("history")) {
			showHistory = fieldValues.get("history");
			fieldValues.remove("history");
		}
		List<? extends BusinessObject> objectList = super
				.getSearchResults(fieldValues);
		if (!objectList.isEmpty() && showHistory != null
				&& StringUtils.equals(showHistory, "N")) {
			Map<String, BusinessObject> objectsWithoutHistory = new HashMap<String, BusinessObject>();
			// Creating map for objects without history
			for (BusinessObject bo : objectList) {
				DailyOvertimeRule dailyOvertimeRuleNew = (DailyOvertimeRule) bo;
				if (objectsWithoutHistory.containsKey(dailyOvertimeRuleNew
						.getDept()
						+ dailyOvertimeRuleNew.getWorkArea()
						+ dailyOvertimeRuleNew.getLocation())) {
					// Comparing here for duplicates
					DailyOvertimeRule dailyOvertimeRuleOld = (DailyOvertimeRule) objectsWithoutHistory
							.get(dailyOvertimeRuleNew.getDept()
									+ dailyOvertimeRuleNew.getWorkArea()
									+ dailyOvertimeRuleNew.getLocation());
					int comparison = dailyOvertimeRuleNew.getEffectiveDate()
							.compareTo(dailyOvertimeRuleOld.getEffectiveDate());
					// Comparison for highest effective date object to put
					switch (comparison) {
					case 0:
						if (dailyOvertimeRuleNew.getTimeStamp().after(
								dailyOvertimeRuleOld.getTimeStamp())) {
							// Sorting here by timestamp value
							objectsWithoutHistory.put(dailyOvertimeRuleNew
									.getDept()
									+ dailyOvertimeRuleNew.getWorkArea()
									+ dailyOvertimeRuleNew.getLocation(),
									dailyOvertimeRuleNew);
						}
						break;
					case 1:
						objectsWithoutHistory.put(dailyOvertimeRuleNew
								.getDept()
								+ dailyOvertimeRuleNew.getWorkArea()
								+ dailyOvertimeRuleNew.getLocation(),
								dailyOvertimeRuleNew);
					}
				} else {
					objectsWithoutHistory.put(dailyOvertimeRuleNew.getDept()
							+ dailyOvertimeRuleNew.getWorkArea()
							+ dailyOvertimeRuleNew.getLocation(),
							dailyOvertimeRuleNew);
				}
			}
			List<BusinessObject> objectListWithoutHistory = new ArrayList<BusinessObject>();
			objectListWithoutHistory.addAll(objectsWithoutHistory.values());
			return objectListWithoutHistory;
		}
		return objectList;
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
