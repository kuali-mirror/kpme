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
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.kfs.coa.businessobject.Account;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;

public class AccountLookupableHelper extends KualiLookupableHelperServiceImpl {

	private static final long serialVersionUID = 2900185403539471985L;

    @Override
    public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {
        if (fieldValues.containsKey("closed")) {
            String closedValue = fieldValues.get("closed");
            if (StringUtils.isNotEmpty(closedValue)) {
                fieldValues.put("active", StringUtils.equals(closedValue, "Y") ? "N" : "Y");
            }
            fieldValues.remove("closed");
        }
        return super.getSearchResults(fieldValues);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject, List pkNames) {
		List<HtmlData> customActionUrls = super.getCustomActionUrls(businessObject, pkNames);
		
		Account account = (Account) businessObject;
		String accountNumber = account.getAccountNumber();
		String chartOfAccountsCode = account.getChartOfAccountsCode();

		Properties params = new Properties();
		params.put(KRADConstants.BUSINESS_OBJECT_CLASS_ATTRIBUTE, getBusinessObjectClass().getName());
		params.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.MAINTENANCE_NEW_METHOD_TO_CALL);
		params.put("accountNumber", accountNumber);
		params.put("chartOfAccountsCode", chartOfAccountsCode);
		AnchorHtmlData viewUrl = new AnchorHtmlData(UrlFactory.parameterizeUrl(KRADConstants.INQUIRY_ACTION, params), "view");
		viewUrl.setDisplayText("view");
		viewUrl.setTarget(AnchorHtmlData.TARGET_BLANK);
		customActionUrls.add(viewUrl);
		
		return customActionUrls;
	}
	
}