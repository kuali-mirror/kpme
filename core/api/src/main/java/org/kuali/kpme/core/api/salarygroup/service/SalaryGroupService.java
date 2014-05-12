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
package org.kuali.kpme.core.api.salarygroup.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.salarygroup.SalaryGroup;
import org.kuali.kpme.core.api.salarygroup.SalaryGroupContract;
import org.springframework.cache.annotation.Cacheable;

public interface SalaryGroupService {
	/**
	 * Fetch a SalaryGroup as of a particular date
	 * @param salGroup
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= SalaryGroupContract.CACHE_NAME, key="'salGroup=' + #p0 + '|' + 'asOfDate=' + #p1")
	public SalaryGroup getSalaryGroup(String salGroup, LocalDate asOfDate);

    @Cacheable(value= SalaryGroupContract.CACHE_NAME, key="'hrSalGroupId=' + #p0")
	public SalaryGroup getSalaryGroup(String hrSalGroupId);
	
	public int getSalGroupCount(String salGroup);

    List<SalaryGroup> getSalaryGroups(String salGroup, String institution, String location, String leavePlan, LocalDate fromEffdt, LocalDate toEffdt, String active, String showHist, String benefitEligible, String leaveEligible, String percentTime);
}
