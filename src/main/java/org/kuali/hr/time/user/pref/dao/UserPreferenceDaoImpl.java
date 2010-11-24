package org.kuali.hr.time.user.pref.dao;

import org.kuali.hr.time.user.pref.UserPreferences;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class UserPreferenceDaoImpl extends PersistenceBrokerDaoSupport implements UserPreferenceDao {
	
	
	public UserPreferences getUserPreferences(String principalId){
		return (UserPreferences)this.getPersistenceBrokerTemplate().getObjectById(UserPreferences.class, principalId);
	}
}
