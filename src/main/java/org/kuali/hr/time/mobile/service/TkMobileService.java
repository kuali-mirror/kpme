package org.kuali.hr.time.mobile.service;

import java.util.List;
import java.util.Map;


public interface TkMobileService {
	public String getClockEntryInfo(String principalId);
	public Map<String,List<String>> addClockAction(String principalId, String assignmentKey, String clockAction);
}
