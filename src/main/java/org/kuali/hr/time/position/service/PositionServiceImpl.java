package org.kuali.hr.time.position.service;

import org.kuali.hr.time.position.Position;
import org.kuali.hr.time.position.PositionNumber;
import org.kuali.hr.time.position.dao.PositionDao;

public class PositionServiceImpl implements PositionService {

	private PositionDao positionDao;
	
	@Override
	public Position getPosition(Long hrPositionId) {
		return positionDao.getPosition(hrPositionId);
	}

    @Override
    public String getNextUniquePositionNumber() {
        PositionNumber positionNumber = positionDao.getNextUniquePositionNumber();
        return String.valueOf(positionNumber.getPositionNumber()+1);
    }

    @Override
    public void updatePositionNumber(String currentPositionNumber) {
        PositionNumber positionNumber = new PositionNumber();
        long updatedPositionNumber = Long.parseLong(currentPositionNumber);
        positionNumber.setPositionNumber(updatedPositionNumber);

        positionDao.saveOrUpdate(positionNumber);
    }

    public PositionDao getPositionDao() {
		return positionDao;
	}

	public void setPositionDao(PositionDao positionDao) {
		this.positionDao = positionDao;
	}

}
