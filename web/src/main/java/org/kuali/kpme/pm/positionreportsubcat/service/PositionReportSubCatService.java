package org.kuali.kpme.pm.positionreportsubcat.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.pm.positionreportsubcat.PositionReportSubCategory;

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
	public List<PositionReportSubCategory> getPositionReportSubCat(String pstnRptSubCat, String institution, String campus, LocalDate asOfDate);

}
