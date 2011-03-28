package org.kuali.hr.time.user.service;

import java.sql.Date;

import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUser;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.KIMServiceLocator;

public class UserServiceImpl implements UserService {

	@Override
	public TKUser buildTkUser(String principalId, Date asOfDate) {
		TKUser tkUser = new TKUser();
		tkUser.setUserPreference(TkServiceLocator.getUserPreferenceService().getUserPreferences(principalId));
		Person person = KIMServiceLocator.getPersonService().getPerson(principalId);
		tkUser.setActualPerson(person);
		
		return tkUser;
	}


}
