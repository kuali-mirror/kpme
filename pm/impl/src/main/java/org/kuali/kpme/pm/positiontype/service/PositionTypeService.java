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
package org.kuali.kpme.pm.positiontype.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.pm.positiontype.PositionType;

public interface PositionTypeService {
	/**
	 * retrieve the PositionType with given id
	 * @param pmPositionTypeId
	 * @return
	 */
	public PositionType getPositionTypeById(String pmPositionTypeId);
	
	/**
	 * Get list of PositionType with given group, institution, campus and effective date
	 * wild card allowed
	 * @param positionType
	 * @param institution
	 * @param campus
	 * @param asOfDate
	 * @return
	 */
	public List<PositionType> getPositionTypeList(String positionType, String institution, String campus, LocalDate asOfDate);
}
