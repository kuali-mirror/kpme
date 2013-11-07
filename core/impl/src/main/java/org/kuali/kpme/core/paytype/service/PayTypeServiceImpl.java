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
package org.kuali.kpme.core.paytype.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.paytype.PayTypeContract;
import org.kuali.kpme.core.api.paytype.service.PayTypeService;
import org.kuali.kpme.core.paytype.PayType;
import org.kuali.kpme.core.paytype.dao.PayTypeDao;

public class PayTypeServiceImpl implements PayTypeService {

	private PayTypeDao payTypeDao;

	@Override
	public void saveOrUpdate(PayTypeContract payType) {
		payTypeDao.saveOrUpdate((PayType)payType);
	}

	@Override
	public void saveOrUpdate(List<? extends PayTypeContract> payTypeList) {
		payTypeDao.saveOrUpdate((List<PayType>) payTypeList);
	}

	public void setPayTypeDao(PayTypeDao payTypeDao) {
		this.payTypeDao = payTypeDao;
	}

	@Override
	public PayType getPayType(String payType, LocalDate effectiveDate) {
		return payTypeDao.getPayType(payType, effectiveDate);
	}

	@Override
	public PayType getPayType(String hrPayTypeId) {
		return payTypeDao.getPayType(hrPayTypeId);
	}
	
	@Override
	public int getPayTypeCount(String payType) {
		return payTypeDao.getPayTypeCount(payType);
	}

    @Override
    public List<PayType> getPayTypes(String payType, String regEarnCode, String descr, String location, String institution, String flsaStatus,
    		String payFrequency, LocalDate fromEffdt, LocalDate toEffdt, String active, String showHist) {
        return payTypeDao.getPayTypes(payType, regEarnCode, descr, location, institution, flsaStatus, payFrequency, fromEffdt, toEffdt, active, showHist);
    }

}
