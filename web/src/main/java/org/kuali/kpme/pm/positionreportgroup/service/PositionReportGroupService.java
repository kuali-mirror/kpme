package org.kuali.kpme.pm.positionreportgroup.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.pm.positionreportgroup.PositionReportGroup;

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
	public List<PositionReportGroup> getPositionReportGroupList(String positionReportGroup, String institution, String campus, LocalDate asOfDate);
}
