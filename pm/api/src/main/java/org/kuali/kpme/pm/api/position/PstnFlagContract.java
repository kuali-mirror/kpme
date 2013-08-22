package org.kuali.kpme.pm.api.position;

import org.kuali.kpme.pm.api.flag.FlagContract;

/**
 * <p>PstnFlagContract interface</p>
 *
 */
public interface PstnFlagContract extends FlagContract {
	
	/**
	 * The HR position id associated with the PstnFlag
	 * 
	 * <p>
	 * hrPositionId of a PstnFlag.
	 * <p>
	 * 
	 * @return hrPositionId for PstnFlag
	 */
	public String getHrPositionId();

}
