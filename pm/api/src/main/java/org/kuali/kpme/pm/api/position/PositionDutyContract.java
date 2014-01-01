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
package org.kuali.kpme.pm.api.position;

import java.math.BigDecimal;

import org.kuali.rice.krad.bo.PersistableBusinessObject;

/**
 * <p>PositionDutyContract interface</p>
 *
 */
public interface PositionDutyContract extends PersistableBusinessObject {

    /**
     * The primary key for a PositionDuty entry saved in the database
     *
     * <p>
     * pmDutyId of a PositinDuty.
     * <p>
     *
     * @return pmDutyId for PositinDuty
     */
	public String getPmDutyId();

    /**
     * The name associated with the PositionDuty
     *
     * <p>
     * name of a PositionDuty
     * <p>
     *
     * @return name for PositionDuty
     */
	public String getName();

    /**
     * The description associated with the PositionDuty
     *
     * <p>
     * description of a PositionDuty.
     * <p>
     *
     * @return description for PositionDuty
     */
	public String getDescription();

    /**
     * The percentage associated with the PositionDuty
     *
     * <p>
     * percentage of a PositionDuty.
     * <p>
     *
     * @return percentage for PositionDuty
     */
	public BigDecimal getPercentage();

    /**
     * The HR position id associated with the PositionDuty
     *
     * <p>
     * hrPositionId of a PositionDuty.
     * <p>
     *
     * @return hrPositionId for PositionDuty
     */
	public String getHrPositionId();
	
}
