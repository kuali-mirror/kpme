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
package org.kuali.kpme.core.departmentaffiliation.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.departmentaffiliation.DepartmentAffiliationContract;
import org.kuali.kpme.core.api.departmentaffiliation.service.DepartmentAffiliationService;
import org.kuali.kpme.core.departmentaffiliation.DepartmentAffiliation;
import org.kuali.kpme.core.departmentaffiliation.dao.DepartmentAffiliationDao;

public class DepartmentAffiliationServiceImpl implements DepartmentAffiliationService {

	private DepartmentAffiliationDao departmentAffiliationDao;

	/**
	 * @return the departmentAffiliationDao
	 */
	public DepartmentAffiliationDao getDepartmentAffiliationDao() {
		return departmentAffiliationDao;
	}

	/**
	 * @param departmentAffiliationDao the departmentAffiliationDao to set
	 */
	public void setDepartmentAffiliationDao(
			DepartmentAffiliationDao departmentAffiliationDao) {
		this.departmentAffiliationDao = departmentAffiliationDao;
	}

	@Override
	public DepartmentAffiliation getDepartmentAffiliationById(
			String hrDeptAfflId) {
		return this.departmentAffiliationDao.getDepartmentAffiliationById(hrDeptAfflId);
	}
	
	@Override
	public DepartmentAffiliation getDepartmentAffiliationByType(String deptAfflType) {
		return this.departmentAffiliationDao.getDepartmentAffiliationByType(deptAfflType);
	}
	
	@Override
	public List<DepartmentAffiliation> getDepartmentAffiliationList(String deptAfflType, LocalDate asOfDate) {
		return this.departmentAffiliationDao.getDepartmentAffiliationList(deptAfflType, asOfDate);
	}

	@Override
	public List<DepartmentAffiliation> getAllActiveAffiliations() {
		return this.departmentAffiliationDao.getAllActiveAffiliations();
	}


    @Override
    public DepartmentAffiliation getPrimaryAffiliation() {
        return this.departmentAffiliationDao.getPrimaryAffiliation();
    }
}
