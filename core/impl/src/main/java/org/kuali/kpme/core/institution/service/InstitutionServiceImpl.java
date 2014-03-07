/**
 * Copyright 2004-2014 The Kuali Foundation
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
import org.kuali.kpme.core.api.institution.Institution;
import org.kuali.kpme.core.api.institution.service.InstitutionService;
import org.kuali.kpme.core.api.paytype.PayType;
import org.kuali.kpme.core.institution.InstitutionBo;
import org.kuali.kpme.core.institution.dao.InstitutionDao;
import org.kuali.kpme.core.paytype.PayTypeBo;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

public class InstitutionServiceImpl implements InstitutionService {

	private InstitutionDao institutionDao;
    private static final ModelObjectUtils.Transformer<InstitutionBo, Institution> toInstitution =
            new ModelObjectUtils.Transformer<InstitutionBo, Institution>() {
                public Institution transform(InstitutionBo input) {
                    return InstitutionBo.to(input);
                };
            };

	@Override
	public Institution getInstitutionById(String institutionId) {
		return InstitutionBo.to(getInstitutionBoById(institutionId));
	}

    public InstitutionBo getInstitutionBoById(String institutionId) {
        return institutionDao.getInstitutionById(institutionId);
    }

	@Override
	public List<Institution> getActiveInstitutionsAsOf(LocalDate asOfDate) {
		return ModelObjectUtils.transform(institutionDao.getActiveInstitutions(asOfDate), toInstitution);
	}

	@Override
	public List<Institution> getInstitutionsByCode(String code) {
		return ModelObjectUtils.transform(institutionDao.getInstitutionByCode(code), toInstitution);
	}

	public InstitutionDao getInstitutionDao() {
		return institutionDao;
	}

	public void setInstitutionDao(InstitutionDao institutionDao) {
		this.institutionDao = institutionDao;
	}
	
	@Override
	public Institution getInstitution(String institutionCode, LocalDate asOfDate) {
		return InstitutionBo.to(getInstitutionBo(institutionCode, asOfDate));
	}

    protected InstitutionBo getInstitutionBo(String institutionCode, LocalDate asOfDate) {
        return institutionDao.getInstitution(institutionCode, asOfDate);
    }

	@Override
	public int getInstitutionCount(String institutionCode, LocalDate asOfDate) {
		return institutionDao.getInstitutionCount(institutionCode, asOfDate);
	}

	@Override
	public List<Institution> getInstitutions(LocalDate fromEffdt,
			LocalDate toEffdt, String institutionCode, String active, String showHistory) {
		return ModelObjectUtils.transform(institutionDao.getInstitutions(fromEffdt, toEffdt, institutionCode, active, showHistory), toInstitution);
	}

}
