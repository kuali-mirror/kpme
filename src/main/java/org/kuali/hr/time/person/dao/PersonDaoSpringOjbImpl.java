package org.kuali.hr.time.person.dao;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.hr.time.person.TKPerson;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class PersonDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements PersonDao {

    private static final Logger LOG = Logger.getLogger(PersonDaoSpringOjbImpl.class);

    public List<TKPerson> getPersonCollection(List<String> principalIds) {

        List<TKPerson> persons = new LinkedList<TKPerson>();

        for (String principalId : principalIds) {
            Person p = KimApiServiceLocator.getPersonService().getPerson(principalId);
            if (p != null) {
                TKPerson person = new TKPerson();
                person.setPrincipalId(p.getPrincipalId());
                person.setFirstName(p.getFirstName());
                person.setLastName(p.getLastName());
                person.setPrincipalName(p.getName());
                persons.add(person);
            }
        }
        return persons;
    }

}
