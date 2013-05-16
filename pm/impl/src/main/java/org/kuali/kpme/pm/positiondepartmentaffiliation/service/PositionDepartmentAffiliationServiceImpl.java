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
package org.kuali.kpme.pm.positiondepartmentaffiliation.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.pm.positiondepartmentaffiliation.PositionDepartmentAffiliation;
import org.kuali.kpme.pm.positiondepartmentaffiliation.dao.PositionDepartmentAffiliationDao;

public class PositionDepartmentAffiliationServiceImpl implements PositionDepartmentAffiliationService {

	private PositionDepartmentAffiliationDao positionDepartmentAffiliationDao;

	/**
	 * @return the positionDepartmentAffiliationDao
	 */
	public PositionDepartmentAffiliationDao getPositionDepartmentAffiliationDao() {
		return positionDepartmentAffiliationDao;
	}

	/**
	 * @param positionDepartmentAffiliationDao the positionDepartmentAffiliationDao to set
	 */
	public void setPositionDepartmentAffiliationDao(
			PositionDepartmentAffiliationDao positionDepartmentAffiliationDao) {
		this.positionDepartmentAffiliationDao = positionDepartmentAffiliationDao;
	}

	@Override
	public PositionDepartmentAffiliation getPositionDepartmentAffiliationById(
			String pmPositionDeptAfflId) {
		return this.positionDepartmentAffiliationDao.getPositionDepartmentAffiliationById(pmPositionDeptAfflId);
	}
	
	@Override
	public List<PositionDepartmentAffiliation> getPositionDepartmentAffiliationList(String positionDeptAfflType, LocalDate asOfDate) {
		return this.positionDepartmentAffiliationDao.getPositionDepartmentAffiliationList(positionDeptAfflType, asOfDate);
	}


}
