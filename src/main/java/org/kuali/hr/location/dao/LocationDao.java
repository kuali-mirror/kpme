package org.kuali.hr.location.dao;

import java.sql.Date;

import org.kuali.hr.location.Location;

public interface LocationDao {
	public Location getLocation(String location,Date asOfDate);
}
