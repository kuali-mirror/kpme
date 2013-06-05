package org.kuali.kpme.pm.position.funding.service;

import java.util.List;

import org.kuali.kpme.pm.position.funding.PositionFunding;

public interface PositionFundingService {
	public List<PositionFunding> getFundingListForPosition(String hrPositionId);

}
