package org.kuali.hr.pm.positionreportgroup.dao;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.hr.pm.positionreportgroup.PositionReportGroup;

public interface PositionReportGroupDao {
	
	public PositionReportGroup getPositionReportGroupById(String pmPositionReportGroupId);
	
	public List<PositionReportGroup> getPositionReportGroupList(String positionReportGroup, String institution, String campus, LocalDate asOfDate);
}
