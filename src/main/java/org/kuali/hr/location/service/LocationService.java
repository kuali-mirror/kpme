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
package org.kuali.hr.location.service;

import org.kuali.hr.location.Location;
import org.springframework.cache.annotation.Cacheable;

import java.sql.Date;
import java.util.List;

public interface LocationService {
	
	/**
	 * Get location by unique id
	 * @param hrLocationId
	 * @return
	 */
    @Cacheable(value= Location.CACHE_NAME, key="'hrLocationId=' + #p0")
	public Location getLocation(String hrLocationId);
    
	/**
	 * Get location count by location
	 * @param location
	 * @return
	 */
	public int getLocationCount(String location);
    
	/**
	 * Get location as of a particular date
	 * @param location
	 * @param asOfDate
	 * @return {@link Location}
	 */
    @Cacheable(value= Location.CACHE_NAME, key="'location=' + #p0 + '|' + 'asOfDate=' + #p1")
	public Location getLocation(String location, Date asOfDate);

    List<Location> searchLocations(String location, String locationDescr, String active, String showHistory);

}