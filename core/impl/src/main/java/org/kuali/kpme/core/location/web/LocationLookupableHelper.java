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
package org.kuali.kpme.core.location.web;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.kuali.kpme.core.KPMENamespace;
import org.kuali.kpme.core.location.Location;
import org.kuali.kpme.core.lookup.KPMELookupableHelper;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;

@SuppressWarnings("deprecation")
public class LocationLookupableHelper extends KPMELookupableHelper {

	private static final long serialVersionUID = 1285833127534968764L;

	@Override
	@SuppressWarnings("rawtypes")
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject, List pkNames) {
		List<HtmlData> customActionUrls = super.getCustomActionUrls(businessObject, pkNames);

		Location locationObj = (Location) businessObject;
		String hrLocationId = locationObj.getHrLocationId();
		String principalId = GlobalVariables.getUserSession().getPrincipalId();
        boolean isSysAdmin = HrServiceLocator.getKPMEGroupService().isMemberOfSystemAdministratorGroup(principalId, new DateTime());
        boolean isTimeLocationAdmin = HrServiceLocator.getKPMERoleService().principalHasRole(principalId, KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_LOCATION_ADMINISTRATOR.getRoleName(), new DateTime());
        boolean isTimeSysAdmin = HrServiceLocator.getKPMERoleService().principalHasRole(principalId, KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_SYSTEM_ADMINISTRATOR.getRoleName(), new DateTime());
        boolean isLeaveLocationAdmin = HrServiceLocator.getKPMERoleService().principalHasRole(principalId, KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_LOCATION_ADMINISTRATOR.getRoleName(), new DateTime());
        boolean isLeaveSysAdmin = HrServiceLocator.getKPMERoleService().principalHasRole(principalId, KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_SYSTEM_ADMINISTRATOR.getRoleName(), new DateTime());
        List<Location> newerVersion = HrServiceLocator.getLocationService().getNewerVersionLocation(locationObj.getLocation(),locationObj.getEffectiveLocalDate());

        if (newerVersion.size() > 0 || !locationObj.isActive()) {
            if (!(isTimeLocationAdmin || isTimeSysAdmin || isLeaveLocationAdmin || isLeaveSysAdmin || isSysAdmin)) {
                for (HtmlData action : customActionUrls) {
                    if (StringUtils.equals(action.getMethodToCall(),"edit")) {
                        customActionUrls.remove(action);
                        break;
                    }
                }
            }

        }
		Properties params = new Properties();
		params.put(KRADConstants.BUSINESS_OBJECT_CLASS_ATTRIBUTE, getBusinessObjectClass().getName());
		params.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.MAINTENANCE_NEW_METHOD_TO_CALL);
		params.put("hrLocationId", hrLocationId);
		AnchorHtmlData viewUrl = new AnchorHtmlData(UrlFactory.parameterizeUrl(KRADConstants.INQUIRY_ACTION, params), "view");
		viewUrl.setDisplayText("view");
		viewUrl.setTarget(AnchorHtmlData.TARGET_BLANK);
		customActionUrls.add(viewUrl);
		
		return customActionUrls;
	}

    @Override
    public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {
        String location = fieldValues.get("location");
        String descr = fieldValues.get("description");
        String active = fieldValues.get("active");
        String showHist = fieldValues.get("history");

        if (StringUtils.contains(location, "*")) {
        	location = "";
		}
		
        return HrServiceLocator.getLocationService().searchLocations(GlobalVariables.getUserSession().getPrincipalId(), location, descr, active, showHist);
    }
    
    @Override
	protected void validateSearchParameterWildcardAndOperators(
			String attributeName, String attributeValue) {
		if (!StringUtils.equals(attributeValue, "*")) {
			super.validateSearchParameterWildcardAndOperators(attributeName,
					attributeValue);
		}
	}
    
}