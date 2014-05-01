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
package org.kuali.kpme.pm.api.positionreportcat.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.pm.api.positionreportcat.PositionReportCategory;

public interface PositionReportCatService {
	
	/**
	 * retrieve the PositionReportCategory with given id
	 * @param pmPositionReportCatId
	 * @return
	 */
	public PositionReportCategory getPositionReportCatById(String pmPositionReportCatId);
	
	/**
	 * Get List of PositionReportCategory with given category, type, groupKeyCode and effective date
	 * @param positionReportCat
	 * @param positionReportType
	 * @param groupKeyCode
	 * @param asOfDate
	 * @return
	 */
	public List<PositionReportCategory> getPositionReportCatList(String positionReportCat, String positionReportType, String groupKeyCode, LocalDate asOfDate);
	
	/**
	 * Retrieve the latest active PositionReportCategory with given positionReportCat and effective date
	 * @param positionReportCat
	 * @param asOfDate
	 * @return
	 */
	public PositionReportCategory getPositionReportCat(String positionReportCat, LocalDate asOfDate);

}
