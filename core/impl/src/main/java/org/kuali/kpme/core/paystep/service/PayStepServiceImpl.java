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
package org.kuali.kpme.core.paystep.service;

import java.util.List;

import org.kuali.kpme.core.api.paystep.service.PayStepService;
import org.kuali.kpme.core.paystep.PayStep;
import org.kuali.kpme.core.paystep.dao.PayStepDao;

public class PayStepServiceImpl implements PayStepService {

	private PayStepDao payStepDao;
	
	@Override
	public PayStep getPayStepById(String payStepId) {
		return payStepDao.getPayStepById(payStepId);
	}

	public PayStepDao getPayStepDao() {
		return payStepDao;
	}

	public void setPayStepDao(PayStepDao payStepDao) {
		this.payStepDao = payStepDao;
	}

	@Override
	public List<PayStep> getPaySteps(String payStep, String groupKeyCode,
			String salaryGroup, String payGrade, String history, String active) {
		// TODO Auto-generated method stub
		return payStepDao.getPaySteps(payStep,groupKeyCode,salaryGroup,payGrade,history,active);
	}

}
