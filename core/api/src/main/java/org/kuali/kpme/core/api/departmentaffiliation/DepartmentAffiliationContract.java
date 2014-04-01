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
package org.kuali.kpme.core.api.departmentaffiliation;

import org.kuali.kpme.core.api.mo.KpmeEffectiveDataTransferObject;

/**
 * <p>DepartmentAffiliationContract interface</p>
 *
 */
public interface DepartmentAffiliationContract extends KpmeEffectiveDataTransferObject {

    /**
     * The primary key for a DepartmentAffiliation entry saved in the database
     *
     * <p>
     * hrDeptAfflId of a DepartmentAffiliation.
     * <p>
     *
     * @return hrDeptAfflId for DepartmentAffiliation
     */
    public String getHrDeptAfflId();

    /**
     * The name of affiliation types positions may have with departments 
     *
     * <p>
     * deptAfflType of a DepartmentAffiliation.
     * <p>
     *
     * @return deptAfflType for departmentAffiliation
     */
    public String getDeptAfflType();

    /**
     * The flag that indicates the department affiliation is the primary department that could be used to determine department's access to modify position data
     *
     * <p>
     * primaryIndicator of a Department Affiliation.
     * <p>
     *
     * @return primaryIndicator for Department Affiliation
     */
    public boolean isPrimaryIndicator();

}