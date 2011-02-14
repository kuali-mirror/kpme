package org.kuali.hr.location.service;

import java.sql.Date;

import org.kuali.hr.location.Location;

public interface LocationService {
	public Location getLocation(String location, Date asOfDate);
}
