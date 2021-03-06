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
package org.kuali.kpme.pm.api.pstnrptgrpsubcat;

import org.kuali.kpme.core.api.mo.KpmeEffectiveDataTransferObject;

/**
 * <p>PositionReportGroupSubCategoryContract interface</p>
 *
 */
public interface PositionReportGroupSubCategoryContract extends KpmeEffectiveDataTransferObject {

	/**
	 * The primary key of a PositionReportGroupSubCategory entry saved in a database
	 * 
	 * <p>
	 * pmPstnRptGrpSubCatId of a PositionReportGroupSubCategory
	 * <p>
	 * 
	 * @return pmPstnRptGrpSubCatId for PositionReportGroupSubCategory
	 */
	public String getPmPstnRptGrpSubCatId();

	/**
	 * The text used to identify the PositionReportGroupSubCategory
	 * 
	 * <p>
	 * pstnRptGrpSubCat of a PositionReportGroupSubCategory
	 * <p>
	 * 
	 * @return pstnRptGrpSubCat for PositionReportGroupSubCategory
	 */
	public String getPstnRptGrpSubCat();

	/**
	 * The position reporting group associated with the PositionReportGroupSubCategory
	 * 
	 * <p>
	 * positionReportGroup of a PositionReportGroupSubCategory
	 * <p>
	 * 
	 * @return positionReportGroup for PositionReportGroupSubCategory
	 */
	public String getPositionReportGroup();

	/**
	 * The position report sub category associated with the PositionReportGroupSubCategory
	 * 
	 * <p>
	 * positionReportSubCat of a PositionReportGroupSubCategory
	 * <p>
	 * 
	 * @return positionReportSubCat for PositionReportGroupSubCategory
	 */
	public String getPositionReportSubCat();
	
	/**
	 * The descriptive text for the PositionReportGroupSubCategory
	 * 
	 * <p>
	 * description of a PositionReportGroupSubCategory
	 * <p>
	 * 
	 * @return description for PositionReportGroupSubCategory
	 */
	public String getDescription();
	
}