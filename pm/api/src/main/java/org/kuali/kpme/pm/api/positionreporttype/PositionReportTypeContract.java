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
package org.kuali.kpme.pm.api.positionreporttype;


import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;
import org.kuali.kpme.core.api.location.LocationContract;

import org.kuali.kpme.core.api.institution.InstitutionContract;

/**
 * <p>PositionReportTypeContract interface</p>
 *
 */
public interface PositionReportTypeContract extends HrBusinessObjectContract {

    /**
     * The text used to identify the PositionReportType
     *
     * <p>
     * positionReportType of a PositionReportType
     * <p>
     *
     * @return positionReportType for PositionReportType
     */
	public String getPositionReportType();

    /**
     * The descriptive text of the types of reporting categories that will be defined
     *
     * <p>
     * description of a PositionReportType.
     * <p>
     *
     * @return description for PositionReportType
     */
	public String getDescription();

    /**
     * The institution associated with the PositionReportType
     *
     * <p>
     * institution of a PositionReportType.
     * <p>
     *
     * @return institution for PositionReportType
     */
	public String getInstitution();

    /**
     * The position reportType id associated with the PositionReportType
     *
     * <p>
     * pmPositionReportTypeId of a PositionReportType.
     * <p>
     *
     * @return pmPositionReportTypeId for PositionReportType
     */
	public String getPmPositionReportTypeId();

    /**
     * The Institution object associated with the PositionReportType
     *
     * <p>
     * institutionObj of a PositionReportType.
     * <p>
     *
     * @return institutionObj for PositionReportType
     */
	public InstitutionContract getInstitutionObj();

    /**
     * The location associated with the PositionReportType
     *
     * <p>
     * location of a PositionReportType.
     * <p>
     *
     * @return location for PositionReportType
     */
	public String getLocation();

    /**
     * The Location object associated with the PositionReportType
     *
     * <p>
     * locationObj of a PositionReportType.
     * <p>
     *
     * @return locationObj for PositionReportType
     */
	public LocationContract getLocationObj();

}
