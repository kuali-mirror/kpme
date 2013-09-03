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
package org.kuali.kpme.core.earncode.security.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.earncode.security.EarnCodeSecurity;
import org.kuali.kpme.core.earncode.security.EarnCodeType;
import org.kuali.kpme.core.lookup.KPMELookupableHelper;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.kns.web.struts.form.LookupForm;
import org.kuali.rice.kns.web.ui.Field;
import org.kuali.rice.kns.web.ui.Row;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;

@SuppressWarnings("deprecation")
public class EarnCodeSecurityLookupableHelper extends KPMELookupableHelper {

	private static final long serialVersionUID = 696878785547265569L;

	@Override
	@SuppressWarnings("rawtypes")
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject, List pkNames) {
		List<HtmlData> customActionUrls = super.getCustomActionUrls(businessObject, pkNames);

		EarnCodeSecurity earnCodeSecurity = (EarnCodeSecurity) businessObject;
		String hrEarnCodeSecurityId = earnCodeSecurity.getHrEarnCodeSecurityId();
		
		Properties params = new Properties();
		params.put(KRADConstants.BUSINESS_OBJECT_CLASS_ATTRIBUTE, getBusinessObjectClass().getName());
		params.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.MAINTENANCE_NEW_METHOD_TO_CALL);
		params.put("hrEarnCodeSecurityId", hrEarnCodeSecurityId);
		AnchorHtmlData viewUrl = new AnchorHtmlData(UrlFactory.parameterizeUrl(KRADConstants.INQUIRY_ACTION, params), "view");
		viewUrl.setDisplayText("view");
		viewUrl.setTarget(AnchorHtmlData.TARGET_BLANK);
		customActionUrls.add(viewUrl);
		
		return customActionUrls;
	}

	@Override
	public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {
        String salGroup = fieldValues.get("hrSalGroup");
        String dept = fieldValues.get("dept");
        String earnCode = fieldValues.get("earnCode");
        String location = fieldValues.get("location");
        String fromEffdt = TKUtils.getFromDateString(fieldValues.get("effectiveDate"));
        String toEffdt = TKUtils.getToDateString(fieldValues.get("effectiveDate"));
        String active = fieldValues.get("active");
        String showHist = fieldValues.get("history");
        String earnCodeType = fieldValues.get("earnCodeType");
        
        List<EarnCodeSecurity> searchResults = new ArrayList<EarnCodeSecurity>();

        List<EarnCodeSecurity> rawSearchResults = HrServiceLocator.getEarnCodeSecurityService().getEarnCodeSecuritiesByType(GlobalVariables.getUserSession().getPrincipalId(), dept, salGroup, earnCode, location, TKUtils.formatDateString(fromEffdt), 
        		TKUtils.formatDateString(toEffdt), active, showHist, earnCodeType);
        
        if(rawSearchResults != null && !rawSearchResults.isEmpty()) {
        	for(EarnCodeSecurity ecs : rawSearchResults) {
        		ecs.setEarnCodeType(HrConstants.EARN_CODE_SECURITY_TYPE.get(ecs.getEarnCodeType()));
        		searchResults.add(ecs);
        	}
        }
        
        return searchResults;
	}
	
	@Override
	public void performClear(LookupForm lookupForm) {
       for (Iterator iter = this.getRows().iterator(); iter.hasNext();) {
           Row row = (Row) iter.next();
           for (Iterator iterator = row.getFields().iterator(); iterator.hasNext();) {
               Field field = (Field) iterator.next();
               if (field.isSecure()) {
                   field.setSecure(false);
                   field.setDisplayMaskValue(null);
                   field.setEncryptedValue(null);
               }
	           if (!field.getFieldType().equals(Field.RADIO)) {
	                field.setPropertyValue(field.getDefaultValue());
	                if (field.getFieldType().equals(Field.MULTISELECT)) {
	                    field.setPropertyValues(null);
	                }
	           }
	          
	          if(field.getFieldType().equals(Field.RADIO) && StringUtils.equals(field.getFieldLabel(), "Earn Code Type")) {
	        		field.setPropertyValue("A");
	        	}
           }
       }
    }


}