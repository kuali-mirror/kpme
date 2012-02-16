package org.kuali.hr.time.person.dao;

import java.util.List;

import org.kuali.hr.time.person.TKPerson;

public interface PersonDao {
	
	public List<TKPerson> getPersonCollection(List<String> principalIds);

}
