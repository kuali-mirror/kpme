package org.kuali.kpme.pm.positionResponsibilityOption.service;

import org.kuali.kpme.pm.positionResponsibilityOption.PositionResponsibilityOption;
import org.kuali.kpme.pm.positionResponsibilityOption.dao.PositionResponsibilityOptionDao;

public class PositionResponsibilityOptionServiceImpl implements PositionResponsibilityOptionService {
	
	private PositionResponsibilityOptionDao positionResponsibilityOptionDao;

	public PositionResponsibilityOption getPositionResponsibilityOptionById(
			String prOptionId) {
		return positionResponsibilityOptionDao.getPositionResponsibilityOptionById(prOptionId);
	}
	
	

}
