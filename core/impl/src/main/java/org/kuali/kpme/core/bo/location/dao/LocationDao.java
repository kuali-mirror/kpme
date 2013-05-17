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
package org.kuali.kpme.core.bo.location.dao;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.location.Location;

public interface LocationDao {
	/**
	 * Get location as of a particular date
	 * @param location
	 * @param asOfDate
	 * @return
	 */
	public Location getLocation(String location,LocalDate asOfDate);
	/**
	 * Get location by unique id
	 * @param hrLocationId
	 * @return
	 */
	public Location getLocation(String hrLocationId);
	
	public int getLocationCount(String location);

    List<Location> searchLocations(String location, String locationDescr, String active, String showHistory);
}
