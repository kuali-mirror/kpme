package org.kuali.hr.lm.leavedonation.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.kuali.hr.lm.leavedonation.LeaveDonation;
import org.kuali.hr.time.HrEffectiveDateActiveLookupableHelper;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;

public class LeaveDonationLookupableHelper extends HrEffectiveDateActiveLookupableHelper {

	private static final long serialVersionUID = 4181583515349590532L;

	@Override
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject, List pkNames) {
		List<HtmlData> customActionUrls = super.getCustomActionUrls(businessObject, pkNames);
		
		List<HtmlData> copyOfUrls = new ArrayList<HtmlData>();
		copyOfUrls.addAll(customActionUrls);
		for(HtmlData aData : copyOfUrls) {
			if(aData.getMethodToCall().equals(KRADConstants.MAINTENANCE_EDIT_METHOD_TO_CALL)) {
				customActionUrls.remove(aData);
			}
		}
		LeaveDonation leaveDonation = (LeaveDonation) businessObject;
		String lmLeaveDonationId = leaveDonation.getLmLeaveDonationId();
		
		Properties params = new Properties();
		params.put(KRADConstants.BUSINESS_OBJECT_CLASS_ATTRIBUTE, getBusinessObjectClass().getName());
		params.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.MAINTENANCE_NEW_METHOD_TO_CALL);
		params.put("lmLeaveDonationId", lmLeaveDonationId);
		AnchorHtmlData viewUrl = new AnchorHtmlData(UrlFactory.parameterizeUrl(KRADConstants.INQUIRY_ACTION, params), "view");
		viewUrl.setDisplayText("view");
		viewUrl.setTarget(AnchorHtmlData.TARGET_BLANK);
		customActionUrls.add(viewUrl);
		
		return customActionUrls;
	}

}