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
import org.kuali.hr.time.util.TKUser;
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
		
		boolean systemAdmin = TKUser.isSystemAdmin();
		boolean locationAdmin = TKUser.getLocationAdminAreas().contains(location);
		boolean departmentAdmin = TKUser.getDepartmentAdminAreas().contains(department);
		
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
	
	@Override
	public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {
		String dept = fieldValues.get("dept");
		String workArea = fieldValues.get("workArea");
		String description = fieldValues.get("description");
		String fromEffdt = TKUtils.getFromDateString(fieldValues.get("effectiveDate"));
		String toEffdt = TKUtils.getToDateString(fieldValues.get("effectiveDate"));
		String active = fieldValues.get("active");
		String showHist = fieldValues.get("history");
		
		if (StringUtils.contains(workArea, "%")) {
			workArea = "";
		}
		
		return TkServiceLocator.getWorkAreaService().getWorkAreas(dept, workArea, description, TKUtils.formatDateString(fromEffdt), 
				TKUtils.formatDateString(toEffdt), active, showHist);
	}

}