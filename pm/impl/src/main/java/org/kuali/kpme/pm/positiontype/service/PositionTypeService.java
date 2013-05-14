package org.kuali.kpme.pm.positiontype.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.pm.positiontype.PositionType;

public interface PositionTypeService {
	/**
	 * retrieve the PositionType with given id
	 * @param pmPositionTypeId
	 * @return
	 */
	public PositionType getPositionTypeById(String pmPositionTypeId);
	
	/**
	 * Get list of PositionType with given group, institution, campus and effective date
	 * wild card allowed
	 * @param positionType
	 * @param institution
	 * @param campus
	 * @param asOfDate
	 * @return
	 */
	public List<PositionType> getPositionTypeList(String positionType, String institution, String campus, LocalDate asOfDate);
}
