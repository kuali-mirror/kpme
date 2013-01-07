/**
 * Copyright 2004-2013 The Kuali Foundation
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
package org.kuali.hr.time.clock.location.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.authorization.DepartmentalRule;
import org.kuali.hr.time.authorization.DepartmentalRuleAuthorizer;
import org.kuali.hr.time.authorization.TkAuthorizedLookupableHelperBase;
import org.kuali.hr.time.clock.location.ClockLocationRule;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;

public class ClockLocationRuleLookupableHelper extends TkAuthorizedLookupableHelperBase {

	private static final long serialVersionUID = 7261054962204557586L;

	@Override
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
		
		ClockLocationRule clockLocationRule = (ClockLocationRule) businessObject;
		String tkClockLocationRuleId = clockLocationRule.getTkClockLocationRuleId();
        Department dept = TkServiceLocator.getDepartmentService().getDepartment(clockLocationRule.getDept(), TKUtils.getCurrentDate());
		String location = dept == null ? null : dept.getLocation();
        String department = clockLocationRule.getDept();
        
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
		params.put("tkClockLocationRuleId", tkClockLocationRuleId);
		AnchorHtmlData viewUrl = new AnchorHtmlData(UrlFactory.parameterizeUrl(KRADConstants.INQUIRY_ACTION, params), "view");
		viewUrl.setDisplayText("view");
		viewUrl.setTarget(AnchorHtmlData.TARGET_BLANK);
		customActionUrls.add(viewUrl);
		
		return customActionUrls;
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

    public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {
        String fromEffdt = TKUtils.getFromDateString(fieldValues.get("effectiveDate"));
        String toEffdt = TKUtils.getToDateString(fieldValues.get("effectiveDate"));
        String principalId = fieldValues.get("principalId");
        String jobNumber = fieldValues.get("jobNumber");
        String dept = fieldValues.get("dept");
        String workArea = fieldValues.get("workArea");
        String active = fieldValues.get("active");
        String showHist = fieldValues.get("history");

        return TkServiceLocator.getClockLocationRuleService().getClockLocationRules(TKUtils.formatDateString(fromEffdt), TKUtils.formatDateString(toEffdt), 
        		principalId, jobNumber, dept, workArea, active, showHist);
    }
    
}