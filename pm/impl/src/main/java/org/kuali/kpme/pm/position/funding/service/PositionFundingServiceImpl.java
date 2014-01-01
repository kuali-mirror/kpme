/**
 * Copyright 2004-2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kpme.pm.position.funding.service;

import java.util.List;

import org.kuali.kpme.pm.api.position.funding.service.PositionFundingService;
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
