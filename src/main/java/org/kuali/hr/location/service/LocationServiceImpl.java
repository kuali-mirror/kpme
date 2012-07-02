package org.kuali.hr.location.service;

import org.kuali.hr.location.Location;
import org.kuali.hr.location.dao.LocationDao;
import org.kuali.hr.time.cache.CacheResult;
import org.kuali.hr.time.util.TkConstants;

import java.sql.Date;

/**
 * Represents an implementation of {@link LocationService}
 * 
 */
public class LocationServiceImpl implements LocationService {

	private LocationDao locationDao;
	@Override
	@CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
	public Location getLocation(String location, Date asOfDate) {
		return locationDao.getLocation(location, asOfDate);
	}
 
	public void setLocationDao(LocationDao locationDao) {
		this.locationDao = locationDao;
	}

	@Override
	@CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
	public Location getLocation(String hrLocationId) {
		return locationDao.getLocation(hrLocationId);
	}
	
	@Override
	public int getLocationCount(String location) {
		return locationDao.getLocationCount(location);
	}

}
