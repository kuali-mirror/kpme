package org.kuali.kpme.pm.positionresponsibility.service;

import org.kuali.kpme.pm.positionResponsibilityOption.dao.PositionResponsibilityOptionDao;
import org.kuali.kpme.pm.positionresponsibility.PositionResponsibility;
import org.kuali.kpme.pm.positionresponsibility.dao.PositionResponsibilityDao;


public class PositionResponsibilityServiceImpl implements PositionResponsibilityService {
	private PositionResponsibilityDao positionResponsibilityDao;

	@Override
	public PositionResponsibility getPositionResponsibilityById(
			String positionResponsibilityId) {
		return positionResponsibilityDao.getPositionResponsibilityById(positionResponsibilityId);

	}

	public PositionResponsibilityDao getPositionResponsibilityDao() {
		return positionResponsibilityDao;
	}

	public void setPositionResponsibilityDao(
			PositionResponsibilityDao positionResponsibilityDao) {
		this.positionResponsibilityDao = positionResponsibilityDao;
	}

	

}
