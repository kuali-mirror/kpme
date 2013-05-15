package org.kuali.kpme.pm.positionreportcat.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.pm.positionreportcat.PositionReportCategory;

public interface PositionReportCatService {
	
	/**
	 * retrieve the PositionReportCategory with given id
	 * @param pmPositionReportCatId
	 * @return
	 */
	public PositionReportCategory getPositionReportCatById(String pmPositionReportCatId);
	
	/**
	 * Get List of PositionReportCategory with given category, type and effective date
	 * @param positionReportCat
	 * @param positionReportType
	 * @param institution
	 * @param campus
	 * @param asOfDate
	 * @return
	 */
	public List<PositionReportCategory> getPositionReportCatList(String positionReportCat, String positionReportType, String institution, String campus, LocalDate asOfDate);

}
