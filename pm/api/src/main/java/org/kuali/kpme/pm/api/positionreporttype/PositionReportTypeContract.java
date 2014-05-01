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

import org.kuali.kpme.core.api.mo.KpmeEffectiveKeyedDataTransferObject;

/**
 * <p>PositionReportTypeContract interface</p>
 *
 */
public interface PositionReportTypeContract extends KpmeEffectiveKeyedDataTransferObject {

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
     * The position reportType id associated with the PositionReportType
     *
     * <p>
     * pmPositionReportTypeId of a PositionReportType.
     * <p>
     *
     * @return pmPositionReportTypeId for PositionReportType
     */
	public String getPmPositionReportTypeId();
	
}
