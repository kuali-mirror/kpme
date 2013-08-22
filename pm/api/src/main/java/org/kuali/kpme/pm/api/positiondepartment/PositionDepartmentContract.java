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
package org.kuali.kpme.pm.api.positiondepartment;


import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;
import org.kuali.kpme.core.api.department.DepartmentContract;
import org.kuali.kpme.core.api.institution.InstitutionContract;
import org.kuali.kpme.core.api.location.LocationContract;

import org.kuali.kpme.pm.api.positiondepartmentaffiliation.PositionDepartmentAffiliationContract;

/**
 * <p>PositionDepartmentContract interface</p>
 *
 */
public interface PositionDepartmentContract extends HrBusinessObjectContract {

    /**
     * The position department affiliation associated with the PositionDepartment
     *
     * <p>
     * positionDeptAffl for the PositionDepartment.
     * <p>
     *
     * @return positionDeptAffl for PositionDepartment
     */
	public String getPositionDeptAffl();

    /**
     * The PositionDepartmentAffiliation object associated with the PositionDepartment
     *
     * <p>
     * positionDeptAfflObj for the PositionDepartment.
     * <p>
     *
     * @return positionDeptAfflObj for PositionDepartment
     */
	public PositionDepartmentAffiliationContract getPositionDeptAfflObj();

    /**
     * The primary key for a PositionDepartment entry saved in the database
     *
     * <p>
     * pmPositionDeptId for the PositionDepartment.
     * <p>
     *
     * @return pmPositionDeptId for PositionDepartment
     */
	public String getPmPositionDeptId();

    /**
     * The institution associated with the PositionDepartment
     *
     * <p>
     * institution for the PositionDepartment.
     * <p>
     *
     * @return institution for PositionDepartment
     */
	public String getInstitution();

    /**
     * The location associated with the PositionDepartment
     *
     * <p>
     * location for the PositionDepartment.
     * <p>
     *
     * @return location for PositionDepartment
     */
	public String getLocation();

    /**
     * The Location object associated with the PositionDepartment
     *
     * <p>
     * locationObj for the PositionDepartment.
     * <p>
     *
     * @return locationObj for PositionDepartment
     */
	public LocationContract getLocationObj();

    /**
     * The department name associated with the PositionDepartment
     *
     * <p>
     * department for the PositionDepartment.
     * <p>
     *
     * @return department for PositionDepartment
     */
	public String getDepartment();

    /**
     * The Institution object associated with the PositionDepartment
     *
     * <p>
     * institutionObj for the PositionDepartment.
     * <p>
     *
     * @return institutionObj for PositionDepartment
     */
	public InstitutionContract getInstitutionObj();

    /**
     * The Department object associated with the PositionDepartment
     *
     * <p>
     * departmentObj for the PositionDepartment.
     * <p>
     *
     * @return departmentObj for PositionDepartment
     */
	public DepartmentContract getDepartmentObj();

}