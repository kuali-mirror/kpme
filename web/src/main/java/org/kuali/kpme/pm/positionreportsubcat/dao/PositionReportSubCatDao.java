package org.kuali.kpme.pm.positionreportsubcat.dao;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.pm.positionreportsubcat.PositionReportSubCategory;

public interface PositionReportSubCatDao {
	public PositionReportSubCategory getPositionReportSubCatById(String pmPositionReportSubCatId);
	
	public List<PositionReportSubCategory> getPositionReportSubCat(String pstnRptSubCat, String institution, String campus, LocalDate asOfDate);
}
