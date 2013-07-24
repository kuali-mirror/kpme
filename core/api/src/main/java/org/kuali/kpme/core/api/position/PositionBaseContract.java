package org.kuali.kpme.core.api.position;

import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;

/**
 * <p>PositionBaseContract interface.</p>
 *
 */
public interface PositionBaseContract extends HrBusinessObjectContract {
	
	/**
	 * The Primary Key of a PositionBase entry saved in a database
	 * 
	 * <p>
	 * hrPositionId of a PositionBase
	 * <p>
	 * 
	 * @return hrPositionId for PositionBase
	 */
	public String getHrPositionId();

	/**
	 * The position number of a PositionBase
	 * 
	 * <p>
	 * positionNumber of a PositionBase
	 * <p>
	 * 
	 * @return positionNumber for PositionBase
	 */
	public String getPositionNumber();
	
	/**
	 * The description of a PositionBase
	 * 
	 * <p>
	 * description of a PositionBase
	 * <p>
	 * 
	 * @return description for PositionBase
	 */
	public String getDescription();

	 /**
	 * History flag for PositionBase lookups 
	 * 
	 * <p>
	 * history of PositionBase
	 * </p>
	 * 
	 * @return true if want to show history, false if not
	 */
	public String getHistory();
}
