package org.kuali.hr.time.person.dao;

import org.apache.log4j.Logger;
import org.kuali.hr.time.person.TKPerson;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

import java.util.LinkedList;
import java.util.List;

public class PersonDaoSpringOjbImpl extends PersistenceBrokerDaoSupport  implements PersonDao {

	private static final Logger LOG = Logger.getLogger(PersonDaoSpringOjbImpl.class);
	
	public List<TKPerson> getPersonCollection(List<String> principalIds) {
		
		StringBuilder pid = new StringBuilder();
		for (String principalId : principalIds) {
			pid.append("'" + principalId + "',");
		}
		
		String principalIdForQuery = pid.substring(0, pid.length() - 1);
		
		String GET_PERSON_SQL = "SELECT DISTINCT " 
				+ "ENTITY_ID, LAST_NM, FIRST_NM "
				+ "FROM "
				+ "KRIM_ENTITY_NM_T "
				+ "WHERE "
				+ "ACTV_IND = 'Y' AND "
				+ "NM_TYP_CD = 'PRFR' AND "
				+ "ENTITY_ID IN (" + principalIdForQuery + ") "
				+ "ORDER BY LAST_NM, FIRST_NM";
		
		List<TKPerson> persons = new LinkedList<TKPerson>();
		
		SqlRowSet rs = TkServiceLocator.getRiceJdbcTemplate().queryForRowSet(GET_PERSON_SQL, new Object[]{});
		while(rs.next()){
			TKPerson person = new TKPerson();
			person.setPrincipalId(rs.getString("ENTITY_ID"));
			person.setFirstName(rs.getString("FIRST_NM"));
			person.setLastName(rs.getString("LAST_NM"));
			person.setPrincipalName(person.getLastName() + ", " + person.getFirstName());
			persons.add(person);
		}
		return persons;
	}

}
