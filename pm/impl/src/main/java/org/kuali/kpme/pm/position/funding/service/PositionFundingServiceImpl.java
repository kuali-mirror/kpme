package org.kuali.kpme.pm.position.funding.service;

import java.util.List;

import org.kuali.kpme.pm.position.funding.PositionFunding;
import org.kuali.kpme.pm.position.funding.dao.PositionFundingDao;

public class PositionFundingServiceImpl implements  PositionFundingService {

	private PositionFundingDao positionFundingDao;
	
	@Override
	public List<PositionFunding> getFundingListForPosition(String hrPositionId) {
		return positionFundingDao.getFundingListForPosition(hrPositionId);
	}

	public PositionFundingDao getPositionFundingDao() {
		return positionFundingDao;
	}

	public void setPositionFundingDao(PositionFundingDao positionFundingDao) {
		this.positionFundingDao = positionFundingDao;
	}

}
