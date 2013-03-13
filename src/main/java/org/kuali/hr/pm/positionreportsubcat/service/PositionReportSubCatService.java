package org.kuali.hr.pm.positionreportsubcat.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.pm.positionreportsubcat.PositionReportSubCategory;

public interface PositionReportSubCatService {
	
	/**
	 * retrieve the PositionReportSubCategory with given id
	 * @param pmPositionReportSubCatId
	 * @return
	 */
	public PositionReportSubCategory getPositionReportSubCatById(String pmPositionReportSubCatId);
	
	/**
	 * retrieve list of active PositionReportSubCategory with given pstnRptSubCat, institution, campus and effective date
	 * wild card allowed
	 * @param pstnRptSubCat
	 * @param institution
	 * @param campus
	 * @param asOfDate
	 * @return
	 */
	public List<PositionReportSubCategory> getPositionReportSubCat(String pstnRptSubCat, String institution, String campus, Date asOfDate);

}
