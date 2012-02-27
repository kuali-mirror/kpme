package org.kuali.hr.time.person.dao;

import org.apache.log4j.Logger;
import org.kuali.hr.time.person.TKPerson;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

import java.util.LinkedList;
import java.util.List;

public class PersonDaoSpringOjbImpl extends PersistenceBrokerDaoSupport  implements PersonDao {

	private static final Logger LOG = Logger.getLogger(PersonDaoSpringOjbImpl.class);
	
	public List<TKPerson> getPersonCollection(List<String> principalIds) {
		
		List<TKPerson> persons = new LinkedList<TKPerson>();
		
		for(String principalId: principalIds){
			Person p = KIMServiceLocator.getPersonService().getPerson(principalId);
			TKPerson person = new TKPerson();
			person.setPrincipalId(p.getPrincipalId());
			person.setFirstName(p.getFirstName());
			person.setLastName(p.getLastName());
			person.setPrincipalName(p.getName());
			persons.add(person);
		}
		return persons;
	}

}
