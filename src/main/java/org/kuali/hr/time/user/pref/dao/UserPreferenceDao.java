package org.kuali.hr.time.user.pref.dao;

import org.kuali.hr.time.user.pref.UserPreferences;

public interface UserPreferenceDao {
	public UserPreferences getUserPreferences(String principalId);
}
