package org.kuali.hr.pm.positionflag.service;

import org.kuali.hr.pm.positionflag.PositionFlag;

public interface PositionFlagService {
	/**
	 * retrieve the PositionFlag with given id
	 * @param pmPositionFlagId
	 * @return
	 */
	public PositionFlag getPositionFlagById(String pmPositionFlagId);
}
