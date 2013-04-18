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
package org.kuali.hr.time.calendar.service;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.kuali.hr.core.lookup.KPMELookupableHelper;
import org.kuali.hr.time.calendar.Calendar;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;

@SuppressWarnings("deprecation")
public class CalendarLookupableHelper extends KPMELookupableHelper {

	private static final long serialVersionUID = 2324703412211619217L;

	@Override
	@SuppressWarnings("rawtypes")
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject, List pkNames) {
		List<HtmlData> customActionUrls = super.getCustomActionUrls(businessObject, pkNames);
		
		Calendar calendar = (Calendar) businessObject;
		String hrCalendarId = calendar.getHrCalendarId();
		
		Properties params = new Properties();
		params.put(KRADConstants.BUSINESS_OBJECT_CLASS_ATTRIBUTE, getBusinessObjectClass().getName());
		params.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.MAINTENANCE_NEW_METHOD_TO_CALL);
		params.put("hrCalendarId", hrCalendarId);
		AnchorHtmlData viewUrl = new AnchorHtmlData(UrlFactory.parameterizeUrl(KRADConstants.INQUIRY_ACTION, params), "view");
		viewUrl.setDisplayText("view");
		viewUrl.setTarget(AnchorHtmlData.TARGET_BLANK);
		customActionUrls.add(viewUrl);
		
		return customActionUrls;
	}
	
	@Override
	public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {
		String calendarName = fieldValues.get("calendarName");
		String calendarTypes = fieldValues.get("calendarTypes");
        String flsaBeginDay = fieldValues.get("flsaBeginDay");
        String flsaBeginTime = fieldValues.get("flsaBeginTime");

        return TkServiceLocator.getCalendarService().getCalendars(calendarName, calendarTypes, flsaBeginDay, flsaBeginTime);
	}

}