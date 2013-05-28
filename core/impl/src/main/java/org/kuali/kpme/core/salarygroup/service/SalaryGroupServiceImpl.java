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
package org.kuali.kpme.core.salarygroup.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.salarygroup.SalaryGroup;
import org.kuali.kpme.core.salarygroup.dao.SalaryGroupDao;

public class SalaryGroupServiceImpl implements SalaryGroupService {
	
	private SalaryGroupDao salaryGroupDao;

	@Override
	public SalaryGroup getSalaryGroup(String salGroup, LocalDate asOfDate) {
		return salaryGroupDao.getSalaryGroup(salGroup, asOfDate);
	}

	public void setSalaryGroupDao(SalaryGroupDao salaryGroupDao) {
		this.salaryGroupDao = salaryGroupDao;
	}

	@Override
	public SalaryGroup getSalaryGroup(String hrSalGroupId) {
		return salaryGroupDao.getSalaryGroup(hrSalGroupId);
	}
	
	@Override
	public int getSalGroupCount(String salGroup) {
		return salaryGroupDao.getSalGroupCount(salGroup);
	}

    @Override
    public List<SalaryGroup> getSalaryGroups(String salGroup, String descr, LocalDate fromEffdt, LocalDate toEffdt, String active, String showHist) {
        return salaryGroupDao.getSalaryGroups(salGroup, descr, fromEffdt, toEffdt, active, showHist);
    }
}
