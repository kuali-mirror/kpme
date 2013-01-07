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
package org.kuali.hr.lm.accrual.service;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.time.HrEffectiveDateActiveLookupableHelper;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;

/**
 * Used to override lookup functionality for the accrual category lookup
 */
public class AccrualCategoryLookupableHelper extends HrEffectiveDateActiveLookupableHelper {

	private static final long serialVersionUID = 1L;

    @Override
    public List<HtmlData> getCustomActionUrls(BusinessObject businessObject, List pkNames) {
		List<HtmlData> customActionUrls = super.getCustomActionUrls(businessObject, pkNames);

		AccrualCategory leaveAccrualCategory = (AccrualCategory) businessObject;
		String lmAccrualCategoryId = leaveAccrualCategory.getLmAccrualCategoryId();
		
		Properties params = new Properties();
		params.put(KRADConstants.BUSINESS_OBJECT_CLASS_ATTRIBUTE, getBusinessObjectClass().getName());
		params.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.MAINTENANCE_NEW_METHOD_TO_CALL);
		params.put("lmAccrualCategoryId", lmAccrualCategoryId);
		AnchorHtmlData viewUrl = new AnchorHtmlData(UrlFactory.parameterizeUrl(KRADConstants.INQUIRY_ACTION, params), "view");
		viewUrl.setDisplayText("view");
		viewUrl.setTarget(AnchorHtmlData.TARGET_BLANK);
		customActionUrls.add(viewUrl);
		
		return customActionUrls;
	}

    @Override
    public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {
        String accrualCategory = fieldValues.get("accrualCategory");
        String descr = fieldValues.get("descr");
        String fromEffdt = TKUtils.getFromDateString(fieldValues.get("effectiveDate"));
        String toEffdt = TKUtils.getToDateString(fieldValues.get("effectiveDate"));
        String leavePlan = fieldValues.get("leavePlan");
        String accrualEarnInterval = fieldValues.get("accrualEarnInterval");
        String unitOfTime = fieldValues.get("unitOfTime");
        String minPercentWorked = fieldValues.get("minPercentWorked");
        String active = fieldValues.get("active");
        String showHist = fieldValues.get("history");

        if (StringUtils.equals(accrualCategory, "%")) {
            accrualCategory = "";
        }

        return TkServiceLocator.getAccrualCategoryService().getAccrualCategories(accrualCategory, descr, leavePlan, accrualEarnInterval, unitOfTime, 
        		minPercentWorked, TKUtils.formatDateString(fromEffdt), TKUtils.formatDateString(toEffdt), active, showHist);
    }

}
