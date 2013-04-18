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
package org.kuali.hr.lm.employeeoverride.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.hr.lm.employeeoverride.EmployeeOverride;
import org.kuali.hr.lm.employeeoverride.dao.EmployeeOverrideDao;

public class EmployeeOverrideServiceImpl implements EmployeeOverrideService {
	
	private EmployeeOverrideDao employeeOverrideDao;

	@Override
	public List<EmployeeOverride> getEmployeeOverrides(String principalId, LocalDate asOfDate) {
		return employeeOverrideDao.getEmployeeOverrides(principalId, asOfDate);
	}
	
	@Override
	public EmployeeOverride getEmployeeOverride(String principalId, String leavePlan, String accrualCategory, String overrideType, LocalDate asOfDate) {
		return employeeOverrideDao.getEmployeeOverride(principalId, leavePlan, accrualCategory, overrideType, asOfDate);
	}

	@Override
	public EmployeeOverride getEmployeeOverride(String lmEmployeeOverrideId) {
		return employeeOverrideDao.getEmployeeOverride(lmEmployeeOverrideId);
	}

    @Override
    public List<EmployeeOverride> getEmployeeOverrides(String principalId, String leavePlan, String accrualCategory, String overrideType, LocalDate fromEffdt, LocalDate toEffdt, String active) {
        return employeeOverrideDao.getEmployeeOverrides(principalId, leavePlan, accrualCategory, overrideType, fromEffdt, toEffdt, active);
    }

    public EmployeeOverrideDao getEmployeeOverrideDao() {
		return employeeOverrideDao;
	}

	public void setEmployeeOverrideDao(EmployeeOverrideDao employeeOverrideDao) {
		this.employeeOverrideDao = employeeOverrideDao;
	}

}
