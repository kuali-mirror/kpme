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
package org.kuali.kpme.pm.api.positiondepartment;


import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.kpme.core.api.departmentaffiliation.DepartmentAffiliationContract;
import org.kuali.kpme.core.api.institution.InstitutionContract;
import org.kuali.kpme.core.api.location.LocationContract;




public interface PositionDepartmentContract extends PersistableBusinessObject {


    /**
     * Department Affiliation
     *
     * <p>
     * feptAffl for the Position Department.
     * <p>
     *
     * @return deptAffl for Position Department
     */
    public String getDeptAffl();

    /**
     * Department Affiliation   Object
     *
     * <p>
     * deptAfflObj for the Position Department.
     * <p>
     *
     * @return deptAfflObj for Position Department
     */
    public DepartmentAffiliationContract getDeptAfflObj();

    /**
     * Position Department ID
     *
     * <p>
     * pmPositionDeptId for the Position Department.
     * <p>
     *
     * @return pmPositionDeptId for Position Department
     */
    public String getPmPositionDeptId();

    /**
     * Position Department Institution
     *
     * <p>
     * institution for the Position Department.
     * <p>
     *
     * @return institution for Position Department
     */
    public String getInstitution();

    /**
     * Position Department Location
     *
     * <p>
     * location for the Position Department.
     * <p>
     *
     * @return location for Position Department
     */
    public String getLocation();

    /**
     * Position Department Location Object
     *
     * <p>
     * locationObj for the Position Department.
     * <p>
     *
     * @return locationObj for Position Department
     */
    public LocationContract getLocationObj();

    /**
     * Position Department Department
     *
     * <p>
     * department for the Position Department.
     * <p>
     *
     * @return department for Position Department
     */
    public String getDepartment();

    /**
     * Position Department Institution
     *
     * <p>
     * institutionObj for the Position Department.
     * <p>
     *
     * @return institutionObj for Position Department
     */
    public InstitutionContract getInstitutionObj();

    /**
     * Position Department Department Object
     *
     * <p>
     * departmentObj for the Position Department.
     * <p>
     *
     * @return departmentObj for Position Department
     */
    //public Department getDepartmentObj();
    //TODO find correct contract



    /**
     * Position Department HrPositionId
     *
     * <p>
     * hrPositionId for the Position Department collection in Position.
     * <p>
     *
     * @return hrPositionId for Position Department
     */
    public String getHrPositionId();



}