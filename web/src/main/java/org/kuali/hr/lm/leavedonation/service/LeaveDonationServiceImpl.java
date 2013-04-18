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
package org.kuali.hr.lm.leavedonation.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.kuali.hr.lm.leavedonation.LeaveDonation;
import org.kuali.hr.lm.leavedonation.dao.LeaveDonationDao;

public class LeaveDonationServiceImpl implements LeaveDonationService {

    private static final Logger LOG = Logger.getLogger(LeaveDonationServiceImpl.class);
    private LeaveDonationDao leaveDonationDao;


    public LeaveDonationDao getLeaveDonationDao() {
        return leaveDonationDao;
    }


    public void setLeaveDonationDao(LeaveDonationDao leaveDonationDao) {
        this.leaveDonationDao = leaveDonationDao;
    }


    @Override
    public LeaveDonation getLeaveDonation(String lmLeaveDonationId) {
        return getLeaveDonationDao().getLeaveDonation(lmLeaveDonationId);
    }


	@Override
	public List<LeaveDonation> getLeaveDonations(LocalDate fromEffdt, LocalDate toEffdt, String donorsPrincipalId, String donatedAccrualCategory, String amountDonated, String recipientsPrincipalId, String recipientsAccrualCategory, String amountReceived, String active, String showHist) {
		return leaveDonationDao.getLeaveDonations(fromEffdt, toEffdt, donorsPrincipalId, donatedAccrualCategory, amountDonated, recipientsPrincipalId, recipientsAccrualCategory, amountReceived, active, showHist);
	}

}
