package org.kuali.hr.pm.positionreportgroup.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.pm.positionreportgroup.PositionReportGroup;

public interface PositionReportGroupService {
	/**
	 * retrieve the PositionReportGroup with given id
	 * @param pmPositionReportGroupId
	 * @return
	 */
	public PositionReportGroup getPositionReportGroupById(String pmPositionReportGroupId);
	
	/**
	 * Get list of PositionReportGroup with given group, institution, campus and effective date
	 * wild card allowed
	 * @param positionReportGroup
	 * @param institution
	 * @param campus
	 * @param asOfDate
	 * @return
	 */
	public List<PositionReportGroup> getPositionReportGroupList(String positionReportGroup, String institution, String campus, Date asOfDate);
}
