package org.kuali.hr.pm.positionreportcat.service;

import java.sql.Date;

import org.kuali.hr.pm.positionreportcat.PositionReportCategory;

public interface PositionReportCatService {
	
	/**
	 * retrieve the PositionReportCategory with given id
	 * @param pmPositionReportCatId
	 * @return
	 */
	public PositionReportCategory getPositionReportCatById(String pmPositionReportCatId);
	
	/**
	 * Get the newest active PositionReportCategory with given type and effective date
	 * @param positionReportCat
	 * @param asOfDate
	 * @return
	 */
	public PositionReportCategory getPositionReportCatByCatAndDate(String positionReportCat, Date asOfDate);

}
