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
package org.kuali.hr.paygrade.service;

import org.kuali.hr.paygrade.PayGrade;
import org.kuali.hr.paygrade.dao.PayGradeDao;

import java.sql.Date;
import java.util.List;

public class PayGradeServiceImpl implements PayGradeService {

	private PayGradeDao payGradeDao;
	@Override
	public PayGrade getPayGrade(String payGrade, String salGroup, Date asOfDate) {
		return payGradeDao.getPayGrade(payGrade, salGroup, asOfDate);
	}
 
	public void setPayGradeDao(PayGradeDao payGradeDao) {
		this.payGradeDao = payGradeDao;
	}

	@Override
	public PayGrade getPayGrade(String hrPayGradeId) {
		return payGradeDao.getPayGrade(hrPayGradeId);
	}
	@Override
	public int getPayGradeCount(String payGrade) {
		return payGradeDao.getPayGradeCount(payGrade);
	}

    @Override
    public List<PayGrade> getPayGrades(String payGrade, String payGradeDescr, String active, String showHistory) {
        return payGradeDao.getPayGrades(payGrade, payGradeDescr, active, showHistory);
    }
}
