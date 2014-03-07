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
package org.kuali.kpme.core.api.location.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.location.Location;
import org.kuali.kpme.core.api.location.LocationContract;
import org.springframework.cache.annotation.Cacheable;

public interface LocationService {
	
	/**
	 * Get location by unique id
	 * @param hrLocationId
	 * @return
	 */
    @Cacheable(value= LocationContract.CACHE_NAME, key="'hrLocationId=' + #p0")
	public Location getLocation(String hrLocationId);
    
	/**
	 * Get location count by location and date
	 * @param location
	 * @param asOfDate
	 * @return
	 */
	public int getLocationCount(String location,  LocalDate asOfDate);
    
	/**
	 * Get location as of a particular date
	 * @param location
	 * @param asOfDate
	 * @return {@link LocationContract}
	 */
    @Cacheable(value= LocationContract.CACHE_NAME, key="'location=' + #p0 + '|' + 'asOfDate=' + #p1")
	public Location getLocation(String location, LocalDate asOfDate);

    public List<Location> searchLocations(String userPrincipalId, String location, String locationDescr, String active, String showHistory);

    public List<Location> getNewerVersionLocation(String location, LocalDate asOfDate);

}