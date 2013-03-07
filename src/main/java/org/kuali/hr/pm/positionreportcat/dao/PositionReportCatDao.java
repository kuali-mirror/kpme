package org.kuali.hr.pm.positionreportcat.dao;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.pm.positionreportcat.PositionReportCategory;

public interface PositionReportCatDao {
	
	public PositionReportCategory getPositionReportCatById(String pmPositionReportCatId);
	
	public List<PositionReportCategory> getPositionReportCatList(String positionReportCat, String positionReportType, String institution, String campus, Date asOfDate);
}
