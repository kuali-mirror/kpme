package org.kuali.hr.pm.institution.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.hr.pm.institution.Institution;

public interface InstitutionService {

	public Institution getInstitutionById(String institutionId);
	
	public List<Institution> getActiveInstitutionsAsOf(LocalDate asOfDate);
	
	public List<Institution> getInstitutionsByCode(String code);
	
	/**
	 * retrieve the institution with given code and exists before given date
	 * @param institutionCode
	 * @param asOfDate
	 * @return
	 */
	public Institution getInstitution(String institutionCode, LocalDate asOfDate);
	
}
