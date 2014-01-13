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
package org.kuali.kpme.core.departmentaffiliation.dao;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.departmentaffiliation.DepartmentAffiliation;

public interface DepartmentAffiliationDao {

    public DepartmentAffiliation getDepartmentAffiliationById(String hrDeptAfflId);
    public DepartmentAffiliation getDepartmentAffiliationByType(String deptAfflType);
    public List<DepartmentAffiliation> getDepartmentAffiliationList(String deptAfflType, LocalDate asOfDate);
    public List<DepartmentAffiliation> getAllActiveAffiliations();
    public DepartmentAffiliation getPrimaryAffiliation();

}
