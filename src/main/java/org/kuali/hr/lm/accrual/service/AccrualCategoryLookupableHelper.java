package org.kuali.hr.lm.accrual.service;

import java.util.List;
import java.util.Properties;

import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.time.HrEffectiveDateActiveLookupableHelper;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;

public class AccrualCategoryLookupableHelper extends HrEffectiveDateActiveLookupableHelper {

	private static final long serialVersionUID = 1945437057693562692L;

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

}
