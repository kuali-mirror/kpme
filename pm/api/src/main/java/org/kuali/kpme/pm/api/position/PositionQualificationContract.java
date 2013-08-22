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
package org.kuali.kpme.pm.api.position;


import org.kuali.rice.krad.bo.PersistableBusinessObject;

/**
 * <p>PositionQualificationContract interface</p>
 *
 */
public interface PositionQualificationContract extends PersistableBusinessObject {

    /**
     * The qualification type associated with the PositionQualification
     *
     * <p>
     * qualificationType of a PositionQualification.
     * <p>
     *
     * @return qualificationType for PositionQualification
     */
	public String getQualificationType();

    /**
     * The qualifier associated with the PositionQualification
     *
     * <p>
     * qualifier of a PositionQualification.
     * <p>
     *
     * @return qualifier for PositionQualification
     */
    public String getQualifier();

    /**
     * The qualification value associated with the PositionQualification
     *
     * <p>
     * qualificationValue of a PositionQualification.
     * <p>
     *
     * @return qualificationValue for PositionQualification
     */
	public String getQualificationValue();

    /**
     * The type value associated with the PositionQualification (could be an empty string)
     *
     * <p>
     * typeValue of a PositionQualification.
     * <p>
     *
     * @return typeValue for PositionQualification
     */
	public String getTypeValue();

    /**
     * The primary key for a PositionQualification entry saved in the database
     *
     * <p>
     * pmQualificationId of a PositionQualification.
     * <p>
     *
     * @return pmQualificationId for PositionQualification
     */
	public String getPmQualificationId();

    /**
     * The HR position id associated with the PositionQualification
     *
     * <p>
     * hrPositionId of a PositionQualification.
     * <p>
     *
     * @return hrPositionId for PositionQualification
     */
	public String getHrPositionId();

}
