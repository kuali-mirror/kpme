package org.kuali.kpme.core.bo.institution.web;

import java.util.List;
import java.util.Map;

import org.kuali.kpme.core.bo.HrEffectiveDateActiveLookupableHelper;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.krad.bo.BusinessObject;

public class InstitutionLookupableHelper extends HrEffectiveDateActiveLookupableHelper{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5277378871669021091L;

	@Override
	public List<? extends BusinessObject> getSearchResults(
			Map<String, String> fieldValues) {
		// TODO Auto-generated method stub
		return super.getSearchResults(fieldValues);
	}

	@Override
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject,
			List pkNames) {
		// TODO Auto-generated method stub
		return super.getCustomActionUrls(businessObject, pkNames);
	}


}
