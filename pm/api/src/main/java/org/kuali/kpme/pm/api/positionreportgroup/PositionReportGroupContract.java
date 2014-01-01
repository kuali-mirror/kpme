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
package org.kuali.kpme.pm.api.positionreportgroup;


import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;
import org.kuali.kpme.core.api.location.LocationContract;
import org.kuali.kpme.core.api.institution.InstitutionContract;

/**
 * <p>PositionReportGroupContract interface</p>
 *
 */
public interface PositionReportGroupContract extends HrBusinessObjectContract {

    /**
     * The primary key for a PositionReportGroup entry saved in the database
     *
     * <p>
     * pmPositionReportGroupId of a Group.
     * <p>
     *
     * @return pmPositionReportGroupId for Group
     */
	public String getPmPositionReportGroupId();

    /**
     * The text used to identify the PositionReportGroup
     *
     * <p>
     * positionReportGroup of a PositionReportGroup.
     * <p>
     *
     * @return positionReportGroup for PositionReportGroup
     */
	public String getPositionReportGroup();

    /**
     * The descriptive text of the PositionReportingGroup
     *
     * <p>
     * description of a PositionReportGroup.
     * <p>
     *
     * @return description for PositionReportGroup
     */
	public String getDescription();

    /**
     * The institution associated with the PositionReportGroup
     *
     * <p>
     * institution of a PositionReportGroup.
     * <p>
     *
     * @return institution for PositionReportGroup
     */
	public String getInstitution();

    /**
     * The location associated with the PositionReportGroup
     *
     * <p>
     * location of a PositionReportGroup.
     * <p>
     *
     * @return location for PositionReportGroup
     */
	public String getLocation();

    /**
     * The Location object associated with the PositionReportGroup
     *
     * <p>
     * locationObj of a PositionReportGroup.
     * <p>
     *
     * @return locationObj for PositionReportGroup
     */
	public LocationContract getLocationObj();

    /**
     * The Institution object associated with the PositionReportGroup
     *
     * <p>
     * institutionObj of a PositionReportGroup.
     * <p>
     *
     * @return institutionObj for PositionReportGroup
     */
	public InstitutionContract getInstitutionObj();

}
