package org.kuali.hr.location.service;

import org.kuali.hr.location.Location;
import org.kuali.hr.location.dao.LocationDao;

import java.sql.Date;

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

}
