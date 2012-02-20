package org.kuali.hr.time.person.service;

import org.kuali.hr.time.person.TKPerson;

import java.util.List;

public interface PersonService {

	public List<TKPerson> getPersonCollection(List<String> principalIds);

}
