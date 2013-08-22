package org.kuali.kpme.pm.api.pstnqlfctnvl;

import org.kuali.rice.krad.bo.PersistableBusinessObject;

/**
 * <p>PositionQualificationValueContract interface</p>
 *
 */
public interface PositionQualificationValueContract extends PersistableBusinessObject {

	/**
	 * The value name associated with the PositionQualificationValue 
	 * 
	 * <p>
	 * vlName of a PositionQualificationValue.
	 * <p>
	 * 
	 * return vlName for PositionQualificationValue
	 */
	public String getVlName();
		
	/**
	 * The primary key for a PositionQualificationValue entry saved in the database
	 * 
	 * <p>
	 * pmPstnQlfctnVlId of a PositionQualificationValue.
	 * <p>
	 * 
	 * return pmPstnQlfctnVlId for PositionQualificationValue
	 */
	public String getPmPstnQlfctnVlId();
			
}
