package org.kuali.hr.pm.positionreportsubcat.dao;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.pm.positionreportsubcat.PositionReportSubCategory;

public interface PositionReportSubCatDao {
	public PositionReportSubCategory getPositionReportSubCatById(String pmPositionReportSubCatId);
	
	public List<PositionReportSubCategory> getPositionReportSubCat(String pstnRptSubCat, String institution, String campus, Date asOfDate);
}
