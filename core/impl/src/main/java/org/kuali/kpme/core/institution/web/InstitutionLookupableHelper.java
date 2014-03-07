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
package org.kuali.kpme.core.institution.web;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.api.institution.Institution;
import org.kuali.kpme.core.bo.HrEffectiveDateActiveLookupableHelper;
import org.kuali.kpme.core.institution.InstitutionBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;

@SuppressWarnings("deprecation")
public class InstitutionLookupableHelper extends HrEffectiveDateActiveLookupableHelper{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5277378871669021091L;

    private static final ModelObjectUtils.Transformer<Institution, InstitutionBo> toInstitutionBo =
            new ModelObjectUtils.Transformer<Institution, InstitutionBo>() {
                public InstitutionBo transform(Institution input) {
                    return InstitutionBo.from(input);
                };
            };

	@Override
	public List<? extends BusinessObject> getSearchResults(
			Map<String, String> fieldValues) {
		if (StringUtils.contains(fieldValues.get("institutionCode"), "*")) {
			fieldValues.put("institutionCode","");			
		}
		
		String institutionCode = fieldValues.get("institutionCode");
        String fromEffdt = TKUtils.getFromDateString(fieldValues.get("effectiveDate"));
        String toEffdt = TKUtils.getToDateString(fieldValues.get("effectiveDate"));
        String active = fieldValues.get("active");
        String showHistory = fieldValues.get("history");
        
		return ModelObjectUtils.transform(HrServiceLocator.getInstitutionService().getInstitutions(TKUtils.formatDateString(fromEffdt), TKUtils.formatDateString(toEffdt), institutionCode, active, showHistory), toInstitutionBo);
	}

	@Override
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject,
			List pkNames) {
    	List<HtmlData> customActionUrls = super.getCustomActionUrls(businessObject, pkNames);

		InstitutionBo institution = (InstitutionBo) businessObject;
        String pmInstitutionId = institution.getPmInstitutionId();
		
		Properties params = new Properties();
		params.put(KRADConstants.BUSINESS_OBJECT_CLASS_ATTRIBUTE, getBusinessObjectClass().getName());
		params.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.MAINTENANCE_NEW_METHOD_TO_CALL);
		
		params.put("pmInstitutionId", pmInstitutionId);
		
		AnchorHtmlData viewUrl = new AnchorHtmlData(UrlFactory.parameterizeUrl(KRADConstants.INQUIRY_ACTION, params), "view");
		viewUrl.setDisplayText("view");
		viewUrl.setTarget(AnchorHtmlData.TARGET_BLANK);
		customActionUrls.add(viewUrl);
		
		return customActionUrls;
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
