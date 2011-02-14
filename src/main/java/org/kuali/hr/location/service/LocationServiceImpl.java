package org.kuali.hr.location.service;

import java.sql.Date;

import org.kuali.hr.location.Location;
import org.kuali.hr.location.dao.LocationDao;

public class LocationServiceImpl implements LocationService {

	private LocationDao locationDao;
	@Override
	public Location getLocation(String location, Date asOfDate) {
		// TODO Auto-generated method stub
		return locationDao.getLocation(location, asOfDate);
	}
 
	public void setLocationDao(LocationDao locationDao) {
		this.locationDao = locationDao;
	}

}
