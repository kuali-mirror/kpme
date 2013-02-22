package org.kuali.hr.pm.positionreporttype.service;

import org.kuali.hr.pm.positionreporttype.PositionReportType;

public interface PositionReportTypeService {
	
	/**
	 * retrieve the PositionReportType with given id
	 * @param pmPositionReportTypeId
	 * @return
	 */
	public PositionReportType getPositionReportTypeById(String pmPositionReportTypeId);

}
