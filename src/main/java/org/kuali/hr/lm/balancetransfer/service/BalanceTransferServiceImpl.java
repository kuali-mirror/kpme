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
package org.kuali.hr.lm.balancetransfer.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.lm.balancetransfer.BalanceTransfer;
import org.kuali.hr.lm.balancetransfer.dao.BalanceTransferDao;

public class BalanceTransferServiceImpl implements BalanceTransferService {

	private BalanceTransferDao balanceTransferDao;
	
	@Override
	public List<BalanceTransfer> getAllBalanceTransfersForPrincipalId(
			String principalId) {
		// TODO Auto-generated method stub
		return balanceTransferDao.getAllBalanceTransfersForPrincipalId(principalId);
	}

	@Override
	public List<BalanceTransfer> getAllBalanceTransferForPrincipalIdAsOfDate(
			String principalId, Date effectiveDate) {
		return balanceTransferDao.getAllBalanceTransferForPrincipalIdAsOfDate(principalId,effectiveDate);
	}

	@Override
	public List<BalanceTransfer> getAllBalanceTransferByEffectiveDate(
			Date effectiveDate) {
		// TODO Auto-generated method stub
		return balanceTransferDao.getAllBalanceTransferByEffectiveDate(effectiveDate);
	}

	@Override
	public BalanceTransfer getBalanceTransferById(String balanceTransferId) {
		// TODO Auto-generated method stub
		return balanceTransferDao.getBalanceTransferById(balanceTransferId);
	}

	public BalanceTransferDao getBalanceTransferDao() {
		return balanceTransferDao;
	}
	
	public void setBalanceTransferDao(BalanceTransferDao balanceTransferDao) {
		this.balanceTransferDao = balanceTransferDao;
	}

}
