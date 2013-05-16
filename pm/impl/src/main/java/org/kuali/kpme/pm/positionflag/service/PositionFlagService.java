package org.kuali.kpme.pm.positionflag.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.pm.positionflag.PositionFlag;

public interface PositionFlagService {
	/**
	 * retrieve list of categories of active PositionFlags
	 * @return
	 */
	public List<String> getAllActiveFlagCategories();
	/**
	 * retrieve list of active PositionFlag with given parameters
	 * @param category
	 * @param name
	 * @param effDate
	 * @return
	 */
	public List<PositionFlag> getAllActivePositionFlags(String category, String name, LocalDate effDate);
	
	public List<PositionFlag> getAllActivePositionFlagsWithCategory(String category, LocalDate effDate);
	/**
     * retrieve the PositionFlag with given id
	 * @param pmPositionFlagId
	 * @return
	 */
	public PositionFlag getPositionFlagById(String pmPositionFlagId);
}
