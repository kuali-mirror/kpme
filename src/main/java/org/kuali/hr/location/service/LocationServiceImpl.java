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
import org.kuali.hr.location.dao.LocationDao;

import java.sql.Date;
import java.util.List;

/**
 * Represents an implementation of {@link LocationService}
 * 
 */
public class LocationServiceImpl implements LocationService {

	private LocationDao locationDao;
	@Override
	public Location getLocation(String location, Date asOfDate) {
		return locationDao.getLocation(location, asOfDate);
	}
 
	public void setLocationDao(LocationDao locationDao) {
		this.locationDao = locationDao;
	}

	@Override
	public Location getLocation(String hrLocationId) {
		return locationDao.getLocation(hrLocationId);
	}
	
	@Override
	public int getLocationCount(String location) {
		return locationDao.getLocationCount(location);
	}

    @Override
    public List<Location> searchLocations(String location, String locationDescr, String active, String showHistory) {
        return locationDao.searchLocations(location, locationDescr, active, showHistory);
    }
}
