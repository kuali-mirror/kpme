package org.kuali.kfs.coa.businessobject.service;

import java.util.List;
import java.util.Properties;

import org.kuali.kfs.coa.businessobject.Organization;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;

public class OrganizationLookupableHelper extends KualiLookupableHelperServiceImpl {

	private static final long serialVersionUID = 6145986437726258575L;

	@Override
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject, List pkNames) {
		List<HtmlData> customActionUrls = super.getCustomActionUrls(businessObject, pkNames);
		
		Organization organization = (Organization) businessObject;
		final String organizationCode = organization.getOrganizationCode();
		final String chartOfAccountsCode = organization.getChartOfAccountsCode();
		
		Properties params = new Properties();
		params.put(KRADConstants.BUSINESS_OBJECT_CLASS_ATTRIBUTE, getBusinessObjectClass().getName());
		params.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.MAINTENANCE_NEW_METHOD_TO_CALL);
		params.put("organizationCode", organizationCode);
		params.put("chartOfAccountsCode", chartOfAccountsCode);
		AnchorHtmlData viewUrl = new AnchorHtmlData(UrlFactory.parameterizeUrl(KRADConstants.INQUIRY_ACTION, params), "view");
		viewUrl.setDisplayText("view");
		viewUrl.setTarget(AnchorHtmlData.TARGET_BLANK);
		customActionUrls.add(viewUrl);
		
		return customActionUrls;
	}
	 
}