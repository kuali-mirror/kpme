package org.kuali.hr.pm.institution.service;

import java.util.List;

import org.joda.time.DateTime;
import org.kuali.hr.pm.institution.Institution;
import org.kuali.hr.pm.institution.dao.InstitutionDao;

public class InstitutionServiceImpl implements InstitutionService {

	private InstitutionDao institutionDao;
	
	@Override
	public Institution getInstitutionById(String institutionId) {
		return institutionDao.getInstitutionById(institutionId);
	}

	@Override
	public List<Institution> getActiveInstitutionsAsOf(DateTime asOfDate) {
		return institutionDao.getActiveInstitutions(new java.sql.Date(asOfDate.getMillis()));
	}

	@Override
	public List<Institution> getInstitutionsByCode(String code) {
		return institutionDao.getInstitutionByCode(code);
	}

	public InstitutionDao getInstitutionDao() {
		return institutionDao;
	}

	public void setInstitutionDao(InstitutionDao institutionDao) {
		this.institutionDao = institutionDao;
	}

}
