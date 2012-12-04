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
package org.kuali.hr.lm.timeoff.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.lm.timeoff.SystemScheduledTimeOff;
import org.kuali.hr.time.HrEffectiveDateActiveLookupableHelper;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;

public class SystemScheduledTimeOffLookupableHelper extends HrEffectiveDateActiveLookupableHelper {

	private static final long serialVersionUID = -1285064132683716221L;

	@Override
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject, List pkNames) {
		List<HtmlData> customActionUrls = new ArrayList<HtmlData>();
		
		List<HtmlData> defaultCustomActionUrls = super.getCustomActionUrls(businessObject, pkNames);

		SystemScheduledTimeOff systemScheduledTimeOff = (SystemScheduledTimeOff) businessObject;
		String lmSystemScheduledTimeOffId = systemScheduledTimeOff.getLmSystemScheduledTimeOffId();
		String location = systemScheduledTimeOff.getLocation();
		
		boolean systemAdmin = TKContext.getUser().isSystemAdmin();
		boolean locationAdmin = TKContext.getUser().getLocationAdminAreas().contains(location);

		for (HtmlData defaultCustomActionUrl : defaultCustomActionUrls){
			if (StringUtils.equals(defaultCustomActionUrl.getMethodToCall(), "edit")) {
				if (systemAdmin || locationAdmin) {
					customActionUrls.add(defaultCustomActionUrl);
				}
			} else {
				customActionUrls.add(defaultCustomActionUrl);
			}
		}
		
		Properties params = new Properties();
		params.put(KRADConstants.BUSINESS_OBJECT_CLASS_ATTRIBUTE, getBusinessObjectClass().getName());
		params.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.MAINTENANCE_NEW_METHOD_TO_CALL);
		params.put("lmSystemScheduledTimeOffId", lmSystemScheduledTimeOffId);
		AnchorHtmlData viewUrl = new AnchorHtmlData(UrlFactory.parameterizeUrl(KRADConstants.INQUIRY_ACTION, params), "view");
		viewUrl.setDisplayText("view");
		viewUrl.setTarget(AnchorHtmlData.TARGET_BLANK);
		customActionUrls.add(viewUrl);
		
		return customActionUrls;
	}

    @SuppressWarnings({"unchecked"})
    @Override
    public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {
        String fromEffdt = fieldValues.get("rangeLowerBoundKeyPrefix_effectiveDate");
        String toEffdt = TKUtils.getToDateString(fieldValues.get("effectiveDate"));
        String earnCode = fieldValues.get("earnCode");
        String fromAccruedDate = fieldValues.get("rangeLowerBoundKeyPrefix_accruedDate");
        String toAccruedDate = TKUtils.getToDateString(fieldValues.get("accruedDate"));
        String fromSchTimeOffDate = fieldValues.get("rangeLowerBoundKeyPrefix_scheduledTimeOffDate");
        String toSchTimeOffDate = TKUtils.getToDateString(fieldValues.get("scheduledTimeOffDate"));
        String active = fieldValues.get("active");
        String showHist = fieldValues.get("history");

        List<SystemScheduledTimeOff> sysSchTimeOffs = TkServiceLocator.getSysSchTimeOffService().getSystemScheduledTimeOffs(TKUtils.formatDateString(fromEffdt), TKUtils.formatDateString(toEffdt), earnCode,
                fromAccruedDate, toAccruedDate, fromSchTimeOffDate, toSchTimeOffDate, active, showHist);

        return sysSchTimeOffs;
    }
}