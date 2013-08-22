package org.kuali.kpme.pm.api.classification.flag;

import org.kuali.kpme.pm.api.flag.FlagContract;

/**
 * <p>ClassificationFlagContract interface</p>
 *
 */
public interface ClassificationFlagContract extends FlagContract {
	
	/**
	 * THe Position class that the ClassificationFlag is associated with
	 * 
	 * <p>
	 * pmPositionClassId of a ClassificationFlag.
	 * <p>
	 * 
	 * @return pmPositionClassId for ClassificationFlag
	 */
	public String getPmPositionClassId();

}
