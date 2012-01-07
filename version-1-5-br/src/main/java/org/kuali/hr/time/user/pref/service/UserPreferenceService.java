package org.kuali.hr.time.user.pref.service;

import org.kuali.hr.time.user.pref.UserPreferences;

public interface UserPreferenceService {
	/**
	 * Fetch UserPreferences for a given PrincipalId
	 * @param principalId
	 * @return
	 */
	public UserPreferences getUserPreferences(String principalId);
}
