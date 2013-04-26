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
package org.kuali.hr.tklm.leave.adjustment.service;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.kuali.hr.core.lookup.KPMELookupableHelper;
import org.kuali.hr.tklm.common.TKUtils;
import org.kuali.hr.tklm.leave.adjustment.LeaveAdjustment;
import org.kuali.hr.tklm.leave.service.LmServiceLocator;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;

@SuppressWarnings("deprecation")
public class LeaveAdjustmentLookupableHelper extends KPMELookupableHelper {

	private static final long serialVersionUID = 8673584702786497392L;

	@Override
	@SuppressWarnings("rawtypes")
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject, List pkNames) {
		List<HtmlData> customActionUrls = super.getCustomActionUrls(businessObject, pkNames);
		
		LeaveAdjustment leaveAdjustment = (LeaveAdjustment) businessObject;
		String lmLeaveAdjustmentId = leaveAdjustment.getLmLeaveAdjustmentId();
		
		Properties params = new Properties();
		params.put(KRADConstants.BUSINESS_OBJECT_CLASS_ATTRIBUTE, getBusinessObjectClass().getName());
		params.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.MAINTENANCE_NEW_METHOD_TO_CALL);
		params.put("lmLeaveAdjustmentId", lmLeaveAdjustmentId);
		AnchorHtmlData viewUrl = new AnchorHtmlData(UrlFactory.parameterizeUrl(KRADConstants.INQUIRY_ACTION, params), "view");
		viewUrl.setDisplayText("view");
		viewUrl.setTarget(AnchorHtmlData.TARGET_BLANK);
		customActionUrls.add(viewUrl);
		
		return customActionUrls;
	}
	
    @Override
    public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {
    	String fromEffdt = TKUtils.getFromDateString(fieldValues.get("effectiveDate"));
        String toEffdt = TKUtils.getToDateString(fieldValues.get("effectiveDate"));
    	String principalId = fieldValues.get("principalId");
        String accrualCategory = fieldValues.get("accrualCategory");
        String earnCode = fieldValues.get("earnCode");
        
        return LmServiceLocator.getLeaveAdjustmentService().getLeaveAdjustments(TKUtils.formatDateString(fromEffdt), TKUtils.formatDateString(toEffdt), 
        		principalId, accrualCategory, earnCode);
    }
	
}