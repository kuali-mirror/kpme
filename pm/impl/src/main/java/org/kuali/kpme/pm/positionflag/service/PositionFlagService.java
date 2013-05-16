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
package org.kuali.kpme.pm.positionflag.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.pm.positionflag.PositionFlag;

public interface PositionFlagService {
	/**
	 * retrieve list of categories of active PositionFlags
	 * @return
	 */
	public List<String> getAllActiveFlagCategories();
	/**
	 * retrieve list of active PositionFlag with given parameters
	 * @param category
	 * @param name
	 * @param effDate
	 * @return
	 */
	public List<PositionFlag> getAllActivePositionFlags(String category, String name, LocalDate effDate);
	
	public List<PositionFlag> getAllActivePositionFlagsWithCategory(String category, LocalDate effDate);
	/**
     * retrieve the PositionFlag with given id
	 * @param pmPositionFlagId
	 * @return
	 */
	public PositionFlag getPositionFlagById(String pmPositionFlagId);
}
