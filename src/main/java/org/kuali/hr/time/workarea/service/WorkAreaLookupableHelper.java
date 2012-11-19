/**
 * Copyright 2004-2012 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.hr.time.workarea.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.authorization.DepartmentalRule;
import org.kuali.hr.time.authorization.DepartmentalRuleAuthorizer;
import org.kuali.hr.time.authorization.TkAuthorizedLookupableHelperBase;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kns.document.authorization.BusinessObjectRestrictions;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.kns.web.struts.form.LookupForm;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;

public class WorkAreaLookupableHelper extends TkAuthorizedLookupableHelperBase {

    /**
     * Implemented method to reduce the set of Business Objects that are shown
     * to the user based on their current roles.
     */
    public boolean shouldShowBusinessObject(BusinessObject bo) {
        return (bo instanceof DepartmentalRule) && DepartmentalRuleAuthorizer.hasAccessToRead((DepartmentalRule)bo);
    }

	@Override
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject, List pkNames) {
		List<HtmlData> customActionUrls = new ArrayList<HtmlData>();
		
		List<HtmlData> defaultCustomActionUrls = super.getCustomActionUrls(businessObject, pkNames);
		
		WorkArea workArea = (WorkArea) businessObject;
		String tkWorkAreaId = workArea.getTkWorkAreaId();
		String location = TkServiceLocator.getDepartmentService().getDepartment(workArea.getDept(), TKUtils.getCurrentDate()).getLocation();
		String department = workArea.getDept();
		
		boolean systemAdmin = TKContext.getUser().isSystemAdmin();
		boolean locationAdmin = TKContext.getUser().getLocationAdminAreas().contains(location);
		boolean departmentAdmin = TKContext.getUser().getDepartmentAdminAreas().contains(department);
		
		for (HtmlData defaultCustomActionUrl : defaultCustomActionUrls){
			if (StringUtils.equals(defaultCustomActionUrl.getMethodToCall(), "edit")) {
				if (systemAdmin || locationAdmin || departmentAdmin) {
					customActionUrls.add(defaultCustomActionUrl);
				}
			} else {
				customActionUrls.add(defaultCustomActionUrl);
			}
		}
		
		Properties params = new Properties();
		params.put(KRADConstants.BUSINESS_OBJECT_CLASS_ATTRIBUTE, getBusinessObjectClass().getName());
		params.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.MAINTENANCE_NEW_METHOD_TO_CALL);
		params.put("tkWorkAreaId", tkWorkAreaId);
		AnchorHtmlData viewUrl = new AnchorHtmlData(UrlFactory.parameterizeUrl(KRADConstants.INQUIRY_ACTION, params), "view");
		viewUrl.setDisplayText("view");
		viewUrl.setTarget(AnchorHtmlData.TARGET_BLANK);
		customActionUrls.add(viewUrl);
		
		return customActionUrls;
	}

	@Override
	public HtmlData getReturnUrl(BusinessObject businessObject,
			LookupForm lookupForm, List returnKeys,
			BusinessObjectRestrictions businessObjectRestrictions) {
		if (lookupForm.getFieldConversions().containsKey("effectiveDate")) {
			lookupForm.getFieldConversions().remove("effectiveDate");
		}
		if (returnKeys.contains("effectiveDate")) {
			returnKeys.remove("effectiveDate");
		}
		if (lookupForm.getFieldConversions().containsKey("dept")) {
			lookupForm.getFieldConversions().remove("dept");
		}
		if (returnKeys.contains("dept")) {
			returnKeys.remove("dept");
		}
		
		if(lookupForm.getFieldConversions().containsKey("tkWorkAreaId")){
			lookupForm.getFieldConversions().remove("tkWorkAreaId");
		}
		if(returnKeys.contains("tkWorkAreaId")){
			returnKeys.remove("tkWorkAreaId");
		}
		return super.getReturnUrl(businessObject, lookupForm, returnKeys,
				businessObjectRestrictions);
	}
	


	@Override
	protected void validateSearchParameterWildcardAndOperators(
			String attributeName, String attributeValue) {
		if (!StringUtils.equals(attributeValue, "%")) {
			super.validateSearchParameterWildcardAndOperators(attributeName,
					attributeValue);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	/**
	 * Custom work area search logic
	 * 
	 */
	public List<? extends BusinessObject> getSearchResults(
			Map<String, String> fieldValues) {
		
		String dept = fieldValues.get("dept");
		String workArea = fieldValues.get("workArea");
		String descr = fieldValues.get("description");
		String fromEffdt = fieldValues.get("rangeLowerBoundKeyPrefix_effectiveDate");
		String toEffdt = TKUtils.getToDateString(fieldValues.get("effectiveDate"));
		
		String active = fieldValues.get("active");
		String showHist = fieldValues.get("history");
		
		if (StringUtils.contains(workArea, "%")) {
			workArea = "";
		}
		
		if(workArea != null && !workArea.isEmpty()) {
			try{
				Long workarea = Long.parseLong(workArea);
			}catch(NumberFormatException nfe) {
				workArea = "-1";
			}
		}
		
		return TkServiceLocator.getWorkAreaService().getWorkAreas(dept, workArea, descr, TKUtils.formatDateString(fromEffdt), 
													TKUtils.formatDateString(toEffdt), active, showHist);
		
//		if (fieldValues.containsKey("jobNumber")
//				&& StringUtils.equals(fieldValues.get("jobNumber"), "%")) {
//			fieldValues.put("jobNumber", "");
//		}
//		
		
//		if (fieldValues.containsKey("principalId")
//				&& StringUtils.equals(fieldValues.get("principalId"), "%")) {
//			fieldValues.put("principalId", "");
//		}		
//		String showHistory = "Y";
//		if (fieldValues.containsKey("history")) {
//			showHistory = fieldValues.get("history");
//			fieldValues.remove("history");
//		}
//		String active = "";
//		if(fieldValues.containsKey("active")){
//			active = fieldValues.get("active");
//			fieldValues.put("active", "");
//		}
//		
//		List<HrBusinessObject> hrObjList = (List<HrBusinessObject>)super.getSearchResults(fieldValues);
//		//Create a principalId+jobNumber map as this is the unique key for results
//		Map<String,List<HrBusinessObject>> hrBusinessMap = new HashMap<String,List<HrBusinessObject>>();
//		
//		for(HrBusinessObject hrObj : hrObjList){
//			String key = hrObj.getUniqueKey();
//			
//			//If no key exists for this business object return full collection
//			if(StringUtils.isEmpty(key)){
//				return hrObjList;
//			}
//			if(hrBusinessMap.get(key)!= null){
//				List<HrBusinessObject> lstHrBusinessList = hrBusinessMap.get(key);
//				lstHrBusinessList.add(hrObj);
//			} else {
//				List<HrBusinessObject> lstHrBusinessObj = new ArrayList<HrBusinessObject>();
//				lstHrBusinessObj.add(hrObj);
//				hrBusinessMap.put(key, lstHrBusinessObj);
//			}
//		}
//		
//		List<BusinessObject> finalBusinessObjectList = new ArrayList<BusinessObject>();
//		
//		for(List<HrBusinessObject> lstHrBusinessObj: hrBusinessMap.values()){
//			Collections.sort(lstHrBusinessObj, new EffectiveDateTimestampCompare());
//			Collections.reverse(lstHrBusinessObj);
//		}
//		
//		
//		Date currDate = TKUtils.getCurrentDate();
//		//Active = Both and Show History = Yes
//		//return all results
//		if(StringUtils.isEmpty(active) && StringUtils.equals("Y", showHistory)){
//			return hrObjList;
//		} 
//		//Active = Both and show history = No
//		//return the most effective results from today and any future rows
//		else if(StringUtils.isEmpty(active) && StringUtils.equals("N", showHistory)){
//			for(List<HrBusinessObject> lstHrBusiness : hrBusinessMap.values()){
//				for(HrBusinessObject hrBus : lstHrBusiness){
//					if(hrBus.getEffectiveDate().before(currDate)){
//						finalBusinessObjectList.add(hrBus);
//						break;
//					} else {
//						finalBusinessObjectList.add(hrBus);
//					}
//				}
//			}
//		}
//		//Active = Yes and Show History = No
//		//return all active records as of today and any active future rows
//		//if there is an inactive record before the active one then do not show the results as this record is inactive
//		else if(StringUtils.equals(active, "Y") && StringUtils.equals("N", showHistory)){
//			for(List<HrBusinessObject> lstHrBus : hrBusinessMap.values()){
//				for(HrBusinessObject hrBusinessObject : lstHrBus){
//					if(!hrBusinessObject.isActive() && hrBusinessObject.getEffectiveDate().before(currDate)){
//						break;
//					}
//					else {
//						if(hrBusinessObject.getEffectiveDate().before(currDate)){
//							finalBusinessObjectList.add(hrBusinessObject);
//							break;
//						} else {
//							if(hrBusinessObject.isActive()){
//								finalBusinessObjectList.add(hrBusinessObject);
//							}
//						}
//					}
//				}
//			}			
//		}
//		//Active = Yes and Show History = Yes
//		//return all active records from database
//		//if there is an inactive record before the active one then do not show the results as this record is inactive
//		else if(StringUtils.equals(active, "Y") && StringUtils.equals("Y", showHistory)){
//			for(List<HrBusinessObject> lstHrBus : hrBusinessMap.values()){
//				for(HrBusinessObject hrBus : lstHrBus){
//					if(!hrBus.isActive() && hrBus.getEffectiveDate().before(currDate)){
//						break;
//					}
//					else if(hrBus.isActive()){
//						finalBusinessObjectList.add(hrBus);			
//					}
//				}
//			}
//		}
//		//Active = No and Show History = Yes
//		//return all inactive records in the database
//		else if(StringUtils.equals(active, "N") && StringUtils.equals(showHistory, "Y")){
//			for(List<HrBusinessObject> lstHrBus : hrBusinessMap.values()){
//				for(HrBusinessObject hrBus : lstHrBus){
//					if(!hrBus.isActive()){
//						finalBusinessObjectList.add(hrBus);	
//					}
//				}
//			}
//		}
//		//Active = No and Show History = No
//		//return the most effective inactive rows if there are no active rows <= the curr date
//		else if(StringUtils.equals(active, "N") && StringUtils.equals(showHistory, "N")){
//			for(List<HrBusinessObject> lstHrBusiness : hrBusinessMap.values()){
//				for(HrBusinessObject hrBus : lstHrBusiness){
//					if(hrBus.getEffectiveDate().before(currDate)){
//						if(!hrBus.isActive()){
//							finalBusinessObjectList.add(hrBus);
//						} 
//						break;
//					} else {
//						if(!hrBus.isActive()){
//							finalBusinessObjectList.add(hrBus);
//						}
//					}
//				}
//			}
//		}
//		
//		Integer searchResultsLimit = LookupUtils.getSearchResultsLimit(businessObjectClass);
//
//		Long matchingResultsCount = Long.valueOf(finalBusinessObjectList.size());
//
//		if (matchingResultsCount.intValue() <= searchResultsLimit.intValue()) {
//
//		matchingResultsCount = new Long(0);
//
//		}
//
//		return new CollectionIncomplete(finalBusinessObjectList, matchingResultsCount);

	}



	
}
