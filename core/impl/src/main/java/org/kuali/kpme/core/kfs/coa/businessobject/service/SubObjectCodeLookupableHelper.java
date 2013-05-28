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
package org.kuali.kpme.core.kfs.coa.businessobject.service;

import java.util.List;
import java.util.Properties;

import org.kuali.kpme.core.kfs.coa.businessobject.SubObjectCode;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;

public class SubObjectCodeLookupableHelper extends KualiLookupableHelperServiceImpl {

	private static final long serialVersionUID = -1851652201286445549L;

	@Override
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject, List pkNames) {
		List<HtmlData> customActionUrls = super.getCustomActionUrls(businessObject, pkNames);
		
		SubObjectCode subObjectCode = (SubObjectCode) businessObject;
		String financialSubObjectCode = subObjectCode.getFinancialSubObjectCode();
		String financialObjectCode = subObjectCode.getFinancialObjectCode();
		String accountNumber = subObjectCode.getAccountNumber();
		String universityFiscalYear = String.valueOf(subObjectCode.getUniversityFiscalYear());
		String chartOfAccountsCode = subObjectCode.getChartOfAccountsCode();
		
		Properties params = new Properties();
		params.put(KRADConstants.BUSINESS_OBJECT_CLASS_ATTRIBUTE, getBusinessObjectClass().getName());
		params.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.MAINTENANCE_NEW_METHOD_TO_CALL);
		params.put("financialSubObjectCode", financialSubObjectCode);
		params.put("financialObjectCode", financialObjectCode);
		params.put("accountNumber", accountNumber);
		params.put("universityFiscalYear", universityFiscalYear);
		params.put("hrEarnCodeSecurityId", chartOfAccountsCode);
		AnchorHtmlData viewUrl = new AnchorHtmlData(UrlFactory.parameterizeUrl(KRADConstants.INQUIRY_ACTION, params), "view");
		viewUrl.setDisplayText("view");
		viewUrl.setTarget(AnchorHtmlData.TARGET_BLANK);
		customActionUrls.add(viewUrl);
		
		return customActionUrls;
	}
	
}