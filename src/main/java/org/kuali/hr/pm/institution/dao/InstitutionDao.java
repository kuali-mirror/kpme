package org.kuali.hr.pm.institution.dao;

import java.util.List;
import java.sql.Date;

import org.kuali.hr.pm.institution.Institution;

public interface InstitutionDao {

	public Institution getInstitution(String institution, Date asOfDate);
	
	public List<Institution> getActiveInstitutions(Date asOfDate);
	
	public List<Institution> getInstitutionByCode(String code);
	
	public Institution getInstitutionById(String institutionId);
	
}
