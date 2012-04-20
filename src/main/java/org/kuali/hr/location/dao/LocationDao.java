package org.kuali.hr.location.dao;

import java.sql.Date;

import org.kuali.hr.location.Location;

public interface LocationDao {
	/**
	 * Get location as of a particular date
	 * @param location
	 * @param asOfDate
	 * @return
	 */
	public Location getLocation(String location,Date asOfDate);
	/**
	 * Get location by unique id
	 * @param hrLocationId
	 * @return
	 */
	public Location getLocation(String hrLocationId);
	
	public int getLocationCount(String location);
}
