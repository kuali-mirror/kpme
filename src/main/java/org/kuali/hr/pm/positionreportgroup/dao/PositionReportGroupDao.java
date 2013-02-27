package org.kuali.hr.pm.positionreportgroup.dao;

import java.sql.Date;

import org.kuali.hr.pm.positionreportgroup.PositionReportGroup;

public interface PositionReportGroupDao {
	
	public PositionReportGroup getPositionReportGroupById(String pmPositionReportGroupId);
	
	public PositionReportGroup getPositionReportGroupByGroupAndDate(String positionReportGroup, Date asOfDate);
}
