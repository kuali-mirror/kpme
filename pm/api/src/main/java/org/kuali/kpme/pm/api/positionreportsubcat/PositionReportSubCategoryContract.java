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
package org.kuali.kpme.pm.api.positionreportsubcat;

import org.kuali.kpme.core.api.mo.KpmeEffectiveDataTransferObject;
import org.kuali.kpme.pm.api.positionreportcat.PositionReportCategoryContract;

/**
 * <p>PositionReportSubCategoryContract interface</p>
 *
 */
public interface PositionReportSubCategoryContract extends KpmeEffectiveDataTransferObject {

    /**
     * The primary key for a PositionReportSubCategory entry saved in the database
     *
     * <p>
     * pmPositionReportSubCatId of a PositionReportSubCategory.
     * <p>
     *
     * @return pmPositionReportSubCatId for PositionReportSubCategory
     */
	public String getPmPositionReportSubCatId();

    /**
     * The text used to identify the PositionReportSubCategory
     *
     * <p>
     * positionReportSubCat of a PositionReportSubCategory.
     * <p>
     *
     * @return positionReportSubCat for PositionReportSubCategory
     */
	public String getPositionReportSubCat();

    /**
     * The position report category associated with the PositionReportSubCategory
     *
     * <p>
     * positionReportCat of a PositionReportSubCategory.
     * <p>
     *
     * @return positionReportCat for PositionReportSubCategory
     */
	public String getPositionReportCat();

    /**
     * The position report type associated with the PositionReportSubCategory
     *
     * <p>
     * positionReportType of a PositionReportSubCategory.
     * <p>
     *
     * @return positionReportType for PositionReportSubCategory
     */
	public String getPositionReportType();

    /**
     * The descriptive text of PositionReportSubCategory that will be defined for a position report category 
     *
     * <p>
     * description of a PositionReportSubCategory.
     * <p>
     *
     * @return description for PositionReportSubCategory
     */
	public String getDescription();

    /**
     * The PositionReportCategory object associated with the PositionReportSubCategory
     *
     * <p>
     * prcObj of a PositionReportSubCategory.
     * <p>
     *
     * @return prcObj for PositionReportSubCategory
     */
	public PositionReportCategoryContract getPrcObj();

}
