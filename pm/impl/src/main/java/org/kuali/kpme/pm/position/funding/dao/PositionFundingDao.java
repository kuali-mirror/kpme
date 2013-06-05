package org.kuali.kpme.pm.position.funding.dao;

import java.util.List;

import org.kuali.kpme.pm.position.funding.PositionFunding;

public interface PositionFundingDao {
	public List<PositionFunding> getFundingListForPosition(String hrPositionId);

}
