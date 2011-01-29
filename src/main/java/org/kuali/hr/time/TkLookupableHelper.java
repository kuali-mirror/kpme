package org.kuali.hr.time;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;

public class TkLookupableHelper extends KualiLookupableHelperServiceImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public List<? extends BusinessObject> getSearchResults(
			Map<String, String> fieldValues) {
		if(fieldValues.containsKey("workArea") && StringUtils.equals(fieldValues.get("workArea"),"%")){
			fieldValues.put("workArea", "");
		}
		if(fieldValues.containsKey("jobNumber") && StringUtils.equals(fieldValues.get("jobNumber"), "%")){
			fieldValues.put("jobNumber", "");
		}
		return super.getSearchResults(fieldValues);
	}

	@Override
	protected void validateSearchParameterWildcardAndOperators(
			String attributeName, String attributeValue) {
		if(!StringUtils.equals(attributeValue, "%")){
			super.validateSearchParameterWildcardAndOperators(attributeName,
					attributeValue);	
		}
	}



}
