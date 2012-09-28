/**
 * Copyright 2004-2012 The Kuali Foundation
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
package org.kuali.hr.lm.accrual.service;

import org.kuali.hr.lm.accrual.PrincipalAccrualRan;
import org.kuali.hr.lm.accrual.dao.PrincipalAccrualRanDao;

public class PrincipalAccrualRanServiceImpl implements PrincipalAccrualRanService{

	private PrincipalAccrualRanDao principalAccrualRanDao;
	
	@Override
	public PrincipalAccrualRan getLastPrincipalAccrualRan(String principalId) {
		return principalAccrualRanDao.getLastPrincipalAccrualRan(principalId);
	}
	
	@Override
	public void updatePrincipalAccrualRanInfo(String principalId) {
		principalAccrualRanDao.updatePrincipalAccrualRanInfo(principalId);
	}

	public PrincipalAccrualRanDao getPrincipalAccrualRanDao() {
		return principalAccrualRanDao;
	}

	public void setPrincipalAccrualRanDao(
			PrincipalAccrualRanDao principalAccrualRanDao) {
		this.principalAccrualRanDao = principalAccrualRanDao;
	}
}
