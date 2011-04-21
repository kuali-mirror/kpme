package org.kuali.hr.time.salgroup.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.salgroup.SalGroup;
import org.kuali.hr.time.util.TKContext;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;

public class SalaryGroupLookupableHelper extends
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
			SalGroup salGroup = (SalGroup) businessObject;
			final String className = this.getBusinessObjectClass().getName();
			final Long tkSalGroupId = salGroup.getTkSalGroupId();
			HtmlData htmlData = new HtmlData() {

				@Override
				public String constructCompleteHtmlTag() {
					return "<a target=\"_blank\" href=\"inquiry.do?businessObjectClassName="
							+ className
							+ "&methodToCall=start&tkSalGroupId="
							+ tkSalGroupId + "\">view</a>";
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
				SalGroup salGroupNew = (SalGroup) bo;
				if (objectsWithoutHistory.containsKey(salGroupNew
						.getTkSalGroup())) {
					// Comparing here for duplicates
					SalGroup salGroupOld = (SalGroup) objectsWithoutHistory
							.get(salGroupNew.getTkSalGroup());
					int comparison = salGroupNew.getEffectiveDate().compareTo(
							salGroupOld.getEffectiveDate());
					// Comparison for highest effective date object to put
					switch (comparison) {
					case 0:
						if (salGroupNew.getTimestamp().after(
								salGroupOld.getTimestamp())) {
							// Sorting here by timestamp value
							objectsWithoutHistory.put(salGroupNew
									.getTkSalGroup(), salGroupNew);
						}
						break;
					case 1:
						objectsWithoutHistory.put(salGroupNew.getTkSalGroup(),
								salGroupNew);
					}
				} else {
					objectsWithoutHistory.put(salGroupNew.getTkSalGroup(),
							salGroupNew);
				}
			}
			List<BusinessObject> objectListWithoutHistory = new ArrayList<BusinessObject>();
			objectListWithoutHistory.addAll(objectsWithoutHistory.values());
			return objectListWithoutHistory;
		}
		return objectList;
	}
}
