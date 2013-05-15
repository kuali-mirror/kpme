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
package org.kuali.kpme.tklm.leave.override.web;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.kuali.kpme.core.bo.HrEffectiveDateActiveLookupableHelper;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.leave.override.EmployeeOverride;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;

public class EmployeeOverrideLookupableHelper extends HrEffectiveDateActiveLookupableHelper  {

	private static final long serialVersionUID = -2208016099188014844L;

	@Override
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject, List pkNames) {
		List<HtmlData> customActionUrls = super.getCustomActionUrls(businessObject, pkNames);
		
		EmployeeOverride employeeOverride = (EmployeeOverride) businessObject;
		String lmEmployeeOverrideId = employeeOverride.getLmEmployeeOverrideId();
		
		Properties params = new Properties();
		params.put(KRADConstants.BUSINESS_OBJECT_CLASS_ATTRIBUTE, getBusinessObjectClass().getName());
		params.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.MAINTENANCE_NEW_METHOD_TO_CALL);
		params.put("lmEmployeeOverrideId", lmEmployeeOverrideId);
		AnchorHtmlData viewUrl = new AnchorHtmlData(UrlFactory.parameterizeUrl(KRADConstants.INQUIRY_ACTION, params), "view");
		viewUrl.setDisplayText("view");
		viewUrl.setTarget(AnchorHtmlData.TARGET_BLANK);
		customActionUrls.add(viewUrl);
		
		return customActionUrls;
	}

    @Override
    public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {
        String principalId = fieldValues.get("principalId");
        String leavePlan = fieldValues.get("leavePlan");
        String accrualCategory = fieldValues.get("accrualCategory");
        String overrideType = fieldValues.get("overrideType");
        String fromEffdt = TKUtils.getFromDateString(fieldValues.get("effectiveDate"));
        String toEffdt = TKUtils.getToDateString(fieldValues.get("effectiveDate"));
        String active = fieldValues.get("active");

        return LmServiceLocator.getEmployeeOverrideService().getEmployeeOverrides(principalId, leavePlan, accrualCategory, overrideType, 
        		TKUtils.formatDateString(fromEffdt), TKUtils.formatDateString(toEffdt), active);
    }
	
}