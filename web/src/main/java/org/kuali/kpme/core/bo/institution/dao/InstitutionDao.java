package org.kuali.kpme.core.bo.institution.dao;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.institution.Institution;

public interface InstitutionDao {

	public Institution getInstitution(String institution, LocalDate asOfDate);
	
	public List<Institution> getActiveInstitutions(LocalDate asOfDate);
	
	public List<Institution> getInstitutionByCode(String code);
	
	public Institution getInstitutionById(String institutionId);
	
}
