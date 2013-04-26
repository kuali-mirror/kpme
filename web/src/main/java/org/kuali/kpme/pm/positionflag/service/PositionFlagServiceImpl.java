package org.kuali.kpme.pm.positionflag.service;

import org.kuali.kpme.pm.positionflag.PositionFlag;
import org.kuali.kpme.pm.positionflag.dao.PositionFlagDao;

public class PositionFlagServiceImpl implements PositionFlagService {

	private PositionFlagDao positionFlagDao;
	
	@Override
	public PositionFlag getPositionFlagById(String pmPositionFlagId) {
		return positionFlagDao.getPositionFlagById(pmPositionFlagId);
	}

	public PositionFlagDao getPositionFlagDao() {
		return positionFlagDao;
	}

	public void setPositionFlagDao(PositionFlagDao positionFlagDao) {
		this.positionFlagDao = positionFlagDao;
	}

}
