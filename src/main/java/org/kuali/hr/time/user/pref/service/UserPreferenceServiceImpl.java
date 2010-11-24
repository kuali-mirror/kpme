package org.kuali.hr.time.user.pref.service;

import org.kuali.hr.time.user.pref.UserPreferences;
import org.kuali.hr.time.user.pref.dao.UserPreferenceDao;

public class UserPreferenceServiceImpl implements UserPreferenceService {
	private UserPreferenceDao userPrefDao;
	
	
	@Override
	public UserPreferences getUserPreferences(String principalId) {
		return userPrefDao.getUserPreferences(principalId);
	}


	public UserPreferenceDao getUserPrefDao() {
		return userPrefDao;
	}


	public void setUserPrefDao(UserPreferenceDao userPrefDao) {
		this.userPrefDao = userPrefDao;
	}

}
