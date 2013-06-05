package org.kuali.kpme.pm.position.service;

import org.kuali.kpme.pm.position.Position;
import org.kuali.kpme.pm.position.dao.PositionDao;

public class PositionServiceImpl implements PositionService {

	private PositionDao positionDao;
	@Override
	public Position getPosition(String id) {
		return positionDao.getPosition(id);
	}
	public PositionDao getPositionDao() {
		return positionDao;
	}
	public void setPositionDao(PositionDao positionDao) {
		this.positionDao = positionDao;
	}

}
