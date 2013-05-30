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
package org.kuali.kpme.core.paystep.web;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.kuali.kpme.core.lookup.KPMELookupableHelper;
import org.kuali.kpme.core.paystep.PayStep;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;

@SuppressWarnings("deprecation")
public class PayStepLookupableHelper extends KPMELookupableHelper {

	private static final long serialVersionUID = 7597508514001732034L;

	@Override
	@SuppressWarnings("rawtypes")
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject, List pkNames) {
		List<HtmlData> customActionUrls = super.getCustomActionUrls(businessObject, pkNames);
			
		PayStep payStep = (PayStep) businessObject;
		String pmPayStepId = payStep.getPmPayStepId();
		
		Properties params = new Properties();
		params.put(KRADConstants.BUSINESS_OBJECT_CLASS_ATTRIBUTE, getBusinessObjectClass().getName());
		params.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.MAINTENANCE_NEW_METHOD_TO_CALL);
		params.put("pmPayStepId", pmPayStepId);
		AnchorHtmlData viewUrl = new AnchorHtmlData(UrlFactory.parameterizeUrl(KRADConstants.INQUIRY_ACTION, params), "view");
		viewUrl.setDisplayText("view");
		viewUrl.setTarget(AnchorHtmlData.TARGET_BLANK);
		customActionUrls.add(viewUrl);
		
		return customActionUrls;
	}

    @Override
    public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {

        String payStep = fieldValues.get("payStep");
        String institution = fieldValues.get("institution");
        String location = fieldValues.get("location");
        String salaryGroup = fieldValues.get("salaryGroup");
        String payGrade = fieldValues.get("payGrade");
        String active = fieldValues.get("active");

        List<PayStep> paySteps = HrServiceLocator.getPayStepService().getPaySteps(payStep, institution, location, salaryGroup, payGrade, active);

        return paySteps;
    }
}
