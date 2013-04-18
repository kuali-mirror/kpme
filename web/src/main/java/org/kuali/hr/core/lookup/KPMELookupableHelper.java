package org.kuali.hr.core.lookup;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.KRADConstants;

@SuppressWarnings("deprecation")
public class KPMELookupableHelper extends KualiLookupableHelperServiceImpl {

	private static final long serialVersionUID = 6428435554717901643L;

	@Override
	@SuppressWarnings("rawtypes")
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject, List pkNames) {
		List<HtmlData> customActionUrls = new ArrayList<HtmlData>();

		List<HtmlData> existingCustomActionUrls = super.getCustomActionUrls(businessObject, pkNames);
		
		for (HtmlData existingCustomActionUrl : existingCustomActionUrls) {
			if (!StringUtils.equals(existingCustomActionUrl.getMethodToCall(), KRADConstants.MAINTENANCE_COPY_METHOD_TO_CALL)) {
				customActionUrls.add(existingCustomActionUrl);
			}
		}
		
		return customActionUrls;
	}

}