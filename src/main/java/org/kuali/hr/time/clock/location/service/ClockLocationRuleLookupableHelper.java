package org.kuali.hr.time.clock.location.service;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.authorization.DepartmentalRule;
import org.kuali.hr.time.authorization.DepartmentalRuleAuthorizer;
import org.kuali.hr.time.authorization.TkAuthorizedLookupableHelperBase;
import org.kuali.hr.time.clock.location.ClockLocationRule;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.HtmlData;

import edu.emory.mathcs.backport.java.util.Collections;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClockLocationRuleLookupableHelper extends
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
			List pkNames) {
		List<HtmlData> customActionUrls = super.getCustomActionUrls(
				businessObject, pkNames);
		ClockLocationRule clockLocationRule = (ClockLocationRule) businessObject;
		final String className = this.getBusinessObjectClass().getName();
		final Long tkClockLocationRuleId = clockLocationRule.getTkClockLocationRuleId();
		HtmlData htmlData = new HtmlData() {

			@Override
			public String constructCompleteHtmlTag() {
				return "<a target=\"_blank\" href=\"inquiry.do?businessObjectClassName="
						+ className + "&methodToCall=start&tkClockLocationRuleId=" + tkClockLocationRuleId
						+ "\">view</a>";
			}
		};
		customActionUrls.add(htmlData);
		return customActionUrls;
	}

	@Override
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
		
		List<ClockLocationRule> clockLocationList = (List<ClockLocationRule>)super.getSearchResults(fieldValues);
		//Create a principalId+jobNumber map as this is the unique key for results
		Map<String,List<ClockLocationRule>> clockLocationMap = new HashMap<String,List<ClockLocationRule>>();
		
		for(ClockLocationRule clockLocationRule : clockLocationList){
			String clockLocKey = clockLocationRule.getDept()+"_"+clockLocationRule.getIpAddress()+"_"+clockLocationRule.getPrincipalId()+"_"+
									(clockLocationRule.getJobNumber()!=null ? clockLocationRule.getJobNumber().toString(): "") +"_" + 
									(clockLocationRule.getWorkArea() !=null ? clockLocationRule.getWorkArea().toString() : "");
			if(clockLocationMap.get(clockLocKey)!=null){
				List<ClockLocationRule> lstClockLocationRules = clockLocationMap.get(clockLocKey);
				lstClockLocationRules.add(clockLocationRule);
			} else {
				List<ClockLocationRule> lstClockLocationRules = new ArrayList<ClockLocationRule>();
				lstClockLocationRules.add(clockLocationRule);
				clockLocationMap.put(clockLocKey, lstClockLocationRules);
			}
		}
		
		List<BusinessObject> finalBusinessObjectList = new ArrayList<BusinessObject>();
		
		for(List<ClockLocationRule> lstClockLocation : clockLocationMap.values()){
			Collections.sort(lstClockLocation, new EffectiveDateTimestampCompare());
			Collections.reverse(lstClockLocation);
		}
		
		Date currDate = TKUtils.getCurrentDate();
		//Active = Both and Show History = Yes
		//return all results
		if(StringUtils.isEmpty(active) && StringUtils.equals("Y", showHistory)){
			return clockLocationList;
		} 
		//Active = Both and show history = No
		//return the most effective results from today and any future rows
		else if(StringUtils.isEmpty(active) && StringUtils.equals("N", showHistory)){
			for(List<ClockLocationRule> lstClockLocationRules : clockLocationMap.values()){
				for(ClockLocationRule clr : lstClockLocationRules){
					if(clr.getEffectiveDate().before(currDate)){
						finalBusinessObjectList.add(clr);
						break;
					} else {
						finalBusinessObjectList.add(clr);
					}
				}
			}
		}
		//Active = Yes and Show History = No
		//return all active records as of today and any active future rows
		else if(StringUtils.equals(active, "Y") && StringUtils.equals("N", showHistory)){
			for(List<ClockLocationRule> lstClr : clockLocationMap.values()){
				for(ClockLocationRule clockLocationRule : lstClr){
					if(clockLocationRule.isActive()){
						if(clockLocationRule.getEffectiveDate().before(currDate)){
							finalBusinessObjectList.add(clockLocationRule);
							break;
						} else {
							finalBusinessObjectList.add(clockLocationRule);
						}
					}
				}
			}			
		}
		//Active = Yes and Show History = Yes
		//return all active records from database
		else if(StringUtils.equals(active, "Y") && StringUtils.equals("Y", showHistory)){
			for(List<ClockLocationRule> lstClr : clockLocationMap.values()){
				for(ClockLocationRule clr : lstClr){
					if(clr.isActive()){
						finalBusinessObjectList.add(clr);			
					}
				}
			}
		}
		//Active = No and Show History = Yes
		//return all inactive records in the database
		else if(StringUtils.equals(active, "N") && StringUtils.equals(showHistory, "Y")){
			for(List<ClockLocationRule> lstClr : clockLocationMap.values()){
				for(ClockLocationRule clr : lstClr){
					if(!clr.isActive()){
						finalBusinessObjectList.add(clr);	
					}
				}
			}
		}
		//Active = No and Show History = No
		//return the most effective inactive rows if there are no active rows <= the curr date
		else if(StringUtils.equals(active, "N") && StringUtils.equals(showHistory, "N")){
			for(List<ClockLocationRule> lstClr : clockLocationMap.values()){
				for(ClockLocationRule clr : lstClr){
					if(clr.getEffectiveDate().before(currDate)){
						if(!clr.isActive()){
							finalBusinessObjectList.add(clr);
						} 
						break;
					} else {
						if(!clr.isActive()){
							finalBusinessObjectList.add(clr);
						}
					}
				}
			}
		}
		
		return finalBusinessObjectList;
	}

	@Override
	protected void validateSearchParameterWildcardAndOperators(
			String attributeName, String attributeValue) {
		if (!StringUtils.equals(attributeValue, "%")) {
			super.validateSearchParameterWildcardAndOperators(attributeName,
					attributeValue);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public class EffectiveDateTimestampCompare implements Comparator{

		@Override
		public int compare(Object arg0, Object arg1) {
			ClockLocationRule clockLocationRule = (ClockLocationRule)arg0;
			ClockLocationRule clockLocationRule2 = (ClockLocationRule)arg1;
			int result = clockLocationRule.getEffectiveDate().compareTo(clockLocationRule2.getEffectiveDate());
			if(result==0){
				return clockLocationRule.getTimestamp().compareTo(clockLocationRule2.getTimestamp());
			}
			return result;
		}
		
	}
}
