package org.kuali.hr.pm.positionreportcat.dao;

import java.sql.Date;

import org.kuali.hr.pm.positionreportcat.PositionReportCategory;

public interface PositionReportCatDao {
	
	public PositionReportCategory getPositionReportCatById(String pmPositionReportCatId);
	
	public PositionReportCategory getPositionReportCatByCatAndDate(String positionReportCat, Date asOfDate);
}
