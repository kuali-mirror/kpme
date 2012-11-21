package org.kuali.hr.lm.balancetransfer.service;

import java.util.List;
import java.util.Map;

import org.kuali.hr.time.HrEffectiveDateActiveLookupableHelper;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.krad.bo.BusinessObject;

public class BalanceTransferLookupableHelper extends
		HrEffectiveDateActiveLookupableHelper {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6910172165048825489L;

	@Override
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject,
			List pkNames) {
		return super.getCustomActionUrls(businessObject, pkNames);
	}

	@Override
	public List<? extends BusinessObject> getSearchResults(
			Map<String, String> fieldValues) {
		// TODO Auto-generated method stub
		return super.getSearchResults(fieldValues);
	}

}
