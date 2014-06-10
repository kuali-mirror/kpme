/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.tklm.time.rules.overtime.daily.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;
import org.kuali.kpme.core.lookup.KpmeHrBusinessObjectLookupableHelper;
import org.kuali.kpme.tklm.time.rules.overtime.daily.DailyOvertimeRule;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;

@SuppressWarnings("deprecation")
public class DailyOvertimeRuleLookupableHelperServiceImpl extends KpmeHrBusinessObjectLookupableHelper {

	private static final long serialVersionUID = 2720495398967391250L;

	@Override
	@SuppressWarnings("rawtypes")
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject, List pkNames) {
		List<HtmlData> customActionUrls = super.getCustomActionUrls(businessObject, pkNames);

		DailyOvertimeRule dailyOvertimeRule = (DailyOvertimeRule) businessObject;
		String tkDailyOvertimeRuleId = dailyOvertimeRule.getTkDailyOvertimeRuleId();
		
		Properties params = new Properties();
		params.put(KRADConstants.BUSINESS_OBJECT_CLASS_ATTRIBUTE, getBusinessObjectClass().getName());
		params.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.MAINTENANCE_NEW_METHOD_TO_CALL);
		params.put("tkDailyOvertimeRuleId", tkDailyOvertimeRuleId);

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

    public List<? extends HrBusinessObjectContract> getSearchResults(Map<String, String> fieldValues) {
    	List<? extends HrBusinessObjectContract> temp = new ArrayList<HrBusinessObjectContract>();
		temp = super.getSearchResults(fieldValues);
		
		if ( temp != null ){
			List<DailyOvertimeRule> results = new ArrayList<DailyOvertimeRule>();
			for ( int i = 0; i < temp.size(); i++ ){
				DailyOvertimeRule dailyOvertimeRule = (DailyOvertimeRule)temp.get(i);
				results.add(dailyOvertimeRule);
			}
			
			return TkServiceLocator.getDailyOvertimeRuleService().getDailyOvertimeRules(GlobalVariables.getUserSession().getPrincipalId(), results);
		} else {
			return temp;
		}
    }
    
}
