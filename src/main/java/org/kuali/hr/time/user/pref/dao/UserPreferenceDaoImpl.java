package org.kuali.hr.time.user.pref.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.time.user.pref.UserPreferences;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class UserPreferenceDaoImpl extends PersistenceBrokerDaoSupport implements UserPreferenceDao {
	
	
	public UserPreferences getUserPreferences(String principalId){
		Criteria crit = new Criteria();
		crit.addEqualTo("principalId", principalId);
		Query query = QueryFactory.newQuery(UserPreferences.class, crit);
		return (UserPreferences)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}
}
