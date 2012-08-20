package org.kuali.hr.time.user.pref.service;

import org.kuali.hr.time.user.pref.UserPreferences;
import org.springframework.cache.annotation.Cacheable;

public interface UserPreferenceService {
	/**
	 * Fetch UserPreferences for a given PrincipalId
	 * @param principalId
	 * @return
	 */
    @Cacheable(value= UserPreferences.CACHE_NAME, key="'principalId=' + #p0")
	public UserPreferences getUserPreferences(String principalId);
}
