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
package org.kuali.kpme.pm.api.positionreportcat.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.pm.api.positionreportcat.PositionReportCategoryContract;

public interface PositionReportCatService {
	
	/**
	 * retrieve the PositionReportCategory with given id
	 * @param pmPositionReportCatId
	 * @return
	 */
	public PositionReportCategoryContract getPositionReportCatById(String pmPositionReportCatId);
	
	/**
	 * Get List of PositionReportCategory with given category, type and effective date
	 * @param positionReportCat
	 * @param positionReportType
	 * @param institution
	 * @param location
	 * @param asOfDate
	 * @return
	 */
	public List<? extends PositionReportCategoryContract> getPositionReportCatList(String positionReportCat, String positionReportType, String institution, String location, LocalDate asOfDate);
	
	/**
	 * Retrieve the latest active PositionReportCategory with given positionReportCat and effective date
	 * @param positionReportCat
	 * @param asOfDate
	 * @return
	 */
	public PositionReportCategoryContract getPositionReportCat(String positionReportCat, LocalDate asOfDate);

}
