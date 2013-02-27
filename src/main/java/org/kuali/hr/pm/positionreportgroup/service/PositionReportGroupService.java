package org.kuali.hr.pm.positionreportgroup.service;

import java.sql.Date;

import org.kuali.hr.pm.positionreportgroup.PositionReportGroup;

public interface PositionReportGroupService {
	/**
	 * retrieve the PositionReportGroup with given id
	 * @param pmPositionReportGroupId
	 * @return
	 */
	public PositionReportGroup getPositionReportGroupById(String pmPositionReportGroupId);
	
	/**
	 * Get the newest active PositionReportGroup with given group and effective date
	 * @param positionReportGroup
	 * @param asOfDate
	 * @return
	 */
	public PositionReportGroup getPositionReportGroupByGroupAndDate(String positionReportGroup, Date asOfDate);
}
