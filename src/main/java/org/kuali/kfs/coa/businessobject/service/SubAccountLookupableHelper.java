package org.kuali.kfs.coa.businessobject.service;

import java.util.List;
import java.util.Properties;

import org.kuali.kfs.coa.businessobject.SubAccount;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;

public class SubAccountLookupableHelper extends KualiLookupableHelperServiceImpl {

	private static final long serialVersionUID = -6756637488997782255L;

	@Override
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject, List pkNames) {
		List<HtmlData> customActionUrls = super.getCustomActionUrls(businessObject, pkNames);
		
		SubAccount subAccount = (SubAccount) businessObject;
		String subAccountNumber = subAccount.getSubAccountNumber();
		String accountNumber = subAccount.getAccountNumber();
		String chartOfAccountsCode = subAccount.getChartOfAccountsCode();
		
		Properties params = new Properties();
		params.put(KRADConstants.BUSINESS_OBJECT_CLASS_ATTRIBUTE, getBusinessObjectClass().getName());
		params.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.MAINTENANCE_NEW_METHOD_TO_CALL);
		params.put("subAccountNumber", subAccountNumber);
		params.put("accountNumber", accountNumber);
		params.put("chartOfAccountsCode", chartOfAccountsCode);
		AnchorHtmlData viewUrl = new AnchorHtmlData(UrlFactory.parameterizeUrl(KRADConstants.INQUIRY_ACTION, params), "view");
		viewUrl.setDisplayText("view");
		viewUrl.setTarget(AnchorHtmlData.TARGET_BLANK);
		customActionUrls.add(viewUrl);
		
		return customActionUrls;
	}
	
}