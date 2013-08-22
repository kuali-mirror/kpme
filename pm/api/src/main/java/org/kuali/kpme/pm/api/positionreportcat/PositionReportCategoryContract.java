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
package org.kuali.kpme.pm.api.positionreportcat;

import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;
import org.kuali.kpme.core.api.location.LocationContract;

import org.kuali.kpme.core.api.institution.InstitutionContract;

import org.kuali.kpme.pm.api.positionreporttype.PositionReportTypeContract;

/**
 * <p>PositionReportCategoryContract interface</p>
 *
 */
public interface PositionReportCategoryContract extends HrBusinessObjectContract {

    /**
     * The position report type associated with the PositionReportCategory 
     *
     * <p>
     * positionReportType of a PositionReportCategory.
     * <p>
     *
     * @return positionReportType for PositionReportCategory
     */
	public String getPositionReportType();

    /**
     * The descriptive text that will be defined for a position report type
     *
     * <p>
     * description of a PositionReportCategory.
     * <p>
     *
     * @return description for PositionReportCategory
     */
	public String getDescription();

    /**
     * The institution associated with the PositionReportCategory
     *
     * <p>
     * institution of a PositionReportCategory
     * <p>
     *
     * @return institution for PositionReportCategory
     */
	public String getInstitution();

    /**
     * The Institution object associated with the PositionReportCategory
     *
     * <p>
     * institution Object of a PositionReportCategory
     * <p>
     *
     * @return institution Object for PositionReportCategory
     */
	public InstitutionContract getInstitutionObj();

    /**
     * The location associated with the PositionReportCategory
     *
     * <p>
     * location of a PositionReportCategory
     * <p>
     *
     * @return location for PositionReportCategory
     */
	public String getLocation();

    /**
     * The Location object associated with the PositionReportCategory
     *
     * <p>
     * location Object of a PositionReportCategory
     * <p>
     *
     * @return location Object for PositionReportCategory
     */
	public LocationContract getLocationObj();

    /**
     * The primary key for a PositionReportCategory entry saved in the database
     *
     * <p>
     * pmPositionReportCatId of a PositionReportCategory
     * <p>
     *
     * @return pmPositionReportCatId for PositionReportCategory
     */
	public String getPmPositionReportCatId();

    /**
     * The text used to identify the PositionReportCategory
     *
     * <p>
     * positionReportCat of a PositionReportCategory
     * <p>
     *
     * @return positionReportCat for PositionReportCategory
     */
	public String getPositionReportCat();

    /**
     * The PositionReportType object associated with the PositionReportCategory
     *
     * <p>
     * prtObj of a PositionReportCategory
     * <p>
     *
     * @return prtObj for PositionReportCategory
     */
	public PositionReportTypeContract getPrtObj();

}
