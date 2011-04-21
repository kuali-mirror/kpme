package org.kuali.hr.time.dept.earncode.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.dept.earncode.DepartmentEarnCode;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.util.TKContext;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;

public class DepartmentEarnCodeLookupableHelper extends
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
			DepartmentEarnCode departmentEarnCode = (DepartmentEarnCode) businessObject;
			final String className = this.getBusinessObjectClass().getName();
			final Long tkDeptEarnCodeId = departmentEarnCode
					.getTkDeptEarnCodeId();
			HtmlData htmlData = new HtmlData() {

				@Override
				public String constructCompleteHtmlTag() {
					return "<a target=\"_blank\" href=\"inquiry.do?businessObjectClassName="
							+ className
							+ "&methodToCall=start&tkDeptEarnCodeId="
							+ tkDeptEarnCodeId + "\">view</a>";
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
				DepartmentEarnCode departmentEarnCodeNew = (DepartmentEarnCode) bo;
				if (objectsWithoutHistory.containsKey(departmentEarnCodeNew
						.getDept()
						+ departmentEarnCodeNew.getEarnCode()
						+ departmentEarnCodeNew.getLocation())) {
					// Comparing here for duplicates
					DepartmentEarnCode departmentEarnCodeOld = (DepartmentEarnCode) objectsWithoutHistory
							.get(departmentEarnCodeNew.getDept()
									+ departmentEarnCodeNew.getEarnCode()
									+ departmentEarnCodeNew.getLocation());
					int comparison = departmentEarnCodeNew
							.getEffectiveDate()
							.compareTo(departmentEarnCodeOld.getEffectiveDate());
					// Comparison for highest effective date object to put 
					switch (comparison) {
					case 0:
						if (departmentEarnCodeNew.getTimestamp().after(
								departmentEarnCodeOld.getTimestamp())) {
							// Sorting here by timestamp value
							objectsWithoutHistory.put(departmentEarnCodeNew
									.getDept()
									+ departmentEarnCodeNew.getEarnCode()
									+ departmentEarnCodeNew.getLocation(),
									departmentEarnCodeNew);
						}
						break;
					case 1:
						objectsWithoutHistory.put(departmentEarnCodeNew
								.getDept()
								+ departmentEarnCodeNew.getEarnCode()
								+ departmentEarnCodeNew.getLocation(),
								departmentEarnCodeNew);
					}
				} else {
					objectsWithoutHistory.put(departmentEarnCodeNew.getDept()
							+ departmentEarnCodeNew.getEarnCode()
							+ departmentEarnCodeNew.getLocation(),
							departmentEarnCodeNew);
				}
			}
			List<BusinessObject> objectListWithoutHistory = new ArrayList<BusinessObject>();
			objectListWithoutHistory.addAll(objectsWithoutHistory.values());
			return objectListWithoutHistory;
		}
		return objectList;
	}
}
