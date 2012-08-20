package org.kuali.hr.location.service;

import org.kuali.hr.location.Location;
import org.springframework.cache.annotation.Cacheable;

import java.sql.Date;

public interface LocationService {
	/**
	 * Get location as of a particular date
	 * @param location
	 * @param asOfDate
	 * @return {@link Location}
	 */
    @Cacheable(value= Location.CACHE_NAME, key="'location=' + #p0 + '|' + 'asOfDate=' + #p1")
	public Location getLocation(String location, Date asOfDate);
	/**
	 * Get location by unique id
	 * @param hrLocationId
	 * @return
	 */
    @Cacheable(value= Location.CACHE_NAME, key="'leavePlan=' + #p0")
	public Location getLocation(String hrLocationId);
	/**
	 * Get location count by location
	 * @param location
	 * @return
	 */
	public int getLocationCount(String location);
}
