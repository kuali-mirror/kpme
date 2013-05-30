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
package org.kuali.kpme.pm.positionreportsubcat.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.pm.positionreportsubcat.PositionReportSubCategory;

public interface PositionReportSubCatService {
	
	/**
	 * retrieve the PositionReportSubCategory with given id
	 * @param pmPositionReportSubCatId
	 * @return
	 */
	public PositionReportSubCategory getPositionReportSubCatById(String pmPositionReportSubCatId);
	
	/**
	 * retrieve list of active PositionReportSubCategory with given pstnRptSubCat, institution, location and effective date
	 * wild card allowed
	 * @param pstnRptSubCat
	 * @param institution
	 * @param location
	 * @param asOfDate
	 * @return
	 */
	public List<PositionReportSubCategory> getPositionReportSubCat(String pstnRptSubCat, String institution, String location, LocalDate asOfDate);

}
