package org.kuali.hr.pm.positionreporttype.service;

import java.sql.Date;

import org.kuali.hr.pm.positionreporttype.PositionReportType;

public interface PositionReportTypeService {
	
	/**
	 * retrieve the PositionReportType with given id
	 * @param pmPositionReportTypeId
	 * @return
	 */
	public PositionReportType getPositionReportTypeById(String pmPositionReportTypeId);
	
	/**
	 * Get the newest active PositionReportType with given type and effective date
	 * @param positionReportType
	 * @param asOfDate
	 * @return
	 */
	public PositionReportType getPositionReportTypeByTypeAndDate(String positionReportType, Date asOfDate);
}
