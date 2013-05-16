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
package org.kuali.kpme.pm.positionreportcat.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.pm.positionreportcat.PositionReportCategory;

public interface PositionReportCatService {
	
	/**
	 * retrieve the PositionReportCategory with given id
	 * @param pmPositionReportCatId
	 * @return
	 */
	public PositionReportCategory getPositionReportCatById(String pmPositionReportCatId);
	
	/**
	 * Get List of PositionReportCategory with given category, type and effective date
	 * @param positionReportCat
	 * @param positionReportType
	 * @param institution
	 * @param campus
	 * @param asOfDate
	 * @return
	 */
	public List<PositionReportCategory> getPositionReportCatList(String positionReportCat, String positionReportType, String institution, String campus, LocalDate asOfDate);

}
