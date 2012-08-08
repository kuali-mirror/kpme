package org.kuali.hr.time.user.pref.service;

import org.kuali.hr.time.user.pref.UserPreferences;
import org.kuali.hr.time.user.pref.dao.UserPreferenceDao;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TkConstants;

public class UserPreferenceServiceImpl implements UserPreferenceService {
	private UserPreferenceDao userPrefDao;
	
	
	@Override
	public UserPreferences getUserPreferences(String principalId) {
		UserPreferences userPref = userPrefDao.getUserPreferences(principalId);
		if(userPref == null){
			return new UserPreferences(TKContext.getPrincipalId(), TkConstants.SYSTEM_TIME_ZONE);
		}
		return userPref;
	}


	public UserPreferenceDao getUserPrefDao() {
		return userPrefDao;
	}


	public void setUserPrefDao(UserPreferenceDao userPrefDao) {
		this.userPrefDao = userPrefDao;
	}

}
