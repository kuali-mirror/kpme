package org.kuali.kpme.pm.positionflag.service;

import org.kuali.kpme.pm.positionflag.PositionFlag;

public interface PositionFlagService {
	/**
	 * retrieve the PositionFlag with given id
	 * @param pmPositionFlagId
	 * @return
	 */
	public PositionFlag getPositionFlagById(String pmPositionFlagId);
}
