package org.kuali.hr.time.person.dao;

import org.kuali.hr.time.person.TKPerson;

import java.util.List;

public interface PersonDao {
	
	public List<TKPerson> getPersonCollection(List<String> principalIds);

}
