package org.kuali.hr.time.accrual.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.accrual.AccrualCategory;
import org.kuali.hr.time.util.TKContext;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;

/**
 * Used to override lookup functionality for the accrual category lookup
 * 
 * 
 */
public class AccrualCategoryLookupableHelper extends
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
			List<HtmlData> overrideUrls = new ArrayList<HtmlData>();
			for(HtmlData actionUrl : customActionUrls){
				if(!StringUtils.equals(actionUrl.getMethodToCall(), "copy")){
					overrideUrls.add(actionUrl);
				}
			}

		if (TKContext.getUser().getCurrentRoles().isSystemAdmin()) {
			AccrualCategory accrualCategory = (AccrualCategory) businessObject;
			final String className = this.getBusinessObjectClass().getName();
			final Long laAccrualCategoryId = accrualCategory
					.getLaAccrualCategoryId();
			HtmlData htmlData = new HtmlData() {

				@Override
				public String constructCompleteHtmlTag() {
					return "<a target=\"_blank\" href=\"inquiry.do?businessObjectClassName="
							+ className
							+ "&methodToCall=start&laAccrualCategoryId="
							+ laAccrualCategoryId + "\">view</a>";
				}
			};
			overrideUrls.add(htmlData);
		} else if (overrideUrls.size() != 0) {
			overrideUrls.remove(0);
		}
		return overrideUrls;
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
				AccrualCategory accrualCategoryNew = (AccrualCategory) bo;
				if (objectsWithoutHistory.containsKey(accrualCategoryNew
						.getAccrualCategory())) {
					// Comparing here for duplicates
					AccrualCategory accrualCategoryOld = (AccrualCategory) objectsWithoutHistory
							.get(accrualCategoryNew.getAccrualCategory());
					int comparison = accrualCategoryNew.getEffectiveDate()
							.compareTo(accrualCategoryOld.getEffectiveDate());
					// Comparison for highest effective date object to put 
					switch (comparison) {
					case 0:
						if (accrualCategoryNew.getTimestamp().after(
								accrualCategoryOld.getTimestamp())) {
							// Sorting here by timestamp value
							objectsWithoutHistory.put(accrualCategoryNew
									.getAccrualCategory(), accrualCategoryNew);
						}
						break;
					case 1:
						objectsWithoutHistory.put(accrualCategoryNew
								.getAccrualCategory(), accrualCategoryNew);
					}
				} else {
					objectsWithoutHistory.put(accrualCategoryNew
							.getAccrualCategory(), accrualCategoryNew);
				}
			}
			List<BusinessObject> objectListWithoutHistory = new ArrayList<BusinessObject>();
			objectListWithoutHistory.addAll(objectsWithoutHistory.values());
			return objectListWithoutHistory;
		}
		return objectList;
	}
}
