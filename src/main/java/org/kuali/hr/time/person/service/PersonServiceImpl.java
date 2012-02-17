package org.kuali.hr.time.person.service;

import java.util.List;

import org.kuali.hr.time.cache.CacheResult;
import org.kuali.hr.time.person.TKPerson;
import org.kuali.hr.time.person.dao.PersonDao;
import org.kuali.hr.time.util.TkConstants;


public class PersonServiceImpl implements PersonService { 
	private PersonDao personDao;
	
	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}

	@Override
	@CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
	public List<TKPerson> getPersonCollection(List<String> principalIds) {
		return personDao.getPersonCollection(principalIds);
	}
	
}
