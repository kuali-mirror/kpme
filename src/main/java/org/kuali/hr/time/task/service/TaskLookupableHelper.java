package org.kuali.hr.time.task.service;

import java.util.List;
import java.util.Map;

import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;

public class TaskLookupableHelper extends KualiLookupableHelperServiceImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {
		if (fieldValues.containsKey("workAreaDescription")) {
			fieldValues.remove("workAreaDescription");
		}
		return super.getSearchResults(fieldValues);
	}
}
