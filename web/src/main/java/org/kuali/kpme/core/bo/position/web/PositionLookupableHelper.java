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
package org.kuali.kpme.core.bo.position.web;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.web.form.LookupForm;

@SuppressWarnings("deprecation")
//public class PositionLookupableHelper extends KPMELookupableHelper {
public class PositionLookupableHelper extends LookupableImpl {
	
	private static final long serialVersionUID = -131891943631454679L;

	/* kns
	@Override
	@SuppressWarnings("rawtypes")
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject, List pkNames) {
		List<HtmlData> customActionUrls = super.getCustomActionUrls(businessObject, pkNames);

		Position position = (Position) businessObject;
		String hrPositionId = position.getHrPositionId();
		
		Properties params = new Properties();
		params.put(KRADConstants.BUSINESS_OBJECT_CLASS_ATTRIBUTE, getBusinessObjectClass().getName());
		params.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.MAINTENANCE_NEW_METHOD_TO_CALL);
		params.put("hrPositionId", hrPositionId);
		AnchorHtmlData viewUrl = new AnchorHtmlData(UrlFactory.parameterizeUrl(KRADConstants.INQUIRY_ACTION, params), "view");
		viewUrl.setDisplayText("view");
		viewUrl.setTarget(AnchorHtmlData.TARGET_BLANK);
		customActionUrls.add(viewUrl);
		
		System.out.println("getCustomActionUrls: "+viewUrl);
		return customActionUrls;
	} 
	
    @Override
    public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {
        String positionNum = fieldValues.get("positionNumber");
        String description = fieldValues.get("description");
        String fromEffdt = TKUtils.getFromDateString(fieldValues.get("effectiveDate"));
        String toEffdt = TKUtils.getToDateString(fieldValues.get("effectiveDate"));
        String active = fieldValues.get("active");
        String showHist = fieldValues.get("history");

        if (StringUtils.equals(positionNum, "%")) {
            positionNum = "";
        }
                
        return HrServiceLocator.getPositionService().getPositions(positionNum, description, TKUtils.formatDateString(fromEffdt),
                TKUtils.formatDateString(toEffdt), active, showHist);
    }*/
	
    @Override
    protected List<? extends BusinessObject> getSearchResults(LookupForm form, Map<String, String> fieldValues, boolean unbounded) {
				
    	String positionNum = fieldValues.get("positionNumber");
        String description = fieldValues.get("description");
        String fromEffdt = TKUtils.getFromDateString(fieldValues.get("effectiveDate"));
        String toEffdt = TKUtils.getToDateString(fieldValues.get("effectiveDate"));
        String active = fieldValues.get("active");
        String showHist = fieldValues.get("history");

        if (StringUtils.equals(positionNum, "%")) {
            positionNum = "";
        }
        
        return HrServiceLocator.getPositionService().getPositions(positionNum, description, TKUtils.formatDateString(fromEffdt),
                TKUtils.formatDateString(toEffdt), active, showHist);	

    }    

}