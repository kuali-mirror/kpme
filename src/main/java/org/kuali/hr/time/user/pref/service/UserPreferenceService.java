package org.kuali.hr.time.user.pref.service;

import org.kuali.hr.time.user.pref.UserPreferences;

public interface UserPreferenceService {
	public UserPreferences getUserPreferences(String principalId);
}
