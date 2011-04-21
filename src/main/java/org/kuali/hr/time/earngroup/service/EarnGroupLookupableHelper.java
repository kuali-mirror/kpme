package org.kuali.hr.time.earngroup.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.earngroup.EarnGroup;
import org.kuali.hr.time.util.TKContext;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;

public class EarnGroupLookupableHelper extends KualiLookupableHelperServiceImpl {
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
			EarnGroup earnGroupObj = (EarnGroup) businessObject;
			final String className = this.getBusinessObjectClass().getName();
			final String earnGroup = earnGroupObj.getEarnGroup();
			HtmlData htmlData = new HtmlData() {

				@Override
				public String constructCompleteHtmlTag() {
					return "<a target=\"_blank\" href=\"inquiry.do?businessObjectClassName="
							+ className
							+ "&methodToCall=start&earnGroup="
							+ earnGroup + "&tkEarnGroupId=\">view</a>";
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
				EarnGroup earnGroupNew = (EarnGroup) bo;
				if (objectsWithoutHistory.containsKey(earnGroupNew
						.getEarnGroup())) {
					// Comparing here for duplicates
					EarnGroup earnGroupOld = (EarnGroup) objectsWithoutHistory
							.get(earnGroupNew.getEarnGroup());
					int comparison = earnGroupNew.getEffectiveDate()
							.compareTo(earnGroupOld.getEffectiveDate());
					// Comparison for highest effective date object to put
					switch (comparison) {
					case 0:
						if (earnGroupNew.getTimestamp().after(
								earnGroupOld.getTimestamp())) {
							// Sorting here by timestamp value
							objectsWithoutHistory.put(earnGroupNew
									.getEarnGroup(), earnGroupNew);
						}
						break;
					case 1:
						objectsWithoutHistory.put(earnGroupNew.getEarnGroup(),
								earnGroupNew);
					}
				} else {
					objectsWithoutHistory.put(earnGroupNew.getEarnGroup(),
							earnGroupNew);
				}
			}
			List<BusinessObject> objectListWithoutHistory = new ArrayList<BusinessObject>();
			objectListWithoutHistory.addAll(objectsWithoutHistory.values());
			return objectListWithoutHistory;
		}
		return objectList;
	}
}
