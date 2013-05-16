package org.kuali.kpme.pm.positionreportcat.dao;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.pm.positionreportcat.PositionReportCategory;

public interface PositionReportCatDao {
	
	public PositionReportCategory getPositionReportCatById(String pmPositionReportCatId);
	
	public List<PositionReportCategory> getPositionReportCatList(String positionReportCat, String positionReportType, String institution, String campus, LocalDate asOfDate);
}
