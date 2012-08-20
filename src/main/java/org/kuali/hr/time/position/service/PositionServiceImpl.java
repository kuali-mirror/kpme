package org.kuali.hr.time.position.service;

import org.kuali.hr.time.position.Position;
import org.kuali.hr.time.position.dao.PositionDao;

public class PositionServiceImpl implements PositionService {

	private PositionDao positionDao;
	
	@Override
	public Position getPosition(String hrPositionId) {
		return positionDao.getPosition(hrPositionId);
	}
	
	@Override
	public Position getPositionByPositionNumber(String hrPositionNbr) {
		return positionDao.getPositionByPositionNumber(hrPositionNbr);
	}

    public PositionDao getPositionDao() {
		return positionDao;
	}

	public void setPositionDao(PositionDao positionDao) {
		this.positionDao = positionDao;
	}

}
