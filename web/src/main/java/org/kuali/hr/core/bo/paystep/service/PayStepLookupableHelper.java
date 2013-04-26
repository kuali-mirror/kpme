package org.kuali.hr.core.bo.paystep.service;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.kuali.hr.core.bo.paystep.PayStep;
import org.kuali.hr.core.lookup.KPMELookupableHelper;
import org.kuali.hr.pm.service.base.PmServiceLocator;
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
        String campus = fieldValues.get("campus");
        String salaryGroup = fieldValues.get("salaryGroup");
        String payGrade = fieldValues.get("payGrade");
        String active = fieldValues.get("active");

        List<PayStep> paySteps = PmServiceLocator.getPayStepService().getPaySteps(payStep, institution, campus, salaryGroup, payGrade, active);

        return paySteps;
    }
}
