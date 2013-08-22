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
package org.kuali.kpme.pm.api.positiondepartmentaffiliation;

import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;

/**
 * <p>PositionDepartmentAffiliationContract interface</p>
 *
 */
public interface PositionDepartmentAffiliationContract extends HrBusinessObjectContract {

    /**
     * The primary key for a PositionDepartmentAffiliation entry saved in the database
     *
     * <p>
     * pmPositionDeptAfflId of a PositionDepartmentAffiliation.
     * <p>
     *
     * @return pmPositionDeptAfflId for PositionDepartmentAffiliation
     */
	public String getPmPositionDeptAfflId();

    /**
     * The name of affiliation types positions may have with departments 
     *
     * <p>
     * positionDeptAfflType of a PositionDepartmentAffiliation.
     * <p>
     *
     * @return positionDeptAfflType for PositionDepartmentAffiliation
     */
	public String getPositionDeptAfflType();

    /**
     * The flag that indicates the department affiliation is the primary department that could be used to determine department's access to modify position data
     *
     * <p>
     * primaryIndicator of a Position Department Affiliation.
     * <p>
     *
     * @return primaryIndicator for Position Department Affiliation
     */
	public boolean isPrimaryIndicator();

}