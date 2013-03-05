package org.kuali.hr.pm.positionreportsubcat.service;

import org.kuali.hr.pm.positionreportsubcat.PositionReportSubCategory;

public interface PositionReportSubCatService {
	
	/**
	 * retrieve the PositionReportSubCategory with given id
	 * @param pmPositionReportSubCatId
	 * @return
	 */
	public PositionReportSubCategory getPositionReportSubCatById(String pmPositionReportSubCatId);

}
