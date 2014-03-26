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
package org.kuali.kpme.tklm.api.leave.override;

import org.joda.time.LocalDate;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface EmployeeOverrideService {
    @Cacheable(value= EmployeeOverrideContract.CACHE_NAME, key="'{getEmployeeOverrides}' + 'principalId=' + #p0 + '|' + 'asOfDate=' + #p1")
	public List<? extends EmployeeOverrideContract> getEmployeeOverrides(String principalId, LocalDate asOfDate);

    @Cacheable(value= EmployeeOverrideContract.CACHE_NAME, key="'{getEmployeeOverride}' + 'principalId=' + #p0 + '|' + 'leavePlan=' + #p1 + '|' + 'overrideType=' + #p2 + '|' + 'asOfDate=' + #p3")
	public EmployeeOverrideContract getEmployeeOverride(String principalId, String leavePlan, String accrualCategory, String overrideType, LocalDate asOfDate);

    @Cacheable(value= EmployeeOverrideContract.CACHE_NAME, key="'lmEmployeeOverrideId=' + #p0")
	public EmployeeOverrideContract getEmployeeOverride(String lmEmployeeOverrideId);

    List<? extends EmployeeOverrideContract> getEmployeeOverrides(String principalId, String leavePlan, String accrualCategory, String overrideType, LocalDate fromEffdt, LocalDate toEffdt, String active);
}
