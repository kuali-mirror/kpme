package org.kuali.hr.location.service;

import java.sql.Date;

import org.kuali.hr.location.Location;

public interface LocationService {
	/**
	 * Get location as of a particular date
	 * @param location
	 * @param asOfDate
	 * @return {@link Location}
	 */
	public Location getLocation(String location, Date asOfDate);
	
	public Location getLocation(Long hrLocationId);
}
