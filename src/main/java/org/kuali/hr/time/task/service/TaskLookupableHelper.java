package org.kuali.hr.time.task.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.task.Task;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;

public class TaskLookupableHelper extends KualiLookupableHelperServiceImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings({"unchecked" })
	@Override
	public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {
		List finalList = new ArrayList<Task>();
		String wad = "";
		String wa = "";
		if (fieldValues.containsKey("workAreaDescription") && !fieldValues.get("workAreaDescription").isEmpty()) {
			wad = fieldValues.get("workAreaDescription");
			fieldValues.remove("workAreaDescription");
		}
		if(fieldValues.containsKey("workArea") && !fieldValues.get("workArea").isEmpty()) {
			wa = fieldValues.get("workArea");
			fieldValues.remove("workArea");
		}
		List aList = super.getSearchResults(fieldValues);
		if(wa.isEmpty() && wad.isEmpty()) {
			return aList;
		}
		for(int i = 0; i< aList.size(); i++) {
			Task aTask = (Task) aList.get(i);
			if(aTask.getTkWorkAreaId() != null) {
				WorkArea aWorkArea = TkServiceLocator.getWorkAreaService().getWorkArea(aTask.getTkWorkAreaId());
				if(aWorkArea != null) {
					if( (!wa.isEmpty() && aWorkArea.getWorkArea().toString().equals(wa)) 
							|| (!wad.isEmpty()&& aWorkArea.getDescription().equals(wad))) {
						finalList.add(aTask);
					}
				}
			}
		}
		
		return finalList;
	}
}
