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
package org.kuali.kpme.pm.api.positiontype;

import org.kuali.kpme.core.api.mo.KpmeEffectiveKeyedDataTransferObject;

/**
 * <p>PositionTypeContract interface</p>
 *
 */
public interface PositionTypeContract extends KpmeEffectiveKeyedDataTransferObject {

    /**
     * The primary key for a PositionType entry saved in the database
     *
     * <p>
     * pmPositionTypeId of a PositionType.
     * <p>
     *
     * @return pmPositionTypeId for PositionType
     */
	public String getPmPositionTypeId();

    /**
     * The text field used to identify the PositionType
     *
     * <p>
     * positionType of a PositionType.
     * <p>
     *
     * @return positionType for PositionType
     */
	public String getPositionType();

    /**
     * The description of the associated with the PositionType
     *
     * <p>
     * description of a PositionType.
     * <p>
     *
     * @return description for PositionType
     */
	public String getDescription();

    /**
     * Indicates if this Position Type is academic
     *
     * <p>
     * academicFlag of a PositionType
     * <p>
     *
     * @return academic for PositionType
     */
    public boolean isAcademicFlag();

}