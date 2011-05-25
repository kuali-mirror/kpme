package org.kuali.hr.time.assignment.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.AssignmentDescriptionKey;
import org.kuali.hr.time.util.TKContext;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;

public class AssignmentLookupableHelper extends
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
			Assignment assignment = (Assignment) businessObject;
			final String className = this.getBusinessObjectClass().getName();
			final Long tkAssignmentId = assignment.getTkAssignmentId();
			HtmlData htmlData = new HtmlData() {

				@Override
				public String constructCompleteHtmlTag() {
					return "<a target=\"_blank\" href=\"inquiry.do?businessObjectClassName="
							+ className
							+ "&methodToCall=start&tkAssignmentId="
							+ tkAssignmentId
							+ "&principalId=&jobNumber=\">view</a>";
				}
			};
			customActionUrls.add(htmlData);
		} else if (customActionUrls.size() != 0) {
			customActionUrls.remove(0);
		}
		return customActionUrls;
	}
	
	@Override
	public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {
		String showHistory = "Y";
		if (fieldValues.containsKey("history")) {
			showHistory = fieldValues.get("history");
			fieldValues.remove("history");
		}
		List<? extends BusinessObject> objectList = super.getSearchResults(fieldValues);
		if (!objectList.isEmpty() && StringUtils.equals(showHistory, "N")) {
			Map<String, BusinessObject> objectsWithoutHistory = new HashMap<String, BusinessObject>();
			// Creating map for objects without history
			for (BusinessObject bo : objectList) {
				Assignment assignNew = (Assignment) bo;
				AssignmentDescriptionKey assignKey = new AssignmentDescriptionKey(assignNew.getJobNumber(), assignNew.getWorkArea(), assignNew.getTask());
				String assignmentKey = assignNew.getPrincipalId() + "_" + assignKey.toAssignmentKeyString();
				if (objectsWithoutHistory.containsKey(assignKey)) {
					// Comparing here for duplicates
					Assignment assignOld = (Assignment) objectsWithoutHistory.get(assignmentKey);
					int comparison = assignNew.getEffectiveDate().compareTo(assignOld.getEffectiveDate());
					// Comparison for highest effective date object to put 
					switch (comparison) {
					case 0:
						if (assignNew.getTimestamp().after(assignOld.getTimestamp())) {
							// Sorting here by timestamp value
							objectsWithoutHistory.put(assignmentKey, assignNew);
						}
						break;
					case 1:
						objectsWithoutHistory.put(assignmentKey, assignNew);
					}
				} else {
					objectsWithoutHistory.put(assignmentKey, assignNew);
				}
			}
			List<BusinessObject> objectListWithoutHistory = new ArrayList<BusinessObject>();
			objectListWithoutHistory.addAll(objectsWithoutHistory.values());
			return objectListWithoutHistory;
		}
		return objectList;
	}

}
