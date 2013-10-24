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
package org.kuali.kpme.pm.api.positionresponsibility;

import java.math.BigDecimal;

import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;
import org.kuali.rice.location.api.campus.CampusContract;

/**
 * <p>PositionResponsibilityContract interface</p>
 *
 */
public interface PositionResponsibilityContract extends HrBusinessObjectContract {

    /**
     * The primary key for a PositionResponsibility entry saved in the database
     *
     * <p>
     * positionResponsibilityId of a PositionResponsibility.
     * <p>
     *
     * @return positionResponsibilityId for PositionResponsibility
     */
	public String getPositionResponsibilityId();

    /**
     * The institution associated with the PositionResponsibility
     *
     * <p>
     * institution of a PositionResponsibility.
     * <p>
     *
     * @return institution for PositionResponsibility
     */
	public String getInstitution();

    /**
     * The position responsibility Option associated with the PositionResponsibility
     *
     * <p>
     * positionresponsibilityoption of a PositionResponsibility.
     * <p>
     *
     * @return positionresponsibilityoption for PositionResponsibility
     */
	public String getPositionResponsibilityOption();

    /**
     * The percentage of time spent on selected position responsibility
     *
     * <p>
     * percentTime of a PositionResponsibility.
     * <p>
     *
     * @return percentTime for PositionResponsibility
     */
	public BigDecimal getPercentTime();


    /**
     * The Campus business object associated with the PositionResponsibility
     *
     * <p>
     * campusObj of a PositionResponsibility.
     * <p>
     *
     * @return campusObj for PositionResponsibility
     */
	public CampusContract getCampusObj();

    /**
     * The HR position id associated with the PositionResponsibility
     *
     * <p>
     * getHrPositionId of a PositionResponsibility.
     * <p>
     *
     * @return getHrPositionId for PositionResponsibility
     */
	public String getHrPositionId();

    /**
     * The location associated with the PositionResponsibility
     *
     * <p>
     * location of a PositionResponsibility.
     * <p>
     *
     * @return location for PositionResponsibility
     */
	public String getLocation();

}
