package org.kuali.hr.time.person.service;

import java.util.List;

import org.kuali.hr.time.person.TKPerson;

public interface PersonService {

	public List<TKPerson> getPersonCollection(List<String> principalIds);

}
