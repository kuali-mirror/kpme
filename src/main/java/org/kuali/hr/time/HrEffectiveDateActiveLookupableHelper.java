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
package org.kuali.hr.time;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;
import org.kuali.rice.kns.lookup.LookupUtils;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.lookup.CollectionIncomplete;

public abstract class HrEffectiveDateActiveLookupableHelper extends KualiLookupableHelperServiceImpl{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * Core HR Effective dating lookup logic
	 * alter at your own risk
	 */
	public List<? extends BusinessObject> getSearchResults(
			Map<String, String> fieldValues) {
		if (fieldValues.containsKey("workArea")
				&& StringUtils.equals(fieldValues.get("workArea"), "%")) {
			fieldValues.put("workArea", "");
		}
		if (fieldValues.containsKey("jobNumber")
				&& StringUtils.equals(fieldValues.get("jobNumber"), "%")) {
			fieldValues.put("jobNumber", "");
		}
		if (fieldValues.containsKey("dept")
				&& StringUtils.equals(fieldValues.get("dept"), "%")) {
			fieldValues.put("dept", "");
		}
		if (fieldValues.containsKey("principalId")
				&& StringUtils.equals(fieldValues.get("principalId"), "%")) {
			fieldValues.put("principalId", "");
		}		
		String showHistory = "Y";
		if (fieldValues.containsKey("history")) {
			showHistory = fieldValues.get("history");
			fieldValues.remove("history");
		}
		String active = "";
		if(fieldValues.containsKey("active")){
			active = fieldValues.get("active");
			fieldValues.put("active", "");
		}
		
		List<HrBusinessObject> hrObjList = (List<HrBusinessObject>)super.getSearchResults(fieldValues);
		//Create a principalId+jobNumber map as this is the unique key for results
		Map<String,List<HrBusinessObject>> hrBusinessMap = new HashMap<String,List<HrBusinessObject>>();
		
		for(HrBusinessObject hrObj : hrObjList){
			String key = hrObj.getUniqueKey();
			
			//If no key exists for this business object return full collection
			if(StringUtils.isEmpty(key)){
				return hrObjList;
			}
			if(hrBusinessMap.get(key)!= null){
				List<HrBusinessObject> lstHrBusinessList = hrBusinessMap.get(key);
				lstHrBusinessList.add(hrObj);
			} else {
				List<HrBusinessObject> lstHrBusinessObj = new ArrayList<HrBusinessObject>();
				lstHrBusinessObj.add(hrObj);
				hrBusinessMap.put(key, lstHrBusinessObj);
			}
		}
		
		List<BusinessObject> finalBusinessObjectList = new ArrayList<BusinessObject>();
		
		for(List<HrBusinessObject> lstHrBusinessObj: hrBusinessMap.values()){
			Collections.sort(lstHrBusinessObj, new EffectiveDateTimestampCompare());
			Collections.reverse(lstHrBusinessObj);
		}
		
		
		Date currDate = TKUtils.getCurrentDate();
		//Active = Both and Show History = Yes
		//return all results
		if(StringUtils.isEmpty(active) && StringUtils.equals("Y", showHistory)){
			return hrObjList;
		} 
		//Active = Both and show history = No
		//return the most effective results from today and any future rows
		else if(StringUtils.isEmpty(active) && StringUtils.equals("N", showHistory)){
			for(List<HrBusinessObject> lstHrBusiness : hrBusinessMap.values()){
				for(HrBusinessObject hrBus : lstHrBusiness){
					if(hrBus.getEffectiveDate().before(currDate)){
						finalBusinessObjectList.add(hrBus);
						break;
					} else {
						finalBusinessObjectList.add(hrBus);
					}
				}
			}
		}
		//Active = Yes and Show History = No
		//return all active records as of today and any active future rows
		//if there is an inactive record before the active one then do not show the results as this record is inactive
		else if(StringUtils.equals(active, "Y") && StringUtils.equals("N", showHistory)){
			for(List<HrBusinessObject> lstHrBus : hrBusinessMap.values()){
				for(HrBusinessObject hrBusinessObject : lstHrBus){
					if(!hrBusinessObject.isActive() && hrBusinessObject.getEffectiveDate().before(currDate)){
						break;
					}
					else {
						if(hrBusinessObject.getEffectiveDate().before(currDate)){
							finalBusinessObjectList.add(hrBusinessObject);
							break;
						} else {
							if(hrBusinessObject.isActive()){
								finalBusinessObjectList.add(hrBusinessObject);
							}
						}
					}
				}
			}			
		}
		//Active = Yes and Show History = Yes
		//return all active records from database
		//if there is an inactive record before the active one then do not show the results as this record is inactive
		else if(StringUtils.equals(active, "Y") && StringUtils.equals("Y", showHistory)){
			for(List<HrBusinessObject> lstHrBus : hrBusinessMap.values()){
				for(HrBusinessObject hrBus : lstHrBus){
					if(!hrBus.isActive() && hrBus.getEffectiveDate().before(currDate)){
						break;
					}
					else if(hrBus.isActive()){
						finalBusinessObjectList.add(hrBus);			
					}
				}
			}
		}
		//Active = No and Show History = Yes
		//return all inactive records in the database
		else if(StringUtils.equals(active, "N") && StringUtils.equals(showHistory, "Y")){
			for(List<HrBusinessObject> lstHrBus : hrBusinessMap.values()){
				for(HrBusinessObject hrBus : lstHrBus){
					if(!hrBus.isActive()){
						finalBusinessObjectList.add(hrBus);	
					}
				}
			}
		}
		//Active = No and Show History = No
		//return the most effective inactive rows if there are no active rows <= the curr date
		else if(StringUtils.equals(active, "N") && StringUtils.equals(showHistory, "N")){
			for(List<HrBusinessObject> lstHrBusiness : hrBusinessMap.values()){
				for(HrBusinessObject hrBus : lstHrBusiness){
					if(hrBus.getEffectiveDate().before(currDate)){
						if(!hrBus.isActive()){
							finalBusinessObjectList.add(hrBus);
						} 
						break;
					} else {
						if(!hrBus.isActive()){
							finalBusinessObjectList.add(hrBus);
						}
					}
				}
			}
		}
		
		Integer searchResultsLimit = LookupUtils.getSearchResultsLimit(businessObjectClass);

		Long matchingResultsCount = Long.valueOf(finalBusinessObjectList.size());

		if (matchingResultsCount.intValue() <= searchResultsLimit.intValue()) {

		matchingResultsCount = new Long(0);

		}

		return new CollectionIncomplete(finalBusinessObjectList, matchingResultsCount);

	}
	@SuppressWarnings("rawtypes")
	public class EffectiveDateTimestampCompare implements Comparator{

		@Override
		public int compare(Object arg0, Object arg1) {
            HrBusinessObject hrBusinessObject = (HrBusinessObject)arg0;
			HrBusinessObject hrBusinessObject2 = (HrBusinessObject)arg1;

            java.sql.Date effDate1 = hrBusinessObject.getEffectiveDate();
            java.sql.Date effDate2 = hrBusinessObject2.getEffectiveDate();
            if (effDate1 == null ^ effDate2 == null) {
                return (effDate1 == null) ? -1 : 1;
            }
            if (effDate1 == null && effDate2 == null) {
                return 0;
            }
			int result = hrBusinessObject.getEffectiveDate().compareTo(hrBusinessObject2.getEffectiveDate());
			if(result==0){
				return hrBusinessObject.getTimestamp().compareTo(hrBusinessObject2.getTimestamp());
			}
			return result;
		}
		
	}
	@Override
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject,
			@SuppressWarnings("rawtypes") List pkNames) {
		List<HtmlData> customActionUrls = super.getCustomActionUrls(businessObject, pkNames);
		List<HtmlData> overrideUrls = new ArrayList<HtmlData>();
		for(HtmlData actionUrl : customActionUrls){
			if(!StringUtils.equals(actionUrl.getMethodToCall(), "copy")){
				overrideUrls.add(actionUrl);
			}

		}
		return overrideUrls;
	}
	
	
	
 
}
