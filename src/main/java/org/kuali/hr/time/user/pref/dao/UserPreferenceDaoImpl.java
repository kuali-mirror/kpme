package org.kuali.hr.time.user.pref.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.time.user.pref.UserPreferences;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class UserPreferenceDaoImpl extends PlatformAwareDaoBaseOjb implements UserPreferenceDao {
	
	
	public UserPreferences getUserPreferences(String principalId){
		Criteria crit = new Criteria();
		crit.addEqualTo("principalId", principalId);
		Query query = QueryFactory.newQuery(UserPreferences.class, crit);
		return (UserPreferences)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}
}
