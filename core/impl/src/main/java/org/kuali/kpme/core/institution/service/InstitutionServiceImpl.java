/**
 * Copyright 2004-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kpme.core.institution.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.institution.Institution;
import org.kuali.kpme.core.institution.dao.InstitutionDao;

public class InstitutionServiceImpl implements InstitutionService {

	private InstitutionDao institutionDao;
	
	@Override
	public Institution getInstitutionById(String institutionId) {
		return institutionDao.getInstitutionById(institutionId);
	}

	@Override
	public List<Institution> getActiveInstitutionsAsOf(LocalDate asOfDate) {
		return institutionDao.getActiveInstitutions(asOfDate);
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
	
	@Override
	public Institution getInstitution(String institutionCode, LocalDate asOfDate) {
		return institutionDao.getInstitution(institutionCode, asOfDate);
	}

}
