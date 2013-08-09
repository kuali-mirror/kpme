package org.kuali.kpme.core.paytype.web;

import java.util.Map;

import org.kuali.kpme.core.inquirable.KPMEInquirableImpl;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.krad.bo.BusinessObject;

public class PayTypeInquirableImpl extends KPMEInquirableImpl {

	@Override
	public HtmlData getInquiryUrl(BusinessObject businessObject,
			String attributeName, boolean forceInquiry) {
		return super.getInquiryUrl(businessObject, attributeName, forceInquiry);
	}

	@Override
	@Deprecated
	public BusinessObject getBusinessObject(Map fieldValues) {
		return super.getBusinessObject(fieldValues);
	}

}
