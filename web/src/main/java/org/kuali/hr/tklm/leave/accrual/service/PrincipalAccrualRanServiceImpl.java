/**
 * Copyright 2004-2013 The Kuali Foundation
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
package org.kuali.hr.tklm.leave.accrual.service;

import org.kuali.hr.tklm.leave.accrual.PrincipalAccrualRan;
import org.kuali.hr.tklm.leave.accrual.dao.PrincipalAccrualRanDao;
import org.kuali.hr.tklm.time.util.TKUtils;
import org.kuali.rice.krad.service.KRADServiceLocator;

public class PrincipalAccrualRanServiceImpl implements PrincipalAccrualRanService{

	private PrincipalAccrualRanDao principalAccrualRanDao;
	
	@Override
	public PrincipalAccrualRan getLastPrincipalAccrualRan(String principalId) {
		return principalAccrualRanDao.getLastPrincipalAccrualRan(principalId);
	}
	
	@Override
	public void updatePrincipalAccrualRanInfo(String principalId) {
		PrincipalAccrualRan par = this.getLastPrincipalAccrualRan(principalId);
		if(par == null) { // no data for last ran
			par = new PrincipalAccrualRan();
			par.setPrincipalId(principalId);
			par.setLastRanTs(TKUtils.getCurrentTimestamp());
		} else {
			par.setLastRanTs(TKUtils.getCurrentTimestamp());
		}
		
		KRADServiceLocator.getBusinessObjectService().save(par);
	}

	public PrincipalAccrualRanDao getPrincipalAccrualRanDao() {
		return principalAccrualRanDao;
	}

	public void setPrincipalAccrualRanDao(
			PrincipalAccrualRanDao principalAccrualRanDao) {
		this.principalAccrualRanDao = principalAccrualRanDao;
	}
}
