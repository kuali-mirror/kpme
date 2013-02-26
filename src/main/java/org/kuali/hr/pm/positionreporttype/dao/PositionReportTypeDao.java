package org.kuali.hr.pm.positionreporttype.dao;

import java.sql.Date;

import org.kuali.hr.pm.positionreporttype.PositionReportType;

public interface PositionReportTypeDao {
	
	public PositionReportType getPositionReportTypeById(String pmPositionReportTypeId);
	
	public PositionReportType getPositionReportTypeByTypeAndDate(String positionReportType, Date asOfDate);

}
