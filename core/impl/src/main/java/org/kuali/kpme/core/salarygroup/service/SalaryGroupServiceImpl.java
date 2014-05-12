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
package org.kuali.kpme.core.salarygroup.service;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.salarygroup.SalaryGroup;
import org.kuali.kpme.core.api.salarygroup.service.SalaryGroupService;
import org.kuali.kpme.core.salarygroup.SalaryGroupBo;
import org.kuali.kpme.core.salarygroup.dao.SalaryGroupDao;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

import java.util.List;

public class SalaryGroupServiceImpl implements SalaryGroupService {
	
	private SalaryGroupDao salaryGroupDao;
    private static final ModelObjectUtils.Transformer<SalaryGroupBo, SalaryGroup> toSalaryGroup =
            new ModelObjectUtils.Transformer<SalaryGroupBo, SalaryGroup>() {
                public SalaryGroup transform(SalaryGroupBo input) {
                    return SalaryGroupBo.to(input);
                };
            };
	@Override
	public SalaryGroup getSalaryGroup(String salGroup, LocalDate asOfDate) {
		return SalaryGroupBo.to(salaryGroupDao.getSalaryGroup(salGroup, asOfDate));
	}

	public void setSalaryGroupDao(SalaryGroupDao salaryGroupDao) {
		this.salaryGroupDao = salaryGroupDao;
	}

	@Override
	public SalaryGroup getSalaryGroup(String hrSalGroupId) {
		return SalaryGroupBo.to(salaryGroupDao.getSalaryGroup(hrSalGroupId));
	}
	
	@Override
	public int getSalGroupCount(String salGroup) {
		return salaryGroupDao.getSalGroupCount(salGroup);
	}

    @Override
    public List<SalaryGroup> getSalaryGroups(String salGroup, String institution, String location, String leavePlan, LocalDate fromEffdt, LocalDate toEffdt, String active, String showHist, String benefitEligible, String leaveEligible, String percentTime) {
        return ModelObjectUtils.transform(salaryGroupDao.getSalaryGroups(salGroup, institution, location, leavePlan, fromEffdt, toEffdt, active, showHist, benefitEligible, leaveEligible, percentTime), toSalaryGroup);
    }
}
