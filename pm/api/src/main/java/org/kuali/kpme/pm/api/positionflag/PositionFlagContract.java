package org.kuali.kpme.pm.api.positionflag;

import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;

/**
 * <p>PositionFlagContract interface</p>
 *
 */
public interface PositionFlagContract extends HrBusinessObjectContract {

	/**
	 * THe Primary Key that a PositionFlag record will be saved to a database with
	 * 
	 * <p>
	 * pmPositionFlagId of a Flag.
	 * <p>
	 * 
	 * @return pmPositionFlagId for Flag
	 */
	public String getPmPositionFlagId();

	/**
	 * A grouping of PositionFlags
	 * 
	 * <p>
	 * category of a PositionFlag, user will select a category to display all the PositionFlags for that category
	 * <p>
	 * 
	 * @return category for PositionFlag
	 */
	public String getCategory();

	/**
	 * The name of the PositionFlag
	 * 
	 * <p>
	 * A descriptive name for the PositionFlag.
	 * <p>
	 * 
	 * @return positionFlagName for PositionFlag
	 */
	public String getPositionFlagName();

}
