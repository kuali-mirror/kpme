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
package org.kuali.kpme.tklm.time.rules.lunch.department.service;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.tklm.time.authorization.AuthorizationValidationUtils;
import org.kuali.kpme.tklm.time.authorization.DepartmentalRule;
import org.kuali.kpme.tklm.time.authorization.TkAuthorizedLookupableHelperBase;
import org.kuali.kpme.tklm.time.rules.lunch.department.DeptLunchRule;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;

@SuppressWarnings("deprecation")
public class DepartmentLunchRuleLookupableHelper extends TkAuthorizedLookupableHelperBase {
	
	private static final long serialVersionUID = -6171434403261481651L;

	@Override
    /**
     * Implemented method to reduce the set of Business Objects that are shown
     * to the user based on their current roles.
     */
    public boolean shouldShowBusinessObject(BusinessObject bo) {
        return (bo instanceof DepartmentalRule) && AuthorizationValidationUtils.hasAccessToRead((DepartmentalRule)bo);
    }
    
	@Override
	@SuppressWarnings("rawtypes")
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject, List pkNames) {
		List<HtmlData> customActionUrls = super.getCustomActionUrls(businessObject, pkNames);

		DeptLunchRule deptLunchRule = (DeptLunchRule) businessObject;
		String tkDeptLunchRuleId = deptLunchRule.getTkDeptLunchRuleId();
		
		Properties params = new Properties();
		params.put(KRADConstants.BUSINESS_OBJECT_CLASS_ATTRIBUTE, getBusinessObjectClass().getName());
		params.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.MAINTENANCE_NEW_METHOD_TO_CALL);
		params.put("tkDeptLunchRuleId", tkDeptLunchRuleId);
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

    @Override
    public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {
        String principalId = fieldValues.get("principalId");
        String jobNumber = fieldValues.get("jobNumber");
        String dept = fieldValues.get("dept");
        String workArea = fieldValues.get("workArea");
        String active = fieldValues.get("active");

        if (StringUtils.equals(workArea,"%") || StringUtils.equals(workArea,"*")){
            workArea = "";
        }
        
        return TkServiceLocator.getDepartmentLunchRuleService().getDepartmentLunchRules(dept,
                workArea, principalId, jobNumber, active);
    }
}
